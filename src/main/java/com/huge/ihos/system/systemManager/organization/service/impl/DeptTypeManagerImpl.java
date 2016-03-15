package com.huge.ihos.system.systemManager.organization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.organization.dao.DeptTypeDao;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.service.DeptTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "deptTypeManager" )
public class DeptTypeManagerImpl
    extends GenericManagerImpl<DeptType, String>
    implements DeptTypeManager {
    DeptTypeDao deptTypeDao;

    @Autowired
    public DeptTypeManagerImpl( DeptTypeDao deptTypeDao ) {
        super( deptTypeDao );
        this.deptTypeDao = deptTypeDao;
    }

    public JQueryPager getDeptTypeCriteria( JQueryPager paginatedList ) {
        return deptTypeDao.getDeptTypeCriteria( paginatedList );
    }

}