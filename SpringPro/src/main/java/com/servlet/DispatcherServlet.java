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
	//获取项目路径 D:\****\**\WEB-INF\classes\ maven项目/resources/即项目路径 项目根路径 而不是当前类根路径
	private String projectPath = this.getClass().getResource("/").getPath();
	private DomUtil dom = DomUtil.getInstance();
	private UrlParseUtil urlParse = UrlParseUtil.getInstance();
	public static final String PRODECT_PREFIX = "/SpringPro";
	Map<String, Method> methodMap = new HashMap<String, Method>();
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
		scanPath(projectPath+packages);
	}
	/**
	 * 扫描path下所有的class文件
	 * @param path
	 */
	public void scanPath(String path) {
		File file = new File(path);
		scanFile(file);
	}
	/**
	 * 封装执行链
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
					// 找到有Controller注解的类
					if(clz.isAnnotationPresent(Controller.class)) {
						RequestMapping requestMapping = clz.getAnnotation(RequestMapping.class);
						String classRequestMappingPath = "";
						if(requestMapping!=null) {
							classRequestMappingPath = requestMapping.value();
						}
						//获取扫描的当前文件 所有方法
						for (Method method : clz.getMethods()) {
							RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
							if(methodRequestMapping!=null) {
								String methodRequestMappingPath = methodRequestMapping.value();
								//URI映射到具体方法
								methodMap.put(classRequestMappingPath+methodRequestMappingPath, method);
							}
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.scanPath(String path): 反射失败："+path+" Err:"+e.getMessage());
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
		// 映射到执行方法
		executeMappingMethod(req, resp);
	}
	/**
	 *根据field得类型 转换请求参数
	 * @param field 反射属性对象
	 * @param param 请求参数
	 * @return 根据field得类型 转换请求参数返回
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
	 * 把请求中的参数封装到POJO类中
	 * @param type POJO类
	 * @param req 请求对象
	 * @return 封装后的POJO类
	 */
	public Object setPojo(Class<?> type, HttpServletRequest req) {
		Field[] fields = type.getDeclaredFields();
		Object pojo = null;
		try {
			pojo = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.setPojo(): 反射失败  Err:"+e.getMessage());
		}
		for (Field field : fields) {
			field.setAccessible(true);// 强行访问属性
			//赋值
			String fieldName = field.getName();
			String requestParam = req.getParameter(fieldName);
			if(requestParam!=null) {
				// 转换成参数类型
				Object value = parseParam(field,requestParam);
				
				String setMethodName = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
				Method setMethod;
				try {
					setMethod = type.getMethod("set"+setMethodName, field.getType());
					setMethod.invoke(pojo, value);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.setPojo(): 方法回调失败  Err:"+e.getMessage());
				}
			}
		}
		return pojo;
	}
	/**
	 * 映射到指定方法
	 * @param req 请求对象
	 * @param resp 响应对象
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
				Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.executeMappingMethod(): 反射失败  Err:"+e.getMessage());
			}
			Parameter[] parameters = mappingMethod.getParameters();
			
			Object[] objects = new Object[parameters.length];
			
			for (int i = 0; i < parameters.length; i++) {
				// 方法的形参名称
				String name = parameters[i].getName();
				// 方法的参数类型
				Class<?> type = parameters[i].getType();
				
				if(type.equals(HttpServletRequest.class)) {// request
					
					objects[i] = req;
					
				}else if(type.equals(HttpServletResponse.class)) {// response
					
					objects[i] = resp;
					
				}else if(type.equals(String.class)) {
					
					objects[i] = req.getParameter(name);
					
				}else {// Pojo类型
					// type 参数类型 比如：User, req 请求对象
					objects[i] = setPojo(type, req);
				}
			}
			try {
				mappingMethod.invoke(pojo, objects);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				Log4jUtil.getInstance().log.debug("com.tool.ClassUtil.doPost(): 方法回调失败  Err:"+e.getMessage());
			}
		}else {
			resp.setStatus(404);
		}
	}
}
