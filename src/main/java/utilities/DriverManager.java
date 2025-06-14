
        package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import filter.LoggingFilter;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);


    public static WebDriver getDriver() {
        if (driver.get() == null) {
            logger.info("Initializing new ChromeDriver for thread: {}", Thread.currentThread().getId());

            // 1. Automatically downloads and sets up the chromedriver executable
            WebDriverManager.chromedriver().setup();

            // 2. Creates a new Chrome browser instance
            WebDriver webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();

            // 3. Stores this driver instance in our ThreadLocal variable
            driver.set(webDriver);
        }
        // Returns the driver for the current thread
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting WebDriver for thread: {}", Thread.currentThread().getId());
            driver.get().quit();
            driver.remove();
        }
    }
}
