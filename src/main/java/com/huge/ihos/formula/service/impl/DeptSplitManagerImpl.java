package com.huge.ihos.formula.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.DeptSplitDao;
import com.huge.ihos.formula.model.DeptSplit;
import com.huge.ihos.formula.service.DeptSplitManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "deptSplitManager" )
public class DeptSplitManagerImpl
    extends GenericManagerImpl<DeptSplit, Long>
    implements DeptSplitManager {
    DeptSplitDao deptSplitDao;

    @Autowired
    public DeptSplitManagerImpl( DeptSplitDao deptSplitDao ) {
        super( deptSplitDao );
        this.deptSplitDao = deptSplitDao;
    }

    public JQueryPager getDeptSplitCriteria( JQueryPager paginatedList ) {
        return deptSplitDao.getDeptSplitCriteria( paginatedList );
    }

}