package org.projecta.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.projecta.TestRunner;
import org.projecta.framework.restservice.RepositoryResponse;
import org.projecta.framework.restservice.RestService;
import org.projecta.pages.HomePage;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class homePageStepDefs extends TestRunner {

    private final Logger log = Logger.getLogger(getClass());
    private HomePage homePage;
    private List<RepositoryResponse> searchedRepositoriesResult;
    private final Random rand = new Random();

    @Before
    public void setUp() {
        homePage = new HomePage(driver);
    }

    @Given("User navigate to the application page")
    public void userIsOnTheApplicationPage() {
        homePage.navigateTo();
        log.info("User navigate to the home page");
    }

    @And("See {string} page header")
    public void userSeePagePageHeader(String pageHeader) {
        Assert.assertTrue(homePage.isHeaderPresentWithText(pageHeader));
        log.info(String.format("Page header with text: %s present", pageHeader));
    }

    @When("User enter {string} into search box")
    public void userEnterIntoSearchBox(String githubUserName) {
        homePage.enterUserNameToBeSearched(githubUserName);
    }

    @And("Search by {string}")
    public void searchBy(String searchMode) {
        if (searchMode.toLowerCase().equalsIgnoreCase("go button")) {
            homePage.clickOnButtonGo();
        } else {
            homePage.hitEnter();
        }
    }

    @Then("On {string}")
    public void on(String searchResult) {
        if (searchResult.toLowerCase().equalsIgnoreCase("success")) {
            Assert.assertTrue(homePage.getSuccessMessage().equalsIgnoreCase("Success!"));
        } else if (searchResult.toLowerCase().equalsIgnoreCase("failure")) {
            Assert.assertTrue(homePage.getFailureMessage().equalsIgnoreCase("Github user not found"));
        }
    }

    @And("Get all the public github repositories for the {string} user")
    public void getAllThePublicGithubRepositoriesForTheUser(String userName) {
        Assert.assertTrue(homePage.isUserRepositoriesPresent(true));

        List<RepositoryResponse> repos = RestService.getRepos(userName);

        searchedRepositoriesResult = homePage.getSearchedRepositoriesResult();

        Assert.assertEquals(homePage.getSearchedResultCount(), repos.size());
        Assert.assertEquals(searchedRepositoriesResult.size(), repos.size());

    }

    @And("On clicking any repository name link navigate user to actual github repository")
    public void onClickAnyRepositoryNameLinkNavigateUserToActualGithubRepository() {
        String linkToBeOpened = searchedRepositoriesResult.get(rand.nextInt(searchedRepositoriesResult.size())).name;
        String actualGitHubURL = homePage.openGithubRepo(linkToBeOpened);

        Assert.assertTrue(actualGitHubURL.contains(linkToBeOpened));
    }

    @And("See correct error message with text {string}")
    public void seeCorrectErrorMessageWithText(String errorMessageText) {
        Assert.assertTrue(homePage.getFailureMessage().trim().equalsIgnoreCase(errorMessageText.trim()));
    }
}
