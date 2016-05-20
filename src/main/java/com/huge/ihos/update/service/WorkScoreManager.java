package com.huge.ihos.update.service;


import java.util.List;

import com.huge.ihos.update.model.WorkScore;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface WorkScoreManager extends GenericManager<WorkScore, String> {
     public JQueryPager getWorkScoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}