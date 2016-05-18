package com.rstech.vendor.pearson;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;


public class LongmanWordSense { 
	private static final Class thisClass = LongmanWordSense.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	String signpost;
	String definition;
	ArrayList<String> exampleSentence;
	ArrayList<String> defsynonym;
	ArrayList<String> multimediavoice;
	ArrayList<LongmanWordSenseSub>subsense;

	public ArrayList<LongmanWordSenseSub> getSubsense() {
		return subsense;
	}
	public void setSubsense(ArrayList<LongmanWordSenseSub> subsense) {
		this.subsense = subsense;
	}
	public String getSignpost() {
		return signpost;
	}
	public void setSignpost(String signpost) {
		this.signpost = signpost;
	}
	public ArrayList<String> getExampleSentence() {
		return exampleSentence;
	}
	public void setExampleSentence(ArrayList<String> exampleSentence) {
		this.exampleSentence = exampleSentence;
	}
	public ArrayList<String> getDefsynonym() {
		return defsynonym;
	}
	public void setDefsynonym(ArrayList<String> defsynonym) {
		this.defsynonym = defsynonym;
	}
	public ArrayList<String> getMultimediavoice() {
		return multimediavoice;
	}
	public void setMultimediavoice(ArrayList<String> multimediavoice) {
		this.multimediavoice = multimediavoice;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void display() {
		String methodName = "display";
		logger.debug("entering " + methodName);
		
		System.out.println("Word Sense...");
		if (signpost != null) {
			System.out.println(signpost);
		}
		if (definition != null) {
			System.out.println(definition);
		}
		if (exampleSentence != null) {
			for (String each : exampleSentence) {
				System.out.println(each);
			}
		}
		if (subsense != null) {
			for (LongmanWordSenseSub each : subsense) {
				each.display();
			}
		}
		logger.debug("exiting " + methodName);
	}
	
}
