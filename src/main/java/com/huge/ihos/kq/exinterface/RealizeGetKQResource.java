package com.huge.ihos.kq.exinterface;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.kq.kqType.service.KqTypeManager;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemDao;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.system.exinterface.GetKQResource;
import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.util.OtherUtil;
import com.huge.webapp.util.PropertyFilter;

@Component
public class RealizeGetKQResource extends GetKQResource {

	private KqTypeManager kqTypeManager;
	private KqUpItemDao kqUpItemDao;
	
	@Autowired
	public void setKqTypeManager(KqTypeManager kqTypeManager) {
		this.kqTypeManager = kqTypeManager;
	}
	@Autowired
	public void setKqUpItemDao(KqUpItemDao kqUpItemDao) {
		this.kqUpItemDao = kqUpItemDao;
	}
	@Override
	public List<ReportType> allKqTypes(Boolean containDisabled) {
		List<KqType> kqTypes = kqTypeManager.allKqTypes(containDisabled);
		List<ReportType> kqTypeList = new ArrayList<ReportType>();
		if(OtherUtil.measureNotNull(kqTypes)&&!kqTypes.isEmpty()){
			for(KqType kqType:kqTypes){
				ReportType reportType = new ReportType();
				reportType.setTypeId(kqType.getKqTypeId());
				reportType.setTypeCode(kqType.getKqTypeCode());
				reportType.setTypeName(kqType.getKqTypeName());
				reportType.setIssueType("月");
				kqTypeList.add(reportType);
			}
		}
		return kqTypeList;
	}
	@Override
	public List<ReportItem> getReportItems(List<PropertyFilter> filters){
		List<ReportItem> reportItems = new ArrayList<ReportItem>();
		List<KqUpItem> kqUpItems = kqUpItemDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
			for(KqUpItem kqUpItem:kqUpItems){
				ReportItem reportItem = new ReportItem();
				reportItem.setItemCode(kqUpItem.getItemCode());
				reportItem.setItemName(kqUpItem.getItemName());
//				reportItem.setColName(kqUpItem.getColName());
				reportItem.setSn(kqUpItem.getSn());
				reportItem.setItemType(kqUpItem.getItemType());
				reportItem.setItemShowName(kqUpItem.getItemName());
				reportItems.add(reportItem);
			}
		}
		return reportItems;
	}
}
