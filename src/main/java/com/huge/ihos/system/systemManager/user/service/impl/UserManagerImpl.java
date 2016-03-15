package com.huge.ihos.system.systemManager.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.dataprivilege.dao.DataPrivilegeDao;
import com.huge.ihos.system.systemManager.dataprivilege.dao.UserDataPrivilegeDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilegeBean;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.service.UserExistsException;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.ihos.system.systemManager.user.service.UserService;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service( "userManager" )
public class UserManagerImpl
    extends GenericManagerImpl<User, Long>
    implements UserManager, UserService {
    private PasswordEncoder passwordEncoder;

    private UserDao userDao;
    private DepartmentDao departmentDao;
    private DataPrivilegeDao dataPrivilegeDao;
    private UserDataPrivilegeDao userDataPrivilegeDao;
    @Autowired
	public void setDataPrivilegeDao(DataPrivilegeDao dataPrivilegeDao) {
		this.dataPrivilegeDao = dataPrivilegeDao;
	}
	@Autowired
	public void setUserDataPrivilegeDao(UserDataPrivilegeDao userDataPrivilegeDao) {
		this.userDataPrivilegeDao = userDataPrivilegeDao;
	}

	@Autowired
    public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao( UserDao userDao ) {
        this.dao = userDao;
        this.userDao = userDao;
    }
    
    @Autowired
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

    /**
     * {@inheritDoc}
     */
    public User getUser( String userId ) {
        return userDao.get( new Long( userId ) );

    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser( User user )
        throws UserExistsException {
        /*
         * Integer v = 0; if (v == null) { // if new user, lowercase userId
         * user.setUsername(user.getUsername().toLowerCase()); }
         * 
         * // Get and prepare password management-related artifacts boolean
         * passwordChanged = false; if (passwordEncoder != null) { // Check
         * whether we have to encrypt (or re-encrypt) the password if (v ==
         * null) { // New user, always encrypt passwordChanged = true; } else {
         * // Existing user, check password in DB String currentPassword =
         * userDao.getUserPassword(user.getUsername()); if (currentPassword ==
         * null) { passwordChanged = true; } else { if
         * (!currentPassword.equals(user.getPassword())) { passwordChanged =
         * true; } } }
         * 
         * // If password was changed (or new user), encrypt it if
         * (passwordChanged) {
         * user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
         * null)); } } else {
         * log.warn("PasswordEncoder not set, skipping password encryption...");
         * }
         */
        user.setPassword( passwordEncoder.encodePassword( user.getPassword(), null ) );

        if ( user.getId() != null ) {
            //User tempUser = this.userDao.get(user.getId());
            user.setPassword( user.getConfirmPassword() );
        }

        try {
            return userDao.saveUser( user );
        }
        catch ( DataIntegrityViolationException e ) {
            // e.printStackTrace();
            log.warn( e.getMessage() );
            throw new UserExistsException( "User '" + user.getUsername() + "' already exists!" );
        }
        catch ( JpaSystemException e ) { // needed for JPA
            // e.printStackTrace();
            log.warn( e.getMessage() );
            throw new UserExistsException( "User '" + user.getUsername() + "' already exists!" );
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeUser( String userId ) {
        log.debug( "removing user: " + userId );
        userDao.remove( new Long( userId ) );
    }

    /**
     * {@inheritDoc}
     * 
     * @param username
     *            the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException
     *             thrown when username not found
     */
    public User getUserByUsername( String username )
        throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername( username );
    }

    /**
     * {@inheritDoc}
     */
    public List<User> search( String searchTerm ) {
       // return super.search( searchTerm, User.class );
        return null;
    }

    public JQueryPager getUserCriteria( JQueryPager paginatedList ) {
        return userDao.getUserCriteria( paginatedList );
    }

    public boolean modifyPwd( User user, String oldPwd, String newPwd )
        throws UserExistsException {
        String zhuanPwd = passwordEncoder.encodePassword( oldPwd, null );
        if ( user.getPassword().equals( zhuanPwd ) ) {
            newPwd = passwordEncoder.encodePassword( newPwd, null );
            user.setPassword( newPwd );
            user.setConfirmPassword( newPwd );
            userDao.modifyPwd( user );
            return true;
        }
        return false;
    }

    public List searchDictionary( String entityName, String str ) {
        return userDao.searchDictionary( entityName, str );
    }

    public User getCurrentLoginUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        User user = this.getUserByUsername( username );
        return user;
    }

	@Override
	public int getUserCountByRooId(String rootId) {
		int userCount = 0;
		List<User> users = this.getAll();
		for(User user : users){
			Set<Menu> menus = user.getRootMenus();
			for(Menu menu:menus){
				if(rootId.equals(menu.getMenuId())){
					userCount++;
				}
			}
		}
		return userCount;
	}

	@Override
	public List getUserByRoleName(String roleName) {
		return userDao.getUserByRoleName(roleName);
	}
	
	@Override
	public String getDateBaseName() {
		return userDao.getDateBaseName();
	}

	@Override
	public List<User>  getUsersByDept(Department department) {
		List<User> users = getUsersByPDept(department);
		return users;
	}
	
	private List<User>  getUsersByPDept(Department department){
		List<User> users = null;
		if(department.getLeaf()!=null&&department.getLeaf()){
			users = userDao.getUsersByDept(department);
		}else{
			if(users==null){
				users = new ArrayList<User>();
			}
			List<Department> departments = departmentDao.getAllSubByParent(department.getDepartmentId());
			for (int i = 0; i < departments.size(); i++) {
				List<User> userTemp = getUsersByPDept(department);
				if(userTemp!=null){
					users.addAll(userTemp);
				}
			}
		}
		return users;
	}

	@Override
	public List<User>  getUsersByRoles(Set<Role> roles) {
		List<User> users = null;
		Iterator<Role> roleIt = roles.iterator();
		while (roleIt.hasNext()) {
			if(users==null){
				users = new ArrayList<User>();
			}
			Role role = roleIt.next();
			List<User> userTemp = userDao.getUsersByRole(role);
			if(userTemp!=null){
				users.addAll(userTemp);
			}
		}
		return users;
	}
	
	@Override
	public Map getDataPrivates(String userId) {
		Map<String, Set<UserDataPrivilegeBean>> userDataPrivilegeMap = new HashMap<String, Set<UserDataPrivilegeBean>>();
		try {
			User user = userDao.get(Long.parseLong(userId));
			Set<Role> roles = user.getRoles();
			//Set<UserDataPrivilegeBean> userPrivilegeSet = new TreeSet<UserDataPrivilegeBean>();
			for(Role role : roles){
				List<DataPrivilege> dataPrivileges = dataPrivilegeDao.getDataPrivilegesByRole(""+role.getId());
				for(DataPrivilege dataPrivilege : dataPrivileges){
					UserDataPrivilegeBean userDataPrivilegeTemp = new UserDataPrivilegeBean();
					userDataPrivilegeTemp.setClassCode(dataPrivilege.getClassCode());
					userDataPrivilegeTemp.setOrgCode(dataPrivilege.getOrgCode());
					userDataPrivilegeTemp.setCopyCode(dataPrivilege.getCopyCode());
					userDataPrivilegeTemp.setPeriodYear(dataPrivilege.getPeriodYear());
					userDataPrivilegeTemp.setItem(dataPrivilege.getItem());
					userDataPrivilegeTemp.setItemName(dataPrivilege.getItemName());
					if(dataPrivilege.getWriteRight()!=null&&dataPrivilege.getWriteRight()==true){
						userDataPrivilegeTemp.setRightType("2");
					}else if(dataPrivilege.getReadRight()!=null&&dataPrivilege.getReadRight()==true){
							userDataPrivilegeTemp.setRightType("1");
					}else{
						continue;
					}
					//userPrivilegeSet.add(userDataPrivilegeTemp);
					if(userDataPrivilegeMap.get(userDataPrivilegeTemp.getClassCode())==null){
						Set<UserDataPrivilegeBean> userPrivilegeSet = new HashSet<UserDataPrivilegeBean>();
						userPrivilegeSet.add(userDataPrivilegeTemp);
						userDataPrivilegeMap.put(userDataPrivilegeTemp.getClassCode(), userPrivilegeSet);
					}else{
						Set<UserDataPrivilegeBean> userPrivilegeSet = userDataPrivilegeMap.get(userDataPrivilegeTemp.getClassCode());
						userPrivilegeSet.add(userDataPrivilegeTemp);
						userDataPrivilegeMap.put(userDataPrivilegeTemp.getClassCode(), userPrivilegeSet);
					}
				}
			}
			List<UserDataPrivilege> dataPrivileges = userDataPrivilegeDao.getByUserId(userId);
			for(UserDataPrivilege dataPrivilege : dataPrivileges){
				UserDataPrivilegeBean userDataPrivilegeTemp = new UserDataPrivilegeBean();
				userDataPrivilegeTemp.setClassCode(dataPrivilege.getClassCode());
				userDataPrivilegeTemp.setOrgCode(dataPrivilege.getOrgCode());
				userDataPrivilegeTemp.setCopyCode(dataPrivilege.getCopyCode());
				userDataPrivilegeTemp.setPeriodYear(dataPrivilege.getPeriodYear());
				userDataPrivilegeTemp.setItem(dataPrivilege.getItem());
				userDataPrivilegeTemp.setItemName(dataPrivilege.getItemName());
				//userPrivilegeSet.add(userDataPrivilegeTemp);
				if(dataPrivilege.getWriteRight()!=null&&dataPrivilege.getWriteRight()==true){
					userDataPrivilegeTemp.setRightType("2");
				}else if(dataPrivilege.getReadRight()!=null&&dataPrivilege.getReadRight()==true){
						userDataPrivilegeTemp.setRightType("1");
				}else{
					continue;
				}
				if(userDataPrivilegeMap.get(userDataPrivilegeTemp.getClassCode())==null){
					Set<UserDataPrivilegeBean> userPrivilegeSet = new HashSet<UserDataPrivilegeBean>();
					userPrivilegeSet.add(userDataPrivilegeTemp);
					userDataPrivilegeMap.put(userDataPrivilegeTemp.getClassCode(), userPrivilegeSet);
				}else{
					Set<UserDataPrivilegeBean> userPrivilegeSet = userDataPrivilegeMap.get(userDataPrivilegeTemp.getClassCode());
					userPrivilegeSet.add(userDataPrivilegeTemp);
					userDataPrivilegeMap.put(userDataPrivilegeTemp.getClassCode(), userPrivilegeSet);
				}
			}
			/*List<PrivilegeClass> privilegeClasses = privilegeClassDao.getAllExceptDisable();
			for(PrivilegeClass privilegeClass : privilegeClasses){
				
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDataPrivilegeMap;
	}
	@Override
	public Set<UserDataPrivilegeBean> getDataPrivatesByClass(String userId, String classCode) {
		Set<UserDataPrivilegeBean> userPrivilegeSet = new HashSet<UserDataPrivilegeBean>();
		try {
			User user = userDao.get(Long.parseLong(userId));
			Set<Role> roles = user.getRoles();
			//Set<UserDataPrivilegeBean> userPrivilegeSet = new TreeSet<UserDataPrivilegeBean>();
			for(Role role : roles){
				List<DataPrivilege> dataPrivileges = dataPrivilegeDao.getDataPrivilegesByRole(""+role.getId(),classCode);
				for(DataPrivilege dataPrivilege : dataPrivileges){
					UserDataPrivilegeBean userDataPrivilegeTemp = new UserDataPrivilegeBean();
					userDataPrivilegeTemp.setClassCode(dataPrivilege.getClassCode());
					userDataPrivilegeTemp.setOrgCode(dataPrivilege.getOrgCode());
					userDataPrivilegeTemp.setCopyCode(dataPrivilege.getCopyCode());
					userDataPrivilegeTemp.setPeriodYear(dataPrivilege.getPeriodYear());
					userDataPrivilegeTemp.setItem(dataPrivilege.getItem());
					if(dataPrivilege.getWriteRight()!=null&&dataPrivilege.getWriteRight()==true){
						userDataPrivilegeTemp.setRightType("2");
					}else if(dataPrivilege.getReadRight()!=null&&dataPrivilege.getReadRight()==true){
							userDataPrivilegeTemp.setRightType("1");
					}else{
						continue;
					}
					//userPrivilegeSet.add(userDataPrivilegeTemp);
					userPrivilegeSet.add(userDataPrivilegeTemp);
				}
			}
			List<UserDataPrivilege> dataPrivileges = userDataPrivilegeDao.findByUserIdAndClass(userId,classCode);
			for(UserDataPrivilege dataPrivilege : dataPrivileges){
				UserDataPrivilegeBean userDataPrivilegeTemp = new UserDataPrivilegeBean();
				userDataPrivilegeTemp.setClassCode(dataPrivilege.getClassCode());
				userDataPrivilegeTemp.setOrgCode(dataPrivilege.getOrgCode());
				userDataPrivilegeTemp.setCopyCode(dataPrivilege.getCopyCode());
				userDataPrivilegeTemp.setPeriodYear(dataPrivilege.getPeriodYear());
				userDataPrivilegeTemp.setItem(dataPrivilege.getItem());
				//userPrivilegeSet.add(userDataPrivilegeTemp);
				if(dataPrivilege.getWriteRight()!=null&&dataPrivilege.getWriteRight()==true){
					userDataPrivilegeTemp.setRightType("2");
				}else if(dataPrivilege.getReadRight()!=null&&dataPrivilege.getReadRight()==true){
						userDataPrivilegeTemp.setRightType("1");
				}else{
					continue;
				}
				userPrivilegeSet.add(userDataPrivilegeTemp);
			}
			/*List<PrivilegeClass> privilegeClasses = privilegeClassDao.getAllExceptDisable();
			for(PrivilegeClass privilegeClass : privilegeClasses){
				
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userPrivilegeSet;
	}
	@Override
	public String getDataPrivatesByClass2Sql(String userId, String classCode) {
		Set<UserDataPrivilegeBean> userPrivilegeSet = getDataPrivatesByClass(userId, classCode);
		String priviStr = "";
		if(userPrivilegeSet!=null){
			Iterator<UserDataPrivilegeBean> upIt = userPrivilegeSet.iterator();
			while (upIt.hasNext()) {
				UserDataPrivilegeBean userDataPrivilegeBean = upIt.next();
				priviStr += "'"+userDataPrivilegeBean.getItem()+"',";
			}
			if(!"".equals(priviStr)){
				priviStr = OtherUtil.subStrEnd(priviStr, ",");
				priviStr = "("+priviStr+")";
			}else{
				priviStr = "('')";
			}
		}
		return priviStr;
	}
	@Override
	public String getDataPrivatesByClass2Str(String userId, String classCode) {
		Set<UserDataPrivilegeBean> userPrivilegeSet = getDataPrivatesByClass(userId, classCode);
		String priviStr = "";
		if(userPrivilegeSet!=null){
			Iterator<UserDataPrivilegeBean> upIt = userPrivilegeSet.iterator();
			while (upIt.hasNext()) {
				UserDataPrivilegeBean userDataPrivilegeBean = upIt.next();
				priviStr += userDataPrivilegeBean.getItem()+",";
			}
			if(!"".equals(priviStr)){
				priviStr = OtherUtil.subStrEnd(priviStr, ",");
			}
		}
		return priviStr;
	}
	@Override
	public Set<UserDataPrivilegeBean> getRoleDataPrivatesByClass(String roleId,
			String classCode) {
		Set<UserDataPrivilegeBean> rolePrivilegeSet = new HashSet<UserDataPrivilegeBean>();
		List<DataPrivilege> dataPrivileges = dataPrivilegeDao.getDataPrivilegesByRole(roleId,classCode);
		for(DataPrivilege dataPrivilege : dataPrivileges){
			UserDataPrivilegeBean userDataPrivilegeTemp = new UserDataPrivilegeBean();
			userDataPrivilegeTemp.setClassCode(dataPrivilege.getClassCode());
			userDataPrivilegeTemp.setOrgCode(dataPrivilege.getOrgCode());
			userDataPrivilegeTemp.setCopyCode(dataPrivilege.getCopyCode());
			userDataPrivilegeTemp.setPeriodYear(dataPrivilege.getPeriodYear());
			userDataPrivilegeTemp.setItem(dataPrivilege.getItem());
			if(dataPrivilege.getWriteRight()!=null&&dataPrivilege.getWriteRight()==true){
				userDataPrivilegeTemp.setRightType("2");
			}else if(dataPrivilege.getReadRight()!=null&&dataPrivilege.getReadRight()==true){
					userDataPrivilegeTemp.setRightType("1");
			}else{
				continue;
			}
			//userPrivilegeSet.add(userDataPrivilegeTemp);
			rolePrivilegeSet.add(userDataPrivilegeTemp);
		}
		return rolePrivilegeSet;
	}
	@Override
	public String getRoleDataPrivatesByClass2Sql(String roleId, String classCode) {
		Set<UserDataPrivilegeBean> userPrivilegeSet = getRoleDataPrivatesByClass(roleId, classCode);
		String priviStr = "";
		if(userPrivilegeSet!=null){
			Iterator<UserDataPrivilegeBean> upIt = userPrivilegeSet.iterator();
			while (upIt.hasNext()) {
				UserDataPrivilegeBean userDataPrivilegeBean = upIt.next();
				priviStr += "'"+userDataPrivilegeBean.getItem()+"',";
			}
			if(!"".equals(priviStr)){
				priviStr = OtherUtil.subStrEnd(priviStr, ",");
				priviStr = "("+priviStr+")";
			}else{
				priviStr = "('')";
			}
		}
		return priviStr;
	}
	@Override
	public String getRoleDataPrivatesByClass2Str(String roleId, String classCode) {
		Set<UserDataPrivilegeBean> userPrivilegeSet = getRoleDataPrivatesByClass(roleId, classCode);
		String priviStr = "";
		if(userPrivilegeSet!=null){
			Iterator<UserDataPrivilegeBean> upIt = userPrivilegeSet.iterator();
			while (upIt.hasNext()) {
				UserDataPrivilegeBean userDataPrivilegeBean = upIt.next();
				priviStr += userDataPrivilegeBean.getItem()+",";
			}
			if(!"".equals(priviStr)){
				priviStr = OtherUtil.subStrEnd(priviStr, ",");
			}
		}
		return priviStr;
	}
}
