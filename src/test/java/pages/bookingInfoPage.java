package pages;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helpers.bookingDataHelper;
import helpers.globalData;
import pages.components.bookingInfoComponents;

public class bookingInfoPage {

    private WebDriver driver;
    private WebDriverWait wait;


    // ==== Fields ====
    private bookingInfoComponents components;

    // ==== Global variable for Contact & Passenger ====
    public static String contactFirstName;
    public static String contactLastName;
    public static String contactEmail;
    public static String contactPhone;
    public static String isMale;

    public static String selectedFlightName;
    public static String passengerLastName;
    public static String dobDay;
    public static String dobYearAdult;
    public static String dobYearChild;
    public static String dobYearInfant;
    public static String passportNumber;
    public static String birthMonth;
    public static String nationality;
    public static String dopYear;

    // --- CONSTRUCTOR ---
    public bookingInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
        this.components = new bookingInfoComponents(driver); // Initialize the components field
    }

    // --- Get Latest Data ---
    public void latestBookingData() {
        
        // latest flight data
        String latestFlightName = globalData.selectedFlightName;
        String latestPrice = globalData.selectedFlightPrice;

        // latest passenger data
        int latestAdults = globalData.adults;
        int latestChildren = globalData.children;
        int latestInfants = globalData.infants;

        System.out.println("===== This is Final Booking Data for Assertion =====");
        System.out.println("===== Latest Flight Booking Data: " + latestFlightName + " | " + latestPrice + " =====");
        System.out.println("===== Latest Passenger Data: " + latestAdults + " Adults | " + latestChildren + " Children | " + latestInfants + " Infants =====");
        
        paymentCheckPage.latestValidFLight = latestFlightName;


    }

    // Fill form using global variable

    // ===== Wait until page fully loaded / main element visible =====
    public void waitUntilPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(components.contactFirstNameInput));
    }

    // load global data form
    public void setBookingInfo(Properties prop) {
        bookingDataHelper.setBookingInfoToGlobal(prop);
    }

    public void fillFormWithGlobalData(int finalTotalPassengers) {

        waitUntilPageLoaded();

        // Contact static booking
            components.contactFirstNameInput.sendKeys(contactFirstName);
            components.contactLastNameInput.sendKeys(contactLastName);
            components.contactEmailInput.sendKeys(contactEmail);
            components.contactPhoneInput.sendKeys(contactPhone);

        // input datafor all passenger 
        for (int passenger = 0; passenger < finalTotalPassengers; passenger++) {

            String passengerCategory = "(Adult)";
            // get passenger category
            if (passenger >= 1) {
                WebElement h3 = driver.findElement(By.cssSelector(
                    "div[data-testid='form-card-flight-" + passenger + "'] h3"
                ));
                String[] words = h3.getText().trim().split("\\s+");
                String lastWord = words[words.length - 1]; // get last word category validation

                passengerCategory = lastWord;
            }


            // count n loop for each passenger    
            components.genderRadio(passenger, isMale).click();
            components.passengerFirstNameInput(passenger).sendKeys(selectedFlightName);
            components.passengerLastNameInput(passenger).sendKeys(passengerLastName);
            components.passengerDOBDayInput(passenger).sendKeys(dobDay);
            System.out.println("passengerCategory: " + passengerCategory);
            switch (passengerCategory) {
                case "(Adult)":
                    components.passengerDOBYearInput(passenger).sendKeys(dobYearAdult);
                    break;
                case "(Child)":
                    components.passengerDOBYearInput(passenger).sendKeys(dobYearChild);
                    break;
                case "(Infant)":
                    components.passengerDOBYearInput(passenger).sendKeys(dobYearInfant);
                    break;
                default:
                    components.passengerDOBYearInput(passenger).sendKeys(dobYearAdult);
                    break;
            }
            
            selectDropdown("nationality", passenger);
            selectDropdown("birthDay", passenger );

            // intermittent passport issue
            List<WebElement> passportIssueFields = driver.findElements(By.id("flight.forms.i0.units.i" + passenger + ".passportNumber"));

            if (!passportIssueFields.isEmpty()) {
                selectDropdown("passportIssue", passenger );
                selectDropdown("passportExpiryDate", passenger );
                components.passportNumberInput(passenger).sendKeys(passportNumber);
                components.passportDateInput(passenger).sendKeys(dobDay);
                components.passportYearInput(passenger).sendKeys(dopYear);
                System.out.println("Passport issue field.");
            } else {
                System.out.println("Passport issue field is not Exist.");
            }
            
        }
        
        // get final price calculation
        String actualFinalPriceText = bookingInfoComponents.finalPrice.getText();
        globalData.actualFinalPriceText = actualFinalPriceText;
        System.out.println("Actual Total Final Price: " + actualFinalPriceText);
    }

    // ==== dropdown methods ====

    public void selectDropdown(String param, int totalPassenger) {
    String valueDropdown;
    WebElement dropdownButton;

    // validation dropdown case
    switch (param) {
        case "nationality":
            valueDropdown = nationality;
            dropdownButton = components.nationalityDropdownButton(totalPassenger);
            break;
        case "birthDay":
            valueDropdown = birthMonth;
            dropdownButton = components.birthdayDropdownButton(totalPassenger);
            break;
        case "passportIssue":
            valueDropdown = nationality;
            dropdownButton = components.passportDropdownButton(totalPassenger);
            break;
        case "passportExpiryDate":
            valueDropdown = birthMonth;
            dropdownButton = components.passportDateDropdownButton(totalPassenger);
            break;
        default:
            throw new RuntimeException("Tidak ada dropdown: " + param);
    }

    // dropdown click
    dropdownButton.click();

    // get dropdown text = valueDropdown
    String xpath = String.format(".//li//span[text()='%s']", valueDropdown);
    WebElement targetSpan = new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

    // scroll + click JS
    ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", targetSpan);
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetSpan);
}



    // Submit form
    public void submitForm() {
        components.continuePayment.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(components.lasChanceButton));
        
        components.lasChanceButton.click();
        System.out.println("Submit form");
    }

    public void fillSubmitForm(int finalTotalPassengers) {
        fillFormWithGlobalData(finalTotalPassengers);
        submitForm();
    }
    
}
