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
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.RSClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ClientEditController implements Controller {
	private static final Class thisClass = ClientEditController.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	
	final String SAVE_EVENT = "Save";
	final String CANCEL_EVENT = "Cancel";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);
		
		ModelAndView mav = new ModelAndView("client_edit");
		String event = request.getParameter("event");
		
		RSUserManager userMgr = new RSUserManager();
		RSClientManager mgr = new RSClientManager();
		if(event != null && event.length() > 0){
			if (event.equals(SAVE_EVENT)) {
				mav = saveEditClient(request);
			} else if (event.equals(CANCEL_EVENT)) {  
				String cid = request.getParameter("client_id");
				String parameter = "?cid=" + cid;
				mav = new ModelAndView("redirect:/jsp/client_list.do"
						+ parameter); 
			} else {
				// default behavior
			}
		} else {
			String cid = request.getParameter("cid");
			RSClient theClient = mgr.getClientByID(new Long(cid));
			mav.addObject("client", theClient);
		}
		logger.debug("exiting " + methodName);
		return mav;
	}
	
	/** Process the save event.
	 * 
	 * @param request
	 * @return
	 */
	private ModelAndView saveEditClient(HttpServletRequest request) {
		String methodName = "saveEditClient";
		logger.debug("entering " + methodName);
		
		String id       = request.getParameter("client_id");
		ModelAndView mav = new ModelAndView("client_edit");
		
		if (id != null && id.length() > 0) {
			RSClientManager clientMgr = new RSClientManager();
			RSClient theClient = clientMgr.getClientByID(new Long(id));
			if (theClient != null) {
				String name 	= request.getParameter("Name");
				String disp 	= request.getParameter("Display");
				String addr1 	= request.getParameter("Addr1");
				String addr2 	= request.getParameter("Addr2");
				String city 	= request.getParameter("City");
				String state	= request.getParameter("State");
				String zip     	= request.getParameter("Zip");
				String country 	= request.getParameter("Country");

				clientMgr.updateExistingClient(theClient, name, disp, addr1, addr2, city, state, zip, country);
				
			}
			mav.addObject("client",theClient);
		}
		logger.debug("exiting " + methodName);
		return mav;
	}

}
