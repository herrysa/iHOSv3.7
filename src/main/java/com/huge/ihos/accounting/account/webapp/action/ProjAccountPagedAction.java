package com.huge.ihos.accounting.account.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.account.model.ProjAccount;
import com.huge.ihos.accounting.account.service.ProjAccountManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class ProjAccountPagedAction extends JqGridBaseAction implements Preparable {

	private ProjAccountManager projAccountManager;
	private List<ProjAccount> projAccounts;
	private ProjAccount projAccount;
	private String projAcctId;

	public ProjAccountManager getProjAccountManager() {
		return projAccountManager;
	}

	public void setProjAccountManager(ProjAccountManager projAccountManager) {
		this.projAccountManager = projAccountManager;
	}

	public List<ProjAccount> getprojAccounts() {
		return projAccounts;
	}

	public void setProjAccounts(List<ProjAccount> projAccounts) {
		this.projAccounts = projAccounts;
	}

	public ProjAccount getProjAccount() {
		return projAccount;
	}

	public void setProjAccount(ProjAccount projAccount) {
		this.projAccount = projAccount;
	}

	public String getProjAcctId() {
		return projAcctId;
	}

	public void setProjAcctId(String projAcctId) {
        this.projAcctId = projAcctId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String projAccountGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = projAccountManager
					.getProjAccountCriteria(pagedRequests,filters);
			this.projAccounts = (List<ProjAccount>) pagedRequests.getList();
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
			projAccountManager.save(projAccount);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "projAccount.added" : "projAccount.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (projAcctId != null) {
        	projAccount = projAccountManager.get(projAcctId);
        	this.setEntityIsNew(false);
        } else {
        	projAccount = new ProjAccount();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String projAccountGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					projAccountManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("projAccount.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkProjAccountGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (projAccount == null) {
			return "Invalid projAccount Data";
		}

		return SUCCESS;

	}
}

