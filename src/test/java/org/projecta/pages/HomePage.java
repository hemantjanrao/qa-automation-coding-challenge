package org.projecta.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.projecta.framework.base.page.BasePage;
import org.projecta.framework.webdriver.WebUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    @FindBy(xpath = "//section[@class='output-area']//p[@class='repo-amount']")
    WebElement lblSearchedResult;

    @FindBy(xpath = "//div[@class='repo-list-container']")
    WebElement tblRepositories;

    @FindBy(xpath = "//div[@class='repo-list-container']//ul//li")
    List<WebElement> listRepositories;

    @Step("Search user:  [{0}] .")
    public void findRepositories(final String user) {
        log.info("Fill Github username");
        WebUtils.waitForElementToBeDisplayed(driver, userName, 20);
        userName.sendKeys(user);

        log.info("Click on Go button");
        WebUtils.clickWithWaitForElement(driver, btnGo, 20);
    }

    /**
     * @param isPresent boolean
     * @return boolean
     */
    public boolean isUserRepositoriesPresent(boolean isPresent) {

        if (isPresent)
            WebUtils.waitForElementToBeDisplayed(driver, tblUserRepository, 20);

        return WebUtils.isElementPresent(tblUserRepository);
    }

    /**
     * This method returns count of the searched repositories
     *
     * @return int
     */
    public int getSearchedResultCount() {

        WebUtils.waitForElementToBeDisplayed(driver, lblSearchedResult, 20);

        Matcher matcher = Pattern.compile("\\d+").matcher(lblSearchedResult.getText().trim());

        if (!matcher.find())
            throw new NumberFormatException("For input string");

        return Integer.parseInt(matcher.group());
    }

    /**
     * Method to return map of repository name and description
     *
     * @return Map<String, String>
     */
    public Map<String, String> getSearchedResult() {
        WebUtils.waitForElementToBeDisplayed(driver, tblRepositories, 20);

        Map<String, String> repositories = new HashMap<>();

        for (WebElement elem : listRepositories) {
            String[] split = elem.getText().split("\n");
            repositories.put(split[0], split[1]);
        }

        return repositories;
    }
}
