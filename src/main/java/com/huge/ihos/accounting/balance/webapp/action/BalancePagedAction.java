package com.huge.ihos.accounting.balance.webapp.action;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.account.service.AccountManager;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.accounting.balance.service.BalanceManager;
import com.huge.ihos.accounting.balance.service.BalancePeriodManager;
import com.huge.ihos.accounting.kjyear.model.KjYear;
import com.huge.ihos.accounting.kjyear.service.KjYearManager;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.ihos.system.configuration.AssistType.service.AssistTypeManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.ihos.system.systemManager.period.service.PeriodYearManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BalancePagedAction extends JqGridBaseAction implements Preparable {

	private BalanceManager balanceManager;
	private BalancePeriodManager balancePeriodManager;
	private List<Balance> balances;
	private Balance balance;
	private String balanceId;
	private String acctId;
	private Account account;
	private List<AssistType> assistTypes;
	private Department assit0;
	private AssistTypeManager assistTypeManager;
	private AccountManager accountManager;
	private PeriodYearManager periodYearManager;
	private KjYearManager  kjYearManager;
	
	private CopyManager copyManager;
	private boolean copyBalanceFlag;
	private boolean initBalStatus;
	
	private List<HashMap<String, String>> isBalanceCheckList;
	
	
	private Double jieTotal = 0.00d;
	private Double daiTotal = 0.00d;
	private String jieStr = "";
	private String daiStr = "";
	private boolean isBalance = false;
	private HashMap<String,String> environment = new HashMap<String,String>();

	public void prepare() throws Exception {
		//SystemVariable sv = this.getCurrentSystemVariable();
		UserContext userContext = UserContextUtil.getUserContext();
		this.clearSessionMessages();
		environment.put("orgCode", userContext.getOrgCode());
		environment.put("copyCode", userContext.getCopyCode());
		environment.put("kjYear", userContext.getPeriodYear());
	}
	
	public String balanceList(){
		KjYear kjYear =kjYearManager.getKjYear(environment);
		if(kjYear == null){
			copyBalanceFlag = false;
		} else {
			if(kjYear.getIsOpen()){
				copyBalanceFlag = true;
			} else {
				copyBalanceFlag = false;
			}
		}
		Balance balance = new Balance();
		balance.setOrgCode(environment.get("orgCode"));
		balance.setCopyCode(environment.get("copyCode"));
		balance.setKjYear(environment.get("kjYear"));
		balance.setBeginDai(null);
		balance.setBeginJie(null);
		balance.setYearBalance(null);
		balance.setInitBalance(null);
		Boolean existInEnviro = balanceManager.existByExample(balance);
		if(existInEnviro){
			initBalStatus = true;
		} else {
			initBalStatus = false;
		}
		
		return SUCCESS;
	}
	public String balanceGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			filters.add(new PropertyFilter("EQS_kjYear",userContext.getPeriodYear()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = balanceManager
					.getBalanceCriteria(pagedRequests,filters);
			this.balances = (List<Balance>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String init(){
		List<Balance> balList = balanceManager.getAll(environment);
		if(balList.size()!=0){
			return ajaxForward(false,"期初余额已经初始化.不能重复提交",false);
		}
		Boolean initStatus = balanceManager.init(environment);
		if(initStatus){
			return ajaxForward(true,"初始化成功",false);
		} else {
			return ajaxForward(false,"初始化成功",false);
		}
	}
    
	public String balanceGridEdit() {
		try {
			Balance editBalance = balanceManager.get(balanceId);
			String yearBalanceStr = getRequest().getParameter("yearBalance")!=null? getRequest().getParameter("yearBalance"):"0";
    		String beginJieStr = getRequest().getParameter("beginJie")!=null? getRequest().getParameter("beginJie"):"0";
    		String beginDaiStr = getRequest().getParameter("beginDai")!=null? getRequest().getParameter("beginDai"):"0";
    		String initBalanceStr = getRequest().getParameter("initBalance")!=null? getRequest().getParameter("initBalance"):"0";
			Double beginDai = Double.parseDouble(beginDaiStr);
			Double beginJie = Double.parseDouble(beginJieStr);
			Double yearBalance = Double.parseDouble(yearBalanceStr);
			Double initBalance = Double.parseDouble(initBalanceStr);
			Map<String, Double> diffMap = new HashMap<String,Double>();
			Double diffYearBalance = yearBalance - editBalance.getYearBalance();
			Double diffDai = beginDai - editBalance.getBeginDai();
			Double diffJie = beginJie - editBalance.getBeginJie();
			Double diffInitBalance = initBalance - editBalance.getInitBalance();
			diffMap.put("diffYearBalance", diffYearBalance);
			diffMap.put("diffJie", diffJie);
			diffMap.put("diffDai", diffDai);
			diffMap.put("diffInitBalance", diffInitBalance);
			balanceManager.editBalance(editBalance, diffMap);
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBalanceGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String isBalanceGrid(){
		isBalanceCheckList = balanceManager.isBalance(environment);
		for(Map<String, String> map:isBalanceCheckList){
			String acctType = map.get("acctType");
			String balanceTotal = map.get("balanceTotal");
			if(map.get("direction").equals("借")){
				jieTotal+=Double.parseDouble(balanceTotal);
				if("".equals(jieStr)){
					jieStr+=acctType;
				}else{
					jieStr+="+"+acctType;
				}
			} else {
				daiTotal+= Double.parseDouble(balanceTotal);
				if("".equals(daiStr)){
					daiStr+=acctType;
				}else{
					daiStr+="+"+acctType;
				}
			}
		}
		if(jieTotal.equals(daiTotal)){
			isBalance = true;
		} else {
			isBalance = false;
		}
		jieStr = jieStr + "(借)：";
		daiStr = daiStr + "(贷)：";
		getRequest().setAttribute("jieStr", jieStr);
		getRequest().setAttribute("daiStr", daiStr);
		getRequest().setAttribute("jieTotal", NumberFormat.getInstance().format(jieTotal));
		getRequest().setAttribute("daiTotal", NumberFormat.getInstance().format(daiTotal));
		getRequest().setAttribute("isBalance", isBalance);
		return SUCCESS;
	}
	
	public String checkBalanceList(){
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String assitBalance = this.getRequest().getParameter("assitBalance");
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			filters.add(new PropertyFilter("EQS_kjYear",userContext.getPeriodYear()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			if(StringUtils.isEmpty(assitBalance)){
				pagedRequests = balanceManager.checkBalance(pagedRequests,filters);
			} else {
				pagedRequests = balanceManager.checkBalance(pagedRequests,filters,assitBalance);
			}
			this.balances = (List<Balance>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String setUpBalance(){
		try {
			//检查辅助核算
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			String assitBalance = "0";
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			filters.add(new PropertyFilter("EQS_kjYear",userContext.getPeriodYear()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = balanceManager.checkBalance(pagedRequests,filters,assitBalance);
			List<Balance> balanceList = (List<Balance>) pagedRequests.getList();
			if(balanceList.size() > 0){
				return ajaxForward(false, "辅助核算金额与期初余额不符,请检查.", false); 
			}
			
			//试算平衡
			isBalanceCheckList = balanceManager.isBalance(environment);
			for(Map<String, String> map:isBalanceCheckList){
				String balanceTotal = map.get("balanceTotal");
				if(map.get("direction").equals("借")){
					jieTotal+=Double.parseDouble(balanceTotal);
				} else {
					daiTotal+= Double.parseDouble(balanceTotal);
				}
			}
			if(jieTotal.equals(daiTotal)){
				isBalance = true;
			} else {
				isBalance = false;
			}
			if(!isBalance){
				return ajaxForward(false, "试算不平衡, 请重新调整.", false); 
			}
			Copy copy = copyManager.getCopyByCode(environment.get("copyCode"));
			KjYear kjYear = kjYearManager.getKjYear(environment);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(kjYear == null){
				kjYear = new KjYear();
				PeriodYear periodYear = periodYearManager.getPeriodYearByPlanAndYear(copy.getPeriodPlan().getPlanId(), environment.get("kjYear"));
				kjYear.setCopyCode(environment.get("copyCode"));
				kjYear.setOrgCode(environment.get("orgCode"));
				kjYear.setKjYear(environment.get("kjYear"));
				kjYear.setPeriodSub(periodYear);
				kjYear.setStartDate(sdf.format(periodYear.getBeginDate()));
				kjYear.setEndDate(sdf.format(periodYear.getEndDate()));
			} else {
				if(kjYear.getIsOpen()||kjYear.getIsClose()){
					return ajaxForward(false, "已经建账,不能重复操作", false); 
				}
			}
			kjYear.setIsOpen(true);
			kjYear.setIsClose(false);
			kjYearManager.save(kjYear);
			
			balancePeriodManager.updateMonthBalance(environment.get("orgCode"),environment.get("copyCode"), environment.get("kjYear"),"01");
			
			return ajaxForward(true, "期初建账成功", true); 
		} catch (Exception e) {
			log.error("checkBalanceGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String shutDownBalance(){
		try {
			
			KjYear kjYear =kjYearManager.getKjYear(environment);
			if(kjYear == null){
				return ajaxForward(false, "还没有建账,不能反记账", false); 
			} else {
				if(kjYear.getIsOpen()){
					if(kjYear.getIsClose()){
						return ajaxForward(false, "已经结账,不能反记账", false); 
					} else {
						kjYear.setIsOpen(false);
						kjYearManager.save(kjYear);
						return ajaxForward(true, "反记账成功", true); 
					}
				} else {
					return ajaxForward(false, "还没有建账,不能反记账", false); 
				}
			}
		} catch (Exception e) {
			log.error("checkBalanceGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private String isValid() {
		if (balance == null) {
			return "Invalid balance Data";
		}
		return SUCCESS;
	}

	/**************************get/set**********************************/
	
	public AccountManager getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public CopyManager getCopyManager() {
		return copyManager;
	}
	public void setCopyManager(CopyManager copyManager) {
		this.copyManager = copyManager;
	}
	public BalanceManager getBalanceManager() {
		return balanceManager;
	}

	public void setBalanceManager(BalanceManager balanceManager) {
		this.balanceManager = balanceManager;
	}
	
	public BalancePeriodManager getBalancePeriodManager() {
		return balancePeriodManager;
	}

	public void setBalancePeriodManager(BalancePeriodManager balancePeriodManager) {
		this.balancePeriodManager = balancePeriodManager;
	}

	public List<Balance> getbalances() {
		return balances;
	}

	public void setBalances(List<Balance> balances) {
		this.balances = balances;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}
	public String getBalanceId() {
		return balanceId;
	}
	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}
	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public List<Balance> getBalances() {
		return balances;
	}
	public List<AssistType> getAssistTypes() {
		return assistTypes;
	}
	public void setAssistTypes(List<AssistType> assistTypes) {
		this.assistTypes = assistTypes;
	}
	public Department getAssit0() {
		return assit0;
	}
	public void setAssit0(Department assit0) {
		this.assit0 = assit0;
	}

	public List<HashMap<String, String>> getIsBalanceCheckList() {
		return isBalanceCheckList;
	}
	public void setIsBalanceCheckList(
			List<HashMap<String, String>> isBalanceCheckList) {
		this.isBalanceCheckList = isBalanceCheckList;
	}
	public Double getJieTotal() {
		return jieTotal;
	}
	public void setJieTotal(Double jieTotal) {
		this.jieTotal = jieTotal;
	}
	public Double getDaiTotal() {
		return daiTotal;
	}
	public void setDaiTotal(Double daiTotal) {
		this.daiTotal = daiTotal;
	}
	public String getJieStr() {
		return jieStr;
	}
	public void setJieStr(String jieStr) {
		this.jieStr = jieStr;
	}
	public String getDaiStr() {
		return daiStr;
	}
	public void setDaiStr(String daiStr) {
		this.daiStr = daiStr;
	}
	public void setBalance(boolean isBalance) {
		this.isBalance = isBalance;
	}
	
	public AssistTypeManager getAssistTypeManager() {
		return assistTypeManager;
	}
	public void setAssistTypeManager(AssistTypeManager assistTypeManager) {
		this.assistTypeManager = assistTypeManager;
	}
	public boolean isBalance() {
		return isBalance;
	}
	public boolean isCopyBalanceFlag() {
		return copyBalanceFlag;
	}
	public void setCopyBalanceFlag(boolean copyBalanceFlag) {
		this.copyBalanceFlag = copyBalanceFlag;
	}
	public KjYearManager getKjYearManager() {
		return kjYearManager;
	}
	public void setKjYearManager(KjYearManager kjYearManager) {
		this.kjYearManager = kjYearManager;
	}

	public boolean isInitBalStatus() {
		return initBalStatus;
	}

	public void setInitBalStatus(boolean initBalStatus) {
		this.initBalStatus = initBalStatus;
	}
	
	public PeriodYearManager getPeriodYearManager() {
		return periodYearManager;
	}

	public void setPeriodYearManager(PeriodYearManager periodYearManager) {
		this.periodYearManager = periodYearManager;
	}
}

