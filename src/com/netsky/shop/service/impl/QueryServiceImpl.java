package com.netsky.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl11_chaxuntongji;
import com.netsky.shop.domain.base.Tbl14_column;
import com.netsky.shop.service.QueryService;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.ResultObject;

@Service("queryService")
public class QueryServiceImpl implements QueryService {
	@Autowired
	private BaseDao dao;

	public ResultObject getResultObject(Map<String, Object> dataMap) throws Exception {

		// String tbl11_name, String keyword, Integer pageRowSize, Integer
		// pageNum,
		// String asc, String where
		String tbl11_name = (String) dataMap.get("tbl11_name");
		String keyword = (String) dataMap.get("keyword");
		Integer pageRowSize = (Integer) dataMap.get("pageRowSize");
		Integer pageNum = (Integer) dataMap.get("pageNum");
		String asc = (String) dataMap.get("sequence");
		String where = (String) dataMap.get("where");
		Tbl11_chaxuntongji chaxuntongji = (Tbl11_chaxuntongji) dao.searchFirst("from Tbl11_chaxuntongji where name='"
				+ tbl11_name + "'");
		if (chaxuntongji == null) {
			throw new Exception("CHAXUNTONGJI IS NULL");
		}
		// select ... from ... where ... group by ... having ... order by ...
		// ...
		StringBuffer hsql = new StringBuffer();
		hsql.append("select 1 ");
		// select
		List<Tbl14_column> titleList = getTitleList(tbl11_name, where);
		for (Tbl14_column title : titleList) {
			hsql.append("," + title.getKey());
		}
		// from
		hsql.append(" from " + chaxuntongji.getTablename());
		// where
		hsql.append(" where 1=1");
		if (where != null) {
			hsql.append(where);
		}
		if (!"".equals(ConvertUtil.toString(chaxuntongji.getWhereclause()))) {
			hsql.append(" and " + chaxuntongji.getWhereclause());
		}
		if (keyword != null && !"".equals(keyword)) {
			hsql.append(" and(");
			for (Tbl14_column title : titleList) {
				if (title.getRemark().indexOf("[nonkeyword]") == -1) {
					hsql.append(title.getKey() + " like '%" + keyword + "%' or ");
				}
			}
			if (hsql.toString().endsWith("or ")) {
				hsql.delete(hsql.length() - 3, hsql.length());
			}
			hsql.append(")");
		}
		// group by
		if (chaxuntongji.getGroupby() != null && !"".equals(chaxuntongji.getGroupby())) {
			hsql.append(" group by " + chaxuntongji.getGroupby());
		}
		// having
		// orderby
		if (dataMap.get("orderby") != null&&!dataMap.get("orderby").equals("")) {
			String orderby = (String)dataMap.get("orderby");
			orderby = orderby.indexOf("as")==-1?orderby:orderby.substring(orderby.indexOf("as")+2).trim();
			hsql.append(" order by " + orderby);
		} else {
			hsql.append(" order by " + chaxuntongji.getOrderby());
		}
		// asc/desc
		asc = ConvertUtil.toString(asc);
		hsql.append(" " + (asc.equals("") ? (ConvertUtil.toString(chaxuntongji.getAsc())) : asc));
//		 System.out.println(hsql.toString().toLowerCase());
		return dao.searchByPage(hsql.toString(), pageRowSize, pageNum);
	}

	public List<Tbl14_column> getTitleList(String tbl11_name, String where) {
		List<Tbl14_column> tbl14List = (List<Tbl14_column>) dao.searchList("from Tbl14_column where flag like'%["
				+ tbl11_name + "]%' order by id");
		for (Tbl14_column tbl14_column : tbl14List) {
			if (tbl14_column.getKey().indexOf("[where]") != -1) {
				tbl14_column.setKey(tbl14_column.getKey().replace("[where]", where));
			}
		}
		return tbl14List;
	}
}
