package com.huge.ihos.system.reportManager.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_report_function")
public class ReportFunction extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5641419812776288001L;
	private String code;
	private String name;
	private String category;
	private String rsType;
	private String dataType;
	private String params;
	private String funcSql;
	private String remark;
	
	private String subSystem;
	
	@Column(length=20)
	public String getSubSystem() {
		return subSystem;
	}
	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}
	@Id
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column
	public String getRsType() {
		return rsType;
	}
	public void setRsType(String rsType) {
		this.rsType = rsType;
	}
	
	@Column
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	@Column
	public String getFuncSql() {
		return funcSql;
	}
	public void setFuncSql(String funcSql) {
		this.funcSql = funcSql;
	}
	
	@Column
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
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((funcSql == null) ? 0 : funcSql.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((rsType == null) ? 0 : rsType.hashCode());
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
		ReportFunction other = (ReportFunction) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (funcSql == null) {
			if (other.funcSql != null)
				return false;
		} else if (!funcSql.equals(other.funcSql))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (rsType == null) {
			if (other.rsType != null)
				return false;
		} else if (!rsType.equals(other.rsType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ReportFunction [code=" + code + ", name=" + name
				+ ", category=" + category + ", rsType=" + rsType
				+ ", dataType=" + dataType + ", params=" + params
				+ ", funcSql=" + funcSql + ", remark=" + remark + "]";
	}
	
}
