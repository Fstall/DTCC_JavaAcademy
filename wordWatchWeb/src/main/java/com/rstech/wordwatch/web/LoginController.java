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

public class LoginController implements Controller {
	private static final Class thisClass = LoginController.class;
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

		ModelAndView mav = new ModelAndView("client_login");
		String event = request.getParameter("event");
		String downloadValue = request.getParameter("download");

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
					mav = new ModelAndView("client_login");
					err = new ApplicationError(FAILED_TO_LOGIN,
							FAILED_TO_LOGIN, FAILED_TO_LOGIN_MESSAGE);
					mav.addObject("error", err);
					break;
				case SessionManager.USER_IS_LOCKED_OUT:
					mav = new ModelAndView("client_login");
					err = new ApplicationError(ACCOUNT_IS_LOCKED,
							ACCOUNT_IS_LOCKED, ACCOUNT_IS_LOCKED_MESSAGE);
					mav.addObject("error", err);
					break;
				default:
					mav = WebSessionManager.processLoginSession(userName, mav,
							request, rc);
					WebSessionManager.setRSTechCookie(
							userName + ":" + password, response);
					break;
				}
			} else if (event.equals(RESET_EVENT)) {
				// back to client_login.do
				mav = new ModelAndView("client_login");
			} else if (event.equals(CREATE_NEW_USER_EVENT)) {
				userName = request.getParameter("User");
				password = request.getParameter("Password");
				ApplicationError err = null;
				if (userName == null || userName.trim().isEmpty()) {
					mav = new ModelAndView("client_login");
					err = new ApplicationError(MUST_PROVIDE_USERNAME,
							MUST_PROVIDE_USERNAME, MUST_PROVIDE_USERNAME);
					mav.addObject("error", err);
				} else if (password == null || password.trim().isEmpty()) {
					mav = new ModelAndView("client_login");
					err = new ApplicationError(MUST_PROVIDE_PASSWORD,
							MUST_PROVIDE_PASSWORD, MUST_PROVIDE_PASSWORD);
					mav.addObject("error", err);
				} else {
					RSUser theUser = null;
					try {
						userName = userName.toLowerCase();
						theUser = RSUserManager.createNewUserUnderSystem(
								userName, password, RSUserManager.getRegularUserType(), null);
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
						// save session to DB
						request.getSession().setAttribute("LoginUserInfo", rc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						mav = new ModelAndView("client_login");
						err = new ApplicationError(e.getMessage(),
								e.getMessage(), e.getMessage());
						mav.addObject("error", err);
					}
				}

			} else if (event.equals(DOWNLOAD_EVENT)) {
				// Document document = new Document(); 
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control",
						"no-cache, must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "no-cache, public");
	
				try {
					response.setHeader("Content-Type", "application/octet-stream;");
					response.setHeader("Content-disposition",
							"inline; filename=SpeedDialSetup.msi");
					
					URL url = this.getClass().getClassLoader().getResource("SpeedDialSetup.msi");
					logger.info("download url " + url);
					String urlString = url.toString();
					String filename = "";
					int index = urlString.indexOf("vfs:/");
					if (index >= 0) {
						filename = urlString.substring(4); 
					}
					File f = new File(filename);
	
					int length = 0;
					ServletOutputStream op = response.getOutputStream();
	
					byte[] bbuf = new byte[BUFSIZE];
					DataInputStream in = new DataInputStream(new FileInputStream(f));
					while ((in != null) && ((length = in.read(bbuf)) != -1)) {
						op.write(bbuf, 0, length);
					}
					in.close();
					try {
						response.setContentLength((int) f.length());
						op.flush();
						op.close();
					} catch (Exception e) {
						logger.error(e);
					}
	
				} catch (Exception e) {
					logger.error(e);
				}

				logger.debug("Exiting handleRequest from LoginController.");
			}
		}
		logger.debug("exiting " + methodName);

		return mav;
	}
}
