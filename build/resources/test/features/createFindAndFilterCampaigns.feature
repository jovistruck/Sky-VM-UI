@UI
@UIVOUCHERFINDANDFILTER

Feature: Find and Filter Campaigns

  Scenario Outline: Create XSS filled malicious campaigns

 # **************************************************************************************
 # -------------------------------- Create Invalid Campaigns ---------------------------
 # **************************************************************************************
    Given I go to the Home page

    And I click on the Create Campaign link
    And I select "Promo Code" from campaignType
    And I enter the values
      | name             | <campName>                              |
      | description      | <campDescription>                       |
      | pomOfferId       | pom_id_sut                              |
      | secondaryOfferId | <secondaryOfferId>                      |
    Then I click the Create Campaign button
    And the error "<validationError>" is displayed on the page
    Examples:
      | campName                         | campDescription                  | secondaryOfferId                 | validationError                                        |
      | <script>alert("hello");</script> | campDesc                         | sec12                            | Create Failed name may have unsafe html content        |
      | campName                         | <script>alert("hello");</script> | sec12                            | Create Failed description may have unsafe html content |
      | campName                         | campDesc                         | <script>alert("hello");</script> | Create Failed secondaryOfferId must be alphanumeric    |


  Scenario: Find and filter campaigns

 # **************************************************************************************
 # -------------------------------- Create Campaigns ------------------------------------
 # **************************************************************************************
    Given I go to the Home page

    And I click on the Create Campaign link
    And I select "Promo Code" from campaignType
    When I enter the campaign name Promo Code Campaign 11 Not Started
    And I enter the values
      | description | Promo Code Campaign |
      | pomOfferId  | pom_id_sut          |
      | start       | Tue Jun 26 2204 00:00:00 GMT+0100 (BST) |
      | end         | Sun Jun 26 2214 00:00:00 GMT+0100 (BST) |
      | cap         | 100                 |
    Then I click the Create Campaign button

    And I refresh the page
    When I click on the Create Campaign link
    When I select "Single Use Transferable" from campaignType
    And I enter the campaign name Single Use Transferable 11
    And I enter the values
      | description | Single Use Transferable |
      | pomOfferId  | pom_id_sut              |
      | start       | Sat Jun 26 2004 00:00:00 GMT+0100 (BST) |
      | end         | Tue Jun 26 2114 00:00:00 GMT+0100 (BST) |
      | cap         | 100                     |
    And I click the Create Campaign button

    And I refresh the page
    When I click on the Create Campaign link
    When I select "Gifting" from campaignType
    And I enter the campaign name Gifting Campaign 11 Expired
    And I enter the values
      | description | Gifting Campaign |
      | pomOfferId  | pom_id_sut       |
      | start       | Tue Jun 26 1990 00:00:00 GMT+0100 (BST) |
      | end         | Mon Jun 26 2000 00:00:00 GMT+0100 (BST) |
      | cap         | 100              |
    And I click the Create Campaign button

    And I refresh the page
    When I click on the Create Campaign link
    When I select "Single Use Non-Transferable" from campaignType
    And I enter the campaign name SUNT 11
    And I enter the values
      | description | SUNT Campaign |
      | pomOfferId  | pom_id_sut    |
      | start       | Thu Jun 26 2003 00:00:00 GMT+0100 (BST) |
      | end         | Sat Jun 26 2117 00:00:00 GMT+0100 (BST) |
      | cap         | 100           |
    And I click the Create Campaign button

    And I create the campaign
         """
         {
         	"type":"SINGLE_USE_TRANSFERABLE",
            "name":"Single Use Transferable 11",
            "prefix":"NOW",
            "cancelled":false,
            "pomOfferId":"12346",
            "secondaryOfferId":"abc123",
            "start":"2004-06-26T00:00:00Z",
            "end":"2114-06-26T00:00:00Z",
            "cap":"100"
         }
      """

 # **************************************************************************************
 # -----------------------Create a Cancelled Campaign -----------------------------
 # **************************************************************************************

    When I create a randomly named campaign
    And I go to the List Campaigns page
    Then the browser is at the List Campaigns page
    And I click on the campaign we just created
    And I click on the Edit Campaign link
    And I click on the cancelled-true radio button
    And I click the Update Campaign button
    And I refresh the page
    And I click on the < Back link

 # **************************************************************************************
 # -------------------------------- Find Campaign ---------------------------------------
 # **************************************************************************************
    Given I go to the Home page

    When I enter the values
      | search-name | Promo Code Campaign 11 Not Started |
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | Name | Promo Code Campaign 11 Not Started |

 # **************************************************************************************
 # -------------------------------- Filter Campaign By Type -----------------------------
 # **************************************************************************************

    And I refresh the page
    And I click on the Show Advanced Filters link

    Given I select "Promo Code" from type
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | Type | PROMO_CODE |

    Given I select "Single Use Transferable" from type
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | Type | SINGLE_USE_TRANSFERABLE |

    Given I select "Gifting" from type
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | Type | GIFTING |

    Given I select "Single Use Non-Transferable" from type
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | Type | SINGLE_USE_NON_TRANSFERABLE |

 # **************************************************************************************
 # -------------------------------- Filter Campaign By Status -----------------------------
 # **************************************************************************************

    And I refresh the page
    And I click on the Show Advanced Filters link

    And I enter the values
      | search-name | Gifting Campaign 11 Expired |
    Given I select "Inactive" from status
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | State | EXPIRED |

    When I enter the values
      | search-name | Promo Code Campaign 11 Not Started |
    And I select "Promo Code" from type
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | State | NOT_STARTED |

    And I refresh the page
    And I click on the Show Advanced Filters link

    When I select "Active" from status
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | State | ACTIVE |

    And I refresh the page
    And I search for the the campaign we just created
    And I click on the Show Advanced Filters link

    When I select "Inactive" from status
    And I click the Filter Campaigns button
    Then the table with id "campaign-list" has the following headers with corresponding first row values
      | State | CANCELLED |


