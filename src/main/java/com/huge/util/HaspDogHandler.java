package com.huge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huge.ihos.hasp.service.DogService;
import com.huge.ihos.hasp.service.impl.DummyDogManager;
import com.huge.ihos.hasp.service.impl.HaspDogManager;
import com.huge.ihos.multidatasource.DynamicSessionFactoryHolder;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;

public class HaspDogHandler {

    private static DogService dogService = null;

    private static HaspDogHandler haspDogHandler = null;
    private static MenuManager menuManager;
    private static String[] subsystemIds ;
    public static MenuManager getMenuManager() {
		return menuManager;
	}

	public static void setMenuManager(MenuManager menuManager) {
		HaspDogHandler.menuManager = menuManager;
	}

	private static Map<String,Boolean> dogMenus;
	
	private static Map<String,Map<String,Boolean>> dogMenusMap = new HashMap<String,Map<String,Boolean>>();
    //private static Dog
    public HaspDogHandler(){
    	try {
			subsystemIds = getDogService().getAllowedSubSystem();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public static HaspDogHandler getInstance(){
    	if(haspDogHandler==null){
    		haspDogHandler = new HaspDogHandler();
    	}
    	return haspDogHandler;
    }
    
    public static HaspDogHandler getInstance(MenuManager menuManager){
    	if(haspDogHandler==null){
    		haspDogHandler = new HaspDogHandler();
    		setMenuManager(menuManager);
    		makeDogMenus();
    	}else{
    		Map<String,Boolean> dogMenus = dogMenusMap.get(DynamicSessionFactoryHolder.getSessionFactoryName());
    		if(dogMenus==null){
    			setMenuManager(menuManager);
    			makeDogMenus();
    		}
    	}
    	return haspDogHandler;
    }

	public static DogService getDogService() throws Exception {

		if (dogService != null)
			return dogService;
		else {

			String dogType = System.getProperty("dogType");
			dogType = "dummyDog";

			try {
				if (dogType != null && dogType.equalsIgnoreCase("dummyDog")) {
					dogService = new DummyDogManager();
				} else {

					dogService = new HaspDogManager();
				}
				// subsystemIds = dogService.getAllowedSubSystem();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw e;
			}
			return dogService;
		}
	}
    
    public static void makeDogMenus(){
    	try {
            System.out.println(DynamicSessionFactoryHolder.getSessionFactoryName()+"_dogAllowMenus:"+subsystemIds.length);
            Map<String, Boolean> dogMenuMap = new HashMap<String, Boolean>();
            for ( String subsystemId : subsystemIds ) {
            	dogMenuMap.put(subsystemId, true);
            }
            List<Menu> rootMenu = menuManager.getAllRootMenu();
            Map<String,Boolean> dogMenusTemp = new HashMap<String, Boolean>();
            for(Menu menu : rootMenu){
            	List<Menu> pathStack = new ArrayList<Menu>();
            	traversalTree(menu ,pathStack,dogMenusTemp ,false ,dogMenuMap);
            }
            dogMenusMap.put(DynamicSessionFactoryHolder.getSessionFactoryName(), dogMenusTemp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    }
    
    public static void addDogMenu(String menuId){
    	Map<String,Boolean> dogMenus = dogMenusMap.get(DynamicSessionFactoryHolder.getSessionFactoryName());
    	boolean exit = dogMenus.get(menuId)==null?false:true;
    	if(!exit){
    		dogMenus.put(menuId, true);
    	}
    }
    
    private static void traversalTree(Menu parent,List<Menu> pathStack , Map<String,Boolean> menus , boolean isShow,Map<String, Boolean> dogMenuMap){
    	/*System.out.println("isShow:"+isShow);
    	System.out.println("menus:"+menus.keySet().toString());
    	System.out.println("pathStack:"+pathStack.toString());*/
    	//System.out.println("menus:"+menus.keySet().toString());
    	pathStack.add(parent);
    	boolean showCharge = isShow==true?true:dogMenuMap.get(parent.getMenuId())==null?false:true;
    	//List<Menu> menusTemp = menuManager.getByParent( parent.getMenuId() );
    	List<Menu> menusTemp = menuManager.getAllByParent( parent.getMenuId() );
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
    
    public static Map<String,Boolean> getDogMenus(){
    	dogMenus = dogMenusMap.get(DynamicSessionFactoryHolder.getSessionFactoryName());
    	if(dogMenus==null){
    		makeDogMenus();
    		dogMenus = dogMenusMap.get(DynamicSessionFactoryHolder.getSessionFactoryName());
    	}
    	return dogMenus;
    }
    /*public String[] getAllowedSubSystem() {
        String[] subsystemIds = null;
        try {
            if ( true ) {
                dogService = new DummyDogManager();
            }
            else {
                dogService = new HaspDogManager();
            }
            subsystemIds = dogService.getAllowedSubSystem();
        }
        catch ( Exception e ) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return subsystemIds;

    /*
     * public String[] getAllowedSubSystem() { String[] subsystemIds = null; try { if ( true ) { dogService = new
     * DummyDogManager(); } else { dogService = new HaspDogManager(); } subsystemIds = dogService.getAllowedSubSystem();
     * } catch ( Exception e ) { // TODO: handle exception e.printStackTrace(); } return subsystemIds; }
     */

}
