package com.huge.ihos.orgprivilege.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table( name = "sy_orgPrivilege" )

public class OrgPrivilege extends BaseObject implements Serializable {

	private String privilegeId;
	private Person personId;
	private String orgIds;
	private String orgNames;
	private String remark;
	
	@Id
    @Column( name = "privilegeId")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
    public String getPrivilegeId() {
		return privilegeId;
	}
	
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
    
    
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "personId")
	public Person getPersonId() {
		return personId;
	}


	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	/*@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "deptId")
	public Department getDeptId() {
		return deptId;
	}

	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}*/

	@Column( name = "orgIds", length = 200 )
	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	@Column( name = "orgNames", length = 400 )
	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	@Column( name = "remark", length = 50 )
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgIds == null) ? 0 : orgIds.hashCode());
		result = prime * result
				+ ((orgNames == null) ? 0 : orgNames.hashCode());
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
		result = prime * result
				+ ((privilegeId == null) ? 0 : privilegeId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		OrgPrivilege other = (OrgPrivilege) obj;
		if (orgIds == null) {
			if (other.orgIds != null)
				return false;
		} else if (!orgIds.equals(other.orgIds))
			return false;
		if (orgNames == null) {
			if (other.orgNames != null)
				return false;
		} else if (!orgNames.equals(other.orgNames))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrgPrivilege [privilegeId=" + privilegeId + ", personId="
				+ personId + ", orgIds=" + orgIds + ", orgNames=" + orgNames
				+ ", remark=" + remark + "]";
	}

	

}
