package com.huge.ihos.system.reportManager.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_report_plan_fliter")
public class ReportPlanFilter extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4398455868825850831L;

	private String filterId ;
	
	private String planId ;
	
	private String filterCode ;
	
	private String filterValue ;
    
    @Id
    @Column(name = "filterId", length = 32)
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
    @Column(name = "planId", length = 32)
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Column(name = "filterCode", length = 50)
	public String getFilterCode() {
		return filterCode;
	}

	public void setFilterCode(String filterCode) {
		this.filterCode = filterCode;
	}
	@Column(name = "filterValue", length = 4000)
	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filterCode == null) ? 0 : filterCode.hashCode());
		result = prime * result
				+ ((filterId == null) ? 0 : filterId.hashCode());
		result = prime * result
				+ ((filterValue == null) ? 0 : filterValue.hashCode());
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
		ReportPlanFilter other = (ReportPlanFilter) obj;
		if (filterCode == null) {
			if (other.filterCode != null)
				return false;
		} else if (!filterCode.equals(other.filterCode))
			return false;
		if (filterId == null) {
			if (other.filterId != null)
				return false;
		} else if (!filterId.equals(other.filterId))
			return false;
		if (filterValue == null) {
			if (other.filterValue != null)
				return false;
		} else if (!filterValue.equals(other.filterValue))
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
		return "ReportPlanFilter [filterId=" + filterId + ", planId="
				+ planId + ", filterCode=" + filterCode + ", filterValue="
				+ filterValue + "]";
	}
	
	

}
