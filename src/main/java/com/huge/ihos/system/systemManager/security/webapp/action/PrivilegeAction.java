package com.huge.ihos.system.systemManager.security.webapp.action;

import java.util.List;
import java.util.Set;

import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.role.service.RoleManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.webapp.action.JqGridBaseAction;

public class PrivilegeAction
    extends JqGridBaseAction
     {

    private String roleName;

    private RoleManager roleManager;

    private String content = "";

    private Long roleId;

    private UserManager userManager;

    private MenuManager menuManager;

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager( MenuManager menuManager ) {
        this.menuManager = menuManager;
    }

    private Long userId;

    private String contentUser = "";

    public String getContentUser() {
        return contentUser;
    }

    public void setContentUser( String contentUser ) {
        this.contentUser = contentUser;
    }

    private String type;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager( UserManager userManager ) {
        this.userManager = userManager;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId( Long roleId ) {
        this.roleId = roleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName( String roleName ) {
        this.roleName = roleName;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }

    public void setRoleManager( RoleManager roleManager ) {
        this.roleManager = roleManager;
    }



    /**
     * 获取role对应的privilege
     */
    public String getPrivilegesByRole() {
        if ( roleName != null & !roleName.equals( "" ) ) {
            if ( roleName.contains( "ROLE_ADMIN" ) ) {
                List<Menu> menuList = menuManager.getAllEnabled();
                for ( Menu menu : menuList ) {
                    content += menu.getMenuId() + ",";
                }
            }
            else {
                String[] roleNameArr = roleName.split( "," );
                for ( String rn : roleNameArr ) {
                    if ( !rn.trim().equals( "" ) ) {
                        Role role = roleManager.getRole( rn );
                        roleId = role.getId();
                        Set<Menu> menus = role.getMenus();
                        for ( Menu menu : menus ) {
                            content += menu.getMenuId() + ",";
                        }
                    }
                }
            }
        }
        return SUCCESS;
    }

    /**
     * 获取user对应的privilege
     */
    public String getPrivilegesByUser() {
        if ( userId != null ) {
            User user = userManager.get( userId );
            Set<Menu> menus = user.getMenus();
            for ( Menu menu : menus ) {
                contentUser += menu.getMenuId() + ",";
            }

        }
        return SUCCESS;
    }

    /**
     * 根据type获取权限
     * @return
     */
    public String getPrivileges() {
        if ( type != null && !type.equals( "" ) ) {
            if ( type.equals( "role" ) ) {
                getPrivilegesByRole();
            }
            else if ( type.equals( "user" ) ) {
                getPrivilegesByUser();
            }
            else {
                if ( roleName == null || roleName.equals( "" ) ) {
                    User user = userManager.get( userId );
                    Set<Role> roles = user.getRoles();
                    for ( Role role : roles ) {
                        roleName += role.getName() + ",";
                    }
                }
                getPrivilegesByRole();
                getPrivilegesByUser();
            }
        }
        return SUCCESS;
    }

}
