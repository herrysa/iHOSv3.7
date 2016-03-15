package com.huge.ihos.inout.service;

import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SourcepayinManager
    extends GenericManager<Sourcepayin, Long> {
    public JQueryPager getSourcepayinCriteria( final JQueryPager paginatedList );

    public String getAmountSum( String currentPeriod );
}