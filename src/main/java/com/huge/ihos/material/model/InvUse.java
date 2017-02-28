package com.huge.ihos.material.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;
/**
 * 材料用途
 * @author gaozhengyang
 * @date 2013-11-2
 */
@Entity
@Table(name = "mm_InvUse")
public class InvUse extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8694998697386149463L;
	private String invUseId;
	private String orgCode;
	private String copyCode;
	private String invUseCode;
	private String invUseName;

	@Id
	@Column(name = "id", length = 30, nullable = false)
	public String getInvUseId() {
		return invUseId;
	}

	public void setInvUseId(String invUseId) {
		this.invUseId = invUseId;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode", length = 10, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name = "invUseCode", length = 10, nullable = false)
	public String getInvUseCode() {
		return invUseCode;
	}

	public void setInvUseCode(String invUseCode) {
		this.invUseCode = invUseCode;
	}

	@Column(name = "invUse", length = 50, nullable = false)
	public String getInvUseName() {
		return invUseName;
	}

	public void setInvUseName(String invUseName) {
		this.invUseName = invUseName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((invUseCode == null) ? 0 : invUseCode.hashCode());
		result = prime * result
				+ ((invUseName == null) ? 0 : invUseName.hashCode());
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
		InvUse other = (InvUse) obj;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (invUseCode == null) {
			if (other.invUseCode != null)
				return false;
		} else if (!invUseCode.equals(other.invUseCode))
			return false;
		if (invUseName == null) {
			if (other.invUseName != null)
				return false;
		} else if (!invUseName.equals(other.invUseName))
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
		return "InvUse [invUseId=" + invUseId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", invUseCode=" + invUseCode
				+ ", invUseName=" + invUseName + "]";
	}

}
