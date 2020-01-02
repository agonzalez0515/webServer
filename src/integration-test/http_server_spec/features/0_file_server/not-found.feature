@0_file_server
Feature: Page Not Found

  Scenario: Getting a page that doesn't exist
    Given I make a GET request to a page that doesn't exist
    Then my response should have status code 404
    And my response should return html
