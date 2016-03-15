package com.huge.ihos.hr.hrPerson.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.Post;
import com.huge.ihos.hr.hrOrg.model.HrOrg;
import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.model.BaseObject;

@Entity
@Table(name = "v_hr_person_his")
public class HrPersonHis extends BaseObject implements Serializable {
	private static final long serialVersionUID = -4544928754540676219L;
	private HrPersonPK hrPersonPk;
	private String personCode;
	private String orgCode;
	private HrOrg hrOrg;
	private HrOrgHis hrOrgHis;
	private String orgSnapCode;
	private String name;
	private Post duty; // 岗位
	private String postType; // 岗位
	private HrDepartmentCurrent department;
	private HrDepartmentHis departmentHis;
	private String deptSnapCode;
	private PersonType empType; // 职工类别
	
	
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

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = false)
	public HrDepartmentCurrent getDepartment() {
		return department;
	}

	public void setDepartment(HrDepartmentCurrent department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "deptId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "deptSnapCode", nullable = false, insertable = false, updatable = false) })
	public HrDepartmentHis getDepartmentHis() {
		return departmentHis;
	}

	public void setDepartmentHis(HrDepartmentHis departmentHis) {
		this.departmentHis = departmentHis;
	}

	@Column(name = "deptSnapCode", nullable = true, length = 14)
	public String getDeptSnapCode() {
		return deptSnapCode;
	}

	public void setDeptSnapCode(String deptSnapCode) {
		this.deptSnapCode = deptSnapCode;
	}

	@Column(name = "postType", nullable = true, length = 20)
	public String getPostType() {
		return postType;
	}
	
	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "duty", nullable = true)
	public Post getDuty() {
		return duty;
	}

	public void setDuty(Post duty) {
		this.duty = duty;
	}
	
	@Transient
	public String getHisSnapId(){
		return this.hrPersonPk.getPersonId()+"_"+this.hrPersonPk.getSnapCode();
	}

	@Id
	public HrPersonPK getHrPersonPk() {
		return hrPersonPk;
	}

	public void setHrPersonPk(HrPersonPK hrPersonPk) {
		this.hrPersonPk = hrPersonPk;
	}

	@Column(name = "personCode", length = 20, nullable = false)
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@Column(name = "orgCode", nullable = false, length = 10)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "name", length = 20, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empType", nullable = true)
	public PersonType getEmpType() {
		return empType;
	}

	public void setEmpType(PersonType empType) {
		this.empType = empType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hrPersonPk == null) ? 0 : hrPersonPk.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((personCode == null) ? 0 : personCode.hashCode());
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
		HrPersonHis other = (HrPersonHis) obj;
		if (hrPersonPk == null) {
			if (other.hrPersonPk != null)
				return false;
		} else if (!hrPersonPk.equals(other.hrPersonPk))
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
		if (personCode == null) {
			if (other.personCode != null)
				return false;
		} else if (!personCode.equals(other.personCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrPersonHis [hrPersonPk=" + hrPersonPk + ", personCode="
				+ personCode + ", orgCode=" + orgCode + ", name=" + name + "]";
	}
}
