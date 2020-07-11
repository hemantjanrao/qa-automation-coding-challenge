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

    @Test(dataProvider = "username")
    @Story("User search with existing GitHub repositories of the given username ")
    @Description("User search with existing GitHub username and get all the public GitHub repositories for the same users")
    public void testSearchPublicGithubRepositoriesForGivenUsername(String userName) {

        // Given, User navigate to the homepage
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        Assert.assertTrue(homePage.isHeaderPresentWithText("Get Github Repos"));

        // When, User enter username to be searched
        homePage.enterUserNameToBeSearched(userName);
        homePage.clickOnButtonGo();
        Assert.assertTrue(homePage.getSuccessMessage().equalsIgnoreCase("Success!"));

        // Then, See the list github repositories for given repositories
        Assert.assertTrue(homePage.repositoriesPresent());

        List<RepositoryResponse> repos = RestService.getRepos(userName);
        List<RepositoryResponse> searchedRepositoriesResult = homePage.getSearchedRepositoriesResult();

        // And, Get correct number of public github repositories count into message
        Assert.assertEquals(homePage.getSearchedResultCount(), repos.size());

        // And, Get correct number of public github repositories for the given username
        Assert.assertEquals(searchedRepositoriesResult.size(), repos.size());

        // And, Compare the github api and web repositories list
        Assert.assertTrue(homePage.compareData(searchedRepositoriesResult, repos));

        // And, Open any random repository link
        String linkToBeOpened = searchedRepositoriesResult.get(rand.nextInt(searchedRepositoriesResult.size())).name;
        String actualGitHubURL = homePage.openGithubRepo(linkToBeOpened);

        // And, Verify user navigate to the correct repository into github
        Assert.assertTrue(actualGitHubURL.contains(linkToBeOpened));
    }

    @DataProvider(name = "errors")
    public Object[][] errorData() {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(GithubRepoTest.class, "TestData.xls"), "error");
    }

    @Test(dataProvider = "errors")
    @Story("User search username and see correct error message")
    @Description("Test to verify error message for given non existing usernames which result into error message")
    public void testErrorMessageInCaseIssueWithInput(String userName, String errorMessage) {

        // Given, User navigate to the homepage
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        Assert.assertTrue(homePage.isHeaderPresentWithText("Get Github Repos"));

        // When, User enter username to be searched
        homePage.enterUserNameToBeSearched(userName);
        homePage.hitEnter();

        // Then, User see the appropriate error message
        Assert.assertTrue(homePage.getFailureMessage().trim().equalsIgnoreCase(errorMessage.trim()));
    }


}
