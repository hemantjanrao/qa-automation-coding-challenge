package org.projecta.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.projecta.TestRunner;
import org.projecta.pages.HomePage;
import org.testng.Assert;

public class homePageStepDefs extends TestRunner {

    private final Logger log = Logger.getLogger(getClass());
    private HomePage homePage;

    @Before
    public void setUp(){
        homePage = new HomePage(driver);
    }

    @Given("User navigate to the application page")
    public void userIsOnTheApplicationPage() {
        homePage.navigateTo();
        log.info("User navigate to the home page");
    }

    @And("User see page {string} page header")
    public void userSeePagePageHeader(String pageHeader) {
        Assert.assertTrue(homePage.isHeaderPresentWithText(pageHeader));
        log.info(String.format("Page header with text: %s present", pageHeader));
    }

    @When("User enter {string} into search box and click 'Go' button")
    public void userEnterIntoSearchBox(String githubUserName) {
        homePage.enterUserNameToBeSearched(githubUserName);
    }
}
