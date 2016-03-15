package com.huge.ihos.hr.asyncHrData.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
/**
 * hr同步数据log
 */
@Entity
@Table(name = "hr_syncData_log")
//@SuppressWarnings("serial")
public class syncHrData extends BaseObject{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 350361169501785906L;

	private String syncHrId;  //同步操作的id
	
	private String syncHrName ;  //同步操作名称
	
	private String syncOperator ; //同步操作人
	
	private Date syncTime ; //同步操作时间
	
	private Date hr_snap_time ; //hr_snap的时间点记录 
	
	private String isUseHR ;    //是否使用hr中的单位
	
	private String orgName ; //单位名称 
	
	private String orgCode ; //单位编码
	
	private String deptIds  ;  //同步中的部门ids
	
	private String syncType ; //同步类型
	
	private String deptNames ; //用来显示同步的部门
	
	private String remarks ; //同步的备注
	
	private String temparyTime ; //用于时间的中间转换
	
	private String syncToHrType ;   //该字段记录为那种从hr同步方式
	
     
	

	@Id
	@Column(name = "syncHrId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getSyncHrId() {
		return syncHrId;
	}

	public void setSyncHrId(String syncHrId) {
		this.syncHrId = syncHrId;
	}
	
	@Column(name = "syncHrName", nullable = false, length = 50)
	public String getSyncHrName() {
		return syncHrName;
	}

	public void setSyncHrName(String syncHrName) {
		this.syncHrName = syncHrName;
	}
	@Column(name = "syncOperator", nullable = false, length = 50)
	public String getSyncOperator() {
		return syncOperator;
	}

	public void setSyncOperator(String syncOperator) {
		this.syncOperator = syncOperator;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hr_snap_time",nullable = true)
	public Date getHr_snap_time() {
		return hr_snap_time;
	}

	public void setHr_snap_time(Date hr_snap_time) {
		this.hr_snap_time = hr_snap_time;
	}
	@Column(name = "isUseHR", nullable = true, length = 20)
	public String getIsUseHR() {
		return isUseHR;
	}

	public void setIsUseHR(String isUseHR) {
		this.isUseHR = isUseHR;
	}
	@Column(name = "orgName", nullable = true, length = 50)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(name = "orgCode", nullable = true, length = 10)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Column(name = "deptIds", nullable = true)
	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "syncTime",nullable = true)
    public Date getSyncTime() {
		return syncTime;
	}
	
	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}
	@Column(name = "syncType", nullable = false, length = 2)
	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
	@Column(name = "deptNames", nullable = true)
	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	@Column(name = "remarks", nullable = true, length = 1000)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
	@Transient
	public String getTemparyTime() {
		return temparyTime;
	}

	public void setTemparyTime(String temparyTime) {
		this.temparyTime = temparyTime;
	}
	@Column(name = "syncToHrType", nullable = false, length = 50)
	public String getSyncToHrType() {
		return syncToHrType;
	}

	public void setSyncToHrType(String syncToHrType) {
		this.syncToHrType = syncToHrType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptIds == null) ? 0 : deptIds.hashCode());
		result = prime * result
				+ ((deptNames == null) ? 0 : deptNames.hashCode());
		result = prime * result
				+ ((hr_snap_time == null) ? 0 : hr_snap_time.hashCode());
		result = prime * result + ((isUseHR == null) ? 0 : isUseHR.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result
				+ ((syncHrId == null) ? 0 : syncHrId.hashCode());
		result = prime * result
				+ ((syncHrName == null) ? 0 : syncHrName.hashCode());
		result = prime * result
				+ ((syncOperator == null) ? 0 : syncOperator.hashCode());
		result = prime * result
				+ ((syncTime == null) ? 0 : syncTime.hashCode());
		result = prime * result
				+ ((syncType == null) ? 0 : syncType.hashCode());
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
		syncHrData other = (syncHrData) obj;
		if (deptIds == null) {
			if (other.deptIds != null)
				return false;
		} else if (!deptIds.equals(other.deptIds))
			return false;
		if (deptNames == null) {
			if (other.deptNames != null)
				return false;
		} else if (!deptNames.equals(other.deptNames))
			return false;
		if (hr_snap_time == null) {
			if (other.hr_snap_time != null)
				return false;
		} else if (!hr_snap_time.equals(other.hr_snap_time))
			return false;
		if (isUseHR == null) {
			if (other.isUseHR != null)
				return false;
		} else if (!isUseHR.equals(other.isUseHR))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (syncHrId == null) {
			if (other.syncHrId != null)
				return false;
		} else if (!syncHrId.equals(other.syncHrId))
			return false;
		if (syncHrName == null) {
			if (other.syncHrName != null)
				return false;
		} else if (!syncHrName.equals(other.syncHrName))
			return false;
		if (syncOperator == null) {
			if (other.syncOperator != null)
				return false;
		} else if (!syncOperator.equals(other.syncOperator))
			return false;
		if (syncTime == null) {
			if (other.syncTime != null)
				return false;
		} else if (!syncTime.equals(other.syncTime))
			return false;
		if (syncType == null) {
			if (other.syncType != null)
				return false;
		} else if (!syncType.equals(other.syncType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "syncHrData [syncHrId=" + syncHrId + ", syncHrName="
				+ syncHrName + ", syncOperator=" + syncOperator + ", syncTime="
				+ syncTime + ", hr_snap_time=" + hr_snap_time + ", isUseHR="
				+ isUseHR + ", orgName=" + orgName + ", orgCode=" + orgCode
				+ ", deptIds=" + deptIds + ", syncType=" + syncType
				+ ", deptNames=" + deptNames + ", remarks=" + remarks
				+  "]";
	}
    
	
	
	

    
	
	

}
