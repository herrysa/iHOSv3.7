package com.huge.ihos.system.systemManager.role.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.huge.ihos.multidatasource.DynamicSessionFactoryHolder;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.dao.RoleDao;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.SecurityMetadataSourceManager;
import com.huge.ihos.system.systemManager.user.dao.UserDao;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.HaspDogHandler;
import com.huge.util.TestTimer;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;

public class RoleAction
    extends JqGridBaseAction
    implements  ApplicationContextAware {
    private List roles;

    private List menus;

    private List users;

    private Role role;

    private Long roleId;

    private String hasUserRole;

    private UserDao userDao;

    private RoleDao roleDao;

    private ApplicationContext applicationContext;

    private SecurityMetadataSourceManager securityMetadataSourceManager;

    public String getHasUserRole() {
        return hasUserRole;
    }

    public void setHasUserRole( String hasUserRole ) {
        this.hasUserRole = hasUserRole;
    }

    public Long getRoleId() {
        return roleId;
    }

    private String content;

    // private List deptList;

    // private List dutyList;
    //  private List educationalLevelList;
    //  private List jobTitleList;
    // private List postTypeList;
    //  private List sexList;
    // private List statusList;

    private String iconLeaf = "leaf.png";

    private String iconRoot = "jx_sys.png";

    private String iconParentClose = "folder.png";

    private String iconParentOpen = "folder.png";

    private MenuManager menuManager;

    private List<Menu> menuTreeList;

    private String menuTreeListString;
    
    private String dataType = "";

    //private PrivilegeManager privilegeManager;
    /*public PrivilegeManager getPrivilegeManager() {
    	return privilegeManager;
    }



    public void setPrivilegeManager(PrivilegeManager privilegeManager) {
    	this.privilegeManager = privilegeManager;
    }*/

    public List getMenuTreeList() {
        return menuTreeList;
    }

    public void setMenuTreeList( List menuTreeList ) {
        this.menuTreeList = menuTreeList;
    }

    public String getMenuTreeListString() {
        return menuTreeListString;
    }

    public void setMenuTreeListString( String menuTreeListString ) {
        this.menuTreeListString = menuTreeListString;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid( String pid ) {
        this.pid = pid;
    }

    private String id;

    private String pid;

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager( MenuManager menuManager ) {
        this.menuManager = menuManager;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

 

    //    public void prepare() {
    //       /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
    //            // prevent failures on new
    //            String personId = getRequest().getParameter("person.personId");
    //            if (personId != null && !personId.equals("")) {
    //                person = personManager.get(new Long(personId));
    //            }
    //        }
    //    	 */
    //    	  	this.dutyList=this.getDictionaryItemManager().getAllItemsByCode("postDuty");
    //    	    this.educationalLevelList=this.getDictionaryItemManager().getAllItemsByCode("education");
    //    	    this.jobTitleList=this.getDictionaryItemManager().getAllItemsByCode("jobTitle");
    //    	    this.postTypeList=this.getDictionaryItemManager().getAllItemsByCode("postType");
    //    	    this.sexList=this.getDictionaryItemManager().getAllItemsByCode("sex");
    //    	    this.statusList=this.getDictionaryItemManager().getAllItemsByCode("empType");
    //    	deptList=departmentManager.getAll();
    //    	    
    //    	
    //        this.clearSessionMessages();
    //    }

    /*   public String list() {
           persons = personManager.search(query, Person.class);
           return SUCCESS;
       }*/

    public void setRoleId( Long roleId ) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole( Role role ) {
        this.role = role;
    }

    public String delete() {
        roleManager.remove( role.getId() );
        saveMessage( getText( "role.deleted" ) );

        return "edit_success";
    }

    private int editType = 0; // 0:new,1:edit

    public void setEditType( int editType ) {
        this.editType = editType;
    }

    public int getEditType() {
        return editType;
    }

    public String edit() {
        if ( roleId != null ) {
            role = roleManager.get( roleId );
            Iterator<Menu> pIt = role.getMenus().iterator();
            content = "";
            while ( pIt.hasNext() ) {
                content += pIt.next().getMenuId() + ",";
            }
            this.editType = this.editType == 2 ? 2 : 1;
        }
        else {
            role = new Role();
            this.editType = 0;
        }

        return SUCCESS;
    }

    public String save()
        throws Exception {
        Role roleOld = null;
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }

        boolean isNew = ( role.getId() == null );

        if ( !isNew ) {
            roleOld = roleManager.get( role.getId() );
            role.setUsers( roleOld.getUsers() );
            role.setSearchDataPrivileges(roleOld.getSearchDataPrivileges());
            if(roleOld.getRoleType()!=null&&roleOld.getRoleType()==1){
        		role.setRoleType(1);
        	}else{
        		role.setRoleType(0);
        	}
        }

        role.getMenus().clear();
        String[] contentArr;
        if ( content != null && !content.equals( "" ) ) {
            contentArr = content.split( "," );
            for ( int i = 0; i < contentArr.length; i++ ) {
                role.addMenu( menuManager.get( contentArr[i] ) );
            }
            Set<Menu> roleRootMenu = role.getRootMenus();
            /*for(Menu menu : roleRootMenu){
            	int userCount = roleManager.getUserCountByRooId(menu.getMenuId());
            	int allowCount = HaspDogHandler.getInstance().getDogService().getSubSystemAllowUser(menu.getMenuId());
            	if(allowCount!=-1&&userCount>allowCount){
            		return ajaxForward(false,"子系统'"+menu.getMenuName()+"'的用户数已达上限！",false);
            	}
            }*/
        }
        try {
            roleManager.save( role );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        String key = ( isNew ) ? "role.added" : "role.updated";
        saveMessage( getText( key ) );

        userDao = (UserDao) applicationContext.getBean( "userDao" );
        roleDao = (RoleDao) applicationContext.getBean( "roleDao" );
        securityMetadataSourceManager = (SecurityMetadataSourceManager) applicationContext.getBean( "SecurityMetadataSourceManager" );
        securityMetadataSourceManager.setUserDao( userDao );
        securityMetadataSourceManager.setRoleDao( roleDao );
//        securityMetadataSourceManager.loadResourceDefine();
        securityMetadataSourceManager.loadResourceDefine(DynamicSessionFactoryHolder.getSessionFactoryName());

        return ajaxForward( getText( key ) );
    }

    public String roleGridList() {
        try {
            List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
            PropertyFilter propertyFilterAdmin = new PropertyFilter("NES_name","ROLE_ADMIN");
            filters.add(propertyFilterAdmin);
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = roleManager.getAppManagerCriteriaWithSearch( pagedRequests, Role.class, filters );
            this.roles = (List<Role>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "roleGridList Error", e );
        }
        return SUCCESS;
    }

    public String roleGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    roleManager.remove( Long.parseLong( removeId ) );
                }
                gridOperationMessage = this.getText( "role.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                roleManager.save( role );
                String key = ( oper.equals( "add" ) ) ? "role.added" : "role.updated";
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

    public String initTreeWithCheckJsonString() {
        try {
        	TestTimer tt = new TestTimer("initTreeWithCheck");
        	tt.begin();
        	//String[] subsystemIds = HaspDogHandler.getInstance().getDogService().getAllowedSubSystem();
        	//HaspDogHandler.getInstance(this.menuManager);
            /*Map<String, Boolean> dogMenuMap = new HashMap<String, Boolean>();
            for ( String subsystemId : subsystemIds ) {
            	dogMenuMap.put(subsystemId, true);
            }*/
            //TreeSet<ZTreeSimpleNode> zTreeNodeSet = new TreeSet<ZTreeSimpleNode>();
            /*menuTreeList = new ArrayList<Menu>();
            //HaspDogHandler haspDogHandler = new HaspDogHandler();
            
            List<Menu> enableMenus = this.menuManager.getAllEnabled();
            TreeSet<Menu> menuTreeSet = new TreeSet<Menu>();
            TreeSet<Menu> menuTreeSetFull = new TreeSet<Menu>();
            String[] subsystemIds = HaspDogHandler.getInstance().getDogService().getAllowedSubSystem();
            
            Map<String, Boolean> dogMenuMap = new HashMap<String, Boolean>();
            for ( String subsystemId : subsystemIds ) {
            	dogMenuMap.put(subsystemId, true);
            }
            
            for(Menu menu : enableMenus){
            	if((dogMenuMap.get(menu.getMenuId())==null?false:true)){
            		if(!menu.getFlag())
            			menu.setFlag(true);
            		menuTreeSet.add(menu);
            	}else if((dogMenuMap.get(menu.getFlagNodeInPath().getMenuId())==null?false:true)){
            		menuTreeSet.add(menu);
            	}
        	}
            
            for(Menu menu : menuTreeSet){
            	menuTreeSetFull.add(menu);
            	Menu menuTemp = menu.getParent();
            	while(menuTemp!=null){
            		menuTreeSetFull.add(menuTemp);
            		menuTemp = menuTemp.getParent();
            	}
            }
            Iterator<Menu> menuIt = menuTreeSetFull.iterator();
            List ztreeNodeList = nodeListWithCheck(menuIt);*/
            
            /*List<Menu> rootMenu = menuManager.getAllRootMenu();
            Map<String,Boolean> dogMenus = new HashMap<String, Boolean>();
            for(Menu menu : rootMenu){
            	List<Menu> pathStack = new ArrayList<Menu>();
            	traversalTree(menu ,pathStack,dogMenus ,false ,dogMenuMap);
            }*/
        	Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();
            List<Menu> enableMenus = this.menuManager.getAllEnabled();
            List<Menu> menusShow = new ArrayList<Menu>();
        	Map<String,Boolean> menusFull = new HashMap<String, Boolean>();
        	for(Menu menu : enableMenus){
        		if((dogMenus.get(menu.getMenuId())==null?false:true)){
        			menusShow.add(menu);
        		}
        	}
            Iterator<Menu> menuIt = menusShow.iterator();
            List ztreeNodeList = nodeListWithCheck(menuIt);
            /*if(id!=null){
            	zTreeNodeSet = this.nodeListWithCheck(id);
            }else{
            	dataType = "0";
            	String[] subsystemIds = HaspDogHandler.getInstance().getDogService().getAllowedSubSystem();

                Map<String, Boolean> dogMenuMap = new HashMap<String, Boolean>();
                for ( String subsystemId : subsystemIds ) {
                	dogMenuMap.put(subsystemId, true);
                }
                
                menuTreeList = menuManager.getAllEnabled();
                
                for(Menu menu : menuTreeList){
                	if(((dogMenuMap.get(menu.getMenuId())==null?false:true)||dogMenuMap.get(menu.getFlagNodeInPath().getMenuId()==null?false:true))){
                		menuTreeSet.add(menu);
                	}
            	}
                for ( String subsystemId : subsystemIds ) {
                	TreeSet<ZTreeSimpleNode> subsystems = this.nodeListWithCheck( subsystemId );
                    if ( subsystems != null ) {
                        zTreeNodeSet.addAll( subsystems );
                    }
                }
            }*/
            
           
            //this.menuTreeList = this.nodeListWithCheck(id);
            if ( ztreeNodeList != null ) {
                this.menuTreeListString = JSONArray.fromObject( ztreeNodeList ).toString();
            }
            HttpServletResponse response = ServletActionContext.getResponse();

            response.setCharacterEncoding( "UTF-8" );
            response.getWriter().write( menuTreeListString );
            tt.done();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void traversalTree(Menu parent,List<Menu> pathStack , Map<String,Boolean> menus , boolean isShow,Map<String, Boolean> dogMenuMap){
    	/*System.out.println("isShow:"+isShow);
    	System.out.println("menus:"+menus.keySet().toString());
    	System.out.println("pathStack:"+pathStack.toString());*/
    	//System.out.println("menus:"+menus.keySet().toString());
    	pathStack.add(parent);
    	boolean showCharge = isShow==true?true:dogMenuMap.get(parent.getMenuId())==null?false:true;
    	List<Menu> menusTemp = this.menuManager.getAllByParent( parent.getMenuId() );
    	if(showCharge&&(menusTemp==null||menusTemp.size()==0)){
    		for(Menu menupath : pathStack){
    			menus.put(menupath.getMenuId(),true);
    		}
    		pathStack.remove(parent);
    	}
    	for(Menu menu : menusTemp){
    		traversalTree(menu ,pathStack,menus ,showCharge ,dogMenuMap);
    	}
    }
    
    private List nodeListWithCheck(Iterator itr){
    	String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        //themeName = (String)this.getRequest().getAttribute("themeName");
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";//logo.jpg
    	List list = new ArrayList();
    	try {
    		while ( itr.hasNext() ) {
                Menu menu = (Menu) itr.next();
                ZTreeSimpleNode rt = new ZTreeSimpleNode();
                rt.setName( menu.getMenuName() );
                rt.setId( menu.getMenuId() );
                //if(menu.isLeaf())
                rt.setIsParent( !menu.isLeaf() );
                //rt.setOpen(true);
                //rt.setIcon("/scripts/zTree/css/zTreeStyle/img/diy/5.png");
                if ( menu.getParent() == null ) {
                    rt.setpId( "" );
                }
                else {
                    rt.setpId( menu.getParent().getMenuId() );
                }

                if ( rt.getIsParent() && rt.getpId().equals( "" ) ) {
                    rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                    rt.setOpen( true );
                }
                else if ( rt.getIsParent() && !rt.getpId().equals( "" ) ) {
                    rt.setIcon( this.getContextPath() + iconPath + this.iconParentClose );
                }
                // 
                else if ( rt.getpId().equals( "" ) ) {
                    rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                    rt.setOpen( true );
                }
                else {
                    rt.setIcon( this.getContextPath() + iconPath + this.iconLeaf );
                    rt.setActionUrl( menu.getMenuAction() );
                }

                list.add( rt );
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
    }
    
    private TreeSet<ZTreeSimpleNode> nodeListWithCheck( String id ) {

        String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        //themeName = (String)this.getRequest().getAttribute("themeName");
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";//logo.jpg

        List<Menu> menus = null;
    	
        TreeSet<ZTreeSimpleNode> list = new TreeSet<ZTreeSimpleNode>();

        TreeSet<Menu> menuTreeSet = new TreeSet<Menu>();
        try {
		
        if ( id == null )
            menus = this.menuManager.getAllEnabled();
        else {
            Menu paraent = this.menuManager.get( id );
            if ( !paraent.isDisabled() ) {
                menus = this.menuManager.getAllByParent( id );
                //menus.add( this.menuManager.get( id ) );
                if("0".equals(dataType)){
                	menus.add( this.menuManager.get( id ) );
                	for(Menu menu : menus){
                    	menuTreeSet.add(menu);
                    	Menu menuTemp = menu.getParent();
                    	while(menuTemp!=null){
                    		menuTreeSet.add(menuTemp);
                    		menuTemp = menuTemp.getParent();
                    	}
                    }
                }
                
            }
            else {
                return null;
            }

        }

        Iterator itr = null;
        if("0".equals(dataType)){
        	itr = menuTreeSet.iterator();
        }else{
        	itr = menus.iterator();
        }

        if ( roleId != null ) {
            role = roleManager.get( roleId );

        }

        while ( itr.hasNext() ) {
            Menu menu = (Menu) itr.next();
            ZTreeSimpleNode rt = new ZTreeSimpleNode();
            rt.setName( menu.getMenuName() );
            rt.setId( menu.getMenuId() );
            //if(menu.isLeaf())
            rt.setIsParent( !menu.isLeaf() );
            //rt.setOpen(true);
            //rt.setIcon("/scripts/zTree/css/zTreeStyle/img/diy/5.png");
            if ( menu.getParent() == null ) {
                rt.setpId( "" );
            }
            else {
                rt.setpId( menu.getParent().getMenuId() );
            }

            if ( rt.getIsParent() && rt.getpId().equals( "" ) ) {
                rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                rt.setOpen( true );
            }
            else if ( rt.getIsParent() && !rt.getpId().equals( "" ) ) {
                rt.setIcon( this.getContextPath() + iconPath + this.iconParentClose );
            }
            // 
            else if ( rt.getpId().equals( "" ) ) {
                rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                rt.setOpen( true );
            }
            else {
                rt.setIcon( this.getContextPath() + iconPath + this.iconLeaf );
                rt.setActionUrl( menu.getMenuAction() );
            }

            list.add( rt );
        }
    	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return list;
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( role == null ) {
            return "Invalid role Data";
        }

        return SUCCESS;

    }

    public String roleMenuGridList() {
        try {
            List menuList = null;
            if(roleId==null){
            	return null;
            }
            Set<Menu> menus = roleManager.get( roleId ).getMenus();
            if ( menus != null && menus.size() != 0 ) {
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
            }
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

    public String roleUserGridList() {
        try {
            List menuList = null;
            Set menus = roleManager.get( roleId ).getUsers();
            if ( menus != null && menus.size() != 0 ) {
                menuList = new ArrayList( menus );
            }
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            if ( menuList != null ) {
                pagedRequests.setList( menuList );
                pagedRequests.setTotalNumberOfRows( menuList.size() );
            }
            this.users = new ArrayList(); 
            List<User> userList= (List<User>)pagedRequests.getList();
            for(User user : userList){
            	Map userTemp = new HashMap();
            	userTemp.put("username", user.getUsername());
            	userTemp.put("personName", user.getPersonName());
            	userTemp.put("deptName", user.getDeptName());
            	users.add(userTemp);
            }
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();
        }
        catch ( Exception e ) {
            log.error( "roleGridList Error", e );
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public List getRoles() {
        return roles;
    }

    @Override
    public void setApplicationContext( ApplicationContext applicationContext )
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao( RoleDao roleDao ) {
        this.roleDao = roleDao;
    }

    public SecurityMetadataSourceManager getSecurityMetadataSourceManager() {
        return securityMetadataSourceManager;
    }

    public void setSecurityMetadataSourceManager( SecurityMetadataSourceManager securityMetadataSourceManager ) {
        this.securityMetadataSourceManager = securityMetadataSourceManager;
    }

    public List getMenus() {
        return menus;
    }

    public void setMenus( List menus ) {
        this.menus = menus;
    }

    public List getUsers() {
        return users;
    }

    public void setUsers( List users ) {
        this.users = users;
    }

}