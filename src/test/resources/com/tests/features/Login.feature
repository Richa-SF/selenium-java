Feature: Login functionality
#  Scenario: Successful login
#    Given I open the login page
#    When I enter valid credentials
#    Then I should be redirected to the home page
  Scenario: Login to salesforce
  Given I am on homepage of salesforce "qa"
  When I create a new case with subject "Test" and description "abc"
  Then The case should be created successfully
  And I navigate to "Cases" tab
  Then I search for same "CaseNO"
  Then I click on case
    Then I get details of case
    And I click on Edit
    And I get all values of status
    Then I select "Escalated"




 # Scenario: Successful login with different roles
 #  Given I login as "admin"



