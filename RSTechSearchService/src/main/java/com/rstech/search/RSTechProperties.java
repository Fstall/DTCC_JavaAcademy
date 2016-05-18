package com.rstech.search;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;

public class RSTechProperties {
	private static final Class thisClass = RSTechProperties.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	public static final String PROXY_HOST;
	public static final int PROXY_PORT; 
	public static final String USER_AGENT;
	public static final int TOTAL_TIMEOUT;
	public static final int TIMEOUT;
	public static final int TRANS_POOL_SIZE;
	public static final int TIDY_POOL_SIZE;
	public static final boolean DEBUG = true;
	
	// properties used by RSTechSearchClient
	public static final String RSTECH_SEARCH_SERVER;
	public static final int MAX_WAIT_TIME;
	public static final int MAX_SNIPPET_LENGTH;
	
	static {
		Properties props = new Properties(); 
		try {
			System.out.println("class path="+System.getProperty(
					  "java.class.path"
					  ));
			System.out.println("current working dir="+System.getProperty(
					  "user.dir"
					  ));

			props.load(new FileInputStream("rstech.properties"));
			
			PROXY_HOST = getStringProperty(props, "PROXY_HOST");
			PROXY_PORT = getIntProperty(props, "PROXY_PORT"); 
			
			USER_AGENT = getStringProperty(props, "USER_AGENT");
			TOTAL_TIMEOUT = getIntProperty(props, "TOTAL_TIMEOUT");
			TIMEOUT = getIntProperty(props, "TIMEOUT");
			TRANS_POOL_SIZE = getIntProperty(props, "TRANS_POOL_SIZE");
			TIDY_POOL_SIZE = getIntProperty(props, "TIDY_POOL_SIZE"); 
			
			// properties used by RSTechSearchClient
			RSTECH_SEARCH_SERVER = getStringProperty(props, "RSTECH_SEARCH_SERVER");
			MAX_WAIT_TIME = getIntProperty(props, "MAX_WAIT_TIME");
			MAX_SNIPPET_LENGTH = getIntProperty(props, "MAX_SNIPPET_LENGTH");
		} catch (Exception e){
			throw new RuntimeException("Could not load rstech.properties!", e);
		}
	}

	private static String getStringProperty(Properties props, String propName){
		String prop = props.getProperty(propName);
		if (prop==null){
			throw new RuntimeException("Could not find property: "+propName+"!");			
		}
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

	private static boolean getBooleanProperty(Properties props, String propName){
		String prop = getStringProperty(props, propName);
		try {
			return Boolean.parseBoolean(prop);
		} catch (Exception e){
			throw new RuntimeException("Property "+propName+" must be a boolean!");
		}
	}
}
