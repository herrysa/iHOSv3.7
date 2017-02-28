package com.huge.ihos.bm.loanBill.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "bm_loanbill" )
public class BmLoanbill{
	
	private String billId;
	private String billCode;
	private Date makeDate;
	private String personId;
	private String personName;
	private String expendDeptId;
	private String expendDeptName;
	private String centralDeptId;
	private String centralDeptName;
	private String paymentNum;
	private String loanVoucherNum;
	private String bxVoucherNum;
	private Integer appendix;
	private Double money;
	private Integer state;
	private String checker;
	private String checkerName;
	private String checker2;
	private String checker2Name;
	private String cashier;
	
	
	@Id
	@Column(name = "billId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	
	@Column
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	@Column
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	
	@Column
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	@Column
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	@Column
	public String getExpendDeptId() {
		return expendDeptId;
	}
	public void setExpendDeptId(String expendDeptId) {
		this.expendDeptId = expendDeptId;
	}
	
	@Column
	public String getCentralDeptId() {
		return centralDeptId;
	}
	public void setCentralDeptId(String centralDeptId) {
		this.centralDeptId = centralDeptId;
	}
	
	@Column
	public String getExpendDeptName() {
		return expendDeptName;
	}
	public void setExpendDeptName(String expendDeptName) {
		this.expendDeptName = expendDeptName;
	}
	
	@Column
	public String getCentralDeptName() {
		return centralDeptName;
	}
	public void setCentralDeptName(String centralDeptName) {
		this.centralDeptName = centralDeptName;
	}
	
	@Column
	public String getPaymentNum() {
		return paymentNum;
	}
	public void setPaymentNum(String paymentNum) {
		this.paymentNum = paymentNum;
	}
	
	@Column
	public String getLoanVoucherNum() {
		return loanVoucherNum;
	}
	public void setLoanVoucherNum(String loanVoucherNum) {
		this.loanVoucherNum = loanVoucherNum;
	}
	
	@Column
	public String getBxVoucherNum() {
		return bxVoucherNum;
	}
	public void setBxVoucherNum(String bxVoucherNum) {
		this.bxVoucherNum = bxVoucherNum;
	}
	
	@Column
	public Integer getAppendix() {
		return appendix;
	}
	public void setAppendix(Integer appendix) {
		this.appendix = appendix;
	}
	
	@Column
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	@Column
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@Column
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@Column
	public String getCheckerName() {
		return checkerName;
	}
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
	
	@Column
	public String getChecker2() {
		return checker2;
	}
	public void setChecker2(String checker2) {
		this.checker2 = checker2;
	}
	
	@Column
	public String getChecker2Name() {
		return checker2Name;
	}
	public void setChecker2Name(String checker2Name) {
		this.checker2Name = checker2Name;
	}
	
	@Column
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
	
	
	
	

}
