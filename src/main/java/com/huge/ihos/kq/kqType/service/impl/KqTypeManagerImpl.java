package com.huge.ihos.kq.kqType.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.ihos.kq.kqItem.service.KqItemManager;
import com.huge.ihos.kq.kqType.dao.KqTypeDao;
import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.kq.kqType.service.KqTypeManager;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.service.KqUpItemManager;
import com.huge.ihos.system.configuration.modelstatus.dao.ModelStatusDao;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqTypeManager")
public class KqTypeManagerImpl extends GenericManagerImpl<KqType, String> implements KqTypeManager {
    private KqTypeDao kqTypeDao;
    private KqItemManager kqItemManager;
    private KqUpItemManager kqUpItemManager;
    private ModelStatusDao modelStatusDao;
    
    @Autowired
	public void setModelStatusDao(ModelStatusDao modelStatusDao) {
		this.modelStatusDao = modelStatusDao;
	}
	@Autowired
    public KqTypeManagerImpl(KqTypeDao kqTypeDao) {
        super(kqTypeDao);
        this.kqTypeDao = kqTypeDao;
    }
    @Autowired
    public void setKqUpItemManager(KqUpItemManager kqUpItemManager) {
		this.kqUpItemManager = kqUpItemManager;
	}
    @Autowired
	public void setKqItemManager(KqItemManager kqItemManager) {
		this.kqItemManager = kqItemManager;
	}
    public JQueryPager getKqTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqTypeDao.getKqTypeCriteria(paginatedList,filters);
	}
    @Override
    public KqType getNowKqType() {
    	return null;
    }
    @Override
    public List<KqType> allKqTypes(Boolean containDisable){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    	filters.add(new PropertyFilter("NES_kqTypeId","XT"));
    	if(!containDisable){
    		filters.add(new PropertyFilter("EQB_disabled","0"));
    	}
    	return kqTypeDao.getByFilters(filters);
    }
    @Override
    public KqType saveKqType(KqType kqType,String oper){
    	kqType = kqTypeDao.save(kqType);
    	if("initKqUpItem".equals(oper)){
    		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_kqType.kqTypeId","XT"));
    		filters.add(new PropertyFilter("EQB_disabled","0"));
    		filters.add(new PropertyFilter("EQB_sysField","1"));
    		filters.add(new PropertyFilter("OAI_sn",""));
    		List<KqUpItem> kqUpItems = kqUpItemManager.getByFilters(filters);
    		int sn = 0;
    		List<KqUpItem> needSaveItems = new ArrayList<KqUpItem>();
    		if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
    			for(KqUpItem kqUpItem:kqUpItems){
    				KqUpItem kqUpItemTemp = kqUpItem.clone();
    				kqUpItemTemp.setItemId(null);
    				kqUpItemTemp.setKqType(kqType);
    				if(OtherUtil.measureNotNull(kqUpItemTemp.getSn())&&kqUpItemTemp.getSn()>sn){
    					sn = kqUpItemTemp.getSn();
    				}
    				needSaveItems.add(kqUpItemTemp);
    			}
    		}
    		List<KqItem> kqItems = kqItemManager.getLeafKqItems();
    		List<String> sqlList = new ArrayList<String>();
    		if(OtherUtil.measureNotNull(kqItems)&&!kqItems.isEmpty()){
    			for(KqItem kqItem:kqItems){
    				sn ++;
    				kqItem.setIsUsed(true);
    				KqUpItem kqUpItemTemp = new KqUpItem();
    				String itemCode = "kqItem_" + sn;
    				kqUpItemTemp.setSn(sn);
    				kqUpItemTemp.setItemCode(itemCode);
    				kqUpItemTemp.setKqType(kqType);
    				kqUpItemTemp.setItemType("0");
    				kqUpItemTemp.setItemLength(18);
    				kqUpItemTemp.setScale(2);
    				kqUpItemTemp.setCalculateType("0");
    				kqUpItemTemp.setItemName(kqItem.getKqItemName());
    				kqUpItemTemp.setShowType("kqItem");
    				needSaveItems.add(kqUpItemTemp);
    				Boolean isColumnExist = this.getIsDBColumnExist("kq_dayData", itemCode);
    				if(!isColumnExist){
    					String sqlTemp = "ALTER TABLE kq_dayData ADD "+itemCode+" decimal(18,2)";
    					sqlList.add(sqlTemp);
    				}
    			}
    			kqItemManager.saveAll(kqItems);
    		}
    		kqUpItemManager.saveAll(needSaveItems);
    		kqUpItemManager.executeSqlList(sqlList);
    	}
    	return kqType;
    }
    
	@Override
	public ModelStatus kqModelStatusClose(String id,String nextPeriod,String oper){
		ModelStatus mStatus = modelStatusDao.get(id);
		mStatus.setStatus("2");
		modelStatusDao.save(mStatus);
		if("anti".equals(oper)){
			return mStatus;
		}else if("closed".equals(oper)){
			if(OtherUtil.measureNull(nextPeriod)){
				return mStatus;
			}else{
				String period = mStatus.getCheckperiod();
				int checkNumber = mStatus.getCheckNumber();
				if(period.substring(0, 4).equals(nextPeriod.substring(0, 4))){
					checkNumber = checkNumber + 1;
				}else{
					checkNumber = 1;
				}
				ModelStatus mStatusNew = new ModelStatus();
				mStatusNew.setCheckNumber(checkNumber);
				mStatusNew.setCheckperiod(nextPeriod);
				mStatusNew.setModelId(mStatus.getModelId());
				mStatusNew.setPeriodType("月");
				mStatusNew.setStatus("1");
				return modelStatusDao.save(mStatusNew);
			}
		}
		return mStatus;
	}
	
	@Override
	public List<KqType> getAllAvailable(String kqTypePriv) {
		return this.kqTypeDao.getAllAvailable(kqTypePriv);
	}
	
	@Override
	public List<KqType> getAllAvailable() {
		return this.kqTypeDao.getAllAvailable(null);
	}
}