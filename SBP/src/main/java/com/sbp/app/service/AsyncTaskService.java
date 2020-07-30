package com.sbp.app.service;

import org.springframework.util.concurrent.ListenableFuture;

public interface AsyncTaskService {
	void executeAsyncTask() throws Exception;
	ListenableFuture<String> hasReaultAsyncTask(String str) throws Exception;
}
