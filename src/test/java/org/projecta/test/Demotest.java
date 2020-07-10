package org.projecta.test;

import org.projecta.framework.base.test.BaseWebTest;
import org.testng.annotations.Test;

public class Demotest extends BaseWebTest {

    @Test
    public void sampleTest(){
        driver.get("http://localhost:3000");
    }
}
