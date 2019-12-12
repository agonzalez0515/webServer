@3_conneg
Feature: Create a TODO item with JSON

  Scenario: Successfully creating a TODO item
    Given I make a valid POST request to the listing page with JSON
    Then my response should have status code 201
    And my response should have a Location header pointing to a detail page
