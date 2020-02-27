Feature: Application Login

  Scenario: Home page default Login
    Given Initialise browser with Chrome
    And Navigage to "http://www.qaclickacademy.com/" Site
    And Click on Login link in home page to land on secure sign in Page
    When User enters "test99@gmail.com" and "123456" and logs in
    Then Verify that user is successfully logged in



