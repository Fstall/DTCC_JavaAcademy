package com.rstech.wordwatch.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.business.domain.LoginUserInfo;
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.SessionManager;
import com.rstech.wordwatch.business.domain.WordReportEntryManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WordReportEntry;
import com.rstech.wordwatch.dao.WordReportEntryAux;
import com.rstech.wordwatch.web.display.WordOfTheDay;
import com.rstech.wordwatch.web.display.WordOfTheDayEntry;
import com.rstech.wordwatch.web.display.WordOfTheDayOneDefinition;
import com.rstech.wordwatch.web.display.WordOfTheDayOneExample;
import com.rstech.wordwatch.web.display.XmlUtil;
import com.rstech.wordwatch.web.session.WebSessionManager;

@Controller
public class WordOfTheDayController {
	private static final Class thisClass = WordOfTheDayController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	/**
	 * The keys are
	 * 
	 * public static final String SENTENCE_KEY_CODE = "SEN";
	public static final String DEFINITION_KEY_CODE = "DEF";
	public static final String SAMPLE_SENTENCE_KEY_CODE = "SAMP";
	public static final String CHINESE_DEF_KEY_CODE = "CHN";
	public static final String USER_DEF_KEY_CODE = "USER";
	 */
	
	@RequestMapping("/jsp/word_of_the_day.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		 
		
		 
		String userName = "";
		String password = "";
		userName = request.getParameter("User");
		password = request.getParameter("Password");
		SessionManager sessMgr = new SessionManager();
		if (userName != null && !userName.trim().isEmpty()) {
			userName = userName.toLowerCase();
		}
		
		// # of days the user wants to retrieve back ... usually 2 weeks
		String daysInThePast = request.getParameter("DayInPast");
		if (daysInThePast == null) {
			daysInThePast = "100";
		}
		Hashtable entryModel = new Hashtable(); 
		ModelAndView mav = new ModelAndView("wordOfTheDayXMLView", "model", entryModel);
		LoginUserInfo rc = sessMgr.login(userName, password); 
		if (rc.getLastReturnCode() == SessionManager.SUCCESS_CODE) {
			WordReportEntry entry = WordReportEntryManager.getRandomWordReportEntry(rc.getUser().getID(), Integer.valueOf(daysInThePast));
					
			if (entry != null) {
				WordOfTheDay myWord = formatWordOfTheDay(entry);
				entryModel.put("wordOfTheDayXMLView", myWord);
			} 
			
		} else {
			
		}
		logger.debug("exiting " + methodName);

		//ResourceBundle aBundle = ResourceBundle.getBundle("xmlViews");
		return mav;
	}
	
	/**
	 * Construct and return the object of the WordOfTheDay using the passed in entry.
	 * @param entry
	 * @return
	 */
	WordOfTheDay formatWordOfTheDay(WordReportEntry entry) {
		WordOfTheDay myWord = new WordOfTheDay();
		WordOfTheDayEntry myEntry = new WordOfTheDayEntry ();
		myEntry.setId(entry.getID());
		myEntry.setEntryWord(entry.getWORD());
		List<WordReportEntryAux> auxData = WordReportEntryManager.retrieveAllKeyRecords(entry.getID());
		ArrayList<WordOfTheDayOneDefinition> listOfDefinitions = null;
		ArrayList<WordOfTheDayOneExample> listOfExamples = null; 
		for (WordReportEntryAux each: auxData) {
			if (each.getAUX_KEY().startsWith(WordReportEntryManager.SAMPLE_SENTENCE_KEY_CODE)) {
				WordOfTheDayOneExample exampleOne = new WordOfTheDayOneExample();
				exampleOne.setExampleText(each.getAUX_VALUE());
				exampleOne.setLanguage("EN");
				if (listOfExamples == null) {
					listOfExamples = new ArrayList<WordOfTheDayOneExample>();
				}
				listOfExamples.add(exampleOne);
			} else if (each.getAUX_KEY().startsWith(WordReportEntryManager.CHINESE_DEF_KEY_CODE)) {
				WordOfTheDayOneDefinition definitionOne = new WordOfTheDayOneDefinition();
				definitionOne.setDefinitionText(each.getAUX_VALUE());
				definitionOne.setLanguage("CHN");
				if (listOfDefinitions == null) {
					listOfDefinitions = new ArrayList<WordOfTheDayOneDefinition>();
				}
				listOfDefinitions.add(definitionOne);
			} else if (each.getAUX_KEY().startsWith(WordReportEntryManager.SENTENCE_KEY_CODE)) {
				WordOfTheDayOneDefinition definitionOne = new WordOfTheDayOneDefinition();
				definitionOne.setDefinitionText(each.getAUX_VALUE());
				definitionOne.setLanguage("EN");
				if (listOfDefinitions == null) {
					listOfDefinitions = new ArrayList<WordOfTheDayOneDefinition>();
				}
				listOfDefinitions.add(definitionOne);
			} 
		}
		myEntry.setDefinitions(listOfDefinitions);
		myEntry.setExamples(listOfExamples); 
		myWord.setEntry(myEntry);
		return myWord;	
	}
}
