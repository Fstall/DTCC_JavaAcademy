package com.rstech.wordwatch.business.domain;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
 
import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.dao.RSClient; 
import com.rstech.wordwatch.dao.RSSearchLimitDomainTable;
import com.rstech.wordwatch.dao.RSSearchLimitDomainTableExample;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.RSUserExample;
import com.rstech.wordwatch.dao.RSUserLimit;
import com.rstech.wordwatch.dao.RSUserLimitExample;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.RSUserExample.Criteria; 
import com.rstech.wordwatch.dao.mapper.RSSearchLimitDomainTableMapper;
import com.rstech.wordwatch.dao.mapper.RSUserLimitMapper;
import com.rstech.wordwatch.dao.mapper.RSUserMapper;
import com.rstech.wordwatch.database.SQLConnection;

import edu.vt.middleware.password.AlphabeticalSequenceRule;
import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.NumericalSequenceRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.QwertySequenceRule;
import edu.vt.middleware.password.RepeatCharacterRegexRule;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;

public class RSUserManager {
	
	static final String userAlreadyExists = "User id (email) already exists!";
	static final String failedToCreateUser = "Failed to create user!";
	/** Important - 
	 * REBULAR_USER_BIT 1 is the default value in database.
	 * Do not change it!
	 */
	public static final int USER_BIT = 1; 
	public static final int TEACHER_BIT = 2; 
	public static final int STAFF_BIT = 4; 
	public static final int DEVELPOER_BIT = 8; 
	
	public static final int LONGMAN_SEARCH_TYPE = 1;
	
	private static boolean initialized = false;
	public static int UserSearchCountLimit = 15; 

	private static final Class thisClass = RSUserManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	
	public static int getRegularUserType() {
		return USER_BIT;
	}
	
	public static int getTeacherUserType() {
		return USER_BIT | TEACHER_BIT;
	}

	public static int getStaffUserType() {
		return USER_BIT | TEACHER_BIT | STAFF_BIT;
	}
	
	public static int getDeveloperUserType() {
		return USER_BIT | TEACHER_BIT | STAFF_BIT | DEVELPOER_BIT;
	}

	
	private static boolean checkPassword(String pwd, ArrayList <String>list) {
		boolean resultReturn = false;
		String methodName = "checkPassword";
		logger.debug("entering " + methodName);
		
		String message = "";
		// password must be between 8 and 16 chars long
		LengthRule lengthRule = new LengthRule(8, 16);

		// don't allow whitespace
		WhitespaceRule whitespaceRule = new WhitespaceRule();

		// control allowed characters
		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
		// require at least 1 digit in passwords
		charRule.getRules().add(new DigitCharacterRule(1));
		// require at least 1 non-alphanumeric char
		charRule.getRules().add(new NonAlphanumericCharacterRule(1));
		// require at least 1 upper case char
		charRule.getRules().add(new UppercaseCharacterRule(1));
		// require at least 1 lower case char
		charRule.getRules().add(new LowercaseCharacterRule(1));
		// require at least 3 of the previous rules be met
		charRule.setNumberOfCharacteristics(3);

		// don't allow alphabetical sequences
		AlphabeticalSequenceRule alphaSeqRule = new AlphabeticalSequenceRule();

		// don't allow numerical sequences of length 3
		NumericalSequenceRule numSeqRule = new NumericalSequenceRule(3, false);

		// don't allow qwerty sequences
		QwertySequenceRule qwertySeqRule = new QwertySequenceRule();

		// don't allow 4 repeat characters
		RepeatCharacterRegexRule repeatRule = new RepeatCharacterRegexRule(4);

		// group all rules together in a List
		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(lengthRule);
		ruleList.add(whitespaceRule);
		ruleList.add(charRule);
		ruleList.add(alphaSeqRule);
		ruleList.add(numSeqRule);
		ruleList.add(qwertySeqRule);
		ruleList.add(repeatRule);

		PasswordValidator validator = new PasswordValidator(ruleList);
		PasswordData passwordData = new PasswordData(new Password(pwd));

		RuleResult result = validator.validate(passwordData);
		if (result.isValid()) {
		  resultReturn = true;
		} else {
		  resultReturn = false;
		  
		  message = validator.getMessages(result).get(0);
		  list.add(message);
		}
		logger.debug("exiting " + methodName);
		return resultReturn;
	}
	
	/*
	 * RS Tech allows users to create a new user name and password under
	 * the system client: RS Tech.
	 */
//	public static RSUser createNewUserUnderSystem(String userIdEmailAddress, String password,
//												int userType, RSUser aReferenceUser, String clientName) throws Exception {
	public static RSUser createNewUserUnderSystem(String userIdEmailAddress, String password,
			int userType, RSUser aReferenceUser) throws Exception {		
		String clientName = "RS Tech"; 
	 	String methodName = "createNewUserUnderSystem";
	 	logger.debug("entering " + methodName);
		
	 	  
		RSClientManager mgr = new RSClientManager();
		RSClient aClient = mgr.getClientByClientName(clientName);
		if (aClient == null) {
			aClient = mgr.createNewClient("RS Tech", "RS Tech", "123 ABC Street", "", "TestCity", "OR", "98330", "USA");	
		}
		
	 	RSUserManager userMgr = new RSUserManager();
		RSUser aUser = userMgr.getFirstUserByLogin(userIdEmailAddress);
		
		if (aUser == null) {
			ArrayList<String> list = new ArrayList<String>();
			if (checkPassword(password, list)) {
				if (aReferenceUser != null) {
					aUser = userMgr.createNewReqularUserWithTeacher(aClient, userIdEmailAddress, userIdEmailAddress, userIdEmailAddress, password, userIdEmailAddress,
							"", "", "", "", "", "", "", "", "", "", userType, aReferenceUser);
				} else {
					aUser = userMgr.createNewUser(aClient, userIdEmailAddress, userIdEmailAddress, userIdEmailAddress, password, userIdEmailAddress, 
							"", "", "", "", "", "", "", "", "", "", userType);
				}
			} else {
				throw new Exception(list.get(0));
			}
		} else {
			// this is an exception - you can't create a new user when the user
			// id exist
			throw new Exception(userAlreadyExists);
		}
		if (aUser == null) {
			throw new Exception(failedToCreateUser);
		}
		logger.debug("exiting " + methodName);
		return aUser;	  	
	}
	
	/**
	 * Create a new regular user.
	 * @param aClient
	 * @param login
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 * @param phone1
	 * @param phone2
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 * @param fax
	 * @param title
	 * @param USER_TP_NO
	 * @return
	 */
	public RSUser createNewUser(RSClient aClient, String login, String firstName, 
			String lastName, String password, String email, String phone1, String phone2, 
			String addrLine1, String addrLine2, String city, String state, String zipCode, 
			String country, String fax, String title, int aType) 
	{
		return createUser(aClient, login,  firstName, 
				 lastName,  password,  email,  phone1,  phone2, 
				 addrLine1,  addrLine2,  city,  state,  zipCode, 
				 country,  fax,  title,  aType, null);
	}
	
	/**
	 * Create a new regular user.
	 * @param aClient
	 * @param login
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 * @param phone1
	 * @param phone2
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 * @param fax
	 * @param title
	 * @param USER_TP_NO
	 * @return
	 */
	public RSUser createNewReqularUserWithTeacher(RSClient aClient, String login, String firstName, 
			String lastName, String password, String email, String phone1, String phone2, 
			String addrLine1, String addrLine2, String city, String state, String zipCode, 
			String country, String fax, String title, int aType, RSUser teacher) 
	{
		RSUser theStudent = createUser(aClient, login,  firstName, 
				 lastName,  password,  email,  phone1,  phone2, 
				 addrLine1,  addrLine2,  city,  state,  zipCode, 
				 country,  fax,  title,  aType, teacher.getID());
		
		 
		return theStudent;
	}
	 
	
	public RSUser createUser(RSClient aClient, String login, String firstName, 
			String lastName, String password, String email, String phone1, String phone2, 
			String addrLine1, String addrLine2, String city, String state, String zipCode, 
			String country, String fax, String title, Integer USER_TP_NO, Long anID)
	{
		String methodName = "createUser";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		RSUser     user = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);  
			user = new RSUser(); 
			user.setRS_CLIENT(aClient.getID());
			user.setLOGIN(login);
			user.setFIRST_NAME(firstName);
			user.setLAST_NAME(lastName);
			user.setPASSWORD(password);
			user.setEMAIL(email);
			user.setADDRESS1(addrLine1);
			user.setADDRESS2(addrLine2);
			user.setCITY(city);
			user.setSTATE(state);
			user.setZIP(zipCode);
			user.setCOUNTRY(country);
			user.setFAX(fax);
			user.setTITLE(title);
			// always lock the user on creating new
			user.setIS_LOCKED("N");
			user.setUSER_TP_NO(USER_TP_NO);
			if (anID != null && anID > 0) {
				user.setPRNT_ID(anID);
			}
			dao.insert(user);
			logger.debug("createUser successfully called, new user id = " + user.getID().toString());
			session.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return user;
	}
	
	 
	
	/**
	 *
	 * @param aUser
	 * @param aClient
	 * @param login
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 * @param phone1
	 * @param phone2
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param zipCode
	 * @param country
	 * @param fax
	 * @param title
	 * @return
	 */
	public RSUser updateExistingUser(RSUser aUser, RSClient aClient, String login, String firstName, 
			String lastName, String password, String email, String phone1, String phone2, 
			String addrLine1, String addrLine2, String city, String state, String zipCode, 
			String country, String fax, String title) 
	{
		String methodName = "updateExistingUser";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);  
			aUser.setRS_CLIENT(aClient.getID());
			aUser.setLOGIN(login);
			aUser.setFIRST_NAME(firstName);
			aUser.setLAST_NAME(lastName);
			aUser.setPASSWORD(password);
			aUser.setEMAIL(email);
			aUser.setADDRESS1(addrLine1);
			aUser.setADDRESS2(addrLine2);
			aUser.setCITY(city);
			aUser.setSTATE(state);
			aUser.setZIP(zipCode);
			aUser.setCOUNTRY(country);
			aUser.setFAX(fax);
			aUser.setTITLE(title);
			dao.updateByPrimaryKey(aUser);
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
	
		return aUser;
	}

	public RSUser lockUser(RSUser aUser, boolean flag)  
	{
		String methodName = "lockUser";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);  
			if (flag) {
				aUser.setIS_LOCKED("Y");
			} else {
				aUser.setIS_LOCKED("N");
			}
			dao.updateByPrimaryKey(aUser);
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

		return aUser;
	}
	
	/**
	 * Delete or undelete the user.
	 * @param aUser
	 * @param flag
	 * @return
	 */
	public RSUser markUserDeleted(RSUser aUser, boolean flag)  
	{
		String methodName = "markUserDeleted";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);  
			if (flag) {
				aUser.setIS_DELETED("Y");
			} else {
				aUser.setIS_DELETED("N");
			}
			dao.updateByPrimaryKey(aUser);
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

		return aUser;
	}

	
	public List<RSUser> getUserByLoginAndPassword(String login, String password) 
	{
		String methodName = "getUserByLoginAndPassword";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<RSUser> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);
			RSUserExample ex = new RSUserExample();
			com.rstech.wordwatch.dao.RSUserExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andLOGINEqualTo(login);
			aCriteria.andPASSWORDEqualTo(password);
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
	
	/*
	 * Return a list of RSUser whose login is equal to the passed in login.
	 */
	public List<RSUser> getUserByLogin(String login)  
	{
		String methodName = "getUserByLogin";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<RSUser> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);
			RSUserExample ex = new RSUserExample();
			com.rstech.wordwatch.dao.RSUserExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andLOGINEqualTo(login);			
			rpts = dao.selectByExample(ex);
			ArrayList<RSUser> output = new ArrayList<RSUser>();
			for (RSUser each : rpts) 
			{
				if (each.getIS_DELETED() == null ||
					"N".equals(each.getIS_DELETED()))
					output.add(each);
			}
			rpts = output;
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

	

	public RSUser getUserByID(Long id) 
	{
		String methodName = "getUserByID";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		RSUser theUser = null;
		List<RSUser> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);
			RSUserExample ex = new RSUserExample();
			com.rstech.wordwatch.dao.RSUserExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andIDEqualTo(id);
			rpts = dao.selectByExample(ex);
			if (rpts != null && rpts.size() > 0) {
				theUser = (RSUser) rpts.get(0);
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

		return theUser;
	}
	
	public List<RSUser> getUserList(Long RSClientId) 
	{
		String methodName = "getUserList";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<RSUser> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);
			RSUserExample ex = new RSUserExample();
			com.rstech.wordwatch.dao.RSUserExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andRS_CLIENTEqualTo(RSClientId);
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
	
	
	public RSUser getFirstUserByLogin(String login) 
	{
		String methodName = "getFirstUserByLogin";
		logger.debug("entering " + methodName);
		
		SqlSession                          session = null;
		List<RSUser>                        rpts = null;
		RSUser                              theUser = null;
		try {
			SqlSessionFactory factory = SQLConnection.getSessionFactory();
			session = factory.openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);
			RSUserExample ex = new RSUserExample();
			com.rstech.wordwatch.dao.RSUserExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andLOGINEqualTo(login); 
			rpts = dao.selectByExample(ex);
			session.commit();
			if (rpts != null && rpts.size() > 0)
				theUser = (RSUser) rpts.get(0);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return theUser;
	}
	
	
	
	/**
	 * Return the list of students of the passed in RSUser.
	 */
	
	public List<RSUser> getStudentByUser(Long aUserID) 
	{
		String methodName = "getStudentByUser";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		List<RSUser> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserMapper dao = session.getMapper(RSUserMapper.class);
			RSUserExample ex = new RSUserExample();
			com.rstech.wordwatch.dao.RSUserExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andPRNT_IDEqualTo(aUserID);
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
	
	//=================
	

	/**
	 * For the passed in user, register to the RS_USER_SRCH_LMT table if the
	 * # of count is less than the quota (total #= 4000 per month for Longman).
	 * 
	 * @param aUser
	 * @param sType
	 * @return
	 */
	
	public static int registerIfCanSearch(RSUser aUser, int sType) {
		
		String methodName = "registerIfCanSearch";
		logger.debug("entering " + methodName);
		int totalCount = 0;
		
		SqlSession session = null;
		RSUserLimit     limit = null;
		RSUserLimitExample     ex = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSUserLimitMapper dao = session.getMapper(RSUserLimitMapper.class);
			ex = new RSUserLimitExample();
			com.rstech.wordwatch.dao.RSUserLimitExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andSEARCH_TP_NOEqualTo(sType);
			Date today = new Date();
			Calendar cal = new GregorianCalendar();
			cal.setTime(today);
			cal.add(Calendar.DAY_OF_MONTH, -2);  // test code - change change 2 to 30 later
			Date today30 = cal.getTime();  
			aCriteria.andLAST_MODIFIED_TSGreaterThan(today30); 
			List<RSUserLimit> rpts = dao.selectByExample(ex);
			int userUsages = 0;
			if (rpts == null || rpts.size() < UserSearchCountLimit) {
				RSUserLimit newLimitRecord = new RSUserLimit();
				newLimitRecord.setRS_USER(aUser.getID());
				newLimitRecord.setSEARCH_TP_NO(sType); 
				dao.insert(newLimitRecord);
				totalCount = rpts.size();
			} else {
				userUsages = rpts.size();
				if (userUsages >= UserSearchCountLimit) {
					totalCount = UserSearchCountLimit;
				}
			}
			session.commit();  
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			totalCount = UserSearchCountLimit;
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);

		return totalCount;
	}
	
	
	public static Boolean initDomainTable() { 
		
		String methodName = "initDomainTable";
		logger.debug("entering " + methodName);
		Boolean result = false;
		
		SqlSession session = null; 
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			RSSearchLimitDomainTableMapper dao = session.getMapper(RSSearchLimitDomainTableMapper.class);
			RSSearchLimitDomainTable aRecord = new RSSearchLimitDomainTable();
			aRecord.setID(LONGMAN_SEARCH_TYPE);
			aRecord.setDESC_TX("Longman Search");  
			dao.insert(aRecord); 
			session.commit();  
			result = true;
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
}
