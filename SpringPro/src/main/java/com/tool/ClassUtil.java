package com.tool;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.annotation.Controller;
import com.annotation.RequestMapping;

public class ClassUtil {
	public static final String CLASS_FILE_SUFFIX = ".class";
	
	Map<String, Method> methodMap = new HashMap<String, Method>();
	
	private String projectPath = this.getClass().getResource("/").getPath();
	private ClassUtil() {}
	
	static class ClassUtilClass{
		private static ClassUtil classUtil = new ClassUtil();
	}
	public static ClassUtil getInstance() {
		return ClassUtilClass.classUtil;
	}
	/**
	 * 扫描path下所有的class文件
	 * @param path
	 */
	public void scanPath(String path) {
		File file = new File(path);
		scanFile(file);
	}
	public void scanFile(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				scanFile(f);
			}
		}else {
			String path = file.getPath();
			if(path.substring(path.lastIndexOf(".")).equals(CLASS_FILE_SUFFIX)) {
				String tmpPath = path.replaceAll(new File(projectPath).getPath()+"\\", "");
				String classPath = tmpPath.replaceAll("\\\\", ".");
				String className = classPath.substring(0, classPath.lastIndexOf("."));
				try {
					Class<?> clz = Class.forName(className);
					// 找到有Controller注解的类
					if(clz.isAnnotationPresent(Controller.class)) {
						RequestMapping requestMapping = clz.getAnnotation(RequestMapping.class);
						String classRequestMappingPath = requestMapping.value();
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
}
