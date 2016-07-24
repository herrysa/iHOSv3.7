package com.huge.ihos.bm.budgetModel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_model_process" )
public class BmModelProcess extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3011774363359410973L;

	private String bmProcessId;
	
	private BudgetModel budgetModel;
	private String checkDeptId;
	private String checkDeptName;
	private String checkPersonId;
	private String checkPersonName;
	
	private String stepCode;
	private String stepName;
	private Integer state;
	private BmModelProcess okStep;
	private BmModelProcess noStep;
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
	@Column(name = "bmProcessId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getBmProcessId() {
		return bmProcessId;
	}
	public void setBmProcessId(String bmProcessId) {
		this.bmProcessId = bmProcessId;
	}
	
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
	
	@Column
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="okStepId")
	public BmModelProcess getOkStep() {
		return okStep;
	}
	public void setOkStep(BmModelProcess okStep) {
		this.okStep = okStep;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="noStepId")
	public BmModelProcess getNoStep() {
		return noStep;
	}
	public void setNoStep(BmModelProcess noStep) {
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modelId")
	public BudgetModel getBudgetModel() {
		return budgetModel;
	}
	public void setBudgetModel(BudgetModel budgetModel) {
		this.budgetModel = budgetModel;
	}
	
	@Column(name = "checkDeptId",length=100)
	public String getCheckDeptId() {
		return checkDeptId;
	}
	public void setCheckDeptId(String checkDeptId) {
		this.checkDeptId = checkDeptId;
	}
	
	@Column(name = "checkDeptName",length=100)
	public String getCheckDeptName() {
		return checkDeptName;
	}
	public void setCheckDeptName(String checkDeptName) {
		this.checkDeptName = checkDeptName;
	}
	
	@Column(name = "checkPersonId",length=100)
	public String getCheckPersonId() {
		return checkPersonId;
	}
	public void setCheckPersonId(String checkPersonId) {
		this.checkPersonId = checkPersonId;
	}
	
	@Column(name = "checkPersonName",length=100)
	public String getCheckPersonName() {
		return checkPersonName;
	}
	public void setCheckPersonName(String checkPersonName) {
		this.checkPersonName = checkPersonName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bmProcessId == null) ? 0 : bmProcessId.hashCode());
		result = prime * result
				+ ((budgetModel == null) ? 0 : budgetModel.hashCode());
		result = prime * result
				+ ((checkDeptId == null) ? 0 : checkDeptId.hashCode());
		result = prime * result
				+ ((checkDeptName == null) ? 0 : checkDeptName.hashCode());
		result = prime * result
				+ ((checkPersonId == null) ? 0 : checkPersonId.hashCode());
		result = prime * result
				+ ((checkPersonName == null) ? 0 : checkPersonName.hashCode());
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((isEnd == null) ? 0 : isEnd.hashCode());
		result = prime * result + ((noName == null) ? 0 : noName.hashCode());
		result = prime * result + ((okName == null) ? 0 : okName.hashCode());
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
		BmModelProcess other = (BmModelProcess) obj;
		if (bmProcessId == null) {
			if (other.bmProcessId != null)
				return false;
		} else if (!bmProcessId.equals(other.bmProcessId))
			return false;
		if (budgetModel == null) {
			if (other.budgetModel != null)
				return false;
		} else if (!budgetModel.equals(other.budgetModel))
			return false;
		if (checkDeptId == null) {
			if (other.checkDeptId != null)
				return false;
		} else if (!checkDeptId.equals(other.checkDeptId))
			return false;
		if (checkDeptName == null) {
			if (other.checkDeptName != null)
				return false;
		} else if (!checkDeptName.equals(other.checkDeptName))
			return false;
		if (checkPersonId == null) {
			if (other.checkPersonId != null)
				return false;
		} else if (!checkPersonId.equals(other.checkPersonId))
			return false;
		if (checkPersonName == null) {
			if (other.checkPersonName != null)
				return false;
		} else if (!checkPersonName.equals(other.checkPersonName))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (isEnd == null) {
			if (other.isEnd != null)
				return false;
		} else if (!isEnd.equals(other.isEnd))
			return false;
		if (noName == null) {
			if (other.noName != null)
				return false;
		} else if (!noName.equals(other.noName))
			return false;
		if (okName == null) {
			if (other.okName != null)
				return false;
		} else if (!okName.equals(other.okName))
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
		return "BmModelProcess [bmProcessId=" + bmProcessId + ", budgetModel="
				+ budgetModel + ", checkDeptId=" + checkDeptId
				+ ", checkDeptName=" + checkDeptName + ", checkPersonId="
				+ checkPersonId + ", checkPersonName=" + checkPersonName
				+ ", stepCode=" + stepCode + ", stepName=" + stepName
				+ ", state=" + state + ", okName=" + okName + ", noName=" + noName
				+ ", stepInfo=" + stepInfo + ", unionCheck=" + unionCheck
				+ ", isEnd=" + isEnd + ", condition=" + condition + ", remark="
				+ remark + ", roleId=" + roleId + ", roleName=" + roleName
				+ "]";
	}
	
	
	
}
