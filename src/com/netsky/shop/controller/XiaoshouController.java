package com.netsky.shop.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl01_shangpin;
import com.netsky.shop.domain.base.Tbl03_huiyuan;
import com.netsky.shop.domain.base.Tbl04_xiaoshou;
import com.netsky.shop.domain.base.Tbl05_leixing;
import com.netsky.shop.domain.base.Tbl10_yonghu;
import com.netsky.shop.service.QueryService;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.ResultObject;

@Controller
public class XiaoshouController {

	@Autowired
	private BaseDao dao;
	@Autowired
	private QueryService queryService;

	@RequestMapping("/xiaoshou.do")
	public ModelAndView xiaoshou(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		// 锟斤拷锟斤拷

		return new ModelAndView("/WEB-INF/view/xiaoshou.jsp", modelMap);
	}

	@RequestMapping("/clear.do")
	public ModelAndView clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute("xiaoshouList");
		return new ModelAndView("redirect:/xiaoshou.do");
	}

	@RequestMapping("/jiezhang.do")
	public ModelAndView jiezhang(HttpServletRequest request, HttpServletResponse response) {
		// 锟斤拷没锟叫伙拷员锟斤拷
		Date now = new Date();
		String cardnumber = ConvertUtil.toString(request.getParameter("card"));
		Tbl03_huiyuan huiyuan = (Tbl03_huiyuan) dao.searchFirst("from Tbl03_huiyuan where number='"
				+ cardnumber.toUpperCase() + "'");
		Integer tbl03_id = null;
		if (huiyuan != null) {
			tbl03_id = huiyuan.getId();
		}
		Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Tbl10_yonghu yonghu = (Tbl10_yonghu) request.getSession().getAttribute("yonghu");
			if (yonghu == null) {
				yonghu = new Tbl10_yonghu();
				yonghu.setUsername("mengying");
				yonghu.setId(1);
				
			}
			Double jifen = 0D;
			// 锟斤拷锟斤拷锟斤拷奂锟铰硷拷锟斤拷锟斤拷倏锟斤拷锟斤拷
			List<Object[]> xiaoshouList = (List<Object[]>) request.getSession().getAttribute("xiaoshouList");
			for (Object[] objects : xiaoshouList) {
				((Tbl04_xiaoshou) objects[1]).setTbl03_id(tbl03_id);
				((Tbl04_xiaoshou) objects[1]).setTbl10_id(yonghu.getId());
				((Tbl04_xiaoshou) objects[1]).setTime(now);
				((Tbl04_xiaoshou) objects[1]).setPrice(((Tbl01_shangpin) objects[0]).getPrice());
				((Tbl04_xiaoshou) objects[1]).setChengben(((Tbl01_shangpin) objects[0]).getChengben());
				((Tbl01_shangpin) objects[0]).setKcsl(((Tbl01_shangpin) objects[0]).getKcsl()
						- ((Tbl04_xiaoshou) objects[1]).getGmsl());
				if (huiyuan != null) {
					// 锟斤拷锟姐本锟轿的伙拷锟�
					if (((Tbl05_leixing) objects[2]).getFlag() == null
							|| ((Tbl05_leixing) objects[2]).getFlag().indexOf("[bujifen]") == -1) {
						jifen += ((Tbl04_xiaoshou) objects[1]).getGmsl() * ((Tbl01_shangpin) objects[0]).getPrice();
					}
				}
				session.save(((Tbl04_xiaoshou) objects[1]));
				session.saveOrUpdate(((Tbl01_shangpin) objects[0]));
			}
			// 锟斤拷锟斤拷谢锟皆憋拷锟� 锟斤拷踊锟皆憋拷锟斤拷锟斤拷
			if (huiyuan != null) {
				if ("未激活".equals(huiyuan.getStatus())) {
					huiyuan.setStatus("已激活");
					huiyuan.setJhrq(now);
				}
				huiyuan.setLjjf(huiyuan.getLjjf() + jifen);
				huiyuan.setDqjf(huiyuan.getDqjf() + jifen);
				session.saveOrUpdate(huiyuan);
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		request.getSession().removeAttribute("xiaoshouList");
		return new ModelAndView("redirect:/xiaoshou.do");
	}

	@RequestMapping("/good.do")
	public ModelAndView good(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		String tbl11_name = "shangpin";
		String zero = ConvertUtil.toString(request.getParameter("zero"));
		modelMap.put("shangpin_titleList", queryService.getTitleList(tbl11_name, null));
		String keyword = ConvertUtil.toString(request.getParameter("keyword"));
		Integer pageRowSize = ConvertUtil.toInteger(request.getParameter("pageRowSize"), 8);
		Integer pageNum = ConvertUtil.toInteger(request.getParameter("pageNum"), 1);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tbl11_name", tbl11_name);
		dataMap.put("pageRowSize", pageRowSize);
		dataMap.put("pageNum", pageNum);
		dataMap.put("keyword", keyword);
		if (zero.equals("0")){
			dataMap.put("where", " and shangpin.kcsl!=0");
		}
		ResultObject ro = queryService.getResultObject(dataMap);
		modelMap.put("shangpin_list", ro.getResultList());
		modelMap.put("pageNum", ro.getPageNum());
		modelMap.put("totalPages", ro.getTotalPages());
		modelMap.put("totalRows", ro.getTotalRows());
		modelMap.put("pageRowSize", pageRowSize);
		return new ModelAndView("/WEB-INF/view/good.jsp", modelMap);
	}

	@RequestMapping("/buy.do")
	public ModelAndView buy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<Object[]> xiaoshouList = (List<Object[]>) request.getSession().getAttribute("xiaoshouList");
		if (xiaoshouList == null) {
			xiaoshouList = new ArrayList<Object[]>();
		}
		String timeStamp = (String) request.getSession().getAttribute("timeStamp");
		if (timeStamp == null || "".equals(timeStamp)
				|| !timeStamp.equals(ConvertUtil.toString(request.getParameter("timeStamp")))) {
			String tbl01_number = ConvertUtil.toString(request.getParameter("number"));
			Tbl01_shangpin shangpin = null;
			shangpin = (Tbl01_shangpin) dao.searchFirst("from Tbl01_shangpin where number='" + tbl01_number + "'");
			if (shangpin != null) {
				Tbl05_leixing leixing = (Tbl05_leixing) dao.searchById(Tbl05_leixing.class, shangpin.getTbl05_id());
				boolean inthecart = false;
				for (Object[] o : xiaoshouList) {
					if (((Tbl01_shangpin) o[0]).getNumber().equals(tbl01_number)) {
						if (((Tbl01_shangpin) o[0]).getKcsl().intValue() < ((Tbl04_xiaoshou) o[1]).getGmsl() + 1) {
							modelMap.put("msg", "库存不足！");
							break;
						}
						((Tbl04_xiaoshou) o[1]).setGmsl(((Tbl04_xiaoshou) o[1]).getGmsl() + 1);
						inthecart = true;
						break;
					}
				}
				if (!inthecart) {
					if (shangpin.getKcsl() > 0) {
						Tbl04_xiaoshou xiaoshou = new Tbl04_xiaoshou();
						xiaoshou.setGmsl(1);
						xiaoshou.setTbl01_id(shangpin.getId());
						Object[] o = new Object[] { shangpin, xiaoshou, leixing };
						xiaoshouList.add(0, o);
					} else {
						modelMap.put("msg", "库存不足！");
					}
				}
				request.getSession().setAttribute("xiaoshouList", xiaoshouList);
			}else if (!tbl01_number.equals("")){
				modelMap.put("msg", "没有找到该商品,商品编码为："+tbl01_number);
			}
		}
		Double sum_money = 0D;
		Long sum_count = 0L;
		for (Object[] o : xiaoshouList) {
			sum_money += ((Tbl01_shangpin) o[0]).getPrice() * ((Tbl04_xiaoshou) o[1]).getGmsl();
			sum_count += ((Tbl04_xiaoshou) o[1]).getGmsl();
		}
		request.setAttribute("sum_money", sum_money);
		request.setAttribute("sum_count", sum_count);
		request.getSession().setAttribute("timeStamp", ConvertUtil.toString(request.getParameter("timeStamp")));
		return new ModelAndView("/WEB-INF/view/buy.jsp", modelMap);
	}

	@RequestMapping("/addbuyAjax.do")
	public void addbuyAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer msg = new StringBuffer();
		List<Object[]> xiaoshouList = (List<Object[]>) request.getSession().getAttribute("xiaoshouList");
		if (xiaoshouList == null) {
			msg.append("fail:xiaoshouList is null");
		} else {
			Integer tbl01_id = ConvertUtil.toInteger(request.getParameter("tbl01_id"));
			Integer count = ConvertUtil.toInteger(request.getParameter("count"));
			if (tbl01_id == -1 || count == -1) {
				msg.append("fail:id or count is null");
			} else {
				for (Object[] objects : xiaoshouList) {
					if (((Tbl04_xiaoshou) objects[1]).getTbl01_id().intValue() == tbl01_id) {
						if (count == 0) {
							xiaoshouList.remove(objects);
						} else {
							((Tbl04_xiaoshou) objects[1]).setGmsl(count);
						}
						break;
					}
				}
				Double sum_money = 0D;
				Long sum_count = 0L;
				for (Object[] objects : xiaoshouList) {
					sum_money += ((Tbl04_xiaoshou) objects[1]).getGmsl() * ((Tbl01_shangpin) objects[0]).getPrice();
					sum_count += ((Tbl04_xiaoshou) objects[1]).getGmsl();
				}
				
				msg.append(new BigDecimal(sum_money).setScale(2,BigDecimal.ROUND_HALF_UP)+","+sum_count);
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
	}
}
