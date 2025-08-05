package com.tests.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import com.tests.pages.HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import utilities.ApiHelper;
import utilities.ConfigManager;
import utilities.ScenarioContext;

public class HomeSteps extends BaseSteps {

    private String createdCaseNumber;
    @Given("I am on homepage of salesforce {string}")
    @Step("Navigating to Salesforce homepage for environment: {0}")
    public void i_am_on_homepage_of_salesforce(String env) {
        try {
            logger.info("Navigating to Salesforce environment: {}", env);
            String url = ConfigManager.getUrl();
            driver.get(url);
            loginPage.enterUsername(ConfigManager.getUsername());
            loginPage.enterPassword(ConfigManager.getPassword());
            loginPage.clickLoginButton();
            loginPage.clickAppLaunchButton();
            driver.get(ConfigManager.getHomePageUrl());
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
            createdCaseNumber = ApiHelper.createCase(subject, description, "Web", "New", "High", "0035g00000ABCDE");
            logger.info("Created case with Number: {}", createdCaseNumber);
            ScenarioContext.setAttribute("caseNumber", createdCaseNumber);
        } catch (Exception e) {
            logger.error("API error: {}", e.getMessage());
            ScenarioContext.markScenarioFailed();
            throw e;
        }
    }

    @Then("The case should be created successfully")
    public void the_case_should_be_created_successfully() {
        Assert.assertNotNull("Case ID should not be null", createdCaseNumber);
    }

    @And("I navigate to {string} tab")
    public void i_navigate_to_tab(String tab) {
        homePage.clickOnTab(tab);
    }

    @Then("I search for same {string}")
    public void I_search_for(String caseNo) {
        caseNo = (String) ScenarioContext.getAttribute("caseNumber"); // ✅ match key
        homePage.searchListMethod(caseNo + Keys.ENTER);
        String actualResult = homePage.searchResultMethod();
    }



}
