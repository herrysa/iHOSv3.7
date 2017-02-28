package com.huge.ihos.bm.budgetAssistType.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_assistType" )
public class BudgetAssistType extends BaseObject{

	private String colCode;
	private String colName;
	private AssistType assistType;
	
	@Id
	@Column
	public String getColCode() {
		return colCode;
	}
	public void setColCode(String colCode) {
		this.colCode = colCode;
	}
	
	@Column
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assistType")
	public AssistType getAssistType() {
		return assistType;
	}
	public void setAssistType(AssistType assistType) {
		this.assistType = assistType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assistType == null) ? 0 : assistType.hashCode());
		result = prime * result + ((colCode == null) ? 0 : colCode.hashCode());
		result = prime * result + ((colName == null) ? 0 : colName.hashCode());
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
		BudgetAssistType other = (BudgetAssistType) obj;
		if (assistType == null) {
			if (other.assistType != null)
				return false;
		} else if (!assistType.equals(other.assistType))
			return false;
		if (colCode == null) {
			if (other.colCode != null)
				return false;
		} else if (!colCode.equals(other.colCode))
			return false;
		if (colName == null) {
			if (other.colName != null)
				return false;
		} else if (!colName.equals(other.colName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BudgetAssistType [colCode=" + colCode + ", colName=" + colName
				+ ", assistType=" + assistType + "]";
	}
	
	
}
