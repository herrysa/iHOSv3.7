package com.huge.ihos.update.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table( name = "jj_t_jjdeptmap" )
/*@FilterDef( name = "disableFilter", parameters = { @ParamDef( name = "disabled", type = "boolean" ) } )
@Filters( { @Filter( name = "disableFilter", condition = "disabled <> :disabled" ) } )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )*/

public class JjDeptMap extends BaseObject implements Serializable {

	private Integer id;
	private Person personId;
	private Department deptId;
	private String deptIds;
	private String deptNames;
	private String remark;
	private String orgId;
	
	@Id
    @Column( name = "id")
    @GeneratedValue
    public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
    
    
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "personId")
	public Person getPersonId() {
		return personId;
	}


	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "deptId")
	public Department getDeptId() {
		return deptId;
	}

	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}

	@Column( name = "deptIds", length = 200 )
	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	@Column( name = "deptNames", length = 400 )
	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	@Column( name = "remark", length = 50 )
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column( name = "orgId", length = 10 )
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result + ((deptIds == null) ? 0 : deptIds.hashCode());
		result = prime * result
				+ ((deptNames == null) ? 0 : deptNames.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
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
		JjDeptMap other = (JjDeptMap) obj;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptIds == null) {
			if (other.deptIds != null)
				return false;
		} else if (!deptIds.equals(other.deptIds))
			return false;
		if (deptNames == null) {
			if (other.deptNames != null)
				return false;
		} else if (!deptNames.equals(other.deptNames))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
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
		return "JjDeptMap [id=" + id + ", personId=" + personId + ", deptId="
				+ deptId + ", deptIds=" + deptIds + ", deptNames=" + deptNames
				+ ", remark=" + remark + ", orgId=" + orgId + "]";
	}

}
