package org.projecta.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.projecta.framework.base.test.BaseWebTest;
import org.projecta.framework.restservice.RepositoryResponse;
import org.projecta.framework.restservice.RestService;
import org.projecta.framework.util.FileUtils;
import org.projecta.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Web Automation Task")
public class GithubRepoTest extends BaseWebTest {

    @DataProvider(name = "username")
    public Object[][] userName() {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(GithubRepoTest.class, "TestData.xls"), "username");
    }

    @Story("User search with existing GitHub repositories of the given username ")
    @Description("User search with existing GitHub username and get all the public GitHub repositories for the same " +
            "users")
    @Test(dataProvider = "username")
    public void testSearchPublicGithubRepositoriesForGivenUsername(String userName) {

        // Given, User navigate to the homepage
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        Assert.assertTrue(homePage.isHeaderPresentWithText("Get Github Repos"), "Either header not " +
                "present or text doesn't match");

        // When, User enter username to be searched
        homePage.enterUserName(userName);
        homePage.clickOnButtonGo();
        Assert.assertTrue(homePage.getSuccessMessage().equalsIgnoreCase("Success!"), String.format("Success " +
                "messages is incorrect : %s", homePage.getSuccessMessage()));

        // Then, See the list github repositories for given repositories
        Assert.assertTrue(homePage.repositoriesPresent(), String.format("No repositories found for %s", userName));

        List<RepositoryResponse> apiRepositoriesResult = RestService.getRepos(userName);
        List<RepositoryResponse> webRepositoriesResult = homePage.getSearchedRepositoriesResult();

        // And, Get correct number of public github repositories count into message
        Assert.assertEquals(homePage.getSearchedResultCount(), apiRepositoriesResult.size(),
                String.format("Found %s Repos but actual repositories count on github is %s"
                        , homePage.getSearchedResultCount(), apiRepositoriesResult.size()));

        // And, Get correct number of public github repositories for the given username
        Assert.assertEquals(webRepositoriesResult.size(), apiRepositoriesResult.size(), String.format("Number of displayed " +
                        "repositories:  %s but actual repositories on github are %s", webRepositoriesResult.size(),
                apiRepositoriesResult.size()));

        // And, Compare the github api and web repositories list
        Assert.assertTrue(homePage.compareData(webRepositoriesResult, apiRepositoriesResult),
                "Repositories displayed on UI and repositories on github don't match");

        // And, Open any random repository link
        String randomRepositoryNameToBeOpened = webRepositoriesResult
                .get(rand.nextInt(webRepositoriesResult.size())).name;
        String actualGitHubURL = homePage.openGithubRepo(randomRepositoryNameToBeOpened);

        // And, Verify user navigate to the correct repository into github
        String constructedGithubURL = homePage.constructGithubUrl(randomRepositoryNameToBeOpened, userName);
        Assert.assertTrue(actualGitHubURL.equalsIgnoreCase(constructedGithubURL), String.format("Opened url %s is not" +
                " github repository", constructedGithubURL));
    }

    @DataProvider(name = "errors")
    public Object[][] errorData() {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(GithubRepoTest.class, "TestData.xls"), "error");
    }

    @Story("User search username and see correct error message")
    @Description("Test to verify error message for given non existing usernames which result into error message")
    @Test(dataProvider = "errors")
    public void testErrorMessageInCaseIssueWithInput(String userName, String errorMessage) {

        // Given, User navigate to the homepage
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        Assert.assertTrue(homePage.isHeaderPresentWithText("Get Github Repos"));

        // When, User enter username to be searched
        homePage.enterUserName(userName);
        homePage.hitEnter();

        // Then, User see the appropriate error message
        Assert.assertTrue(homePage.getFailureMessage().trim().equalsIgnoreCase(errorMessage.trim()),
                String.format("Expected %s found %s", homePage.getFailureMessage(), errorMessage));
    }


}
