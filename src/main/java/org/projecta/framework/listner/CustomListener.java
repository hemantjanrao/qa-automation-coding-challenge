package org.projecta.framework.listner;

import org.apache.log4j.Logger;
import org.testng.*;

public class CustomListener implements IInvokedMethodListener {

    protected Logger log = Logger.getLogger(getClass());

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        log.info("Listener is activated");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
    }
}