package com.huge.ihos.system.reportManager.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_report_ds")
public class ReportDataSource extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6578284404803284908L;
	private String code;
	private String name;
	private String reportId;
	private String dataSource;
	private String dsDesc;
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
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	@Column
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	@Column
	public String getDsDesc() {
		return dsDesc;
	}
	public void setDsDesc(String dsDesc) {
		this.dsDesc = dsDesc;
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
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((dataSource == null) ? 0 : dataSource.hashCode());
		result = prime * result + ((dsDesc == null) ? 0 : dsDesc.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((reportId == null) ? 0 : reportId.hashCode());
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
		ReportDataSource other = (ReportDataSource) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (dataSource == null) {
			if (other.dataSource != null)
				return false;
		} else if (!dataSource.equals(other.dataSource))
			return false;
		if (dsDesc == null) {
			if (other.dsDesc != null)
				return false;
		} else if (!dsDesc.equals(other.dsDesc))
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
		if (reportId == null) {
			if (other.reportId != null)
				return false;
		} else if (!reportId.equals(other.reportId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ReportDataSource [code=" + code + ", name=" + name
				+ ", reportId=" + reportId + ", dataSource=" + dataSource
				+ ", dsDesc=" + dsDesc + ", remark=" + remark + "]";
	}
	
}
