package com.huge.ihos.kq.kqUpData.service.impl;

import java.util.List;
import java.util.Map;

import com.huge.ihos.kq.kqUpData.dao.KqMonthDataDao;
import com.huge.ihos.kq.kqUpData.model.KqMonthData;
import com.huge.ihos.kq.kqUpData.service.KqMonthDataManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("kqMonthDataManager")
public class KqMonthDataManagerImpl extends GenericManagerImpl<KqMonthData, String> implements KqMonthDataManager {
    private KqMonthDataDao kqMonthDataDao;

    @Autowired
    public KqMonthDataManagerImpl(KqMonthDataDao kqMonthDataDao) {
        super(kqMonthDataDao);
        this.kqMonthDataDao = kqMonthDataDao;
    }
    
    public JQueryPager getKqMonthDataCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return kqMonthDataDao.getKqMonthDataCriteria(paginatedList,filters);
	}
    @Override
    public List<Map<String,Object>> getKqMonthDataGridData(String columns,String lastPeriod,List<String> sqlFilterList,List<String> sqlOrderList){
    	return kqMonthDataDao.getKqMonthDataGridData(columns, lastPeriod, sqlFilterList, sqlOrderList);
    }
    
}