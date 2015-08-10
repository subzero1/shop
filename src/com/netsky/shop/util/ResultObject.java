package com.netsky.shop.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultObject {
	private Integer index;
	private List<Map<String, Object>> resultList;
	private Integer totalRows;
	private Integer totalPages;
	private Integer pageNum;
	private Integer currentFirstRow;
	private String hsql;
	private String resultArray[];
	private List<?> originalList;
	private Integer length;

	public ResultObject() {
	}

	public ResultObject(List<?> originalList, String hsql) {
		this.hsql = hsql;
		this.originalList = originalList;
		index = -1;
		init();
	}

	private void init() {
		// ��ɹؼ��ּ�
		hsql = hsql.toLowerCase();
		char[] charArr = hsql.toCharArray();
		Integer flag = 0;
		hsql = "";
		for (char c : charArr) {
			if (c == '(') {
				flag--;
			}
			if (c == ')') {
				flag++;
			}
			if (flag == 0 && c == ',') {
				c = '&';
			}
			// if (flag == 0 && c == ' ') {
			// c = '#';
			// }
			hsql += c;
		}
		if (hsql.indexOf("select") != -1) {
			hsql = hsql.substring(hsql.indexOf("select") + 6, hsql.lastIndexOf("from")).trim();
			resultArray = hsql.split("&");
		} else if (hsql.indexOf("where") != -1) {
			hsql = hsql.substring(hsql.lastIndexOf("from") + 4, hsql.lastIndexOf("where")).trim();
			resultArray = hsql.split("&");
		} else {
			hsql = hsql.substring(hsql.indexOf("from") + 4).trim();
			resultArray = hsql.split("&");
		}
		// for (int i = 0; i < resultArray.length; i++) {
		// if (resultArray[i].indexOf("#") != -1) {
		// resultArray[i] =
		// resultArray[i].split("#")[resultArray[i].split("#").length - 1];
		// }
		// }
		// ��ɽ��
		setLength(originalList.size());
		resultList = new ArrayList<Map<String, Object>>();
		for (Object object : originalList) {
			Map<String, Object> rowMap = new HashMap<String, Object>();
			if (resultArray.length > 1) {
				for (int i = 0; i < resultArray.length; i++) {
					rowMap.put(resultArray[i], ((Object[]) object)[i]);
					// System.out.println(resultArray[i]+"----"+((Object[])
					// object)[i]);
				}
			} else {
				rowMap.put(resultArray[0], object);
			}
			resultList.add(rowMap);
		}
	}

	private void setLength(Integer length) {
		this.length = length;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public boolean next() {
		index++;
		if (index < length) {
			return true;
		} else {
			return false;
		}
	}

	public Object get(String columnName) {
		return resultList.get(index).get(columnName);
	}

	public Object get(Integer columnIndex) {
		return resultList.get(index).get(columnIndex);
	}

	public void reset() {
		index = -1;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setParameters(Integer totalRows, Integer pageRowSize, Integer pageNum) {
		this.totalRows = totalRows;
		this.pageNum = pageNum;
		if (pageRowSize == 0) {
			pageRowSize = 1;
		}
		if (totalRows % pageRowSize == 0) {
			this.totalPages = totalRows / pageRowSize;
		} else {
			this.totalPages = totalRows / pageRowSize + 1;
		}
		currentFirstRow = (pageNum - 1) * pageRowSize + 1;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public Integer getCurrentFirstRow() {
		return currentFirstRow;
	}

	public Integer getPageNum() {
		return pageNum;
	}
}
