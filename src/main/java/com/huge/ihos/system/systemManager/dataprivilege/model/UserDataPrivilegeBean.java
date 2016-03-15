package com.huge.ihos.system.systemManager.dataprivilege.model;

public class UserDataPrivilegeBean {
	private String classCode;
	private String orgCode;
	private String copyCode;
	private String periodYear;
	private String rightType;
	private String item;
	private String itemName;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getCopyCode() {
		return copyCode;
	}
	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}
	public String getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
}
