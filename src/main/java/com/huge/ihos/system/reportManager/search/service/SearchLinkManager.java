package com.huge.ihos.system.reportManager.search.service;

import com.huge.ihos.system.reportManager.search.model.SearchLink;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SearchLinkManager
    extends GenericManager<SearchLink, String> {
    public JQueryPager getSearchLinkCriteria( final JQueryPager paginatedList, String searchId );
}