package com.huge.ihos.hr.hrDeptment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.model.BaseObject;

@Entity
@Table(name = "v_hr_department_his")
public class HrDepartmentHis extends BaseObject implements Serializable {

	private static final long serialVersionUID = -658426337498949259L;
	private HrDeptSnapPk hrDeptPk;
	private String orgCode;
	private String deptCode;
	private String name;
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgCode", nullable = false, insertable = false, updatable = false)
	public HrOrg getHrOrg() {
		return hrOrg;
	}

	public void setHrOrg(HrOrg hrOrg) {
		this.hrOrg = hrOrg;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "orgCode", nullable = false, insertable = false, updatable = false),
		@JoinColumn(name = "orgSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrOrgHis getHrOrgHis() {
		return hrOrgHis;
	}

	public void setHrOrgHis(HrOrgHis hrOrgHis) {
		this.hrOrgHis = hrOrgHis;
	}
	
	@Column(name = "orgSnapCode", nullable = true, length = 14)
	public String getOrgSnapCode() {
		return orgSnapCode;
	}

	public void setOrgSnapCode(String orgSnapCode) {
		this.orgSnapCode = orgSnapCode;
	}

	@Id
	public HrDeptSnapPk getHrDeptPk() {
		return hrDeptPk;
	}

	public void setHrDeptPk(HrDeptSnapPk hrDeptPk) {
		this.hrDeptPk = hrDeptPk;
	}

	@Column(name = "orgCode", nullable = false, length = 10)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "deptCode", nullable = false, length = 20)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "name", unique = true, nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result
				+ ((hrDeptPk == null) ? 0 : hrDeptPk.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
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
		HrDepartmentHis other = (HrDepartmentHis) obj;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (hrDeptPk == null) {
			if (other.hrDeptPk != null)
				return false;
		} else if (!hrDeptPk.equals(other.hrDeptPk))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrDepartmentHis [hrDeptPk=" + hrDeptPk + ", orgCode=" + orgCode
				+ ", deptCode=" + deptCode + ", name=" + name + "]";
	}

}
