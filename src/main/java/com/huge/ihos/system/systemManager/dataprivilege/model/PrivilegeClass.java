package com.huge.ihos.system.systemManager.dataprivilege.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.model.BaseObject;

@Entity
@Table(name = "t_privilegeClass")
public class PrivilegeClass extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8654825908398433534L;
	private String classCode;
	private String className;
	private Integer sn;
	private Boolean disabled = false;
	private BdInfo bdInfo;
	private String filterSql = "";

	private PrivilegeClass parentClass;


	@Transient
	public String getFilterSql() {
		return filterSql;
	}

	public void setFilterSql(String filterSql) {
		this.filterSql = filterSql;
	}

	@Id
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Column(name = "className", nullable = false, length = 20)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "sn")
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	@Column(name = "disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bdInfoId")
	public BdInfo getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(BdInfo bdInfo) {
		this.bdInfo = bdInfo;
	}

	
	@ManyToOne
	@JoinColumn(name="parentId")
	public PrivilegeClass getParentClass() {
		return parentClass;
	}

	public void setParentClass(PrivilegeClass parentClass) {
		this.parentClass = parentClass;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classCode == null) ? 0 : classCode.hashCode());
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
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
		PrivilegeClass other = (PrivilegeClass) obj;
		if (classCode == null) {
			if (other.classCode != null)
				return false;
		} else if (!classCode.equals(other.classCode))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrivilegeClass [classCode=" + classCode + ", className=" + className + ", sn=" + sn + ", disabled=" + disabled + "]";
	}

}
