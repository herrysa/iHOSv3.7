package com.huge.ihos.system.systemManager.searchButtonPriv.service;


import java.util.List;

import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ButtonPrivUserDetailManager extends GenericManager<ButtonPrivUserDetail, String> {
     public JQueryPager getButtonPrivUserDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<ButtonPrivUserDetail> findByUserAndSearch(String userId,String searchId);
}