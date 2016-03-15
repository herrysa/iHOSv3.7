package com.huge.ihos.system.systemManager.menu.webapp.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.huge.dao.hibernate.ActionEntityThreadLocalHolder;
import com.huge.exceptions.BusinessException;
import com.huge.ihos.hasp.service.DogService;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.ihos.system.systemManager.operateLog.service.OperateLogManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.util.HaspDogHandler;
import com.huge.webapp.action.BaseAction;
import com.huge.webapp.ztree.ZTreeSimpleNode;

public class MenuTreeAction
    extends BaseAction {
    private static DogService dogService;// = HaspDogHandler.getInstance().getDogService();
    
    static {
        try {
            dogService = HaspDogHandler.getInstance().getDogService();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    // private String iconPath="/scripts/zTree/css/zTreeStyle/img/diy/";
    private String iconLeaf = "leaf.png";

    private String iconRoot = "jx_sys.png";

    private String iconParentClose = "folder.png";

    private String iconParentOpen = "folder.png";

    private MenuManager menuManager;

    private List menuTreeList;

    private String menuTreeListString;

    OperateLog operateLog;

    /*
     * private PrivilegeManager privilegeManager; private PrivilegeUserManager privilegeUserManager;
     */

    private String random = "" + Math.round( Math.random() * 10000 );

    /*
     * public static DogService getDogService() { return dogService; } public static void setDogService( DogService
     * dogService ) { MenuTreeAction.dogService = dogService; }
     */

    private UserManager userManager;

    private OperateLogManager operateLogManager;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager( UserManager userManager ) {
        this.userManager = userManager;
    }

    /*
     * public PrivilegeManager getPrivilegeManager() { return privilegeManager; } public void
     * setPrivilegeManager(PrivilegeManager privilegeManager) { this.privilegeManager = privilegeManager; } public
     * PrivilegeUserManager getPrivilegeUserManager() { return privilegeUserManager; } public void
     * setPrivilegeUserManager(PrivilegeUserManager privilegeUserManager) { this.privilegeUserManager =
     * privilegeUserManager; }
     */

    private String id;

    private String pid;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid( String pid ) {
        this.pid = pid;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager( MenuManager menuManager ) {
        this.menuManager = menuManager;
    }

    public List getMenuTreeList() {
        return menuTreeList;
    }

    public void setMenuTreeList( List menuTreeList ) {
        this.menuTreeList = menuTreeList;
    }

    public String getMenuTreeListString() {
        return menuTreeListString;
    }

    public void setMenuTreeListString( String menuTreeListString ) {
        this.menuTreeListString = menuTreeListString;
    }

    public String initTreeJsonString() {
        try {

            this.menuTreeList = this.nodeList( id );
            this.menuTreeListString = JSONArray.fromObject( this.menuTreeList ).toString();

            HttpServletResponse response = ServletActionContext.getResponse();

            response.setCharacterEncoding( "UTF-8" );
            // System.out.println(menuTreeListString);
            response.getWriter().write( menuTreeListString );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isHavePrivilege( Menu menu ) {
        boolean flag = false;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        User user = userManager.getUserByUsername( username );
        Set<Role> roles = user.getRoles();
        Iterator<Role> roleIt = roles.iterator();
        while ( roleIt.hasNext() ) {
            Role role = roleIt.next();
            if ( role.getName().equals( "ROLE_ADMIN" ) ) {
                flag = true;
                break;
            }
            /*
             * List<Privilege> privileges = privilegeManager.getPrivilegesByRole(role); Iterator<Privilege> privilegeIt
             * = privileges.iterator();
             */
            Set<Menu> menus = role.getMenus();
            Iterator<Menu> privilegeIt = menus.iterator();
            for ( Menu menuItem : menus ) {
                if ( menuItem.getMenuId().equals( menu.getMenuId() ) ) {
                    flag = true;
                    break;
                }
            }
        }

        if ( !flag ) {
            Set<Menu> menus = user.getMenus();
            Iterator<Menu> privilegeIt = menus.iterator();
            for ( Menu menuItem : menus ) {
                if ( menuItem.getMenuId().equals( menu.getMenuId() ) ) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    private List nodeList( String id ) {

        String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        // themeName = (String)this.getRequest().getAttribute("themeName");
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";// logo.jpg

        List menus = null;
        if ( id == null )
            menus = this.menuManager.getAllEnabled();
        else
            menus = this.menuManager.getAllByParent( id );
        List list = new ArrayList();

        Iterator itr = menus.iterator();
        while ( itr.hasNext() ) {
            Menu menu = (Menu) itr.next();
            if ( !isHavePrivilege( menu ) ) {
                continue;
            }
            ZTreeSimpleNode rt = new ZTreeSimpleNode();
            rt.setName( menu.getMenuName() );
            rt.setId( menu.getMenuId() );
            // if(menu.isLeaf())
            rt.setIsParent( !menu.isLeaf() );
            // rt.setOpen(true);
            // rt.setIcon("/scripts/zTree/css/zTreeStyle/img/diy/5.png");
            if ( menu.getParent() == null ) {
                rt.setpId( "" );
            }
            else {
                rt.setpId( menu.getParent().getMenuId() );
            }

            if ( rt.getIsParent() && rt.getpId().equals( "" ) ) {
                rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                rt.setOpen( true );
            }
            else if ( rt.getIsParent() && !rt.getpId().equals( "" ) ) {
                rt.setIcon( this.getContextPath() + iconPath + this.iconParentClose );
            }
            //
            else if ( rt.getpId().equals( "" ) ) {
                rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                rt.setOpen( true );
            }
            else {
                rt.setIcon( this.getContextPath() + iconPath + this.iconLeaf );
                // rt.setActionUrl(menu.getMenuAction());
            }

            rt.setActionUrl( menu.getMenuAction() );
            list.add( rt );
        }

        return list;
    }

    private String targetUrl;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl( String targetUrl ) {
        this.targetUrl = targetUrl;
    }

    /**
     * @return
     */
    public String menuClicked() {

        try {
            if ( this.dogService.login() == 0 )
                this.dogService.logout();

            HttpServletRequest req = this.getRequest();
            String menuId = req.getParameter( "menuId" );
            Menu m = this.menuManager.get( menuId );



            String entityDef = m.getEntityName();
            if ( entityDef != null && !entityDef.trim().equalsIgnoreCase( "" ) ) {
                ActionEntityThreadLocalHolder.setActionEntityName( entityDef );
                this.log.info( entityDef );
                
               // end.set( entityDef );
            }else{
                ActionEntityThreadLocalHolder.setActionEntityName( null );
            }

            
            
            
            List list = this.menuManager.getMenuChain( menuId );
            req.getSession().setAttribute( "MenuPathBarString", getMenuBarPathString( list ) );
            String enUrl = m.getMenuAction();
            
            if ( enUrl.contains( "?" ) ) {
            	enUrl += "&menuId="+menuId+"&menuName="+m.getMenuName()+"&radom=" + random;
            }
            else {
            	enUrl += "?menuId="+menuId+"&menuName="+m.getMenuName()+"&radom=" + random;
            }
            
            enUrl = URLEncoder.encode( enUrl, "UTF-8" );

            enUrl = enUrl.replaceAll( "%3F", "?" );
            enUrl = enUrl.replaceAll( "%3D", "=" );
            enUrl = enUrl.replaceAll( "%25", "%" );
            enUrl = enUrl.replaceAll( "%26", "&" );
            enUrl = enUrl.replaceAll( "%2F", "/" );

            this.targetUrl = enUrl;

           /* if ( this.targetUrl.contains( "?" ) ) {
                this.targetUrl += "&menuId="+menuId+"&menuName="+menuName+"&radom=" + random;
            }
            else {
                this.targetUrl += "?menuId="+menuId+"&menuName="+menuName+"&radom=" + random;
            }*/

            req.getSession().setAttribute( "navTabId", "navTab" + menuId );

            SecurityContext ctx = SecurityContextHolder.getContext();
            UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
            String un = uds.getUsername();
            User user = userManager.getUserByUsername( un );
            operateLog = new OperateLog();
            operateLog.setOperateObject( m.getMenuName() );
            operateLog.setOperateTime( Calendar.getInstance().getTime() );
            operateLog.setUserName( user.getUsername() );
            operateLog.setOperator( user.getPerson().getName() );
            String remoteIp = req.getRemoteHost();
            if ( remoteIp != null ) {
                operateLog.setUserMachine( req.getRemoteHost() );
            }

            ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor( 1 );
            scheduler.schedule( new Runnable() {
                public void run() {
                    operateLogManager.save( operateLog );
                }
            }, 10, TimeUnit.SECONDS );
            scheduler.shutdown();

        }

        catch ( Exception e1 ) {
            e1.printStackTrace();
            throw new BusinessException( e1.getMessage() );
        }
        return this.SUCCESS;
    }

    private String getMenuBarPathString( List list ) {
        StringBuilder sb = new StringBuilder();

        int i = list.size();
        if ( i != 0 )
            for ( int n = i - 1; n >= 0; n-- ) {
                Menu m = (Menu) list.get( n );
                sb.append( m.getMenuName() );
                // System.out.print(m.getMenuName());
                if ( n > 0 )
                    sb.append( ">" );
            }

        return sb.toString();
    }

    public OperateLogManager getOperateLogManager() {
        return operateLogManager;
    }

    public void setOperateLogManager( OperateLogManager operateLogManager ) {
        this.operateLogManager = operateLogManager;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom( String random ) {
        this.random = random;
    }
}
