package com.rstech.search.transform;

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;


public class GoogleImageTransformer extends RSTechTransformer{

	private static GoogleImageTransformer instance = new GoogleImageTransformer();
	
	public static GoogleImageTransformer getInstance(){ return instance; }
	
	protected GoogleImageTransformer(){
	}
	
	public String getXslt(){
		return "googleImage.xslt";
	}
	
	public String getUrl(RsTechRequest request) {
		return getBaseUrl() + request.getQuery(); 
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith(getBaseUrl());
	}

	@Override
	public String getBaseUrl() {
		return "https://www.googleapis.com/customsearch/v1?key=AIzaSyBgvc4K9zd1Z4MRu-wWVgm_SPxykC99k_U&cx=008474200754377895925:ntfk7oxtul8&q=";
	}

	@Override
	public SearchSite getSearchSite() {
		return SearchSite.GOOGLE_IMAGE;
	}
		
}
