package com.rstech.search;

public enum SearchSite {

	LONGMAN,
	GOOGLE,
	YAHOO,
	MSN,
	GOOGLE_UK,
	GOOGLE_IMAGE,
	DICTIONARY,
	GOOGLE_TRANSLATE,
	YAHOO_UK,
	YAHOO_TW;

	public static SearchSite getSearchSite(String site){
		try {
			return SearchSite.valueOf(site.toUpperCase());
		} catch (Exception e){
			throw new IllegalArgumentException("Unknown search site: "+site);
		}
	}
	
		
}
