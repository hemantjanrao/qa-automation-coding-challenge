package org.projecta.framework.base.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {

    protected WebDriver driver;
    protected Logger log = Logger.getLogger(getClass());

    @BeforeClass(alwaysRun = true)
    public void baseTestBeforeClass() {
        log.info("Starting the Before class of 'Base Test'");
    }

    @AfterClass(alwaysRun = true)
    public void baseTestAfterClass() {
        log.info("Starting the After class of 'Base Test'");
    }

    @BeforeMethod(alwaysRun = true)
    public void logStartMethod(Method testMethod) {
        log.info(" =============================================== Starting test method [" + testMethod.getName() + "] ===================================");
    }

    @AfterMethod(alwaysRun = true)
    public void logEndMethod(Method testMethod) {
        log.info("=============================================== Ending test method [" + testMethod.getName() + "] ===================================");
    }
}
