@WIP
@PENDING

Feature: Sample Tests

  Scenario: Using create campaign
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

  Scenario: Using a API call (local base)
    When I do a rest post to "voucher/promocode" with
    """
      {"code":"TERRY5", "state":"ACTIVE", "campaign":{"name":"Promo Code Cancel Campaign"}}
    """
