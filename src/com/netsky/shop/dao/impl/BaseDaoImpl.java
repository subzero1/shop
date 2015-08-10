package com.netsky.shop.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.util.ResultObject;

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.dao.impl.BaseDAO#delete(java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.shop_order.dao.impl.BaseDao#delete(java.lang.Object)
	 */
	public void delete(Object o) {
		getHibernateTemplate().delete(o);
	}

	public void save(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	public void update(String hsql) {
		getHibernateTemplate().bulkUpdate(hsql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.shop_order.dao.impl.BaseDao#searchById(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public Object searchById(Class<?> clazz, Serializable id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.shop_order.dao.impl.BaseDao#searchList(java.lang.String)
	 */
	public List<?> searchList(String hsql) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find(hsql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.shop_order.dao.impl.BaseDao#search(java.lang.String)
	 */
	public ResultObject search(String hsql) {
		// TODO Auto-generated method stub
		return searchByPage(hsql, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.shop_order.dao.impl.BaseDao#searchByPage(java.lang.String,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	public ResultObject searchByPage(String hsql, Integer pageRowSize, Integer pageNum) {
		// TODO Auto-generated method stub
		ResultObject ro = null;
		if (pageRowSize == -1 && pageNum == -1) {
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.createSQLQuery(hsql.toLowerCase());
			ro = new ResultObject(query.list(), hsql);
			session.close();
		} else {
			String countSql = "select count(*) " + hsql.substring(hsql.toUpperCase().lastIndexOf("FROM"));
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			try {
				Query query = session.createSQLQuery(hsql.toLowerCase());
				if (pageRowSize == -1) {
					ro = new ResultObject(query.list(), hsql);
				} else {
					ro = new ResultObject(query.setFirstResult((pageNum - 1) * pageRowSize).setMaxResults(pageRowSize)
							.list(), hsql);
				}
				Integer totalRows = 0;
				if (hsql.toLowerCase().indexOf("group by") != -1) {
//					String groupby = hsql.substring(hsql.toLowerCase().indexOf("group by") + 8);
//					String having = "";
//					if (groupby.toLowerCase().indexOf("order by") != -1) {
//						groupby = groupby.substring(0, groupby.toLowerCase().indexOf("order by"));
//					}
//					if (hsql.toLowerCase().indexOf("having") != -1) {
//						having = groupby.substring(groupby.toLowerCase().indexOf("having") + 6);
//						groupby = groupby.substring(0, groupby.toLowerCase().indexOf("having"));
//					}
//
//					String from = " "
//							+ hsql
//									.substring(hsql.toLowerCase().indexOf("from"), hsql.toLowerCase().indexOf(
//											"group by"));
//					String where = "";
//					if (from.toLowerCase().indexOf("where") != -1) {
//						where = from.substring(from.toLowerCase().indexOf("where") + 5) + " and ";
//						from = from.substring(0, from.toLowerCase().indexOf("where"));
//					}
//					StringBuffer sql = new StringBuffer();
//					sql.append("select count(distinct ");
//					sql.append(groupby + ")");
//					sql.append(from.toLowerCase());
//					sql.append(" where " + where);
//					sql.append(groupby);
//					sql.append(" in (select ");
//					sql.append(groupby + from.toLowerCase() + " group by " + groupby);
//					sql.append(having);
//					sql.append(")");
//					query = session.createSQLQuery(sql.toString());
//					System.out.println(sql.toString());
//					totalRows = Integer.parseInt("" + query.list().get(0));
					query = session.createSQLQuery(hsql.toLowerCase());
					totalRows = query.list().size();
				} else {
					query = session.createSQLQuery(countSql.toLowerCase());
					totalRows = ((BigInteger) query.list().get(0)).intValue();
				}
				if (pageRowSize == -1) {
					pageRowSize = totalRows;
					pageNum = 1;
				}
				ro.setParameters(totalRows, pageRowSize, pageNum);
				session.flush();
			} finally {
				session.close();
			}
		}
		return ro;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.shop_order.dao.impl.BaseDao#delete(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public void delete(Class<?> clazz, Serializable id) {
		this.delete(this.searchById(clazz, id));

	}

	public Object searchFirst(String hsql) {
		// TODO Auto-generated method stub
		List<?> objectList = searchList(hsql);
		if (objectList != null && objectList.size() != 0) {
			return objectList.get(0);
		}
		return null;
	}

}
