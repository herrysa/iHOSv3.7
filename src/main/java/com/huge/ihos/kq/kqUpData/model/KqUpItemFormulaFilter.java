package com.huge.ihos.kq.kqUpData.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "kq_kqUpItemFormulaFilter")
public class KqUpItemFormulaFilter extends BaseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1603251140713594160L;
	private String filterId;
	private KqUpItemFormula kqUpItemFormula;
	private String code;
	private String name;
	private String oper;
	private String searchValue;

	@Id
	@Column(name = "filterId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formulaId",nullable = true)
	public KqUpItemFormula getKqUpItemFormula() {
		return kqUpItemFormula;
	}

	public void setKqUpItemFormula(KqUpItemFormula kqUpItemFormula) {
		this.kqUpItemFormula = kqUpItemFormula;
	}
	@Column(name = "code",nullable = true,length=50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "name",nullable = true,length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "oper",nullable = true,length=50)
	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	@Column(name = "searchValue",nullable = true,length=50)
	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	@Override
	public String toString() {
		return "KqUpItemFormulaFilter [filterId=" + filterId + ", code="
				+ code + ", name=" + name
				+ ", oper=" + oper + ", searchValue=" + searchValue + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KqUpItemFormulaFilter other = (KqUpItemFormulaFilter) obj;
		if (filterId == null) {
			if (other.filterId != null)
				return false;
		} else if (!filterId.equals(other.filterId))
			return false;
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
		if (oper == null) {
			if (other.oper != null)
				return false;
		} else if (!oper.equals(other.oper))
			return false;
		if (searchValue == null) {
			if (other.searchValue != null)
				return false;
		} else if (!searchValue.equals(other.searchValue))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filterId == null) ? 0 : filterId.hashCode());
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((oper == null) ? 0 : oper.hashCode());
		result = prime * result 
				+ ((searchValue == null) ? 0 : searchValue.hashCode());
		return result;
	}
}
