package com.huge.ihos.kq.kqHoliday.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
@Entity
@Table(name="kq_holiday")
public class KqHoliday extends BaseObject {
	private String holidayId; //ID
	private String holidayCode; //节日编码
	private String holidayName; //节日名称
	private Date beginDate; //开始时间
	private Date endDate; //结束时间
	private Double dayNumber; //每天数量
	private String requirement; //休假条件
	private String holidayDesc; //节日说明
	private String currentYear; //当前年
	
	@Id
	@Column(name="holidayId",length=32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator="uuid")
	public String getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}
	@Column(name="holidayCode",nullable=false,length=50)
	public String getHolidayCode() {
		return holidayCode;
	}
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	@Column(name="holidayName",nullable=false,length=50)
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	@Column(name="beginDate",length=40)
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@Column(name="endDate",length=40)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(name="dayNumber")
	public Double getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(Double dayNumber) {
		this.dayNumber = dayNumber;
	}
	@Column(name="requirement",length=200)
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	@Column(name="holidayDesc",length=150)
	public String getHolidayDesc() {
		return holidayDesc;
	}
	public void setHolidayDesc(String holidayDesc) {
		this.holidayDesc = holidayDesc;
	}
	@Column(name="currentYear",length=20)
	public String getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((currentYear == null) ? 0 : currentYear.hashCode());
		result = prime * result + ((dayNumber == null) ? 0 : dayNumber.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((holidayCode == null) ? 0 : holidayCode.hashCode());
		result = prime * result + ((holidayDesc == null) ? 0 : holidayDesc.hashCode());
		result = prime * result + ((holidayId == null) ? 0 : holidayId.hashCode());
		result = prime * result + ((holidayName == null) ? 0 : holidayName.hashCode());
		result = prime * result + ((requirement == null) ? 0 : requirement.hashCode());
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
		KqHoliday other = (KqHoliday) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (currentYear == null) {
			if (other.currentYear != null)
				return false;
		} else if (!currentYear.equals(other.currentYear))
			return false;
		if (dayNumber == null) {
			if (other.dayNumber != null)
				return false;
		} else if (!dayNumber.equals(other.dayNumber))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (holidayCode == null) {
			if (other.holidayCode != null)
				return false;
		} else if (!holidayCode.equals(other.holidayCode))
			return false;
		if (holidayDesc == null) {
			if (other.holidayDesc != null)
				return false;
		} else if (!holidayDesc.equals(other.holidayDesc))
			return false;
		if (holidayId == null) {
			if (other.holidayId != null)
				return false;
		} else if (!holidayId.equals(other.holidayId))
			return false;
		if (holidayName == null) {
			if (other.holidayName != null)
				return false;
		} else if (!holidayName.equals(other.holidayName))
			return false;
		if (requirement == null) {
			if (other.requirement != null)
				return false;
		} else if (!requirement.equals(other.requirement))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "KqHoliday [holidayId=" + holidayId + ", holidayCode=" + holidayCode + ", holidayName=" + holidayName + ", beginDate=" + beginDate + ", endDate=" + endDate + ", dayNumber=" + dayNumber + ", requirement=" + requirement + ", holidayDesc=" + holidayDesc + ", currentYear=" + currentYear + "]";
	}
	public KqHoliday() {
		super();
	}
	
}
