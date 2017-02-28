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
 * 支付方式
 * 
 * @author gaozhengyang
 * @date 2013-12-4
 */
@Entity
@Table(name = "sy_payMode")
public class PayMode extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -236597770427292060L;
	private String payModeId;
	private String payModeCode;
	private String payModeName;
	// 是否票据管理
	private Boolean isCheck = false;
	private Boolean disabled = false;

	@Id
	@Column(name = "payModeId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getPayModeId() {
		return payModeId;
	}

	public void setPayModeId(String payModeId) {
		this.payModeId = payModeId;
	}

	@Column(name = "payModeCode", length = 2, nullable = false)
	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	@Column(name = "payModeName", length = 20, nullable = false)
	public String getPayModeName() {
		return payModeName;
	}

	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}

	@Column(name = "isCheck", nullable = false, columnDefinition = "bit default 0 ")
	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
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
		result = prime * result + ((isCheck == null) ? 0 : isCheck.hashCode());
		result = prime * result
				+ ((payModeCode == null) ? 0 : payModeCode.hashCode());
		result = prime * result
				+ ((payModeName == null) ? 0 : payModeName.hashCode());
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
		PayMode other = (PayMode) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (isCheck == null) {
			if (other.isCheck != null)
				return false;
		} else if (!isCheck.equals(other.isCheck))
			return false;
		if (payModeCode == null) {
			if (other.payModeCode != null)
				return false;
		} else if (!payModeCode.equals(other.payModeCode))
			return false;
		if (payModeName == null) {
			if (other.payModeName != null)
				return false;
		} else if (!payModeName.equals(other.payModeName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PayMode [payModeId=" + payModeId + ", payModeCode="
				+ payModeCode + ", payModeName=" + payModeName + ", isCheck="
				+ isCheck + ", disabled=" + disabled + "]";
	}

}
