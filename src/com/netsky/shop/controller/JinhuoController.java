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
import com.netsky.shop.domain.base.Tbl02_jinhuo;
import com.netsky.shop.domain.base.Tbl05_leixing;
import com.netsky.shop.domain.base.Tbl10_yonghu;
import com.netsky.shop.service.QueryService;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.ResultObject;

@Controller
public class JinhuoController {

	@Autowired
	private BaseDao dao;
	@Autowired
	private QueryService queryService;

	@RequestMapping("/jinhuoview.do")
	public ModelAndView jinhuoview(HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		// 获得进货商品的编码
		String tbl01_number = ConvertUtil.toString(request.getParameter("tbl01_number"));
		// 如果编码不为空
		if (!tbl01_number.equals("")) {
			// 获取进货列表
			List<Object[]> jinhuoList = (List<Object[]>) request.getSession().getAttribute("jinhuoList");
			if (jinhuoList == null) {
				// o[0]:tbl01;o[1]:tbl02;o[2]:tbl05;
				jinhuoList = new ArrayList<Object[]>();
			}
			// 查询该商品是否存在于本次进货的列表中
			Boolean inthelist = false;
			for (Object[] objects : jinhuoList) {
				if (((Tbl01_shangpin) objects[0]).getNumber().equals(tbl01_number)) {
					((Tbl02_jinhuo) objects[1]).setMoney(ConvertUtil.toDouble(request.getParameter("money")));
					((Tbl02_jinhuo) objects[1]).setBz(ConvertUtil.toString(request.getParameter("bz")));
					((Tbl02_jinhuo) objects[1]).setJhsl(ConvertUtil.toInteger(request.getParameter("jhsl")));
					inthelist = true;
					break;
				}
			}
			if (!inthelist) {
				// 如果不存在 则查询数据库中是否有该商品
				Tbl01_shangpin shangpin = (Tbl01_shangpin) dao.searchFirst("from Tbl01_shangpin where number='"
						+ tbl01_number + "'");
				if (shangpin == null) {
					// 如果没有 则做新增货物处理
					shangpin = new Tbl01_shangpin();
					shangpin.setKcsl(ConvertUtil.toInteger(request.getParameter("kcsl")));
					shangpin.setName(ConvertUtil.toString(request.getParameter("name")));
					shangpin.setNumber(tbl01_number);
					shangpin.setPrice(ConvertUtil.toDouble(request.getParameter("price")));
					shangpin.setSpsm(ConvertUtil.toString(request.getParameter("spsm")));
					shangpin.setTbl05_id(ConvertUtil.toInteger(request.getParameter("tbl05_id")));
					shangpin.setChengben(0D);
					shangpin.setYuzhi(5);
					dao.save(shangpin);
				}
				Tbl02_jinhuo jinhuo = new Tbl02_jinhuo();
				jinhuo.setBz(ConvertUtil.toString(request.getParameter("bz")));
				jinhuo.setMoney(ConvertUtil.toDouble(request.getParameter("money")));
				jinhuo.setJhsl(ConvertUtil.toInteger(request.getParameter("jhsl")));
				jinhuo.setTbl01_id(shangpin.getId());
				try {
					jinhuo.setTbl10_id(((Tbl10_yonghu) (request.getSession().getAttribute("yonghu"))).getId());
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					System.out.println("用户已登出");
				}
				Tbl05_leixing leixing = (Tbl05_leixing) dao.searchById(Tbl05_leixing.class, shangpin.getTbl05_id());
				Object[] objects = new Object[] { shangpin, jinhuo, leixing };
				jinhuoList.add(objects);
			}
			Double sum_money = 0D;
			for (Object[] objects : jinhuoList) {
				sum_money += ((Tbl02_jinhuo) objects[1]).getMoney();
			}
			request.getSession().setAttribute("sum_money", sum_money);
			request.getSession().setAttribute("jinhuoList", jinhuoList);
		}
		// 跳转到页面
		// 获得商品类型列表
		modelMap.put("leixingList", (List<Tbl05_leixing>) dao.searchList("from Tbl05_leixing"));

		return new ModelAndView("/WEB-INF/view/jinhuo.jsp", modelMap);
	}

	@RequestMapping("/jinhuodel.do")
	public ModelAndView jinhuodel(HttpServletRequest request, HttpServletResponse response) {
		List<Object[]> jinhuoList = (List<Object[]>) request.getSession().getAttribute("jinhuoList");
		if (jinhuoList != null) {
			for (Object[] objects : jinhuoList) {
				if (((Tbl01_shangpin) objects[0]).getId().intValue() == ConvertUtil.toInteger(
						request.getParameter("tbl01_id")).intValue()) {
					jinhuoList.remove(objects);
					break;
				}
			}
		}
		return new ModelAndView("/jinhuoview.do");
	}

	@RequestMapping("/getshangpinInfoAjax.do")
	public void getshangpinInfoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer msg = new StringBuffer();
		String tbl01_number = ConvertUtil.toString(request.getParameter("tbl01_number"));
		if (!"".equals(tbl01_number)) {
			List<Object[]> jinhuoList = (List<Object[]>) request.getSession().getAttribute("jinhuoList");
			boolean inthelist = false;
			if (jinhuoList != null) {
				for (Object[] objects : jinhuoList) {
					if (((Tbl01_shangpin) objects[0]).getNumber().equals(tbl01_number)) {
						msg.append(((Tbl01_shangpin) objects[0]).getId() + ",");
						msg.append(((Tbl01_shangpin) objects[0]).getName() + ",");
						msg.append(new BigDecimal(((Tbl01_shangpin) objects[0]).getPrice()).setScale(2,
								BigDecimal.ROUND_HALF_UP)
								+ ",");
						msg.append(((Tbl01_shangpin) objects[0]).getKcsl() + ",");
						msg.append(ConvertUtil.toString(((Tbl01_shangpin) objects[0]).getSpsm()) + ",");
						msg.append(((Tbl01_shangpin) objects[0]).getTbl05_id() + ",");
						msg.append(ConvertUtil.toString(((Tbl01_shangpin) objects[0]).getPic()) + ",");
						msg.append(new BigDecimal(((Tbl02_jinhuo) objects[1]).getMoney()).setScale(2,
								BigDecimal.ROUND_HALF_UP)
								+ ",");
						msg.append(((Tbl02_jinhuo) objects[1]).getJhsl() + ",");
						msg.append(((Tbl02_jinhuo) objects[1]).getBz());
						inthelist = true;
						break;
					}
				}
			}
			if (!inthelist) {
				Tbl01_shangpin shangpin = (Tbl01_shangpin) dao.searchFirst("from Tbl01_shangpin where number='"
						+ tbl01_number + "'");
				if (shangpin == null) {
					msg.append("new");
				} else {
					msg.append(shangpin.getId() + ",");
					msg.append(shangpin.getName() + ",");
					msg.append(new BigDecimal(shangpin.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP) + ",");
					msg.append(shangpin.getKcsl() + ",");
					msg.append(ConvertUtil.toString(shangpin.getSpsm()) + ",");
					msg.append(shangpin.getTbl05_id() + ",");
					msg.append(ConvertUtil.toString(shangpin.getPic()) + ",,,");
				}
			}
		}
		out.print(msg.toString());
	}

	@RequestMapping("/jinhuo.do")
	public ModelAndView jinhuo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String view = "redirect:/jinhuodan.do";
		Date now = new Date();
		List<Object[]> jinhuoList = (List<Object[]>) request.getSession().getAttribute("jinhuoList");
		if (jinhuoList != null && jinhuoList.size() != 0) {
			Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			try {
				for (Object[] objects : jinhuoList) {
					Tbl02_jinhuo jinhuo = (Tbl02_jinhuo) objects[1];
					jinhuo.setDate(now);
					jinhuo.setJhdh("" + now.getTime());
					Tbl01_shangpin shangpin = (Tbl01_shangpin) objects[0];
					jinhuo.setJhqsl(shangpin.getKcsl());
					Double selledchengben = ConvertUtil
							.toDouble(
									dao
											.searchFirst("select sum(chengben*gmsl) from Tbl04_xiaoshou where time>=(select  min(date) from Tbl02_jinhuo where bz='[hidden]') and tbl01_id="
													+ shangpin.getId()), 0D);
					Double oldallchengben = ConvertUtil
							.toDouble(
									dao
											.searchFirst("select sum(money) from Tbl02_jinhuo where date>=(select min(date) from Tbl02_jinhuo where bz='[hidden]') and tbl01_id="
													+ shangpin.getId()), 0D);
					shangpin.setKcsl(shangpin.getKcsl() + ((Tbl02_jinhuo) objects[1]).getJhsl());
					if (shangpin.getKcsl() != 0)
						shangpin.setChengben((oldallchengben + jinhuo.getMoney() - selledchengben)
								/ (shangpin.getKcsl()));
					// shangpin.setChengben((shangpin.getChengben()*shangpin.getKcsl()+jinhuo.getMoney())/(shangpin.getKcsl()+jinhuo.getJhsl()));
					session.save(jinhuo);
					session.update(shangpin);
				}
				request.getSession().removeAttribute("jinhuoList");
				request.removeAttribute("sum_money");
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				view = "redirect:/jinhuoview.do?status=fail";
			} finally {
				transaction.commit();
				session.close();
			}
		}
		return new ModelAndView(view);
	}

	@RequestMapping("/jinhuodan.do")
	public ModelAndView jinhuodan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		String tbl11_name = "jinhuodan";
		modelMap.put("jinhuodan_titleList", queryService.getTitleList(tbl11_name, null));
		Integer pageRowSize = ConvertUtil.toInteger(request.getParameter("pageRowSize"), 5);
		Integer pageNum = ConvertUtil.toInteger(request.getParameter("pageNum"), 1);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tbl11_name", tbl11_name);
		dataMap.put("pageRowSize", pageRowSize);
		dataMap.put("pageNum", pageNum);
		ResultObject ro = queryService.getResultObject(dataMap);
		modelMap.put("jinhuodan_list", ro.getResultList());
		modelMap.put("pageNum", ro.getPageNum());
		modelMap.put("totalPages", ro.getTotalPages());
		modelMap.put("totalRows", ro.getTotalRows());
		modelMap.put("pageRowSize", pageRowSize);
		return new ModelAndView("/WEB-INF/view/jinhuoList.jsp", modelMap);
	}

	@RequestMapping("/jinhuomingxi.do")
	public ModelAndView jinhuomingxi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		String tbl11_name = "jinhuomingxi";
		modelMap.put("jinhuomingxi_titleList", queryService.getTitleList(tbl11_name, null));
		Integer pageRowSize = ConvertUtil.toInteger(request.getParameter("pageRowSize"), 5);
		Integer pageNum = ConvertUtil.toInteger(request.getParameter("pageNum"), 1);
		String jhdh = ConvertUtil.toString(request.getParameter("jhdh"));
		String where = " and jinhuo.jhdh='" + jhdh + "' ";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tbl11_name", tbl11_name);
		dataMap.put("pageRowSize", pageRowSize);
		dataMap.put("pageNum", pageNum);
		dataMap.put("where", where);
		ResultObject ro = queryService.getResultObject(dataMap);
		modelMap.put("jinhuomingxi_list", ro.getResultList());
		modelMap.put("pageNum", ro.getPageNum());
		modelMap.put("totalPages", ro.getTotalPages());
		modelMap.put("totalRows", ro.getTotalRows());
		modelMap.put("pageRowSize", pageRowSize);
		return new ModelAndView("/WEB-INF/view/jinhuomingxi.jsp", modelMap);
	}
}
