Feature: Mail draft

Scenario: Saving the letter as draft
Given I opened Mail.ru site
When I create new letter with parameters:
|email| MAIL_SUBJECT | Body|
|"ekaterinamoldavskaia18@gmail.com"| MAIL_SUBJECT | "Test"|
And I save the letter as draft
And I open Draft folder
Then the letter is in Draft folder
