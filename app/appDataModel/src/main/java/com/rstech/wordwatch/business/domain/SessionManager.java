package com.rstech.wordwatch.business.domain;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger; 

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSClientExample; 
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.ScrLoginsAndLogouts;
import com.rstech.wordwatch.dao.ScrLoginsAndLogoutsExample;
import com.rstech.wordwatch.dao.mapper.RSClientMapper;
import com.rstech.wordwatch.dao.mapper.ScrLoginsAndLogoutsMapper;
import com.rstech.wordwatch.database.SQLConnection; 

/**
 * This class is used to manage user sessions.
 * When a user logs on to the application, it wil insert a record to indicate
 * the state. When the user logs out or disconnect, it will update the session 
 * record.
 * 
 * @author Tony
 *
 */
public class SessionManager {

	private static final Class thisClass = SessionManager.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	public static Calendar convertToEasternTimeCalendar(Calendar inputCalendar) {
		String methodName = "convertToEasternTimeCalendar";
		logger.debug("entering " + methodName);
		
		Calendar outputCalendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));   
		outputCalendar.set(Calendar.YEAR, inputCalendar.get(Calendar.YEAR));
		outputCalendar.set(Calendar.MONTH, inputCalendar.get(Calendar.MONTH));
		outputCalendar.set(Calendar.DAY_OF_YEAR, inputCalendar.get(Calendar.DAY_OF_YEAR));
		outputCalendar.set(Calendar.HOUR, inputCalendar.get(Calendar.HOUR));
		outputCalendar.set(Calendar.MINUTE, inputCalendar.get(Calendar.MINUTE));
		outputCalendar.set(Calendar.SECOND, inputCalendar.get(Calendar.SECOND));  
		
		logger.debug("exiting " + methodName);
		return outputCalendar;
	}
	
	public static java.util.Date getEasternTimeNow() {
		Calendar outputCalendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));   
		java.util.Date aDate = outputCalendar.getTime();
		return aDate; 
	}
	
	
	public static int SUCCESS_CODE = 0;
	public static final int CANNOT_FIND_USER_ID = -1;
	public static final int INCORRECT_PASSWORD = -2;
	public static final int MUST_ENTER_USER_ID_AND_PASSWORD = -3;
	public static final int USER_HAS_BEEN_DELETED = -4;
	public static final int USER_IS_LOCKED_OUT = -5;
	public static final int CANNOT_LOGON_USER = -6;
	
	public static String LOGIN_ACTION_TOKEN = "LOGIN";
	public static String LOGOUT_ACTION_TOKEN = "LOGOUT";
	public static String UNLOCK_ACTION_TOKEN = "UNLOCK";
	
	
	public static String LOGIN_SUCCESSFUL_STRING = "Y";
	public static String LOGUOT_SUCCESSFUL_STRING = "Y";
	public static String UNLOCK_SUCCESSFUL_STRING = "Y";
	public static String LOGIN_FAILED_STRING = "N";
	public static String USER_LOCKED_STRING = "N";
	public static String USER_ALREADY_LOCKED_STRING = "N";
	public static String CANNOT_LOGON_USER_STRING = "N";
	
	public LoginUserInfo login(String loginName, String password) {
		String methodName = "login";
		logger.debug("entering " + methodName);
		
		LoginUserInfo userInfo = new LoginUserInfo();
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true); 
			RSUserManager userMgr = new RSUserManager();
			RSClientManager mgr = new RSClientManager();
			List<RSUser> users = userMgr.getUserByLogin(loginName);
			if (users == null || users.size() == 0) {
				userInfo.setLastReturnCode(CANNOT_FIND_USER_ID); 
			} else {
				RSUser theUser = users.get(0);
				if ("Y".equals(theUser.getIS_LOCKED())) {
					userInfo.setLastReturnCode(USER_IS_LOCKED_OUT); 
					recordUserLoginActivity(theUser.getID(), LOGIN_ACTION_TOKEN,USER_ALREADY_LOCKED_STRING);
				} else {
					if (isUserLockedOut(theUser.getID())) {
						theUser.setIS_LOCKED("Y");
						userMgr.lockUser(theUser, true);
						recordUserLoginActivity(theUser.getID(), LOGIN_ACTION_TOKEN,USER_LOCKED_STRING);
					} else { 
						if (theUser.getPASSWORD() != null && theUser.getPASSWORD().equals(password)) {
							List<RSClient> clients = mgr.getClientByUser(theUser.getRS_CLIENT());
							RSClient client = clients.get(0);
							userInfo.setUser(theUser);
							userInfo.setClient(client);
							userInfo.setLastReturnCode(SUCCESS_CODE);
							recordUserLoginActivity(theUser.getID(), LOGIN_ACTION_TOKEN,LOGIN_SUCCESSFUL_STRING);
						} else {
							userInfo.setLastReturnCode(INCORRECT_PASSWORD);
							recordUserLoginActivity(theUser.getID(), LOGIN_ACTION_TOKEN,LOGIN_FAILED_STRING);
						}
						
					} 
				} 
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return userInfo;
	}
			
	public boolean isUserLockedOut(long userID) 
	{
		String methodName = "isUserLockedOut";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		boolean result = false;
		List<ScrLoginsAndLogouts> rpts = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true);
			ScrLoginsAndLogoutsMapper dao = session.getMapper(ScrLoginsAndLogoutsMapper.class);
			ScrLoginsAndLogoutsExample ex = new ScrLoginsAndLogoutsExample();
			com.rstech.wordwatch.dao.ScrLoginsAndLogoutsExample.Criteria aCriteria = ex.createCriteria();
			aCriteria.andAUTHENTICATED_USEREqualTo(userID);
			ex.setOrderByClause("  timestamp desc ");
			rpts = dao.selectByExample(ex);
			int count = 0;
			if (rpts == null || rpts.size() < 3) {
				result = false;
			} else {
				for (int i = rpts.size(); i > 0; i--) {
					ScrLoginsAndLogouts each = rpts.get(i - 1);
					if (LOGIN_ACTION_TOKEN.equals(each.getACTION()) && LOGIN_FAILED_STRING.equals(each.getLOGIN_SUCCESS())) {
						count++;
					}							
				}
				if (count == 3) {
					result = true;
				}
			}
			
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
	
	 
	public long recordUserLoginActivity(long authenticatedUserId, String action, String message) {
		String methodName = "recordUserLoginActivity";
		logger.debug("entering " + methodName);
		
		SqlSession session = null;
		RSClient   client = null;
		long id = 0;
		try {
			session = SQLConnection.getSessionFactory().openSession(true); 
			ScrLoginsAndLogoutsMapper dao = session.getMapper(ScrLoginsAndLogoutsMapper.class);
			ScrLoginsAndLogouts record = new ScrLoginsAndLogouts();
			record.setAUTHENTICATED_USER(authenticatedUserId);
			record.setACTION(action);
			record.setLOGIN_SUCCESS(message);   
			record.setTIMESTAMP(getEasternTimeNow()); 
			dao.insert(record);
			session.commit();
			id = record.getID();
			logger.debug("recordUserLoginActivity successfully called, new recordUserLoginActivity id = " + id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return id;
	}  


	/**
	 * Handle Staff login
	 */

	
	public LoginUserInfo loginStaff(String loginName, String password) {
		String methodName = "loginStaff";
		logger.debug("entering " + methodName);
		
		LoginUserInfo userInfo = new LoginUserInfo();
		SqlSession session = null;
		try {
			session = SQLConnection.getSessionFactory().openSession(true); 
			RSUserManager userMgr = new RSUserManager(); 
			List<RSUser> users = userMgr.getUserByLogin(loginName);
			if (users == null || users.size() == 0) {
				userInfo.setLastReturnCode(CANNOT_FIND_USER_ID); 
			} else {
				RSUser theUser = users.get(0);  
				
				if (theUser.getPASSWORD() != null && 
						theUser.getPASSWORD().equals(password)) {
					int isStaff =  theUser.getUSER_TP_NO() & RSUserManager.getStaffUserType();
					if (isStaff > 0) {
						userInfo.setUser(theUser); 
						userInfo.setLastReturnCode(SUCCESS_CODE);
						recordUserLoginActivity(theUser.getID(), LOGIN_ACTION_TOKEN,LOGIN_SUCCESSFUL_STRING);
					} else {
						userInfo.setUser(theUser); 
						userInfo.setLastReturnCode(CANNOT_LOGON_USER);
						recordUserLoginActivity(theUser.getID(), LOGIN_ACTION_TOKEN,CANNOT_LOGON_USER_STRING);						
					}
				} else {
					userInfo.setLastReturnCode(INCORRECT_PASSWORD);
					recordUserLoginActivity(theUser.getID(), LOGIN_ACTION_TOKEN,LOGIN_FAILED_STRING);
				}			 
			} 			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null) 
				session.close();
		}
		logger.debug("exiting " + methodName);
		return userInfo;
	}

}
