package com.huge.ihos.system.formDesigner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.huge.model.BaseObject;

@Entity
@Table(name="sy_formDesigner")
public class FormDesigner extends BaseObject{

	private String formId;
	private String formName;
	private String bdInfoId;
	private String detailBdinfoId;
	private String formXml;
	private String detailXml;
	private String printReportXml;
	
	@Column
	public String getPrintReportXml() {
		return printReportXml;
	}
	public void setPrintReportXml(String printReportXml) {
		this.printReportXml = printReportXml;
	}
	@Id
	@Column
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@Column
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	@Column(name="formBdInfoId")
	public String getBdInfoId() {
		return bdInfoId;
	}
	public void setBdInfoId(String bdInfoId) {
		this.bdInfoId = bdInfoId;
	}
	
	@Column(name="detailBdinfoId")
	public String getDetailBdinfoId() {
		return detailBdinfoId;
	}
	public void setDetailBdinfoId(String detailBdinfoId) {
		this.detailBdinfoId = detailBdinfoId;
	}
	
	@Column
	public String getFormXml() {
		return formXml;
	}
	public void setFormXml(String formXml) {
		this.formXml = formXml;
	}
	
	@Column
	public String getDetailXml() {
		return detailXml;
	}
	public void setDetailXml(String detailXml) {
		this.detailXml = detailXml;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bdInfoId == null) ? 0 : bdInfoId.hashCode());
		result = prime * result + ((formId == null) ? 0 : formId.hashCode());
		result = prime * result
				+ ((formName == null) ? 0 : formName.hashCode());
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
		FormDesigner other = (FormDesigner) obj;
		if (bdInfoId == null) {
			if (other.bdInfoId != null)
				return false;
		} else if (!bdInfoId.equals(other.bdInfoId))
			return false;
		if (formId == null) {
			if (other.formId != null)
				return false;
		} else if (!formId.equals(other.formId))
			return false;
		if (formName == null) {
			if (other.formName != null)
				return false;
		} else if (!formName.equals(other.formName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FormDesigner [formId=" + formId + ", formName=" + formName
				+ ", bdInfoId=" + bdInfoId + "]";
	}
	
	
}
