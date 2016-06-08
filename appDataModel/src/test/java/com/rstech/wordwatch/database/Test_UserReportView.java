package com.rstech.wordwatch.database;


import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase; 
import org.junit.BeforeClass;
import org.junit.Test;

import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.WDUserReportManager;
import com.rstech.wordwatch.business.domain.WordReportManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WordReport;

public class Test_UserReportView  extends TestCase {
	
	 
		
	 
	
	@Test	
	public void testIt() {
		String                         testClientName = "WDTest7";
		RSClient                       theClient = null;
		RSClientManager                mgr = new RSClientManager();
		
		String                         testUserName = "WDUser7";
		RSUser                         theUser = null;
		RSUserManager                  userMgr = new RSUserManager();
		
		String                         testReportTitle = "Derka1test";
		WordReport                     theReport = null;
		WordReportManager              rptMgr = new WordReportManager();
		
		WDUserReportManager            userReportMgr = new WDUserReportManager();
		WDUserReport                   userReport = null; 
		
		SetupStandaloneContext.setup(); 
		
		String                         cp = System.getProperty("java.class.path");
		System.out.println("CLASSPATH=" + cp);
		theClient = mgr.getClientByClientName(testClientName);
		if (theClient == null) {
			theClient = mgr.createNewClient(testClientName, "Poopsicle2", "", "", "", "", "", "");
		}
		assertNotNull(theClient);
		if (theClient != null) 
		{
			theUser = userMgr.getFirstUserByLogin(testUserName);
			if (theUser == null) {
				theUser = userMgr.createNewUser(theClient, testUserName, "Blabbamowf2", "Poopsikins2", "qwerty", "", "", "", "", "", "", "", "", "", "", "", RSUserManager.getRegularUserType());	
			} 
		}	
		assertNotNull(theUser); 
		
		Date now = new Date(Calendar.getInstance().getTime().getTime());
		List <WordReport>          rpts = rptMgr.getReportsByTitle(testReportTitle);
		if (rpts == null || rpts.size() == 0) {
			theReport = rptMgr.createNewWordReport(null, theUser.getRS_CLIENT(), "None", testReportTitle, "", "", new Long(1), new Long(1), now, now, now, "", "", null, "", "", "", "", "TestRptURL");				
		} else {
			theReport = (WordReport) rpts.get(0); 
		}
		assertNotNull(theReport);
		if (theReport != null) {
			userReport = userReportMgr.addReportToUser(theUser, theReport, "Y"); 
		}
		assertNotNull(userReport);
	}

}
