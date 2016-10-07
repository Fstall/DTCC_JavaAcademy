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
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.RSClient; 

import java.io.IOException;
import java.util.List;

@Controller
public class WelcomeController extends BaseRSController {
	private static final Class thisClass = WelcomeController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	final String LOGIN_EVENT = "Login";
	final String RESET_EVENT = "Reset";
	
	final String CANNOT_FIND_USER_CODE    = "10";
	final String CANNOT_FIND_USER_TEXT    = "Cannot find user ";
	final String CANNOT_FIND_USER_MESSAGE = "Please re-entry! ";
	
	@RequestMapping("/jsp/welcome.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		
		ModelAndView mav = new ModelAndView("welcome");
		String event = request.getParameter("event");
		
		String userName = "";
		String password = "";
		if (event != null) 
		{
			if(event.equals(LOGIN_EVENT)){
				userName = request.getParameter("User");
				password = request.getParameter("Password");
				
				RSUserManager userMgr = new RSUserManager();
				RSClientManager mgr = new RSClientManager();
				
				List<RSUser> users = userMgr.getUserByLoginAndPassword(userName, password);
				if (users != null && users.size() > 0) {
					RSUser user = users.get(0);
					Long RSClientId = user.getRS_CLIENT();
					
					List<RSClient> clients = mgr.getClientByUser(RSClientId);
					RSClient client = clients.get(0);
					
					if (client != null) {
						mav.addObject("client", client);
					}
				} else {
					mav = new ModelAndView("hello.htm");
					ApplicationError err = new ApplicationError(CANNOT_FIND_USER_CODE,  CANNOT_FIND_USER_TEXT,CANNOT_FIND_USER_MESSAGE);
					mav.addObject("error", err);
				}
				
			} else if (event.equals(RESET_EVENT)) { 
				// back to client_login.do
				mav.addObject("client", "");
			}
		}
		logger.debug("exiting " + methodName);
		
	return mav;
	}
}
