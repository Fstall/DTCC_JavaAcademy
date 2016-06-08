package com.rstech.wordwatch.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.RSUserExample;
import com.rstech.wordwatch.dao.mapper.RSUserMapper;
import com.rstech.wordwatch.database.SQLConnection;
 
public class SQLConnection { 
	private static final Class thisClass = SQLConnection.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	private static long lastAccessTime = 0;
	

	private static SqlSessionFactory sessionFactory = null;
 
	private SQLConnection() {
 
	}
 
	private static SqlSessionFactory getInstance() throws IOException {
		String methodName = "getInstance";
		logger.debug("entering " + methodName);
		
		String resource = null;
		Reader reader = null;
 
		if(sessionFactory == null) {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			
			resource = "./com/rstech/wordwatch/dao/config/dev/configuration.xml";
			InputStream input = classLoader.getResourceAsStream(resource);
			System.out.println("resource = " + resource);
			System.out.println("classpath=" + System.getProperty("java.class.path"));
			 
			reader = new InputStreamReader(input);
			
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}
		logger.debug("exiting " + methodName);
		return sessionFactory;
	}
	
	private static synchronized void tryReconnect() {
		long currentTime = System.currentTimeMillis();
		long elapseTime = currentTime - lastAccessTime;
		
		if (sessionFactory != null && elapseTime > 100000) {
			boolean flag = false;
			int retry = 0;
			while (flag == false && retry < 3) {
				
				logger.warn("Retrying to connect " + retry);
				SqlSession session = null;
				List<RSUser> rpts = null;
				try {
					session = sessionFactory.openSession(true);
					RSUserMapper dao = session.getMapper(RSUserMapper.class);
					RSUserExample ex = new RSUserExample();
					com.rstech.wordwatch.dao.RSUserExample.Criteria aCriteria = ex.createCriteria();
					aCriteria.andLOGINEqualTo("tchou");
					aCriteria.andPASSWORDEqualTo("test123");
					rpts = dao.selectByExample(ex);
					session.commit();
					flag = true;
				}
				catch (Exception ex) {
					ex.printStackTrace();					
				} finally {
					if (session != null) 
						session.close();
				}
				retry++;
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		lastAccessTime = System.currentTimeMillis();
	}
	
 
	public static synchronized SqlSessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			try {
				getInstance();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		tryReconnect();
		
		return sessionFactory;
	}
 
	public Object clone() throws CloneNotSupportedException {
		String methodName = "CloneNotSupportedException";
		logger.debug("entering " + methodName);
		logger.debug("exiting " + methodName);
		throw new CloneNotSupportedException();
		
	}
	 
}