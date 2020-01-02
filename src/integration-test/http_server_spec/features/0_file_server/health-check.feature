@0_file_server
Feature: Health Check

  Scenario: Health check request
    Given I make a GET request to '/health-check'
    Then my response should have status code 200
    And my response should return html
    And my response should have a non-empty body

  Scenario: Load balancer health check request
    Given I make a HEAD request to '/health-check'
    Then my response should have status code 200
    And my response should return html
    And my response should have Content-Length greater than 0
    And my response should have an empty body
