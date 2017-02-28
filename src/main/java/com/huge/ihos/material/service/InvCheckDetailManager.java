package com.huge.ihos.material.service;


import java.util.List;

import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvCheckDetailManager extends GenericManager<InvCheckDetail, String> {
     public JQueryPager getInvCheckDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<InvCheckDetail> getInvCheckDetailsInSameCheck(InvCheck invCheck);
     public void addInvDictToCheckDetail(String batchIds,InvCheck invCheck);
}