package com.huge.ihos.system.reportManager.report.model;

public class ReportItem {
	private String itemCode;
	private String itemName;
	private String colName;
	private String itemShowName;
	private Integer sn;
	private String itemType;// 项目类型 0:数字型;1:字符型;2:日期型;3:整型
	private String isThousandSeparat;//千分符
	private String headerFontIndex;
	private String fontIndex;
	private String headerTextColor;
	private Boolean sumColumn = false;
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getIsThousandSeparat() {
		return isThousandSeparat;
	}
	public void setIsThousandSeparat(String isThousandSeparat) {
		this.isThousandSeparat = isThousandSeparat;
	}
	public String getHeaderFontIndex() {
		return headerFontIndex;
	}
	public void setHeaderFontIndex(String headerFontIndex) {
		this.headerFontIndex = headerFontIndex;
	}
	public String getFontIndex() {
		return fontIndex;
	}
	public void setFontIndex(String fontIndex) {
		this.fontIndex = fontIndex;
	}
	public String getHeaderTextColor() {
		return headerTextColor;
	}
	public void setHeaderTextColor(String headerTextColor) {
		this.headerTextColor = headerTextColor;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemShowName() {
		return itemShowName;
	}
	public void setItemShowName(String itemShowName) {
		this.itemShowName = itemShowName;
	}
	public Boolean getSumColumn() {
		return sumColumn;
	}
	public void setSumColumn(Boolean sumColumn) {
		this.sumColumn = sumColumn;
	}
}
