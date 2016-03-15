package com.huge.ihos.hr.query.model;

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

import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_query_commondetail")
public class QueryCommonDetail extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3937971371652168938L;

	private String id;                   //主键ID
				
	private Integer sn;					// 序号
	
	private String remark;                 //备注
		
	private Person changeUser;              //修改人
	
	private Date changeDate;                 //修改时间
				
	private Boolean disabled = false;			//停用
		
	private QueryCommon queryCommon;  //
	
	private String operation;   //运算符
	
	private FieldInfo fieldInfo; //查询字段
	
	private String name;//名称
	
	private String tableName;//指标表
    
    private String targetValue;   //查询值
	
	


	
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
	@Column(name = "name", length = 50, nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "queryCommon",nullable = false)
	public QueryCommon getQueryCommon() {
		return queryCommon;
	}

	public void setQueryCommon(QueryCommon queryCommon) {
		this.queryCommon = queryCommon;
	}

	@Column(name = "operation", length = 10, nullable = true)
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	@Column(name = "targetValue", length = 100, nullable = true)
	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		QueryCommonDetail other = (QueryCommonDetail) obj;
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
		return "QueryCommonDetail [sn=" + sn + 
				", remark=" + remark +  ", changeUser=" + changeUser + ", changeDate=" + changeDate + 
				", disabled=" + disabled + "]";
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldInfo",nullable = false)
	public FieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(FieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	@Column(name = "tableName", length = 50, nullable = true)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
