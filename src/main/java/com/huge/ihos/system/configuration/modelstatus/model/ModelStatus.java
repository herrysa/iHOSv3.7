package com.huge.ihos.system.configuration.modelstatus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;
@Entity
@Table( name = "sy_modelStatus")
public class ModelStatus extends BaseObject implements Serializable{
	//public static final String MODEL_HR = "HR";
	private static final long serialVersionUID = -3900358729135129424L;
	private String id;
	private String modelId;
	private String periodType;
	private String checkperiod;
	private String status;//0:初始化；1：正在使用；2：已结账
	private Integer checkNumber;//次数
	private String orgCode;
	private String copyCode;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="modelId",length=30)
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Column(name="periodType",length=10)
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	@Column(name="checkperiod",length=30)
	public String getCheckperiod() {
		return checkperiod;
	}

	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}

	@Column(name="status",length=10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="checkNumber",nullable=true)
	public Integer getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(Integer checkNumber) {
		this.checkNumber = checkNumber;
	}
	
	@Column
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	
	@Override
	public String toString() {
		return "ModelStatus [id=" + id + ", modelId=" + modelId
				+ ", checkperiod=" + checkperiod + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkperiod == null) ? 0 : checkperiod.hashCode());
		result = prime * result + ((modelId == null) ? 0 : modelId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ModelStatus other = (ModelStatus) obj;
		if (checkperiod == null) {
			if (other.checkperiod != null)
				return false;
		} else if (!checkperiod.equals(other.checkperiod))
			return false;
		if (modelId == null) {
			if (other.modelId != null)
				return false;
		} else if (!modelId.equals(other.modelId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
