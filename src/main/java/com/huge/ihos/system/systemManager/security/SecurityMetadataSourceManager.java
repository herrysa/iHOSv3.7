/*
 * 
 */
package com.huge.ihos.system.systemManager.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huge.ihos.multidatasource.DynamicSessionFactoryHolder;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.role.service.RoleManager;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.util.HaspDogHandler;

/*
 * 
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 注意，我例子中使用的是AntUrlPathMatcher这个path matcher来检查URL是否与资源定义匹配，
 * 事实上你还要用正则的方式来匹配，或者自己实现一个matcher。
 * 
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 
 * 说明：对于方法的spring注入，只能在方法和成员变量里注入，
 * 如果一个类要进行实例化的时候，不能注入对象和操作对象，
 * 所以在构造函数里不能进行操作注入的数据。
 */
@Service( "SecurityMetadataSourceManager" )
public class SecurityMetadataSourceManager
    implements FilterInvocationSecurityMetadataSource {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger( SecurityMetadataSourceManager.class );

    private UserManager userManager;
    private MenuManager menuManager;

    public MenuManager getMenuManager() {
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager( UserManager userManager ) {
        this.userManager = userManager;
    }

    private RoleManager roleManager;

    private UserDao userDao;

    private RoleDao roleDao;

    //private ActionService actionService; 

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao( UserDao userDao ) {
        this.userDao = userDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao( RoleDao roleDao ) {
        this.roleDao = roleDao;
    }

    /*private PrivilegeManager privilegeManager;
    private PrivilegeUserManager privilegeUserManager;
    
    public PrivilegeUserManager getPrivilegeUserManager() {
    	return privilegeUserManager;
    }
    public void setPrivilegeUserManager(PrivilegeUserManager privilegeUserManager) {
    	this.privilegeUserManager = privilegeUserManager;
    }
    public PrivilegeManager getPrivilegeManager() {
    	return privilegeManager;
    }
    public void setPrivilegeManager(PrivilegeManager privilegeManager) {
    	this.privilegeManager = privilegeManager;
    }*/
    public RoleManager getRoleManager() {
        return roleManager;
    }

    public void setRoleManager( RoleManager roleManager ) {
        this.roleManager = roleManager;
    }

    //	public ActionService getActionService() {
    //		return actionService;
    //	}
    //	public void setActionService(ActionService actionService) {
    //		this.actionService = actionService;
    //	}
    private UrlMatcher urlMatcher = new AntUrlPathMatcher();

    private static Map<String, Map<String, Collection<ConfigAttribute>>> multiResourceMap = new HashMap<String, Map<String, Collection<ConfigAttribute>>>();

	public static Map<String, Map<String, Collection<ConfigAttribute>>> getMultiResourceMap() {
		return multiResourceMap;
	}
	
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    private static String currentSF = null;
	public void loadResourceDefine(String currentSF) throws Exception {
		this.currentSF = currentSF;
		this.resourceMap = multiResourceMap.get(currentSF);
		loadResourceDefine();
	}
	
    @Transactional
    public void loadResourceDefine()
        throws Exception {
    	if(currentSF==null) {
			this.resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			this.multiResourceMap.put(DynamicSessionFactoryHolder.getSessionFactoryName(), this.resourceMap);
		}
        //通过数据库中的信息设置，resouce和role
        Session session = this.userDao.openSession();
        List<User> userList = this.userDao.getUsersWithNewSession( session );
        for ( User item : userList ) {
            //Hibernate.initialize(item.getMenus());

            ConfigAttribute ca = new SecurityConfig( item.getUsername() );

            if ( this.resourceMap.get( "menuTreeAjax.action" ) == null ) {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add( ca );
                this.resourceMap.put( "menuTreeAjax.action", atts );
            }
            else {
                Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                attsOld = this.resourceMap.get( "menuTreeAjax.action" );
                attsOld.add( ca );
            }
            if ( this.resourceMap.get( "mainMenu.action" ) == null ) {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add( ca );
                this.resourceMap.put( "mainMenu.action", atts );
            }
            else {
                Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                attsOld = this.resourceMap.get( "mainMenu.action" );
                attsOld.add( ca );
            }
            //List<Privilege> tActionList = actionService.findByRoleID(item.getId());
            //List<PrivilegeUser> tActionList = privilegeUserManager.getPrivilegesByUser(item);
            //userDao.getUsersJionMenu(item.getId());
            Set<Menu> menus = item.getMenus();
            //把资源放入到spring security的resouceMap中
            if ( menus != null ) {
                for ( Menu menu : menus ) {
                    //logger.debug("获得角色：["+item.getName()+"]拥有的acton有："+privilegeUser.getMenu().getMenuAction());
                    if ( this.resourceMap.get( menu.getMenuAction() ) == null ) {
                        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                        atts.add( ca );
                        this.resourceMap.put( menu.getMenuAction(), atts );
                    }
                    else {
                        Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                        attsOld = this.resourceMap.get( menu.getMenuAction() );
                        attsOld.add( ca );
                    }
                    //this.resourceMap.put(menu.getMenuAction(), atts);
                }
            }
        }
        List<Role> roleList = this.roleDao.getRolesWithNewSession( session );
        for ( Role item : roleList ) {
            ConfigAttribute ca = new SecurityConfig( item.getName() );
            //List<Privilege> tActionList = actionService.findByRoleID(item.getId());
            //List<Privilege> tActionList = privilegeManager.getPrivilegesByRole(item);
            Set<Menu> menus = item.getMenus();
            //把资源放入到spring security的resouceMap中
            if ( menus != null ) {
                for ( Menu menu : menus ) {
                    //logger.debug("获得角色：["+item.getName()+"]拥有的acton有："+privilegeUser.getMenu().getMenuAction());
                    if ( this.resourceMap.get( menu.getMenuAction() ) == null ) {
                        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                        atts.add( ca );
                        this.resourceMap.put( menu.getMenuAction(), atts );
                    }
                    else {
                        Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                        attsOld = this.resourceMap.get( menu.getMenuAction() );
                        attsOld.add( ca );
                    }
                    //this.resourceMap.put(menu.getMenuAction(), atts);
                }
            }
        }

        /*//通过硬编码设置，resouce和role
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        ConfigAttribute ca = new SecurityConfig("admin"); 
        atts.add(ca); 
        resourceMap.put("/jsp/admin.jsp", atts); 
        resourceMap.put("/swf/zara.html", atts);*/
        this.userDao.closeSession( session );

        HaspDogHandler.getInstance(menuManager);
    }

    // According to a URL, Find out permission configuration of this URL.
    public Collection<ConfigAttribute> getAttributes( Object object )
        throws IllegalArgumentException {
        try {
            //loadResourceDefine();
        }
        catch ( Exception e ) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if ( logger.isDebugEnabled() ) {
            logger.debug( "getAttributes(Object) - start" ); //$NON-NLS-1$
        }
        // guess object is a URL.
        String url = ( (FilterInvocation) object ).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        Collection<ConfigAttribute> returnCollection = null;
        while ( ite.hasNext() ) {
            String resURL = ite.next();
            if(resURL==null||resURL.equals("")){
            	continue;
            }
            String[] resURLs = resURL.split("\\?");
    		if(resURLs.length<=2){
    			String params = "";
    			if(resURLs.length==2){
    				params = "?"+resURLs[1];
    			}
    			resURL = resURLs[0]+".action"+params;
    		}
            //if (urlMatcher.pathMatchesUrl(url, resURL)) {
            if ( url.contains( resURL ) && !url.contains( "/denied.jsp" ) ) {
                if(returnCollection==null){
                	returnCollection = resourceMap.get( resURL );
                }else{
                	returnCollection.addAll(resourceMap.get( resURL ));
                }
            }
        }
        if ( logger.isDebugEnabled() ) {
            logger.debug( "getAttributes(Object) - end" ); //$NON-NLS-1$
        }
        if(returnCollection!=null){
        	return returnCollection;
        }else{
        	return null;
        }
    }

    public void refresh() {
    	this.resourceMap = multiResourceMap.get(currentSF);
//        this.resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        //通过数据库中的信息设置，resouce和role
        Session session = this.userDao.openSession();
        List<User> userList = this.userDao.getUsersWithNewSession( session );
        for ( User item : userList ) {
            //Hibernate.initialize(item.getMenus());

            ConfigAttribute ca = new SecurityConfig( item.getUsername() );

            if ( this.resourceMap.get( "menuTreeAjax.action" ) == null ) {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add( ca );
                this.resourceMap.put( "menuTreeAjax.action", atts );
            }
            else {
                Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                attsOld = this.resourceMap.get( "menuTreeAjax.action" );
                attsOld.add( ca );
            }
            if ( this.resourceMap.get( "mainMenu.action" ) == null ) {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add( ca );
                this.resourceMap.put( "mainMenu.action", atts );
            }
            else {
                Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                attsOld = this.resourceMap.get( "mainMenu.action" );
                attsOld.add( ca );
            }
            //List<Privilege> tActionList = actionService.findByRoleID(item.getId());
            //List<PrivilegeUser> tActionList = privilegeUserManager.getPrivilegesByUser(item);
            //userDao.getUsersJionMenu(item.getId());
            Set<Menu> menus = item.getMenus();
            //把资源放入到spring security的resouceMap中
            if ( menus != null ) {
                for ( Menu menu : menus ) {
                    //logger.debug("获得角色：["+item.getName()+"]拥有的acton有："+privilegeUser.getMenu().getMenuAction());
                    if ( this.resourceMap.get( menu.getMenuAction() ) == null ) {
                        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                        atts.add( ca );
                        this.resourceMap.put( menu.getMenuAction(), atts );
                    }
                    else {
                        Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                        attsOld = this.resourceMap.get( menu.getMenuAction() );
                        attsOld.add( ca );
                    }
                    //this.resourceMap.put(menu.getMenuAction(), atts);
                }
            }
        }
        List<Role> roleList = this.roleDao.getRolesWithNewSession( session );
        for ( Role item : roleList ) {
            ConfigAttribute ca = new SecurityConfig( item.getName() );
            //List<Privilege> tActionList = actionService.findByRoleID(item.getId());
            //List<Privilege> tActionList = privilegeManager.getPrivilegesByRole(item);
            Set<Menu> menus = item.getMenus();
            //把资源放入到spring security的resouceMap中
            if ( menus != null ) {
                for ( Menu menu : menus ) {
                    //logger.debug("获得角色：["+item.getName()+"]拥有的acton有："+privilegeUser.getMenu().getMenuAction());
                    if ( this.resourceMap.get( menu.getMenuAction() ) == null ) {
                        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                        atts.add( ca );
                        this.resourceMap.put( menu.getMenuAction(), atts );
                    }
                    else {
                        Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                        attsOld = this.resourceMap.get( menu.getMenuAction() );
                        attsOld.add( ca );
                    }
                    //this.resourceMap.put(menu.getMenuAction(), atts);
                }
            }
        }

        /*//通过硬编码设置，resouce和role
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        ConfigAttribute ca = new SecurityConfig("admin"); 
        atts.add(ca); 
        resourceMap.put("/jsp/admin.jsp", atts); 
        resourceMap.put("/swf/zara.html", atts);*/
        this.userDao.closeSession( session );
    }

    public boolean supports( Class<?> clazz ) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

}