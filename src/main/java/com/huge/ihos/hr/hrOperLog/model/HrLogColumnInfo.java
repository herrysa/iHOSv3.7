package com.huge.ihos.hr.hrOperLog.model;
/**
 * 变动的属性信息，对应一列的变动
 * @author Gaozhengyang
 * @date 2014年11月19日
 */
public class HrLogColumnInfo {
	private String columnName;
	private Object oldValue;
	private Object newValue;
	
	public HrLogColumnInfo() {
		super();
	}

	public HrLogColumnInfo(String columnName, Object oldValue, Object newValue) {
		super();
		this.columnName = columnName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public HrLogColumnInfo(String columnName, Object newValue) {
		super();
		this.columnName = columnName;
		this.newValue = newValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
}
