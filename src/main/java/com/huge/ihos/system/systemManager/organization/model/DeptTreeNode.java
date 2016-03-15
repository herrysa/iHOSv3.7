package com.huge.ihos.system.systemManager.organization.model;

import com.huge.webapp.ztree.ZTreeSimpleNode;

public class DeptTreeNode extends ZTreeSimpleNode {
	private String nameWithoutPerson;
	private String postCount;
	private String deptCode;
	private String orgCode;
	private String personCount;
	private String snapCode;
	private String state;
	private Integer displayOrder;
	private String personCountD;
	private String postCountD;
	private String personCountP;
	private String personCountDP;
	private String code;

	public String getPersonCountP() {
		return personCountP;
	}

	public void setPersonCountP(String personCountP) {
		this.personCountP = personCountP;
	}

	public String getPersonCountDP() {
		return personCountDP;
	}

	public void setPersonCountDP(String personCountDP) {
		this.personCountDP = personCountDP;
	}

	public String getPostCountD() {
		return postCountD;
	}

	public void setPostCountD(String postCountD) {
		this.postCountD = postCountD;
	}

	public String getPersonCountD() {
		return personCountD;
	}

	public void setPersonCountD(String personCountD) {
		this.personCountD = personCountD;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	public String getPersonCount() {
		return personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getPostCount() {
		return postCount;
	}

	public void setPostCount(String postCount) {
		this.postCount = postCount;
	}

	public String getNameWithoutPerson() {
		return nameWithoutPerson;
	}

	public void setNameWithoutPerson(String nameWithoutPerson) {
		this.nameWithoutPerson = nameWithoutPerson;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + ((nameWithoutPerson == null) ? 0 : nameWithoutPerson.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((personCount == null) ? 0 : personCount.hashCode());
		result = prime * result + ((personCountD == null) ? 0 : personCountD.hashCode());
		result = prime * result + ((personCountDP == null) ? 0 : personCountDP.hashCode());
		result = prime * result + ((personCountP == null) ? 0 : personCountP.hashCode());
		result = prime * result + ((postCount == null) ? 0 : postCount.hashCode());
		result = prime * result + ((postCountD == null) ? 0 : postCountD.hashCode());
		result = prime * result + ((snapCode == null) ? 0 : snapCode.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeptTreeNode other = (DeptTreeNode) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (displayOrder == null) {
			if (other.displayOrder != null)
				return false;
		} else if (!displayOrder.equals(other.displayOrder))
			return false;
		if (nameWithoutPerson == null) {
			if (other.nameWithoutPerson != null)
				return false;
		} else if (!nameWithoutPerson.equals(other.nameWithoutPerson))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (personCount == null) {
			if (other.personCount != null)
				return false;
		} else if (!personCount.equals(other.personCount))
			return false;
		if (personCountD == null) {
			if (other.personCountD != null)
				return false;
		} else if (!personCountD.equals(other.personCountD))
			return false;
		if (personCountDP == null) {
			if (other.personCountDP != null)
				return false;
		} else if (!personCountDP.equals(other.personCountDP))
			return false;
		if (personCountP == null) {
			if (other.personCountP != null)
				return false;
		} else if (!personCountP.equals(other.personCountP))
			return false;
		if (postCount == null) {
			if (other.postCount != null)
				return false;
		} else if (!postCount.equals(other.postCount))
			return false;
		if (postCountD == null) {
			if (other.postCountD != null)
				return false;
		} else if (!postCountD.equals(other.postCountD))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

}