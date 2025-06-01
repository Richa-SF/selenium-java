package listeners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.DriverManager;
import utilities.ScreenshotUtils;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());

        // Get the WebDriver instance from DriverManager
        WebDriver driver = DriverManager.getDriver(); // This gets the WebDriver for the current thread

        if (driver != null) {
            // Use the ScreenshotUtils to capture the screenshot
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName()); // Use test method name
            if (screenshotPath != null) {
                System.out.println("Screenshot for failed test " + result.getName() + " saved at: " + screenshotPath);
                // If you were using Allure reports, you'd attach it here:
                // Allure.addAttachment("Screenshot on Failure", "image/png", new FileInputStream(screenshotPath), "png");
            }
        } else {
            System.err.println("WebDriver was null, cannot capture screenshot for failed test: " + result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not typically used for Cucumber tests
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Suite Finished: " + context.getName());
    }
}