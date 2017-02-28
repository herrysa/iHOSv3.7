package com.huge.ihos.accounting.balance.webapp.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.accounting.account.service.AccountManager;
import com.huge.ihos.accounting.balance.model.Balance;
import com.huge.ihos.accounting.balance.model.BalanceAssist;
import com.huge.ihos.accounting.balance.service.BalanceAssistManager;
import com.huge.ihos.accounting.balance.service.BalanceManager;
import com.huge.ihos.accounting.kjyear.model.KjYear;
import com.huge.ihos.accounting.kjyear.service.KjYearManager;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.ihos.system.configuration.AssistType.service.AssistTypeManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.opensymphony.xwork2.Preparable;




public class BalanceAssistPagedAction extends JqGridBaseAction implements Preparable {

	private BalanceAssistManager balanceAssistManager;
	private List<BalanceAssist> balanceAssists;
	private BalanceAssist balanceAssist;
	private String balanceAssistId;
	private String balanceId;
	private Balance balance;
	private BalanceManager balanceManager;
	private List<AssistType> assistTypeList;
	private List<HashMap<String,String>> assistTypeValueList;
	private AssistTypeManager assistTypeManager;
	private AccountManager accountManager;
	private List<HashMap<String,Object>> balanceAssistList;
	private CopyManager copyManager;
	private boolean copyBalanceFlag;
	private KjYearManager kjYearManager;
	private HashMap<String,String> environment = new HashMap<String,String>();

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
	
	public void prepare() throws Exception {
		//SystemVariable sv = this.getCurrentSystemVariable();
		UserContext userContext = UserContextUtil.getUserContext();
		this.clearSessionMessages();
		environment.put("orgCode", userContext.getOrgCode());
		environment.put("copyCode", userContext.getCopyCode());
		environment.put("kjYear", userContext.getPeriodYear());
	}
	
	public String balanceAssistList(){
		log.debug("enter list method!");
		try {
			balance = balanceManager.get(balanceId);
			assistTypeList = accountManager.getAssitTypesByAcct(balance.getAccount().getAcctId());
			assistTypeValueList = balanceAssistManager.getAssistTypes(balance);
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
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String balanceAssistGridList() {
		log.debug("enter list method!");
		try {
//			balance = balanceManager.get(balanceId);
//			assistTypeList = assistTypeManager.getAssitTypesByAcct(balance.getAccount().getAcctId());
//			assistTypeValueList = balanceAssistManager.getAssistTypes(balance);
			balanceAssistList = balanceAssistManager.getAssistByBalance(balanceId);
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String saveBalanceAssist(){
		try {
			HttpServletRequest req = this.getRequest();
			String assistBalanceJson = req.getParameter("assistBalanceJson").toString();
			balanceAssistManager.saveBalanceAssist(assistBalanceJson,balanceId);
			balanceAssistManager.saveBalance(balanceId,"save");
			return ajaxForward(true,this.getText("balanceAssist.saved"),true);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ajaxForward(false,"期初余额辅助核算保存失败!",false);
		}
	}
	
	private String isValid() {
		if (balanceAssist == null) {
			return "Invalid balanceAssist Data";
		}
		return SUCCESS;
	}
/////////////////////////////get.set/////////////////////////////////////////
	public BalanceAssistManager getBalanceAssistManager() {
		return balanceAssistManager;
	}

	public void setBalanceAssistManager(BalanceAssistManager balanceAssistManager) {
		this.balanceAssistManager = balanceAssistManager;
	}

	public List<BalanceAssist> getBalanceAssists() {
		return balanceAssists;
	}

	public void setBalanceAssists(List<BalanceAssist> balanceAssists) {
		this.balanceAssists = balanceAssists;
	}

	public BalanceAssist getBalanceAssist() {
		return balanceAssist;
	}

	public void setBalanceAssist(BalanceAssist balanceAssist) {
		this.balanceAssist = balanceAssist;
	}

	public String getBalanceAssistId() {
		return balanceAssistId;
	}

	public void setBalanceAssistId(String balanceAssistId) {
		this.balanceAssistId = balanceAssistId;
	}

	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public BalanceManager getBalanceManager() {
		return balanceManager;
	}

	public void setBalanceManager(BalanceManager balanceManager) {
		this.balanceManager = balanceManager;
	}

	public List<HashMap<String, String>> getAssistTypeValueList() {
		return assistTypeValueList;
	}

	public void setAssistTypeValueList(List<HashMap<String, String>> assistTypeValueList) {
		this.assistTypeValueList = assistTypeValueList;
	}

	public AssistTypeManager getAssistTypeManager() {
		return assistTypeManager;
	}

	public void setAssistTypeManager(AssistTypeManager assistTypeManager) {
		this.assistTypeManager = assistTypeManager;
	}

	public List<HashMap<String, Object>> getBalanceAssistList() {
		return balanceAssistList;
	}

	public void setBalanceAssistList(List<HashMap<String, Object>> balanceAssistList) {
		this.balanceAssistList = balanceAssistList;
	}

	public CopyManager getCopyManager() {
		return copyManager;
	}

	public void setCopyManager(CopyManager copyManager) {
		this.copyManager = copyManager;
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

	public List<AssistType> getAssistTypeList() {
		return assistTypeList;
	}

	public void setAssistTypeList(List<AssistType> assistTypeList) {
		this.assistTypeList = assistTypeList;
	}
	
	
	
}

