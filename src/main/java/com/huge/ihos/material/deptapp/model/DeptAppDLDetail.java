package com.huge.ihos.material.deptapp.model;

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

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.material.model.InvBatch;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 科室申领发放记录子表 某年某月某日发放某批次的材料多少个
 * 
 * @author Gaozhengyang
 * @date 2014年8月18日
 */
@Entity
@Table(name = "mm_dept_app_dis_log_detail")
public class DeptAppDLDetail extends BaseObject implements Serializable {

	private static final long serialVersionUID = 376666704891032256L;
	private String logDetailId;
	private DeptAppDisLog deptAppDL;
	private Date disTime;
	private Person disPerson;
	private InvBatch invBatch;
	private Double disAmount;
	private Double disPrice;
	private String outNo;// 对应生成的出库单号

	@Id
	@Column(name = "logDetailId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getLogDetailId() {
		return logDetailId;
	}

	public void setLogDetailId(String logDetailId) {
		this.logDetailId = logDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logId", nullable = false)
	public DeptAppDisLog getDeptAppDL() {
		return deptAppDL;
	}

	public void setDeptAppDL(DeptAppDisLog deptAppDL) {
		this.deptAppDL = deptAppDL;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disPerson", nullable = true)
	public Person getDisPerson() {
		return disPerson;
	}

	public void setDisPerson(Person disPerson) {
		this.disPerson = disPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "disTime", nullable = true)
	public Date getDisTime() {
		return disTime;
	}

	public void setDisTime(Date disTime) {
		this.disTime = disTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batchId", nullable = false)
	public InvBatch getInvBatch() {
		return invBatch;
	}

	public void setInvBatch(InvBatch invBatch) {
		this.invBatch = invBatch;
	}

	@Column(name = "disAmount", nullable = false)
	public Double getDisAmount() {
		return disAmount;
	}

	public void setDisAmount(Double disAmount) {
		this.disAmount = disAmount;
	}

	@Column(name = "disPrice", nullable = false)
	public Double getDisPrice() {
		return disPrice;
	}

	public void setDisPrice(Double disPrice) {
		this.disPrice = disPrice;
	}

	@Column(name = "outNo", length = 20, nullable = true)
	public String getOutNo() {
		return outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deptAppDL == null) ? 0 : deptAppDL.hashCode());
		result = prime * result
				+ ((disAmount == null) ? 0 : disAmount.hashCode());
		result = prime * result
				+ ((disPrice == null) ? 0 : disPrice.hashCode());
		result = prime * result
				+ ((disPerson == null) ? 0 : disPerson.hashCode());
		result = prime * result + ((disTime == null) ? 0 : disTime.hashCode());
		result = prime * result
				+ ((invBatch == null) ? 0 : invBatch.hashCode());
		result = prime * result
				+ ((logDetailId == null) ? 0 : logDetailId.hashCode());
		result = prime * result + ((outNo == null) ? 0 : outNo.hashCode());
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
		DeptAppDLDetail other = (DeptAppDLDetail) obj;
		if (deptAppDL == null) {
			if (other.deptAppDL != null)
				return false;
		} else if (!deptAppDL.equals(other.deptAppDL))
			return false;
		if (disAmount == null) {
			if (other.disAmount != null)
				return false;
		} else if (!disAmount.equals(other.disAmount))
			return false;
		if (disPrice == null) {
			if (other.disPrice != null)
				return false;
		} else if (!disPrice.equals(other.disPrice))
			return false;
		if (disPerson == null) {
			if (other.disPerson != null)
				return false;
		} else if (!disPerson.equals(other.disPerson))
			return false;
		if (disTime == null) {
			if (other.disTime != null)
				return false;
		} else if (!disTime.equals(other.disTime))
			return false;
		if (invBatch == null) {
			if (other.invBatch != null)
				return false;
		} else if (!invBatch.equals(other.invBatch))
			return false;
		if (logDetailId == null) {
			if (other.logDetailId != null)
				return false;
		} else if (!logDetailId.equals(other.logDetailId))
			return false;
		if (outNo == null) {
			if (other.outNo != null)
				return false;
		} else if (!outNo.equals(other.outNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptAppDLDetail [logDetailId=" + logDetailId + ", deptAppDL="
				+ deptAppDL + ", disPerson=" + disPerson + ", disTime="
				+ disTime + ", invBatch=" + invBatch + ", disAmount="
				+ disAmount + ", disPrice=" + disPrice + ", outNo=" + outNo
				+ "]";
	}

}
