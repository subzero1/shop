package com.netsky.shop.domain.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl13_duihuan")
public class Tbl13_duihuan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364987302869988385L;
	@Id
	@GeneratedValue
	private Integer id;
	private Integer tbl09_id;
	private Integer tbl03_id;
	private Double dhqjf;
	private Date time;
	private Integer tbl04_id;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTbl09_id() {
		return tbl09_id;
	}

	public void setTbl09_id(Integer tbl09_id) {
		this.tbl09_id = tbl09_id;
	}

	public Integer getTbl03_id() {
		return tbl03_id;
	}

	public void setTbl03_id(Integer tbl03_id) {
		this.tbl03_id = tbl03_id;
	}

	public Double getDhqjf() {
		return dhqjf;
	}

	public void setDhqjf(Double dhqjf) {
		this.dhqjf = dhqjf;
	}

	public Integer getTbl04_id() {
		return tbl04_id;
	}

	public void setTbl04_id(Integer tbl04_id) {
		this.tbl04_id = tbl04_id;
	}

}
