package com.huge.ihos.system.reportManager.report.model;

import java.util.ArrayList;
import java.util.List;

public class ReportFunc {

	String name;
	String func;
	Object[] para;
	List<Object> paraList;
	String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public Object[] getPara() {
		if(paraList!=null){
			para = paraList.toArray(new Object[paraList.size()]);
		}else{
			para = new Object[0];
		}
		return para;
	}
	public void setPara(Object[] para) {
		this.para = para;
	}
	
	public void addPara(Object o){
		if(paraList==null){
			paraList = new ArrayList<Object>();
		}
		paraList.add(o);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
