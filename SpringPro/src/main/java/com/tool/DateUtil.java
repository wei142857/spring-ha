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
	 * 	���ַ���ת��ΪDate
	 * @param parseType �ڵ�ǰ����ָ��ת��������
	 * @param dateStr ʱ���ַ���
	 * @return Date����
	 */
	public static Date parse(SimpleDateFormat parseType, String dateStr) {
		try {
			return parseType.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.modules.constant.DateAttribute.parse(SimpleDateFormat parseType, String dateStr): ʱ��ת��ʧ�ܣ�"+dateStr+" Err:"+e.getMessage());
		}
		return null;
	}
	/**
	 * ��ʱ�� ת��Ϊ�ַ���
	 * @param formatType �ڵ�ǰ����ָ��ת��������
	 * @param date ʱ�����
	 * @return ָ���ַ���ʽ
	 */
	public static String format(SimpleDateFormat formatType, Date date) {
		return formatType.format(date);
	}
	/**
	 * ��ʱ���ת��Ϊ ʱ���ַ���
	 * @param formatType �ڵ�ǰ����ָ��ת��������
	 * @param time ʱ���
	 * @return ʱ���ַ���
	 */
	public static String parseTimeStr(SimpleDateFormat formatType, String time) {
		Date date = DATE;
		date.setTime(Long.parseLong(time));
		return formatType.format(date);
	}
	/**
	 * ��ʱ���ת��Ϊ ʱ���ַ���
	 * @param formatType �ڵ�ǰ����ָ��ת��������
	 * @param time ʱ���
	 * @return ʱ���ַ���
	 */
	public static String parseTimeStr(SimpleDateFormat formatType, Long time) {
		Date date = DATE;
		date.setTime(time);
		return formatType.format(date);
	}
	/**
	 * ��ʱ���ת���� ʱ�����
	 * @param formatType �ڵ�ǰ����ָ��ת��������
	 * @param time ʱ���
	 * @return ʱ�����
	 */
	public static Date parseTimeDate(SimpleDateFormat formatType, Long time) {
		Date date = DATE;
		date.setTime(time);
		return date;
	}
	/**
	 * ��ʱ���ת���� ʱ�����
	 * @param formatType �ڵ�ǰ����ָ��ת��������
	 * @param time ʱ���
	 * @return ʱ�����
	 */
	public static Date parseTimeDate(SimpleDateFormat formatType, String time) {
		Date date = DATE;
		date.setTime(Long.parseLong(time));
		return date;
	}
}
