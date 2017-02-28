package com.huge.ihos.system.configuration.procType.model;

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
 * 采购类型
 * @author gaozhengyang
 * @date 2013-12-3
 */
@Entity
@Table(name = "sy_procType")
public class ProcType extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1355732962438306465L;
	private String typeId;
	private String typeCode;
	private String typeName;

	@Id
	@Column(name = "typeId", length = 32, nullable = false)

	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ProcType other = (ProcType) obj;
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
		return "ProcType [typeId=" + typeId + ", typeCode=" + typeCode
				+ ", typeName=" + typeName + "]";
	}

}
