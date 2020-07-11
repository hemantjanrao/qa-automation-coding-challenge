package org.projecta.framework.restservice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.projecta.framework.util.Environment;
import org.projecta.framework.util.PropertyUtils;
import org.testng.Assert;

import java.util.List;

public class RestService {

    /**
     * Method to get the list of public github repositories of the given username
     *
     * @param userName Github User Name
     * @return Rest response
     */
    public static Response getRepositoryList(final String userName) {
        return RestAssured
                .given()
                .baseUri(PropertyUtils.get(Environment.GITHUB_URL))
                .get(PropertyUtils.get(Environment.REPOSITORY_ENDPOINT).replace("{USERNAME}", userName))
                .then()
                .extract().response();
    }

    /**
     * @param userName String
     * @return List<RepositoryResponse>
     */
    public static List<RepositoryResponse> getRepos(final String userName) {
        Response repositoryList = getRepositoryList(userName);

        Assert.assertEquals(repositoryList.statusCode(), HttpStatus.SC_OK);

        return TestHelper.deserializeJson(repositoryList);
    }

    /**
     * @param URL URL to be checked
     * @return HTTP Status code
     */
    public static int isLinkAvailable(String URL) {
        return RestAssured
                .given()
                .baseUri(URL)
                .get("/")
                .then()
                .extract()
                .statusCode();
    }
}
