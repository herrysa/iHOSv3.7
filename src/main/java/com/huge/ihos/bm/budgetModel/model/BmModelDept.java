package com.huge.ihos.bm.budgetModel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_model_dept" )
public class BmModelDept extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6247344269259559778L;
	private String bmDeptId;
	private BudgetModel bmModel;
	private Department bmDepartment;
	
	@Id
	@Column(name = "bmDeptId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getBmDeptId() {
		return bmDeptId;
	}
	public void setBmDeptId(String bmDeptId) {
		this.bmDeptId = bmDeptId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modelId")
	public BudgetModel getBmModel() {
		return bmModel;
	}
	public void setBmModel(BudgetModel bmModel) {
		this.bmModel = bmModel;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="deptId")
	public Department getBmDepartment() {
		return bmDepartment;
	}
	public void setBmDepartment(Department bmDepartment) {
		this.bmDepartment = bmDepartment;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bmDeptId == null) ? 0 : bmDeptId.hashCode());
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
		BmModelDept other = (BmModelDept) obj;
		if (bmDeptId == null) {
			if (other.bmDeptId != null)
				return false;
		} else if (!bmDeptId.equals(other.bmDeptId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BmModelDept [bmDeptId=" + bmDeptId + "]";
	}
	
	
	
}
