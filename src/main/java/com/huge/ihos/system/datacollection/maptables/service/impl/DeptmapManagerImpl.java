package com.huge.ihos.system.datacollection.maptables.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.maptables.dao.DeptmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Deptmap;
import com.huge.ihos.system.datacollection.maptables.service.DeptmapManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "deptmapManager" )
public class DeptmapManagerImpl
    extends GenericManagerImpl<Deptmap, Long>
    implements DeptmapManager {
    DeptmapDao deptmapDao;

    @Autowired
    public DeptmapManagerImpl( DeptmapDao deptmapDao ) {
        super( deptmapDao );
        this.deptmapDao = deptmapDao;
    }

    public JQueryPager getDeptmapCriteria( JQueryPager paginatedList, String targetname, String targetcode, String sourcename, String sourcecode,
                                           String marktype ) {
        return deptmapDao.getDeptmapCriteria( paginatedList, targetname, targetcode, sourcename, sourcecode, marktype );
    }

}