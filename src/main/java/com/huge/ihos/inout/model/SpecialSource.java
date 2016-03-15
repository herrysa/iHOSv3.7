package com.huge.ihos.inout.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.model.BaseObject;

@Entity
@Table(name = "T_SpecialSource")
public class SpecialSource extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long SpecialSourceId;

	private String checkPeriod;
	private SpecialItem itemId;
	private Department deptId;
	private BigDecimal amount;
	private String operatorId;
	private String operatorName;
	private Date processDate;
	private String remark;
	private Department deptid1;
	private Department deptid2;
	private Department deptid3;
	private Org costOrg;
	private String status;
	private Branch cbBranch;
	@Id
    @GeneratedValue
    @Column( name = "SpecialSourceId")
	public Long getSpecialSourceId() {
		return SpecialSourceId;
	}
	public void setSpecialSourceId(Long specialSourceId) {
		SpecialSourceId = specialSourceId;
	}
	
	@Column( name = "checkPeriod", nullable = true, length = 6 )
	public String getCheckPeriod() {
		return checkPeriod;
	}
	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "itemId", nullable = true )
	public SpecialItem getItemId() {
		return itemId;
	}
	public void setItemId(SpecialItem itemId) {
		this.itemId = itemId;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "deptId", nullable = true )
	public Department getDeptId() {
		return deptId;
	}
	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}
	
	@Column( name = "amount", nullable = true)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column( name = "operatorId", nullable = true, length = 10)
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	@Column( name = "operatorName", nullable = false, length = 10)
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	@Column( name = "processDate")
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	
	@Column( name = "remark", nullable = false, length = 100)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "deptId1")
	public Department getDeptid1() {
		return deptid1;
	}
	public void setDeptid1(Department deptid1) {
		this.deptid1 = deptid1;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "deptId2" )
	public Department getDeptid2() {
		return deptid2;
	}
	public void setDeptid2(Department deptid2) {
		this.deptid2 = deptid2;
	}
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "deptId3" )
	public Department getDeptid3() {
		return deptid3;
	}
	public void setDeptid3(Department deptid3) {
		this.deptid3 = deptid3;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cbOrgCode",nullable=true)
	public Org getCostOrg() {
		return costOrg;
	}
	public void setCostOrg(Org costOrg) {
		this.costOrg = costOrg;
	}
	@Column(name="status",length=1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cbBranchCode",nullable=true)
	public Branch getCbBranch() {
		return cbBranch;
	}
	public void setCbBranch(Branch cbBranch) {
		this.cbBranch = cbBranch;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((SpecialSourceId == null) ? 0 : SpecialSourceId.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((checkPeriod == null) ? 0 : checkPeriod.hashCode());
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result + ((deptid1 == null) ? 0 : deptid1.hashCode());
		result = prime * result + ((deptid2 == null) ? 0 : deptid2.hashCode());
		result = prime * result + ((deptid3 == null) ? 0 : deptid3.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result
				+ ((operatorId == null) ? 0 : operatorId.hashCode());
		result = prime * result
				+ ((operatorName == null) ? 0 : operatorName.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		SpecialSource other = (SpecialSource) obj;
		if (SpecialSourceId == null) {
			if (other.SpecialSourceId != null)
				return false;
		} else if (!SpecialSourceId.equals(other.SpecialSourceId))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (checkPeriod == null) {
			if (other.checkPeriod != null)
				return false;
		} else if (!checkPeriod.equals(other.checkPeriod))
			return false;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptid1 == null) {
			if (other.deptid1 != null)
				return false;
		} else if (!deptid1.equals(other.deptid1))
			return false;
		if (deptid2 == null) {
			if (other.deptid2 != null)
				return false;
		} else if (!deptid2.equals(other.deptid2))
			return false;
		if (deptid3 == null) {
			if (other.deptid3 != null)
				return false;
		} else if (!deptid3.equals(other.deptid3))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (operatorId == null) {
			if (other.operatorId != null)
				return false;
		} else if (!operatorId.equals(other.operatorId))
			return false;
		if (operatorName == null) {
			if (other.operatorName != null)
				return false;
		} else if (!operatorName.equals(other.operatorName))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SpecialSource [SpecialSourceId=" + SpecialSourceId
				+ ", checkPeriod=" + checkPeriod + ", itemId=" + itemId
				+ ", deptId=" + deptId + ", amount=" + amount + ", operatorId="
				+ operatorId + ", operatorName=" + operatorName
				+ ", processDate=" + processDate + ", remark=" + remark
				+ " deptid1=" + deptid1
				+ ", deptid2=" + deptid2 + ", deptid3=" + deptid3 + "]";
	}

}
