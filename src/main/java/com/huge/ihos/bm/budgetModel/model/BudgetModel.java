package com.huge.ihos.bm.budgetModel.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.model.BaseObject;

@Entity
@Table( name = "bm_model" )
public class BudgetModel extends BaseObject implements Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3671575311016423934L;
	private String modelId;
	private String modelCopyId;
	private String modelCode;
	private String modelName;
	private String modelType;
	//private String modelTypeTxt;
	private String periodType;
	private String creator;
	private Date createDate;
	private String modifier ;
	private Date modifydate ;
	private Boolean disabled ;
	private String remark ;
	private String department ;
	//private String periodYear;
	
	//private Boolean isHz;
	private BudgetModel hzModelId;
	
	private String reportXml ;
	
	private Set<Department> departments;
	
	private Set<BmModelProcess> bmModelProcesses;
	
	private BmModelProcess updataProcess;
	
	@Transient
	public BmModelProcess getUpdataProcess() {
		if(bmModelProcesses!=null){
			int i=0;
			Iterator<BmModelProcess> bmpIt = bmModelProcesses.iterator();
			while(bmpIt.hasNext()){
				BmModelProcess bmp = bmpIt.next();
				if(i==0){
					updataProcess = bmp;
				}else if(i==2){
					checkProcess = bmp;
					break;
				}
				i++;
			}
		}
		return updataProcess;
	}

	public void setUpdataProcess(BmModelProcess updataProcess) {
		this.updataProcess = updataProcess;
	}

	private BmModelProcess checkProcess;
	
	@Transient
	public BmModelProcess getCheckProcess() {
		return checkProcess;
	}

	public void setCheckProcess(BmModelProcess checkProcess) {
		this.checkProcess = checkProcess;
	}

	@Id
	@Column(name="modelId",length=20)
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	@Transient
	public String getModelCopyId() {
		return modelCopyId;
	}

	public void setModelCopyId(String modelCopyId) {
		this.modelCopyId = modelCopyId;
	}
	
	@Column(name="modelCode",length=20)
	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	@Column(name="modelName",length=255)
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name="modelType")
	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
	/*@Formula("(SELECT dItem.displayContent FROM t_dictionary_items dItem join t_dictionary dic on dic.dictionaryId=dItem.dictionary_id WHERE dic.code='bmModelType' and dItem.value=modelType)")
	public String getModelTypeTxt() {
		return modelTypeTxt;
	}

	public void setModelTypeTxt(String modelTypeTxt) {
		this.modelTypeTxt = modelTypeTxt;
	}*/

	@Column(name="periodType",length=10)
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	@Column(name="creator",length=50)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="createDate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="modifier",length=50)
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name="modifydate")
	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	@Column(name="disabled")
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name="remark",length=255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="reportXml")
	public String getReportXml() {
		return reportXml;
	}

	public void setReportXml(String reportXml) {
		this.reportXml = reportXml;
	}
	
	@JSON(serialize=false)
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable( name = "bm_model_dept", joinColumns = { @JoinColumn( name = "modelId" ) }, inverseJoinColumns = @JoinColumn( name = "deptId" ) )
	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}
	
	@JSON(serialize=false)
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="modelId")
	@OrderBy("state asc")
	public Set<BmModelProcess> getBmModelProcesses() {
		return bmModelProcesses;
	}

	public void setBmModelProcesses(Set<BmModelProcess> bmModelProcesses) {
		this.bmModelProcesses = bmModelProcesses;
	}
	
	@Transient
	public String getDepartment() {
		if(departments!=null){
			department = "";
			for(Department dept : departments){
				department += dept.getDepartmentId()+",";
			}
		}
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	/*@Column(name="isHz")
	public Boolean getIsHz() {
		return isHz;
	}

	public void setIsHz(Boolean isHz) {
		this.isHz = isHz;
	}*/

	@ManyToOne
	@JoinColumn(name="hzModelId")
	public BudgetModel getHzModelId() {
		return hzModelId;
	}

	public void setHzModelId(BudgetModel hzModelId) {
		this.hzModelId = hzModelId;
	}
	
	/*@Column
	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result
				+ ((disabled == null) ? 0 : disabled.hashCode());
		result = prime * result
				+ ((modelCode == null) ? 0 : modelCode.hashCode());
		result = prime * result + ((modelId == null) ? 0 : modelId.hashCode());
		result = prime * result
				+ ((modelName == null) ? 0 : modelName.hashCode());
		result = prime * result
				+ ((modelType == null) ? 0 : modelType.hashCode());
		result = prime * result
				+ ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result
				+ ((modifydate == null) ? 0 : modifydate.hashCode());
		result = prime * result
				+ ((periodType == null) ? 0 : periodType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
		BudgetModel other = (BudgetModel) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (disabled == null) {
			if (other.disabled != null)
				return false;
		} else if (!disabled.equals(other.disabled))
			return false;
		if (modelCode == null) {
			if (other.modelCode != null)
				return false;
		} else if (!modelCode.equals(other.modelCode))
			return false;
		if (modelId == null) {
			if (other.modelId != null)
				return false;
		} else if (!modelId.equals(other.modelId))
			return false;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		if (modelType == null) {
			if (other.modelType != null)
				return false;
		} else if (!modelType.equals(other.modelType))
			return false;
		if (modifier == null) {
			if (other.modifier != null)
				return false;
		} else if (!modifier.equals(other.modifier))
			return false;
		if (modifydate == null) {
			if (other.modifydate != null)
				return false;
		} else if (!modifydate.equals(other.modifydate))
			return false;
		if (periodType == null) {
			if (other.periodType != null)
				return false;
		} else if (!periodType.equals(other.periodType))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BudgetModel [modelId=" + modelId + ", modelCode=" + modelCode
				+ ", modelName=" + modelName + ", modelType=" + modelType
				+ ", periodType=" + periodType + ", creator=" + creator
				+ ", createDate=" + createDate + ", modifier=" + modifier
				+ ", modifydate=" + modifydate + ", disabled=" + disabled
				+ ", remark=" + remark + "]";
	}

	@Override
	public BudgetModel clone() {
		BudgetModel o = null;
		try {
			o = (BudgetModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
