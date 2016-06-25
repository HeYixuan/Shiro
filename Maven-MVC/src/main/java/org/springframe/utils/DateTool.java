package org.springframe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTool {
	
	public int DateDiff(Date d1, Date d2) {
		int d = 0;
		try {
			long l = d1.getTime() - d2.getTime();
			d = (int) (l / 60 / 60 / 1000 / 24);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}
	public int DateDiff(Date d2) {
		int d = 0;
		GregorianCalendar calendar = new GregorianCalendar();
		java.util.Date d1 = calendar.getTime();
		d = DateDiff(d1, d2);
		return d;
	}

	public int DateDiff(String d1) {
		int d = 0;
		if (d1 == null)
			return d;
		try {
			d1 = d1.replaceAll("-", "/");
			Date d2 = new Date(d1);
			d = DateDiff(d2);
		} catch (Exception e) {
		}

		return d;
	}

	public GregorianCalendar DATEADD(
		String datepart,
		int number,
		GregorianCalendar inputdate) 
	{
		int normalDatepart = 0;
		datepart = datepart.toLowerCase();
		if (datepart.equals("y")
			|| datepart.equals("yy")
			|| datepart.equals("yyy")
			|| datepart.equals("yyyy"))
			normalDatepart = inputdate.YEAR;
		if (datepart.equals("m") || datepart.equals("mm"))
			normalDatepart = inputdate.MONTH;
		if (datepart.equals("d") || datepart.equals("dd"))
			normalDatepart = inputdate.DATE;
		if (datepart.equals("wk") || datepart.equals("ww")) {
			normalDatepart = inputdate.DATE;
			number = number * 7;
		}
		if (datepart.equals("hh") || datepart.equals("h"))
			normalDatepart = inputdate.HOUR;
		if (datepart.equals("mi"))
			normalDatepart = inputdate.MINUTE;
		if (datepart.equals("s") || datepart.equals("ss"))
			normalDatepart = inputdate.SECOND;
		if (datepart.equals("ms"))
			normalDatepart = inputdate.MILLISECOND;
		GregorianCalendar tempDate = new GregorianCalendar();
		tempDate.setTime(inputdate.getTime());
		tempDate.add(normalDatepart, number);
		return tempDate;
	}
	public String DATEADD(
		String datepart,
		int number,
		String strInputDate) 
	{
		java.util.GregorianCalendar gcResult = null;
		String outDate = "";
		String formatType = "yyyy-MM-dd";
		String tempInputDate = "";
		if (strInputDate.indexOf(".") >= 0)
			formatType = "yyyy.MM.dd";
		tempInputDate = strInputDate;
		if (strInputDate.indexOf(".") >= 0)
			formatType = "yyyy.MM.dd";
		if (tempInputDate.indexOf(":") >= 0)
			formatType = formatType + " hh:mm";
		tempInputDate = tempInputDate.substring(tempInputDate.indexOf(":") + 1);
		if (tempInputDate.indexOf(":") >= 0)
			formatType = formatType + ":ss";

		java.text.SimpleDateFormat formatter =
			new java.text.SimpleDateFormat(formatType);
		java.text.ParsePosition pos = new java.text.ParsePosition(0);
		java.util.Date sourceDate = formatter.parse(strInputDate, pos);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sourceDate);
		gcResult = DATEADD(datepart, number, gc);
		String strDate = formatter.format(gcResult.getTime());
		outDate = strDate;
		if (formatType.equals("yyyy-MM-dd hh:mm")
			|| formatType.equals("yyyy-MM-dd hh:mm:ss")
			|| formatType.equals("yyyy.MM.dd hh:mm")
			|| formatType.equals("yyyy.MM.dd hh:mm:ss")) 
			{
			if (gcResult.get(Calendar.AM_PM) == 1) 
				{
				String strPMHour =
					Integer.toString(
						Integer.parseInt(strDate.substring(11, 13)) + 12);
				outDate =
					strDate.substring(0, 11)
						+ strPMHour
						+ strDate.substring(13);
			}
		}
		return outDate;
	}
	public Date DATEADD(
		String datepart,
		int number,
		Date dateInputDate) 
	{
		java.util.GregorianCalendar gc = new GregorianCalendar();
		java.util.GregorianCalendar gcResult = null;
		gc.setTime(dateInputDate);
		gcResult = DATEADD(datepart, number, gc);
		return gcResult.getTime();
	}

	public int getYear(GregorianCalendar gc) 
	{
		return gc.get(Calendar.YEAR);
	}
	public int getYear(Date date) 
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(Calendar.YEAR);
	}

	public int getYear(String date) 
	{
		try {

			if (date == null || date.length() < 1) {
				return 0;
			}
			date = FormatDate(date, "");
			GregorianCalendar gc = StringToGCalendar(date);

			return gc.get(Calendar.YEAR);
		} catch (Exception e) {

		}
		return 0;
	}

	public int getMonth(GregorianCalendar gc) 
	{
		return gc.get(Calendar.MONTH) + 1;
	}
	public int getMonth(Date date) 
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(Calendar.MONTH) + 1;
	}
	public int getMonth(String date) 
	{
		if (date == null || (date.indexOf("-") == -1 && date.indexOf("/") == -1))
			return 1;
		try {
			date = FormatDate(date, "");
			
			GregorianCalendar gc = StringToGCalendar(date);
			return gc.get(Calendar.MONTH) + 1;
		} catch (Exception e) {
		}
		return 1;
	}

	public int getDay(GregorianCalendar gc) 
	{
		return gc.get(Calendar.DATE);
	}
	public int getDay(Date date) 
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(Calendar.DATE);
	}
	public int getDay(String date) 
	{
		date = FormatDate(date, "");
		GregorianCalendar gc = StringToGCalendar(date);
		return gc.get(Calendar.DATE);
	}

	public int getDAY_OF_WEEK(GregorianCalendar gc) 
	{
		int weekday = gc.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekday == 0)
			weekday = 7;
		return weekday;
	}
	public int getDAY_OF_WEEK(Date date) 
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		int weekday = gc.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekday == 0)
			weekday = 7;
		return weekday;
	}
	public int getDAY_OF_WEEK(String date) 
	{
		GregorianCalendar gc = StringToGCalendar(date);
		int weekday = gc.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekday == 0)
			weekday = 7;
		return weekday;
	}

	public String FormatDate(
		GregorianCalendar gc,
		String formatType) 
	{
		java.text.SimpleDateFormat formatter =
			new java.text.SimpleDateFormat(formatType);
		String strDate = formatter.format(gc.getTime());
		return strDate;
	}
	public String FormatDate(Date date, String formatType) 
	{
		java.text.SimpleDateFormat formatter =
			new java.text.SimpleDateFormat(formatType);
		String strDate = formatter.format(date);
		return strDate;
	}
	public String FormatDate(
		String dateOfString,
		String formatType) 
	{

		if (dateOfString == null || dateOfString.length() < 1) {
			return "";
		}
		dateOfString = dateOfString.trim();
		if (dateOfString.indexOf("-") > 0 && dateOfString.indexOf(".") > 0) {
			dateOfString = dateOfString.substring(0, dateOfString.indexOf("."));
		}

		if (formatType == null || formatType.length() < 1) {
			formatType = "yyyy-MM-dd";
		}
		if (dateOfString.length() < 5 && dateOfString.indexOf("-") == -1) {
			dateOfString = dateOfString + "-01-01";
		} else if (
			dateOfString.length() < 6 && dateOfString.indexOf("-") > -1) {
			dateOfString = dateOfString + "01-01";
		} else if (dateOfString.length() < 8) {
			dateOfString = dateOfString + "-01";
		}
		java.text.SimpleDateFormat formatter =
			new java.text.SimpleDateFormat(formatType);
		GregorianCalendar gc = StringToGCalendar(dateOfString);
		String strDate = formatter.format(gc.getTime());
		return strDate;
	}

	private GregorianCalendar StringToGCalendar(String strInputDate) {
		String formatType = "yyyy-MM-dd";
		String tempInputDate = "";
		if (strInputDate.indexOf(".") >= 0)
			formatType = "yyyy.MM.dd";
		tempInputDate = strInputDate;
		if (strInputDate.indexOf(".") >= 0)
			formatType = "yyyy.MM.dd";
		if (tempInputDate.indexOf(":") >= 0) 
			formatType = formatType + " hh:mm";
		tempInputDate = tempInputDate.substring(tempInputDate.indexOf(":") + 1);
		if (tempInputDate.indexOf(":") >= 0)
			formatType = formatType + ":ss";

		java.text.SimpleDateFormat formatter =
			new java.text.SimpleDateFormat(formatType);
		java.text.ParsePosition pos = new java.text.ParsePosition(0);
		java.util.Date sourceDate = formatter.parse(strInputDate, pos);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sourceDate);
		return gc;
	}
	
	public static String getDate(java.sql.Date date) {
		if (date == null){
			return "";
		}
		return date.toString();
	}
	
	public static String getDate(java.util.Date date) {
		if (date == null){
			return "";
		}
		return date.toString();
	}
	
	public static void main(String args[]) {
		DateTool dtool = new DateTool();
		System.out.println(dtool.getYear("2004-4-1 00:00:00.0"));

	}
	
	public static String getCurrentTime() {
		 Calendar   cale   =   Calendar.getInstance();   
		 int hour = cale.get(Calendar.HOUR_OF_DAY   );//得到24小时机制的   

        if (1 <= hour && hour <= 12) {
       	 return "上午好";
        } else if (12 < hour && hour <= 18) {
       	 return "下午好";
        } else {
       	 return "晚上好";
        }    		 
	}
	
	public static int getDaysBetween(String beginDate, String endDate)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date bDate = format.parse(beginDate);
		Date eDate = null;
		if (endDate != null) {
			eDate = format.parse(endDate);
		} else {
			String currentDay = format.format(new Date());
			eDate = format.parse(currentDay);
		}
		Calendar d1 = new GregorianCalendar();
		d1.setTime(bDate);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(eDate);
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	
	public static String getFormat(Date date,String formatString){
		SimpleDateFormat formatter = new SimpleDateFormat(formatString);
		return  formatter.format(date);
	}
}