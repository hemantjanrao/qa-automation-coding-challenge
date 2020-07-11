package org.projecta.pages;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.projecta.framework.base.page.BasePage;
import org.projecta.framework.restservice.RepositoryResponse;
import org.projecta.framework.restservice.RestService;
import org.projecta.framework.webdriver.WebUtils;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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
    private WebElement userName;

    @FindBy(xpath = "//button[@type='submit' and text()='Go']")
    private WebElement btnGo;

    @FindBy(xpath = "//div[@class='repo-list-container']")
    private WebElement tblUserRepository;

    @FindBy(xpath = "//section[@class='output-area']//p[@class='repo-amount']")
    private WebElement lblSearchedResult;

    @FindBy(xpath = "//div[@class='repo-list-container']")
    private WebElement tblRepositories;

    @FindBy(xpath = "//div[@class='repo-list-container']//ul//li")
    private List<WebElement> listRepositories;

    @FindBy(css = "header>h1")
    private WebElement lblHeader;

    @FindBy(xpath = "//p[@class='message-failure']/strong")
    private WebElement lblFailureMsg;

    @FindBy(xpath = "//p[@class='message-success']/strong")
    private WebElement lblSuccessMsg;


    /**
     * Method to enter user name to be searched
     *
     * @param user UserName
     */
    public void enterUserNameToBeSearched(final String user) {
        log.info("Fill Github username");
        WebUtils.waitForElementToBeDisplayed(driver, userName, 20);
        userName.sendKeys(user);
    }

    /**
     *  Method to search by hitting 'Go' button
     */
    public void clickOnButtonGo() {
        log.info("Click on Go button");
        WebUtils.clickWithWaitForElement(driver, btnGo, 20);
    }

    /**
     *  Method to search by hitting 'Enter' key
     */
    public void hitEnter() {
        log.info("Click on Go button");
        userName.sendKeys(Keys.ENTER);
    }

    /**
     * @param headerText Expected header text
     * @return Boolean
     */
    public boolean isHeaderPresentWithText(String headerText) {
        WebUtils.waitForElementToBeDisplayed(driver, lblHeader, 20);
        return WebUtils.getTextValue(lblHeader).equalsIgnoreCase(headerText);
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
    public List<RepositoryResponse> getSearchedRepositoriesResult() {
        WebUtils.waitForElementToBeDisplayed(driver, tblRepositories, 20);

        return listRepositories
                .stream()
                .map(l -> {
                    RepositoryResponse repositoryResponse = new RepositoryResponse();
                    String[] split = l.getText().split("\n");
                    String href = l.findElement(By.cssSelector("a")).getAttribute("href");
                    repositoryResponse.setName(split[0]);
                    repositoryResponse.setDescription(split[1]);
                    repositoryResponse.setHtml_url(href);
                    return repositoryResponse;
                })
                .collect(Collectors.toList());
    }

    /**
     * Method to compare list of searched github repositories with list of github repositories for the given user
     *
     * @param webResult List<RepositoryResponse> webResult
     * @param apiResult List<RepositoryResponse> apiResult
     * @return Boolean
     */
    public boolean compareData(List<RepositoryResponse> webResult, List<RepositoryResponse> apiResult) {
        assert webResult.size() == apiResult.size();

        Iterator<RepositoryResponse> it1 = webResult.iterator();
        Iterator<RepositoryResponse> it2 = apiResult.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            RepositoryResponse web = it1.next();
            RepositoryResponse api = it2.next();

            if (api.getDescription() == null)
                api.setDescription("–");

            assert web.getName().equalsIgnoreCase(api.getName()) &&
                    String.valueOf(web.getDescription()).equalsIgnoreCase(String.valueOf(api.getDescription())) &&
                    web.getHtml_url().equalsIgnoreCase(api.getHtml_url());
        }
        return true;
    }

    /**
     * @param repoName Repository name
     * @return String repository URL
     */
    public String openGithubRepo(final String repoName) {
        String parentWindow = driver.getWindowHandle();
        WebUtils.clickWithWaitForElement(driver, driver.findElement(By.linkText(repoName)), 20);

        ArrayList tabs = new ArrayList(driver.getWindowHandles());

        driver.switchTo().window(String.valueOf(tabs.get(1)));

        String actualGithubURL = driver.getCurrentUrl();

        driver.close();
        driver.switchTo().window(parentWindow);

        return actualGithubURL;
    }

    /**
     * Method to check all the repositories accessible and not broken
     *
     * @param searchedRepositoriesResult List<RepositoryResponse>
     * @return Boolean
     */
    public boolean checkBrokenLinks(List<RepositoryResponse> searchedRepositoriesResult) {
        boolean status = false;
        for (RepositoryResponse repository : searchedRepositoriesResult) {
            Assert.assertEquals(RestService.isLinkAvailable(repository.html_url), HttpStatus.SC_OK);
            status = true;
        }

        return status;
    }

    /**
     * Method to return failure message text
     *
     * @return String Failure message text
     */
    public String getFailureMessage() {
        WebUtils.waitForElementToBeDisplayed(driver, lblFailureMsg, 20);

        return WebUtils.getTextValue(lblFailureMsg);
    }

    /**
     * Method to return success message text
     *
     * @return String Success message text
     */
    public String getSuccessMessage() {
        WebUtils.waitForElementToBeDisplayed(driver, lblSuccessMsg, 20);

        return WebUtils.getTextValue(lblSuccessMsg);
    }
}
