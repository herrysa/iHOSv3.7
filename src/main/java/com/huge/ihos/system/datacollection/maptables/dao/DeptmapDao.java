package com.huge.ihos.system.datacollection.maptables.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.maptables.model.Deptmap;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Deptmap table.
 */
public interface DeptmapDao
    extends GenericDao<Deptmap, Long> {

    public JQueryPager getDeptmapCriteria( final JQueryPager paginatedList, String targetname, String targetcode, String sourcename,
                                           String sourcecode, String marktype );
}