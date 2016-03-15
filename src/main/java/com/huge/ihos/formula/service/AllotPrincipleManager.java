package com.huge.ihos.formula.service;

import com.huge.ihos.formula.model.AllotPrinciple;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface AllotPrincipleManager
    extends GenericManager<AllotPrinciple, String> {
    public JQueryPager getAllotPrincipleCriteria( final JQueryPager paginatedList );
}