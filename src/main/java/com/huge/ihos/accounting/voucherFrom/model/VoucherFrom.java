package com.huge.ihos.accounting.voucherFrom.model;

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
@Table(name = "GL_voucherFrom")
public class VoucherFrom extends BaseObject implements Serializable {

	private String voucherFromCode;
	private String voucherFromShort;
	private String voucherFromName;
	
	
	@Id
	@Column(name="voucherFromCode")
	public String getVoucherFromCode() {
		return voucherFromCode;
	}
	public void setVoucherFromCode(String voucherFromCode) {
		this.voucherFromCode = voucherFromCode;
	}
	@Column(name="voucherFromShort")
	public String getVoucherFromShort() {
		return voucherFromShort;
	}
	public void setVoucherFromShort(String voucherFromShort) {
		this.voucherFromShort = voucherFromShort;
	}
	@Column(name="voucherFromName")
	public String getVoucherFromName() {
		return voucherFromName;
	}
	public void setVoucherFromName(String voucherFromName) {
		this.voucherFromName = voucherFromName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((voucherFromCode == null) ? 0 : voucherFromCode.hashCode());
		result = prime * result
				+ ((voucherFromName == null) ? 0 : voucherFromName.hashCode());
		result = prime
				* result
				+ ((voucherFromShort == null) ? 0 : voucherFromShort.hashCode());
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
		VoucherFrom other = (VoucherFrom) obj;
		if (voucherFromCode == null) {
			if (other.voucherFromCode != null)
				return false;
		} else if (!voucherFromCode.equals(other.voucherFromCode))
			return false;
		if (voucherFromName == null) {
			if (other.voucherFromName != null)
				return false;
		} else if (!voucherFromName.equals(other.voucherFromName))
			return false;
		if (voucherFromShort == null) {
			if (other.voucherFromShort != null)
				return false;
		} else if (!voucherFromShort.equals(other.voucherFromShort))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "VoucherFrom [voucherFromCode=" + voucherFromCode
				+ ", voucherFromShort=" + voucherFromShort
				+ ", voucherFromName=" + voucherFromName + "]";
	}

	
}
