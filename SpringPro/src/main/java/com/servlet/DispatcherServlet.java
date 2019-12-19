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
	//获取项目路径 D://****/**/WEB-INF/classes/ maven项目/resources/即项目路径 项目根路径 而不是当前类根路径
	private String projectPath = this.getClass().getResource("/").getPath();
	private DomUtil dom = DomUtil.getInstance();
	private UrlParseUtil urlParse = UrlParseUtil.getInstance();
	//初始化框架
	/**
	 *  容器初始化 加载web.xml 根据web.xml中的servlet找到当前映射
	 *  1.映射标签中的init-param标签中的参数会封装在ServletConfig对象中
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		String tmpPath = config.getInitParameter(FrameStaticAttribute.DISPACHER_SERVLET_XML_CONFIG_PATH);
		String xmlPath = urlParse.parsePath(projectPath+"/"+tmpPath);
		Document document = dom.parse(xmlPath);
		Element beans = document.getRootElement();
		/**扫描包标签*/
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
