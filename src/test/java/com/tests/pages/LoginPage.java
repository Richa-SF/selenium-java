package com.tests.pages;

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

public class LoginPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    @SuppressWarnings("unused")
    private WebDriver driver;
    private WebDriverWait wait;

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
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
        logger.debug("Initialized LoginPage");
    }

    public void enterUsername(String username) {
        logger.debug("Entering username: {}", username);
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        logger.debug("Entering password");
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void clickLoginButton() {
        logger.debug("Clicking login button");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clickAppLaunchButton() {
        logger.debug("Clicking app launch button");
        wait.until(ExpectedConditions.elementToBeClickable(appLaunchButton)).click();
    }
}
