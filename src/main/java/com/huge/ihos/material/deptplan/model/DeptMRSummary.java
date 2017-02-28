package com.huge.ihos.material.deptplan.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "mm_dept_mr_summary")
public class DeptMRSummary extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3649700552618299841L;
	private String mrId;
	private String orgCode;
	private String copyCode;
	private String periodMonth;
	private String mrNo;//汇总单号
	private Store store;
	private Person makePerson;
	private Date makeDate;
	private String planType;// 计划类型：月计划，追加计划
	private String remark;
	private String docTemId;
	private String purchaseId;
	
	@Column(name = "docTemId", length = 32, nullable = true)
	public String getDocTemId() {
		return docTemId;
	}

	public void setDocTemId(String docTemId) {
		this.docTemId = docTemId;
	}
	
	private Set<DeptMRSummaryDetail> deptMRSummaryDetails;
	
	@OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,mappedBy="deptMRSummary",cascade=CascadeType.ALL)
	public Set<DeptMRSummaryDetail> getDeptMRSummaryDetails() {
		return deptMRSummaryDetails;
	}

	public void setDeptMRSummaryDetails(Set<DeptMRSummaryDetail> deptMRSummaryDetails) {
		this.deptMRSummaryDetails = deptMRSummaryDetails;
	}

	@Id
	@Column(name = "mrId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getMrId() {
		return mrId;
	}

	public void setMrId(String mrId) {
		this.mrId = mrId;
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

	@Column(name = "periodMonth", length = 6, nullable = false)
	public String getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(String periodMonth) {
		this.periodMonth = periodMonth;
	}

	@Column(name = "mrNo", length = 20, nullable = true)
	public String getMrNo() {
		return mrNo;
	}

	public void setMrNo(String mrNo) {
		this.mrNo = mrNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maker", nullable = false)
	public Person getMakePerson() {
		return makePerson;
	}

	public void setMakePerson(Person makePerson) {
		this.makePerson = makePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "makeDate", length = 19, nullable = false)
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	@Column(name = "planType", length = 1, nullable = true)
	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	
	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "purchaseId", length = 32, nullable = true)
	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((docTemId == null) ? 0 : docTemId.hashCode());
		result = prime * result
				+ ((makeDate == null) ? 0 : makeDate.hashCode());
		result = prime * result
				+ ((makePerson == null) ? 0 : makePerson.hashCode());
		result = prime * result + ((mrId == null) ? 0 : mrId.hashCode());
		result = prime * result + ((mrNo == null) ? 0 : mrNo.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((periodMonth == null) ? 0 : periodMonth.hashCode());
		result = prime * result
				+ ((planType == null) ? 0 : planType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((purchaseId == null) ? 0 : purchaseId.hashCode());
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
		DeptMRSummary other = (DeptMRSummary) obj;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (docTemId == null) {
			if (other.docTemId != null)
				return false;
		} else if (!docTemId.equals(other.docTemId))
			return false;
		if (makeDate == null) {
			if (other.makeDate != null)
				return false;
		} else if (!makeDate.equals(other.makeDate))
			return false;
		if (makePerson == null) {
			if (other.makePerson != null)
				return false;
		} else if (!makePerson.equals(other.makePerson))
			return false;
		if (mrId == null) {
			if (other.mrId != null)
				return false;
		} else if (!mrId.equals(other.mrId))
			return false;
		if (mrNo == null) {
			if (other.mrNo != null)
				return false;
		} else if (!mrNo.equals(other.mrNo))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (periodMonth == null) {
			if (other.periodMonth != null)
				return false;
		} else if (!periodMonth.equals(other.periodMonth))
			return false;
		if (planType == null) {
			if (other.planType != null)
				return false;
		} else if (!planType.equals(other.planType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if(purchaseId == null){
			if(other.purchaseId != null)
				return false;
		}else if(!purchaseId.equals(other.purchaseId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptMRSummary [mrId=" + mrId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", periodMonth=" + periodMonth
				+ ", mrNo=" + mrNo  + ", store=" + store
				+ ", makePerson=" + makePerson + ", makeDate=" + makeDate
				+ ", checkPerson=" 	+ ", planType=" + planType + ", remark="
				+ remark + ", docTemId=" + docTemId + ", purchaseId=" + purchaseId + "]";
	}
	

}
