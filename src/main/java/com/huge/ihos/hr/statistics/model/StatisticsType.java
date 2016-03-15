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
@Table(name = "sy_statistics_type")
public class StatisticsType extends BaseObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6078134548637646489L;

	private String id;                   //主键ID
	
	private String code;					// 编码
	
	private String name;						// 名称
		
	private String remark;                 //备注
	
	private Boolean leaf = true;            //叶子
	
	private Person changeUser;              //修改人
	
	private Date changeDate;                 //修改时间
					
	private StatisticsType parentType;  //父类型
	
	private String statisticsCode;  //统计分类
	
	


	
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
	
	
	@Column(name = "statisticsCode", length = 50, nullable = true)
	public String getStatisticsCode() {
		return statisticsCode;
	}

	public void setStatisticsCode(String statisticsCode) {
		this.statisticsCode = statisticsCode;
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
	@Column(name = "remark", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "leaf", nullable = true,columnDefinition = "bit default 0")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
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

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentTypeId",nullable = true)
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
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((changeUser == null) ? 0 : changeUser.hashCode());
		result = prime * result + ((changeDate == null) ? 0 : changeDate.hashCode());		
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
		StatisticsType other = (StatisticsType) obj;
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
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
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
		return true;
	}

	@Override
	public String toString() {
		return "StatisticsType [code=" + code + ", name=" + name +  
				", remark=" + remark + ", leaf=" + leaf + ", changeUser=" + changeUser + ", changeDate=" + changeDate + 
				 "]";
	}
}
