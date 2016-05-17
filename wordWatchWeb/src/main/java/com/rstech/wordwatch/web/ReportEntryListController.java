package com.rstech.wordwatch.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.business.domain.LoginUserInfo;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.WDUserReportManager;
import com.rstech.wordwatch.business.domain.WordReportEntryManager;
import com.rstech.wordwatch.business.domain.WordReportManager;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportEntry;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportEntryListController implements Controller {
	private static final Class thisClass = ReportEntryListController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		ModelAndView entries = null;
		String       id      = request.getParameter("user_id");
		String       url     = request.getParameter("rpt_title");
		if (id != null) 
		{
			Long    bid = new Long(id);
			RSUserManager rsMgr = new RSUserManager();
			RSUser theUser = rsMgr.getUserByID(bid); 
			entries = new ModelAndView("report_entry_list"); 
			ArrayList <String> entry_edit_links = new ArrayList<String> ();
			ArrayList <WordReportEntry> words = new ArrayList<WordReportEntry> ();
			WDUserReport userReport = null; 
			LoginUserInfo sessionUser = (LoginUserInfo) request.getSession().getAttribute("LoginUserInfo");
			
			if (sessionUser != null) {
				// This function will create a report for the user if one is not found.
				userReport = WDUserReportManager.getUserReportList(theUser.getID(), url);
				if (userReport != null) { 
					bid = userReport.getWD_REPORT();
					WordReport rpt = WordReportManager.getReportByID(bid);
					sessionUser.setCurrentReport(rpt);
					request.getSession().setAttribute("LoginUserInfo", sessionUser);
					List<WordReportEntry> list = WordReportEntryManager.getWordReportEntry(rpt);
					for (WordReportEntry each: list) {
						String aLink = "";
						aLink = "report_entry_edit.do?entry_id=" +  each.getID();
						entry_edit_links.add(aLink);
						String link = each.getIMG_URL1();
						// each.setENTRY_PUBLISHED_DATE(currentTimestamp);
						if (link != null) {
							int pos = link.indexOf(".");
							if (pos >= 0) {
								StringBuffer sb = new StringBuffer(link);
								each.setIMG_URL3(each.getID().toString());
							}
						}
						words.add(each);
					} 
					entries.addObject("words", words);
					entries.addObject("word_edit_links", entry_edit_links); 
					entries.addObject("imgsvr", "http://masstermmind.com:8080/RSImageService-0.0.1-SNAPSHOT/GetImage?db=tmus&height=150&width=150&scaling=true&docId=");
				}  
			}
		}
		logger.debug("exiting " + methodName);
		return entries;
	}
}
