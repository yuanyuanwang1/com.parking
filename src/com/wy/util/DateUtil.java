package com.wy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author Liuyang
 * @version 1.2
 */
public class DateUtil {
	private static SimpleDateFormat isoTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static Date parseTime(String text) {
		if (StringUtils.isNotEmpty(text)) {
			try {
				return isoTime.parse(text);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String formatTime(Date date) {
		if (date != null) {
			return isoTime.format(date);
		}
		return null;
	}

	public static String formatMonth(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM").format(date);
		}
		return null;
	}

	public static String formatYear(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy").format(date);
		}
		return null;
	}

	private static SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");

	public static Date parseDate(String text) {
		if (StringUtils.isNotEmpty(text)) {
			try {
				return isoDate.parse(text);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String formatDate(Date date) {
		if (date != null) {
			return isoDate.format(date);
		}
		return null;
	}

	public static String formatDate(Date date, String format) {

		SimpleDateFormat shortDate = null;

		if (StringUtils.isBlank(format)) {
			shortDate = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			shortDate = new SimpleDateFormat(format);
		}

		return shortDate.format(date);
	}

	public static String getDate(String format) {

		SimpleDateFormat shortDate = null;

		if (StringUtils.isBlank(format)) {
			shortDate = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			shortDate = new SimpleDateFormat(format);
		}

		return shortDate.format(new Date());
	}

	public static int getMaxDay(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.getActualMaximum(Calendar.DATE);

		return day;

	}

	public static Date parseFormatDate(String format) {

		SimpleDateFormat dateFormat = null;

		String date = getDate(format);

		if (StringUtils.isNotEmpty(date)) {
			try {
				return isoDate.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Date getYesterday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		date = calendar.getTime();
		return date;
	}

	public static Date getTomorrow(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		return date;
	}

	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date date = calendar.getTime();
		return date;
	}

	public static Date getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		Date date = calendar.getTime();
		return date;
	}

	public static Date getPer3Day() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -3);
		Date date = calendar.getTime();
		return date;
	}

	public static Date getNext3Day() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 3);
		Date date = calendar.getTime();
		return date;
	}

	public static Date getPreMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -30);
		date = calendar.getTime();
		return date;
	}

	public static long getLongTimeMillis() {
		Date date = new Date();
		return date.getTime();
	}

	public static Date addDateOfDay(Date date, int addDayNum) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, addDayNum);
		Date newDate = calendar.getTime();
		return newDate;

	}

	public static Date parseDate(String strDate, String format) {

		if (StringUtils.isBlank(format)) {
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(strDate);
		} catch (ParseException e) {

			return null;
		}

	}

	public static String oracleTimeFormat(String timeFormat) {

		if (StringUtils.length(timeFormat) == 4) {

			return "yyyy";

		}

		if (StringUtils.length(timeFormat) == 7) {

			return "yyyy-mm";

		}

		if (StringUtils.length(timeFormat) == 10) {

			return "yyyy-mm-dd";

		}

		return "yyyy-mm-dd hh24:mi:ss";

	}

	public static Date getPreHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, -1);
		date = calendar.getTime();
		return date;
	}

	public static Date getNextHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 1);
		date = calendar.getTime();
		return date;
	}

}
