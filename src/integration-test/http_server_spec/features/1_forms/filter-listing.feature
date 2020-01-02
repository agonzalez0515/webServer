@1_forms
Feature: Filter the listing page

  Scenario: Submitting the filter form
    Given I make a GET request to the listing page with a 'filter' query param
    Then my response should have status code 200
    And my response body should contain only the items that contain the text in the filter
