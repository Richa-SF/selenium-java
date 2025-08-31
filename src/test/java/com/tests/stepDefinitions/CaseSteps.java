package com.tests.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CaseSteps extends BaseSteps {
    @Then("I get details of case")
    public void i_get_details_of_case(){
      casePage.getStatusMethod();
      System.out.println("Status of case is "+casePage.getStatusMethod());
    }
    @And("I click on Edit")
    public void i_click_on_Edit() throws InterruptedException {
        casePage.clickonEdit();
    }
    @And("I get all values of status")
    public void I_get_all_values_of_status() throws InterruptedException {
       casePage.getStatusOptions();
    }

    @Then("I select {string}")
    public void i_select(String status){
        casePage.selectStatusValue(status);
        casePage.clickSave();
        casePage.verifyToast();

    }

}
