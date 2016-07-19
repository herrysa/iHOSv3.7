package com.huge.ihos.system.configuration.businessprocess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "sy_process" )
public class BusinessProcess extends BaseObject{

	private String processCode;
	private String processName;
	private String processType;
	private String businessTable;
	private String stateColumn;
	private String remark;
	
	@Id
	@Column(length=20)
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	
	@Column(length=50)
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	@Column(length=30)
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	@Column(length=50)
	public String getBusinessTable() {
		return businessTable;
	}
	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}
	
	@Column(length=20)
	public String getStateColumn() {
		return stateColumn;
	}
	public void setStateColumn(String stateColumn) {
		this.stateColumn = stateColumn;
	}
	
	@Column(length=200)
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
				+ ((businessTable == null) ? 0 : businessTable.hashCode());
		result = prime * result
				+ ((processCode == null) ? 0 : processCode.hashCode());
		result = prime * result
				+ ((processName == null) ? 0 : processName.hashCode());
		result = prime * result
				+ ((processType == null) ? 0 : processType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((stateColumn == null) ? 0 : stateColumn.hashCode());
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
		BusinessProcess other = (BusinessProcess) obj;
		if (businessTable == null) {
			if (other.businessTable != null)
				return false;
		} else if (!businessTable.equals(other.businessTable))
			return false;
		if (processCode == null) {
			if (other.processCode != null)
				return false;
		} else if (!processCode.equals(other.processCode))
			return false;
		if (processName == null) {
			if (other.processName != null)
				return false;
		} else if (!processName.equals(other.processName))
			return false;
		if (processType == null) {
			if (other.processType != null)
				return false;
		} else if (!processType.equals(other.processType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (stateColumn == null) {
			if (other.stateColumn != null)
				return false;
		} else if (!stateColumn.equals(other.stateColumn))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BusinessProcess [processCode=" + processCode + ", processName="
				+ processName + ", processType=" + processType
				+ ", businessTable=" + businessTable + ", stateColumn="
				+ stateColumn + ", remark=" + remark + "]";
	}
	
	
}
