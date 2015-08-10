package com.netsky.shop.domain.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl05_leixing")
public class Tbl05_leixing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6037724242366109203L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String flag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
