package com.rstech.search.transform;

import com.rstech.search.SearchSite;


public class YahooUkTransformer extends YahooTransformer {

	private static YahooUkTransformer instance = new YahooUkTransformer();
	
	public static YahooUkTransformer getInstance(){ return instance; }
	
	private YahooUkTransformer(){
	}

	@Override
	public String getBaseUrl() {
		return "http://uk.search.yahoo.com";
	}

	@Override
	public SearchSite getSearchSite() {
		return SearchSite.YAHOO_UK;
	}

}
