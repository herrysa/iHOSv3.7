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

@Entity
@Table(name = "kq_kqUpItemFormula")
public class KqUpItemFormula  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -973208441927285642L;

	private String formulaId; // 考勤项目公式表的id
	
	private String name;//名称

	private KqUpItem kqUpItem; // 考勤项目id
	
	private String conditionExp;//如果条件逻辑表达式
	
	private String conditionFormula;//如果条件公式
	
	private String conditionParameter;//如果条件参数
	
	private String conditionParameterDataType;//如果条件参数类型	
	
	private String resultFormula;//结果公式
	
	private String resultFormulaExp;//结果公式解析后
	
	private String resultParameter;//结果参数
	
	private String resultParameterDataType;//结果参数参数类型	

	private Boolean inUsed = false; // 停用
	
	private Boolean otherExped = false;//在本考勤项基数上
	
	private Integer otherExpType;//1：增加;2:减少;3:乘系数
	
	private Double otherExpValue;//值
	
	private String expContent = "如果;那么";//描述
	
	private Integer sn = 0;
	
	@Id
	@Column(name = "formulaId", length = 32)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kqUpItemId",nullable = true)
	public KqUpItem getKqUpItem() {
		return kqUpItem;
	}

	public void setKqUpItem(KqUpItem kqUpItem) {
		this.kqUpItem = kqUpItem;
	}
	
	@Column(name = "inUsed",nullable = true,columnDefinition = "bit default 0")
	public Boolean getInUsed() {
		return inUsed;
	}
    
	public void setInUsed(Boolean inUsed) {
		this.inUsed = inUsed;
	}
	
	@Column(name = "name",nullable = false,length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "conditionFormula",nullable = true,length=1000)
	public String getConditionFormula() {
		return conditionFormula;
	}

	public void setConditionFormula(String conditionFormula) {
		this.conditionFormula = conditionFormula;
	}
	
	@Column(name = "conditionParameter",nullable = true,length=1000)
	public String getConditionParameter() {
		return conditionParameter;
	}

	public void setConditionParameter(String conditionParameter) {
		this.conditionParameter = conditionParameter;
	}
	
	@Column(name = "resultFormula",nullable = true,length=1000)
	public String getResultFormula() {
		return resultFormula;
	}

	public void setResultFormula(String resultFormula) {
		this.resultFormula = resultFormula;
	}
	@Column(name = "resultFormulaExp",nullable = true,length=1000)
	public String getResultFormulaExp() {
		return resultFormulaExp;
	}

	public void setResultFormulaExp(String resultFormulaExp) {
		this.resultFormulaExp = resultFormulaExp;
	}
	
	@Column(name = "resultParameter",nullable = true,length=1000)
	public String getResultParameter() {
		return resultParameter;
	}

	public void setResultParameter(String resultParameter) {
		this.resultParameter = resultParameter;
	}
	
	@Column(name = "resultParameterDataType",nullable = true,length=100)
	public String getResultParameterDataType() {
		return resultParameterDataType;
	}

	public void setResultParameterDataType(String resultParameterDataType) {
		this.resultParameterDataType = resultParameterDataType;
	}
	
	@Column(name = "conditionParameterDataType",nullable = true,length=100)
	public String getConditionParameterDataType() {
		return conditionParameterDataType;
	}

	public void setConditionParameterDataType(String conditionParameterDataType) {
		this.conditionParameterDataType = conditionParameterDataType;
	}
	
	@Column(name = "conditionExp",nullable = true,length=50)
	public String getConditionExp() {
		return conditionExp;
	}

	public void setConditionExp(String conditionExp) {
		this.conditionExp = conditionExp;
	}
	
	@Column(name = "otherExped",nullable = true,columnDefinition = "bit default 0")
	public Boolean getOtherExped() {
		return otherExped;
	}
	public void setOtherExped(Boolean otherExped) {
		this.otherExped = otherExped;
	}
	
	@Column(name = "otherExpType",nullable = true)
	public Integer getOtherExpType() {
		return otherExpType;
	}

	public void setOtherExpType(Integer otherExpType) {
		this.otherExpType = otherExpType;
	}
	@Column(name = "otherExpValue",nullable = true)
	public Double getOtherExpValue() {
		return otherExpValue;
	}

	public void setOtherExpValue(Double otherExpValue) {
		this.otherExpValue = otherExpValue;
	}
	
	@Column(name = "expContent",nullable = true,length=4000)
	public String getExpContent() {
		return expContent;
	}

	public void setExpContent(String expContent) {
		this.expContent = expContent;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditionFormula == null) ? 0 : conditionFormula.hashCode());
		result = prime * result
				+ ((resultFormula == null) ? 0 : resultFormula.hashCode());
		result = prime * result
				+ ((formulaId == null) ? 0 : formulaId.hashCode());
		result = prime * result
				+ ((kqUpItem == null) ? 0 : kqUpItem.hashCode());
		result = prime * result + ((inUsed == null) ? 0 : inUsed.hashCode());
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
		KqUpItemFormula other = (KqUpItemFormula) obj;
		if (conditionFormula == null) {
			if (other.conditionFormula != null)
				return false;
		} else if (!conditionFormula.equals(other.conditionFormula))
			return false;
		if (resultFormula == null) {
			if (other.resultFormula != null)
				return false;
		} else if (!resultFormula.equals(other.resultFormula))
			return false;
		if (formulaId == null) {
			if (other.formulaId != null)
				return false;
		} else if (!formulaId.equals(other.formulaId))
			return false;
		if (kqUpItem == null) {
			if (other.kqUpItem != null)
				return false;
		} else if (!kqUpItem.equals(other.kqUpItem))
			return false;
		if (inUsed == null) {
			if (other.inUsed != null)
				return false;
		} else if (!inUsed.equals(other.inUsed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KqUpItemFormula [formulaId=" + formulaId + ", gzItem="
				+ kqUpItem + ", conditionFormula=" + conditionFormula
				+ ", resultFormula=" + resultFormula + ", inUsed=" + inUsed + "]";
	}
	@Column(name = "sn",nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
}
