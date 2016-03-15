package com.huge.ihos.formula.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.tools.ant.taskdefs.condition.Or;

import com.huge.ihos.formula.model.AllotItem;
import com.huge.ihos.formula.service.AllotItemManager;
import com.huge.ihos.formula.service.AllotSetManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.search.service.QueryManager;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.util.OtherUtil;
import com.huge.util.ReturnUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class AllotItemAction extends JqGridBaseAction {
	private AllotSetManager allotSetManager;

	private AllotItemManager allotItemManager;

	private PeriodManager periodManager;

	private QueryManager queryManager;

	private OrgManager orgManager;

	private List allAllotSets;

	private List allotItems;

	private AllotItem allotItem;

	private Long allotItemId;

	private List periods;

	private String taskName;

	private String proArgsStr;

	private String herpType;

	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProArgsStr() {
		return proArgsStr;
	}

	public void setProArgsStr(String proArgsStr) {
		this.proArgsStr = proArgsStr;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public List getAllAllotSets() {
		return allAllotSets;
	}

	public void setAllAllotSets(List allAllotSets) {
		this.allAllotSets = allAllotSets;
	}

	public void setAllotSetManager(AllotSetManager allotSetManager) {
		this.allotSetManager = allotSetManager;
	}

	public void setAllotItemManager(AllotItemManager allotItemManager) {
		this.allotItemManager = allotItemManager;
	}

	public List getAllotItems() {
		return allotItems;
	}

	/**
	 * Grab the entity from the database before populating with request parameters
	 */
	@Override
	public void prepare() throws Exception {
		super.prepare();
		/* if (getRequest().getMethod().equalsIgnoreCase("post")) {
		     // prevent failures on new
		     String allotItemId = getRequest().getParameter("allotItem.allotItemId");
		     if (allotItemId != null && !allotItemId.equals("")) {
		         allotItem = allotItemManager.get(new Long(allotItemId));
		     }
		 }*/
		//        this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		this.allAllotSets = this.allotSetManager.getAll();
		this.herpType = ContextUtil.herpType;

		this.clearSessionMessages();
	}

	/*   public String list() {
	       allotItems = allotItemManager.search(query, AllotItem.class);
	       return SUCCESS;
	   }*/

	public void setAllotItemId(Long allotItemId) {
		this.allotItemId = allotItemId;
	}

	public AllotItem getAllotItem() {
		return allotItem;
	}

	public void setAllotItem(AllotItem allotItem) {
		this.allotItem = allotItem;
	}

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

	public String allotItemList() {
		this.branches = this.branchManager.getAllAvailable();
		return SUCCESS;
	}

	public String delete() {
		allotItemManager.remove(allotItem.getAllotItemId());
		saveMessage(getText("allotItem.deleted"));

		return "edit_success";
	}

	public String edit() {
		this.branches = this.branchManager.getAllAvailable();
		if (allotItemId != null) {
			allotItem = allotItemManager.get(allotItemId);
		} else {
			allotItem = new AllotItem();
			allotItem.setCheckPeriod(this.currentPeriod);
		}

		return SUCCESS;
	}

	public String save() throws Exception {
		if (cancel != null) {
			return "cancel";
		}

		if (delete != null) {
			return delete();
		}

		boolean isNew = (allotItem.getAllotItemId() == null);

		/*        if(allotItem.getCostItem().getCostItemId().trim().equals(""))
		 allotItem.setCostItem(null);*/
		/*UserContext userContext = UserContextUtil.getUserContext();
		String orgModel = userContext.getCurrentOrgModel();
		if("2".equals(orgModel)) {
			String orgCode = userContext.getOrgCode();
			if(orgCode !=null && !"".equals(orgCode)) {
				Org org = orgManager.get(orgCode);
				allotItem.setOrg(org);
			} else {
				allotItem.setOrg(null);
			}
		} else {
			allotItem.setOrg(null);
		}*/
		if (OtherUtil.measureNull(allotItem.getOrg().getOrgCode())) {
			allotItem.setOrg(null);
		}
		if (OtherUtil.measureNull(allotItem.getBranch().getBranchCode())) {
			allotItem.setBranch(null);
		}
		allotItemManager.save(allotItem);

		String key = (isNew) ? "allotItem.added" : "allotItem.updated";
		saveMessage(getText(key));

		return ajaxForward(getText(key));
	}

	private Map userData = new HashMap();

	public Map getUserdata() {
		return userData;
	}

	public void setUserData(Map userData) {
		this.userData = userData;
	}

	public String allotItemGridList() {
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			//			pagedRequests = allotItemManager.getAllotItemCriteria(pagedRequests);
			userData.clear();
			userData.put("allotCost", null);
			pagedRequests.setInitSumData(userData);
			UserContext userContext = UserContextUtil.getUserContext();
			String orgModel = userContext.getCurrentOrgModel();
			/* if("2".equals(orgModel)) {
			 	String orgCode = userContext.getOrgCode();
			 	filters.add(new PropertyFilter("EQS_org.orgCode",orgCode));
			 }*/
			String periodMonth = UserContextUtil.getLoginPeriod();
			filters.add(new PropertyFilter("EQS_checkPeriod",periodMonth));
			pagedRequests = allotItemManager.getAppManagerCriteriaWithSearch(pagedRequests, AllotItem.class, filters);
			this.allotItems = (List<AllotItem>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

			/*		this.userData.put("checkPeriod", "合计：");
					this.userData.put("allotCost", 21212121.00d);*/
			this.userData = pagedRequests.getSumData();
			this.userData.put("checkPeriod", "合计：");
		} catch (Exception e) {
			log.error("allotItemGridList Error", e);
		}
		return SUCCESS;
	}

	public String allotItemGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					allotItemManager.remove(removeId);
				}
				gridOperationMessage = this.getText("allotItem.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return SUCCESS;
				}
				allotItemManager.save(allotItem);
				String key = (oper.equals("add")) ? "allotItem.added" : "allotItem.updated";
				gridOperationMessage = this.getText(key);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, gridOperationMessage, false);
		}
	}

	/**
	 * @TODO you should add some validation rules those are difficult on client side.
	 * @return
	 */
	private String isValid() {
		if (allotItem == null) {
			return "Invalid allotItem Data";
		}

		return SUCCESS;

	}

	public String eiditInheritanceAlloItem() {
		//        this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		periods = this.periodManager.getLessCurrentPeriod(this.currentPeriod);
		return SUCCESS;
	}

	public String inheritanceAlloItem() {
		try {
			Object[] proArgs = proArgsStr.split(",");
			ReturnUtil returnUtil = this.queryManager.publicPrecess(taskName, proArgs);
			if (returnUtil.getStatusCode() == 0) {
				return ajaxForward(true, returnUtil.getMessage(), true);
			} else {
				return ajaxForward(false, returnUtil.getMessage(), false);
			}
			//return ajaxForward( true, this.queryManager.publicPrecess( taskName, proArgs ), true );
		} catch (Exception e) {
			// TODO: handle exception
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	public List getPeriods() {
		return periods;
	}

	public void setPeriods(List periods) {
		this.periods = periods;
	}

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}
}