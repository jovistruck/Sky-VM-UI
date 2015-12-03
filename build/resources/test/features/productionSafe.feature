@PRODUCTIONSAFE
@WIP

Feature: Production Safe Tests On Production

  Scenario: Check the header navigation links
    When I go to the Home page
    Then the browser is at the Home page

    When I click on the Campaigns link
    Then the browser is at the List Campaigns page

    When I click on the Search Vouchers link
    Then the browser is at the Search Vouchers page

  Scenario: Search Invalid Account ID with SUNT Vouchers
    When I go to the Home page
    And the browser is at the Home page

    And I go to the Search Account page
    And I enter the values
      | accountId | 90909090 |
    And I click the Search Account button
    Then the error "Unknown account or no vouchers associated with account '90909090'" is displayed on the page

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