package com.huge.ihos.inout.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.inout.model.SpecialItem;
import com.huge.ihos.inout.model.SpecialSource;
import com.huge.ihos.inout.service.SpecialItemManager;
import com.huge.ihos.inout.service.SpecialSourceManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.webapp.action.CBBaseAction;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.role.model.Role;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class SpecialSourcePagedAction extends CBBaseAction {

	private SpecialSourceManager specialSourceManager;
	private SpecialItemManager specialItemManager;
	private PeriodManager periodManager;
	private OrgManager orgManager;
	private List<SpecialSource> specialSources;
	private String specialItemStr = "";
	private List specialItemList;
	private SpecialSource specialSource;
	private Long specialSourceId;
	private String amountSum;
	private String statuCB;
	private String pageCheckPeriod;
	private String itemType;
	private String itemTypeName;
	private String itemId;
	private String specialSourceRule;
	private List<SpecialItem> changeSpecialItemList;
	private String orgNumber;
	private String herpType;
	private List<Branch> branches;
	private BranchManager branchManager;
	

	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}


	private List dicSpecialItemList;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		this.dicSpecialItemList = this.getDictionaryItemManager().getAllItemsByCode("specialitemtype");
		specialItemList = specialItemManager.getAllExceptDisable();

		changeSpecialItemList = specialItemManager.getAllExceptDisable();

		//		this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		this.currentUser = this.userManager.getCurrentLoginUser();
		this.herpType = ContextUtil.herpType;
		amountSum = specialSourceManager.getAmountSum("SpecialSource", "amount", currentPeriod);
		List<Org> orgs = this.orgManager.getAllAvailable();
		if(orgs != null && !orgs.isEmpty()) {
			this.orgNumber = orgs.size() + "";
		}
		this.clearSessionMessages();
	}

	public String getCBStatus() {
		statuCB = specialSourceManager.getCBStatus(pageCheckPeriod);
		return SUCCESS;
	}

	public String changeSpecialItemList() {
		changeSpecialItemList = specialSourceManager.changeSpecialItemList(itemTypeName);
		if (changeSpecialItemList == null || OtherUtil.measureNull(itemTypeName)) {
			changeSpecialItemList = specialItemManager.getAllExceptDisable();
			specialItemStr += "<option value=''></option>";
			for (SpecialItem specialItem : changeSpecialItemList) {
				specialItemStr += "<option value='" + specialItem.getItemId() + "'>" + specialItem.getItemName() + "</option>";
			}
			return SUCCESS;
		}
		for (SpecialItem specialItem : changeSpecialItemList) {
			specialItemStr += "<option value='" + specialItem.getItemId() + "'>" + specialItem.getItemName() + "</option>";
		}
		return SUCCESS;
	}

	public String selectItemType() {
		itemType = specialSourceManager.getItemType(itemId);
		return SUCCESS;
	}

	public String specialSourceGridList() {
		log.debug("enter list method!");
		try {
			Map sumAuount = new HashMap();
			sumAuount.put("amount", "");
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());

			Set<Role> roles = currentUser.getRoles();
			int j = 0;
			for (Role role : roles) {
				if (j == 0 && !role.getName().equals("ROLE_CBADMIN")) {
					filters.add(new PropertyFilter("EQS_operatorId", currentUser.getPerson().getPersonId()));
					j++;
				}
			}

			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests.setInitSumData(sumAuount);
			/*String costOrgPriv = UserContextUtil.findUserDataPrivilegeSql("cbdata_org", "1");
			sql += " OR t.costOrgCode in " + costOrgPriv;
			sql += " )";*/
			/*String operatorId = UserContextUtil.getLoginPersonId();
			String cbBranchPriv = UserContextUtil.findUserDataPrivilegeSql("cbdata_branch", "1");
			String sql = "SpecialSourceId in (SELECT t.SpecialSourceId from T_SpecialSource t where t.operatorId ='" +operatorId+ "'";
			sql += " OR t.cbBranchCode in " + cbBranchPriv;
			sql += " )";
			filters.add(new PropertyFilter("SQS_SpecialSourceId",sql));*/
			pagedRequests = specialSourceManager.getSpecialSourceCriteria(pagedRequests, filters);
			this.specialSources = (List<SpecialSource>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			sumAuount = pagedRequests.getSumData();
			//userdata = JSONArray.fromObject(sumAuount).toString();
			Map userData = new HashMap<String, String>();
			if (sumAuount.get("amount") == null) {
				userData.put("amount", "0");
				this.setUserdata(userData);
			} else {
				userData.put("amount", sumAuount.get("amount").toString());
				this.setUserdata(userData);
			}

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
			Person p = currentUser.getPerson();
			specialSource.setOperatorId(p.getPersonId());
			specialSource.setOperatorName(p.getName());
			//System.out.println(getCurrentDate());
			specialSource.setProcessDate(new Date());
			if (OtherUtil.measureNull(specialSource.getDeptid1().getDepartmentId())) {
				specialSource.setDeptid1(null);
			}
			if (OtherUtil.measureNull(specialSource.getDeptid2().getDepartmentId())) {
				specialSource.setDeptid2(null);
			}
			if (OtherUtil.measureNull(specialSource.getDeptid3().getDepartmentId())) {
				specialSource.setDeptid3(null);
			}
			if (OtherUtil.measureNull(specialSource.getStatus())) {
				specialSource.setStatus("0");
			}
			if(specialSource.getCbBranch() != null && OtherUtil.measureNull(specialSource.getCbBranch().getBranchCode())) {
				specialSource.setCbBranch(null);
			}
			String specialSourceRule = this.getGlobalParamByKey("specialSourceRule");
			if("1".equals(specialSourceRule)) {
				if(specialSource.getCbBranch() != null && OtherUtil.measureNotNull(specialSource.getCbBranch().getBranchCode())) {
					Org cbOrg = branchManager.get(specialSource.getCbBranch().getBranchCode()).getOrg();
					specialSource.setCostOrg(cbOrg);
				}
			}
			/*if(OtherUtil.measureNull(specialSource.getCostOrg().getOrgCode())) {
				specialSource.setCostOrg(null);
			}*/
			specialSourceManager.save(specialSource);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "specialSource.added" : "specialSource.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		String branchPriv = UserContextUtil.findUserDataPrivilegeStr("cbdata_branch","2");
		this.branches = branchManager.getAllAvailable(branchPriv);
		specialSourceRule = this.getGlobalParamByKey("specialSourceRule");
		if (specialSourceId != null) {
			specialSource = specialSourceManager.get(specialSourceId);
			this.setEntityIsNew(false);
		} else {
			specialSource = new SpecialSource();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String specialSourceGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					SpecialSource specialSource = specialSourceManager.get(new Long(removeId));
					specialSourceManager.remove(new Long(removeId));

				}
				gridOperationMessage = this.getText("specialSource.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkSpecialSourceGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public String specialSourceList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			boolean loginPeriodClosed = getLoginPeriodClosed();
			Iterator<MenuButton> it = menuButtons.iterator();
			while (it.hasNext()) {
				MenuButton button = it.next();
				if (loginPeriodClosed || !getLoginPeriodStarted()) {
					button.setEnable(false);
				}
			}
			setMenuButtonsToJson(menuButtons);
			String branchPriv = UserContextUtil.findUserDataPrivilegeStr("cbdata_branch","1");
			this.branches = branchManager.getAllAvailable(branchPriv);
		} catch (Exception e) {
			log.error("specialSourceList error!", e);
		}
		return SUCCESS;
	}

	private String isValid() {
		if (specialSource == null) {
			return "Invalid specialSource Data";
		}

		return SUCCESS;

	}

	public SpecialSourceManager getSpecialSourceManager() {
		return specialSourceManager;
	}

	public void setSpecialSourceManager(SpecialSourceManager specialSourceManager) {
		this.specialSourceManager = specialSourceManager;
	}

	public List<SpecialSource> getspecialSources() {
		return specialSources;
	}

	public void setSpecialSources(List<SpecialSource> specialSources) {
		this.specialSources = specialSources;
	}

	public SpecialSource getSpecialSource() {
		return specialSource;
	}

	public void setSpecialSource(SpecialSource specialSource) {
		this.specialSource = specialSource;
	}

	public Long getSpecialSourceId() {
		return specialSourceId;
	}

	public void setSpecialSourceId(Long specialSourceId) {
		this.specialSourceId = specialSourceId;
	}

	public void setSpecialItemManager(SpecialItemManager specialItemManager) {
		this.specialItemManager = specialItemManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public String getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(String amountSum) {
		this.amountSum = amountSum;
	}

	public String getStatuCB() {
		return statuCB;
	}

	public String getSpecialItemStr() {
		return specialItemStr;
	}

	public void setSpecialItemStr(String specialItemStr) {
		this.specialItemStr = specialItemStr;
	}

	public void setStatuCB(String statuCB) {
		this.statuCB = statuCB;
	}

	public String getPageCheckPeriod() {
		return pageCheckPeriod;
	}

	public void setPageCheckPeriod(String pageCheckPeriod) {
		this.pageCheckPeriod = pageCheckPeriod;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public List getDicSpecialItemList() {
		return dicSpecialItemList;
	}

	public void setDicSpecialItemList(List dicSpecialItemList) {
		this.dicSpecialItemList = dicSpecialItemList;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public List getSpecialItemList() {
		return specialItemList;
	}

	public void setSpecialItemList(List specialItemList) {
		this.specialItemList = specialItemList;
	}

	public List<SpecialItem> getChangeSpecialItemList() {
		return changeSpecialItemList;
	}

	public void setChangeSpecialItemList(List<SpecialItem> changeSpecialItemList) {
		this.changeSpecialItemList = changeSpecialItemList;
	}

	public String getSpecialSourceRule() {
		return specialSourceRule;
	}

	public void setSpecialSourceRule(String specialSourceRule) {
		this.specialSourceRule = specialSourceRule;
	}

	public OrgManager getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

}
