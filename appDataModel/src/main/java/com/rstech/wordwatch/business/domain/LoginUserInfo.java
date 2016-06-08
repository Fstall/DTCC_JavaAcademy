package com.rstech.wordwatch.business.domain;

import org.apache.log4j.Logger;

import com.rstech.utility.RSWord;
import com.rstech.wordwatch.dao.RSClient; 
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WordReport;

public class LoginUserInfo {
	private static final Class thisClass = LoginUserInfo.class;
	private static final Logger logger = Logger.getLogger(thisClass);
	RSUser user; 
	RSClient client;
	int    lastReturnCode;
	public RSUser getUser() {
		return user;
	}
	public void setUser(RSUser user) {
		this.user = user;
	}
	public RSClient getClient() {
		return client;
	}
	public void setClient(RSClient client) {
		this.client = client;
	}
	public int getLastReturnCode() {
		return lastReturnCode;
	}
	public void setLastReturnCode(int lastReturnCode) {
		this.lastReturnCode = lastReturnCode;
	} 
	
	WordReport currentReport;
	public WordReport getCurrentReport() {
		return currentReport;
	}
	public void setCurrentReport(WordReport currentReport) {
		this.currentReport = currentReport;
	}
	
	
}
