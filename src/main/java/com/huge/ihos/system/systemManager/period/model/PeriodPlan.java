package com.huge.ihos.system.systemManager.period.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "sy_periodPlan" )
public class PeriodPlan extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String planId;
	private String planName;
	private String remark;
	private Boolean isDefault;
	
	private Set<PeriodYear> periodYearSet = new HashSet<PeriodYear>();
	
	@Id
	@Column(length = 32,name="planId")
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	@Column(length = 50,name="planName")
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	@Column(length = 200,name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "plan" )
	@OrderBy("periodYearCode asc")
	public Set<PeriodYear> getPeriodYearSet() {
		return periodYearSet;
	}
	public void setPeriodYearSet(Set<PeriodYear> periodYearSet) {
		this.periodYearSet = periodYearSet;
	}
	
	@Column(name="isDefault")
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
		result = prime * result
				+ ((planName == null) ? 0 : planName.hashCode());
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
		PeriodPlan other = (PeriodPlan) obj;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
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
		return true;
	}
	@Override
	public String toString() {
		return "PeriodPlan [planId=" + planId + ", planName=" + planName
				+ ", remark=" + remark + "]";
	}
	
	
}
