package com.huge.ihos.system.systemManager.organization.model;

import com.huge.model.BaseObject;
/**
 * t_monthperson的复合主键
 * @author Administrator
 *
 */
public class MonthPersonPk extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7678697794722376679L;
	private String checkperiod;//期间
	private String personId;//人员ID
	
	
	public String getCheckperiod() {
		return checkperiod;
	}
	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public MonthPersonPk() {
		super();
	}
	
	public MonthPersonPk(String checkperiod, String personId) {
		super();
		this.checkperiod = checkperiod;
		this.personId = personId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkperiod == null) ? 0 : checkperiod.hashCode());
		result = prime * result
				+ ((checkperiod == null) ? 0 : checkperiod.hashCode());
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
		MonthPersonPk other = (MonthPersonPk) obj;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (checkperiod == null) {
			if (other.checkperiod != null)
				return false;
		} else if (!checkperiod.equals(other.checkperiod))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MonthPersonPk [personId=" + personId + ", checkperiod=" + checkperiod + "]";
	}
}
