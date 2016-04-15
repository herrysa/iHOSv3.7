package com.huge.ihos.inout.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.ihos.inout.service.ChargeItemManager;
import com.huge.ihos.inout.service.SourcepayinManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.webapp.action.CBBaseAction;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.ExcelUtil;
import com.huge.util.OtherUtil;
import com.huge.util.javabean.JavaBeanUtil;
import com.huge.util.javabean.MapUtils;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class SourcepayinAction extends CBBaseAction {
	private SourcepayinManager sourcepayinManager;

	private PeriodManager periodManager;

	private DepartmentManager departmentManager;

	private OrgManager orgManager;

	private List sourcepayins;

	private Sourcepayin sourcepayin;

	private Long sourcePayinId;

	private String sumData;

	public Long getSourcePayinId() {
		return sourcePayinId;
	}

	private List dicOutInList;

	private List allPeriodList;

	private List deptList;

	private String amountSum;

	private String sourcepayinRule;

	private String orgNumber;

	//	private List chargeTypeList;

	//	public String autocomplete() throws UnsupportedEncodingException {
	//		q = URLDecoder.decode(q,"UTF-8");
	//		String[] columnName =new String[3];
	//		columnName[0]="chargeTypeName";
	//		columnName[1]="medOrLee";
	//		columnName[2]="displayMark";
	//		this.chargeTypeList = this.sourcepayinManager.searchDictionary("ChargeType", columnName, q," disabled=false  and (",")");
	//		return SUCCESS;
	//	}
	/**
	 * Grab the entity from the database before populating with request parameters
	 */
	private String herpType;
	private List<Branch> branches;
	private BranchManager branchManager;
	
	private List<Branch> kdBranches;
	private List<Branch> zxBranches;
	private List<Branch> srBranches;
	private String sqlStr = "";
	

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

	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	public void prepare() {
		/* if (getRequest().getMethod().equalsIgnoreCase("post")) {
		     // prevent failures on new
		     String sourcepayinId = getRequest().getParameter("sourcepayin.sourcePayinId");
		     if (sourcepayinId != null && !sourcepayinId.equals("")) {
		         sourcepayin = sourcepayinManager.get(new Long(sourcepayinId));
		     }
		 }*/
		//        this.currentPeriod = this.periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		this.dicOutInList = this.getDictionaryItemManager().getAllItemsByCode("outin");
		this.allPeriodList = this.getPeriodManager().getAll();
		this.deptList = departmentManager.getAll();
		this.clearSessionMessages();
		this.currentUser = this.userManager.getCurrentLoginUser();
		this.herpType = ContextUtil.herpType;
		amountSum = sourcepayinManager.getAmountSum(currentPeriod);
		List<Org> orgs = this.orgManager.getAllAvailable();
		if (orgs != null && !orgs.isEmpty()) {
			this.orgNumber = orgs.size() + "";
		}
	}

	/*   public String list() {
	       sourcepayins = sourcepayinManager.search(query, Sourcepayin.class);
	       return SUCCESS;
	   }*/

	public void setSourcePayinId(Long sourcePayinId) {
		this.sourcePayinId = sourcePayinId;
	}

	public Sourcepayin getSourcepayin() {
		return sourcepayin;
	}

	public void setSourcepayin(Sourcepayin sourcepayin) {
		this.sourcepayin = sourcepayin;
	}

	public String delete() {
		sourcepayinManager.remove(sourcepayin.getSourcePayinId());
		saveMessage(getText("sourcepayin.deleted"));

		return "edit_success";
	}

	public String edit() {
		String srBranchPriv = UserContextUtil.findUserDataPrivilegeStr("srdata_branch", "2");
		this.srBranches = branchManager.getAllAvailable(srBranchPriv);
		sourcepayinRule = this.getGlobalParamByKey("sourcepayinRule");
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String username = securityContext.getAuthentication().getName();
		User user = userManager.getUserByUsername(username);
		if (sourcePayinId != null) {
			sourcepayin = sourcepayinManager.get(sourcePayinId);
			if (sourcepayin.getOperator() == null) {
				sourcepayin.setOperator(user.getPerson());
			}
		} else {
			sourcepayin = new Sourcepayin();
			sourcepayin = this.prepareNew(sourcepayin);
			sourcepayin.setOperator(user.getPerson());
		}
		if("S2".equals(ContextUtil.herpType)){
			String operatorId = UserContextUtil.getLoginPersonId();
			String kdBranchPriv = UserContextUtil.findUserDataPrivilegeSql("kd_branch_dp", "1");
			String zxBranchPriv = UserContextUtil.findUserDataPrivilegeSql("zx_branch_dp", "1");
			String srBranchPriv1 = UserContextUtil.findUserDataPrivilegeSql("srdata_branch", "1"); 
			sqlStr = "sourcePayinId IN (SELECT t.sourcePayinId FROM t_sourcepayin t WHERE t.operatorId = '" + operatorId + "'";
			sqlStr += " OR t.kdBranchCode IN " + kdBranchPriv;
			sqlStr += " OR t.zxBranchCode IN " + zxBranchPriv;
			sqlStr += " OR t.srBranchCode in " + srBranchPriv1;
			sqlStr += " )";
		}
		
		return SUCCESS;
	}

	private Sourcepayin prepareNew(Sourcepayin sp) {
		//        sp.setCheckPeriod( this.periodManager.getCurrentPeriod().getPeriodCode() );
		sp.setCheckPeriod(this.getLoginPeriod());
		sp.setManual(true);
		sp.setProcessDate(new Date());
		return sp;
	}

	public String save() throws Exception {
		sourcepayin.setCheckPeriod(currentPeriod);
		if (cancel != null) {
			return "cancel";
		}

		if (delete != null) {
			return delete();
		}

		boolean isNew = (sourcepayin.getSourcePayinId() == null);

		/* if(sourcepayin.getOperator().getPersonId()==null||sourcepayin.getOperator().getPersonId().equals("")){
		 	sourcepayin.setOperator(null);
		 }*/
		sourcepayin.setOperator(this.getCurrentUser().getPerson());
		if (OtherUtil.measureNull(sourcepayin.getStatus())) {
			sourcepayin.setStatus("0");
		}
		
		String sourcepayinRule = this.getGlobalParamByKey("sourcepayinRule");
		if("2".equals(sourcepayinRule)) {
			if(sourcepayin.getSrBranch() != null && OtherUtil.measureNotNull(sourcepayin.getSrBranch().getBranchCode())) {
				Org srOrg = branchManager.get(sourcepayin.getSrBranch().getBranchCode()).getOrg();
				sourcepayin.setCostOrg(srOrg);
			}
		}
		
		/*if (OtherUtil.measureNull(sourcepayin.getCostOrg().getOrgCode())) {
			sourcepayin.setCostOrg(null);
		}*/
		if (sourcepayin.getKdBranch()!=null&&OtherUtil.measureNull(sourcepayin.getKdBranch().getBranchCode())) {
			sourcepayin.setKdBranch(null);
		}
		if (sourcepayin.getZxBranch()!=null&&OtherUtil.measureNull(sourcepayin.getZxBranch().getBranchCode())) {
			sourcepayin.setZxBranch(null);
		}
		if (sourcepayin.getSrBranch()!=null&&OtherUtil.measureNull(sourcepayin.getSrBranch().getBranchCode())) {
			sourcepayin.setSrBranch(null);
		}
		sourcepayin = sourcepayinManager.save(sourcepayin);
		sourcePayinId = sourcepayin.getSourcePayinId();
		String key = (isNew) ? "sourcepayin.added" : "sourcepayin.updated";
		saveMessage(getText(key));
		/*if(this.getContinueNew()!=null)
		        return "continueNew";
		else
				return "edit_success";*/
		boolean closeDailog = true;
		if (this.getContinueNew() != null) {
			closeDailog = false;
			//this.setNavTabId(this.getNavTabId()+"_temp");
		}
		return ajaxForward(true, getText(key), closeDailog);
	}

	public String sourcepayinGridList() {
		try {
			Map sumAuount = new HashMap();
			sumAuount.put("amount", "");
			HttpServletRequest req = getRequest();
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests.setInitSumData(sumAuount);
			/*String kdOrgPriv = UserContextUtil.findUserDataPrivilegeSql("kd_org_dp", "1");
			String zxOrgPriv = UserContextUtil.findUserDataPrivilegeSql("zx_org_dp", "1");
			String srDataOrg = UserContextUtil.findUserDataPrivilegeSql("srdata_org", "1");
			String sql = "sourcePayinId IN (SELECT t.sourcePayinId FROM t_sourcepayin t WHERE t.operatorId = '" + operatorId + "'";//
			sql += " OR t.kdOrgCode IN "+kdOrgPriv;
			sql += " OR t.zxOrgCode IN " + zxOrgPriv;
			sql += " OR t.srOrgCode in " + srDataOrg;
			sql += " )";
			filters.add(new PropertyFilter("SQS_sourcePayinId",sql));*/
			
			/*String operatorId = UserContextUtil.getLoginPersonId();
			String kdBranchPriv = UserContextUtil.findUserDataPrivilegeSql("kd_branch_dp", "1");
			String zxBranchPriv = UserContextUtil.findUserDataPrivilegeSql("zx_branch_dp", "1");
			String srBranchPriv = UserContextUtil.findUserDataPrivilegeSql("srdata_branch", "1"); 
			String sql = "sourcePayinId IN (SELECT t.sourcePayinId FROM t_sourcepayin t WHERE t.operatorId = '" + operatorId + "'";
			sql += " OR t.kdBranchCode IN " + kdBranchPriv;
			sql += " OR t.zxBranchCode IN " + zxBranchPriv;
			sql += " OR t.srBranchCode in " + srBranchPriv;
			sql += " )";
			filters.add(new PropertyFilter("SQS_sourcePayinId", sql));*/
			pagedRequests = sourcepayinManager.getAppManagerCriteriaWithSearch(pagedRequests, Sourcepayin.class, filters);
			this.sourcepayins = (List<Sourcepayin>) pagedRequests.getList();
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
			if(this.outputExcel){
				String colDefineStr = req.getParameter("colDefineStr");
				String title = req.getParameter("title");
		    	String excelFullPath = req.getRealPath( "//home//temporary//");
				List<Map<String, String>> dataList = JavaBeanUtil.convertListBean(this.sourcepayins,colDefineStr,"_");
			    //excelFullPath += this.fileFullPath;
				excelFullPath = ExcelUtil.outPutActionToExcel(colDefineStr,title,excelFullPath,dataList);
				this.sourcepayins = null;
				userData.put("excelFullPath", excelFullPath);
			}

		} catch (Exception e) {
			log.error("sourcepayinGridList Error", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String sourcepayinGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					sourcepayinManager.remove(removeId);
				}
				gridOperationMessage = this.getText("sourcepayin.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return SUCCESS;
				}
				sourcepayinManager.save(sourcepayin);
				String key = (oper.equals("add")) ? "sourcepayin.added" : "sourcepayin.updated";
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

	public String sourcepayinList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			boolean loginPeriodClosed = getLoginPeriodClosed();
			Iterator<MenuButton> it = menuButtons.iterator();
			while (it.hasNext()) {
				MenuButton button = it.next();
				if (loginPeriodClosed || !getLoginPeriodStarted()) {
					if(!"01020104".equals(button.getId()) && !"01020105".equals(button.getId()) ) {
						button.setEnable(false);
					}
				}
			}
			setMenuButtonsToJson(menuButtons);
/*			String branchPriv = UserContextUtil.findUserDataPrivilegeStr("branch_dp","1");
			this.branches = branchManager.getAllAvailable(branchPriv);*/
			String kdBranchPriv = UserContextUtil.findUserDataPrivilegeStr("kd_branch_dp", "1");
			this.kdBranches = branchManager.getAllAvailable(kdBranchPriv);
			String zxBranchPriv = UserContextUtil.findUserDataPrivilegeStr("zx_branch_dp", "1");
			this.zxBranches = branchManager.getAllAvailable(zxBranchPriv);
			String srBranchPriv = UserContextUtil.findUserDataPrivilegeStr("srdata_branch", "1");
			this.srBranches = branchManager.getAllAvailable(srBranchPriv);
		} catch (Exception e) {
			log.error("sourcepayinList error!", e);
		}
		return SUCCESS;
	}

	/**
	 * @TODO you should add some validation rules those are difficult on client side.
	 * @return
	 */
	private String isValid() {
		if (sourcepayin == null) {
			return "Invalid sourcepayin Data";
		}

		return SUCCESS;

	}

	private ChargeItemManager chargeItemManager;

	private List allChargeItemByTypeList;

	public ChargeItemManager getChargeItemManager() {
		return chargeItemManager;
	}

	public void setChargeItemManager(ChargeItemManager chargeItemManager) {
		this.chargeItemManager = chargeItemManager;
	}

	public List getAllChargeItemByTypeList() {
		return allChargeItemByTypeList;
	}

	public void setAllChargeItemByTypeList(List allChargeItemByTypeList) {
		this.allChargeItemByTypeList = allChargeItemByTypeList;
	}

	public String chargeItemByTypeSelect() {

		return this.SUCCESS;
	}

	public List getDicOutInList() {
		return dicOutInList;
	}

	public void setDicOutInList(List dicOutInList) {
		this.dicOutInList = dicOutInList;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public void setSourcepayinManager(SourcepayinManager sourcepayinManager) {
		this.sourcepayinManager = sourcepayinManager;
	}

	public List getAllPeriodList() {
		return allPeriodList;
	}

	public List getSourcepayins() {
		return sourcepayins;
	}

	public List getDeptList() {
		return deptList;
	}

	public String getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(String amountSum) {
		this.amountSum = amountSum;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	public String getSumData() {
		return sumData;
	}

	public void setSumData(String sumData) {
		this.sumData = sumData;
	}

	public String getSourcepayinRule() {
		return sourcepayinRule;
	}

	public void setSourcepayinRule(String sourcepayinRule) {
		this.sourcepayinRule = sourcepayinRule;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public List<Branch> getKdBranches() {
		return kdBranches;
	}

	public void setKdBranches(List<Branch> kdBranches) {
		this.kdBranches = kdBranches;
	}

	public List<Branch> getZxBranches() {
		return zxBranches;
	}

	public void setZxBranches(List<Branch> zxBranches) {
		this.zxBranches = zxBranches;
	}

	public List<Branch> getSrBranches() {
		return srBranches;
	}

	public void setSrBranches(List<Branch> srBranches) {
		this.srBranches = srBranches;
	}
	

}