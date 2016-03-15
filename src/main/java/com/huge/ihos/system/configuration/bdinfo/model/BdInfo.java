package com.huge.ihos.system.configuration.bdinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table( name = "t_bdinfo" )
public class BdInfo extends BaseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bdInfoId;//id
	private String bdInfoName;//表中文名称
	private String tableName;//表名
	private String entityName;//实体
	private String tableAlias;//表别名
	private String tableType = "0";//表类型
	private String subSystem;//子系统
	private Boolean treeStructure = false;//树形结构
	private String parentTable;//父表
	private String typeTable;//分类表
	//2014-12-07 add for hrSubSet
	private Integer sn;					// 表序号
	private String remark;                 //备注
	private Person changer;              //修改人
	private Date changeDate;                 //修改时间
	private Boolean disabled = false;;//停用
	private Boolean sysField = false;;//是否系统字段
	private Boolean statistics = false;;//统计
	private String mainTable;//主表
	private Boolean statisticsSingle = false;;//单项统计
	private String orderByField;//排序字段
	private Boolean orderByFieldAsc = false;//升序排列
	
	private Set<FieldInfo> fieldInfos;
	
	@Id
	@Column(length = 50,name="bdInfoId",nullable = false)
	public String getBdInfoId() {
		return bdInfoId;
	}
	public void setBdInfoId(String bdInfoId) {
		this.bdInfoId = bdInfoId;
	}
	
	@Column(length = 50,name="bdInfoName",nullable = false)
	public String getBdInfoName() {
		return bdInfoName;
	}
	public void setBdInfoName(String bdInfoName) {
		this.bdInfoName = bdInfoName;
	}
	
	@Column(length = 50,name="tableName",nullable = false)
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Column(length = 100,name="entityName", nullable = true)
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "changer",nullable = true)
	public Person getChanger() {
		return changer;
	}

	public void setChanger(Person changer) {
		this.changer = changer;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "changeDate", length = 19, nullable = true)
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	@Column(name = "disabled", nullable = true)
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column(name = "sysField", nullable = true)
	public Boolean getSysField() {
		return sysField;
	}

	public void setSysField(Boolean sysField) {
		this.sysField = sysField;
	}
	@Column(name = "tableAlias", length = 20, nullable = true)
	public String getTableAlias() {
		return tableAlias;
	}
	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	@Column(name = "statisticsFlag", nullable = true)
	public Boolean getStatistics() {
		return statistics;
	}
	public void setStatistics(Boolean statistics) {
		this.statistics = statistics;
	}
	@Column(name = "mainTable", nullable = true,length=50)
	public String getMainTable() {
		return mainTable;
	}

	public void setMainTable(String mainTable) {
		this.mainTable = mainTable;
	}
	@Column(name = "statisticsSingle", nullable = true)
	public Boolean getStatisticsSingle() {
		return statisticsSingle;
	}

	public void setStatisticsSingle(Boolean statisticsSingle) {
		this.statisticsSingle = statisticsSingle;
	}
	
	@Column(name = "orderByField", nullable = true,length=50)
	public String getOrderByField() {
		return orderByField;
	}
	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}
	
	@Column(name = "orderByFieldAsc", nullable = true)
	public Boolean getOrderByFieldAsc() {
		return orderByFieldAsc;
	}
	public void setOrderByFieldAsc(Boolean orderByFieldAsc) {
		this.orderByFieldAsc = orderByFieldAsc;
	}
	@Column(name = "tableType", nullable = true,length=2)
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	@Column(name = "subSystem", nullable = true,length=50)
	public String getSubSystem() {
		return subSystem;
	}
	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}
	@Column(name = "treeStructure", nullable = true)
	public Boolean getTreeStructure() {
		return treeStructure;
	}
	public void setTreeStructure(Boolean treeStructure) {
		this.treeStructure = treeStructure;
	}
	@Column(name = "parentTable", nullable = true,length=20)
	public String getParentTable() {
		return parentTable;
	}
	public void setParentTable(String parentTable) {
		this.parentTable = parentTable;
	}
	@Column(name = "typeTable", nullable = true,length=20)
	public String getTypeTable() {
		return typeTable;
	}
	public void setTypeTable(String typeTable) {
		this.typeTable = typeTable;
	}
	
	@OneToMany( fetch = FetchType.LAZY ,mappedBy="bdInfo")
	public Set<FieldInfo> getFieldInfos() {
		return fieldInfos;
	}
	public void setFieldInfos(Set<FieldInfo> fieldInfos) {
		this.fieldInfos = fieldInfos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bdInfoId == null) ? 0 : bdInfoId.hashCode());
		result = prime * result
				+ ((bdInfoName == null) ? 0 : bdInfoName.hashCode());
		result = prime * result
				+ ((entityName == null) ? 0 : entityName.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
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
		BdInfo other = (BdInfo) obj;
		if (bdInfoId == null) {
			if (other.bdInfoId != null)
				return false;
		} else if (!bdInfoId.equals(other.bdInfoId))
			return false;
		if (bdInfoName == null) {
			if (other.bdInfoName != null)
				return false;
		} else if (!bdInfoName.equals(other.bdInfoName))
			return false;
		if (entityName == null) {
			if (other.entityName != null)
				return false;
		} else if (!entityName.equals(other.entityName))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BdInfo [bdInfoId=" + bdInfoId + ", bdInfoName=" + bdInfoName
				+ ", tableName=" + tableName + ", entityName=" + entityName +  "]";
	}
}
