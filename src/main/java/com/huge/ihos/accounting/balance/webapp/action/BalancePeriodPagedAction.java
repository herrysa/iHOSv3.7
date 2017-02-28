package com.huge.ihos.accounting.balance.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.accounting.balance.model.BalancePeriod;
import com.huge.ihos.accounting.balance.service.BalancePeriodManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BalancePeriodPagedAction extends JqGridBaseAction implements Preparable {

	private BalancePeriodManager balancePeriodManager;
	private List<BalancePeriod> balancePeriods;
	private BalancePeriod balancePeriod;
	private String balancePeriodId;

	public BalancePeriodManager getBalancePeriodManager() {
		return balancePeriodManager;
	}

	public void setBalancePeriodManager(BalancePeriodManager balancePeriodManager) {
		this.balancePeriodManager = balancePeriodManager;
	}

	public List<BalancePeriod> getbalancePeriods() {
		return balancePeriods;
	}

	public void setBalancePeriods(List<BalancePeriod> balancePeriods) {
		this.balancePeriods = balancePeriods;
	}

	public BalancePeriod getBalancePeriod() {
		return balancePeriod;
	}

	public void setBalancePeriod(BalancePeriod balancePeriod) {
		this.balancePeriod = balancePeriod;
	}

	public String getBalancePeriodId() {
		return balancePeriodId;
	}

	public void setBalancePeriodId(String balancePeriodId) {
        this.balancePeriodId = balancePeriodId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String balancePeriodGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = balancePeriodManager
					.getBalancePeriodCriteria(pagedRequests,filters);
			this.balancePeriods = (List<BalancePeriod>) pagedRequests.getList();
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
			balancePeriodManager.save(balancePeriod);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "balancePeriod.added" : "balancePeriod.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (balancePeriodId != null) {
        	balancePeriod = balancePeriodManager.get(balancePeriodId);
        	this.setEntityIsNew(false);
        } else {
        	balancePeriod = new BalancePeriod();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String balancePeriodGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					balancePeriodManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("balancePeriod.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBalancePeriodGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (balancePeriod == null) {
			return "Invalid balancePeriod Data";
		}
		return SUCCESS;

	}
}

