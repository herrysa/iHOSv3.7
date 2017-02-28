package com.huge.ihos.accounting.kjyear.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.model.BaseObject;

@Entity
@Table(name="GL_kjYear")
public class KjYear extends BaseObject {

	private String kjYearId;
	private String orgCode;
	private String copyCode;
	private String kjYear;
	private String startDate;
	private String endDate;
	private PeriodYear periodYear;
	private Boolean isClose;
	private Boolean isOpen;
	
	@Id
	@Column(name="kjYearId")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getKjYearId() {
		return kjYearId;
	}

	public void setKjYearId(String kjYearId) {
		this.kjYearId = kjYearId;
	}

	@Column(name="orgCode",length=20)
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Column(name="copyCode",length=20)
	public String getCopyCode() {
		return copyCode;
	}
	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name="kjYear",length=4)
	public String getKjYear() {
		return kjYear;
	}
	public void setKjYear(String kjYear) {
		this.kjYear = kjYear;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="periodYearId")
	public PeriodYear getPeriodSub() {
		return periodYear;
	}
	
	public void setPeriodSub(PeriodYear periodYear) {
		this.periodYear = periodYear;
	}

	@Column(name="isOpen")
	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	@Column(name="startDate",length=10)
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Column(name="endDate",length=10)
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Column(name="isClose")
	public Boolean getIsClose() {
		return isClose;
	}

	public void setIsClose(Boolean isClose) {
		this.isClose = isClose;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((isClose == null) ? 0 : isClose.hashCode());
		result = prime * result + ((isOpen == null) ? 0 : isOpen.hashCode());
		result = prime * result + ((kjYear == null) ? 0 : kjYear.hashCode());
		result = prime * result
				+ ((kjYearId == null) ? 0 : kjYearId.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		KjYear other = (KjYear) obj;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (isClose == null) {
			if (other.isClose != null)
				return false;
		} else if (!isClose.equals(other.isClose))
			return false;
		if (isOpen == null) {
			if (other.isOpen != null)
				return false;
		} else if (!isOpen.equals(other.isOpen))
			return false;
		if (kjYear == null) {
			if (other.kjYear != null)
				return false;
		} else if (!kjYear.equals(other.kjYear))
			return false;
		if (kjYearId == null) {
			if (other.kjYearId != null)
				return false;
		} else if (!kjYearId.equals(other.kjYearId))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KjYear [kjYearId=" + kjYearId + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", kjYear=" + kjYear
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", periodYear=" + periodYear + ", isClose=" + isClose
				+ ", isOpen=" + isOpen + "]";
	}
	

}
