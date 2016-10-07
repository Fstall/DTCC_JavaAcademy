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
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.WDUserReportManager;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WDUserReport;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserReportListController {
	private static final Class thisClass = UserReportListController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	@RequestMapping("/jsp/user_report_list.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		
		ModelAndView user_report_list = new ModelAndView("user_report_list");
		List<WDUserReport> aList = null;
		
		String userID = request.getParameter("uid");		
		
		RSUserManager rsMgr = new RSUserManager();
		RSUser specUser = rsMgr.getUserByID(new Long(userID));
		
		WDUserReportManager rsUsrMgr = new WDUserReportManager();
		Long bid = new Long(userID);
		// For now, this only gives a user report list of ONE user.
		aList = rsUsrMgr.getUserReportList(bid);
		 	
		user_report_list.addObject("user_reports", aList);
		logger.debug("exiting " + methodName);
		return user_report_list;
	}

}
