package com.tool;

import java.io.UnsupportedEncodingException;

public class UrlParseUtil {
	static class UrlFormatClass{
		private static UrlParseUtil urlFormat = new UrlParseUtil();
	}
	private UrlParseUtil() {
	}
	public static UrlParseUtil getInstance() {
		return UrlFormatClass.urlFormat;
	}
	/**
	 * ת���ļ�ϵͳ�е�·��
	 * @param path �ļ�ϵͳ·��
	 * @return ת�����󷵻�null ת���ɹ����سɹ��ַ���
	 */
	public String parsePath(String path) {
		String tmpPath = null;
		try {
			tmpPath = path.replaceAll("%20", " ");
		} catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.UrlFormat.formatPath(String message): �ַ��滻�쳣��"+path+" "+e.getMessage());
		}
		byte[] bytes = null;
		try {
			bytes = tmpPath.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.UrlFormat.formatPath(String message): ת�������쳣��"+path+" "+e.getMessage());
		}
		return new String(bytes);
	}
}
