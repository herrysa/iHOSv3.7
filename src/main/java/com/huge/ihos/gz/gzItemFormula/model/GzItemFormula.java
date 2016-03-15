package com.huge.ihos.gz.gzItemFormula.model;

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

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.util.OtherUtil;

/**
 * 工资项公式
 * @author Administrator
 *
 */
@Entity
@Table(name = "gz_gzItemFormula")
public class GzItemFormula  implements Serializable,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String formulaId; // 工资项目公式表的id
	
	private String name;//名称

	private GzItem gzItem; // 工资项目id
	
	private String conditionExp;//如果条件逻辑表达式
	
	private String conditionFormula;//如果条件公式
	
	private String conditionParameter;//如果条件参数
	
	private String conditionParameterDataType;//如果条件参数类型	
	
	private String resultFormula;//结果公式
	
	private String resultFormulaExp;//结果公式解析后
	
	private String resultParameter;//结果参数
	
	private String resultParameterDataType;//结果参数参数类型	

	private Boolean inUsed = false; // 停用
	
	private Boolean otherExped = false;//在本工资项基数上
	
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
	@JoinColumn(name = "gzItemId",nullable = true)
	public GzItem getGzItem() {
		return gzItem;
	}

	public void setGzItem(GzItem gzItem) {
		this.gzItem = gzItem;
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
	@Column(name = "sn",nullable = true)
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
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
				+ ((gzItem == null) ? 0 : gzItem.hashCode());
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
		GzItemFormula other = (GzItemFormula) obj;
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
		if (gzItem == null) {
			if (other.gzItem != null)
				return false;
		} else if (!gzItem.equals(other.gzItem))
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
		return "GzItemFormula [formulaId=" + formulaId + ", gzItem="
				+ gzItem + ", conditionFormula=" + conditionFormula
				+ ", resultFormula=" + resultFormula + ", inUsed=" + inUsed + "]";
	}
	@Override
	public GzItemFormula clone() {
		GzItemFormula o = null;
		try {
			o = (GzItemFormula) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	public String toInsertSql(){
		String sqlString = " INSERT INTO gz_gzItemFormula ";
		String sqlHead = "";
		String sqlEnd = "";
		sqlHead += "formulaId,";
		sqlEnd += "'"+formulaId+"',";
		sqlHead += "name,";
		sqlEnd += "'"+name+"',";
		sqlHead += "conditionExp,";
		if(OtherUtil.measureNotNull(conditionExp)){
			sqlEnd += "'"+conditionExp+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "gzItemId,";
		sqlEnd += "'"+gzItem.getItemId()+"',";
		sqlHead += "conditionFormula,";
		if(OtherUtil.measureNotNull(conditionFormula)){
			sqlEnd += "'"+conditionFormula.replace("'", "''")+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "conditionParameter,";
		if(OtherUtil.measureNotNull(conditionParameter)){
			sqlEnd += "'"+conditionParameter+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "conditionParameterDataType,";
		if(OtherUtil.measureNotNull(conditionParameterDataType)){
			sqlEnd += "'"+conditionParameterDataType+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "resultFormula,";
		if(OtherUtil.measureNotNull(resultFormula)){
			sqlEnd += "'"+resultFormula+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "resultFormulaExp,";
		if(OtherUtil.measureNotNull(resultFormulaExp)){
			sqlEnd += "'"+resultFormulaExp+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "resultParameter,";
		if(OtherUtil.measureNotNull(resultParameter)){
			sqlEnd += "'"+resultParameter+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "resultParameterDataType,";
		if(OtherUtil.measureNotNull(resultParameterDataType)){
			sqlEnd += "'"+resultParameterDataType+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "inUsed,";
		if(OtherUtil.measureNotNull(inUsed)){
			if(inUsed == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "otherExped,";
		if(OtherUtil.measureNotNull(otherExped)){
			if(otherExped == true){
				sqlEnd += "'1',";
			}else{
				sqlEnd += "'0',";
			}
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "otherExpType,";
		if(OtherUtil.measureNotNull(otherExpType)){
			sqlEnd += "'"+otherExpType+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "otherExpValue,";
		if(OtherUtil.measureNotNull(otherExpValue)){
			sqlEnd += "'"+otherExpValue+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "expContent,";
		if(OtherUtil.measureNotNull(expContent)){
			sqlEnd += "'"+expContent.replace("'", "''")+"',";
		}else{
			sqlEnd += "NULL,";
		}
		sqlHead += "sn";
		if(OtherUtil.measureNotNull(sn)){
			sqlEnd += "'"+sn+"'";
		}else{
			sqlEnd += "NULL";
		}
		sqlString += "(" + sqlHead + ") VALUES (" + sqlEnd + ")";
		return sqlString;
	}
}
