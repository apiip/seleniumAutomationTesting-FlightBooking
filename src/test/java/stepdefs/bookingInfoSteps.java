package stepdefs;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.automation.utilities.configReader;
import com.automation.utilities.driverFactory;

import helpers.bookingDataHelper;
import helpers.globalData;
import io.cucumber.java.en.And;
import pages.bookingInfoPage;

public class bookingInfoSteps {

    WebDriver driver = driverFactory.getDriver();
    bookingInfoPage bookingInfoPage = new bookingInfoPage(driver);


    @And ("user fill data on booking information page")
    public void user_fill_data_on_booking_information_page() {
        // Write code here that turns the phrase above into concrete actions
        bookingInfoPage.latestBookingData();

        // load global data
        Properties loadedProperties = configReader.getProperties();

        bookingDataHelper.setBookingInfoToGlobal(loadedProperties);
        System.out.println("success load data on booking information page");

        int finalTotalPassengers = globalData.adults + globalData.children + globalData.infants;
        globalData.validTotalPassengers = finalTotalPassengers;

        // input form
        bookingInfoPage.waitUntilPageLoaded();
        bookingInfoPage.fillSubmitForm(finalTotalPassengers);

        // Submit form
        assert driver.getPageSource().contains("Thank you for your booking");
    }
}