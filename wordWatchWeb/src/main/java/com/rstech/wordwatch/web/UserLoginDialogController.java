package com.rstech.wordwatch.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

public class UserLoginDialogController implements Controller {
	private static final Class thisClass = UserLoginDialogController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	final int BUFSIZE = 2000000;
	final String LOGIN_EVENT = "Sign In!";
	final String RESET_EVENT = "Reset";
	final String CREATE_NEW_USER_EVENT = "CreateUser";
	final String DOWNLOAD_EVENT = "download";

	final String CANNOT_FIND_USER_CODE = "10";
	final String CANNOT_FIND_USER_TEXT = "Cannot find user ";
	final String CANNOT_FIND_USER_MESSAGE = "Please re-entry! ";

	final String FAILED_TO_LOGIN = "Failed to login!";
	final String FAILED_TO_LOGIN_MESSAGE = "Please re-try! ";
	final String MUST_PROVIDE_USERNAME = "Email is required to register!";
	final String MUST_PROVIDE_PASSWORD = "Password is required to register!";

	final String ACCOUNT_IS_LOCKED = "Your account is locked!";
	final String ACCOUNT_IS_LOCKED_MESSAGE = "Please email to RSTech Customer Service (prototype phase won't provide) to unlock it! ";


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		

		logger.info("Returning client_login view");

		ModelAndView mav = new ModelAndView("user_log_in_dialog");
		String event = request.getParameter("event");

		String userName = "";
		String password = "";
		if (event != null) {
			if (event.equals(LOGIN_EVENT)) {
				userName = request.getParameter("User");
				password = request.getParameter("Password");
				SessionManager sessMgr = new SessionManager();
				if (userName != null && !userName.trim().isEmpty()) {
					userName = userName.toLowerCase();
				}
				LoginUserInfo rc = sessMgr.login(userName, password);
				ApplicationError err = null;

				switch (rc.getLastReturnCode()) {
				case SessionManager.CANNOT_FIND_USER_ID:
				case SessionManager.INCORRECT_PASSWORD:
				case SessionManager.MUST_ENTER_USER_ID_AND_PASSWORD:
				case SessionManager.USER_HAS_BEEN_DELETED:
					mav = new ModelAndView("user_log_in_dialog");
					err = new ApplicationError(FAILED_TO_LOGIN,
							FAILED_TO_LOGIN, FAILED_TO_LOGIN_MESSAGE);
					mav.addObject("error", err);
					break;
				case SessionManager.USER_IS_LOCKED_OUT:
					mav = new ModelAndView("user_log_in_dialog");
					err = new ApplicationError(ACCOUNT_IS_LOCKED,
							ACCOUNT_IS_LOCKED, ACCOUNT_IS_LOCKED_MESSAGE);
					mav.addObject("error", err);
					break;
				default:
					WebSessionManager.setRSTechCookie(
							userName + ":" + password, response);
					break;
				}
			} 
		}
		logger.debug("exiting " + methodName);

		return mav;
	}
}
