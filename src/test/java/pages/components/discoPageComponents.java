package pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class discoPageComponents {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // constructor
    public discoPageComponents(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    // selector
    @FindBy(id = "tab-flight-tab") 
    public WebElement flightIcon;

    @FindBy(css = "[data-element-name='prominent-app-download-floating-button']")
    public WebElement appDownloadButton;

    @FindBy(id = "flight-origin-search-input")
    public WebElement inputOriginFields;

    public WebElement getFlightResultCode(String code) {
    return driver.findElement(By.cssSelector(
        "li[data-element-object-id='" + code + "']"
    ));
    }

    public WebElement getDestSelector(String code) {
    return driver.findElement(By.cssSelector("li[data-element-object-id='" + code + "']"
    ));
    }
    
    // Components.java
    public By getDestSelectorBy(String codeDest) {
        return By.cssSelector("li[data-objectid='" + codeDest + "']");
    }



    @FindBy(id = "flight-destination-search-input")
    public WebElement inputDestinationFields;

    public By getOriginSelector(String codeOrigin) {
        return By.cssSelector(String.format("li[data-element-object-id='%s']", codeOrigin));
    }

    public By getDateSelector(String date) {
        return By.cssSelector(String.format("[data-selenium-date='%s']", date));
    }

    public void passengerClickButton(String elementName, int times) {
        if (times <= 0) return; // tidak klik apa-apa kalau 0
        By selector = By.cssSelector(String.format("[data-element-name='%s']", elementName));

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(selector));
        for (int i = 0; i < times; i++) {
            button.click();
        }
    }

    public By cabinClickButton(String elementName) {        
        return By.cssSelector(String.format("[data-element-object-id='%s']", elementName));            
    }

    @FindBy(css = "[data-selenium='icon-box-child']")
    public WebElement classCabinIcon;
    
    @FindBy(css = "[data-selenium='searchButton']")
    public WebElement searchFLightButton;

    // Parent container get all flight-card
    public By flightCardContainer = By.cssSelector("[data-testid='flight-infinite-scroll'] [data-component='flight-card']");

    // Selector flights name on card
    public By flightNameSelector = By.cssSelector(
        ".sc-dmqHEX.Typographystyled__TypographyStyled-sc-1uoovui-0.czWcZb.iYbjBz");

    // Selector flight price
    public By flightPriceSelector = By.cssSelector(
        ".sc-dmqHEX.Typographystyled__TypographyStyled-sc-1uoovui-0.czWcZb.cTubDS");
    
    // 🔹 Selector Expand Detail (dinamis index)
    public By expandFlightDetailButton(int index) {
        int realIndex = index + 1;
        return By.xpath("(//*[@aria-label='Expand flight details'])[" + realIndex + "]");
    }

    // 🔹 Selector Select in detail flight
    @FindBy(css = "[data-element-name='flight-detail-select-button']")
    public WebElement selectFlightButton;

}
