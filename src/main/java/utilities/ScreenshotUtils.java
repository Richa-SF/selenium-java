package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler; // Use FileHandler for better file operations
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
        /**
     * Captures a screenshot of the current WebDriver instance and saves it to a specified directory.
     * The filename includes a timestamp to ensure uniqueness.
     *
     * @param driver The WebDriver instance to capture the screenshot from.
     * @param screenshotName The base name for the screenshot file (e.g., "LoginFailure").
     * @return The absolute path to the saved screenshot file, or null if an error occurred.
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            System.err.println("WebDriver instance is null. Cannot capture screenshot.");
            return null;
        }

        // Ensure the screenshots directory exists
        String screenshotsDir = "target/screenshots/";
        File dir = new File(screenshotsDir);
        if (!dir.exists()) {
            dir.mkdirs(); // Creates the directory including any necessary but nonexistent parent directories.
        }

        // Generate a unique filename with timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = screenshotsDir + screenshotName + "_" + timestamp + ".png";

        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            FileHandler.copy(screenshotFile, destinationFile);
            System.out.println("Screenshot captured: " + destinationFile.getAbsolutePath());
            return destinationFile.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (WebDriverException e) {
            System.err.println("WebDriverException during screenshot capture: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}