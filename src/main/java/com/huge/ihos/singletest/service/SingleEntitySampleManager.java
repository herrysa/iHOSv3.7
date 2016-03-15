package com.huge.ihos.singletest.service;

import com.huge.ihos.singletest.model.SingleEntitySample;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SingleEntitySampleManager
    extends GenericManager<SingleEntitySample, Long> {
    public JQueryPager getSingleEntitySampleCriteria( final JQueryPager paginatedList );
}