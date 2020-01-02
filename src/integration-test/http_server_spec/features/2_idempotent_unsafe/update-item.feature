@2_idempotent_unsafe
Feature: Update a TODO item

  Scenario: Successfully updating a TODO item
    Given I make a valid PUT request to a TODO item
    Then my response should have status code 200
    And my response should have a non-empty body

  Scenario: Trying to update a TODO item with an unsupported media type
    Given I make a PUT request with an unsupported media type to a TODO item
    Then my response should have status code 415
    And my response should return html

  Scenario: Trying to update a TODO item with invalid values
    Given I make a PUT request with invalid values to the a TODO item
    Then my response should have status code 400
    And my response should return html
