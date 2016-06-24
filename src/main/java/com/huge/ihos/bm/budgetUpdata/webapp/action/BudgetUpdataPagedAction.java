package com.huge.ihos.bm.budgetUpdata.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.ihos.bm.budgetUpdata.service.BudgetUpdataManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BudgetUpdataPagedAction extends JqGridBaseAction implements Preparable {

	private BudgetUpdataManager budgetUpdataManager;
	private List<BudgetUpdata> budgetUpdatas;
	private BudgetUpdata budgetUpdata;
	private String updataId;

	public BudgetUpdataManager getBudgetUpdataManager() {
		return budgetUpdataManager;
	}

	public void setBudgetUpdataManager(BudgetUpdataManager budgetUpdataManager) {
		this.budgetUpdataManager = budgetUpdataManager;
	}

	public List<BudgetUpdata> getbudgetUpdatas() {
		return budgetUpdatas;
	}

	public void setBudgetUpdatas(List<BudgetUpdata> budgetUpdatas) {
		this.budgetUpdatas = budgetUpdatas;
	}

	public BudgetUpdata getBudgetUpdata() {
		return budgetUpdata;
	}

	public void setBudgetUpdata(BudgetUpdata budgetUpdata) {
		this.budgetUpdata = budgetUpdata;
	}

	public String getUpdataId() {
		return updataId;
	}

	public void setUpdataId(String updataId) {
        this.updataId = updataId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String budgetUpdataGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = budgetUpdataManager
					.getBudgetUpdataCriteria(pagedRequests,filters);
			this.budgetUpdatas = (List<BudgetUpdata>) pagedRequests.getList();
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
			budgetUpdataManager.save(budgetUpdata);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "budgetUpdata.added" : "budgetUpdata.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (updataId != null) {
        	budgetUpdata = budgetUpdataManager.get(updataId);
        	this.setEntityIsNew(false);
        } else {
        	budgetUpdata = new BudgetUpdata();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String budgetUpdataGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BudgetUpdata budgetUpdata = budgetUpdataManager.get(removeId);
					budgetUpdataManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("budgetUpdata.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBudgetUpdataGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (budgetUpdata == null) {
			return "Invalid budgetUpdata Data";
		}

		return SUCCESS;

	}
}

