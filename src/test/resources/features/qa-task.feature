@qa-task
Feature: To test feature of user github repository search

  @positive
  Scenario Outline: Search public github repositories for the given username
    Given User navigate to the application page
    And See "Get Github Repos" page header
    When User enter "<username>" into search box
    And Search by "Go button"
    Then On "Success"
    And Get all the public github repositories for the "<username>" user
    And On clicking any repository name link navigate user to actual github repository
    Examples:
      | username     |
      | hemantjanrao |
      | manojsharma  |

  @negative
  Scenario Outline: Search public github repositories for the given username
    Given User navigate to the application page
    And See "Get Github Repos" page header
    When User enter "<username>" into search box
    And Search by "Hitting Enter key"
    Then On "Failure"
    And See correct error message with text "Github user not found"

    Examples:
      | username     |
      |              |
      | non existing |