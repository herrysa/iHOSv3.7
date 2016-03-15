package com.huge.ihos.accounting.account.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.AssistType.model.AssistType;
import com.huge.ihos.accounting.AssistType.service.AssistTypeManager;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.ihos.accounting.account.service.AccountManager;
import com.huge.ihos.accounting.account.service.AccountTypeManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;




public class AccountPagedAction extends JqGridBaseAction implements Preparable {

	private AccountManager accountManager;
	private AccountTypeManager accountTypeManager;
	private List<Account> accounts;
	private List<AccountType> accountTypes;
	private Account account;
	private String accountTreeStr;
	private String accountId;
	private String accountCode="";
	private List<AssistType> assistTypes;
	private AssistTypeManager assistTypeManager;
	private String assistType;
	private Boolean lossDirectionAsync;
	private String acountTypeId ="";
	private String assisttypeCode;
	private CopyManager copyManager;
	
	private String initAccountStatus;
	
	public String getInitAccountStatus() {
		return initAccountStatus;
	}

	public void setInitAccountStatus(String initAccountStatus) {
		this.initAccountStatus = initAccountStatus;
	}

	public String getAssisttypeCode() {
		return assisttypeCode;
	}

	public void setAssisttypeCode(String assisttypeCode) {
		this.assisttypeCode = assisttypeCode;
	}

	private Account acct;
	private Boolean hasFather = true;
	
	public Boolean getHasFather() {
		return hasFather;
	}

	public void setHasFather(Boolean hasFather) {
		this.hasFather = hasFather;
	}

	public Account getAcct() {
		return acct;
	}

	public void setAcct(Account acct) {
		this.acct = acct;
	}
	
	public String getAcountTypeId() {
		return acountTypeId;
	}

	public void setAcountTypeId(String acountTypeId) {
		this.acountTypeId = acountTypeId;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public Boolean getLossDirectionAsync() {
		return lossDirectionAsync;
	}

	public void setLossDirectionAsync(Boolean lossDirectionAsync) {
		this.lossDirectionAsync = lossDirectionAsync;
	}

	public String getAssistType() {
		return assistType;
	}

	public void setAssistType(String assistType) {
		this.assistType = assistType;
	}

	public List<AssistType> getAssistTypes() {
		return assistTypes;
	}

	public void setAssistTypes(List<AssistType> assistTypes) {
		this.assistTypes = assistTypes;
	}
	public List<AccountType> getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(List<AccountType> accountTypes) {
		this.accountTypes = accountTypes;
	}
//	public AssistTypeManager getAssistTypeManager() {
//		return assistTypeManager;
//	}

	public void setAssistTypeManager(AssistTypeManager assistTypeManager) {
		this.assistTypeManager = assistTypeManager;
	}

	public CopyManager getCopyManager() {
		return copyManager;
	}

	public void setCopyManager(CopyManager copyManager) {
		this.copyManager = copyManager;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public AccountTypeManager getAccountTypeManager() {
		return accountTypeManager;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public AssistTypeManager getAssistTypeManager() {
		return assistTypeManager;
	}

	private String id;
	List<ZTreeSimpleNode> ztreeList = new ArrayList<ZTreeSimpleNode>();
	List<ZTreeSimpleNode> nodes = new ArrayList<ZTreeSimpleNode>();

	public List<ZTreeSimpleNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<ZTreeSimpleNode> nodes) {
		this.nodes = nodes;
	}

	public List<ZTreeSimpleNode> getZtreeList() {
		return ztreeList;
	}

	public void setZtreeList(List<ZTreeSimpleNode> ztreeList) {
		this.ztreeList = ztreeList;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountTreeStr() {
		return accountTreeStr;
	}

	public void setAccountTreeStr(String accountTreeStr) {
		this.accountTreeStr = accountTreeStr;
	}

//	public AccountManager getAccountManager() {
//		return accountManager;
//	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

//	public AccountTypeManager getAccountTypeManager() {
//		return accountTypeManager;
//	}

	public void setAccountTypeManager(AccountTypeManager accountTypeManager) {
		this.accountTypeManager = accountTypeManager;
	}

	public List<Account> getaccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	
	public String checkAcctcode(){
		String acctCode = this.getRequest().getParameter("acctcode");
		/*super.setCodeRule(Account.class);
		if(!CodeUtil.isRoot(codeRule, acctCode)){
			SystemVariable systemVariable = this.getCurrentSystemVariable();
			String parentId = systemVariable.getOrgCode()+"_"+systemVariable.getCopyCode()+"_"+systemVariable.getKjYear()+"_"+CodeUtil.getFather(codeRule, acctCode);
			hasFather = accountManager.hasChildren(parentId)!=-1;	
		}*/
		return SUCCESS;
	}
	
	public String getAccountByNumber() {
		int number = Integer.parseInt(getRequest().getParameter("number"));
		String selectTreeId = getRequest().getParameter("selectId");
		String selectId = UserContextUtil.getUserContext().getOrgCode()+"_"+UserContextUtil.getUserContext().getCopyCode()+"_"+UserContextUtil.getUserContext().getPeriodYear()+"_"+(selectTreeId==null?"":selectTreeId);
		this.acct = accountManager.getAccountByNumber(number,selectId);
		return SUCCESS;
	}
	public String accountListPrepare(){
		entityName = "Account";
		//this.colShows = this.colShowManager.getByEntityName(entityName);
		HashMap<String,String> environment = new HashMap<String,String>();
		environment.put("orgCode", UserContextUtil.getUserContext().getOrgCode());
		environment.put("copyCode", UserContextUtil.getUserContext().getCopyCode());
		environment.put("kjYear", UserContextUtil.getUserContext().getPeriodYear());
		List<Account> acctList = accountManager.getAll(environment);
		if(acctList.size() == 0){
			initAccountStatus = "init";
		} else {
			initAccountStatus = "inited";
		}
		assistTypes = assistTypeManager.getAllExceptDisable();
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String accountGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			
			filters.add(new PropertyFilter("EQS_orgCode", UserContextUtil.getUserContext().getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", UserContextUtil.getUserContext().getCopyCode()));
			filters.add(new PropertyFilter("EQS_kjYear", UserContextUtil.getUserContext().getPeriodYear()));
			
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = accountManager
					.getAccountCriteria(pagedRequests,filters);
			this.accounts = (List<Account>) pagedRequests.getList();
			//super.setCodeRule(Account.class);
			for(Account account : accounts){
				String acctCode = account.getAcctCode();
				//int level = CodeUtil.getLevel(codeRule, acctCode);
				int level = 4;
				String appendixSpace = "";
				for(int i=1;i<level;i++){
					appendixSpace +="	";
				}
				account.setAcctname((appendixSpace+account.getAcctname()));
				String assisTypeName = "";
				assistType = account.getAssistTypes();
				if(assistType==null){
					continue;
				}
				String[] assistTypeArr = assistType.split(",");
				for(String assistTypeCode : assistTypeArr){
					assistTypeCode  = assistTypeCode.trim();
					if("".equals(assistTypeCode)){
						continue;
					}else{
						//assistTypeId=systemVariable.getOrgCode()+"_"+systemVariable.getKjYear()+"_"+assistTypeId.trim();
						AssistType assistType = assistTypeManager.get(assistTypeCode);
						if(assistType!=null){
							assisTypeName += assistType.getTypeName().trim()+"  ";
						}
					}
				}
				account.setAssistTypes(assisTypeName);
			}
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
		/*if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			String ockInfo =  UserContextUtil.getUserContext().getOrgCode()+"_"+UserContextUtil.getUserContext().getCopyCode()+"_"+systemVariable.getKjYear()+"_";
			if(this.isEntityIsNew()){
				account.setAcctId(ockInfo+account.getAcctCode());//修改Id包含帐套、单位、会计年
				account.setOrgCode(UserContextUtil.getUserContext().getOrgCode());
				account.setCopyCode(UserContextUtil.getUserContext().getCopyCode());
				account.setKjYear(UserContextUtil.getUserContext().getKjYear());
			}else{
				account = accountManager.get(ockInfo+account.getAcctCode());
			}
			if(assistType!=null) {
				assistType = ","+assistType.replaceAll(" ","")+",";
			}
			account.setAssistTypes(assistType);
			account.setDisabled(account.getDisabled());
			account.setLeaf(true);
			super.setCodeRule(Account.class);
			Account newAccount = accountManager.save(account,codeRule);
			if(lossDirectionAsync!=null && lossDirectionAsync){
				accountManager.updateAccountDisabled(account.getAcctId(),account.getLossDirection());
			}
			//修改父级leaf标志
			if(!CodeUtil.isRoot(codeRule, newAccount.getAcctCode())){
				String parentId = CodeUtil.getFather(CodeUtil.updateCodeRule(codeRule, 0, newAccount.getAcctId().length()-newAccount.getAcctCode().length()+Integer.parseInt(codeRule.split("-")[0])), newAccount.getAcctId());
				cascadeUpdateParentLeaf(parentId,false);
			}
			
			// 添加时向balance表插入数据
			List<Balance> balances = balanceManager.getBalancesByAcct(account.getAcctId());
			if(balances.size()==0){
				Balance balance = new Balance();
				balance.setAccount(account);
				balance.setOrgCode(account.getOrgCode());
				balance.setCopyCode(account.getCopyCode());
				balance.setKjYear(account.getKjYear());
				balance.setAcctCode(account.getAcctCode());
				balance.setBalanceType("B");
				Copy copy  = copyManager.getCopyByCode(account.getCopyCode());
				PeriodPlan plan = copy.getPeriodPlan();
				List<PeriodMonth> periodMonthList = periodMonthManager.getMonthByPlanAndYear(plan.getPlanId(), account.getKjYear());
				Set<BalancePeriod> periodSet = new HashSet<BalancePeriod>();
				for(PeriodMonth month : periodMonthList){
					String monthStr = month.getPeriodMonthCode();
					BalancePeriod bp = new BalancePeriod();
					bp.setPeriodMonth(monthStr);
					bp.setBalance(balance);
					periodSet.add(bp);
				}
				balance.setBalancePeriodSet(periodSet);
				balanceManager.save(balance);
			}
		} catch (Exception dre) {
			dre.printStackTrace();
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}*/
		String key = ((this.isEntityIsNew())) ? "account.added" : "account.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
    	/*assistTypes = assistTypeManager.getAllExceptDisable();
        if (accountId != null) { 
        	account = accountManager.get(accountId);
        	accountTypes = new ArrayList<AccountType>();
        	accountTypes.add(account.getAccttype());
        }else{
        	SystemVariable systemVariable = this.getCurrentSystemVariable();
        	accountTypes = accountTypeManager.getAllAccountTypeByOC(systemVariable.getOrgCode(), systemVariable.getCopyCode());
        }
        
    	if (accountId != null) { 
        	account = accountManager.get(accountId);
        	this.setEntityIsNew(false);
        	String assisTypes = account.getAssistTypes();
        	String[] assisTypeArr =null ;
        	if(assisTypes!=null){
        		assisTypeArr = assisTypes.split(",");
	        	for(AssistType assistType : assistTypes){
	        		for(String at:assisTypeArr){
	        			if(assistType.getTypeCode().equals(at.trim())){
	        				assistType.setChecked(true);
	        				break;
	        			}
	        		}
	        	}
        	}
        } else {
        	account = new Account();
        	if (!"".equals(accountCode)){
        		account.setAcctCode(accountCode);
        	}
        	this.setEntityIsNew(true);
        }
		super.setCodeRule(Account.class);*/
        return SUCCESS;
    }

	
	private void cascadeUpdateParentLeaf(String parentId,boolean leaf){
		Account parentAccount = accountManager.get(parentId);
		if(!leaf){//代表添加
			if(parentAccount.isLeaf()){
				parentAccount.setLeaf(leaf);
				accountManager.save(parentAccount);
			}
		}else{
			parentAccount.setLeaf(leaf);
			accountManager.save(parentAccount);
		}
	}

	private String isValid() {
		if (account == null) {
			return "Invalid account Data";
		}

		return SUCCESS;

	}
	
	public String makeAccountTree(){
		
		return SUCCESS;
	}
	
	public String makeAccountTreeWithCode(){
		return SUCCESS;
	}

}

