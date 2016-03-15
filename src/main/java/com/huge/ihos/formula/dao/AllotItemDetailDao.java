package com.huge.ihos.formula.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.formula.model.AllotItemDetail;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the AllotItemDetail table.
 */
public interface AllotItemDetailDao
    extends GenericDao<AllotItemDetail, Long> {

    public JQueryPager getAllotItemDetailCriteria( final JQueryPager paginatedList, Long allotItemId );
}