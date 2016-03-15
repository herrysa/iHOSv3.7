package com.huge.ihos.hr.hrPerson.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HrPersonPK implements Serializable {
	private static final long serialVersionUID = 7600518359719164570L;
	private String snapCode;
	private String personId;

	@Column(name = "snapCode", length = 14, nullable = false)
	public String getSnapCode() {
		return snapCode;
	}

	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}

	@Column(name="personId",length=50,nullable=false)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public HrPersonPK() {
		super();
	}

	public HrPersonPK(String personId,String snapCode) {
		super();
		this.personId = personId;
		this.snapCode = snapCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
		result = prime * result
				+ ((snapCode == null) ? 0 : snapCode.hashCode());
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
		HrPersonPK other = (HrPersonPK) obj;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (snapCode == null) {
			if (other.snapCode != null)
				return false;
		} else if (!snapCode.equals(other.snapCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HrPersonPK [snapCode=" + snapCode + ", personId=" + personId
				+ "]";
	}

}
