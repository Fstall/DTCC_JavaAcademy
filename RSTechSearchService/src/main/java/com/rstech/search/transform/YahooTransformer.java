package com.rstech.search.transform;

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;


public class YahooTransformer extends RSTechTransformer{

	private static YahooTransformer instance = new YahooTransformer();
	
	public static YahooTransformer getInstance(){ return instance; }
	
	protected YahooTransformer(){
	}
	
	public String getXslt(){
		return "yahoo.xslt";
	}
	
	public String getUrl(RsTechRequest request) {
		int b = (request.getPageNo()-1) * request.getResults() + 1;
		
		return getBaseUrl() +
				"/search?ei=UTF-8&" +
				"p=" + request.getQuery() + 
				"&n=" + request.getResults() + 
				"&b=" + b;
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith("http://rds.yahoo.com");
	}
	
	@Override
	public String getBaseUrl() {
		return "http://search.yahoo.com";
	}
	
	@Override
	public SearchSite getSearchSite() {
		return SearchSite.YAHOO;
	}


}
