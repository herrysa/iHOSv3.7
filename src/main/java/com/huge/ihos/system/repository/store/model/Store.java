package com.huge.ihos.system.repository.store.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseTreeNode;

/**
 * 仓库字典
 * 
 * @author gaozhengyang
 * @date 2013-11-14
 */
@Entity
@Table(name = "sy_store")
public class Store extends BaseTreeNode<Store, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5191307678620386135L;
	// private String id;
	private String orgCode;
	private String storeCode;
	private String storeName;
	private String cnCode;
	/*
	 * 仓库类型： 0 一般库房 1 科室库房 2 其他库房 9 虚拟库房
	 */
	private String storeType;
	private Boolean isPos = true;
	private String address;
	private String telephone;
	private String remark;
	private Boolean leaf;
	private Boolean disabled = false;
	// 期初记账标志
	private Boolean isBook;
	// 封帐标志
	private Boolean isLock;
	private Date startDate;

	private Department department;
	//保管员
	private String keeper;
	//会计
	private String accountor;
	//采购员
	private String purchaser;

	@Id
	@Column(name = "storeId", length = 30, nullable = false)
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

	@Column(name = "storeCode", length = 20, nullable = false)
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Column(name = "storeName", length = 50, nullable = false)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Column(name = "cncode", length = 50, nullable = false)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	@Column(name = "storeType", length = 1, nullable = false)
	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name = "is_pos", nullable = false, columnDefinition = "bit default 1")
	public Boolean getIsPos() {
		return isPos;
	}

	public void setIsPos(Boolean isPos) {
		this.isPos = isPos;
	}

	@Column(name = "addr", length = 100, nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "tel", length = 30, nullable = true)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "is_book", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsBook() {
		return isBook;
	}

	public void setIsBook(Boolean isBook) {
		this.isBook = isBook;
	}

	@Column(name = "is_lock", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	@Column(name = "startDate", nullable = true)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", nullable = true)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "keeperId", nullable = true)
	@Column(name = "keeper", nullable = true,length=50)
	public String getKeeper() {
		return keeper;
	}

	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "acctId", nullable = true)
	@Column(name = "accountor", nullable = true,length=50)
	public String getAccountor() {
		return accountor;
	}

	public void setAccountor(String accountor) {
		this.accountor = accountor;
	}

//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "purId", nullable = true)
	@Column(name = "purchaser", nullable = true,length=50)
	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	@Override
	@Transient
	public boolean getIsLeaf() {
		return this.leaf;
	}

	@Transient
	public String getStoreNameWithCode() {
		if (null == this.getParentNode()) {
			return this.getStoreName();
		}
		return this.getStoreName() + "(" + this.getStoreCode() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accountor == null) ? 0 : accountor.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((isBook == null) ? 0 : isBook.hashCode());
		result = prime * result + ((isLock == null) ? 0 : isLock.hashCode());
		result = prime * result + ((isPos == null) ? 0 : isPos.hashCode());
		result = prime * result + ((keeper == null) ? 0 : keeper.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((purchaser == null) ? 0 : purchaser.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((storeCode == null) ? 0 : storeCode.hashCode());
		result = prime * result + ((storeName == null) ? 0 : storeName.hashCode());
		result = prime * result + ((storeType == null) ? 0 : storeType.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		if (accountor == null) {
			if (other.accountor != null)
				return false;
		} else if (!accountor.equals(other.accountor))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (isBook == null) {
			if (other.isBook != null)
				return false;
		} else if (!isBook.equals(other.isBook))
			return false;
		if (isLock == null) {
			if (other.isLock != null)
				return false;
		} else if (!isLock.equals(other.isLock))
			return false;
		if (isPos == null) {
			if (other.isPos != null)
				return false;
		} else if (!isPos.equals(other.isPos))
			return false;
		if (keeper == null) {
			if (other.keeper != null)
				return false;
		} else if (!keeper.equals(other.keeper))
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
		if (purchaser == null) {
			if (other.purchaser != null)
				return false;
		} else if (!purchaser.equals(other.purchaser))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (storeCode == null) {
			if (other.storeCode != null)
				return false;
		} else if (!storeCode.equals(other.storeCode))
			return false;
		if (storeName == null) {
			if (other.storeName != null)
				return false;
		} else if (!storeName.equals(other.storeName))
			return false;
		if (storeType == null) {
			if (other.storeType != null)
				return false;
		} else if (!storeType.equals(other.storeType))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Store [id=" + this.getId() + ", orgCode=" + orgCode + ", storeCode=" + storeCode + ", storeName=" + storeName + ", cnCode=" + cnCode + ", storeType=" + storeType + ", isPos=" + isPos
				+ ", address=" + address + ", telephone=" + telephone + ", remark=" + remark + ", leaf=" + leaf + ", disabled=" + disabled + ", startDate=" + startDate + ", isBook=" + isBook
				+ ", isLock=" + isLock + ", department=" + department + ", keeper=" + keeper + ", accountor=" + accountor + ", purchaser=" + purchaser + "]";
	}

}
