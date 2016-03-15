package com.huge.ihos.system.reportManager.chart.webapp.action;

import com.huge.webapp.action.BaseAction;

public class ChartAction extends BaseAction{

	private String chartStr = "";
	private String charSwf ="";

	public String getCharSwf() {
		return charSwf;
	}

	public void setCharSwf(String charSwf) {
		this.charSwf = charSwf;
	}

	public String getChartStr() {
		return chartStr;
	}

	public void setChartStr(String chartStr) {
		this.chartStr = chartStr;
	}
}
