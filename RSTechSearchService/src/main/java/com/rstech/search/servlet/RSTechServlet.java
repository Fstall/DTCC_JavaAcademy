 package com.rstech.search.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;
import com.rstech.search.transform.RSTechTransformer;
import com.rstech.search.transform.RSTechTransformerFactory;
import com.rstech.utility.URLEncoder;
import com.rstech.vendor.pearson.LongmanWord;
import com.rstech.vendor.pearson.PearsonLongmanManager;

public class RSTechServlet extends HttpServlet {
	private static final Class thisClass = RSTechServlet.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = "doPost";
		logger.debug("entering " + methodName);
		

		try {
			SearchSite site = getSearchSite(request.getRequestURI());
			
			RsTechRequest rstechRequest = new RsTechRequest();
			getQuery(request, rstechRequest);
			getResults(request, rstechRequest);
			getPage(request, rstechRequest);
			getIncludeHtml(request, rstechRequest);
			getResolveUrls(request, rstechRequest);
			rstechRequest.setSite(site);
			String result = "";
			if (rstechRequest.getSite().equals(SearchSite.LONGMAN)) {
				ArrayList <LongmanWord> list = new ArrayList<LongmanWord>();
				PearsonLongmanManager mgr = new PearsonLongmanManager();
				result = mgr.performSearchJson(rstechRequest.getQuery(), "longman", list); 
			} else {
				result = RSTechTransformerFactory.getTransformer(site).doTransform(rstechRequest);	
			}
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(result);
			response.getWriter().close();

		} catch (Exception e){
			response.sendError(400, e.getMessage());
			
		}
		logger.debug("exiting " + methodName);
	}

	private SearchSite getSearchSite(String requestURI) {
		String path = null;
		int idx = requestURI.indexOf('/', 1);
		if (idx!=-1){
			path = requestURI.substring(idx+1);
		}
		return SearchSite.getSearchSite(path);
	}
			
	private void getQuery(HttpServletRequest request, RsTechRequest rstechRequest) {
		String query = request.getParameter("query");
		if (query!=null){
			rstechRequest.setQuery(query);
		} else {
			throw new RuntimeException("The parameter query is required.");
		}
	}

	private void getResults(HttpServletRequest request, RsTechRequest rstechRequest) {
		try {
			rstechRequest.setResults(Integer.parseInt(request.getParameter("results")));
		} catch (Exception e) {
			// oh well
		}
	}
	
	private void getPage(HttpServletRequest request, RsTechRequest rstechRequest) {
		try {
			rstechRequest.setPageNo(Integer.parseInt(request.getParameter("page")));
		} catch (Exception e) {
			// oh well
		}
	}

	private void getIncludeHtml(HttpServletRequest request, RsTechRequest rstechRequest) {
		try {
			rstechRequest.setIncludeHtml(Boolean.parseBoolean(request.getParameter("includeHtml")));
		} catch (Exception e){
			// oh well
		}
	}
	
	private void getResolveUrls(HttpServletRequest request, RsTechRequest rstechRequest) {
		try {
			rstechRequest.setResolveUrls(Boolean.parseBoolean(request.getParameter("resolveUrls")));
		} catch (Exception e) {
			// oh well
		}
	}


}
