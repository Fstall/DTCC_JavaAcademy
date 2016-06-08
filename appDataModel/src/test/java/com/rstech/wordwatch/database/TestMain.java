package com.rstech.wordwatch.database;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

  
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.rstech.utility.RSWord;
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
import com.rstech.wordwatch.dao.util.ReportAndReportEntryMapper.ReportWithReportEntry; 

public class TestMain {
	
	private static void initRecord(WordReport record) {
		record.setDESCRIPTION("test"); 
		Long bd = new Long(1);
		record.setTITLE("Paradigm");
		record.setRS_CLIENT(bd);
		record.setREPORT_TYPE("test"); 
		record.setVERSION(bd.intValue()); 
		
		
	}
	
	public static Map<WordReport, List<WordReportEntry>> selectReportWithReportEntry() throws IOException {
        SqlSession sqlSession = SQLConnection.getSessionFactory().openSession(true);
        try {
                class MyResultHandler implements ResultHandler {
                        Map<WordReport, List<WordReportEntry>> reportWithReportEntry = new HashMap<WordReport, List<WordReportEntry>>();
                                //@Override
                                public void handleResult(ResultContext context) {
                                        final ReportWithReportEntry complex = (ReportWithReportEntry)context.getResultObject();
                                        if (!reportWithReportEntry.containsKey(complex.report)) {
                                        	reportWithReportEntry.put(complex.report, new ArrayList<WordReportEntry>());
                                        }
                                        reportWithReportEntry.get(complex.report).add(complex.entry);
                                }
								 
                };
                MyResultHandler handler = new MyResultHandler();
                Long bd = new Long(10);
                sqlSession.select("com.rstech.wordwatch.dao.util.ReportAndReportEntryMapper.selectReportEntryByReportID", bd, handler);
                
                return handler.reportWithReportEntry;
        } finally {
                sqlSession.close();
        }
    }
	 
	
	public static void setup() {
		SetupStandaloneContext.setup(); 
	}
	
	
	  public static void main(String argv[]) throws IOException {
		SqlSession session = null;
		String clientName = "RS Tech";
		String testUserId = "tchou";
		
		setup();
		session = SQLConnection.getSessionFactory().openSession(true);
		System.out.println("ReceiveListd Session Object : " + session);
		
		RSUserManager.initDomainTable(); // set up some domain tables
		
		RSClientManager mgr = new RSClientManager();
		RSClient aClient = mgr.getClientByClientName( clientName);
		if (aClient == null) {
			aClient = mgr.createNewClient("RS Tech", "RS Tech", "123 ABC Street", "", "TestCity", "OR", "98330", "USA");	
		}
		
		RSUserManager userMgr = new RSUserManager();
		RSUser aUser = userMgr.getFirstUserByLogin(testUserId);
		
		if (aUser == null) {
			try {
				aUser = RSUserManager.createNewUserUnderSystem("tchou", "test_919", RSUserManager.getDeveloperUserType(), null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		// test whether you can create a new user
		try {
			RSUserManager.createNewUserUnderSystem("tchou", "testpass", RSUserManager.getRegularUserType(), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// test whether you can create a new user
		try {
			RSUserManager.createNewUserUnderSystem("tonychoud@aol.com", "te!st1pass", RSUserManager.getDeveloperUserType(), aUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//   create a new student 
		try {
			RSUserManager.createNewUserUnderSystem("mdong", "test_798", RSUserManager.getRegularUserType(), aUser );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//   create a new student 
		try {
			RSUserManager.createNewUserUnderSystem("fchou", "test_465", RSUserManager.getRegularUserType(), aUser );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<RSUser> users = userMgr.getUserByLoginAndPassword(testUserId, "test_919");
		for (RSUser each : users) {
			System.out.print(each);
			RSUserManager.registerIfCanSearch(each, RSUserManager.LONGMAN_SEARCH_TYPE);
		}  
		
		
		if (users != null) { 
			aUser = (RSUser) users.get(0);
			WordReportEntry entry = WordReportEntryManager.getRandomWordReportEntry(aUser.getID(), 200);
			if (entry != null) {
				List<WordReportEntryAux> auxData = WordReportEntryManager.retrieveAllKeyRecords(entry.getID());
				for (WordReportEntryAux each: auxData) {
					System.out.println(each.getAUX_KEY());
					System.out.println(each.getAUX_VALUE());
				}
			} 
		}
		
		
		 
		List<WDUserReport> userReports = null;
		WDUserReport userReport = null;
		String          testURL = "abc.com";
		
		if (aUser != null) {
			// This function will create a report for the user if one is not found.
			userReport = WDUserReportManager.getUserReportList(aUser.getID(), testURL); 
			
			userReports = WDUserReportManager.getUserReportList(aUser.getID());
			
			
		} else {
			throw new RuntimeException("userReport must not be null!");
		}
		
		
		
		for (WDUserReport eachReport : userReports) {
			System.out.println("getID" + eachReport.getID());
			System.out.println("getIS_READ_ONLY" + eachReport.getIS_READ_ONLY());
			System.out.println("getRS_USER" + eachReport.getRS_USER());
			System.out.println("getVERSION" + eachReport.getVERSION());
			System.out.println("getWD_REPORT" + eachReport.getWD_REPORT());
		
		}
		
		for (int count = 0; count < userReports.size(); count++) {
			WDUserReport eachReport = userReports.get(count);
			System.out.println("getID" + eachReport.getID());
			System.out.println("getIS_READ_ONLY" + eachReport.getIS_READ_ONLY());
			System.out.println("getRS_USER" + eachReport.getRS_USER());
			System.out.println("getVERSION" + eachReport.getVERSION());
			System.out.println("getWD_REPORT" + eachReport.getWD_REPORT());
		}
		
		 
		
		 
		
		
		
		// userReport should not be null 
			
		/*  Old test code to create a new report.
		 * 
		  	WordReportMapper dao = session.getMapper(WordReportMapper.class);
		 	WordReport record = new WordReport();
			record.setDESCRIPTION("test");  
			record.setTITLE("Paradigm");
			record.setRS_CLIENT(aClient.getID());
			record.setREPORT_TYPE("test");
			record.setORDER_ID("1234"); 
			dao.insert(record);
		*/
		
		ArrayList<String> words = new ArrayList<String>();
		
		words.add("paradigm");
		words.add("orchestra");
		WordReport aReport = WordReportManager.getReportByID(userReport.getWD_REPORT());
		
		List<WordReportEntry> entryList = WordReportEntryManager.findWordReportEntryFromList(words, aReport);
 
		 
		if (entryList != null && entryList.size() > 0) {
			for (WordReportEntry each : entryList) {
				int i = 0;
				System.out.println("WordReportEntry: " + each);
				WordReportEntryManager.createAuxRecord(each.getID(),
						"ttttttestttttt" + i, WordReportEntryManager.USER_DEF_KEY_CODE);
				List<WordReportEntryAux> list = 
				WordReportEntryManager.retrieveAllKeyRecords(each.getID());
				for (WordReportEntryAux auxRecord : list) {
					System.out.println(auxRecord.getWD_REPORT_ENTRY());
					System.out.println(auxRecord.getAUX_VALUE());
				}
				i++;
			}
		} else {
			ArrayList<RSWord> list = new ArrayList<RSWord>();
			
			RSWord aWord = new RSWord();
			aWord.setWordName("Test 1 2 3");
			list.add(aWord);
			aWord = new RSWord();
			aWord.setWordName("paradigm");
			list.add(aWord);
			
			WDUserReportManager.addWordToReport(list, aReport);
		}
		
		/*
		 * Old test code.
		 * 
		WordReportEntryMapper entryDao = session.getMapper(WordReportEntryMapper.class);
		WordReportEntry entry = new WordReportEntry();
		entry.setWD_REPORT(record.getID());
		entry.setWORD("Paradiam");
		entry.setDEFINITION("This is just a unit test program... ");
		entryDao.insert(entry);
		entry = new WordReportEntry();
		entry.setWD_REPORT(record.getID());
		entryDao.insert(entry);
		
		session.commit();
		*/
		
		/*
		System.out.println("The new ID of the record just inserted = " + record.getID());
		WordReportExample ex = new WordReportExample();
		Criteria aCriteria = ex.createCriteria();
		Long bd = new Long(9);
		aCriteria.andIDEqualTo(bd);
		List<WordReport> rpts = dao.selectByExample(ex);
		System.out.println("List size = " + rpts.size());
		
		*/
		//selectReportWithReportEntry();
		session.close(); 
	}
}
