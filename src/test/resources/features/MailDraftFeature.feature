Feature: Mail draft

  Scenario: Saving the letter as draft
    Given I opened Mail.ru site
    When I log in
    And I create new letter with parameters:
      | email                            | mailBodyText |
      | ekaterinamoldavskaia18@gmail.com | Test         |
    And I save the letter as draft
    And I open Draft folder
    Then the letter is in Draft folder
