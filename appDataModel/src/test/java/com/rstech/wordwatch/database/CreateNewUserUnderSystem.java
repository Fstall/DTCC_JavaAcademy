package com.rstech.wordwatch.database;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.rstech.utility.RSWord;
import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.WDUserReportManager;
import com.rstech.wordwatch.business.domain.WordReportEntryManager;
import com.rstech.wordwatch.business.domain.WordReportManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportEntry;
import com.rstech.wordwatch.dao.WordReportEntryAux;
import com.rstech.wordwatch.dao.WordReportExample;
import com.rstech.wordwatch.dao.WordReportExample.Criteria;
import com.rstech.wordwatch.dao.mapper.WordReportEntryMapper;
import com.rstech.wordwatch.dao.mapper.WordReportMapper;
import com.rstech.wordwatch.dao.util.ReportAndReportEntryMapper.ReportWithReportEntry;

public class CreateNewUserUnderSystem {
	private static final Class thisClass = CreateNewUserUnderSystem.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	
	static final String userAlreadyExists = "User id (email) already exists!";
	static final String failedToCreateUser = "Failed to create user!";
	
	public static void main(String argv[]) throws Exception {
		String methodName = "main";
		logger.debug("entering " + methodName);
		
		if (argv.length < 1) { 
			throw new Exception("You must run this program with two arguments- userID and email address");
		}
		SqlSession session = null;
		String clientName = "RS Tech"; 
		
		SetupStandaloneContext.setup();
		 
		session = SQLConnection.getSessionFactory().openSession(true);
		System.out.println("ReceiveListd Session Object : " + session);
		 
		
		RSClientManager mgr = new RSClientManager();
		RSClient aClient = mgr.getClientByClientName( clientName);
		if (aClient == null) {
			aClient = mgr.createNewClient("RS Tech", "RS Tech", "123 ABC Street", "", "TestCity", "OR", "98330", "USA");	
		}
		
		String userIdEmailAddress = argv[0];
		String password = argv[1];
		RSUserManager userMgr = new RSUserManager();
		RSUser aUser = userMgr.getFirstUserByLogin(userIdEmailAddress);
		
		if (aUser == null) {
			aUser = userMgr.createNewUser(aClient, userIdEmailAddress, userIdEmailAddress, userIdEmailAddress, password, password, "", "", "", "", "", "", "", "", "", "", RSUserManager.getRegularUserType());	
		} else {
			// this is an exception - you can't create a new user when the user
			// id exist
			session.close();
			throw new Exception(userAlreadyExists);
		}
		if (aUser == null) {
			session.close(); 
			throw new Exception(failedToCreateUser);
		}
	  	
		session.close(); 
		logger.debug("exiting " + methodName);
	}
}
