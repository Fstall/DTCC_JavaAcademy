package com.rstech.search;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;

import java.net.URI;

public class UrlUtil {
	private static final Class thisClass = UrlUtil.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	public static String getActualUrl(String sponsoredUrl, boolean resolveUrls) throws Exception {
		String methodName = "getActualUrl";
		logger.debug("entering " + methodName);
		
		
		String actualUrl = sponsoredUrl;
		System.out.println(URIUtil.decode(actualUrl));

		if (isFacade(actualUrl)){
			String q = getParameter(sponsoredUrl, "q");
			if (q!=null && q.length()>0){
				actualUrl = q;
			}
			for (String parm : new String[]{"ic_url", "url", "adurl", "u"}){
				String parmUrl = getParameter(actualUrl, parm);
				if (parmUrl!=null && parmUrl.length()>0){
					int start = parmUrl.toLowerCase().indexOf("http:");
					if(start!=-1){
						parmUrl = parmUrl.substring(start);
						actualUrl = parmUrl;
						break;
					}					
				}
			}
		}
		logger.debug("exiting " + methodName);
		
		if (resolveUrls && isFacade(actualUrl)){

			HttpClient client = new HttpClient();
			/*client.getHostConfiguration().setProxy(RSTechProperties.PROXY_HOST, RSTechProperties.PROXY_PORT);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(3000);
*/
			HttpMethod method = new GetMethod(actualUrl);
			method.setRequestHeader("User-Agent", RSTechProperties.USER_AGENT);
			
			try {		
				client.executeMethod(method);
				String temp = actualUrl;
				URI uri = new URI(temp);
				actualUrl = uri.toString();
			} catch (Exception e){
				// couldn't do anything
			} finally {
				//method.releaseConnection();
			}
		}
		logger.debug("exiting " + methodName);
		return actualUrl;
	}
	
	private static boolean isFacade(String url){
		String methodName = "isFacade";
		logger.debug("entering " + methodName);
		
		if (url.startsWith("http://www.google.com/pagead") ||
			url.startsWith("http://www.google.com/url?") ||
			url.startsWith("http://www.google.com/aclk?") ||
			url.startsWith("http://rds.yahoo.com") ||
			url.startsWith("http://rc10.overture.com") ||
			url.startsWith("http://rc12.overture.com") ||
			url.startsWith("http://www.google.co.uk/pagead") ||
			url.startsWith("http://www.google.co.uk/url?") ||
			url.startsWith("http://www.google.co.uk/aclk?") ||
			url.startsWith("http://uk.wrs.yahoo.com")){
			logger.debug("exiting " + methodName);
			return true;
		} else {
			logger.debug("exiting " + methodName);
			return false;
		}
	}
		
	private static String getParameter(String url, String parm) throws Exception {
		String methodName = "getParameter";
		logger.debug("entering " + methodName);
		
		String value = null;
		URI uri = new URI(url);
		String query = uri.getQuery();
		if (query!=null){
			int qIdx = query.indexOf("?"+parm+"=");
			if(qIdx==-1)
				qIdx = query.indexOf("&"+parm+"=");
			if(qIdx==-1)
				qIdx = query.indexOf("&amp;"+parm+"=");
			if (qIdx!=-1){
				qIdx+=1+parm.length()+1;
				int qmIdx = query.indexOf("?", qIdx);
				if (qmIdx==-1){
					int ampIdx = query.indexOf("&", qIdx);
					if (ampIdx==-1){
						value = query.substring(qIdx);
					} else {
						value = query.substring(qIdx, ampIdx);
					}
				} else {
					value = query.substring(qIdx);
				}
			}
		}
		logger.debug("exiting " + methodName);
		
		if (value==null){
			logger.debug("exiting " + methodName);
			return null;
		} else {
			logger.debug("exiting " + methodName);
			return URIUtil.decode(value);
		}
	}

}
