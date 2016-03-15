package com.huge.ihos.kaohe.model;

public class DeptInspectScore {

	private InspectBSC inspectBSC;
	private String checkperiod;
	private String periodType;
	private Integer state;
	private String comId;
	
	public String getComId() {
		comId = inspectBSC.getInspectContentId()+"_"+state+"_"+checkperiod;
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public InspectBSC getInspectBSC() {
		return inspectBSC;
	}
	public void setInspectBSC(InspectBSC inspectBSC) {
		this.inspectBSC = inspectBSC;
	}
	public String getCheckperiod() {
		return checkperiod;
	}
	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
}
