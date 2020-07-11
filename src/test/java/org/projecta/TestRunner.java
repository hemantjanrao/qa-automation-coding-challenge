package org.projecta;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.log4j.Logger;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.projecta.framework.webdriver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml"},
        glue = {"org/projecta/stepdefs"},
        features = "src/test/resources/features",
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    protected static WebDriver driver;
    protected Logger log = Logger.getLogger(getClass());

    @BeforeMethod
    public void setUp() {
        log.info("Start initializing the driver");
        try {
            driver = DriverManager.getWebDriver();
        } catch (Exception e) {
            Assert.fail("Error creating driver", e);
        }
        log.info("Finish initializing the driver");
    }

    @AfterMethod
    public void destroyDriver() {
        try {
            log.info("Destroying the driver");
            driver.quit();
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
        }
    }

}