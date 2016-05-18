package com.rstech.search;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

import org.apache.log4j.Logger;

import com.rstech.exception.RSTechException;
import com.rstech.exception.RSTechSystemException;
import com.rstech.vendor.pearson.LongmanWord;
import com.rstech.vendor.pearson.PearsonLongmanManager;

public class RSTechSearchClient {
	private static final Class thisClass = RSTechSearchClient.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	// ----- DTCC Java Academy

	public static String searchJson(String searchTarget, String query,
			int results, int page, boolean includeHtml, boolean resolveUrls)
			throws RSTechException {
		String methodName = "search";
		logger.debug("entering " + methodName);

		String data = "";
		String newQuery = "";
		InputStream urlStream = null;
		
		
		try {
			newQuery = URLEncoder.encode(query, "utf-8");
		} catch (UnsupportedEncodingException e) {
			RSTechSystemException ex = new RSTechSystemException();
			ex.setMessage(e.getMessage());
			e.printStackTrace();
			throw ex;
		}

		StringBuffer urlStr = new StringBuffer();
		urlStr.append(RSTechProperties.RSTECH_SEARCH_SERVER)
				.append(searchTarget).append("?query=").append(newQuery)
				.append("&results=").append(results).append("&page=")
				.append(page).append("&includeHtml=").append(includeHtml)
				.append("&resolveUrls=").append(resolveUrls);
		URL url;
		try {
			url = new URL(urlStr.toString());

			long finishBy = System.currentTimeMillis()
					+ RSTechProperties.MAX_WAIT_TIME * 1000;

			Exception ex = null;
			BufferedReader bufferedReader = null;
			while (System.currentTimeMillis() < finishBy) {
				
				try {
					urlStream = url.openStream();
				} catch (IOException e) {
					RSTechSystemException exIOException = new RSTechSystemException();
					exIOException.setMessage(e.getMessage());
					e.printStackTrace();
					throw exIOException;
				}

				if (searchTarget.equals("LONGMAN")
						|| searchTarget.equals("GOOGLE_IMAGE")) {
					if (bufferedReader == null) {
						try {
							bufferedReader = new BufferedReader(
									new InputStreamReader(urlStream, "UTF-8"));

							StringBuffer sb = new StringBuffer();

							for (String line = bufferedReader.readLine(); line != null; line = bufferedReader
									.readLine()) {
								sb.append(line);
							}
							data = sb.toString();
							bufferedReader.close();
							bufferedReader = null;

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							RSTechSystemException exEncoding = new RSTechSystemException();
							exEncoding.setMessage(e.getMessage());
							e.printStackTrace();
							throw exEncoding;
						} catch (IOException e) {
							RSTechSystemException exIOException = new RSTechSystemException();
							exIOException.setMessage(e.getMessage());
							e.printStackTrace();
							throw exIOException;
						}

					}
				}
				ex = null;
			}

		} catch (MalformedURLException e) {
			RSTechSystemException ex = new RSTechSystemException();
			ex.setMessage(e.getMessage());
			e.printStackTrace();
			throw ex;
		}  finally { 
			if (urlStream!=null) { 
				try { 
					urlStream.close(); 
				} catch (Exception e) {
					e.printStackTrace();
				} 
			} 
		}
		
		logger.debug("exiting " + methodName);
		return data;
	}

	public static ArrayList<String> parseGoogleImageJson(String contents) {
		ArrayList<String> results = new ArrayList<String>();

		try {
			InputStream is = new ByteArrayInputStream(contents.getBytes());
			JsonReader rdr = Json.createReader(is);
			JsonObject obj = rdr.readObject();
			JsonArray items = obj.getJsonArray("items");
			for (JsonObject eachItem : items.getValuesAs(JsonObject.class)) {

				JsonObject pagemapObject = eachItem.getJsonObject("pagemap");
				if (pagemapObject != null) {
					JsonArray cse_imageArray = pagemapObject
							.getJsonArray("cse_image");
					if (cse_imageArray != null) {
						for (JsonObject eachImage : cse_imageArray
								.getValuesAs(JsonObject.class)) {
							JsonString srcUrl = eachImage.getJsonString("src");
							results.add(srcUrl.toString());
						}
					}
				}
			}
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
}
