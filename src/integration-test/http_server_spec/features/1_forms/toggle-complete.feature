@1_forms
Feature: Toggle TODO item completed flag

  Scenario: Successfully toggling an unfinished TODO item
    Given I make a valid POST request to an unfinished todo item
    Then my response should have status code 303
    And my response should have a Location header pointing to the detail page
    And the resource should be marked as completed

  Scenario: Successfully toggling a completed TODO item
    Given I make a valid POST request to a completed todo item
    Then my response should have status code 303
    And my response should have a Location header pointing to the detail page
    And the resource should be marked as unfinished
