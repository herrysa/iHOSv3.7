package com.huge.ihos.maptables.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.maptables.dao.DeptmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Deptmap;
import com.huge.ihos.system.datacollection.maptables.service.impl.DeptmapManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class DeptmapManagerImplTest
    extends BaseManagerMockTestCase {
    private DeptmapManagerImpl manager = null;

    private DeptmapDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DeptmapDao.class );
        manager = new DeptmapManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDeptmap() {
        log.debug( "testing get..." );

        final Long id = 7L;
        final Deptmap deptmap = new Deptmap();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( id ) ) );
                will( returnValue( deptmap ) );
            }
        } );

        Deptmap result = manager.get( id );
        assertSame( deptmap, result );
    }

    @Test
    public void testGetDeptmaps() {
        log.debug( "testing getAll..." );

        final List deptmaps = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( deptmaps ) );
            }
        } );

        List result = manager.getAll();
        assertSame( deptmaps, result );
    }

    @Test
    public void testSaveDeptmap() {
        log.debug( "testing save..." );

        final Deptmap deptmap = new Deptmap();
        // enter all required fields
        deptmap.setMarktype( "IhGmAcNiPdMzFpGkZhAsJqTfQgMcKgGtZlRdVqZsFuXgQmAmNpBhOuEyPzTp" );
        deptmap.setSourcecode( "GpCmCxRiLhYpIcKuRoXiYbWvUwEnUqWvZdLcCkRcXgNoIvHoOb" );
        deptmap.setSourcename( "EvUdTmBiXfTnJrLeWuNoXpHxXjPeGjXiZpPlQiVnDxIhWqLlYwGhKpXgPoFcBbFfWtByUhRzAkWqCkUbKfGuLwHpXrWyAuJqRuDm" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( deptmap ) ) );
            }
        } );

        manager.save( deptmap );
    }

    @Test
    public void testRemoveDeptmap() {
        log.debug( "testing remove..." );

        final Long id = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( id ) ) );
            }
        } );

        manager.remove( id );
    }
}