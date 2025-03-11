Feature: Login functionality
#  Scenario: Successful login
#    Given I open the login page
#    When I enter valid credentials
#    Then I should be redirected to the home page
  Scenario: Login to salesforce
  Given I am on homepage of salesforce "qa"
  When I create a new case with subject "Test" and description "abc"
  Then The case should be created successfully

#  Scenario: Mulitselect picklist
#    Given I open the select page
#    When I select one values



