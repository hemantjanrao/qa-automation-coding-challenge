@qa-task
Feature: To test feature of user github repository search

  @positive
  Scenario Outline: Search public github repositories for the given username
    Given User navigate to the application page
    And User see page "Get Github Repos" page header
    When User enter "<username>" into search box and click 'Go' button

    Examples:
      | username     |
      | hemantjanrao |
      | manojsharma  |