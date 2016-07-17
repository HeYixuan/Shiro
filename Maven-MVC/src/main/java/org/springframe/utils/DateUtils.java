package org.springframe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author 何壹轩
 * @version 2016-6-24
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

		/**
		 * 得到当前日期字符串 格式（yyyy-MM-dd）
		 */
		public static String getYMD() {
			return getDate("yyyy-MM-dd");
		}
		
		/**
		 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
		 */
		public static String getDate(String pattern) {
			return DateFormatUtils.format(new Date(), pattern);
		}
		
		/**
		 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
		 */
		public static String formatDate(Date date, Object... pattern) {
			String formatDate = null;
			if (pattern != null && pattern.length > 0) {
				formatDate = DateFormatUtils.format(date, pattern[0].toString());
			} else {
				formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
			}
			return formatDate;
		}
		
		/**
		 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
		 */
		public static String formatDateTime(Date date) {
			return formatDate(date, "yyyy-MM-dd HH:mm:ss");
		}

		/**
		 * 得到当前时间字符串 格式（HH:mm:ss）
		 */
		public static String getTime() {
			return formatDate(new Date(), "HH:mm:ss");
		}

		/**
		 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
		 */
		public static String getDateTime() {
			return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		}

		/**
		 * 得到当前年份字符串 格式（yyyy）
		 */
		public static String getYear() {
			return formatDate(new Date(), "yyyy");
		}

		/**
		 * 得到当前月份字符串 格式（MM）
		 */
		public static String getMonth() {
			return formatDate(new Date(), "MM");
		}

		/**
		 * 得到当天字符串 格式（dd）
		 */
		public static String getDay() {
			return formatDate(new Date(), "dd");
		}

		/**
		 * 得到当前星期字符串 格式（E）星期几
		 */
		public static String getWeek() {
			return formatDate(new Date(), "E");
		}
		
		/**
		 * 日期型字符串转化为日期 格式
		 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
		 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
		 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
		 */
		public static Date parseDate(Object str) {
			if (str == null){
				return null;
			}
			try {
				return parseDate(str.toString(), parsePatterns);
			} catch (ParseException e) {
				return null;
			}
		}

		/**
		 * 获取过去的天数
		 * @param date
		 * @return
		 */
		public static long pastDays(Date date) {
			long t = new Date().getTime()-date.getTime();
			return t/(24*60*60*1000);
		}

		/**
		 * 获取过去的小时
		 * @param date
		 * @return
		 */
		public static long pastHour(Date date) {
			long t = new Date().getTime()-date.getTime();
			return t/(60*60*1000);
		}
		
		/**
		 * 获取过去的分钟
		 * @param date
		 * @return
		 */
		public static long pastMinutes(Date date) {
			long t = new Date().getTime()-date.getTime();
			return t/(60*1000);
		}
		
		/**
		 * 转换为时间（天,时:分:秒.毫秒）
		 * @param timeMillis
		 * @return
		 */
	    public static String formatDateTime(long timeMillis){
			long day = timeMillis/(24*60*60*1000);
			long hour = (timeMillis/(60*60*1000)-day*24);
			long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
			long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
			long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
			return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
	    }
		
		/**
		 * 获取两个日期之间的天数
		 * 
		 * @param before
		 * @param after
		 * @return
		 */
		public static double getDistanceOfTwoDate(Date before, Date after) {
			long beforeTime = before.getTime();
			long afterTime = after.getTime();
			return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
		}
		
		
		
		/**************华丽的分割线***************************/
		// 得到当前的时间
	    public static Date getDate() {
	        Calendar canlendar = Calendar.getInstance();
	        return canlendar.getTime();
	    }
	    // 提到指定的millis得到时间
	    public static Date getDate(long millis) {
	        Calendar canlendar = Calendar.getInstance();
	        canlendar.clear();
	        canlendar.setTimeInMillis(millis);
	        return canlendar.getTime();
	    }

	    public static long getMillis() {
	        return Calendar.getInstance().getTimeInMillis();
	    }
	    // 得到指定日期的字符串(yyyy-MM-dd HH:mm:ss.SSS)
	    public static String getDateFormate(Date date, String formate) {
	        try {
	            SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
	            return simpleDateFormate.format(date);
	        } catch (Exception e) {
	        }
	        return "";
	    }
	    // 根据日期得到YYYY-MM-DD HH:MM:SS.SSS格式字符串
	    public static String get4yMdHmsS(Date date) {
	        return DateUtils.getDateFormate(date, "yyyy-MM-dd HH:mm:ss.SSS");
	    }
	    // 根据日期得到YYYY-MM-DD HH:MM:SS格式字符串
	    public static String get4yMdHms(Date date) {
	        return DateUtils.getDateFormate(date, "yyyy-MM-dd HH:mm:ss");
	    }
	    // 根据日期得到YYYY-MM-DD HH:MM格式字符串
	    public static String get4yMdHm(Date date) {
	        return DateUtils.getDateFormate(date, "yyyy-MM-dd HH:mm");
	    }
	    // 根据日期得到YYYY-MM-DD格式字符串
	    public static String get4yMd(Date date) {
	        return DateUtils.getDateFormate(date, "yyyy-MM-dd");
	    }
	    // 把指定字符(yyyy-MM-dd HH:mm:ss.SSS)串转成Date
	    public static Date parse4yMdHmsS(String sDate) {
	        return DateUtils.parseDate(sDate, "yyyy-MM-dd HH:mm:ss.SSS");
	    }
	    // 把指定字符(yyyy-MM-dd HH:mm:ss)串转成Date
	    public static Date parse4yMdHms(String sDate) {
	        return DateUtils.parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
	    }
	    // 把指定字符(yyyy-MM-dd HH:mm)串转成Date
	    public static Date parse4yMdHm(String sDate) {
	        return DateUtils.parseDate(sDate, "yyyy-MM-dd HH:mm");
	    }
	    // 把指定字符(yyyy-MM-dd)串转成Date
	    public static Date parse4yMd(String sDate) {
	        return DateUtils.parseDate(sDate, "yyyy-MM-dd");
	    }
	    // 根据指定格式,把字符串转成日期
	    public static Date parseDate(String sDate, String formate) {
	        SimpleDateFormat simpleDateFormate = new SimpleDateFormat(formate);
	        try {
	            return simpleDateFormate.parse(sDate);
	        } catch (ParseException e) {
	            return null;
	        }
	    }

	    // 两个长整型的时间相差(时间的毫秒数),可以得到指定的毫秒数,秒数,分钟数,天数
	    public static double getDifTwoTime(Date minuendTime, Date subtrahendTime,
	            String tdatestr) {
	        if (minuendTime == null || subtrahendTime != null) {
	            return DateUtils.getDifTwoTime(minuendTime.getTime(),
	                    subtrahendTime.getTime(), tdatestr);
	        }
	        return 0;
	    }

	    // 两个长整型的时间相差(时间的毫秒数),可以得到指定的毫秒数,秒数,分钟数,天数
	    public static double getDifTwoTime(long minuendTime, long subtrahendTime,
	            String tdatestr) {
	        if (tdatestr == null || tdatestr.equals("")) {
	            tdatestr = "MS";
	        }
	        double temp = 1;
	        /** 毫秒数 */
	        if ("MS".equalsIgnoreCase(tdatestr)) {
	            temp = 1;
	        }
	        /** 得到秒 */
	        if ("S".equalsIgnoreCase(tdatestr)) {
	            temp = 1000;
	        }
	        /** 得到分 */
	        if ("M".equalsIgnoreCase(tdatestr)) {
	            temp = 1000 * 60;
	        }
	        /** 得到小时 */
	        if ("H".equalsIgnoreCase(tdatestr)) {
	            temp = 1000 * 60 * 60;
	        }
	        /** 得到天 */
	        if ("D".equalsIgnoreCase(tdatestr)) {
	            temp = 1000 * 60 * 60 * 24;
	        }
	        return (minuendTime - subtrahendTime) / temp;
	    }

	    // 从日期中得到指定部分(YYYY/MM/DD/HH/SS/SSS)数字
	    public static int getPartOfTime(Date date, String part) {
	        Calendar canlendar = Calendar.getInstance();
	        canlendar.clear();
	        canlendar.setTime(date);
	        if (part.equalsIgnoreCase("Y")) {// 得到年
	            return canlendar.get(Calendar.YEAR);
	        }
	        if (part.equalsIgnoreCase("M")) {// 得到月
	            return canlendar.get(Calendar.MONTH) + 1;
	        }
	        if (part.equalsIgnoreCase("D")) {// 得到日
	            return canlendar.get(Calendar.DAY_OF_MONTH);
	        }
	        if (part.equalsIgnoreCase("H")) {// 得到时
	            return canlendar.get(Calendar.HOUR_OF_DAY);
	        }
	        if (part.equalsIgnoreCase("M")) {// 得到分
	            return canlendar.get(Calendar.MINUTE);
	        }
	        if (part.equalsIgnoreCase("S")) {// 得到秒
	            return canlendar.get(Calendar.SECOND);
	        }
	        if (part.equalsIgnoreCase("MS")) {// 得到毫秒
	            return canlendar.get(Calendar.MILLISECOND);
	        }
	        return -1;
	    }
		
		/**
		 * @param args
		 * @throws ParseException
		 */
		public static void main(String[] args) throws ParseException {
//			System.out.println(formatDate(parseDate("2010/3/6")));
//			System.out.println(getDate("yyyy年MM月dd日 E"));
//			long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//			System.out.println(time/(24*60*60*1000));
		}
}
