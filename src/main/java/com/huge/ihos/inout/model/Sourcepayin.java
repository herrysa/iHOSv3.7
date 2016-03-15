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
@Table(name = "t_sourcepayin")
public class Sourcepayin extends BaseObject implements Serializable {
	private Long sourcePayinId;

	private String checkPeriod;

	private BigDecimal lici;

	private BigDecimal amount;

	private Date processDate;

	private Boolean manual;

	private String remarks;

	private String outin;

	private ChargeItem chargeItem;

	private String freemark;

	private String kdDoctorid;

	private String kdDoctorName;

	private String patientId;

	private String patientName;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String patientType;

	private Department kdDept;

	private Department zxDept;

	private Department hlDept;

	private Department jzDept;

	private ChargeType chargeType;

	private Person operator;

	private Org kdOrg;

	private Org zxOrg;

	private Org costOrg;
	
	private Branch kdBranch;
	
	private Branch zxBranch;
	
	private Branch srBranch;

	private String status;		//状态（0：新建；1：已结帐）

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chargeItemId", nullable = true)
	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId", nullable = true)
	public Person getOperator() {
		return operator;
	}

	public void setOperator(Person operator) {
		this.operator = operator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kdDeptId", nullable = true)
	public Department getKdDept() {
		return kdDept;
	}

	public void setKdDept(Department kdDept) {
		this.kdDept = kdDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zxDeptId", nullable = true)
	public Department getZxDept() {
		return zxDept;
	}

	public void setZxDept(Department zxDept) {
		this.zxDept = zxDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hlDeptId", nullable = true)
	public Department getHlDept() {
		return hlDept;
	}

	public void setHlDept(Department hlDept) {
		this.hlDept = hlDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jzDeptId", nullable = true)
	public Department getJzDept() {
		return jzDept;
	}

	public void setJzDept(Department jzDept) {
		this.jzDept = jzDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chargeTypeId", nullable = true)
	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getSourcePayinId() {
		return this.sourcePayinId;
	}

	public void setSourcePayinId(Long sourcePayinId) {
		this.sourcePayinId = sourcePayinId;
	}

	@Column(name = "checkPeriod", nullable = false, length = 6)
	public String getCheckPeriod() {
		return this.checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	@Column(name = "lc", precision = 18, scale = 4)
	public BigDecimal getLici() {
		return this.lici;
	}

	public void setLici(BigDecimal lici) {
		this.lici = lici;
	}

	@Column(name = "amount", nullable = false, precision = 18, scale = 6)
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

	@Column(name = "outin", nullable = false, length = 6)
	public String getOutin() {
		return this.outin;
	}

	public void setOutin(String outin) {
		this.outin = outin;
	}

	@Column(name = "freemark", length = 50)
	public String getFreemark() {
		return this.freemark;
	}

	public void setFreemark(String freemark) {
		this.freemark = freemark;
	}

	@Column(name = "kdDoctorid", length = 20)
	public String getKdDoctorid() {
		return this.kdDoctorid;
	}

	public void setKdDoctorid(String kdDoctorid) {
		this.kdDoctorid = kdDoctorid;
	}

	@Column(name = "kdDoctorName", length = 50)
	public String getKdDoctorName() {
		return this.kdDoctorName;
	}

	public void setKdDoctorName(String kdDoctorName) {
		this.kdDoctorName = kdDoctorName;
	}

	@Column(name = "patientId", length = 20)
	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@Column(name = "patientName", length = 50)
	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
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

	@Column(name = "userdefine3", length = 50)
	public String getUserdefine3() {
		return this.userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kdOrgCode", nullable = true)
	public Org getKdOrg() {
		return kdOrg;
	}

	public void setKdOrg(Org kdOrg) {
		this.kdOrg = kdOrg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zxOrgCode", nullable = true)
	public Org getZxOrg() {
		return zxOrg;
	}

	public void setZxOrg(Org zxOrg) {
		this.zxOrg = zxOrg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "srOrgCode", nullable = true)
	public Org getCostOrg() {
		return costOrg;
	}

	public void setCostOrg(Org costOrg) {
		this.costOrg = costOrg;
	}

	@Column(name = "status", length = 1, nullable = true)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kdBranchCode", nullable = true)
	public Branch getKdBranch() {
		return kdBranch;
	}

	public void setKdBranch(Branch kdBranch) {
		this.kdBranch = kdBranch;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zxBranchCode", nullable = true)
	public Branch getZxBranch() {
		return zxBranch;
	}

	public void setZxBranch(Branch zxBranch) {
		this.zxBranch = zxBranch;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "srBranchCode", nullable = true)
	public Branch getSrBranch() {
		return srBranch;
	}

	public void setSrBranch(Branch srBranch) {
		this.srBranch = srBranch;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Sourcepayin pojo = (Sourcepayin) o;

		if (checkPeriod != null ? !checkPeriod.equals(pojo.checkPeriod) : pojo.checkPeriod != null)
			return false;

		if (lici != null ? !lici.equals(pojo.lici) : pojo.lici != null)
			return false;
		if (amount != null ? !amount.equals(pojo.amount) : pojo.amount != null)
			return false;
		if (processDate != null ? !processDate.equals(pojo.processDate) : pojo.processDate != null)
			return false;

		if (manual != null ? !manual.equals(pojo.manual) : pojo.manual != null)
			return false;
		if (remarks != null ? !remarks.equals(pojo.remarks) : pojo.remarks != null)
			return false;
		if (outin != null ? !outin.equals(pojo.outin) : pojo.outin != null)
			return false;
		if (freemark != null ? !freemark.equals(pojo.freemark) : pojo.freemark != null)
			return false;
		if (kdDoctorid != null ? !kdDoctorid.equals(pojo.kdDoctorid) : pojo.kdDoctorid != null)
			return false;
		if (kdDoctorName != null ? !kdDoctorName.equals(pojo.kdDoctorName) : pojo.kdDoctorName != null)
			return false;
		if (patientId != null ? !patientId.equals(pojo.patientId) : pojo.patientId != null)
			return false;
		if (patientName != null ? !patientName.equals(pojo.patientName) : pojo.patientName != null)
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

		result = 31 * result + (lici != null ? lici.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (processDate != null ? processDate.hashCode() : 0);

		result = 31 * result + (manual != null ? manual.hashCode() : 0);
		result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
		result = 31 * result + (outin != null ? outin.hashCode() : 0);
		result = 31 * result + (freemark != null ? freemark.hashCode() : 0);
		result = 31 * result + (kdDoctorid != null ? kdDoctorid.hashCode() : 0);
		result = 31 * result + (kdDoctorName != null ? kdDoctorName.hashCode() : 0);
		result = 31 * result + (patientId != null ? patientId.hashCode() : 0);
		result = 31 * result + (patientName != null ? patientName.hashCode() : 0);
		result = 31 * result + (userdefine1 != null ? userdefine1.hashCode() : 0);
		result = 31 * result + (userdefine2 != null ? userdefine2.hashCode() : 0);
		result = 31 * result + (userdefine3 != null ? userdefine3.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("sourcePayinId").append("='").append(getSourcePayinId()).append("', ");
		sb.append("checkPeriod").append("='").append(getCheckPeriod()).append("', ");
		sb.append("lc").append("='").append(getLici()).append("', ");
		sb.append("amount").append("='").append(getAmount()).append("', ");
		sb.append("processDate").append("='").append(getProcessDate()).append("', ");

		sb.append("manual").append("='").append(getManual()).append("', ");
		sb.append("remarks").append("='").append(getRemarks()).append("', ");
		sb.append("outin").append("='").append(getOutin()).append("', ");
		sb.append("freemark").append("='").append(getFreemark()).append("', ");
		sb.append("kdDoctorid").append("='").append(getKdDoctorid()).append("', ");
		sb.append("kdDoctorName").append("='").append(getKdDoctorName()).append("', ");
		sb.append("patientId").append("='").append(getPatientId()).append("', ");
		sb.append("patientName").append("='").append(getPatientName()).append("', ");
		sb.append("userdefine1").append("='").append(getUserdefine1()).append("', ");
		sb.append("userdefine2").append("='").append(getUserdefine2()).append("', ");
		sb.append("userdefine3").append("='").append(getUserdefine3()).append("'");
		sb.append("]");

		return sb.toString();
	}

	@Column(name = "patientType", nullable = true, length = 50)
	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

}
