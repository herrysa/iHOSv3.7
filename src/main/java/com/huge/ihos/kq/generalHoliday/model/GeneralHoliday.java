package com.huge.ihos.kq.generalHoliday.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
@Entity
@Table(name="kq_generalholiday")
public class GeneralHoliday extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4063480841537662285L;
	private String holidayId; //ID
	private String dateCode; //日期序号
	private Double number; //公休数量
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
	@Column(name="dateCode",length=20)
	public String getDateCode() {
		return dateCode;
	}
	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}
	@Column(name="number")
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCode == null) ? 0 : dateCode.hashCode());
		result = prime * result + ((holidayId == null) ? 0 : holidayId.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		GeneralHoliday other = (GeneralHoliday) obj;
		if (dateCode == null) {
			if (other.dateCode != null)
				return false;
		} else if (!dateCode.equals(other.dateCode))
			return false;
		if (holidayId == null) {
			if (other.holidayId != null)
				return false;
		} else if (!holidayId.equals(other.holidayId))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GeneralHoliday [holidayId=" + holidayId + ", dateCode=" + dateCode + ", number=" + number + "]";
	}
	
	public GeneralHoliday() {
		super();
	}
}
