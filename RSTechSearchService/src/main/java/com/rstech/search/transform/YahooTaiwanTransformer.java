package com.rstech.search.transform;
 
 

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;

public class YahooTaiwanTransformer extends RSTechTransformer {
private static YahooTaiwanTransformer instance = new YahooTaiwanTransformer();
	
	public static YahooTaiwanTransformer getInstance(){ return instance; }
	
	protected YahooTaiwanTransformer(){
	}
	
	public String getXslt(){
		return "yahoo_tw.xslt"; 
	}
	
	public String getUrl(RsTechRequest request) { 
		return getBaseUrl() + "/dictionary?p=" + 
				request.getQuery();
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith(getBaseUrl());
	}

	@Override
	public String getBaseUrl() {
		return "http://tw.dictionary.yahoo.com";
	}

	@Override
	public SearchSite getSearchSite() {
		return SearchSite.YAHOO_TW;
	}
		

}

