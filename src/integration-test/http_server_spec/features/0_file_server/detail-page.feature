@0_file_server
Feature: The TODO detail page

  Scenario: Getting the detail page
    Given I make a GET request to a todo detail page
    Then my response should have status code 200
    And my response should return html

  Scenario: Link to listing page
    Given I make a GET request to a todo detail page
    Then the body should contain a link to the TODO item listing page
