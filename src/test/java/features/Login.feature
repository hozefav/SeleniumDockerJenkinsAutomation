Feature: Application Login

  Scenario: Home page default Login
    Given User is on Netbanking login page
    When User login into application with username and password
    Then Home page is populated
    And Cards are displayed



