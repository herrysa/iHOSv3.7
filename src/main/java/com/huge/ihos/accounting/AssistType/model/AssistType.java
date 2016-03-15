package com.huge.ihos.accounting.AssistType.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.model.BaseObject;

@Entity
@Table(name = "GL_assistType")
@FilterDef(name = "disableFilter", parameters = { @ParamDef(name = "disabled", type = "boolean") })
@Filters({ @Filter(name = "disableFilter", condition = "disabled <> :disabled") })
public class AssistType extends BaseObject implements Serializable {

	private String typeCode;
	private String typeName;
	private Boolean checked;
	private String filter;
	private String memo;
	private Boolean disabled;
	
	private BdInfo bdInfo;

	
	@Id
	@Column(name = "assistTypeCode")
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "assistTypeName")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Transient
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Column(name = "filter")
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bdinfoId")
	public BdInfo getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(BdInfo bdInfo) {
		this.bdInfo = bdInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
		result = prime * result	+ ((typeName == null) ? 0 : typeName.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((bdInfo == null) ? 0 : bdInfo.hashCode());
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
		AssistType other = (AssistType) obj;
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
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (bdInfo == null) {
			if (other.bdInfo != null)
				return false;
		} else if (!bdInfo.equals(other.bdInfo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AssistType [typeCode = " + typeCode + ", typeName = "
				+ typeName +  ", filter = " + filter
				+ ", disabled = " + disabled + ",memo = "+memo+", bdinfo = "+bdInfo+" ]";

	}
	

	

}
