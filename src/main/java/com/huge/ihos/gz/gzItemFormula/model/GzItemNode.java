package com.huge.ihos.gz.gzItemFormula.model;

import java.util.List;

import com.huge.webapp.ztree.ZTreeSimpleNode;

public class GzItemNode extends ZTreeSimpleNode{

	private Integer type = 1;//1:字符型；2：数字型；3：整型；4：日期型；5：下拉框
	private List<String> values;
	private List<String> operTypes;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public List<String> getOperTypes() {
//		String[] opers = {"=","<>",">","<",">=","<=","like"}; 
//		operTypes = Arrays.asList(opers);
//		if(OtherUtil.measureNotNull(type)){
//			switch (type) {
//			case 1:
//			case 5:
//				operTypes.remove(">");
//				operTypes.remove("<");
//				operTypes.remove(">=");
//				operTypes.remove("<=");
//				break;
//			case 2:
//			case 3:
//			case 4:
//				operTypes.remove("like");
//				break;
//			default:
//				break;
//			}
//		}
		return operTypes;
	}
	public void setOperTypes(List<String> operTypes) {
		this.operTypes = operTypes;
	}
}
