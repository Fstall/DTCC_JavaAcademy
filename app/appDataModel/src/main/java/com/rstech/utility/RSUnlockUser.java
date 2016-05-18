package com.rstech.utility;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.database.SQLConnection;

public class RSUnlockUser {

	private static void setup() {
		final Properties myBatisProperties = new Properties();
			
		myBatisProperties.setProperty("mybatis.environment.id", "test");
		myBatisProperties.setProperty("JDBC.host", "localhost"); 
		myBatisProperties.setProperty("JDBC.port", "3306"); 
		myBatisProperties.setProperty("JDBC.schema", "WDWATCH");
		myBatisProperties.setProperty("JDBC.username", "root");
		myBatisProperties.setProperty("JDBC.password", "MyDTCCR00tPass!");
		myBatisProperties.setProperty("JDBC.autoCommit", "true"); 
		ComboPooledDataSource cpds = new ComboPooledDataSource(); 
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/WDWATCH?characterEncoding=utf8&tcpKeepAlive=true&autoReconnect=true");
	    cpds.setUser("root");
	    cpds.setPassword("MyDTCCR00tPass!");

	    cpds.setInitialPoolSize(10); 
		try {
			
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
	                "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, 
                "org.apache.naming");    
            Context ic = new InitialContext(myBatisProperties);
            ic.createSubcontext("java:jboss");
            ic.createSubcontext("java:jboss/datasources");
            ic.bind("java:jboss/datasources/MySqlDS", cpds);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Injector injector = Guice.createInjector(
			JdbcHelper.MySQL,
				new Module() {
					public void configure(Binder binder) {
						Names.bindProperties(binder, myBatisProperties);
					}
				}
			);
			*/
		
		
	}
	
	public static void main(String argv[]) {
		SqlSession session = null;
		String inputUserId = argv[0]; 
		
		// set up database access pool
		setup();
		session = SQLConnection.getSessionFactory().openSession(true); ;
		  
		RSUserManager userMgr = new RSUserManager();
		RSUser aUser = userMgr.getFirstUserByLogin(inputUserId);
		
		if (aUser == null) {
			System.err.println("Entered user: " + inputUserId + " does not exist!");
			System.exit(-1);
		} else {
			// unlock the user
			userMgr.lockUser(aUser, false);
		}
 		session.close(); 
 		System.out.println("Unlock complete");
	}
}
