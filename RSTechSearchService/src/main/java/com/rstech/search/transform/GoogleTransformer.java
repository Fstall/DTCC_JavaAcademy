package com.rstech.search.transform;

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;


public class GoogleTransformer extends RSTechTransformer{

	private static GoogleTransformer instance = new GoogleTransformer();
	
	public static GoogleTransformer getInstance(){ return instance; }
	
	protected GoogleTransformer(){
	}
	
	public String getXslt(){
		return "google.xslt";
	}
	
	public String getUrl(RsTechRequest request) {
		int start = (request.getPageNo()-1)*request.getResults();
		
		return getBaseUrl() +
				"/images?safe=on&q=" + request.getQuery() + 
				"&num=" + request.getResults() + 
				"&start=" + start;
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith(getBaseUrl());
	}

	@Override
	public String getBaseUrl() {
		return "http://www.google.com";
	}

	@Override
	public SearchSite getSearchSite() {
		return SearchSite.GOOGLE;
	}
		
}
