package com.rstech.wordwatch.web;



import org.springframework.web.servlet.ModelAndView;

public class BaseRSController {

	// Create an application level ApplicationError instance. By default this instance's errorIndicator is 
	// set to "N" (ERROR_NEGATIVE)
	private static ApplicationError noErrorInstance = new ApplicationError();
	
	
	/**
	 * This function is called to attach a bunch of objects to the model and view dictionary.
	 * For example, I will add an empty object for ApplicationError under the "error" key.
	 */

	protected void addDefaultObject(ModelAndView aMAV) {
		aMAV.addObject("error", noErrorInstance);
		
	}

}
