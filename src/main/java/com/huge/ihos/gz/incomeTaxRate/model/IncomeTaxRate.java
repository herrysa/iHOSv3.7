package com.huge.ihos.gz.incomeTaxRate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 个人所得税率实体类
 * */
@Entity
@Table(name = "gz_income_taxRate")
public class IncomeTaxRate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9055632672127276298L;

	private String rateId;   //个人所得税率ID
	
	private Integer level ;      //个人所得税的级别
	
	private Integer incomeFloor ;  //应纳税所得额下限
	
	private Integer incomeTopLimit;  // 应纳税所得额上限
	
	private Integer baseNum;         //免税收入
	
	private Double fullTaxCost;     //速算扣除数
	
	private Double extraCost ;     //附加费
	
	private Double taxRate;          //税率
	
	private Boolean disabled = false;         //停用  	
	
	@Id
	@Column(name = "rateId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	@Column(name = "incomeFloor")
	public Integer getIncomeFloor() {
		return incomeFloor;
	}

	public void setIncomeFloor(Integer incomeFloor) {
		this.incomeFloor = incomeFloor;
	}
	@Column(name = "incomeTopLimit")
	public Integer getIncomeTopLimit() {
		return incomeTopLimit;
	}

	public void setIncomeTopLimit(Integer incomeTopLimit) {
		this.incomeTopLimit = incomeTopLimit;
	}
	@Column(name = "baseNum")
	public Integer getBaseNum() {
		return baseNum;
	}

	public void setBaseNum(Integer baseNum) {
		this.baseNum = baseNum;
	}
	@Column(name = "fullTaxCost", nullable = true,precision=12,scale=2)
	public Double getFullTaxCost() {
		return fullTaxCost;
	}

	public void setFullTaxCost(Double fullTaxCost) {
		this.fullTaxCost = fullTaxCost;
	}
	@Column(name = "taxRate", nullable = true,precision=12,scale=2)
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	
	@Column(name = "disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	@Column(name = "extraCost", nullable = true,precision=12,scale=2)
    public Double getExtraCost() {
		return extraCost;
	}

	public void setExtraCost(Double extraCost) {
		this.extraCost = extraCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + baseNum;
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((extraCost == null) ? 0 : extraCost.hashCode());
		long temp;
		temp = Double.doubleToLongBits(fullTaxCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + incomeFloor;
		result = prime * result + incomeTopLimit;
		result = prime * result + level;
		result = prime * result + ((rateId == null) ? 0 : rateId.hashCode());
		temp = Double.doubleToLongBits(taxRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		IncomeTaxRate other = (IncomeTaxRate) obj;
		if (baseNum != other.baseNum)
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (extraCost == null) {
			if (other.extraCost != null)
				return false;
		} else if (!extraCost.equals(other.extraCost))
			return false;
		if (Double.doubleToLongBits(fullTaxCost) != Double
				.doubleToLongBits(other.fullTaxCost))
			return false;
		if (incomeFloor != other.incomeFloor)
			return false;
		if (incomeTopLimit != other.incomeTopLimit)
			return false;
		if (level != other.level)
			return false;
		if (rateId == null) {
			if (other.rateId != null)
				return false;
		} else if (!rateId.equals(other.rateId))
			return false;
		if (Double.doubleToLongBits(taxRate) != Double
				.doubleToLongBits(other.taxRate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IncomeTaxRate [rateId=" + rateId + ", level=" + level
				+ ", incomeFloor=" + incomeFloor + ", incomeTopLimit="
				+ incomeTopLimit + ", baseNum=" + baseNum + ", fullTaxCost="
				+ fullTaxCost + ", extraCost=" + extraCost + ", taxRate="
				+ taxRate + ", disabled=" + disabled + "]";
	}
}
