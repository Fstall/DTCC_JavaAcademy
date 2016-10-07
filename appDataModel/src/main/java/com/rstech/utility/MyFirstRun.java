package com.rstech.utility;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

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
import com.rstech.wordwatch.database.SQLConnection;

public class MyFirstRun   {
	
		
	public static void main(String argss[]) {
				SqlSession session = null;
				String clientName = "Kim Tech";
				String testUserId = "ckim5";
				
				SetupStandaloneContext.setup(); 
				session = SQLConnection.getSessionFactory().openSession(true);
				System.out.println("ReceiveListd Session Object : " + session);
				
				try {
					RSUserManager.initDomainTable(); // set up some domain tables	
				} catch (Exception e) {					
					e.printStackTrace();					
				}
				
				
				RSClientManager mgr = new RSClientManager();
				RSClient aClient = mgr.createNewClient(clientName, clientName, "456 Jones Way", "", "Hillsboro", "OR", "98455", "USA");	
				aClient = mgr.getClientByClientName(clientName);
				if (aClient == null) {
					aClient = mgr.createNewClient("RS Tech", "RS Tech", "123 ABC Street", "", "TestCity", "OR", "98330", "USA");	
				}
				
				RSUserManager userMgr = new RSUserManager();
				RSUser aUser = userMgr.getFirstUserByLogin(testUserId);
				
				if (aUser == null) {
					try {
						aUser = RSUserManager.createNewUserUnderSystem("tchou", "test_919", RSUserManager.getDeveloperUserType(), null);
//						aUser = RSUserManager.createNewUserUnderSystem("tchou", "test_919", RSUserManager.getDeveloperUserType(), null, clientName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				
				// test whether you can create a new user
				try {
//					RSUserManager.createNewUserUnderSystem("dchou", "test$pass2", RSUserManager.getRegularUserType(), null, clientName);
					RSUserManager.createNewUserUnderSystem("dchou", "test$pass2", RSUserManager.getRegularUserType(), null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// test whether you can create a new user
				try {
//					RSUserManager.createNewUserUnderSystem("tonychoud@aol.com", "te!st1pass", RSUserManager.getDeveloperUserType(), aUser, clientName);
					RSUserManager.createNewUserUnderSystem("tonychoud@aol.com", "te!st1pass", RSUserManager.getDeveloperUserType(), aUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//   test whether you can create a new user
				try {
//					RSUserManager.createNewUserUnderSystem("mdong", "test_798", RSUserManager.getRegularUserType(), aUser, clientName);
					RSUserManager.createNewUserUnderSystem("mdong", "test_798", RSUserManager.getRegularUserType(), aUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//   test whether you can create a new user
				try {
//					RSUserManager.createNewUserUnderSystem("fchou", "test_465", RSUserManager.getRegularUserType(), aUser , clientName);
					RSUserManager.createNewUserUnderSystem("fchou", "test_465", RSUserManager.getRegularUserType(), aUser);
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
				try {
					
					
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
				}
				catch (Exception e) {
					// ignore
					e.printStackTrace();
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

