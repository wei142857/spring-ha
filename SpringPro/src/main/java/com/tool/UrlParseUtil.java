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
	 * 转换文件系统中的路径
	 * @param path 文件系统路径
	 * @return 转换错误返回null 转换成功返回成功字符串
	 */
	public String parsePath(String path) {
		String tmpPath = null;
		try {
			tmpPath = path.replaceAll("%20", " ");
		} catch (Exception e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.UrlFormat.formatPath(String message): 字符替换异常："+path+" "+e.getMessage());
		}
		byte[] bytes = null;
		try {
			bytes = tmpPath.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.UrlFormat.formatPath(String message): 转换错误异常："+path+" "+e.getMessage());
		}
		return new String(bytes);
	}
}
