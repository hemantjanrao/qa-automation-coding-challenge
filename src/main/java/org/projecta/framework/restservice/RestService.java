package org.projecta.framework.restservice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.projecta.framework.util.Environment;
import org.projecta.framework.util.PropertyUtils;

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
}
