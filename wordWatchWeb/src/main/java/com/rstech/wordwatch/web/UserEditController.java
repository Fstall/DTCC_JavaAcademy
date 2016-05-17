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

public class UserEditController implements Controller {
	private static final Class thisClass = UserEditController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	final String SAVE_EVENT = "Save";
	final String CANCEL_EVENT = "Cancel";
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		ModelAndView mav = new ModelAndView("user_edit");
		String event = request.getParameter("event");
		RSUserManager userMgr = new RSUserManager();
		String clientId = request.getParameter("cid"); 
		if(event != null && event.length() > 0){
			if (event.equals(SAVE_EVENT)) {
				mav = saveEditUser(request);
			} else if (event.equals(CANCEL_EVENT)) {  
				String parameter = "?cid=" + clientId;
				mav = new ModelAndView("redirect:/jsp/user_list.do" + parameter);
				RSUserManager rsMgr = new RSUserManager();
				ArrayList <RSUser> aList = new ArrayList<RSUser> ();
				aList = (ArrayList<RSUser>) rsMgr.getUserList(new Long(clientId));
				mav.addObject("users", aList);
				mav.addObject("cid", clientId);
				
			} else {
				// default behavior
			}
		} else {
			String uid = request.getParameter("uid");
			RSUser specificUser = new RSUser();
			
			if(uid != null) {
				specificUser = userMgr.getUserByID(new Long(uid));
				mav.addObject("user", specificUser);
			}
			mav.addObject("cid", clientId);
		}
		logger.debug("exiting " + methodName);
		return mav;
	}
	
	private ModelAndView saveEditUser(HttpServletRequest request) {
		String methodName = "saveEditUser";
		logger.debug("entering " + methodName);
		
		String id       = request.getParameter("user_id");
		ModelAndView mav = new ModelAndView("user_edit");
		String clientId = request.getParameter("cid");
		if (clientId != null || id != null) {
			RSUserManager usrMgr = new RSUserManager();
			RSClientManager mgr = new RSClientManager();
			RSClient theClient = mgr.getClientByID(new Long(clientId));
			RSUser theUser = null;
			if (id != null && id.length() > 0) {
				theUser = usrMgr.getUserByID(new Long(id));
			}
			
			String firstName	= request.getParameter("First");
			String lastName 	= request.getParameter("Last");
			String login		= request.getParameter("Login");
			String password		= request.getParameter("Password");
			String email		= request.getParameter("Email");
			String phone1		= request.getParameter("Phone1");
			String phone2		= request.getParameter("Phone2");
			String addrLine1	= request.getParameter("Addr1");
			String addrLine2	= request.getParameter("Addr2");
			String city			= request.getParameter("City");
			String state		= request.getParameter("State");
			String zipCode		= request.getParameter("Zip");
			String country		= request.getParameter("Country");
			String fax			= request.getParameter("Fax");
			String title		= request.getParameter("Title");
	
			if (theUser != null) 
			{
				String lockedFlag		= request.getParameter("account_lock_id");
				if (lockedFlag != null && (!theUser.getIS_LOCKED().equals(lockedFlag)))
				{
					if (lockedFlag.equals("Y"))
					{
						usrMgr.lockUser(theUser, true);
					}
					else
					{
						usrMgr.lockUser(theUser, false);
					}
				}
				
				usrMgr.updateExistingUser(theUser, theClient, login, firstName, lastName, password, email, phone1, phone2, addrLine1, addrLine2, city, state, zipCode, country, fax, title);
			} else {
				// new user
				try {
					/**
					 * TODO - handle errors ... 
					 * 
					 */
					theUser = RSUserManager.createNewUserUnderSystem(email, password, RSUserManager.getRegularUserType(), null);
					usrMgr.lockUser(theUser, false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String parameter = "?cid=" + clientId;
			mav = new ModelAndView("redirect:/jsp/user_list.do" + parameter);
			RSUserManager rsMgr = new RSUserManager();
			ArrayList <RSUser> aList = new ArrayList<RSUser> ();
			aList = (ArrayList<RSUser>) rsMgr.getUserList(new Long(clientId));
			mav.addObject("users", aList);
			mav.addObject("cid", clientId);
		}
		logger.debug("exiting " + methodName);
		return mav;
	}

}
