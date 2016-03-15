package com.huge.ihos.system.systemManager.security.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.role.service.RoleManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;

public class RoleDataPrivilegeAction
    extends JqGridBaseAction
     {
    protected RoleManager roleManager;

    private List searchEntityClusters;

    private Long roleId;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId( Long userId ) {
        this.userId = userId;
    }

    private String clusters;

    public String getClusters() {
        return clusters;
    }

    public void setClusters( String clusters ) {
        this.clusters = clusters;
    }

    public List getSearchEntityClusters() {
        return searchEntityClusters;
    }

    public void setSearchEntityClusters( List searchEntityClusters ) {
        this.searchEntityClusters = searchEntityClusters;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId( Long roleId ) {
        this.roleId = roleId;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }

    public void setRoleManager( RoleManager roleManager ) {
        this.roleManager = roleManager;
    }

  

    public String RoleDataPrivilegeList() {
        try {
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = roleManager.getPagedRoleSearchDp( pagedRequests, this.roleId );
            this.searchEntityClusters = pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
        }
        catch ( Exception e ) {
            log.error( "RoleDataPrivilegeList Error", e );
        }
        return SUCCESS;
    }

    public String UserRoleDataPrivilegeList() {
        try {
            String roleIds = "";
            Set<Role> roles = userManager.get( userId ).getRoles();
            for ( Role role : roles ) {
                roleIds += "'" + role.getId() + "',";
            }
            if ( !roleIds.equals( "" ) ) {
                roleIds = roleIds.substring( 0, roleIds.length() - 1 );
            }
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = roleManager.getPagedUserSearchDp( pagedRequests, roleIds );
            this.searchEntityClusters = pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
        }
        catch ( Exception e ) {
            log.error( "RoleDataPrivilegeList Error", e );
        }
        return SUCCESS;
    }

    public String edit() {
        return SUCCESS;
    }

    public String save() {
        try {
            StringTokenizer ids = new StringTokenizer( clusters, "," );
            ArrayList l = new ArrayList();
            while ( ids.hasMoreTokens() ) {
                String clusterId = ids.nextToken();
                Long lcId = Long.parseLong( clusterId );
                l.add( lcId );
            }
            Long[] cids = new Long[l.size()];
            l.toArray( cids );
            this.roleManager.saveRoleDataPrivilege( this.roleId, cids );
            return ajaxForward( "保存成功。" );
        }
        catch ( Exception e ) {
            log.error( "RoleDataPrivilegeList Error", e );
            return ajaxForward( "保存失败。" );
        }
    }

    public String searchEntityClusterGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                ArrayList l = new ArrayList();
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    Long lcId = Long.parseLong( removeId );
                    l.add( lcId );
                }
                Long[] cids = new Long[l.size()];
                l.toArray( cids );
                roleManager.removeRoleDataPrivilege( this.roleId, cids );
                gridOperationMessage = this.getText( "role.dataPrivilegeDeleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            return SUCCESS;
        }
        catch ( Exception e ) {
            log.error( "checkPeriodGridEdit Error", e );
            if ( log.isDebugEnabled() )
                gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
            return ajaxForward( false, gridOperationMessage, false );
        }
    }
}
