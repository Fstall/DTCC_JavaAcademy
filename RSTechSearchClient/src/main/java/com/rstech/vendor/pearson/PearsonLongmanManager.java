package com.rstech.vendor.pearson;




import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.ParserConfigurationException; 
 



import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rstech.utility.URLEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
 

public class PearsonLongmanManager {
	private static final Class thisClass = PearsonLongmanManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);


//sample query url
// API key = ac2e449fca45531fa8ec661b073a88f6
//http://api.pearson.com/longman/dictionary/entry/exercise.xml?apikey=ac2e449fca45531fa8ec661b073a88f6
	
	  
	private String baseUrl = null; 	private static String apiKey = "ac2e449fca45531fa8ec661b073a88f6";  
	public PearsonLongmanManager() {
		 
	}

	/**
	 * Go to PearsonLongmanManager and perform the search using the query term.
	 * 
	 * @param query
	 * @return
	 */
	public int performSearch(String query, String database, ArrayList<LongmanWord> output_args) {
		String methodName = "performSearch";
		logger.debug("entering " + methodName);
		
		 
		String local_query = "";
		StringBuffer sb_1 = new StringBuffer();
		StringTokenizer st = new StringTokenizer(query, " ");
		
		while (st.hasMoreElements()) {
			local_query = st.nextToken();
			break;
		}
		String url_string = "https://api.pearson.com/longman/dictionary/entry.xml?q="
				+ local_query 
				+ "&apikey=" + apiKey;
		 
		System.out.println("Sending url: " + url_string);
		URL url;
		try {
			url = new URL(url_string);
			Authenticator.setDefault(new Authenticator() {

			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {          
			        return new PasswordAuthentication("choutonyd@aol.com", 
			        		"PEARSONR0cks!".toCharArray());
			    }
			});
			  
	         
	        InputStream content = (InputStream)url.openStream();
	        BufferedReader in   =  new BufferedReader (new InputStreamReader(content, "UTF-8"));
	        String line; 
	        StringBuffer sb = new StringBuffer();
	        while ((line = in.readLine()) != null) {
	        	sb.append(line);
	        }
	        
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(new InputSource(new StringReader(sb.toString())));
			
			System.out.println("XML return data:");
			System.out.println(sb.toString()); 
			fetchDocumentContent(doc, output_args);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -3;
		} catch(Exception e){
			System.out.println("PearsonLongmanManager: Error while retrieving PearsonLongmanManager data: " + e);
			return -1;
        }
		logger.debug("exiting " + methodName);
		return output_args.size();
	}
	
	
	/**
	 * Go to PearsonLongmanManager and perform the search using the query term.
	 * 
	 * @param query
	 * @return
	 */
	public StringBuffer performSearchRaw(String query, String database, ArrayList<LongmanWord> output_args) {
		String methodName = "performSearchRaw";
		logger.debug("entering " + methodName);
		
		 
		String local_query = "";
		StringTokenizer st = new StringTokenizer(query, " ");
		
		while (st.hasMoreElements()) {
			local_query = st.nextToken();
			break;
		}
		String url_string = "https://api.pearson.com/longman/dictionary/entry.xml?q="
				+ local_query 
				+ "&apikey=" + apiKey;
		 
		System.out.println("Sending url: " + url_string);
		URL url;
		try {
			url = new URL(url_string);
			Authenticator.setDefault(new Authenticator() {

			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {          
			        return new PasswordAuthentication("choutonyd@aol.com", 
			        		"PEARSONR0cks!".toCharArray());
			    }
			});
	        InputStream content = (InputStream)url.openStream();
	        BufferedReader in   =  new BufferedReader (new InputStreamReader(content, "UTF-8"));
	        String line; 
	        StringBuffer sb = new StringBuffer();
	        while ((line = in.readLine()) != null) {
	        	sb.append(line);
	        }
	        logger.debug("exiting " + methodName);
			return sb;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} catch(Exception e){
			System.out.println("PearsonLongmanManager: Error while retrieving PearsonLongmanManager data: " + e);
        }
		logger.debug("exiting " + methodName);
		return null;
	}
	
	/*
	 * <HWD>run</HWD>
      <HYPHENATION>run</HYPHENATION>
      <HOMNUM>1</HOMNUM>
      <FREQ>S1</FREQ>
      <FREQ>W1</FREQ>
      <PronCodes>
        <PRON>rʌn</PRON>
      </PronCodes>
      <POS>verb</POS>
      <Inflections>
        <PASTTENSE>ran</PASTTENSE>
        <PronCodes>
          <PRON>ræn</PRON>
        </PronCodes>
        <PASTPART>run</PASTPART>
        <PRESPART>running</PRESPART>
      </Inflections>
	 */
	
	private static LongmanWordHead  parseWordHead(Node headNode)
	{
		String methodName = "parseWordHead";
		logger.debug("entering " + methodName);
		
		LongmanWordHead head = new LongmanWordHead();
		NodeList childNodes = headNode.getChildNodes();
		if (childNodes != null && childNodes.getLength() > 0) 
		{  
			for (int i = 0; i < childNodes.getLength(); i++) { // each result record
				Node aNode = (Node) childNodes.item(i);
				if (aNode.getNodeName().equals("HYPHENATION")) {
					String aString = aNode.getChildNodes().item(0).getNodeValue();
					head.setHyphenation(processHyphenation(aString)); 
				} else if (aNode.getNodeName().equals("POS")) {
					head.setPartOfSpeech(aNode.getChildNodes().item(0).getNodeValue());
				}   
			}
		}
		logger.debug("exiting " + methodName);
		return head;
		
	}
	
	private static String processHyphenation(String aString) {
		String methodName = "processHyphenation";
		logger.debug("entering " + methodName);
		
		String out = "";
		for (int i = 0; i < aString.length(); i++) {
			out = out + Character.toString((char) aString.charAt(i)) ;
		}
		logger.debug("exiting " + methodName);
		return out; 
	}
	
	private static LongmanWordSense  parseWordSense(Node senseNode)
	{
		String methodName = "parseWordSense";
		logger.debug("entering " + methodName);
		
		ArrayList<LongmanWordSenseSub> list = null;
		ArrayList<String> examples = null;
		LongmanWordSense sense = new LongmanWordSense();
		NodeList childNodes = senseNode.getChildNodes();
		if (childNodes != null && childNodes.getLength() > 0) 
		{  
			for (int i = 0; i < childNodes.getLength(); i++) { // each result record
				Node aNode = (Node) childNodes.item(i);
				if (aNode.getNodeName().equals("SIGNPOST")) {
					sense.setSignpost(aNode.getChildNodes().item(0).getNodeValue()); 
				} else if (aNode.getNodeName().equals("DEF")) {  
					sense.setDefinition(aNode.getChildNodes().item(0).getNodeValue());  
				} else if (aNode.getNodeName().equals("EXAMPLE")) { 
					if (examples == null)
						examples = new ArrayList<String>(); 
					NodeList allTextNodeList = aNode.getChildNodes();
					String text = "";
					for (int j = 0; j < allTextNodeList.getLength(); j++) {
						
						if (aNode.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
							Node specialTextNode = aNode.getChildNodes().item(j);
							text += "[special]" + specialTextNode.getChildNodes().item(0).getNodeValue() + "[/special]";
						} else {						
							text += aNode.getChildNodes().item(j).getNodeValue();
						}
					}
					examples.add(text);
				} else if (aNode.getNodeName().equals("Subsense")) { 
					if (list == null) {
						list = new ArrayList<LongmanWordSenseSub>();
					}
					LongmanWordSenseSub subSense = parseWordSubSense(aNode);
					list.add(subSense);
				}   
			}
		} 
		sense.setSubsense(list);
		sense.setExampleSentence(examples);
		logger.debug("exiting " + methodName);
		return sense;
		
	}
	
	private static LongmanWordSenseSub  parseWordSubSense(Node senseNode)
	{
		String methodName = "parseWordSubSense";
		logger.debug("entering " + methodName);
		
		LongmanWordSenseSub subSense = new LongmanWordSenseSub();
		NodeList childNodes = senseNode.getChildNodes();
		ArrayList<String> exs = null;
		if (childNodes != null && childNodes.getLength() > 0) 
		{  
			for (int i = 0; i < childNodes.getLength(); i++) { // each result record
				Node aNode = (Node) childNodes.item(i);
				if (aNode.getNodeName().equals("DEF")) {
					subSense.setSubsensedef(aNode.getChildNodes().item(0).getNodeValue()); 
				} else if (aNode.getNodeName().equals("EXAMPLE")) { 
					if (exs == null) {
						exs = new ArrayList<String>();
					}
					exs.add(aNode.getNodeValue());
				}   
			}
		} 
		subSense.setSubexample(exs);
		logger.debug("exiting " + methodName);
		return subSense;
		
	}
	
	private static LongmanWord  parseEntry(Node entryNode)
	{
		String methodName = "parseEntry";
		logger.debug("entering " + methodName);
		
		LongmanWord aWord = new LongmanWord();
		NodeList childNodes = entryNode.getChildNodes();
		ArrayList<LongmanWordSense> senseList = null;
		ArrayList<String> exs = null;
		if (childNodes != null && childNodes.getLength() > 0) 
		{  
			for (int i = 0; i < childNodes.getLength(); i++) { // each result record
				Node aNode = (Node) childNodes.item(i);
				if (aNode.getNodeName().equals("Head")) {
					LongmanWordHead wordHead = parseWordHead(aNode);
					aWord.setHead(wordHead);
				} else if (aNode.getNodeName().equals("Sense")) {
					LongmanWordSense sense = parseWordSense(aNode);  
					if (senseList == null) {
						senseList = new ArrayList<LongmanWordSense> ();
					}
					senseList.add(sense);
				} else if (aNode.getNodeName().equals("multimedia")) {
					String content = aNode.getChildNodes().item(0).getNodeValue();
					NamedNodeMap map = aNode.getAttributes();
					Node attrNode = map.getNamedItem("type");
					if (attrNode != null && attrNode.getNodeValue().equals("US_PRON")) {
						String first = "https://api.pearson.com/longman/dictionary";
						String second = content;
						String third = "3?apikey=" + apiKey; 
						aWord.setSoundMediaAmerican(first+second+third);
					}
				}
			}
		} 
		aWord.setSense(senseList); 
		logger.debug("exiting " + methodName);
		return aWord;
		
	}  
	
	/*
	 * Parse the Pearson output xml.
	 */
	public static ArrayList<LongmanWord> parse(String input) {
		String methodName = "parse";
		logger.debug("entering " + methodName);
		
		ArrayList<LongmanWord> list = null;
		try { 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(new InputSource(new StringReader(input)));
	 
			list = new ArrayList<LongmanWord>();
			fetchDocumentContent(doc, list);
			} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch(Exception e){
			System.out.println("PearsonLongmanManager: Error while retrieving PearsonLongmanManager data: " + e);
			return null;
		}
		logger.debug("exiting " + methodName);
		return list;
	}

	
	private static void fetchDocumentContent(Document doc, ArrayList output) {
		String methodName = "fetchDocumentContent";
		logger.debug("entering " + methodName);
		
		
		NodeList entryNodeList = doc.getElementsByTagName("Entries");  
		LongmanWord aWord = null;   
		
		if (entryNodeList != null && entryNodeList.getLength() > 0) {  // 1 "resultSet" 
			NodeList results = entryNodeList.item(0).getChildNodes();
			if (results != null && results.getLength() > 0) {  
				for (int i = 0; i < results.getLength(); i++) { // each result record
					Node aNode = (Node) results.item(i);
					if (aNode != null) {  
						System.out.println(aNode.getNodeType());
						if (aNode.getNodeType() == Node.ELEMENT_NODE) {
							if (aNode.getNodeName().equals("Entry")) {
								aWord = parseEntry(aNode);
								output.add(aWord); 
							}
							
						}
					}
				} 
			}
		}
		logger.debug("exiting " + methodName);
		
	}
	
	private static String readFile(String path) throws IOException {
		  String methodName = "readFile";
		  logger.debug("entering " + methodName);
			
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		    logger.debug("exiting " + methodName);
		  }
		}
	
	public void testParser(String filename, ArrayList<LongmanWord> list) throws SAXException, IOException {
		String methodName = "testParser";
		logger.debug("entering " + methodName);
		
		String abc = "";
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder(); 
			abc = readFile(filename);
			Document doc = dBuilder.parse(new InputSource(new StringReader(abc))); 
			  
			System.out.println("XML return data:");
			System.out.println(abc); 
			
			fetchDocumentContent(doc, list);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("exiting " + methodName);
	}
	
	public static void main(String args[]) throws SAXException, IOException {
		String methodName = "main";
		logger.debug("entering " + methodName);
		
		PearsonLongmanManager mgr = new PearsonLongmanManager();
		ArrayList<LongmanWord> words = new ArrayList<LongmanWord>();
		mgr.performSearchJson("exercise", "Longman", words);
		//mgr.testParser("C:/tmp/abc.xml", words);
		for (int i = 0; i < words.size(); i++) {
			words.get(i).display();
		}
		logger.debug("exiting " + methodName);
	}
	
	// Below code done in Summer 2915 for DTCC Java Academy.
	
	private  String parsePronounciationURL(JsonArray anArray) {
		// Parse out the American pronunciations...
		String url = "";
		if (anArray != null) {
			for (JsonObject eachPronounciation : anArray.getValuesAs(JsonObject.class)) {
				JsonArray audioArray = eachPronounciation.getJsonArray("audio");
				if (audioArray != null) {
					for (JsonObject eachAudioObject : audioArray.getValuesAs(JsonObject.class)) {
						JsonString audioLang = eachAudioObject.getJsonString("lang");
						if (audioLang != null && audioLang.getString().equals("American English")) {
							JsonString jsonUrl = eachAudioObject.getJsonString("url");
							if (jsonUrl != null) {
								url = jsonUrl.getString();
								break;
							}
						}
					}
				}
			}
		}
		return url;
	}
	
	private ArrayList<String> parseExample(JsonObject aSense) {
		ArrayList<String> exampleStringArray= new ArrayList<String>(); 
		JsonArray examples = aSense.getJsonArray("examples");
		   if (examples != null) {
	    	   for (JsonObject eachExample : examples.getValuesAs(JsonObject.class)) {
		    	   JsonString exampleText = eachExample.getJsonString("text"); 
		    	   exampleStringArray.add(exampleText.getString());  
	    	   } 
		   }
		return exampleStringArray;
	}
	
	
	// Parse out all the words in datasets and return the list of LongmanWord.
	
	public ArrayList<LongmanWord> parseJsonDatasets(JsonArray datasets) {
		ArrayList<LongmanWord> words = new ArrayList<LongmanWord>();
		for (JsonObject eachDataSet : datasets
				.getValuesAs(JsonObject.class)) {
			LongmanWord aWord = new LongmanWord();
			LongmanWordHead aHeadWord = new LongmanWordHead();
			aHeadWord.setPartOfSpeech(eachDataSet.getString("part_of_speech"));
			ArrayList<LongmanWordSense> listOfSense = new ArrayList<LongmanWordSense>();
			JsonArray senses = eachDataSet.getJsonArray("senses");
			if (senses != null) {
				
				
				for (JsonObject eachSense : senses
						.getValuesAs(JsonObject.class)) {
					JsonString definition = null;
					LongmanWordSense wordSense = new LongmanWordSense();
					try {
						definition = eachSense.getJsonString("definition");
						if (definition == null) {
							wordSense = null;
							continue;
						}
						wordSense.setDefinition(definition.getString());
					} catch (ClassCastException e) {
						// e.printStackTrace();
						JsonArray definitionResults = eachSense
								.getJsonArray("definition");
						for (JsonString eachDefinition : definitionResults
								.getValuesAs(JsonString.class)) {
							wordSense.setDefinition(eachDefinition.getString()); 
						}
					}
					wordSense.setExampleSentence(parseExample(eachSense));
					listOfSense.add(wordSense);
				}
			}
			JsonArray pronunciationsArray = eachDataSet
					.getJsonArray("pronunciations");
			String mediaURL = parsePronounciationURL(pronunciationsArray);
			if (mediaURL != null && listOfSense.size() > 0) {
				LongmanWordSense theFirstSense = listOfSense.get(0);
				if (theFirstSense != null) {
					String voiceURL = "https://api.pearson.com" + mediaURL;
					// just use this guy to create the sense
					ArrayList <String> list = new ArrayList<String>();
					list.add(voiceURL);
					theFirstSense.setMultimediavoice(list); 
					aWord.setSoundMediaAmerican(voiceURL);
				}
			}
			aWord.setSense(listOfSense); 
			words.add(aWord);
		}
		return words;
	}
	
	/**
	 * Go to PearsonLongmanManager and perform the search using the query term.
	 * 
	 * @param query
	 * @return
	 */
	public String performSearchJson(String query, String database,
			ArrayList<LongmanWord> output_args) {
		String methodName = "performSearchJson";
		logger.debug("entering " + methodName);

		String outputString = null;
		String local_query = "";
		StringTokenizer st = new StringTokenizer(query, " ");

		while (st.hasMoreElements()) {
			local_query = st.nextToken();
			break;
		}
		String url_string = "https://api.pearson.com/v2/dictionaries/entries?headword="
				+ local_query;

		System.out.println("Sending url: " + url_string);
		try {
			URL url = new URL(url_string);
			InputStream is = url.openStream();
			JsonReader rdr = Json.createReader(is);
			JsonObject obj = rdr.readObject();
			outputString = obj.toString();
			JsonArray datasets = obj.getJsonArray("results");
			ArrayList<LongmanWord> longmanWords = parseJsonDatasets(datasets);
			output_args.addAll(longmanWords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out
					.println("PearsonLongmanManager: Error while retrieving PearsonLongmanManager data: "
							+ e);
			e.printStackTrace();
		}
		logger.debug("exiting " + methodName);
		return outputString;
	} 
}
