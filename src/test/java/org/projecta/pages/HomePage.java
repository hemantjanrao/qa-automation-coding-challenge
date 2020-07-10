package org.projecta.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.projecta.framework.base.page.BasePage;

public class HomePage extends BasePage<HomePage> {

    private Logger log = Logger.getLogger(getClass());

    private String homePageUrl = "";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return homePageUrl;
    }

    @FindBy(id = "username")
    WebElement userName;

    @FindBy(xpath = "//button[text()='Go']")
    WebElement btnGo;

    /**
     * Method to search github repositories for given user name
     *
     * @param user GitHub username repositories to fetch
     */
    public void findRepositories(String user){
        log.info("Fill Github username");
        userName.sendKeys(user);

        log.info("Click on Go button");
        btnGo.submit();
    }
}
