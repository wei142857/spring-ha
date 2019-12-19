package com.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import com.modules.constant.FrameStaticAttribute;
import com.tool.DomUtil;
import com.tool.UrlParseUtil;

public class DispatcherServlet extends HttpServlet{
	//��ȡ��Ŀ·�� D://****/**/WEB-INF/classes/ maven��Ŀ/resources/����Ŀ·�� ��Ŀ��·�� �����ǵ�ǰ���·��
	private String projectPath = this.getClass().getResource("/").getPath();
	private DomUtil dom = DomUtil.getInstance();
	private UrlParseUtil urlParse = UrlParseUtil.getInstance();
	//��ʼ�����
	/**
	 *  ������ʼ�� ����web.xml ����web.xml�е�servlet�ҵ���ǰӳ��
	 *  1.ӳ���ǩ�е�init-param��ǩ�еĲ������װ��ServletConfig������
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		String tmpPath = config.getInitParameter(FrameStaticAttribute.DISPACHER_SERVLET_XML_CONFIG_PATH);
		String xmlPath = urlParse.parsePath(projectPath+"/"+tmpPath);
		Document document = dom.parse(xmlPath);
		Element beans = document.getRootElement();
		/**ɨ�����ǩ*/
		Element compentScan = beans.element(DomUtil.XML_ELEMENT_SCAN);
		String packages = compentScan.attribute(DomUtil.XML_ATTR_PACKAGES).getValue();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
