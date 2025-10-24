package pages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helpers.globalData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

public class paymentCheckPage {

    WebDriver driver;
    WebDriverWait wait;

    // data-component="name-container-name"
    // data-wl-no-leak-test="true"
    // dd[data-component="mob-flight-price-total-desc"]

    // --- LOCATORS ---

    @FindBy(css = "[data-component='name-container-name']")
    private WebElement nameFinal;
    
    @FindBy(css = "[data-wl-no-leak-test='true']")
    private WebElement emailFinal; // check index 1

    @FindBy(css = "dd[data-component='mob-flight-price-total-desc']")
    public static WebElement finalPrice;

    @FindBy(css = "div[data-component='passenger-summary-list']")
    public WebElement passengerSummaryContainer;
    
    // global value
    String validName = bookingInfoPage.contactFirstName + " " + bookingInfoPage.contactLastName;
    String validEmail = bookingInfoPage.contactEmail;
    int validTotalPassengers = globalData.validTotalPassengers;
    String actualTotalPriceText = globalData.actualFinalPriceText;
    static String latestValidFLight;
    static String latestValidTotalPrice;


    // --- CONSTRUCTOR ---
    public paymentCheckPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // tunggu max 10 detik
    }

    // final passenger summary
    public int getTotalChildDivs() {
    // waiting container
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement container = wait.until(ExpectedConditions.visibilityOf(passengerSummaryContainer));

        // Ambil semua child div langsung
        List<WebElement> childDivs = container.findElements(By.xpath("./div"));

        return childDivs.size();
    }


    public void paymentDetailCheck() {
        // final price to string and remove Rp
        String priceTextInSummary = finalPrice.getText();
        // String actualDigits = priceText.replaceAll("[^0-9,]", "");
        int actualTotalPassenger = getTotalChildDivs();

        System.out.println("==========  PAYMENT CHECK   ==========");
        assertEquals(validName.toUpperCase(), nameFinal.getText().trim(), "Name mismatch!");
        assertEquals(validEmail, emailFinal.getText().trim(), "Email mismatch!");
        assertEquals(actualTotalPriceText, priceTextInSummary, "Price mismatch!");
        assertEquals(validTotalPassengers, actualTotalPassenger, "Price mismatch!");

        System.out.println("Booking info validation successful!");
        System.out.println("Name: " + validName);
        System.out.println("Email: " + validEmail);
        System.out.println("Flight: " + latestValidFLight);
        System.out.println("Price: " + priceTextInSummary);
        System.out.println("Total Passengers: " + actualTotalPassenger);
    }
}
