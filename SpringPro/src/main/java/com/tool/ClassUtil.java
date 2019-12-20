package com.tool;

public class ClassUtil {
	
	private ClassUtil() {}
	
	static class ClassUtilClass{
		private static ClassUtil classUtil = new ClassUtil();
	}
	public static ClassUtil getInstance() {
		return ClassUtilClass.classUtil;
	}
	
}
