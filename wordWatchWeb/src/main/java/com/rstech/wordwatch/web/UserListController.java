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
import com.rstech.wordwatch.dao.RSUser;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class UserListController {
	private static final Class thisClass = UserListController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	@RequestMapping("/jsp/user_list.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		String grabClient = request.getParameter("rs_client_id");
				
		if(grabClient != null && grabClient.length() > 0) { 
			/* Do nothing */ 
		} else if(request.getParameter("cid") != null && request.getParameter("cid").length() > 0) {
			grabClient = request.getParameter("cid");
		}
		
		RSUserManager rsMgr = new RSUserManager();
		ArrayList <RSUser> aList = new ArrayList<RSUser> ();
		aList = (ArrayList<RSUser>) rsMgr.getUserList(new Long(grabClient));
		ModelAndView users = new ModelAndView("user_list");
		users.addObject("users", aList);
		ArrayList <String> user_edit_links = new ArrayList<String> ();
		String aLink = "";
		for (RSUser eachUser : aList) {
			aLink = "user_edit.do#uid=" + eachUser.getID();
			user_edit_links.add(aLink);
		}
		users.addObject("cid", grabClient);
		//users.addObject("edit_link", user_edit_links);
		logger.debug("exiting " + methodName);
		return users;
	}

}
