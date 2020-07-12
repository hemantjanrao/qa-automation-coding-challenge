mvn test -Dsurefire.suiteXmlFiles=run_test_cucumber.xml -Dcucumber.filter.tags="@qa-task"

# Automation Suite

## Summary

Framework is build using the stack
* Selenium Webdriver
* Java
* Cucumber
* TestNG
* Allure Reporting
* Extent Reporting  
* Dockerized selenium grid support
* Listeners to take the snapshot in case of test failure


<br>
### Table of Contents

* **[Summary](#Summary)**<br>
* **[Setting Developement Environment](#Setting-up-Developement-Environment)**<br>
* **[Running the Tests](#Running-the-Tests )**<br>
* **[Test Report Generation](#Test-Report-Generation)**<br>
<br>

### Project Pre-Installation

#### Dependency handling
All Dependencies are handled by Maven