package com.huge.ihos.accounting.account.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.account.model.Account;
import com.huge.ihos.accounting.account.model.AccountType;
import com.huge.ihos.accounting.account.model.ZTreeAccountNode;
import com.huge.ihos.accounting.account.service.AccountManager;
import com.huge.ihos.accounting.account.service.AccountTypeManager;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.accounting.balance.model.BalancePeriod;
import com.huge.ihos.accounting.balance.service.BalanceManager;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.ihos.system.configuration.AssistType.service.AssistTypeManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.ihos.system.systemManager.period.service.PeriodMonthManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.util.CodeUtil;
import com.huge.util.TestTimer;
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
	private BalanceManager balanceManager;
	private CopyManager copyManager;
	private PeriodMonthManager periodMonthManager;
	
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

	public PeriodMonthManager getPeriodMonthManager() {
		return periodMonthManager;
	}

	public void setPeriodMonthManager(PeriodMonthManager periodMonthManager) {
		this.periodMonthManager = periodMonthManager;
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
		codeRule = "4-2-2-2-2";
	}
	
	public String checkAcctcode(){
		String acctCode = this.getRequest().getParameter("acctcode");
		//super.setCodeRule(Account.class);
		//codeRule = "2-2-2-2-2-2";
		if(!CodeUtil.isRoot(codeRule, acctCode)){
			UserContext userContext = UserContextUtil.getUserContext();
			String parentId = userContext.getOrgCode()+"_"+userContext.getCopyCode()+"_"+userContext.getPeriodYear()+"_"+CodeUtil.getFather(codeRule, acctCode);
			hasFather = accountManager.hasChildren(parentId)!=-1;	
		}
		return SUCCESS;
	}
	
	public String getAccountByNumber() {
		int number = Integer.parseInt(getRequest().getParameter("number"));
		String selectTreeId = getRequest().getParameter("selectId");
		UserContext userContext = UserContextUtil.getUserContext();
		String selectId = userContext.getOrgCode()+"_"+userContext.getCopyCode()+"_"+userContext.getPeriodYear()+"_"+(selectTreeId==null?"":selectTreeId);
		this.acct = accountManager.getAccountByNumber(number,selectId);
		return SUCCESS;
	}
	public String accountListPrepare(){
		entityName = "Account";
		//this.colShows = this.colShowManager.getByEntityName(entityName);
		UserContext userContext = UserContextUtil.getUserContext();
		HashMap<String,String> environment = new HashMap<String,String>();
		environment.put("orgCode", userContext.getOrgCode());
		environment.put("copyCode", userContext.getCopyCode());
		environment.put("kjYear", userContext.getPeriodYear());
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
			UserContext userContext = UserContextUtil.getUserContext();
			
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			filters.add(new PropertyFilter("EQS_kjYear", userContext.getPeriodYear()));
			
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = accountManager
					.getAccountCriteria(pagedRequests,filters);
			this.accounts = (List<Account>) pagedRequests.getList();
			//super.setCodeRule(Account.class);
			for(Account account : accounts){
				String acctCode = account.getAcctCode();
				int level = CodeUtil.getLevel(codeRule, acctCode);
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
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			String ockInfo =  userContext.getOrgCode()+"_"+userContext.getCopyCode()+"_"+userContext.getPeriodYear()+"_";
			if(this.isEntityIsNew()){
				account.setAcctId(ockInfo+account.getAcctCode());//修改Id包含帐套、单位、会计年
				account.setOrgCode(userContext.getOrgCode());
				account.setCopyCode(userContext.getCopyCode());
				account.setKjYear(userContext.getPeriodYear());
			}else{
				account = accountManager.get(ockInfo+account.getAcctCode());
			}
			if(assistType!=null) {
				assistType = ","+assistType.replaceAll(" ","")+",";
			}
			account.setAssistTypes(assistType);
			account.setDisabled(account.getDisabled());
			account.setLeaf(true);
			//super.setCodeRule(Account.class);
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
		}
		String key = ((this.isEntityIsNew())) ? "account.added" : "account.updated";
		return ajaxForward(this.getText(key));
	}
    public BalanceManager getBalanceManager() {
		return balanceManager;
	}

	public void setBalanceManager(BalanceManager balanceManager) {
		this.balanceManager = balanceManager;
	}

	public String edit() {
    	assistTypes = assistTypeManager.getAllExceptDisable();
        if (accountId != null) { 
        	account = accountManager.get(accountId);
        	accountTypes = new ArrayList<AccountType>();
        	accountTypes.add(account.getAccttype());
        }else{
        	UserContext userContext = UserContextUtil.getUserContext();
        	accountTypes = accountTypeManager.getAllAccountTypeByOC(userContext.getOrgCode(), userContext.getCopyCode());
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
		//super.setCodeRule(Account.class);
        return SUCCESS;
    }
	public String accountGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					// 删除之前进行判断是否为末级
					Account removeAccount = accountManager.get(removeId);
					if(!removeAccount.isLeaf()){
						gridOperationMessage = "科目\""+removeAccount.getAcctname()+"\"不是末级，不能删除";
						return ajaxForward(true, gridOperationMessage, false);
					}
					log.debug("Delete Customer " + removeId);
					accountManager.remove(removeId);
					//检查刚才删除的科目父级下是否还有子级
					//super.setCodeRule(Account.class);
					String parentId = CodeUtil.getFather(CodeUtil.updateCodeRule(codeRule, 0, removeAccount.getAcctId().length()-removeAccount.getAcctCode().length()+Integer.parseInt(codeRule.split("-")[0])), removeAccount.getAcctId());
					if(accountManager.hasChildren(parentId)==0){
						cascadeUpdateParentLeaf(parentId,true);
					}
				}
				gridOperationMessage = this.getText("account.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkAccountGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
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
		try {
			HashMap<String,String> environment = new HashMap<String,String>();
			UserContext userContext = UserContextUtil.getUserContext();
			environment.put("orgCode", userContext.getOrgCode());
			environment.put("copyCode", userContext.getCopyCode());
			environment.put("kjYear", userContext.getPeriodYear());
			List<AccountType> accountTypes = accountTypeManager.getAll(environment);
			
			List<Account> accountes = accountManager.getAll(environment);
			//TODO 将来改成接收当前单位和套账的环境变量
			Iterator<AccountType> itr = accountTypes.iterator();
	        
	        
	        ZTreeSimpleNode rtRoot = new ZTreeSimpleNode();
	        rtRoot.setName( "所有科目");
            rtRoot.setId( "-1" );
            rtRoot.setOpen(true);
            ztreeList.add( rtRoot );
			while ( itr.hasNext() ) {
	        	AccountType accountType = itr.next();
	        	ZTreeSimpleNode rt = new ZTreeSimpleNode();
	            rt.setName( accountType.getAccounttype().trim() );
	            rt.setId( accountType.getAccttypeId() );
	            rt.setpId("-1");
	            ztreeList.add( rt );
	        }
			Iterator<Account> itrAt = accountes.iterator();
			//super.setCodeRule(Account.class);
			while ( itrAt.hasNext() ) {
	        	Account account = itrAt.next();
	        	String accountCode = account.getAcctCode();
	        	ZTreeSimpleNode rt = new ZTreeSimpleNode();
	            rt.setName( account.getAcctname().trim() );
	            rt.setId( accountCode.trim() );
	            if(CodeUtil.isRoot(codeRule, accountCode.trim())){
	            	rt.setpId( account.getAccttype().getAccttypeId() );
	            }else{
	            	rt.setpId(CodeUtil.getFather(codeRule, accountCode));
	            }
	            
	            ztreeList.add( rt );
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String makeAccountTreeWithCode(){
		try {
			HashMap<String,String> environment = new HashMap<String,String>();
			UserContext userContext = UserContextUtil.getUserContext();
			environment.put("orgCode", userContext.getOrgCode());
			environment.put("copyCode", userContext.getCopyCode());
			environment.put("kjYear", userContext.getPeriodYear());
			List<AccountType> accountTypes = accountTypeManager.getAll(environment);
			
			TestTimer tt = new TestTimer("accountTree");
			tt.begin();
			List<Account> accountes = accountManager.getAll(environment);
			tt.done();
			//TODO 将来改成接收当前单位和套账的环境变量
			Iterator<AccountType> itr = accountTypes.iterator();
	        
	        
			ZTreeAccountNode rtRoot = new ZTreeAccountNode();
	        rtRoot.setName( "所有科目");
            rtRoot.setId( "-1" );
            rtRoot.setOpen(true);
            nodes.add( rtRoot );
			while ( itr.hasNext() ) {
	        	AccountType accountType = itr.next();
	        	ZTreeSimpleNode rt = new ZTreeSimpleNode();
	            rt.setName( accountType.getAccounttype().trim() );
	            rt.setId( accountType.getAccttypeId() );
	            rt.setpId("-1");
	            nodes.add( rt );
	        }
			Iterator<Account> itrAt = accountes.iterator();
			//super.setCodeRule(Account.class);
			while ( itrAt.hasNext() ) {
	        	Account account = itrAt.next();
	        	String accountCode = account.getAcctCode();
	        	ZTreeAccountNode rt = new ZTreeAccountNode();
	            rt.setName( "["+accountCode+"]"+account.getAcctname().trim() );
	            rt.setId( accountCode.trim() );
	            rt.setAcctFullname(account.getAcctFullname());
	            rt.setAssistTypes(account.getAssistTypes());
	            rt.setAcctId(account.getAcctId());
	            if(CodeUtil.isRoot(codeRule, accountCode.trim())){
	            	rt.setpId( account.getAccttype().getAccttypeId() );
	            }else{
	            	rt.setpId(CodeUtil.getFather(codeRule, accountCode));
	            }
	            
	            nodes.add( rt );
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	
	public String initAccount(){
		try {
			HashMap<String,String> environment = new HashMap<String,String>();
			UserContext userContext = UserContextUtil.getUserContext();
			environment.put("orgCode", userContext.getOrgCode());
			environment.put("copyCode", userContext.getCopyCode());
			environment.put("kjYear", userContext.getPeriodYear());
			accountManager.initAccount(environment);
			return ajaxForward(true, "初始化成功", true);
		} catch (Exception e) {
			log.error("init account error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

}

