@1_forms
Feature: Create a TODO item

  Scenario: Successfully creating a TODO item
    Given I make a valid POST request to the listing page
    Then my response should have status code 201
    And my response should have a Location header pointing to a detail page

  Scenario: Trying to create a TODO item with an unsupported media type
    Given I make a POST request with an unsupported media type to the listing page
    Then my response should have status code 415
    And my response should return html

  Scenario: Trying to create a TODO item with invalid values
    Given I make a POST request with invalid values to the listing page
    Then my response should have status code 400
    And my response should return html
