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

import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_check_detail")
public class InvCheckDetail extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -334238672617699396L;
	/**
	 * 
	 */
	//
	// id Char 32 Uuid
	private String invCheckDetailId;

	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getInvCheckDetailId() {
		return invCheckDetailId;
	}

	public void setInvCheckDetailId(String invCheckDetailId) {
		this.invCheckDetailId = invCheckDetailId;
	}

	// check_id Char 32 盘点主表id
	private InvCheck invCheck;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checkId", nullable = false)
	public InvCheck getInvCheck() {
		return invCheck;
	}

	public void setInvCheck(InvCheck invCheck) {
		this.invCheck = invCheck;
	}

	// invId 材料id
	private InventoryDict invDict;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	// BatchNo 批号
	private InvBatch invBatch;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batchId", nullable = false)
	public InvBatch getInvBatch() {
		return invBatch;
	}

	public void setInvBatch(InvBatch invBatch) {
		this.invBatch = invBatch;
	}
	/*private String batchNo;

	@Column(name = "batchNo", length = 30, nullable = false)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}*/

	// BarCode 条形码
	private String barCode;

	@Column(name = "barCode", length = 20, nullable = true)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	// posId 货位id
	private StorePosition position;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posId", nullable = true)
	public StorePosition getPosition() {
		return position;
	}

	public void setPosition(StorePosition position) {
		this.position = position;
	}

	// Price 单价
	private Double price = 0.0;

	@Column(name = "price", nullable = false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	// AcctAmount 账面数量
	private Double acctAmount = 0d;

	@Column(name = "acctAmount", nullable = false)
	public Double getAcctAmount() {
		return acctAmount;
	}

	public void setAcctAmount(Double acctAmount) {
		this.acctAmount = acctAmount;
	}

	// ChkAmount 实盘数量
	private Double chkAmount = 0d;

	@Column(name = "chkAmount", nullable = false)
	public Double getChkAmount() {
		return chkAmount;
	}

	public void setChkAmount(Double chkAmount) {
		this.chkAmount = chkAmount;
	}

	// DiffAmount 盈亏数量
	private Double diffAmount = 0d;

	@Column(name = "diffAmount", nullable = false)
	public Double getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}
	// 账面金额
	@Transient
	public Double getAcctMoney() {
		return this.acctAmount * price;
	}

	// 盈亏金额
	@Transient
	public Double getDiffMoney() {
		return this.diffAmount * price;
	}

	// 盘点金额
	@Transient
	public Double getChkMoney() {
		return this.chkAmount * price;
	}

	// Note 盈亏说明
	private String note;

	@Column(name = "note", length = 100, nullable = true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "InvCheckDetail [id=" + invCheckDetailId + ", invCheck="
				+ invCheck + ", invDict=" + invDict + ", batchNo=" + this.getInvBatch().getBatchNo()
				+ ", barCode=" + barCode + ", price=" + price + ", acctAmount="
				+ acctAmount + ", chkAmount=" + chkAmount + ", diffAmount="
				+ diffAmount + ", note=" + note + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvCheckDetail other = (InvCheckDetail) obj;
		if (acctAmount == null) {
			if (other.acctAmount != null)
				return false;
		} else if (!acctAmount.equals(other.acctAmount))
			return false;
		if (barCode == null) {
			if (other.barCode != null)
				return false;
		} else if (!barCode.equals(other.barCode))
			return false;
//		if (batchNo == null) {
//			if (other.batchNo != null)
//				return false;
//		} else if (!batchNo.equals(other.batchNo))
//			return false;
		if (chkAmount == null) {
			if (other.chkAmount != null)
				return false;
		} else if (!chkAmount.equals(other.chkAmount))
			return false;
		if (diffAmount == null) {
			if (other.diffAmount != null)
				return false;
		} else if (!diffAmount.equals(other.diffAmount))
			return false;
		if (invCheck == null) {
			if (other.invCheck != null)
				return false;
		} else if (!invCheck.equals(other.invCheck))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acctAmount == null) ? 0 : acctAmount.hashCode());
		result = prime * result + ((barCode == null) ? 0 : barCode.hashCode());
//		result = prime * result + ((batchNo == null) ? 0 : batchNo.hashCode());
		result = prime * result
				+ ((chkAmount == null) ? 0 : chkAmount.hashCode());
		result = prime * result
				+ ((diffAmount == null) ? 0 : diffAmount.hashCode());
		result = prime * result
				+ ((invCheck == null) ? 0 : invCheck.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

}
