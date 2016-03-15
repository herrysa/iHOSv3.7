package com.huge.ihos.inout.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.inout.dao.CostItemDao;
import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.inout.service.CostItemManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.ChineseSpelling;
import com.huge.webapp.pagers.JQueryPager;

@Service( "costItemManager" )
public class CostItemManagerImpl
    extends GenericManagerImpl<CostItem, String>
    implements CostItemManager {
    CostItemDao costItemDao;

    @Autowired
    public CostItemManagerImpl( CostItemDao costItemDao ) {
        super( costItemDao );
        this.costItemDao = costItemDao;
    }

    public JQueryPager getCostItemCriteria( JQueryPager paginatedList ) {
        return costItemDao.getCostItemCriteria( paginatedList );
    }

    @Override
    public CostItem save( CostItem object ) {
        object.setCnCode( costItemDao.getPyCodes( object.getCostItemName() ) );
        super.save( object );
        return object;

    }

    @Override
    public void remove( String id ) {
        int childCount = costItemDao.countItemByParentId( id );
        if ( childCount == 0 )

            costItemDao.remove( id );
        else {
            throw new BusinessException( "此成本项目不能被删除，它正在被子成本项目所引用。" );
        }
    }

	@Override
	public boolean isInUse(String costItemId) {
		return costItemDao.isInUse(costItemId);
	}

	@Override
	public boolean hasChildren(String costItemId) {
		return costItemDao.hasChildren(costItemId);
	}
	
	
}