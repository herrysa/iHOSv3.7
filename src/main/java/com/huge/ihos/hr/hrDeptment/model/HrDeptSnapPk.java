package com.huge.ihos.hr.hrDeptment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
@Embeddable
public class HrDeptSnapPk implements Serializable{
	private static final long serialVersionUID = -4705411440480517852L;
	private String snapCode;
	private String deptId;
	@Column(name="snapCode",length=14,nullable=false)
	public String getSnapCode() {
		return snapCode;
	}
	public void setSnapCode(String snapCode) {
		this.snapCode = snapCode;
	}
	
	@Column(name="deptId",length=50,nullable=false)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Transient
	public String getHisSnapId(){
		return this.deptId+"_"+this.snapCode;
	}
	
	public HrDeptSnapPk() {
		super();
	}
	
	public HrDeptSnapPk(String deptId, String snapCode) {
		super();
		this.deptId = deptId;
		this.snapCode = snapCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptId == null) ? 0 : deptId.hashCode());
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
		HrDeptSnapPk other = (HrDeptSnapPk) obj;
		if (deptId == null) {
			if (other.deptId != null)
				return false;
		} else if (!deptId.equals(other.deptId))
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
		return "SnapPk [snapCode=" + snapCode + ", deptId=" + deptId + "]";
	}
}
