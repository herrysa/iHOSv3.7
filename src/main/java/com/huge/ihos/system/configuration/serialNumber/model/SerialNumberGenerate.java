package com.huge.ihos.system.configuration.serialNumber.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
@Entity
@Table(name = "sy_serial_genarate")
public class SerialNumberGenerate extends BaseObject implements Serializable{
	private static final long serialVersionUID = -5505410800257957777L;
	private String id;//仅作为标识主键的Id，无其他意义
	private String subSystem; //子系统
	private String orgCode; 
	private String copyCode;
	private String businessCode; //业务编码
	private String yearMonth;
	private Long serialNumber;  //序列号
	private Boolean isReal = true;    //是否真实的序列号
	
	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "subSystem",nullable = false, length = 10)
	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	@Column(name = "orgCode",nullable = false, length = 10)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode",nullable = false, length = 10)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name = "businessCode",nullable = false, length = 20)
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	@Column(name = "yearMonth",nullable = true, length = 6)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Column(name = "serialnumber")
	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "isReal", nullable = false, columnDefinition = "bit default 1")
	public Boolean getIsReal() {
		return isReal;
	}

	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessCode == null) ? 0 : businessCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isReal == null) ? 0 : isReal.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((serialNumber == null) ? 0 : serialNumber.hashCode());
		result = prime * result
				+ ((subSystem == null) ? 0 : subSystem.hashCode());
		result = prime * result
				+ ((yearMonth == null) ? 0 : yearMonth.hashCode());
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
		SerialNumberGenerate other = (SerialNumberGenerate) obj;
		if (businessCode == null) {
			if (other.businessCode != null)
				return false;
		} else if (!businessCode.equals(other.businessCode))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isReal == null) {
			if (other.isReal != null)
				return false;
		} else if (!isReal.equals(other.isReal))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		if (subSystem == null) {
			if (other.subSystem != null)
				return false;
		} else if (!subSystem.equals(other.subSystem))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SerialNumberGenerate [id=" + id + ", subSystem=" + subSystem
				+ ", orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", businessCode=" + businessCode + ", yearMonth=" + yearMonth
				+ ", serialNumber=" + serialNumber + ", isReal=" + isReal + "]";
	}
}
