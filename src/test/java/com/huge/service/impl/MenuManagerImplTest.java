package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.systemManager.menu.dao.MenuDao;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.impl.MenuManagerImpl;

public class MenuManagerImplTest
    extends BaseManagerMockTestCase {
    private MenuManagerImpl manager = null;

    private MenuDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( MenuDao.class );
        manager = new MenuManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetMenu() {
        log.debug( "testing get..." );

        final String menuId = "7L";
        final Menu menu = new Menu();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( menuId ) ) );
                will( returnValue( menu ) );
            }
        } );

        Menu result = manager.get( menuId );
        assertSame( menu, result );
    }

    @Test
    public void testGetMenus() {
        log.debug( "testing getAll..." );

        final List menus = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( menus ) );
            }
        } );

        List result = manager.getAll();
        assertSame( menus, result );
    }

    //@Test
    public void testSaveMenu() {
        log.debug( "testing save..." );

        final Menu menu = new Menu();
        // enter all required fields
        menu.setLeaf( Boolean.FALSE );
        menu.setMenuLevel( 1L );
        menu.setMenuName( "NdTiZuDsQoSeEnLtUoWnUiJnMtPeGdZsJvJcUaHjAyXcJfApRz" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( menu ) ) );
            }
        } );

        manager.save( menu );
    }

    @Test
    public void testRemoveMenu() {
        log.debug( "testing remove..." );

        final String menuId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( menuId ) ) );
            }
        } );

        manager.remove( menuId );
    }
    
    @Test
    public void testMenuCache(){
    	List list1 = manager.getAll();
    }
}