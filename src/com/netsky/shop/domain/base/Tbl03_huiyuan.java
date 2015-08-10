package com.netsky.shop.domain.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl03_huiyuan")
public class Tbl03_huiyuan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -278270296678576742L;
	@Id
	@GeneratedValue
	private Integer id;
	private String number;
	private Date zcrq;
	private Date jhrq;
	private Date zxrq;
	private String zxyy;
	private Double dqjf;
	private Double ljjf;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getZcrq() {
		return zcrq;
	}

	public void setZcrq(Date zcrq) {
		this.zcrq = zcrq;
	}

	public Date getJhrq() {
		return jhrq;
	}

	public void setJhrq(Date jhrq) {
		this.jhrq = jhrq;
	}

	public Date getZxrq() {
		return zxrq;
	}

	public void setZxrq(Date zxrq) {
		this.zxrq = zxrq;
	}

	public String getZxyy() {
		return zxyy;
	}

	public void setZxyy(String zxyy) {
		this.zxyy = zxyy;
	}

	public Double getDqjf() {
		return dqjf;
	}

	public void setDqjf(Double dqjf) {
		this.dqjf = dqjf;
	}

	public Double getLjjf() {
		return ljjf;
	}

	public void setLjjf(Double ljjf) {
		this.ljjf = ljjf;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
