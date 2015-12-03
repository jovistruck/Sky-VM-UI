@UI
@UIPAGENAVIGATION

Feature: Page navigation spec

  Scenario: Check the header navigation links
    When I go to the Home page
    Then the browser is at the Home page

    When I click on the Campaigns link
    Then the browser is at the List Campaigns page

    When I click on the Create Campaign link
    Then the browser is at the Create Campaign page

    When I click on the Search Vouchers link
    Then the browser is at the Search Vouchers page


