package com.huge.ihos.kaohe.model;

import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;

public class DeptInspectScoreState {

	private String scoreStaeId = "";

	private String checkperiod = "";
	private Department inspectdept;
	private Org inspectOrg;
	private Integer state;
	private String totalcore;
	private String money1;
	private String money2;
	private String inspectTemplId;
	private String periodType;
	private String inspectTemplName;

	public Org getInspectOrg() {
		return inspectOrg;
	}

	public void setInspectOrg(Org inspectOrg) {
		this.inspectOrg = inspectOrg;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getInspectTemplName() {
		return inspectTemplName;
	}

	public void setInspectTemplName(String inspectTemplName) {
		this.inspectTemplName = inspectTemplName;
	}

	public String getInspectTemplId() {
		return inspectTemplId;
	}

	public void setInspectTemplId(String inspectTemplId) {
		this.inspectTemplId = inspectTemplId;
	}

	public String getTotalcore() {
		return totalcore;
	}

	public void setTotalcore(String totalcore) {
		this.totalcore = totalcore;
	}

	public String getMoney1() {
		return money1;
	}

	public void setMoney1(String money1) {
		this.money1 = money1;
	}

	public String getMoney2() {
		return money2;
	}

	public void setMoney2(String money2) {
		this.money2 = money2;
	}

	public String getScoreStaeId() {
		scoreStaeId = inspectdept.getDepartmentId() + "_" + state;
		return scoreStaeId;
	}

	public void setScoreStaeId(String scoreStaeId) {
		this.scoreStaeId = scoreStaeId;
	}

	public String getCheckperiod() {
		return checkperiod;
	}

	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}

	public Department getInspectdept() {
		return inspectdept;
	}

	public void setInspectdept(Department inspectdept) {
		this.inspectdept = inspectdept;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
