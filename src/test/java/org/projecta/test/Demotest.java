package org.projecta.test;

import io.restassured.response.Response;
import org.projecta.framework.base.test.BaseWebTest;
import org.projecta.framework.restservice.RepositoryResponse;
import org.projecta.framework.restservice.RestService;
import org.projecta.framework.restservice.TestHelper;
import org.testng.annotations.Test;

import java.util.List;

public class Demotest extends BaseWebTest {

    @Test
    public void sampleTest(){
        driver.get("http://localhost:3000");
    }

    @Test
    public void testRest(){
        Response response = RestService.getRepositoryList("hemantjanrao");

        List<RepositoryResponse> repositoryResponses = TestHelper.deserializeJson(response);

        System.out.println(repositoryResponses.size());
    }
}
