package com.tests.pages;

import com.tests.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class LoginPage extends BasePage {

   // private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
   public LoginPage(WebDriver driver) {
       super(driver); // Calls BasePage constructor
       PageFactory.initElements(driver, this);
       logger.debug("Initialized LoginPage");
   }

    @FindBy(id = "username")
    @CacheLookup
    private WebElement usernameField;

    @FindBy(id = "password")
    @CacheLookup
    private WebElement passwordField;

    @FindBy(id = "Login")
    @CacheLookup
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='slds-icon-waffle']")
    @CacheLookup
    private WebElement appLaunchButton;

    public void enterUsername(String username) {
        logger.debug("Entering username: {}", username);
        type(usernameField, username); // BasePage method
    }

    public void enterPassword(String password) {
        logger.debug("Entering password");
        type(passwordField, password); // BasePage method
    }

    public void clickLoginButton() {
        logger.debug("Clicking login button");
        click(loginButton); // BasePage method
    }

    public void clickAppLaunchButton() {
        logger.debug("Clicking app launch button");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement tab = wait.until(ExpectedConditions.visibilityOf(appLaunchButton));
        click(appLaunchButton); // BasePage method
    }
}
