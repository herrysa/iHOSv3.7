package com.huge.ihos.material.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_serial_set",uniqueConstraints = {@UniqueConstraint(columnNames={"businessCode", "orgCode","copycode"})})

public class InvBillNumberSetting extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8474451438679031687L;
	/**
	 * 序列号规则对应的code
	 */
	public static final String INV_MAIN_IN = "MMRK";
	public static final String INV_MAIN_OUT = "MMCK";
	public static final String INV_MAIN_MOVE = "MMYK";
	public static final String INV_CHECK = "MMPD";
	public static final String DEPT_NEED_PLAN = "KSXQ";
	public static final String DEPT_APP = "DPSL";
	public static final String PURCHASE_PLAN = "CGJH";
	public static final String ORDER = "CGDD";
	public static final String DEPT_MR_SUMMARY = "MRS";
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(nullable = false, length = 50, unique = false, name = "businessCode")
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	@Column(nullable = false, length = 20, unique = false, name = "businessName")
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(name = "needPrefix", nullable = false, columnDefinition = "bit default 1")
	public Boolean getNeedPrefix() {
		return needPrefix;
	}

	public void setNeedPrefix(Boolean needPrefix) {
		this.needPrefix = needPrefix;
	}

	@Column(name = "prefix", nullable = true, length = 10)
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/*@Column(name = "needOther")
	public Boolean isNeedOther() {
		return needOther;
	}

	public void setNeedOther(Boolean needOther) {
		this.needOther = needOther;
	}*/

	@Column(name = "needYearMonth", nullable = false, columnDefinition = "bit default 1")
	public Boolean getNeedYearMonth() {
		return needYearMonth;
	}

	public void setNeedYearMonth(Boolean needYearMonth) {
		this.needYearMonth = needYearMonth;
	}

	@Column(name = "serialLenth")
	public Integer getSerialLenth() {
		return serialLenth;
	}

	public void setSerialLenth(Integer serialLenth) {
		this.serialLenth = serialLenth;
	}

	/*@Column(name = "inarow", nullable = false, columnDefinition = "bit default 1")
	public Boolean getInArow() {
		return inArow;
	}

	public void setInArow(Boolean inArow) {
		this.inArow = inArow;
	}*/

	@Column(nullable = false, length = 20, unique = false, name = "orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(nullable = false, length = 20, unique = false, name = "copyCode")
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	
	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	

	

	@Override
	public String toString() {
		return "InvBillNumberSetting [id=" + id + ", businessName="
				+ businessName + ", businessCode=" + businessCode
				+ ", needPrefix=" + needPrefix + ", prefix=" + prefix
				+ ", needYearMonth=" + needYearMonth + ", serialLenth="
				+ serialLenth + ", orgCode=" + orgCode + ", copyCode="
				+ copyCode + ", disabled=" + disabled + "]";
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
		InvBillNumberSetting other = (InvBillNumberSetting) obj;
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
		return true;
	}




	// id
	private String id;
	// 中文名
	private String businessName;
	// 英文代码
	private String businessCode;

	//private Boolean inArow=true;

	// 是否使用前缀
	private Boolean needPrefix=true;
	// 前缀定义
	private String prefix;

	// 是否需要自定义的额外的编码串
	//private Boolean needOther;

	// 是否使用6位年月数字
	private Boolean needYearMonth=true;

	// 流水号长度
	private Integer serialLenth;
	
	private String orgCode;
	private String copyCode;
	
	private Boolean disabled = false;

}
