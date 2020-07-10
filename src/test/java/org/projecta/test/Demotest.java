package org.projecta.test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.projecta.framework.base.test.BaseWebTest;
import org.projecta.framework.restservice.RepositoryResponse;
import org.projecta.framework.restservice.RestService;
import org.projecta.framework.restservice.TestHelper;
import org.projecta.framework.util.FileUtils;
import org.projecta.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

@Epic("Web Automation Task")
public class Demotest extends BaseWebTest {

    @Test
    public void sampleTest() throws ClassNotFoundException {
        driver.get("http://localhost:3000");
        Assert.fail();
    }

    @Test
    public void testRest() {
        Response response = RestService.getRepositoryList("hemantjanrao");

        List<RepositoryResponse> repositoryResponses = TestHelper.deserializeJson(response);

        System.out.println(repositoryResponses.size());
    }

    @Test(dataProvider = "userData")
    @Story("User search with existing GitHub username and get all the public GitHub repositories for the same users")
    @Description("User search with existing GitHub username and get all the public GitHub repositories for the same users")
    public void testWeb(String userData, String status) throws ClassNotFoundException {

        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();

        homePage.findRepositories(userData);

        if (!status.trim().equals("Y")) {
            Assert.assertFalse(homePage.isUserRepositoriesPresent(false));
        } else {
            Assert.assertTrue(homePage.isUserRepositoriesPresent(true));
        }


    }

    @DataProvider(name = "userData")
    public Object[][] userData() throws IOException, IOException {
        return parseExcelDataToDataProvider(
                FileUtils.getResourcePath(Demotest.class, "TestData.xls"), "username");
    }
}
