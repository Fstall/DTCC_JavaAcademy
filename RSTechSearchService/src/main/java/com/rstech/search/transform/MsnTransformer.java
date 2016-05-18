package com.rstech.search.transform;

import org.apache.log4j.Logger;

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;
import com.rstech.utility.URLEncoder;


public class MsnTransformer extends RSTechTransformer{
	private static final Class thisClass = MsnTransformer.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	private static MsnTransformer instance = new MsnTransformer();
	
	public static MsnTransformer getInstance(){ return instance; }
	
	private MsnTransformer(){
	}
	
	public String getXslt(){
		return "msn.xslt";
	}

	
	public String getUrl(RsTechRequest request) {
		int first = (request.getPageNo()-1)*request.getResults() + 1;
		
		// the parameter &form=QBRE enables sponsored link in the result pages.
		return getBaseUrl() +
			"/results.aspx?q=" + request.getQuery() + 
			"&first=" + first + "&form=QBRE";
	}
	
	public boolean isSearchUrl(String url){
		return url!=null && url.startsWith(getBaseUrl());
	}
	
	@Override
	public String getBaseUrl() {
		return "http://search.msn.com";
	}
	
	@Override
	public SearchSite getSearchSite() {
		return SearchSite.MSN;
	}

	public String doTransform(RsTechRequest request){	
		// preprocess the html before parsing and transformation	
		return doTransform(preprocessHTML(getContent(request)), request);
	}

	protected String preprocessHTML(String html){
		String methodName = "preprocessHTML";
		logger.debug("entering " + methodName);
		
		String startPattern = "<script type=\"text/javascript\">";
		String endPattern   = "</script>";		
		
		// remove all scripts
		int startScript, endScript;		
		while(true){
			startScript = html.indexOf(startPattern);
			if(startScript>=0){
				endScript = html.indexOf(endPattern, startScript)+endPattern.length();
				if(endScript<=0)
					html = html.substring(0, startScript);
				else
					html = html.substring(0, startScript)+html.substring(endScript);
			}else{
				break;
			}
		}
		logger.debug("exiting " + methodName);
		return html;
	}
}
