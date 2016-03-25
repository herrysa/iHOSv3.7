package com.huge.ihos.system.context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huge.exceptions.ModelStatusException;
import com.huge.foundation.common.AppException;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.ihos.system.configuration.syvariable.model.VariableConstan;
import com.huge.ihos.system.configuration.syvariable.service.VariableManager;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilegeBean;
import com.huge.ihos.system.systemManager.dataprivilege.service.PrivilegeClassManager;
import com.huge.ihos.system.systemManager.dataprivilege.util.DataPrivilegeUtil;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.menu.service.MenuButtonManager;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.util.TestTimer;
import com.huge.util.javabean.MapUtils;
import com.huge.webapp.util.SpringContextHelper;

public class UserContextUtil{
	
	private static MenuManager menuManager;
	private static UserManager userManager;
	private static MenuButtonManager menuButtonManager;
	private static PrivilegeClassManager privilegeClassManager;
	private static VariableManager variableManager;
	private static PersonTypeManager personTypeManager; 
	private static PeriodManager periodManager; 
	public static UserManager getUserManager() {
		return userManager;
	}
	private static final ThreadLocal<UserContext> threadLocal = new ThreadLocal<UserContext>();
	public static UserContext getUserContext(){
		return (UserContext)threadLocal.get();
	}
	
	public static void setUserContext(UserContext userContext){
		threadLocal.set(userContext);
	}
	
	public static void init(){
		UserContextUtil.userManager = (UserManager)SpringContextHelper.getBean("userManager");
		UserContextUtil.menuManager = (MenuManager)SpringContextHelper.getBean("menuManager");
		UserContextUtil.menuButtonManager = (MenuButtonManager)SpringContextHelper.getBean("menuButtonManager");
		UserContextUtil.variableManager = (VariableManager)SpringContextHelper.getBean("variableManager");
		UserContextUtil.privilegeClassManager = (PrivilegeClassManager)SpringContextHelper.getBean("privilegeClassManager");
		UserContextUtil.personTypeManager = (PersonTypeManager)SpringContextHelper.getBean("personTypeManager");
		UserContextUtil.periodManager = (PeriodManager)SpringContextHelper.getBean("periodManager");
	}
	
	public static UserContext loadUserContext(HttpSession session,User user){
		TestTimer tt = new TestTimer("----------开始加载用户变量-----------------");
		tt.begin();
		UserContext userContext = new UserContext();
		try {
			init();
			loadUserBaseContext(userContext,user);
			//加载user menu权限
			loadUserMenuContext(userContext);
			//加载user button权限
			loadUserButtonContext(userContext);
			//加载user 数据权限
			loadUserDataPriviContext(userContext);
			//加载user 系统变量
			loadUserSysVariContext(userContext);
			
			setUserContext(userContext);
			session.setAttribute("currentUserContext", userContext);
		} catch (Exception e) {
			System.out.println("用户变量加载错误！");
			e.printStackTrace();
		}
		tt.done();
		return userContext;
	}
	public static UserContext loadUserContext(HttpSession session,SystemVariable systemVariable){
		TestTimer tt = new TestTimer("----------开始加载环境变量-----------------");
		tt.begin();
		UserContext userContext = null;
		try {
			userContext = (UserContext)session.getAttribute("currentUserContext");
			if(userContext==null){
				init();
				userContext = new UserContext();
				//加载userContex基本信息
				loadUserBaseContext(userContext,systemVariable);
				//加载user menu权限
				loadUserMenuContext(userContext);
				//加载user button权限
				loadUserButtonContext(userContext);
				//加载user 数据权限
				loadUserDataPriviContext(userContext);
				//加载user 系统变量
				loadUserSysVariContext(userContext);
				
				setUserContext(userContext);
				session.setAttribute("currentUserContext", userContext);
			}else{
				loadUserSyContext(userContext,systemVariable);
				loadUserSysVariWithEnviron(userContext);
			}
		} catch (Exception e) {
			System.out.println("用户环境变量加载错误！");
			e.printStackTrace();
		}
		tt.done();
		return userContext;
	}
	
	private static void loadUserBaseContext(UserContext userContext,User user){
		try {
			User loginUser = user;
			if(loginUser != null) {
				userContext.setLoginUserId(""+loginUser.getId());
				userContext.setLoginUserName(loginUser.getUsername());
				//userContext.setUser(loginUser);
				//Set<Role> roleSet = user.getRoles();
				/*Iterator<Role> roleIterator = roleSet.iterator();
				List<Role> roles = new ArrayList<Role>();
				while (roleIterator.hasNext()) {
					Role role = roleIterator.next();
					roleSet.c
				}*/
				//userContext.setRoles(roleSet);
			}
			Person loginPerson = user.getPerson();
			String personTypeName = "";
			Department department = null;
			Branch branch = null;
			Org org = null;
			if(loginPerson != null) {
				userContext.setLoginPersonId(loginPerson.getPersonId());
				userContext.setLoginPersonCode(loginPerson.getPersonCode());
				userContext.setLoginPersonName(loginPerson.getName());
				//userContext.setPerson(loginPerson);
				personTypeName = loginPerson.getStatus();
				department = loginPerson.getDepartment();
				org = department.getOrg();
			}
			PersonType personType = null; 
			userContext.setLoginPersonTypeName(personTypeName);
			if(personTypeManager != null) {
				personType = personTypeManager.getPersonTypeByName(personTypeName);
			}
			if(personType != null) {
				userContext.setLoginPersonTypeId(personType.getId());
				userContext.setLoginPersonTypeCode(personType.getCode());
			}
			if(department != null) {
				userContext.setLoginDeptId(department.getDepartmentId());
				userContext.setLoginDeptCode(department.getDeptCode());
				userContext.setLoginDeptName(department.getName());
				branch = department.getBranch();
				if(branch!=null){
					userContext.setBranchCode(branch.getBranchCode());
					userContext.setBranchName(branch.getBranchName());
				}
			}
			if(org != null){
				userContext.setLoginOrgId(org.getOrgCode());
				userContext.setLoginOrgName(org.getOrgname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadUserSyContext(UserContext userContext,SystemVariable systemVariable){
		//时间类型
		String businessDateStr = systemVariable.getBusinessDate();
		userContext.setBusinessDateStr(businessDateStr);
		
		String curTime = DateUtil.convertDateToString("HH:mm:ss", new Date());
    	String businessTime = businessDateStr+" "+curTime;
    	try {
    		userContext.setBusinessDate(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", businessTime));
		} catch (ParseException pe) {
			//log.error("getBusinessTime error:", pe);
		}
		//Date businessDate = DateUtil.convertStringToDate(businessDateStr);
		//userContext.setBusinessDate(businessDate);
		
		
		String periodMonth = systemVariable.getPeriod();
		userContext.setPeriodMonth(periodMonth);
		userContext.setPeriodPlanCode(systemVariable.getPeriodPlanCode());
		
		if(periodMonth.length()==6) {
			Period period = periodManager.getPeriodByCode(periodMonth);
			//PeriodMonth period = periodMonthManager.getPeriodByCode(userContext.getPeriodPlanCode(),periodMonth);
			userContext.setPeriodBeginDate(period.getStartDate());
			userContext.setPeriodEndDate(period.getEndDate());
			String periodYear = "";
			if(periodMonth != null && !"".equals(periodMonth)) {
				periodYear = periodMonth.substring(0, 4);
			}
			userContext.setPeriodYear(periodYear);
		}
		
		//PeriodYear periodYear = period.getPeriodYear();
		//userContext.setPeriodYear(periodYear.getPeriodYearCode());
		//userContext.setYearBeginDate(periodYear.getBeginDate());
		//userContext.setYearEndDate(periodYear.getEndDate());
		
		Calendar  calendar = Calendar.getInstance();
		userContext.setSysDate(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		userContext.setMonthBeginDate(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		userContext.setMonthEndDate(calendar.getTime());
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		userContext.setYearBeginDate(calendar.getTime());
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		userContext.setYearEndDate(calendar.getTime());
		
		
		//其他
		userContext.setDataBaseName(systemVariable.getDataBaseName());
		userContext.setCurrentRootMenu(systemVariable.getCurrentRootMenu());
		Map<String, String> syModel = ContextUtil.getSyModel(userContext.getCurrentRootMenu());
     	if(syModel!=null){
    		String modelCode = syModel.get("code");
    		userContext.setCurrentRootMenuCode(modelCode);
    		String orgModel = ContextUtil.getHerpParamByKey("orgModel_"+userContext.getPeriodYear(), modelCode);
    		String copyModel = ContextUtil.getHerpParamByKey("copyModel_"+userContext.getPeriodYear(), modelCode);
    		userContext.setCurrentOrgModel(orgModel);
    		userContext.setCurrentCopyModel(copyModel);
    	}
		
		userContext.setSubSysMainPage(systemVariable.getSubSysMainPage());
		//组织结构
		userContext.setOrgCode(systemVariable.getOrgCode());
		userContext.setOrgName(systemVariable.getOrgName());
		userContext.setCopyCode(systemVariable.getCopyCode());
		userContext.setCopyName(systemVariable.getCopyName());
		
		
		userContext.setSysVariableInited(true);
	}
	
	private static void loadUserBaseContext(UserContext userContext,SystemVariable systemVariable){
		try {
			//时间类型
			String businessDateStr = systemVariable.getBusinessDate();
			userContext.setBusinessDateStr(businessDateStr);
			
			String curTime = DateUtil.convertDateToString("HH:mm:ss", new Date());
	    	String businessTime = businessDateStr+" "+curTime;
	    	try {
	    		userContext.setBusinessDate(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", businessTime));
			} catch (ParseException pe) {
				//log.error("getBusinessTime error:", pe);
			}
			//Date businessDate = DateUtil.convertStringToDate(businessDateStr);
			//userContext.setBusinessDate(businessDate);
			
			
			String periodMonth = systemVariable.getPeriod();
			userContext.setPeriodMonth(periodMonth);
			
			String periodYear = "";
			if(periodMonth != null && !"".equals(periodMonth)) {
				periodYear = periodMonth.substring(0, 4);
			}
			userContext.setPeriodYear(periodYear);
			
			userContext.setPeriodPlanCode(systemVariable.getPeriodPlanCode());
			userContext.setPeriodBeginDate(null);
			//其他
			userContext.setDataBaseName(systemVariable.getDataBaseName());
			userContext.setCurrentRootMenu(systemVariable.getCurrentRootMenu());
			userContext.setCurrentRootMenuCode("");
			userContext.setSubSysMainPage(systemVariable.getSubSysMainPage());
			//组织结构
			userContext.setOrgCode(systemVariable.getOrgCode());
			userContext.setOrgName(systemVariable.getOrgName());
			userContext.setCopyCode(systemVariable.getCopyCode());
			userContext.setCopyName(systemVariable.getCopyName());
			
			User loginUser = systemVariable.getLoginUser();
			if(loginUser != null) {
				userContext.setLoginUserId(""+loginUser.getId());
				userContext.setLoginUserName(loginUser.getUsername());
				//userContext.setUser(loginUser);
			}
			Person loginPerson = systemVariable.getLoginPerson();
			String personTypeName = "";
			Department department = null;
			if(loginPerson != null) {
				userContext.setLoginPersonId(loginPerson.getPersonId());
				userContext.setLoginPersonCode(loginPerson.getPersonCode());
				userContext.setLoginPersonName(loginPerson.getName());
				//userContext.setPerson(loginPerson);
				personTypeName = loginPerson.getStatus();
				department = loginPerson.getDepartment();
			}
			PersonType personType = null; 
			ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			PersonTypeManager personTypeManager = (PersonTypeManager) context.getBean("personTypeManager");
			if(personTypeManager != null) {
				personType = personTypeManager.getPersonTypeByName(personTypeName);
			}
			if(personType != null) {
				userContext.setLoginPersonTypeId(personType.getId());
				userContext.setLoginPersonTypeCode(personType.getCode());
				userContext.setLoginPersonTypeName(personTypeName);
			}
			if(department != null) {
				userContext.setLoginDeptId(department.getDepartmentId());
				userContext.setLoginDeptCode(department.getDeptCode());
				userContext.setLoginDeptName(department.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadUserMenuContext(UserContext userContext){
		//Set<Map<String, Object>> sysRoot = ContextUtil.getAllowSysRoot("0");
		//Set<Map<String, Object>> busRoot = ContextUtil.getAllowBusRoot("0");
		Map<String,Boolean> dogMenus = ContextUtil.getDogMenus();
		
		/*String[] subsystemIds = ContextUtil.getSubsystemIds();
		Map<String, Boolean> dogMenuMap = new HashMap<String, Boolean>();
        for ( String subsystemId : subsystemIds ) {
        	dogMenuMap.put(subsystemId, true);
        }*/
        
		boolean flag = false;
		//List<String> sysMenuList = new ArrayList<String>();
		Map<String, TreeSet<Menu>> subSystemMenuMap = new HashMap<String, TreeSet<Menu>>();
		//Map<String, Boolean> rootMenuMap = new HashMap<String, Boolean>();
		//Map<String, Boolean> leve2rootMenuMap = new HashMap<String, Boolean>();
		TreeSet<Menu> sysRootMenu = new TreeSet<Menu>();
		TreeSet<Menu> busRootMenu = new TreeSet<Menu>();
		TreeSet<Menu> rightMenus = new TreeSet<Menu>();
        User user = userManager.get(Long.parseLong(userContext.getLoginUserId()));
		
        /*if("未定义".equals(userContext.getPeriodMonth())){
        	rootMenuMap.put("50", true);
        	rootMenuMap.put("60", true);
        	//sysMenuList.add("50");
        	//sysMenuList.add("60");
        }else{
        	List<Menu> sysMenus = menuManager.getBusinessSys(0);
            //String sysMenuStr = "";
            for(Menu rootMenu : sysMenus){
            	//sysMenuStr += rootMenu.getMenuId()+",";
            	rootMenuMap.put(rootMenu.getMenuId(), true);
            	//sysMenuList.add(rootMenu.getMenuId());
            }
           // sysMenuStr = OtherUtil.subStrEnd(sysMenuStr, ",");
            
            for(Menu rootMenu : busMenus){
            	//sysMenuStr += rootMenu.getMenuId()+",";
            	rootMenuMap.put(rootMenu.getMenuId(), true);
            	//sysMenuList.add(rootMenu.getMenuId());
            }
            String currentSys = userContext.getCurrentRootMenu();
            if(OtherUtil.measureNotNull(currentSys)&&!"-1".equals(currentSys)){
            	//sysMenuStr += ","+currentSys;
				sysMenuMap.put(currentSys, true);
				sysMenuList.add(currentSys);
            }
        }*/
        
        /*List<Menu> sysMenus = menuManager.getBusinessSys(0);
        for(Menu rootMenu : sysMenus){
        	String rootMenuId = rootMenu.getMenuId();
        	Boolean allow = dogMenuMap.get(rootMenuId);
        	if(allow==null||allow==false){
        		List<Menu> level2Mune = menuManager.getAllByParent(rootMenuId);
        		boolean f = false;
        		for(Menu l2m : level2Mune){
        			Boolean l2Allow = dogMenuMap.get(l2m.getMenuId());
        			if(l2Allow!=null&&l2Allow==true){
        				leve2rootMenuMap.put(l2m.getMenuId(), true);
        				sysRootMenu.add(rootMenu);
        				//f = true;
        				break;
        			}
        		}
        		if(f==false){
        			rootMenuMap.put(rootMenuId, false);
        			sysRootMenu.add(rootMenu);
        		}
        	}else{
        		rootMenuMap.put(rootMenuId, true);
        		sysRootMenu.add(rootMenu);
        	}
        }*/
       /* List<Menu> busMenus = menuManager.getBusinessSys(1);
        for(Menu rootMenu : busMenus){
        	String rootMenuId = rootMenu.getMenuId();
        	Boolean allow = dogMenuMap.get(rootMenuId);
        	if(allow==null||allow==false){
        		List<Menu> level2Mune = menuManager.getAllByParent(rootMenuId);
        		boolean f = false;
        		for(Menu l2m : level2Mune){
        			Boolean l2Allow = dogMenuMap.get(l2m.getMenuId());
        			if(l2Allow!=null&&l2Allow==true){
        				leve2rootMenuMap.put(l2m.getMenuId(), true);
        				busRootMenu.add(rootMenu);
        				break;
        				//f = true;
        			}
        		}
        		if(f==false){
        			rootMenuMap.put(rootMenuId, false);
        			busRootMenu.add(rootMenu);
        		}
        	}else{
        		rootMenuMap.put(rootMenuId, true);
        		busRootMenu.add(rootMenu);
        	}
        }*/
        
        /*for(String rootMenu : sysMenuList){
        	Boolean allow = dogMenuMap.get(rootMenu);
        	if(allow==null||allow==false){
        		List<Menu> level2Mune = menuManager.getAllByParent(rootMenu);
        		boolean f = false;
        		for(Menu l2m : level2Mune){
        			Boolean l2Allow = dogMenuMap.get(l2m.getMenuId());
        			if(allow==true){
        				leve2rootMenuMap.put(l2m.getMenuId(), true);
        				f = true;
        			}
        		}
        		if(f==false){
        			rootMenuMap.put(rootMenu, false);
        		}
        	}
        }*/
        
        Set<Role> roles = user.getRoles();
        Iterator<Role> roleIt = roles.iterator();
        int i = 0;
        while ( roleIt.hasNext() ) {
            Role role = roleIt.next();
            if ( role.getName().equals( "ROLE_ADMIN" ) ) {
                flag = true;
                break;
            }
            Set<Menu> menus = role.getMenus();
            for(Menu menu : menus){
            	String subsystemId = menu.getSubSystem().getMenuId();
            	if(!menu.isDisabled()&&dogMenus.get(menu.getMenuId())!=null&&dogMenus.get(menu.getMenuId())){
            		rightMenus.add(menu);
            	}
        	}
        }
        
        if ( !flag ) {
            Set<Menu> menus = user.getMenus();
            for ( Menu menuItem : menus ) {
            	if(!menuItem.isDisabled()&&dogMenus.get(menuItem.getMenuId())!=null&&dogMenus.get(menuItem.getMenuId())){
            		rightMenus.add(menuItem);
            	}
            }
            //TreeSet<Menu> rightMenusCopy = new TreeSet<Menu>();
            for(Menu menu : rightMenus){
            	//rightMenusCopy.add(menu);
            	addMenuToMap(subSystemMenuMap,menu);
            	Menu rootMenu = menu.getSubSystem();
            	if(rootMenu.getSystemType()!=null&&rootMenu.getSystemType()==1){
            		busRootMenu.add(rootMenu);
            	}else{
            		sysRootMenu.add(rootMenu);
            	}
            	Menu menuTemp = menu.getParent();
            	while(menuTemp!=null){
            		//rightMenusCopy.add(menuTemp);
            		addMenuToMap(subSystemMenuMap,menuTemp);
            		menuTemp = menuTemp.getParent();
            	}
            }
        }else{
        	List<Menu> enableMenus = menuManager.getAllEnabled();
        	//TreeSet<Menu> rightMenusCopy = new TreeSet<Menu>();
        	for(Menu menu : enableMenus){
        		if(!menu.isDisabled()&&dogMenus.get(menu.getMenuId())!=null&&dogMenus.get(menu.getMenuId())){
        			addMenuToMap(subSystemMenuMap,menu);
        			Menu rootMenu = menu.getSubSystem();
                	if(rootMenu.getSystemType()!=null&&rootMenu.getSystemType()==1){
                		busRootMenu.add(rootMenu);
                	}else{
                		sysRootMenu.add(rootMenu);
                	}
            	}
        		/*Boolean allowSubsys = rootMenuMap.get(menu.getSubSystem().getMenuId());
        		if(allowSubsys!=null&&allowSubsys){
        			//rightMenusCopy.add(menu);
        			addMenuToMap(subSystemMenuMap,menu);
        		}else{
        			Menu menuTemp = menu;
        			while (menuTemp.getParent()!=null) {
        				menuTemp = menuTemp.getParent();
        				allowSubsys = leve2rootMenuMap.get(menuTemp.getMenuId());
        				if(allowSubsys!=null&&allowSubsys){
        					//rightMenus.add(menu);
        					addMenuToMap(subSystemMenuMap,menu);
        					break;
        				}
					}
        		}*/
        	}
        }
        userContext.setUserSysRootMenus(sysRootMenu);
        userContext.setUserBusRootMenus(busRootMenu);
        userContext.setUserMenuPrivileges(subSystemMenuMap);
        
	}
	
	private static void addMenuToMap(Map<String, TreeSet<Menu>> subSystemMenuMap,Menu menu){
		String subsystemId = menu.getSubSystem().getMenuId();
		TreeSet<Menu> subSysMenus = subSystemMenuMap.get(subsystemId);
		if(subSysMenus==null){
			subSysMenus = new TreeSet<Menu>();
			subSysMenus.add(menu);
			subSystemMenuMap.put(subsystemId, subSysMenus);
		}else{
			subSysMenus.add(menu);
		}
	}
	
	private static void loadUserButtonContext(UserContext userContext){
		/*TreeSet<Menu> menuPrivis = userContext.getUserMenuPrivileges();
		for(Menu menu :menuPrivis){
			List<MenuButton> menuButtons = menuButtonManager.getMenuButtonsInRight(menu.getMenuId(), userContext.getLoginUserId());
			
		}*/
	}
	private static void loadUserDataPriviContext(UserContext userContext){
		Map<String, Set<UserDataPrivilegeBean>> userDataPrivilegeMap = userManager.getDataPrivates(userContext.getLoginUserId());
		userContext.setUserDataPrivilegeMap(userDataPrivilegeMap);
	}
	
	private static void loadUserSysVariContext(UserContext userContext){
		Map<String, Object> sysVars = new HashMap<String, Object>();
		
		sysVars.put("%"+VariableConstan.USER_ID+"%", userContext.getLoginUserId());
		sysVars.put("%"+VariableConstan.USER_NAME+"%", userContext.getLoginUserName());
		
		sysVars.put("%"+VariableConstan.HASP_NAME+"%", ContextUtil.orgName);
		
		sysVars.put("%"+VariableConstan.ORG_CODE+"%", userContext.getLoginOrgId());
		sysVars.put("%"+VariableConstan.ORG_NAME+"%", userContext.getLoginOrgName());
		sysVars.put("%"+VariableConstan.BRANCH_CODE+"%", userContext.getBranchCode());
		sysVars.put("%"+VariableConstan.BRANCH_NAME+"%", userContext.getBranchName());
		sysVars.put("%"+VariableConstan.DEPT_CODE+"%", userContext.getLoginDeptCode());
		sysVars.put("%"+VariableConstan.DEPT_ID+"%", userContext.getLoginDeptId());
		sysVars.put("%"+VariableConstan.DEPT_NAME+"%", userContext.getLoginDeptName());
		sysVars.put("%"+VariableConstan.PERSON_CODE+"%", userContext.getLoginPersonCode());
		sysVars.put("%"+VariableConstan.PERSON_ID+"%", userContext.getLoginPersonId());
		sysVars.put("%"+VariableConstan.PERSON_NAME+"%", userContext.getLoginPersonName());
		sysVars.put("%"+VariableConstan.PERSONTYPE_CODE+"%", userContext.getLoginPersonTypeCode());
		sysVars.put("%"+VariableConstan.PERSONTYPE_ID+"%", userContext.getLoginPersonTypeId());
		sysVars.put("%"+VariableConstan.PERSONTYPE_NAME+"%", userContext.getLoginPersonTypeName());
		
		Map<String, Set<UserDataPrivilegeBean>> userDataPrivilegeMap = userContext.getUserDataPrivilegeMap();
		List<Variable> variables = variableManager.getEnableVariables();
		String personId = userContext.getLoginPersonId();
		for(Variable variable : variables){
			String varName = variable.getVariableName();
			if(varName.startsWith("%DP_")){
				PrivilegeClass privilegeClass = variable.getPrivaleClass();
				if(privilegeClass==null){
					continue;
				}
				String classCode;
				String rightType = variable.getRightType();
				int rt = Integer.parseInt(rightType);
				String dataFormat = variable.getDataFormat();
				String varDataStr = "";
				//String[] varDataArr ;
				if(privilegeClass!=null){
					classCode = privilegeClass.getClassCode();
					Set<UserDataPrivilegeBean> userDataPrivilegeBeanSet = userDataPrivilegeMap.get(classCode);
					if(userDataPrivilegeBeanSet==null){
						userDataPrivilegeBeanSet = new HashSet<UserDataPrivilegeBean>();
					}
					if(userDataPrivilegeBeanSet.size()==0){
						if("XT".equals(personId)){
							//varDataStr = "all";
							//sysVars.put(varName, varDataStr);
							UserDataPrivilegeBean userDataPrivilegeBean = new UserDataPrivilegeBean();
							userDataPrivilegeBean.setClassCode(classCode);
							userDataPrivilegeBean.setItem("all");
							userDataPrivilegeBean.setItemName("(全部"+privilegeClass.getClassName()+")");
							userDataPrivilegeBean.setRightType("2");
							userDataPrivilegeBeanSet.add(userDataPrivilegeBean);
						}
						//continue;
					}
					//varDataArr = new String[userDataPrivilegeBeanSet.size()];
					Iterator<UserDataPrivilegeBean> udIt = userDataPrivilegeBeanSet.iterator();
					int i = 0;
					while (udIt.hasNext()) {
						UserDataPrivilegeBean userDataPrivilegeBean = udIt.next();
						String dataRightType = userDataPrivilegeBean.getRightType();
						int dataRt = Integer.parseInt(dataRightType);
						if(dataRt>=rt){
							String item = userDataPrivilegeBean.getItem();
							if(item.startsWith("all")||item.startsWith("ALL")){
								varDataStr = DataPrivilegeUtil.formatDataPrivilege(privilegeClass, item , userContext.getLoginUserId());
								break;
							}else{
								if("1".equals(dataFormat)){
									varDataStr += item+",";
								}else if("2".equals(dataFormat)){
									varDataStr += "'"+item+"',";
								}
							}
						}
					}
					if("1".equals(dataFormat)){
						varDataStr = OtherUtil.subStrEnd(varDataStr, ",");
					}else if("2".equals(dataFormat)){
						if(varDataStr.equals("")){
							varDataStr = "('')";
						}else{
							varDataStr = OtherUtil.subStrEnd(varDataStr, ",");
							varDataStr = "("+varDataStr+")";
						}
					}
					sysVars.put(varName, varDataStr);
				}else{
					continue;
				}
				
			}else if(varName.startsWith("%DPN_")){
				PrivilegeClass privilegeClass = variable.getPrivaleClass();
				String classCode;
				String rightType = variable.getRightType();
				String dataFormat = variable.getDataFormat();
				String varDataStr = "";
				String[] varDataArr ;
				if(privilegeClass!=null){
					classCode = privilegeClass.getClassCode();
					Set<UserDataPrivilegeBean> userDataPrivilegeBeanSet = userDataPrivilegeMap.get(classCode);
					if(userDataPrivilegeBeanSet==null||userDataPrivilegeBeanSet.size()==0){
						continue;
					}
					varDataArr = new String[userDataPrivilegeBeanSet.size()];
					Iterator<UserDataPrivilegeBean> udIt = userDataPrivilegeBeanSet.iterator();
					int i = 0;
					while (udIt.hasNext()) {
						UserDataPrivilegeBean userDataPrivilegeBean = udIt.next();
						if(rightType.equals(userDataPrivilegeBean.getRightType())){
							if("1".equals(dataFormat)){
								varDataStr += userDataPrivilegeBean.getItemName()+",";
							}else if("2".equals(dataFormat)){
								varDataStr += "'"+userDataPrivilegeBean.getItemName()+"'";
							}else if("3".equals(dataFormat)){
								varDataArr[i] = userDataPrivilegeBean.getItemName();
							}
						}
					}
					if("1".equals(dataFormat)){
						varDataStr = OtherUtil.subStrEnd(varDataStr, ",");
					}else if("2".equals(dataFormat)){
						if(varDataStr.equals("")){
							varDataStr = "('')";
						}else{
							varDataStr = "("+varDataStr+")";
						}
					}
					sysVars.put(varName, varDataStr);
				}else{
					continue;
				}
			}
			if("%JJFGKS%".equals(varName)){
				String dpName = variable.getRemark();
				Object varDataStr = sysVars.get(dpName);
				sysVars.put("%"+VariableConstan.JJFGKS+"%", varDataStr);
			}
		}
		userContext.setSysVars(sysVars);
	}
	
	private static void loadUserSysVariWithEnviron(UserContext userContext){
		Map<String, Object> sysVars = userContext.getSysVars();
		//时间类型
		sysVars.put("%"+VariableConstan.PERIOD_DATE+"%", userContext.getBusinessDateStr());
		sysVars.put("%"+VariableConstan.PERIOD_YEAR+"%", userContext.getPeriodYear());
		sysVars.put("%"+VariableConstan.PERIOD_MONTH+"%", userContext.getPeriodMonth());
		sysVars.put("%"+VariableConstan.PERIOD_QUARTER+"%", userContext.getPeriodQuarter());
		sysVars.put("%"+VariableConstan.PERIOD_BEGIN+"%", userContext.getPeriodBeginDateStr());
		sysVars.put("%"+VariableConstan.PERIOD_END+"%", userContext.getPeriodEndDateStr());
		
		sysVars.put("%"+VariableConstan.MONTH_BEGIN+"%", userContext.getMonthBeginDateStr());
		sysVars.put("%"+VariableConstan.MONTH_END+"%", userContext.getMonthEndDateStr());
		sysVars.put("%"+VariableConstan.SYSTEM_DATE+"%", userContext.getSysDateStr());
		sysVars.put("%"+VariableConstan.SYSTEM_MONTH+"%", userContext.getSysMonth());
		sysVars.put("%"+VariableConstan.SYSTEM_YEAR+"%", userContext.getSysYear());
		
		sysVars.put("%"+VariableConstan.C_ORG_CODE+"%", userContext.getOrgCode());
		sysVars.put("%"+VariableConstan.C_ORG_NAME+"%", userContext.getOrgName());
		sysVars.put("%"+VariableConstan.C_COPY_CODE+"%", userContext.getCopyCode());
		sysVars.put("%"+VariableConstan.C_COPY_NAME+"%", userContext.getCopyName());
		sysVars.put("%"+VariableConstan.C_PERIODPLAN_CODE+"%", userContext.getPeriodPlanCode());
		sysVars.put("%"+VariableConstan.C_MODEL_ID+"%", userContext.getCurrentRootMenu());
		sysVars.put("%"+VariableConstan.C_MODEL_CODE+"%", userContext.getCurrentRootMenuCode());
		sysVars.put("%"+VariableConstan.C_ORGMODEL+"%", userContext.getCurrentOrgModel());
		sysVars.put("%"+VariableConstan.C_COPYMODEL+"%", userContext.getCurrentCopyModel());
		
		sysVars.put("%"+VariableConstan.HSQJ+"%", userContext.getPeriodMonth());
		sysVars.put("%"+VariableConstan.HSQJ_YEAR+"%", userContext.getPeriodYear());
		sysVars.put("%"+VariableConstan.HSQJ_MONTH+"%", ""+userContext.getMonth());
		sysVars.put("%"+VariableConstan.HSQJ_QC+"%", userContext.getPeriodYear()+"01");
		sysVars.put("%"+VariableConstan.HSQJ_BEGIN+"%", userContext.getPeriodBeginDateStr());
		sysVars.put("%"+VariableConstan.HSQJ_END+"%", userContext.getPeriodEndDateStr());
		
		sysVars.put("%"+VariableConstan.CJQJ+"%", userContext.getPeriodMonth());
		sysVars.put("%"+VariableConstan.CJQJ_BEGIN+"%", userContext.getPeriodBeginDateStr());
		sysVars.put("%"+VariableConstan.CJQJ_END+"%", userContext.getPeriodEndDateStr());
		
		/*String cbPeriod = "";
		String cbPeriodYear = "";
		String cbMonth = "";
		Period cbPeriodMonth = null;
		try {
			cbPeriod = getWorkPeriodByContext("CB","月");
			cbPeriodYear = getWorkPeriodByContext("CB","年");
			cbMonth = cbPeriod.replace(cbPeriodYear, "");
			sysVars.put("%"+VariableConstan.HSQJ+"%", cbPeriod);
			sysVars.put("%"+VariableConstan.HSQJ_YEAR+"%", cbPeriodYear);
			sysVars.put("%"+VariableConstan.HSQJ_MONTH+"%", cbMonth);
			sysVars.put("%"+VariableConstan.HSQJ_QC+"%", cbPeriodYear+"01");
			
			//PeriodMonth cbPeriodMonth = periodMonthManager.getPeriodByCode(userContext.getPeriodPlanCode(),cbPeriod);
			cbPeriodMonth = periodManager.getPeriodByCode(cbPeriod);
			if(cbPeriodMonth!=null){
				Date cbBeginDate = cbPeriodMonth.getStartDate();
				Date cbEndDate = cbPeriodMonth.getEndDate();
				sysVars.put("%"+VariableConstan.HSQJ_BEGIN+"%", DateUtil.convertDateToString(cbBeginDate));
				sysVars.put("%"+VariableConstan.HSQJ_END+"%", DateUtil.convertDateToString(cbEndDate));
			}
		} catch (ModelStatusException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		String cjPeriod = "";
		try {
			cjPeriod = getWorkPeriodByContext("CJ","月");
			sysVars.put("%"+VariableConstan.CJQJ+"%", cjPeriod);
			//cbPeriodMonth = periodMonthManager.getPeriodByCode(userContext.getPeriodPlanCode(),cjPeriod);
			cbPeriodMonth = periodManager.getPeriodByCode(cjPeriod);
			if(cbPeriodMonth!=null){
				Date cjBeginDate = cbPeriodMonth.getStartDate();
				Date cjEndDate = cbPeriodMonth.getEndDate();
				sysVars.put("%"+VariableConstan.CJQJ_BEGIN+"%", DateUtil.convertDateToString(cjBeginDate));
				sysVars.put("%"+VariableConstan.CJQJ_END+"%", DateUtil.convertDateToString(cjEndDate));
			}
		} catch (ModelStatusException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}*/
		//String cjPeriodYear = getWorkPeriodByContext("cj","年");
		inserUserSys(userContext);
	}
	
	public static void inserUserSys(UserContext userContext){
		Map<String, Object> sysVars = userContext.getSysVars();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
		String userId = getLoginUserId();
		jt.execute("DELETE FROM  SY_VARIABLE_VALUE WHERE userId='"+userId+"'");
		String snapcode = DateUtil.convertDateToString(DateUtil.TIMESTAMP_PATTERN,Calendar.getInstance().getTime());
		String insertSql = "INSERT INTO SY_VARIABLE_VALUE ";
		String nameStr = "(id,userId,snapCode,";
		String valueStr = "('"+userId+"_"+snapcode+"','"+userId+"','"+snapcode+"',";
		Set<String> varKeySet = sysVars.keySet();
		for(String varKey:varKeySet){
			Object sysVar = sysVars.get(varKey);
			String sysVarStr = "";
			varKey = varKey.substring(1,varKey.length()-1);
			Boolean colExist = menuManager.getIsDBColumnExist("SY_VARIABLE_VALUE",varKey);
			if(colExist==null||colExist==false){
				continue;
			}
			if(sysVar!=null){
				sysVarStr = sysVar.toString();
				if(valueStr.contains("'")){
					sysVarStr = sysVarStr.replaceAll("'", "''");
				}
				valueStr += "'"+sysVarStr+"',";
				nameStr += varKey+",";
			}
		}
		if(!"(".equals(nameStr)){
			nameStr = OtherUtil.subStrEnd(nameStr, ",");
			nameStr += ")";
		}
		if(!"(".equals(valueStr)){
			valueStr = OtherUtil.subStrEnd(valueStr, ",");
			valueStr += ")";
		}
		insertSql = insertSql + nameStr +" VALUES "+valueStr;
		
		jt.execute(insertSql);
		System.out.println(insertSql);
	}
	
	/*private static void loadParams(UserContext userContext){
		List<GlobalParam> globalParams = globalParamManager.getAll();
		Map<String ,Map<String,String>>  globalParamMap = new HashMap<String, Map<String,String>>();
		for(GlobalParam globalParam:globalParams){
			String subSystemCode = globalParam.getSubSystemCode();
			if(subSystemCode==null||"".equals(subSystemCode)){
				subSystemCode = "all";
			}
			Map<String,String> subsSystemParam = globalParamMap.get(subSystemCode);
			if(subsSystemParam==null){
				subsSystemParam = new HashMap<String, String>();
				globalParamMap.put(subSystemCode, subsSystemParam);
			}
			subsSystemParam.put(globalParam.getParamKey(), globalParam.getParamValue());
		}
		userContext.setGlobalParamMap(globalParamMap);
	}*/
	
	public static Object findSysVariable(String sysVarCode){
		UserContext userContext = UserContextUtil.getUserContext();
		Map<String, Object> sysVars = userContext.getSysVars();
		Object sysVariable = sysVars.get(sysVarCode);
		return sysVariable;
	}
	
	public static Set<UserDataPrivilegeBean> findUserDataPrivilege(String privilegeCode){
		UserContext userContext = UserContextUtil.getUserContext();
		Map<String, Set<UserDataPrivilegeBean>> userDataPrivilegeMap = userContext.getUserDataPrivilegeMap();
		Set<UserDataPrivilegeBean> userDataPrivilegeSet = userDataPrivilegeMap.get(privilegeCode);
		if(userDataPrivilegeSet==null){
			userDataPrivilegeSet = new HashSet<UserDataPrivilegeBean>();
			String personId = userContext.getLoginPersonId();
			if("XT".equals(personId)){
				UserDataPrivilegeBean userDataPrivilegeBean = new UserDataPrivilegeBean();
				userDataPrivilegeBean.setClassCode(privilegeCode);
				userDataPrivilegeBean.setRightType("2");
				userDataPrivilegeBean.setItem("all");
				userDataPrivilegeSet.add(userDataPrivilegeBean);
			}
		}
		return userDataPrivilegeSet;
	}
	
	public static String findUserDataPrivilegeStr(String privilegeCode,String rightType){
		Set<UserDataPrivilegeBean> userDataPrivilegeSet = findUserDataPrivilege(privilegeCode);
		String priviStr = "";
		if(userDataPrivilegeSet!=null){
			int rt = Integer.parseInt(rightType);
			Iterator<UserDataPrivilegeBean> upIt = userDataPrivilegeSet.iterator();
			while (upIt.hasNext()) {
				UserDataPrivilegeBean userDataPrivilegeBean = upIt.next();
				String dataRightType = userDataPrivilegeBean.getRightType();
				int dataRt = Integer.parseInt(dataRightType);
				if(dataRt>=rt){
					priviStr += userDataPrivilegeBean.getItem()+",";
				}
			}
			if(!"".equals(priviStr)){
				priviStr = OtherUtil.subStrEnd(priviStr, ",");
				PrivilegeClass privilegeClass = privilegeClassManager.get(privilegeCode);
				priviStr = DataPrivilegeUtil.formatDataPrivilege(privilegeClass, priviStr , null);
			}
		}
		return priviStr;
	}
	
	public static String findUserDataPrivilegeSql(String privilegeCode,String rightType){
		String priviStr = findUserDataPrivilegeStr(privilegeCode,rightType);
		String priviSql = "";
		if(priviStr.startsWith("SELECT")){
			return "("+priviStr+")";
		}else{
			String[] priviArr = priviStr.split(",");
			for (String privi : priviArr) {
				priviSql += "'"+privi+"',";
			}
			if(!"".equals(priviSql)){
				priviSql = OtherUtil.subStrEnd(priviSql, ",");
				priviSql = "("+priviSql+")";
			}else{
				priviSql = "('')";
			}
		}
		return priviSql;
	}
	public static String[] findUserDataPrivilegeArray(String privilegeCode,String rightType){
		String[] varDataArr = new String[0];
		String priviStr = findUserDataPrivilegeStr(privilegeCode,rightType);
		if(!priviStr.startsWith("SELECT")){
			varDataArr = priviStr.split(",");
		}else{
			varDataArr = null;
		}
		return varDataArr;
	}
	
	public static String findUserDataPrivilegeFilter(String privilegeCode,String rightType){
		Set<UserDataPrivilegeBean> userDataPrivilegeSet = findUserDataPrivilege(privilegeCode);
		String priviStr = "";
		if(userDataPrivilegeSet!=null){
			int rt = Integer.parseInt(rightType);
			Iterator<UserDataPrivilegeBean> upIt = userDataPrivilegeSet.iterator();
			while (upIt.hasNext()) {
				UserDataPrivilegeBean userDataPrivilegeBean = upIt.next();
				String dataRightType = userDataPrivilegeBean.getRightType();
				int dataRt = Integer.parseInt(dataRightType);
				if(dataRt>=rt){
					priviStr += userDataPrivilegeBean.getItem()+",";
				}
			}
			if(!"".equals(priviStr)){
				priviStr = OtherUtil.subStrEnd(priviStr, ",");
			}
		}
		return priviStr;
	}
	
	public static User getContextUser(){
		UserContext userContext = UserContextUtil.getUserContext();
		User user = userManager.get(Long.parseLong(userContext.getLoginUserId()));
		return user;
	}
	
	public static Set<Role> getContextRoles(){
		UserContext userContext = UserContextUtil.getUserContext();
		User user = userManager.get(Long.parseLong(userContext.getLoginUserId()));
		return user.getRoles();
	}
	
	/*public static Person getContextPerson(){
		UserContext userContext = UserContextUtil.getUserContext();
		return userContext.getPerson();
	}*/
	
	public static User getSessionUser() {
		//TODO 需要验证，不同登陆人取得的user
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetails uds = (UserDetails) ctx.getAuthentication().getPrincipal();
        String un = uds.getUsername();
        User user = userManager.getUserByUsername( un );
        return user;
    }
	
	public static String getLoginUserId(){
		UserContext userContext = UserContextUtil.getUserContext();
		return userContext.getLoginUserId();
	}
	
	public static String getLoginPersonId(){
		UserContext userContext = UserContextUtil.getUserContext();
		return userContext.getLoginPersonId();
	}
	
	//TODO 加入系统参数
	/**根据系统参数判断是登陆期间还是工作期间
	 * @return
	 */
	public static String getUsePeriod(){
		String period = getLoginPeriod();
		
		return period;
	}
	
	/**获取登陆期间
	 * @return
	 */
	public static String getLoginPeriod(){
		UserContext userContext = UserContextUtil.getUserContext();
		return userContext.getPeriodMonth();
	}
	
	/**获取该子系统年度是否启动
	 * @param moduleCode
	 * @return
	 * @throws ModelStatusException 
	 */
	public static boolean isModuleYearStarted(String moduleCode) throws ModelStatusException{
		boolean yearStarted = false;
		UserContext userContext = UserContextUtil.getUserContext();
		String workPeriodYear = getWorkPeriod(moduleCode, "年" ,userContext.getOrgCode(), userContext.getCopyCode());
		if(workPeriodYear!=null&&!"".equals(workPeriodYear)){
			yearStarted = true;
		}else{
			throw new ModelStatusException(moduleCode, "年", "", "");
		}
		return yearStarted;
	}
	
	//TODO 通过systemModelStatus获取启动状态
	public static boolean isModuleStarted(String moduleCode,String periodType){
		boolean started = false;
		String workPeriod = "";
		try {
			workPeriod = getWorkPeriodByContext(moduleCode , periodType);
		} catch (ModelStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(workPeriod!=null&&!"".equals(workPeriod)){
			started = true;
		}
		return started;
	}
	//TODO 通过systemModelStatus获取结账状态
	public static boolean isModuleClosed(String moduleCode,String periodType,String period){
		boolean closed = false;
		String status = getModuleStatusByContext(moduleCode, periodType, period);
		if(!"2".equals(status)){
			closed = true;
		}
		return closed;
	}
	
	/**获取子系统工作的期间
	 * @param moduleCode
	 * @param periodType
	 * @param orgCode
	 * @param copyCode
	 * @return
	 */
	public  static String getWorkPeriod(String moduleCode , String periodType,String orgCode , String copyCode){
		String orgStr = "";
		if(orgCode!=null&&!"".equals(orgCode)){
			orgStr += "_"+orgCode;
			if(copyCode!=null&&!"".equals(copyCode)){
				orgStr += "_"+copyCode;
			}
		}
		//Map<String, String> moduleStatusMap = ContextUtil.getSystemModelStatus();
		String workPeriod =  ContextUtil.getSystemModelStatus(moduleCode+"_"+periodType+orgStr);
		//String workPeriod = moduleStatusMap.get(moduleCode+"_"+periodType+orgStr);
		return workPeriod;
	}
	
	//TODO 获取子系统工作的期间，无论哪种期间类型，获取的是第一个。替换getCurrentPeriod
	public static String getWorkPeriodByContext(String moduleCode , String periodType) throws ModelStatusException{
		boolean yearStarted = isModuleYearStarted(moduleCode);
		String workPeriod = null;
		if(yearStarted){
			UserContext userContext = UserContextUtil.getUserContext(); 
			workPeriod = getWorkPeriod(moduleCode,periodType,userContext.getOrgCode(),userContext.getCopyCode());
			if(workPeriod!=null&&!"".equals(workPeriod)){
				return workPeriod;
			}else{
				throw new ModelStatusException(moduleCode, periodType, "", "");
			}
		}
		return workPeriod;
	}
	
	
	/**获取子系统结账状态
	 * @param moduleCode
	 * @param periodType
	 * @param period
	 * @param orgCode
	 * @param copyCode
	 * @return
	 */
	public static String getModulePeriodStatus(String moduleCode , String periodType , String period , String orgCode , String copyCode){
		String moduleState = null;
		//Map<String, String> moduleStatusMap = ContextUtil.getSystemModelStatus();
		//moduleState = moduleStatusMap.get(moduleCode+"_"+periodType+"_"+orgCode+"_"+copyCode+"_"+period);
		moduleState =  ContextUtil.getSystemModelStatus(moduleCode+"_"+periodType+"_"+orgCode+"_"+copyCode+"_"+period);
		return moduleState;
	}
	
	/**根据登陆的单位、账套变量获取子系统结账状态
	 * @param moduleCode
	 * @param periodType
	 * @param period
	 * @return
	 */
	public static String getModuleStatusByContext(String moduleCode , String periodType , String period){
		UserContext userContext = UserContextUtil.getUserContext();
		return getModulePeriodStatus(moduleCode,periodType,period,userContext.getOrgCode(),userContext.getCopyCode());
	}
	
	//TODO 获取当前menu的menubuttons 替换getMenuButtons
	public static List<MenuButton> userMenuButtons(String menuId){
		UserContext userContext = UserContextUtil.getUserContext();
		List<MenuButton> menuButtons = menuButtonManager.getMenuButtonsInRight(menuId, userContext.getLoginUserId());
    	return menuButtons;
    }
	
	public static List<MenuButton> userMenuButtonsDisabled(String menuId){
		List<MenuButton> menuButtons = userMenuButtons(menuId);
		if(menuButtons!=null){
			for(int i = 0;i < menuButtons.size();i++){
    			menuButtons.get(i).setEnable(false);
    		}
		}
    	return menuButtons;
    }
	//TODO 根据结账状态获取menubutton  替换findMenuButtonsYearMothClosed
	public static List<MenuButton> userMenuButtonsByPeriod(String moduleCode,String menuId,String period){
		boolean isModuleClosed = isModuleClosed(moduleCode,"月",period);
		if(isModuleClosed){
			return userMenuButtonsDisabled(menuId);
		}else{
			return userMenuButtons(menuId);
		}
	}
	
	//TODO 替换setMenuButtonsToJson
	public static String userMenuButtonsToJson(List<MenuButton> menuButtons) throws UnsupportedEncodingException{
		JSONArray menuButtonArr = JSONArray.fromObject(menuButtons);
    	String menuButtonArrJson = menuButtonArr.toString();
    	menuButtonArrJson = URLEncoder.encode(menuButtonArrJson, "utf-8");
    	return menuButtonArrJson;
	}
	
	public static String replaceSysVars(String str){
		Map map = new HashMap();
        UserContext userContext = UserContextUtil.getUserContext();
        map = MapUtils.mapObjToMapStr(userContext.getSysVars());
        Set<Entry<String, String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> entryIt = entrySet.iterator();
		while(entryIt.hasNext()){
			Entry<String, String> entry = entryIt.next();
			String key = entry.getKey();
			String value = entry.getValue();
			try {
				str = str.replace(key, value);
			} catch (Exception e) {
			}
		}
		return str;
	}
	
	public static void main(String[] args) {
		Calendar  calendar = Calendar.getInstance();
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		int m = calendar.get(Calendar.MONTH);
		System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, m+1);
		calendar.set(Calendar.DAY_OF_MONTH,0);
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
	}
}
