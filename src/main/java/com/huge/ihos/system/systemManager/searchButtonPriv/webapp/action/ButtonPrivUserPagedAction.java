package com.huge.ihos.system.systemManager.searchButtonPriv.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPriv;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUser;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivManager;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivUserManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;


public class ButtonPrivUserPagedAction extends JqGridBaseAction implements Preparable {

	private ButtonPrivUserManager buttonPrivUserManager;
	private List<ButtonPrivUser> buttonPrivUsers;
	private ButtonPrivUser buttonPrivUser;
	private String privId;
	private String userId;
	private SearchManager searchManager;
	private ButtonPrivManager buttonPrivManager;
	private String buttonType;
	private MenuManager menuManager;
	
	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public void setButtonPrivManager(
			ButtonPrivManager buttonPrivManager) {
		this.buttonPrivManager = buttonPrivManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setButtonPrivUserManager(ButtonPrivUserManager buttonPrivUserManager) {
		this.buttonPrivUserManager = buttonPrivUserManager;
	}

	public List<ButtonPrivUser> getButtonPrivUsers() {
		return buttonPrivUsers;
	}

	public void setButtonPrivUsers(List<ButtonPrivUser> buttonPrivUsers) {
		this.buttonPrivUsers = buttonPrivUsers;
	}

	public ButtonPrivUser getButtonPrivUser() {
		return buttonPrivUser;
	}

	public void setButtonPrivUser(ButtonPrivUser buttonPrivUser) {
		this.buttonPrivUser = buttonPrivUser;
	}

	public String getPrivId() {
		return privId;
	}

	public void setPrivId(String privId) {
        this.privId = privId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String buttonPrivUserGridList() {
		log.debug("enter list method!");
		try {
			List<ButtonPrivUser> realList = null;
			if(userId!=null && buttonType!=null){
//				List<ButtonPrivUser> userSBPUS = buttonPrivUserManager.findByUser(userId);
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_userId",userId));
				filters.add(new PropertyFilter("EQS_buttonType",buttonType));
				List<ButtonPrivUser> userBPUS = buttonPrivUserManager.getByFilters(filters);
				if(userBPUS==null || userBPUS.size()==0){
					userBPUS = new ArrayList<ButtonPrivUser>();
				}
				List<ButtonPrivUser> roleSBPUS = this.getButtonPrivsInUserRole(userId,buttonType);
				if(roleSBPUS!=null){
					userBPUS.addAll(roleSBPUS);
				}
				
				Set<ButtonPrivUser> bpTreeSet = new TreeSet<ButtonPrivUser>();
				for(ButtonPrivUser sbpu:userBPUS){
					bpTreeSet.add(sbpu);
				}
				realList = new ArrayList<ButtonPrivUser>(bpTreeSet);
				
			}
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			if(realList!=null){
				pagedRequests.setTotalNumberOfRows(realList.size());
				int endRow = pagedRequests.getStart() + pagedRequests.getPageSize();
				if ( endRow > realList.size() ) {
	                endRow = realList.size();
	            }
				realList = realList.subList( pagedRequests.getStart(), endRow );
	            pagedRequests.setList( realList );
			}
			this.buttonPrivUsers = (List<ButtonPrivUser>)pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	/**
	 * 获取从属于当前用户所属角色的buttonPriv集合，并转型为buttonPrivUser集合
	 * @param userId
	 * @return
	 */
	private List<ButtonPrivUser> getButtonPrivsInUserRole(String userId,String buttonType){
		List<ButtonPrivUser> buttonPrivUsers = null;
		Set<Role> roles = userManager.get(Long.parseLong(userId)).getRoles();
		int roleSize = roles.size();
		Map<String,Integer> roleBPM = new HashMap<String,Integer>();
		List<PropertyFilter> filters = null;
		String roleSearchOrMenuId = null;
		for(Role role:roles){
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_buttonType",buttonType));
			filters.add(new PropertyFilter("EQS_roleId",""+role.getId()));
			List<ButtonPriv> sbps = buttonPrivManager.getByFilters(filters);
			for(ButtonPriv sbp:sbps){
				roleSearchOrMenuId = sbp.getSearchOrMenuId();
				if(roleBPM.get(roleSearchOrMenuId)==null){
					roleBPM.put(roleSearchOrMenuId, 1);
				}else{
					roleBPM.put(roleSearchOrMenuId, roleBPM.get(roleSearchOrMenuId)+1);
				}
			}
		}
		Iterator<Entry<String,Integer>> ite = roleBPM.entrySet().iterator();
		Entry<String,Integer> entry = null;
		int entryValue = -1;
		while(ite.hasNext()){
			entry = ite.next();
			entryValue = entry.getValue();
			if(entryValue!=roleSize){
				ite.remove();
			}
		}
		Set<String> roleSearchOrMenuIds = roleBPM.keySet();
		if(roleSearchOrMenuIds!=null && roleSearchOrMenuIds.size()>0){
			buttonPrivUsers = new ArrayList<ButtonPrivUser>();
			ButtonPrivUser sbpu = null;
			if(buttonType.equals("1")){
				Search search = null;
				for(String searchId:roleSearchOrMenuIds){
					search = searchManager.get(searchId);
					sbpu = new ButtonPrivUser();
					sbpu.setSearchOrMenuId(searchId);
					sbpu.setSearchName(search.getSearchName());
					sbpu.setLabel(search.getTitle());
					sbpu.setUserId(userId);
					sbpu.setPrivId(OtherUtil.getRandomUUID());
//					sbpu.setSearchButtonPrivUserDetails(null);
					buttonPrivUsers.add(sbpu);
				}
			}else if(buttonType.equals("2")){
				Menu menu = null;
				for(String menuId:roleSearchOrMenuIds){
					menu = menuManager.get(menuId);
					sbpu = new ButtonPrivUser();
					sbpu.setSearchOrMenuId(menuId);
//					sbpu.setSearchName();
					sbpu.setLabel(menu.getMenuName().trim());
					sbpu.setUserId(userId);
					sbpu.setPrivId(OtherUtil.getRandomUUID());
//					sbpu.setSearchButtonPrivUserDetails(null);
					buttonPrivUsers.add(sbpu);
				}
			}
			
		}
		return buttonPrivUsers;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			buttonPrivUserManager.save(buttonPrivUser);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "searchButtonPrivUser.added" : "searchButtonPrivUser.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (privId != null) {
        	buttonPrivUser = buttonPrivUserManager.get(privId);
        	this.setEntityIsNew(false);
        } else {
        	buttonPrivUser = new ButtonPrivUser();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String buttonPrivUserGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.buttonPrivUserManager.remove(ida);
				gridOperationMessage = this.getText("searchButtonPrivUser.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("buttonPrivUserGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (buttonPrivUser == null) {
			return "Invalid buttonPrivUser Data";
		}

		return SUCCESS;

	}
}

