package com.huge.ihos.pz.businesstype.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;
@Entity
@Table(name="pz_businessType_param")
public class BusinessTypeParam extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paramId;
	private BusinessType businessType;
	private Boolean disabled;
	private Integer sn;
	private String remark;
	private String paramCode;
	private String paramValue;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="businessId",nullable=false,insertable=true,updatable=false)
	public BusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}
	@Column(name="disabled",nullable=false)
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Column(name="sn",nullable=false)
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	@Column(name="remark",length=150,nullable=true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="paramCode",length=50,nullable=false)
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	@Column(name="paramValue",length=200,nullable=true)
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((paramCode == null) ? 0 : paramCode.hashCode());
		result = prime * result + ((paramId == null) ? 0 : paramId.hashCode());
		result = prime * result + ((paramValue == null) ? 0 : paramValue.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
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
		BusinessTypeParam other = (BusinessTypeParam) obj;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (paramCode == null) {
			if (other.paramCode != null)
				return false;
		} else if (!paramCode.equals(other.paramCode))
			return false;
		if (paramId == null) {
			if (other.paramId != null)
				return false;
		} else if (!paramId.equals(other.paramId))
			return false;
		if (paramValue == null) {
			if (other.paramValue != null)
				return false;
		} else if (!paramValue.equals(other.paramValue))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BusinessTypeParam [paramId=" + paramId + ", businessType=" + businessType + ", disabled=" + disabled + ", sn=" + sn + ", remark=" + remark + ", paramCode=" + paramCode + ", paramValue=" + paramValue + "]";
	}

}
