package com.huge.ihos.system.systemManager.menu.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.util.HaspDogHandler;
import com.huge.webapp.action.BaseAction;

public class MenuDoubleSelectAction
    extends BaseAction
     {
    private MenuManager menuManager;

    private List subSystems;

    private List subMenus;

    private String rootId;

    private String rootIdFirst;

    private String menuId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId( String menuId ) {
        this.menuId = menuId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId( String rootId ) {
        this.rootId = rootId;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager( MenuManager menuManager ) {
        this.menuManager = menuManager;
    }

    public List getSubSystems() {
        return subSystems;
    }

    public void setSubSystems( List subSystems ) {
        this.subSystems = subSystems;
    }

    public List getSubMenus() {
        return subMenus;
    }

    public void setSubMenus( List subMenus ) {
        this.subMenus = subMenus;
    }

    public String menuDoubleSelect() {
    	try {
    		Map<String,Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();
        	List<Menu> subsystems = this.menuManager.getAllSubSystem();
            List<Menu> subMenus = new ArrayList();
            if ( this.rootId != null && !rootId.trim().equalsIgnoreCase( "" ) )
            	subMenus = this.menuManager.getAllNotLeafByRoot( rootId );
            else if ( this.menuId != null && !menuId.trim().equalsIgnoreCase( "" ) ) {
            	subMenus = this.menuManager.getAllNotLeafByRoot( menuId );
            }
            else if ( this.rootIdFirst != null && !rootIdFirst.trim().equalsIgnoreCase( "" ) ) {
            	subMenus = this.menuManager.getAllNotLeafByRoot( rootIdFirst );
            }
            
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
		} catch (Exception e) {
			e.printStackTrace();
		}
        return SUCCESS;
    }

    public String getRootIdFirst() {
        return rootIdFirst;
    }

    public void setRootIdFirst( String rootIdFirst ) {
        this.rootIdFirst = rootIdFirst;
    }
}
