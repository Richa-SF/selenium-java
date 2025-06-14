package com.tests.pages;

import com.tests.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {

   // private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

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

    public LoginPage(WebDriver driver) {
        super(driver); // Calls BasePage constructor
        PageFactory.initElements(driver, this);
        logger.debug("Initialized LoginPage");
    }

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
        click(appLaunchButton); // BasePage method
    }
}
