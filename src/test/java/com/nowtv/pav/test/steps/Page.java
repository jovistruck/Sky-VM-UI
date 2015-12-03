package com.nowtv.pav.test.steps;

public class Page {
	
	private String url;
	private String isAtText;
	
	public Page(String url, String isAtText) {
		this.url = url;
		this.isAtText = isAtText;
	}

	public String getUrl() {
		return url;
	}

	public String getIsAtText() {
		return isAtText;
	}

}
