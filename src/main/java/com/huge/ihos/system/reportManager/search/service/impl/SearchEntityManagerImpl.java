package com.huge.ihos.system.reportManager.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.SearchEntityDao;
import com.huge.ihos.system.reportManager.search.model.SearchEntity;
import com.huge.ihos.system.reportManager.search.service.SearchEntityManager;
import com.huge.service.impl.GenericManagerImpl;

@Service( "searchEntityManager" )
public class SearchEntityManagerImpl
    extends GenericManagerImpl<SearchEntity, Long>
    implements SearchEntityManager {
    SearchEntityDao searchEntityDao;

    @Autowired
    public SearchEntityManagerImpl( SearchEntityDao searchEntityDao ) {
        super( searchEntityDao );
        this.searchEntityDao = searchEntityDao;
    }

}
