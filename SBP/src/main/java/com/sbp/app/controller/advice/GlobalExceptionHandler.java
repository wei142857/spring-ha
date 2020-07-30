package com.sbp.app.controller.advice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbp.app.entity.ResponseDate;

/**
 * @ControllerAdvice 增强Controller
 * - 1.定义全局处理
 * @author weiqz
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * @ExceptionHandler(value = Exception.class) 全局异常处理
	 * - 1.指定需要统一处理项目的哪个异常
	 * - 2.instanceof 对不同异常进行处理
	 * - 3.value 处理的异常类型
	 * @return 统一异常返回数据
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseDate defaultErrorHandler(HttpServletRequest req, Exception e) {
		ResponseDate r = new ResponseDate();
		logger.error("",e);
		r.setMessage(e.getMessage());
		if(e instanceof org.springframework.web.servlet.NoHandlerFoundException)
			r.setCode(404);
		else
			r.setCode(500);
		r.setData(null);
		r.setStatus(false);
		return r;
	}
	
	/**
	 * @ModelAttribute(name = "***") 全局数据绑定
	 * - 1.初始化数据，使每一个Controller都能访问到指定数据
	 * - 2.name全局数据中的key值，可以通过在形参中添加 Model 来访问这个key
	 * @return
	 */
	@ModelAttribute(name = "base_attr")
	public Map<String, Object> baseAttr(){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("server_timpstamp", new Date().getTime());
		return m;
	}
	
	/**
	 * @InitBinder("***") 全局数据预处理
	 * 如果前端同时传入书的名称name和作者的名称name，后端则无法区分到底是哪一个实体类的name
	 * 可以通过数据预处理的方式，例如：
	 * - 1. 在controller的形参中加入@ModelAttribute("book") Book book, @ModelAttribute("author") Author author
	 * - 2.请求参数加上前缀如：https:****.com?book.name=***&author.name=***即可区分
	 */
	@InitBinder("book")
	public void book(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("book.");
	}
	@InitBinder("author")
	public void Author(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("author.");
	}

}
