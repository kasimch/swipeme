package com.swipeme.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TimeSheetUtility {
	/*
	 * public static void main(String[] args) {
	 * TimeSheetUtility.stringToDate("2016-08-16 15:33:48"); }
	 */

	public static LocalDateTime convertStringToDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
		return LocalDateTime.parse(dateString, formatter);
	}

	public static long getHour(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
		return LocalDateTime.parse(dateString, formatter).getHour();
	}

	public static long calDiffTwoDates(LocalDateTime startDate, LocalDateTime endDate) {

		return java.time.Duration.between(startDate, endDate).toMinutes();
	}

	public static LocalDateTime addHoursToDate(LocalDateTime date, long hours) {
		return date.plusHours(hours);
	}

	public static String convertStringToDatePlusDay(String dateString) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String convertedDate = "";
		try {
			Calendar cal = Calendar.getInstance();

			cal.setTime(dateFormat.parse(dateString));

			cal.add(Calendar.DATE, 1);
			 convertedDate = dateFormat.format(cal.getTime());
			System.out.println("Date increase by one.." + convertedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertedDate;
	}

}
