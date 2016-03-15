package com.huge.ihos.hr.sysTables.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sys_hr_table_kind")
public class SysTableKind extends BaseObject implements Serializable{





	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2642470493000357644L;
	
	private String id;  //主键ID
	
	private String code;				// 表类型code
	
	private String name;					// 表类型名字
	
	private Integer sn;					// 表类型序号
	
	private String remark;						// 表类型描述

	private String mainTable;						// 主表
		
	private String mainTablePK;  //主表主键
	
	private Integer mainTablePKLength;//主表主键长度
	
	private Boolean disabled = false;//停用
	
	private Boolean sysFiled = false;//是否系统字段


	// id	
	@Id
	@Column(name = "kindId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "code", length = 30, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "sysFiled", nullable = true,columnDefinition = "bit default 0")
	public Boolean getSysFiled() {
		return sysFiled;
	}

	public void setSysFiled(Boolean sysFiled) {
		this.sysFiled = sysFiled;
	}	

	@Column(name = "sn",nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
	
	@Column(name = "name", nullable = true,length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "remark", nullable = true,length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "main_table", nullable = true,length=50)
	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}
	
	@Column(name = "main_table_pk", nullable = true,length=50)
	public String getMainTablePK() {
		return mainTablePK;
	}

	public void setMainTablePK(String mainTablePK) {
		this.mainTablePK = mainTablePK;
	}
	@Column(name = "main_table_pk_length", nullable = true)
	public Integer getMainTablePKLength() {
		return mainTablePKLength;
	}

	public void setMainTablePKLength(Integer mainTablePKLength) {
		this.mainTablePKLength = mainTablePKLength;
	}
	@Column(name = "disabled", nullable = true,columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((mainTable == null) ? 0 : mainTable.hashCode());
		result = prime * result + ((mainTablePK == null) ? 0 : mainTablePK.hashCode());
		result = prime * result + ((mainTablePKLength == null) ? 0 : mainTablePKLength.hashCode());
		result = prime * result + ((disabled == null) ? 0 : disabled.hashCode());
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
		SysTableKind other = (SysTableKind) obj;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (mainTable == null) {
			if (other.mainTable != null)
				return false;
		} else if (!mainTable.equals(other.mainTable))
			return false;
		if (mainTablePK == null) {
			if (other.mainTablePK != null)
				return false;
		} else if (!mainTablePK.equals(other.mainTablePK))
			return false;
		if (mainTablePKLength == null) {
			if (other.mainTablePKLength != null)
				return false;
		} else if (!mainTablePKLength.equals(other.mainTablePKLength))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysTableKind [sn=" +sn + ", code=" + code + ", name=" + name + ", remark=" + remark + ", mainTable=" + mainTable +
				", mainTablePK=" + mainTablePK +", mainTablePKLength=" + mainTablePKLength +", disabled=" + disabled +"]";
	}
}
