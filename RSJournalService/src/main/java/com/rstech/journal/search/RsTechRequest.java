package com.rstech.journal.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

// import org.apache.log4j.Logger;

public class RsTechRequest {

	private String query;
	private int results = 100;
	private int pageNo = 1;
	private boolean includeHtml = false;
	private boolean resolveUrls = false;
	private SearchSite site;
	private String proxyHost;
	private int proxyPort;
//	private static org.apache.log4j.Logger LOGGER = Logger.getLogger(RsTechRequest.class);
	
	public String getQuery(){ return query; }
	public void setQuery(String query){ 
		try {
			this.query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.query = query;
		}
	}
	
	public int getResults(){ return results; }
	public void setResults(int results){ this.results = results; }
	
	public int getPageNo(){ return pageNo; }
	public void setPageNo(int pageNo){ this.pageNo = pageNo; }
	
	public boolean getIncludeHtml(){ return includeHtml; }
	public void setIncludeHtml(boolean includeHtml){ this.includeHtml = includeHtml; }
	
	public boolean getResolveUrls(){ return resolveUrls; }
	public void setResolveUrls(boolean resolveUrls){ this.resolveUrls = resolveUrls; }
	
	public SearchSite getSite() {
		return site;
	}
	public void setSite(SearchSite site) {
		this.site = site; 
//		proxyHost = RSTechProperties.PROXY_HOST;
//		proxyPort = RSTechProperties.PROXY_PORT;
//		LOGGER.debug("setSite DEFAULT PROXY HOST/PORT:"+ proxyHost + ":" + proxyPort);
		 
	}
	public String getProxyHost() {
		return proxyHost;
	}
	public int getProxyPort() {
		return proxyPort;
	}
}

