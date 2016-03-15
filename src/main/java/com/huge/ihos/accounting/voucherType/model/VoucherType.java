package com.huge.ihos.accounting.voucherType.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.model.BaseObject;

@Entity
@Table(name = "GL_voucherType")
/*@FilterDef( name = "sysVariable", parameters = { @ParamDef( name = "orgCode", type = "string" ),
		                                     @ParamDef( name = "copyCode", type = "string" ),
		                                     @ParamDef( name = "kjYear", type = "string" ),
		                                     @ParamDef( name = "period", type = "string" ) } )*/
@Filters( { @Filter( name = "sysVariable", condition = "orgCode = :orgCode and copyCode = :copyCode" )} )
public class VoucherType extends BaseObject implements Serializable {

	private String voucherTypeId;
	private String orgCode;
	private String copyCode;
	private String vouchertype;
	private String voucherTypeShort;
	private String voucherTypeCode;
	private Boolean isUsed;

	@Id
	@Column(name="voucherTypeId")
	public String getVoucherTypeId() {
		return voucherTypeId;
	}

	public void setVoucherTypeId(String voucherTypeId) {
		this.voucherTypeId = voucherTypeId;
	}

	@Column(name = "orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode")
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name = "voucherType")
	public String getVouchertype() {
		return vouchertype;
	}

	public void setVouchertype(String vouchertype) {
		this.vouchertype = vouchertype;
	}

	@Column(name = "voucherTypeShort")
	public String getVoucherTypeShort() {
		return voucherTypeShort;
	}

	public void setVoucherTypeShort(String voucherTypeShort) {
		this.voucherTypeShort = voucherTypeShort;
	}

	@Column(name="voucherTypeCode")
	public String getVoucherTypeCode() {
		return voucherTypeCode;
	}

	public void setVoucherTypeCode(String voucherTypeCode) {
		this.voucherTypeCode = voucherTypeCode;
	}

	@Column(name="isUsed")
	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public VoucherType() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((isUsed == null) ? 0 : isUsed.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((voucherTypeCode == null) ? 0 : voucherTypeCode.hashCode());
		result = prime * result
				+ ((voucherTypeId == null) ? 0 : voucherTypeId.hashCode());
		result = prime
				* result
				+ ((voucherTypeShort == null) ? 0 : voucherTypeShort.hashCode());
		result = prime * result
				+ ((vouchertype == null) ? 0 : vouchertype.hashCode());
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
		VoucherType other = (VoucherType) obj;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (isUsed == null) {
			if (other.isUsed != null)
				return false;
		} else if (!isUsed.equals(other.isUsed))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (voucherTypeCode == null) {
			if (other.voucherTypeCode != null)
				return false;
		} else if (!voucherTypeCode.equals(other.voucherTypeCode))
			return false;
		if (voucherTypeId == null) {
			if (other.voucherTypeId != null)
				return false;
		} else if (!voucherTypeId.equals(other.voucherTypeId))
			return false;
		if (voucherTypeShort == null) {
			if (other.voucherTypeShort != null)
				return false;
		} else if (!voucherTypeShort.equals(other.voucherTypeShort))
			return false;
		if (vouchertype == null) {
			if (other.vouchertype != null)
				return false;
		} else if (!vouchertype.equals(other.vouchertype))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VoucherType [voucherTypeId=" + voucherTypeId + ", orgCode="
				+ orgCode + ", copyCode=" + copyCode + ", vouchertype="
				+ vouchertype + ", voucherTypeShort=" + voucherTypeShort
				+ ", voucherTypeCode=" + voucherTypeCode + ", isUsed=" + isUsed
				+ "]";
	}

	

}
