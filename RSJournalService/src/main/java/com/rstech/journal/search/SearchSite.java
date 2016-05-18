package com.rstech.journal.search;

public enum SearchSite {

	GOOGLE,
	YAHOO,
	MSN,
	GOOGLE_UK,
	DICTIONARY,
	YAHOO_UK;

	public static SearchSite getSearchSite(String site){
		try {
			return SearchSite.valueOf(site.toUpperCase());
		} catch (Exception e){
			throw new IllegalArgumentException("Unknown search site: "+site);
		}
	}
	
		
}
