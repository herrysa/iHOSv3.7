package com.huge.ihos.accounting.balance.model;

import java.io.Serializable;
import java.util.HashMap;

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

import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.model.BaseObject;

@Entity
@Table( name = "gl_balance_assist" )
public class BalanceAssist extends BaseObject implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String balanceAssistId;
	private Integer num;
	private Balance balance;
	private Double beginDai;
	private Double beginJie;
	private Double yearBalance;
	private Double initBalance;
	
	private String assistValue;
	private AssistType assistType;
	private String assistName;
	private HashMap<String,Object> assist = new HashMap<String,Object>();
	
	
	@Transient
	public HashMap<String, Object> getAssist() {
		return assist;
	}

	public void setAssist(HashMap<String, Object> assist) {
		this.assist = assist;
	}
	
	@Id
	@Column(length = 32,name="balanceAssistId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getBalanceAssistId() {
		return balanceAssistId;
	}

	public void setBalanceAssistId(String balanceAssistId) {
		this.balanceAssistId = balanceAssistId;
	}

	@Column(name="num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assistCode")
	public AssistType getAssistType() {
		return assistType;
	}

	public void setAssistType(AssistType assistType) {
		this.assistType = assistType;
	}

	@Column(name="assistValue")
	public String getAssistValue() {
		return assistValue;
	}

	public void setAssistValue(String assistValue) {
		this.assistValue = assistValue;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="balanceId")
	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
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
	
	@Column(name="assistName")
	public String getAssistName() {
		return assistName;
	}

	public void setAssistName(String assistName) {
		this.assistName = assistName;
	}

	@Override
	public String toString() {
		return "BalanceAssist [balanceAssistId=" + balanceAssistId + ",num = "+num
		+ ", assistType=" + assistType + ", assistValue=" + assistValue
		+ ", balance=" + balance + ", beginJie=" + beginJie
		+ ", beginDai=" + beginDai + ", yearBalance=" + yearBalance
		+ ", initBalance=" + initBalance+"]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BalanceAssist other = (BalanceAssist) obj;
		if (balanceAssistId == null) {
			if (other.balanceAssistId != null)
				return false;
		} else if (!balanceAssistId.equals(other.balanceAssistId))
			return false;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;
		if (assistType == null) {
			if (other.assistType != null)
				return false;
		} else if (!assistType.equals(other.assistType))
			return false;
		if (assistValue == null) {
			if (other.assistValue != null)
				return false;
		} else if (!assistValue.equals(other.assistValue))
			return false;
		
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (beginJie == null) {
			if (other.beginJie != null)
				return false;
		} else if (!beginJie.equals(other.beginJie))
			return false;
		if (beginDai == null) {
			if (other.beginDai != null)
				return false;
		} else if (!beginDai.equals(other.beginDai))
			return false;
		if (yearBalance == null) {
			if (other.yearBalance != null)
				return false;
		} else if (!yearBalance.equals(other.yearBalance))
			return false;
		if (initBalance == null) {
			if (other.initBalance != null)
				return false;
		} else if (!initBalance.equals(other.initBalance))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balanceAssistId == null) ? 0 : balanceAssistId.hashCode());
		result = prime * result + ((num == null) ? 0 : num.hashCode());
		result = prime * result + ((assistType == null) ? 0 : assistType.hashCode());
		result = prime * result + ((assistValue == null) ? 0 : assistValue.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((beginJie == null) ? 0 : beginJie.hashCode());
		result = prime * result + ((beginDai == null) ? 0 : beginDai.hashCode());
		result = prime * result + ((yearBalance == null) ? 0 : yearBalance.hashCode());
		result = prime * result + ((initBalance == null) ? 0 : initBalance.hashCode());
		return result;
	}

	

}
