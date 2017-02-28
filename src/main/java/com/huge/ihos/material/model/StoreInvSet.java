package com.huge.ihos.material.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.model.BaseObject;
/**
 * 仓库材料设置
 * @author gaozhengyang
 * @date 2013-11-26
 */
@Entity
@Table(name = "mm_storeInvSet")
public class StoreInvSet extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String storeInvSetId;
	private InventoryDict inventoryDict;
	private Store store;
	private StorePosition storePosition;
	// 安全库存
	private Double safestock;
	// 最低限
	private Double lowLimit;
	// 最高限
	private Double highLimit;
	
	private String orgCode;

	@Column(name = "orgCode", length = 20, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Id
	@Column(name = "storeInvSetId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getStoreInvSetId() {
		return storeInvSetId;
	}

	public void setStoreInvSetId(String storeInvSetId) {
		this.storeInvSetId = storeInvSetId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInventoryDict() {
		return inventoryDict;
	}

	public void setInventoryDict(InventoryDict inventoryDict) {
		this.inventoryDict = inventoryDict;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posId", nullable = true)
	public StorePosition getStorePosition() {
		return storePosition;
	}

	public void setStorePosition(StorePosition storePosition) {
		this.storePosition = storePosition;
	}

	@Column(name = "safestock", nullable = true, columnDefinition = "numeric default 0.0 ")
	public Double getSafestock() {
		return safestock;
	}

	public void setSafestock(Double safestock) {
		this.safestock = safestock;
	}

	@Column(name = "low_limit", nullable = true, columnDefinition = "numeric default 0.0 ")
	public Double getLowLimit() {
		return lowLimit;
	}

	public void setLowLimit(Double lowLimit) {
		this.lowLimit = lowLimit;
	}

	@Column(name = "high_limit", nullable = true, columnDefinition = "numeric default 0.0")
	public Double getHighLimit() {
		return highLimit;
	}

	public void setHighLimit(Double highLimit) {
		this.highLimit = highLimit;
	}
	
	private String storeNames;
	
	@Transient
	public String getStoreNames() {
		return storeNames;
	}

	public void setStoreNames(String storeNames) {
		this.storeNames = storeNames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((highLimit == null) ? 0 : highLimit.hashCode());
		result = prime * result
				+ ((inventoryDict == null) ? 0 : inventoryDict.hashCode());
		result = prime * result
				+ ((lowLimit == null) ? 0 : lowLimit.hashCode());
		result = prime * result
				+ ((safestock == null) ? 0 : safestock.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result
				+ ((storePosition == null) ? 0 : storePosition.hashCode());
		result = prime * result
				+ ((orgCode == null) ? 0 : orgCode.hashCode());
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
		StoreInvSet other = (StoreInvSet) obj;
		if (highLimit == null) {
			if (other.highLimit != null)
				return false;
		} else if (!highLimit.equals(other.highLimit))
			return false;
		if (inventoryDict == null) {
			if (other.inventoryDict != null)
				return false;
		} else if (!inventoryDict.equals(other.inventoryDict))
			return false;
		if (lowLimit == null) {
			if (other.lowLimit != null)
				return false;
		} else if (!lowLimit.equals(other.lowLimit))
			return false;
		if (safestock == null) {
			if (other.safestock != null)
				return false;
		} else if (!safestock.equals(other.safestock))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if (storePosition == null) {
			if (other.storePosition != null)
				return false;
		} else if (!storePosition.equals(other.storePosition))
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
		return "StoreInvSet [storeInvSetId=" + storeInvSetId+",orgCode="+orgCode
				+ ", inventoryDict=" + inventoryDict + ", store=" + store
				+ ", storePosition=" + storePosition + ", safestock="
				+ safestock + ", lowLimit=" + lowLimit + ", highLimit="
				+ highLimit + "]";
	}

}
