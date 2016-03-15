package com.huge.ihos.formula.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.ihos.formula.model.FormulaField;

public class FormulaFieldDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private FormulaFieldDao formulaFieldDao;

    @Autowired
    private FormulaEntityDao formulaEntityDao;

    //@Test
    //@ExpectedException(DataAccessException.class)
    public void testAddAndRemoveFormulaField() {
        FormulaField formulaField = new FormulaField();

        // enter all required fields
        formulaField.setCalcType( "AaBjYmHpRmFdYlZiVbTaDwUfCgDwXzGcFoQsSqVkVbZrAcGuVp" );
        formulaField.setDefaultValue( "CqQjZjTzXkOxQoYdGfQeSuLaLyWbZlIvCjCqCmScNvRoEtAzAm" );
        formulaField.setDirection( "WxIjDoAzEoDePrTeScJgRlObDgHgHxFmUxCmZaDdNmUoPvVyPi" );
        formulaField.setFieldName( "UjXrAxPsCdUgFdEuZhSeFcOlPuBwUbUkRwLeNmZdIqEcXzNhCl" );
        formulaField.setFieldTitle( "AdWgLqUpAoDfVoNwNvNrBcDrYxYgPyWqFlLiJhBtKqErLdFhNg" );
        formulaField.setInherited( Boolean.FALSE );
        formulaField.setKeyClass( "GrAiJbScNaJcExCgVaQeZrClOpQrGgTjHaTfWjXzMvCiWuFyNg" );

        log.debug( "adding formulaField..." );
        formulaField = formulaFieldDao.save( formulaField );

        formulaField = formulaFieldDao.get( formulaField.getFormulaFieldId() );

        assertNotNull( formulaField.getFormulaFieldId() );

        log.debug( "removing formulaField..." );

        formulaFieldDao.remove( formulaField.getFormulaFieldId() );

        // should throw DataAccessException 
        formulaFieldDao.get( formulaField.getFormulaFieldId() );
    }

   // @Test
    public void testinitFormulaFieldByTargetTable() {
        FormulaEntity fe = formulaEntityDao.getByTableName( "T_DeptKey" );
        formulaFieldDao.initFormulaFieldByTargetTable( fe );

        List list = this.formulaFieldDao.getAll();
        System.out.println( "test List size is: " + list.size() );
        Assert.assertTrue( list.size() > 2 );
    }

    //  @Test
    public void testgetAllFormulaFieldByEntityId() {
        //FormulaEntity fe = formulaEntityDao.getByTableName("T_DeptKey");
        List list = formulaFieldDao.getAllFormulaFieldByEntityId( "0000000001" );//initFormulaFieldByTargetTable(fe);
        //    	
        //    	List list=this.formulaFieldDao.getAll();
        //    	System.out.println("test List size is: "+list.size());
        Assert.assertTrue( list.size() > 0 );
    }
    @Test
    public void dummyTest(){
    	Assert.assertTrue(true);
    }
}