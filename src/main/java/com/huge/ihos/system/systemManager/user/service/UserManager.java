package com.huge.ihos.system.systemManager.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilegeBean;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.service.UserExistsException;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> 
 */
public interface UserManager
    extends GenericManager<User, Long> {

    boolean modifyPwd( User user, String oldPwd, String newPwd )
        throws UserExistsException;

    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setUserDao( UserDao userDao );

    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    User getUser( String userId );

    /**
     * Finds a user by their username.
     * @param username the user's username used to login
     * @return User a populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *         exception thrown when user not found
     */
    User getUserByUsername( String username )
        throws UsernameNotFoundException;

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<User> getUsers();

    /**
     * Saves a user's information.
     *
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     * @return user the updated user object
     */
    User saveUser( User user )
        throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    void removeUser( String userId );

    /**
     * Search a user for search terms.
     * @param searchTerm the search terms.
     * @return a list of matches, or all if no searchTerm.
     */
    List<User> search( String searchTerm );

    public JQueryPager getUserCriteria( final JQueryPager paginatedList );

    public List searchDictionary( String entityName, String str );

    public User getCurrentLoginUser();
    
    public int getUserCountByRooId(String rootId);
    
    public List getUserByRoleName(String roleName);
    
    public String getDateBaseName();
    
    public List<User>  getUsersByDept(Department department);
    
    public List<User>  getUsersByRoles(Set<Role> roles);
    
    public Map getDataPrivates(String userId);
    
    public Set<UserDataPrivilegeBean> getRoleDataPrivatesByClass(String roleId, String classCode);
    
    public String getRoleDataPrivatesByClass2Sql(String roleId,String classCode);
    
    public String getRoleDataPrivatesByClass2Str(String roleId,String classCode);
    
    public Set<UserDataPrivilegeBean> getDataPrivatesByClass(String userId,String classCode);
    
    public String getDataPrivatesByClass2Sql(String userId,String classCode);
    
    public String getDataPrivatesByClass2Str(String userId,String classCode);

}
