package com.huge.ihos.system.reportManager.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.SearchUrlDao;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.service.SearchUrlManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "searchUrlManager" )
public class SearchUrlManagerImpl
    extends GenericManagerImpl<SearchUrl, String>
    implements SearchUrlManager {
    SearchUrlDao searchUrlDao;

    @Autowired
    public SearchUrlManagerImpl( SearchUrlDao searchUrlDao ) {
        super( searchUrlDao );
        this.searchUrlDao = searchUrlDao;
    }

    public JQueryPager getSearchUrlCriteria( JQueryPager paginatedList, String searchId ) {
        return searchUrlDao.getSearchUrlCriteria( paginatedList, searchId );
    }

}