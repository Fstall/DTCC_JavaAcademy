package com.rstech.wordwatch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

@Controller
public class StudentListController {
	private static final Class thisClass = StudentListController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	@RequestMapping("/jsp/student_list.do")
	public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		ModelAndView entries = null;
		String       id      = request.getParameter("instructor");
		if (id != null) 
		{
			Long    bid = new Long(id);
			RSUserManager rsMgr = new RSUserManager();
			RSUser theUser = rsMgr.getUserByID(bid); 
			entries = new ModelAndView("student_list");   
			/**
			 * Populate the list of students from the instructor's user id...
			 */
			 List<RSUser> students = rsMgr.getStudentByUser(bid);
			 ArrayList <String> student_edit_links = new ArrayList<String> ();
			 ArrayList <RSUser> realStudents = new ArrayList<RSUser> ();
			 for (RSUser eachUser : students) {
				realStudents.add(eachUser); 
				String aLink = "";
				aLink = "student_entry_edit.do?uid=" +  eachUser.getID();
				student_edit_links.add(aLink);    
			}  
			entries.addObject("student_edit_links", student_edit_links); 
			/**
			 * Need to format the objects.
			 */
			entries.addObject("entries", realStudents);
		}
		ModelAndView mav = new ModelAndView("student_list");
		/*String save = request.getParameter("notes");	
		LoginUserInfo sessionUser = (LoginUserInfo) request.getSession().getAttribute("LoginUserInfo");
		
		if (sessionUser != null) {
			RSUser aUser = sessionUser.getUser();	 
			mav.addObject("user", aUser);
		    WordReport currentReport =  sessionUser.getCurrentReport();
		  	if (currentReport != null) {
				List<WordReportEntry> studentList = WordReportEntryManager.getWordReportEntry(currentReport);
				for (WordReportEntry each : studentList) {
					if (save != null){
						System.out.println("save: " + each);
						WordReportEntryManager.createAuxRecord(each.getID(),
								save, WordReportEntryManager.USER_DEF_KEY_CODE);
		logger.debug("exiting " + methodName);
		return entries;
					}
				}
			}
		}*/
		return entries;
	}
}
