package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class bookingInfoComponents {

    private final WebDriver driver;

    // constructor
    public bookingInfoComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // selector

    // contact detail n static selector
    @FindBy(id = "contact.contactFirstName")
    public WebElement contactFirstNameInput;

    @FindBy(id = "contact.contactLastName")
    public WebElement contactLastNameInput;

    @FindBy(id = "contact.contactEmail")
    public WebElement contactEmailInput;

    @FindBy(id = "contact.contactPhoneNumber")
    public WebElement contactPhoneInput;

    @FindBy(css = "ul[role='listbox']")
    public WebElement dropdownContainer;

    @FindBy(css = "input[placeholder='Search']")
    public WebElement searchInput;
    
    @FindBy(css = "button[data-testid='continue-to-payment-button']")
    public WebElement continuePayment;

    @FindBy(css = "button[data-id='addon-last-chance-CEG_UPSELL']")
    public WebElement lasChanceButton;

    @FindBy(css = "dd[data-component='mob-flight-price-total-desc']")
    public static WebElement finalPrice;

    // dynamic selector
        public WebElement passengerFirstNameInput(int passengerIndex) {
        return driver.findElement(By.id("flight.forms.i0.units.i" + passengerIndex + ".passengerFirstName"));
    }

    public WebElement passengerLastNameInput(int passengerIndex) {
        return driver.findElement(By.id("flight.forms.i0.units.i" + passengerIndex + ".passengerLastName"));
    }

    public WebElement passengerDOBDayInput(int passengerIndex) {
        return driver.findElement(By.cssSelector("[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passengerDateOfBirth-DateInputDataTestId']"));
    }

    public WebElement passengerDOBYearInput(int passengerIndex) {
        return driver.findElement(By.cssSelector("[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passengerDateOfBirth-YearInputDataTestId']"));
    }

    public WebElement birthdayDropdownButton(int passengerIndex) {
        return driver.findElement(By.cssSelector("div[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passengerDateOfBirth-MonthInputDataTestId']"));
    }

    public WebElement nationalityDropdownButton(int passengerIndex) {
        return driver.findElement(By.cssSelector("div[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passengerNationality']"));
    }

    public WebElement passportNumberInput(int passengerIndex) {
        return driver.findElement(By.id("flight.forms.i0.units.i" + passengerIndex + ".passportNumber"));
    }

    public WebElement passportDateInput(int passengerIndex) {
        return driver.findElement(By.cssSelector("[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passportExpiryDate-DateInputDataTestId']"));
    }

    public WebElement passportYearInput(int passengerIndex) {
        return driver.findElement(By.cssSelector("[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passportExpiryDate-YearInputDataTestId']"));
    }

    public WebElement passportDropdownButton(int passengerIndex) {
        return driver.findElement(By.cssSelector("div[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passportCountryOfIssue']"));
    }

    public WebElement passportDateDropdownButton(int passengerIndex) {
        return driver.findElement(By.cssSelector("div[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passportExpiryDate-MonthInputDataTestId']"));
    }

    // ===== Gender Radio per passenger =====
    public WebElement genderRadio(int passengerIndex, String gender) {
        // gender = "Male" atau "Female"
        return driver.findElement(By.cssSelector(
            "div[data-testid='flight.forms.i0.units.i" + passengerIndex + ".passengerGender'] input[aria-label='" + gender + "'][type='radio']"
        ));
    }

}
