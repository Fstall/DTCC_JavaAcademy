package com.rstech.utility;

import org.apache.log4j.Logger;
 
public class URLEncoder {
	private static final Class thisClass = URLEncoder.class;
	private static final Logger logger = Logger.getLogger(thisClass);

    public static String encode(String input) {
    	String methodName = "encode";
    	logger.debug("entering " + methodName);
		
        StringBuilder resultStr = new StringBuilder();
        for (char ch : input.toCharArray()) {
        	if (isUnsafe(ch)) {
                resultStr.append('%');
                resultStr.append(toHex(ch / 16));
                resultStr.append(toHex(ch % 16));
            } else {
                resultStr.append(ch);
            } 
        }
        logger.debug("exiting " + methodName);
        return resultStr.toString();
    }

    private static char toHex(int ch) {
    	String methodName = "toHex";
    	logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    private static boolean isUnsafe(char ch) {
    	String methodName = "isUnsafe";
    	logger.debug("entering " + methodName);		
        if (ch > 128 || ch < 0) {
        	logger.debug("exiting " + methodName);
            return true;
        }
        logger.debug("exiting " + methodName);
        return " %$&+,/:;=?@<>#%".indexOf(ch) >= 0;
    }

}
