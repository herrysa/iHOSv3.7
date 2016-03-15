package com.huge.ihos.system.systemManager.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.webapp.pagers.JQueryPager;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface UserDao
    extends GenericDao<User, Long> {

    /**
     * Gets users information based on login name.
     * @param username the user's username
     * @return userDetails populated userDetails object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException thrown when user not
     * found in database
     */
    @Transactional
    UserDetails loadUserByUsername( String username )
        throws UsernameNotFoundException;

    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    List<User> getUsers();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    User saveUser( User user );

    /**
     * 修改密码
     * @param user
     * @return
     */
    public User modifyPwd( User user );

    /**
     * Retrieves the password in DB for a user
     * @param username the user's username
     * @return the password in DB, if the user is already persisted
     */
    @Transactional( propagation = Propagation.NOT_SUPPORTED )
    String getUserPassword( String username );

    public JQueryPager getUserCriteria( final JQueryPager paginatedList );

    public Session openSession();

    public List getUsersWithNewSession( Session session );

    public void closeSession( Session session );

    public int getUserCountByRooId(String rootId);
    
    public List getUserByRoleName(String roleName);
    
    public String getDateBaseName();
    
    public List<User>  getUsersByDept(Department department);
    
    public List<User>  getUsersByRole(Role role);
}
