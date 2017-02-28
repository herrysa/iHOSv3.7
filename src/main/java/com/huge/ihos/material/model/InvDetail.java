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

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_detail")
public class InvDetail extends BaseObject implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3140602279979798921L;

									
	private String invDetialId;					// id Char 32 Uuid
	
	private InvMain invMain;					// Io_id Char 32 主表单据id
	
	private InventoryDict invDict;				// InvId varchar 30 材料id
	
	private Double inAmount;					// inAmount Numeric 10,,2 数量
	
	private Double inPrice;						// inPrice Numeric 10,4 单价

	private Double inMoney;						// inMoney Numeric 18,4 金额

	private Double addRate;						// Addrate Numeric 10,4 √ 加成率

	private Double salePrice;					// SalePrice Numeric 10,4 √ 零售价

	private String snCode;						// Sn varchar 20 条形码
	
	private String unitBarCode;					// barCode varchar 20 院内码(个体码)
	
	private Boolean isPerBar = false;			// IsPerbar Bit 1 是否个体条码

	private Double currentStock;				//瞬态.显示当前库存数量
	
	private InvBatch invBatch;					//批号

	
	@Id
	@Column(name = "io_DetialId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getInvDetialId() {
		return invDetialId;
	}

	public void setInvDetialId(String invDetialId) {
		this.invDetialId = invDetialId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "io_id", nullable = false)
	public InvMain getInvMain() {
		return invMain;
	}

	public void setInvMain(InvMain invMain) {
		this.invMain = invMain;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inv_id", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}
	

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batchId", nullable = false)
	public InvBatch getInvBatch() {
		return invBatch;
	}

	public void setInvBatch(InvBatch invBatch) {
		this.invBatch = invBatch;
	}

	@Column(name = "currentStock", nullable = true)
	public Double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Double currentStock) {
		this.currentStock = currentStock;
	}

	@Column(name = "inAmount", nullable = false)
	public Double getInAmount() {
		return inAmount;
	}

	public void setInAmount(Double inAmount) {
		this.inAmount = inAmount;
	}

	@Column(name = "inPrice", nullable = false)
	public Double getInPrice() {
		return inPrice;
	}

	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}

	@Column(name = "inMoney", nullable = false)
	public Double getInMoney() {
		return inMoney;
	}

	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}

	@Column(name = "addRate", nullable = true)
	public Double getAddRate() {
		return addRate;
	}

	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}

	@Column(name = "salePrice", nullable = true)
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	@Column(name = "snCode", length = 20, nullable = true)
	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	@Column(name = "unitBarCode", length = 20, nullable = true)
	public String getUnitBarCode() {
		return unitBarCode;
	}

	public void setUnitBarCode(String unitBarCode) {
		this.unitBarCode = unitBarCode;
	}

	

	@Column(name = "isPerBar", nullable = false, columnDefinition = "bit default 0")
	public Boolean getIsPerBar() {
		return isPerBar;
	}

	public void setIsPerBar(Boolean isPerBar) {
		this.isPerBar = isPerBar;
	}

	@Override
	public Object clone(){
		 InvDetail o = null;
	        try{
	            o = (InvDetail)super.clone();
	        }catch(CloneNotSupportedException e){
	            e.printStackTrace();
	        }
	        return o;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addRate == null) ? 0 : addRate.hashCode());
		result = prime * result + ((inAmount == null) ? 0 : inAmount.hashCode());
		result = prime * result + ((inMoney == null) ? 0 : inMoney.hashCode());
		result = prime * result + ((inPrice == null) ? 0 : inPrice.hashCode());
		result = prime * result + ((invBatch == null) ? 0 : invBatch.hashCode());
		result = prime * result + ((invDetialId == null) ? 0 : invDetialId.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((invMain == null) ? 0 : invMain.hashCode());
		result = prime * result + ((isPerBar == null) ? 0 : isPerBar.hashCode());
		result = prime * result + ((salePrice == null) ? 0 : salePrice.hashCode());
		result = prime * result + ((snCode == null) ? 0 : snCode.hashCode());
		result = prime * result + ((unitBarCode == null) ? 0 : unitBarCode.hashCode());
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
		InvDetail other = (InvDetail) obj;
		if (addRate == null) {
			if (other.addRate != null)
				return false;
		} else if (!addRate.equals(other.addRate))
			return false;
		if (inAmount == null) {
			if (other.inAmount != null)
				return false;
		} else if (!inAmount.equals(other.inAmount))
			return false;
		if (inMoney == null) {
			if (other.inMoney != null)
				return false;
		} else if (!inMoney.equals(other.inMoney))
			return false;
		if (inPrice == null) {
			if (other.inPrice != null)
				return false;
		} else if (!inPrice.equals(other.inPrice))
			return false;
		if (invBatch == null) {
			if (other.invBatch != null)
				return false;
		} else if (!invBatch.equals(other.invBatch))
			return false;
		if (invDetialId == null) {
			if (other.invDetialId != null)
				return false;
		} else if (!invDetialId.equals(other.invDetialId))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (invMain == null) {
			if (other.invMain != null)
				return false;
		} else if (!invMain.equals(other.invMain))
			return false;
		if (isPerBar == null) {
			if (other.isPerBar != null)
				return false;
		} else if (!isPerBar.equals(other.isPerBar))
			return false;
		if (salePrice == null) {
			if (other.salePrice != null)
				return false;
		} else if (!salePrice.equals(other.salePrice))
			return false;
		if (snCode == null) {
			if (other.snCode != null)
				return false;
		} else if (!snCode.equals(other.snCode))
			return false;
		if (unitBarCode == null) {
			if (other.unitBarCode != null)
				return false;
		} else if (!unitBarCode.equals(other.unitBarCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvDetail [invDetialId=" + invDetialId + ", invMain=" + invMain + ", invDict=" + invDict + ", invBatch=" + invBatch + ", inAmount=" + inAmount + ", inPrice=" + inPrice + ", inMoney="
				+ inMoney + ", addRate=" + addRate + ", salePrice=" + salePrice + ", snCode=" + snCode + ", unitBarCode=" + unitBarCode + ", isPerBar=" + isPerBar + "]";
	}

}
