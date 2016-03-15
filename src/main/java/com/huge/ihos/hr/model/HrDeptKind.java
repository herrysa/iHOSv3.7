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
@Table(name = "sys_dept_kind")
public class HrDeptKind extends BaseObject implements Serializable{

									

	/**
	 * 
	 */
	private static final long serialVersionUID = 7817902960033026352L;
	
	private String kindCode;					// 科室类型编码
	
	private String kindName;				// 科室类型名称
	
	private String cnCode;					// 缩略码
	
	private String pcd;						// WH

	private Boolean disabled;						// 是否可用


	
	@Id
	@Column(name = "kind_code", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	@Column(name = "kind_name", unique = true, nullable = false, length = 50)
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
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
		result = prime * result + ((kindCode == null) ? 0 : kindCode.hashCode());
		result = prime * result + ((kindName == null) ? 0 : kindName.hashCode());
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
		HrDeptKind other = (HrDeptKind) obj;
		if (kindCode == null) {
			if (other.kindCode != null)
				return false;
		} else if (!kindCode.equals(other.kindCode))
			return false;
		if (kindName == null) {
			if (other.kindName != null)
				return false;
		} else if (!kindName.equals(other.kindName))
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
		return "HrDeptType [kindCode=" +kindCode + ", kindName=" + kindName + ", cnCode=" + cnCode + ", pcd=" + pcd + ", disabled=" + disabled + "]";
	}

}

