package com.huge.ihos.accounting.balance.model;

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
@Table( name = "gl_balance_period" )
public class BalancePeriod extends BaseObject implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String balancePeriodId;
	private String periodMonth;
	private Double lend;	//贷
	private Double loan;	//借
	
	private Double monthBalance;	//月余额
	private Boolean hasAccrual = false; 	//是否有发生额
	
	private Balance balance;
	
	@Id
	@Column(name="balancePeriodId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getBalancePeriodId() {
		return balancePeriodId;
	}
	public void setBalancePeriodId(String balancePeriodId) {
		this.balancePeriodId = balancePeriodId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="balanceId")
	public Balance getBalance() {
		return balance;
	}
	public void setBalance(Balance balance) {
		this.balance = balance;
	}
    
	@Column(name="periodMonth",length=6)
	public String getPeriodMonth() {
		return periodMonth;
	}
	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}
	
	@Column(name="lend")
	public Double getLend() {
		return lend;
	}
	public void setLend(Double lend) {
		this.lend = lend;
	}
	@Column(name="loan")
	public Double getLoan() {
		return loan;
	}
	public void setLoan(Double loan) {
		this.loan = loan;
	}
	
	@Column(name="monthBalance")
	public Double getMonthBalance() {
		return monthBalance;
	}
	public void setMonthBalance(Double monthBalance) {
		this.monthBalance = monthBalance;
	}
	@Column(name="hasAccrual")
	public Boolean getHasAccrual() {
		return hasAccrual;
	}
	public void setHasAccrual(Boolean hasAccrual) {
		this.hasAccrual = hasAccrual;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result
				+ ((balancePeriodId == null) ? 0 : balancePeriodId.hashCode());
		result = prime * result
				+ ((hasAccrual == null) ? 0 : hasAccrual.hashCode());
		result = prime * result + ((lend == null) ? 0 : lend.hashCode());
		result = prime * result + ((loan == null) ? 0 : loan.hashCode());
		result = prime * result
				+ ((monthBalance == null) ? 0 : monthBalance.hashCode());
		result = prime * result
				+ ((periodMonth == null) ? 0 : periodMonth.hashCode());
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
		BalancePeriod other = (BalancePeriod) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (balancePeriodId == null) {
			if (other.balancePeriodId != null)
				return false;
		} else if (!balancePeriodId.equals(other.balancePeriodId))
			return false;
		if (hasAccrual == null) {
			if (other.hasAccrual != null)
				return false;
		} else if (!hasAccrual.equals(other.hasAccrual))
			return false;
		if (lend == null) {
			if (other.lend != null)
				return false;
		} else if (!lend.equals(other.lend))
			return false;
		if (loan == null) {
			if (other.loan != null)
				return false;
		} else if (!loan.equals(other.loan))
			return false;
		if (monthBalance == null) {
			if (other.monthBalance != null)
				return false;
		} else if (!monthBalance.equals(other.monthBalance))
			return false;
		if (periodMonth == null) {
			if (other.periodMonth != null)
				return false;
		} else if (!periodMonth.equals(other.periodMonth))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BalancePeriod [balancePeriodId=" + balancePeriodId
				+ ", periodMonth=" + periodMonth + ", lend=" + lend + ", loan="
				+ loan + ", monthBalance=" + monthBalance + ", hasAccrual="
				+ hasAccrual + ", balance=" + balance + "]";
	}
}
