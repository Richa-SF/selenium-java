package com.tests.stepDefinitions;
import com.tests.pages.HomePage;
import com.tests.pages.LoginPage;
import com.tests.pages.SelPracticePage;
import filter.LoggingFilter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.DriverManager;

import java.time.Duration;

public class BaseSteps {
    protected final Logger logger = LoggerFactory.getLogger(getClass());  // Dynamic per subclass
    protected WebDriver driver = DriverManager.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    protected LoggingFilter loggingFilter = new LoggingFilter();

    // Common page objects
    protected LoginPage loginPage = new LoginPage(driver);
    protected SelPracticePage selPracticePage = new SelPracticePage(driver);
    protected HomePage homePage = new HomePage(driver);
}
