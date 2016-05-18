package com.rstech.search.transform;
 
 

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;

public class LongmanTransformer extends RSTechTransformer {
private static LongmanTransformer instance = new LongmanTransformer();
	
	public static LongmanTransformer getInstance(){ return instance; }
	
	protected LongmanTransformer(){
	}
	
	public String getXslt(){
		return "not_applicable"; 
	}
	
	public String getUrl(RsTechRequest request) { 
		return getBaseUrl() +   
				request.getQuery() + "&apikey=ac2e449fca45531fa8ec661b073a88f6";
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith(getBaseUrl());
	}

	@Override
	public String getBaseUrl() {
		return "https://api.pearson.com/longman/dictionary/entry.xml?q=";
	}

	@Override
	public SearchSite getSearchSite() {
		return SearchSite.LONGMAN;
	}
		

}

