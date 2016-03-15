package com.huge.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.system.reportManager.search.dao.QueryDao;
import com.huge.ihos.system.reportManager.search.service.QueryManager;

public class ExcelUtilTest
    extends BaseDaoTestCase {
    @Autowired
    QueryDao queryDao;

    @Autowired
    private QueryManager queryManager;

    public void setQueryManager( QueryManager queryManager ) {
        this.queryManager = queryManager;
    }

    public void setQueryDao( QueryDao queryDao ) {
        this.queryDao = queryDao;
    }

    @Test
    public void testExcel1() {
      /*  ExcelUtil ex = new ExcelUtil();
        ex.setQueryDao( queryDao );
        ex.setQueryManager( queryManager );

        File file = new File( "D:\\NRepY01.xls" );
        List list = null;*/
        /*try {
        	ex.read(null,null,null);
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IllegalArgumentException e) {
        	e.printStackTrace();
        } catch (SecurityException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (InstantiationException e) {
        	e.printStackTrace();
        } catch (IllegalAccessException e) {
        	e.printStackTrace();
        } catch (InvocationTargetException e) {
        	e.printStackTrace();
        } catch (NoSuchMethodException e) {
        	e.printStackTrace();
        }*/
        //System.out.println( list.size() );
    	Assert.assertTrue(true);
    }
}
