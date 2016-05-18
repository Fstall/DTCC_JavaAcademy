package com.rstech.search;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.io.Reader;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;
public class RSTechProperties {
	private static final Class thisClass = RSTechProperties.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	public static final String RSTECH_SEARCH_SERVER;
	public static final int MAX_WAIT_TIME;
	public static final int MAX_SNIPPET_LENGTH;

	static {
		Properties props = new Properties();
		try {
			 ClassLoader cl = ClassLoader.getSystemClassLoader();
			 
		        URL[] urls = ((URLClassLoader)cl).getURLs();
		 
		        for(URL url: urls){
		        	System.out.println(url.getFile());
		        }
		 
			String resource = null;
			resource = "com/rstech/search/rstech.properties";
			// - commented out on 11/12/2012 tchou props.load(new FileInputStream("/home/tchou/local/workspace/rs_temp_galileo/RSTechSearchClient/rstech.properties"));
			
			//InputStream input = ClassLoader.getSystemResourceAsStream(resource);
			//props.load(input);
			
			RSTECH_SEARCH_SERVER = "http://localhost:9080/RSTechSearchService/";
			MAX_WAIT_TIME = 5; //getIntProperty(props, "MAX_WAIT_TIME");
			MAX_SNIPPET_LENGTH = 1000; //getIntProperty(props, "MAX_SNIPPET_LENGTH");
			
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("Could not load rstech.properties!", e);
		}
	}

	private static String getStringProperty(Properties props, String propName){
		String methodName = "getStringProperty";
		logger.debug("entering " + methodName);
		
		String prop = props.getProperty(propName);
		if (prop==null){
			throw new RuntimeException("Could not find property: "+propName+"!");			
		}
		logger.debug("exiting " + methodName);
		return prop;
	}

	private static int getIntProperty(Properties props, String propName){
		String prop = getStringProperty(props, propName);
		try {
			return Integer.parseInt(prop);
		} catch (Exception e){
			throw new RuntimeException("Property "+propName+" must be an int!");
		}
	}
	
	public static void main(String args[]) {
		int j = 1;
		j++;
		 
	}


}
