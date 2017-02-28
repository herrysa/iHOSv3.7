package com.huge.ihos.system.repository.paymode.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 支付条件
 * 
 * @author gaozhengyang
 * @date 2013-12-4
 */
@Entity
@Table(name = "sy_payCondition")
public class PayCondition extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8600905204206492205L;
	private String payConId;
	private String payConCode;
	private String payConName;
	private Integer endDate = 0;// 天
	private Boolean disabled = false;

	@Id
	@Column(name = "payConId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getPayConId() {
		return payConId;
	}

	public void setPayConId(String payConId) {
		this.payConId = payConId;
	}

	@Column(name = "payConCode", length = 5, nullable = false)
	public String getPayConCode() {
		return payConCode;
	}

	public void setPayConCode(String payConCode) {
		this.payConCode = payConCode;
	}

	@Column(name = "payConName", length = 20, nullable = false)
	public String getPayConName() {
		return payConName;
	}

	public void setPayConName(String payConName) {
		this.payConName = payConName;
	}

	@Column(name = "endDate", nullable = true)
	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	@Column(name = "disabled", nullable = false, columnDefinition = "bit default 0 ")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((payConCode == null) ? 0 : payConCode.hashCode());
		result = prime * result
				+ ((payConName == null) ? 0 : payConName.hashCode());
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
		PayCondition other = (PayCondition) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (payConCode == null) {
			if (other.payConCode != null)
				return false;
		} else if (!payConCode.equals(other.payConCode))
			return false;
		if (payConName == null) {
			if (other.payConName != null)
				return false;
		} else if (!payConName.equals(other.payConName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PayCondition [payConId=" + payConId + ", payConCode="
				+ payConCode + ", payConName=" + payConName + ", endDate="
				+ endDate + ", disabled=" + disabled + "]";
	}

}
