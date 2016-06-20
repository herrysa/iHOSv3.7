package com.huge.ihos.bm.index.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.bm.budgetType.model.BudgetType;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_index" )
public class BudgetIndex extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364486572907937688L;
	private String indexCode;
	private String indexName;
	private BudgetIndex parentId;
	
	private BudgetType budgetType;
	
	private String exceedBudgetType;
	private Integer warningPercent;
	
	private String indexType;
	
	private Boolean leaf;
	private Boolean disabled;


	@Id
	@Column(name="index_code")
	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	@Column(name="index_name")
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@Column(name="parent_id")
	public BudgetIndex getParentId() {
		return parentId;
	}

	public void setParentId(BudgetIndex parentId) {
		this.parentId = parentId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="budgetType_id")
	public BudgetType getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
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

	@Column(name="index_type")
	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	@Column(name="leaf")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	@Column(name="disabled")
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
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime
				* result
				+ ((exceedBudgetType == null) ? 0 : exceedBudgetType.hashCode());
		result = prime * result
				+ ((indexCode == null) ? 0 : indexCode.hashCode());
		result = prime * result
				+ ((indexName == null) ? 0 : indexName.hashCode());
		result = prime * result
				+ ((indexType == null) ? 0 : indexType.hashCode());
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
		BudgetIndex other = (BudgetIndex) obj;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (exceedBudgetType == null) {
			if (other.exceedBudgetType != null)
				return false;
		} else if (!exceedBudgetType.equals(other.exceedBudgetType))
			return false;
		if (indexCode == null) {
			if (other.indexCode != null)
				return false;
		} else if (!indexCode.equals(other.indexCode))
			return false;
		if (indexName == null) {
			if (other.indexName != null)
				return false;
		} else if (!indexName.equals(other.indexName))
			return false;
		if (indexType == null) {
			if (other.indexType != null)
				return false;
		} else if (!indexType.equals(other.indexType))
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
		return "BudgetIndex [indexCode=" + indexCode + ", indexName="
				+ indexName + ", exceedBudgetType=" + exceedBudgetType
				+ ", warningPercent=" + warningPercent + ", indexType="
				+ indexType + ", disabeld=" + disabled + "]";
	}
	
	
	
}
