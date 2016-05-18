package com.rstech.vendor.pearson;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;


public class LongmanWordSenseSub {
	private static final Class thisClass = LongmanWordSenseSub.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	String subsensedef;
	String subsensegrammar;
	ArrayList<String> properform;
	ArrayList<String> subexample;
	public String getSubsensedef() {
		return subsensedef;
	}
	public void setSubsensedef(String subsensedef) {
		this.subsensedef = subsensedef;
	}
	public String getSubsensegrammar() {
		return subsensegrammar;
	}
	public void setSubsensegrammar(String subsensegrammar) {
		this.subsensegrammar = subsensegrammar;
	}
	public ArrayList<String> getProperform() {
		return properform;
	}
	public void setProperform(ArrayList<String> properform) {
		this.properform = properform;
	}
	public ArrayList<String> getSubexample() {
		return subexample;
	}
	public void setSubexample(ArrayList<String> subexample) {
		this.subexample = subexample;
	}

	public void display() {
		String methodName = "display";
		logger.debug("entering " + methodName);
		
		System.out.println("SubSense...");
		if (subsensedef != null) {
			System.out.println(subsensedef);
		}
		if (subsensegrammar != null) {
			System.out.println(subsensegrammar);
		}
		if (properform != null) {
			for (String each : properform) {
				System.out.println(each);
			}
		}
		if (subexample != null) {
			for (String each : subexample) {
				System.out.println(each);
			}
		}
		logger.debug("exiting " + methodName);
	}
}
