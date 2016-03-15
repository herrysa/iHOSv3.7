package com.huge.ihos.inout.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.inout.dao.ChargeItemDao;
import com.huge.ihos.inout.dao.ChargeTypeDao;
import com.huge.ihos.inout.dao.SourcepayinDao;
import com.huge.ihos.inout.model.ChargeType;
import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.ihos.inout.service.SourcepayinManager;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.dao.PersonDao;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "sourcepayinManager" )
public class SourcepayinManagerImpl
    extends GenericManagerImpl<Sourcepayin, Long>
    implements SourcepayinManager {
    SourcepayinDao sourcepayinDao;

    ChargeItemDao chargeItemDao;

    ChargeTypeDao chargeTypeDao;

    DepartmentDao departmentDao;

    PersonDao personDao;

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

    @Autowired
    public SourcepayinManagerImpl( SourcepayinDao sourcepayinDao ) {
        super( sourcepayinDao );
        this.sourcepayinDao = sourcepayinDao;
    }

    public JQueryPager getSourcepayinCriteria( JQueryPager paginatedList ) {
        return sourcepayinDao.getSourcepayinCriteria( paginatedList );
    }

    @Override
    public Sourcepayin save( Sourcepayin sp ) {
        this.upDepends( sp );
        return super.save( sp );
    }

    //TODO OPERATOR NEED UP
    private Sourcepayin upDepends( Sourcepayin sp ) {
        //		if(sp.getChargeItem().getChargeItemNo()!=null)
        //			sp.setChargeItem(this.chargeItemDao.get(sp.getChargeItem().getChargeItemNo()));
        if ( sp.getChargeType() != null && sp.getChargeType().getChargeTypeId() != null
            && !sp.getChargeType().getChargeTypeId().equalsIgnoreCase( "" ) ) {
            ChargeType ct;
            try {
                ct = this.chargeTypeDao.get( sp.getChargeType().getChargeTypeId() );
            }
            catch ( Exception e ) {
                ct = null;
            }

            sp.setChargeType( ct );
        }
        if ( sp.getHlDept() != null && sp.getHlDept().getDepartmentId() != null && !sp.getHlDept().getDepartmentId().equalsIgnoreCase( "" ) )
            sp.setHlDept( this.departmentDao.get( sp.getHlDept().getDepartmentId() ) );
        else
            sp.setHlDept( null );
        if ( sp.getJzDept() != null && sp.getJzDept().getDepartmentId() != null && !sp.getJzDept().getDepartmentId().equalsIgnoreCase( "" ) )
            sp.setJzDept( this.departmentDao.get( sp.getJzDept().getDepartmentId() ) );
        else
            sp.setJzDept( null );
        if ( sp.getKdDept() != null && sp.getKdDept().getDepartmentId() != null && !sp.getKdDept().getDepartmentId().equalsIgnoreCase( "" ) )
            sp.setKdDept( this.departmentDao.get( sp.getKdDept().getDepartmentId() ) );
        else
            sp.setKdDept( null );
        if ( sp.getZxDept() != null && sp.getZxDept().getDepartmentId() != null && !sp.getZxDept().getDepartmentId().equalsIgnoreCase( "" ) )
            sp.setZxDept( this.departmentDao.get( sp.getZxDept().getDepartmentId() ) );
        else
            sp.setZxDept( null );

        return sp;
    }

    @Override
    public String getAmountSum( String currentPeriod ) {
        return sourcepayinDao.getAmountSum( currentPeriod );
    }
}