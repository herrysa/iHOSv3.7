package com.huge.ihos.inout.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.huge.ihos.inout.model.Sourcecost;
import com.huge.ihos.inout.service.SourcecostManager;
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
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class SourcecostAction extends CBBaseAction {
	private SourcecostManager sourcecostManager;

	private PeriodManager periodManager;

	private OrgManager orgManager;

	private List sourcecosts;

	private Sourcecost sourcecost;

	private Long sourceCostId;

	private String paytypeId;

	private String operrater;

	private Person person;

	private String amountSum;
	private String sourcecostRule;
	private String orgNumber;
	private String herpType;
	private List<Branch> branches;
	private BranchManager branchManager;
	private String sqlStr;
	
	

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}


	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getOperrater() {
		return operrater;
	}

	public void setOperrater(String operrater) {
		this.operrater = operrater;
	}

	public OrgManager getOrgManager() {
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public void setSourcecostManager(SourcecostManager sourcecostManager) {
		this.sourcecostManager = sourcecostManager;
	}

	public List getSourcecosts() {
		return sourcecosts;
	}

	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */
	
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		/*
		 * if (getRequest().getMethod().equalsIgnoreCase("post")) { // prevent
		 * failures on new String sourcecostId =
		 * getRequest().getParameter("sourcecost.sourceCostId"); if
		 * (sourcecostId != null && !sourcecostId.equals("")) { sourcecost =
		 * sourcecostManager.get(new Long(sourcecostId)); } }
		 */
		//		this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		this.currentUser = this.userManager.getCurrentLoginUser();
		this.branches = branchManager.getAllAvailable();
		this.herpType = ContextUtil.herpType;
		this.clearSessionMessages();
		amountSum = sourcecostManager.getAmountSum(currentPeriod);
		List<Org> orgs = this.orgManager.getAllAvailable();
		if(orgs != null && !orgs.isEmpty()) {
			this.orgNumber = orgs.size() + "";
		}
	}

	/*
	 * public String list() { sourcecosts = sourcecostManager.search(query,
	 * Sourcecost.class); return SUCCESS; }
	 */

	public void setSourceCostId(Long sourceCostId) {
		this.sourceCostId = sourceCostId;
	}

	public Sourcecost getSourcecost() {
		return sourcecost;
	}

	public void setSourcecost(Sourcecost sourcecost) {
		this.sourcecost = sourcecost;
	}

	public String delete() {
		sourcecostManager.remove(sourcecost.getSourceCostId());
		saveMessage(getText("sourcecost.deleted"));

		return "edit_success";
	}

	public String edit() {
		String branchPriv = UserContextUtil.findUserDataPrivilegeStr("cbdata_branch","2");
		this.branches = branchManager.getAllAvailable(branchPriv);
		sourcecostRule = this.getGlobalParamByKey("sourcecostRule");
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = securityContext.getAuthentication().getName();
		User user = userManager.getUserByUsername(username);
		person = user.getPerson();
		if (sourceCostId != null) {
			sourcecost = sourcecostManager.get(sourceCostId);
			if (sourcecost.getOperator() == null) {
				sourcecost.setOperator(person);
			}
		} else {
			sourcecost = new Sourcecost();
			sourcecost = this.prepareNew(sourcecost);
			sourcecost.setOperator(person);
		}
		String operatorId = UserContextUtil.getLoginPersonId();
		String cbBranchPriv = UserContextUtil.findUserDataPrivilegeSql("cbdata_branch", "1");
		sqlStr = "sourceCostId IN (SELECT t.sourceCostId FROM t_sourcecost t WHERE t.operatorId = '" + operatorId +"'";
		sqlStr += " OR t.cbBranchCode in "+cbBranchPriv;
		sqlStr += " )";
		// operrater = SecurityContext

		return SUCCESS;
	}

	private Sourcecost prepareNew(Sourcecost sp) {
		//		sp.setCheckPeriod(this.periodManager.getCurrentPeriod().getPeriodCode());
		sp.setCheckPeriod(this.getLoginPeriod());
		sp.setManual(true);
		sp.setProcessDate(new Date());
		return sp;
	}

	public String save() throws Exception {
		if (cancel != null) {
			return "cancel";
		}

		if (delete != null) {
			return delete();
		}

		boolean isNew = (sourcecost.getSourceCostId() == null);
		if (sourcecost.getOperator().getPersonId() == null || sourcecost.getOperator().getPersonId().equals("")) {
			sourcecost.setOperator(null);
		}
		if (OtherUtil.measureNull(sourcecost.getStatus())) {
			sourcecost.setStatus("0");
		}
		/*if(OtherUtil.measureNull(sourcecost.getCostOrg().getOrgCode())) {
			sourcecost.setCostOrg(null);
		}*/

		String sourcecostRule = this.getGlobalParamByKey("sourcecostRule");
		if("1".equals(sourcecostRule)) {
			if(sourcecost.getCbBranch() != null && OtherUtil.measureNotNull(sourcecost.getCbBranch().getBranchCode())) {
				Org cbOrg = branchManager.get(sourcecost.getCbBranch().getBranchCode()).getOrg();
				sourcecost.setCostOrg(cbOrg);
			}
		}
		if (sourcecost.getCbBranch()!=null&&OtherUtil.measureNull(sourcecost.getCbBranch().getBranchCode())) {
			sourcecost.setCbBranch(null);
		}
		sourcecostManager.save(sourcecost);

		String key = (isNew) ? "sourcecost.added" : "sourcecost.updated";
		saveMessage(getText(key));

		return ajaxForward(true, getText(key), !(this.getContinueNew() != null));
	}

	public String sourcecostGridList() {
		try {
			Map sumAuount = new HashMap();
			sumAuount.put("amount", "");
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			if (paytypeId != null) {

				if (paytypeId.equals("paytypeOne")) {
					filters.add(new PropertyFilter("INS_deptartment.deptClass", "医疗,医技,医辅"));
					filters.add(new PropertyFilter("LTS_costItemId.costUse.costUseId", "8"));
				} else if (paytypeId.equals("paytypeTwo")) {
					filters.add(new PropertyFilter("EQS_deptartment.deptClass", "管理"));
					filters.add(new PropertyFilter("LTS_costItemId.costUse.costUseId", "8"));
				} else if (paytypeId.equals("paytypeThree")) {
					filters.add(new PropertyFilter("EQS_costItemId.costUse.costUseId", "8"));

				} else if (paytypeId.equals("paytypeFour")) {
					filters.add(new PropertyFilter("EQS_costItemId.costUse.costUseId", "9"));

				} else if (paytypeId.equals("paytypeFive")) {
					filters.add(new PropertyFilter("LTS_costItemId.costUse.costUseId", "8"));
				} else if (paytypeId.equals("paytypeSix")) {
					filters.add(new PropertyFilter("LES_costItemId.costUse.costUseId", "8"));
				} else if (paytypeId.equals("paytype")) {
					filters.add(new PropertyFilter("LES_costItemId.costUse.costUseId", "9"));
				}
			}
			/*String costOrgPriv = UserContextUtil.findUserDataPrivilegeSql("cbdata_org", "1");
			String sql = "sourceCostId IN (SELECT t.sourceCostId FROM t_sourcecost t WHERE t.operatorId = '" + operatorId +"'";
			sql += " OR t.cbOrgCode in " + costOrgPriv;
			sql += " )";*/
			/*String operatorId = UserContextUtil.getLoginPersonId();
			String cbBranchPriv = UserContextUtil.findUserDataPrivilegeSql("cbdata_branch", "1");
			String sql = "sourceCostId IN (SELECT t.sourceCostId FROM t_sourcecost t WHERE t.operatorId = '" + operatorId +"'";
			sql += " OR t.cbBranchCode in "+cbBranchPriv;
			sql += " )";
			filters.add(new PropertyFilter("SQS_sourceCostId",sql));*/
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			// pagedRequests =
			// sourcecostManager.getSourcecostCriteria(pagedRequests);
			pagedRequests.setInitSumData(sumAuount);
			pagedRequests = sourcecostManager.getAppManagerCriteriaWithSearch(pagedRequests, Sourcecost.class, filters);
			this.sourcecosts = (List<Sourcecost>) pagedRequests.getList();

			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
			sumAuount = pagedRequests.getSumData();
			// userdata = JSONArray.fromObject(sumAuount).toString();
			Map userData = new HashMap<String, String>();
			if (sumAuount.get("amount") == null) {
				userData.put("amount", "0");
				this.setUserdata(userData);
			} else {
				userData.put("amount", sumAuount.get("amount").toString());
				this.setUserdata(userData);
			}

		} catch (Exception e) {
			log.error("sourcecostGridList Error", e);
		}
		return SUCCESS;
	}

	public String sourcecostGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					sourcecostManager.remove(removeId);
				}
				gridOperationMessage = this.getText("sourcecost.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return SUCCESS;
				}
				sourcecostManager.save(sourcecost);
				String key = (oper.equals("add")) ? "sourcecost.added" : "sourcecost.updated";
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

	public String sourcecostList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			boolean loginPeriodClosed = getLoginPeriodClosed();
			Iterator<MenuButton> it = menuButtons.iterator();
			while (it.hasNext()) {
				MenuButton button = it.next();
				if (loginPeriodClosed || !getLoginPeriodStarted()) {
					if(!"01020204".equals(button.getId()) && !"01020205".equals(button.getId())) {
						button.setEnable(false);
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
			String branchPriv = UserContextUtil.findUserDataPrivilegeStr("cbdata_branch","1");
			this.branches = branchManager.getAllAvailable(branchPriv);
		} catch (Exception e) {
			log.error("sourcepayinList error!", e);
		}
		return SUCCESS;
	}

	/**
	 * @TODO you should add some validation rules those are difficult on client
	 *       side.
	 * @return
	 */
	private String isValid() {
		if (sourcecost == null) {
			return "Invalid sourcecost Data";
		}

		return SUCCESS;

	}

	public String getPaytypeId() {
		return paytypeId;
	}

	public void setPaytypeId(String paytypeId) {
		this.paytypeId = paytypeId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(String amountSum) {
		this.amountSum = amountSum;
	}

	public String getSourcecostRule() {
		return sourcecostRule;
	}

	public void setSourcecostRule(String sourcecostRule) {
		this.sourcecostRule = sourcecostRule;
	}

}