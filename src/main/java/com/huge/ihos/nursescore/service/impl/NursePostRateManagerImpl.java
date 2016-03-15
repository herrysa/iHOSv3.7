package com.huge.ihos.nursescore.service.impl;

import java.util.List;
import com.huge.ihos.nursescore.dao.NursePostRateDao;
import com.huge.ihos.nursescore.model.NursePostRate;
import com.huge.ihos.nursescore.service.NursePostRateManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("nursePostRateManager")
public class NursePostRateManagerImpl extends GenericManagerImpl<NursePostRate, String> implements NursePostRateManager {
    private NursePostRateDao nursePostRateDao;

    @Autowired
    public NursePostRateManagerImpl(NursePostRateDao nursePostRateDao) {
        super(nursePostRateDao);
        this.nursePostRateDao = nursePostRateDao;
    }
    
    public JQueryPager getNursePostRateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return nursePostRateDao.getNursePostRateCriteria(paginatedList,filters);
	}
}