package com.huge.ihos.bm.budgetModel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "bm_process_log" )
public class BmModelProcessLog {

	private String logId;
	private String modelId;
	private String updataId;
	private String stepCode;
	private String operation;
	private String info;
	private Date optTime;
	private String deptId;
	private String deptName;
	private String personId;
	private String personName;
	private Integer state;
	
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
	
	@Column(length=20)
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Column(length=32)
	public String getUpdataId() {
		return updataId;
	}
	public void setUpdataId(String updataId) {
		this.updataId = updataId;
	}
	
	@Column(length=20)
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	
	@Column(length=5)
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Column(length=200)
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Column
	public Date getOptTime() {
		return optTime;
	}
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	
	@Column(length=50)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(length=50)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Column(length=50)
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	@Column(length=50)
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	@Column
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
		result = prime * result
				+ ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result + ((modelId == null) ? 0 : modelId.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((optTime == null) ? 0 : optTime.hashCode());
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
		result = prime * result
				+ ((personName == null) ? 0 : personName.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((stepCode == null) ? 0 : stepCode.hashCode());
		result = prime * result
				+ ((updataId == null) ? 0 : updataId.hashCode());
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
		BmModelProcessLog other = (BmModelProcessLog) obj;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (modelId == null) {
			if (other.modelId != null)
				return false;
		} else if (!modelId.equals(other.modelId))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (optTime == null) {
			if (other.optTime != null)
				return false;
		} else if (!optTime.equals(other.optTime))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (personName == null) {
			if (other.personName != null)
				return false;
		} else if (!personName.equals(other.personName))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (stepCode == null) {
			if (other.stepCode != null)
				return false;
		} else if (!stepCode.equals(other.stepCode))
			return false;
		if (updataId == null) {
			if (other.updataId != null)
				return false;
		} else if (!updataId.equals(other.updataId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BmModelProcessLog [logId=" + logId + ", modelId=" + modelId
				+ ", updataId=" + updataId + ", stepCode=" + stepCode
				+ ", operation=" + operation + ", info=" + info + ", optTime="
				+ optTime + ", deptId=" + deptId + ", deptName=" + deptName
				+ ", personId=" + personId + ", personName=" + personName
				+ ", state=" + state + "]";
	}
	
	
	
}
