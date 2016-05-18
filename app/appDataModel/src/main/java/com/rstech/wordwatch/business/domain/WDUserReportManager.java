package com.rstech.wordwatch.business.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

 
import com.rstech.utility.RSWord;
import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WDUserReportExample;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportEntry;
import com.rstech.wordwatch.dao.WordReportExample;
import com.rstech.wordwatch.dao.mapper.WDUserReportMapper;
import com.rstech.wordwatch.dao.mapper.WordReportEntryMapper;
import com.rstech.wordwatch.dao.mapper.WordReportMapper;
import com.rstech.wordwatch.database.SQLConnection;

public class WDUserReportManager {
	
	private static final Class thisClass = WDUserReportManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	public static final Integer OneMillion = 1000000;

	// use this constant to define how to create a report title 
	private static final String STRING_URL_TITLE = "STRING_URL_TITLE"; 
	
	/**
	 * Use the passed in user and suggested title to create the report title. Usually I think the
	 * report title should include the underlying URL for the page the reader was navigating to.
	 * 
	 * @param aUser
	 * @param suggestedTitlePart
	 * @param tag
	 * @return
	 */
	public static String CreateNewReportTitle(RSUser aUser, String suggestedTitlePart, String tag) {
		String methodName = "CreateNewReportTitle";
		logger.debug("entering " + methodName);
		
		String result = "";
		
		if (tag != null && tag.equals(STRING_URL_TITLE)) {
			result = aUser.getID() + "$" + suggestedTitlePart;
		}
		logger.debug("exiting " + methodName);
		return result;
		
	}
	
	/**
	 * Retrieve the report by the specified user - using the login name and url.
	 * If there is no such report by the user, this function actually adds a new report 
	 * for the user based on its ID and URL.
	 * 
	 * The user must exist in the first place!! If not, then return null. 
	 * 
	 * This is the interface to the outside world for
	 *  
	 * users to add reports to their reports.
	 * 
	 * @param userLoginName
	 * @param url addWordToReport(List<RSWord> words, WordReport aReport)
	 * @return a WDUserReport
	 */
	public static WDUserReport retrieveReportByUserAndURL(String userLoginName, String url, String title)
	{
		String methodName = "retrieveReportByUserAndURL";
		logger.debug("entering " + methodName);
		
		List<WordReport>             rpts = null; 
		WDUserReport                   theUserReport = null;
		RSUserManager                  userMgr = new RSUserManager();
		RSUser                         theUser = null; 
		WordReport                     theReport = null;
		WordReportManager              rptMgr = new WordReportManager();
		
		theUser = userMgr.getFirstUserByLogin(userLoginName);
		if (theUser != null) { 
			theUserReport = getUserReportList(theUser.getID(), url);  
			if (theUserReport == null) 
			{
				Long monthsToOrder = new Long(10);
				Long reportsToRun = new Long(10);
				Date now = new Date(Calendar.getInstance().getTime().getTime()); 
				
			    theReport = rptMgr.createNewWordReport(null, theUser.getRS_CLIENT(), "None", 
			    		title, "test_order", "", monthsToOrder, reportsToRun, now, now, now, "", "", null, "", "", "", "", url);				
				 
				if (theReport != null) {
					theUserReport = addReportToUser(theUser, theReport, "Y");
				}
			}  
		}
		logger.debug("exiting " + methodName);
		
		return theUserReport;
	}
	 
	/**
	 * As of 9/24/2011, I don't have any word image url in the database yet.
	 * This is a simple version of addWordToReport as a proof of concept.
	 * @param words
	 * @param aReport
	 * @return
	 * 
	 */
	public static List<WordReportEntry> addWordToReport(List<RSWord> words, WordReport aReport)
	{
		String methodName = "addWordToReport";
		logger.debug("entering " + methodName);
		
		Hashtable<String, RSWord>        table = new Hashtable<String, RSWord>();
		List<String>                   keys = new ArrayList<String>();
		List<WordReportEntry>          existList = null;
		List<WordReportEntry>          resultList = null;
		
		for (RSWord each : words) {
			table.put(each.getWordName(), each);
			keys.add(each.getWordName());
		}
		
		existList = WordReportEntryManager.findWordReportEntryFromList(keys, aReport); 
		Hashtable<String, String>      table2 = new Hashtable<String, String>();
		
		// If there are nothing in the existing list, then just start adding the words.
		// only add the ones that are not in the list
		for (WordReportEntry eachEntry : existList) {
			table2.put(eachEntry.getWORD(), eachEntry.getWORD());
		}
		
		for (RSWord each : words) {
			String wordName = table2.get(each.getWordName());
			if (wordName == null) {   // this word is not in the databse, add it
				WordReportEntry newEntry = null;
				/** TODO
				 * Add more columns to the WordReportEntry table to allow image URL.
				 */
				newEntry = WordReportEntryManager.createNewEntryLess(aReport.getID(), each);
				if (newEntry != null)
				{
					if (resultList == null) {
						resultList = new ArrayList<WordReportEntry>();
					}
					resultList.add(newEntry);
				} 
			}
		}
		logger.debug("exiting " + methodName);
		return resultList;
	} 
	
	
	/**
	 * Associate the report to the user.
	 * @param user
	 * @param report
	 * @param isReadOnly
	 * @return
	 */
	public static WDUserReport addReportToUser(RSUser aRSUser, WordReport aWDReport, String isReadOnly)
	{
		String methodName = "addReportToUser";
		logger.debug("entering " + methodName);
		
		WDUserReport                   wdUserReport = null;
		List<WDUserReport>             rpts = null; 

		wdUserReport = getUserReportList(aRSUser.getID(), aWDReport.getRPT_URL());  
		if (wdUserReport == null) 
		{
			wdUserReport = addReportToUser(aRSUser.getID(), aWDReport.getID(), isReadOnly);		
		} 
		else
		{
			rpts = getUserReportList(aRSUser.getID());
			for (WDUserReport each : rpts) {
				if (each.getWD_REPORT() != null) {
					if (each.getWD_REPORT().equals(aWDReport.getID())) {
						wdUserReport = each;
						return wdUserReport;
					}
				}
			}
		}
		logger.debug("exiting " + methodName);
		
		return wdUserReport;
	}
	
	/**
	 * Associate the report to the user.
	 * @param user
	 * @param report
	 * @param isReadOnly
	 * @return
	 */
	public static WDUserReport addReportToUser(Long aRSUser, Long aWDReport, String isReadOnly)
	{
		String methodName = "addReportToUser";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		WDUserReport wdUserReport = null;

		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WDUserReportMapper dao = session.getMapper(WDUserReportMapper.class);
			wdUserReport = new WDUserReport();
			wdUserReport.setRS_USER(aRSUser);
			wdUserReport.setWD_REPORT(aWDReport);
			wdUserReport.setIS_READ_ONLY(isReadOnly);  
			dao.insert(wdUserReport);
			logger.debug("createNewReport successfully called, new WD_REPORT id = " + wdUserReport.getID());
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return wdUserReport;
	}
	
	public WDUserReport updateExistingUserReport(WDUserReport wdUserReport, Long user, Long report, String isReadOnly, Long id, Long version) 
	{
		String methodName = "updateExistingUserReport";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WDUserReportMapper dao = session.getMapper(WDUserReportMapper.class); 
			WDUserReportExample ex = new WDUserReportExample();
			wdUserReport.setRS_USER(user);
			wdUserReport.setID(id);
			wdUserReport.setIS_READ_ONLY(isReadOnly);
			wdUserReport.setWD_REPORT(report);
			wdUserReport.setVERSION(version.intValue());
			dao.updateByExample(wdUserReport, ex);
			logger.debug("updateExistingUser successfully called");
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return wdUserReport;
	}
	
	/**
	 * Return the list of WordReport associated with the given userID and title.
	 * 
	 * 12/01/2012 - Each user should just have one report for the given title (url).
	 * 
	 * @param userID
	 * @return
	 */
	
	public static WDUserReport getUserReportList(Long userID, String url) 
	{
		String methodName = "getUserReportList";
		logger.debug("entering " + methodName);
		
		SqlSession session = null; 
		List<WDUserReport> rpts = null;
		WDUserReport theWordUserReport = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			rpts = getUserReportList(userID); 
			WordReportMapper daoReport = session.getMapper(WordReportMapper.class);
			WordReportExample exReport = new WordReportExample();
			com.rstech.wordwatch.dao.WordReportExample.Criteria rptCriteria = exReport.createCriteria();
			rptCriteria.andRPT_URLEqualTo(url);
			List<WordReport> theReports = daoReport.selectByExample(exReport); 
			
			if (rpts != null && rpts.size() > 0) {
				for (WordReport each : theReports) {
					for (WDUserReport aWdUserRpt : rpts) {
						if (each.getID().equals(aWdUserRpt.getWD_REPORT())) {
							theWordUserReport = aWdUserRpt;
							break;
						}
					} 
				}
			} 
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return theWordUserReport;
	}
	
	
	public static List<WDUserReport> getUserReportList(Long userID) 
	{
		String methodName = "getUserReportList";
		logger.debug("entering " + methodName);
		
		SqlSession session = null; 
		List<WDUserReport> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WDUserReportMapper dao = session.getMapper(WDUserReportMapper.class);
			WDUserReportExample ex = new WDUserReportExample();
			com.rstech.wordwatch.dao.WDUserReportExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andRS_USEREqualTo(userID); 
			rpts = dao.selectByExample(ex); 
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return rpts;
	}
	
	/**
	 * Return a list of WordReport by the user in the provide last given days.
	 * 
	 * @param userID
	 * @param daysFromToday
	 * @return
	 */
	public static List<WordReport> getUserReportListInThePast(Long userID, Integer daysFromToday) 
	{
		String methodName = "getUserReportListInThePast";
		logger.debug("entering " + methodName);
		
		SqlSession session = null; 
		List<WDUserReport> rpts = null;
		List<WordReport> actualReports = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WDUserReportMapper dao = session.getMapper(WDUserReportMapper.class);
			WDUserReportExample ex = new WDUserReportExample();
			com.rstech.wordwatch.dao.WDUserReportExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andRS_USEREqualTo(userID);
			rpts = dao.selectByExample(ex);
			if (rpts != null && rpts.size() > 0) {
				WordReportMapper reportDAO = session.getMapper(WordReportMapper.class);
				WordReportExample exRpt = new WordReportExample();
				com.rstech.wordwatch.dao.WordReportExample.Criteria rptCriteria = exRpt.createCriteria();
				ArrayList<Long> subClauseIds = new ArrayList<Long>();
				for (WDUserReport each: rpts) {
					subClauseIds.add(each.getWD_REPORT());
				}
				rptCriteria.andIDIn(subClauseIds);
				Date now = new Date(Calendar.getInstance().getTime().getTime());
				long milliseconds = (long) daysFromToday * 86400000;
				long before = now.getTime() - milliseconds;
				Date theOtherDate = new Date(before);
				rptCriteria.andFIRST_RUN_DATEBetween(theOtherDate, now);
				actualReports = reportDAO.selectByExample(exRpt);
			}
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return actualReports;
	}
	
	/**
	 * Based on the last few reports by the user, randomly select one and return it.
	 * 
	 * @param userID
	 * @param daysFromToday
	 * @return
	 */
	public static WordReport getOneRandomSampleFromUserReportListInThePast(Long userID, Integer daysFromToday) 
	{
		WordReport theReport = null;
		List<WordReport> reports = getUserReportListInThePast(userID, daysFromToday);
		if (reports != null && reports.size() > 0) {
			Random rnd = new Random();
			Integer aNumber = rnd.nextInt(OneMillion);
			int pos = aNumber % reports.size();
			theReport = reports.get(pos); 
		}
		return theReport;
	}
}
