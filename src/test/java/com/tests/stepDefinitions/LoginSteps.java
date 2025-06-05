package com.tests.stepDefinitions;
import com.tests.pages.LoginPage;
import com.tests.pages.SelPracticePage;
import filter.LoggingFilter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utilities.ApiHelper;
import utilities.ConfigManager;
import utilities.DriverManager;
import utilities.ScenarioContext;

import java.time.Duration;
import java.util.Arrays;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private WebDriver driver;
    private LoginPage loginPage;
    private SelPracticePage selPracticePage;
    private WebDriverWait wait;
    private LoggingFilter loggingFilter; // FIXED: Correctly declared
    private String createdCaseId; // Stores caseId for API steps

    @Before
    public void setup(Scenario scenario) {
        ScenarioContext.setScenario(scenario);
        logger.info("Starting scenario: {}", scenario.getName());
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        loggingFilter = new LoggingFilter(); // FIXED: Correct initialization
        loginPage = new LoginPage(driver);
        selPracticePage = new SelPracticePage(driver);
    }

    @After
    public void afterScenario() {
        if (driver != null) {
            if (ScenarioContext.isScenarioFailed() || ScenarioContext.getScenario().isFailed()) {
                logger.info("Scenario failed: {}", ScenarioContext.getScenario().getName());
                captureScreenshot("Scenario Failed: " + ScenarioContext.getScenario().getName());
                loggingFilter.attachLogsToAllure("UI Logs: " + ScenarioContext.getScenario().getName());
            }
            DriverManager.quitDriver();
            logger.info("Completed scenario: {}", ScenarioContext.getScenario().getName());
            ScenarioContext.reset();
        }
    }

    @Given("I open the login page")
    public void i_open_the_login_page() {
        driver.get("https://example.com/login");
    }

    @Given("I open the select page")
    public void i_open_the_select_page() {
        driver.get("https://www.tutorialspoint.com/selenium/practice/select-menu.php");
    }

    @When("I select one value")
    public void i_select_one_value() {
        selPracticePage.selectSingleValue("Other");
    }

    @When("I select date")
    public void i_select_date() {
        selPracticePage.selectDate("August 2025", "1");
    }

    @When("I select multi values")
    public void i_select_multi_values() {
        selPracticePage.selectMultiValues(Arrays.asList("Books", "Movies, Music & Games"));
    }

    @Given("I am on homepage of salesforce {string}")
    @Step("Navigating to Salesforce homepage for environment: {0}")
    public void i_am_on_homepage_of_salesforce(String env) {
        try {
            logger.info("Navigating to Salesforce environment: {}", env);
            String url = ConfigManager.getUrl(env);
            driver.get(url);
            loginPage.enterUsername(ConfigManager.getUsername(env));
            loginPage.enterPassword(ConfigManager.getPassword(env));
            loginPage.clickLoginButton();
            driver.get(ConfigManager.gethomePageUrl(env));
        } catch (Exception e) {
            logger.error("UI error: {}", e.getMessage());
            ScenarioContext.markScenarioFailed();
            throw e;
        }
    }

    @When("I create a new case with subject {string} and description {string}")
    @Step("Creating case with subject: {0}")
    public void i_create_a_new_case_with_subject_and_description(String subject, String description) {
        try {
            logger.info("Creating case with subject: {}", subject);
            createdCaseId = ApiHelper.createCase(subject, description, "Web", "New", "High", "0035g00000ABCDE");
            logger.info("Created case with ID: {}", createdCaseId);
        } catch (Exception e) {
            logger.error("API error: {}", e.getMessage());
            ScenarioContext.markScenarioFailed();
            throw e;
        }
    }

    @Then("The case should be created successfully")
    public void the_case_should_be_created_successfully() {
        Assert.assertNotNull("Case ID should not be null", createdCaseId);
    }

    private void captureScreenshot(String name) {
        logger.info("Capturing screenshot: {}", name);
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new java.io.ByteArrayInputStream(screenshot), ".png");
    }
}
