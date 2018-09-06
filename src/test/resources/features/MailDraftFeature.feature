Feature: Mail draft

  Background:
    Given I opened Mail.ru site
    And I create the subject of the letter

  Scenario: Saving the letter as draft
    When I log in
    And I create new letter with parameters:
      | email                            | mailBodyText |
      | ekaterinamoldavskaia18@gmail.com | Test         |
    And I save the letter as draft
    And I open Draft folder
    Then the letter is in Draft folder


  Scenario: Checking the contain of draft fields
    When I log in
    And I create new letter with parameters:
      | email                            | mailBodyText |
      | ekaterinamoldavskaia18@gmail.com | Test         |
    And I save the letter as draft
    And I open Draft folder
    And I open the last saved letter
    Then all fields contain same parameters

  Scenario: Sending letter
    When I log in
    And I create new letter with parameters:
      | email                            | mailBodyText |
      | ekaterinamoldavskaia18@gmail.com | Test         |
    And I save the letter as draft
    And I open Draft folder
    And I open the last saved letter
    And I send the letter
    Then the letter should not be in Draft folder
    And the letter should be in Sent folder

  Scenario: Sending the letter and logging out
    When I log in
    And I create new letter with parameters:
      | email                            | mailBodyText |
      | ekaterinamoldavskaia18@gmail.com | Test         |
    And I save the letter as draft
    And I open Draft folder
    And I open the last saved letter
    And I send the letter
    And I log out
    Then the main page should be opened

  Scenario Outline: Sending letter with different bodies
    When I log in
    And I create new letter with parameters:
      | email                            | mailBodyText |
      | ekaterinamoldavskaia18@gmail.com | <text>       |
    And I send the letter
    Then the letter should be in Sent folder

    Examples:
      | text   |
      | Test 1 |
      | Test 2 |
      | Test 3 |

  Scenario: Sending letter with different text of email
    When I log in
    And I fill the email field in new letter page with "ekaterinamoldavskaia18@gmail.com"
    And I fill the subject in new letter page
    And I fill the email body with "Text 10"
    And I send the letter
    Then the letter should be in Sent folder

