package com.huge.ihos.system.systemManager.period.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.period.dao.MoudlePeriodDao;
import com.huge.ihos.system.systemManager.period.model.MoudlePeriod;
import com.huge.ihos.system.systemManager.period.service.MoudlePeriodManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("moudlePeriodManager")
public class MoudlePeriodManagerImpl extends GenericManagerImpl<MoudlePeriod, String> implements MoudlePeriodManager {
    private MoudlePeriodDao moudlePeriodDao;

    @Autowired
    public MoudlePeriodManagerImpl(MoudlePeriodDao moudlePeriodDao) {
        super(moudlePeriodDao);
        this.moudlePeriodDao = moudlePeriodDao;
    }
    
    public JQueryPager getMoudlePeriodCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return moudlePeriodDao.getMoudlePeriodCriteria(paginatedList,filters);
	}

	@Override
	public void removeBySubId(String periodSubject_periodId) {
		moudlePeriodDao.removeBySubId(periodSubject_periodId);
		
	}

	@Override
	public List<MoudlePeriod> getMoudlePeriod(String planId, String periodSubId,
			String moudleId) {
		return moudlePeriodDao.getMoudlePeriod(planId, periodSubId, moudleId);
	}
}