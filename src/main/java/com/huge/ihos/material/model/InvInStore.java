package com.huge.ihos.material.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;
/**
 * 
 * @author 
 * @date 
 */
@Entity
@Table(name = "v_mm_invInstore")
public class InvInStore extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String invId;
	private String invCode;
	private String invName;
	private String invModel;
	private String firstUnit;
	private String cnCode;
	private String invTypeId;
	private String invTypeName;
	private String vendorId;
	private String vendorName;
	private String orgCode;
	private String copyCode;
	private String storeId;
	
	private Boolean isBar;
	private Boolean isQuality;
	private Boolean isApply;
	private Double refCost;
	private String factory;
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	public String getInvCode() {
		return invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}

	public String getInvName() {
		return invName;
	}

	public void setInvName(String invName) {
		this.invName = invName;
	}

	public String getInvModel() {
		return invModel;
	}

	public void setInvModel(String invModel) {
		this.invModel = invModel;
	}

	public String getFirstUnit() {
		return firstUnit;
	}

	public void setFirstUnit(String firstUnit) {
		this.firstUnit = firstUnit;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	public String getInvTypeId() {
		return invTypeId;
	}

	public void setInvTypeId(String invTypeId) {
		this.invTypeId = invTypeId;
	}

	public String getInvTypeName() {
		return invTypeName;
	}

	public void setInvTypeName(String invTypeName) {
		this.invTypeName = invTypeName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name = "orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public Boolean getIsBar() {
		return isBar;
	}

	public void setIsBar(Boolean isBar) {
		this.isBar = isBar;
	}

	public Boolean getIsQuality() {
		return isQuality;
	}

	public void setIsQuality(Boolean isQuality) {
		this.isQuality = isQuality;
	}
	
	public Boolean getIsApply() {
		return isApply;
	}

	public void setIsApply(Boolean isApply) {
		this.isApply = isApply;
	}

	public Double getRefCost() {
		return refCost;
	}

	public void setRefCost(Double refCost) {
		this.refCost = refCost;
	}
	
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((firstUnit == null) ? 0 : firstUnit.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invCode == null) ? 0 : invCode.hashCode());
		result = prime * result + ((invId == null) ? 0 : invId.hashCode());
		result = prime * result
				+ ((invModel == null) ? 0 : invModel.hashCode());
		result = prime * result + ((invName == null) ? 0 : invName.hashCode());
		result = prime * result
				+ ((invTypeId == null) ? 0 : invTypeId.hashCode());
		result = prime * result
				+ ((invTypeName == null) ? 0 : invTypeName.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result + ((isApply == null) ? 0 : isApply.hashCode());
		result = prime * result+ ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result+ ((vendorName == null) ? 0 : vendorName.hashCode());
		result = prime * result+ ((isBar == null) ? 0 : isBar.hashCode());
		result = prime * result+ ((isQuality == null) ? 0 : isQuality.hashCode());
		result = prime * result+ ((refCost == null) ? 0 : refCost.hashCode());
		result = prime * result+ ((factory == null) ? 0 : factory.hashCode());
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
		InvInStore other = (InvInStore) obj;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (firstUnit == null) {
			if (other.firstUnit != null)
				return false;
		} else if (!firstUnit.equals(other.firstUnit))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invCode == null) {
			if (other.invCode != null)
				return false;
		} else if (!invCode.equals(other.invCode))
			return false;
		if (invId == null) {
			if (other.invId != null)
				return false;
		} else if (!invId.equals(other.invId))
			return false;
		if (invModel == null) {
			if (other.invModel != null)
				return false;
		} else if (!invModel.equals(other.invModel))
			return false;
		if (invName == null) {
			if (other.invName != null)
				return false;
		} else if (!invName.equals(other.invName))
			return false;
		if (invTypeId == null) {
			if (other.invTypeId != null)
				return false;
		} else if (!invTypeId.equals(other.invTypeId))
			return false;
		if (invTypeName == null) {
			if (other.invTypeName != null)
				return false;
		} else if (!invTypeName.equals(other.invTypeName))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (storeId == null) {
			if (other.storeId != null)
				return false;
		} else if (!storeId.equals(other.storeId))
			return false;
		if (vendorId == null) {
			if (other.vendorId != null)
				return false;
		} else if (!vendorId.equals(other.vendorId))
			return false;
		if (vendorName == null) {
			if (other.vendorName != null)
				return false;
		} else if (!vendorName.equals(other.vendorName))
			return false;
		if (isBar == null) {
			if (other.isBar != null)
				return false;
		} else if (!isBar.equals(other.isBar))
			return false;
		if (isQuality == null) {
			if (other.isQuality != null)
				return false;
		} else if (!isQuality.equals(other.isQuality))
			return false;
		if (refCost == null) {
			if (other.refCost != null)
				return false;
		} else if (!refCost.equals(other.refCost))
			return false;
		if (cnCode == null) {
			if (other.cnCode != null)
				return false;
		} else if (!cnCode.equals(other.cnCode))
			return false;
		if (isApply == null) {
			if (other.isApply != null)
				return false;
		} else if (!isApply.equals(other.isApply))
			return false;
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvInStore [id=" + id + ", invId=" + invId + ", invCode="
				+ invCode + ", invName=" + invName + ", invModel=" + invModel
				+ ", firstUnit=" + firstUnit + ", invTypeId=" + invTypeId
				+ ", invTypeName=" + invTypeName + ", vendorId=" + vendorId
				+ ", vendorName=" + vendorName + ", orgCode=" + orgCode
				+ ", isBar=" + isBar + ", isQuality=" + isQuality
				+ ", refCost=" + refCost +", cnCode="+cnCode+",isApply="+isApply
				+ ", copyCode=" + copyCode + ", storeId=" + storeId + ", factory="+factory+"]";
	}

	

}
