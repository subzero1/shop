package com.netsky.shop.domain.base;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl02_jinhuo")
public class Tbl02_jinhuo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1918693717005534700L;
	@Id
	@GeneratedValue
	private Integer id;
	private Integer tbl01_id;
	private Double money;
	private Date date;
	private String jhdh;
	private String bz;
	private Integer tbl10_id;
	private Integer jhsl;
	private Integer jhqsl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTbl01_id() {
		return tbl01_id;
	}

	public void setTbl01_id(Integer tbl01_id) {
		this.tbl01_id = tbl01_id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getJhdh() {
		return jhdh;
	}

	public void setJhdh(String jhdh) {
		this.jhdh = jhdh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getTbl10_id() {
		return tbl10_id;
	}

	public void setTbl10_id(Integer tbl10_id) {
		this.tbl10_id = tbl10_id;
	}

	public Integer getJhsl() {
		return jhsl;
	}

	public void setJhsl(Integer jhsl) {
		this.jhsl = jhsl;
	}

	public Integer getJhqsl() {
		return jhqsl;
	}

	public void setJhqsl(Integer jhqsl) {
		this.jhqsl = jhqsl;
	}

}