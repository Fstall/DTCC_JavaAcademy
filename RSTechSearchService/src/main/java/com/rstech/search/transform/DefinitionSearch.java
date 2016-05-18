package com.rstech.search.transform;

import java.io.BufferedReader;
import org.apache.commons.io.FileUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger; 
import org.xml.sax.Attributes;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import com.rstech.search.RSTechProperties;
import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite; 
import com.rstech.utility.URLEncoder;

public abstract class DefinitionSearch {
	private static final Class thisClass = DefinitionSearch.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	protected String getContent(RsTechRequest request) {
		String methodName = "getContent";
		logger.debug("entering " + methodName);
		
		long start = System.currentTimeMillis();
		String url = getUrl(request);
		System.out.println("url=" + url);
		
		HttpMethod method = new GetMethod(url);
		method.setRequestHeader("User-Agent", RSTechProperties.USER_AGENT);
		method.setRequestHeader("Connection", "close");
		
		HttpClient client = new HttpClient(); 
/*  tchou commented out on 11/10/2012
  		HostConfiguration config = client.getHostConfiguration();
		config.setProxy(RSTechProperties.PROXY_HOST, RSTechProperties.PROXY_PORT);
		client.getParams().setSoTimeout( RSTechProperties.TIMEOUT*1000); 
*/
		try {		
			while (System.currentTimeMillis()-start < RSTechProperties.TOTAL_TIMEOUT*1000){
				try {
					client.executeMethod(method);
					if (method.getStatusCode()==200){
						String response = method.getResponseBodyAsString();
						if (response!=null && response.length()>0){
							return response;
						}
					} 
				} catch (Exception e){
					e.printStackTrace();
				}  
			}
			
			throw new RuntimeException("Could not retrieve content for "+url);
			
		} finally {  
			// tchou commented on 11/10/2012 - function not supported in this version
			//method.releaseConnection();
			logger.debug("exiting " + methodName);
		}
		
	}

	public abstract String getUrl(RsTechRequest request);
}
