package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LoginPage;
import utilities.ApiHelper;
import utilities.ConfigManager;
import utilities.DriverManager;

import java.time.Duration;
import java.util.List;

public class LoginSteps {
    WebDriver driver = DriverManager.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    private static String createdCaseId;


    @Given("I open the login page")
    public void i_open_the_login_page() {
        driver.get("https://example.com/login");
    }

    @When("I enter valid credentials")
    public void i_enter_valid_credentials() {
        // Add code to enter credentials
    }

    @Then("I should be redirected to the home page")
    public void i_should_be_redirected_to_the_home_page() {
        // Add assertions
    }

    @Given("I open the select page")
    public void i_open_the_select_page() {
        driver.get("https://www.tutorialspoint.com/selenium/practice/select-menu.php");
    }

    @When("I select one values")
    public void i_multi_select_values() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       WebElement picklist = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='form-select']")));
        picklist.click();
        Select sel=new Select(picklist);
        sel.selectByVisibleText("Other");
    }
//
@When("I select multi values")
public void i_select_multi_values() {
    // Wait for the input field to be visible
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Locate the input element (which triggers the multi-select dropdown)
    WebElement multiSelectInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='demo-multiple-select-input']")));
    js.executeScript("arguments[0].click();", multiSelectInput);

    // Click on the input field to open the dropdown
    multiSelectInput.click();

    // Wait for the dropdown options to appear (you might need to adjust this based on the actual dropdown structure)
    List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@id='select-options']//li")));

    // Loop through the options and select the desired ones
    for (WebElement option : options) {
        String optionText = option.getText();
        if (optionText.equals("Books") || optionText.equals("Movies, Music & Games")) {
            option.click();
        }
    }
}
    @Given("I am on homepage of salesforce {string}")
    public void I_am_on_homepage_of_salesforce(String env){
        String url = ConfigManager.getUrl(env);  // Fetch URL from config
        String homePageUrl = ConfigManager.gethomePageUrl(env);  // Fetch URL from config

        driver.get(url);
        String username = ConfigManager.getUsername(env); // Fetch username
        String password = ConfigManager.getPassword(env); // Fetch password
        String homeUrl = ConfigManager.gethomePageUrl(env); // Fetch password

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        driver.get(homeUrl);
    }
    @When("I create a new case with subject {string} and description {string}")
    public void i_create_a_new_case_with_subject_and_description(String subject, String description) {
        createdCaseId = ApiHelper.createCase(subject, description, "Web", "New", "High", "0035g00000ABCDE");
        System.out.println("Created Case ID: " + createdCaseId);
    }

    @Then("The case should be created successfully")
    public void the_case_should_be_created_successfully() {
        Assert.assertNotNull("Case ID should not be null", createdCaseId);
    }



}

