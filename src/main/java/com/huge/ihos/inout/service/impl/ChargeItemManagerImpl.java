package com.huge.ihos.inout.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.inout.dao.ChargeItemDao;
import com.huge.ihos.inout.model.ChargeItem;
import com.huge.ihos.inout.service.ChargeItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "chargeItemManager" )
public class ChargeItemManagerImpl
    extends GenericManagerImpl<ChargeItem, String>
    implements ChargeItemManager {
    ChargeItemDao chargeItemDao;

    @Autowired
    public ChargeItemManagerImpl( ChargeItemDao chargeItemDao ) {
        super( chargeItemDao );
        this.chargeItemDao = chargeItemDao;
    }

    public JQueryPager getChargeItemCriteria( JQueryPager paginatedList ) {
        return chargeItemDao.getChargeItemCriteria( paginatedList );
    }

    @Override
    public ChargeItem save( ChargeItem object ) {
        /*ChineseSpelling finder = ChineseSpelling.getInstance();
        finder.setResource( object.getChargeItemName() );*/
        object.setPyCode( chargeItemDao.getPyCodes( object.getChargeItemName() ) );
        super.save( object );
        //chargeItemDao.updateCnCodeById(object.getChargeItemId());
        return object;
    }

}