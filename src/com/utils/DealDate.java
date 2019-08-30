package com.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DealDate {
	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	public static String dateToStr(Date date) {
	    SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
	    String str = sf.format(date);
	    return str;
	}
	
	public static Date strToUtilDate(String strDate) {
	    SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
	    java.util.Date date = null;
	    try {
	        date = sf.parse(strDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return date;
	}
	
	public static String dateToStr(Timestamp time) {
	    DateFormat df = new SimpleDateFormat(dateFormat);
	    String str = df.format(time);
	    return str;
	}
	
	public static Timestamp strToSqlDate(String strDate) {
	    SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
	    java.util.Date date = null;
	    try {
	            date = sf.parse(strDate);
	    } catch (ParseException e) {
	            e.printStackTrace();
	    }
	    java.sql.Timestamp dateSQL = new java.sql.Timestamp(date.getTime());
	    return dateSQL;
	}
	
	public static Timestamp dateToTime(Date date) {
	    String strDate = dateToStr(date);
	    return strToSqlDate(strDate);
	}
	
	public static Date timeToDate(Timestamp time) {
	    return time;
	}
}
