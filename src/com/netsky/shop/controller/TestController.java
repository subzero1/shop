package com.netsky.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.dao.BaseDao;

@Controller
public class TestController {

	@Autowired
	private BaseDao dao;

	@RequestMapping("/test1.do")
	public ModelAndView test1(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/WEB-INF/view/test.jsp");
	}

}
