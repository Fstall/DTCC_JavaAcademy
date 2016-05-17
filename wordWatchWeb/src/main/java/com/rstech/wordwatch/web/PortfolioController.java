package com.rstech.wordwatch.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.rstech.wordwatch.business.domain.LoginUserInfo;
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.WDUserReportManager;
import com.rstech.wordwatch.business.domain.WordReportManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.web.display.UserReportRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PortfolioController implements Controller {
	private static final Class thisClass = PortfolioController.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	
	private static final String SORT_BY_DATE_ASC = "ORDER_BY_DATE_ASC";
	private static final String SORT_BY_DATE_DESC = "ORDER_BY_DATE_DESC";


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		
		 		
		ModelAndView mv = new ModelAndView("rpt_portfolio");
		String isSortByDate = request.getParameter("sortByDate");
		 
		LoginUserInfo sessionUser = (LoginUserInfo) request.getSession().getAttribute("LoginUserInfo");
		String portfolioURL = "rpt_portfolio.do?";
		RSUser aUser = sessionUser.getUser();
		if (sessionUser != null) { 
			if (aUser != null) {
				String listURL = "report_entry_list.do?";
				String parameter = "user_id=" + aUser.getID();
				
				ArrayList<UserReportRecord> userReportRecords = new ArrayList<UserReportRecord> ();
				List<WDUserReport> userReports = WDUserReportManager.getUserReportList(aUser.getID());
				for (WDUserReport each : userReports) {
					WordReport aReport = WordReportManager.getReportByID(each.getWD_REPORT());
					UserReportRecord aUserReportRecord = new UserReportRecord();
					aUserReportRecord.setReportId(each.getID());
					aUserReportRecord.setReportName(aReport.getTITLE());
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					String firstRunDate = "01/01/1990 00:00:00";
					if (aReport.getFIRST_RUN_DATE() != null)
						firstRunDate = df.format(aReport.getFIRST_RUN_DATE());
					aUserReportRecord.setFirstRunDate( firstRunDate);
					String title = aReport.getTITLE();
					int pos = title.indexOf("$");
					if (pos >= 0) {
						title = title.substring(pos + 1);
					}  
					aUserReportRecord.setFirstRunDateForSort(aReport.getFIRST_RUN_DATE());
					aUserReportRecord.setViewLink(listURL+"rpt_title=" +URLEncoder.encode(aReport.getRPT_URL(), "UTF-8") + "&user_id=" + aUser.getID());
					userReportRecords.add(aUserReportRecord);
				}
				//sortByDateLink
				if (isSortByDate != null) {
					if (isSortByDate.compareToIgnoreCase(SORT_BY_DATE_ASC) == 0) {
						Comparator<UserReportRecord> comparator = new Comparator<UserReportRecord>() {
							public int compare(UserReportRecord c1, UserReportRecord c2) {
								if (c1.getFirstRunDateForSort().before(c2.getFirstRunDateForSort())) {
									return -1;
								} else {
									return 1;
								} 
							}
						};
						Collections.sort(userReportRecords, comparator);
						parameter += "&sortByDate=" + SORT_BY_DATE_DESC;
						String ascdsc = "ascending";
						mv.addObject("ascdsc", ascdsc);
						mv.addObject("imgsvr", "http://localhost:8080/RSImageService-0.0.1-SNAPSHOT/GetImage?db=tmus&height=150&width=150&scaling=true&docId=arrowup");
						
					}
					else if (isSortByDate.compareToIgnoreCase(SORT_BY_DATE_DESC) == 0) {
						Comparator<UserReportRecord> comparator = new Comparator<UserReportRecord>() {
							public int compare(UserReportRecord c1, UserReportRecord c2) {
								if (c1.getFirstRunDateForSort().before(c2.getFirstRunDateForSort())) {
									return 1;
								} else {
									return -1;
								} 
							}
							
						};
						Collections.sort(userReportRecords, comparator);
						parameter += "&sortByDate=" + SORT_BY_DATE_ASC;
						String ascdsc = "descending";
						mv.addObject("ascdsc", ascdsc);					
						mv.addObject("imgsvr", "http://localhost:8080/RSImageService-0.0.1-SNAPSHOT/GetImage?db=tmus&height=150&width=150&scaling=true&docId=arrowdown");
						
					} 
					String nextClickDateSortURL = portfolioURL + parameter;
					mv.addObject("sortByDateLink", nextClickDateSortURL); 
				}
				else {
					parameter += "&sortByDate=" + SORT_BY_DATE_DESC;
					String nextClickDateSortURL = portfolioURL + parameter;
					mv.addObject("sortByDateLink", nextClickDateSortURL);
					String ascdsc = "ascending";
					mv.addObject("ascdsc", ascdsc);
					mv.addObject("imgsvr", "http://localhost:8080/RSImageService-0.0.1-SNAPSHOT/GetImage?db=tmus&height=150&width=150&scaling=true&docId=arrowup");
				}
			
				// note the function name is getClientByUser but the argument is 
				// really the client id...
				RSClientManager mgr = new RSClientManager();
				List<RSClient> clients = mgr.getClientByUser(aUser.getRS_CLIENT());
				RSClient client = clients.get(0); 
				mv.addObject("client", client); 
				mv.addObject("user_reports", userReportRecords);
				//teacher can see the list of student users. 
				int  teacherBit = aUser.getUSER_TP_NO() | RSUserManager.getTeacherUserType();
				String studentLink =  "";
				if (teacherBit > 0) {
					parameter = "instructor="+aUser.getID();
					studentLink =  "student_list.do?" + parameter; 
					mv.addObject("studentLink", studentLink);
				} else {
					mv.addObject("studentLink", studentLink);
				}
			}
			
		}
		logger.debug("exiting " + methodName);
		return mv;
	}

}
