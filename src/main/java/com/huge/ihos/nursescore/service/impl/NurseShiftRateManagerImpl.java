package com.huge.ihos.nursescore.service.impl;

import java.util.List;
import com.huge.ihos.nursescore.dao.NurseShiftRateDao;
import com.huge.ihos.nursescore.model.NurseShiftRate;
import com.huge.ihos.nursescore.service.NurseShiftRateManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("nurseShiftRateManager")
public class NurseShiftRateManagerImpl extends GenericManagerImpl<NurseShiftRate, String> implements NurseShiftRateManager {
    private NurseShiftRateDao nurseShiftRateDao;

    @Autowired
    public NurseShiftRateManagerImpl(NurseShiftRateDao nurseShiftRateDao) {
        super(nurseShiftRateDao);
        this.nurseShiftRateDao = nurseShiftRateDao;
    }
    
    public JQueryPager getNurseShiftRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return nurseShiftRateDao.getNurseShiftRateCriteria(paginatedList,filters);
	}
}