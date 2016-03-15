package com.huge.ihos.inout.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.inout.model.PayinItem;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the PayinItem table.
 */
public interface PayinItemDao
    extends GenericDao<PayinItem, String> {

    public JQueryPager getPayinItemCriteria( final JQueryPager paginatedList );
    /* public void updateCnCodeById(String id);*/
}