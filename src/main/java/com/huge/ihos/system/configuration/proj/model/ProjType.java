package com.huge.ihos.system.configuration.proj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table( name = "sy_projType" )
public class ProjType extends BaseObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String typeId;
	private String typeName;
	private Boolean isSys;
	private Boolean disabled;
	private String projDetailKind;
	private String cnCode;
	
	@Id
	@Column(name="type_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@Column(name="type_name", length=50)
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Column(name="is_sys")
	public Boolean getIsSys() {
		return isSys;
	}
	public void setIsSys(Boolean isSys) {
		this.isSys = isSys;
	}
	@Column(name="disabled")
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Column(name="proj_detail_kind",length=50)
	public String getProjDetailKind() {
		return projDetailKind;
	}
	public void setProjDetailKind(String projDetailKind) {
		this.projDetailKind = projDetailKind;
	}
	@Column(name="cnCode",length=50)
	public String getCnCode() {
		return cnCode;
	}
	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((isSys == null) ? 0 : isSys.hashCode());
		result = prime * result
				+ ((projDetailKind == null) ? 0 : projDetailKind.hashCode());
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
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
		ProjType other = (ProjType) obj;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (isSys == null) {
			if (other.isSys != null)
				return false;
		} else if (!isSys.equals(other.isSys))
			return false;
		if (projDetailKind == null) {
			if (other.projDetailKind != null)
				return false;
		} else if (!projDetailKind.equals(other.projDetailKind))
			return false;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
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
		return "ProjType [typeId=" + typeId + ", typeName=" + typeName
				+ ", isSys=" + isSys + ", disabled=" + disabled
				+ ", projDetailKind=" + projDetailKind + ", cnCode=" + cnCode
				+ "]";
	}
}
