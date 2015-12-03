package com.nowtv.pav.test.steps;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.fluentlenium.adapter.IsolatedTest;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertFalse;

public class StepBase extends IsolatedTest {

    protected static String hostApi;
    protected static String hostUi;
    protected static String testEnvironment = System.getProperty("testEnvironment","localhost");

    protected static int elementWaitTimeout = 30;
    protected static int pageLoadTimeout = 60;
    private static String adminUsername = "admin";
    private static String adminPassword = "pass";

    private final int MAX_STALE_ELEMENT_RETRIES = 100;

    protected static byte[][] screenshotBytes = new byte[50][];
    protected static int screenshotBytesElementCount = 0;
    protected static ArrayList<String> cucumberLogCollector = new ArrayList<String>();
    protected static boolean screenshotTrail = Boolean.parseBoolean(System.getProperty("screenshotTrail", "false"));

    protected static HttpResponse restCallResponse;

    protected static final WebDriver DRIVER = WebProxy.createWebDriverWithProxy(elementWaitTimeout, TimeUnit.SECONDS);

    protected static final JavascriptExecutor JAVASCRIPTDRIVER = (JavascriptExecutor) DRIVER;

    static {
        hostApi = "http://" + adminUsername + ":" + adminPassword + "@" + System.getProperty(testEnvironment + "API");
        hostUi = System.getProperty(testEnvironment + "UI");

        quitDriveOnShutdown();
    }

    private static void quitDriveOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                DRIVER.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    protected static Map<String, Page> pages = null;
    protected static String campaignName = null;
    protected static String lastVoucherCreated = null;

    public StepBase() {
        if (pages == null) {
            pages = new HashMap<>();

            pages.put("Home", new Page("/#/campaigns", "Campaign List"));
            pages.put("List Campaigns", new Page("/#/", "Campaign List"));
            pages.put("Create Campaign", new Page("/#/campaign", "Create Campaign Details"));
            pages.put("Edit Campaign", new Page("/#/campaign/{campaignId}/edit", "Update Campaign Details"));
            pages.put("Campaign Details", new Page("/#/campaign/{campaignId}", "Campaign Details"));
            pages.put("Campaign History", new Page("/#/campaign/{campaignId}/history", "Campaign Details Audit"));
            pages.put("Search Vouchers", new Page("/#/voucher", "Search Vouchers"));
            pages.put("Search Account", new Page("/#/account", "Search Account"));
            pages.put("Voucher Batch History", new Page("/#/voucher", "Batch Export Audit"));
        }
    }

    @Override
    public WebDriver getDefaultDriver() {return DRIVER;}

    @Override
    public String getDefaultBaseUrl() {
        return hostUi;
    }

    @Override
    public Fluent click(String cssSelector, Filter... filters) {

        int retries = 0;
        nullifyImplicitWait();
        while (true) {
            try {
                $(cssSelector, filters).click();
                return this;
            } catch (Exception e) {
                if (retries < MAX_STALE_ELEMENT_RETRIES) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException intEx) {
                        intEx.printStackTrace();
                    }
                    retries++;
                } else {
                    //Element not found, stop retry
                    throw e;
                }
            } finally {
                resetImplicitWait();
            }
        }
    }

    @Override
    public Fluent click(FluentDefaultActions fluentObject) {

        int retries = 0;
        nullifyImplicitWait();
        while (true) {
            try {
                fluentObject.click();
                return this;
            } catch (Exception e) {
                if (retries < MAX_STALE_ELEMENT_RETRIES) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException intEx) {
                        intEx.printStackTrace();
                    }
                    retries++;
                } else {
                    //Element not found, stop retry
                    throw e;
                }
            } finally {
                resetImplicitWait();
            }
        }
    }


    public static void nullifyImplicitWait(){
        DRIVER.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public static void resetImplicitWait(){
        DRIVER.manage().timeouts().implicitlyWait(elementWaitTimeout, TimeUnit.SECONDS);
    }

    public void awaitElementDisplayed(String elementCssSelector, String elementText) {
        await().atMost(elementWaitTimeout, TimeUnit.SECONDS).until(elementCssSelector).withText().contains(elementText).areDisplayed();
    }

    public void awaitElementDisplayed(String elementCssSelector) {
        await().atMost(elementWaitTimeout, TimeUnit.SECONDS).until(elementCssSelector).areDisplayed();
    }

    public void awaitElementPresence(String elementCssSelector) {
        await().atMost(elementWaitTimeout, TimeUnit.SECONDS).until(elementCssSelector).isPresent();
    }
    
    public boolean voucherBatchesComplete() {
    	return find("td", withText().startsWith("PENDING")).isEmpty();
    }

    public void assertElementNotDisplayed(String elementCssSelector) {
        try {
            nullifyImplicitWait();
            assertFalse("Element found, Fail " + elementCssSelector, findFirst(elementCssSelector).isDisplayed());
        } finally{
            resetImplicitWait();
        }
    }

    public void awaitTextPresence(String textToWaitFor) {
        int waitFor = 1000;
        int sleepTime = 30;
        while (!pageSource().contains(textToWaitFor) && waitFor > 0) {
            try {
				Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waitFor -= sleepTime;
        }
    }

    public void awaitPageLoad() {
        await().atMost(pageLoadTimeout, TimeUnit.SECONDS).untilPage().isLoaded();
    }

    public void screenshotTrail() {
        if(screenshotTrail) {
            try {
                screenshotBytes[screenshotBytesElementCount] = ((TakesScreenshot) getDefaultDriver()).getScreenshotAs(OutputType.BYTES);
                screenshotBytesElementCount += 1;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public static void logToCucumberReport(String stringToWrite) {
        try {
            cucumberLogCollector.add(stringToWrite);
        } catch (Exception cce) {
            cce.printStackTrace();
        }
    }

    public static String getAuthApiUrlForUser(String Username, String Password) {
        return "http://" + Username +":" + Password + "@"+ System.getProperty(testEnvironment + "API");
    }

    public static String getAuthApiUrlForAdmin() {
        return "http://" + adminUsername + ":" + adminPassword + "@" + System.getProperty(testEnvironment + "API");
    }

    public Request post(String uri) {
        return withCommonHeaders(Request.Post(uri));
    }

    public Request put(String uri) {
        return withCommonHeaders(Request.Put(uri));
    }

    public Request get(String uri) {
        return withCommonHeaders(Request.Get(uri));
    }

    public Request delete(String uri) {
        return withCommonHeaders(Request.Delete(uri));
    }

    private Request withCommonHeaders(Request request) {
        return request.addHeader("X-Forwarded-Proto", "https")
                .addHeader("Content-Type", "application/json");
    }

    protected void assertStatusCode(int statusCode) {
        assertThat(restCallResponse.getStatusLine().getStatusCode()).isEqualTo(statusCode);
    }
}
