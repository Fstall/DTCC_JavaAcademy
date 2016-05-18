package com.rstech.utility;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SetupStandaloneContext {
	
	public static void setup() {
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
		
	}

}
