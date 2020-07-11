package org.projecta.stepdefs;

import io.cucumber.java.en.Given;
import org.projecta.TestRunner;

public class homePageStepDefs extends TestRunner {

    @Given("User is on the page")
    public void userIsOnSearchAppHomepage() {
        driver.get("http://localhost:3000");
    }
}
