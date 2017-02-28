package com.huge.ihos.bm.bugetSubj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Formula;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_dict_bugetSubj" )
public class BugetSubj extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1746347312625825838L;
	private String bugetSubjId;
	private String orgCode;
	private String copyCode;
	private String periodYear;
	private String bugetSubjCode;
	private String bugetSubjName;
	private String cnCode;
	private Integer slevel;
	private String subjTypeCode;
	private String subjTypeName;
	private BugetSubj parentId;
	private Boolean leaf;
	private Boolean isprocessctrl;
	private Boolean isCarry;
	private String ctrlPeriod;
	private Department centralDeptId;
	private Boolean disabled;
	
	@Column
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Id
	public String getBugetSubjId() {
		return bugetSubjId;
	}
	public void setBugetSubjId(String bugetSubjId) {
		this.bugetSubjId = bugetSubjId;
	}
	
	@Column
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Column
	public String getCopyCode() {
		return copyCode;
	}
	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	
	@Column
	public String getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}
	
	@Column
	public String getBugetSubjCode() {
		return bugetSubjCode;
	}
	public void setBugetSubjCode(String bugetSubjCode) {
		this.bugetSubjCode = bugetSubjCode;
	}
	
	@Column
	public String getBugetSubjName() {
		return bugetSubjName;
	}
	public void setBugetSubjName(String bugetSubjName) {
		this.bugetSubjName = bugetSubjName;
	}
	
	@Column
	public String getCnCode() {
		return cnCode;
	}
	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	
	@Column
	public Integer getSlevel() {
		if(slevel==null){
			slevel = 1;
		}
		return slevel;
	}
	public void setSlevel(Integer slevel) {
		this.slevel = slevel;
	}
	
	@Column
	public String getSubjTypeCode() {
		return subjTypeCode;
	}
	public void setSubjTypeCode(String subjTypeCode) {
		this.subjTypeCode = subjTypeCode;
	}
	
	@Formula("(SELECT at.Accttype FROM GL_accountType at where at.AccttypeId=subjTypeCode)")
	public String getSubjTypeName() {
		return subjTypeName;
	}
	public void setSubjTypeName(String subjTypeName) {
		this.subjTypeName = subjTypeName;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parentId")
	public BugetSubj getParentId() {
		return parentId;
	}
	public void setParentId(BugetSubj parentId) {
		this.parentId = parentId;
	}
	
	@Column
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	@Column
	public Boolean getIsprocessctrl() {
		return isprocessctrl;
	}
	public void setIsprocessctrl(Boolean isprocessctrl) {
		this.isprocessctrl = isprocessctrl;
	}
	
	@Column
	public Boolean getIsCarry() {
		return isCarry;
	}
	public void setIsCarry(Boolean isCarry) {
		this.isCarry = isCarry;
	}
	
	@Column
	public String getCtrlPeriod() {
		return ctrlPeriod;
	}
	public void setCtrlPeriod(String ctrlPeriod) {
		this.ctrlPeriod = ctrlPeriod;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="centralDeptId")
	public Department getCentralDeptId() {
		return centralDeptId;
	}
	public void setCentralDeptId(Department centralDeptId) {
		this.centralDeptId = centralDeptId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bugetSubjCode == null) ? 0 : bugetSubjCode.hashCode());
		result = prime * result
				+ ((bugetSubjId == null) ? 0 : bugetSubjId.hashCode());
		result = prime * result
				+ ((bugetSubjName == null) ? 0 : bugetSubjName.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((ctrlPeriod == null) ? 0 : ctrlPeriod.hashCode());
		result = prime * result + ((isCarry == null) ? 0 : isCarry.hashCode());
		result = prime * result
				+ ((isprocessctrl == null) ? 0 : isprocessctrl.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result + ((slevel == null) ? 0 : slevel.hashCode());
		result = prime * result
				+ ((subjTypeCode == null) ? 0 : subjTypeCode.hashCode());
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
		BugetSubj other = (BugetSubj) obj;
		if (bugetSubjCode == null) {
			if (other.bugetSubjCode != null)
				return false;
		} else if (!bugetSubjCode.equals(other.bugetSubjCode))
			return false;
		if (bugetSubjId == null) {
			if (other.bugetSubjId != null)
				return false;
		} else if (!bugetSubjId.equals(other.bugetSubjId))
			return false;
		if (bugetSubjName == null) {
			if (other.bugetSubjName != null)
				return false;
		} else if (!bugetSubjName.equals(other.bugetSubjName))
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
		if (ctrlPeriod == null) {
			if (other.ctrlPeriod != null)
				return false;
		} else if (!ctrlPeriod.equals(other.ctrlPeriod))
			return false;
		if (isCarry == null) {
			if (other.isCarry != null)
				return false;
		} else if (!isCarry.equals(other.isCarry))
			return false;
		if (isprocessctrl == null) {
			if (other.isprocessctrl != null)
				return false;
		} else if (!isprocessctrl.equals(other.isprocessctrl))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (slevel == null) {
			if (other.slevel != null)
				return false;
		} else if (!slevel.equals(other.slevel))
			return false;
		if (subjTypeCode == null) {
			if (other.subjTypeCode != null)
				return false;
		} else if (!subjTypeCode.equals(other.subjTypeCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BugetSubj [bugetSubjId=" + bugetSubjId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", periodYear=" + periodYear
				+ ", bugetSubjCode=" + bugetSubjCode + ", bugetSubjName="
				+ bugetSubjName + ", cnCode=" + cnCode + ", slevel=" + slevel
				+ ", subjTypeCode=" + subjTypeCode + ", leaf=" + leaf
				+ ", isprocessctrl=" + isprocessctrl + ", isCarry=" + isCarry
				+ ", ctrlPeriod=" + ctrlPeriod + "]";
	}
	
	
	
}
