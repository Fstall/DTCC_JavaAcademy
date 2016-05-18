package com.rstech.search.transform;

import com.rstech.search.RsTechRequest;
import com.rstech.search.transform.DefinitionSearch;
import com.rstech.utility.URLEncoder;

import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class DefinitionSearchTest {
	private static final Class thisClass = DefinitionSearchTest.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		String methodName = "main";
		logger.debug("entering " + methodName);
		
		DefinitionSearch mySearch = null;
		RsTechRequest request = null;
		 
		
		request.setQuery("doogie");
		
		String response = mySearch.getContent(request);		
		System.out.println(response);
		logger.debug("exiting " + methodName);
	}

}
