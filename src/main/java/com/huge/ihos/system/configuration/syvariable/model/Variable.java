package com.huge.ihos.system.configuration.syvariable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_variable")
public class Variable extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2199227916160099719L;
	// 变量名称，主键
	private String variableName;
	// 变量描述
	private String variableDesc;
	// sql表达式
	private String sqlExpression;
	// 备注
	private String remark;
	// 停用
	private Boolean disabled = false;
	// 系统
	private Boolean sys;
	// 序号
	private Integer sn;
	// 数据权限类别编码
	private PrivilegeClass privaleClass;
	// 变量类型（期间;组织结构;结账状态）
	private String variableType;
	// 过滤变量（1:登录单位;2:登录帐套;3:登录期间年）
	private String variableConstraint;
	// 权限类别（1:读;2:写）
	private String rightType;
	// 数据格式（1:逗号字符串;2:sql形式;3:数组）
	private String dataFormat;
	
	private String value;
	
	@Id
	@Column(name = "variableName", length = 32)
	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	@Column(name = "variableDesc", length = 200)
	public String getVariableDesc() {
		return variableDesc;
	}

	public void setVariableDesc(String variableDesc) {
		this.variableDesc = variableDesc;
	}

	@Column(name = "sqlExpression", length = 200)
	public String getSqlExpression() {
		return sqlExpression;
	}

	public void setSqlExpression(String sqlExpression) {
		this.sqlExpression = sqlExpression;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "sys")
	public Boolean getSys() {
		return sys;
	}

	public void setSys(Boolean sys) {
		this.sys = sys;
	}

	@Column(name = "sn", length = 10)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="classCode")
	public PrivilegeClass getPrivaleClass() {
		return privaleClass;
	}

	public void setPrivaleClass(PrivilegeClass privaleClass) {
		this.privaleClass = privaleClass;
	}

	@Column(name="variableType",length = 50)
	public String getVariableType() {
		return variableType;
	}

	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}

	@Column(name="variableConstraint")
	public String getVariableConstraint() {
		return variableConstraint;
	}
	
	public void setVariableConstraint(String variableConstraint) {
		this.variableConstraint = variableConstraint;
	}
	
	@Column(name="rightType")
	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	
	@Column(name="dataFormat")
	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	
	@Transient
	public String getValue() {
		Object varValueObject = UserContextUtil.findSysVariable(variableName);
		if(varValueObject!=null){
			value = varValueObject.toString();
		}
		return value ;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result
				+ ((sqlExpression == null) ? 0 : sqlExpression.hashCode());
		result = prime * result + ((sys == null) ? 0 : sys.hashCode());
		result = prime * result
				+ ((variableDesc == null) ? 0 : variableDesc.hashCode());
		result = prime * result
				+ ((variableName == null) ? 0 : variableName.hashCode());
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
		Variable other = (Variable) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (sqlExpression == null) {
			if (other.sqlExpression != null)
				return false;
		} else if (!sqlExpression.equals(other.sqlExpression))
			return false;
		if (sys == null) {
			if (other.sys != null)
				return false;
		} else if (!sys.equals(other.sys))
			return false;
		if (variableDesc == null) {
			if (other.variableDesc != null)
				return false;
		} else if (!variableDesc.equals(other.variableDesc))
			return false;
		if (variableName == null) {
			if (other.variableName != null)
				return false;
		} else if (!variableName.equals(other.variableName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SyVariable [variableName=" + variableName + ", variableDesc="
				+ variableDesc + ", sqlExpression=" + sqlExpression
				+ ", remark=" + remark + ", disabled=" + disabled + ", sys="
				+ sys + ", sn=" + sn + "]";
	}

	public Variable(String variableName, String variableDesc,
			String sqlExpression, String remark, Boolean disabled, Boolean sys,
			Integer sn) {
		super();
		this.variableName = variableName;
		this.variableDesc = variableDesc;
		this.sqlExpression = sqlExpression;
		this.remark = remark;
		this.disabled = disabled;
		this.sys = sys;
		this.sn = sn;
	}

	public Variable() {
		super();
	}

}
