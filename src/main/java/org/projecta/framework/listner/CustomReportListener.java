package org.projecta.framework.listner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import org.projecta.framework.util.Environment;
import org.projecta.framework.util.PropertyUtils;

import java.util.HashMap;
import java.util.Map;

public class CustomReportListener implements EventListener {
    private ExtentSparkReporter spark;
    private ExtentReports extent;
    Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
    ExtentTest scenario;
    ExtentTest step;

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
        publisher.registerHandlerFor(TestSourceRead.class, this::featureRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::ScenarioStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
    }

    private void runStarted(TestRunStarted event) {
        spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/testReport.html");
        spark.loadConfig("html-config.xml");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(spark);

        //To add system or environment info by using the setSystemInfo method.
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", PropertyUtils.get(Environment.WEB_BROWSER));
        extent.setSystemInfo("User ", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", String.valueOf(Runtime.version()));
    }

    private void runFinished(TestRunFinished event) {
        extent.flush();
    }

    private void featureRead(TestSourceRead event) {
        String featureSource = event.getUri().toString();
        String featureName = featureSource.split(".*/")[1];
        if (feature.get(featureSource) == null) {
            feature.putIfAbsent(featureSource, extent.createTest(featureName));
        }
    }

    private void ScenarioStarted(TestCaseStarted event) {
        String featureName = event.getTestCase().getUri().toString();
        scenario = feature.get(featureName).createNode(event.getTestCase().getName());
    }

    private void stepStarted(TestStepStarted event) {
        String stepName = " ";
        String keyword = "Triggered the hook :";
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
            stepName = steps.getStep().getText();
            keyword = steps.getStep().getKeyword();
        } else {
            HookTestStep hoo = (HookTestStep) event.getTestStep();
            stepName = hoo.getHookType().name();
        }
        step = scenario.createNode(Given.class, keyword + " " + stepName);
    }

    private void stepFinished(TestStepFinished event) {
        if (event.getResult().getStatus().toString() == "PASSED") {
            step.log(Status.PASS, "This passed");
        } else if (event.getResult().getStatus().toString() == "SKIPPED") {
            step.log(Status.SKIP, "This step was skipped ");
        } else {
            step.log(Status.FAIL, "This failed");
        }
    }

}
