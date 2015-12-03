$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("features/pageNavigation.feature");
formatter.feature({
  "line": 4,
  "name": "Page navigation spec",
  "description": "",
  "id": "page-navigation-spec",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@UI"
    },
    {
      "line": 2,
      "name": "@UIPAGENAVIGATION"
    }
  ]
});
formatter.before({
  "duration": 24526880233,
  "status": "passed"
});
formatter.before({
  "duration": 1010774170,
  "status": "passed"
});
formatter.scenario({
  "line": 6,
  "name": "Check the header navigation links",
  "description": "",
  "id": "page-navigation-spec;check-the-header-navigation-links",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 7,
  "name": "I go to the Home page",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "the browser is at the Home page",
  "keyword": "Then "
});
formatter.step({
  "line": 10,
  "name": "I click on the Campaigns link",
  "keyword": "When "
});
formatter.step({
  "line": 11,
  "name": "the browser is at the List Campaigns page",
  "keyword": "Then "
});
formatter.step({
  "line": 13,
  "name": "I click on the Create Campaign link",
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "the browser is at the Create Campaign page",
  "keyword": "Then "
});
formatter.step({
  "line": 16,
  "name": "I click on the Search Vouchers link",
  "keyword": "When "
});
formatter.step({
  "line": 17,
  "name": "the browser is at the Search Vouchers page",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "Home",
      "offset": 12
    }
  ],
  "location": "GenericSteps.i_go_to_page(String)"
});
formatter.result({
  "duration": 3303674377,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Home",
      "offset": 22
    }
  ],
  "location": "GenericSteps.the_browser_is_at_the_page(String)"
});
formatter.result({
  "duration": 24627541,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Campaigns",
      "offset": 15
    }
  ],
  "location": "GenericSteps.i_click_on_the_link(String)"
});
formatter.result({
  "duration": 1795022066,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "List Campaigns",
      "offset": 22
    }
  ],
  "location": "GenericSteps.the_browser_is_at_the_page(String)"
});
formatter.result({
  "duration": 242856440,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Create Campaign",
      "offset": 15
    }
  ],
  "location": "GenericSteps.i_click_on_the_link(String)"
});
formatter.result({
  "duration": 1716419796,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Create Campaign",
      "offset": 22
    }
  ],
  "location": "GenericSteps.the_browser_is_at_the_page(String)"
});
formatter.result({
  "duration": 249786106,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Search Vouchers",
      "offset": 15
    }
  ],
  "location": "GenericSteps.i_click_on_the_link(String)"
});
formatter.result({
  "duration": 392082520,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Search Vouchers",
      "offset": 22
    }
  ],
  "location": "GenericSteps.the_browser_is_at_the_page(String)"
});
formatter.result({
  "duration": 16859747,
  "status": "passed"
});
formatter.after({
  "duration": 409822,
  "status": "passed"
});
});