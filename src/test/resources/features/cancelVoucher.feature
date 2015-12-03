@UI
@UICANCELVOUCHER

Feature: Cancel Voucher Tests

  Scenario: Search Account ID with SUNT Vouchers and check cross linking(id-code)
    When I create the campaign
         """
         {
         	"type":"SINGLE_USE_NON_TRANSFERABLE",
            "name":"SUNT Cancel Campaign",
            "prefix":"NOW",
            "cancelled":false,
            "pomOfferId":"12346",
            "secondaryOfferId":"abc123",
            "start":"2004-06-26T00:00:00Z",
            "end":"2114-06-26T00:00:00Z",
            "cap":"100"
         }
      """
    When I create the campaign
         """
         {
         	"type":"SINGLE_USE_TRANSFERABLE",
            "name":"SUT Cancel Campaign",
            "prefix":"NOW",
            "cancelled":false,
            "pomOfferId":"12346",
            "secondaryOfferId":"abc123",
            "start":"2004-06-26T00:00:00Z",
            "end":"2114-06-26T00:00:00Z",
            "cap":"100"
         }
      """

    When I create the campaign
         """
         {
         	"type":"PROMO_CODE",
            "name":"Promo Code Cancel Campaign",
            "prefix":"NOW",
            "cancelled":false,
            "pomOfferId":"12346",
            "secondaryOfferId":"abc123",
            "start":"2004-06-26T00:00:00Z",
            "end":"2114-06-26T00:00:00Z",
            "cap":"100"
         }
      """
    When I create the campaign
         """
         {
         	"type":"GIFTING",
            "name":"Gifting Cancel Campaign",
            "prefix":"NOW",
            "cancelled":false,
            "pomOfferId":"12346",
            "secondaryOfferId":"abc123",
            "start":"2004-06-26T00:00:00Z",
            "end":"2114-06-26T00:00:00Z",
            "cap":"100"
         }
      """

  Scenario: Check that the Promo Code has the create voucher button
    Given I go to the Home page
    And I go to the campaign "Promo Code Cancel Campaign"
    And I click on the Show Add Promo Code Vouchers link
    When I enter the values
      | code | CANCOL |
    And I click the Add Promo Code to Campaign button
    And I go to the url "#/voucher/CANCOL"
    And the Cancel Voucher button is displayed

  Scenario: Check that the SUNT voucher has the create voucher button
    Given I go to the Home page
    And I go to the campaign "SUNT Cancel Campaign"
    And I click on the Show Create Vouchers link
    When I enter the values
      | accountId          | 88888888 |
      | requestedAccountId | 22222222 |
      | caseNumber         | 33333333 |
    And I click the Create Voucher button

    And I go to the url "#/account/88888888"
    And I wait for "300" milliseconds
    And I click on the link starting with NOW
    And I wait for "300" milliseconds
    And the Cancel Voucher button is displayed

#  Scenario: Check that the SUT Voucher has the create voucher button
#    And I go to the campaign "SUT Cancel Campaign"
#    And I click on the Show Create Vouchers link
#    When I enter the values
#      | caseNumber         | 12122121 |
#      | requestedAccountId | 77777777 |
#    And I click the Create Voucher button
 #add tests to go to the voucher created by SUT and Gifting and assert cancel button