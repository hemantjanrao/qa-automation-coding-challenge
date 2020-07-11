package org.projecta.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.projecta.framework.base.test.BaseWebTest;
import org.projecta.framework.restservice.RepositoryResponse;
import org.projecta.framework.restservice.RestService;
import org.projecta.framework.restservice.TestHelper;
import org.projecta.framework.util.FileUtils;
import org.projecta.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Web Automation Task")
public class GithubRepoTest extends BaseWebTest {

    @Test(dataProvider = "username")
    @Story("User search with existing GitHub repositories of the given username ")
    @Description("User search with existing GitHub username and get all the public GitHub repositories for the same users")
    public void searchPublicGithubRepositoriesForGivenUsername(String userData, String status) {

        // Given, User navigate to the homepage
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        Assert.assertTrue(homePage.isHeaderPresentWithText("Get Github Repos"));

        // When, User enter username to be searched
        homePage.enterUserNameToBeSearched(userData);
        homePage.clickOnButtonGo();
        Assert.assertTrue(homePage.getSuccessMessage().equalsIgnoreCase("Success!"));

        // Then, User see the list of given username public github repositories
        if (!status.trim().equals("Y")) {
            Assert.assertFalse(homePage.isUserRepositoriesPresent(false));
        } else {
            Assert.assertTrue(homePage.isUserRepositoriesPresent(true));

            List<RepositoryResponse> repos = RestService.getRepos(userData);
            List<RepositoryResponse> searchedRepositoriesResult = homePage.getSearchedRepositoriesResult();

            Assert.assertEquals(homePage.getSearchedResultCount(), repos.size());
            Assert.assertEquals(searchedRepositoriesResult.size(), repos.size());

            Response response = RestService.getRepositoryList(userData);
            Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
            List<RepositoryResponse> repositoryResponses = TestHelper.deserializeJson(response);
            Assert.assertTrue(homePage.compareData(searchedRepositoriesResult, repositoryResponses));

            String linkToBeOpened = searchedRepositoriesResult.get(rand.nextInt(searchedRepositoriesResult.size())).name;
            String actualGitHubURL = homePage.openGithubRepo(linkToBeOpened);

            Assert.assertTrue(actualGitHubURL.contains(linkToBeOpened));
        }
    }

    @Test(dataProvider = "errors")
    @Story("User search username and see correct error message")
    @Description("Test to verify error message for given non existing usernames which result into error message")
    public void testErrorMessages(String userData, String errorMessage) {

        // Given, User navigate to the homepage
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
        Assert.assertTrue(homePage.isHeaderPresentWithText("Get Github Repos"));

        // When, User enter username to be searched
        homePage.enterUserNameToBeSearched(userData);
        homePage.hitEnter();

        // Then, User see the appropriate error message
        Assert.assertTrue(homePage.getFailureMessage().trim().equalsIgnoreCase(errorMessage.trim()));
    }

    @DataProvider(name = "username")
    public Object[][] userName() {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(GithubRepoTest.class, "TestData.xls"), "username");
    }

    @DataProvider(name = "errors")
    public Object[][] errorData() {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(GithubRepoTest.class, "TestData.xls"), "error");
    }
}
