package com.netsky.shop.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl01_shangpin;
import com.netsky.shop.domain.base.Tbl05_leixing;
import com.netsky.shop.domain.base.Tbl10_yonghu;
import com.netsky.shop.service.QueryService;
import com.netsky.shop.util.ConvertUtil;

@Controller
public class JcxxwhController {

	@Autowired
	private BaseDao dao;
	@Autowired
	private QueryService queryService;

	@RequestMapping("/jcxxwh.do")
	public ModelAndView jcxxwh(HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("/WEB-INF/view/jcxxwh.jsp");
	}

	@RequestMapping("/leixingwh.do")
	public ModelAndView leixingwh(HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		List<Tbl05_leixing> leixingList = (List<Tbl05_leixing>) dao.searchList("from Tbl05_leixing");
		modelMap.put("leixingList", leixingList);
		return new ModelAndView("/WEB-INF/view/leixingwh.jsp", modelMap);
	}

	@RequestMapping("/leixing.do")
	public ModelAndView leixing(HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		Tbl05_leixing leixing = (Tbl05_leixing) dao.searchById(Tbl05_leixing.class, ConvertUtil.toInteger(request
				.getParameter("tbl05_id")));
		modelMap.put("leixing", leixing);
		return new ModelAndView("/WEB-INF/view/leixing.jsp", modelMap);
	}

	@RequestMapping("/shangpinwh.do")
	public ModelAndView shangpinwh(HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		String keyword = ConvertUtil.toString(request.getParameter("keyword"));
		String tbl05_id = ConvertUtil.toString(request.getParameter("tbl05_id"));
		StringBuffer sql = new StringBuffer();
		sql.append("from Tbl01_shangpin shangpin where 1=1 ");
		if (!"".equals(keyword)) {
			sql.append(" and (shangpin.name like '%" + keyword + "%' or shangpin.number like '%" + keyword + "%') ");
		}
		if (!"".equals(tbl05_id)) {
			sql.append(" and shangpin.tbl05_id=" + tbl05_id);
		}
		sql.append(" order by shangpin.number asc");
		List<Tbl01_shangpin> shangpinList = (List<Tbl01_shangpin>) dao.searchList(sql.toString());
		modelMap.put("shangpinList", shangpinList);
		List<Tbl05_leixing> leixingList = (List<Tbl05_leixing>) dao.searchList("from Tbl05_leixing");
		modelMap.put("leixingList", leixingList);
		return new ModelAndView("/WEB-INF/view/shangpinwh.jsp", modelMap);
	}

	@RequestMapping("/shangpin.do")
	public ModelAndView shangpin(HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		Tbl01_shangpin shangpin = (Tbl01_shangpin) dao.searchById(Tbl01_shangpin.class, ConvertUtil.toInteger(request
				.getParameter("tbl01_id")));
		modelMap.put("shangpin", shangpin);
		List<Tbl05_leixing> leixingList = (List<Tbl05_leixing>) dao.searchList("from Tbl05_leixing");
		modelMap.put("leixingList", leixingList);
		return new ModelAndView("/WEB-INF/view/shangpin.jsp", modelMap);
	}

	@RequestMapping("/validatePwd.do")
	public void validatePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer msg = new StringBuffer();
		String pwd = ConvertUtil.toString(request.getParameter("pwd"));
		Tbl10_yonghu yonghu = (Tbl10_yonghu) request.getSession().getAttribute("yonghu");
		if (pwd.equals(yonghu.getPassword())) {
			msg.append("true");
		} else {
			msg.append("false");
		}
		out.print(msg);
	}

	@RequestMapping("/changepwd.do")
	public ModelAndView changepwd(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		Tbl10_yonghu yonghu = (Tbl10_yonghu) request.getSession().getAttribute("yonghu");
		yonghu.setPassword(password);
		dao.save(yonghu);
		request.setAttribute("msg", "密码修改成功！");
		return new ModelAndView("/WEB-INF/view/mmxg.jsp");
	}

	@RequestMapping("/bulkupdateshangpin.do")
	public ModelAndView bulkupdateshangpin(HttpServletRequest request, HttpServletResponse response) {
		String[] ids = request.getParameterValues("id");
		String[] names = request.getParameterValues("name");
		String[] tbl05_ids = request.getParameterValues("tbl05_id");
		String[] prices = request.getParameterValues("price");
		String[] changes = request.getParameterValues("change");
		if (ids != null) {
			Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			Query query = null;
			try {
				for (int i = 0; i < ids.length; i++) {
					if (changes[i].equals("yes")) {
						String sql = "update Tbl01_shangpin shangpin set name='" + names[i] + "',price='" + prices[i]
								+ "',tbl05_id='" + tbl05_ids[i] + "' where id=" + ids[i];
						query = session.createQuery(sql);
						query.executeUpdate();
					}
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				session.close();
			}
		}
		return new ModelAndView("/tongji.do");
	}
}
