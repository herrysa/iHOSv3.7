package com.huge.ihos.material.documenttemplate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

/**
 * 单据模板
 * 
 * @author gaozhengyang
 * @date 2014年3月10日
 */
@Entity
@Table(name = "mm_document_template")
public class DocumentTemplate extends BaseObject implements Serializable {
	private static final long serialVersionUID = -8723239992313487747L;

	private String id;
	private String orgCode;
	private String copyCode;
	/*
	 * 单据类型：1:期初入库单；2：入库单；3：出库单；4：盘点单
	 */
	private String templateType;
	/*
	 * 模版名称
	 */
	private String templateName;
	/*
	 * 单据头
	 */
	private String title;
	/*
	 * 单据上方需要输入的区域
	 */
	private String inputArea;
	/*
	 * 单据中间的列表
	 */
	private String listArea;
	/*
	 * 单据底部的脚注
	 */
	private String footArea;
	// 使用日期
	private Date useDate;
	// 是否被使用
	private Boolean beUsed;

	private String inputAreaValue;
	private String listAreaValue;
	private String footAreaValue;

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

	@Column(name = "orgCode", length = 10, nullable = false)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "copyCode", length = 10, nullable = false)
	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	@Column(name = "templateType", length = 20, nullable = false)
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	@Column(name = "templateName", length = 50, nullable = false)
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "title", length = 50, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "inputArea", length = 500, nullable = true)
	public String getInputArea() {
		return inputArea;
	}

	public void setInputArea(String inputArea) {
		this.inputArea = inputArea;
	}

	@Column(name = "listArea", length = 500, nullable = true)
	public String getListArea() {
		return listArea;
	}

	public void setListArea(String listArea) {
		this.listArea = listArea;
	}

	@Column(name = "footArea", length = 100, nullable = true)
	public String getFootArea() {
		return footArea;
	}

	public void setFootArea(String footArea) {
		this.footArea = footArea;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "useDate", length = 19, nullable = true)
	@JSON(format = "yyyy-MM-dd")
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	@Column(name = "beUsed", nullable = false, columnDefinition = "bit default 0")
	public Boolean getBeUsed() {
		return beUsed;
	}

	public void setBeUsed(Boolean beUsed) {
		this.beUsed = beUsed;
	}

	@Column(name = "inputAreaValue", length = 100, nullable = true)
	public String getInputAreaValue() {
		return inputAreaValue;
	}

	public void setInputAreaValue(String inputAreaValue) {
		this.inputAreaValue = inputAreaValue;
	}

	@Column(name = "listAreaValue", length = 100, nullable = true)
	public String getListAreaValue() {
		return listAreaValue;
	}

	public void setListAreaValue(String listAreaValue) {
		this.listAreaValue = listAreaValue;
	}

	@Column(name = "footAreaValue", length = 100, nullable = true)
	public String getFootAreaValue() {
		return footAreaValue;
	}

	public void setFootAreaValue(String footAreaValue) {
		this.footAreaValue = footAreaValue;
	}
	
	@Transient
	public List<String> getInputNames() {
		return Arrays.asList(inputArea.split(","));
	}
	
	@Transient
	public List<String> getFootNames() {
		return Arrays.asList(footArea.split(","));
	}
	
	@Transient
	public HashMap<String, String> getListMap() {
		HashMap<String,String> listMap = new HashMap<String,String>();
		String[] lareas = listArea.split(",");
		String[] lvalues = listAreaValue.split(",");
		for(int i=0;i<lvalues.length;i++){
			listMap.put(lvalues[i].trim(), lareas[i].trim());
		}
		return listMap;
	}
	
	@Transient
	public List<TemplateAssist> getInputList(){
		List<TemplateAssist> inputList = new ArrayList<TemplateAssist>();
		TemplateAssist ta = null;
		String[] iareas = inputArea.split(",");
		String[] ivalues = inputAreaValue.split(",");
		String[] keys = null,values = null;
		String realValue = null;
		String valueType = null;
		for(int i=0;i<ivalues.length;i++){
			ta = new TemplateAssist(iareas[i].trim());
			keys = ivalues[i].split("_");
			realValue = keys[0];
			valueType = keys[2];
			ta.setType(valueType);
			ta.setNecessary(Boolean.parseBoolean(keys[1]));
			if(valueType.equals("refer")){
				ta.setValue(realValue.substring(0,realValue.indexOf('[')));
				values = realValue.substring(realValue.indexOf('[')+1, realValue.indexOf(']')).split(";");
				ta.setReferId(values[0]);
				ta.setReferName(values[1]);
			}else{
				ta.setValue(realValue);
			}
			inputList.add(ta);
		}
		return inputList;
	}
	
	@Transient
	public Map<String, String> getFootMap() {
		Map<String,String> footMap = new LinkedHashMap<String,String>();
		String[] fareas = footArea.split(",");
		String[] fvalues = footAreaValue.split(",");
		for(int i=0;i<fvalues.length;i++){
			footMap.put(fvalues[i].trim(), fareas[i].trim());
		}
		return footMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beUsed == null) ? 0 : beUsed.hashCode());
		result = prime * result
				+ ((copyCode == null) ? 0 : copyCode.hashCode());
		result = prime * result
				+ ((useDate == null) ? 0 : useDate.hashCode());
		result = prime * result
				+ ((footArea == null) ? 0 : footArea.hashCode());
		result = prime * result
				+ ((footAreaValue == null) ? 0 : footAreaValue.hashCode());
		result = prime * result
				+ ((inputArea == null) ? 0 : inputArea.hashCode());
		result = prime * result
				+ ((inputAreaValue == null) ? 0 : inputAreaValue.hashCode());
		result = prime * result
				+ ((listArea == null) ? 0 : listArea.hashCode());
		result = prime * result
				+ ((listAreaValue == null) ? 0 : listAreaValue.hashCode());
		result = prime * result + ((orgCode == null) ? 0 : orgCode.hashCode());
		result = prime * result
				+ ((templateName == null) ? 0 : templateName.hashCode());
		result = prime * result
				+ ((templateType == null) ? 0 : templateType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		DocumentTemplate other = (DocumentTemplate) obj;
		if (beUsed == null) {
			if (other.beUsed != null)
				return false;
		} else if (!beUsed.equals(other.beUsed))
			return false;
		if (copyCode == null) {
			if (other.copyCode != null)
				return false;
		} else if (!copyCode.equals(other.copyCode))
			return false;
		if (useDate == null) {
			if (other.useDate != null)
				return false;
		} else if (!useDate.equals(other.useDate))
			return false;
		if (footArea == null) {
			if (other.footArea != null)
				return false;
		} else if (!footArea.equals(other.footArea))
			return false;
		if (footAreaValue == null) {
			if (other.footAreaValue != null)
				return false;
		} else if (!footAreaValue.equals(other.footAreaValue))
			return false;
		if (inputArea == null) {
			if (other.inputArea != null)
				return false;
		} else if (!inputArea.equals(other.inputArea))
			return false;
		if (inputAreaValue == null) {
			if (other.inputAreaValue != null)
				return false;
		} else if (!inputAreaValue.equals(other.inputAreaValue))
			return false;
		if (listArea == null) {
			if (other.listArea != null)
				return false;
		} else if (!listArea.equals(other.listArea))
			return false;
		if (listAreaValue == null) {
			if (other.listAreaValue != null)
				return false;
		} else if (!listAreaValue.equals(other.listAreaValue))
			return false;
		if (orgCode == null) {
			if (other.orgCode != null)
				return false;
		} else if (!orgCode.equals(other.orgCode))
			return false;
		if (templateName == null) {
			if (other.templateName != null)
				return false;
		} else if (!templateName.equals(other.templateName))
			return false;
		if (templateType == null) {
			if (other.templateType != null)
				return false;
		} else if (!templateType.equals(other.templateType))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DocumentTemplate [id=" + id + ", orgCode=" + orgCode
				+ ", copyCode=" + copyCode + ", templateType=" + templateType
				+ ", templateName=" + templateName + ", title=" + title
				+ ", inputArea=" + inputArea + ", listArea=" + listArea
				+ ", footArea=" + footArea + ", useDate=" + useDate
				+ ", beUsed=" + beUsed + ", inputAreaValue=" + inputAreaValue
				+ ", listAreaValue=" + listAreaValue + ", footAreaValue="
				+ footAreaValue + "]";
	}

}
