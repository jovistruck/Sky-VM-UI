package com.nowtv.pav.test.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertTrue;

public class GenericSteps extends StepBase {

    @Before
    public void before() {
        DRIVER.get(hostApi);
    }

    @When("I login with user \"(.*)\" and password \"(.*)\"")
    public void login_with_username_password(String userName, String password) {
        DRIVER.get(getAuthApiUrlForUser(userName, password));
    }

    @When("I go to the (.*) page")
    public void i_go_to_page(String pageName) {
        Page page = pages.get(pageName);
        if (page == null) {
            throw new RuntimeException("Unknown page: " + pageName);
        }
        goTo(page.getUrl());
        awaitPageLoad();
        the_browser_is_at_the_page(pageName);
        screenshotTrail();
    }

    @When("I click on the (.*) link")
    public void i_click_on_the_link(String link) {
        awaitElementDisplayed("a", link);
        awaitTextPresence(link);
        click("a", withText().equalTo(link));
        screenshotTrail();
    }

    @When("I click on the link starting with (.*)")
    public void i_click_on_the_first_voucher_link(String linkStartsWith) {
        findFirst("a", withText().startsWith(linkStartsWith)).click();
        screenshotTrail();
    }

    @When("I click on the (.*) radio button")
    public void i_click_on_the_radio_button(String cssSelectorOfRadio) {
        awaitElementDisplayed("#" + cssSelectorOfRadio);
        click("#" + cssSelectorOfRadio);
        screenshotTrail();
    }

    @When("I click the (.*) button")
    public void click_the_submit_button(String buttonText) {
        awaitElementDisplayed("button", buttonText);
        click("button", withText().equalTo(buttonText));
        screenshotTrail();
    }

    @Then("the (.*) button is displayed")
    public void button_is_displayed(String buttonText) {
        awaitElementDisplayed("button", buttonText);
        assertTrue(findFirst("button", withText().equalTo(buttonText)).isDisplayed());
        screenshotTrail();
    }

    @Then("the (.*) link is displayed")
    public void link_is_displayed(String linkText) {
        assertTrue(findFirst("a", withText().equalTo(linkText)).isDisplayed());
        screenshotTrail();
    }


    @When("I select \"(.*)\" from (.*)")
    public void i_select_from(String optionText, String selectId) {
        awaitElementDisplayed("option", optionText);
        click(find("#" + selectId).find("option", withText().contains(optionText)));
        screenshotTrail();
    }

    @When("I enter (.*) into (.*)")
    public void i_enter_into(String text, String fieldId) {
        awaitElementDisplayed("#" + fieldId);
        fill("#" + fieldId).with(text);
        screenshotTrail();
    }

    @When("I enter the values")
    public void i_enter_the_values(Map<String, String> enterMap) {
        for (Entry<String, String> entry : enterMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            JAVASCRIPTDRIVER.executeScript(String.format("$('#%s').removeAttr('disabled')",key));
            i_enter_into(value, key);
        }
        screenshotTrail();
    }

    @When("the following elements are not be displayed")
    public void i_enter_the_values(List<String> cssSelectorList) {
        for (String cssSelectorOfElement : cssSelectorList) {
            assertElementNotDisplayed("#" + cssSelectorOfElement);
        }
        screenshotTrail();
    }

    @Then("the browser is at the (.*) page")
    public void the_browser_is_at_the_page(String pageName) {
        Page page = pages.get(pageName);
        awaitTextPresence(page.getIsAtText());
        screenshotTrail();
    }

    @Then("the description list values are")
    public void the_values_are(Map<String, String> expectedMap) {

        for (Entry<String, String> entry : expectedMap.entrySet()) {
            awaitTextPresence(entry.getKey());
            awaitTextPresence(entry.getValue());
        }

        FluentList<FluentWebElement> keys = find("dt");
        FluentList<FluentWebElement> values = find("dd");
        Map<String, String> actualMap = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            FluentWebElement key = keys.get(i);
            FluentWebElement value = values.get(i);
            actualMap.put(key.getText().replace(":", ""), value.getText().trim());
        }

        for (Entry<String, String> entry : expectedMap.entrySet()) {
            assertThat(actualMap).containsEntry(entry.getKey(), entry.getValue());
        }
        screenshotTrail();
    }

    @Then("the table has the following headers with corresponding first row values")
    public void the_table_headers_versus_rows(Map<String, String> expectedMap) throws InterruptedException {

        the_table_headers_details_versus_rows_details("", expectedMap);
    }

    @Then("the table with id \"(.*)\" has the following headers with corresponding first row values")
    public void the_table_headers_versus_rows_with_id(String tableId, Map<String, String> expectedMap) throws InterruptedException {

        the_table_headers_details_versus_rows_details("#" + tableId, expectedMap);
    }

    @Then("the table with class \"(.*)\" has the following headers with corresponding first row values")
    public void the_table_headers_details_contains_values(String tableCssClass, Map<String, String> expectedMap) throws InterruptedException {

        the_table_headers_details_versus_rows_details("." + tableCssClass, expectedMap);
    }

    public void the_table_headers_details_versus_rows_details(String tableSelector, Map<String, String> expectedMap) throws InterruptedException {

        awaitElementPresence(String.format("%s th", tableSelector));
        awaitElementPresence(String.format("%s td", tableSelector));

        for (Entry<String, String> entry : expectedMap.entrySet()) {
            awaitTextPresence(entry.getKey());
            awaitTextPresence(entry.getValue());
        }

        //Block to ensure table headers are fine and have values
        int maxRetryTillValuesFound = 50;
        int retryCounter = 0;

        Map<String, String> actualMap = new HashMap<>();


        while (retryCounter++ < maxRetryTillValuesFound) {

            try {
                nullifyImplicitWait();
                actualMap.clear();
                FluentList<FluentWebElement> keys = find(String.format("%s th", tableSelector));
                FluentList<FluentWebElement> values = find(String.format("%s td", tableSelector));

                keys.first().getText();//element not found, then retry in exception block

//                logToCucumberReport("Key value pair, " + keys.first().getText().trim() + "" + keys.first().getText());
                if (!values.first().getText().trim().equalsIgnoreCase("") && (keys.size() > 0)) {
                    for (int i = 0; i < keys.size(); i++) {
                        if (keys.get(i).getText() != null && !keys.get(i).getText().isEmpty()) {
                            FluentWebElement key = keys.get(i);
                            FluentWebElement value = values.get(i);
                            actualMap.put(key.getText().replace(":", ""), value.getText().trim());
                        }
                    }
                    break;
                }
                Thread.sleep(10);
            } catch (Exception e) {
                if (retryCounter == maxRetryTillValuesFound) {
                    //Retries exhausted
                    throw e;
                }
                Thread.sleep(10);
//                logToCucumberReport(retryCounter + " element not found/stale element " + e);
            } finally {
                resetImplicitWait();
            }
        }

        for (Entry<String, String> entry : expectedMap.entrySet()) {
            assertThat(actualMap).containsEntry(entry.getKey(), entry.getValue());
        }

        screenshotTrail();
    }

    @And("the (.+) link is ([dD]isabled|[Ee]nabled)")
    public void the_link_is_disabled(String linkText, String expectedState) {
        awaitElementDisplayed("a", linkText);
        if (expectedState.equalsIgnoreCase("disabled")) {
            assertThat(find("a", withText().equalTo(linkText)).getAttribute("disabled")).isNotNull();
        } else {
            assertThat(find("a", withText().equalTo(linkText)).getAttribute("disabled")).isNull();
        }
        screenshotTrail();
    }

    @And("^the error \"([^\"]*)\" is displayed on the page$")
    public void the_page_displays_error_containing_text(String errorExpected) {
        await().atMost(elementWaitTimeout, TimeUnit.SECONDS).until(".error").withText().contains(errorExpected).areDisplayed();
        awaitTextPresence(errorExpected);
        assertThat(find(".error").getText()).isEqualToIgnoringCase(errorExpected);
        screenshotTrail();
    }

    @And("^the text \"([^\"]*)\" is displayed on the page$")
    public void the_page_displays_text_containing_text(String messageExpected) {
        awaitElementDisplayed("body");
        awaitTextPresence(messageExpected);
        screenshotTrail();
    }

    @After
    public void embedScreenshotOnFail(Scenario s) {
        if (s.isFailed()) {
            try {
                for (byte[] byteElement : screenshotBytes) {
                    if (byteElement != null) {
                        s.embed(byteElement, "image/png");
                    }
                }
                byte[] screenshot = ((TakesScreenshot) getDefaultDriver()).getScreenshotAs(OutputType.BYTES);
                s.embed(screenshot, "image/png");

                s.write("URL at failure point: " + getDefaultDriver().getCurrentUrl());
            } catch (WebDriverException wde) {
                s.write("Embed Failed " + wde.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Arrays.fill(screenshotBytes, null);
            }
        }
        Arrays.fill(screenshotBytes, null);
        //Write out collected logs
        if (!cucumberLogCollector.isEmpty()) {
            s.write("Test logs: ");
            for (String myString : cucumberLogCollector) {
                s.write(myString);
            }
            cucumberLogCollector.clear();
        }
    }

    @And("^I wait for element with selector \"([^\"]*)\"$")
    public void I_wait_for_element_with_selector(String cssSelector) throws Throwable {
        awaitElementDisplayed(cssSelector);
    }

    @And("^I wait for \"([^\"]*)\" milliseconds$")
    public void i_wait_for(int waitTimeInMilliseconds) {
        try {
            Thread.sleep(waitTimeInMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void before_test_actions() {
        maximizeWindow();
    }

    @And("^I wait for the page to load$")
    public void I_wait_for_the_page_to_load() throws Throwable {
        awaitPageLoad();
    }

    @And("^I go to the url \"([^\"]*)\"$")
    public void I_go_to_the_url(String urlToGoTo) throws Throwable {
        goTo(getDefaultBaseUrl() + urlToGoTo);
        awaitPageLoad();
        screenshotTrail();
        ;
    }
}
