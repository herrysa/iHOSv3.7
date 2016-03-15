package com.huge.ihos.system.systemManager.period.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "sy_moudle_period" )
public class MoudlePeriod {
	private static final long serialVersionUID = 1L;
	private String moudlePeriodId;
	private String periodId;
	private String menuId;
	private String month;
	private String planId;
	private Boolean flag = false;	
	@Id
	@Column(name="moudlePeriodId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getMoudlePeriodId() {
		return moudlePeriodId;
	}
	public void setMoudlePeriodId(String moudlePeriodId) {
		this.moudlePeriodId = moudlePeriodId;
	}
	
	@Column(name="periodId",length=32)
	public String getPeriodId() {
		return periodId;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	@Column(name="menuId",length=32)
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	@Column(name="planId",length=32)
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Column(name="month",length=6)
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Column(name="flag")
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result
				+ ((moudlePeriodId == null) ? 0 : moudlePeriodId.hashCode());
		result = prime * result
				+ ((periodId == null) ? 0 : periodId.hashCode());
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
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
		MoudlePeriod other = (MoudlePeriod) obj;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (moudlePeriodId == null) {
			if (other.moudlePeriodId != null)
				return false;
		} else if (!moudlePeriodId.equals(other.moudlePeriodId))
			return false;
		if (periodId == null) {
			if (other.periodId != null)
				return false;
		} else if (!periodId.equals(other.periodId))
			return false;
		if (planId == null) {
			if (other.planId != null)
				return false;
		} else if (!planId.equals(other.planId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MoudlePeriod [moudlePeriodId=" + moudlePeriodId + ", flag="
				+ flag + ", periodId=" + periodId + ", menuId=" + menuId
				+ ", month=" + month + ", planId=" + planId + "]";
	}
	
}
