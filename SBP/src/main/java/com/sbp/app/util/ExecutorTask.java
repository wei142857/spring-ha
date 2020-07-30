package com.sbp.app.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sbp.app.config.ThreadPoolConfig;

@Configuration
@EnableAsync
public class ExecutorTask {
	private Logger logger  = Logger.getLogger(ExecutorTask.class);
	
	@Autowired
	private ThreadPoolConfig config;
	
	// 别名 @Bean(name = { "dataSource", "subsystemA-dataSource", "subsystemB-dataSource" })
	@Bean(name = "taskExecutor")// 注入一个Bean 默认id为方法名 可以通过name指定
	@Description("默认线程池ThreadPoolTaskExecutor")
	public Executor detaultThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
		executor.setCorePoolSize(config.getCorePoolSize());
		executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
		executor.setQueueCapacity(config.getQueueCapacity());
		executor.setMaxPoolSize(config.getMaxPoolSize());
		executor.setThreadNamePrefix(config.getThreadNamePrefix());
		executor.setAwaitTerminationSeconds(config.getAwaitTerminationSeconds());
		
		/**
		 * rejection-policy (拒绝政策) : 当池已经达到MaxSize的时候，使用什么政策
		 * CALLER_RUNS-policy (调用者运行)  : 调用者所在线程自己执行
		 */
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		
		executor.initialize();// 初始化
		
		logger.info("ThreadPoolTaskExecutor be initialize");
		
		return executor;
	}
	
	@Bean(name = "default_pool")// 注入一个Bean 默认id为方法名 可以通过name指定
	@Description("默认线程池ThreadPoolTaskExecutor")
	public Executor asyncServiceExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
		executor.setCorePoolSize(config.getCorePoolSize());
		executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
		executor.setQueueCapacity(config.getQueueCapacity());
		executor.setMaxPoolSize(config.getMaxPoolSize());
		executor.setThreadNamePrefix(config.getThreadNamePrefix());
		executor.setAwaitTerminationSeconds(config.getAwaitTerminationSeconds());
		
		/**
		 * rejection-policy (拒绝政策) : 当池已经达到MaxSize的时候，使用什么政策
		 * CALLER_RUNS-policy (调用者运行)  : 调用者所在线程自己执行
		 */
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		
		executor.initialize();// 初始化
		
		logger.info("ThreadPoolTaskExecutor be initialize");
		
		return executor;
	}
}
