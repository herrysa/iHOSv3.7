package com.huge.ihos.nursescore.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.nursescore.dao.NurseDayScoreDao;
import com.huge.ihos.nursescore.dao.NurseDayScoreDetailDao;
import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.ihos.nursescore.model.NurseDayScoreDetail;
import com.huge.ihos.nursescore.service.NurseDayScoreManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("nurseDayScoreManager")
public class NurseDayScoreManagerImpl extends GenericManagerImpl<NurseDayScore, Long> implements NurseDayScoreManager {
    private NurseDayScoreDao nurseDayScoreDao;
    private NurseDayScoreDetailDao nurseDayScoreDetailDao;

    @Autowired
    public NurseDayScoreManagerImpl(NurseDayScoreDao nurseDayScoreDao,NurseDayScoreDetailDao nurseDayScoreDetailDao) {
        super(nurseDayScoreDao);
        this.nurseDayScoreDao = nurseDayScoreDao;
        this.nurseDayScoreDetailDao = nurseDayScoreDetailDao;
    }
    
    public JQueryPager getNurseDayScoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return nurseDayScoreDao.getNurseDayScoreCriteria(paginatedList,filters);
	}

	@Override
	public List<NurseDayScore> findByScoreDate(String deptId,Date date) {
		return nurseDayScoreDao.findByScoreDate(deptId,date);
	}

	@Override
	public boolean delNurseDayscore(Long dayScoreID) {
		try {
			nurseDayScoreDao.get(dayScoreID);
			List<NurseDayScoreDetail> nurseDayScoreDetails = nurseDayScoreDetailDao.findByNurseDayScore(nurseDayScoreDao.get(dayScoreID));
			for(NurseDayScoreDetail nurseDayScoreDetail : nurseDayScoreDetails){
				nurseDayScoreDetailDao.remove(nurseDayScoreDetail.getDayScoreDetailID());
			}
			nurseDayScoreDao.remove(dayScoreID);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}