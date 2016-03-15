package com.huge.ihos.formula.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.formula.model.AllotSet;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the AllotSet table.
 */
public interface AllotSetDao
    extends GenericDao<AllotSet, String> {

    public JQueryPager getAllotSetCriteria( final JQueryPager paginatedList );
}