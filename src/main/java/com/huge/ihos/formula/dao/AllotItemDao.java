package com.huge.ihos.formula.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.formula.model.AllotItem;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the AllotItem table.
 */
public interface AllotItemDao
    extends GenericDao<AllotItem, Long> {

    public JQueryPager getAllotItemCriteria( final JQueryPager paginatedList );
}