package com.huge.ihos.material.service;


import java.util.List;
import com.huge.ihos.material.model.InvOutStore;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvOutStoreManager extends GenericManager<InvOutStore, String> {
     public JQueryPager getInvOutStoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}