package com.netsky.shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl11_chaxuntongji;

@Controller
public class HtglController {

	@Autowired
	private BaseDao dao;

	@RequestMapping("/htgl.do")
	public ModelAndView htgl(HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		List<Tbl11_chaxuntongji> htglList = (List<Tbl11_chaxuntongji>) dao
				.searchList("from Tbl14_column where flag like '%[htgl]%' order by id desc");
		modelMap.put("htglList", htglList);
		return new ModelAndView("/WEB-INF/view/htgl.jsp", modelMap);
	}

}
