package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.inout.dao.CostItemDao;
import com.huge.ihos.inout.model.CostItem;

public class CostItemDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private CostItemDao costItemDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveCostItem() {
        CostItem costItem = new CostItem();

        // enter all required fields
        costItem.setBehaviour( "FwZzYkNiSp" );
        costItem.setClevel( 1118193212 );
        costItem.setControllable( Boolean.FALSE );
        costItem.setCostDesc( "AvTjAuRfEjTiCyXqAmUjEzJcCzKrMdVuXbThOeYaWrDgDoAlQlOuZdHnEpCsMjEjAcRaSlJwRbOpBuOwQyFxMoPnTeOkBmJiDkSl" );
        costItem.setCostItemName( "TrDnOgXfStNkSeUlNgViRlWqXwMbPo" );
        costItem.setDisabled( Boolean.FALSE );
        costItem.setLeaf( Boolean.FALSE );

        log.debug( "adding costItem..." );
        costItem = costItemDao.save( costItem );

        costItem = costItemDao.get( costItem.getCostItemId() );

        assertNotNull( costItem.getCostItemId() );

        log.debug( "removing costItem..." );

        costItemDao.remove( costItem.getCostItemId() );

        // should throw DataAccessException 
        costItemDao.get( costItem.getCostItemId() );
    }
}