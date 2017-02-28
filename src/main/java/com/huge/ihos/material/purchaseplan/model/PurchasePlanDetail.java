package com.huge.ihos.material.purchaseplan.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.system.repository.vendor.model.Vendor;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;
/**
 * 采购计划明细
 * @author Gaozhengyang
 * @date 2014年5月8日
 */
@Entity
@Table(name = "mm_purchase_plan_detail")
public class PurchasePlanDetail extends BaseObject implements Serializable,Cloneable{
	private static final long serialVersionUID = -9075180335618929286L;
	private String purchaseDetailId;
	private PurchasePlan purchasePlan;
	private InventoryDict invDict;//材料
	private Department needDept;//需求部门
	private Double amount = 0d;//采购数量
	private Double price = 0d;//计划单价
	private Date arrivalDate;//计划到货日期
	private Person purchaser;//采购员
	private String remark;
	
	@Id
	@Column(name = "purchaseDetailId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getPurchaseDetailId() {
		return purchaseDetailId;
	}

	public void setPurchaseDetailId(String purchaseDetailId) {
		this.purchaseDetailId = purchaseDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaseId", nullable = false)
	public PurchasePlan getPurchasePlan() {
		return purchasePlan;
	}

	public void setPurchasePlan(PurchasePlan purchasePlan) {
		this.purchasePlan = purchasePlan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "needDeptId", nullable = true)
	public Department getNeedDept() {
		return needDept;
	}

	public void setNeedDept(Department needDept) {
		this.needDept = needDept;
	}

	@Column(name = "amount", nullable = false)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "price", nullable = false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "arrivalDate", length = 19, nullable = true)
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId", nullable = true)
	public Person getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(Person purchaser) {
		this.purchaser = purchaser;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// VendorId Varchar 30 √ 主要供货单位id，取 往来单位字典
		private Vendor vendor;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "vendorId", nullable = true)
		public Vendor getVendor() {
			return vendor;
		}

		public void setVendor(Vendor vendor) {
			this.vendor = vendor;
		}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result
				+ ((needDept == null) ? 0 : needDept.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((purchasePlan == null) ? 0 : purchasePlan.hashCode());
		result = prime * result
				+ ((purchaser == null) ? 0 : purchaser.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		PurchasePlanDetail other = (PurchasePlanDetail) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (arrivalDate == null) {
			if (other.arrivalDate != null)
				return false;
		} else if (!arrivalDate.equals(other.arrivalDate))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (needDept == null) {
			if (other.needDept != null)
				return false;
		} else if (!needDept.equals(other.needDept))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (purchasePlan == null) {
			if (other.purchasePlan != null)
				return false;
		} else if (!purchasePlan.equals(other.purchasePlan))
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
		if(vendor == null){
			if(other.vendor != null)
				return false;
		}else if(!vendor.equals(other.vendor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PurchasePlanDetail [purchaseDetailId=" + purchaseDetailId
				+ ", purchasePlan=" + purchasePlan + ", invDict=" + invDict
				+ ", needDept=" + needDept + ", amount=" + amount + ", price="
				+ price + ", arrivalDate=" + arrivalDate
				+ ", purchaser=" + purchaser + ", remark=" + remark + ",vendor" + vendor +"]";
	}

	private Double curStock = 0d;//当前库存
	private Double storeStock = 0d;//对应仓库库存
	private Double needAmount = 0d;//科室需求数量
	
	@Transient
	public Double getCurStock() {
		return curStock;
	}
	
	public void setCurStock(Double curStock) {
		this.curStock = curStock;
	}

	@Transient
	public Double getStoreStock() {
		return storeStock;
	}
	
	public void setStoreStock(Double storeStock) {
		this.storeStock = storeStock;
	}

	@Column(name = "needAmount", nullable = true)
	public Double getNeedAmount() {
		return needAmount;
	}
	
	public void setNeedAmount(Double needAmount) {
		this.needAmount = needAmount;
	}

	@Transient
	public Double getMoney() {
		return this.amount*this.price;
	}
	//Clone
	public PurchasePlanDetail clone() {  
		PurchasePlanDetail o = null;  
        try {  
            o = (PurchasePlanDetail) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }  
	
}
