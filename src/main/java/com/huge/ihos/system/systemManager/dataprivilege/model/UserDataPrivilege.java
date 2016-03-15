package com.huge.ihos.system.systemManager.dataprivilege.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name="t_dataPrivilege_user")
public class UserDataPrivilege extends BaseObject implements Serializable {

	private String dataPrivilegeId;
	private String userId;
	private String classCode;
	private String orgCode;
	private String copyCode;
	private String periodYear;
	private String item;
	private String itemName;
	private Boolean readRight;
	private Boolean writeRight;
	private Boolean controlAll;
	
	@Id
	@GeneratedValue(generator = "uuid")     
	@GenericGenerator(name = "uuid", strategy = "uuid")  
	public String getDataPrivilegeId() {
		return dataPrivilegeId;
	}

	public void setDataPrivilegeId(String dataPrivilegeId) {
		this.dataPrivilegeId = dataPrivilegeId;
	}

	@Column(nullable=false,length=20)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(nullable=false,length=10)
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Column(nullable=false)
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Column(length=20)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(length=20)
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
	
	@Column(name="read_right")
	public Boolean getReadRight() {
		return readRight;
	}

	public void setReadRight(Boolean readRight) {
		this.readRight = readRight;
	}

	@Column(name="write_right")
	public Boolean getWriteRight() {
		return writeRight;
	}

	public void setWriteRight(Boolean writeRight) {
		this.writeRight = writeRight;
	}

	@Column(name="control_all")
	public Boolean getControlAll() {
		return controlAll;
	}

	public void setControlAll(Boolean controlAll) {
		this.controlAll = controlAll;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((classCode == null) ? 0 : classCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((dataPrivilegeId == null) ? 0 : dataPrivilegeId.hashCode());
		result = prime * result
				+ ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserDataPrivilege other = (UserDataPrivilege) obj;
		if (classCode == null) {
			if (other.classCode != null)
				return false;
		} else if (!classCode.equals(other.classCode))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (dataPrivilegeId == null) {
			if (other.dataPrivilegeId != null)
				return false;
		} else if (!dataPrivilegeId.equals(other.dataPrivilegeId))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDataPrivilege [dataPrivilegeId=" + dataPrivilegeId
				+ ", userId=" + userId + ", classCode=" + classCode
				+ ", orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", kjYear=" + periodYear + ", item=" + item + "]";
	}
	

}
