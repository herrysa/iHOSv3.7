package com.huge.ihos.system.reportManager.search.service;

import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SearchOptionManager
    extends GenericManager<SearchOption, String> {
    public JQueryPager getSearchOptionCriteria( final JQueryPager paginatedList, String searchId );
}