package com.sbp.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("server.thread.pool")
@Component
public class ThreadPoolConfig {
	
	private Integer corePoolSize = 5;// 核心线程数
	private Integer maxPoolSize = 20;// 最大池大小
	private Integer queueCapacity = 80;// 队列长度
	private Integer keepAliveSeconds = 10;// 线程池维护线程所允许的空闲时间
	private String threadNamePrefix = "ThreadPool---";// 线程名称前缀
	private Boolean waitForTasksToCompleteOnShutdown = Boolean.TRUE;// 线程执行完才会死亡
	private Integer awaitTerminationSeconds = 10;// 死亡时间
	
	public Integer getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(Integer corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public Integer getQueueCapacity() {
		return queueCapacity;
	}
	public void setQueueCapacity(Integer queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
	public Integer getKeepAliveSeconds() {
		return keepAliveSeconds;
	}
	public void setKeepAliveSeconds(Integer keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}
	public String getThreadNamePrefix() {
		return threadNamePrefix;
	}
	public void setThreadNamePrefix(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
	}
	public Boolean getWaitForTasksToCompleteOnShutdown() {
		return waitForTasksToCompleteOnShutdown;
	}
	public void setWaitForTasksToCompleteOnShutdown(Boolean waitForTasksToCompleteOnShutdown) {
		this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
	}
	public Integer getAwaitTerminationSeconds() {
		return awaitTerminationSeconds;
	}
	public void setAwaitTerminationSeconds(Integer awaitTerminationSeconds) {
		this.awaitTerminationSeconds = awaitTerminationSeconds;
	}
}
