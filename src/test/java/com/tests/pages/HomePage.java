package com.tests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.debug("Initialized HomePage");
    }
        @FindBy(xpath = "//input[contains(@aria-label,'Search this list...')]")
        @CacheLookup
        private WebElement searchList;

   @FindBy(xpath = "//th[@data-label='Case Number']//a[@title]")
    @CacheLookup
    private WebElement searchResult;

    public void clickOnTab(String name){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//one-app-nav-bar//span[text()='Cases']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);

    }

    public void searchListMethod(String value){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement search = wait.until(ExpectedConditions.visibilityOf(searchList));
        search.sendKeys(value, Keys.ENTER);
    }

    public String searchResultMethod(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String caseNo = wait.until(ExpectedConditions.visibilityOf(searchResult)).getText();
        return caseNo;
    }

}
