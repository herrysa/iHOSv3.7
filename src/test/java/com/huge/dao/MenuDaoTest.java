package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.systemManager.menu.dao.MenuDao;
import com.huge.ihos.system.systemManager.menu.model.Menu;

public class MenuDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private MenuDao menuDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveMenu() {
        Menu menu = new Menu();

        // enter all required fields
        menu.setLeaf( Boolean.FALSE );
        menu.setMenuLevel( 1L );
        menu.setMenuName( "VkQiJwGhLmKrEjVcDqPuWjCcGyHuBhIgYiNgTpVeCsViYfIgDi" );

        log.debug( "adding menu..." );
        menu = menuDao.save( menu );

        menu = menuDao.get( menu.getMenuId() );

        assertNotNull( menu.getMenuId() );

        log.debug( "removing menu..." );

        menuDao.remove( menu.getMenuId() );

        // should throw DataAccessException 
        menuDao.get( menu.getMenuId() );
    }

    @Test
    public void testGetAllRootMenu() {
        List list = this.menuDao.getAllRootMenu();
        Assert.assertTrue( list.size() > 0 );
    }

   // @Test
    public void testGetAllByParent() {
        List list = this.menuDao.getAllByParent( "-1" );
        Assert.assertTrue( list.size() > 0 );
    }

   // @Test
    public void testgetAllByRoot() {
        List list = this.menuDao.getAllByRoot( "-1" );
        Assert.assertTrue( list.size() > 0 );
    }

   // @Test
    public void testgetMenuChain() {
        List list = this.menuDao.getMenuChain( "502001" );
        /*	Iterator itr = list.iterator();
        	while(itr.hasNext()){
        		Menu m= (Menu)itr.next();
        		System.out.print(m.getMenuName());
        	}*/

        int i = list.size();
        if ( i != 0 )
            for ( int n = i - 1; n >= 0; n-- ) {
                Menu m = (Menu) list.get( n );
                System.out.print( m.getMenuName() );
                if ( n > 0 )
                    System.out.print( ">" );
            }

        Assert.assertTrue( list.size() > 0 );
    }
    
    @Test
    public void testMenuCache(){
    	this.menuDao.getHibernateTemplate().setCacheQueries(true);
    	List list1 = this.menuDao.getAll();
    	/*try {
			this.wait(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	System.out.println(list1.size());
    	//DynamicSessionFactoryHolder.setSessionFactoryName("HOSP2");
    	
    	//List list2 = this.menuDao.getAll();
    	//System.out.println(list2.size());
    }
}