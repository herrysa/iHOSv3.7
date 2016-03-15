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
@Table(name = "sy_serial_set")
public class SerialNumberSet extends BaseObject implements Serializable {
	private static final long serialVersionUID = 379690247012193487L;
	private String id;
	private String subSystem;
	private String orgCode;
	private String copyCode;
	private String businessName;
	private String businessCode;
	private Integer serialLenth;
	private String prefix;
	private Boolean needPrefix = true;
	private Boolean needYearMonth = true;
	private Boolean disabled = false;

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

	@Column(name = "businessName",nullable = false, length = 50)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(name = "businessCode",nullable = false, length = 20)
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	@Column(name = "serialLenth")
	public Integer getSerialLenth() {
		return serialLenth;
	}

	public void setSerialLenth(Integer serialLenth) {
		this.serialLenth = serialLenth;
	}

	@Column(name = "prefix", nullable = true, length = 10)
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name = "needPrefix", nullable = false, columnDefinition = "bit default 1")
	public Boolean getNeedPrefix() {
		return needPrefix;
	}

	public void setNeedPrefix(Boolean needPrefix) {
		this.needPrefix = needPrefix;
	}

	@Column(name = "needYearMonth", nullable = false, columnDefinition = "bit default 1")
	public Boolean getNeedYearMonth() {
		return needYearMonth;
	}

	public void setNeedYearMonth(Boolean needYearMonth) {
		this.needYearMonth = needYearMonth;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessCode == null) ? 0 : businessCode.hashCode());
		result = prime * result
				+ ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((needPrefix == null) ? 0 : needPrefix.hashCode());
		result = prime * result
				+ ((needYearMonth == null) ? 0 : needYearMonth.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result
				+ ((serialLenth == null) ? 0 : serialLenth.hashCode());
		result = prime * result
				+ ((subSystem == null) ? 0 : subSystem.hashCode());
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
		SerialNumberSet other = (SerialNumberSet) obj;
		if (businessCode == null) {
			if (other.businessCode != null)
				return false;
		} else if (!businessCode.equals(other.businessCode))
			return false;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (needPrefix == null) {
			if (other.needPrefix != null)
				return false;
		} else if (!needPrefix.equals(other.needPrefix))
			return false;
		if (needYearMonth == null) {
			if (other.needYearMonth != null)
				return false;
		} else if (!needYearMonth.equals(other.needYearMonth))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (serialLenth == null) {
			if (other.serialLenth != null)
				return false;
		} else if (!serialLenth.equals(other.serialLenth))
			return false;
		if (subSystem == null) {
			if (other.subSystem != null)
				return false;
		} else if (!subSystem.equals(other.subSystem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SerialNumberSet [id=" + id + ", subSystem=" + subSystem
				+ ", orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", businessName=" + businessName + ", businessCode="
				+ businessCode + ", serialLenth=" + serialLenth + ", prefix="
				+ prefix + ", needPrefix=" + needPrefix + ", needYearMonth="
				+ needYearMonth + ", disabled=" + disabled + "]";
	}

}
