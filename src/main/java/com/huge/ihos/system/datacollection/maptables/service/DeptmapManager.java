package com.huge.ihos.system.datacollection.maptables.service;

import com.huge.ihos.system.datacollection.maptables.model.Deptmap;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface DeptmapManager
    extends GenericManager<Deptmap, Long> {
    public JQueryPager getDeptmapCriteria( final JQueryPager paginatedList, String targetname, String targetcode, String sourcename,
                                           String sourcecode, String marktype );
}