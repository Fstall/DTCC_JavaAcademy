package com.rstech.wordwatch.sandbox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestOnly {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar outputCalendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		Date aDate = outputCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		System.out.println(sdf.format(aDate));
		

	}

}
