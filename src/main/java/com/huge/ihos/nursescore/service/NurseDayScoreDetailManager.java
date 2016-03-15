package com.huge.ihos.nursescore.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.ihos.nursescore.model.NurseDayScoreDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface NurseDayScoreDetailManager extends GenericManager<NurseDayScoreDetail, Long> {
     public JQueryPager getNurseDayScoreDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List addByDeptId(String deptId,String checkPeriod,String dayScoreID);
     
     public List<NurseDayScoreDetail> selectedPerson(Date score,String deptId);
     
     public List<NurseDayScoreDetail> findByNurseDayScore(NurseDayScore nurseDayScore);
}