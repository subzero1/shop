package com.netsky.shop.domain.base;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.netsky.shop.util.SlaveObject;

@Entity
@Table(name = "tbl01_shangpin")
public class Tbl01_shangpin implements java.io.Serializable, SlaveObject {
	private static final String defaultpkg = "yjr/shangpintu/";
	private static final String defaultsrc = "default.jpg";
	private static final long serialVersionUID = 581847863197149487L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private Integer tbl05_id;
	private Double price;
	private Integer kcsl;
	private String pic;
	private String spsm;
	private String number;
	private Double chengben;
	private Integer yuzhi;
	private String guige;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

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

	public Integer getTbl05_id() {
		return tbl05_id;
	}

	public void setTbl05_id(Integer tbl05_id) {
		this.tbl05_id = tbl05_id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getKcsl() {
		return kcsl;
	}

	public void setKcsl(Integer kcsl) {
		this.kcsl = kcsl;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSpsm() {
		return spsm;
	}

	public void setSpsm(String spsm) {
		this.spsm = spsm;
	}

	public Double getChengben() {
		return chengben;
	}

	public void setChengben(Double chengben) {
		this.chengben = chengben;
	}

	public Integer getYuzhi() {
		return yuzhi;
	}

	public void setYuzhi(Integer yuzhi) {
		this.yuzhi = yuzhi;
	}

	public void setFileName(String fileName) {
		this.pic = fileName;
	}

	public String getSlaveType() {
		return "商品图";
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFilePath() {
		// TODO Auto-generated method stub
		return pic;
	}

	public String getExtName() {
		// TODO Auto-generated method stub
		return ".jpg";
	}
	public String getDefaultpkg() {
		return defaultpkg;
	}

	public String getDefaultsrc() {
		return defaultsrc;
	}
}