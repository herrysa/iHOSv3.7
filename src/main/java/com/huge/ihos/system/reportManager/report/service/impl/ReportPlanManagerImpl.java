package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.report.dao.ReportPlanDao;
import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportPlan;
import com.huge.ihos.system.reportManager.report.model.ReportPlanFilter;
import com.huge.ihos.system.reportManager.report.model.ReportPlanItem;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.ihos.system.reportManager.report.service.ReportPlanFilterManager;
import com.huge.ihos.system.reportManager.report.service.ReportPlanItemManager;
import com.huge.ihos.system.reportManager.report.service.ReportPlanManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("reportPlanManager")
public class ReportPlanManagerImpl extends GenericManagerImpl<ReportPlan, String> implements ReportPlanManager {
    private ReportPlanDao reportPlanDao;
    private ReportPlanFilterManager reportPlanFilterManager;
    private ReportPlanItemManager reportPlanItemManager;

    @Autowired
    public ReportPlanManagerImpl(ReportPlanDao reportPlanDao) {
        super(reportPlanDao);
        this.reportPlanDao = reportPlanDao;
    }
    @Autowired
	public void setReportPlanFilterManager(ReportPlanFilterManager reportPlanFilterManager) {
		this.reportPlanFilterManager = reportPlanFilterManager;
	}
    @Autowired
	public void setReportPlanItemManager(ReportPlanItemManager reportPlanItemManager) {
		this.reportPlanItemManager = reportPlanItemManager;
	}
    
    public JQueryPager getReportPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return reportPlanDao.getReportPlanCriteria(paginatedList,filters);
	}
    
    @Override
    public List<Map<String,Object>> getReportPlanGridData(String columns,List<String> sqlFilterList,ReportDefine reportDefine,Map<String,String> groupFilterMap,List<ReportType> reportTypes)  throws Exception{
    	return reportPlanDao.getReportPlanGridData(columns, sqlFilterList, reportDefine, groupFilterMap,reportTypes);
    }
	@Override
	public void removeReportPlan(String planId){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_planId",planId));
		List<ReportPlanItem> removeItemList = reportPlanItemManager.getByFilters(filters);
		List<ReportPlanFilter> removeFilterList = reportPlanFilterManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(removeItemList)&&!removeItemList.isEmpty()){
			for(ReportPlanItem itemTemp:removeItemList){
				reportPlanItemManager.remove(itemTemp.getColId());
			}
		}
		if(OtherUtil.measureNotNull(removeFilterList)&&!removeFilterList.isEmpty()){
			for(ReportPlanFilter filterTemp:removeFilterList){
				reportPlanFilterManager.remove(filterTemp.getFilterId());
			}
		}
		this.remove(planId);
	}
	@Override
	public ReportPlan saveReportPlan(ReportPlan reportPlan,String reportPlanItemsStr,String reportPlanFilterStr) throws Exception{
		/*删除同名的方案*/
		try{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_defineId",reportPlan.getDefineId()));
			filters.add(new PropertyFilter("EQS_typeId",reportPlan.getTypeId()));
			filters.add(new PropertyFilter("EQS_planName",reportPlan.getPlanName()));
			List<ReportPlan> removePlanList = reportPlanDao.getByFilters(filters);
//			String customLayout = null;
			if(OtherUtil.measureNotNull(removePlanList)&&!removePlanList.isEmpty()){
				String removePlanIdS = "";
				for(ReportPlan planTemp:removePlanList){
//					customLayout = planTemp.getCustomLayout();
					String planId = planTemp.getPlanId();
					removePlanIdS += planId + ",";
				}
				removePlanIdS = OtherUtil.subStrEnd(removePlanIdS, ",");
				filters.clear();
				filters.add(new PropertyFilter("INS_planId",removePlanIdS));
				List<ReportPlanItem> removeItemList = reportPlanItemManager.getByFilters(filters);
				List<ReportPlanFilter> removeFilterList = reportPlanFilterManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(removeItemList)&&!removeItemList.isEmpty()){
					for(ReportPlanItem itemTemp:removeItemList){
						reportPlanItemManager.remove(itemTemp.getColId());
					}
				}
				if(OtherUtil.measureNotNull(removeFilterList)&&!removeFilterList.isEmpty()){
					for(ReportPlanFilter filterTemp:removeFilterList){
						reportPlanFilterManager.remove(filterTemp.getFilterId());
					}
				}
				reportPlanDao.remove(removePlanIdS.split(","));
			}
			/*新方案*/
//			reportPlan.setCustomLayout(customLayout);
			reportPlan = reportPlanDao.save(reportPlan);
			String planId = reportPlan.getPlanId();
			List<ReportPlanFilter> filterList = new ArrayList<ReportPlanFilter>();
			if(OtherUtil.measureNotNull(reportPlanFilterStr)){
				JSONObject jsonObject = JSONObject.fromObject(reportPlanFilterStr);
				Iterator iter = jsonObject.keys();
				while(iter.hasNext()){
					  String key = (String) iter.next();
					  String value = jsonObject.get(key).toString();
					  ReportPlanFilter filter = new ReportPlanFilter();
					  filter.setPlanId(planId);
					  filter.setFilterCode(key);
					  filter.setFilterValue(value);
					  filterList.add(filter);
				}
				reportPlanFilterManager.saveAll(filterList);
			}
			if(OtherUtil.measureNotNull(reportPlanItemsStr)){
				List<ReportPlanItem> planItems = new ArrayList<ReportPlanItem>();
				JSONObject jsonObject = JSONObject.fromObject(reportPlanItemsStr);
				Iterator iter = jsonObject.keys();
				int snTemp = 1;
				while(iter.hasNext()){
					  String itemCode = (String) iter.next();
					  String valueStr = jsonObject.get(itemCode).toString();
					  JSONObject valueObject = JSONObject.fromObject(valueStr);
					  String name = valueObject.get("name").toString();
					  String isThousandSeparat = valueObject.get("isThousandSeparat").toString();
					  String fontIndex = valueObject.get("fontIndex").toString();
					  String headerFontIndex = valueObject.get("headerFontIndex").toString();
					  String headerTextColor = valueObject.get("headerTextColor").toString();
					  ReportPlanItem item = new ReportPlanItem();
					  item.setPlanId(planId);
					  item.setItemCode(itemCode);
					  item.setColName(name);
					  item.setColSn(snTemp++);
					  item.setIsThousandSeparat(isThousandSeparat);
					  item.setFontIndex(fontIndex);
					  item.setHeaderFontIndex(headerFontIndex);
					  item.setHeaderTextColor(headerTextColor);
					  planItems.add(item);
				}
				reportPlanItemManager.saveAll(planItems);
			}
			return reportPlan;
		}catch(Exception e){
			throw e;
		}
	}
	@Override
	public List<Map<String,Object>> getReportPlanReverseGridData(List<ReportItem> items,List<String> sqlFilterList,ReportDefine reportDefine) throws Exception{
		return reportPlanDao.getReportPlanReverseGridData(items, sqlFilterList, reportDefine);
	}
}