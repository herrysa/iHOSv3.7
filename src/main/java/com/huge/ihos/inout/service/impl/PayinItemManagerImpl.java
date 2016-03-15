package com.huge.ihos.inout.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.inout.dao.ChargeTypeDao;
import com.huge.ihos.inout.dao.PayinItemDao;
import com.huge.ihos.inout.model.PayinItem;
import com.huge.ihos.inout.service.PayinItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "payinItemManager" )
public class PayinItemManagerImpl
    extends GenericManagerImpl<PayinItem, String>
    implements PayinItemManager {
    PayinItemDao payinItemDao;

    ChargeTypeDao chargeTypeDao;

    public ChargeTypeDao getChargeTypeDao() {
        return chargeTypeDao;
    }

    @Autowired
    public void setChargeTypeDao( ChargeTypeDao chargeTypeDao ) {
        this.chargeTypeDao = chargeTypeDao;
    }

    @Autowired
    public PayinItemManagerImpl( PayinItemDao payinItemDao ) {
        super( payinItemDao );
        this.payinItemDao = payinItemDao;
    }

    public JQueryPager getPayinItemCriteria( JQueryPager paginatedList ) {
        return payinItemDao.getPayinItemCriteria( paginatedList );
    }

    public List getAllPayItems() {
        return this.getAll();
    }

    @Override
    public PayinItem save( PayinItem object ) {
        /*ChineseSpelling finder = ChineseSpelling.getInstance();
        finder.setResource( object.getPayinItemName() );*/
    	object.setCnCode( payinItemDao.getPyCodes( object.getPayinItemName() ) );
        super.save( object );
        //this.payinItemDao.updateCnCodeById(object.getPayinItemId());
        return object;
    }

    @Override
    public void remove( String id ) {
        int childCount = chargeTypeDao.countItemByPayinItemId( id );
        if ( childCount == 0 )

            payinItemDao.remove( id );
        else {
            throw new BusinessException( "此收入项目不能被删除，它正在被收费类别所引用。" );
        }
    }

}