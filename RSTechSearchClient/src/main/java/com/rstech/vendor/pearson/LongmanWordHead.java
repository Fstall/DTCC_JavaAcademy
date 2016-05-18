package com.rstech.vendor.pearson;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;

public class LongmanWordHead {
	private static final Class thisClass = LongmanWordHead.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	String hyphenation;  
	String partOfSpeech;
	String grammerAuxModifier;
	String inflections;
	public String getHyphenation() {
		return hyphenation;
	}
	public void setHyphenation(String hyphenation) {
		this.hyphenation = hyphenation;
	}
	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	public String getGrammerAuxModifier() {
		return grammerAuxModifier;
	}
	public void setGrammerAuxModifier(String grammerAuxModifier) {
		this.grammerAuxModifier = grammerAuxModifier;
	}
	public String getInflections() {
		return inflections;
	}
	public void setInflections(String inflections) {
		this.inflections = inflections;
	}
	public void display() {
		String methodName = "display";
		logger.debug("entering " + methodName);
		
		System.out.println("Word Head...");
		if (hyphenation != null) {
			System.out.println(hyphenation);
		}
		if (partOfSpeech != null) {
			System.out.println(partOfSpeech);
		}
		if (grammerAuxModifier != null) {
			System.out.println(grammerAuxModifier);
		}
		if (inflections != null) {
			System.out.println(inflections);
		}
		logger.debug("exiting " + methodName);
	}
}
