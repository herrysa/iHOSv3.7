package com.huge.ihos.inout.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.inout.model.ChargeType;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the ChargeType table.
 */
public interface ChargeTypeDao
    extends GenericDao<ChargeType, String> {

    public JQueryPager getChargeTypeCriteria( final JQueryPager paginatedList );

    /*public void updateCnCodeById(String id);*/
    public int countItemByPayinItemId( String id );
}