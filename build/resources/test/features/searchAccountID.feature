@UI
@UISEARCHACCOUNTID

Feature: Search AccountID

  Scenario: Search Account ID with SUNT Vouchers and check cross linking(id-code)
    When I create the campaign
         """
         {
         	"type":"SINGLE_USE_NON_TRANSFERABLE",
            "name":"SUNT Search Account Campaign",
            "prefix":"NOW",
            "cancelled":false,
            "pomOfferId":"12346",
            "secondaryOfferId":"abc123",
            "start":"2004-06-26T00:00:00Z",
            "end":"2114-06-26T00:00:00Z",
            "cap":"100"
         }
      """
    When I go to the Home page
    And the browser is at the Home page
    And I wait until the campaign links are displayed

    And I click on the SUNT Search Account Campaign link
    And I click on the Show Create Vouchers link

    When I enter the values
      | accountId          | 11777711 |
      | requestedAccountId | 22222222 |
      | caseNumber         | 33333333 |
    And I click the Create Voucher button
    And I go to the Search Account page
    And I enter the values
      | accountId | 90909090 |
    And I click the Search Account button
    Then the error "Unknown account or no vouchers associated with account '90909090'" is displayed on the page

    And I enter the values
      | accountId | 11777711 |
    And I click the Search Account button
    And the table has the following headers with corresponding first row values
      | Account Id    | 11777711                    |
      | Campaign Type | SINGLE_USE_NON_TRANSFERABLE |
    And I click on the link starting with NOW
    Then the browser is at the Search Vouchers page
    And the Search Vouchers button is displayed

    And I click on the 11777711 link
    Then the browser is at the Search Account page
    And the Search Account button is displayed

    And I click on the SUNT Search Account Campaign link
    And I wait for the page to load
    Then the browser is at the Campaign Details page
    And the Edit Campaign link is displayed
