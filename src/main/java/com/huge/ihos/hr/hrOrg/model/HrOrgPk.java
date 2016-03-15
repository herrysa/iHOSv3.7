package com.huge.ihos.hr.hrOrg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class HrOrgPk implements Serializable {
	private static final long serialVersionUID = 930015639868898541L;
	private String orgCode;
	private String snapCode;

	@Column(name = "snapCode", length = 14, nullable = false)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Transient
	public String getHisSnapId(){
		return this.orgCode+"_"+this.snapCode;
	}

	public HrOrgPk(String orgCode, String snapCode) {
		super();
		this.orgCode = orgCode;
		this.snapCode = snapCode;
	}

	public HrOrgPk() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
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
		HrOrgPk other = (HrOrgPk) obj;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrOrgPk [snapCode=" + snapCode + ", orgCode=" + orgCode + "]";
	}
}
