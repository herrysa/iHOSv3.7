package com.huge.ihos.inout.model;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "t_sourcecost")
public class Sourcecost extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7320200689371299370L;

	private Long sourceCostId;

	private String checkPeriod;

	private BigDecimal amount;

	private Date processDate;

	private Boolean manual;

	private String remarks;

	private String freemark;

	private Boolean ifallot;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private Department deptartment;

	private CostItem costItemId;

	private Person operator;

	private Org costOrg;
	
	private Branch cbBranch;
	
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deptId", nullable = false)
	public Department getDeptartment() {
		return deptartment;
	}

	public void setDeptartment(Department deptartment) {
		this.deptartment = deptartment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "costItemId", nullable = false)
	public CostItem getCostItemId() {
		return costItemId;
	}

	public void setCostItemId(CostItem costItemId) {
		this.costItemId = costItemId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId", nullable = true)
	public Person getOperator() {
		return operator;
	}

	public void setOperator(Person operator) {
		this.operator = operator;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getSourceCostId() {
		return this.sourceCostId;
	}

	public void setSourceCostId(Long sourceCostId) {
		this.sourceCostId = sourceCostId;
	}

	@Column(name = "checkPeriod", nullable = false, length = 6)
	public String getCheckPeriod() {
		return this.checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	@Column(name = "amount", precision = 18, scale = 6)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "processDate", length = 19)
	public Date getProcessDate() {
		return this.processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@Column(name = "manual")
	public Boolean getManual() {
		return this.manual;
	}

	public void setManual(Boolean manual) {
		this.manual = manual;
	}

	@Column(name = "remarks", length = 128)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "freemark", length = 30)
	public String getFreemark() {
		return this.freemark;
	}

	public void setFreemark(String freemark) {
		this.freemark = freemark;
	}

	@Column(name = "ifallot", nullable = false)
	public Boolean getIfallot() {
		return this.ifallot;
	}

	public void setIfallot(Boolean ifallot) {
		this.ifallot = ifallot;
	}

	@Column(name = "userdefine1", length = 50)
	public String getUserdefine1() {
		return this.userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	@Column(name = "userdefine2", length = 50)
	public String getUserdefine2() {
		return this.userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbOrgCode", nullable = true)
	public Org getCostOrg() {
		return costOrg;
	}

	public void setCostOrg(Org costOrg) {
		this.costOrg = costOrg;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cbBranchCode",nullable=true)
	public Branch getCbBranch() {
		return cbBranch;
	}
	public void setCbBranch(Branch cbBranch) {
		this.cbBranch = cbBranch;
	}

	@Column(name = "userdefine3", length = 50)
	public String getUserdefine3() {
		return this.userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}
	@Column(name="status",length=1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Sourcecost pojo = (Sourcecost) o;

		if (checkPeriod != null ? !checkPeriod.equals(pojo.checkPeriod) : pojo.checkPeriod != null)
			return false;

		if (amount != null ? !amount.equals(pojo.amount) : pojo.amount != null)
			return false;
		if (processDate != null ? !processDate.equals(pojo.processDate) : pojo.processDate != null)
			return false;

		if (manual != null ? !manual.equals(pojo.manual) : pojo.manual != null)
			return false;
		if (remarks != null ? !remarks.equals(pojo.remarks) : pojo.remarks != null)
			return false;
		if (freemark != null ? !freemark.equals(pojo.freemark) : pojo.freemark != null)
			return false;
		if (ifallot != null ? !ifallot.equals(pojo.ifallot) : pojo.ifallot != null)
			return false;
		if (userdefine1 != null ? !userdefine1.equals(pojo.userdefine1) : pojo.userdefine1 != null)
			return false;
		if (userdefine2 != null ? !userdefine2.equals(pojo.userdefine2) : pojo.userdefine2 != null)
			return false;
		if (userdefine3 != null ? !userdefine3.equals(pojo.userdefine3) : pojo.userdefine3 != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (checkPeriod != null ? checkPeriod.hashCode() : 0);

		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (processDate != null ? processDate.hashCode() : 0);
		result = 31 * result + (manual != null ? manual.hashCode() : 0);
		result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
		result = 31 * result + (freemark != null ? freemark.hashCode() : 0);
		result = 31 * result + (ifallot != null ? ifallot.hashCode() : 0);
		result = 31 * result + (userdefine1 != null ? userdefine1.hashCode() : 0);
		result = 31 * result + (userdefine2 != null ? userdefine2.hashCode() : 0);
		result = 31 * result + (userdefine3 != null ? userdefine3.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("sourceCostId").append("='").append(getSourceCostId()).append("', ");
		sb.append("checkPeriod").append("='").append(getCheckPeriod()).append("', ");
		sb.append("amount").append("='").append(getAmount()).append("', ");
		sb.append("processDate").append("='").append(getProcessDate()).append("', ");
		sb.append("manual").append("='").append(getManual()).append("', ");
		sb.append("remarks").append("='").append(getRemarks()).append("', ");
		sb.append("freemark").append("='").append(getFreemark()).append("', ");
		sb.append("ifallot").append("='").append(getIfallot()).append("', ");
		sb.append("userdefine1").append("='").append(getUserdefine1()).append("', ");
		sb.append("userdefine2").append("='").append(getUserdefine2()).append("', ");
		sb.append("userdefine3").append("='").append(getUserdefine3()).append("'");
		sb.append("]");

		return sb.toString();
	}

}
