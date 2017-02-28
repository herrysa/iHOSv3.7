package com.huge.ihos.accounting.balance.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.model.BaseObject;

@Entity
@Table( name = "gl_balance" )
public class Balance extends BaseObject implements Serializable {

	private String balanceId;
	
	private Account account;
	
	private String orgCode;
	private String copyCode;
	private String kjYear;
	private String acctCode;
	private String balanceType;
	private Double beginJie = 0d;
	private Double beginDai = 0d;
	
	private Double yearBalance = 0d;
	private Double initBalance = 0d;
	private Set<BalanceAssist> balanceAssistSet;
	private Set<BalancePeriod> balancePeriodSet;
	
	private HashMap<String,Object> assistMap = new HashMap<String,Object>();
	
	
	
	@Id
	@Column(name="balanceId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getBalanceId() {
		return balanceId;
	}
	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="acctId")
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "balance" )
	public Set<BalanceAssist> getBalanceAssistSet() {
		return balanceAssistSet;
	}
	public void setBalanceAssistSet(Set<BalanceAssist> balanceAssistSet) {
		this.balanceAssistSet = balanceAssistSet;
	}
	
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "balance" )
	@OrderBy("periodMonth asc")
	public Set<BalancePeriod> getBalancePeriodSet() {
		return balancePeriodSet;
	}
	public void setBalancePeriodSet(Set<BalancePeriod> balancePeriodSet) {
		this.balancePeriodSet = balancePeriodSet;
	}
	@Column(name="orgCode")
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Column(name="copyCode")
	public String getCopyCode() {
		return copyCode;
	}
	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	@Column(name="kjYear")
	public String getKjYear() {
		return kjYear;
	}
	public void setKjYear(String kjYear) {
		this.kjYear = kjYear;
	}
	@Column(name="acctCode")
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	@Column(name="balanceType")
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	@Column(name="beginJ")
	public Double getBeginJie() {
		return beginJie;
	}
	public void setBeginJie(Double beginJie) {
		this.beginJie = beginJie;
	}
	@Column(name="beginD")
	public Double getBeginDai() {
		return beginDai;
	}
	public void setBeginDai(Double beginDai) {
		this.beginDai = beginDai;
	}
	@Column(name="yearBalance")
	public Double getYearBalance() {
		return yearBalance;
	}
	public void setYearBalance(Double yearBalance) {
		this.yearBalance = yearBalance;
	}
	
	@Column(name="initBalance")
	public Double getInitBalance() {
		return initBalance;
	}
	public void setInitBalance(Double initBalance) {
		this.initBalance = initBalance;
	}
	@Transient
	public HashMap<String, Object> getAssistMap() {
		return assistMap;
	}
	public void setAssistMap(HashMap<String, Object> assistMap) {
		this.assistMap = assistMap;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result
				+ ((acctCode == null) ? 0 : acctCode.hashCode());
		result = prime * result
				+ ((balanceId == null) ? 0 : balanceId.hashCode());
		result = prime * result
				+ ((balanceType == null) ? 0 : balanceType.hashCode());
		result = prime * result
				+ ((beginDai == null) ? 0 : beginDai.hashCode());
		result = prime * result
				+ ((beginJie == null) ? 0 : beginJie.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((initBalance == null) ? 0 : initBalance.hashCode());
		result = prime * result + ((kjYear == null) ? 0 : kjYear.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((yearBalance == null) ? 0 : yearBalance.hashCode());
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
		Balance other = (Balance) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (acctCode == null) {
			if (other.acctCode != null)
				return false;
		} else if (!acctCode.equals(other.acctCode))
			return false;
		if (balanceId == null) {
			if (other.balanceId != null)
				return false;
		} else if (!balanceId.equals(other.balanceId))
			return false;
		if (balanceType == null) {
			if (other.balanceType != null)
				return false;
		} else if (!balanceType.equals(other.balanceType))
			return false;
		if (beginDai == null) {
			if (other.beginDai != null)
				return false;
		} else if (!beginDai.equals(other.beginDai))
			return false;
		if (beginJie == null) {
			if (other.beginJie != null)
				return false;
		} else if (!beginJie.equals(other.beginJie))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (initBalance == null) {
			if (other.initBalance != null)
				return false;
		} else if (!initBalance.equals(other.initBalance))
			return false;
		if (kjYear == null) {
			if (other.kjYear != null)
				return false;
		} else if (!kjYear.equals(other.kjYear))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (yearBalance == null) {
			if (other.yearBalance != null)
				return false;
		} else if (!yearBalance.equals(other.yearBalance))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Balance [balanceId=" + balanceId + ", account=" + account
				+ ", orgCode=" + orgCode + ", copyCode=" + copyCode
				+ ", kjYear=" + kjYear + ", acctCode=" + acctCode
				+ ", balanceType=" + balanceType + ", beginJie=" + beginJie
				+ ", beginDai=" + beginDai + ", yearBalance=" + yearBalance
				+ ", initBalance=" + initBalance + ", balanceAssistSet="
				+ balanceAssistSet + ", balancePeriodSet=" + balancePeriodSet
				+ ", assistMap=" + assistMap + "]";
	}
}
