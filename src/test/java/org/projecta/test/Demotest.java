package org.projecta.test;

import io.restassured.response.Response;
import org.projecta.framework.base.test.BaseWebTest;
import org.projecta.framework.restservice.RestService;
import org.testng.annotations.Test;

public class Demotest extends BaseWebTest {

    @Test
    public void sampleTest(){
        driver.get("http://localhost:3000");
    }

    @Test
    public void testRest(){
        Response hemantjanrao = RestService.getRepositoryList("hemantjanrao");

        System.out.println(hemantjanrao.);
    }
}
