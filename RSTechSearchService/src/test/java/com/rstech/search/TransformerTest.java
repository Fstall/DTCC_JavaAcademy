package com.rstech.search;

import java.util.ArrayList;

import com.rstech.search.transform.RSTechTransformerFactory;
import com.rstech.vendor.pearson.LongmanWord;
import com.rstech.vendor.pearson.PearsonLongmanManager;

public class TransformerTest { 
	

	public static void main(String[] args) {
		RsTechRequest rstechRequest = new RsTechRequest();
		rstechRequest.setIncludeHtml(true);
		rstechRequest.setResolveUrls(true);
		rstechRequest.setResults(30); 
		rstechRequest.setPageNo(0);  
		rstechRequest.setQuery("symphony");
		rstechRequest.setSite(SearchSite.GOOGLE_IMAGE);
		String result = "";
		 
		result = RSTechTransformerFactory.getTransformer(SearchSite.GOOGLE_IMAGE).doTransform(rstechRequest);	
		 

	}

}
