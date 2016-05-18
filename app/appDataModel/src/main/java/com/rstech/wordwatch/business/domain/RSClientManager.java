package com.rstech.wordwatch.business.domain;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSClientExample;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.RSUserExample;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.mapper.RSClientMapper;
import com.rstech.wordwatch.dao.mapper.RSUserMapper;
import com.rstech.wordwatch.dao.mapper.WordReportMapper;
import com.rstech.wordwatch.database.SQLConnection;
import org.apache.log4j.Logger;

public class RSClientManager {
	private static final Class thisClass = RSClientManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	public RSClient createNewClient(String clientName, String displayName, String addrLine1, String addrLine2, String city, String state, String zipCode, String country) {
		String methodName = "createNewClient";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		RSClient   client = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSClientMapper dao = session.getMapper(RSClientMapper.class);  
			client = new RSClient();
			client.setNAME(clientName);
			client.setDISPLAY_NAME(displayName);
			client.setADDRESS1(addrLine1);
			client.setADDRESS2(addrLine2);
			client.setCITY(city);
			client.setSTATE(state);
			client.setZIP(zipCode);
			client.setCOUNTRY(country);
			dao.insert(client);
			session.commit();
			logger.debug("createNewClient successfully called, new client id = " + client.getID().toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		
		return client;
	}
	
	public RSClient updateExistingClient(RSClient aClient, String clientName, String displayName, String addrLine1, String addrLine2, String city, String state, String zipCode, String country) {
		String methodName = "updateExistingClient";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSClientMapper dao = session.getMapper(RSClientMapper.class);
			aClient.setID(aClient.getID());
			aClient.setNAME(clientName);
			aClient.setDISPLAY_NAME(displayName);
			aClient.setADDRESS1(addrLine1);
			aClient.setADDRESS2(addrLine2);
			aClient.setCITY(city);
			aClient.setSTATE(state);
			aClient.setZIP(zipCode);
			aClient.setCOUNTRY(country);
			dao.updateByPrimaryKey(aClient);
			session.commit();
			logger.debug("updateExistingClient successfully called");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return aClient;
	}
	
	public List<RSClient> getClientByUser(Long RSClientId) 
	{
		String methodName = "getClientByUser";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<RSClient> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSClientMapper dao = session.getMapper(RSClientMapper.class);
			RSClientExample ex = new RSClientExample();
			com.rstech.wordwatch.dao.RSClientExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andIDEqualTo(RSClientId);
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
	
	public RSClient getClientByID(Long id) 
	{
		String methodName = "getClientByID";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		RSClient theClient = null;
		List<RSClient> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSClientMapper dao = session.getMapper(RSClientMapper.class);
			RSClientExample ex = new RSClientExample();
			com.rstech.wordwatch.dao.RSClientExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andIDEqualTo(id);
			rpts = dao.selectByExample(ex);
			if (rpts != null && rpts.size() > 0) {
				theClient = (RSClient) rpts.get(0);
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
		
		return theClient;
	}
	
	public List<RSClient> getClientList() 
	{
		String methodName = "getClientList";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<RSClient> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSClientMapper dao = session.getMapper(RSClientMapper.class);
			RSClientExample ex = new RSClientExample();
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
	
	
	public RSClient getClientByClientName(String aClientName) 
	{
		String methodName = "getClientByClientName";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<RSClient> rpts = null;
		RSClient theClient = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSClientMapper dao = session.getMapper(RSClientMapper.class);
			RSClientExample ex = new RSClientExample();
			com.rstech.wordwatch.dao.RSClientExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andNAMEEqualTo(aClientName); 
			rpts = dao.selectByExample(ex);
			session.commit();
			if (rpts != null && rpts.size() > 0)
				theClient = (RSClient) rpts.get(0);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return theClient;
	}
	
}
