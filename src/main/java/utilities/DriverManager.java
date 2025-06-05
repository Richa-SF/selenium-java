
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
            logger.info("Initializing ChromeDriver for thread: {}", Thread.currentThread().getId());
            // Clear WebDriverManager cache to avoid using outdated ChromeDriver
            WebDriverManager.chromedriver().clearDriverCache().clearResolutionCache();
            // Force WebDriverManager to download ChromeDriver for Chrome 137
            WebDriverManager.chromedriver().browserVersion("137").setup();
            // Log the ChromeDriver version being used (optional, for debugging)
            String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
            logger.info("Using ChromeDriver at: {}", chromeDriverPath);
            WebDriver rawDriver = new ChromeDriver();
            LoggingFilter loggingFilter = new LoggingFilter();
            WebDriver decoratedDriver = new EventFiringDecorator<>(loggingFilter).decorate(rawDriver);
            driver.set(decoratedDriver);
            driver.get().manage().window().maximize();
        }
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
