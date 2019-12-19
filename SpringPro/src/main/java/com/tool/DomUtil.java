package com.tool;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DomUtil {
	// ɨ�����ǩ��
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
	 * ��ȡ�ļ�
	 * @param path ϵͳ�ļ�·��
	 * @return null����ʧ�� �����ɹ�����Document����
	 */
	public Document parse(String path) {
		try {
			return saxReader.read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.DomUtil.parse(String path): ��ȡ�����ļ���"+path+" Err:"+e.getMessage());
		}
		return null;
	}
	public void getElemant(String element) {
		
	}
}
