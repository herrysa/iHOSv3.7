package com.huge.ihos.system.datacollection.maptables.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.maptables.model.Acctcostmap;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Acctcostmap table.
 */
public interface AcctcostmapDao
    extends GenericDao<Acctcostmap, Long> {

    public JQueryPager getAcctcostmapCriteria( final JQueryPager paginatedList, String acctcode, String acctname, String costitemid, String costitem );
}