package com.huge.ihos.system.repository.vendor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.model.BaseTreeNode;

/**
 * 单位类型(供应商类型)
 * 
 * @author gaozhengyang
 * @date 2013-12-4
 */
@Entity
@Table(name = "sy_vendorType")
public class VendorType extends BaseTreeNode<VendorType, String> {
	private static final long serialVersionUID = -3117732076609490792L;
	private String vendorTypeCode;
	private String orgCode;
	private String vendorTypeName;
	private Boolean leaf = true;
	private Boolean disabled = false;

	@Id
	@Column(name = "vendorTypeId", length = 30, nullable = false)
	public String getId() {
		return super.getPK();
	}
	
	public void setId(String id) {
		super.setId(id);
	}

	@Column(name = "vendorTypeCode", length = 20, nullable = false)
	public String getVendorTypeCode() {
		return vendorTypeCode;
	}

	public void setVendorTypeCode(String vendorTypeCode) {
		this.vendorTypeCode = vendorTypeCode;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "vendorTypeName", length = 20, nullable = false)
	public String getVendorTypeName() {
		return vendorTypeName;
	}

	public void setVendorTypeName(String vendorTypeName) {
		this.vendorTypeName = vendorTypeName;
	}

	@Column(name = "leaf", nullable = false, columnDefinition = "bit default 1")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Transient
	public String getVendorTypeNameWithCode() {
		if (null == this.getParentNode()) {
			return this.getVendorTypeName();
		}
		return this.getVendorTypeName() + "(" + this.getVendorTypeCode() + ")";
	}

	@Override
	@Transient
	public boolean getIsLeaf() {
		return this.leaf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((vendorTypeCode == null) ? 0 : vendorTypeCode.hashCode());
		result = prime * result
				+ ((vendorTypeName == null) ? 0 : vendorTypeName.hashCode());
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
		VendorType other = (VendorType) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
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
		if (vendorTypeCode == null) {
			if (other.vendorTypeCode != null)
				return false;
		} else if (!vendorTypeCode.equals(other.vendorTypeCode))
			return false;
		if (vendorTypeName == null) {
			if (other.vendorTypeName != null)
				return false;
		} else if (!vendorTypeName.equals(other.vendorTypeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VendorType [vendorTypeId=" + this.getId() + ", vendorTypeCode="
				+ vendorTypeCode + ", orgCode=" + orgCode + ", vendorTypeName="
				+ vendorTypeName + ", leaf=" + leaf + ", disabled=" + disabled
				+ "]";
	}

}
