package com.huge.ihos.singletest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.singletest.dao.SingleEntitySampleDao;
import com.huge.ihos.singletest.model.SingleEntitySample;
import com.huge.ihos.singletest.service.SingleEntitySampleManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "singleEntitySampleManager" )
public class SingleEntitySampleManagerImpl
    extends GenericManagerImpl<SingleEntitySample, Long>
    implements SingleEntitySampleManager {
    SingleEntitySampleDao singleEntitySampleDao;

    @Autowired
    public SingleEntitySampleManagerImpl( SingleEntitySampleDao singleEntitySampleDao ) {
        super( singleEntitySampleDao );
        this.singleEntitySampleDao = singleEntitySampleDao;
    }

    public JQueryPager getSingleEntitySampleCriteria( JQueryPager paginatedList ) {
        return singleEntitySampleDao.getSingleEntitySampleCriteria( paginatedList );
    }

}