package com.huge.ihos.system.reportManager.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_define_report")
public class DefineReport extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8745413276729792588L;
	private String code;
	private String name;
	private String type;
	private String searchName;
	private Boolean isSys;
	private String report;
	private String reportData;
	private String remark;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column
	public Boolean getIsSys() {
		return isSys;
	}
	public void setIsSys(Boolean isSys) {
		this.isSys = isSys;
	}
	
	@Column
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	
	@Column
	public String getReportData() {
		return reportData;
	}
	public void setReportData(String reportData) {
		this.reportData = reportData;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		DefineReport other = (DefineReport) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DefineReport [code=" + code + ", name=" + name + ", type="
				+ type + ", remark=" + remark + "]";
	}
	
	
}
