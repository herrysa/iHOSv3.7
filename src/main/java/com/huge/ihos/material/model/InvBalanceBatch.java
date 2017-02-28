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
@Table(name = "mm_balance_batch")
public class InvBalanceBatch extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336095660665422234L;
	// copyCode Varchar 30 账套编码 主键
	// yearMonth char 6 所属期间 主键
	// StoreId Varchar 30 仓库id 主键
	// posId Varchar 30 ** 货位id 主键
	// InvId Varchar 30 材料id 主键
	private String id;
	// B_Ybegin_money numeric (16, 6) 0
	private Double batchYearBeginMoney=0d;
	// B_Ybegin_amount numeric (16, 2) 0
	private Double batchYearBeginAmount=0d;
	// B_Out_money numeric (16, 6) 0
	private Double batchOutMoney=0d;
	// B_Out_amount numeric (16, 2) 0
	private Double batchOutAmount=0d;
	// B_In_money numeric (16, 6) 0
	private Double batchInMoney=0d;
	// B_In_amount numeric (16, 2) 0
	private Double batchInAmount=0d;
	// B_begin_money numeric (16, 6) 0
	private Double batchBeginMoney=0d;
	// B_begin_amount numeric (16, 2) 0 期初数量_批次
	private Double batchBeginAmount=0d;
	// Price 单价 主键
	private Double price=0d;
	// BatchSn 序号 主键
	// BatchNo 批号 主键
	// BarCode 条码 主键
	private String batchSn;
	//private String batchNo;
	private InvBatch invBatch;
	
	private String barCode;
	// InvId Varchar 30 材料id 主键
	// InvId varchar 30 材料id
	private InventoryDict invDict;
	// posId Varchar 30 ** 货位id 主键
	
	// InPosid Varchar 30 √ 入库货位id
	private StorePosition storePosition;
	private Store store;
	// copyCode 账套编码
	private String copyCode;
	// OrgCode Varchar 30 单位编码 主键
	// OrgCode 单位编码
	private String orgCode;
	// yearMonth char 6 会计期间，以填制单据日期所在期间自动赋值
	private String yearMonth;
	// B_YIn_amount numeric (16, 2) 0
	// B_YIn_money numeric (16, 6) 0
	// B_YOut_amount numeric (16, 2) 0
	// B_YOut_money numeric (16, 6) 0
	private Double batchYearInAmount=0d;
	private Double batchYearInMoney=0d;
	private Double batchYearOutAmount=0d;
	private Double batchYearOutMoney=0d;

	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Column(name = "orgCode", length = 20, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	@Column(name = "copyCode", length = 20, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}


	@Column(name = "yearMonth", length = 6, nullable = false)
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}


	@Column(name = "batchSn", length = 50, nullable = false)
	public String getBatchSn() {
		return batchSn;
	}

	public void setBatchSn(String batchSn) {
		this.batchSn = batchSn;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batchId", nullable = false)
	public InvBatch getInvBatch() {
		return invBatch;
	}

	public void setInvBatch(InvBatch invBatch) {
		this.invBatch = invBatch;
	}
//	@Column(name = "batchNo", length = 50, nullable = false)
//	public String getBatchNo() {
//		return batchNo;
//	}
//
//	public void setBatchNo(String batchNo) {
//		this.batchNo = batchNo;
//	}

	@Column(name = "barCode", length = 50, nullable = false)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}


	@Column(name = "price", nullable = false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	@Column(name = "B_begin_amount", nullable = false)
	public Double getBatchBeginAmount() {
		return batchBeginAmount;
	}

	public void setBatchBeginAmount(Double batchBeginAmount) {
		this.batchBeginAmount = batchBeginAmount;
	}


	@Column(name = "B_begin_money", nullable = false)
	public Double getBatchBeginMoney() {
		return batchBeginMoney;
	}

	public void setBatchBeginMoney(Double batchBeginMoney) {
		this.batchBeginMoney = batchBeginMoney;
	}


	@Column(name = "B_in_amount", nullable = false)
	public Double getBatchInAmount() {
		return batchInAmount;
	}

	public void setBatchInAmount(Double batchInAmount) {
		this.batchInAmount = batchInAmount;
	}


	@Column(name = "B_in_money", nullable = false)
	public Double getBatchInMoney() {
		return batchInMoney;
	}

	public void setBatchInMoney(Double batchInMoney) {
		this.batchInMoney = batchInMoney;
	}


	@Column(name = "B_out_amount", nullable = false)
	public Double getBatchOutAmount() {
		return batchOutAmount;
	}

	public void setBatchOutAmount(Double batchOutAmount) {
		this.batchOutAmount = batchOutAmount;
	}


	@Column(name = "B_out_money", nullable = false)
	public Double getBatchOutMoney() {
		return batchOutMoney;
	}

	public void setBatchOutMoney(Double batchOutMoney) {
		this.batchOutMoney = batchOutMoney;
	}


	@Column(name = "B_Ybegin_amount", nullable = false)
	public Double getBatchYearBeginAmount() {
		return batchYearBeginAmount;
	}

	public void setBatchYearBeginAmount(Double batchYearBeginAmount) {
		this.batchYearBeginAmount = batchYearBeginAmount;
	}


	@Column(name = "B_Ybegin_money", nullable = false)
	public Double getBatchYearBeginMoney() {
		return batchYearBeginMoney;
	}

	public void setBatchYearBeginMoney(Double batchYearBeginMoney) {
		this.batchYearBeginMoney = batchYearBeginMoney;
	}
	

	@Column(name = "B_Yin_amount", nullable = false)
	public Double getBatchYearInAmount() {
		return batchYearInAmount;
	}

	public void setBatchYearInAmount(Double batchYearInAmount) {
		this.batchYearInAmount = batchYearInAmount;
	}

	@Column(name = "B_Yin_money", nullable = false)
	public Double getBatchYearInMoney() {
		return batchYearInMoney;
	}

	public void setBatchYearInMoney(Double batchYearInMoney) {
		this.batchYearInMoney = batchYearInMoney;
	}

	@Column(name = "B_Yout_amount", nullable = false)
	public Double getBatchYearOutAmount() {
		return batchYearOutAmount;
	}

	public void setBatchYearOutAmount(Double batchYearOutAmount) {
		this.batchYearOutAmount = batchYearOutAmount;
	}

	@Column(name = "B_Yout_money", nullable = false)
	public Double getBatchYearOutMoney() {
		return batchYearOutMoney;
	}

	public void setBatchYearOutMoney(Double batchYearOutMoney) {
		this.batchYearOutMoney = batchYearOutMoney;
	}
	
	//实时数量[账面数量]
	private Double curAmount = 0d;
	//实时金额[账面金额]
	private Double curMoney = 0.0;
	
	@Column(name = "cur_amount", nullable = false)
	public Double getCurAmount() {
		return curAmount;
	}

	public void setCurAmount(Double curAmount) {
		this.curAmount = curAmount;
	}
	
	@Column(name = "cur_money", nullable = false)
	public Double getCurMoney() {
		return curMoney;
	}

	public void setCurMoney(Double curMoney) {
		this.curMoney = curMoney;
	}

//	//账面数量
//	@Transient
//	public Integer getAccountAmount(){
//		return this.batchBeginAmount+this.batchInAmount-this.batchOutAmount;
//	}
//	//账面金额
//	@Transient
//	public Double getCurrentMoney(){
//		return this.getAccountAmount()*this.price;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barCode == null) ? 0 : barCode.hashCode());
		result = prime * result + ((batchBeginAmount == null) ? 0 : batchBeginAmount.hashCode());
		result = prime * result + ((batchBeginMoney == null) ? 0 : batchBeginMoney.hashCode());
		result = prime * result + ((batchInAmount == null) ? 0 : batchInAmount.hashCode());
		result = prime * result + ((batchInMoney == null) ? 0 : batchInMoney.hashCode());
//		result = prime * result + ((batchNo == null) ? 0 : batchNo.hashCode());
		result = prime * result + ((batchOutAmount == null) ? 0 : batchOutAmount.hashCode());
		result = prime * result + ((batchOutMoney == null) ? 0 : batchOutMoney.hashCode());
		result = prime * result + ((batchSn == null) ? 0 : batchSn.hashCode());
		result = prime * result + ((batchYearBeginAmount == null) ? 0 : batchYearBeginAmount.hashCode());
		result = prime * result + ((batchYearBeginMoney == null) ? 0 : batchYearBeginMoney.hashCode());
		result = prime * result + ((batchYearInAmount == null) ? 0 : batchYearInAmount.hashCode());
		result = prime * result + ((batchYearInMoney == null) ? 0 : batchYearInMoney.hashCode());
		result = prime * result + ((batchYearOutAmount == null) ? 0 : batchYearOutAmount.hashCode());
		result = prime * result + ((batchYearOutMoney == null) ? 0 : batchYearOutMoney.hashCode());
		result = prime * result + ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((storePosition == null) ? 0 : storePosition.hashCode());
		result = prime * result + ((yearMonth == null) ? 0 : yearMonth.hashCode());
		result = prime * result + ((curMoney == null) ? 0 : curMoney.hashCode());
		result = prime * result + ((curAmount == null) ? 0 : curAmount.hashCode());
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
		InvBalanceBatch other = (InvBalanceBatch) obj;
		if (barCode == null) {
			if (other.barCode != null)
				return false;
		} else if (!barCode.equals(other.barCode))
			return false;
		if (batchBeginAmount == null) {
			if (other.batchBeginAmount != null)
				return false;
		} else if (!batchBeginAmount.equals(other.batchBeginAmount))
			return false;
		if (batchBeginMoney == null) {
			if (other.batchBeginMoney != null)
				return false;
		} else if (!batchBeginMoney.equals(other.batchBeginMoney))
			return false;
		if (batchInAmount == null) {
			if (other.batchInAmount != null)
				return false;
		} else if (!batchInAmount.equals(other.batchInAmount))
			return false;
		if (batchInMoney == null) {
			if (other.batchInMoney != null)
				return false;
		} else if (!batchInMoney.equals(other.batchInMoney))
			return false;
//		if (batchNo == null) {
//			if (other.batchNo != null)
//				return false;
//		} else if (!batchNo.equals(other.batchNo))
//			return false;
		if (batchOutAmount == null) {
			if (other.batchOutAmount != null)
				return false;
		} else if (!batchOutAmount.equals(other.batchOutAmount))
			return false;
		if (batchOutMoney == null) {
			if (other.batchOutMoney != null)
				return false;
		} else if (!batchOutMoney.equals(other.batchOutMoney))
			return false;
		if (batchSn == null) {
			if (other.batchSn != null)
				return false;
		} else if (!batchSn.equals(other.batchSn))
			return false;
		if (batchYearBeginAmount == null) {
			if (other.batchYearBeginAmount != null)
				return false;
		} else if (!batchYearBeginAmount.equals(other.batchYearBeginAmount))
			return false;
		if (batchYearBeginMoney == null) {
			if (other.batchYearBeginMoney != null)
				return false;
		} else if (!batchYearBeginMoney.equals(other.batchYearBeginMoney))
			return false;
		if (batchYearInAmount == null) {
			if (other.batchYearInAmount != null)
				return false;
		} else if (!batchYearInAmount.equals(other.batchYearInAmount))
			return false;
		if (batchYearInMoney == null) {
			if (other.batchYearInMoney != null)
				return false;
		} else if (!batchYearInMoney.equals(other.batchYearInMoney))
			return false;
		if (batchYearOutAmount == null) {
			if (other.batchYearOutAmount != null)
				return false;
		} else if (!batchYearOutAmount.equals(other.batchYearOutAmount))
			return false;
		if (batchYearOutMoney == null) {
			if (other.batchYearOutMoney != null)
				return false;
		} else if (!batchYearOutMoney.equals(other.batchYearOutMoney))
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
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
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
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		if (curMoney == null) {
			if (other.curMoney != null)
				return false;
		} else if (!curMoney.equals(other.curMoney))
			return false;
		if (curAmount == null) {
			if (other.curAmount != null)
				return false;
		} else if (!curAmount.equals(other.curAmount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvBalanceBatch [id=" + id + ", orgCode=" + orgCode + ", copyCode=" + copyCode + ", yearMonth=" + yearMonth + ", store=" + store + ", storePosition=" + storePosition + ", invDict="
				+ invDict + ", batchSn=" + batchSn + ", batchNo=" + this.getInvBatch().getBatchNo() + ", barCode=" + barCode + ", price=" + price + ", batchBeginAmount=" + batchBeginAmount + ", batchBeginMoney="
				+ batchBeginMoney + ", batchInAmount=" + batchInAmount + ", batchInMoney=" + batchInMoney + ", batchOutAmount=" + batchOutAmount + ", batchOutMoney=" + batchOutMoney
				+ ", batchYearBeginAmount=" + batchYearBeginAmount + ", batchYearBeginMoney=" + batchYearBeginMoney + ", batchYearInAmount=" + batchYearInAmount + ", batchYearInMoney="
				+ ",curMoney="+curMoney+",curAmount="+curAmount
				+ batchYearInMoney + ", batchYearOutAmount=" + batchYearOutAmount + ", batchYearOutMoney=" + batchYearOutMoney + "]";
	}

}
