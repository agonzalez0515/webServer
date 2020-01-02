@3_conneg
Feature: Update a TODO item with JSON

  Scenario: Successfully updating a TODO item with JSON and accepting JSON
    Given I make a valid PUT request to a TODO item with JSON accepting JSON
    Then my response should have status code 200
    And my response should return json
    And my response should be the same representation as the request
    And reteiving the resource as JSON should be the same representation as the request

  Scenario: Successfully updating a TODO item with JSON and accepting HTML
    Given I make a valid PUT request to a TODO item with JSON accepting HTML
    Then my response should have status code 200
    And my response should return html

  Scenario: Successfully updating a TODO item with JSON and accepting XML
    Given I make a valid PUT request to a TODO item with JSON accepting XML
    Then my response should have status code 406
    And my response should return html
