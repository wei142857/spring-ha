package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.pojo.User;
/**
 * 学习网址：https://www.bilibili.com/video/av73960323 当前看到 32:10
 * @author weiqz
 *
 */
@Controller
public class IndexController {
	@RequestMapping("/index")
	public Object index(String time, HttpServletRequest req, HttpServletResponse resp, User user){
		return null;
	}
}
