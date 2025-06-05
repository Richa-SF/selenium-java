package utilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            logger.error("WebDriver instance is null. Cannot capture screenshot.");
            return null;
        }

        String screenshotsDir = "target/screenshots/";
        File dir = new File(screenshotsDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = screenshotsDir + screenshotName + "_" + timestamp + ".png";

        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            FileHandler.copy(screenshotFile, destinationFile);
            logger.info("Screenshot captured: {}", destinationFile.getAbsolutePath());
            return destinationFile.getAbsolutePath();
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            return null;
        } catch (WebDriverException e) {
            logger.error("WebDriverException during screenshot capture: {}", e.getMessage(), e);
            return null;
        }
    }
}
