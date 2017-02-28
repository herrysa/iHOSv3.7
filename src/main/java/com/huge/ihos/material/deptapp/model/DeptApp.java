package com.huge.ihos.material.deptapp.model;

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
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

/**
 * 科室申领主表
 * 
 * @author Gaozhengyang
 * @date 2014年7月1日
 */
@Entity
@Table(name = "mm_dept_app_main")
public class DeptApp extends BaseObject implements Serializable {
	private static final long serialVersionUID = 7680357125800798843L;

	private String deptAppId;
	private String orgCode;
	private String copyCode;
	private String yearMonth;
	private String appNo;
	private Date appDate;
	private Department appDept;
	private Person appPerson;// 申领人--新建
	private Store store;
	private Person appChecker;// 审核人--审核
	private Date checkDate;
	private Person appConfirmer;// 发送人--发送
	private Date confirmDate;
	private Person storeChecker;// 仓库管理员[审核]
	private Date storeCheckDate;
	private String appState;
//	private String generalBillNo;// 生成出库单据号
//	private Date generalBillDate;
	private String remark;
	private String docTemId;

	@Column(name = "docTemId", length = 32, nullable = true)
	public String getDocTemId() {
		return docTemId;
	}

	public void setDocTemId(String docTemId) {
		this.docTemId = docTemId;
	}

	private Set<DeptAppDetail> deptAppDetails;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "deptApp", cascade = CascadeType.ALL)
	public Set<DeptAppDetail> getDeptAppDetails() {
		return deptAppDetails;
	}

	public void setDeptAppDetails(Set<DeptAppDetail> deptAppDetails) {
		this.deptAppDetails = deptAppDetails;
	}

	@Id
	@Column(name = "deptAppId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getDeptAppId() {
		return deptAppId;
	}

	public void setDeptAppId(String deptAppId) {
		this.deptAppId = deptAppId;
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

	@Column(name = "appNo", length = 20, nullable = false)
	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "appDate", nullable = false)
	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appDeptId", nullable = false)
	public Department getAppDept() {
		return appDept;
	}

	public void setAppDept(Department appDept) {
		this.appDept = appDept;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appPersonId", nullable = false)
	public Person getAppPerson() {
		return appPerson;
	}

	public void setAppPerson(Person appPerson) {
		this.appPerson = appPerson;
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
	@JoinColumn(name = "appChecker", nullable = true)
	public Person getAppChecker() {
		return appChecker;
	}

	public void setAppChecker(Person appChecker) {
		this.appChecker = appChecker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkDate", nullable = true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appConfirmer", nullable = true)
	public Person getAppConfirmer() {
		return appConfirmer;
	}

	public void setAppConfirmer(Person appConfirmer) {
		this.appConfirmer = appConfirmer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmDate", nullable = true)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeChecker", nullable = true)
	public Person getStoreChecker() {
		return storeChecker;
	}

	public void setStoreChecker(Person storeChecker) {
		this.storeChecker = storeChecker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "storeCheckDate", nullable = true)
	public Date getStoreCheckDate() {
		return storeCheckDate;
	}

	public void setStoreCheckDate(Date storeCheckDate) {
		this.storeCheckDate = storeCheckDate;
	}

	@Column(name = "appState", length = 1, nullable = true)
	public String getAppState() {
		return appState;
	}

	public void setAppState(String appState) {
		this.appState = appState;
	}

//	@Column(name = "generalBillNo", length = 20, nullable = true)
//	public String getGeneralBillNo() {
//		return generalBillNo;
//	}
//
//	public void setGeneralBillNo(String generalBillNo) {
//		this.generalBillNo = generalBillNo;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "generalBillDate", nullable = true)
//	public Date getGeneralBillDate() {
//		return generalBillDate;
//	}
//
//	public void setGeneralBillDate(Date generalBillDate) {
//		this.generalBillDate = generalBillDate;
//	}

	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appChecker == null) ? 0 : appChecker.hashCode());
		result = prime * result
				+ ((appConfirmer == null) ? 0 : appConfirmer.hashCode());
		result = prime * result + ((appDate == null) ? 0 : appDate.hashCode());
		result = prime * result + ((appDept == null) ? 0 : appDept.hashCode());
		result = prime * result + ((appNo == null) ? 0 : appNo.hashCode());
		result = prime * result
				+ ((yearMonth == null) ? 0 : yearMonth.hashCode());
		result = prime * result
				+ ((appPerson == null) ? 0 : appPerson.hashCode());
		result = prime * result
				+ ((appState == null) ? 0 : appState.hashCode());
		result = prime * result
				+ ((checkDate == null) ? 0 : checkDate.hashCode());
		result = prime * result
				+ ((confirmDate == null) ? 0 : confirmDate.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((deptAppId == null) ? 0 : deptAppId.hashCode());
		result = prime * result
				+ ((docTemId == null) ? 0 : docTemId.hashCode());
//		result = prime * result
//				+ ((generalBillDate == null) ? 0 : generalBillDate.hashCode());
//		result = prime * result
//				+ ((generalBillNo == null) ? 0 : generalBillNo.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result
				+ ((storeCheckDate == null) ? 0 : storeCheckDate.hashCode());
		result = prime * result
				+ ((storeChecker == null) ? 0 : storeChecker.hashCode());
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
		DeptApp other = (DeptApp) obj;
		if (appChecker == null) {
			if (other.appChecker != null)
				return false;
		} else if (!appChecker.equals(other.appChecker))
			return false;
		if (appConfirmer == null) {
			if (other.appConfirmer != null)
				return false;
		} else if (!appConfirmer.equals(other.appConfirmer))
			return false;
		if (appDate == null) {
			if (other.appDate != null)
				return false;
		} else if (!appDate.equals(other.appDate))
			return false;
		if (appDept == null) {
			if (other.appDept != null)
				return false;
		} else if (!appDept.equals(other.appDept))
			return false;
		if (appNo == null) {
			if (other.appNo != null)
				return false;
		} else if (!appNo.equals(other.appNo))
			return false;
		if (appPerson == null) {
			if (other.appPerson != null)
				return false;
		} else if (!appPerson.equals(other.appPerson))
			return false;
		if (appState == null) {
			if (other.appState != null)
				return false;
		} else if (!appState.equals(other.appState))
			return false;
		if (checkDate == null) {
			if (other.checkDate != null)
				return false;
		} else if (!checkDate.equals(other.checkDate))
			return false;
		if (confirmDate == null) {
			if (other.confirmDate != null)
				return false;
		} else if (!confirmDate.equals(other.confirmDate))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (deptAppId == null) {
			if (other.deptAppId != null)
				return false;
		} else if (!deptAppId.equals(other.deptAppId))
			return false;
		if (docTemId == null) {
			if (other.docTemId != null)
				return false;
		} else if (!docTemId.equals(other.docTemId))
//			return false;
//		if (generalBillDate == null) {
//			if (other.generalBillDate != null)
//				return false;
//		} else if (!generalBillDate.equals(other.generalBillDate))
//			return false;
//		if (generalBillNo == null) {
//			if (other.generalBillNo != null)
//				return false;
//		} else if (!generalBillNo.equals(other.generalBillNo))
//			return false;
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
		if (storeCheckDate == null) {
			if (other.storeCheckDate != null)
				return false;
		} else if (!storeCheckDate.equals(other.storeCheckDate))
			return false;
		if (storeChecker == null) {
			if (other.storeChecker != null)
				return false;
		} else if (!storeChecker.equals(other.storeChecker))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptApp [deptAppId=" + deptAppId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", yearMonth=" + yearMonth
				+ ", appNo=" + appNo + ", appDate=" + appDate + ", appDept="
				+ appDept + ", appPerson=" + appPerson + ", store=" + store
				+ ", appChecker=" + appChecker + ", checkDate=" + checkDate
				+ ", appConfirmer=" + appConfirmer + ", confirmDate="
				+ confirmDate + ", storeChecker=" + storeChecker
				+ ", storeCheckDate=" + storeCheckDate + ", appState="+ appState + 
//				", generalBillNo=" + generalBillNo+ ", generalBillDate=" + generalBillDate +
				", remark=" + remark+ ", docTemId=" + docTemId + "]";
	}

}
