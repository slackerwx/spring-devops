Feature: Testing a REST API
  Members group should be able to submit a new deploy info

  Scenario: Deploy info submit
    When member submit a new deploy info
    Then the server should handle it and return a success status

  Scenario: Deploy info retrieve
    When user want to get information about a specific deploy
    Then the requested deploy is returned