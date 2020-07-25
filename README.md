# Automation Suite

## Summary

Framework is build using the stack
* [Selenium Webdriver 3](https://www.selenium.dev/documentation/en/)
* [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Cucumber](https://cucumber.io/)
* [TestNG](https://testng.org/doc/)
* [Allure Reporting](http://allure.qatools.ru/)
* [Extent Reporting](https://extentreports.com/)
* [Selenium Grid](https://www.selenium.dev/documentation/en/grid/grid_3/components_of_a_grid/)
* Listeners to take the snapshot in case of test failure

<br>

### Table of Contents

* **[Project Pre-Installation](#Project-Pre-Installation)**<br>
* **[Running the Tests](#Running-the-Tests)**<br>
* **[Steps to run TesNG tests](#Steps-to-run-TesNG-tests)**<br>
* **[Steps to run Cucumber tests](#Steps-to-run-Cucumber-tests )**<br>
* **[Selenium Grid](#Selenium-Grid)**<br>
<br>

## Project Pre-Installation

#### Dependency handling
All Dependencies handled by Maven

#### Required software to run tests from Command Line
* [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Apache Maven 3](http://maven.apache.org/download.cgi)

#### Required software to run tests from IDE
Install *Required software to run tests from IDE* 
* [IntelliJ IDEA](https://www.jetbrains.com/de-de/idea/)
* Add Cucumber for Java plugin in IntelliJ IDEA from plugin market place

### Test Project Build

#### Steps
1. Clone the repository.
2. Go to ***qa-automation-coding-challenge*** folder.
3. Import the project as maven project.

## Running the Tests 

#### Available test suites
- TestNG tests configured to run via file: ***run_test_testng.xml***
- Cucumber tests configured to run via file:  ***run_test_cucumber.xml***

#### Test run configuration

***config.properties***
<br>
It is possible to configure test run via **config.properties**

    web.url=http://localhost:3000
    web.browser=chrome
    web.chrome.version=83.0.4103.39
    web.firefox.version=0.26.0
    web.defaultTimeout=20
    github.base.url=https://api.github.com
    repos.endpoint=users/{0}/repos
    web.isGridEnabled=false
    web.seleniumGrid=localhost:4444

#### Command line way
It is also possible to trigger tests from command line.

**Note:** 
Currently tests running again specific chrome and firefox browser versions if the browser versions are latest 
then  no need to specify browser versions framework will by default pickup latest browser version drivers.

## Steps to run TesNG tests
1. Go to ***qa-automation-coding-challenge*** folder.
2. Use the below command to run the TestNG tests
     
        mvn clean test -Dsurefire.suiteXmlFiles=run_test_testng.xml -Dweb.browser=chrome
        
#### TestNG test Report Generation

##### Using Allure to generate allure test report
1. Run the command as shown in example below after running as test as above.
    
       
        mvn allure:report           # Generate the report
        mvn allure:serve            # Open the report on browser        
        
##### TestNG tests Extent report
1. You can find generated extent report for tests under "/test-output/testReporter.html"      
        
## Steps to run Cucumber tests
1. Go to ***qa-automation-coding-challenge*** folder.    
2. Use the below command to run the Cucumber tests        
        
        mvn clean test -Dsurefire.suiteXmlFiles=run_test_cucumber.xml -Dcucumber.filter.tags="@positive" -Dweb.browser=chrome
3. Browser to be run can be configured via parameter **-Dbrowser** in commandline or via **config.properties**

#### Cucumber tests Extent report
1. You can find generated extent report for tests under "/test-output/testReporter.html"
    
## Selenium Grid

Since mostly linux box doesn't have browsers configured. We prefer to run using ***selenium grid***.

* Configure the selenium grid.
* Add the below properties in the ***config.properties***

    
      #Selenium grid
      web.isGridEnabled = false
      web.seleniumGrid= <GridHost>:<gridport> # example 'localhost:4444'
         
          
