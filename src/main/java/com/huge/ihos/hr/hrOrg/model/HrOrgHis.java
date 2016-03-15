package com.huge.ihos.hr.hrOrg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.model.BaseObject;

@Entity
@Table(name = "v_hr_org_his")
public class HrOrgHis extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8113133131195526707L;
	private HrOrgPk hrOrgPk;
	private String orgname;
	private Boolean disabled = false;
	private Boolean deleted = false;

	@Transient
	public String getHisSnapId() {
		return this.hrOrgPk.getHisSnapId();
	}

	@Id
	public HrOrgPk getHrOrgPk() {
		return hrOrgPk;
	}

	public void setHrOrgPk(HrOrgPk hrOrgPk) {
		this.hrOrgPk = hrOrgPk;
	}

	@Column(name = "orgname", length = 50, nullable = false)
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "deleted", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((hrOrgPk == null) ? 0 : hrOrgPk.hashCode());
		result = prime * result + ((orgname == null) ? 0 : orgname.hashCode());
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
		HrOrgHis other = (HrOrgHis) obj;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (hrOrgPk == null) {
			if (other.hrOrgPk != null)
				return false;
		} else if (!hrOrgPk.equals(other.hrOrgPk))
			return false;
		if (orgname == null) {
			if (other.orgname != null)
				return false;
		} else if (!orgname.equals(other.orgname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrOrgHis [hrOrgPk=" + hrOrgPk + ", orgname=" + orgname
				+ ", disabled=" + disabled + ", deleted=" + deleted + "]";
	}

}
