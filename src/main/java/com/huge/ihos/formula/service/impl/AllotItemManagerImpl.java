package com.huge.ihos.formula.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.formula.dao.AllotItemDao;
import com.huge.ihos.formula.model.AllotItem;
import com.huge.ihos.formula.service.AllotItemManager;
import com.huge.ihos.inout.dao.CostItemDao;
import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "allotItemManager" )
public class AllotItemManagerImpl
    extends GenericManagerImpl<AllotItem, Long>
    implements AllotItemManager {
    AllotItemDao allotItemDao;

    DepartmentDao departmentDao;

    CostItemDao costItemDao;

    public CostItemDao getCostItemDao() {
        return costItemDao;
    }

    @Autowired
    public void setCostItemDao( CostItemDao costItemDao ) {
        this.costItemDao = costItemDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    @Autowired
    public void setDepartmentDao( DepartmentDao departmentDao ) {
        this.departmentDao = departmentDao;
    }

    @Autowired
    public AllotItemManagerImpl( AllotItemDao allotItemDao ) {
        super( allotItemDao );
        this.allotItemDao = allotItemDao;
    }

    public JQueryPager getAllotItemCriteria( JQueryPager paginatedList ) {
        return allotItemDao.getAllotItemCriteria( paginatedList );
    }

    public AllotItem save( AllotItem allotItem ) {
        String pid = allotItem.getDepartment().getDepartmentId();
        if ( pid == null || pid.trim().equalsIgnoreCase( "" ) )
            allotItem.setDepartment( null );
        else {
            Department dep = this.departmentDao.get( pid );

            allotItem.setDepartment( dep );
        }

        String cid = allotItem.getCostItem().getCostItemId();
        if ( cid == null || cid.trim().equalsIgnoreCase( "" ) )
            allotItem.setCostItem( null );
        else {
            CostItem dep = this.costItemDao.get( cid );

            allotItem.setCostItem( dep );
        }

        return super.save( allotItem );
    }
}