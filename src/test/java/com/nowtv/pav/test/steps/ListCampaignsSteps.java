package com.nowtv.pav.test.steps;

import cucumber.api.java.en.When;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class ListCampaignsSteps extends StepBase {

	@When("I wait until the campaign links are displayed")
	public void i_wait_until_the_campaign_links_are_displayed() {
        awaitElementDisplayed("a", campaignName);
	}
	
	@When("I click on the campaign we just created")
	public void i_click_on_the_campaign_we_just_created() throws InterruptedException {
        awaitElementDisplayed("#" + "search-name");
        fill("#" + "search-name").with(campaignName);
        Thread.sleep(300);
        awaitElementDisplayed("a", campaignName);
        goTo(findFirst("a", withText(campaignName)).getAttribute("href"));
        awaitPageLoad();
        screenshotTrail();
	}


    @When("I search for the the campaign we just created")
    public void i_search_on_the_campaign_we_just_created() throws InterruptedException {
        awaitElementDisplayed("#" + "search-name");
        fill("#" + "search-name").with(campaignName);
        Thread.sleep(300);
        awaitElementDisplayed("a", campaignName);
        screenshotTrail();
    }


//      Someday
//      @Then("^the (Start|End) date is (lessThanOrEqualTo|greaterThanOrEqualTo) \"([^\"]*)\"$")
//      public void the_date_is(String dateType, String testCondition, String testDate) throws ParseException {
//
//        awaitElementDisplayed("td");
//
//        FluentList<FluentWebElement> keys = find("th");
//        FluentList<FluentWebElement> values = find("td");
//
//        Map<String, String> expectedMap = new HashMap<>();
//        expectedMap.put(dateType,testDate);
//
//        Map<String, String> actualMap = new HashMap<>();
//
//        for (int i = 0; i < keys.size(); i++) {
//            if (keys.get(i).getText() != null && !keys.get(i).getText().isEmpty()) {
//                FluentWebElement key = keys.get(i);
//                FluentWebElement value = values.get(i);
//                actualMap.put(key.getText().replace(":", ""), value.getText().trim());
//            }
//        }
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        String valueOfDateField;
//
//        for (Map.Entry<String, String> entry : expectedMap.entrySet()) {
//
//            valueOfDateField = actualMap.get(entry.getKey());
//            Date dateFetched = formatter.parse(valueOfDateField);
//            Date dateExpected = formatter.parse(testDate);
//
//            if(testCondition.equals("lessThanOrEqualTo")){
//
//                assertTrue(dateFetched.equals(dateExpected) || dateFetched.after(dateExpected));
//            } else if (testCondition.equals("greaterThanOrEqualTo")) {
//
//                assertTrue(dateFetched.equals(dateExpected) || dateFetched.before(dateExpected));
//            }
//        }
//
//    }
}
