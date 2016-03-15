package com.huge.ihos.kq.generalHoliday.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;
@Entity
@Table(name="kq_generalholiday_change")
public class GeneralHolidayChange extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -428285213883249054L;
	private String changeId; //ID
	private Date oldDate; //公休日期
	private Date newDate; //调整日期
	private String changeDesc; //调整说明
	
	@Id
	@Column(name="changeId",length=32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator="uuid")
	public String getChangeId() {
		return changeId;
	}
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}
	@Column(name="oldDate",nullable=false,length=40)
	public Date getOldDate() {
		return oldDate;
	}
	public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
	}
	@Column(name="newDate",nullable=false,length=40)
	public Date getNewDate() {
		return newDate;
	}
	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
	@Column(name="changeDesc",length=200)
	public String getChangeDesc() {
		return changeDesc;
	}
	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changeDesc == null) ? 0 : changeDesc.hashCode());
		result = prime * result + ((changeId == null) ? 0 : changeId.hashCode());
		result = prime * result + ((newDate == null) ? 0 : newDate.hashCode());
		result = prime * result + ((oldDate == null) ? 0 : oldDate.hashCode());
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
		GeneralHolidayChange other = (GeneralHolidayChange) obj;
		if (changeDesc == null) {
			if (other.changeDesc != null)
				return false;
		} else if (!changeDesc.equals(other.changeDesc))
			return false;
		if (changeId == null) {
			if (other.changeId != null)
				return false;
		} else if (!changeId.equals(other.changeId))
			return false;
		if (newDate == null) {
			if (other.newDate != null)
				return false;
		} else if (!newDate.equals(other.newDate))
			return false;
		if (oldDate == null) {
			if (other.oldDate != null)
				return false;
		} else if (!oldDate.equals(other.oldDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GeneralHolidayChange [changeId=" + changeId + ", oldDate=" + oldDate + ", newDate=" + newDate + ", changeDesc=" + changeDesc + "]";
	}
	
	public GeneralHolidayChange() {
		super();
	}
}
