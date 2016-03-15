package com.huge.ihos.formula.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.formula.model.FormulaEntity;

public class FormulaEntityDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private FormulaEntityDao formulaEntityDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveFormulaEntity() {
        FormulaEntity formulaEntity = new FormulaEntity();

        // enter all required fields
        formulaEntity.setEntityName( "VvSjZvHdJeWgSvDwNaZkDwCbIvDuNmCnYcAeGwLyOhCsHjZnXk" );
        formulaEntity.setTableName( "GgFgMnUjKhLuAfGpCaDeLyYtHpSlAyUaLrPxCnNtTeRvWwOjFk" );

        log.debug( "adding formulaEntity..." );
        formulaEntity = formulaEntityDao.save( formulaEntity );

        formulaEntity = formulaEntityDao.get( formulaEntity.getFormulaEntityId() );

        assertNotNull( formulaEntity.getFormulaEntityId() );

        log.debug( "removing formulaEntity..." );

        formulaEntityDao.remove( formulaEntity.getFormulaEntityId() );

        // should throw DataAccessException 
        formulaEntityDao.get( formulaEntity.getFormulaEntityId() );
    }
}