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

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_balance")
public class InvBalance extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103598565219807598L;

	@Override
	public String toString() {
		return "InvBalance [id=" + id + ", orgCode=" + orgCode + ", copyCode=" + copyCode + ", yearMonth=" + yearMonth + ", store=" + store + ", storePosition=" + storePosition + ", invDict="
				+ invDict + ", beginAmount=" + beginAmount + ", beginMoney=" + beginMoney + ", inAmount=" + inAmount + ", inMoney=" + inMoney + ", outAmount=" + outAmount + ", outMoney=" + outMoney
				+ ", yearBeginAmount=" + yearBeginAmount + ", yearBeginMoney=" + yearBeginMoney + ", yearInAmount=" + yearInAmount + ", yearInMoney=" + yearInMoney + ", yearOutAmount="
				+ yearOutAmount + ", yearOutMoney=" + yearOutMoney + ", priceDiff=" + priceDiff + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvBalance other = (InvBalance) obj;
		if (beginAmount == null) {
			if (other.beginAmount != null)
				return false;
		} else if (!beginAmount.equals(other.beginAmount))
			return false;
		if (beginMoney == null) {
			if (other.beginMoney != null)
				return false;
		} else if (!beginMoney.equals(other.beginMoney))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (outAmount == null) {
			if (other.outAmount != null)
				return false;
		} else if (!outAmount.equals(other.outAmount))
			return false;
		if (outMoney == null) {
			if (other.outMoney != null)
				return false;
		} else if (!outMoney.equals(other.outMoney))
			return false;
		if (priceDiff == null) {
			if (other.priceDiff != null)
				return false;
		} else if (!priceDiff.equals(other.priceDiff))
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
		if (yearBeginAmount == null) {
			if (other.yearBeginAmount != null)
				return false;
		} else if (!yearBeginAmount.equals(other.yearBeginAmount))
			return false;
		if (yearBeginMoney == null) {
			if (other.yearBeginMoney != null)
				return false;
		} else if (!yearBeginMoney.equals(other.yearBeginMoney))
			return false;
		if (yearInAmount == null) {
			if (other.yearInAmount != null)
				return false;
		} else if (!yearInAmount.equals(other.yearInAmount))
			return false;
		if (yearInMoney == null) {
			if (other.yearInMoney != null)
				return false;
		} else if (!yearInMoney.equals(other.yearInMoney))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		if (yearOutAmount == null) {
			if (other.yearOutAmount != null)
				return false;
		} else if (!yearOutAmount.equals(other.yearOutAmount))
			return false;
		if (yearOutMoney == null) {
			if (other.yearOutMoney != null)
				return false;
		} else if (!yearOutMoney.equals(other.yearOutMoney))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beginAmount == null) ? 0 : beginAmount.hashCode());
		result = prime * result + ((beginMoney == null) ? 0 : beginMoney.hashCode());
		result = prime * result + ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inAmount == null) ? 0 : inAmount.hashCode());
		result = prime * result + ((inMoney == null) ? 0 : inMoney.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((outAmount == null) ? 0 : outAmount.hashCode());
		result = prime * result + ((outMoney == null) ? 0 : outMoney.hashCode());
		result = prime * result + ((priceDiff == null) ? 0 : priceDiff.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((storePosition == null) ? 0 : storePosition.hashCode());
		result = prime * result + ((yearBeginAmount == null) ? 0 : yearBeginAmount.hashCode());
		result = prime * result + ((yearBeginMoney == null) ? 0 : yearBeginMoney.hashCode());
		result = prime * result + ((yearInAmount == null) ? 0 : yearInAmount.hashCode());
		result = prime * result + ((yearInMoney == null) ? 0 : yearInMoney.hashCode());
		result = prime * result + ((yearMonth == null) ? 0 : yearMonth.hashCode());
		result = prime * result + ((yearOutAmount == null) ? 0 : yearOutAmount.hashCode());
		result = prime * result + ((yearOutMoney == null) ? 0 : yearOutMoney.hashCode());
		return result;
	}

	private String id;

	@Id
	@Column(name = "id", length = 32, nullable = false)
	/*
	 * @GeneratedValue(generator = "hibernate-uuid")
	 * 
	 * @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	 */
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// OrgCode 单位编码
	private String orgCode;

	@Column(name = "orgCode", length = 20, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	// copyCode 账套编码
	private String copyCode;

	@Column(name = "copyCode", length = 20, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	// yearMonth char 6 会计期间，以填制单据日期所在期间自动赋值
	private String yearMonth;

	@Column(name = "yearMonth", length = 6, nullable = false)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	// posId Varchar 30 ** 货位id 主键

	// InPosid Varchar 30 √ 入库货位id
	private StorePosition storePosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posId", nullable = true)
	public StorePosition getStorePosition() {
		return storePosition;
	}

	public void setStorePosition(StorePosition storePosition) {
		this.storePosition = storePosition;
	}

	// InvId Varchar 30 材料id 主键
	// InvId varchar 30 材料id
	private InventoryDict invDict;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	// begin_amount numeric (16, 2) 0 期初数量

	private Double beginAmount=0d;

	@Column(name = "beginAmount", nullable = false)
	public Double getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(Double beginAmount) {
		this.beginAmount = beginAmount;
	}

	// begin_money numeric (16, 6) 0 期初金额
	private Double beginMoney=0d;

	@Column(name = "beginMoney", nullable = false)
	public Double getBeginMoney() {
		return beginMoney;
	}

	public void setBeginMoney(Double beginMoney) {
		this.beginMoney = beginMoney;
	}

	// In_amount numeric (16, 2) 0 入库数量
	private Double inAmount=0d;

	@Column(name = "inAmount", nullable = false)
	public Double getInAmount() {
		return inAmount;
	}

	public void setInAmount(Double inAmount) {
		this.inAmount = inAmount;
	}

	// In_money numeric (16, 6) 0 入库金额
	private Double inMoney=0d;

	@Column(name = "inMoney", nullable = false)
	public Double getInMoney() {
		return inMoney;
	}

	public void setInMoney(Double inMoney) {
		this.inMoney = inMoney;
	}

	// Out_amount numeric (16, 2) 0 出库数量
	private Double outAmount=0d;

	// Out_money numeric (16, 6) 0 出库金额
	@Column(name = "outAmount", nullable = false)
	public Double getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Double outAmount) {
		this.outAmount = outAmount;
	}

	private Double outMoney=0d;

	@Column(name = "outMoney", nullable = false)
	public Double getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(Double outMoney) {
		this.outMoney = outMoney;
	}

	// Y_begin_amount numeric (16, 2) 0 年初数量
	private Double yearBeginAmount=0d;

	@Column(name = "yearBeginAmount", nullable = false)
	public Double getYearBeginAmount() {
		return yearBeginAmount;
	}

	public void setYearBeginAmount(Double yearBeginAmount) {
		this.yearBeginAmount = yearBeginAmount;
	}

	// Y_begin_money numeric (16, 6) 0 年初金额
	private Double yearBeginMoney=0d;

	@Column(name = "yearBeginMoney", nullable = false)
	public Double getYearBeginMoney() {
		return yearBeginMoney;
	}

	public void setYearBeginMoney(Double yearBeginMoney) {
		this.yearBeginMoney = yearBeginMoney;
	}

	// Y_In_amount numeric (16, 2) 0 本年入库数量
	private Double yearInAmount=0d;

	@Column(name = "yearInAmount", nullable = false)
	public Double getYearInAmount() {
		return yearInAmount;
	}

	public void setYearInAmount(Double yearInAmount) {
		this.yearInAmount = yearInAmount;
	}

	// Y_In_money numeric (16, 6) 0 本年入库金额

	private Double yearInMoney=0d;

	@Column(name = "yearInMoney", nullable = false)
	public Double getYearInMoney() {
		return yearInMoney;
	}

	public void setYearInMoney(Double yearInMoney) {
		this.yearInMoney = yearInMoney;
	}

	// Y_Out_amount numeric (16, 2) 0 本年出库数量
	private Double yearOutAmount=0d;
	private Double yearOutMoney=0d;
	private Double priceDiff=0d;

	@Column(name = "yearOutAmount", nullable = false)
	public Double getYearOutAmount() {
		return yearOutAmount;
	}

	public void setYearOutAmount(Double yearOutAmount) {
		this.yearOutAmount = yearOutAmount;
	}

	@Column(name = "yearOutMoney", nullable = false)
	public Double getYearOutMoney() {
		return yearOutMoney;
	}

	public void setYearOutMoney(Double yearOutMoney) {
		this.yearOutMoney = yearOutMoney;
	}

	@Column(name = "priceDiff", nullable = false)
	public Double getPriceDiff() {
		return priceDiff;
	}

	public void setPriceDiff(Double priceDiff) {
		this.priceDiff = priceDiff;
	}

	// Y_Out_money numeric (16, 6) 0 本年出库金额
	// price_diff numeric (16, 6) 0 价格差异

}
