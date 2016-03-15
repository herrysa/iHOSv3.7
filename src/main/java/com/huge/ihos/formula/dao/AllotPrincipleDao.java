package com.huge.ihos.formula.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.formula.model.AllotPrinciple;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the AllotPrinciple table.
 */
public interface AllotPrincipleDao
    extends GenericDao<AllotPrinciple, String> {

    public JQueryPager getAllotPrincipleCriteria( final JQueryPager paginatedList );
}