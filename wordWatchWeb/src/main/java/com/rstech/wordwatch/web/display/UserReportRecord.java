package com.rstech.wordwatch.web.display;

import java.util.Date;

import org.apache.log4j.Logger;

import com.rstech.utility.URLEncoder;

public class UserReportRecord {
	private static final Class thisClass = UserReportRecord.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	String reportName;
	long   reportId;
	int    pendingCount;
	int    trackingCount;
	int    completeCount;
	String viewLink;
	String firstRunDate;
	Date   firstRunDateForSort;
	
	public Date getFirstRunDateForSort() {
		return firstRunDateForSort;
	}
	public void setFirstRunDateForSort(Date firstRunDateForSort) {
		this.firstRunDateForSort = firstRunDateForSort;
	}
	public String getFirstRunDate() {
		return firstRunDate;
	}
	public void setFirstRunDate(String firstRunDate) {
		this.firstRunDate = firstRunDate;
	}
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public int getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(int pendingCount) {
		this.pendingCount = pendingCount;
	}
	public int getTrackingCount() {
		return trackingCount;
	}
	public void setTrackingCount(int trackingCount) {
		this.trackingCount = trackingCount;
	}
	public int getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCount(int completeCount) {
		this.completeCount = completeCount;
	}
	 
	

}
