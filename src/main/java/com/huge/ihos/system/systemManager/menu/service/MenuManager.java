package com.huge.ihos.system.systemManager.menu.service;

import java.util.List;

import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface MenuManager
    extends GenericManager<Menu, String> {
    public JQueryPager getMenuCriteria( final JQueryPager paginatedList );

    public List getAllSubSystem();

    public List getAllRootMenu();

    // public List getAllValidRootMenu();
    public List getAllByParent( String pid );

    //  public List getAllValidByParent(String pid);
    public List getAllByRoot( String rid );

    public List getAllNotLeafByRoot( String rid );

    public List getAllEnabled();

    public List getMenuChain( String id );
    
    public List getByParent(String pid);
    
    public List getBusinessSys(int type);
    
    public List getAllByRoots(String roots);
    /**
     * 获得所有下级菜单
     * @param pid
     * @return
     */
    public List<Menu> getAllDescendants(String pid);
    
    public List<Menu> findAllowRootMenu(User user);
    /**
     * 根据子系统代码获取结账类型
     * @param subSystemCode
     * @return
     */
    public String getJzTypeBySubSystemCode(String subSystemCode);
}