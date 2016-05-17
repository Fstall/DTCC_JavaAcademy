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
import java.util.Properties;

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

import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
import javax.activation.*;

public class ForgottenPasswordController implements Controller {
	private static final Class thisClass = ForgottenPasswordController.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	final int BUFSIZE = 2000000;
	final String RESET_EVENT = "Reset";

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = "handleRequest";
		logger.debug("entering " + methodName);

		logger.info("Returning forgotten_password_email view");

		ModelAndView mav = new ModelAndView("forgotten_password_email");
		String event = request.getParameter("event");

		if (event != null) {
			if (event.equals(RESET_EVENT)) {
				String emailAddr = request.getParameter("Email");
				if (emailAddr != null && !emailAddr.trim().isEmpty()) {
					emailAddr = emailAddr.toLowerCase();
					sendEmail(emailAddr);
				}
			}
		}
		logger.debug("exiting " + methodName);
		return mav;
	}

	private boolean sendEmail(String emailAddr) {
		boolean result = false;
		// Recipient's email ID needs to be mentioned.
		String to = emailAddr;

		// Sender's email ID needs to be mentioned
		String from = "web@gmail.com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		/*
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message); 
			result = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}*/
		return result;
	}
}
