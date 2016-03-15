package com.huge.ihos.system.reportManager.search.service;

import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SearchItemManager
    extends GenericManager<SearchItem, String> {
    public JQueryPager getSearchItemCriteria( final JQueryPager paginatedList, String parentId );
}