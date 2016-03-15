package com.huge.ihos.nursescore.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface NurseDayScoreManager extends GenericManager<NurseDayScore, Long> {
     public JQueryPager getNurseDayScoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<NurseDayScore> findByScoreDate(String deptId,Date date);
     
     public boolean delNurseDayscore(Long dayScoreID);
}