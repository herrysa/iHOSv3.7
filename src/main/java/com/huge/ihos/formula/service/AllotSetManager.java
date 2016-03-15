package com.huge.ihos.formula.service;

import com.huge.ihos.formula.model.AllotSet;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface AllotSetManager
    extends GenericManager<AllotSet, String> {
    public JQueryPager getAllotSetCriteria( final JQueryPager paginatedList );
}