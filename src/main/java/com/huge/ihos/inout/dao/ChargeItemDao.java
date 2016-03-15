package com.huge.ihos.inout.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.inout.model.ChargeItem;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the ChargeItem table.
 */
public interface ChargeItemDao
    extends GenericDao<ChargeItem, String> {

    public JQueryPager getChargeItemCriteria( final JQueryPager paginatedList );

    public int countItemByChargeTypeId( String id );
    /*public void updateCnCodeById(String id);*/
}