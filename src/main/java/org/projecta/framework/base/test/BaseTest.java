package org.projecta.framework.base.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentAventReporter;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.projecta.framework.util.Environment;
import org.projecta.framework.util.PropertyUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseTest {

    protected WebDriver driver;
    protected Logger log = Logger.getLogger(getClass());

    //builds a new report using the html template
    ExtentHtmlReporter htmlReporter;

    protected ExtentReports extent;
    //helps to generate the logs in test report.
    protected ExtentTest test;

    ExtentAventReporter avent;

    @BeforeClass(alwaysRun = true)
    public void baseTestBeforeClass() {
        log.info("Starting the Before class of 'Base Test'");
    }

    @BeforeTest
    public void baseBeforeTest() {
        // initialize the HtmlReporter
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        //To add system or environment info by using the setSystemInfo method.
        extent.setSystemInfo("OS", System.getenv("OS"));
        extent.setSystemInfo("Browser", PropertyUtils.get(Environment.WEB_BROWSER));

        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setDocumentTitle("Extent Report Demo");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

    }

    @AfterTest
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
            test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("1.png").build());

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
        } else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }

    @AfterClass(alwaysRun = true)
    public void baseTestAfterClass() {
        log.info("Starting the After class of 'Base Test'");
    }

    @BeforeMethod(alwaysRun = true)
    public void logStartMethod(Method testMethod) {
        log.info(" =============================================== Starting test method [" + testMethod.getName() + "] " +
                "===================================");
        test = extent.createTest(testMethod.getName(), "Extent test");
    }

    @AfterMethod(alwaysRun = true)
    public void logEndMethod(Method testMethod) {
        log.info("=============================================== Ending test method [" + testMethod.getName() + "] ===================================");
    }

    /**
     * Method to read data input from csv file
     *
     * @param fileName File Name
     * @return String[][]
     */
    protected String[][] parseExcelDataToDataProvider(String fileName, String sheetName) {
        log.info("Reading data from excel '" + fileName + "' with sheet name '" + sheetName + "'");
        String[][] arrayExcelData = null;
        try {
            FileInputStream fs = new FileInputStream(fileName);
            Workbook wb = Workbook.getWorkbook(fs);
            Sheet sh = wb.getSheet(sheetName);
            int totalNoOfCols = sh.getColumns();
            int totalNoOfRows = sh.getRows();

            arrayExcelData = new String[totalNoOfRows][totalNoOfCols];
            for (int i = 0; i < totalNoOfRows; i++) {

                for (int j = 0; j < totalNoOfCols; j++) {
                    arrayExcelData[i][j] = sh.getCell(j, i).getContents();
                }

            }
        } catch (IOException | BiffException e) {
            log.error("Error reading the exel fileException-" + e);
        }
        return arrayExcelData;
    }
}
