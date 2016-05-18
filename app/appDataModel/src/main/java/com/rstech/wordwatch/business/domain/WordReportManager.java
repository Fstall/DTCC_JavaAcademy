package com.rstech.wordwatch.business.domain;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportExample;
import com.rstech.wordwatch.dao.mapper.WordReportMapper;
import com.rstech.wordwatch.database.SQLConnection;

public class WordReportManager {
	
	private static final Class thisClass = WordReportManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	public WordReport createNewWordReport(Long id, Long rsClient, String reportType, String title,
											String orderID, String status, Long monthsOrdered, Long reportsRun,
											Date firstRunDate, Date lastRunDate, Date expirationDate,
											String description, String isDeleted, Long version, String isExpired,
											String isTrial, String frequency, String isAutoPublished, String rptURL)
	{
		String methodName = "createNewWordReport";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		WordReport wordReport = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportMapper dao = session.getMapper(WordReportMapper.class);
			wordReport = new WordReport();
			if (id != null)
			  wordReport.setID(id);
			wordReport.setRS_CLIENT(rsClient);
			wordReport.setREPORT_TYPE(reportType);
			wordReport.setTITLE(title); 
			wordReport.setSTATUS(status); 
			wordReport.setREPORTS_RUN(reportsRun.shortValue());
			wordReport.setFIRST_RUN_DATE(firstRunDate);
			wordReport.setLAST_RUN_DATE(lastRunDate);
			wordReport.setEXPIRATION_DATE(expirationDate);
			wordReport.setDESCRIPTION(description);
			wordReport.setIS_DELETED(isDeleted);
			wordReport.setIS_TRIAL(isTrial);
			wordReport.setFREQUENCY(frequency);
			wordReport.setIS_AUTO_PUBLISH(isAutoPublished);
			wordReport.setRPT_URL(rptURL);
			dao.insert(wordReport);
			logger.debug("createNewWordReport successfully called, new WD_REPORT id = " + wordReport.getID());
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordReport;
	}
	
	public WordReport updateExistingWordReport(WordReport wordReport, Long id, Long rsClient, String reportType, String title,
			String orderID, String status, Long monthsOrdered, Long reportsRun,
			Date firstRunDate, Date lastRunDate, Date expirationDate,
			String description, String isDeleted, Long version, String isExpired,
			String isTrial, String frequency, String isAutoPublished, String aURL)
	{
		String methodName = "updateExistingWordReport";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportMapper dao = session.getMapper(WordReportMapper.class); 
			WordReportExample ex = new WordReportExample();
			//wordReport.setID(id);
			wordReport.setRS_CLIENT(rsClient);
			wordReport.setREPORT_TYPE(reportType);
			wordReport.setTITLE(title); 
			wordReport.setSTATUS(status); 
			wordReport.setREPORTS_RUN(reportsRun.shortValue());
			wordReport.setFIRST_RUN_DATE(firstRunDate);
			wordReport.setLAST_RUN_DATE(lastRunDate);
			wordReport.setEXPIRATION_DATE(expirationDate);
			wordReport.setDESCRIPTION(description);
			wordReport.setIS_DELETED(isDeleted);
			wordReport.setIS_TRIAL(isTrial);
			wordReport.setFREQUENCY(frequency);
			wordReport.setIS_AUTO_PUBLISH(isAutoPublished);
			wordReport.setRPT_URL(aURL);
			dao.updateByExample(wordReport, ex);
			logger.debug("updateExistingWordReport successfully called");
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return wordReport;
	}
	 
	/**
	 * Warning: this will return all reports of the given title - not associated with a user.
	 * @param aTitle
	 * @return
	 */
	public List<WordReport> getReportsByTitle(String aTitle)  
	{
		String methodName = "getReportsByTitle";
		logger.debug("entering " + methodName);
		
		SqlSession                     session = null;
		List<WordReport>               rpts = null;
		
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportMapper dao = session.getMapper(WordReportMapper.class); 
			WordReportExample ex = new WordReportExample();
			com.rstech.wordwatch.dao.WordReportExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andTITLELike(aTitle);
			rpts = dao.selectByExample(ex);
			logger.debug("getReportByTitle successfully called");
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
	
	 
	public static WordReport getReportByID(Long id)  
	{
		String methodName = "getReportByID";
		logger.debug("entering " + methodName);
		
		SqlSession                     session = null;
		WordReport                     rpt = null;
		
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			WordReportMapper dao = session.getMapper(WordReportMapper.class); 
			WordReportExample ex = new WordReportExample();
			com.rstech.wordwatch.dao.WordReportExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andIDEqualTo(id);
			List<WordReport> rpts = dao.selectByExample(ex);
			if (rpts == null || rpts.size() == 0)
				rpt = null;
			else
				rpt = (WordReport) rpts.get(0);
			  
			session.commit();
			logger.debug("getReportByTitle successfully called");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return rpt;
	}
	
	
}
