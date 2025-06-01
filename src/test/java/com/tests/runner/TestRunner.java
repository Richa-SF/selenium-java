package com.tests.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

    @CucumberOptions(
            features = "classpath:com/tests/features/Login.feature",
            glue = "com.tests.stepDefinitions",
            plugin = {"pretty", "html:target/cucumber-reports.html"},
            monochrome = true

    )
    public class TestRunner extends AbstractTestNGCucumberTests {
    }


