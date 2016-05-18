package com.rstech.search.transform;

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;


public class DictionaryTransformer extends RSTechTransformer{

	private static DictionaryTransformer instance = new DictionaryTransformer();
	
	public static DictionaryTransformer getInstance(){ return instance; }
	
	protected DictionaryTransformer(){
	}
	
	public String getXslt(){
		return "dictionary.xslt";
	}
	
	public String getUrl(RsTechRequest request) {
		int start = (request.getPageNo()-1)*request.getResults();
		
		return getBaseUrl() +
				"/browse/" + request.getQuery();
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith(getBaseUrl());
	}

	@Override
	public String getBaseUrl() {
		return "http://dictionary.reference.com";
	}

	@Override
	public SearchSite getSearchSite() {
		return SearchSite.DICTIONARY;
	}
		
}
