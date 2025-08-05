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
import utilities.*;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Arrays;

public class LoginSteps extends BaseSteps {

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

}
