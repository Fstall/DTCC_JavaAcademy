package com.rstech.search.transform;

import com.rstech.search.SearchSite;

public class RSTechTransformerFactory {

	public static RSTechTransformer getTransformer(SearchSite site){
		switch(site){
		case GOOGLE_TRANSLATE: return GoogleTranslateTransformer.getInstance();
		case GOOGLE: return GoogleTransformer.getInstance();
		case GOOGLE_UK:	return GoogleUkTransformer.getInstance();
		case GOOGLE_IMAGE:	return GoogleImageTransformer.getInstance();
		case MSN: return MsnTransformer.getInstance();
		case YAHOO: return YahooTransformer.getInstance();
		case YAHOO_UK: return YahooUkTransformer.getInstance();
		case YAHOO_TW: return YahooTaiwanTransformer.getInstance();  // this is a fake transformer - i am parsing strings and not using xslt
		case DICTIONARY: return DictionaryTransformer.getInstance();
		default: return null;
		}
	}
	
}
