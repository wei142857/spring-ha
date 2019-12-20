package com.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat DATE_ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat HH_MM = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat HH_MM_SS = new SimpleDateFormat("HH:mm:ss");
	public static final String DATE_ALL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String HH_MM_STR = "HH:mm";
	public static final String HH_MM_SS_STR = "HH:mm:ss";
	public static final Date DATE = new Date();
	/**
	 * 	将字符串转换为Date
	 * @param parseType 在当前类中指定转换的类型
	 * @param dateStr 时间字符串
	 * @return Date对象
	 */
	public static Date parse(SimpleDateFormat parseType, String dateStr) {
		try {
			return parseType.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.modules.constant.DateAttribute.parse(SimpleDateFormat parseType, String dateStr): 时间转换失败："+dateStr+" Err:"+e.getMessage());
		}
		return null;
	}
	/**
	 * 将时间 转换为字符串
	 * @param formatType 在当前类中指定转换的类型
	 * @param date 时间对象
	 * @return 指定字符格式
	 */
	public static String format(SimpleDateFormat formatType, Date date) {
		return formatType.format(date);
	}
	/**
	 * 将时间戳转换为 时间字符串
	 * @param formatType 在当前类中指定转换的类型
	 * @param time 时间戳
	 * @return 时间字符串
	 */
	public static String parseTimeStr(SimpleDateFormat formatType, String time) {
		Date date = DATE;
		date.setTime(Long.parseLong(time));
		return formatType.format(date);
	}
	/**
	 * 将时间戳转换为 时间字符串
	 * @param formatType 在当前类中指定转换的类型
	 * @param time 时间戳
	 * @return 时间字符串
	 */
	public static String parseTimeStr(SimpleDateFormat formatType, Long time) {
		Date date = DATE;
		date.setTime(time);
		return formatType.format(date);
	}
	/**
	 * 将时间戳转换成 时间对象
	 * @param formatType 在当前类中指定转换的类型
	 * @param time 时间戳
	 * @return 时间对象
	 */
	public static Date parseTimeDate(SimpleDateFormat formatType, Long time) {
		Date date = DATE;
		date.setTime(time);
		return date;
	}
	/**
	 * 将时间戳转换成 时间对象
	 * @param formatType 在当前类中指定转换的类型
	 * @param time 时间戳
	 * @return 时间对象
	 */
	public static Date parseTimeDate(SimpleDateFormat formatType, String time) {
		Date date = DATE;
		date.setTime(Long.parseLong(time));
		return date;
	}
}
