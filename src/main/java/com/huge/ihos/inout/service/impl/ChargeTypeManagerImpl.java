package com.huge.ihos.inout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.inout.dao.ChargeItemDao;
import com.huge.ihos.inout.dao.ChargeTypeDao;
import com.huge.ihos.inout.model.ChargeType;
import com.huge.ihos.inout.service.ChargeTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.ChineseSpelling;
import com.huge.webapp.pagers.JQueryPager;

@Service( "chargeTypeManager" )
public class ChargeTypeManagerImpl
    extends GenericManagerImpl<ChargeType, String>
    implements ChargeTypeManager {
    ChargeTypeDao chargeTypeDao;

    ChargeItemDao chargeItemDao;

    @Autowired
    public ChargeTypeManagerImpl( ChargeTypeDao chargeTypeDao ) {
        super( chargeTypeDao );
        this.chargeTypeDao = chargeTypeDao;
    }

    public JQueryPager getChargeTypeCriteria( JQueryPager paginatedList ) {
        return chargeTypeDao.getChargeTypeCriteria( paginatedList );
    }

    public ChargeItemDao getChargeItemDao() {
        return chargeItemDao;
    }

    @Autowired
    public void setChargeItemDao( ChargeItemDao chargeItemDao ) {
        this.chargeItemDao = chargeItemDao;
    }

    public List getAllChargeTypeList() {
        return this.getAll();
    }

    @Override
    public void remove( String id ) {
        int childCount = chargeItemDao.countItemByChargeTypeId( id );
        if ( childCount == 0 )

            chargeTypeDao.remove( id );
        else {
            throw new BusinessException( "此收费类别不能被删除，它正在被收费项目所引用." );
        }
    }

    @Override
    public ChargeType save( ChargeType object ) {
        object.setCnCode( chargeTypeDao.getPyCodes( object.getChargeTypeName() ) );
        super.save( object );
        return object;

    }
}