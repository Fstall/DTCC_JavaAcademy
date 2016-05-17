package com.rstech.wordwatch.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.rstech.wordwatch.dao.RSClient;

import java.io.IOException;
import java.util.ArrayList;

public class ClientListController extends BaseRSController implements Controller {
	private static final Class thisClass = ClientListController.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        // userDetails = auth.getPrincipal()
		}

		ArrayList <RSClient> aList = new ArrayList<RSClient> ();
		RSClientManager mgr = new RSClientManager();
		aList = (ArrayList<RSClient>) mgr.getClientList();
		
		/*String event = request.getParameter("event");
		
		if(event != null && event.length() > 0) {
			if (event.equals(SAVE_EVENT)) {

			} else if (event.equals(CANCEL_EVENT)) {  
				// default behavior
			}
		}*/
		ModelAndView clients = new ModelAndView("client_list");
		clients.addObject("clients", aList);
		logger.debug("exiting " + methodName);

		return clients;
	}

}
