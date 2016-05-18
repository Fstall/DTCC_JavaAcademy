package com.rstech.search.transform;

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;

public class GoogleTranslateTransformer extends RSTechTransformer {
private static GoogleTranslateTransformer instance = new GoogleTranslateTransformer();
	
	public static GoogleTranslateTransformer getInstance(){ return instance; }
	
	protected GoogleTranslateTransformer(){
	}
	
	public String getXslt(){
		return "google_translate.xslt";
	}
	
	public String getUrl(RsTechRequest request) {
		int start = (request.getPageNo()-1)*request.getResults();
		
		return getBaseUrl() +
				request.getQuery();
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith(getBaseUrl());
	}

	@Override
	public String getBaseUrl() {
		return "http://translate.google.com/#en/zh-TW/";
	}

	@Override
	public SearchSite getSearchSite() {
		return SearchSite.GOOGLE_TRANSLATE;
	}
		

}
