package com.huge.ihos.gz.exinterface;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huge.ihos.gz.gzItem.dao.GzItemDao;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.system.exinterface.GetGZResource;
import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.util.OtherUtil;
import com.huge.webapp.util.PropertyFilter;

@Component
public class RealizeGetGZResource extends GetGZResource {

	private GzTypeManager gzTypeManager;
	private GzItemDao gzItemDao;
	
	@Autowired
	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}
	@Autowired
	public void setGzItemDao(GzItemDao gzItemDao) {
		this.gzItemDao = gzItemDao;
	}
	@Override
	public List<ReportType> allGzTypes(Boolean containDisabled) {
		List<GzType> gzTypes = gzTypeManager.allGzTypes(containDisabled);
		List<ReportType> gzccList = new ArrayList<ReportType>();
		if(OtherUtil.measureNotNull(gzTypes)){
			for(GzType gzType:gzTypes){
				ReportType reportType = new ReportType();
				reportType.setTypeId(gzType.getGzTypeId());
				reportType.setTypeCode(gzType.getGzTypeId());
				reportType.setTypeName(gzType.getGzTypeName());
				String issueType = gzType.getIssueType();
				if("1".equals(issueType)){
					reportType.setIssueType("次");
				}else{
					reportType.setIssueType("月");
				}
				gzccList.add(reportType);
			}
		}
		return gzccList;
	}
	@Override
	public List<ReportItem> getReportItems(List<PropertyFilter> filters){
		List<ReportItem> reportItems = new ArrayList<ReportItem>();
		List<GzItem> gzItems = gzItemDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
			for(GzItem gzItem:gzItems){
				ReportItem reportItem = new ReportItem();
				reportItem.setItemCode(gzItem.getItemCode());
				reportItem.setItemName(gzItem.getItemName());
//				reportItem.setColName(gzItem.getColName());
				reportItem.setSn(gzItem.getSn());
				reportItem.setItemType(gzItem.getItemType());
				reportItem.setItemShowName(gzItem.getItemShowName());
				reportItems.add(reportItem);
			}
		}
		return reportItems;
	}
}
