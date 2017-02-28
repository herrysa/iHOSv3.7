package com.huge.ihos.material.deptapp.model;

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

import com.huge.ihos.material.model.InventoryDict;
import com.huge.model.BaseObject;

/**
 * 科室申领明细表
 * 
 * @author Gaozhengyang
 * @date 2014年7月1日
 */
@Entity
@Table(name = "mm_dept_app_detail")
public class DeptAppDetail extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8212532395728366292L;

	private String deptAppDetailId;
	private DeptApp deptApp;
	private InventoryDict invDict;
	private Double appAmount = 0d;
	private Double appPrice = 0d;
	private Double appMoney = 0d;
	private Double throughAmount = 0d;// 本次通过数量
	private Double noThroughAmount = 0d;// 未通过数量
	private Double waitingAmount = 0d;// 等待通过数量
	private String remark;
	private String needNo;//根据科室申领生成的单据 记录对应的需求号

	@Id
	@Column(name = "deptAppDetailId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getDeptAppDetailId() {
		return deptAppDetailId;
	}

	public void setDeptAppDetailId(String deptAppDetailId) {
		this.deptAppDetailId = deptAppDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptAppId", nullable = false)
	public DeptApp getDeptApp() {
		return deptApp;
	}

	public void setDeptApp(DeptApp deptApp) {
		this.deptApp = deptApp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	@Column(name = "appAmount", nullable = false)
	public Double getAppAmount() {
		return appAmount;
	}

	public void setAppAmount(Double appAmount) {
		this.appAmount = appAmount;
	}

	@Column(name = "appPrice", nullable = false)
	public Double getAppPrice() {
		return appPrice;
	}

	public void setAppPrice(Double appPrice) {
		this.appPrice = appPrice;
	}

	@Column(name = "appMoney", nullable = false)
	public Double getAppMoney() {
		return appMoney;
	}

	public void setAppMoney(Double appMoney) {
		this.appMoney = appMoney;
	}

	@Column(name = "throughAmount", nullable = false)
	public Double getThroughAmount() {
		return throughAmount;
	}

	public void setThroughAmount(Double throughAmount) {
		this.throughAmount = throughAmount;
	}

	@Column(name = "noThroughAmount", nullable = false)
	public Double getNoThroughAmount() {
		return noThroughAmount;
	}

	public void setNoThroughAmount(Double noThroughAmount) {
		this.noThroughAmount = noThroughAmount;
	}

	@Column(name = "waitingAmount", nullable = false)
	public Double getWaitingAmount() {
		return waitingAmount;
	}

	public void setWaitingAmount(Double waitingAmount) {
		this.waitingAmount = waitingAmount;
	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "needNo", length = 20, nullable = true)
	public String getNeedNo() {
		return needNo;
	}

	public void setNeedNo(String needNo) {
		this.needNo = needNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appAmount == null) ? 0 : appAmount.hashCode());
		result = prime * result
				+ ((appMoney == null) ? 0 : appMoney.hashCode());
		result = prime * result
				+ ((appPrice == null) ? 0 : appPrice.hashCode());
		result = prime * result + ((deptApp == null) ? 0 : deptApp.hashCode());
		result = prime * result
				+ ((deptAppDetailId == null) ? 0 : deptAppDetailId.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result
				+ ((noThroughAmount == null) ? 0 : noThroughAmount.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((throughAmount == null) ? 0 : throughAmount.hashCode());
		result = prime * result
				+ ((waitingAmount == null) ? 0 : waitingAmount.hashCode());
		result = prime * result
				+ ((needNo == null) ? 0 : needNo.hashCode());
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
		DeptAppDetail other = (DeptAppDetail) obj;
		if (appAmount == null) {
			if (other.appAmount != null)
				return false;
		} else if (!appAmount.equals(other.appAmount))
			return false;
		if (appMoney == null) {
			if (other.appMoney != null)
				return false;
		} else if (!appMoney.equals(other.appMoney))
			return false;
		if (appPrice == null) {
			if (other.appPrice != null)
				return false;
		} else if (!appPrice.equals(other.appPrice))
			return false;
		if (deptApp == null) {
			if (other.deptApp != null)
				return false;
		} else if (!deptApp.equals(other.deptApp))
			return false;
		if (deptAppDetailId == null) {
			if (other.deptAppDetailId != null)
				return false;
		} else if (!deptAppDetailId.equals(other.deptAppDetailId))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (noThroughAmount == null) {
			if (other.noThroughAmount != null)
				return false;
		} else if (!noThroughAmount.equals(other.noThroughAmount))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (throughAmount == null) {
			if (other.throughAmount != null)
				return false;
		} else if (!throughAmount.equals(other.throughAmount))
			return false;
		if (waitingAmount == null) {
			if (other.waitingAmount != null)
				return false;
		} else if (!waitingAmount.equals(other.waitingAmount))
			return false;
		if (needNo == null) {
			if (other.needNo != null)
				return false;
		} else if (!needNo.equals(other.needNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptAppDetail [deptAppDetailId=" + deptAppDetailId
				+ ", deptApp=" + deptApp + ", invDict=" + invDict
				+ ", appAmount=" + appAmount + ", appPrice=" + appPrice
				+ ", appMoney=" + appMoney + ", throughAmount=" + throughAmount
				+ ", noThroughAmount=" + noThroughAmount + ", waitingAmount="
				+ waitingAmount + ", remark=" + remark + ", needNo="+needNo+ "]";
	}

}
