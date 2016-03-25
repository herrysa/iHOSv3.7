package com.huge.ihos.system.configuration.funcDefine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_func_define")
public class FuncDefine extends BaseObject {
	
	private static final long serialVersionUID = -6414166444760517678L;
	//ID主键
	private String funcId;
	//函数编码
	private String funcCode;
	//函数名称
	private String funcName;
	//备注
	private String remark;
	//函数类型（0:表内函数;1:表间函数）
	private Integer funcType;
	//参数
	private String funcParam;
	//函数体
	private String funcBody;
	//函数描述
	private String funcDesc;
	//状态（true:系统函数（不可更改，删除）;false:自定义函数）
	private Boolean isSystemFunc;
	
	//private Boolean prepareExe;
	@Id
	@Column(name = "func_id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	@Column(name="func_code",length=50)
	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	
	@Column(name="func_name",length=50)
	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	
	@Column(name="remark",length=100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="func_type",length=20)
	public Integer getFuncType() {
		return funcType;
	}

	public void setFuncType(Integer funcType) {
		this.funcType = funcType;
	}

	@Column(name="func_param",length=100)
	public String getFuncParam() {
		return funcParam;
	}

	public void setFuncParam(String funcParam) {
		this.funcParam = funcParam;
	}
	@Column(name="func_body")
	public String getFuncBody() {
		return funcBody;
	}

	public void setFuncBody(String funcBody) {
		this.funcBody = funcBody;
	}
	@Column(name="func_desc")
	public String getFuncDesc() {
		return funcDesc;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}
	@Column(name="func_issystemfunc")
	public Boolean getIsSystemFunc() {
		return isSystemFunc;
	}

	public void setIsSystemFunc(Boolean isSystemFunc) {
		this.isSystemFunc = isSystemFunc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcBody == null) ? 0 : funcBody.hashCode());
		result = prime * result + ((funcCode == null) ? 0 : funcCode.hashCode());
		result = prime * result + ((funcDesc == null) ? 0 : funcDesc.hashCode());
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((funcParam == null) ? 0 : funcParam.hashCode());
		result = prime * result + ((funcType == null) ? 0 : funcType.hashCode());
		result = prime * result + ((isSystemFunc == null) ? 0 : isSystemFunc.hashCode());
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
		FuncDefine other = (FuncDefine) obj;
		if (funcBody == null) {
			if (other.funcBody != null)
				return false;
		} else if (!funcBody.equals(other.funcBody))
			return false;
		if (funcCode == null) {
			if (other.funcCode != null)
				return false;
		} else if (!funcCode.equals(other.funcCode))
			return false;
		if (funcDesc == null) {
			if (other.funcDesc != null)
				return false;
		} else if (!funcDesc.equals(other.funcDesc))
			return false;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (funcParam == null) {
			if (other.funcParam != null)
				return false;
		} else if (!funcParam.equals(other.funcParam))
			return false;
		if (funcType == null) {
			if (other.funcType != null)
				return false;
		} else if (!funcType.equals(other.funcType))
			return false;
		if (isSystemFunc == null) {
			if (other.isSystemFunc != null)
				return false;
		} else if (!isSystemFunc.equals(other.isSystemFunc))
			return false;
		return true;
	}

	public FuncDefine() {
		super();
		this.isSystemFunc = false;
	}

	@Override
	public String toString() {
		return "FuncDefine [funcId=" + funcId + ", funcCode=" + funcCode + ", funcName=" + remark + ", funcType=" + funcType + ", funcParam=" + funcParam + ", funcBody=" + funcBody + ", funcDesc=" + funcDesc + ", isSystemFunc=" + isSystemFunc + "]";
	}
	
}
