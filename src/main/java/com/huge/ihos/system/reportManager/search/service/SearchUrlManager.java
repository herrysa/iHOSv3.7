package com.huge.ihos.system.reportManager.search.service;

import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SearchUrlManager
    extends GenericManager<SearchUrl, String> {
    public JQueryPager getSearchUrlCriteria( final JQueryPager paginatedList, String searchId );
}