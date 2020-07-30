package com.sbp.app.service.impl;

import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.sbp.app.service.AsyncTaskService;

@Service
public class AsyncLoggerTaskServiceImpl implements AsyncTaskService{
	private Logger logger = Logger.getLogger(AsyncLoggerTaskServiceImpl.class);
	
	@Override
	@Async("default_pool")// 括号内指定使用的线程池，如果未找到直接报错，如果缺省时springboot会优先使用名称为'taskExecutor'的线程池，如果没有找到，才会使用其他类型为TaskExecutor或其子类的线程池。
	public void executeAsyncTask() throws InterruptedException {
		Thread.sleep(1000);
		logger.info(Thread.currentThread().getName()+" to do the new task");
	}
	@Override
	@Async // springboot会优先使用名称为'taskExecutor'的线程池，如果没有找到，才会使用其他类型为TaskExecutor或其子类的线程池。
	public ListenableFuture<String> hasReaultAsyncTask(String str) throws InterruptedException{
		Thread.sleep(5000);
		logger.info("hasReaultAsyncTask param is "+str);
		return new AsyncResult<String>(str);
	}
	
}
