package com.nowtv.pav.test.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WebDriverFactory {

	public static <D extends WebDriver> D apply(Class<D> clazz) {
		D driver = null;
		try {
			driver = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		if (driver instanceof HtmlUnitDriver) {
			((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		}
		return driver;
	}
		
}
