package com.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.time.Duration;

public class SelPracticePage extends BasePage {
    @SuppressWarnings("unused")
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//select[@class='form-select']")
    @CacheLookup
    private WebElement selectOneForm;

    @FindBy(xpath = "//button[contains(text(),'Widget')]")
    @CacheLookup
    private WebElement widgetButton;

    @FindBy(xpath = "//a[contains(text(),'Date Picker')]")
    @CacheLookup
    private WebElement datePickerLink;

    @FindBy(xpath = "//div[text()='Select Date']/following-sibling::div")
    @CacheLookup
    private WebElement selectDateField;

    @FindBy(xpath = "//div[@class='ui-datepicker-title']")
    @CacheLookup
    private WebElement datePickerTitle;

    @FindBy(xpath = "//a[@title='Next']")
    @CacheLookup
    private WebElement nextMonthButton;

    @FindBy(xpath = "//input[@id='demo-multiple-select-input']")
    @CacheLookup
    private WebElement multiSelectInput;

    @FindBy(xpath = "//ul[@id='select-options']//li")
    @CacheLookup
    private List<WebElement> multiSelectOptions;

    public SelPracticePage(WebDriver driver) {
        super(driver); // Calls BasePage constructor
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void selectSingleValue(String value) {
        wait.until(ExpectedConditions.visibilityOf(selectOneForm));
        selectOneForm.click();
        Select select = new Select(selectOneForm);
        select.selectByVisibleText(value);
    }

    public void selectDate(String targetMonth, String targetDate) {
        wait.until(ExpectedConditions.elementToBeClickable(widgetButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(datePickerLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(selectDateField)).click();

        // Navigate to the target month
        while (!wait.until(ExpectedConditions.visibilityOf(datePickerTitle)).getText().equals(targetMonth)) {
            wait.until(ExpectedConditions.elementToBeClickable(nextMonthButton)).click();
        }

        // Select the target date
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='" + targetDate + "']")
        ));
        dateElement.click();
    }

    public void selectMultiValues(List<String> values) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(multiSelectInput));
        js.executeScript("arguments[0].click();", multiSelectInput);
        multiSelectInput.click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElements(multiSelectOptions));
        for (WebElement option : options) {
            String optionText = option.getText();
            if (values.contains(optionText)) {
                option.click();
            }
        }
    }

}
