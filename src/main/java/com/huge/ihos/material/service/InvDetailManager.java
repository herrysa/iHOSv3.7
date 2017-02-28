package com.huge.ihos.material.service;


import java.util.List;

import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvDetailManager extends GenericManager<InvDetail, String> {
     public JQueryPager getInvDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<InvDetail> getInvDetailsInSameInvMain(InvMain invMain);
}