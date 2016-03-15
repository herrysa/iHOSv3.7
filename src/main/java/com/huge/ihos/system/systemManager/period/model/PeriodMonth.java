package com.huge.ihos.system.systemManager.period.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.model.BaseObject;
import com.huge.util.DateUtil;

@Entity
@Table( name = "sy_periodMonth" )
public class PeriodMonth extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String periodMonthId;
	private PeriodYear periodYear;
	//private PeriodPlan plan;
	private String periodMonthCode;
	private Integer month;
	private Date beginDate;
	private Date endDate;
	private String periodMonthInfo;
	
	@Id
	@Column(length = 6,name="periodMonthId")
	public String getPeriodMonthId() {
		return periodMonthId;
	}
	public void setPeriodMonthId(String periodMonthId) {
		this.periodMonthId = periodMonthId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="periodYearId")
	public PeriodYear getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(PeriodYear periodYear) {
		this.periodYear = periodYear;
	}
	
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="planId")
	public PeriodPlan getPlan() {
		return plan;
	}
	public void setPlan(PeriodPlan plan) {
		this.plan = plan;
	}*/
//	@Id
	@Column(length=6,name="periodMonthCode")
	public String getPeriodMonthCode() {
		return periodMonthCode;
	}
	public void setPeriodMonthCode(String periodMonthCode) {
		this.periodMonthCode = periodMonthCode;
	}
	@Column(name="month")
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
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
	
	@Transient
	public String getPeriodMonthInfo() {
		if("自定义".equals(month)){
			periodMonthInfo = "-1";
		}else{
			periodMonthInfo = month+"_"+DateUtil.convertDateToString("yyyy-MM-dd", beginDate)+"_"+DateUtil.convertDateToString("yyyy-MM-dd", endDate);
		}
		return periodMonthInfo;
	}
	public void setPeriodMonthInfo(String periodMonthInfo) {
		this.periodMonthInfo = periodMonthInfo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result
				+ ((periodMonthCode == null) ? 0 : periodMonthCode.hashCode());
		result = prime * result
				+ ((periodMonthInfo == null) ? 0 : periodMonthInfo.hashCode());
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
		PeriodMonth other = (PeriodMonth) obj;
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
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (periodMonthCode == null) {
			if (other.periodMonthCode != null)
				return false;
		} else if (!periodMonthCode.equals(other.periodMonthCode))
			return false;
		if (periodMonthInfo == null) {
			if (other.periodMonthInfo != null)
				return false;
		} else if (!periodMonthInfo.equals(other.periodMonthInfo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PeriodMonth [periodYear=" + periodYear + ", periodMonthCode="
				+ periodMonthCode + ", month=" + month + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", periodMonthInfo="
				+ periodMonthInfo + "]";
	}
	
	
}
