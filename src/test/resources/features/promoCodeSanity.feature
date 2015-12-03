@UI
@UIPROMOCODESANITY
@UIPROMOCODESANITYADMIN

Feature: Promo code sanity check

  Scenario: Promo code sanity check
    When I go to the Home page
    And I click on the Create Campaign link
    Then the browser is at the Create Campaign page

    When I select "Promo Code" from campaignType
    And I enter a random campaign name
    And I enter the values
      | description      | desc_text          |
      | pomOfferId       | pom_id             |
      | secondaryOfferId | secondaryOfferId13 |
      | start            | Sat Jan 01 2000 00:00:00 GMT+0000 (BST) |
      | end              | Sun Jan 01 2090 00:00:00 GMT+0000 (BST) |
      | cap              | 1000               |
    And I click the Create Campaign button
    And I wait until the campaign links are displayed
    Then the browser is at the List Campaigns page

    And I click on the campaign we just created
    Then the browser is at the Campaign Details page
    And the description list values are
      | Type               | PROMO_CODE         |
      | State              | ACTIVE             |
      | POM offer Id       | pom_id             |
      | Secondary offer Id | secondaryOfferId13 |
      | Description        | desc_text          |
      | Redeemed           | 0                  |
      | Start              | 1/1/2000 0:00      |
      | End                | 1/1/2090 0:00      |
      | Redemption Cap     | 1000               |

    When I click on the Edit Campaign link
    Then Voucher Creation Lock is Unlocked
    And Campaign State is Active

    When I enter the values
      | description      | desc_text_2        |
      | pomOfferId       | pom_id_2           |
      | secondaryOfferId | secondaryOfferId25 |
      | start            | Mon Jan 01 2001 00:00:00 GMT+0000 (BST) |
      | end              | Mon Jan 01 2091 00:00:00 GMT+0000 (BST) |
      | cap              | 1001               |
    And I click the Update Campaign button
    Then the browser is at the Campaign Details page
    And the description list values are
      | State              | ACTIVE             |
      | POM offer Id       | pom_id_2           |
      | Secondary offer Id | secondaryOfferId25 |
      | Description        | desc_text_2        |
      | Redeemed           | 0                  |
      | Start              | 1/1/2001 0:00      |
      | End                | 1/1/2091 0:00      |
      | Redemption Cap     | 1001               |

    When I click on the Show Add Promo Code Vouchers link
    And I enter a random code
    And I enter voucher_desc into description
    And I click the Add Promo Code to Campaign button
    And I wait for the "Edit" button to display
    And the table with class "promo-codes" has the following headers with corresponding first row values
      | Description | voucher_desc |
      | Status      | ACTIVE      |
      | Created By  | admin       |


    Then the Promo Code was created successfully

    When I click on the View Campaign History link
    Then the browser is at the Campaign History page
    And I wait for "200" milliseconds
    And the audit values are
      | Field            | Old value            | New value            |
      | cap              | 1000                 | 1001                 |
      | description      | desc_text            | desc_text_2          |
      | end              | 2090-01-01T00:00:00Z | 2091-01-01T00:00:00Z |
      | pomOfferId       | pom_id               | pom_id_2             |
      | secondaryOfferId | secondaryOfferId13   | secondaryOfferId25   |
      | start            | 2000-01-01T00:00:00Z | 2001-01-01T00:00:00Z |
