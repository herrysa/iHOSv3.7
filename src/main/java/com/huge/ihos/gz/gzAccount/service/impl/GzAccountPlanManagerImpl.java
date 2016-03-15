package com.huge.ihos.gz.gzAccount.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.gz.gzAccount.dao.GzAccountPlanDao;
import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlan;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanFilter;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanItem;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanFilterManager;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanItemManager;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanManager;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzAccountPlanManager")
public class GzAccountPlanManagerImpl extends GenericManagerImpl<GzAccountPlan, String> implements GzAccountPlanManager {
    private GzAccountPlanDao gzAccountPlanDao;
    private GzAccountPlanFilterManager gzAccountPlanFilterManager;
    private GzAccountPlanItemManager gzAccountPlanItemManager;

    @Autowired
    public GzAccountPlanManagerImpl(GzAccountPlanDao gzAccountPlanDao) {
        super(gzAccountPlanDao);
        this.gzAccountPlanDao = gzAccountPlanDao;
    }
    @Autowired
    public void setGzAccountPlanFilterManager(GzAccountPlanFilterManager gzAccountPlanFilterManager) {
		this.gzAccountPlanFilterManager = gzAccountPlanFilterManager;
	}
    @Autowired
	public void setGzAccountPlanItemManager(GzAccountPlanItemManager gzAccountPlanItemManager) {
		this.gzAccountPlanItemManager = gzAccountPlanItemManager;
	}
    public JQueryPager getGzAccountPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzAccountPlanDao.getGzAccountPlanCriteria(paginatedList,filters);
	}

	@Override
	public List<Map<String,Object>> getGzAccountGridData(String columns,List<String> sqlFilterList,GzAccountDefine gzAccountDefine,Map<String,String> groupFilterMap) throws Exception{
		return gzAccountPlanDao.getGzAccountGridData(columns, sqlFilterList,gzAccountDefine,groupFilterMap);
	}
	@Override
	public List<Map<String,Object>> getGzAccountReverseGridData(List<GzItem> items,List<String> sqlFilterList,GzAccountDefine gzAccountDefine)  throws Exception{
		return gzAccountPlanDao.getGzAccountReverseGridData(items, sqlFilterList, gzAccountDefine);
	}
	@Override
	public GzAccountPlan saveGzAccountPlan(String defineId,String gzTypeId,String planName,String toPublic,String toDepartment,String toRole,String gzAccountItemsStr,String gzAccountFilterStr,String userId) throws Exception{
		/*删除同名的方案*/
		try{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_defineId",defineId));
			filters.add(new PropertyFilter("EQS_gzTypeId",gzTypeId));
			filters.add(new PropertyFilter("EQS_planName",planName));
			List<GzAccountPlan> removePlanList = gzAccountPlanDao.getByFilters(filters);
			String customLayout = null;
			if(OtherUtil.measureNotNull(removePlanList)&&!removePlanList.isEmpty()){
				String removePlanIdS = "";
				for(GzAccountPlan planTemp:removePlanList){
					customLayout = planTemp.getCustomLayout();
					String planId = planTemp.getPlanId();
					removePlanIdS += planId + ",";
				}
				removePlanIdS = OtherUtil.subStrEnd(removePlanIdS, ",");
				filters.clear();
				filters.add(new PropertyFilter("INS_planId",removePlanIdS));
				List<GzAccountPlanItem> removeItemList = gzAccountPlanItemManager.getByFilters(filters);
				List<GzAccountPlanFilter> removeFilterList = gzAccountPlanFilterManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(removeItemList)&&!removeItemList.isEmpty()){
					for(GzAccountPlanItem itemTemp:removeItemList){
						gzAccountPlanItemManager.remove(itemTemp.getColId());
					}
				}
				if(OtherUtil.measureNotNull(removeFilterList)&&!removeFilterList.isEmpty()){
					for(GzAccountPlanFilter filterTemp:removeFilterList){
						gzAccountPlanFilterManager.remove(filterTemp.getFilterId());
					}
				}
				gzAccountPlanDao.remove(removePlanIdS.split(","));
			}
			/*新方案*/
			GzAccountPlan gzPlan = new GzAccountPlan();
			gzPlan.setDefineId(defineId);
			gzPlan.setPlanName(planName);
			gzPlan.setToPublic(toPublic);
			gzPlan.setToDepartment(toDepartment);
			gzPlan.setToRole(toRole);
			gzPlan.setUserId(userId);
			gzPlan.setGzTypeId(gzTypeId);
			gzPlan.setCustomLayout(customLayout);
			gzPlan = gzAccountPlanDao.save(gzPlan);
			String planId = gzPlan.getPlanId();
			List<GzAccountPlanFilter> filterList = new ArrayList<GzAccountPlanFilter>();
			if(OtherUtil.measureNotNull(gzAccountFilterStr)){
				JSONObject jsonObject = JSONObject.fromObject(gzAccountFilterStr);
				Iterator iter = jsonObject.keys();
				while(iter.hasNext()){
					  String key = (String) iter.next();
					  String value = jsonObject.get(key).toString();
					  GzAccountPlanFilter filter = new GzAccountPlanFilter();
					  filter.setPlanId(planId);
					  filter.setFilterCode(key);
					  filter.setFilterValue(value);
					  filterList.add(filter);
				}
				gzAccountPlanFilterManager.saveAll(filterList);
			}
			if(OtherUtil.measureNotNull(gzAccountItemsStr)){
				List<GzAccountPlanItem> planItems = new ArrayList<GzAccountPlanItem>();
				JSONObject jsonObject = JSONObject.fromObject(gzAccountItemsStr);
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
					  GzAccountPlanItem item = new GzAccountPlanItem();
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
				gzAccountPlanItemManager.saveAll(planItems);
			}
			return gzPlan;
		}catch(Exception e){
			throw e;
		}
	}
	@Override
	public void removeGzAccountPlan(String planId){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_planId",planId));
		List<GzAccountPlanItem> removeItemList = gzAccountPlanItemManager.getByFilters(filters);
		List<GzAccountPlanFilter> removeFilterList = gzAccountPlanFilterManager.getByFilters(filters);
		if(OtherUtil.measureNotNull(removeItemList)&&!removeItemList.isEmpty()){
			for(GzAccountPlanItem itemTemp:removeItemList){
				gzAccountPlanItemManager.remove(itemTemp.getColId());
			}
		}
		if(OtherUtil.measureNotNull(removeFilterList)&&!removeFilterList.isEmpty()){
			for(GzAccountPlanFilter filterTemp:removeFilterList){
				gzAccountPlanFilterManager.remove(filterTemp.getFilterId());
			}
		}
		this.remove(planId);
	}
}