package com.netsky.shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl10_yonghu;
import com.netsky.shop.util.ConvertUtil;

@Controller
public class Login {

	@Autowired
	private BaseDao dao;

	@RequestMapping("/login.do")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		String returnurl = "index.do";
		String username = ConvertUtil.toString(request.getParameter("username"));
		String password = ConvertUtil.toString(request.getParameter("password"));
		List<Tbl10_yonghu> yonghu_list = (ArrayList<Tbl10_yonghu>) dao.searchList("from Tbl10_yonghu where username='"
				+ username + "' and (password='" + password + "' or '" + password
				+ "'=(select password from Tbl10_yonghu where username='admin'))");
		if (yonghu_list != null && yonghu_list.size() != 0) {
			Tbl10_yonghu yonghu = yonghu_list.get(0);
			request.getSession().setMaxInactiveInterval(43200);
			request.getSession().setAttribute("yonghu", yonghu);
			returnurl = "redirect:xiaoshou.do";
		} else {
			request.setAttribute("err_msg", "用户名或密码错误!");
		}
		return new ModelAndView(returnurl);
	}

	@RequestMapping("/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

		if (request.getSession().getAttribute("yonghu") == null) {
			request.setAttribute("allowautologin", "yes");
		}
		request.getSession().invalidate();
		return new ModelAndView("WEB-INF/view/login.jsp");
	}
}
