package com.huge.ihos.system.configuration.businessprocess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.model.BaseObject;

@Entity
@Table( name = "sy_process_step" )
public class BusinessProcessStep extends BaseObject{

	private String stepCode;
	private String stepName;
	private Integer state;
	private BusinessProcess businessProcess;
	private BusinessProcessStep okStep;
	private BusinessProcessStep noStep;
	private String okName;
	private String noName;
	private Boolean stepInfo;
	private Boolean unionCheck;
	private Boolean isEnd;
	private String condition;
	private String remark;
	
	private String roleId;
	private String roleName;
	
	@Id
	@Column(length=20)
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	
	@Column(length=50)
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="processCode")
	public BusinessProcess getBusinessProcess() {
		return businessProcess;
	}
	public void setBusinessProcess(BusinessProcess businessProcess) {
		this.businessProcess = businessProcess;
	}
	
	@Column
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="okStepId")
	public BusinessProcessStep getOkStep() {
		return okStep;
	}
	public void setOkStep(BusinessProcessStep okStep) {
		this.okStep = okStep;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="noStepId")
	public BusinessProcessStep getNoStep() {
		return noStep;
	}
	public void setNoStep(BusinessProcessStep noStep) {
		this.noStep = noStep;
	}
	
	@Column(name="okName",length=20)
	public String getOkName() {
		return okName;
	}
	public void setOkName(String okName) {
		this.okName = okName;
	}
	
	@Column(name="noName",length=20)
	public String getNoName() {
		return noName;
	}
	public void setNoName(String noName) {
		this.noName = noName;
	}
	
	@Column(name="stepInfo")
	public Boolean getStepInfo() {
		return stepInfo;
	}
	public void setStepInfo(Boolean stepInfo) {
		this.stepInfo = stepInfo;
	}
	
	@Column
	public Boolean getUnionCheck() {
		return unionCheck;
	}
	public void setUnionCheck(Boolean unionCheck) {
		this.unionCheck = unionCheck;
	}
	
	@Column(length=100)
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	@Column(length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(length=100)
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Column(length=100)
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Column
	public Boolean getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((noName == null) ? 0 : noName.hashCode());
		result = prime * result + ((noStep == null) ? 0 : noStep.hashCode());
		result = prime * result + ((okName == null) ? 0 : okName.hashCode());
		result = prime * result + ((okStep == null) ? 0 : okStep.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result
				+ ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((stepCode == null) ? 0 : stepCode.hashCode());
		result = prime * result
				+ ((stepInfo == null) ? 0 : stepInfo.hashCode());
		result = prime * result
				+ ((stepName == null) ? 0 : stepName.hashCode());
		result = prime * result
				+ ((unionCheck == null) ? 0 : unionCheck.hashCode());
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
		BusinessProcessStep other = (BusinessProcessStep) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (noName == null) {
			if (other.noName != null)
				return false;
		} else if (!noName.equals(other.noName))
			return false;
		if (noStep == null) {
			if (other.noStep != null)
				return false;
		} else if (!noStep.equals(other.noStep))
			return false;
		if (okName == null) {
			if (other.okName != null)
				return false;
		} else if (!okName.equals(other.okName))
			return false;
		if (okStep == null) {
			if (other.okStep != null)
				return false;
		} else if (!okStep.equals(other.okStep))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
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
		if (stepInfo == null) {
			if (other.stepInfo != null)
				return false;
		} else if (!stepInfo.equals(other.stepInfo))
			return false;
		if (stepName == null) {
			if (other.stepName != null)
				return false;
		} else if (!stepName.equals(other.stepName))
			return false;
		if (unionCheck == null) {
			if (other.unionCheck != null)
				return false;
		} else if (!unionCheck.equals(other.unionCheck))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BusinessProcessStep [stepCode=" + stepCode + ", stepName="
				+ stepName + ", state=" + state + ", okStep=" + okStep
				+ ", noStep=" + noStep + ", okName=" + okName + ", noName="
				+ noName + ", stepInfo=" + stepInfo + ", unionCheck="
				+ unionCheck + ", condition=" + condition + ", remark="
				+ remark + ", roleId=" + roleId + ", roleName=" + roleName
				+ "]";
	}
	

	
	
}
