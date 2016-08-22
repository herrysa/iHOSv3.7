package com.huge.ihos.bm.budgetUpdata.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_updata" )
public class BudgetUpdata extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4132070077614782961L;
	private String updataId;
	private BudgetModelXf modelXfId;
	private Department department;
	private Person operator;
	private Date optDate;
	private Person submitter ;
	private Date submitDate ;
	private Person checker ;
	private Date checkDate ;
	private Integer state ;		//0:上报中;9:已过期
	private String periodYear;
	private String updataXml;
	private Integer deptType;
	
	@Column
	public Integer getDeptType() {
		return deptType;
	}

	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}

	private Map checkMap;
	
	@Transient
	public Map getCheckMap() {
		return checkMap;
	}

	public void setCheckMap(Map checkMap) {
		this.checkMap = checkMap;
	}

	@Id
	@Column(name = "updataId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getUpdataId() {
		return updataId;
	}

	public void setUpdataId(String updataId) {
		this.updataId = updataId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "modelXfId")
	public BudgetModelXf getModelXfId() {
		return modelXfId;
	}

	public void setModelXfId(BudgetModelXf modelXfId) {
		this.modelXfId = modelXfId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "deptId")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "operator")
	public Person getOperator() {
		return operator;
	}

	public void setOperator(Person operator) {
		this.operator = operator;
	}

	@Column(name = "optDate")
	public Date getOptDate() {
		return optDate;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "submitter")
	public Person getSubmitter() {
		return submitter;
	}

	public void setSubmitter(Person submitter) {
		this.submitter = submitter;
	}

	@Column(name = "submitDate")
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "checker")
	public Person getChecker() {
		return checker;
	}

	public void setChecker(Person checker) {
		this.checker = checker;
	}

	@Column(name = "checkDate")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "period_year",length=4)
	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}
	
	@Column
	public String getUpdataXml() {
		return updataXml;
	}

	public void setUpdataXml(String updataXml) {
		this.updataXml = updataXml;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((updataId == null) ? 0 : updataId.hashCode());
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
		BudgetUpdata other = (BudgetUpdata) obj;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (updataId == null) {
			if (other.updataId != null)
				return false;
		} else if (!updataId.equals(other.updataId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BudgetUpdata [updataId=" + updataId + ", state=" + state + ", periodYear=" + periodYear + "]";
	}

	

}
