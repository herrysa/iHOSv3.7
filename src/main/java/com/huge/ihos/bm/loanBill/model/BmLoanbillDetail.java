package com.huge.ihos.bm.loanBill.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "bm_loanbill_detail" )
public class BmLoanbillDetail {

	private String billdetailId;
	private String billId;
	private String purpose;
	private Integer sn;
	private String bmSubjId;
	private Double total;
	private Double usable;
	private Double apply;
	private Double balance;
	private String payModel;
	private String billNum;
	
	@Id
	@Column(name = "normId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getBilldetailId() {
		return billdetailId;
	}
	public void setBilldetailId(String billdetailId) {
		this.billdetailId = billdetailId;
	}
	
	@Column
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	
	@Column
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	@Column
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	
	@Column
	public String getBmSubjId() {
		return bmSubjId;
	}
	public void setBmSubjId(String bmSubjId) {
		this.bmSubjId = bmSubjId;
	}
	
	@Column
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	@Column
	public Double getUsable() {
		return usable;
	}
	public void setUsable(Double usable) {
		this.usable = usable;
	}
	
	@Column
	public Double getApply() {
		return apply;
	}
	public void setApply(Double apply) {
		this.apply = apply;
	}
	
	@Column
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Column
	public String getPayModel() {
		return payModel;
	}
	public void setPayModel(String payModel) {
		this.payModel = payModel;
	}
	
	@Column
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	
}
