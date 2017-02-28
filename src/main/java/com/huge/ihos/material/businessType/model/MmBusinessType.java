package com.huge.ihos.material.businessType.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 仓库业务类型
 * 
 * @author gaozhengyang
 * @date 2013-12-3
 */
@Entity
@Table(name = "mm_businessType")
public class MmBusinessType extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8463336290580014988L;
//	private String businessTypeId;
	private String typeCode;
	private String typeName;
	//出入库类型 1：入库 ； 2：出库
	private String inOut;
	private Boolean disabled = false;
	private String sql;

/*	@Id
	@Column(name = "businessTypeId", length = 32, nullable = false)

	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}*/
	@Id
	@Column(name = "typeCode", length = 4, nullable = false)
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "typeName", length = 40, nullable = false)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "inOut", length = 1, nullable = false)
	public String getInOut() {
		return inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "sql", length = 500, nullable = true)
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((inOut == null) ? 0 : inOut.hashCode());
		result = prime * result + ((sql == null) ? 0 : sql.hashCode());
		result = prime * result
				+ ((typeCode == null) ? 0 : typeCode.hashCode());
		result = prime * result
				+ ((typeName == null) ? 0 : typeName.hashCode());
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
		MmBusinessType other = (MmBusinessType) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (inOut == null) {
			if (other.inOut != null)
				return false;
		} else if (!inOut.equals(other.inOut))
			return false;
		if (sql == null) {
			if (other.sql != null)
				return false;
		} else if (!sql.equals(other.sql))
			return false;
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
		return true;
	}

	@Override
	public String toString() {
		return "BusinessType [typeCode="
				+ typeCode + ", typeName=" + typeName + ", inOut=" + inOut
				+ ", disabled=" + disabled + ", sql=" + sql + "]";
	}

}
