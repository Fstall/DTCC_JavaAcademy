package com.rstech.wordwatch.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.rstech.utility.OSFinder;
import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.business.domain.LoginUserInfo;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.WordReportEntryManager;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportEntry;
import com.rstech.wordwatch.dao.WordReportEntryAux;
import com.rstech.wordwatch.utility.GimpClient;
import com.rstech.wordwatch.web.display.WordReportEntryDetail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportEntryEditController implements Controller {
	private static final Class thisClass = ReportEntryEditController.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	
	final String SAVE_EVENT = "save";


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		String save = request.getParameter("notes");	
		
		String entryID = request.getParameter("entry_id");
		LoginUserInfo sessionUser = (LoginUserInfo) request.getSession().getAttribute("LoginUserInfo");
		ModelAndView mav = new ModelAndView("report_entry_edit");
		if (sessionUser != null) {
			RSUser aUser = sessionUser.getUser();	 
			mav.addObject("user", aUser);
		    WordReport currentReport =  sessionUser.getCurrentReport();
		    WordReportEntry theEntry = null;
			if (currentReport != null) {
				List<WordReportEntry> entryList = WordReportEntryManager.getWordReportEntry(currentReport);
				for (WordReportEntry each : entryList) {
					if (save != null){
						System.out.println("save: " + each);
						WordReportEntryManager.createAuxRecord(each.getID(),
								save, WordReportEntryManager.USER_DEF_KEY_CODE);
						/*List<WordReportEntryAux> list = 
						WordReportEntryManager.retrieveAllKeyRecords(each.getID());
						for (WordReportEntryAux auxRecord : list) {
							System.out.println(auxRecord.getWD_REPORT_ENTRY());
							System.out.println(auxRecord.getAUX_VALUE());
						}*/
					
					}else {
						save = null;
					}
					if (each.getID().toString().equals(entryID)) {
						/* The following code is OS specfic - the Runtime guy is a DOS command! 
						 * It creates the word image in the location specified in the GIMP script - deployed to
						 * C:\Program Files (x86)\GIMP-2.0\share\gimp\2.0\scripts\draw_word_image.scm -> See also
						 * the GIMP project in this workspace.
						 */
						
						
						theEntry = each;
						/*
						Runtime rt = Runtime.getRuntime();  
						Process proc = null;
						if (OSFinder.isWindows())
							proc = rt.exec("cmd.exe /c C:\\projects\\java\\rstech\\GIMP_script\\scripts\\driver.cmd " + theEntry.getWORD() );
						else if (OSFinder.isUnix()) 
							proc = rt.exec("/opt/utils/draw_word_need_input.sh " + theEntry.getWORD() );
						try {
							proc.waitFor();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						
						GimpClient aClient = new GimpClient();
						
						try {
							aClient.drawWord(theEntry.getWORD());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						List<WordReportEntryAux> auxRecords = WordReportEntryManager.retrieveAllKeyRecords(theEntry.getID());
						for (WordReportEntryAux eachAuxRecord : auxRecords) {
							int pos = -1;
							String value = eachAuxRecord.getAUX_VALUE();
							if (value.contains("[special]")) {
								String data = this.processSpecial(value);
								logger.debug("special data:" + data);
								eachAuxRecord.setAUX_VALUE(data);
							} else if (value.contains("[yahoo_special]")) {
								String data = this.processYahooSpecial(value);
								logger.debug("yahoo special data:" + data);
								eachAuxRecord.setAUX_VALUE(data);
							}
							else if ((pos = value.indexOf(theEntry.getWORD())) >= 0) {
								String firstPart = value.substring(0, pos);
								String secondPart = " <strong style=\"color:black\">" + theEntry.getWORD() + "</strong>";
								String thirdPart = value.substring(pos + theEntry.getWORD().length());
								eachAuxRecord.setAUX_VALUE(firstPart + secondPart + thirdPart);
							}
						}
						mav.addObject("entry_id", entryID);
						mav.addObject("word", theEntry);
						mav.addObject("report", currentReport);
						WordReportEntryDetail detail = new WordReportEntryDetail();
						detail.setTheEntry(theEntry);
						detail.setSampleSentenceList((ArrayList) auxRecords);
						
						mav.addObject("detailWord", detail);
						mav.addObject("imgsvr", "http://masstermmind.com:8080/RSImageService-0.0.1-SNAPSHOT/GetImage?db=tmus&scaling=true&docId=");
						break;
					}
				}
 			} 
		}
		//users.addObject("edit_link", user_edit_links);
		logger.debug("exiting " + methodName);
		return mav;
	}
	
	static final String pattern = "[special]";
	static final String patternEnd = "[/special]";
	static final int skip = 9;
	static final int skipEnd = 10;

	static final String yahooPattern = "[yahoo_special]";
	static final int skipYahoo = 15;
	

	/*
	 	http://api.pearson.com/longman/dictionary/
						/multimedia/exa_pron/p008-001319316.mp3
						?apikey=ac2e449fca45531fa8ec661b073a88f6
	 */
	static final String baseUrl = "http://api.pearson.com/longman/dictionary";
	
	static final String apikey = "?apikey=ac2e449fca45531fa8ec661b073a88f6";
	
	
	String processSpecial(String value) {
		String methodName = "processSpecial";
		logger.debug("entering " + methodName);
		
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		if (value.contains(pattern)) {
			int pos = -1;
			int endPos = 0;
			String token = "";
			while ((pos = value.indexOf(pattern, pos)) >= 0) {
				if (first && pos > 0) {
					token = value.substring(0, pos);
					first = false;
					sb.append(token);
				}
				endPos = value.indexOf(patternEnd, pos);
				token = value.substring(pos + 9, endPos);
				token = token.trim();
				if (token.startsWith("/multimedia/")) {
					if (token.endsWith(".mp3")) {
						//sb.append("<embed name=\"plugin\" src=" + baseUrl + token + apikey + "\" type=\"audio/mpeg\">");
						
						sb.append("<embed width=\"17\" height=\"15\" align=\"texttop\" flashvars=\"soundUrl="+
								baseUrl + token + apikey + "\" " + 
								"wmode=\"transparent\" allowscriptaccess=\"sameDomain\" salign=\"t\" menu=\"false\" loop=\"false\" quality=\"high\" id=\"speaker\" src=\"http://static.sfdict.com/dictstatic/d/g/speaker.swf\" type=\"application/x-shockwave-flash\">");
						
						//http://static.sfdict.com/dictstatic/dictionary/audio/luna/E03/E0391900.mp3" wmode="transparent" allowscriptaccess="sameDomain" salign="t" menu="false" loop="false" quality="high" id="speaker" src="http://static.sfdict.com/dictstatic/d/g/speaker.swf" type="application/x-shockwave-flash">
						 
						
					} else if (token.endsWith(".jpg")) {
						sb.append("<img src=\"" + baseUrl + token + apikey + "\" " + "> </img>");   
					}
					sb.append("<br/>");
				} else {
					sb.append(" <strong style=\"color:black\">" + token + "</strong>");
				}
				pos++;
			}
			token = value.substring(endPos + skipEnd);
		}
		logger.debug("exiting " + methodName);
		return sb.toString();
	}

	String processYahooSpecial(String value) {
		String methodName = "processYahooSpecial";
		logger.debug("entering " + methodName);
		
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		if (value.contains(yahooPattern)) {
			int pos = -1;
			int endPos = 0;
			String token = "";
			pos = value.indexOf(yahooPattern);
				 
			endPos = value.indexOf(".mp3");
			token = value.substring(pos + skipYahoo + 2 + "href=".length(), endPos + 5);
			sb.append("<embed width=\"17\" height=\"15\" align=\"texttop\" flashvars=\"soundUrl="+
							token + 
							" wmode=\"transparent\" allowscriptaccess=\"sameDomain\" salign=\"t\" menu=\"false\" loop=\"false\" quality=\"high\" id=\"speaker\" src=\"http://static.sfdict.com/dictstatic/d/g/speaker.swf\" type=\"application/x-shockwave-flash\">");
			sb.append("<br/>");
		}
		logger.debug("exiting " + methodName);
		return sb.toString();
	}

	
}
