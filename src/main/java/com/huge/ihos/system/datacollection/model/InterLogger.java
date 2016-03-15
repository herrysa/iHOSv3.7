package com.huge.ihos.system.datacollection.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.model.BaseObject;

@Entity
@Table(name = "t_interlog")
public class InterLogger extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8798353874267173097L;

	private Long logId;

	private String taskInterId;

	private Date logDateTime;

	private String logFrom;

	private String logMsg;

	private String periodCode;

	private boolean prompt;

	private String subSystemCode;//子系统代码

	private String operator;
	@Column(name="operator",length=20)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Transient
	public boolean isPrompt() {
		return prompt;
	}

	public void setPrompt(boolean prompt) {
		this.prompt = prompt;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "interlogId")
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	@Column(name = "periodCode", nullable = true, length = 50)
	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	@Column(name = "intertaskExecId", nullable = false, length = 50)
	public String getTaskInterId() {
		return taskInterId;
	}

	public void setTaskInterId(String taskInterId) {
		this.taskInterId = taskInterId;
	}

	@Column(name = "logDateTime", nullable = false)
	public Date getLogDateTime() {
		return logDateTime;
	}

	public void setLogDateTime(Date logDateTime) {
		this.logDateTime = logDateTime;
	}

	@Column(name = "step_name", nullable = false, length = 50)
	public String getLogFrom() {
		return logFrom;
	}

	public void setLogFrom(String logFrom) {
		this.logFrom = logFrom;
	}

	@Lob
	@Column(name = "logmessage", nullable = false, columnDefinition = "TEXT")
	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

	@Column(name = "subSystemCode", length = 10)
	public String getSubSystemCode() {
		return subSystemCode;
	}

	public void setSubSystemCode(String subSystemCode) {
		this.subSystemCode = subSystemCode;
	}

	@Override
	public String toString() {
		return "InterLog [logId=" + logId + ", taskInterId=" + taskInterId + ", logDateTime=" + logDateTime + ", logFrom=" + logFrom + ", logMsg=" + logMsg + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterLogger other = (InterLogger) obj;
		if (logDateTime == null) {
			if (other.logDateTime != null)
				return false;
		} else if (!logDateTime.equals(other.logDateTime))
			return false;
		if (logFrom == null) {
			if (other.logFrom != null)
				return false;
		} else if (!logFrom.equals(other.logFrom))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		if (logMsg == null) {
			if (other.logMsg != null)
				return false;
		} else if (!logMsg.equals(other.logMsg))
			return false;
		if (taskInterId == null) {
			if (other.taskInterId != null)
				return false;
		} else if (!taskInterId.equals(other.taskInterId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logDateTime == null) ? 0 : logDateTime.hashCode());
		result = prime * result + ((logFrom == null) ? 0 : logFrom.hashCode());
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
		result = prime * result + ((logMsg == null) ? 0 : logMsg.hashCode());
		result = prime * result + ((taskInterId == null) ? 0 : taskInterId.hashCode());
		return result;
	}
}
