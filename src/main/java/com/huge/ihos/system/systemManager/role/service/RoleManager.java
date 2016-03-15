package com.huge.ihos.system.systemManager.role.service;

import java.util.List;

import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface RoleManager
    extends GenericManager<Role, Long> {
    /**
     * {@inheritDoc}
     */
    List getRoles( Role role );

    /**
     * {@inheritDoc}
     */
    Role getRole( String rolename );

    /**
     * {@inheritDoc}
     */
    Role saveRole( Role role );

    /**
     * {@inheritDoc}
     */
    void removeRole( String rolename );

    List getRoleDataPrivilege( Long roleId );

    public JQueryPager getPagedRoleSearchDp( JQueryPager paginatedList, Long roleId );

    public void saveRoleDataPrivilege( Long roleId, Long[] clusterIds );

    public void removeRoleDataPrivilege( Long roleId, Long[] clusterIds );

    public JQueryPager getPagedUserSearchDp( JQueryPager paginatedList, String roleIds );
    
    public int getUserCountByRooId(String rootId);
}
