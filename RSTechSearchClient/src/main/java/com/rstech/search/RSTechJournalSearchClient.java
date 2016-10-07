package com.rstech.search;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

import org.apache.log4j.Logger;

import com.rstech.exception.RSTechException;
import com.rstech.utility.RSWord;
import com.rstech.vendor.pearson.LongmanWord;
import com.rstech.vendor.pearson.LongmanWordSense;
import com.rstech.vendor.pearson.PearsonLongmanManager;

public class RSTechJournalSearchClient {
	private static final Class thisClass = RSTechJournalSearchClient.class;
	private static Logger logger = Logger.getLogger(thisClass);
	
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
						for (int i = 0; i < results.size(); i++){
							String noQuotes = results.get(i);
							noQuotes = noQuotes.replace("\"", "");
							results.set(i, noQuotes);						
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

	/**
	 * Search the definition of aWord from the LONGMAN site.
	 * 
	 * @param aWord
	 */
	public void searchLongman(RSWord aWord) {
		String methodName = "searchLongman";
		logger.debug("entering " + methodName);

		String dictionaryText;
		try {
			dictionaryText = RSTechSearchClient.searchJson("LONGMAN",
					aWord.getWordName(), 10, 1, true, true);
			ArrayList<String> definitionList = new ArrayList<String>();
			ArrayList<String> sampleSentenceList = new ArrayList<String>();
			// Each result (definition) will be put into the specified word.
			if (dictionaryText != null && dictionaryText.length() > 1) {
				InputStream is = new ByteArrayInputStream(
						dictionaryText.getBytes());
				JsonReader rdr = Json.createReader(is);
				JsonObject obj = rdr.readObject();
				JsonArray datasets = obj.getJsonArray("results");
				PearsonLongmanManager mgr = new PearsonLongmanManager();
				ArrayList<LongmanWord> longmanWords = mgr
						.parseJsonDatasets(datasets);
				if (longmanWords != null) {
					System.out.println("list size  = " + longmanWords.size());
					if (longmanWords != null && longmanWords.size() > 0) {
						logger.debug("list count = " + longmanWords.size());
						LongmanWord longmanWord = longmanWords.get(0);
						ArrayList<LongmanWordSense> senses = longmanWord
								.getSense();
						logger.debug("senses = " + senses);
						if (senses != null && senses.size() > 0) {
							for (LongmanWordSense each : senses) {
								definitionList.add(each.getDefinition());
								ArrayList<String> sentences = each
										.getExampleSentence();
								if (sentences != null && sentences.size() > 0) {
									sampleSentenceList.add(sentences.get(0));
								}
							}
						}
					}
				}
			}
			aWord.setDefinitions(definitionList);
			aWord.setSampleSentences(sampleSentenceList);
		} catch (RSTechException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("exiting " + methodName);

	}

	public void searchGoogleImage(RSWord aWord) {
		try {
		String imageText = RSTechSearchClient.searchJson("GOOGLE_IMAGE",
				aWord.getWordName(), 10, 1, true, true);
		ArrayList<String> urlList = parseGoogleImageJson(imageText);
		if (urlList != null && urlList.size() > 0) {
			System.out.println("list size  = " + urlList.size());
			aWord.setImageURL(urlList.get(0));
		}
		} catch (RSTechException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<RSWord> populateDefinitions(ArrayList<RSWord> wordList)
			throws Exception {
		String methodName = "populateDefinitions";
		logger.debug("entering " + methodName);

		for (int k = 0; k < wordList.size(); k++) {
			RSWord werd = wordList.get(k);
			// Find the definition(s) of said word.
			searchLongman(werd);
			searchGoogleImage(werd);
		}
		logger.debug("exiting " + methodName);
		return wordList;
	}
}
