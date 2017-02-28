package com.huge.ihos.system.repository.store.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;

/**
 * 货位字典
 * 
 * @author gaozhengyang
 * @date 2013-11-14
 */
@Entity
@Table(name = "sy_storePos")
public class StorePosition extends BaseObject implements Serializable {
	private static final long serialVersionUID = -3585706307581245473L;
	private String posId;
	private String orgCode;
	private String posCode;
	private String posName;
	private Store store;
	private String remark;
	private Boolean disabled = false;

	@Id
	@Column(name = "posId", length = 30, nullable = false)
	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "postCode", length = 20, nullable = false)
	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	@Column(name = "postName", length = 50, nullable = false)
	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = true)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Column(name = "remark", length = 100, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((posCode == null) ? 0 : posCode.hashCode());
		result = prime * result + ((posName == null) ? 0 : posName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
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
		StorePosition other = (StorePosition) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (posCode == null) {
			if (other.posCode != null)
				return false;
		} else if (!posCode.equals(other.posCode))
			return false;
		if (posName == null) {
			if (other.posName != null)
				return false;
		} else if (!posName.equals(other.posName))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StorePosition [posId=" + posId + ", orgCode=" + orgCode
				+ ", posCode=" + posCode + ", posName=" + posName + ", store="
				+ store + ", remark=" + remark + ", disabled=" + disabled + "]";
	}

}
