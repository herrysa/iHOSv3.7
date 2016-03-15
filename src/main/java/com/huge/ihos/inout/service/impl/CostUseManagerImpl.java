package com.huge.ihos.inout.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.exceptions.BusinessException;
import com.huge.ihos.inout.dao.CostItemDao;
import com.huge.ihos.inout.dao.CostUseDao;
import com.huge.ihos.inout.model.CostUse;
import com.huge.ihos.inout.service.CostUseManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

@Service( "costUseManager" )
public class CostUseManagerImpl
    extends GenericManagerImpl<CostUse, String>
    implements CostUseManager {
    CostUseDao costUseDao;

    CostItemDao costItemDao;

    @Autowired
    public CostUseManagerImpl( CostUseDao costUseDao ) {
        super( costUseDao );
        this.costUseDao = costUseDao;
    }

    public CostItemDao getCostItemDao() {
        return costItemDao;
    }

    @Autowired
    public void setCostItemDao( CostItemDao costItemDao ) {
        this.costItemDao = costItemDao;
    }

    public JQueryPager getCostUseCriteria( JQueryPager paginatedList ) {
        return costUseDao.getCostUseCriteria( paginatedList );
    }

    @Override
    public void remove( String id ) {
        int childCount = costItemDao.countItemByCostuseId( id );
        if ( childCount == 0 )

            costUseDao.remove( id );
        else {
            throw new BusinessException( "此成本用途不能被删除，它正在被成本项目所引用。" );
        }
    }

}
