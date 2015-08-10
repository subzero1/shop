package com.netsky.shop.service;

import java.util.List;
import java.util.Map;

import com.netsky.shop.domain.base.Tbl14_column;
import com.netsky.shop.util.ResultObject;

public interface QueryService {

	public abstract ResultObject getResultObject(Map<String,Object> dataMap) throws Exception;
	public abstract List<Tbl14_column> getTitleList(String tbl11_name,String where);
}