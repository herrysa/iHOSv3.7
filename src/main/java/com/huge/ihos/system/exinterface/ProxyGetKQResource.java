package com.huge.ihos.system.exinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;

public class ProxyGetKQResource extends GetKQResource {

	private GetKQResource getKQResource;
	
	public ProxyGetKQResource(){
		getKQResource = (GetKQResource)SpringContextHelper.getBean("realizeGetKQResource");
	}
	@Override
	public List<ReportType> allKqTypes(Boolean containDisabled) {
		List<ReportType> kqTypes = new ArrayList<ReportType>();
		if(getKQResource!=null){
			kqTypes =  getKQResource.allKqTypes(containDisabled);
		}
		return kqTypes;
	}
	@Override
	public List<ReportItem> getReportItems(List<PropertyFilter> filters){
		return getKQResource.getReportItems(filters);
	}
}
