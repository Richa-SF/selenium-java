package com.tests.hooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.*;
import java.io.ByteArrayInputStream;
import java.time.Duration;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private WebDriver driver;
    @Before
    public void setUp(Scenario scenario) {
        ScenarioContext.setScenario(scenario);
        logger.info("Starting scenario: {}", scenario.getName());
        driver = DriverManager.getDriver();
        ScenarioContext.setAttribute("driver", driver);  // Optional: make driver available globally
    }
    @After
    public void tearDown() {
        Scenario scenario = ScenarioContext.getScenario();
        if (driver != null) {
            if (ScenarioContext.isScenarioFailed() || scenario.isFailed()) {
                logger.info("Scenario failed: {}", scenario.getName());
                ScreenshotUtils.captureScreenshot(driver, scenario.getName().replaceAll("[^a-zA-Z0-9]", "_"));
                // Attach to Allure
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
            }
            DriverManager.quitDriver();
            ScenarioContext.reset();
            logger.info("Completed scenario: {}", scenario.getName());
        }
    }
}
