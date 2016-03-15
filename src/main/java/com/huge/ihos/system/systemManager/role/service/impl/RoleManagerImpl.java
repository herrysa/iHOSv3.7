package com.huge.ihos.system.systemManager.role.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.role.service.RoleManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;

/**
 * Implementation of RoleManager interface.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service( "roleManager" )
public class RoleManagerImpl
    extends GenericManagerImpl<Role, Long>
    implements RoleManager {
    RoleDao roleDao;

    @Autowired
    public RoleManagerImpl( RoleDao roleDao ) {
        super( roleDao );
        this.roleDao = roleDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Role> getRoles( Role role ) {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole( String rolename ) {
        return roleDao.getRoleByName( rolename );
    }

    /**
     * {@inheritDoc}
     */
    public Role saveRole( Role role ) {
        return dao.save( role );
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole( String rolename ) {
        roleDao.removeRole( rolename );
    }

    @Override
    public List getRoleDataPrivilege( Long roleId ) {
        return roleDao.getRoleDataPrivilege( roleId );
    }

    public JQueryPager getPagedRoleSearchDp( JQueryPager paginatedList, Long roleId ) {
        return roleDao.getPagedRoleSearchDp( paginatedList, roleId );
    }

    public JQueryPager getPagedUserSearchDp( JQueryPager paginatedList, String roleIds ) {
        return roleDao.getPagedUserSearchDp( paginatedList, roleIds );
    }

    public void saveRoleDataPrivilege( Long roleId, Long[] clusterIds ) {
        for ( int i = 0; i < clusterIds.length; i++ ) {
            roleDao.saveRoleDataPrivilege( roleId, clusterIds[i] );
        }
    }

    public void removeRoleDataPrivilege( Long roleId, Long[] clusterIds ) {
        for ( int i = 0; i < clusterIds.length; i++ ) {
            roleDao.removeRoleDataPrivilege( roleId, clusterIds[i] );
        }
    }

	@Override
	public int getUserCountByRooId(String rootId) {
		int userCount = 0;
		Set<User> users = new HashSet<User>();
		List<Role> roles = this.getAll();
		for(Role role : roles){
			Set<Menu> menus = role.getRootMenus();
			for(Menu menu:menus){
				if(rootId.equals(menu.getMenuId())){
					users.addAll(role.getUsers());
				}
			}
		}
		userCount = users.size();
		return userCount;
	}
}