package com.huge.ihos.system.systemManager.security.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;

import com.huge.Constants;
import com.huge.ihos.multidatasource.DynamicDatabaseManager;
import com.huge.ihos.multidatasource.DynamicSessionFactoryHolder;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.syvariable.service.VariableManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.operateLog.model.OperateLog;
import com.huge.ihos.system.systemManager.operateLog.service.OperateLogManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.SecurityMetadataSourceManager;
import com.huge.ihos.system.systemManager.security.SecurityUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.ihos.system.systemManager.security.service.UserExistsException;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.model.LabelValue;
import com.huge.util.DateUtil;
import com.huge.util.HaspDogHandler;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.SpringContextHelper;

public class IHosUserAction
    extends JqGridBaseAction
    implements  ApplicationContextAware {
    private PersonManager personManager;

    private PasswordEncoder passwordEncoder;

    private List<User> users;

    private List menus;

    private List roleList;

    private User user;

    private Long id;

    private String userName;

    private String oldPwd;

    private String newPwd;

    private String content;

    private List<LabelValue> roles;

    private String userRolesSelected;

    private ApplicationContext applicationContext;

    private SecurityMetadataSourceManager securityMetadataSourceManager;

    private String jsonMessage;

    private UserDao userDao;

    private RoleDao roleDao;

    private OperateLogManager operateLogManager;

    private DepartmentManager departmentManager;
    
    private PeriodManager periodManager;
    
    //private PeriodPlanManager periodPlanManager;
    
    private VariableManager variableManager;

	/*public void setPeriodPlanManager(PeriodPlanManager periodPlanManager) {
		this.periodPlanManager = periodPlanManager;
	}*/


	public void setVariableManager(VariableManager variableManager) {
		this.variableManager = variableManager;
	}

	private List departList;
    
    private SystemVariable systemVariable;
    
    private String pagefrom;

	@Autowired
    private SessionRegistry sessionRegistry;

    private List loginTime;

    private List loginIp;
    
    private String maxData = "";

    public String getMaxData() {
    	if(ContextUtil.versionType==0){
    		return "";
    	}
    	Calendar c = Calendar.getInstance();
    	if(ContextUtil.maxDate==-9){
    		maxData = "";
    	}else{
    		c.setTimeInMillis(ContextUtil.maxDate);
    		try {
    			maxData = DateUtil.convertDateToString(c.getTime());
			} catch (Exception e) {
				maxData = "2014-01-01";
			}
    	}
		return maxData;
	}

	public void setMaxData(String maxData) {
		this.maxData = maxData;
	}

	Map jsonMap = new HashMap<String, String>();

    public Map getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap( Map jsonMap ) {
        this.jsonMap = jsonMap;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }

    public void setJsonMessage( String jsonMessage ) {
        this.jsonMessage = jsonMessage;
    }

    public String getUserRolesSelected() {
        return userRolesSelected;
    }

    public void setUserRolesSelected( String userRolesSelected ) {
        this.userRolesSelected = userRolesSelected;
    }

    //private PrivilegeUserManager privilegeUserManager;
    private MenuManager menuManager;

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager( MenuManager menuManager ) {
        this.menuManager = menuManager;
    }

    public List<LabelValue> getRoles() {
        return roles;
    }

    public void setRoles( List<LabelValue> roles ) {
        this.roles = roles;
    }

    /*public PrivilegeUserManager getPrivilegeUserManager() {
    	return privilegeUserManager;
    }

    public void setPrivilegeUserManager(PrivilegeUserManager privilegeUserManager) {
    	this.privilegeUserManager = privilegeUserManager;
    }*/

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public PersonManager getPersonManager() {
        return personManager;
    }

    public void setPersonManager( PersonManager personManager ) {
        this.personManager = personManager;
    }

    public List getUsers() {
        return users;
    }

    public SystemVariable getSystemVariable() {
		return systemVariable;
    }

    public void setSystemVariable(SystemVariable systemVariable) {
		this.systemVariable = systemVariable;
    }

    public String getPagefrom() {
		return pagefrom;
	}

	public void setPagefrom(String pagefrom) {
		this.pagefrom = pagefrom;
	}
    /*
     * public String list() { users = userManager.search(query, User.class);
     * return SUCCESS; }
     */

    public String OnlineUsers() {
        departList = this.departmentManager.getAllDept();
        return SUCCESS;
    }

    public String modifyPwd()
        throws UserExistsException {
        if ( userManager.modifyPwd( getSessionUser(), oldPwd, newPwd ) )
            return ajaxForward( true, "密码修改成功!", false );
        else
            return ajaxForward( false, "密码修改失败，请询问管理员后重新修改!", false );
    }

    public String delete() {
        userManager.remove( user.getId() );
        saveMessage( getText( "user.deleted" ) );

        return "edit_success";
    }

    private int editType = 0; // 0:new,1:edit

    public String edit() {
        if ( id != null ) {
            user = userManager.get( id );
            /*content = "";
            Set<String> menuIdSet = new HashSet<String>();
            Set<Menu> userMenus = user.getMenus();
            for(Menu menu : userMenus){
            	menuIdSet.add(menu.getMenuId());
            }*/
            /*Set<Role> roles = user.getRoles();
            
            for(Role role : roles){
            	Set<Menu> roleMenus = role.getMenus();
            	for(Menu menu : userMenus){
            		menuIdSet.add(menu.getMenuId());
            	}
            }*/
            /*for(String menuId : menuIdSet){
            	content += menuId+",";
            }*/
            this.editType = this.editType == 2 ? 2 : 1;
        }
        else {
            user = new User();
            user.addRole( new Role( Constants.USER_ROLE ) );
            this.editType = 0;
        }
        roles = getAllRoles();
        return SUCCESS;
    }

    public String save()
        throws Exception {
        try {
            if ( cancel != null ) {
                return "cancel";
            }

            if ( delete != null ) {
                return delete();
            }
            
            boolean isNew = editType == 0 ? true : false;
            User oldUser = null;
            if ( editType == 2 ) {
                user = userManager.get( id );
            }

            if ( !isNew ) {
                user.setConfirmPassword( user.getPassword() );
            }
            if ( editType != 2 ) {
                user.getRoles().clear();
                String[] userRoles = null;
                if ( userRolesSelected != null && !userRolesSelected.equals( "" ) ) {
                    userRoles = userRolesSelected.split( "," );
                }
                for ( int i = 0; userRoles != null && i < userRoles.length; i++ ) {
                    if ( !userRoles[i].trim().equals( "" ) ) {
                        String roleName = userRoles[i];
                        user.addRole( roleManager.getRole( roleName ) );
                    }
                }

                String pid = user.getPerson().getPersonId();
                if ( pid != null && !pid.equals( "" ) ) {
                    user.setPerson( this.personManager.get( pid ) );
                }
                else {
                    user.setPerson( null );
                }
                if(user.getId()!=null&&!"".equals(user.getId())){
                	oldUser = userManager.get( user.getId() );
                	user.setMenus(oldUser.getMenus());
                }
            }

            String[] contentArr;
            if ( content != null && !"".equals(content)&&!content.equals( "undefined" ) ) {
                user.getMenus().clear();
                contentArr = content.split( "," );
                for ( int i = 0; i < contentArr.length; i++ ) {
                    if ( !contentArr[i].trim().equals( "" ) ) {
                        user.addMenu( menuManager.get( contentArr[i] ) );
                    }
                }
            }
            //if(isNew){
        	//Set<Menu> userRootMenu = user.getRootMenus();
            /*for(Menu menu : userRootMenu){
            	int userCount = userManager.getUserCountByRooId(menu.getMenuId());
            	int allowCount = HaspDogHandler.getInstance().getDogService().getSubSystemAllowUser(menu.getMenuId());
            	if(allowCount!=-1&&userCount>allowCount){
            		return ajaxForward(false,"子系统'"+menu.getMenuName()+"'的用户数已达上限！",false);
            	}
            }*/
            //}
            
            userManager.saveUser( user );

            String key = ( isNew ) ? "user.added" : "user.updated";

            saveMessage( getText( key ) );
            jsonMessage = getText( key );

            userDao = (UserDao) applicationContext.getBean( "userDao" );
            roleDao = (RoleDao) applicationContext.getBean( "roleDao" );
            securityMetadataSourceManager = (SecurityMetadataSourceManager) applicationContext.getBean( "SecurityMetadataSourceManager" );
            securityMetadataSourceManager.setUserDao( userDao );
            securityMetadataSourceManager.setRoleDao( roleDao );
//            securityMetadataSourceManager.loadResourceDefine();
            securityMetadataSourceManager.loadResourceDefine(DynamicSessionFactoryHolder.getSessionFactoryName());
        }
        catch ( Exception e ) {
            jsonMessage = e.getMessage();
            return ajaxForward(false, jsonMessage,false );
        }

        return ajaxForward( jsonMessage );
    }

    public String userGridList() {
        try {
        	String excludeIds = "";
        	List excludeUser = userManager.getUserByRoleName("ROLE_ADMIN");
        	for(Object userId : excludeUser){
        		excludeIds += userId+",";
        	}
        	if(excludeIds.equals("")){
        		excludeIds = excludeIds.substring(0,excludeIds.length()-1);
        	}
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            PropertyFilter propertyFilterAdmin = new PropertyFilter("NIL_id",excludeIds);
            filters.add(propertyFilterAdmin);
            
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            //pagedRequests = userManager.getUserCriteria(pagedRequests);
            pagedRequests = userManager.getAppManagerCriteriaWithSearch( pagedRequests, User.class, filters );
            this.users = (List<User>) pagedRequests.getList();

            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "userGridList Error", e );
        }
        return SUCCESS;
    }

    public String userGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( "" + id, "," );
                while ( ids.hasMoreTokens() ) {
                    Long removeId = Long.parseLong( ids.nextToken() );
                    userManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "user.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                userManager.save( user );
                String key = ( oper.equals( "add" ) ) ? "user.added" : "user.updated";
                gridOperationMessage = this.getText( key );
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

    public String userMenuGridList() {
        try {
            List menuList = null;
            Set<Menu> menus = userManager.get( id ).getMenus();
            if ( menus == null || menus.size() == 0 ) {
                menus = new HashSet<Menu>();
            }
            /*if(menus!=null&&menus.size()!=0){
            	menuList = new ArrayList(menus);
            }*/
            //else{
            Set<Role> roles = userManager.get( id ).getRoles();
            Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();
            for ( Role role : roles ) {
                Set<Menu> rolemenus = role.getMenus();
                if ( role.getName().equals( "ROLE_ADMIN" ) ) {
                	List<Menu> enableMenus = menuManager.getAllEnabled();
                	menuList = new ArrayList();
                	for(Menu menu : enableMenus){
                		if((dogMenus.get(menu.getMenuId())==null?false:true)){
                			menuList.add(menu);
                		}
                	}
                    break;
                }
                menus.addAll( rolemenus );
            }
            if ( menuList == null ) {
                Set<String> menuTreeSet = new TreeSet();
                menuList = new ArrayList<Menu>();
                Map menuMap = new HashMap();
                for ( Menu menu : menus ) {
                    menuTreeSet.add( menu.getMenuId() );
                    menuMap.put( menu.getMenuId(), menu );
                }
                for ( String menuId : menuTreeSet ) {
                    menuList.add( menuMap.get( menuId ) );
                }
                //menuList = new ArrayList(menus);
            }

            //}
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            if ( menuList != null ) {
                pagedRequests.setTotalNumberOfRows( menuList.size() );
                int endRow = pagedRequests.getStart() + pagedRequests.getPageSize();
                if ( endRow > menuList.size() ) {
                    endRow = menuList.size();
                }
                menuList = menuList.subList( pagedRequests.getStart(), endRow );
                pagedRequests.setList( menuList );
            }
            this.menus = (List<Menu>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "roleGridList Error", e );
        }
        return SUCCESS;
    }

    public String userRoleGridList() {
        try {
            List roleList = null;
            Set roles = userManager.get( id ).getRoles();
            if ( roles != null && roles.size() != 0 ) {
                roleList = new ArrayList( roles );
            }
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            if ( roleList != null ) {
                pagedRequests.setList( roleList );
                pagedRequests.setTotalNumberOfRows( roleList.size() );
            }
            this.roleList = (List<Role>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "roleGridList Error", e );
        }
        return SUCCESS;
    }

    /*
     * public String userGridEdit() { try{ if (oper.equals("del")) {
     * StringTokenizer ids = new StringTokenizer(selIds, ","); while
     * (ids.hasMoreTokens()) { Long removeId = Long.parseLong(ids.nextToken());
     * userManager.remove(removeId); } gridOperationMessage =
     * this.getText("user.deleted"); return SUCCESS; }
     * 
     * if (oper.equals("add") || oper.equals("edit")) { String error =
     * isValid(); if (!error.equalsIgnoreCase(SUCCESS)) { gridOperationMessage =
     * error; return SUCCESS; } userManager.save(user); String key =
     * (oper.equals("add")) ? "user.added" : "user.updated";
     * gridOperationMessage = this.getText(key); } return SUCCESS; } catch
     * (Exception e) { log.error("checkPeriodGridEdit Error", e);
     * if(log.isDebugEnabled()) gridOperationMessage =
     * e.getMessage()+e.getLocalizedMessage()+e.getStackTrace(); return SUCCESS;
     * } }
     */

    /**
     * @TODO you should add some validation rules those are difficult on client
     *       side.
     * @return
     */

    private String isValid() {
        if ( user == null ) {
            return "Invalid user Data";
        }

        return SUCCESS;

    }

    public void setId( Long id ) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd( String oldPwd ) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd( String newPwd ) {
        this.newPwd = newPwd;
    }

    public void setPasswordEncoder( PasswordEncoder passwordEncoder ) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<LabelValue> getAllRoles() {
        List<Role> roles = roleManager.getRoles( null );
        List<LabelValue> list = new ArrayList<LabelValue>();

        for ( Role role1 : roles ) {
        	if(!role1.getName().equals("ROLE_ADMIN")){
        		list.add( new LabelValue( role1.getChName(), role1.getName() ) );
        	}
        }

        return list;
    }

    public String checkRepeatName() {
        user = userManager.getUserByUsername( userName );
        if ( user != null ) {
            return ajaxForward( false, "用户名'" + userName + "'已存在！", false );
        }
        else {
            return null;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public SecurityMetadataSourceManager getSecurityMetadataSourceManager() {
        return securityMetadataSourceManager;
    }

    public void setSecurityMetadataSourceManager( SecurityMetadataSourceManager securityMetadataSourceManager ) {
        this.securityMetadataSourceManager = securityMetadataSourceManager;
    }

    @Override
    public void setApplicationContext( ApplicationContext applicationContext )
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getOnlineUserList() {
        try {
            List<Object> userList = sessionRegistry.getAllPrincipals();
            this.users = new ArrayList<User>();
            for ( Object ob : userList ) {
                User user = new User();
                user = (User) ob;
                user.setMenus( new HashSet<Menu>() );
                user.setRoles( new HashSet<Role>() );
                List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions( user, false );
                if ( sessionInformationList!=null&&sessionInformationList.size()>0&&sessionInformationList.get( 0 ).isExpired() ) {
                    continue;
                }
                users.add( user );
                /*List lastLoginList = operateLogManager.getHibernateTemplate().find("from OperateLog ol where ol.userName='"+user.getUsername()+"' ol.operateObjec='登录成功' order by ol.operateTime desc");
                if(lastLoginList!=null){
                	OperateLog ol = (OperateLog)lastLoginList.get(0);
                	loginIp.add(ol.getUserMachine());
                	loginTime.add(ol.getOperateTime());
                }*/
            }
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            if ( users != null ) {
                pagedRequests.setList( users );
                pagedRequests.setTotalNumberOfRows( users.size() );
            }
            this.users = (List<User>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

    public String logoutUser() {
        HttpServletRequest request = getRequest();
        User sessionUser = getSessionUser();
        try {
            user = userManager.get( id );
            List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions( user, false );
            if ( sessionInformationList != null ) {
                for ( int j = 0; j < sessionInformationList.size(); j++ ) {
                    sessionInformationList.get( j ).expireNow();
                    //sessionRegistry.removeSessionInformation(sessionInformationList.get(j).getSessionId());  
                    //SecurityUtil.putLogoutedUser(user.getUsername());
                    //SecurityContext ctx = SecurityContextHolder.getContext();
                    /* String remark=userTemp.getName()+"被管理员"+SecurityHolder.getUsername()+"踢出";  
                     loginLogService.logoutLog(userTemp, sessionId, remark);     //记录注销日志和减少在线用户1个  
                     logger.info(userTemp.getId()+"  "+userTemp.getName()+"用户会话销毁，" + remark);  */
                    OperateLog operateLog = new OperateLog();
                    operateLog.setOperateObject( "'" + user.getUsername() + "'被管理员'" + sessionUser.getUsername() + "'踢出" );
                    operateLog.setOperateTime( Calendar.getInstance().getTime() );
                    operateLog.setUserName( sessionUser.getUsername() );
                    operateLog.setOperator( sessionUser.getPerson().getName() );
                    operateLog.setUserMachine( request.getRemoteHost() );
                    operateLogManager.save( operateLog );
                }
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return ajaxForward( true, "成功将用户'" + user.getUsername() + "'退出系统！", false );

    }

    public String userIsTimeout() {
        boolean isTimeout = SecurityUtil.getLogoutedUser( getSessionUser().getUsername() );
        return ajaxForward( true, "" + isTimeout, false );
    }

    public String alreadyTichu() {
        HttpServletRequest req = this.getRequest();
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = userManager.getUserByUsername( un );
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateObject( "被管理员踢出系统" );
        operateLog.setOperateTime( Calendar.getInstance().getTime() );
        operateLog.setUserName( user.getUsername() );
        operateLog.setOperator( user.getPerson().getName() );
        operateLog.setUserMachine( req.getRemoteHost() );
        operateLogManager.save( operateLog );
        return null;
    }
    
    public String initPassword(){
    	try {
    		user = userManager.get(id);
    		user.setPassword(passwordEncoder.encodePassword( "111111", null ) );
    		user.setConfirmPassword(user.getPassword());
    		userManager.save(user);
			return ajaxForward(true,"初始化密码成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"初始化密码失败！",false);
		}
    }
    
    public String initSystemVariable(){
    	SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = userManager.getUserByUsername( un );
    	Calendar calendar = Calendar.getInstance();
    	
    	String bd = DateUtil.getDateTime("yyyy-MM-dd", calendar.getTime());
    	systemVariable = new SystemVariable();
    	systemVariable.setBusinessDate(bd);
    	String period = periodManager.getPeriodCodeByDate(calendar.getTime());
		if(period==null){
			period = "未定义";
		}
		systemVariable.setPeriod(period);
    	String muneId = user.getSubSystem();
		if(OtherUtil.measureNotNull(muneId)){
			systemVariable.setCurrentRootMenu(muneId);
		}
    	List<Menu> allRootMenus =menuManager.findAllowRootMenu(user);
    	Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();
    	menus = new ArrayList<Menu>();
    	for(Menu menu : allRootMenus){
    		if((dogMenus.get(menu.getMenuId())==null?false:true)){
    			menus.add(menu);
    		}
    	}
    	return SUCCESS;
    }
    
    public String setSystemVariable(){
    	try {
    		SecurityContext ctx = SecurityContextHolder.getContext();
    		UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
    		String un = uds.getUsername();
    		User user = userManager.getUserByUsername( un );
    		systemVariable.setLoginUser(user);
    		systemVariable.setLoginPerson(user.getPerson());
    		String subSysId = systemVariable.getCurrentRootMenu();
    		if(OtherUtil.measureNotNull(subSysId)&&!"-1".equals(subSysId)){
    			user.setSubSystem(subSysId);
    			userManager.save(user);
    			Menu rootMenu = menuManager.get(subSysId);
    			String action = rootMenu.getMenuAction();
    			systemVariable.setSubSysMainPage(action);
    		}
    		String businessDate = systemVariable.getBusinessDate();
    		
    		if(ContextUtil.versionType==1||ContextUtil.versionType==2){
    			Date busDate = DateUtil.convertStringToDate(businessDate);
    			if(ContextUtil.maxDate!=-9&&ContextUtil.maxDate<busDate.getTime()){
    				return ajaxForward(false,"期间错误，请重新选择！",false);
    			}
    		}
    		/*if(!"-1".equals(systemVariable.getOrgCode())){
    			Org org = orgManager.get(systemVariable.getOrgCode());
    			systemVariable.setOrgName(org.getOrgname());
    		}
    		Copy copy = null;
    		if(!"-1".equals(systemVariable.getCopyCode())){
    			copy = copyManager.get(systemVariable.getCopyCode());
    			systemVariable.setCopyName(copy.getCopyname());
    			PeriodSubject periodSubject = periodSubjectManager.getPeriodSubByPlanAndDate(copy.getPeriodPlan().getPlanId(), DateUtil.convertStringToDate(systemVariable.getBusinessDate()));
    			if(periodSubject!=null){
    				systemVariable.setKjYear(periodSubject.getPeriodYear());
    				systemVariable.setPeriodSubjectId(periodSubject.getPeriodId());
    				systemVariable.setPeriodPlanId(periodSubject.getPlan().getPlanId());
    			}
    			PeriodMonth periodMonth = periodMonthManager.getPeriodMonth(copy.getPeriodPlan().getPlanId(),periodSubject.getPeriodYear() ,DateUtil.convertStringToDate(systemVariable.getBusinessDate()));
    			if(periodMonth!=null){
    				systemVariable.setPeriod(periodMonth.getMonth());
    			}
    		}*/
    		//periodManager.getPeriodCodeByDate(date);
    		/*KjYear kjyear = kjYearManager.getKjyearByDate(systemVariable.getOrgCode(),systemVariable.getBusinessDate());
    		
    		if(kjyear!=null){
    			systemVariable.setKjYear(kjyear.getKjYear());
    		}*/
    		String currSF = DynamicSessionFactoryHolder.getSessionFactoryName();
            Map<String,String> map = ((DynamicDatabaseManager)SpringContextHelper.getBean("selectedDataSource")).getSelectedDataSource();
    		systemVariable.setDataBaseName((String)map.get(currSF));
//    		systemVariable.setDataBaseName(userManager.getDateBaseName());
    		/*user.setSystemVariable(systemVariable);
    		getSession().setAttribute(""+user.getId(), user);
    		getSession().setAttribute("currentUser", user);
    		RequestContext requestContext = (RequestContext)getSession().getAttribute("currentRequestContext");
    		requestContext.setSystemVariable(systemVariable);*/
    		//处理期间 TODO 还需加入单位期间方案和账套期间方案
    		/*if(systemVariable.getOrgCode()==null||"".equals(systemVariable.getOrgCode())){
    			PeriodPlan periodPlan = periodPlanManager.getDefaultPeriodPlan();
    			systemVariable.setPeriodPlanCode(periodPlan.getPlanId());
    		}*/
    		String period = periodManager.getPeriodCodeByDate(DateUtil.convertStringToDate(businessDate));
    		if(period==null){
    			period = "未定义";
    		}
    		systemVariable.setPeriod(period);
    		user.setSystemVariable(systemVariable);
    		
    		UserContextUtil.loadUserContext(getSession(), systemVariable);
    		
    		getSession().setAttribute(""+user.getId(), user);
    		getSession().setAttribute("currentSystemVariable", systemVariable);
    		//RequestContext requestContext = new RequestContext();
    		//requestContext.setSystemVariable(systemVariable);
    		//RequestContext.setCurrentRequestContext(requestContext);
    		//getSession().setAttribute("currentRequestContext", requestContext);
    		return ajaxForward(true,"1",true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"系统错误！",false);
		}
    }
    
    public String chargeSystemVariable(){
    	try {
    		UserContext userContext = UserContextUtil.getUserContext();
    		if(userContext!=null){
	    		return ajaxForward("0");
    		}else{
    			return ajaxForward("0");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
    }
    
    String orgModel;
    String copyModel;
    public String getOrgModel() {
		return orgModel;
	}

	public void setOrgModel(String orgModel) {
		this.orgModel = orgModel;
	}

	public String getCopyModel() {
		return copyModel;
	}

	public void setCopyModel(String copyModel) {
		this.copyModel = copyModel;
	}

	public String findOrgModel(){
    	String businessYear = getRequest().getParameter("businessYear");
    	String currentRootMenu = getRequest().getParameter("currentRootMenu");
    	Map<String, String> syModel = ContextUtil.getSyModel(currentRootMenu);
     	if(syModel!=null){
    		String modelCode = syModel.get("code");
    		orgModel = ContextUtil.getHerpParamByKey("orgModel", modelCode);
    		copyModel = ContextUtil.getHerpParamByKey("copyModel", modelCode);
    	}
    	//userManager.getBySql("");
    	//String paramValue = ContextUtil.getHerpParamByKey(key, subSystemCode);
    	return SUCCESS;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao( RoleDao roleDao ) {
        this.roleDao = roleDao;
    }

    public List getMenus() {
        return menus;
    }

    public void setMenus( List menus ) {
        this.menus = menus;
    }

    public List getRoleList() {
        return roleList;
    }

    public void setRoleList( List roleList ) {
        this.roleList = roleList;
    }

    public SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }

    public void setSessionRegistry( SessionRegistry sessionRegistry ) {
        this.sessionRegistry = sessionRegistry;
    }

    public OperateLogManager getOperateLogManager() {
        return operateLogManager;
    }

    public void setOperateLogManager( OperateLogManager operateLogManager ) {
        this.operateLogManager = operateLogManager;
    }

    public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager( DepartmentManager departmentManager ) {
        this.departmentManager = departmentManager;
    }
    
    public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

    public List getDepartList() {
        return departList;
    }

    public void setDepartList( List departList ) {
        this.departList = departList;
    }

    public List getLoginTime() {
        return loginTime;
    }

    public void setLoginTime( List loginTime ) {
        this.loginTime = loginTime;
    }

    public List getLoginIp() {
        return loginIp;
    }

    public void setLoginIp( List loginIp ) {
        this.loginIp = loginIp;
    }
}
