package com.huge.ihos.system.systemManager.copy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.model.BaseObject;

@Entity
@Table(name="T_COPY")
public class Copy extends BaseObject {

	private String copycode;
	private Org org;
	private String copyname;
	private String copyshort;
	private String cwmanager;
	private Boolean balanceFlag = false;
	
	private PeriodPlan periodPlan;
	
	@Id
	public String getCopycode() {
		return copycode;
	}

	public void setCopycode(String copycode) {
		this.copycode = copycode;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orgCode",nullable=false)
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="planId",nullable=false)
	public PeriodPlan getPeriodPlan() {
		return periodPlan;
	}

	public void setPeriodPlan(PeriodPlan periodPlan) {
		this.periodPlan = periodPlan;
	}

	@Column(name="copyname",length=50,nullable=false)
	public String getCopyname() {
		return copyname;
	}

	public void setCopyname(String copyname) {
		this.copyname = copyname;
	}

	@Column(name="copyshort",length=30,nullable=true)
	public String getCopyshort() {
		return copyshort;
	}

	public void setCopyshort(String copyshort) {
		this.copyshort = copyshort;
	}

	@Column(name="cwmanager",length=50)
	public String getCwmanager() {
		return cwmanager;
	}

	public void setCwmanager(String cwmanager) {
		this.cwmanager = cwmanager;
	}

	@Column(name="balanceFlag")
	public Boolean getBalanceFlag() {
		return balanceFlag;
	}

	public void setBalanceFlag(Boolean balanceFlag) {
		this.balanceFlag = balanceFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((balanceFlag == null) ? 0 : balanceFlag.hashCode());
		result = prime * result
				+ ((copycode == null) ? 0 : copycode.hashCode());
		result = prime * result
				+ ((copyname == null) ? 0 : copyname.hashCode());
		result = prime * result
				+ ((copyshort == null) ? 0 : copyshort.hashCode());
		result = prime * result
				+ ((cwmanager == null) ? 0 : cwmanager.hashCode());
		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result
				+ ((periodPlan == null) ? 0 : periodPlan.hashCode());
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
		Copy other = (Copy) obj;
		if (balanceFlag == null) {
			if (other.balanceFlag != null)
				return false;
		} else if (!balanceFlag.equals(other.balanceFlag))
			return false;
		if (copycode == null) {
			if (other.copycode != null)
				return false;
		} else if (!copycode.equals(other.copycode))
			return false;
		if (copyname == null) {
			if (other.copyname != null)
				return false;
		} else if (!copyname.equals(other.copyname))
			return false;
		if (copyshort == null) {
			if (other.copyshort != null)
				return false;
		} else if (!copyshort.equals(other.copyshort))
			return false;
		if (cwmanager == null) {
			if (other.cwmanager != null)
				return false;
		} else if (!cwmanager.equals(other.cwmanager))
			return false;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
			return false;
		if (periodPlan == null) {
			if (other.periodPlan != null)
				return false;
		} else if (!periodPlan.equals(other.periodPlan))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Copy [copycode=" + copycode + ", org=" + org + ", copyname="
				+ copyname + ", copyshort=" + copyshort + ", cwmanager="
				+ cwmanager + ", balanceFlag=" + balanceFlag + ", periodPlan="
				+ periodPlan + "]";
	}
}
