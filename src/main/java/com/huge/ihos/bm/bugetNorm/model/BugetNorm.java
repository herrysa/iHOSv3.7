package com.huge.ihos.bm.bugetNorm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_bugetNorm" )
public class BugetNorm extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6367418345944337407L;
	private String normId;
	private Department deptId;
	private BugetSubj bmSubjId;
	private Double norm;
	private Double rs;
	private Double amount;
	
	@Id
	@Column(name = "normId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getNormId() {
		return normId;
	}
	public void setNormId(String normId) {
		this.normId = normId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="deptId")
	public Department getDeptId() {
		return deptId;
	}
	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bmSubjId")
	public BugetSubj getBmSubjId() {
		return bmSubjId;
	}
	public void setBmSubjId(BugetSubj bmSubjId) {
		this.bmSubjId = bmSubjId;
	}
	
	@Column
	public Double getNorm() {
		return norm;
	}
	public void setNorm(Double norm) {
		this.norm = norm;
	}
	
	@Column
	public Double getRs() {
		return rs;
	}
	public void setRs(Double rs) {
		this.rs = rs;
	}
	
	@Column
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((norm == null) ? 0 : norm.hashCode());
		result = prime * result + ((normId == null) ? 0 : normId.hashCode());
		result = prime * result + ((rs == null) ? 0 : rs.hashCode());
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
		BugetNorm other = (BugetNorm) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (norm == null) {
			if (other.norm != null)
				return false;
		} else if (!norm.equals(other.norm))
			return false;
		if (normId == null) {
			if (other.normId != null)
				return false;
		} else if (!normId.equals(other.normId))
			return false;
		if (rs == null) {
			if (other.rs != null)
				return false;
		} else if (!rs.equals(other.rs))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BugetNorm [normId=" + normId + ", de=" + norm + ", rs=" + rs
				+ ", amount=" + amount + "]";
	}
	
	
}
