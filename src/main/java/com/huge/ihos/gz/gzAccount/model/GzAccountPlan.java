package com.huge.ihos.gz.gzAccount.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "gz_account_plan")
public class GzAccountPlan extends BaseObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4174455441370665122L;

	private String planId;

	private String planName;

	private String toPublic;

	private String toDepartment;

	private String toRole;

	private String userId;

	private String defineId;
	
	private String gzTypeId;//工资类别
	
	private String customLayout;
	
	private Boolean sysField = false;//系统项
	
	private String reportTitle;//报表标题
    
	@Id
	@Column(name = "planId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Column(name = "planName", length = 100)
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}
	@Column(name = "reportTitle", length = 100)
	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	@Column(name = "toPublic", length = 1)
	public String getToPublic() {
		return toPublic;
	}

	public void setToPublic(String toPublic) {
		this.toPublic = toPublic;
	}
	@Column(name = "toDepartment", length = 1)
	public String getToDepartment() {
		return toDepartment;
	}

	public void setToDepartment(String toDepartment) {
		this.toDepartment = toDepartment;
	}
	@Column(name = "toRole", length = 1)
	public String getToRole() {
		return toRole;
	}

	public void setToRole(String toRole) {
		this.toRole = toRole;
	}
	@Column(name = "userId", length = 20)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "defineId", length = 20)
	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}
	@Column(name = "gzTypeId", length = 32,nullable = false)
	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
	}
	
	@Column(name="custom_layout")
	public String getCustomLayout() {
		return customLayout;
	}

	public void setCustomLayout(String customLayout) {
		this.customLayout = customLayout;
	}
	@Column(name="sysField")
	public Boolean getSysField() {
		return sysField;
	}

	public void setSysField(Boolean sysField) {
		this.sysField = sysField;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((defineId == null) ? 0 : defineId.hashCode());
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
		result = prime * result
				+ ((planName == null) ? 0 : planName.hashCode());
		result = prime * result
				+ ((toDepartment == null) ? 0 : toDepartment.hashCode());
		result = prime * result
				+ ((toPublic == null) ? 0 : toPublic.hashCode());
		result = prime * result + ((toRole == null) ? 0 : toRole.hashCode());
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
		GzAccountPlan other = (GzAccountPlan) obj;
		if (defineId == null) {
			if (other.defineId != null)
				return false;
		} else if (!defineId.equals(other.defineId))
			return false;
		if (planId == null) {
			if (other.planId != null)
				return false;
		} else if (!planId.equals(other.planId))
			return false;
		if (planName == null) {
			if (other.planName != null)
				return false;
		} else if (!planName.equals(other.planName))
			return false;
		if (toDepartment == null) {
			if (other.toDepartment != null)
				return false;
		} else if (!toDepartment.equals(other.toDepartment))
			return false;
		if (toPublic == null) {
			if (other.toPublic != null)
				return false;
		} else if (!toPublic.equals(other.toPublic))
			return false;
		if (toRole == null) {
			if (other.toRole != null)
				return false;
		} else if (!toRole.equals(other.toRole))
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
		return "GzAccountPlan [planId=" + planId + ", planName=" + planName
				+ ", toPublic=" + toPublic + ", toDepartment=" + toDepartment
				+ ", toRole=" + toRole + ", userId=" + userId + ", defineId="
				+ defineId + "]";
	}
}
