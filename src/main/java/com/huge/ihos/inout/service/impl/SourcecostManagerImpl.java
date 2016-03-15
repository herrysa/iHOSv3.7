package com.huge.ihos.inout.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.inout.dao.ChargeItemDao;
import com.huge.ihos.inout.dao.ChargeTypeDao;
import com.huge.ihos.inout.dao.CostItemDao;
import com.huge.ihos.inout.dao.SourcecostDao;
import com.huge.ihos.inout.model.Sourcecost;
import com.huge.ihos.inout.service.SourcecostManager;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "sourcecostManager" )
public class SourcecostManagerImpl
    extends GenericManagerImpl<Sourcecost, Long>
    implements SourcecostManager {
    SourcecostDao sourcecostDao;

    ChargeItemDao chargeItemDao;

    ChargeTypeDao chargeTypeDao;

    CostItemDao costItemDao;

    DepartmentDao departmentDao;

    PersonDao personDao;

    @Autowired
    public SourcecostManagerImpl( SourcecostDao sourcecostDao ) {
        super( sourcecostDao );
        this.sourcecostDao = sourcecostDao;
    }

    public ChargeItemDao getChargeItemDao() {
        return chargeItemDao;
    }

    @Autowired
    public void setChargeItemDao( ChargeItemDao chargeItemDao ) {
        this.chargeItemDao = chargeItemDao;
    }

    public ChargeTypeDao getChargeTypeDao() {
        return chargeTypeDao;
    }

    @Autowired
    public void setChargeTypeDao( ChargeTypeDao chargeTypeDao ) {
        this.chargeTypeDao = chargeTypeDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    @Autowired
    public void setDepartmentDao( DepartmentDao departmentDao ) {
        this.departmentDao = departmentDao;
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    @Autowired
    public void setPersonDao( PersonDao personDao ) {
        this.personDao = personDao;
    }

    public CostItemDao getCostItemDao() {
        return costItemDao;
    }

    @Autowired
    public void setCostItemDao( CostItemDao costItemDao ) {
        this.costItemDao = costItemDao;
    }

    public JQueryPager getSourcecostCriteria( JQueryPager paginatedList ) {
        return sourcecostDao.getSourcecostCriteria( paginatedList );
    }

    @Override
    public Sourcecost save( Sourcecost object ) {
        this.upDepends( object );
        return super.save( object );
    }

    //TODO OPERATOR NEED UP
    private Sourcecost upDepends( Sourcecost sc ) {
        if ( sc.getCostItemId().getCostItemId() != null )
            sc.setCostItemId( this.costItemDao.get( sc.getCostItemId().getCostItemId() ) );

        if ( sc.getDeptartment().getDepartmentId() != null )
            sc.setDeptartment( this.departmentDao.get( sc.getDeptartment().getDepartmentId() ) );

        return sc;
    }

    @Override
    public String getAmountSum( String currentPeriod ) {
        return sourcecostDao.getAmountSum( currentPeriod );
    }
}