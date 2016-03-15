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
@Table(name = "sys_dept_type")
public class HrDeptType extends BaseObject implements Serializable{

									
	/**
	 * 
	 */
	private static final long serialVersionUID = -2092416973191053360L;

	// id
	
	private String typeCode;					// 科室类别编码
	
	private String typeName;				// 科室类别名称
	
	private String cnCode;					// 缩略码
	
	private String pcd;						// WH

	private Boolean disabled;						// 是否可用


	
	@Id
	@Column(name = "type_code", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "type_name", unique = true, nullable = false, length = 50)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
		result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
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
		HrDeptType other = (HrDeptType) obj;
		if (typeCode == null) {
			if (other.typeCode != null)
				return false;
		} else if (!typeCode.equals(other.typeCode))
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
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
		return "HrDeptType [typeCode=" + typeCode + ", typeName=" + typeName + ", cnCode=" + cnCode + ", pcd=" + pcd + ", disabled=" + disabled + "]";
	}

}
