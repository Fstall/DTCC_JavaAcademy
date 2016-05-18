package com.rstech.vendor.pearson;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;


public class LongmanWord {
	private static final Class thisClass = LongmanWord.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	LongmanWordHead head;
	String          soundMediaAmerican;
	String          soundMediaUK;
	public LongmanWordHead getHead() {
		return head;
	}
	public void setHead(LongmanWordHead head) {
		this.head = head;
	}
	public ArrayList<LongmanWordSense> getSense() {
		return sense;
	}
	public void setSense(ArrayList<LongmanWordSense> sense) {
		this.sense = sense;
	}
	public LongmanWordTail getTail() {
		return tail;
	}
	public void setTail(LongmanWordTail tail) {
		this.tail = tail;
	}
	ArrayList<LongmanWordSense> sense;
	LongmanWordTail tail;

	// diagnostic function
	public void display() {
		String methodName = "display";
		logger.debug("entering " + methodName);
		
		System.out.println("Sense...");
		if (head != null) {
			head.display();
		}
		if (sense != null) {
			for (LongmanWordSense each : sense) {
				each.display();
			}
		}
		logger.debug("exiting " + methodName);
	}
	
	public String getSoundMediaAmerican() {
		return soundMediaAmerican;
	}
	public void setSoundMediaAmerican(String soundMediaAmerican) {
		this.soundMediaAmerican = soundMediaAmerican;
	}
 
}
