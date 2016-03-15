package com.huge.ihos.hr.hrOperLog.model;

import java.util.List;
/**
 * 变动的实体信息,对应一条记录的多列变动
 * @author Gaozhengyang
 * @date 2014年11月19日
 */
public class HrLogEntityInfo<T> {
	private Class<T> entityClass;// 实体名
	private String operType;
	private String mainCode;
	private String orgCode;
	private List<HrLogColumnInfo> columnInfoList;//变动的属性集合
	
	public HrLogEntityInfo() {
		super();
	}
	
	public HrLogEntityInfo(Class<T> entityClass) {
		super();
		this.entityClass = entityClass;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public HrLogEntityInfo(String operType, String mainCode, String orgCode,
			List<HrLogColumnInfo> columnInfoList) {
		super();
		this.operType = operType;
		this.mainCode = mainCode;
		this.orgCode = orgCode;
		this.columnInfoList = columnInfoList;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getMainCode() {
		return mainCode;
	}

	public void setMainCode(String mainCode) {
		this.mainCode = mainCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public List<HrLogColumnInfo> getColumnInfoList() {
		return columnInfoList;
	}

	public void setColumnInfoList(List<HrLogColumnInfo> columnInfoList) {
		this.columnInfoList = columnInfoList;
	}
}
