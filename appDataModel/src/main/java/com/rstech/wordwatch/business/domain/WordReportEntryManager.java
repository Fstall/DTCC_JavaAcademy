package com.rstech.wordwatch.business.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
 
import com.rstech.utility.RSWord;
import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.dao.RSClient; 
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.RSUserExample;
import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WDUserReportExample;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportEntry;
import com.rstech.wordwatch.dao.WordReportEntryAux;
import com.rstech.wordwatch.dao.WordReportEntryAuxExample;
import com.rstech.wordwatch.dao.WordReportEntryExample;
import com.rstech.wordwatch.dao.WordReportExample;
import com.rstech.wordwatch.dao.mapper.WDUserReportMapper;
import com.rstech.wordwatch.dao.mapper.WordReportEntryMapper;
import com.rstech.wordwatch.dao.mapper.WordReportEntryAuxMapper;
import com.rstech.wordwatch.dao.mapper.WordReportMapper;
import com.rstech.wordwatch.database.SQLConnection;

public class WordReportEntryManager {
	
	private static final Class thisClass = WordReportEntryManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	@SuppressWarnings("null")
	public WordReportEntry createNewWordReportEntry(Long wordReport, String wordNumber, String isPublished, String isDeleted,
													String status, String word, Long id, Long version, Date entryPublishedDate,
													String statusCode, String nextPublishedDate, String lastStatusCode, String nextStatusCode,
													Date dateCompleted, Date dateDeleted, String userEntered)
	{
		String methodName = "createNewwordReportEntry";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		WordReportEntry wordReportEntry = null;

		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryMapper dao = session.getMapper(WordReportEntryMapper.class);
			wordReportEntry = new WordReportEntry();
			wordReportEntry.setWD_REPORT(wordReport);
			wordReportEntry.setWD_NUMBER(wordNumber); 
			wordReportEntry.setIS_DELETED(isDeleted);
			wordReportEntry.setSTATUS(status);
			wordReportEntry.setWORD(word);
			wordReportEntry.setID(id);
			wordReportEntry.setVERSION(version.intValue()); 
			wordReportEntry.setDATE_COMPLETED(dateCompleted);
			wordReportEntry.setDATE_DELETED(dateDeleted);
			wordReportEntry.setUSER_ENTERED(userEntered);
			dao.insert(wordReportEntry);
			logger.debug("createNewWordReportEntry successfully called, new WD_REPORT_ENTRY id = " + wordReportEntry.getID());
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordReportEntry;
	}
	
	public WordReportEntry updateExistingWordReportEntry(WordReportEntry wordReportEntry, Long wordReport, String wordNumber, String isPublished, String isDeleted,
			String status, String word, Long id, Long version, Date entryPublishedDate,
			String statusCode, String nextPublishedDate, String lastStatusCode, String nextStatusCode,
			Date dateCompleted, Date dateDeleted, String userEntered)
	{
		String methodName = "updateExistingWordReportEntry";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryMapper dao = session.getMapper(WordReportEntryMapper.class); 
			WordReportEntryExample ex = new WordReportEntryExample();
			com.rstech.wordwatch.dao.WordReportEntryExample.Criteria aCriteria = ex.createCriteria();
			wordReportEntry.setWD_REPORT(wordReport);
			wordReportEntry.setWD_NUMBER(wordNumber); 
			wordReportEntry.setIS_DELETED(isDeleted);
			wordReportEntry.setSTATUS(status);
			wordReportEntry.setWORD(word);
			wordReportEntry.setID(id);
			wordReportEntry.setVERSION(version.intValue()); 
			wordReportEntry.setDATE_COMPLETED(dateCompleted);
			wordReportEntry.setDATE_DELETED(dateDeleted);
			wordReportEntry.setUSER_ENTERED(userEntered);
			dao.updateByExample(wordReportEntry, ex);
			logger.debug("updateExistingWordReportEntry successfully called");
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordReportEntry;
	}
	
	public static WordReportEntry updatePublishDate(WordReportEntry wordReportEntry, Date entryPublishedDate) 
	{
		String methodName = "updatePublishDate";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryMapper dao = session.getMapper(WordReportEntryMapper.class); 
			WordReportEntryExample ex = new WordReportEntryExample();            
			com.rstech.wordwatch.dao.WordReportEntryExample.Criteria aCriteria = ex.createCriteria();
		    aCriteria.andIDEqualTo(wordReportEntry.getID()); 
			dao.updateByExample(wordReportEntry, ex);
			logger.debug("updateExistingWordReportEntry successfully called");
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordReportEntry;
	}
	
	
	/**
	 * To add a word into the report - just as a test!!!!!!
	 * A more full function should be put in here.
	 * @param wordReport
	 * @param word
	 * @return
	 */
	public static WordReportEntry createNewEntryLess(Long wordReport, RSWord word) 
	{
		String methodName = "createNewEntryLess";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		WordReportEntry wordReportEntry = null;
		String          def = "", imageUrl = "";
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryMapper dao = session.getMapper(WordReportEntryMapper.class);  
			wordReportEntry = new WordReportEntry();
			
			String 			audioURL;
			String			sentence;
			  
			wordReportEntry.setWD_REPORT(wordReport);
			wordReportEntry.setWORD(word.getWordName()); 
			imageUrl = word.getImageURL();
			wordReportEntry.setIMG_URL1(RSWord.getImageName(imageUrl));
			wordReportEntry.setIMG_URL2(imageUrl);
			wordReportEntry.setIMG_URL3(imageUrl);
			
			Calendar calendar = Calendar.getInstance();
			java.util.Date currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime()); 
			
			if (word.getDefinitions() != null && word.getDefinitions().size() > 0) {
				def = word.getDefinitions().get(0);
				wordReportEntry.setDEFINITION(def);
			}
			dao.insert(wordReportEntry);
			session.commit();
			WordReportEntryManager.createAuxRecord(wordReportEntry.getID(), word.getSentence(), 
					WordReportEntryManager.SENTENCE_KEY_CODE);
			ArrayList<String> sampleSentenceList = word.getSampleSentences();
			if (sampleSentenceList != null) {
				for (String each : sampleSentenceList) {
					WordReportEntryManager.createAuxRecord(wordReportEntry.getID(), each, 
							WordReportEntryManager.SAMPLE_SENTENCE_KEY_CODE);
				}
			}
			
			ArrayList<String> chineseList = word.getChinesDefinitions();
			if (chineseList != null) {
				for (String each : chineseList) {
					WordReportEntryManager.createAuxRecord(wordReportEntry.getID(), each, 
							WordReportEntryManager.CHINESE_DEF_KEY_CODE);
				}
			}
			logger.debug("createNewEntryLess successfully called, new WD_REPORT_ENTRY id = " + wordReportEntry.getID());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordReportEntry;
	}
	
	
	/**
	 * Find the list of report entries equal to aWord.
	 * 
	 * @param aWord
	 * @param theReport
	 * @return
	 */
	public List<WordReportEntry> findWordReportEntryFromWord(String aWord, WordReport theReport) 
	{
		String methodName = "findWordReportEntryFromWord";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<WordReportEntry> wordEntryList = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryMapper dao = session.getMapper(WordReportEntryMapper.class); 
			WordReportEntryExample ex = new WordReportEntryExample(); 
			com.rstech.wordwatch.dao.WordReportEntryExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andWD_REPORTEqualTo(theReport.getID());
			aCriteria.andWORDEqualTo(aWord);
			wordEntryList = dao.selectByExample(ex);
			logger.debug("findWordReportEntryFromWord successfully called");
			session.commit(); 
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordEntryList;
	}
	
	/**
	 * Find the list of report entries containing the word list.
	 * 
	 * @param aWord
	 * @param theReport
	 * @return
	 */
	public static List<WordReportEntry> findWordReportEntryFromList(List <String> wordList, WordReport theReport) 
	{
		String methodName = "findWordReportEntryFromList";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<WordReportEntry> wordEntryList = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryMapper dao = session.getMapper(WordReportEntryMapper.class); 
			WordReportEntryExample ex = new WordReportEntryExample(); 
			com.rstech.wordwatch.dao.WordReportEntryExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andWD_REPORTEqualTo(theReport.getID());
			aCriteria.andWORDIn(wordList);
			wordEntryList = dao.selectByExample(ex);
			logger.debug("findWordReportEntryFromList successfully called");
			session.commit(); 
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordEntryList;
	}
	
	public static List<WordReportEntry> getWordReportEntry(WordReport theReport) {
		String methodName = "getWordReportEntry";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<WordReportEntry> wordEntryList = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryMapper dao = session.getMapper(WordReportEntryMapper.class); 
			WordReportEntryExample ex = new WordReportEntryExample(); 
			com.rstech.wordwatch.dao.WordReportEntryExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andWD_REPORTEqualTo(theReport.getID()); 
			wordEntryList = dao.selectByExample(ex);
			session.commit();
			logger.debug("getWordReportEntry successfully called");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordEntryList;
	}
	
	/**
	 * *************************************************
	 * I need more report entry related records - put them 
	 * into the word_rpt_entry_aux table.
	 * *************************************************
	 */
	
	public static final String SENTENCE_KEY_CODE = "SEN";
	public static final String DEFINITION_KEY_CODE = "DEF";
	public static final String SAMPLE_SENTENCE_KEY_CODE = "SAMP";
	public static final String CHINESE_DEF_KEY_CODE = "CHN";
	public static final String USER_DEF_KEY_CODE = "USER";
	 
	private static List<WordReportEntryAux> retrieveKeyRecords(Long reportEntryID, String keyCode) {
		String methodName = "retrieveKeyRecords";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<WordReportEntryAux> wordEntryAuxList = null, result = null;
		
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryAuxMapper dao = session.getMapper(WordReportEntryAuxMapper.class);  
		
			WordReportEntryAuxExample ex = new WordReportEntryAuxExample();
			com.rstech.wordwatch.dao.WordReportEntryAuxExample.Criteria aCriteria = ex.createCriteria();
			
			aCriteria.andWD_REPORT_ENTRYEqualTo(reportEntryID);
			aCriteria.andAUX_KEYLike(keyCode + "%");
			ex.setOrderByClause("AUX_KEY");
			wordEntryAuxList = dao.selectByExample(ex);
			result = wordEntryAuxList;
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return result;
	}
	
	/**
	 * Insert a sample sentence associated with the report entry.
	 * @param reportEntryID
	 * @param sentence
	 * @return
	 */
	public static WordReportEntryAux createAuxRecord(Long reportEntryID, String sentence, String keyCode) {
		String methodName = "createAuxRecord";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<WordReportEntryAux> list = retrieveKeyRecords(reportEntryID, keyCode);
		String keyValue = "";
		if (list == null) {
			keyValue = keyCode + 1;
		} else {
			keyValue = keyCode + (list.size() + 1);
		}
		
		WordReportEntryAux auxRecord = null;
		
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryAuxMapper dao = session.getMapper(WordReportEntryAuxMapper.class);  
			auxRecord = new WordReportEntryAux();
			auxRecord.setWD_REPORT_ENTRY(reportEntryID);
			auxRecord.setAUX_KEY(keyValue);
			auxRecord.setAUX_VALUE(sentence);
			dao.insert(auxRecord);
			logger.debug("createSampleSentence successfully called, new WD_REPORT_ENTRY_AUX id = " + auxRecord.getID());
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return auxRecord;
	}
	
	public static List<WordReportEntryAux> retrieveAllKeyRecords(Long reportEntryID) {
		String methodName = "retrieveAllKeyRecords";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<WordReportEntryAux> wordEntryAuxList = null, result = null;
		
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportEntryAuxMapper dao = session.getMapper(WordReportEntryAuxMapper.class);  
		
			WordReportEntryAuxExample ex = new WordReportEntryAuxExample();
			com.rstech.wordwatch.dao.WordReportEntryAuxExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andWD_REPORT_ENTRYEqualTo(reportEntryID);
			//ex.setOrderByClause("AUX_KEY");
			wordEntryAuxList = dao.selectByExample(ex);
			result = wordEntryAuxList;
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return result;
	}
	
	public static WordReportEntry getRandomWordReportEntry(Long userID, Integer daysFromToday) {
		WordReport aReport = WDUserReportManager.getOneRandomSampleFromUserReportListInThePast(userID, daysFromToday);
		WordReportEntry theEntry = null;
		if (aReport != null) {
			List<WordReportEntry> entries = getWordReportEntry(aReport);
			if (entries != null && entries.size() >0) {
				Random rnd = new Random();
				int pos = rnd.nextInt(WDUserReportManager.OneMillion);
				pos = pos % entries.size();
				theEntry = entries.get(pos);
			} 
		} 
		return theEntry;
	}
	
		
}
