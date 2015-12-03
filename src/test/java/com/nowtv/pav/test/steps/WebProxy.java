package com.nowtv.pav.test.steps;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;


public class WebProxy {

    public static WebDriver createWebDriverWithProxy(int elementWaitTimeout, TimeUnit timeUnit) {
        BrowserMobProxy mobProxy = new BrowserMobProxyServer();
        mobProxy.start(0);

        Proxy proxy = ClientUtil.createSeleniumProxy(mobProxy);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        mobProxy.addRequestFilter(
                (request, contents, messageInfo) -> {
                    if (messageInfo.getOriginalUrl().contains(":8080")) {
                        request.headers().add("X-Forwarded-Proto", "https");
                    }
                    return null;
                }
        );


        ChromeDriver chromeDriver = new ChromeDriver(capabilities);
        chromeDriver.manage().timeouts().implicitlyWait(elementWaitTimeout, timeUnit);
        return chromeDriver;
    }
}
