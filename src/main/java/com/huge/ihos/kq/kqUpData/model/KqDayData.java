package com.huge.ihos.kq.kqUpData.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 考勤上报表
 * @author Administrator
 *
 */
@Entity
@Table(name = "kq_dayData")
public class KqDayData extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3197927684746541465L;
	private String kqId;//ID
	private String period;//期间
	private String kqType;//考勤类别
	private String personId;//人员Id
	private String personCode;//人员编码
	private String personName;//人员姓名
	private String branchCode;//院区
	private String branchName;//院区名称
	private String orgCode;//单位
	private String orgName;//单位名称
	private String deptId;//部门Id
	private String deptCode;//部门编码
	private String deptName;//部门名称
	private String kqDeptName;//出勤部门
	private String empType;//职工类别
	private String status = "0";//状态0新增；1审核；2：提交；3：通过；4：退回
	private String maker;//编制人
	private Date makeDate;//编制日期
	private String checker;//审核人
	private Date checkDate;//审核日期
	private String submiter;//提交人
	private Date submitDate;//提交日期

	@Id
	@Column(name = "kqId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getKqId() {
		return kqId;
	}

	public void setKqId(String kqId) {
		this.kqId = kqId;
	}

	@Column(name = "kqTypeId", nullable = false, length = 32)
	public String getKqType() {
		return kqType;
	}

	public void setKqType(String kqType) {
		this.kqType = kqType;
	}

	@Column(name = "branchCode", nullable = true, length = 30)
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "branchName", nullable = true, length = 50)
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "orgCode", nullable = true, length = 10)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "orgName", nullable = true, length = 50)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "period", nullable = true, length = 6)
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Column(name = "personId", nullable = true, length = 50)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "personCode", nullable = true, length = 50)
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@Column(name = "personName", nullable = true, length = 50)
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "deptId", nullable = true, length = 50)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "deptName", nullable = true, length = 50)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "deptCode", nullable = true, length = 20)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name = "empType", nullable = true, length = 20)
	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	@Column(name = "maker", nullable = true, length = 20)
	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = true)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@Column(name = "checker", nullable = true, length = 20)
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", length = 19, nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "submiter", nullable = true, length = 20)
	public String getSubmiter() {
		return submiter;
	}

	public void setSubmiter(String submiter) {
		this.submiter = submiter;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submitDate", length = 19, nullable = true)
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "status", nullable = true, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "KqUpdata [kqId=" + kqId + ", orgCode=" + orgCode + ", period=" + period + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KqDayData other = (KqDayData) obj;
		if (kqId == null) {
			if (other.kqId != null)
				return false;
		} else if (!kqId.equals(other.kqId))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kqId == null) ? 0 : kqId.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		return result;
	}

	@Column(name = "kqDeptName", nullable = true, length = 50)
	public String getKqDeptName() {
		return kqDeptName;
	}

	public void setKqDeptName(String kqDeptName) {
		this.kqDeptName = kqDeptName;
	}
}
