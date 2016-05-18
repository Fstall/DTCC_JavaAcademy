package com.rstech.wordwatch.database;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.junit.BeforeClass;

import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.RSUserExample.Criteria; 
 

public class TestClientUser { 
	public static Map<RSClient, List<RSUser>> selectUsersFromClient() throws IOException {
        SqlSession sqlSession = SQLConnection.getSessionFactory().openSession(true);
        return null;
	} 
	
	public static void main(String argv[]) throws IOException { 			 
		RSClientManager mgr = new RSClientManager();
		
		 
		SetupStandaloneContext.setup();  
		
		
		RSClient aClient = null;
		String   clientName = "Barnacles";
		aClient = mgr.getClientByClientName(clientName);
		
		if (aClient == null) {
			aClient = mgr.createNewClient("RS Tech", "RS Tech", "123 ABC Street", "", "TestCity", "OR", "98330", "USA");
		} else {
			mgr.updateExistingClient(aClient, "Barnacles", "Wilfurbrady", "5", "", "Imaginationopolis", "Imaginationstate", "iiiii", "Imaginationland");
		}
		
		RSUserManager userMgr = new RSUserManager();
		String   login = "barnacles";
				
		RSUser aUser = userMgr.getFirstUserByLogin(login);
		if (aUser == null) {
			aUser = userMgr.createNewUser(aClient, "barnacles", "Dumb", "Kitteh", "qwerty", "ddd@aol.com", "3025298510", "", "322 abc Lane", "", "Corvallis", "OR", "93200", "USA", "", "", RSUserManager.getRegularUserType());
		} else {
			userMgr.updateExistingUser(aUser, aClient, "poopy2", "Poopy", "Poopy", "poopy", "poopy@poop.poop", "", "", "", "", "", "", "", "", "", "McPoopin");
		} 
	}
}

