package com.huge.ihos.material.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.inout.model.CostItem;
import com.huge.model.BaseTreeNode;

@Entity
@Table(name = "mm_InvType")
public class InventoryType extends BaseTreeNode<InventoryType, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8270529763019361093L;
	
	private String orgCode;
	
	private String copyCode;
	
	private String invTypeCode;
	
	private String invTypeName;
	
	private String cnCode;
	
	private Boolean disabled = false;
	
	private Boolean leaf = true;
	
	private Boolean isBudg = true;
	
	private CostItem costItem;
	
	private String remark;

	@Id
	@Column(name = "InvtypeId", length = 40, nullable = false)
	public String getId() {
		return super.getPK();
	}

	public void setId(String id) {
		super.setId(id);
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

	@Column(name = "InvtypeCode", length = 20, nullable = false)
	public String getInvTypeCode() {
		return invTypeCode;
	}

	public void setInvTypeCode(String invTypeCode) {
		this.invTypeCode = invTypeCode;
	}

	@Column(name = "Invtype", length = 100, nullable = false)
	public String getInvTypeName() {
		return invTypeName;
	}

	public void setInvTypeName(String invTypeName) {
		this.invTypeName = invTypeName;
	}

	@Column(name = "cncode", length = 100, nullable = true)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "leaf", nullable = false, columnDefinition = "bit default 1")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "is_budg", nullable = false, columnDefinition = "bit default 1")
	public Boolean getIsBudg() {
		return isBudg;
	}

	public void setIsBudg(Boolean isBudg) {
		this.isBudg = isBudg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "costItemId", nullable = true)
	public CostItem getCostItem() {
		return costItem;
	}

	public void setCostItem(CostItem costItem) {
		this.costItem = costItem;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public String getInvTypeNameWithCode(){
		if(null == this.getParentNode()){
			return this.getInvTypeName();
		}
		return this.getInvTypeName()+"("+this.getInvTypeCode()+")";
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
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((costItem == null) ? 0 : costItem.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((invTypeCode == null) ? 0 : invTypeCode.hashCode());
		result = prime * result
				+ ((invTypeName == null) ? 0 : invTypeName.hashCode());
		result = prime * result + ((isBudg == null) ? 0 : isBudg.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
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
		InventoryType other = (InventoryType) obj;
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
		if (costItem == null) {
			if (other.costItem != null)
				return false;
		} else if (!costItem.equals(other.costItem))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (invTypeCode == null) {
			if (other.invTypeCode != null)
				return false;
		} else if (!invTypeCode.equals(other.invTypeCode))
			return false;
		if (invTypeName == null) {
			if (other.invTypeName != null)
				return false;
		} else if (!invTypeName.equals(other.invTypeName))
			return false;
		if (isBudg == null) {
			if (other.isBudg != null)
				return false;
		} else if (!isBudg.equals(other.isBudg))
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InventoryType [id="+this.getId()+"orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", invTypeCode=" + invTypeCode + ", invTypeName="
				+ invTypeName + ", cnCode=" + cnCode + ", disabled=" + disabled
				+ ", leaf=" + leaf + ", isBudg=" + isBudg + ", costItem="
				+ costItem + ", remark=" + remark + "]";
	}

}
