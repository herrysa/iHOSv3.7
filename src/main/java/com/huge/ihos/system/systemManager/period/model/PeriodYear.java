package com.huge.ihos.system.systemManager.period.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.huge.model.BaseObject;

/**
 * @author Administrator
 *
 */
@Entity
@Table( name = "sy_periodYear" )
public class PeriodYear extends BaseObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private String periodYearId;
	private String periodYearCode;
	private int year;
	private Integer periodNum;
	private Date beginDate;
	private Date endDate;
	
	private PeriodPlan plan;
	private Set<PeriodMonth> periodMonthSet = new HashSet<PeriodMonth>();
	
	@Id
	@Column(length = 4,name="periodYearId")
	public String getPeriodYearId() {
		return periodYearId;
	}
	public void setPeriodYearId(String periodYearId) {
		this.periodYearId = periodYearId;
	}
//	@Id
	@Column(length = 4,name="periodYearCode")
	public String getPeriodYearCode() {
		return periodYearCode;
	}
	public void setPeriodYearCode(String periodYearCode) {
		this.periodYearCode = periodYearCode;
	}
	@Column(name="year")
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Column(name="periodNum")
	public Integer getPeriodNum() {
		return periodNum;
	}
	public void setPeriodNum(Integer periodNum) {
		this.periodNum = periodNum;
	}
	
	@Column(name="beginDate")
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@Column(name="endDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="planId")
	public PeriodPlan getPlan() {
		return plan;
	}
	public void setPlan(PeriodPlan plan) {
		this.plan = plan;
	}
	
	
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "periodYear" )
	public Set<PeriodMonth> getPeriodMonthSet() {
		return periodMonthSet;
	}
	public void setPeriodMonthSet(Set<PeriodMonth> periodMonthSet) {
		this.periodMonthSet = periodMonthSet;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((periodNum == null) ? 0 : periodNum.hashCode());
		result = prime * result
				+ ((periodYearCode == null) ? 0 : periodYearCode.hashCode());
		result = prime * result + year;
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
		PeriodYear other = (PeriodYear) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (periodNum == null) {
			if (other.periodNum != null)
				return false;
		} else if (!periodNum.equals(other.periodNum))
			return false;
		if (periodYearCode == null) {
			if (other.periodYearCode != null)
				return false;
		} else if (!periodYearCode.equals(other.periodYearCode))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PeriodYear [periodYearCode=" + periodYearCode + ", year="
				+ year + ", periodNum=" + periodNum + ", beginDate="
				+ beginDate + ", endDate=" + endDate + "]";
	}
	
}
