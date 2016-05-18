package com.rstech.search.transform;

import java.io.BufferedReader;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


//import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Document; 
import org.xml.sax.Attributes;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;


//import com.rstech.search.RSTechProperties;
import com.rstech.search.RSTechProperties;
import com.rstech.search.RsTechRequest;
import com.rstech.search.SearchSite;
import com.rstech.utility.URLEncoder;
import com.rstech.vendor.pearson.LongmanWord;
import com.rstech.vendor.pearson.LongmanWordSense;

public abstract class RSTechTransformer {
	private static final Class thisClass = RSTechTransformer.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	private static org.apache.log4j.Logger LOGGER = Logger
			.getLogger(RSTechTransformer.class);

	public String doTransform(RsTechRequest request) {
		return getContent(request);
	}

	/**
	 * Write the string a file
	 * 
	 * @param string
	 * @param file
	 */
	public static void writeToFile(String string, String file) {
		String methodName = "writeToFile";
		logger.debug("entering " + methodName);

		try {
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(string);
			out.close();
		} catch (Exception e) {// Catch exception if any
			LOGGER.error(e.getMessage());
		}
		logger.debug("exiting " + methodName);
	}

	/**
	 * Read text from a file to a string.
	 * 
	 * @param filename
	 * @return
	 */
	public static String readFromFile(String filename) {
		String methodName = "readFromFile";
		logger.debug("entering " + methodName);

		String result = "";
		try {
			File file = new File(filename);
			InputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String buffer;
			while ((buffer = br.readLine()) != null) {
				result = result + buffer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("exiting " + methodName);
		return result;
	}

	/**
	 * This is the key method for parsing Google Image urls.
	 * 
	 * Commented out the debug files save since it will flood my /tmp and I
	 * don't wnat that. In case you need to test out for any new versions of
	 * xslt, you can use the BackToBasic project the Styler.java class.
	 * 
	 * 
	 * @param rawHtml
	 * @param request
	 * @return
	 */
	protected String doTransform(String rawHtml, RsTechRequest request) {
		String methodName = "doTransform";
		logger.debug("entering " + methodName);

		/*
		 * if (RSTechProperties.DEBUG) {
		 * 
		 * writeToFile(rawHtml, "/tmp/" + getSearchSite() + "_" +
		 * request.getQuery() + "_raw" + ".xml"); }
		 */

		if (request.getSite().equals(SearchSite.GOOGLE_IMAGE)) {
				// this is special
				logger.debug("exiting " + methodName);
				return getGoogleImageURL(rawHtml);
		}
		
		 
		StringWriter outWriter = new StringWriter();
		StreamResult result = new StreamResult( outWriter );
		long start = System.currentTimeMillis();
		try {

			String html = rawHtml;
			CleanerProperties props = new CleanerProperties();

			// set some properties to non-default values
			props.setTranslateSpecialEntities(true);
			props.setTransResCharsToNCR(true);
			props.setOmitComments(true);

			TagNode root = new HtmlCleaner(props).clean(html);
			OutputStream outStream = new ByteArrayOutputStream();
			StringBuilder xhtml = new StringBuilder();
			// serialize to xml file
			try {
				new PrettyXmlSerializer(props).writeToStream(root, outStream,
						"UTF-8");
				xhtml.append(outStream.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			if (RSTechProperties.DEBUG) {
				writeToFile(xhtml.toString(), "/tmp/" + getSearchSite() + "_"
						+ request.getQuery() + "_tidy" + ".xml");
			}
			*/
			
			Document document;
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				document = builder.parse(new InputSource(new StringReader(xhtml.toString())));

				// Use a Transformer for output
				TransformerFactory tFactory = TransformerFactory.newInstance();
				InputStream inputStream = getClass().getResourceAsStream(getXslt());
				StreamSource stylesource = new StreamSource(inputStream);
				Transformer transformer = tFactory.newTransformer(stylesource);
				transformer.setOutputProperty("encoding", "UTF-8");

				DOMSource source = new DOMSource(document);
				
				transformer.transform(source, result);

				// output to files for debugging
				/*
				 * if (RSTechProperties.DEBUG) { writeToFile(result1.toString(),
				 * "/tmp/" + getSearchSite() + "_" + request.getQuery() +
				 * "_trans" + ".xml"); }
				 */
				logger.debug("exiting " + methodName);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   

		} catch (TransformerException e) {
			throw new RuntimeException(e);
		} finally {
			// LOGGER.info("doTransform took "+(System.currentTimeMillis()-start)+" ms.");
		}
		
		StringBuffer sb = outWriter.getBuffer(); 
		String finalstring = sb.toString();
		return finalstring;
	}

	 
	
	private ArrayList<String> parseGoogleImageJson(String contents)
	{ 
		ArrayList<String> results = new ArrayList<String>();

	    try {
	    	InputStream is = new ByteArrayInputStream( contents.getBytes() ); 
	        JsonReader rdr = Json.createReader(is); 
			JsonObject obj = rdr.readObject();
			JsonArray items = obj.getJsonArray("items");
			for (JsonObject eachItem : items.getValuesAs(JsonObject.class)) {

				JsonObject pagemapObject = eachItem.getJsonObject("pagemap");
				if (pagemapObject != null) {
					JsonArray cse_imageArray = pagemapObject.getJsonArray("cse_image");
					if (cse_imageArray != null) {
						for (JsonObject eachImage : cse_imageArray.getValuesAs(JsonObject.class)) {
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
	
	static private String xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
			+ "<RSTechResult xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
			+ "  xmlns:rs=\"http://www.rstechsvc.com/ns\""
			+ "  xmlns:java=\"java:com.rstech.search.UrlUtil\""
			+ "  xsi:noNamespaceSchemaLocation=\"rstechResult.xsd\">"
			+ "<Source>Longman Dictionary</Source>" + "<Results>";
	
	/**
	 * Note - Since 6/28/2015, I have started to use Google Custom Search API to perform the search.
	 * @param jsonString
	 * @return
	 */
	private String getGoogleImageURL(String jsonString) {
		String methodName = "getGoogleImageURK";
		logger.debug("entering " + methodName);

		ArrayList<String> urlList = parseGoogleImageJson(jsonString);
		StringBuilder boilierTemplate = new StringBuilder();
		boilierTemplate.append(xmlHeader);
		for (int i = 0; i < 1; i++) {
			String aUrlString = urlList.get(i);
			boilierTemplate.append("<Result>");
			boilierTemplate.append("<Title>").append("rstech_sound").append("</Title>");
			boilierTemplate.append( "<Link>");
			boilierTemplate.append( aUrlString);
			boilierTemplate.append( "</Link>");
			boilierTemplate.append("</Result>"); 
		}
		boilierTemplate.append("</Results>");
		boilierTemplate.append( "</RSTechResult>");
		logger.debug("exiting " + methodName);
		return boilierTemplate.toString();
		
	} 

	public static String getLongmanDictionaryResult(ArrayList<LongmanWord> list) {
		String methodName = "getLongmanDictionaryResult";
		logger.debug("entering " + methodName);

		String boilierTemplate = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<RSTechResult xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
				+ "  xmlns:rs=\"http://www.rstechsvc.com/ns\""
				+ "  xmlns:java=\"java:com.rstech.search.UrlUtil\""
				+ "  xsi:noNamespaceSchemaLocation=\"rstechResult.xsd\">"
				+ "<Source>Longman Dictionary</Source>" + "<Results>";
		for (int i = 0; i < list.size(); i++) {
			LongmanWord aWord = list.get(i);
			// get the first one that has a sound back ... 
			if (list.get(i).getSoundMediaAmerican() != null &&
				list.get(i).getSoundMediaAmerican().length() > 35) {				
				boilierTemplate += "<Result>";
				boilierTemplate += "<Title>" + "rstech_sound" + "</Title>";
				boilierTemplate += "<Link>";
				boilierTemplate += list.get(i).getSoundMediaAmerican();
				boilierTemplate += "</Link>";
				boilierTemplate += "</Result>";
				for (LongmanWordSense each : aWord.getSense()) {
					boilierTemplate += "<Result>";
					boilierTemplate += "<Title>" + each.getDefinition()
							+ "</Title>";
					boilierTemplate += "<Link>" + each.getDefinition() + "</Link>";
					boilierTemplate += "</Result>";
				}
				break;
			}
		}
		boilierTemplate += "</Results>";
		boilierTemplate += "</RSTechResult>";
		logger.debug("exiting " + methodName);
		return boilierTemplate;
	}

	private static String readFileToString(String filename) {
		String methodName = "readFileToString";
		logger.debug("entering " + methodName);

		String content = "";
		try {
			// Read the entire contents of sample.txt
			content = FileUtils.fileRead(filename);

		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("exiting " + methodName);
		return content;
	}

	public static class BooksLibrary extends DefaultHandler2 {

		boolean skipElement = false;

		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			String methodName = "startElement";
			logger.debug("entering " + methodName);

			if (qName.equals("script") || qName.equals("iframe"))
				skipElement = true;
			else {
				skipElement = false;
				// System.out.println("StartElement: " + qName);
				super.startElement(uri, localName, qName, attributes);
			}
			logger.debug("exiting " + methodName);

		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			String methodName = "endElement";
			logger.debug("entering " + methodName);

			if (qName.equals("script") || qName.equals("iframe")) {
				skipElement = false;
			} else {
				// System.out.println("EndElement: " + qName);
				super.endElement(uri, localName, qName);
			}
			logger.debug("exiting " + methodName);

		}

		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String methodName = "characters";
			logger.debug("entering " + methodName);

			// System.out.println("characters: " + new String(ch, start,
			// length));
			if (skipElement == false) {
				super.characters(ch, start, length);
				// System.out.println("characters: " + new String(ch, start,
				// length));
			}
			logger.debug("exiting " + methodName);
		}

	}
  
	 
	/**
	 * 
	 * Note - As of 5/19/2014, I have started to use a new User-Agent parameter
	 * value. This new parameter will ask Google to provide back "div" tags in
	 * which the image URLs are placed.
	 * 
	 * @param request
	 * @return
	 */
	protected String getContent(RsTechRequest request) {
		String methodName = "getContent";
		logger.debug("entering " + methodName);

		long start = System.currentTimeMillis();
		String url = getUrl(request);

		HttpMethod method = new GetMethod(url);
		method.setRequestHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:28.0) Gecko/20100101 Firefox/28.0");
		method.setRequestHeader("Connection", "close");

		HttpClient client = new HttpClient();
		/*
		 * HostConfiguration config = client.getHostConfiguration();
		 * config.setProxy(RSTechProperties.PROXY_HOST,
		 * RSTechProperties.PROXY_PORT); client.getParams().setSoTimeout(
		 * RSTechProperties.TIMEOUT*1000);
		 */
		try {
			while (System.currentTimeMillis() - start < 550 * 1000) {
				try {
					client.executeMethod(method);
					if (method.getStatusCode() == 200) {
						String response = method.getResponseBodyAsString();
						if (response != null && response.length() > 0) {
							return response;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			throw new RuntimeException("Could not retrieve content for " + url);

		} finally {
			logger.info("getContent took "
					+ (System.currentTimeMillis() - start) + " ms.");
			// method.releaseConnection();
			logger.debug("exiting " + methodName);
		}
	}

	public abstract String getXslt();

	public abstract SearchSite getSearchSite();

	public abstract String getBaseUrl();

	public abstract String getUrl(RsTechRequest request);

	public abstract boolean isSearchUrl(String url);

}
