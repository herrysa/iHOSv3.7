package com.huge.ihos.system.reportManager.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;


@Entity
@Table(name = "sy_userReportDefine")
public class UserReportDefine extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1817315147834431170L;
	private String defineId;
	private String userId;
	private String defineClass;
	private String reportXml;
	
	@Id
	@Column(name = "defineId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getDefineId() {
		return defineId;
	}
	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}
	
	@Column
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column
	public String getDefineClass() {
		return defineClass;
	}
	public void setDefineClass(String defineClass) {
		this.defineClass = defineClass;
	}
	
	@Column
	public String getReportXml() {
		return reportXml;
	}
	public void setReportXml(String reportXml) {
		this.reportXml = reportXml;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((defineClass == null) ? 0 : defineClass.hashCode());
		result = prime * result
				+ ((defineId == null) ? 0 : defineId.hashCode());
		result = prime * result
				+ ((reportXml == null) ? 0 : reportXml.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserReportDefine other = (UserReportDefine) obj;
		if (defineClass == null) {
			if (other.defineClass != null)
				return false;
		} else if (!defineClass.equals(other.defineClass))
			return false;
		if (defineId == null) {
			if (other.defineId != null)
				return false;
		} else if (!defineId.equals(other.defineId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserReportDefine [defineId=" + defineId + ", userId=" + userId
				+ ", defineClass=" + defineClass + "]";
	}
	
	
}
