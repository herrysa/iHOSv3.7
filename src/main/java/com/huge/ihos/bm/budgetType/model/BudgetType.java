package com.huge.ihos.bm.budgetType.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_budgetType" )
public class BudgetType extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6614255313967181451L;
	private String budgetTypeCode;
	private String budgetTypeName;
	private BudgetType parentId;
	private Boolean leaf;
	
	private Boolean disabled;
	
	private String exceedBudgetType;
	private Integer warningPercent;
	
	@Id
	@Column(name="type_code")
	public String getBudgetTypeCode() {
		return budgetTypeCode;
	}
	public void setBudgetTypeCode(String budgetTypeCode) {
		this.budgetTypeCode = budgetTypeCode;
	}
	
	@Column(name="type_name")
	public String getBudgetTypeName() {
		return budgetTypeName;
	}
	public void setBudgetTypeName(String budgetTypeName) {
		this.budgetTypeName = budgetTypeName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public BudgetType getParentId() {
		return parentId;
	}
	public void setParentId(BudgetType parentId) {
		this.parentId = parentId;
	}
	
	@Column(name="exceed_budget_type")
	public String getExceedBudgetType() {
		return exceedBudgetType;
	}
	public void setExceedBudgetType(String exceedBudgetType) {
		this.exceedBudgetType = exceedBudgetType;
	}
	
	@Column(name="warning_percent")
	public Integer getWarningPercent() {
		return warningPercent;
	}
	public void setWarningPercent(Integer warningPercent) {
		this.warningPercent = warningPercent;
	}
	@Column(name="disabled")
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column(name="leaf")
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((budgetTypeCode == null) ? 0 : budgetTypeCode.hashCode());
		result = prime * result
				+ ((budgetTypeName == null) ? 0 : budgetTypeName.hashCode());
		result = prime
				* result
				+ ((exceedBudgetType == null) ? 0 : exceedBudgetType.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result
				+ ((warningPercent == null) ? 0 : warningPercent.hashCode());
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
		BudgetType other = (BudgetType) obj;
		if (budgetTypeCode == null) {
			if (other.budgetTypeCode != null)
				return false;
		} else if (!budgetTypeCode.equals(other.budgetTypeCode))
			return false;
		if (budgetTypeName == null) {
			if (other.budgetTypeName != null)
				return false;
		} else if (!budgetTypeName.equals(other.budgetTypeName))
			return false;
		if (exceedBudgetType == null) {
			if (other.exceedBudgetType != null)
				return false;
		} else if (!exceedBudgetType.equals(other.exceedBudgetType))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (warningPercent == null) {
			if (other.warningPercent != null)
				return false;
		} else if (!warningPercent.equals(other.warningPercent))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BudgetType [budgetTypeCode=" + budgetTypeCode
				+ ", budgetTypeName=" + budgetTypeName + ", exceedBudgetType="
				+ exceedBudgetType + ", warningPercent=" + warningPercent + "]";
	}
	
	
}
