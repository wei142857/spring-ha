package com.sbp.app.config;

import java.util.Date;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Spring 宽松绑定规则 (relaxed binding)
 * 
 * default.email.serverStartTime
 * default.email.serverstarttime
 * default.email.server_start_time
 * default.email.server_Start_Time
 * default.email.server_STAET_TIME
 * - 以上变体都将绑定到 serverStartTime 属性上
 * 
 * 参考文章 https://www.jianshu.com/p/7f75936b573b
 * 
 * @ConfigurationProperties
 * - 为每个要捕获的外部属性（配置文件属性）提供一个带有字段的类
 * 
 * prefix 
 * 		  -	前缀定义了哪些配置文件属性将被绑定到类的字段上
 * 		  -	根据 Spring Boot 宽松的绑定规则，类的属性名称必须与外部属性（配置文件属性）的名称匹配
 * 		  -	我们可以简单地用一个值初始化一个字段来定义一个默认值
 * 		  -	类本身可以是包私有的
 * 		  -	类的字段必须有公共 setter 方法
 * 
 * 
 * ignoreInvalidFields(翻译：忽略无效字段) 默认为 false
 * -如果配置文件配置的属性和类中属性类型不匹配如：
 * -配置文件default.email.enabled=foo而类中enabled的类型为Boolean程序启动会报错
 * -如果设置为true，配置文件内的配置无效则会使用类属性的默认值,否则配置文件属性会覆盖类中的属性
 * 
 * ignoreUnknownFields 默认为true
 * -默认情况下SpringBoot会忽略不能被绑定到@ConfigurationProperties类字段上的属性
 * -如果想如果存在没有被绑定到类字段上的属性时启动失败，ignoreUnknownFields设为false
 * 
 * 
 * 
 * @author weiqz
 */
@ConfigurationProperties(prefix = "default.email", ignoreInvalidFields=true, ignoreUnknownFields=false)// 用于自定义配置文件：prefix - 参数前缀
@Component //使ComponentScan扫描到
public class DefaultConfig {
	private Date serverStartTime = new Date();
	private Boolean enabled = Boolean.TRUE;// 配置文件中的配置会覆盖这个默认赋值
	private String defaultSubject;
	
	public Date getServerStartTime() {
		return serverStartTime;
	}
	public void setServerStartTime(Date serverStartTime) {
		this.serverStartTime = serverStartTime;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getDefaultSubject() {
		return defaultSubject;
	}
	public void setDefaultSubject(String defaultSubject) {
		this.defaultSubject = defaultSubject;
	}
	
}
