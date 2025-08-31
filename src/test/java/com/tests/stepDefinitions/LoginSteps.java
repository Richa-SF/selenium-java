package com.tests.stepDefinitions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LoginSteps extends BaseSteps {
    private static final String USERS_FILE_PATH = "src/main/resources/users.yaml";

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


    @Given("I login as {string}")
    public void i_login_as(String role) throws IOException {
        // Create an ObjectMapper for YAML files
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        // Read the YAML file and parse it into a Map
        Map<String, List<Map<String, String>>> yamlData = mapper.readValue(new File(USERS_FILE_PATH), Map.class);
        List<Map<String, String>> users = yamlData.get("users");

        String username = null;
        String password = null;

        // Iterate through the list of users to find the one with the matching role
        for (Map<String, String> user : users) {
            if (role.equals(user.get("role"))) {
                username = user.get("username");
                password = user.get("password");
                break;
            }
        }

        if (username != null && password != null) {
            // Use your existing login methods with the data from the YAML file
            // Based on your HomeSteps, this would look something like this:
            String url = ConfigManager.getUrl();
            driver.get(url);
            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();
            // You may need to add additional steps like launching the app.
        } else {
            throw new IllegalArgumentException("User role not found in users.yaml: " + role);
        }
    }
}

