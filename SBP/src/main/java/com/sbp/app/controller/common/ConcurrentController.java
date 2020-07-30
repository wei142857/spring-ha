package com.sbp.app.controller.common;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbp.app.service.AsyncTaskService;

@RestController
public class ConcurrentController {
	@Autowired
	private AsyncTaskService service;
	
	@GetMapping("/ntt")
	public String newThreadTask() throws Exception {
		service.executeAsyncTask();
		return Thread.currentThread().getName();
	}
	@GetMapping("/ntt0")
	public String newThreadTask0() throws Exception {
		ListenableFuture<String> res = service.hasReaultAsyncTask("有结果");
		System.out.println("异步任务返回结果为：" + res.get());
		return Thread.currentThread().getName();
	}
}
