@UI
@UISEARCHVOUCHERS

Feature: Search Vouchers Functionality

  Scenario: Search SUT Vouchers
    When I go to the Home page
    And the browser is at the Home page

    And I click on the Create Campaign link
    And the browser is at the Create Campaign page

    And I select "Single Use Transferable" from campaignType
    And I enter a random campaign name
    And I enter the values
      | description | desc_text  |
      | pomOfferId  | pom_id_sut |
      | start       | Sat Jan 01 2000 00:00:00 GMT+0000 (BST) |
      | end         | Sun Jan 01 2090 00:00:00 GMT+0000 (BST) |
      | cap         | 100        |
    And I click the Create Campaign button
    And I wait until the campaign links are displayed
    Then the browser is at the List Campaigns page

    And I click on the campaign we just created
    And I click on the Show Create Vouchers link
    And I enter the values
      | sizeBatch | 100 |

    And I click the Create Voucher Batch button

    When I click on the Search Vouchers link
    Then the browser is at the Search Vouchers page

    When I enter the values
      | voucher-code | NOWHLUBYWA9BLE |
    And I click the Search Vouchers button
    Then the description list values are
      | Code | NOWHLUBYWA9BLE |

  Scenario Outline: Search Invalid Vouchers
    Given I go to the Home page
    And the browser is at the Home page
    When I click on the Search Vouchers link
    Then the browser is at the Search Vouchers page

    When I enter the values
      | voucher-code | <invalidVoucher> |
    And I click the Search Vouchers button
    Then the error "Unknown voucher '<invalidVoucher>'" is displayed on the page
    Examples:
      | invalidVoucher |
      | NOWRUNKNOWN    |
      | 2323NOWRUNK    |


#  Scenario: Search SUNT Vouchers
#    When I go to the Home page
#    And the browser is at the Home page
#
#    And I click on the Create Campaign link
#    And the browser is at the Create Campaign page
#
#    And I select Single Use Non-Transferable from campaignType
#    And I enter a random campaign name
#    And I enter the values
#      | description | desc_text   |
#      | pomOfferId  | pom_id_sunt |
#      | start       | Sat Jan 01 2000 00:00:00 GMT+0000 (BST) |
#      | end         | Sun Jan 01 2090 00:00:00 GMT+0000 (BST) |
#      | cap         | 100         |
#    And I click the Create Campaign button
#    And I wait until the campaign links are displayed
#    Then the browser is at the List Campaigns page
#
#    And I click on the campaign we just created
#    And I click on the Show Create Vouchers link
#
#    When I enter the values
#      | accountId          | 11111111 |
#      | requestedAccountId | 22222222 |
#      | caseNumber         | 33333333 |
#    And I click the Create Voucher button
#    And I save the voucher created
#

#  Scenario: Search Promo Code
#    Given I go to the Home page
#    And the browser is at the Home page
#    When I click on the Search Vouchers link
#    Then the browser is at the Search Vouchers page
#
#    When I enter the values
#      | voucher-code | REDEEM100 |
#    And I click the Search Vouchers button
#    Then the description list values are
#      | Code | REDEEM100 |

    #TODO: Add tests for gifting search, cancel on search, fix promocode search test, fix SUNT search