package com.rstech.wordwatch.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller; 

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.business.domain.LoginUserInfo;
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.SessionManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser; 
import com.rstech.wordwatch.web.session.WebSessionManager;
import com.rstech.wordwatch.business.domain.RSUserManager;

public class UserSignupController  implements Controller {
	private static final Class thisClass = UserSignupController.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	
	private static final String SIGNUP_EVENT = "Sign Up!"; 

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String methodName = "handleRequest";
    	logger.debug("entering " + methodName);
		

    	String event = request.getParameter("event");
        String FAILED_TO_CREATE_USER = "Cannot create user!";
    	String userName = "";
		String password = "";
		String retypePassword = "";
		ApplicationError err = null;
		
		 
		ModelAndView mav = new ModelAndView("user_signup");
			
		if (event != null) 
		{
			
			if(event.equals(SIGNUP_EVENT)){
				userName = request.getParameter("Username");
				password = request.getParameter("NewPassword");
				retypePassword = request.getParameter("RetypePassword");
				
				try {
					RSUser theUser = RSUserManager.createNewUserUnderSystem(userName,  password, RSUserManager.getRegularUserType(), null);
					SessionManager sessMgr = new SessionManager();
					LoginUserInfo rc = sessMgr.login(userName, password);
					String parameter = "?user_id=" + theUser.getID();
					mav = new ModelAndView("redirect:/jsp/rpt_portfolio.do"
							+ parameter);
					RSClientManager mgr = new RSClientManager();
					List<RSClient> clients = mgr.getClientByUser(theUser
							.getID());
					RSClient client = clients.get(0);
					mav.addObject("client", client);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					err = new ApplicationError(FAILED_TO_CREATE_USER,  FAILED_TO_CREATE_USER,e.getMessage());
					mav.addObject("error", err);
				} 
			}
		} 
         logger.debug("exiting " + methodName);
       return mav;
    } 
}
