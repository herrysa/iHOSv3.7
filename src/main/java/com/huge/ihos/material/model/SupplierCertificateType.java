package com.huge.ihos.material.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "ma_supp_cert_type")
public class SupplierCertificateType extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4464637482062116428L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private String id;
	private String name;
	private Integer warnDay;
	private Boolean warnFlag=true;
	private String remark;
	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	@Id
	@Column(name = "id", length = 20)
	public String getId() {
		return id;
	}



	@Column(name = "name", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(name = "warnday",nullable = false, columnDefinition = "int default 0")
	public Integer getWarnDay() {
		return warnDay;
	}



	public void setWarnDay(Integer warnDay) {
		this.warnDay = warnDay;
	}



	public void setId(String id) {
		this.id = id;
	}


	@Column(name = "warnflag", nullable = false, columnDefinition = "bit default 1")
	public Boolean getWarnFlag() {
		return warnFlag;
	}



	public void setWarnFlag(Boolean warnFlag) {
		this.warnFlag = warnFlag;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((warnDay == null) ? 0 : warnDay.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierCertificateType other = (SupplierCertificateType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (warnDay == null) {
			if (other.warnDay != null)
				return false;
		} else if (!warnDay.equals(other.warnDay))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "MaterialCertificateType [id=" + id + ", name=" + name + ", warnDay=" + warnDay + "]";
	}




}
