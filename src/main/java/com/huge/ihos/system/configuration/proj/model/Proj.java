package com.huge.ihos.system.configuration.proj.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;


@Entity
@Table(name="sy_project")
public class Proj extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projId;
	private String projCode;
	private String projName;
	private ProjNature projNature;
	private ProjType projType;
	private ProjUse projUse;
	private Boolean disabled;
//	private Department department;
	private String principal;
	private String memo;
	private String cnCode;
	private String copyCode;
	private String orgCode;
	private Date beginDate;
	private Date endDate;
	private Boolean leaf;
	private String periodYear;
	
	//private List<ProjAccount> projAccountList = new ArrayList<ProjAccount>();
	@Id
	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	@Column(name="projCode", length=30)
	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	@Column(name="projName", length=50)
	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="projNatureId")
	public ProjNature getProjNature() {
		return projNature;
	}

	public void setProjNature(ProjNature projNature) {
		this.projNature = projNature;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="projTypeId")
	public ProjType getProjType() {
		return projType;
	}

	public void setProjType(ProjType projType) {
		this.projType = projType;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="projUseId")
	public ProjUse getProjUse() {
		return projUse;
	}

	public void setProjUse(ProjUse projUse) {
		this.projUse = projUse;
	}

	@Column(name="disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="deptId")
//	public Department getDepartment() {
//		return department;
//	}
//
//	public void setDepartment(Department department) {
//		this.department = department;
//	}

	@Column(name="principal", length=50)
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name="remark", length=100)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name="cnCode", length=10)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	@Column(name="copyCode", length=50)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	@Column(name="orgCode", length=50)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Column(name="beginDate")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@Column(name="endDate")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
//	@OneToMany(fetch=FetchType.LAZY,mappedBy="proj", cascade = CascadeType.ALL)
//	public List<ProjAccount> getProjAccountList() {
//		return projAccountList;
//	}
//
//	public void setProjAccountList(List<ProjAccount> projAccountList) {
//		this.projAccountList = projAccountList;
//	}
	@Column(name="leaf")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	@Column(name="periodYear")
	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((principal == null) ? 0 : principal.hashCode());
		result = prime * result
				+ ((projCode == null) ? 0 : projCode.hashCode());
		result = prime * result + ((projId == null) ? 0 : projId.hashCode());
		result = prime * result
				+ ((projName == null) ? 0 : projName.hashCode());
		result = prime * result
				+ ((projNature == null) ? 0 : projNature.hashCode());
		result = prime * result
				+ ((projType == null) ? 0 : projType.hashCode());
		result = prime * result + ((projUse == null) ? 0 : projUse.hashCode());
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
		Proj other = (Proj) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (principal == null) {
			if (other.principal != null)
				return false;
		} else if (!principal.equals(other.principal))
			return false;
		if (projCode == null) {
			if (other.projCode != null)
				return false;
		} else if (!projCode.equals(other.projCode))
			return false;
		if (projId == null) {
			if (other.projId != null)
				return false;
		} else if (!projId.equals(other.projId))
			return false;
		if (projName == null) {
			if (other.projName != null)
				return false;
		} else if (!projName.equals(other.projName))
			return false;
		if (projNature == null) {
			if (other.projNature != null)
				return false;
		} else if (!projNature.equals(other.projNature))
			return false;
		if (projType == null) {
			if (other.projType != null)
				return false;
		} else if (!projType.equals(other.projType))
			return false;
		if (projUse == null) {
			if (other.projUse != null)
				return false;
		} else if (!projUse.equals(other.projUse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Proj [projId=" + projId + ", projCode=" + projCode
				+ ", projName=" + projName + ", projNature=" + projNature
				+ ", projType=" + projType + ", projUse=" + projUse
				+ ", disabled=" + disabled + ", principal=" + principal
				+ ", memo=" + memo + ", cnCode=" + cnCode + ", copyCode="
				+ copyCode + ", orgCode=" + orgCode + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", leaf=" + leaf
				+ ", kjYear=" + periodYear + "]";
	}

	
}
