package stepdefs;

import org.openqa.selenium.WebDriver;

import com.automation.utilities.driverFactory;

import io.cucumber.java.en.Then;
import pages.paymentCheckPage;

public class paymentCheckSteps {
    WebDriver driver = driverFactory.getDriver();
    paymentCheckPage paymentCheckPage = new paymentCheckPage(driver);

    @Then("user validate the detail order on payment check page")
    public void user_click_payment_button() {
        paymentCheckPage.paymentDetailCheck();
    }

    

}
