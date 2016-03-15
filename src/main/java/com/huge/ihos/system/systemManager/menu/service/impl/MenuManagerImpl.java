package com.huge.ihos.system.systemManager.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.menu.dao.MenuDao;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "menuManager" )
public class MenuManagerImpl
    extends GenericManagerImpl<Menu, String>
    implements MenuManager {
    MenuDao menuDao;

    private String pid;

    @Autowired
    public MenuManagerImpl( MenuDao menuDao ) {
        super( menuDao );
        this.menuDao = menuDao;
    }

    /*@Cacheable("menus")*/
    public JQueryPager getMenuCriteria( JQueryPager paginatedList ) {
        return menuDao.getMenuCriteria( paginatedList );
    }

    //@Cacheable("menus")
    public List getAllSubSystem() {
        return this.menuDao.getAllSubSystem();
    }

    //@Cacheable("menus")
    public List getAllRootMenu() {
        return this.menuDao.getAllRootMenu();
    }

    //@Cacheable("menus")
    public List getAllByParent( String pid ) {
        return this.menuDao.getAllByParent( pid );
    }

    public List getByParent(String pid){
    	return this.menuDao.getByParent( pid );
    }
    //@Cacheable(value="menus",key="#rid + 'getAllByRoot'")
    public List getAllByRoot( String rid ) {
        return this.menuDao.getAllByRoot( rid );
    }

    //@Cacheable(value="menus",key="#rid + 'getAllNotLeafByRoot'")
    public List getAllNotLeafByRoot( String rid ) {
        return this.menuDao.getAllNotLeafByRoot( rid );
    }

    @Override
    //@CacheEvict(value = "menus", allEntries=true)
    public Menu save( Menu object ) {
        String pid;
        if ( object.getParent() != null ) {
            pid = object.getParent().getMenuId();
            if ( pid == null || pid.trim().equalsIgnoreCase( "" ) )
                object.setParent( null );
        }
        return super.save( object );
    }

    //@Cacheable("menus")
    public List getAllEnabled() {
        return this.menuDao.getAllEnabled();
    }

    //@Cacheable("menus")
    public List getMenuChain( String id ) {
        return this.menuDao.getMenuChain( id );
    }

    @Override
    //@CacheEvict(value = "menus", allEntries=true)
    public void remove( String id ) {
        super.remove( id );
    }

	@Override
	public List getBusinessSys(int type) {
		Menu example = new Menu();
		example.setDisabled(false);
		example.setSystemType(type);
		example.setMenuLevel(0L);
		return menuDao.getByExample(example);
	}

	@Override
	public List getAllByRoots(String roots) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter subSysFilter = new PropertyFilter("INS_subSystem.menuId",roots);
		filters.add(subSysFilter);
		return menuDao.getByFilters(filters);
	}

	@Override
	public List<Menu> getAllDescendants(String pid) {
		List<Menu> menuList = new ArrayList<Menu>();
		List<PropertyFilter> pfs = new ArrayList<PropertyFilter>();
		pfs.add(new PropertyFilter("EQS_parent.menuId",pid));
		List<Menu> menus = this.getByFilters(pfs);
		if(menus!=null && !menus.isEmpty()){
			menuList.addAll(menus);
			for(Menu menu:menus){
				if(menu.isLeaf()){
					continue;
				}
				List<Menu> childMenus = getAllDescendants(menu.getMenuId());
				menuList.addAll(childMenus);
			}
		}
		return menuList;
	}

	@Override
	public List<Menu> findAllowRootMenu(User user) {
		Set<Role> roles = user.getRoles();
        Iterator<Role> roleIt = roles.iterator();
        int i = 0;
        boolean flag = false;
        Set<Menu> rootMenus = new TreeSet<Menu>();
        List<Menu> sysRootMenus = this.getBusinessSys(0);
        Map<String, Boolean> sysMenuMap = new HashMap<String, Boolean>();
        for(Menu rootMenu : sysRootMenus){
        	sysMenuMap.put(rootMenu.getMenuId(), true);
        }
        while ( roleIt.hasNext() ) {
            Role role = roleIt.next();
            if ( role.getName().equals( "ROLE_ADMIN" ) ) {
                flag = true;
                break;
            }
            Set<Menu> menus = role.getMenus();
            for(Menu menu : menus){
            	Menu rootMenu = menu.getSubSystem();
            	if(sysMenuMap.get(rootMenu.getMenuId())==null?true:false){
            		rootMenus.add(rootMenu);
            	}
        	}
        }
        Iterator itr = null;
        if ( !flag ) {
            Set<Menu> menus = user.getMenus();
            for ( Menu menuItem : menus ) {
            	Menu rootMenu = menuItem.getSubSystem();
            	if(sysMenuMap.get(rootMenu.getMenuId())==null?true:false){
            		rootMenus.add(rootMenu);
            	}
            }
            return new ArrayList<Menu>(rootMenus);
        }else{
        	return this.getBusinessSys(1);
        }
	}
	@Override
	public String getJzTypeBySubSystemCode(String subSystemCode){
		String jzType = "年,月";
		String sql = "SELECT code,jzType FROM sy_model WHERE code = '"+ subSystemCode +"'";
		List<Map<String,Object>> list = this.getBySqlToMap(sql);
		if(OtherUtil.measureNotNull(list)&&!list.isEmpty()){
			jzType = list.get(0).get("jzType").toString();
		}
		return jzType;
	}
}
