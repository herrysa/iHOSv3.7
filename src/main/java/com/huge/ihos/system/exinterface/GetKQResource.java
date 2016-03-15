package com.huge.ihos.system.exinterface;

import java.util.List;

import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.webapp.util.PropertyFilter;


public abstract class GetKQResource extends SystemCallback{

	public abstract List<ReportType> allKqTypes(Boolean containDisabled );
	/**
	 * 获得报表的项
	 * @return
	 */
	public abstract List<ReportItem> getReportItems(List<PropertyFilter> filters);
}
