package com.huge.ihos.material.order.model;

import java.io.Serializable;
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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.model.BaseObject;
/**
 * 根据订单入库的log记录主表，记录引用的某张订单的某种材料
 * @author Gaozhengyang
 * @date 2014年8月25日
 */
@Entity
@Table(name = "mm_import_order_log")
public class ImportOrderLog extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8618682198587474359L;
	private String logId;
	private String orgCode;
	private String copyCode;
	private String yearMonth;
	private String orderNo;
	private InventoryDict invDict;
	private Set<ImportOrderLogDetail> importOrderLogDetails;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "importOrderLog", cascade = CascadeType.ALL)
	public Set<ImportOrderLogDetail> getImportOrderLogDetails() {
		return importOrderLogDetails;
	}

	public void setImportOrderLogDetails(
			Set<ImportOrderLogDetail> importOrderLogDetails) {
		this.importOrderLogDetails = importOrderLogDetails;
	}

	@Id
	@Column(name = "logId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode", length = 10, nullable = false)
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

	@Column(name = "orderNo", length = 20, nullable = false)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((yearMonth == null) ? 0 : yearMonth.hashCode());
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
		ImportOrderLog other = (ImportOrderLog) obj;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImportOrderLog [logId=" + logId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", yearMonth=" + yearMonth
				+ ", orderNo=" + orderNo + ", invDict=" + invDict + "]";
	}

}
