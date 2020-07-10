package org.projecta.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.projecta.framework.base.page.BasePage;
import org.projecta.framework.webdriver.WebUtils;
import org.testng.Assert;

public class HomePage extends BasePage<HomePage> {

    private final Logger log = Logger.getLogger(getClass());

    private final String homePageUrl = "/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return homePageUrl;
    }

    @FindBy(id = "username")
    WebElement userName;

    @FindBy(xpath = "//button[@type='submit' and text()='Go']")
    WebElement btnGo;

    @FindBy(xpath = "//div[@class='repo-list-container']")
    WebElement tblUserRepository;

    @Step("Search user:  [{0}] .")
    public void findRepositories(final String user) {
        log.info("Fill Github username");
        WebUtils.waitForElementToBeDisplayed(driver, userName, 60);
        userName.sendKeys(user);

        log.info("Click on Go button");
        WebUtils.clickWithWaitForElement(driver, btnGo, 60);
    }

    public boolean isUserRepositoriesPresent(boolean isPresent) {

        if (isPresent)
            WebUtils.waitForElementToBeDisplayed(driver, tblUserRepository, 60);

        return WebUtils.isElementPresent(tblUserRepository);
    }
}
