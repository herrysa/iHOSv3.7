package com.huge.ihos.material.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;
/**
 * 
 * @author Gaozhengyang
 * @date 2014年7月18日
 */
@Entity
@Table(name = "v_mm_invOutStore")
public class InvOutStore extends BaseObject implements Serializable{
	private static final long serialVersionUID = 1648367478078961784L;
	private String id;
	private String storeId;
	private String orgCode;
	private String copyCode;
	private String yearMonth;
	
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
	private String factory;
	private String batchId;
	private String batchNo;
	private Date productionDate;
	private String barCode;
	private String guarantee;
	private Double price;
	private Double curAmount;
	private Double curMoney;
	private Date validityDate;
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

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

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCurAmount() {
		return curAmount;
	}

	public void setCurAmount(Double curAmount) {
		this.curAmount = curAmount;
	}

	public Double getCurMoney() {
		return curMoney;
	}

	public void setCurMoney(Double curMoney) {
		this.curMoney = curMoney;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barCode == null) ? 0 : barCode.hashCode());
		result = prime * result + ((batchId == null) ? 0 : batchId.hashCode());
		result = prime * result + ((batchNo == null) ? 0 : batchNo.hashCode());
		result = prime * result + ((cnCode == null) ? 0 : cnCode.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((curAmount == null) ? 0 : curAmount.hashCode());
		result = prime * result
				+ ((curMoney == null) ? 0 : curMoney.hashCode());
		result = prime * result + ((factory == null) ? 0 : factory.hashCode());
		result = prime * result
				+ ((firstUnit == null) ? 0 : firstUnit.hashCode());
		result = prime * result
				+ ((guarantee == null) ? 0 : guarantee.hashCode());
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
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((productionDate == null) ? 0 : productionDate.hashCode());
		result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
		result = prime * result
				+ ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result
				+ ((vendorName == null) ? 0 : vendorName.hashCode());
		result = prime * result
				+ ((yearMonth == null) ? 0 : yearMonth.hashCode());
		result = prime * result
				+ ((validityDate == null) ? 0 : validityDate.hashCode());
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
		InvOutStore other = (InvOutStore) obj;
		if (barCode == null) {
			if (other.barCode != null)
				return false;
		} else if (!barCode.equals(other.barCode))
			return false;
		if (batchId == null) {
			if (other.batchId != null)
				return false;
		} else if (!batchId.equals(other.batchId))
			return false;
		if (batchNo == null) {
			if (other.batchNo != null)
				return false;
		} else if (!batchNo.equals(other.batchNo))
			return false;
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
		if (curAmount == null) {
			if (other.curAmount != null)
				return false;
		} else if (!curAmount.equals(other.curAmount))
			return false;
		if (curMoney == null) {
			if (other.curMoney != null)
				return false;
		} else if (!curMoney.equals(other.curMoney))
			return false;
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		if (firstUnit == null) {
			if (other.firstUnit != null)
				return false;
		} else if (!firstUnit.equals(other.firstUnit))
			return false;
		if (guarantee == null) {
			if (other.guarantee != null)
				return false;
		} else if (!guarantee.equals(other.guarantee))
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
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productionDate == null) {
			if (other.productionDate != null)
				return false;
		} else if (!productionDate.equals(other.productionDate))
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
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		if (validityDate == null) {
			if (other.validityDate != null)
				return false;
		} else if (!validityDate.equals(other.validityDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvOutStore [id=" + id + ", storeId=" + storeId + ", orgCode="
				+ orgCode + ", copyCode=" + copyCode + ", yearMonth="
				+ yearMonth + ", invId=" + invId + ", invCode=" + invCode
				+ ", invName=" + invName + ", invModel=" + invModel
				+ ", firstUnit=" + firstUnit + ", cnCode=" + cnCode
				+ ", invTypeId=" + invTypeId + ", invTypeName=" + invTypeName
				+ ", vendorId=" + vendorId + ", vendorName=" + vendorName
				+ ", factory=" + factory + ", batchId=" + batchId
				+ ", batchNo=" + batchNo + ", productionDate=" + productionDate
				+ ", barCode=" + barCode + ", guarantee=" + guarantee
				+ ", price=" + price + ", curAmount=" + curAmount
				+ ", curMoney=" + curMoney + ", validityDate="+validityDate+"]";
	}

}
