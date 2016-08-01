package com.huge.ihos.bm.budgetUpdata.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_model_xf" )
public class BudgetModelXf extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3671575311016423934L;
	private String xfId;
	private BudgetModel modelId;
	private Date xfDate;
	private String periodYear;
	private Integer state;			//0:未上报;1:上报中;2:已上报;3已过期
	private Integer xfNum;
	private Integer updataingNum;
	private Integer confirmNum;
	private Integer checkedNum;
	private Integer submitedNum;
	
	private Map<String,Object> stepMap;
	
	@Transient
	public Map<String, Object> getStepMap() {
		return stepMap;
	}
	public void setStepMap(Map<String, Object> stepMap) {
		this.stepMap = stepMap;
	}
	@Formula("(select count(*) from bm_updata up where up.modelXfId=xfId)")
	public Integer getXfNum() {
		return xfNum;
	}
	public void setXfNum(Integer xfNum) {
		this.xfNum = xfNum;
	}
	
	@Formula("(select count(*) from bm_updata up where up.modelXfId=xfId and up.state=0)")
	public Integer getUpdataingNum() {
		return updataingNum;
	}
	public void setUpdataingNum(Integer updataingNum) {
		this.updataingNum = updataingNum;
	}
	
	@Formula("(select count(*) from bm_updata up where up.modelXfId=xfId and up.state=1)")
	public Integer getConfirmNum() {
		return confirmNum;
	}
	public void setConfirmNum(Integer confirmNum) {
		this.confirmNum = confirmNum;
	}
	
	@Formula("(select count(*) from bm_updata up where up.modelXfId=xfId and up.state=2)")
	public Integer getCheckedNum() {
		return checkedNum;
	}
	public void setCheckedNum(Integer checkedNum) {
		this.checkedNum = checkedNum;
	}
	
	@Formula("(select count(*) from bm_updata up where up.modelXfId=xfId and up.state=3)")
	public Integer getSubmitedNum() {
		return submitedNum;
	}
	public void setSubmitedNum(Integer submitedNum) {
		this.submitedNum = submitedNum;
	}
	@Id
	@Column(name = "xfId", length = 32, nullable = false)
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	public String getXfId() {
		return xfId;
	}
	public void setXfId(String xfId) {
		this.xfId = xfId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "modelId")
	public BudgetModel getModelId() {
		return modelId;
	}

	public void setModelId(BudgetModel modelId) {
		this.modelId = modelId;
	}
	
	@Column(name = "xfDate")
	public Date getXfDate() {
		return xfDate;
	}
	public void setXfDate(Date xfDate) {
		this.xfDate = xfDate;
	}
	
	@Column(name = "period_year")
	public String getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}
	
	@Column(name = "state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((periodYear == null) ? 0 : periodYear.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((xfDate == null) ? 0 : xfDate.hashCode());
		result = prime * result + ((xfId == null) ? 0 : xfId.hashCode());
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
		BudgetModelXf other = (BudgetModelXf) obj;
		if (periodYear == null) {
			if (other.periodYear != null)
				return false;
		} else if (!periodYear.equals(other.periodYear))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (xfDate == null) {
			if (other.xfDate != null)
				return false;
		} else if (!xfDate.equals(other.xfDate))
			return false;
		if (xfId == null) {
			if (other.xfId != null)
				return false;
		} else if (!xfId.equals(other.xfId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BudgetModelXf [xfId=" + xfId + ", xfDate=" + xfDate
				+ ", periodYear=" + periodYear + ", state=" + state + "]";
	}
	
	

	
}
