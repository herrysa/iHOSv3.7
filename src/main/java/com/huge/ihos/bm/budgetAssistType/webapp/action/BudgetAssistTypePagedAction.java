package com.huge.ihos.bm.budgetAssistType.webapp.action;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.huge.ihos.bm.budgetAssistType.model.BudgetAssistType;
import com.huge.ihos.bm.budgetAssistType.service.BudgetAssistTypeManager;
import com.huge.ihos.system.configuration.AssistType.model.AssistType;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BudgetAssistTypePagedAction extends JqGridBaseAction implements Preparable {

	private BudgetAssistTypeManager budgetAssistTypeManager;
	private List<BudgetAssistType> budgetAssistTypes;
	private BudgetAssistType budgetAssistType;
	private String colCode;
	private String colName;
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	private AssistType assistType;

	public AssistType getAssistType() {
		return assistType;
	}

	public void setAssistType(AssistType assistType) {
		this.assistType = assistType;
	}

	public BudgetAssistTypeManager getBudgetAssistTypeManager() {
		return budgetAssistTypeManager;
	}

	public void setBudgetAssistTypeManager(BudgetAssistTypeManager budgetAssistTypeManager) {
		this.budgetAssistTypeManager = budgetAssistTypeManager;
	}

	public List<BudgetAssistType> getbudgetAssistTypes() {
		return budgetAssistTypes;
	}

	public void setBudgetAssistTypes(List<BudgetAssistType> budgetAssistTypes) {
		this.budgetAssistTypes = budgetAssistTypes;
	}

	public BudgetAssistType getBudgetAssistType() {
		return budgetAssistType;
	}

	public void setBudgetAssistType(BudgetAssistType budgetAssistType) {
		this.budgetAssistType = budgetAssistType;
	}

	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
        this.colCode = colCode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String budgetAssistTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = budgetAssistTypeManager
					.getBudgetAssistTypeCriteria(pagedRequests,filters);
			this.budgetAssistTypes = (List<BudgetAssistType>) pagedRequests.getList();
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
			budgetAssistTypeManager.save(budgetAssistType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "budgetAssistType.added" : "budgetAssistType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (colCode != null) {
        	budgetAssistType = budgetAssistTypeManager.get(colCode);
        	this.setEntityIsNew(false);
        } else {
        	budgetAssistType = new BudgetAssistType();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String budgetAssistTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String	removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BudgetAssistType budgetAssistType = budgetAssistTypeManager.get(new String(removeId));
					budgetAssistTypeManager.remove(new String(removeId));
					
				}
				gridOperationMessage = this.getText("budgetAssistType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if (oper.equals("edit")) {
				if(id.equals("new_row")){
					budgetAssistType = new BudgetAssistType();
					budgetAssistType.setColCode(colCode);
					budgetAssistType.setColName(colName);
					budgetAssistType.setAssistType(assistType);
					budgetAssistTypeManager.save(budgetAssistType);
					budgetAssistTypeManager.executeSql("ALTER TABLE bm_updatadetail ADD "+colCode+" varchar(50)");
				}else{
					budgetAssistType = budgetAssistTypeManager.get(colCode);
					if(!StringUtils.isEmpty(colName)){
						budgetAssistType.setColName(colName);
					}
					if(assistType!=null){
						budgetAssistType.setAssistType(assistType);
					}
					budgetAssistTypeManager.save(budgetAssistType);
				}
				
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBudgetAssistTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (budgetAssistType == null) {
			return "Invalid budgetAssistType Data";
		}

		return SUCCESS;

	}
}

