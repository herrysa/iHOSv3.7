package com.huge.ihos.nursescore.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;

@Entity
@Table( name = "jj_t_monthNurseList")
public class MonthNurse {
	
	private Long id;
	private String checkperiod;
	private Person personid ;
	private String code;
	private String name = "";
	private Department ownerdeptid;
	private String ownerdept;
	private NurseYearRate yearcode;
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="checkperiod")
	public String getCheckperiod() {
		return checkperiod;
	}
	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="ownerdeptid")
	public Department getOwnerdeptid() {
		return ownerdeptid;
	}
	public void setOwnerdeptid(Department ownerdeptid) {
		this.ownerdeptid = ownerdeptid;
	}
	@Column(name="ownerdept")
	public String getOwnerdept() {
		return ownerdept;
	}
	public void setOwnerdept(String ownerdept) {
		this.ownerdept = ownerdept;
	}
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="personid")
	public Person getPersonid() {
		return personid;
	}
	public void setPersonid(Person personid) {
		this.personid = personid;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name="yearcode")
	public NurseYearRate getYearcode() {
		return yearcode;
	}
	public void setYearcode(NurseYearRate yearcode) {
		this.yearcode = yearcode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkperiod == null) ? 0 : checkperiod.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((ownerdept == null) ? 0 : ownerdept.hashCode());
		result = prime * result
				+ ((ownerdeptid == null) ? 0 : ownerdeptid.hashCode());
		result = prime * result
				+ ((personid == null) ? 0 : personid.hashCode());
		result = prime * result
				+ ((yearcode == null) ? 0 : yearcode.hashCode());
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
		MonthNurse other = (MonthNurse) obj;
		if (checkperiod == null) {
			if (other.checkperiod != null)
				return false;
		} else if (!checkperiod.equals(other.checkperiod))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownerdept == null) {
			if (other.ownerdept != null)
				return false;
		} else if (!ownerdept.equals(other.ownerdept))
			return false;
		if (ownerdeptid == null) {
			if (other.ownerdeptid != null)
				return false;
		} else if (!ownerdeptid.equals(other.ownerdeptid))
			return false;
		if (personid == null) {
			if (other.personid != null)
				return false;
		} else if (!personid.equals(other.personid))
			return false;
		if (yearcode == null) {
			if (other.yearcode != null)
				return false;
		} else if (!yearcode.equals(other.yearcode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MonthNurse [id=" + id + ", checkperiod=" + checkperiod
				+ ", personid=" + personid + ", code=" + code + ", name="
				+ name + ", ownerdeptid=" + ownerdeptid + ", ownerdept="
				+ ownerdept + ", yearcode=" + yearcode + "]";
	}
	
	
	
}