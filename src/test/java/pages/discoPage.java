package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.utilities.configReader;

import helpers.globalData;
import pages.components.discoPageComponents;

public class discoPage {

    WebDriver driver;
    WebDriverWait wait;

    // --- LOCATORS ---
    private discoPageComponents components;

    // --- Global data ---

    // data only for this page
    private List<String> savedFlightNames = new ArrayList<>();
    private List<String> savedFlightPrices = new ArrayList<>();
    private List<Integer> savedFlightIndexes = new ArrayList<>();

    // get data config -> it can adjust to helper if the scope is bigger
    public static String urlFLight = configReader.getFlightUrl();
    public static String origin;
    public static String dest;
    public static String codeOrigin;
    public static String codeDest;

     // --- CONSTRUCTOR ---
    public discoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        this.components = new discoPageComponents(driver); // Initialize the components field
    }

    // --- ACTIONS ---

    public void flightSection() {
        System.out.println("ini url flight: " + urlFLight);
        System.out.println("ini data load: " + origin);
        System.out.println("ini data load: " + codeOrigin);
        System.out.println("ini data load: " + dest);
        System.out.println("ini data load: " + codeDest);
        driver.get(urlFLight);
    }

    // input Form

    // select destination
    public void inputDestination() {
        wait.until(ExpectedConditions.visibilityOf(components.appDownloadButton));
        components.appDownloadButton.click();
        components.inputOriginFields.sendKeys(origin);

        // waiting element
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        By originSelector = components.getOriginSelector(codeOrigin);

        // waiting visible code
        wait.until(ExpectedConditions.visibilityOfElementLocated(originSelector));

        WebElement flight = components.getFlightResultCode(codeOrigin);
        flight.click();

        components.inputDestinationFields.sendKeys(dest);

    //    WebElement suggestion = wait.until(
    //         ExpectedConditions.elementToBeClickable(components.getDestSelector(codeDest))
    //     );

        By destSelectorBy = components.getDestSelectorBy(codeDest);

        // Tunggu sampai element clickable
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(destSelectorBy));


        System.out.println("ini udah dapet " + codeDest);

        // scroll dan click with fallback JS if intercepted
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", suggestion);
            suggestion.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Normal click intercepted, menggunakan JS click fallback...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", suggestion);
        }

    }

    // get specific date
    public String getDateTomorrow() {
        // get date + 1 - modify if needed
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return tomorrow.format(formatter);
    }

    // click date by specific date
     public void selectDate(String date) {
        By selector = components.getDateSelector(date);

        // waiting element
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(selector));

        // scrolling element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateElement);

        // click with fallback JS if intercepted
        try {
            dateElement.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateElement);
        }

        System.out.println("✅ Date selected: " + date);
    }

    // wrap date on stepdefs
    public void selectTomorrowDate() {
        String tomorrow = getDateTomorrow();
        selectDate(tomorrow);
    }

    // set passengers (it already dinamis with data sampling if needed)
    public void setPassengers(int adults, int children, int infants) {
        // passengers validation (not null) - please confirm the value is not null before calling this method, at least fill wiht 0 required
        if (adults > 1) { // default 1 adult, jadi klik (adults - 1)
            components.passengerClickButton("flight-occupancy-adult-increase", adults - 1);
        }

        if (children > 0) {
            components.passengerClickButton("flight-occupancy-children-increase", children);
        }

        if (infants > 0) {
            components.passengerClickButton("flight-occupancy-infant-increase", infants);
        }

        // set passengers global data
        globalData.adults = adults;
        globalData.children = children;
        globalData.infants = infants;
        System.out.println("✅ Passengers set: Adult=" + adults + ", Child=" + children + ", Infant=" + infants);
        
    }

    // set cabin (it already dinamis with data sampling if needed)
    public void setCabin(String cabin) {
        By cabinLocator = components.cabinClickButton(cabin);
        WebElement finalCabin = driver.findElement(cabinLocator);
        finalCabin.click();
        System.out.println("✅ Cabin set: " + cabin);
        components.classCabinIcon.click();
    }

    // search flight button click
    public void searchFLight() {
        components.searchFLightButton.click();
    }

    /**
     * get top 3 data flight with targeted name.
     * @param targetName maskapai name
     * @return map list {index, name, price}
     */
    public List<Map<String, String>> getFlightsByName(String targetName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(components.flightCardContainer));

        List<WebElement> flightCards = driver.findElements(components.flightCardContainer);
        AtomicInteger indexCounter = new AtomicInteger(0);

        // Stream to get target name
        List<Map<String, String>> matchedFlights = flightCards.stream()
            .map(card -> {
                int currentIndex = indexCounter.getAndIncrement();
                wait.until(ExpectedConditions.visibilityOf(card));
                try {
                    String flightName = card.findElement(components.flightNameSelector).getText();
                    String flightPrice = card.findElement(components.flightPriceSelector).getText();

                    Map<String, String> data = new HashMap<>();
                    data.put("index", String.valueOf(currentIndex));
                    data.put("name", flightName);
                    data.put("price", flightPrice);
                    return data;
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .filter(f -> f.get("name").toLowerCase().contains(targetName.toLowerCase()))
            .limit(3)
            .collect(Collectors.toList());

        // ✅ Simpan hasil ke global variable
        matchedFlights.forEach(f -> {
            savedFlightNames.add(f.get("name"));
            savedFlightPrices.add(f.get("price"));
            savedFlightIndexes.add(Integer.parseInt(f.get("index")));
        });

        return matchedFlights;
        
    }

    // ===== select flight by name =====
    public void clickCheapestFlight() {
        // valid data validation
            if (savedFlightIndexes == null || savedFlightIndexes.isEmpty()) {
                throw new RuntimeException("No saved flight data found!");
            }

        // get index from global variable
            int minIndex = savedFlightIndexes.stream()
                    .min(Integer::compareTo)
                    .orElseThrow(() -> new RuntimeException("No valid index found!"));

            System.out.println("                                  ");
            System.out.println("Lowest flight index to click: " + minIndex);

            // minIndex in savedFlightIndexes list
            int pos = savedFlightIndexes.indexOf(minIndex);

            if (pos == -1) {
                throw new RuntimeException("Flight with index " + minIndex + " not found!");
            }

            globalData.selectedFlightName = savedFlightNames.get(pos);
            globalData.selectedFlightPrice = savedFlightPrices.get(pos);

            System.out.println("Selected flight: " + globalData.selectedFlightName + " | Price: " + globalData.selectedFlightPrice);
        
        // click expand detail
            WebElement expandBtn = driver.findElement(components.expandFlightDetailButton(minIndex));
            ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", expandBtn);

            try {
                expandBtn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", expandBtn);
            }

        // click select button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement selectBtn = wait.until(
                ExpectedConditions.elementToBeClickable(components.selectFlightButton)
            );
            selectBtn.click();

            System.out.println("Clicked select button for flight index: " + minIndex);
    }

}
