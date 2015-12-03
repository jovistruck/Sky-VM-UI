package com.nowtv.pav.test.steps;

import cucumber.api.java.en.When;

import java.util.Random;

import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class CampaignDetailsSteps extends StepBase {

	private Base28 base28 = new Base28();
	private Random rand = new Random(System.currentTimeMillis());
	
	@When("I enter a random code")
	public void i_enter_a_random_code() {
		awaitElementDisplayed("#code");
		fill("#code").with(new String(base28.toBase(rand.nextInt(Integer.MAX_VALUE), 10)));
		screenshotTrail();
	}

	@When("I refresh the page")
	public void i_refresh_the_page() {
		goTo(this.url());
		awaitPageLoad();
	}
	
	@When("I wait for the voucher batches to display")
	public void i_wait_for_the_voucher_batches_to_display() {
		awaitElementDisplayed("a", "Export");
	}

	@When("I wait for all voucher batches to complete by refreshing the page")
	public void i_wait_for_the_voucher_batches_to_generate() throws InterruptedException {
        int timeoutMs = 30 * 1000; // 30 seconds
        int attempts = 0;
        int attemptTimeMs = 5000;
        while (attempts * attemptTimeMs < timeoutMs) {
            i_refresh_the_page();
            if (voucherBatchesComplete()) {
                return;
            }
            Thread.sleep(attemptTimeMs);
            attempts++;
        }
        screenshotTrail();
        throw new RuntimeException("batches still pending after " + timeoutMs + " ms");
	}
	
	@When("I wait for the \"(.*)\" button to display")
	public void i_wait_for_the_button_to_display(String buttonDisplayName) {
		awaitElementDisplayed("a", buttonDisplayName);
	}

	@When("I go to the campaign \"(.*)\"")
	public void i_go_to_the_campaign(String campName) throws InterruptedException {
		Page page = pages.get("Home");
		if (page == null) {
			throw new RuntimeException("Unknown page: " + "Home");
		}
		goTo(page.getUrl());
		awaitPageLoad();
		awaitTextPresence("Show Advanced Filters");
		click("a", withText().equalTo("Show Advanced Filters"));
		awaitElementDisplayed("#search-name");
		findFirst("#search-name").fill().with(campName);
		click("button", withText().equalTo("Filter Campaigns"));
		Thread.sleep(300);
		awaitTextPresence(campName);
		goTo(findFirst("a", withText(campName)).getAttribute("href"));
	}

//
//	@And("^I save the voucher created$")
//	public void I_save_the_voucher_created() throws Throwable {
//
//		Thread.sleep(100);
//		awaitElementDisplayed("#generatedVoucherCode");
//		lastVoucherCreated=findFirst("#generatedVoucherCode").getValue();
//		//Does not work, needs fixing
//		String voucherCode= (String) ((JavascriptExecutor) getDriver()).executeScript("return document.getElementById(\"generatedVoucherCode\").innerHTML;");
//		logToCucumberReport(lastVoucherCreated);
//	}
}
