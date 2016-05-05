package com.huge.ihos.kq.kqUpData.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.ihos.kq.kqUpData.dao.KqDayDataDao;
import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.ihos.kq.kqUpData.service.KqDayDataManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqDayDataManager")
public class KqDayDataManagerImpl extends GenericManagerImpl<KqDayData, String> implements KqDayDataManager {
    private KqDayDataDao kqDayDataDao;

    @Autowired
    public KqDayDataManagerImpl(KqDayDataDao kqDayDataDao) {
        super(kqDayDataDao);
        this.kqDayDataDao = kqDayDataDao;
    }
    
    public JQueryPager getKqDayDataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqDayDataDao.getKqDayDataCriteria(paginatedList,filters);
	}
    @Override
    public List<Map<String,Object>> getKqDayDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList){
    	return kqDayDataDao.getKqDayDataGridData(columns, lastPeriod, sqlFilterList, sqlOrderList);
    }
    @Override
    public List<Map<String, Object>> getKqSumaryDayData(String curPeriod,String kqTypeId,String curDeptId){
    	return kqDayDataDao.getKqSumaryDayData(curPeriod, kqTypeId, curDeptId);
    }
    
    @Override
    public Boolean getKqDayDataChecked(String period,String kqType, String branchCode, String status) {
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_period",period));
		filters.add(new PropertyFilter("EQS_kqType",kqType));
		filters.add(new PropertyFilter("EQS_branchCode",branchCode));
		if(status != null && !"".equals(status)) {
			filters.add(new PropertyFilter("INS_status",status));
		}
		List<KqDayData> kqDayDatas = kqDayDataDao.getByFilters(filters);
		if(OtherUtil.measureNotNull(kqDayDatas)&&!kqDayDatas.isEmpty()){
			return false;
		}else{
			return true;
		}
    }
}