package com.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.modules.constant.FrameStaticAttribute;
import com.tool.DateUtil;
import com.tool.DomUtil;
import com.tool.Log4jUtil;
import com.tool.UrlParseUtil;

public class DispatcherServlet extends HttpServlet{
	//��ȡ��Ŀ·�� D:\****\**\WEB-INF\classes\ maven��Ŀ/resources/����Ŀ·�� ��Ŀ��·�� �����ǵ�ǰ���·��
	private String projectPath = this.getClass().getResource("/").getPath();
	private DomUtil dom = DomUtil.getInstance();
	private UrlParseUtil urlParse = UrlParseUtil.getInstance();
	public static final String PRODECT_PREFIX = "/SpringPro";
	Map<String, Method> methodMap = new HashMap<String, Method>();
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
		scanPath(projectPath+packages);
	}
	/**
	 * ɨ��path�����е�class�ļ�
	 * @param path
	 */
	public void scanPath(String path) {
		File file = new File(path);
		scanFile(file);
	}
	/**
	 * ��װִ����
	 * @param file
	 */
	public void scanFile(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				scanFile(f);
			}
		}else {
			String path = file.getPath();
			if(path.substring(path.lastIndexOf(".")).equals(".class")) {
				String tmpPath = path.replace(new File(projectPath).getPath()+"\\", "");
				String classPath = tmpPath.replaceAll("\\\\", ".");
				String className = classPath.substring(0, classPath.lastIndexOf("."));
				try {
					Class<?> clz = Class.forName(className);
					// �ҵ���Controllerע�����
					if(clz.isAnnotationPresent(Controller.class)) {
						RequestMapping requestMapping = clz.getAnnotation(RequestMapping.class);
						String classRequestMappingPath = "";
						if(requestMapping!=null) {
							classRequestMappingPath = requestMapping.value();
						}
						//��ȡɨ��ĵ�ǰ�ļ� ���з���
						for (Method method : clz.getMethods()) {
							RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
							if(methodRequestMapping!=null) {
								String methodRequestMappingPath = methodRequestMapping.value();
								//URIӳ�䵽���巽��
								methodMap.put(classRequestMappingPath+methodRequestMappingPath, method);
							}
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.scanPath(String path): ����ʧ�ܣ�"+path+" Err:"+e.getMessage());
				}
				
			}
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ӳ�䵽ִ�з���
		executeMappingMethod(req, resp);
	}
	/**
	 *����field������ ת���������
	 * @param field �������Զ���
	 * @param param �������
	 * @return ����field������ ת�������������
	 */
	public Object parseParam(Field field, String param) {
		if(field==null|param==null)return null;
		if(field.getType().equals(Integer.class)) {
			
			return Integer.parseInt(param);
			
		}else if(field.getType().equals(Double.class)) {
			
			return Double.parseDouble(param);
			
		}else if(field.getType().equals(Character.class)) {
			
			return new String().toCharArray()[0];
			
		}else if(field.getType().equals(Date.class)) {
			
			return DateUtil.parse(DateUtil.DATE_ALL, param);
			
		}else if(field.getType().equals(Float.class)) {
			
			return Float.parseFloat(param);
			
		}else if(field.getType().equals(Boolean.class)) {
			
			return Boolean.parseBoolean(param);
			
		}else if(field.getType().equals(Long.class)) {
			
			return Long.parseLong(param);
			
		}else {
			
			return param;
			
		}
	}
	/**
	 * �������еĲ�����װ��POJO����
	 * @param type POJO��
	 * @param req �������
	 * @return ��װ���POJO��
	 */
	public Object setPojo(Class<?> type, HttpServletRequest req) {
		Field[] fields = type.getDeclaredFields();
		Object pojo = null;
		try {
			pojo = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.setPojo(): ����ʧ��  Err:"+e.getMessage());
		}
		for (Field field : fields) {
			field.setAccessible(true);// ǿ�з�������
			//��ֵ
			String fieldName = field.getName();
			String requestParam = req.getParameter(fieldName);
			if(requestParam!=null) {
				// ת���ɲ�������
				Object value = parseParam(field,requestParam);
				
				String setMethodName = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
				Method setMethod;
				try {
					setMethod = type.getMethod("set"+setMethodName, field.getType());
					setMethod.invoke(pojo, value);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.setPojo(): �����ص�ʧ��  Err:"+e.getMessage());
				}
			}
		}
		return pojo;
	}
	/**
	 * ӳ�䵽ָ������
	 * @param req �������
	 * @param resp ��Ӧ����
	 */
	public void executeMappingMethod(HttpServletRequest req, HttpServletResponse resp) {
		String uri = req.getRequestURI();
		
		uri = uri.replace(PRODECT_PREFIX,"");
		
		Method mappingMethod = methodMap.get(uri);
		
		
		if(mappingMethod!=null) {
			Object pojo = null;
			try {
				pojo = mappingMethod.getDeclaringClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.executeMappingMethod(): ����ʧ��  Err:"+e.getMessage());
			}
			Parameter[] parameters = mappingMethod.getParameters();
			
			Object[] objects = new Object[parameters.length];
			
			for (int i = 0; i < parameters.length; i++) {
				// �������β�����
				String name = parameters[i].getName();
				// �����Ĳ�������
				Class<?> type = parameters[i].getType();
				
				if(type.equals(HttpServletRequest.class)) {// request
					
					objects[i] = req;
					
				}else if(type.equals(HttpServletResponse.class)) {// response
					
					objects[i] = resp;
					
				}else if(type.equals(String.class)) {
					
					objects[i] = req.getParameter(name);
					
				}else {// Pojo����
					// type �������� ���磺User, req �������
					objects[i] = setPojo(type, req);
				}
			}
			try {
				mappingMethod.invoke(pojo, objects);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.doPost(): �����ص�ʧ��  Err:"+e.getMessage());
			}
		}else {
			resp.setStatus(404);
		}
	}
}
