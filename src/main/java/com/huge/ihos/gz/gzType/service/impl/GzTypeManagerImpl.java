package com.huge.ihos.gz.gzType.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzType.dao.GzTypeDao;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.system.configuration.modelstatus.dao.ModelStatusDao;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("gzTypeManager")
public class GzTypeManagerImpl extends GenericManagerImpl<GzType, String> implements GzTypeManager {
    private GzTypeDao gzTypeDao;
    private ModelStatusDao modelStatusDao;
    private GzItemManager gzItemManager;
    
    @Autowired
    public GzTypeManagerImpl(GzTypeDao gzTypeDao) {
        super(gzTypeDao);
        this.gzTypeDao = gzTypeDao;
    }
    @Autowired
    public void setGzItemManager(GzItemManager gzItemManager) {
		this.gzItemManager = gzItemManager;
	}
    @Autowired
    public void setModelStatusDao(ModelStatusDao modelStatusDao) {
		this.modelStatusDao = modelStatusDao;
	}
    public JQueryPager getGzTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return gzTypeDao.getGzTypeCriteria(paginatedList,filters);
	}
    @Override
    public GzType getCurrentGzType(String userId){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("EQB_disabled","0"));
    	List<GzType> gzTypes = gzTypeDao.getByFilters(filters);
    	if(OtherUtil.measureNotNull(gzTypes)&&!gzTypes.isEmpty()){
    		for(GzType gzType:gzTypes){
    			String gzUserIds = gzType.getGzUserIds();
    			if(OtherUtil.measureNotNull(gzUserIds)){
    				String[] userIdArr = gzUserIds.split(",");
    				List<String> userIdList = Arrays.asList(userIdArr);
    				if(userIdList.contains(userId)){
    					return gzType;
    				}
    			}
    		}
    	}
    	return null;
    }
    @Override
    public void changeCurrentGzType(String userId,String[] changeIdArr){
    	List<GzType> gzTypes = new ArrayList<GzType>();
    	GzType curGzType = this.getCurrentGzType(userId);
    	String userIdStr = "";
    	if(OtherUtil.measureNotNull(curGzType)){
    		String gzUserIds = curGzType.getGzUserIds();
    		if(OtherUtil.measureNotNull(gzUserIds)){
    			String[] userIdArr = gzUserIds.split(",");
    			userIdStr = "";
    			for(String userIdTemp:userIdArr){
    				if(!userId.equals(userIdTemp)){
    					userIdStr += userIdTemp+",";
    				}
    			}
    			if(OtherUtil.measureNotNull(userIdStr)){
    				userIdStr = OtherUtil.subStrEnd(userIdStr, ",");
    			}
    			curGzType.setGzUserIds(userIdStr);
    			gzTypes.add(curGzType);
    		}
    	}
    	if (OtherUtil.measureNotNull(changeIdArr)) {
			for(String changeId:changeIdArr){
				GzType gzType = gzTypeDao.get(changeId);
				String gzUserIds = gzType.getGzUserIds();
				if(OtherUtil.measureNotNull(gzUserIds)){
					gzUserIds += "," + userId;
				}else{
					gzUserIds = userId;
				}
				gzType.setGzUserIds(gzUserIds);
				gzTypes.add(gzType);
			}
		}
    	this.saveAll(gzTypes);
    }
    @Override
    public void removeGzType(List<String> removeIds){
    	if(OtherUtil.measureNotNull(removeIds)&&!removeIds.isEmpty()){
    		for(String removeId:removeIds){
    			String modelId = "GZ_" + removeId;
    	    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	    	filters.add(new PropertyFilter("EQS_modelId",modelId));
    	    	List<ModelStatus> modelStatusList = modelStatusDao.getByFilters(filters);
    	    	if(OtherUtil.measureNotNull(modelStatusList)&&!modelStatusList.isEmpty()){
    	    		for(ModelStatus msTemp:modelStatusList){
    	    			modelStatusDao.remove(msTemp.getId());
    	    		}
    	    	}
    	    	filters.clear();
    	    	filters.add(new PropertyFilter("EQS_gzType.gzTypeId",removeId));
    	    	List<GzItem> gzItems = gzItemManager.getByFilters(filters);
    	    	if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
    	    		for(GzItem gzItemTemp:gzItems){
    	    			gzItemManager.remove(gzItemTemp.getItemId());
    	    		}
    	    	}
    			gzTypeDao.remove(removeId);
    		}
    	}
    }
    @Override
    public GzType savaGzType(GzType gzType,String oper,Boolean entityIsNew){
    	gzType = gzTypeDao.save(gzType);
    	if("initGzItem".equals(oper)){
    		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_gzType.gzTypeId","XT"));
    		filters.add(new PropertyFilter("EQB_disabled","0"));
    		filters.add(new PropertyFilter("EQB_sysField","1"));
    		filters.add(new PropertyFilter("OAI_sn",""));
    		List<GzItem> gzItems = gzItemManager.getByFilters(filters);
    		if(OtherUtil.measureNotNull(gzItems)&&!gzItems.isEmpty()){
    			List<GzItem> gzItemList = new ArrayList<GzItem>();
    			for(GzItem gzItemTemp:gzItems){
    				GzItem gzItem = gzItemTemp.clone();
    				gzItem.setItemId(null);
    				gzItem.setGzType(gzType);
    				gzItemList.add(gzItem);
    			}
    			gzItemManager.saveAll(gzItemList);
    		}
    	}
    	if(entityIsNew){
    		String gzTypeId = gzType.getGzTypeId();
    		String modelId = "GZ_" + gzTypeId;
    		ModelStatus mStatus = new ModelStatus();
    		mStatus.setModelId(modelId);
    		mStatus.setPeriodType("月");
    		mStatus.setCheckNumber(gzType.getIssueNumber());
    		mStatus.setCheckperiod(gzType.getIssueDate());
    		mStatus.setStatus("1");
    		modelStatusDao.save(mStatus);
    	}
    	return gzType;
    }
    @Override
    public List<GzType> allGzTypes(Boolean containDisabled){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("NES_gzTypeId","XT"));
		if(!containDisabled){
			filters.add(new PropertyFilter("EQB_disabled","0"));
		}
		return gzTypeDao.getByFilters(filters);
    }
    @Override
    public List<GzType> getAllAvailable() {
    	return this.gzTypeDao.getAllAvailable(null);
    }
    @Override
    public List<GzType> getAllAvailable(String gzTypePriv) {
    	return this.gzTypeDao.getAllAvailable(gzTypePriv);
    }
}