package com.huge.ihos.system.context;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import com.huge.ihos.hasp.service.DogService;
import com.huge.ihos.hasp.service.impl.DummyDogManager;
import com.huge.ihos.hasp.service.impl.HaspDogManager;
import com.huge.ihos.multidatasource.DynamicSessionFactoryHolder;
import com.huge.util.ReturnUtil;
import com.huge.webapp.util.SpringContextHelper;

public class ContextUtil extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5717320394451047589L;

	//herp开关
	public static String herpType = "S2";// S：单个单位版；M:多单位版；G：集团版
	public static int orgNum = 1;		// 允许的单位数量
	public static String orgName;
	public static int versionType = 0;// 1:演示版；0：发行版
	public static Long maxDate = 1472631053621L;
	
	private static DogService dogService = null;
    private static String[] subsystemIds ;
    private static int dogType = 1; //1虚拟狗；2实体狗
    
    private static Map<String,Map<String,Boolean>> dogMenusMap ;
    private static Map<String, Set<Map<String,Object>>> sysRootMenuMap;
    private static Map<String, Set<Map<String,Object>>> busRootMenuMap;
    
	private static Map<String,Map<String, Collection<ConfigAttribute>>> resourceMap;
	private static Map<String,Map<String ,Map<String,String>>>  globalParamMap;
    private static Map<String,Map<String ,List<Map<String, Object>>>>  dictionaryItemMap;
    private static Map<String,Map<String, String>> systemModelStatus; //子系统结账状态  key：hr_月_orgCode_copyCode_201501 value:1 另外还存储每个子系统的工作期间 key：hr_月_orgCode_copyCode value：201501
    
    private static Map<String,Map<String ,Map<String, String>>> herpParam; //子系统参数
    
    private static Map<String,Map<String ,Map<String, String>>> syModelMap; //子系统对照
    
	public void init(){
		try {
			//获取加密狗服务类
			dogService = getDogService();
			
			//加载子系统数据	 程序里没有对应页面操作，所以不同步，修改数据库后要重启服务
			loadSyModel();
			
			//加载herp参数 eg.orgModel 程序里没有对应页面操作，所以不同步，修改数据库后要重启服务
			loadHerpParam();
			
			//根据加密狗信息加载menu 增加菜单时要注意同步
			loadDogMenus();
			
			//加载系统资源，用户对应的menuaction 对用户和角色操作时注意同步
			loadResourceMap();
			
			//加载结账状态 结账和反结账时，要同步,并推送信息
			loadModelStatusContext();
			
			//加载系统参数，需同步
			loadGlobalParamMap();
			
			//加载公共数据，需同步
			loadDictionaryItemMap();
			
			//加载定时任务
			loadTimerTask();
			
		} catch (Exception e) {
			System.out.println("系统环境加载错误！");
			e.printStackTrace();
		}
	}
	
	public static DogService getDogService() throws Exception {

		if (dogService != null)
			return dogService;
		else {

			String dogType = System.getProperty("dogType");
			if(ContextUtil.dogType==1){
				dogType = "dummyDog";
			}
			try {
				if (dogType != null && dogType.equalsIgnoreCase("dummyDog")) {
					dogService = new DummyDogManager();
				} else {
					dogService = new HaspDogManager();
				}
				herpType = dogService.getOrgType();
				orgName = dogService.getHospitalName();
				orgName = orgName.trim();
				orgNum = dogService.getOrgNum();
				subsystemIds = dogService.getAllowedSubSystem();
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return dogService;
		}
	}
	
	private static void loadSyModel(){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
		String syModelSql = "select * from sy_model";
		List<Map<String, Object>> modelList = jt.queryForList(syModelSql);
		Map<String , Map<String, String>> modelMap = new HashMap<String , Map<String, String>>();
		for(Map<String, Object> model : modelList){
			String menuId = model.get("menuId").toString();
			String code = model.get("code").toString();
			String jzType = model.get("jzType").toString();
			String jzSn = model.get("jzSn").toString();
			Map<String,String> syModel = modelMap.get(menuId);
			if(syModel==null){
				syModel = new HashMap<String, String>();
			}
			syModel.put("code", code);
			syModel.put("jzType", jzType);
			syModel.put("jzSn", jzSn);
			modelMap.put(menuId, syModel);
		}
		if(syModelMap==null){
			syModelMap = new HashMap<String, Map<String,Map<String,String>>>();
		}
		syModelMap.put(dsKey, modelMap);
	}
	
	private static void loadHerpParam(){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
		String herpSql = "select * from sy_herp_param";
		List<Map<String, Object>> herpList = jt.queryForList(herpSql);
		Map<String , Map<String, String>> dsHerpParamMap = new HashMap<String , Map<String, String>>();
		for(Map<String, Object> param : herpList){
			Object subSystemCode = param.get("modelId");
			String subSystemCodeStr ="";
			String paramKey = param.get("paramKey").toString();
			String paramValue = param.get("paramValue").toString();
			Object periodYear = param.get("periodYear");
			if(subSystemCode!=null){
				subSystemCodeStr = subSystemCode.toString();
			}
			Map<String,String> subsSystemParam = dsHerpParamMap.get(subSystemCodeStr);
			if(subsSystemParam==null){
				subsSystemParam = new HashMap<String, String>();
				//dsGlobalParamMap.put(subSystemCodeStr, subsSystemParam);
			}
			if(periodYear==null){
				subsSystemParam.put(paramKey, paramValue);
			}else{
				subsSystemParam.put(paramKey+"_"+periodYear.toString(), paramValue);
			}
			dsHerpParamMap.put(subSystemCodeStr, subsSystemParam);
		}
		if(herpParam==null){
			herpParam = new HashMap<String, Map<String,Map<String,String>>>();
		}
		herpParam.put(dsKey, dsHerpParamMap);
	}
	
	private static void loadDogMenus(){
    	try {
    		String dsKey = getMultiDataSourceKey();
            System.out.println(dsKey+"_dogAllowMenus:"+subsystemIds.length);
            Map<String, Boolean> dogMenuMap = new HashMap<String, Boolean>();
            for ( String subsystemId : subsystemIds ) {
            	dogMenuMap.put(subsystemId, true);
            }
            JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
    		String menurootSql = "select * from t_menu where parentId IS NULL ORDER BY menuId ASC";
    		List<Map<String, Object>> rootList = jt.queryForList(menurootSql);
            //List<Menu> rootMenu = menuManager.getAllRootMenu();
            Map<String,Boolean> dogMenusTemp = new HashMap<String, Boolean>();
            Set<Map<String,Object>> sysRootMenu = new HashSet<Map<String,Object>>();
            Set<Map<String,Object>> busRootMenu = new HashSet<Map<String,Object>>();
            for(Map<String, Object> menu : rootList){
            	List<String> pathStack = new ArrayList<String>();
            	String menuId = menu.get("menuId").toString();
            	traversalTree(menuId ,pathStack,dogMenusTemp ,false ,dogMenuMap);
            	if(dogMenuMap.get(menuId)!=null&&dogMenuMap.get(menuId)){
            		Object systemTypeObj = menu.get("systemType").toString();
            		String systemType = "0";
            		if(systemTypeObj!=null){
            			systemType = systemTypeObj.toString();
            		}
            		if(systemType.equals("0")){
            			sysRootMenu.add(menu);
            		}else if(systemType.equals("1")){
            			busRootMenu.add(menu);
            		}
            		
            	}
            }
            if(dogMenusMap==null){
            	dogMenusMap = new HashMap<String,Map<String,Boolean>>();
            }
            if(sysRootMenuMap==null){
            	sysRootMenuMap = new HashMap<String, Set<Map<String,Object>>>();
            }
            if(busRootMenuMap==null){
            	busRootMenuMap = new HashMap<String, Set<Map<String,Object>>>();
            }
            dogMenusMap.put(dsKey, dogMenusTemp);
            sysRootMenuMap.put(dsKey, sysRootMenu);
            busRootMenuMap.put(dsKey, busRootMenu);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    }
    
    public static void addDogMenu(String menuId){
    	String dsKey = getMultiDataSourceKey();
    	Map<String,Boolean> dogMenus = dogMenusMap.get(dsKey);
    	boolean exit = dogMenus.get(menuId)==null?false:true;
    	if(!exit){
    		dogMenus.put(menuId, true);
    		dogMenusMap.put(dsKey, dogMenus);
    	}
    }
    
    private static boolean traversalTree(String parent,List<String> pathStack , Map<String,Boolean> menus , boolean isShow,Map<String, Boolean> dogMenuMap){
    	/*System.out.println("isShow:"+isShow);
    	System.out.println("menus:"+menus.keySet().toString());
    	System.out.println("pathStack:"+pathStack.toString());*/
    	//System.out.println("menus:"+menus.keySet().toString());
    	pathStack.add(parent);
    	boolean showCharge = isShow==true?true:dogMenuMap.get(parent)==null?false:true;
    	//List<Menu> menusTemp = menuManager.getByParent( parent.getMenuId() );
    	//List<Menu> menusTemp = menuManager.getAllByParent( parent );
    	JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
		String menuSql = "select * from t_menu where parentId='"+parent+"' ORDER BY menuId ASC";
		List<Map<String, Object>> menuList = jt.queryForList(menuSql);
    	if(menuList==null||menuList.size()==0){
    		if(showCharge){
    			for(String menupath : pathStack){
        			menus.put(menupath,true);
        		}
    		}
    		
    		//pathStack.remove(parent);
    	}
    	boolean childShowCharge = false;
    	for(Map<String, Object> menu : menuList){
    		/*try {
				Menu m = MapUtils.toObject(Menu.class, menu);
				System.out.println();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
    		String menuId = menu.get("menuId").toString();
    		childShowCharge = traversalTree(menuId ,pathStack,menus ,showCharge ,dogMenuMap);
    	}
    	if(!showCharge&&!childShowCharge){
    		pathStack.remove(parent);
    	}
    	return childShowCharge;
    }
    
    public static Map<String,Boolean> getDogMenus(){
    	String dsKey = getMultiDataSourceKey();
    	Map<String,Boolean> dogMenus = dogMenusMap.get(dsKey);
    	if(dogMenus==null){
    		loadDogMenus();
    		dogMenus = dogMenusMap.get(dsKey);
    	}
    	return dogMenus;
    }
	
	private static void loadResourceMap(){
		String dsKey = getMultiDataSourceKey();
    	Map<String,Boolean> dogMenus = dogMenusMap.get(dsKey);
    	if(dogMenus==null){
    		return ;
    	}
		Map<String, Collection<ConfigAttribute>> dsResourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getBean( "dataSource" ) );
		String userSql = "select * from app_user";
		List<Map<String, Object>> userList = jt.queryForList(userSql);
		//List<User> userList = userManager.getAll();
        for ( Map<String, Object> item : userList ) {
            //Hibernate.initialize(item.getMenus());
        	String userId = item.get("id").toString();
        	String userName = item.get("username").toString();
            ConfigAttribute ca = new SecurityConfig( userName );

            if ( dsResourceMap.get( "menuTreeAjax.action" ) == null ) {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add( ca );
                dsResourceMap.put( "menuTreeAjax.action", atts );
            }
            else {
                Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                attsOld = dsResourceMap.get( "menuTreeAjax.action" );
                attsOld.add( ca );
            }
            if ( dsResourceMap.get( "mainMenu.action" ) == null ) {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add( ca );
                dsResourceMap.put( "mainMenu.action", atts );
            }
            else {
                Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                attsOld = dsResourceMap.get( "mainMenu.action" );
                attsOld.add( ca );
            }
            //List<Privilege> tActionList = actionService.findByRoleID(item.getId());
            //List<PrivilegeUser> tActionList = privilegeUserManager.getPrivilegesByUser(item);
            //userDao.getUsersJionMenu(item.getId());
            //Set<Menu> menus = item.getMenus();
            String userMenusSql = "SELECT * FROM t_privilege_user p LEFT JOIN t_menu m ON p.menu_id=m.menuId WHERE p.user_id='"+userId+"'";
            List<Map<String, Object>> userMenuList = jt.queryForList(userMenusSql);
            //把资源放入到spring security的resouceMap中
            if ( userMenuList != null ) {
                for ( Map<String, Object> menu : userMenuList ) {
                    //logger.debug("获得角色：["+item.getName()+"]拥有的acton有："+privilegeUser.getMenu().getMenuAction());
                	String menuId = menu.get("menu_id").toString();
                	if(dogMenus.get(menuId)==null||!dogMenus.get(menuId)){
                		continue;
                	}
                    Object menuAction = menu.get("menuAction");
                    String menuActionStr = "";
                    if(menuAction!=null){
                    	menuActionStr = menuAction.toString();
                    }
                	if ( dsResourceMap.get( menuActionStr ) == null ) {
                        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                        atts.add( ca );
                        dsResourceMap.put( menuActionStr, atts );
                    }
                    else {
                        Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                        attsOld = dsResourceMap.get( menuActionStr );
                        attsOld.add( ca );
                    }
                    //this.resourceMap.put(menu.getMenuAction(), atts);
                }
            }
        }
        
        String roleSql = "SELECT * FROM role";
		List<Map<String, Object>> roleList = jt.queryForList(roleSql);
        //List<Role> roleList = roleManager.getAll();
        for ( Map<String, Object> item : roleList ) {
        	String roleId = item.get("id").toString();
        	String roleName = item.get("name").toString();
            ConfigAttribute ca = new SecurityConfig( roleName );
            //Set<Menu> menus = item.getMenus();
            String roleMenusSql = "SELECT * FROM t_privilege p LEFT JOIN t_menu m ON p.menu_id=m.menuId WHERE p.role_id='"+roleId+"'";
            List<Map<String, Object>> roleMenuList = jt.queryForList(roleMenusSql);
            //把资源放入到spring security的resouceMap中
            if ( roleMenuList != null ) {
                for ( Map<String, Object> menu : roleMenuList ) {
                    //logger.debug("获得角色：["+item.getName()+"]拥有的acton有："+privilegeUser.getMenu().getMenuAction());
                	String menuId = menu.get("menu_id").toString();
                	if(dogMenus.get(menuId)==null||!dogMenus.get(menuId)){
                		continue;
                	}
                	Object menuAction = menu.get("menuAction");
                    String menuActionStr = "";
                    if(menuAction!=null){
                    	menuActionStr = menuAction.toString();
                    }
                    if ( dsResourceMap.get( menuActionStr ) == null ) {
                        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                        atts.add( ca );
                        dsResourceMap.put( menuActionStr, atts );
                    }
                    else {
                        Collection<ConfigAttribute> attsOld = new ArrayList<ConfigAttribute>();
                        attsOld = dsResourceMap.get( menuActionStr );
                        attsOld.add( ca );
                    }
                    //this.resourceMap.put(menu.getMenuAction(), atts);
                }
            }
        }
        
        if(resourceMap==null){
        	resourceMap = new HashMap<String, Map<String,Collection<ConfigAttribute>>>();
        }
        resourceMap.put(dsKey, dsResourceMap);
	}
	//加载user 模块状态
	private static void loadModelStatusContext(){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getDataSource() );
		String statusSql = "SELECT * FROM sy_modelstatus";
		List<Map<String, Object>> statusList = jt.queryForList(statusSql);
		Map<String,String>  modelStatusMap = new HashMap<String,String>();
		for(Map<String, Object> status:statusList){
			Object orgCode = status.get("orgCode");
			Object copyCode = status.get("copyCode");
			Object modelId = status.get("modelId");
			Object periodType = status.get("periodType");
			Object checkperiod = status.get("checkperiod");
			Object statusStr = status.get("status");
			String orgStr = "";
			if(orgCode!=null){
				orgStr += "_"+orgCode.toString();
				if(copyCode!=null){
					orgStr += "_"+copyCode.toString();
				}
			}
			modelStatusMap.put(modelId+"_"+periodType+"_"+checkperiod+orgStr, statusStr.toString());
			if("1".equals(statusStr.toString())){
				String sysWorkPeriodKey = modelId+"_"+periodType+orgStr;
				if(modelStatusMap.get(sysWorkPeriodKey)==null){
					modelStatusMap.put(sysWorkPeriodKey, checkperiod.toString());
				}
			}
		}
		if(systemModelStatus==null){
			systemModelStatus = new HashMap<String, Map<String,String>>();
		}
		systemModelStatus.put(dsKey, modelStatusMap);
	}
	
	private static void loadGlobalParamMap(){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getDataSource() );
		String paramSql = "SELECT * FROM sy_globalParam";
		List<Map<String, Object>> globalParams = jt.queryForList(paramSql);
		//List<GlobalParam> globalParams = globalParamManager.getAll();
		Map<String ,Map<String,String>>  dsGlobalParamMap = new HashMap<String, Map<String,String>>();
		for(Map<String, Object> globalParam:globalParams){
			Object subSystemCode = globalParam.get("subSystemCode");
			String subSystemCodeStr ="";
			String paramKey = globalParam.get("paramKey").toString();
			String paramValue = globalParam.get("paramValue").toString();
			//String paramName = globalParam.get("paramName").toString();
			if(subSystemCode!=null){
				subSystemCodeStr = subSystemCode.toString();
			}
			if(subSystemCodeStr==null||"".equals(subSystemCodeStr)){
				subSystemCodeStr = "all";
			}
			Map<String,String> subsSystemParam = dsGlobalParamMap.get(subSystemCodeStr);
			if(subsSystemParam==null){
				subsSystemParam = new HashMap<String, String>();
				dsGlobalParamMap.put(subSystemCodeStr, subsSystemParam);
			}
			subsSystemParam.put(paramKey, paramValue);
		}
		if(globalParamMap==null){
			globalParamMap = new HashMap<String, Map<String,Map<String,String>>>();
		}
		globalParamMap.put(dsKey, dsGlobalParamMap);
	}

	private static void loadDictionaryItemMap(){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getDataSource() );
		String dicSql = "SELECT * FROM t_dictionary";
		List<Map<String, Object>> dictionaries = jt.queryForList(dicSql);
		//List<Dictionary> dictionaries = dictionaryManager.getAll();
		Map<String ,List<Map<String, Object>>>  dsDictionaryItemMap = new HashMap<String, List<Map<String, Object>>>();
		for(Map<String, Object> dictionary : dictionaries){
			String dicCode = dictionary.get("code").toString();
			String dicItemSql = "SELECT * FROM t_dictionary_items item LEFT JOIN t_dictionary dic ON item.dictionary_id=dic.dictionaryId WHERE dic.code='"+dicCode+"'";
			List<Map<String, Object>> dictionaryItems = jt.queryForList(dicItemSql);
			//List<DictionaryItem> dictionaryItems = dictionaryItemManager.getAllItemsByCode(dicCode);
			dsDictionaryItemMap.put(dicCode, dictionaryItems);
		}
		if(dictionaryItemMap==null){
			dictionaryItemMap = new HashMap<String,Map<String ,List<Map<String, Object>>>>();
		}
		dictionaryItemMap.put(dsKey, dsDictionaryItemMap);
	}
	
	public static Map<String, Collection<ConfigAttribute>> getResource(){
		String dsKey = getMultiDataSourceKey();
		Map<String, Collection<ConfigAttribute>> dsResourceMap = resourceMap.get(dsKey);
		if(dsResourceMap==null){
			loadResourceMap();
			dsResourceMap = resourceMap.get(dsKey);
		}
		return dsResourceMap;
	}
	
	
	public static String getGlobalParamByKey( String key ) {
        return getGlobalParamByKey(key,"all");
    }
	
	public static String getGlobalParamByKey( String key ,String subSystemCode) {
		String dsKey = getMultiDataSourceKey();
		String paramValue = "";
		Map<String ,Map<String,String>>  dsGlobalParamMap = globalParamMap.get(dsKey);
		if(dsGlobalParamMap==null){
			loadGlobalParamMap();
			dsGlobalParamMap = globalParamMap.get(dsKey);
		}
		Map<String,String> globalParamMap = dsGlobalParamMap.get(subSystemCode);
		if(globalParamMap==null){
			
		}else{
			paramValue = globalParamMap.get(key);
		}
        return paramValue;
    }
	
	public static List<Map<String, Object>> getAllItemsByCode(String dicCode){
		String dsKey = getMultiDataSourceKey();
		Map<String ,List<Map<String, Object>>>  dsDictionaryItemMap = dictionaryItemMap.get(dsKey);
		if(dsDictionaryItemMap==null){
			loadDictionaryItemMap();
			dsDictionaryItemMap = dictionaryItemMap.get(dsKey);
		}
		List<Map<String, Object>> dictionaryItems = dsDictionaryItemMap.get(dicCode);
		return dictionaryItems;
	}
	
	public static List<Map<String, Object>> getDicItemsByCode(String dicCode){
		String dsKey = getMultiDataSourceKey();
		Map<String ,List<Map<String, Object>>>  dsDictionaryItemMap = dictionaryItemMap.get(dsKey);
		if(dsDictionaryItemMap==null){
			loadDictionaryItemMap();
			dsDictionaryItemMap = dictionaryItemMap.get(dsKey);
		}
		List<Map<String, Object>> dictionaryItems = dsDictionaryItemMap.get(dicCode);
		return dictionaryItems;
	}
	
	public static List<String> getAllItemValuesByCode(String dicCode){
		List<Map<String, Object>> dictionaryItems = getAllItemsByCode(dicCode);
		List<String> dicValues = new ArrayList<String>();
		for(Map<String, Object> dictionaryItem : dictionaryItems){
			dicValues.add(dictionaryItem.get("value").toString());
		}
		return dicValues;
	}
	
	public static Map<String, String> getSystemModelStatus(){
		String dsKey = getMultiDataSourceKey();
		Map<String, String> dsSyModelStatus = systemModelStatus.get(dsKey);
		if(dsSyModelStatus==null){
			loadModelStatusContext();
			dsSyModelStatus = systemModelStatus.get(dsKey);
		}
		return dsSyModelStatus;
	}
	
	public static String getSystemModelStatus(String modelStatusKey){
		String modelStatus = "";
		Map<String, String> dsSyModelStatus = getSystemModelStatus();
		if(dsSyModelStatus!=null){
			modelStatus = dsSyModelStatus.get(modelStatusKey);
		}
		return modelStatus;
	}
	
	public static String getHerpParamByKey( String key ,String subSystemCode) {
		String dsKey = getMultiDataSourceKey();
		String paramValue = "";
		Map<String ,Map<String,String>>  dsHerpParamMap = herpParam.get(dsKey);
		if(dsHerpParamMap==null){
			loadHerpParam();
			dsHerpParamMap = herpParam.get(dsKey);
		}
		Map<String,String> herpParamMap = dsHerpParamMap.get(subSystemCode);
		if(herpParamMap==null){
			
		}else{
			paramValue = herpParamMap.get(key);
		}
        return paramValue;
    }
	
	public static Map<String, String> getSyModel( String menuId) {
		String dsKey = getMultiDataSourceKey();
		Map<String ,Map<String,String>>  modelMap = syModelMap.get(dsKey);
		if(modelMap==null){
			loadSyModel();
			modelMap = syModelMap.get(dsKey);
		}
		Map<String,String> syModel = modelMap.get(menuId);
        return syModel;
    }
	
	/*public static String getSystemModelStatus(String modelStatusKey){
		String dsKey = getMultiDataSourceKey();
		String v = "";
		return v;
	}*/
	
	//重新加载资源权限
	public void reloadResourceMap(){
		loadResourceMap();
	}
	
	//同步修改结账状态
	public void addModelStatus(String id){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getDataSource() );
		String statusSql = "SELECT * FROM sy_modelstatus WHERE id='"+id+"'";
		List<Map<String, Object>> statusList = jt.queryForList(statusSql);
		if(statusList!=null&&statusList.size()>0){
			Map<String,String> modelStatusMap = null;
			if(systemModelStatus==null){
				systemModelStatus = new HashMap<String, Map<String,String>>();
			}else{
				modelStatusMap = systemModelStatus.get(dsKey);
			}
			if(modelStatusMap==null){
				modelStatusMap = new HashMap<String,String>();
			}
			Map<String, Object> status = statusList.get(0);
			Object orgCode = status.get("orgCode");
			Object copyCode = status.get("copyCode");
			Object modelId = status.get("modelId");
			Object periodType = status.get("periodType");
			Object checkperiod = status.get("checkperiod");
			Object statusStr = status.get("status");
			String orgStr = "";
			if(orgCode!=null){
				orgStr += "_"+orgCode.toString();
				if(copyCode!=null){
					orgStr += "_"+copyCode.toString();
				}
			}
			modelStatusMap.put(modelId+"_"+periodType+"_"+checkperiod+orgStr, statusStr.toString());
			if("1".equals(statusStr.toString())){
				String sysWorkPeriodKey = modelId+"_"+periodType+orgStr;
				if(modelStatusMap.get(sysWorkPeriodKey)==null){
					modelStatusMap.put(sysWorkPeriodKey, checkperiod.toString());
				}
			}
			systemModelStatus.put(dsKey, modelStatusMap);
		}
	}
	
	//同步修改系统参数
	public static void addGlobalParam(String paramId){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getDataSource() );
		String paramSql = "SELECT * FROM sy_globalParam WHERE paramId='"+paramId+"'";
		List<Map<String, Object>> globalParams = jt.queryForList(paramSql);
		if(globalParams!=null&&globalParams.size()>0){
			Map<String ,Map<String,String>> dsGlobalParamMap = null;
			if(globalParamMap==null){
				globalParamMap = new HashMap<String, Map<String,Map<String,String>>>();
			}else{
				dsGlobalParamMap = globalParamMap.get(dsKey);
			}
			if(dsGlobalParamMap==null){
				dsGlobalParamMap = new HashMap<String ,Map<String,String>>();
			}
			Map<String, Object> globalParam = globalParams.get(0);
			Object subSystemCode = globalParam.get("subSystemCode");
			String subSystemCodeStr ="";
			String paramKey = globalParam.get("paramKey").toString();
			String paramValue = globalParam.get("paramValue").toString();
			//String paramName = globalParam.get("paramName").toString();
			if(subSystemCode!=null){
				subSystemCodeStr = subSystemCode.toString();
			}
			if(subSystemCodeStr==null||"".equals(subSystemCodeStr)){
				subSystemCodeStr = "all";
			}
			Map<String,String> subsSystemParam = dsGlobalParamMap.get(subSystemCodeStr);
			if(subsSystemParam==null){
				subsSystemParam = new HashMap<String, String>();
				//dsGlobalParamMap.put(subSystemCodeStr, subsSystemParam);
			}
			subsSystemParam.put(paramKey, paramValue);
			globalParamMap.put(dsKey, dsGlobalParamMap);
		}
	}
	
	//同步修改字典
	public static void addDictionaryItem(String dictionaryId){
		String dsKey = getMultiDataSourceKey();
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getDataSource() );
		String dicSql = "SELECT * FROM t_dictionary WHERE dictionaryId='"+dictionaryId+"'";
		List<Map<String, Object>> dictionaries = jt.queryForList(dicSql);
		if(dictionaries!=null&&dictionaries.size()>0){
			Map<String ,List<Map<String, Object>>>  dsDictionaryItemMap = null;
			if(dictionaryItemMap==null){
				dictionaryItemMap = new HashMap<String,Map<String ,List<Map<String, Object>>>>();
			}else{
				dsDictionaryItemMap = dictionaryItemMap.get(dsKey);
			}
			if(dsDictionaryItemMap==null){
				dsDictionaryItemMap = new HashMap<String, List<Map<String, Object>>>();
			}
			Map<String, Object> dictionary = dictionaries.get(0);
			String dicCode = dictionary.get("code").toString();
			String dicItemSql = "SELECT * FROM t_dictionary_items item LEFT JOIN t_dictionary dic ON item.dictionary_id=dic.dictionaryId WHERE dic.code='"+dicCode+"'";
			List<Map<String, Object>> dictionaryItems = jt.queryForList(dicItemSql);
			//List<DictionaryItem> dictionaryItems = dictionaryItemManager.getAllItemsByCode(dicCode);
			dsDictionaryItemMap.put(dicCode, dictionaryItems);
			dictionaryItemMap.put(dsKey, dsDictionaryItemMap);
		}
	}
	
	private static String getMultiDataSourceKey(){
		String dsKey = DynamicSessionFactoryHolder.getSessionFactoryName();
		if(dsKey==null){
			dsKey = "defaultDs";
		}
		return dsKey;
	}
	
	public static String[] getSubsystemIds() {
		return subsystemIds;
	}

	public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) 
		throws ServletException, IOException{
	}
	
	public void destory(){
	
	}
	
	public static Set<Map<String, Object>> getAllowSysRoot(String disabled) {
		String dsKey = getMultiDataSourceKey();
		Set<Map<String, Object>> sysRoot = new TreeSet<Map<String,Object>>(new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Map c1 = (Map) o1;
				Map c2 = (Map) o2;
				String m1 = c1.get("menuId").toString();
				String m2 = c2.get("menuId").toString();
				if(  m1.compareTo(m2)>0 ) {
					return 1;
				}else if(m1.compareTo(m2)==0){
					return 0;
				}else{
					return -1;
				}
			}
		});
		Set<Map<String, Object>> sysRootMenu = sysRootMenuMap.get(dsKey);
		if(sysRootMenu==null){
			loadDogMenus();
			sysRootMenu = sysRootMenuMap.get(dsKey);
		}
		for (Map<String, Object> menuMap : sysRootMenu) {
			String menuDisabled = menuMap.get("disabled").toString();
			if("2".equals(disabled)||menuDisabled.equals(disabled)){
				sysRoot.add(menuMap);
			}
		}
		return sysRoot;
	}
	
	public static Set<Map<String, Object>> getAllowBusRoot(String disabled) {
		String dsKey = getMultiDataSourceKey();
		Set<Map<String, Object>> busRoot = new TreeSet<Map<String,Object>>(new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Map c1 = (Map) o1;
				Map c2 = (Map) o2;
				String m1 = c1.get("menuId").toString();
				String m2 = c2.get("menuId").toString();
				if(  m1.compareTo(m2)>0 ) {
					return 1;
				}else if(m1.compareTo(m2)==0){
					return 0;
				}else{
					return -1;
				}
			}
		});
		Set<Map<String, Object>> busRootMenu = busRootMenuMap.get(dsKey);
		if(busRootMenu==null){
			loadDogMenus();
			busRootMenu = busRootMenuMap.get(dsKey);
		}
		for (Map<String, Object> menuMap : busRootMenu) {
			String menuDisabled = menuMap.get("disabled").toString();
			if("2".equals(disabled)||menuDisabled.equals(disabled)){
				busRoot.add(menuMap);
			}
		}
		return busRoot;
	}
	
	public static Set<Map<String, Object>> getAllowSubSystem(String disabled) {
		String dsKey = getMultiDataSourceKey();
		Set<Map<String, Object>> subSystemMenu = new TreeSet<Map<String,Object>>(new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Map c1 = (Map) o1;
				Map c2 = (Map) o2;
				String m1 = c1.get("menuId").toString();
				String m2 = c2.get("menuId").toString();
				if(  m1.compareTo(m2)>0 ) {
					return 1;
				}else if(m1.compareTo(m2)==0){
					return 0;
				}else{
					return -1;
				}
			}
		});
		Set<Map<String, Object>> sysRootMenu = sysRootMenuMap.get(dsKey);
		Set<Map<String, Object>> busRootMenu = busRootMenuMap.get(dsKey);
		if(sysRootMenu==null||busRootMenu==null){
			loadDogMenus();
			sysRootMenu = sysRootMenuMap.get(dsKey);
			busRootMenu = busRootMenuMap.get(dsKey);
		}
		for (Map<String, Object> menuMap : sysRootMenu) {
			String menuDisabled = menuMap.get("disabled").toString();
			if("2".equals(disabled)||menuDisabled.equals(disabled)){
				subSystemMenu.add(menuMap);
			}
		}
		for (Map<String, Object> menuMap : busRootMenu) {
			String menuDisabled = menuMap.get("disabled").toString();
			if("2".equals(disabled)||menuDisabled.equals(disabled)){
				subSystemMenu.add(menuMap);
			}
		}
		return subSystemMenu;
	}

	public static Map<String, Map<String, Boolean>> getDogMenusMap() {
		return dogMenusMap;
	}

	public static void setDogMenusMap(Map<String, Map<String, Boolean>> dogMenusMap) {
		ContextUtil.dogMenusMap = dogMenusMap;
	}
	
	public static void loadTimerTask(){
		String spSql = "SELECT * FROM sy_sptask where disabled=0";
		JdbcTemplate jt = new JdbcTemplate( (DataSource) SpringContextHelper.getDataSource() );
		List<Map<String, Object>> sptasks = jt.queryForList(spSql);
		ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor( 5 );
		for(Map<String, Object> task : sptasks){
			final String taskName = task.get("taskName").toString();
			Object time = task.get("exeTime");
			int t = 1000;
			if(time!=null){
				try {
					t = Integer.parseInt(time.toString());
					scheduler.scheduleAtFixedRate( new Runnable() {
						public void run() {
							exePrecess(taskName,null);
						}
					}, 10, t,TimeUnit.SECONDS );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private static void exePrecess( String taskName, Object[] args ) {
		System.out.println("执行存储过程："+taskName);
		String retMsg = "";
        int retCode = 0;
        ReturnUtil returnUtil = new ReturnUtil();
        try {
        	if(args==null){
        		args = new Object[0];
        	}
            StringBuffer sb = new StringBuffer();
            for ( int i = 0; i < args.length; i++ ) {
                sb.append( "?" );
                sb.append( "," );
            }
            String callString = "{call " + taskName + "(" + sb.toString() + "?,?)}";
            
            DataSource dataSource = (DataSource) SpringContextHelper.getDataSource();
            Connection con = null;
            if(dataSource==null){
            	return ;
            }else{
            	 con = dataSource.getConnection();
            }

            CallableStatement callStat = null;
            callStat = con.prepareCall( callString );
            int j = 1;
            for ( int k = 0; k < args.length; k++ ) {
                callStat.setObject( j + 1, args[k] );
                j++;
            }
            callStat.registerOutParameter( 1, Types.INTEGER );
            callStat.registerOutParameter( j + 1, Types.VARCHAR );
            callStat.executeUpdate();
            retCode = callStat.getInt( 1 );
            retMsg = callStat.getString( j + 1 );
            returnUtil.setStatusCode(retCode);
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
            retMsg = ex.getMessage();
            returnUtil.setStatusCode(300);

        }
        if ( retMsg == null || retMsg.trim().equals( "" ) ) {
            if ( ( retCode != 100 ) && ( retCode != 0 ) )
                retMsg = "处理失败。";
            else
                retMsg = "处理成功。";
        }
	}
	/* public static Map<String, Map<String, Collection<ConfigAttribute>>> getResourceMap() {
			return resourceMap;
		}

	public static void setResourceMap(
			Map<String, Map<String, Collection<ConfigAttribute>>> resourceMap) {
		ContextUtil.resourceMap = resourceMap;
	}

	public static Map<String, Map<String, Map<String, String>>> getGlobalParamMap() {
		return globalParamMap;
	}

	public static void setGlobalParamMap(
			Map<String, Map<String, Map<String, String>>> globalParamMap) {
		ContextUtil.globalParamMap = globalParamMap;
	}

	public static Map<String, Map<String, List<Map<String, Object>>>> getDictionaryItemMap() {
		return dictionaryItemMap;
	}

	public static void setDictionaryItemMap(
			Map<String, Map<String, List<Map<String, Object>>>> dictionaryItemMap) {
		ContextUtil.dictionaryItemMap = dictionaryItemMap;
	}
	public static Map<String, Map<String, String>> getSystemModelStatus() {
		return systemModelStatus;
	}

	public static void setSystemModelStatus(
			Map<String, Map<String, String>> systemModelStatus) {
		ContextUtil.systemModelStatus = systemModelStatus;
	}*/
	public class MenuMapComparator implements Comparator {
		public int compare(Object o1, Object o2){
			Map c1 = (Map) o1;
			Map c2 = (Map) o2;
			String m1 = c1.get("menuId").toString();
			String m2 = c2.get("menuId").toString();
			if(  m1.compareTo(m2)>0 ) {
				return 1;
			}else if(m1.compareTo(m2)==0){
				return 0;
			}else{
				return -1;
			}
		}
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 7);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		System.out.println(calendar.getTimeInMillis());
	}
}
