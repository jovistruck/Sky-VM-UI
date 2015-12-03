package com.nowtv.pav.test.steps;

import cucumber.api.java.en.When;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RestSteps extends StepBase {

    @When("^I do a rest (get|post|delete) (from|to) \"(.*)\"$")
    public void i_get_post_delete(String method, String fromTo, String path) throws IOException, ParseException {

        if (method.equalsIgnoreCase("get")) {
            restCallResponse = get(getAuthApiUrlForAdmin() + path)
                    .execute().returnResponse();
        } else if (method.equalsIgnoreCase("post")) {
            restCallResponse = post(getAuthApiUrlForAdmin() + path)
                    .bodyString(null, ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
        } else if (method.equalsIgnoreCase("delete")) {
            restCallResponse = put(getAuthApiUrlForAdmin() + path)
                    .bodyString(null, ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
        }
    }

    @When("^I do a rest (post|put) (from|to) \"(.*)\" with$")
    public void i_post_put_with_body(String method, String fromTo, String path, String bodyOf) throws IOException, ParseException {

        if (method.equalsIgnoreCase("post")) {
            restCallResponse = post(getAuthApiUrlForAdmin() + path)
                    .bodyString(bodyOf, ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
        } else if (method.equalsIgnoreCase("put")) {
            restCallResponse = put(getAuthApiUrlForAdmin() + path)
                    .bodyString(bodyOf, ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
        }
    }

    @When("I do a rest (post|put) \"(.*)\" to \"(.*)\"$")
    public void i_post_put_with_string(String method, String body, String path) throws IOException {
        if (method.equalsIgnoreCase("post")) {
            restCallResponse = post(getAuthApiUrlForAdmin() + path)
                    .bodyString(body, ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
        } else if (method.equalsIgnoreCase("put")) {
            restCallResponse = Request.Put(getAuthApiUrlForAdmin() + path)
                    .bodyString(body, ContentType.APPLICATION_JSON)
                    .execute().returnResponse();
        }
    }

    @When("the rest response code is \"(.*)\"$")
    public void rest_response_assert(String code) throws IOException {
        assertThat(restCallResponse.getStatusLine().getStatusCode()).isEqualTo(code);
    }



}
