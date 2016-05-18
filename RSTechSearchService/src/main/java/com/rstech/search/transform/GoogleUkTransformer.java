package com.rstech.search.transform;

import com.rstech.search.SearchSite;


public class GoogleUkTransformer extends GoogleTransformer{
	
	private static GoogleUkTransformer instance = new GoogleUkTransformer();
	
	public static GoogleUkTransformer getInstance(){ return instance; }
	
	private GoogleUkTransformer(){
	}

	
	@Override
	public String getBaseUrl() {
		return "http://www.google.co.uk";
	}
	
	@Override
	public SearchSite getSearchSite() {
		return SearchSite.GOOGLE_UK;
	}

		
}
