package com.netsky.shop.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.util.ExcelWrite;
import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl01_shangpin;
import com.netsky.shop.domain.base.Tbl02_jinhuo;
import com.netsky.shop.domain.base.Tbl03_huiyuan;
import com.netsky.shop.domain.base.Tbl04_xiaoshou;
import com.netsky.shop.domain.base.Tbl05_leixing;
import com.netsky.shop.domain.base.Tbl09_duihuanshangpin;
import com.netsky.shop.domain.base.Tbl11_chaxuntongji;
import com.netsky.shop.domain.base.Tbl13_duihuan;
import com.netsky.shop.domain.base.Tbl14_column;
import com.netsky.shop.service.QueryService;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.ResultObject;

@Controller
public class CxtjController {

	@Autowired
	private BaseDao dao;
	@Autowired
	private QueryService queryService;

	@RequestMapping("/tongji.do")
	public ModelAndView tongji(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean toExcel = false;
		if (ConvertUtil.toString(request.getParameter("toExcel")).equals("yes")) {
			toExcel = true;
		}
		// 是否删除销售明细
		Integer tbl04_id = ConvertUtil.toInteger(request.getParameter("tbl04_id"));
		Tbl04_xiaoshou xiaoshou = (Tbl04_xiaoshou) dao.searchById(Tbl04_xiaoshou.class, tbl04_id);
		if (xiaoshou != null) {
			Tbl01_shangpin shangpin = (Tbl01_shangpin) dao.searchById(Tbl01_shangpin.class, xiaoshou.getTbl01_id());
			shangpin.setKcsl(shangpin.getKcsl() + xiaoshou.getGmsl());
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
			shangpin.setChengben((oldallchengben + xiaoshou.getChengben() - selledchengben) / (shangpin.getKcsl()));
			Tbl13_duihuan duihuan = (Tbl13_duihuan) dao
					.searchFirst("from Tbl13_duihuan duihuan where duihuan.tbl04_id=" + xiaoshou.getId());
			Tbl03_huiyuan huiyuan = null;
			if (duihuan != null) {
				huiyuan = (Tbl03_huiyuan) dao.searchById(Tbl03_huiyuan.class, duihuan.getTbl03_id());
				Tbl09_duihuanshangpin dhsp = (Tbl09_duihuanshangpin) dao.searchById(Tbl09_duihuanshangpin.class,
						duihuan.getTbl09_id());
				if (huiyuan != null && dhsp != null) {
					huiyuan.setDqjf(huiyuan.getDqjf() + dhsp.getDhjf());
				}
			} else {
				if (xiaoshou.getTbl03_id() != null) {
					String flag = ConvertUtil
							.toString((String) dao
									.searchFirst("select leixing.flag from Tbl05_leixing leixing where leixing.id=(select shangpin.tbl05_id from Tbl01_shangpin shangpin where shangpin.id="
											+ xiaoshou.getTbl01_id() + ")"));
					if (flag.indexOf("[bujifen]") == -1) {
						huiyuan = (Tbl03_huiyuan) dao.searchById(Tbl03_huiyuan.class, xiaoshou.getTbl03_id());
						huiyuan.setLjjf(huiyuan.getLjjf() - xiaoshou.getPrice());
						huiyuan.setDqjf(huiyuan.getDqjf() - xiaoshou.getPrice());
					}
				}
			}
			Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			try {
				session.saveOrUpdate(shangpin);
				session.delete(xiaoshou);
				if (duihuan != null) {
					session.delete(duihuan);
				}
				if (huiyuan != null) {
					session.update(huiyuan);
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				session.close();
			}
		}
		ModelMap modelMap = new ModelMap();
		// 取页面所需要内容
		modelMap.put("cxlx_list", dao.searchList("from Tbl11_chaxuntongji where flag like '%[cxtj]%' order by id"));
		modelMap.put("leixingList", dao.searchList("from Tbl05_leixing"));
		// 查询
		String tbl11_name = ConvertUtil.toString(request.getParameter("cxlx"), "xsmx");
		Integer tbl05_id = ConvertUtil.toInteger(request.getParameter("leixing"));
		Integer pageRowSize = -1;
		Integer pageNum = -1;
		String time1 = ConvertUtil.toString(request.getParameter("time1"), new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		String time2 = ConvertUtil.toString(request.getParameter("time2"), new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		if (time1.equals("")) {
			time1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		if (time2.equals("")) {
			time2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		String spmc = ConvertUtil.toString(request.getParameter("spmc")).trim();
		if (toExcel) {
			tbl11_name = "xsmx";
		}
		pageNum = ConvertUtil.toInteger(request.getParameter("pageNum"), 1);
		pageRowSize = ConvertUtil.toInteger(request.getParameter("pageRowSize"), 13);
		modelMap.put("pageRowSize", pageRowSize);
		if (tbl11_name.equals("xsmx") && (time1.equals("") || !time1.equals(time2))) {
		} else {
			pageRowSize = -1;
			pageNum = -1;
		}
		modelMap.put("time1", time1);
		modelMap.put("time2", time2);
		String where = "";
		if (!toExcel && tbl05_id != -1) {
			where += " and leixing.id=" + tbl05_id;
		}
		if (!spmc.equals("")) {
			where += " and shangpin.name like '%" + spmc + "%' ";
		}
		Tbl11_chaxuntongji cxtj = (Tbl11_chaxuntongji) dao.searchFirst("from Tbl11_chaxuntongji where name='"
				+ tbl11_name + "'");
		String chartstype = ConvertUtil.toString(cxtj.getChartstyle(), "none");
		if (cxtj != null && cxtj.getTime() != null) {
			String time = cxtj.getTime();
			if (!time1.equals("")) {
				where += " and " + time + " >= '" + time1 + " 00:00:00' ";
			}
			if (!time1.equals("")) {
				where += " and " + time + " <= '" + time2 + " 23:59:59' ";
			}
		}
		List<Tbl14_column> titleList = queryService.getTitleList(tbl11_name, where);
		modelMap.put("cxtj_titleList", titleList);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tbl11_name", tbl11_name);
		dataMap.put("pageRowSize", pageRowSize);
		dataMap.put("pageNum", pageNum);
		dataMap.put("where", where);
		String orderby = ConvertUtil.toString(request.getParameter("orderby"));
		dataMap.put("orderby", orderby);
		String sequence = ConvertUtil.toString(request.getParameter("sequence"));
		dataMap.put("sequence", sequence);
		ResultObject ro = queryService.getResultObject(dataMap);
		// for(String s:ro.getResultList().get(0).keySet()){
		// System.out.println(s+" "+ro.getResultList().get(0).get(s));
		// }
		modelMap.put("cxtj_list", ro.getResultList());
		if (ro.getResultList().size() == 0 && time2.compareTo("2011-03-31") >= 0) {
			modelMap.put("none", "yes");
		}
		if (tbl11_name.indexOf("xs") != -1) {
			String sql = "select sum(xiaoshou.gmsl),count(distinct xiaoshou.time),sum(xiaoshou.gmsl*xiaoshou.price),sum(xiaoshou.gmsl*xiaoshou.chengben),sum(xiaoshou.gmsl*(xiaoshou.price-xiaoshou.chengben)),sum(xiaoshou.gmsl*(xiaoshou.price-xiaoshou.chengben))/sum(xiaoshou.gmsl*xiaoshou.price) from tbl04_xiaoshou xiaoshou left join tbl01_shangpin shangpin on shangpin.id=xiaoshou.tbl01_id left join tbl05_leixing leixing on shangpin.tbl05_id=leixing.id where 1=1 and xiaoshou.time >= '"
					+ time1
					+ " 00:00:00'  and xiaoshou.time <= '"
					+ time2
					+ " 23:59:59' and shangpin.name like '%"
					+ spmc + "%'";
			if (tbl05_id != -1) {
				sql += " and leixing.id=" + tbl05_id;
			}
			ResultObject ro1 = dao.search(sql);
			if (ro1.next()) {
				modelMap.put("xssl", ro1.get("sum(xiaoshou.gmsl)"));
				modelMap.put("ggrs", ro1.get("count(distinct xiaoshou.time)"));
				modelMap.put("xse", ro1.get("sum(xiaoshou.gmsl*xiaoshou.price)"));
				modelMap.put("cb", ro1.get("sum(xiaoshou.gmsl*xiaoshou.chengben)"));
				modelMap.put("lr", ro1.get("sum(xiaoshou.gmsl*(xiaoshou.price-xiaoshou.chengben))"));
				modelMap
						.put(
								"lrl",
								ro1
										.get("sum(xiaoshou.gmsl*(xiaoshou.price-xiaoshou.chengben))/sum(xiaoshou.gmsl*xiaoshou.price)"));
			}
			sql = "select sum(xiaoshou.gmsl*xiaoshou.price) from tbl04_xiaoshou xiaoshou left join tbl01_shangpin shangpin on shangpin.id=xiaoshou.tbl01_id left join tbl05_leixing leixing on shangpin.tbl05_id=leixing.id where 1=1 and xiaoshou.time >= '"
					+ time1
					+ " 00:00:00'  and xiaoshou.time <= '"
					+ time2
					+ " 23:59:59' and tbl03_id is not null  and shangpin.name like '%" + spmc + "%'";
			ro1 = dao.search(sql);
			if (ro1.next()) {
				modelMap.put("kxs", ro1.get("sum(xiaoshou.gmsl*xiaoshou.price)"));
			}
		} else if (tbl11_name.indexOf("jh") != -1) {
			String sql = "select sum(jinhuo.money) from Tbl02_jinhuo jinhuo left join Tbl01_shangpin shangpin on shangpin.id=jinhuo.tbl01_id left join Tbl05_leixing leixing on leixing.id=shangpin.tbl05_id where jinhuo.bz<>'[hidden]' and jinhuo.date >= '"
					+ time1
					+ " 00:00:00'  and jinhuo.date <= '"
					+ time2
					+ " 23:59:59' and shangpin.name like '%"
					+ spmc + "%'";
			if (tbl05_id != -1) {
				sql += " and leixing.id=" + tbl05_id;
			}
			ResultObject ro1 = dao.search(sql);
			if (ro1.next()) {
				modelMap.put("jhze", ro1.get("sum(jinhuo.money)"));
			}
		} else if (tbl11_name.equals("kc")) {
			String sql = "select sum(shangpin.kcsl),sum(shangpin.kcsl*shangpin.price),sum(shangpin.kcsl*shangpin.chengben) from Tbl01_shangpin shangpin left join Tbl05_leixing leixing on leixing.id=shangpin.tbl05_id where 1=1";
			if (tbl05_id != -1) {
				sql += " and leixing.id=" + tbl05_id;
			}
			if (!spmc.equals("")) {
				sql += " and shangpin.name like '%" + spmc + "%'";
			}
			ResultObject ro1 = dao.search(sql);
			if (ro1.next()) {
				modelMap.put("spzl", ro1.get("sum(shangpin.kcsl)"));
				modelMap.put("zje", ro1.get("sum(shangpin.kcsl*shangpin.price)"));
				modelMap.put("zcb", ro1.get("sum(shangpin.kcsl*shangpin.chengben)"));
			}
		} else if (tbl11_name.indexOf("dh") != -1) {
			String sql = "select sum(dhsp.dhsl*dhsp.dhjf) from Tbl13_duihuan duihuan left join Tbl09_duihuanshangpin dhsp on dhsp.id=duihuan.tbl09_id left join Tbl01_shangpin shangpin on shangpin.id=dhsp.tbl01_id left join Tbl05_leixing leixing on leixing.id=shangpin.tbl05_id where duihuan.time >= '"
					+ time1
					+ " 00:00:00'  and duihuan.time <= '"
					+ time2
					+ " 23:59:59' and shangpin.name like '%"
					+ spmc + "%' ";
			if (tbl05_id != -1) {
				sql += " and leixing.id=" + tbl05_id;
			}
			ResultObject ro1 = dao.search(sql);
			if (ro1.next()) {
				modelMap.put("dhjf", ro1.get("sum(dhsp.dhsl*dhsp.dhjf)"));
			}
			sql = "select sum(xiaoshou.gmsl*xiaoshou.chengben) from Tbl13_duihuan duihuan left join Tbl04_xiaoshou xiaoshou on xiaoshou.id=duihuan.tbl04_id left join Tbl01_shangpin shangpin on shangpin.id=xiaoshou.tbl01_id left join Tbl05_leixing leixing on leixing.id=shangpin.tbl05_id where duihuan.time >= '"
					+ time1 + " 00:00:00'  and duihuan.time <= '" + time2 + " 23:59:59'";
			if (tbl05_id != -1) {
				sql += " and leixing.id=" + tbl05_id;
			}
			ro1 = dao.search(sql);
			if (ro1.next()) {
				modelMap.put("dhcb", ro1.get("sum(xiaoshou.gmsl*xiaoshou.chengben)"));
			}
			sql = "select shangpin.id,shangpin.name,sum(xiaoshou.gmsl*xiaoshou.chengben) from Tbl13_duihuan duihuan left join Tbl04_xiaoshou xiaoshou on xiaoshou.id=duihuan.tbl04_id left join Tbl01_shangpin shangpin on shangpin.id=xiaoshou.tbl01_id left join Tbl05_leixing leixing on leixing.id=shangpin.tbl05_id where duihuan.time >= '"
					+ time1 + " 00:00:00'  and duihuan.time <= '" + time2 + " 23:59:59'";
			if (tbl05_id != -1) {
				sql += " and leixing.id=" + tbl05_id;
			}
			sql += " group by shangpin.id";
			ro1 = dao.search(sql);
			StringBuffer gspdhze = new StringBuffer();
			while (ro1.next()) {
				gspdhze.append(ro1.get("shangpin.name"));
				gspdhze.append("：<span style=\"color:red;font-weight:bolder\">");
				gspdhze.append(new BigDecimal(ConvertUtil.toDouble(ro1.get("sum(xiaoshou.gmsl*xiaoshou.chengben)")))
						.setScale(2, BigDecimal.ROUND_HALF_UP));
				gspdhze.append("</span>&nbsp;&nbsp;");
			}
			modelMap.put("gspdhze", gspdhze);
		}
		if (!toExcel && tbl11_name.equals("xsmx")) {
			modelMap.put("pageNum", ro.getPageNum());
			modelMap.put("totalPages", ro.getTotalPages());
			modelMap.put("totalRows", ro.getTotalRows());
		}
		if (!chartstype.equals("none")) {
			modelMap.put("tbl11", cxtj);
			String xstr = "";
			List<String> dataArr = new ArrayList<String>();
			for (int i = 0; i < ro.getResultList().size(); i++) {
				if (tbl11_name.indexOf("sp") != -1 && i == 15) {
					break;
				}
				Map<String, Object> rowMap = ro.getResultList().get(i);
				String names = "";
				int j = 0;
				for (Tbl14_column column : titleList) {
					Object o = rowMap.get(column.getKey());
					if (o != null) {
						if ("y".equals(column.getXory())) {
							if (i == 0) {
								dataArr.add(o.toString() + ",");
							} else {

								try {
									dataArr.set(j, dataArr.get(j) + o.toString() + ",");
								} catch (RuntimeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									throw e;
								}
							}
							names += column.getTitle() + ",";
							j++;
						} else if ("x".equals(column.getXory())) {
							String ostr = o.toString();
							if (column.getPattern() != null && !column.getPattern().equals("")
									&& column.getPattern().startsWith("yyyy")) {
								ostr = new SimpleDateFormat(column.getPattern()).format((Date) o);
							}
							xstr += ostr + ",";
						}
					} else {
						System.out.println(column.getKey());
					}
				}
				if (modelMap.get("names") == null && names.endsWith(",")) {
					names = names.substring(0, names.length() - 1);
					modelMap.put("names", names);
				}
			}
			if (xstr.endsWith(",")) {
				xstr = xstr.substring(0, xstr.length() - 1);
			}
			modelMap.put("xstr", xstr);
			String s = "";
			for (String string : dataArr) {
				if (string != null)
					if (string.endsWith(",")) {
						string = string.substring(0, string.length() - 1);
					}
				s += string + "&";
			}
			if (s.endsWith("&")) {
				s = s.substring(0, s.length() - 1);
				modelMap.put("data", s);
			}
		}
		if (toExcel) {
			time1 = time1.substring(0, (time1 + " ").indexOf(" "));
			time2 = time2.substring(0, (time1 + " ").indexOf(" "));
			String filename = "";
			if (time1.equals(time2)) {
				filename = time1;
			} else {
				filename = time1 + "至" + time2;
			}
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=GBK;filename="
					+ new String((filename + "销售记录.xls").getBytes("GBK"), "ISO-8859-1"));
			response.setHeader("Content-Disposition", "filename="
					+ new String((filename + "销售记录.xls").getBytes("gb2312"), "iso8859-1"));
			ExcelWrite excel = new ExcelWrite(new HSSFWorkbook());
			// SHEET1 销售明细
			excel.setTitle(filename + "销售明细");
			excel.setTitleList(titleList);
			List<List<Object>> rowList = new ArrayList<List<Object>>();
			for (Map<String, Object> lineMap : ro.getResultList()) {
				List<Object> lineList = new ArrayList<Object>();
				for (Tbl14_column tbl14_column : titleList) {
					Object o = lineMap.get(tbl14_column.getKey());
					String pattern = tbl14_column.getPattern();
					if (pattern != null && o != null) {
						if (pattern.equals("price")) {
							o = new BigDecimal((Double) o).setScale(2, BigDecimal.ROUND_HALF_UP);
						} else if (pattern.equals("0.00%")) {
							NumberFormat nf = NumberFormat.getPercentInstance();
							nf.setMinimumFractionDigits(2);
							o = nf.format(o);
						} else {
							o = new SimpleDateFormat(pattern).format(o);
						}
					}
					lineList.add(o);
				}
				rowList.add(lineList);
			}
			List<Object> bottomLine = new ArrayList<Object>();
			List<Object> sum_list = (List<Object>) dao
					.searchList("select sum(price*gmsl) from Tbl04_xiaoshou where time>='" + time1
							+ " 00:00:00' and time<='" + time2 + " 23:59:59'");
			bottomLine.add("合计");
			if (sum_list != null && sum_list.size() != 0 && sum_list.get(0) != null) {
				bottomLine.add(new BigDecimal((Double) sum_list.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			rowList.add(bottomLine);
			excel.setRowList(rowList);
			excel.write("销售明细");
			// SHEET1 销售汇总
			excel.setTitle(filename + "销售汇总");
			tbl11_name = "xssphz";
			titleList = queryService.getTitleList(tbl11_name, where);
			excel.setTitleList(titleList);
			dataMap = new HashMap<String, Object>();
			dataMap.put("tbl11_name", tbl11_name);
			dataMap.put("pageRowSize", pageRowSize);
			dataMap.put("pageNum", pageNum);
			dataMap.put("where", where);
			dataMap.put("orderby", " leixing.id ");
			ro = queryService.getResultObject(dataMap);
			rowList = new ArrayList<List<Object>>();
			for (Map<String, Object> lineMap : ro.getResultList()) {
				List<Object> lineList = new ArrayList<Object>();
				for (Tbl14_column tbl14_column : titleList) {
					if (!("none").equals(tbl14_column.getDisplay())) {
						Object o = lineMap.get(tbl14_column.getKey());
						String pattern = tbl14_column.getPattern();
						if (pattern != null && o != null) {
							if (pattern.equals("price")) {
								o = new BigDecimal((Double) o).setScale(2, BigDecimal.ROUND_HALF_UP);
							} else if (pattern.equals("0.00%")) {
								NumberFormat nf = NumberFormat.getPercentInstance();
								nf.setMinimumFractionDigits(2);
								o = nf.format(o);
							} else {
								o = new SimpleDateFormat(pattern).format(o);
							}
						}
						lineList.add(o);
					}
				}
				rowList.add(lineList);
			}
			rowList.add(bottomLine);
			excel.setRowList(rowList);
			excel.write("销售汇总");
			excel.getWorkbook().write(response.getOutputStream());
			return null;
		}
		return new ModelAndView("/WEB-INF/view/tongji.jsp", modelMap);
	}
}
