package com.huge.ihos.material.service;


import java.util.List;
import com.huge.ihos.material.model.InvInStore;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InvInStoreManager extends GenericManager<InvInStore, String> {
     public JQueryPager getInvInStoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}