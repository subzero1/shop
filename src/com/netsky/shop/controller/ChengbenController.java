package com.netsky.shop.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl06_chengben;
import com.netsky.shop.service.QueryService;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.ResultObject;

@Controller
public class ChengbenController {

	@Autowired
	private BaseDao dao;
	@Autowired
	private QueryService queryService;

	@RequestMapping("/chengbenList.do")
	public ModelAndView chengbenList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		String tbl11_name = "chengben";
		modelMap.put("chengben_titleList", queryService.getTitleList(tbl11_name,null));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tbl11_name", tbl11_name);
		dataMap.put("pageRowSize", -1);
		dataMap.put("pageNum", -1);
		ResultObject ro = queryService.getResultObject(dataMap);
		modelMap.put("chengben_list", ro.getResultList());
		modelMap.put("pageNum", ro.getPageNum());
		modelMap.put("selled_zongjia", dao.searchFirst("select sum(xiaoshou.price*xiaoshou.gmsl) from Tbl04_xiaoshou xiaoshou"));
		modelMap.put("selled_chengben", dao.searchFirst("select sum(xiaoshou.chengben*xiaoshou.gmsl) from Tbl04_xiaoshou xiaoshou"));
		modelMap.put("other_chengben", dao.searchFirst("select sum(money) from Tbl06_chengben"));
		modelMap.put("stored_zongjia", dao.searchFirst("select sum(shangpin.price*shangpin.kcsl) from Tbl01_shangpin shangpin"));
		modelMap.put("stored_chengben", dao.searchFirst("select sum(shangpin.chengben*shangpin.kcsl) from Tbl01_shangpin shangpin"));
		return new ModelAndView("/WEB-INF/view/chengben.jsp", modelMap);
	}

	@RequestMapping("/getChengbenAjax.do")
	public void getChengbenAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html,charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer msg = new StringBuffer();
		Tbl06_chengben chengben = (Tbl06_chengben) dao.searchById(Tbl06_chengben.class, ConvertUtil.toInteger(request
				.getParameter("tbl06_id")));
		if (chengben != null) {
			out.append(chengben.getId() + ",");
			out.append(new SimpleDateFormat("yyyy-MM-dd").format(chengben.getDate()) + ",");
			out.append(chengben.getName() + ",");
			out.append(ConvertUtil.toString(new BigDecimal(chengben.getMoney()).setScale(2, BigDecimal.ROUND_HALF_UP)));
		} else {
			msg.append("fail");
		}
		out.print(msg);
	}

	@RequestMapping("/delchengben.do")
	public ModelAndView delchengben(HttpServletRequest request, HttpServletResponse response) throws Exception {
		dao.delete(Tbl06_chengben.class, ConvertUtil.toInteger(request.getParameter("tbl06_id")));
		return new ModelAndView("/chengbenList.do");
	}
}
