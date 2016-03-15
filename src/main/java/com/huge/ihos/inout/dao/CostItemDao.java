package com.huge.ihos.inout.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.inout.model.CostItem;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the CostItem table.
 */
public interface CostItemDao
    extends GenericDao<CostItem, String> {

    public JQueryPager getCostItemCriteria( final JQueryPager paginatedList );

    public int countItemByCostuseId( String id );

    public int countItemByParentId( String id );
    /* public void updateCnCodeById(String id);*/

	public boolean isInUse(String costItemId);

	public boolean hasChildren(String costItemId);
}