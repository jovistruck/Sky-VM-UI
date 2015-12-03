package com.nowtv.pav.test.steps;

import cucumber.api.java.en.Then;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class EditCampaignSteps extends StepBase {

	@Then("Voucher Creation Lock is (Unlocked|Locked)")
	public void voucher_Creation_Lock_is(String lockState) {
		awaitElementDisplayed("#lock-voucher-" + (lockState.equals("Locked")));
		assertThat(findFirst("#lock-voucher-" + (lockState.equals("Locked")))).isSelected();
		screenshotTrail();
	}

	@Then("Campaign State is (Active|Cancelled)")
	public void campaign_State_is(String activeState) {
		awaitElementDisplayed("#cancelled-" + (activeState.equals("Cancelled")));
		assertThat(findFirst("#cancelled-" + (activeState.equals("Cancelled")))).isSelected();
		screenshotTrail();
	}
	
	@Then("the Promo Code was created successfully")
	public void thePromoCodeWasCreatedSuccessfully() {
		awaitElementDisplayed("body");
		assertThat(findFirst("body")).hasText("Success: Created Promo Code");
		screenshotTrail();
	}


}
