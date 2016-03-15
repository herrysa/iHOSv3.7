package com.huge.ihos.hr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sys_dept_attr")
public class HrDeptAttr extends BaseObject implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7623263597454915844L;

	// id
	
	private String attrCode;					// 科室性质编码
	
	private String attrName;				// 科室性质名称
	
	private String cnCode;					// 缩略码
	
	private String pcd;						// WH

	private Boolean disabled;						// 是否可用


	
	@Id
	@Column(name = "attr_Code", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	@Column(name = "attr_name", unique = true, nullable = false, length = 50)
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	
	@Column(name = "cnCode", nullable = true,length = 20)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Column(name = "pcd", nullable = true,length = 20)
	public String getPcd() {
		return pcd;
	}

	public void setPcd(String pcd) {
		this.pcd = pcd;
	}

	@Column(name = "disabled", nullable = false)
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
		result = prime * result + ((attrCode == null) ? 0 : attrCode.hashCode());
		result = prime * result + ((attrName == null) ? 0 : attrName.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result + ((pcd == null) ? 0 : pcd.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
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
		HrDeptAttr other = (HrDeptAttr) obj;
		if (attrCode == null) {
			if (other.attrCode != null)
				return false;
		} else if (!attrCode.equals(other.attrCode))
			return false;
		if (attrName == null) {
			if (other.attrName != null)
				return false;
		} else if (!attrName.equals(other.attrName))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (pcd == null) {
			if (other.pcd != null)
				return false;
		} else if (!pcd.equals(other.pcd))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrDeptType [attrCode=" +attrCode + ", attrName=" + attrName + ", cnCode=" + cnCode + ", pcd=" + pcd + ", disabled=" + disabled + "]";
	}

}