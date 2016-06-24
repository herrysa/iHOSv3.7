package com.huge.ihos.system.systemManager.menu.webapp.action;

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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.oa.bulletin.model.Bulletin;
import com.huge.ihos.system.oa.bulletin.service.BulletinManager;
import com.huge.ihos.system.oa.bylaw.model.ByLaw;
import com.huge.ihos.system.oa.bylaw.service.ByLawManager;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.HaspDogHandler;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;

public class MenuAction
    extends JqGridBaseAction
      {
    private MenuManager menuManager;

    private PeriodManager periodManager;

    private BulletinManager bulletinManager;

    private ByLawManager byLawManager;

    private List<Menu> menus;

    private Menu menu;

    private String menuId;

    //  private List roots;
    private List subSystems;

    private int navTabNum = 10;
    
    private String leftMenuType = "";

    //private List		roots;
    private List subMenus;

    private String rootId;

    private String rootMenusString;

    private String subSystem = "";

    private List<Bulletin> bulletinList;

    private List<ByLaw> byLawList;
    
    private String[] subsystemIds;
    
    private String moduleFlag = "";
    private String menuTreeListString = "";
    
    public String getMenuTreeListString() {
		return menuTreeListString;
	}

	public void setMenuTreeListString(String menuTreeListString) {
		this.menuTreeListString = menuTreeListString;
	}
	
	private Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();

	public Map<String, Boolean> getDogMenus() {
		return dogMenus;
	}

	public void setDogMenus(Map<String, Boolean> dogMenus) {
		this.dogMenus = dogMenus;
	}

	private String iconLeaf = "leaf.png";

    private String iconRoot = "jx_sys.png";

    private String iconParentClose = "folder.png";

    private String iconParentOpen = "folder.png";

    public String getModuleFlag() {
		return moduleFlag;
	}

	public void setModuleFlag(String moduleFlag) {
		this.moduleFlag = moduleFlag;
	}

    public String getRootMenusString() {
        return rootMenusString;
    }

    public void setRootMenusString( String rootMenusString ) {
        this.rootMenusString = rootMenusString;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem( String subSystem ) {
        this.subSystem = subSystem;
    }

    public void setMenuManager( MenuManager menuManager ) {
        this.menuManager = menuManager;
    }

    public List getMenus() {
        return menus;
    }

    //    public List getRoots() {
    //		return roots;
    //	}

    public List getSubSystems() {
        return subSystems;
    }

    /**
     * Grab the entity from the database before populating with request parameters
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        /* if (getRequest().getMethod().equalsIgnoreCase("post")) {
             // prevent failures on new
             String menuId = getRequest().getParameter("menu.menuId");
             if (menuId != null && !menuId.equals("")) {
                 menu = menuManager.get(new Long(menuId));
             }
         }*/
        //	this.roots = this.menuManager.getAllRootMenu();
//        this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
        this.currentUser = this.getSessionUser();
        this.currentDate = this.getCurrentDateString();
        List<Menu> subsystems = this.menuManager.getAllSubSystem();
        this.subSystems = new ArrayList();
        for(Menu menu : subsystems){
            if(dogMenus==null){
                HaspDogHandler.getInstance().makeDogMenus();
                dogMenus =  HaspDogHandler.getInstance().getDogMenus();
            }
        	if((dogMenus.get(menu.getMenuId())==null)?false:true){
        		this.subSystems.add(menu);
        	}
        }
        String navTabNumString = this.getGlobalParamByKey( "navTab_num" );
        leftMenuType = this.getGlobalParamByKey( "leftMenuType" );
        if ( navTabNumString != null && !navTabNumString.equals( "" ) ) {
            navTabNum = Integer.parseInt( navTabNumString );
        }
        bulletinList = this.bulletinManager.getBulletinsByUser( currentUser );
        byLawList = this.byLawManager.getByLawsByUser( currentUser );
        this.clearSessionMessages();
    }

    /*   public String list() {
           menus = menuManager.search(query, Menu.class);
           return SUCCESS;
       }*/

    public void setMenuId( String menuId ) {
        this.menuId = menuId;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu( Menu menu ) {
        this.menu = menu;
    }

    public String delete() {
        menuManager.remove( menu.getMenuId() );
        saveMessage( getText( "menu.deleted" ) );

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
        if ( menuId != null ) {
            menu = menuManager.get( menuId );

            this.subMenus = this.menuManager.getAllNotLeafByRoot( menu.getSubSystem().getMenuId() );
            this.editType = 1;
            this.setEntityIsNew( false );
        }
        else {
            menu = new Menu();
            this.editType = 0;
            this.setEntityIsNew( true );
        }

        return SUCCESS;
    }

    public String getMenuId() {
        return menuId;
    }

    public String save()
        throws Exception {
        if ( cancel != null ) {
            return "cancel";
        }

        if ( delete != null ) {
            return delete();
        }
        
        boolean isNew = ( menu.getMenuId() == null );
        if(!this.isEntityIsNew()){
        	Menu oldMenu = menuManager.get(menu.getMenuId());
            if(oldMenu.getIconName()!=null){
            	menu.setIconName(oldMenu.getIconName());
            	menu.setRoles(oldMenu.getRoles());
            	menu.setUsers(oldMenu.getUsers());
            }
        }
        menuManager.save( menu );
        HaspDogHandler.getInstance().addDogMenu(menu.getMenuId());
        String key = ( this.isEntityIsNew() ) ? "menu.added" : "menu.updated";
        saveMessage( getText( key ) );

        return ajaxForward( getText( key ) );
    }

    public String menuGridList() {
        try {
        	String menuids = "";
        	for(String menuid : dogMenus.keySet()){
        		menuids += menuid+",";
        	}
        	if(!"".equals(menuids)){
        		menuids = menuids.substring(0,menuids.length()-1);
        	}
        	List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest( getRequest() );
        	PropertyFilter allowMenu = new PropertyFilter("INS_menuId",menuids);
        	filters.add(allowMenu);
            JQueryPager pagedRequests = null;
            pagedRequests = (JQueryPager) pagerFactory.getPager( PagerFactory.JQUERYTYPE, getRequest() );
            pagedRequests = menuManager.getAppManagerCriteriaWithSearch( pagedRequests, Menu.class, filters );
            this.menus = (List<Menu>) pagedRequests.getList();
            records = pagedRequests.getTotalNumberOfRows();
            total = pagedRequests.getTotalNumberOfPages();
            page = pagedRequests.getPageNumber();

        }
        catch ( Exception e ) {
            log.error( "menuGridList Error", e );
        }
        return SUCCESS;
    }

    public String menuGridEdit() {
        try {
            if ( oper.equals( "del" ) ) {
                StringTokenizer ids = new StringTokenizer( id, "," );
                while ( ids.hasMoreTokens() ) {
                    String removeId = ids.nextToken();
                    menuManager.remove( removeId );
                }
                gridOperationMessage = this.getText( "menu.deleted" );
                return ajaxForward( true, gridOperationMessage, false );
            }

            if ( oper.equals( "add" ) || oper.equals( "edit" ) ) {
                String error = isValid();
                if ( !error.equalsIgnoreCase( SUCCESS ) ) {
                    gridOperationMessage = error;
                    return SUCCESS;
                }
                menuManager.save( menu );
                String key = ( oper.equals( "add" ) ) ? "menu.added" : "menu.updated";
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

    public String menuDoubleSelect() {
        List<Menu> subsystems = this.menuManager.getAllRootMenu();
        List<Menu> subMenus = null;
        if ( this.rootId != null && !rootId.trim().equalsIgnoreCase( "" ) )
            subMenus = this.menuManager.getAllNotLeafByRoot( rootId );
        this.subSystems = new ArrayList();
        this.subMenus = new ArrayList();
        for(Menu menu : subsystems){
        	if((dogMenus.get(menu.getMenuId())==null)?false:true){
        		this.subSystems.add(menu);
        	}
        }
        for(Menu menu : subMenus){
        	if((dogMenus.get(menu.getMenuId())==null)?false:true){
        		this.subMenus.add(menu);
        	}
        }
        return SUCCESS;
    }

    /**
     * @TODO you should add some validation rules those are difficult on client side.
     * @return
     */
    private String isValid() {
        if ( menu == null ) {
            return "Invalid menu Data";
        }

        return SUCCESS;

    }

    List<ZTreeSimpleNode> alowMenus;
    public List<ZTreeSimpleNode> getAlowMenus() {
		return alowMenus;
	}

	public void setAlowMenus(List<ZTreeSimpleNode> alowMenus) {
		this.alowMenus = alowMenus;
	}

	public String findAlowMuens(){
    	String iconLeaf = "leaf.png";
        String iconRoot = "jx_sys.png";
        String iconParentClose = "folder.png";
        String iconParentOpen = "folder.png";
        String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";//logo.jpg
        try {
        	TreeSet<Menu> rightMenus = new TreeSet<Menu>();
        	boolean flag = false;
        	Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();
        	Map<String, Boolean> sysMenuMap = new HashMap<String, Boolean>();
        	SecurityContext securityContext = SecurityContextHolder.getContext();
            String username = securityContext.getAuthentication().getName();
            User user = userManager.getUserByUsername( username );
            
            User userSv = (User)getSession().getAttribute(""+user.getId());
            SystemVariable sv = userSv.getSystemVariable();
            if("未定义".equals(sv.getPeriod())){
            	sysMenuMap.put("50", true);
            	sysMenuMap.put("60", true);
            }else{
            	List<Menu> sysMenus = menuManager.getBusinessSys(0);
                String sysMenuStr = "";
                for(Menu rootMenu : sysMenus){
                	sysMenuStr += rootMenu.getMenuId()+",";
                	sysMenuMap.put(rootMenu.getMenuId(), true);
                }
                sysMenuStr = OtherUtil.subStrEnd(sysMenuStr, ",");
                
                String currentSys = sv.getCurrentRootMenu();
                if(OtherUtil.measureNotNull(currentSys)&&!"-1".equals(currentSys)){
                	sysMenuStr += ","+currentSys;
    				sysMenuMap.put(currentSys, true);
                }
            }
            
            
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
                	Boolean allowSubsys = sysMenuMap.get(menu.getSubSystem().getMenuId());
            		if(allowSubsys!=null&&allowSubsys){
	                	if((dogMenus.get(menu.getMenuId())==null?false:true)){
	                		rightMenus.add(menu);
	            		}
            		}
            	}
            }
            Iterator itr = null;
            if ( !flag ) {
                Set<Menu> menus = user.getMenus();
                for ( Menu menuItem : menus ) {
                	Boolean allowSubsys = sysMenuMap.get(menuItem.getSubSystem().getMenuId());
            		if(allowSubsys!=null&&allowSubsys){
	                	if((dogMenus.get(menuItem.getMenuId())==null?false:true)){
	                		rightMenus.add(menuItem);
	            		}
            		}
                }
                TreeSet<Menu> rightMenusCopy = new TreeSet<Menu>();
                for(Menu menu : rightMenus){
                	rightMenusCopy.add(menu);
                	Menu menuTemp = menu.getParent();
                	while(menuTemp!=null){
                		rightMenusCopy.add(menuTemp);
                		menuTemp = menuTemp.getParent();
                	}
                }
                itr = rightMenusCopy.iterator();
            }else{
            	List<Menu> enableMenus = menuManager.getAllEnabled();
            	TreeSet<Menu> rightMenusCopy = new TreeSet<Menu>();
            	for(Menu menu : enableMenus){
            		/*if(rootMenuId.equals(menu.getSubSystem().getMenuId())&&(dogMenus.get(menu.getMenuId())==null?false:true)){
            			rightMenusCopy.add(menu);
            		}*/
            		Boolean allowSubsys = sysMenuMap.get(menu.getSubSystem().getMenuId());
            		if(allowSubsys!=null&&allowSubsys){
	            		if((dogMenus.get(menu.getMenuId())==null?false:true)){
	            			rightMenusCopy.add(menu);
	            		}
            		}
            	}
            	itr = rightMenusCopy.iterator();
            }
        	
            alowMenus = new ArrayList();
            while ( itr.hasNext() ) {
                subSystem = "";
                Menu menu = (Menu) itr.next();
                if((ContextUtil.versionType==1)&&menu.getParent()==null){
                	String menuName = menu.getMenuName();
                	menu.setMenuName(menuName+"（演示版）");
                }
                if ( menu.isDisabled() ) {
                    continue;
                }
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
                    rt.setIcon( this.getContextPath() + iconPath + iconParentClose );
                }
                // 
                else if ( rt.getpId().equals( "" ) ) {
                    rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                    rt.setOpen( true );
                }
                else {
                    rt.setIcon( this.getContextPath() + iconPath + iconLeaf );
                    //rt.setActionUrl(menu.getMenuAction());
                }

                rt.setActionUrl( menu.getMenuAction() );
                alowMenus.add( rt );
                //ttTestTimer.done();
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
    
    public String getAllRootMenu() {
    	log.info("enter getAllRootMenu method");
    	String iconLeaf = "leaf.png";
        String iconRoot = "jx_sys.png";
        String iconParentClose = "folder.png";
        String iconParentOpen = "folder.png";
        String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";//logo.jpg

        try {
        	SystemVariable systemVariable = (SystemVariable)getSession().getAttribute("currentSystemVariable");
        	//String rootMenuId = systemVariable.getCurrentRootMenu();
        	TreeSet<Menu> rightMenus = new TreeSet<Menu>();
        	boolean flag = false;
        	//HaspDogHandler haspDogHandler = new HaspDogHandler();
        	//HaspDogHandler.getInstance(menuManager);
        	Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();
        	/*subsystemIds = HaspDogHandler.getInstance().getDogService().getAllowedSubSystem();
            Map<String, Boolean> dogMenuMap = new HashMap<String, Boolean>();
            for ( String subsystemId : subsystemIds ) {
            	dogMenuMap.put(subsystemId, true);
            }*/
            /*List<Menu> allMenu = menuManager.getAll();
            Map<String, Boolean> menuMap = new HashMap<String, Boolean>();
            for(Menu menu : allMenu){
            	menuMap.put(menu.getMenuId(), true);
            	
            }*/
//this.log.error(  subsystemIds );
        	Map<String, Boolean> sysMenuMap = new HashMap<String, Boolean>();
        	SecurityContext securityContext = SecurityContextHolder.getContext();
            String username = securityContext.getAuthentication().getName();
            User user = userManager.getUserByUsername( username );
            
            User userSv = (User)getSession().getAttribute(""+user.getId());
            SystemVariable sv = userSv.getSystemVariable();
            if("未定义".equals(sv.getPeriod())){
            	sysMenuMap.put("50", true);
            	sysMenuMap.put("60", true);
            }else{
            	List<Menu> sysMenus = menuManager.getBusinessSys(0);
                String sysMenuStr = "";
                for(Menu rootMenu : sysMenus){
                	sysMenuStr += rootMenu.getMenuId()+",";
                	sysMenuMap.put(rootMenu.getMenuId(), true);
                }
                sysMenuStr = OtherUtil.subStrEnd(sysMenuStr, ",");
                
                String currentSys = sv.getCurrentRootMenu();
                if(OtherUtil.measureNotNull(currentSys)&&!"-1".equals(currentSys)){
                	sysMenuStr += ","+currentSys;
    				sysMenuMap.put(currentSys, true);
                }
            }
            
            
            Set<Role> roles = user.getRoles();
            Iterator<Role> roleIt = roles.iterator();
            int i = 0;
            while ( roleIt.hasNext() ) {
                Role role = roleIt.next();
                if ( role.getName().equals( "ROLE_ADMIN" ) ) {
                    flag = true;
                    break;
                }
                /*List<Privilege> privileges = privilegeManager.getPrivilegesByRole(role);
                Iterator<Privilege> privilegeIt = privileges.iterator();*/
                Set<Menu> menus = role.getMenus();
                for(Menu menu : menus){
                	/*if(rootMenuId.equals(menu.getSubSystem().getMenuId())&&(dogMenus.get(menu.getMenuId())==null?false:true)){
                		rightMenus.add(menu);
            		}*/
                	Boolean allowSubsys = sysMenuMap.get(menu.getSubSystem().getMenuId());
            		if(allowSubsys!=null&&allowSubsys){
	                	if((dogMenus.get(menu.getMenuId())==null?false:true)){
	                		rightMenus.add(menu);
	            		}
            		}
                	/*if(!menu.isDisabled()&&((dogMenuMap.get(menu.getMenuId())==null?false:true)||(dogMenuMap.get(menu.getFlagNodeInPath().getMenuId())==null?false:true))){
                		rightMenus.add(menu);
                	}*/
                	/*for(String rootId : subsystemIds){
                		if(menu.getSubSystem().getMenuId().equals(rootId)){
                			rightMenus.add(menu);
                			break;
                		}
                	}*/
            	}
                /*if(i==0){
                	for(Menu menu : menus){
                		rightMenus.add(menu);
                	}
                	//rightMenus.addAll(menus);
                	i++;
                }else{
                	for ( Menu menuItem : menus ) {
                    	rightMenus.add(menuItem);
                    }
                }*/
            }
            Iterator itr = null;
            if ( !flag ) {
                Set<Menu> menus = user.getMenus();
                for ( Menu menuItem : menus ) {
                	//rightMenus.add(menuItem);
                	/*if(!menuItem.isDisabled()&&((dogMenuMap.get(menuItem.getMenuId())==null?false:true)||(dogMenuMap.get(menuItem.getFlagNodeInPath().getMenuId())==null?false:true))){
                		rightMenus.add(menuItem);
                	}*/
                	/*if(rootMenuId.equals(menuItem.getSubSystem().getMenuId())&&(dogMenus.get(menuItem.getMenuId())==null?false:true)){
                		rightMenus.add(menuItem);
            		}*/
                	Boolean allowSubsys = sysMenuMap.get(menuItem.getSubSystem().getMenuId());
            		if(allowSubsys!=null&&allowSubsys){
	                	if((dogMenus.get(menuItem.getMenuId())==null?false:true)){
	                		rightMenus.add(menuItem);
	            		}
            		}
                }
                TreeSet<Menu> rightMenusCopy = new TreeSet<Menu>();
                for(Menu menu : rightMenus){
                	rightMenusCopy.add(menu);
                	Menu menuTemp = menu.getParent();
                	while(menuTemp!=null){
                		rightMenusCopy.add(menuTemp);
                		menuTemp = menuTemp.getParent();
                	}
                }
                itr = rightMenusCopy.iterator();
            }else{
            	List<Menu> enableMenus = menuManager.getAllEnabled();
            	TreeSet<Menu> rightMenusCopy = new TreeSet<Menu>();
            	for(Menu menu : enableMenus){
            		/*if(rootMenuId.equals(menu.getSubSystem().getMenuId())&&(dogMenus.get(menu.getMenuId())==null?false:true)){
            			rightMenusCopy.add(menu);
            		}*/
            		Boolean allowSubsys = sysMenuMap.get(menu.getSubSystem().getMenuId());
            		if(allowSubsys!=null&&allowSubsys){
	            		if((dogMenus.get(menu.getMenuId())==null?false:true)){
	            			rightMenusCopy.add(menu);
	            		}
            		}
            	}
            	itr = rightMenusCopy.iterator();
            }
        	
            List list = new ArrayList();
            while ( itr.hasNext() ) {
                subSystem = "";
                Menu menu = (Menu) itr.next();
                if((ContextUtil.versionType==1)&&menu.getParent()==null){
                	String menuName = menu.getMenuName();
                	menu.setMenuName(menuName+"（演示版）");
                }
                //TestTimer ttTestTimer = new TestTimer(menu.getMenuName());
               // ttTestTimer.begin();
                /*if ( !isHavePrivilege( menu ) || menu.isDisabled() ) {
                    continue;
                }*/
                if ( menu.isDisabled() ) {
                    continue;
                }
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
                    rt.setIcon( this.getContextPath() + iconPath + iconParentClose );
                }
                // 
                else if ( rt.getpId().equals( "" ) ) {
                    rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                    rt.setOpen( true );
                }
                else {
                    rt.setIcon( this.getContextPath() + iconPath + iconLeaf );
                    //rt.setActionUrl(menu.getMenuAction());
                }

                rt.setActionUrl( menu.getMenuAction() );
                list.add( rt );
                //ttTestTimer.done();
            }
            rootMenusString = JSONArray.fromObject( list ).toString();
           // this.log.error(  rootMenusString );
            HttpServletResponse response = ServletActionContext.getResponse();
    		
    		response.setCharacterEncoding("UTF-8");
    			response.getWriter().write(rootMenusString);
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }
    public String getAllRootMenu11() {
        String iconLeaf = "leaf.png";
        String iconRoot = "jx_sys.png";
        String iconParentClose = "folder.png";
        String iconParentOpen = "folder.png";
        String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";//logo.jpg

        try {
            List<Menu> allRootMenus = this.menuManager.getAllRootMenu();
            menus = new ArrayList<Menu>();
            //HaspDogHandler haspDogHandler = new HaspDogHandler();
            String[] subsystemIds = HaspDogHandler.getInstance().getDogService().getAllowedSubSystem();
            
            for ( String subsystemId : subsystemIds ) {
                for ( Menu menu : allRootMenus ) {
                    if ( menu.getMenuId().equals( subsystemId ) ) {
                        menus.add( menu );
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
            List list = new ArrayList();

            Iterator itr = menus.iterator();
            while ( itr.hasNext() ) {
                subSystem = "";
                Menu menu = (Menu) itr.next();
                if ( !isHavePrivilege( menu ) || menu.isDisabled() ) {
                    continue;
                }
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
                    rt.setIcon( this.getContextPath() + iconPath + iconParentClose );
                }
                // 
                else if ( rt.getpId().equals( "" ) ) {
                    rt.setIcon( this.getContextPath() + iconPath + menu.getIconName() );
                    rt.setOpen( true );
                }
                else {
                    rt.setIcon( this.getContextPath() + iconPath + iconLeaf );
                    //rt.setActionUrl(menu.getMenuAction());
                }

                rt.setActionUrl( menu.getMenuAction() );
                makeTree( menu.getMenuId() );
                rt.setSubSysTem( subSystem );
                list.add( rt );
            }
            rootMenusString = JSONArray.fromObject( list ).toString();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public void makeTree( String menuid ) {
        String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        //themeName = (String)this.getRequest().getAttribute("themeName");
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";//logo.jpg

        int i = 1;
        findTree( menuid, i );
    }

    public void findTree( String fId, int i ) {
        List menus = this.menuManager.getAllByParent( fId );
        Iterator itr = menus.iterator();
        if ( i == 1 ) {
            subSystem += "<ul class='tree treeFolder'>";
        }
        else {
            subSystem += "<ul>";
        }
        i++;
        while ( itr.hasNext() ) {
            Menu menu = (Menu) itr.next();
            /*if ( menu.getMenuName().equals( "财务成本归集" ) ) {
                System.err.println();
            }*/
            if ( menu.getParent().getMenuId().equals( fId ) && menu.isDisabled() == false && isHavePrivilege( menu ) ) {
                String url = "/#";
                if ( menu.isLeaf() ) {
                    url = "/menuClicked?menuId=" + menu.getMenuId();
                    subSystem +=
                        "<li><a name='treeHref' href='" + url + "' target='navTab' onclick='menuClick(\"" + menu.getMenuId() + "\")' rel='navTab"
                            + menu.getMenuId() + "'>" + menu.getMenuName() + "</a>";
                }
                else {
                    subSystem += "<li><a href='javaScript:;' onclick='menuClick()'>" + menu.getMenuName() + "</a>";
                }

                if ( menu.isLeaf() ) {
                    subSystem += "</li>";
                }
                else {
                    findTree( menu.getMenuId(), i );
                }
            }
        }
        subSystem += "</ul>";
    }

    private boolean isHavePrivilege( Menu menu ) {
        boolean flag = false;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        User user = userManager.getUserByUsername( username );
        Set<Role> roles = user.getRoles();
        Iterator<Role> roleIt = roles.iterator();
        while ( roleIt.hasNext() ) {
            Role role = roleIt.next();
            if ( role.getName().equals( "ROLE_ADMIN" ) ) {
                flag = true;
                break;
            }
            /*List<Privilege> privileges = privilegeManager.getPrivilegesByRole(role);
            Iterator<Privilege> privilegeIt = privileges.iterator();*/
            Set<Menu> menus = role.getMenus();
            Iterator<Menu> privilegeIt = menus.iterator();
            for ( Menu menuItem : menus ) {
                if ( menuItem.getMenuId().equals( menu.getMenuId() ) ) {
                    flag = true;
                    break;
                }
            }
        }

        if ( !flag ) {
            Set<Menu> menus = user.getMenus();
            Iterator<Menu> privilegeIt = menus.iterator();
            for ( Menu menuItem : menus ) {
                if ( menuItem.getMenuId().equals( menu.getMenuId() ) ) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    public String moduleFlag() {
		try {
			List<Menu> menuList =  menuManager.getAll();
			for(Menu menu : menuList){
				if(menu.getFlag()){
					moduleFlag += menu.getMenuId()+",";
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
    
    public String saveModuleFlag(){
    	try {
			if(!("").equals(moduleFlag)){
				String[] moduleFlagArr = moduleFlag.split(",");
				for(String menuId : moduleFlagArr){
					Menu menu = menuManager.get(menuId);
					menu.setFlag(true);
					menuManager.save(menu);
				}
				return ajaxForward(true, "设置成功。",true);
			}else{
				return ajaxForward(true, "",false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "设置失败。",false);
		}
    }
    
    public String allMenuTree(){
    	try {
    		List menuList = null;
    		menuList = this.nodeListWithCheck(null);
    		if ( menuList != null ) {
                this.menuTreeListString = JSONArray.fromObject( menuList ).toString();
                HttpServletResponse response = ServletActionContext.getResponse();

                response.setCharacterEncoding( "UTF-8" );
                response.getWriter().write( menuTreeListString );
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    private List nodeListWithCheck( String id ) {
        String themeName = (String) this.getRequest().getSession().getAttribute( "themeName" );
        //themeName = (String)this.getRequest().getAttribute("themeName");
        String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";//logo.jpg

        List menus = null;
        if ( id == null )
            menus = this.menuManager.getAll();
        else {
            Menu paraent = this.menuManager.get( id );
            if ( !paraent.isDisabled() ) {
                menus = this.menuManager.getAllByParent( id );
                menus.add( this.menuManager.get( id ) );
            }
            else {
                return null;
            }

        }

        List list = new ArrayList();

        Iterator itr = menus.iterator();

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

        return list;
    }
    
    public PeriodManager getPeriodManager() {
        return periodManager;
    }

    public void setPeriodManager( PeriodManager periodManager ) {
        this.periodManager = periodManager;
    }

    public int getNavTabNum() {
        return navTabNum;
    }

    public void setNavTabNum( int navTabNum ) {
        this.navTabNum = navTabNum;
    }

    public BulletinManager getBulletinManager() {
        return bulletinManager;
    }

    public void setBulletinManager( BulletinManager bulletinManager ) {
        this.bulletinManager = bulletinManager;
    }

    public List getBulletinList() {
        return bulletinList;
    }

    public void setBulletinList( List bulletinList ) {
        this.bulletinList = bulletinList;
    }

    public ByLawManager getByLawManager() {
        return byLawManager;
    }

    public void setByLawManager( ByLawManager byLawManager ) {
        this.byLawManager = byLawManager;
    }

    public List<ByLaw> getByLawList() {
        return byLawList;
    }

    public void setByLawList( List<ByLaw> byLawList ) {
        this.byLawList = byLawList;
    }

	public String getLeftMenuType() {
		return leftMenuType;
	}

	public void setLeftMenuType(String leftMenuType) {
		this.leftMenuType = leftMenuType;
	}

}