package com.rstech.wordwatch.web.display;

import java.util.ArrayList;

import com.rstech.wordwatch.dao.WordReportEntry;
import com.rstech.wordwatch.dao.WordReportEntryAux;

public class WordReportEntryDetail {
	ArrayList<WordReportEntryAux> sampleSentenceList; 
	ArrayList<WordReportEntryAux> urlList;
	ArrayList<WordReportEntryAux> chineseList;
	public WordReportEntryDetail() {
		sampleSentenceList=new ArrayList<WordReportEntryAux>();
		urlList=new ArrayList<WordReportEntryAux>();
		chineseList=new ArrayList<WordReportEntryAux>();
		
	}
	WordReportEntry                theEntry;
	public ArrayList<WordReportEntryAux> getSampleSentenceList() {
		return sampleSentenceList;
	}
	public void setSampleSentenceList(
			ArrayList<WordReportEntryAux> sampleSentenceList) {
		this.sampleSentenceList = sampleSentenceList;
	}
	public ArrayList<WordReportEntryAux> getUrlList() {
		return urlList;
	}
	public void setUrlList(ArrayList<WordReportEntryAux> urlList) {
		this.urlList = urlList;
	}
	public ArrayList<WordReportEntryAux> getChineseList() {
		return chineseList;
	}
	public void setChineseList(ArrayList<WordReportEntryAux> chineseList) {
		this.chineseList = chineseList;
	}
	public WordReportEntry getTheEntry() {
		return theEntry;
	}
	public void setTheEntry(WordReportEntry theEntry) {
		this.theEntry = theEntry;
	}

	
}
