package com.huge.ihos.system.exinterface;

import java.util.ArrayList;
import java.util.List;

import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;

public class ProxyGetGZResource extends GetGZResource {

	private GetGZResource getGZResource;
	
	public ProxyGetGZResource(){
		getGZResource = (GetGZResource)SpringContextHelper.getBean("realizeGetGZResource");
	}
	@Override
	public List<ReportType> allGzTypes(Boolean containDisabled) {
		List<ReportType> gzTypes = new ArrayList<ReportType>();
		if(getGZResource!=null){
			gzTypes = getGZResource.allGzTypes(containDisabled);
		}
		return gzTypes;
	}
	@Override
	public List<ReportItem> getReportItems(List<PropertyFilter> filters){
		return getGZResource.getReportItems(filters);
	}
}
