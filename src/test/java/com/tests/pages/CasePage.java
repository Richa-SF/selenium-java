package com.tests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CasePage extends BasePage {

    public CasePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[normalize-space()='Status']/parent::div/following-sibling::div//lightning-formatted-text")
    private WebElement getstatus;
    @FindBy(xpath = "//button[@name='Edit']")
    private WebElement edit;
    @FindBy(xpath = "//button[normalize-space()='Save']")
    private WebElement save;
    @FindBy(xpath = "//span[contains(@class,'toastMessage')]")
    private WebElement toast;

    public void verifyToast(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       String message= wait.until(ExpectedConditions.visibilityOf(toast)).getText();
       System.out.println(message);
    }

    public void clickSave(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       wait.until(ExpectedConditions.elementToBeClickable(save)).click();
    }
    public void selectStatusValue(String status) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Status']")));
        dropdown.click();
        dropdown.sendKeys(status);
        dropdown.sendKeys(Keys.ENTER);
    }

    public String getStatusMethod() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement statusElement = wait.until(ExpectedConditions.visibilityOf(getstatus));
        return statusElement.getText();
    }

    public void clickonEdit() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement editElement = wait.until(ExpectedConditions.visibilityOf(edit));
        editElement.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.forceModalSpinner")
        ));

// 2. Wait for modal header to appear
        WebElement modalHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'slds-modal__header')]")
        ));

    }

    public List<String> getStatusOptions() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Click the Status dropdown button
        WebElement statusButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Status']")));
        statusButton.click();

        // Wait for all dropdown options to be visible
        List<WebElement> options = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(
                        By.xpath("//lightning-base-combobox-item[@role='option']")
                ));

        // Extract visible text from each option
        List<String> statusValues = new ArrayList<>();
        for (WebElement option : options) {
            System.out.println(option.getText());
        }

        return statusValues;
    }
}