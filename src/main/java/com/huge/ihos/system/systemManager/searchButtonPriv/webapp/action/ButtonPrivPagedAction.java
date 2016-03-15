package com.huge.ihos.system.systemManager.searchButtonPriv.webapp.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.service.SearchManager;
import com.huge.ihos.system.reportManager.search.service.SearchUrlManager;
import com.huge.ihos.system.systemManager.menu.model.Menu;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.menu.service.MenuButtonManager;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPriv;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUser;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivManager;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivUserManager;
import com.huge.util.HaspDogHandler;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;

public class ButtonPrivPagedAction extends JqGridBaseAction implements
		Preparable {

	private ButtonPrivManager buttonPrivManager;
	private List<ButtonPriv> buttonPrivs;
	private ButtonPriv buttonPriv;
	private String privId;
	private String roleId;
	private SearchManager searchManager;
	private SearchUrlManager searchUrlManager;
	private String userId;
	private ButtonPrivUserManager buttonPrivUserManager;
	private String buttonType;
	private MenuManager menuManager;
	private MenuButtonManager menuButtonManager;

	public void setMenuButtonManager(MenuButtonManager menuButtonManager) {
		this.menuButtonManager = menuButtonManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public void setButtonPrivUserManager(
			ButtonPrivUserManager buttonPrivUserManager) {
		this.buttonPrivUserManager = buttonPrivUserManager;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setSearchUrlManager(SearchUrlManager searchUrlManager) {
		this.searchUrlManager = searchUrlManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setButtonPrivManager(ButtonPrivManager buttonPrivManager) {
		this.buttonPrivManager = buttonPrivManager;
	}

	public List<ButtonPriv> getButtonPrivs() {
		return buttonPrivs;
	}

	public void setButtonPrivs(List<ButtonPriv> buttonPrivs) {
		this.buttonPrivs = buttonPrivs;
	}

	public ButtonPriv getButtonPriv() {
		return buttonPriv;
	}

	public void setButtonPriv(ButtonPriv buttonPriv) {
		this.buttonPriv = buttonPriv;
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
	
	private String iconParentClose = "folder.png";
	private String iconLeaf = "leaf.png";
	private String noSubIcon = "3.png";

	public String buttonPrivGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			PropertyFilter rolePropertyFilter = new PropertyFilter(
					"EQS_roleId", roleId);
			filters.add(rolePropertyFilter);
			filters.add(new PropertyFilter("EQS_buttonType", buttonType));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = buttonPrivManager.getButtonPrivCriteria(
					pagedRequests, filters);
			this.buttonPrivs = (List<ButtonPriv>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			buttonPrivManager.save(buttonPriv);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "searchButtonPriv.added"
				: "searchButtonPriv.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (privId != null) {
			buttonPriv = buttonPrivManager.get(privId);
			this.setEntityIsNew(false);
		} else {
			buttonPriv = new ButtonPriv();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String buttonPrivGridEdit() {
		try {
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					// Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);

				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.buttonPrivManager.remove(ida);
				gridOperationMessage = this.getText("searchButtonPriv.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkButtonPrivGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String content;
	private String contentUser;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentUser() {
		return contentUser;
	}

	public void setContentUser(String contentUser) {
		this.contentUser = contentUser;
	}

	public String selectButtonPrivs() {
		if (userId != null) {
			roleId = "";
			List<ButtonPrivUser> buttonPrivUsers = buttonPrivUserManager
					.findByUser(userId);
			Set<ButtonPrivUserDetail> bpuds = null;
			contentUser = "";
			for (ButtonPrivUser buttonPrivUser : buttonPrivUsers) {
				contentUser += buttonPrivUser.getSearchOrMenuId() + ",";
				bpuds = buttonPrivUser.getButtonPrivUserDetails();
				for (ButtonPrivUserDetail sbpud : bpuds) {
					contentUser += buttonPrivUser.getSearchOrMenuId()
							+ "~" + sbpud.getButtonId() + ",";
				}
			}
			Set<Role> roles = userManager.get(Long.parseLong(userId))
					.getRoles();
			for (Role role : roles) {
				roleId += "" + role.getId() + ",";
			}
			roleId = OtherUtil.subStrEnd(roleId, ",");
		}
		if (roleId != null) {
			Set<ButtonPrivDetail> sbpds = null;
			content = "";
			if (roleId.indexOf(",") > 0) {
				Map<String, Integer> sbpdm = new HashMap<String, Integer>();
				String[] roleIds = roleId.split(",");
				int roleSize = roleIds.length;
				String searchOrMenuId = "", buttonId = "";
				for (int i = 0; i < roleSize; i++) {
					buttonPrivs = buttonPrivManager.findByRole(roleIds[i]);
					for (ButtonPriv searchButtonPriv : buttonPrivs) {
						searchOrMenuId = searchButtonPriv.getSearchOrMenuId();
						if (sbpdm.get(searchOrMenuId) == null) {
							sbpdm.put(searchOrMenuId, 1);
						} else {
							sbpdm.put(searchOrMenuId, sbpdm.get(searchOrMenuId) + 1);
						}
						sbpds = searchButtonPriv.getButtonPrivDetails();
						for (ButtonPrivDetail sbpd : sbpds) {
							buttonId = searchOrMenuId + "~"
									+ sbpd.getButtonId();
							if (sbpdm.get(buttonId) == null) {
								sbpdm.put(buttonId, 1);
							} else {
								sbpdm.put(buttonId,
										sbpdm.get(buttonId) + 1);
							}
						}
					}
				}
				Iterator<Entry<String, Integer>> ite = sbpdm.entrySet()
						.iterator();
				while (ite.hasNext()) {
					Entry<String, Integer> entry = ite.next();
					int value = entry.getValue();
					if (value != roleSize) {
						ite.remove();
					}
				}
				Set<String> surls = sbpdm.keySet();
				content = surls.toString();
				content = content.substring(1, content.length() - 1);
				content = content.replaceAll(" +", "");
			} else {
				buttonPrivs = buttonPrivManager.findByRole(roleId);
				for (ButtonPriv buttonPriv : buttonPrivs) {
					content += buttonPriv.getSearchOrMenuId() + ",";
					sbpds = buttonPriv.getButtonPrivDetails();
					for (ButtonPrivDetail sbpd : sbpds) {
						content += buttonPriv.getSearchOrMenuId() + "~"
								+ sbpd.getButtonId() + ",";
					}
				}
			}
		}
		this.setRandom(this.getRequest().getParameter("random"));
		return SUCCESS;
	}

	private List<ZTreeSimpleNode> buttonPrivTreeList;

	public List<ZTreeSimpleNode> getButtonPrivTreeList() {
		return buttonPrivTreeList;
	}
	
	public String makeButtonTree(){
		try {
			if(buttonType.equals("1")){
				makeSearchButtonTree();
			}else if(buttonType.equals("2")){
				makeMenuButtonTree();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String makeSearchButtonTree() {
		try {
			buttonPrivTreeList = new ArrayList<ZTreeSimpleNode>();
			String searchName = this.getRequest().getParameter("searchOrMenuName");
			List<Search> searchs = null;
			if (searchName != null && !"".equals(searchName.trim())) {
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("LIKES_title", searchName));
				searchs = searchManager.getByFilters(filters);
			} else {
				searchs = searchManager.getAllExceptDisable();
			}
			List<Search> subSearchs = null;
			String subSystemName = null;
			final String noSub = "未分类";
			Map<String, List<Search>> map = new HashMap<String, List<Search>>();
			for (Search search : searchs) {
				subSystemName = search.getSubSystem();
				subSearchs = new ArrayList<Search>();
				if (OtherUtil.measureNull(subSystemName)) {
					if (map.get(noSub) == null) {
						subSearchs.add(search);
						map.put(noSub, subSearchs);
					} else {
						map.get(noSub).add(search);
					}
				} else {
					if (map.get(subSystemName) == null) {
						subSearchs.add(search);
						map.put(subSystemName, subSearchs);
					} else {
						map.get(subSystemName).add(search);
					}
				}
			}
			Iterator<Entry<String, List<Search>>> ite = map.entrySet()
					.iterator();
			Entry<String, List<Search>> entry = null;
			String key = null;
			Set<SearchUrl> searchUrls = null;
			ZTreeSimpleNode searchNode = null, searchUrlNode = null, searchSubNode = null;
			String themeName = (String) this.getRequest().getSession()
					.getAttribute("themeName");
			String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";
			while (ite.hasNext()) {// 子系统层
				entry = ite.next();
				key = entry.getKey();
				searchSubNode = new ZTreeSimpleNode();
				searchSubNode.setName(key);
				searchSubNode.setIsParent(true);
				searchSubNode.setOpen(false);
				searchSubNode.setpId("");
				searchSubNode.setId(OtherUtil.getRandomUUID());
				if(key.equals(noSub)){
					searchSubNode.setIcon(this.getContextPath() + iconPath+ this.noSubIcon);
				}else{
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_menuName", key));
					Menu menu = menuManager.getByFilters(filters).get(0);
					searchSubNode.setIcon(this.getContextPath() + iconPath+ menu.getIconName());
				}
				
				buttonPrivTreeList.add(searchSubNode);

				subSearchs = entry.getValue();
				for (Search search : subSearchs) {// search层
					searchUrls = search.getSearchurls();
					if (searchUrls.size() >= 1) {
						searchNode = new ZTreeSimpleNode();
						searchNode.setName(search.getTitle());
						searchNode.setId(search.getSearchId());
						searchNode.setIsParent(true);
						searchNode.setOpen(false);
						searchNode.setpId(searchSubNode.getId());
						searchNode.setIcon(this.getContextPath() + iconPath+ this.iconParentClose);
						buttonPrivTreeList.add(searchNode);

						for (SearchUrl searchUrl : searchUrls) {// searchButton层
							searchUrlNode = new ZTreeSimpleNode();
							searchUrlNode.setName(searchUrl.getTitle());
							searchUrlNode.setId(search.getSearchId() + "~"
									+ searchUrl.getSearchUrlId());
							searchUrlNode.setIsParent(false);
							searchUrlNode.setpId(searchNode.getId());
							searchUrlNode.setIcon(this.getContextPath() + iconPath+ this.iconLeaf);
							buttonPrivTreeList.add(searchUrlNode);
						}
					}
				}
			}
			Collections.sort(buttonPrivTreeList,new Comparator<ZTreeSimpleNode>(){
				@Override
				public int compare(ZTreeSimpleNode o1, ZTreeSimpleNode o2) {
					if(o1.getName().equals(noSub) && !o2.getName().equals(noSub)){
						return 1;
					}else if(!o1.getName().equals(noSub) && o2.getName().equals(noSub)){
						return -1;
					}else{
						return 0;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	private String makeMenuButtonTree() {
		try {
			String menuName = this.getRequest().getParameter("searchOrMenuName");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			List<Menu> searchMenus = null;
			String[] menuIds = null;
			if(menuName!=null && !"".equals(menuName)){
				filters.add(new PropertyFilter("LIKES_menuName", menuName));
				searchMenus = menuManager.getByFilters(filters);
				menuIds = menuButtonManager.getDistinctMenuIds(searchMenus);
			}else{
				menuIds = menuButtonManager.getDistinctMenuIds("all");
			}
			
			if (menuIds == null || menuIds.length == 0) {
				return SUCCESS;
			}
			String mIds = Arrays.toString(menuIds);
			mIds = mIds.substring(1, mIds.length() - 1).replaceAll(" +", "");
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("INS_menuId", mIds));
			List<Menu> menus = menuManager.getByFilters(filters);// 叶子menu集合
			Set<Menu> treeMenus = new HashSet<Menu>();
			for (Menu menu : menus) {
				if (!menu.isLeaf()) {// 不是叶子menu
					continue;
				} else {
					Menu parent = menu.getParent();
					if (parent != null) {
						Set<Menu> menuChain = new HashSet<Menu>();
						Menu m = menu;
						while (m != null) {
							menuChain.add(m);
							if (m.getParent() != null)
								m = m.getParent();
							else
								m = null;
						}
						treeMenus.addAll(menuChain);
					}
				}
			}
			Map<String, Boolean> dogMenus = HaspDogHandler.getInstance().getDogMenus();
			List<Menu> menusShow = new ArrayList<Menu>();
			for (Menu menu : treeMenus) {
				if ((dogMenus.get(menu.getMenuId()) == null ? false : true)) {
					menusShow.add(menu);
				}
			}
			buttonPrivTreeList = getMenuButtonTreeList(menusShow);
			Collections.sort(buttonPrivTreeList,new Comparator<ZTreeSimpleNode>(){
				@Override
				public int compare(ZTreeSimpleNode o1, ZTreeSimpleNode o2) {
					return o1.getId().compareTo(o2.getId());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private List<ZTreeSimpleNode> getMenuButtonTreeList(List<Menu> menus) {
		List<ZTreeSimpleNode> nodeList = new ArrayList<ZTreeSimpleNode>();
		Iterator<Menu> menuIt = menus.iterator();
		String themeName = (String) this.getRequest().getSession()
				.getAttribute("themeName");
		String iconPath = "/styles/themes/" + themeName + "/ihos_images/tree/";
		Menu menu = null;
		ZTreeSimpleNode menuNode = null, buttonNode = null;
		List<PropertyFilter> filters = null;
		List<MenuButton> menuButtons = null;
		while (menuIt.hasNext()) {
			menu = (Menu) menuIt.next();
			menuNode = new ZTreeSimpleNode();
			menuNode.setName(menu.getMenuName());
			menuNode.setId(menu.getMenuId());
			menuNode.setIsParent(true);
			if (menu.getParent() == null) {
				menuNode.setpId("");
			} else {
				menuNode.setpId(menu.getParent().getMenuId());
			}
			if (menuNode.getIsParent() && menuNode.getpId().equals("")) {
				menuNode.setIcon(this.getContextPath() + iconPath
						+ menu.getIconName());
				menuNode.setOpen(false);
			} else if (menuNode.getIsParent() && !menuNode.getpId().equals("")) {
				menuNode.setIcon(this.getContextPath() + iconPath
						+ this.iconParentClose);
			} else if (menuNode.getpId().equals("")) {
				menuNode.setIcon(this.getContextPath() + iconPath
						+ menu.getIconName());
				menuNode.setOpen(false);
			} else {
				menuNode.setIcon(this.getContextPath() + iconPath
						+ this.iconLeaf);
			}
			nodeList.add(menuNode);
			if (menu.isLeaf()) {
				filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_menuId", menu.getMenuId()));
				menuButtons = menuButtonManager.getByFilters(filters);
				if (menuButtons == null) {
					continue;
				} else {
					for (MenuButton menuButton : menuButtons) {
						buttonNode = new ZTreeSimpleNode();
						buttonNode.setName(menuButton.getButtonLabel());
						buttonNode.setId(menu.getMenuId()+"~"+menuButton.getId());
						buttonNode.setIsParent(false);
						buttonNode.setpId(menuButton.getMenuId());
						buttonNode.setOpen(false);
						buttonNode.setIcon(this.getContextPath() + iconPath+ this.iconLeaf);
						nodeList.add(buttonNode);
					}
				}
			}
		}
		return nodeList;
	}

	public String addButtonPriv() {
		try {
			if(buttonType.equals("1")){
				addSearchButtonPriv();
			}else if(buttonType.equals("2")){
				addMenuButtonPriv();
			}
			return ajaxForward("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, "保存失败！");
		}
		
	}
	
	private void addSearchButtonPriv(){
		String[] searchIds = content.split(",");
		String id = "";
		String sid = "", suid = "";
		int semi = -1;
		Search search = null;
		SearchUrl searchUrl = null;
		List<PropertyFilter> filters = null;
		try {
			if (userId != null) {// buttonPrivUser--user
				
				ButtonPrivUser searchButtonPrivUser = null;
				ButtonPrivUserDetail searchButtonPrivUserDetail = null;
				Set<ButtonPrivUserDetail> searchButtonPrivUserDetails = null;
				Map<String, ButtonPrivUser> sbpus = new HashMap<String, ButtonPrivUser>();
				for (int i = 0; i < searchIds.length; i++) {
					id = searchIds[i];
					semi = id.indexOf('~');
					if (semi < 0) {
						continue;
					} else {
						sid = id.substring(0, semi);
						suid = id.substring(semi + 1, id.length());
						searchUrl = searchUrlManager.get(suid);
						if (sbpus.get(sid) == null) {
							search = searchManager.get(sid);
							searchButtonPrivUser = new ButtonPrivUser();
							searchButtonPrivUser.setUserId(userId);
							searchButtonPrivUser.setSearchOrMenuId(sid);
							searchButtonPrivUser.setLabel(search.getTitle());
							searchButtonPrivUser.setSearchName(search.getSearchName());
							searchButtonPrivUser.setButtonType(buttonType);

							searchButtonPrivUserDetails = new HashSet<ButtonPrivUserDetail>();
							searchButtonPrivUserDetail = new ButtonPrivUserDetail();
							searchButtonPrivUserDetail.setButtonId(suid);
							searchButtonPrivUserDetail.setButtonLabel(searchUrl.getTitle());
							searchButtonPrivUserDetail.setButtonURL(searchUrl.getUrl());
							searchButtonPrivUserDetail.setOrder(searchUrl.getOorder().intValue());
							searchButtonPrivUserDetail.setButtonPrivUser(searchButtonPrivUser);
							searchButtonPrivUserDetails.add(searchButtonPrivUserDetail);
							searchButtonPrivUser.setButtonPrivUserDetails(searchButtonPrivUserDetails);
							sbpus.put(sid, searchButtonPrivUser);
						} else {
							searchButtonPrivUser = sbpus.get(sid);
							searchButtonPrivUserDetails = searchButtonPrivUser.getButtonPrivUserDetails();
							searchButtonPrivUserDetail = new ButtonPrivUserDetail();
							searchButtonPrivUserDetail.setButtonId(suid);
							searchButtonPrivUserDetail.setButtonLabel(searchUrl.getTitle());
							searchButtonPrivUserDetail.setButtonURL(searchUrl.getUrl());
							searchButtonPrivUserDetail.setOrder(searchUrl.getOorder().intValue());
							searchButtonPrivUserDetail.setButtonPrivUser(searchButtonPrivUser);
							searchButtonPrivUserDetails.add(searchButtonPrivUserDetail);
							searchButtonPrivUser.setButtonPrivUserDetails(searchButtonPrivUserDetails);
							sbpus.put(sid, searchButtonPrivUser);
						}
					}
				}
				
				Collection<ButtonPrivUser> newSearchButtonPrivUsers = sbpus.values();
				filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_userId",userId));
				filters.add(new PropertyFilter("EQS_buttonType",buttonType));
				List<ButtonPrivUser> searchButtonPrivUsers = buttonPrivUserManager.getByFilters(filters);
				buttonPrivUserManager.addButtonPrivUser(newSearchButtonPrivUsers, searchButtonPrivUsers);
			} else if (roleId != null) {// buttonPriv--role
				ButtonPriv searchButtonPriv = null;
				ButtonPrivDetail searchButtonPrivDetail = null;
				Set<ButtonPrivDetail> searchButtoPrivDetails = null;

				Map<String, ButtonPriv> sbps = new HashMap<String, ButtonPriv>();
				for (int i = 0; i < searchIds.length; i++) {
					id = searchIds[i];
					semi = id.indexOf('~');
					if (semi < 0) {
						continue;
					} else {
						sid = id.substring(0, semi);
						suid = id.substring(semi + 1, id.length());
						searchUrl = searchUrlManager.get(suid);
						if (sbps.get(sid) == null) {
							search = searchManager.get(sid);

							searchButtonPriv = new ButtonPriv();
							searchButtonPriv.setRoleId(roleId);
							searchButtonPriv.setSearchOrMenuId(sid);
							searchButtonPriv.setLabel(search.getTitle());
							searchButtonPriv.setSearchName(search
									.getSearchName());
							searchButtonPriv.setButtonType(buttonType);

							searchButtoPrivDetails = new HashSet<ButtonPrivDetail>();
							searchButtonPrivDetail = new ButtonPrivDetail();
							searchButtonPrivDetail.setButtonId(suid);
							searchButtonPrivDetail.setButtonLabel(searchUrl
									.getTitle());
							searchButtonPrivDetail.setButtonURL(searchUrl
									.getUrl());
							searchButtonPrivDetail
									.setButtonPriv(searchButtonPriv);
							searchButtonPrivDetail.setOrder(searchUrl
									.getOorder().intValue());
							searchButtoPrivDetails.add(searchButtonPrivDetail);
							searchButtonPriv
									.setButtonPrivDetails(searchButtoPrivDetails);
							sbps.put(sid, searchButtonPriv);
						} else {
							searchButtonPriv = sbps.get(sid);
							searchButtoPrivDetails = searchButtonPriv.getButtonPrivDetails();
							searchButtonPrivDetail = new ButtonPrivDetail();
							searchButtonPrivDetail.setButtonId(suid);
							searchButtonPrivDetail.setButtonLabel(searchUrl.getTitle());
							searchButtonPrivDetail.setButtonURL(searchUrl.getUrl());
							searchButtonPrivDetail.setButtonPriv(searchButtonPriv);
							searchButtonPrivDetail.setOrder(searchUrl.getOorder().intValue());
							searchButtoPrivDetails.add(searchButtonPrivDetail);
							searchButtonPriv.setButtonPrivDetails(searchButtoPrivDetails);
							sbps.put(sid, searchButtonPriv);
						}
					}
				}
				Collection<ButtonPriv> newSearchButtonPrivs = sbps.values();
				filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_roleId",roleId));
				filters.add(new PropertyFilter("EQS_buttonType",buttonType));
				buttonPrivs = buttonPrivManager.getByFilters(filters);
				buttonPrivManager.addButtonPriv(newSearchButtonPrivs,buttonPrivs);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	private void addMenuButtonPriv(){
		String[] menuIds = content.split(",");
		String mid = "",bid = "";
		int semi = -1;
		Menu menu = null;
		MenuButton menuButton = null;
		List<PropertyFilter> filters = null;
		if (userId != null){
			ButtonPrivUser menuButtonPrivUser = null;
			ButtonPrivUserDetail menuButtonPrivUserDetail = null;
			Set<ButtonPrivUserDetail> menuButtonPrivUserDetails = null;
			Map<String, ButtonPrivUser> map = new HashMap<String, ButtonPrivUser>();
			for (int i = 0; i < menuIds.length; i++) {
				id = menuIds[i];
				semi = id.indexOf('~');
				if (semi < 0) {
					continue;
				} else {
					mid = id.substring(0, semi);
					bid = id.substring(semi + 1, id.length());
					menuButton = menuButtonManager.getByMenuIdAndButtonId(mid, bid);
					if(map.get(mid)==null){
						menu = menuManager.get(mid);
						menuButtonPrivUser = new ButtonPrivUser();
						menuButtonPrivUser.setUserId(userId);
						menuButtonPrivUser.setSearchOrMenuId(mid);
						menuButtonPrivUser.setLabel(menu.getMenuName().trim());
						menuButtonPrivUser.setButtonType(buttonType);

						menuButtonPrivUserDetails = new HashSet<ButtonPrivUserDetail>();
						menuButtonPrivUserDetail = new ButtonPrivUserDetail();
						menuButtonPrivUserDetail.setButtonId(bid);
						menuButtonPrivUserDetail.setButtonLabel(menuButton.getButtonLabel());
						menuButtonPrivUserDetail.setButtonURL(menuButton.getButtonUrl());
						menuButtonPrivUserDetail.setOrder(menuButton.getOorder().intValue());
						
						menuButtonPrivUserDetail.setButtonPrivUser(menuButtonPrivUser);
						menuButtonPrivUserDetails.add(menuButtonPrivUserDetail);
						menuButtonPrivUser.setButtonPrivUserDetails(menuButtonPrivUserDetails);
						
						map.put(mid, menuButtonPrivUser);
					}else{
						menuButtonPrivUser = map.get(mid);
						menuButtonPrivUserDetails = menuButtonPrivUser.getButtonPrivUserDetails();
						menuButtonPrivUserDetail = new ButtonPrivUserDetail();
						menuButtonPrivUserDetail.setButtonId(bid);
						menuButtonPrivUserDetail.setButtonLabel(menuButton.getButtonLabel());
						menuButtonPrivUserDetail.setButtonURL(menuButton.getButtonUrl());
						menuButtonPrivUserDetail.setOrder(menuButton.getOorder().intValue());
						menuButtonPrivUserDetail.setButtonPrivUser(menuButtonPrivUser);
						
						menuButtonPrivUserDetails.add(menuButtonPrivUserDetail);
						menuButtonPrivUser.setButtonPrivUserDetails(menuButtonPrivUserDetails);
						map.put(mid, menuButtonPrivUser);
					}
				}
			}
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_userId",userId));
			filters.add(new PropertyFilter("EQS_buttonType",buttonType));
			List<ButtonPrivUser> menuButtonPrivUsers = buttonPrivUserManager.getByFilters(filters);
			Collection<ButtonPrivUser> newMenuButtonPrivUsers = map.values();
			buttonPrivUserManager.addButtonPrivUser(newMenuButtonPrivUsers, menuButtonPrivUsers);
		}else if (roleId != null){
			ButtonPriv menuButtonPriv = null;
			ButtonPrivDetail menuButtonPrivDetail = null;
			Set<ButtonPrivDetail> menuButtonPrivDetails = null;
			Map<String,ButtonPriv> map = new HashMap<String,ButtonPriv>();
			for (int i = 0; i < menuIds.length; i++) {
				id = menuIds[i];
				semi = id.indexOf('~');
				if (semi < 0) {
					continue;
				} else {
					mid = id.substring(0, semi);
					bid = id.substring(semi + 1, id.length());
					menuButton = menuButtonManager.getByMenuIdAndButtonId(mid, bid);
					if(map.get(mid)==null){
						menu = menuManager.get(mid);
						menuButtonPriv = new ButtonPriv();
						menuButtonPriv.setButtonType(buttonType);
						menuButtonPriv.setRoleId(roleId);
						menuButtonPriv.setSearchOrMenuId(mid);
						menuButtonPriv.setLabel(menu.getMenuName().trim());
						
						menuButtonPrivDetails = new HashSet<ButtonPrivDetail>();
						menuButtonPrivDetail = new ButtonPrivDetail();
						menuButtonPrivDetail.setButtonId(bid);
						menuButtonPrivDetail.setButtonLabel(menuButton.getButtonLabel());
						menuButtonPrivDetail.setButtonURL(menuButton.getButtonUrl());
						menuButtonPrivDetail.setButtonPriv(menuButtonPriv);
						menuButtonPrivDetail.setOrder(menuButton.getOorder().intValue());
						
						menuButtonPrivDetails.add(menuButtonPrivDetail);
						menuButtonPriv.setButtonPrivDetails(menuButtonPrivDetails);
						
						map.put(mid, menuButtonPriv);
					}else {
						menuButtonPriv = map.get(mid);
						menuButtonPrivDetails = menuButtonPriv.getButtonPrivDetails();
						menuButtonPrivDetail = new ButtonPrivDetail();
						menuButtonPrivDetail.setButtonId(bid);
						menuButtonPrivDetail.setButtonLabel(menuButton.getButtonLabel());
						menuButtonPrivDetail.setButtonURL(menuButton.getButtonUrl());
						menuButtonPrivDetail.setButtonPriv(menuButtonPriv);
						menuButtonPrivDetail.setOrder(menuButton.getOorder().intValue());
						
						menuButtonPrivDetails.add(menuButtonPrivDetail);
						menuButtonPriv.setButtonPrivDetails(menuButtonPrivDetails);
						
						map.put(mid, menuButtonPriv);
					}
				}
			}
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_roleId",roleId));
			filters.add(new PropertyFilter("EQS_buttonType",buttonType));
			buttonPrivs = buttonPrivManager.getByFilters(filters);
			Collection<ButtonPriv> newMenuButtonPrivs = map.values();
			buttonPrivManager.addButtonPriv(newMenuButtonPrivs,buttonPrivs);
		}
	}

	private String isValid() {
		if (buttonPriv == null) {
			return "Invalid buttonPriv Data";
		}

		return SUCCESS;

	}
}
