package com.huge.ihos.nursescore.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.nursescore.dao.NurseDayScoreDetailDao;
import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.ihos.nursescore.model.NurseDayScoreDetail;
import com.huge.ihos.nursescore.service.NurseDayScoreDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("nurseDayScoreDetailManager")
public class NurseDayScoreDetailManagerImpl extends GenericManagerImpl<NurseDayScoreDetail, Long> implements NurseDayScoreDetailManager {
    private NurseDayScoreDetailDao nurseDayScoreDetailDao;

    @Autowired
    public NurseDayScoreDetailManagerImpl(NurseDayScoreDetailDao nurseDayScoreDetailDao) {
        super(nurseDayScoreDetailDao);
        this.nurseDayScoreDetailDao = nurseDayScoreDetailDao;
    }
    
    public JQueryPager getNurseDayScoreDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return nurseDayScoreDetailDao.getNurseDayScoreDetailCriteria(paginatedList,filters);
	}

	@Override
	public List addByDeptId(String deptId, String checkPeriod,String dayScoreID) {
		return nurseDayScoreDetailDao.addByDeptId(deptId, checkPeriod,dayScoreID);
	}

	@Override
	public List<NurseDayScoreDetail> selectedPerson(Date score, String deptId) {
		return nurseDayScoreDetailDao.selectedPerson(score, deptId);
	}

	@Override
	public List<NurseDayScoreDetail> findByNurseDayScore(
			NurseDayScore nurseDayScore) {
		return nurseDayScoreDetailDao.findByNurseDayScore(nurseDayScore);
	}
}