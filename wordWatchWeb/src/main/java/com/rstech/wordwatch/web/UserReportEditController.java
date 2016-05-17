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
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserReportEditController implements Controller {
	private static final Class thisClass = UserReportEditController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	final String SAVE_EVENT = "Save";
	final String CANCEL_EVENT = "Cancel";
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		

		ModelAndView mav = new ModelAndView("user_edit");

		String uid = request.getParameter("uid");
		RSUserManager userMgr = new RSUserManager();
		RSUser specificUser = new RSUser();
		
		if(uid != null){
			specificUser = userMgr.getUserByID(new Long(uid));
			mav.addObject("user", specificUser);
		}
		logger.debug("exiting " + methodName);
		return mav;
	}

}
