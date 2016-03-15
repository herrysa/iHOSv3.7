package com.huge.ihos.hr.statistics.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.hr.sysTables.model.SysTableKind;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_statistics_detail")
public class StatisticsDetail extends BaseObject implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1264161945802024615L;

	private String code;					//主键
	
	private String name;						// 名称
	
	private Statistics parentCode;  //父类Code
	
	private BdInfo bdInfo;//表
	
	private Boolean subset = false;            //子集
	
	private SysTableKind subsetKind;//子集类型
	
	private Boolean tableOrResultset = false;			//数据集
	
	private String remark;                 //备注
	
	private String resultSetSql;  //统计分类
		
	private String deptFK;  //部门过滤关联
	
	private String shijianFK;//时间关联
	
	private String shijianName;//名称
	
	


	
	@Id
	@Column(name = "code", length = 50, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Column(name = "resultSetSql", length = 200, nullable = true)
	public String getresultSetSql() {
		return resultSetSql;
	}

	public void setresultSetSql(String resultSetSql) {
		this.resultSetSql = resultSetSql;
	}
	
	@Column(name = "name", length = 50, nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "subset", nullable = true,columnDefinition = "bit default 0")
	public Boolean getsubset() {
		return subset;
	}

	public void setsubset(Boolean subset) {
		this.subset = subset;
	}
	
	@Column(name = "tableOrResultset", nullable = true,columnDefinition = "bit default 0")
	public Boolean gettableOrResultset() {
		return tableOrResultset;
	}

	public void settableOrResultset(Boolean tableOrResultset) {
		this.tableOrResultset = tableOrResultset;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentCode",nullable = true)
	public Statistics getParentCode() {
		return parentCode;
	}

	public void setParentCode(Statistics parentCode) {
		this.parentCode = parentCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bdInfoId",nullable = true)
	public BdInfo getBdInfo() {
		return bdInfo;
	}

	public void setBdInfo(BdInfo bdInfo) {
		this.bdInfo = bdInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subsetKindId",nullable = true)
	public SysTableKind getSubsetKind() {
		return subsetKind;
	}

	public void setSubsetKind(SysTableKind subsetKind) {
		this.subsetKind = subsetKind;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((subset == null) ? 0 : subset.hashCode());
		result = prime * result + ((tableOrResultset == null) ? 0 : tableOrResultset.hashCode());
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
		StatisticsDetail other = (StatisticsDetail) obj;
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
		if (subset == null) {
			if (other.subset != null)
				return false;
		} else if (!subset.equals(other.subset))
			return false;
		if (tableOrResultset == null) {
			if (other.tableOrResultset != null)
				return false;
		} else if (!tableOrResultset.equals(other.tableOrResultset))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatisticsDetail [code=" + code + ", name=" + name + ", remark=" + remark + ", subset=" + subset + 
				", tableOrResultset=" + tableOrResultset + "]";
	}
	@Column(name = "deptFK", length = 50, nullable = true)
	public String getDeptFK() {
		return deptFK;
	}

	public void setDeptFK(String deptFK) {
		this.deptFK = deptFK;
	}
	@Column(name = "shijianFK", length = 50, nullable = true)
	public String getShijianFK() {
		return shijianFK;
	}

	public void setShijianFK(String shijianFK) {
		this.shijianFK = shijianFK;
	}
	@Column(name = "shijianName", length = 50, nullable = true)
	public String getShijianName() {
		return shijianName;
	}

	public void setShijianName(String shijianName) {
		this.shijianName = shijianName;
	}

}
