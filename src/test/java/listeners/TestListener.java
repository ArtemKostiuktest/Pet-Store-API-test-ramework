package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

public class TestListener implements ITestListener {

    public static final Logger logger = Logger.getLogger(TestListener.class);

    private void logInfo(String message) {
        logger.info("====================================================");
        logger.info(message);
        logger.info("====================================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logInfo("Test started: " + result.getMethod().getMethodName());
        logger.info("Description: " + result.getMethod().getDescription());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logInfo("Test failed: " + result.getMethod().getMethodName());
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            logger.error("Error message: " + throwable.getMessage());
            logger.error("Stacktrace: ", throwable);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logInfo("Test finished successfully: " + result.getMethod().getMethodName());
        long duration = result.getEndMillis() - result.getStartMillis();
        logger.info("Duration: " + duration + " ms");
        logger.info("Test passed with the following result: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logInfo("Test skipped: " + result.getMethod().getMethodName());
        logger.info("Reason: " + result.getThrowable());
    }

    @Override
    public void onStart(ITestContext context) {
        logInfo("Starting the test suite: " + context.getName());
        logger.info("Test suite description: " + context.getSuite().getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logInfo("Test suite finished: " + context.getName());
        logger.info("Total number of tests run: " + context.getAllTestMethods().length);
    }
}