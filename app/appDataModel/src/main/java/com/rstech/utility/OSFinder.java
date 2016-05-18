package com.rstech.utility; 

import org.apache.log4j.Logger;

public class OSFinder {
 
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	private static final Class thisClass = OSFinder.class;
	private static final Logger logger = Logger.getLogger(thisClass);
 
	public static void main(String[] args) {
 
		String junk =  System.getProperty("os.name").toLowerCase();
		System.out.println(OS);
 
		if (isWindows()) {
			System.out.println("This is Windows");
		} else if (isMac()) {
			System.out.println("This is Mac");
		} else if (isUnix()) {
			System.out.println("This is Unix or Linux");
		} else if (isSolaris()) {
			System.out.println("This is Solaris");
		} else {
			System.out.println("Your OS is not support!!");
		}
	}
 
	public static boolean isWindows() {
		String methodName = "isWindows";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
		String methodName = "isMac";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
		String methodName = "isUnix";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
		String methodName = "isSolaris";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		return (OS.indexOf("sunos") >= 0);
 
	}
	 
 
}