package org.projecta.framework.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebUtils {

    /**
     * Method to click on element after waiting for specified time
     *
     * @param elem    WebElement
     * @param timeout long
     */
    public static void clickWithWaitForElement(WebDriver driver, WebElement elem, long timeout) {
        waitForElementToBeDisplayed(driver, elem, timeout);
        elem.click();
    }

    /**
     * Wait for element to be present
     *
     * @param driver  WebDriver
     * @param element WebElement
     * @param timeout long
     */
    public static void waitForElementToBeDisplayed(WebDriver driver, WebElement element, long timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new ElementNotVisibleException("Timeout" + element + " is not visible/present.");
        }
    }

    /**
     * Method to check the presence of element
     *
     * @param element WebElement
     * @return boolean
     */
    public static boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;

        }
    }
}
