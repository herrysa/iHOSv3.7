package com.huge.ihos.material.deptapp.model;

import java.io.Serializable;
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

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;
/**
 * 科室申领发放记录主表
 * 记录某人依据某申领单从某仓库发放若干数量的某批次的某材料到某科室
 * @author Gaozhengyang
 * @date 2014年8月12日
 */
@Entity
@Table(name = "mm_dept_app_dis_log")
public class DeptAppDisLog extends BaseObject implements Serializable{
	private static final long serialVersionUID = 3460125473839250254L;
	private String logId;
	private String orgCode;
	private String copyCode;
	private String yearMonth;
	private Store store;
	private InventoryDict invDict;
	private Department appDept;
	private String deptAppNo;
	private Set<DeptAppDLDetail> deptAppDLDetails;
	
	@Id
	@Column(name = "logId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getLogId() {
		return logId;
	}
	
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	@Column(name = "deptAppNo", length = 20, nullable = false)
	public String getDeptAppNo() {
		return deptAppNo;
	}

	public void setDeptAppNo(String deptAppNo) {
		this.deptAppNo = deptAppNo;
	}
	
	@OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,mappedBy="deptAppDL",cascade=CascadeType.ALL)
	public Set<DeptAppDLDetail> getDeptAppDLDetails() {
		return deptAppDLDetails;
	}

	public void setDeptAppDLDetails(Set<DeptAppDLDetail> deptAppDLDetails) {
		this.deptAppDLDetails = deptAppDLDetails;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invId", nullable = false)
	public InventoryDict getInvDict() {
		return invDict;
	}

	public void setInvDict(InventoryDict invDict) {
		this.invDict = invDict;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appDeptId", nullable = false)
	public Department getAppDept() {
		return appDept;
	}

	public void setAppDept(Department appDept) {
		this.appDept = appDept;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appDept == null) ? 0 : appDept.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((deptAppNo == null) ? 0 : deptAppNo.hashCode());
		result = prime * result + ((invDict == null) ? 0 : invDict.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result
				+ ((yearMonth == null) ? 0 : yearMonth.hashCode());
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
		DeptAppDisLog other = (DeptAppDisLog) obj;
		if (appDept == null) {
			if (other.appDept != null)
				return false;
		} else if (!appDept.equals(other.appDept))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (deptAppNo == null) {
			if (other.deptAppNo != null)
				return false;
		} else if (!deptAppNo.equals(other.deptAppNo))
			return false;
		if (invDict == null) {
			if (other.invDict != null)
				return false;
		} else if (!invDict.equals(other.invDict))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeptAppDisLog [logId=" + logId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", yearMonth=" + yearMonth
				+ ", store=" + store + ", invDict=" + invDict + ", appDept="
				+ appDept + ", deptAppNo=" + deptAppNo + "]";
	}

}
