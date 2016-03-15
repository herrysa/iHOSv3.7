package com.huge.ihos.system.systemManager.searchButtonPriv.service;


import java.util.List;

import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ButtonPrivDetailManager extends GenericManager<ButtonPrivDetail, String> {
     public JQueryPager getButtonPrivDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public void cascadeUpdate(List<ButtonPrivDetail> buttoPrivDetails);
}