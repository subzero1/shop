package com.netsky.shop.domain.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl04_xiaoshou")
public class Tbl04_xiaoshou implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5612117798578511344L;
	@Id
	@GeneratedValue
	private Integer id;
	private Integer tbl03_id;
	private Integer tbl01_id;
	private Integer gmsl;
	private Date time;
	private Integer tbl10_id;
	private Double chengben;
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTbl03_id() {
		return tbl03_id;
	}

	public void setTbl03_id(Integer tbl03_id) {
		this.tbl03_id = tbl03_id;
	}

	public Integer getTbl01_id() {
		return tbl01_id;
	}

	public void setTbl01_id(Integer tbl01_id) {
		this.tbl01_id = tbl01_id;
	}

	public Integer getGmsl() {
		return gmsl;
	}

	public void setGmsl(Integer gmsl) {
		this.gmsl = gmsl;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getTbl10_id() {
		return tbl10_id;
	}

	public void setTbl10_id(Integer tbl10_id) {
		this.tbl10_id = tbl10_id;
	}

	public Double getChengben() {
		return chengben;
	}

	public void setChengben(Double chengben) {
		this.chengben = chengben;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
