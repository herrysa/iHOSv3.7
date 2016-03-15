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

import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.service.SearchUrlManager;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.menu.service.MenuButtonManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPriv;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivManager;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivUserDetailManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.opensymphony.xwork2.Preparable;




public class ButtonPrivUserDetailPagedAction extends JqGridBaseAction implements Preparable {

	private ButtonPrivUserDetailManager buttonPrivUserDetailManager;
	private List<ButtonPrivUserDetail> buttonPrivUserDetails;
	private ButtonPrivUserDetail buttonPrivUserDetail;
	private String bpuDetailId;
	private String userId;
	private String searchOrMenuId;
	private ButtonPrivManager buttonPrivManager;
	private SearchUrlManager searchUrlManager;
	private String buttonType;
	private MenuButtonManager menuButtonManager;

	public void setMenuButtonManager(MenuButtonManager menuButtonManager) {
		this.menuButtonManager = menuButtonManager;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public void setSearchUrlManager(SearchUrlManager searchUrlManager) {
		this.searchUrlManager = searchUrlManager;
	}

	public void setButtonPrivManager(
			ButtonPrivManager buttonPrivManager) {
		this.buttonPrivManager = buttonPrivManager;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setSearchOrMenuId(String searchOrMenuId) {
		this.searchOrMenuId = searchOrMenuId;
	}

	public void setButtonPrivUserDetailManager(ButtonPrivUserDetailManager buttonPrivUserDetailManager) {
		this.buttonPrivUserDetailManager = buttonPrivUserDetailManager;
	}

	public List<ButtonPrivUserDetail> getButtonPrivUserDetails() {
		return buttonPrivUserDetails;
	}

	public void setButtonPrivUserDetails(List<ButtonPrivUserDetail> buttonPrivUserDetails) {
		this.buttonPrivUserDetails = buttonPrivUserDetails;
	}

	public ButtonPrivUserDetail getButtonPrivUserDetail() {
		return buttonPrivUserDetail;
	}

	public void setButtonPrivUserDetail(ButtonPrivUserDetail buttonPrivUserDetail) {
		this.buttonPrivUserDetail = buttonPrivUserDetail;
	}

	public String getBpuDetailId() {
		return bpuDetailId;
	}

	public void setBpuDetailId(String bpuDetailId) {
        this.bpuDetailId = bpuDetailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String buttonPrivUserDetailGridList() {
		log.debug("enter list method!");
		try {
			List<ButtonPrivUserDetail> realList = null;
			if(userId!=null && searchOrMenuId!=null && buttonType!=null){
				List<ButtonPrivUserDetail> userBPS = buttonPrivUserDetailManager.findByUserAndSearch(userId, searchOrMenuId);
				if(userBPS==null || userBPS.size()==0){
					userBPS = new ArrayList<ButtonPrivUserDetail>();
				}
				List<ButtonPrivUserDetail> roleBPS = this.getButtonPrivDetailsInRoleByUser(userId,searchOrMenuId,buttonType);
				if(roleBPS!=null){
					userBPS.addAll(roleBPS);
				}
				Set<ButtonPrivUserDetail> bpuTreeSet = new TreeSet<ButtonPrivUserDetail>();
				for(ButtonPrivUserDetail sbpu:userBPS){
					bpuTreeSet.add(sbpu);
				}
				realList = new ArrayList<ButtonPrivUserDetail>(bpuTreeSet);
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
			this.buttonPrivUserDetails = (List<ButtonPrivUserDetail>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	private List<ButtonPrivUserDetail> getButtonPrivDetailsInRoleByUser(String userId, String searchOrMenuId,String buttonType) {
		List<ButtonPrivUserDetail> buttonPrivUserDetails = null;
		Set<Role> roles = userManager.get(Long.parseLong(userId)).getRoles();
		int roleSize = roles.size();
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(Role role:roles){
			ButtonPriv sbp = buttonPrivManager.findByRoleAndSearch(""+role.getId(), searchOrMenuId);
			if(sbp==null){
				continue;
			}
			Set<ButtonPrivDetail> bpds = sbp.getButtonPrivDetails();
			for(ButtonPrivDetail bpd:bpds){
				String buttonId = bpd.getButtonId();
				if(map.get(buttonId)==null){
					map.put(buttonId, 1);
				}else{
					map.put(buttonId, map.get(buttonId)+1);
				}
			}
		}
		Iterator<Entry<String,Integer>> ite = map.entrySet().iterator();
		Entry<String,Integer> entry = null;
		int entryValue = -1;
		while(ite.hasNext()){
			entry = ite.next();
			entryValue = entry.getValue();
			if(entryValue!=roleSize){
				ite.remove();
			}
		}
		Set<String> buttonIds = map.keySet();
		if(buttonIds!=null && buttonIds.size()>0){
			buttonPrivUserDetails = new ArrayList<ButtonPrivUserDetail>();
			ButtonPrivUserDetail bpud = null;
			if(buttonType.equals("1")){
				SearchUrl searchUrl = null;
				for(String searchUrlId:buttonIds){
					searchUrl =searchUrlManager.get(searchUrlId);
					bpud = new ButtonPrivUserDetail();
					bpud.setButtonId(searchUrlId);
					bpud.setButtonLabel(searchUrl.getTitle());
					bpud.setButtonURL(searchUrl.getUrl());
					bpud.setOrder(searchUrl.getOorder().intValue());
//					sbpud.setSearchButtonPrivUser(null);
					bpud.setBpuDetailId(OtherUtil.getRandomUUID());
					buttonPrivUserDetails.add(bpud);
				}
			}else if(buttonType.equals("2")){
				MenuButton menuButton = null;
				for(String buttonId:buttonIds){
					menuButton = menuButtonManager.getByMenuIdAndButtonId(searchOrMenuId, buttonId);
					bpud = new ButtonPrivUserDetail();
					bpud.setButtonId(buttonId);
					bpud.setButtonLabel(menuButton.getButtonLabel());
					bpud.setButtonURL(menuButton.getButtonUrl());
					bpud.setOrder(menuButton.getOorder().intValue());
//					sbpud.setSearchButtonPrivUser(null);
					bpud.setBpuDetailId(OtherUtil.getRandomUUID());
					buttonPrivUserDetails.add(bpud);
				}
			}
			
		}
		return buttonPrivUserDetails;
	}

	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			buttonPrivUserDetailManager.save(buttonPrivUserDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "searchButtonPrivUserDetail.added" : "searchButtonPrivUserDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (bpuDetailId != null) {
        	buttonPrivUserDetail = buttonPrivUserDetailManager.get(bpuDetailId);
        	this.setEntityIsNew(false);
        } else {
        	buttonPrivUserDetail = new ButtonPrivUserDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String buttonPrivUserDetailGridEdit() {
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
				this.buttonPrivUserDetailManager.remove(ida);
				gridOperationMessage = this.getText("searchButtonPrivUserDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("buttonPrivUserDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private String isValid() {
		if (buttonPrivUserDetail == null) {
			return "Invalid buttonPrivUserDetail Data";
		}

		return SUCCESS;

	}
}

