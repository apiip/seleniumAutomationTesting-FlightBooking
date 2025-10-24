package hooks;

import com.automation.utilities.driverFactory;

import io.cucumber.java.After;
// import io.cucumber.java.After;
import io.cucumber.java.Before;

public class hook {

    // Hook Before and After
    @Before
    public void setup() {
        driverFactory.getDriver();
    }

    @After
    public void teardown() {
        driverFactory.quitDriver();
        System.out.println("==== Driver closed - Testing Completed ====");
    }

}
