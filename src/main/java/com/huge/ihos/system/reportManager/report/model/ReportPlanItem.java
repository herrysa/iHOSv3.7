package com.huge.ihos.system.reportManager.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.huge.model.BaseObject;

@Entity
@Table(name = "sy_report_plan_item")
public class ReportPlanItem extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4998187366670319322L;

	private String colId ;
	
	private String itemName;//项名称
    
    private String itemCode;//项编码
    
    private String colName;//列名
    
    private Integer colSn;//序号
    
    private Integer colWidth = 80;//列宽
    
    private String planId ;
    
    private String isThousandSeparat;//千分符
    
    private String headerFontIndex;//标题字体
    
    private String fontIndex;//字体
    
    private String headerTextColor;//标题文字颜色
    
    @Id
    @Column(name = "colId", length = 32)
    @GenericGenerator(name = "uuid", strategy = "uuid")
   	@GeneratedValue(generator = "uuid")
	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
		this.colId = colId;
	}
	@Column(name = "itemCode", length = 20)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	@Column(name = "colName", length = 50)
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}
	@Column(name = "colSn")
	public Integer getColSn() {
		return colSn;
	}

	public void setColSn(Integer colSn) {
		this.colSn = colSn;
	}
	@Column(name = "colWidth")
	public Integer getColWidth() {
		return colWidth;
	}

	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
	}
	@Column(name = "planId", length = 32)
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(name = "isThousandSeparat", length = 10)
	public String getIsThousandSeparat() {
		return isThousandSeparat;
	}

	public void setIsThousandSeparat(String isThousandSeparat) {
		this.isThousandSeparat = isThousandSeparat;
	}
	@Column(name = "headerFontIndex", length = 10)
	public String getHeaderFontIndex() {
		return headerFontIndex;
	}

	public void setHeaderFontIndex(String headerFontIndex) {
		this.headerFontIndex = headerFontIndex;
	}
	@Column(name = "fontIndex", length = 10)
	public String getFontIndex() {
		return fontIndex;
	}

	public void setFontIndex(String fontIndex) {
		this.fontIndex = fontIndex;
	}
	@Column(name = "headerTextColor", length = 20)
	public String getHeaderTextColor() {
		return headerTextColor;
	}

	public void setHeaderTextColor(String headerTextColor) {
		this.headerTextColor = headerTextColor;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colId == null) ? 0 : colId.hashCode());
		result = prime * result + ((colName == null) ? 0 : colName.hashCode());
		result = prime * result + ((colSn == null) ? 0 : colSn.hashCode());
		result = prime * result
				+ ((colWidth == null) ? 0 : colWidth.hashCode());
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
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
		ReportPlanItem other = (ReportPlanItem) obj;
		if (colId == null) {
			if (other.colId != null)
				return false;
		} else if (!colId.equals(other.colId))
			return false;
		if (colName == null) {
			if (other.colName != null)
				return false;
		} else if (!colName.equals(other.colName))
			return false;
		if (colSn == null) {
			if (other.colSn != null)
				return false;
		} else if (!colSn.equals(other.colSn))
			return false;
		if (colWidth == null) {
			if (other.colWidth != null)
				return false;
		} else if (!colWidth.equals(other.colWidth))
			return false;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (planId == null) {
			if (other.planId != null)
				return false;
		} else if (!planId.equals(other.planId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportPlanItem [colId=" + colId + ", itemCode=" + itemCode
				+ ", colName=" + colName + ", colSn=" + colSn + ", colWidth="
				+ colWidth + ", planId=" + planId + "]";
	}
}
