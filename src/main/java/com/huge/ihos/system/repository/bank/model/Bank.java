package com.huge.ihos.system.repository.bank.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_bank")
public class Bank extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6848681175326218657L;
	private String bankId;
	private String bankName;
	private Boolean disabled = false;
	private String remark;
	@Id
	@Column(name = "bankId", nullable = false, length = 50)
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	@Column(name = "bankName", nullable = false, length = 50)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "disabled", nullable = false)
	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Bank [bankId=" + bankId + ", bankName="
				+ bankName + ", disabled=" + disabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bankId == null) ? 0 : bankId.hashCode());
		result = prime * result
				+ ((bankName == null) ? 0 : bankName.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
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
		Bank other = (Bank) obj;
		if (bankId == null) {
			if (other.bankId != null)
				return false;
		} else if (!bankId.equals(other.bankId))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		return true;
	}
}
