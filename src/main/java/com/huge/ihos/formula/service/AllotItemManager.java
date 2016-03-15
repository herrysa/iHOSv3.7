package com.huge.ihos.formula.service;

import com.huge.ihos.formula.model.AllotItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface AllotItemManager
    extends GenericManager<AllotItem, Long> {
    public JQueryPager getAllotItemCriteria( final JQueryPager paginatedList );
}