package com.huge.ihos.hr.statistics.model;

import com.huge.webapp.ztree.ZTreeSimpleNode;

public class StatisticsTreeNode extends ZTreeSimpleNode{
	private Boolean statisticsAuto;
	private Boolean deptRequired;
	public Boolean getDeptRequired() {
		return deptRequired;
	}
	public void setDeptRequired(Boolean deptRequired) {
		this.deptRequired = deptRequired;
	}
	public Boolean getStatisticsAuto() {
		return statisticsAuto;
	}
	public void setStatisticsAuto(Boolean statisticsAuto) {
		this.statisticsAuto = statisticsAuto;
	}
}
