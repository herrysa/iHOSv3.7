package com.huge.ihos.hr.statistics.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_statistics_item")
public class StatisticsItem extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7420421766273697870L;

	private String id;                   //主键ID
	
	private String code;					// 编码
	
	private String name;						// 名称
		
	private Integer sn;					// 序号
	
	private String remark;                 //备注
		
	private Person changeUser;              //修改人
	
	private Date changeDate;                 //修改时间
				
	private Boolean disabled = false;			//停用
	
	private Boolean sysFiled = false;			//系统
	
	private StatisticsType parentType;  //类型
	
	private String statisticsCode;  //统计分类
	
	private StatisticsDetail statisticsBdInfo;//统计表
	
	private String statisticsFieldInfo;//统计字段
	
	private Boolean statisticsValue = false;//统计明细
		
	private Boolean dynamicStatistics = false;//动态统计
	
	private String dynamicTable;//动态统计项表
	
	private String dynamicTablePk;//动态统计项表主键
	
	private String dynamicTableForeignKey;//统计表关联字段
	
	private String dynamicCode;//统计条件名称字段
	
	private String dynamicFilterSql;//动态统计过滤条件
	
	private String mccKeyId;//图形定义编号
	
	private String filterSql;//过滤条件
	
	private Boolean statisticsAuto = true;//自动统计

	private Boolean deptRequired = false;//部门必填
	
	@Id
	@Column(name = "id", length = 32, nullable = false)
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
	
	
	@Column(name = "name", length = 50, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "sn", nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "statisticsCode", length = 50, nullable = true)
	public String getStatisticsCode() {
		return statisticsCode;
	}

	public void setStatisticsCode(String statisticsCode) {
		this.statisticsCode = statisticsCode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId",nullable = true)
	public Person getChangeUser() {
		return changeUser;
	}

	public void setChangeUser(Person changeUser) {
		this.changeUser = changeUser;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "changeDate", length = 19, nullable = true)
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	
	@Column(name = "disabled", nullable = true,columnDefinition = "bit default 0")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column(name = "sysFiled", nullable = true,columnDefinition = "bit default 0")
	public Boolean getSysFiled() {
		return sysFiled;
	}

	public void setSysFiled(Boolean sysFiled) {
		this.sysFiled = sysFiled;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentTypeId",nullable = false)
	public StatisticsType getParentType() {
		return parentType;
	}

	public void setParentType(StatisticsType parentType) {
		this.parentType = parentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((changeUser == null) ? 0 : changeUser.hashCode());
		result = prime * result + ((changeDate == null) ? 0 : changeDate.hashCode());		
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
		StatisticsItem other = (StatisticsItem) obj;
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
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (changeUser == null) {
			if (other.changeUser != null)
				return false;
		} else if (!changeUser.equals(other.changeUser))
			return false;
		if (changeDate == null) {
			if (other.changeDate != null)
				return false;
		} else if (!changeDate.equals(other.changeDate))
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
		return "StatisticsItem [code=" + code + ", name=" + name + ", sn=" + sn + 
				", remark=" + remark +  ", changeUser=" + changeUser + ", changeDate=" + changeDate + 
				", disabled=" + disabled + "]";
	}

	@Column(name = "statisticsValue", nullable = true,columnDefinition = "bit default 0")
	public Boolean getStatisticsValue() {
		return statisticsValue;
	}

	public void setStatisticsValue(Boolean statisticsValue) {
		this.statisticsValue = statisticsValue;
	}

	@Column(name = "dynamicStatistics", nullable = true,columnDefinition = "bit default 0")
	public Boolean getDynamicStatistics() {
		return dynamicStatistics;
	}

	public void setDynamicStatistics(Boolean dynamicStatistics) {
		this.dynamicStatistics = dynamicStatistics;
	}

	@Column(name = "dynamicTable", length = 50, nullable = true)
	public String getDynamicTable() {
		return dynamicTable;
	}

	public void setDynamicTable(String dynamicTable) {
		this.dynamicTable = dynamicTable;
	}

	@Column(name = "dynamicTablePk", length = 50, nullable = true)
	public String getDynamicTablePk() {
		return dynamicTablePk;
	}

	public void setDynamicTablePk(String dynamicTablePk) {
		this.dynamicTablePk = dynamicTablePk;
	}

	@Column(name = "dynamicTableForeignKey", length = 50, nullable = true)
	public String getDynamicTableForeignKey() {
		return dynamicTableForeignKey;
	}

	public void setDynamicTableForeignKey(String dynamicTableForeignKey) {
		this.dynamicTableForeignKey = dynamicTableForeignKey;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "statisticsBdInfo",nullable = false)
	public StatisticsDetail getStatisticsBdInfo() {
		return statisticsBdInfo;
	}

	public void setStatisticsBdInfo(StatisticsDetail statisticsBdInfo) {
		this.statisticsBdInfo = statisticsBdInfo;
	}
	
	@Column(name = "statisticsFieldInfo", length = 50, nullable = true)
	public String getStatisticsFieldInfo() {
		return statisticsFieldInfo;
	}

	public void setStatisticsFieldInfo(String statisticsFieldInfo) {
		this.statisticsFieldInfo = statisticsFieldInfo;
	}
	@Column(name = "dynamicCode", length = 50, nullable = true)
	public String getDynamicCode() {
		return dynamicCode;
	}

	public void setDynamicCode(String dynamicCode) {
		this.dynamicCode = dynamicCode;
	}
	@Column(name = "dynamicFilterSql", length = 200, nullable = true)
	public String getDynamicFilterSql() {
		return dynamicFilterSql;
	}

	public void setDynamicFilterSql(String dynamicFilterSql) {
		this.dynamicFilterSql = dynamicFilterSql;
	}
	@Column(name = "mccKeyId", length = 10, nullable = true)
	public String getMccKeyId() {
		return mccKeyId;
	}

	public void setMccKeyId(String mccKeyId) {
		this.mccKeyId = mccKeyId;
	}
	@Column(name = "filterSql", length = 200, nullable = true)
	public String getFilterSql() {
		return filterSql;
	}

	public void setFilterSql(String filterSql) {
		this.filterSql = filterSql;
	}
	@Column(name = "statisticsAuto", nullable = true,columnDefinition = "bit default 1")
	public Boolean getStatisticsAuto() {
		return statisticsAuto;
	}

	public void setStatisticsAuto(Boolean statisticsAuto) {
		this.statisticsAuto = statisticsAuto;
	}
	@Column(name = "deptRequired", nullable = true,columnDefinition = "bit default 0")
	public Boolean getDeptRequired() {
		return deptRequired;
	}

	public void setDeptRequired(Boolean deptRequired) {
		this.deptRequired = deptRequired;
	}
}
