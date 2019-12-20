package com.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jUtil {
	public Logger log = LoggerFactory.getLogger(Log4jUtil.class);
	private Log4jUtil() {}
	static class Log4jClass{
		private static Log4jUtil log4j = new Log4jUtil();
	}
	public static Log4jUtil getInstance() {
		return Log4jClass.log4j;
	}
}
