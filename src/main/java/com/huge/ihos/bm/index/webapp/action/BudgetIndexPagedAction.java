package com.huge.ihos.bm.index.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.ihos.bm.index.service.BudgetIndexManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BudgetIndexPagedAction extends JqGridBaseAction implements Preparable {

	private BudgetIndexManager budgetIndexManager;
	private List<BudgetIndex> budgetIndices;
	private BudgetIndex budgetIndex;
	private String indexCode;

	public BudgetIndexManager getBudgetIndexManager() {
		return budgetIndexManager;
	}

	public void setBudgetIndexManager(BudgetIndexManager budgetIndexManager) {
		this.budgetIndexManager = budgetIndexManager;
	}

	public List<BudgetIndex> getbudgetIndices() {
		return budgetIndices;
	}

	public void setBudgetIndexs(List<BudgetIndex> budgetIndices) {
		this.budgetIndices = budgetIndices;
	}

	public BudgetIndex getBudgetIndex() {
		return budgetIndex;
	}

	public void setBudgetIndex(BudgetIndex budgetIndex) {
		this.budgetIndex = budgetIndex;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String budgetIndexGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = budgetIndexManager
					.getBudgetIndexCriteria(pagedRequests,filters);
			this.budgetIndices = (List<BudgetIndex>) pagedRequests.getList();
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
			budgetIndexManager.save(budgetIndex);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "budgetIndex.added" : "budgetIndex.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (indexCode != null) {
        	budgetIndex = budgetIndexManager.get(indexCode);
        	this.setEntityIsNew(false);
        } else {
        	budgetIndex = new BudgetIndex();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String budgetIndexGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BudgetIndex budgetIndex = budgetIndexManager.get(removeId);
					budgetIndexManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("budgetIndex.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBudgetIndexGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (budgetIndex == null) {
			return "Invalid budgetIndex Data";
		}

		return SUCCESS;

	}
}

