package com.netsky.shop.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.netsky.shop.util.ResultObject;

public interface BaseDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.dao.impl.BaseDAO#delete(java.lang.Object)
	 */
	public abstract void delete(Object o);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.dao.impl.BaseDAO#save(java.lang.Object)
	 */
	public abstract void save(Object o);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netsky.dao.impl.BaseDAO#update(java.lang.String)
	 */
	public abstract void update(String hsql);

	public abstract Object searchById(Class<?> clazz, Serializable id);

	public abstract List<?> searchList(String hsql);

	public abstract ResultObject search(String hsql);

	public abstract ResultObject searchByPage(String hsql, Integer pageRowSize, Integer pageNum);

	public abstract void delete(Class<?> clazz, Serializable id);

	public abstract Object searchFirst(String hsql);
	
	public abstract HibernateTemplate getHibernateTemplate();
}