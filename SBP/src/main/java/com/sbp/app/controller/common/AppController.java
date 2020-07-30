package com.sbp.app.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbp.app.config.DefaultConfig;

@RestController // @Controller + @ResponseBody 的结合体：此注解修饰类 该类下所有方法全部返回jSON格式
public class AppController {
	// 读取配置文件的配置信息
	@Autowired
	private Environment env;// 读取配置文件属性的类
	@Value("${server.port}")// 注解方式注入配置属性
	private String port;
	
	@Autowired
	private DefaultConfig config;
	
	
	@GetMapping("/") // @RequestMapping(method=RequestMethod.GET)的简写版
	public String index() {
		return "Hello SBP!";
	}
	
	@GetMapping("/getServerPort")
	public List<Map<String,Object>> getServerPort() {
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		Map<String,Object> m = new HashMap<String,Object>();
		
		m.put("server.port", env.getProperty("server.port"));
		//m.put("default.email.serverStartTime", env.getProperty("default.email.serverStartTime"));
		m.put("default.email.serverStartTime", config.getServerStartTime());
		//m.put("default.email.enabled", env.getProperty("default.email.enabled"));
		m.put("default.email.enabled", config.getEnabled());
		//m.put("default.email.defaultSubject", env.getProperty("default.email.defaultSubject"));
		m.put("default.email.defaultSubject", config.getDefaultSubject());
		
		l.add(m);
		return l;// 读取不到返回空字符串
	}
	
}
