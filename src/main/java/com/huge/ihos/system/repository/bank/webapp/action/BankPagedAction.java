package com.huge.ihos.system.repository.bank.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.repository.bank.model.Bank;
import com.huge.ihos.system.repository.bank.service.BankManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BankPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3226825206649377917L;
	private BankManager bankManager;
	private List<Bank> banks;
	private Bank bank;
	private String bankId;

	public BankManager getBankManager() {
		return bankManager;
	}

	public void setBankManager(BankManager bankManager) {
		this.bankManager = bankManager;
	}

	public List<Bank> getbanks() {
		return banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
        this.bankId = bankId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String bankGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = bankManager
					.getBankCriteria(pagedRequests,filters);
			this.banks = (List<Bank>) pagedRequests.getList();
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
			bankManager.save(bank);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "bank.added" : "bank.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (bankId != null) {
        	bank = bankManager.get(bankId);
        	this.setEntityIsNew(false);
        } else {
        	bank = new Bank();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String bankGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					bankManager.remove(removeId);
				}
				gridOperationMessage = this.getText("bank.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBankGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (bank == null) {
			return "Invalid bank Data";
		}

		return SUCCESS;

	}
}

