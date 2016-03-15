package com.huge.ihos.nursescore.service.impl;

import java.util.List;
import com.huge.ihos.nursescore.dao.NurseYearRateDao;
import com.huge.ihos.nursescore.model.NurseYearRate;
import com.huge.ihos.nursescore.service.NurseYearRateManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("nurseYearRateManager")
public class NurseYearRateManagerImpl extends GenericManagerImpl<NurseYearRate, String> implements NurseYearRateManager {
    private NurseYearRateDao nurseYearRateDao;

    @Autowired
    public NurseYearRateManagerImpl(NurseYearRateDao nurseYearRateDao) {
        super(nurseYearRateDao);
        this.nurseYearRateDao = nurseYearRateDao;
    }
    
    public JQueryPager getNurseYearRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return nurseYearRateDao.getNurseYearRateCriteria(paginatedList,filters);
	}
}