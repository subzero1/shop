package com.netsky.shop.domain.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl07_tongbu")
public class Tbl07_tongbu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6770948584684101610L;
	@Id
	@GeneratedValue
	private Integer id;
	private String table;
	private Integer table_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Integer getTable_id() {
		return table_id;
	}

	public void setTable_id(Integer table_id) {
		this.table_id = table_id;
	}

}
