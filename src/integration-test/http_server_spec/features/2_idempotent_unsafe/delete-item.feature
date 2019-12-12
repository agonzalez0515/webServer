@2_idempotent_unsafe
Feature: Delete a TODO item

  Scenario: Successfully delete a TODO item
    Given I make a DELETE request to a TODO item
    Then my response should have status code 204
    And my response should have an empty body

  Scenario: Delete a TODO item that's already been deleted
    Given I make a DELETE request to a TODO item that doesn't exist
    Then my response should have status code 204
    And my response should have an empty body
