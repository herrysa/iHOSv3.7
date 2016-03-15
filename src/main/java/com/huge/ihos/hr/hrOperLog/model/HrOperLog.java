package com.huge.ihos.hr.hrOperLog.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "hr_operlog")
public class HrOperLog extends BaseObject implements Serializable {
	public static final String OPER_ADD = "add";
	public static final String OPER_EDIT = "edit";
	public static final String OPER_DELETE = "del";
	
	private static final long serialVersionUID = 6498326903687285157L;
	private String logId;
	private String orgCode;
	private String recordCode;	//操作的记录的唯一标识(不一定是ID)
	private String operTable;	//操作的表
	private Person operPerson;
	private Date operTime;
	private String operType;
	private String columnName;
	private String oldValue;
	private String newValue;

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

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "recordCode", length = 50, nullable = false)
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	@Column(name = "operTable", length = 30, nullable = false)
	public String getOperTable() {
		return operTable;
	}

	public void setOperTable(String operTable) {
		this.operTable = operTable;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator",nullable = false)
	public Person getOperPerson() {
		return operPerson;
	}

	public void setOperPerson(Person operPerson) {
		this.operPerson = operPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operTime", length = 19, nullable = false)
	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	@Column(name = "operType", length = 4, nullable = false)
	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	@Column(name = "columnName", length = 50, nullable = true)
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Column(name = "oldValue",nullable = true)
	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	@Column(name = "newValue",nullable = true)
	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result
				+ ((newValue == null) ? 0 : newValue.hashCode());
		result = prime * result
				+ ((oldValue == null) ? 0 : oldValue.hashCode());
		result = prime * result
				+ ((operPerson == null) ? 0 : operPerson.hashCode());
		result = prime * result
				+ ((operTable == null) ? 0 : operTable.hashCode());
		result = prime * result
				+ ((operTime == null) ? 0 : operTime.hashCode());
		result = prime * result
				+ ((operType == null) ? 0 : operType.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((recordCode == null) ? 0 : recordCode.hashCode());
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
		HrOperLog other = (HrOperLog) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		if (oldValue == null) {
			if (other.oldValue != null)
				return false;
		} else if (!oldValue.equals(other.oldValue))
			return false;
		if (operPerson == null) {
			if (other.operPerson != null)
				return false;
		} else if (!operPerson.equals(other.operPerson))
			return false;
		if (operTable == null) {
			if (other.operTable != null)
				return false;
		} else if (!operTable.equals(other.operTable))
			return false;
		if (operTime == null) {
			if (other.operTime != null)
				return false;
		} else if (!operTime.equals(other.operTime))
			return false;
		if (operType == null) {
			if (other.operType != null)
				return false;
		} else if (!operType.equals(other.operType))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (recordCode == null) {
			if (other.recordCode != null)
				return false;
		} else if (!recordCode.equals(other.recordCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrOperLog [logId=" + logId + ", orgCode=" + orgCode
				+ ", recordCode=" + recordCode + ", operTable=" + operTable
				+ ", operPerson=" + operPerson + ", operTime=" + operTime
				+ ", operType=" + operType + ", columnName=" + columnName
				+ ", oldValue=" + oldValue + ", newValue=" + newValue + "]";
	}

}
