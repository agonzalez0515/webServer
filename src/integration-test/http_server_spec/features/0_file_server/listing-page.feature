@0_file_server
Feature: The TODO listing page

  Scenario: Getting the listing page
    Given I make a GET request to the listing page
    Then my response should have status code 200
    And my response should return html

  Scenario: Link to detail page
    Given I make a GET request to the listing page
    Then each item listed should contain a link to a TODO item detail page
