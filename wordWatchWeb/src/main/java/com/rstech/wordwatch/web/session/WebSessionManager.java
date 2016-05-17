package com.rstech.wordwatch.web.session;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.business.domain.LoginUserInfo;
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.SessionManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;

public class WebSessionManager {
	private static final Class thisClass = WebSessionManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);



	/**
	 * RSTech login cookie name.
	 */
	public static final String RS_LOGIN_COOKIE_NAME   = "RSLogin";
		
	/**
	 * Attempt the login process.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public static LoginUserInfo loginUser(String userName, String password) {
		String methodName = "loginUser";
		logger.debug("entering " + methodName);
		

		SessionManager sessMgr = new SessionManager();
		if (userName != null && !userName.trim().isEmpty()) {
			userName = userName.toLowerCase();
		}
		LoginUserInfo rc = sessMgr.login(userName, password);
		logger.debug("exiting " + methodName);
		return rc;
	}

	/**
	 * Handle the session of the user info in rc. Save the login info into the
	 * current session.
	 * 
	 * At this point, the user has successfully passed username and password check.
	 * @param userName
	 * @param mav
	 * @param request
	 * @param rc
	 */
	public static ModelAndView processLoginSession(String userName, ModelAndView mav,
			HttpServletRequest request, LoginUserInfo rc) {
		String methodName = "processLoginSession";
		logger.debug("entering " + methodName);
		
		RSUserManager userMgr = new RSUserManager();
		RSClientManager mgr = new RSClientManager();
		List<RSUser> users = userMgr.getUserByLogin(userName);
		RSUser theUser = users.get(0);
		// note the function name is getClientByUser but the argument is 
		// really the client id...
		List<RSClient> clients = mgr.getClientByUser(theUser.getRS_CLIENT());
		RSClient client = clients.get(0);
		if (client != null) {
			String parameter = "?user_id=" + theUser.getID();
			mav = new ModelAndView("redirect:/jsp/rpt_portfolio.do" + parameter);
			mav.addObject("client", client);
			// save session to DB
		}
		request.getSession().setAttribute("LoginUserInfo", rc); 
		logger.debug("exiting " + methodName);
		return mav;
	}
	
	
	public static Cookie getRSTechCookie(HttpServletRequest request) {
 		
		Cookie[] cookieArray = request.getCookies();
		if (cookieArray == null) {
			return null;
		}
		for (int i = 0; i < cookieArray.length; i++) {
			Cookie cookie = cookieArray[i];
			if (cookie == null) {
				continue;
			}
			if (RS_LOGIN_COOKIE_NAME.equals(cookie.getName())) {
 				return cookie;
			}
		}
 		return null;
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
	 	
		Cookie[] cookieArray = request.getCookies();
		if (cookieArray == null) {
			return null;
		}
		for (int i = 0; i < cookieArray.length; i++) {
			Cookie cookie = cookieArray[i];
			if (cookie == null) {
				continue;
			}
			if (cookie.getName().equals(name)) {
	 			return cookie;
			}
		}
		return null;
	}
	
	public static void setRSTechCookie(String value, HttpServletResponse response) {
		
		Cookie cookie = new Cookie(RS_LOGIN_COOKIE_NAME, value);
		cookie.setMaxAge(20*365*24*60*60);
		response.addCookie(cookie);
		
	}

	
}
