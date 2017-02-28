package com.huge.ihos.material.deptplan.model;

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
import com.huge.model.BaseObject;
@Entity
@Table(name = "mm_dept_plan_detail")
public class DeptNeedPlanDetail extends BaseObject implements Serializable,Cloneable  {

	private static final long serialVersionUID = 5095423189794353839L;
	private String needDetailId;
	private DeptNeedPlan deptNeedPlan;
	private InventoryDict invDict;
	private Double amount = 0d;//计划数量
	private Double sumAmount = 0d;//
	private Double price = 0d;//单价 
	private String remark;
	private Date needDate;//需求日期

	@Id
	@Column(name = "needDetailId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getNeedDetailId() {
		return needDetailId;
	}

	public void setNeedDetailId(String needDetailId) {
		this.needDetailId = needDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "needId", nullable = false)
	public DeptNeedPlan getDeptNeedPlan() {
		return deptNeedPlan;
	}

	public void setDeptNeedPlan(DeptNeedPlan deptNeedPlan) {
		this.deptNeedPlan = deptNeedPlan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	@Column(name = "amount", nullable = false)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "sumAmount", nullable = false)
	public Double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "price", nullable = false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "needDate", length = 19, nullable = false)
	public Date getNeedDate() {
		return needDate;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	@Transient
	public Double getMoney() {
		return this.amount*this.price;
	}
	
	private Double lastAmount = 0d;//上期计划量
	private Double lastCostAmount = 0d;//上期耗用量
	private Double storeAmount = 0d;//库存量
//	private Double realBuyAmount = 0d;//执行数量
	
	@Transient
	public Double getLastAmount() {
		return lastAmount;
	}
	
	public void setLastAmount(Double lastAmount) {
		this.lastAmount = lastAmount;
	}

	@Transient
	public Double getLastCostAmount() {
		return lastCostAmount;
	}
	
	public void setLastCostAmount(Double lastCostAmount) {
		this.lastCostAmount = lastCostAmount;
	}
	
	@Transient
	public Double getStoreAmount() {
		return storeAmount;
	}
	
	public void setStoreAmount(Double storeAmount) {
		this.storeAmount = storeAmount;
	}

//	@Transient
//	public Double getRealBuyAmount() {
//		return realBuyAmount;
//	}
//	
//	public void setRealBuyAmount(Double realBuyAmount) {
//		this.realBuyAmount = realBuyAmount;
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((deptNeedPlan == null) ? 0 : deptNeedPlan.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result
				+ ((needDate == null) ? 0 : needDate.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((sumAmount == null) ? 0 : sumAmount.hashCode());
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
		DeptNeedPlanDetail other = (DeptNeedPlanDetail) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (deptNeedPlan == null) {
			if (other.deptNeedPlan != null)
				return false;
		} else if (!deptNeedPlan.equals(other.deptNeedPlan))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (needDate == null) {
			if (other.needDate != null)
				return false;
		} else if (!needDate.equals(other.needDate))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sumAmount == null) {
			if (other.sumAmount != null)
				return false;
		} else if (!sumAmount.equals(other.sumAmount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptNeedPlanDetail [needDetailId=" + needDetailId
				+ ", deptNeedPlan=" + deptNeedPlan + ", invDict=" + invDict
				+ ", amount=" + amount + ", sumAmount=" + sumAmount
				+ ", price=" + price + ", remark=" + remark + ", needDate="
				+ needDate + "]";
	}
	//clone
	 public DeptNeedPlanDetail clone() {  
		 DeptNeedPlanDetail o = null;  
	        try {  
	            o = (DeptNeedPlanDetail) super.clone();  
	        } catch (CloneNotSupportedException e) {  
	            e.printStackTrace();  
	        }  
	        return o;  
	    }  
}
