package com.huge.ihos.system.configuration.code.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.model.BaseObject;
@Entity
@Table(name="sy_code")
public class Code extends BaseObject {
	private String codeId;
	private String codeName;
	private String codeRule;
	private String remark;
	private Boolean disabled = false;
	private BdInfo bdInfo;
	private Org org;
	private Copy copy;
	private String periodYear;


	@Id
	@Column(length = 32,name = "codeId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	@Column(name = "codeName", length = 30)
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	@Column(name = "codeRule", length = 20)
	public String getCodeRule() {
		return codeRule;
	}

	public void setCodeRule(String codeRule) {
		this.codeRule = codeRule;
	}

	@Column(name = "remark", length = 50)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bdinfoId")
	public BdInfo getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(BdInfo bdInfo) {
		this.bdInfo = bdInfo;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orgCode")
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="copyCode")
	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	@Column(name="periodYear")
	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}

	@Override
	public String toString() {
		return "Code [codeId=" + codeId + ", codeName=" + codeName
				+ ", codeRule=" + codeRule + ", remark=" + remark + ", disabled="
				+ disabled + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Code other = (Code) obj;
		if (codeId == null) {
			if (other.codeId != null)
				return false;
		} else if (!codeId.equals(other.codeId))
			return false;
		if (codeName == null) {
			if (other.codeName != null)
				return false;
		} else if (!codeName.equals(other.codeName))
			return false;
		if (codeRule == null) {
			if (other.codeRule != null)
				return false;
		} else if (!codeRule.equals(other.codeRule))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeId == null) ? 0 : codeId.hashCode());
		result = prime * result
				+ ((codeName == null) ? 0 : codeName.hashCode());
		result = prime * result
				+ ((codeRule == null) ? 0 : codeRule.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		return result;
	}
}
