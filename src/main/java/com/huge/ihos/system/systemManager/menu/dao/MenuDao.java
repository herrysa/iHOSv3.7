package com.huge.ihos.system.systemManager.menu.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Menu table.
 */
public interface MenuDao
    extends GenericDao<Menu, String> {

    public JQueryPager getMenuCriteria( final JQueryPager paginatedList );

    public List getAllSubSystem();

    public List getAllRootMenu();

    // public List getAllValidRootMenu();
    public List getAllByParent( String pid );
    
    public List getByParent(String pid);

    public List getAllByRoot( String rid );

    public List getAllNotLeafByRoot( String rid );

    public List getAllEnabled();

    public List getMenuChain( String id );
}