package com.nowtv.pav.test.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.UUID;

public class CreateCampaignSteps extends StepBase {

    @When("I enter a random campaign name")
    public void i_enter_a_random_name() {
        campaignName = UUID.randomUUID().toString();
        awaitElementDisplayed("#name");
        fill("#name").with(campaignName);
        screenshotTrail();
    }

    @And("^I enter the campaign name ([^\"]*)$")
    public void I_enter_the_campaign_name(String campaignN) {
        campaignName = campaignN;
        awaitElementDisplayed("#name");
        fill("#name").with(campaignN);
        screenshotTrail();
    }

    @And("^I create the campaign$")
    public void i_create_campaign(String campaignPost) throws IOException, ParseException {

        restCallResponse = post(getAuthApiUrlForAdmin() + "/campaign")
                .bodyString(campaignPost, ContentType.APPLICATION_JSON)
                .execute().returnResponse();
        assertStatusCode(201);
        JSONObject json = (JSONObject) new JSONParser().parse(campaignPost);
        campaignName = (String) json.get("name");
        screenshotTrail();
    }

    @And("^I create a random (SINGLE_USE_NON_TRANSFERABLE|SINGLE_USE_TRANSFERABLE|PROMO_CODE|GIFTING) campaign$")
    public void i_create_a_campaign_of_type(String campaignType) throws IOException, ParseException {

        String campaignPost = " { \"type\":\"" + campaignType + "\", \"name\":\"" + UUID.randomUUID().toString() + "\", \"prefix\":\"NOW\", \"cancelled\":false, \"pomOfferId\":\"12346\", \"secondaryOfferId\":\"abc123\", \"start\":\"2014-06-26T00:00:00Z\", \"end\":\"2016-06-26T00:00:00Z\", \"cap\":\"20\"}";

        restCallResponse = post(getAuthApiUrlForAdmin() + "/campaign")
                .bodyString(campaignPost, ContentType.APPLICATION_JSON)
                .execute().returnResponse();

        assertStatusCode(201);
        JSONObject json = (JSONObject) new JSONParser().parse(campaignPost);
        campaignName = (String) json.get("name");
        screenshotTrail();
    }

    @And("^I create a randomly named campaign$")
    public void i_create_random_campaign() throws IOException, ParseException {
        campaignName = UUID.randomUUID().toString();
        String campaignPost = " { \"type\":\"SINGLE_USE_TRANSFERABLE\", \"name\":\" " + campaignName + " \", \"prefix\":\"NOW\", \"cancelled\":false, \"pomOfferId\":\"12346\", \"secondaryOfferId\":\"abc123\", \"start\":\"2014-06-26T00:00:00Z\", \"end\":\"2016-06-26T00:00:00Z\", \"cap\":\"20\"}";
        restCallResponse = post(getAuthApiUrlForAdmin() + "/campaign")
                .bodyString(campaignPost, ContentType.APPLICATION_JSON)
                .execute().returnResponse();
        assertStatusCode(201);
    }

    @And("^I create (.+) campaigns$")
    public void i_create_campaigns(int numberOfCampaigns) throws IOException, ParseException {

        for (int i = 0; i < numberOfCampaigns; i++) {
            i_create_random_campaign();
        }
    }


    @And("^I update the campaign$")
    public void i_update_campaign(String campaignPost) throws IOException, ParseException {
        restCallResponse = Request.Put(getAuthApiUrlForAdmin() + "/campaign")
                .bodyString(campaignPost, ContentType.APPLICATION_JSON)
                .execute().returnResponse();
        assertStatusCode(201);
        JSONObject json = (JSONObject) new JSONParser().parse(campaignPost);
        if(json.get("name") != null){
            campaignName = (String) json.get("name");
        }
    }
}
