package com.huge.ihos.material.deptplan.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_dept_mr_summary_detail")
public class DeptMRSummaryDetail extends BaseObject implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4336403122239953682L;
	private String mrDetailId;
	private DeptMRSummary deptMRSummary;
	private InventoryDict invDict;
	private Double amount = 0d;//计划数量
	private Double price = 0d;//单价 
	private String remark;
	private Date makeDate;//需求日期

	@Id
	@Column(name = "mrDetailId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getMrDetailId() {
		return mrDetailId;
	}

	public void setMrDetailId(String mrDetailId) {
		this.mrDetailId = mrDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mrId", nullable = false)
	public DeptMRSummary getDeptMRSummary() {
		return deptMRSummary;
	}

	public void setDeptMRSummary(DeptMRSummary deptMRSummary) {
		this.deptMRSummary = deptMRSummary;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	@Column(name = "amount", nullable = false)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "price", nullable = false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = false)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	
	@Transient
	public Double getMoney() {
		return this.amount*this.price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((deptMRSummary == null) ? 0 : deptMRSummary.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
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
		DeptMRSummaryDetail other = (DeptMRSummaryDetail) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (deptMRSummary == null) {
			if (other.deptMRSummary != null)
				return false;
		} else if (!deptMRSummary.equals(other.deptMRSummary))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptMRSummaryDetail [mrDetailId=" + mrDetailId
				+ ", deptMRSummary=" + deptMRSummary + ", invDict=" + invDict + ", makeDate=" + makeDate
				+ ", amount=" + amount + ", price=" + price + ", remark=" + remark;
	}
}
