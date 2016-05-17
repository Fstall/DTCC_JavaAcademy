package com.rstech.wordwatch.web;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;

 
public class RSSession {
	/**
	 * Session data
	 */
 		private static final Class thisClass = RSSession.class;
		private static Logger logger = Logger.getLogger(thisClass);
		
	    /**
	     * Session data key. Session data must be accessed via
	     * provided get/set pair. That's why the key is marked
	     * as private.
	     */
		public final static String SESSION_DATA_KEY = "RSSessionDataKey";

		private Long authenticatedUserID = null; //user from cookie
		private Long effectiveUserID  = null; //user from session
		
		/**
		 * RSTech login cookie name.
		 */
		public static final String RS_LOGIN_COOKIE_NAME   = "NpLogin";
		public static final String JSESSIONID_COOKIE_NAME   = "JSESSIONID";
		public static final String USERNAME_COOKIE_NAME   = "userName";
		
		public Long getAuthenticatedUserID() { return this.authenticatedUserID; }
		public void setAuthenticatedUserID(Long authenticatedUserID) { this.authenticatedUserID = authenticatedUserID; }

		public Long getEffectiveUserID(){ return this.effectiveUserID; }
		public void setEffectiveUserID(Long effectiveUserID){ this.effectiveUserID = effectiveUserID; }
		
		public static RSSession getRSSession(HttpServletRequest request) {
			String methodName = "getRSSession";
			logger.debug("entering " + methodName);
			
			RSSession session = (RSSession) request.getSession().getAttribute(SESSION_DATA_KEY);
			if (session == null) {
				// Create a new session
				session = new RSSession();
				request.getSession().setAttribute(SESSION_DATA_KEY, session);
			}
			logger.debug("exiting " + methodName);
			return session;
		}

		public static Cookie getRSTechCookie(HttpServletRequest request) {
			String methodName = "getRSTechCookie";
			logger.debug("entering " + methodName);
			
			 
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
					logger.debug("exiting " + methodName);
			 		return cookie;
				}
			}
			logger.debug("exiting " + methodName);
			return null;
		}

		public static Cookie getCookie(HttpServletRequest request, String name) {
			String methodName = "getCookie";
			logger.debug("entering " + methodName);
			
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
			logger.debug("exiting " + methodName);
			
			return null;
		}
		
		/**
		 * Get a string that concatenates all cookies as URL parameters as in the example: name1=value1&name2=value2.
		 * @param request
		 * @return
		 */
		public static String getCookiesAsParam(HttpServletRequest request){
			String methodName = "getCookiesAsParam";
			logger.debug("entering " + methodName);
			
			
			StringBuffer sb = new StringBuffer();

			Cookie[] cookieArray = request.getCookies();
			if (cookieArray == null) {
				logger.debug("exiting " + methodName);
				return null;
			}

			try{
				for (int i = 0; i < cookieArray.length; i++) {
					Cookie cookie = cookieArray[i];
					if (cookie == null) 
						continue;
					String name = java.net.URLEncoder.encode(cookie.getName(), "UTF-8");
					String value = java.net.URLEncoder.encode(cookie.getValue(), "UTF-8");
					if(sb.length()>0)
						sb.append("&");
					sb.append(name).append("=").append(value);
				}
			}catch (UnsupportedEncodingException e){
				logger.debug("exiting " + methodName);
				return null;
			}
			logger.debug("exiting " + methodName);
			return sb.toString();
		}
		
		public static void setRSTechCookie(String value, HttpServletResponse response) {
			Cookie cookie = new Cookie(RS_LOGIN_COOKIE_NAME, value);
			response.addCookie(cookie);
		}

		public static void removeRSTechCookie(HttpServletRequest request, HttpServletResponse response) {
			String methodName = "removeRSTechCookie";
			logger.debug("entering " + methodName);
			
			
			Cookie cookie = getRSTechCookie(request);
			if (cookie == null) {
				logger.debug("exiting " + methodName);
				return;
			}
			
			cookie.setMaxAge(0);
			cookie.setValue("");
			response.addCookie(cookie);
			
			logger.debug("exiting " + methodName);
			return;
		}
		
	}