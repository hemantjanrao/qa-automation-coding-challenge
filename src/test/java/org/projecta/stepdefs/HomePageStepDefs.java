package org.projecta.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.projecta.acceptance.cucumber.TestRunner;
import org.projecta.framework.restservice.RepositoryResponse;
import org.projecta.framework.restservice.RestService;
import org.projecta.pages.HomePage;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class HomePageStepDefs extends TestRunner {

    private final Logger log = Logger.getLogger(getClass());
    private HomePage homePage;
    private List<RepositoryResponse> webRepositoriesResult;
    private final Random rand = new Random();
    private String userName = null;

    @Before
    public void setUp() {
        homePage = new HomePage(driver);
    }

    @Given("User navigate to the application page")
    public void user_is_on_the_application_page() {
        homePage.navigateTo();
        log.info("User navigate to the home page");
    }

    @And("See {string} page header")
    public void user_see_page_header(String pageHeader) {
        Assert.assertTrue(homePage.isHeaderPresentWithText(pageHeader), "Either header not " +
                "present or text doesn't match");
        log.info(String.format("Page header with text: %s present", pageHeader));
    }

    @When("Enter {string} into search box")
    public void user_enter_into_searchBox(String githubUserName) {
        userName = githubUserName;
        homePage.enterUserName(githubUserName);
        log.info(String.format("enter %s username to be search", githubUserName));
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
            Assert.assertTrue(homePage.getSuccessMessage().equalsIgnoreCase("Success!"),
                    String.format("Success messages is incorrect : %s", homePage.getSuccessMessage()));
        } else if (searchResult.toLowerCase().equalsIgnoreCase("failure")) {
            Assert.assertTrue(homePage.getFailureMessage().equalsIgnoreCase("Github user not found"),
                    String.format("Failure messages is incorrect : %s", homePage.getFailureMessage()));
        }
    }

    @And("Get all the public github repositories for the {string} user")
    public void get_all_the_public_github_repositories_for_the_user(String userName) {
        log.info(String.format("Get all the public repositories for the given user %s", userName));
        Assert.assertTrue(homePage.repositoriesPresent(), String.format("No repositories found for %s", userName));

        List<RepositoryResponse> apiRepositoriesResult = RestService.getRepos(userName);
        webRepositoriesResult = homePage.getSearchedRepositoriesResult();

        // And, Get correct number of public github repositories count into message
        Assert.assertEquals(homePage.getSearchedResultCount(), apiRepositoriesResult.size(),
                String.format("Found %s Repos but actual repositories count on github is %s"
                        , homePage.getSearchedResultCount(), apiRepositoriesResult.size()));

        // And, Get correct number of public github repositories for the given username
        Assert.assertEquals(webRepositoriesResult.size(), apiRepositoriesResult.size(), String.format("Number of displayed " +
                        "repositories:  %s but actual repositories on github are %s", webRepositoriesResult.size(),
                apiRepositoriesResult.size()));
    }

    @And("On clicking any repository link navigate user to actual github repository")
    public void on_click_any_repository_name_link_navigate_user_to_actual_github_repository() {
        String randomRepositoryNameToBeOpened = webRepositoriesResult.get(rand.nextInt(webRepositoriesResult.size())).name;
        String actualGitHubURL = homePage.openGithubRepo(randomRepositoryNameToBeOpened);

        String constructedGithubURL = homePage.constructGithubUrl(randomRepositoryNameToBeOpened, userName);
        Assert.assertTrue(actualGitHubURL.equalsIgnoreCase(constructedGithubURL), String.format("Opened url %s is not" +
                " github repository", constructedGithubURL));
    }

    @And("See correct error message with text {string}")
    public void see_correct_error_message_with_text(String errorMessageText) {
        Assert.assertTrue(homePage.getFailureMessage().trim().equalsIgnoreCase(errorMessageText.trim()),
                String.format("Expected %s found %s", homePage.getFailureMessage(), errorMessageText));
    }
}
