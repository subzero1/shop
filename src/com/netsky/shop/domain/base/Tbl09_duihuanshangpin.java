package com.netsky.shop.domain.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl09_duihuanshangpin")
public class Tbl09_duihuanshangpin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7310578240187600830L;
	@Id
	@GeneratedValue
	private Integer id;
	private Integer tbl01_id;
	private Double dhjf;
	private Integer dhsl;
	private Integer deleted;

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

	public Double getDhjf() {
		return dhjf;
	}

	public void setDhjf(Double dhjf) {
		this.dhjf = dhjf;
	}

	public Integer getDhsl() {
		return dhsl;
	}

	public void setDhsl(Integer dhsl) {
		this.dhsl = dhsl;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

}
