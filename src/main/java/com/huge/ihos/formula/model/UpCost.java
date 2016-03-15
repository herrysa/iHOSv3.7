package com.huge.ihos.formula.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonUpCost;

@Entity
@Table(name = "t_UpCost")
public class UpCost implements Cloneable {

	private Long upcostId;
	private String checkperiod;
	private Department deptId;
	private String deptName;
	private String deptInternalCode;
	private UpItem upitemid;
	private String upitemName;
	private CostItem costitemid;
	private String costItemName;
	private PersonUpCost personId;
	private Department ownerdept;
	private String owerdeptName;
	private Person operatorId;
	private String personName;
	private String personCode;
	private String operatorName = "";
	private Date operateDate;
	private Department operatordeptid;
	private BigDecimal amount = new BigDecimal(0);
	private Person auditorId;
	private String auditorName;
	private Date auditDate;
	private Department auditorDeptid;
	private Integer state;
	private String remark = "";
	private String upItemType;
	private Org costOrg;
	private Branch branch;
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getUpcostId() {
		return upcostId;
	}

	public void setUpcostId(Long upcostId) {
		this.upcostId = upcostId;
	}

	@Column(name = "checkperiod")
	public String getCheckperiod() {
		return checkperiod;
	}

	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId")
	public Department getDeptId() {
		return deptId;
	}

	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId")
	public Person getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Person operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "operatorName")
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Column(name = "operatedate")
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditorId")
	public Person getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Person auditorId) {
		this.auditorId = auditorId;
	}

	@Column(name = "auditorName")
	public String getAuditorName() {
		if (auditorName != null) {
			auditorName = auditorId.getName();

		}
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	@Column(name = "auditDate")
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditorDeptid")
	public Department getAuditorDeptid() {
		return auditorDeptid;
	}

	public void setAuditorDeptid(Department auditorDeptid) {
		this.auditorDeptid = auditorDeptid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "upitemid")
	public UpItem getUpitemid() {
		return upitemid;
	}

	public void setUpitemid(UpItem upitemid) {
		this.upitemid = upitemid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "costitemid")
	public CostItem getCostitemid() {
		return costitemid;
	}

	public void setCostitemid(CostItem costitemid) {
		this.costitemid = costitemid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId")
	public PersonUpCost getPersonId() {
		return personId;
	}

	public void setPersonId(PersonUpCost personId) {
		this.personId = personId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownerdept")
	public Department getOwnerdept() {
		return ownerdept;
	}

	public void setOwnerdept(Department ownerdept) {
		this.ownerdept = ownerdept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatordeptid")
	public Department getOperatordeptid() {
		return operatordeptid;
	}

	public void setOperatordeptid(Department operatordeptid) {
		this.operatordeptid = operatordeptid;
	}

	@Column(name = "amount")
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getUpitemName() {
		if (upitemid != null) {
			upitemName = upitemid.getItemName();
		}
		return upitemName;
	}

	public void setUpitemName(String upitemName) {
		this.upitemName = upitemName;
	}

	@Transient
	public String getDeptName() {
		if (deptId != null) {
			deptName = deptId.getName();
		}
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public String getPersonName() {
		if (personId != null) {
			personName = personId.getName();
		}
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Transient
	public String getOwerdeptName() {
		if (ownerdept != null) {
			owerdeptName = ownerdept.getName();
		}
		return owerdeptName;
	}

	public void setOwerdeptName(String owerdeptName) {
		this.owerdeptName = owerdeptName;
	}

	@Transient
	public String getUpItemType() {
		if (this.getUpitemid() != null) {
			if (this.getUpitemid().getItemClass().equals("本科室")) {
				upItemType = "1";
			} else {
				upItemType = "0";
			}
		}
		return upItemType;
	}

	public void setUpItemType(String upItemType) {
		this.upItemType = upItemType;
	}

	@Transient
	public String getPersonCode() {
		if (personId != null) {
			personCode = personId.getPersonCode();
		}
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@Transient
	public String getCostItemName() {
		if (costitemid != null) {
			costItemName = costitemid.getCostItemName();
		} else {
			costItemName = "";
		}
		return costItemName;
	}

	public void setCostItemName(String costItemName) {
		this.costItemName = costItemName;
	}

	@Transient
	public String getDeptInternalCode() {
		if (deptId != null) {
			deptInternalCode = deptId.getInternalCode();
		} else {
			deptInternalCode = "";
		}
		return deptInternalCode;
	}

	public void setDeptInternalCode(String deptInternalCode) {
		this.deptInternalCode = deptInternalCode;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orgCode",nullable=true)
	public Org getCostOrg() {
		return costOrg;
	}

	public void setCostOrg(Org costOrg) {
		this.costOrg = costOrg;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="branchCode",nullable=true)
	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((auditorDeptid == null) ? 0 : auditorDeptid.hashCode());
		result = prime * result + ((auditDate == null) ? 0 : auditDate.hashCode());
		result = prime * result + ((auditorId == null) ? 0 : auditorId.hashCode());
		result = prime * result + ((auditorName == null) ? 0 : auditorName.hashCode());
		result = prime * result + ((checkperiod == null) ? 0 : checkperiod.hashCode());
		result = prime * result + ((costitemid == null) ? 0 : costitemid.hashCode());
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result + ((operateDate == null) ? 0 : operateDate.hashCode());
		result = prime * result + ((operatorId == null) ? 0 : operatorId.hashCode());
		result = prime * result + ((operatorName == null) ? 0 : operatorName.hashCode());
		result = prime * result + ((operatordeptid == null) ? 0 : operatordeptid.hashCode());
		result = prime * result + ((ownerdept == null) ? 0 : ownerdept.hashCode());
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((upcostId == null) ? 0 : upcostId.hashCode());
		result = prime * result + ((upitemid == null) ? 0 : upitemid.hashCode());
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
		UpCost other = (UpCost) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (auditorDeptid == null) {
			if (other.auditorDeptid != null)
				return false;
		} else if (!auditorDeptid.equals(other.auditorDeptid))
			return false;
		if (auditDate == null) {
			if (other.auditDate != null)
				return false;
		} else if (!auditDate.equals(other.auditDate))
			return false;
		if (auditorId == null) {
			if (other.auditorId != null)
				return false;
		} else if (!auditorId.equals(other.auditorId))
			return false;
		if (auditorName == null) {
			if (other.auditorName != null)
				return false;
		} else if (!auditorName.equals(other.auditorName))
			return false;
		if (checkperiod == null) {
			if (other.checkperiod != null)
				return false;
		} else if (!checkperiod.equals(other.checkperiod))
			return false;
		if (costitemid == null) {
			if (other.costitemid != null)
				return false;
		} else if (!costitemid.equals(other.costitemid))
			return false;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (operateDate == null) {
			if (other.operateDate != null)
				return false;
		} else if (!operateDate.equals(other.operateDate))
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
		if (operatordeptid == null) {
			if (other.operatordeptid != null)
				return false;
		} else if (!operatordeptid.equals(other.operatordeptid))
			return false;
		if (ownerdept == null) {
			if (other.ownerdept != null)
				return false;
		} else if (!ownerdept.equals(other.ownerdept))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (upcostId == null) {
			if (other.upcostId != null)
				return false;
		} else if (!upcostId.equals(other.upcostId))
			return false;
		if (upitemid == null) {
			if (other.upitemid != null)
				return false;
		} else if (!upitemid.equals(other.upitemid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "upCost [upcostId=" + upcostId + ", checkperiod=" + checkperiod + ", deptId=" + deptId + ", upitemid=" + upitemid + ", costitemid=" + costitemid + ", personId=" + personId + ", ownerdept=" + ownerdept + ", operatorId=" + operatorId + ", operatorName=" + operatorName + ", operateDate=" + operateDate + ", operatordeptid=" + operatordeptid + ", amount=" + amount + ", auditorId=" + auditorId + ", auditorName=" + auditorName + ", auditorDate=" + auditDate + ", auditDeptid=" + auditorDeptid + ", state=" + state + ", remark=" + remark + "]";
	}

	@Override
	public UpCost clone() throws CloneNotSupportedException {
		return (UpCost) super.clone();
	}
}