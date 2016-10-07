package com.rstech.wordwatch.web;

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.web.ApplicationError;

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

import com.rstech.wordwatch.business.domain.LoginUserInfo;
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.SessionManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;

import java.io.IOException; 
import java.util.List;


@Controller 
public class StaffLoginController {
	private static final Class thisClass = StaffLoginController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	
	final String LOGIN_EVENT = "Login";
	final String RESET_EVENT = "Reset";
	
	final String CANNOT_FIND_USER_CODE    = "10";
	final String CANNOT_FIND_USER_TEXT    = "Cannot find user ";
	final String CANNOT_FIND_USER_MESSAGE = "Please re-entry! ";
	
	final String FAILED_TO_LOGIN = "Failed to login!";
	final String FAILED_TO_LOGIN_MESSAGE = "Please re-try! ";
	 
	@RequestMapping("/jsp/staff_login.do")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String methodName = "handleRequest";
    	logger.debug("entering " + methodName);
		

        logger.info("Returning client_login view");

        ModelAndView mav = new ModelAndView("staff_login");
		String event = request.getParameter("event");
		
		String userName = "";
		String password = "";
		if (event != null) 
		{
			if(event.equals(LOGIN_EVENT)) {
				userName = request.getParameter("User");
				password = request.getParameter("Password");
				SessionManager sessMgr = new SessionManager();
				
				LoginUserInfo rc = sessMgr.loginStaff(userName, password);
				ApplicationError err = null;
				
				switch (rc.getLastReturnCode()) {
				case SessionManager.CANNOT_FIND_USER_ID:
				case SessionManager.INCORRECT_PASSWORD:
				case SessionManager.MUST_ENTER_USER_ID_AND_PASSWORD:
				case SessionManager.USER_HAS_BEEN_DELETED:
					mav = new ModelAndView("staff_login");
					err = new ApplicationError(FAILED_TO_LOGIN,  FAILED_TO_LOGIN,FAILED_TO_LOGIN_MESSAGE);
					mav.addObject("error", err);
					break;
				default:
					RSUserManager userMgr = new RSUserManager();
					RSClientManager mgr = new RSClientManager();
					List<RSUser> users = userMgr.getUserByLogin(userName);
					RSUser theUser = users.get(0);  
					mav = new ModelAndView("welcome");
					mav.addObject("user", rc.getUser());  
					break;
				} 
			} else if (event.equals(RESET_EVENT)) { 
				// back to client_login.do
				mav = new ModelAndView("client_login"); 
			}
		}
		logger.debug("exiting " + methodName);
		
	return mav;
    } 
}
