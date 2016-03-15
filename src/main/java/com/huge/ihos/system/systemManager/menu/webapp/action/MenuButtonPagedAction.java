package com.huge.ihos.system.systemManager.menu.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;
//import com.huge.ihos.menu.service.MenuButtonManager;




@SuppressWarnings("serial")
public class MenuButtonPagedAction extends JqGridBaseAction implements Preparable {

//	private MenuButtonManager menuButtonManager;
	private List<MenuButton> menuButtons;
	private MenuButton menuButton;
	private String id;
	private String menuId;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

//	public void setMenuButtonManager(MenuButtonManager menuButtonManager) {
//		this.menuButtonManager = menuButtonManager;
//	}

	public List<MenuButton> getmenuButtons() {
		return menuButtons;
	}

	public void setMenuButtons(List<MenuButton> menuButtons) {
		this.menuButtons = menuButtons;
	}

	public MenuButton getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(MenuButton menuButton) {
		this.menuButton = menuButton;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String menuButtonGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if(menuId!=null){
				List<Menu> menuList = menuManager.getAllDescendants(menuId);
				String menuIds = menuId;
				for(Menu menu:menuList){
					menuIds += ","+menu.getMenuId();
				}
				filters.add(new PropertyFilter("INS_menuId",menuIds));
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = menuButtonManager
					.getMenuButtonCriteria(pagedRequests,filters);
			this.menuButtons = (List<MenuButton>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			menuButtonManager.save(menuButton);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "menuButton.added" : "menuButton.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	menuButton = menuButtonManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	menuButton = new MenuButton();
        	menuButton.setMenuId(menuId);
        	Long buttonSn = menuButtonManager.createNextButtonSn(menuId);
        	String buttonId = menuId+(buttonSn<10?"0"+buttonSn:buttonSn);
        	menuButton.setId(buttonId);
        	menuButton.setOorder(new Long(buttonSn));
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String menuButtonGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.menuButtonManager.remove(ida);
				gridOperationMessage = this.getText("menuButton.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkMenuButtonGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private List<ZTreeSimpleNode> menuNodes;
	private MenuManager menuManager;
	
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public List<ZTreeSimpleNode> getMenuNodes() {
		return menuNodes;
	}

	public String makeMenuTree(){
		try {
			menuNodes = new ArrayList<ZTreeSimpleNode>();
			ZTreeSimpleNode node = null;
			List<Menu> menuList = menuManager.getAll();
			if(menuList!=null && !menuList.isEmpty()){
				for(Menu menu:menuList){
					node = new ZTreeSimpleNode();
					node.setId(menu.getMenuId());
					node.setName(menu.getMenuName()+"("+menu.getMenuId()+")");
					node.setIsParent(!menu.isLeaf());
					node.setpId(menu.getParent()!=null?menu.getParent().getMenuId():"-1");
//					node.setIcon(icon);
					menuNodes.add(node);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String isValid() {
		if (menuButton == null) {
			return "Invalid menuButton Data";
		}

		return SUCCESS;

	}
}

