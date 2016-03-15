package com.huge.ihos.gz.gzAccount.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huge.ihos.gz.gzAccount.dao.GzAccountPlanItemDao;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanItem;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzAccountPlanItemManager")
public class GzAccountPlanItemManagerImpl extends GenericManagerImpl<GzAccountPlanItem, String> implements GzAccountPlanItemManager {
    private GzAccountPlanItemDao gzAccountPlanItemDao;

    @Autowired
    public GzAccountPlanItemManagerImpl(GzAccountPlanItemDao gzAccountPlanItemDao) {
        super(gzAccountPlanItemDao);
        this.gzAccountPlanItemDao = gzAccountPlanItemDao;
    }
    
    public JQueryPager getGzAccountPlanItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzAccountPlanItemDao.getGzAccountPlanItemCriteria(paginatedList,filters);
	}
    public void saveGzAccountPlanItem(String gridAllDatas,String planId){
    	if(OtherUtil.measureNull(planId)||OtherUtil.measureNull(gridAllDatas)){
    		return;
    	}
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQS_planId",planId));
    	List<GzAccountPlanItem> planItems = gzAccountPlanItemDao.getByFilters(filters);
    	List<String> idList = new ArrayList<String>();
    	if(OtherUtil.measureNotNull(planItems)&&!planItems.isEmpty()){
    		for(GzAccountPlanItem planItemTemp:planItems){
    			idList.add(planItemTemp.getColId());
    		}
    	}
    	planItems = new ArrayList<GzAccountPlanItem>();
    	JSONObject json = JSONObject.fromObject(gridAllDatas);
    	JSONArray allDatas = json.getJSONArray("edit");
    	for(int i=0;i<allDatas.size();i++){
   		 //获取每一个JsonObject对象
		    JSONObject myjObject = allDatas.getJSONObject(i);
			if(myjObject.size()>0){
				GzAccountPlanItem planItemTemp = new GzAccountPlanItem();
				String colId = myjObject.getString("colId");
				if(colId.startsWith("planItem")){
					planItemTemp.setColId(null);
				}else{
					planItemTemp.setColId(colId);
					idList.remove(colId);
				}
				planItemTemp.setColName(myjObject.getString("colName"));
				planItemTemp.setColSn(Integer.parseInt(myjObject.getString("colSn")));
				planItemTemp.setColWidth(Integer.parseInt(myjObject.getString("colWidth")));
				planItemTemp.setItemCode(myjObject.getString("itemCode"));
				planItemTemp.setPlanId(planId);
				planItems.add(planItemTemp);
			}
    	}
    	String[] ida=new String[idList.size()];
    	idList.toArray(ida);
    	this.remove(ida);
    	this.saveAll(planItems);
    }
}