package com.huge.ihos.formula.service;

import com.huge.ihos.formula.model.DeptSplit;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DeptSplitManager
    extends GenericManager<DeptSplit, Long> {
    public JQueryPager getDeptSplitCriteria( final JQueryPager paginatedList );
}