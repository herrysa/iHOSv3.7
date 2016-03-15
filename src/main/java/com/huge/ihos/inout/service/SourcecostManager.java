package com.huge.ihos.inout.service;

import com.huge.ihos.inout.model.Sourcecost;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface SourcecostManager
    extends GenericManager<Sourcecost, Long> {
    public JQueryPager getSourcecostCriteria( final JQueryPager paginatedList );

    public String getAmountSum( String currentPeriod );
}