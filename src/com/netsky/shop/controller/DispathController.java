package com.netsky.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.util.ConvertUtil;

@Controller
public class DispathController {

	@RequestMapping("/dispath.do")
	public ModelAndView dispath(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String redirect = ConvertUtil.toString(request.getParameter("redirect"));
		String url = ConvertUtil.toString(request.getParameter("url"));
		if (!"yes".equals(redirect)) {
			return new ModelAndView("WEB-INF/view/" + url);
		} else {
			return new ModelAndView("redirect:redirect.do?url=" + url);
		}
	}

	@RequestMapping("/redirect.do")
	public ModelAndView redirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("WEB-INF/view/" + ConvertUtil.toString(request.getParameter("url")));
	}
}
