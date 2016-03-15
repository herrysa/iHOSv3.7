package com.huge.ihos.system.reportManager.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.SearchLinkDao;
import com.huge.ihos.system.reportManager.search.model.SearchLink;
import com.huge.ihos.system.reportManager.search.service.SearchLinkManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "searchLinkManager" )
public class SearchLinkManagerImpl
    extends GenericManagerImpl<SearchLink, String>
    implements SearchLinkManager {
    SearchLinkDao searchLinkDao;

    @Autowired
    public SearchLinkManagerImpl( SearchLinkDao searchLinkDao ) {
        super( searchLinkDao );
        this.searchLinkDao = searchLinkDao;
    }

    public JQueryPager getSearchLinkCriteria( JQueryPager paginatedList, String searchId ) {
        return searchLinkDao.getSearchLinkCriteria( paginatedList, searchId );
    }

}