package com.huge.ihos.gz.gzType.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "gz_gzType")
public class GzType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8607026205420763450L;

	private String gzTypeId; //工资类别id
	
	private String gzTypeName; //类别名称
	
	private Integer issueNumber;  //新类别开始发放次数
	
	private String issueDate ;  //新类别开始发放年月
	
	private Boolean status = false;  //当前工资类别
	
	private String gzUserIds;//将该类别作为当前类别的人员Id
	
	private String remark;  //备注
	
	private Boolean disabled = false; //工资类别是否禁用
	
	private String issueType;//0月，1次	
	
	
	@Id
	@Column(name = "gzTypeId", length = 32)
	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
	}
	@Column(name = "gzTypeName", length = 50,nullable = false)
	public String getGzTypeName() {
		return gzTypeName;
	}

	public void setGzTypeName(String gzTypeName) {
		this.gzTypeName = gzTypeName;
	}
	
	@Column(name = "issueType", length = 20,nullable = false)
	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	
	@Column(name = "issueNumber" )
	public Integer getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(Integer issueNumber) {
		this.issueNumber = issueNumber;
	}
	@Column(name = "issueDate" ,length = 6)
	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	@Column(name = "remark",length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
	@Column(name = "disabled",nullable = false)
	public Boolean getDisabled() {
		return disabled;
	}


	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	} 
	
	@Column(name = "gzUserIds",length = 4000)
	public String getGzUserIds() {
		return gzUserIds;
	}

	public void setGzUserIds(String gzUserIds) {
		this.gzUserIds = gzUserIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((gzTypeId == null) ? 0 : gzTypeId.hashCode());
		result = prime * result
				+ ((gzTypeName == null) ? 0 : gzTypeName.hashCode());
		result = prime * result
				+ ((issueDate == null) ? 0 : issueDate.hashCode());
		result = prime * result + issueNumber;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		GzType other = (GzType) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (gzTypeId == null) {
			if (other.gzTypeId != null)
				return false;
		} else if (!gzTypeId.equals(other.gzTypeId))
			return false;
		if (gzTypeName == null) {
			if (other.gzTypeName != null)
				return false;
		} else if (!gzTypeName.equals(other.gzTypeName))
			return false;
		if (issueDate == null) {
			if (other.issueDate != null)
				return false;
		} else if (!issueDate.equals(other.issueDate))
			return false;
		if (issueNumber != other.issueNumber)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GzType [gzTypeId=" + gzTypeId + ", gzTypeName=" + gzTypeName
				+ ", issueNumber=" + issueNumber + ", issueDate=" + issueDate
				+ ", status=" + status + ", remark=" + remark + ", disabled="
				+ disabled + "]";
	}
}
