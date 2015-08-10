package com.netsky.shop.domain.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tbl12_peizhi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6654531908979641467L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
