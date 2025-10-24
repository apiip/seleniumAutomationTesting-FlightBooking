package stepdefs;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.automation.utilities.configReader;
import com.automation.utilities.driverFactory;

import helpers.bookingDataHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.discoPage;

public class dicoSteps {
    WebDriver driver = driverFactory.getDriver();
    discoPage discoPage = new discoPage(driver);
    

    @Given("user on flight tab")
    public void user_on_flight_tab() {
        
        // load global data
        Properties loadedProperties = configReader.getProperties();
        bookingDataHelper.flightSchedule(loadedProperties);
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("success fill data flight schedule");
        
        discoPage.flightSection();
        System.out.println("Berhasil ke Tab FLight");        
    }

    @When("user had input flight detail")
    public void user_had_input_flight_detail() {
        discoPage.inputDestination();
        System.out.println("success fill origin dan destination" );
        discoPage.selectTomorrowDate();
        System.out.println("success to get date -> tomorrow" );
    }

    @And("user sets passengers adult {int} child {int} infant {int}")
    // please check passenger at least have value 0 and not null 
    public void user_sets_passengers(Integer adults, Integer children, Integer infants) {
        discoPage.setPassengers(adults, children, infants);
    }

    @And("user sets flight category {string}")
    public void userSetsFlightCategory(String flightCategory) {
        discoPage.setCabin(flightCategory);
        discoPage.searchFLight();
    }

    @And("user search and select cheapest flight of {string}")
    public void userSearchFlight(String airlineName) {
        List<Map<String, String>> flights = discoPage.getFlightsByName(airlineName);

        System.out.println("\n=== ✈️ Flight Search Result for: " + airlineName + " ===");
        if (flights.isEmpty()) {
            System.out.println("❌ No matching flights found!");
        } else {
            flights.forEach(f ->
                System.out.println("Index: " + f.get("index") + 
                                   " | Airline: " + f.get("name") + 
                                   " | Price: " + f.get("price")));
        }
        // flights assertion
        assertTrue("No flights found for " + airlineName, flights.size() > 0);
        discoPage.clickCheapestFlight();
        
    }
}
