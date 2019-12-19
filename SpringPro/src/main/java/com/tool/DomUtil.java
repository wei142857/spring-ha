package com.tool;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DomUtil {
	// 扫描包标签名
	public static final String XML_ELEMENT_SCAN = "compentscan";
	public static final String XML_ATTR_PACKAGES = "packages";
	
	private SAXReader saxReader = new SAXReader();
	private DomUtil() {}
	static class DomUtilClass{
		private static DomUtil dom = new DomUtil();
	}
	public static DomUtil getInstance() {
		return DomUtilClass.dom;
	}
	/**
	 * 读取文件
	 * @param path 系统文件路径
	 * @return null解析失败 解析成功返回Document对象
	 */
	public Document parse(String path) {
		try {
			return saxReader.read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.DomUtil.parse(String path): 读取不到文件："+path+" Err:"+e.getMessage());
		}
		return null;
	}
	public void getElemant(String element) {
		
	}
}
