package com.huge.ihos.inout.service;

import com.huge.ihos.inout.model.CostUse;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface CostUseManager
    extends GenericManager<CostUse, String> {
    public JQueryPager getCostUseCriteria( final JQueryPager paginatedList );
}