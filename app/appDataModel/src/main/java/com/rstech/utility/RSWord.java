package com.rstech.utility;
import java.util.ArrayList;

import org.apache.log4j.Logger;

// RSWord is the basic word unit.
public class RSWord {
  private static final Class thisClass = RSWord.class;
  private static final Logger logger = Logger.getLogger(thisClass);
  String 			wordName;
  String		 	pos;
  ArrayList<String> definitions;
  ArrayList<String> chinesDefinitions;
  ArrayList<String> sampleSentences;
  ArrayList<String> listOfImageURL;
  String 			imageURL;
  String 			imageURLOriginal;
  String 			audioURL;
  String			sentence;
  
  
  
  public String getImageURLOriginal() {
	  String methodName = "getImageURLOriginal";
	  logger.debug("entering " + methodName);
	  logger.debug("exiting " + methodName);
	return imageURLOriginal;
  }
	public void setImageURLOriginal(String imageURLOriginal) {
		String methodName = "setImageURLOriginal";
		logger.debug("entering " + methodName);
		this.imageURLOriginal = imageURLOriginal;
		logger.debug("exiting " + methodName);
	}
	  
	public String getAudioURL() {
		String methodName = "getAudioURL";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		return audioURL;
	}
	public void setAudioURL(String audioURL) {
		String methodName = "setAudioURL";
		logger.debug("entering " + methodName);
		this.audioURL = audioURL;
		logger.debug("exiting " + methodName);
	}
	public String getSentence() {
		String methodName = "getSentence";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		return sentence;
	}
	public void setSentence(String sentence) {
		String methodName = "setSentence";
		logger.debug("entering " + methodName);
		this.sentence = sentence;
		logger.debug("exiting " + methodName);
	}
	public String getWordName() {
		String methodName = "getWorName";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		return wordName;
	}
	public void setWordName(String wordName) {
		String methodName = "setWordName";
		logger.debug("entering " + methodName);
		this.wordName = wordName;
		logger.debug("exiting " + methodName);
	}
	public String getPos() {
		String methodName = "getPos";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		return pos;
	}
	public void setPos(String wordType) {
		String methodName = "setPos";
		logger.debug("entering " + methodName);
		this.pos = wordType;
		logger.debug("exiting " + methodName);
	}
	public ArrayList<String> getDefinitions() {
		String methodName = "getDefinitions";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		return definitions;
	}
	public void setDefinitions(ArrayList<String> definitions) {
		String methodName = "setDefinitions";
		logger.debug("entering " + methodName);
		this.definitions = definitions;
		logger.debug("exiting " + methodName);
	}
	public String getImageURL() {
		String methodName = "getImageURL";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		return imageURL;
	}
	public void setImageURL(String string) {
		String methodName = "setImageURL";
		logger.debug("entering " + methodName);
		this.imageURL = string;
		logger.debug("exiting " + methodName);
	}
	/**
	 * Return the last part of the (image) url.
	 * @param url
	 * @returngetImageName
	 */
	public static String getImageName(String url) {
		String results = "";
		String methodName = "getImageName";
		logger.debug("entering " + methodName);
		
		if (url != null)
		{
			String[] chunks = url.split("/");
			if (chunks != null && chunks.length > 0) {
				results = chunks[chunks.length - 1];
				// remove the dot notation
				String[] moreResults = results.split(".");
				if (moreResults != null && moreResults.length > 0)
					results = moreResults[moreResults.length - 1]; 
			}
		}
		logger.debug("exiting " + methodName);
		return results;
	}
	public ArrayList<String> getChinesDefinitions() {
		return chinesDefinitions;
	}
	public void setChinesDefinitions(ArrayList<String> chinesDefinitions) {
		this.chinesDefinitions = chinesDefinitions;
	}
	public ArrayList<String> getListOfImageURL() {
		return listOfImageURL;
	}
	public void setListOfImageURL(ArrayList<String> listOfImageURL) {
		this.listOfImageURL = listOfImageURL;
	}
	public ArrayList<String> getSampleSentences() {
		return sampleSentences;
	}
	public void setSampleSentences(ArrayList<String> sampleSentences) {
		this.sampleSentences = sampleSentences;
	} 
}
