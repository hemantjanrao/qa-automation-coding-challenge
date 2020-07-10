package org.projecta.framework.base.test;


import org.openqa.selenium.UnhandledAlertException;
import org.projecta.framework.listner.CustomListener;
import org.projecta.framework.webdriver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({CustomListener.class})
public class BaseWebTest extends BaseTest {

    @BeforeClass(alwaysRun = true)
    public void initializeDriver() {
        log.info("Start initializing the driver");
        try {
            driver = DriverManager.getWebDriver();
        } catch (Exception e) {
            Assert.fail("Error creating driver", e);
        }

        log.info("Finish initializing the driver");
    }

    @AfterClass(alwaysRun = true)
    public void destroyDriver() {
        try {

            log.info("Destroying the driver");
            driver.quit();
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
        }
    }
}
