package com.huge.ihos.indicatoranalysis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 指标字典
 * 
 * @author Gaozhengyang
 * @date 2014年11月5日
 */
@Entity
@Table(name = "t_indicator")
public class Indicator extends BaseObject implements Serializable {
	private static final long serialVersionUID = 8581251448231001402L;
	private String id;
	private IndicatorType indicatorType;
	private String code;// 编码
	private String name;// 名称
	private String unit;// 指标单位
	private String formula;// 公式文本
	private Integer precision;// 精度
	private Boolean needSeparator = true;// 是否需要千分符
	private String remark;
	private Indicator parent;
	private Boolean leaf = true;
	private Integer level;
	private Integer seq;
	private String leftOper;
	private String rightOper;
	private Boolean toPercent = false; //结果转化为百分比
	
	@Column(name = "toPercent", nullable = true, columnDefinition = "bit default 0")
	public Boolean getToPercent() {
		return toPercent;
	}

	public void setToPercent(Boolean toPercent) {
		this.toPercent = toPercent;
	}

	@Column(name = "left_oper", nullable = true, length = 50)
	public String getLeftOper() {
		return leftOper;
	}

	public void setLeftOper(String leftOper) {
		this.leftOper = leftOper;
	}

	@Column(name = "right_oper", nullable = true, length = 50)
	public String getRightOper() {
		return rightOper;
	}

	public void setRightOper(String rightOper) {
		this.rightOper = rightOper;
	}

	@Column(name = "seq")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	private String realFormula;// 公式

	@Column(name = "realFormula", nullable = true, length = 500)
	public String getRealFormula() {
		return realFormula;
	}

	public void setRealFormula(String realFormula) {
		this.realFormula = realFormula;
	}

	@Transient
	public Boolean getExpanded() {
		return true;
	}

	@Transient
	public String getNameWithCode() {
		return this.name + "(" + this.code + ")";
	}

	@Id
	@Column(name = "id", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "indicatorTypeId", nullable = true)
	public IndicatorType getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(IndicatorType indicatorType) {
		this.indicatorType = indicatorType;
	}

	@Column(name = "code", nullable = false, length = 20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "unit", nullable = true, length = 10)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "formula", nullable = true, length = 500)
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Column(name = "precision")
	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	@Column(name = "needSeparator", nullable = false, columnDefinition = "bit default 1")
	public Boolean getNeedSeparator() {
		return needSeparator;
	}

	public void setNeedSeparator(Boolean needSeparator) {
		this.needSeparator = needSeparator;
	}

	@Column(name = "remark", nullable = true, length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	// @JSON(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", nullable = true)
	public Indicator getParent() {
		return parent;
	}

	public void setParent(Indicator parent) {
		this.parent = parent;
	}

	@Column(name = "leaf", nullable = false, columnDefinition = "bit default 0")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((indicatorType == null) ? 0 : indicatorType.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((needSeparator == null) ? 0 : needSeparator.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((precision == null) ? 0 : precision.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result
				+ ((realFormula == null) ? 0 : realFormula.hashCode());
		result = prime * result + ((seq == null) ? 0 : seq.hashCode());
		result = prime * result
				+ ((leftOper == null) ? 0 : leftOper.hashCode());
		result = prime * result
				+ ((rightOper == null) ? 0 : rightOper.hashCode());
		result = prime * result
				+ ((toPercent == null) ? 0 : toPercent.hashCode());
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
		Indicator other = (Indicator) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (indicatorType == null) {
			if (other.indicatorType != null)
				return false;
		} else if (!indicatorType.equals(other.indicatorType))
			return false;
		if (leaf == null) {
			if (other.leaf != null)
				return false;
		} else if (!leaf.equals(other.leaf))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (needSeparator == null) {
			if (other.needSeparator != null)
				return false;
		} else if (!needSeparator.equals(other.needSeparator))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (precision == null) {
			if (other.precision != null)
				return false;
		} else if (!precision.equals(other.precision))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (seq == null) {
			if (other.seq != null)
				return false;
		} else if (!seq.equals(other.seq))
			return false;
		if (leftOper == null) {
			if (other.leftOper != null)
				return false;
		} else if (!leftOper.equals(other.leftOper))
			return false;
		if (rightOper == null) {
			if (other.rightOper != null)
				return false;
		} else if (!rightOper.equals(other.rightOper))
			return false;
		if (realFormula == null) {
			if (other.realFormula != null)
				return false;
		} else if (!realFormula.equals(other.realFormula))
			return false;
		if (toPercent == null) {
			if (other.toPercent != null)
				return false;
		} else if (!toPercent.equals(other.toPercent))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Indicator [id=" + id + ", indicatorType=" + indicatorType
				+ ", code=" + code + ", name=" + name + ", unit=" + unit
				+ ", formula=" + formula + ", precision=" + precision
				+ ", needSeparator=" + needSeparator + ", remark=" + remark
				+ ", parent=" + parent + ", leaf=" + leaf + ", level=" + level
				+ ", seq = " + seq + ", leftOper=" + leftOper + ",rightOper="
				+ rightOper + ", toPercent="+toPercent+"]";
	}

}
