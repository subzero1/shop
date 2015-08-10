package com.netsky.shop.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
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
import com.netsky.shop.domain.base.Tbl09_duihuanshangpin;
import com.netsky.shop.domain.base.Tbl10_yonghu;
import com.netsky.shop.domain.base.Tbl13_duihuan;
import com.netsky.shop.service.QueryService;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.ResultObject;

@Controller
public class CardController {

	@Autowired
	private BaseDao dao;
	@Autowired
	private QueryService queryService;

	/**
	 * ���صĲ�����','�ָ� status,dqjf,ljjf,zjxfrq,xfcs,id
	 * 
	 * 
	 */
	@RequestMapping("/getCardInfoAjax.do")
	public void getCardInfoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer msg = new StringBuffer();
		String cardnumber = ConvertUtil.toString(request.getParameter("cardnumber"));
		if (!cardnumber.equals("")) {
			Tbl03_huiyuan huiyuan = (Tbl03_huiyuan) dao.searchFirst("from Tbl03_huiyuan where number='"
					+ cardnumber.toUpperCase() + "'");
			if (huiyuan != null) {
				msg.append(huiyuan.getStatus() + ",");
				msg.append(huiyuan.getDqjf() + ",");
				msg.append(huiyuan.getLjjf() + ",");
				ResultObject ro = dao.search("select time from Tbl04_xiaoshou where tbl03_id=" + huiyuan.getId()
						+ " order by time desc limit 1");
				String zjxfrq = "/";
				if (ro.next()) {
					zjxfrq = new SimpleDateFormat("yyyy-MM-dd HH:mm").format((Date) ro.get("time"));
				}
				msg.append(zjxfrq + ",");
				ro = dao.search("select count(distinct time) from Tbl04_xiaoshou where tbl03_id=" + huiyuan.getId());
				String xfcs = "/";
				if (ro.next()) {
					xfcs = ((BigInteger) ro.get("count(distinct time)")).toString();
				}
				msg.append(xfcs + ",");
				msg.append(huiyuan.getId());
			} else {
				msg.append("fail");
			}
		} else {
			msg.append("fail");
		}
		out.print(msg.toString());
	}

	@RequestMapping("/huiyuanList.do")
	public ModelAndView huiyuanList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String status = ConvertUtil.toString(request.getParameter("status"), "已激活");
		String number = ConvertUtil.toString(request.getParameter("number"));
		Double dqjf = ConvertUtil.toDouble(request.getParameter("dqjf"), 0D);
		String jflx = ConvertUtil.toString(request.getParameter("jflx"), "dqjf");
		String ydh = ConvertUtil.toString(request.getParameter("ydh"));
		ModelMap modelMap = new ModelMap();
		String tbl11_name = "huiyuan";
		modelMap.put("huiyuan_titleList", queryService.getTitleList(tbl11_name, null));
		Integer pageRowSize = ConvertUtil.toInteger(request.getParameter("pageRowSize"), 13);
		Integer pageNum = ConvertUtil.toInteger(request.getParameter("pageNum"), 1);
		String where = " and huiyuan.status like '%" + status + "%' and huiyuan.number like'%" + number + "' and "+jflx+">='"
				+ dqjf + "' ";
		if (ydh.equals("曾经兑换")){
			where += " and huiyuan.dqjf<huiyuan.ljjf ";
		}else if (ydh.equals("从未兑换")){
			where += " and huiyuan.dqjf=huiyuan.ljjf ";
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tbl11_name", tbl11_name);
		dataMap.put("pageRowSize", pageRowSize);
		dataMap.put("pageNum", pageNum);
		dataMap.put("where", where);
		ResultObject ro = queryService.getResultObject(dataMap);
		modelMap.put("huiyuan_list", ro.getResultList());
		modelMap.put("pageNum", ro.getPageNum());
		modelMap.put("totalPages", ro.getTotalPages());
		modelMap.put("totalRows", ro.getTotalRows());
		modelMap.put("pageRowSize", pageRowSize);
		modelMap.put("cardcount", dao.searchFirst("select count(*) from Tbl03_huiyuan"));
		modelMap.put("yjhcount", dao.searchFirst("select count(*) from Tbl03_huiyuan where status='已激活'"));
		modelMap.put("wjhcount", dao.searchFirst("select count(*) from Tbl03_huiyuan where status='未激活'"));
		modelMap.put("yzxcount", dao.searchFirst("select count(*) from Tbl03_huiyuan where status='已注销'"));
		modelMap.put("totaldqjf", dao.searchFirst("select sum(dqjf) from Tbl03_huiyuan where status='已激活'"));
		modelMap.put("totalljjf", dao.searchFirst("select sum(ljjf) from Tbl03_huiyuan where status='已激活'"));
		modelMap.put("dhs", dao.searchFirst("select count(*) from Tbl03_huiyuan huiyuan where huiyuan.dqjf<huiyuan.ljjf"));
		return new ModelAndView("/WEB-INF/view/huiyuanList.jsp", modelMap);
	}

	@RequestMapping("/huiyuan.do")
	public ModelAndView huiyuan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer tbl03_id = ConvertUtil.toInteger(request.getParameter("tbl03_id"));
		Tbl03_huiyuan huiyuan = (Tbl03_huiyuan) dao.searchById(Tbl03_huiyuan.class, tbl03_id);
		if (huiyuan != null) {
			modelMap.put("huiyuan", huiyuan);
			String tbl11_name = "huiyuaninfo";
			modelMap.put("huiyuaninfo_titleList", queryService.getTitleList(tbl11_name, null));
			Integer pageRowSize = ConvertUtil.toInteger(request.getParameter("pageRowSize"), 10);
			Integer pageNum = ConvertUtil.toInteger(request.getParameter("pageNum"), 1);
			String where = " and huiyuan.number='" + huiyuan.getNumber() + "' ";
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("tbl11_name", tbl11_name);
			dataMap.put("pageRowSize", pageRowSize);
			dataMap.put("pageNum", pageNum);
			dataMap.put("where", where);
			ResultObject ro = queryService.getResultObject(dataMap);
			List<Map<String, Object>> list = ro.getResultList();
			if (list.size() == 1) {
				modelMap.put("huiyuaninfo", list.get(0));
			}
			List<Object[]> dhspobjectsList = (List<Object[]>) dao
					.searchList("select shangpin,dhsp from Tbl01_shangpin shangpin,Tbl09_duihuanshangpin dhsp where dhsp.tbl01_id=shangpin.id");
			modelMap.put("dhspobjectsList", dhspobjectsList);
			tbl11_name = "huiyuanxiaoshou";
			where = " and huiyuan.id='" + huiyuan.getId() + "' ";
			modelMap.put("huiyuanxiaoshou_titleList", queryService.getTitleList(tbl11_name, null));
			dataMap = new HashMap<String, Object>();
			dataMap.put("tbl11_name", tbl11_name);
			dataMap.put("pageRowSize", -1);
			dataMap.put("pageNum", -1);
			dataMap.put("where", where);
			ro = queryService.getResultObject(dataMap);
			modelMap.put("huiyuanxiaoshou_List", ro.getResultList());
			tbl11_name = "huiyuanduihuan";
			dataMap.put("tbl11_name", tbl11_name);
			modelMap.put("huiyuanduihuan_titleList", queryService.getTitleList(tbl11_name, null));
			ro = queryService.getResultObject(dataMap);
			modelMap.put("huiyuanduihuan_list", ro.getResultList());
			modelMap
					.put(
							"dhspObjectList",
							dao
									.searchList("select dhsp,shangpin from Tbl09_duihuanshangpin dhsp,Tbl01_shangpin shangpin where dhsp.tbl01_id=shangpin.id and (dhsp.deleted is null or dhsp.deleted<>1)"));
		} else {
			return null;
		}
		return new ModelAndView("/WEB-INF/view/huiyuan.jsp", modelMap);
	}

	@RequestMapping("/zhuxiaocard.do")
	public ModelAndView zhuxiaocard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer tbl03_id = ConvertUtil.toInteger(request.getParameter("tbl03_id"));
		String zxyy = ConvertUtil.toString(request.getParameter("zxyy"));
		String sql = "update Tbl03_huiyuan set status='已注销',zxyy='" + zxyy + "',zxrq=sysdate() where id=" + tbl03_id;
		dao.update(sql);
		return new ModelAndView("redirect:/huiyuan.do?tbl03_id=" + tbl03_id);
	}

	@RequestMapping("/getShangpinInfoAjax.do")
	public void getShangpinInfoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer msg = new StringBuffer();
		String number = ConvertUtil.toString(request.getParameter("number"));
		Tbl01_shangpin shangpin = (Tbl01_shangpin) dao.searchFirst("from Tbl01_shangpin where number='" + number + "'");
		if (shangpin != null) {
			msg.append(shangpin.getId());
			msg.append(",");
			msg.append(shangpin.getName());
		} else {
			msg.append("fail");
		}
		out.print(msg);
	}

	@RequestMapping("/adddhsp.do")
	public ModelAndView adddhsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer tbl03_id = ConvertUtil.toInteger(request.getParameter("tbl03_id"));
		Integer tbl01_id = ConvertUtil.toInteger(request.getParameter("tbl01_id"));
		Integer dhsl = ConvertUtil.toInteger(request.getParameter("dhsl"));
		Double dhjf = ConvertUtil.toDouble(request.getParameter("dhjf"));
		Tbl09_duihuanshangpin dhsp = new Tbl09_duihuanshangpin();
		dhsp.setDhjf(dhjf);
		dhsp.setDhsl(dhsl);
		dhsp.setTbl01_id(tbl01_id);
		dao.save(dhsp);
		return new ModelAndView("redirect:/huiyuan.do?tbl03_id=" + tbl03_id);
	}
	@RequestMapping("/deltbl09.do")
	public ModelAndView deltbl09(HttpServletRequest request, HttpServletResponse response) {
		Integer tbl03_id = ConvertUtil.toInteger(request.getParameter("tbl03_id"));
		Integer tbl09_id = ConvertUtil.toInteger(request.getParameter("tbl09_id"));
		
		dao.update("update Tbl09_duihuanshangpin set deleted=1 where id="+tbl09_id);
		return new ModelAndView("redirect:/huiyuan.do?tbl03_id=" + tbl03_id);
	}
	@RequestMapping("/duihuan.do")
	public ModelAndView duihuan(HttpServletRequest request, HttpServletResponse response) {
		Integer tbl03_id = ConvertUtil.toInteger(request.getParameter("tbl03_id"));
		Integer tbl09_id = ConvertUtil.toInteger(request.getParameter("tbl09_id"));
		Date now = new Date();
		// 进行积分兑换的操作
		Tbl03_huiyuan huiyuan = (Tbl03_huiyuan) dao.searchById(Tbl03_huiyuan.class, tbl03_id);
		Tbl09_duihuanshangpin dhsp = (Tbl09_duihuanshangpin) dao.searchById(Tbl09_duihuanshangpin.class, tbl09_id);
		if (huiyuan != null && dhsp != null && "已激活".equals(huiyuan.getStatus()) && huiyuan.getDqjf() >= dhsp.getDhjf()) {
			Tbl01_shangpin shangpin = (Tbl01_shangpin) dao.searchById(Tbl01_shangpin.class, dhsp.getTbl01_id());
			Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			try {
				Tbl13_duihuan duihuan = new Tbl13_duihuan();
				duihuan.setDhqjf(huiyuan.getDqjf());
				duihuan.setTbl03_id(tbl03_id);
				duihuan.setTbl09_id(tbl09_id);
				duihuan.setTime(now);
				huiyuan.setDqjf(huiyuan.getDqjf() - dhsp.getDhjf());
				shangpin.setKcsl(shangpin.getKcsl() - dhsp.getDhsl());
				Tbl04_xiaoshou xiaoshou = new Tbl04_xiaoshou();
				xiaoshou.setChengben(shangpin.getChengben());
				xiaoshou.setGmsl(dhsp.getDhsl());
				xiaoshou.setPrice(0D);
				xiaoshou.setTbl01_id(shangpin.getId());
				xiaoshou.setTbl03_id(tbl03_id);
				xiaoshou.setTime(now);
				Tbl10_yonghu yonghu = (Tbl10_yonghu) request.getSession().getAttribute("yonghu");
				if (yonghu != null) {
					xiaoshou.setTbl10_id(yonghu.getId());
				}
				session.save(xiaoshou);
				duihuan.setTbl04_id(xiaoshou.getId());
				session.update(shangpin);
				session.update(huiyuan);
				session.save(duihuan);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				session.close();
			}
		}
		return new ModelAndView("redirect:/huiyuan.do?tbl03_id=" + tbl03_id);
	}
}
