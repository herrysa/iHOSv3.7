package com.huge.ihos.bm.budgetType.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetType.model.BudgetType;
import com.huge.ihos.bm.budgetType.service.BudgetTypeManager;
import com.huge.ihos.update.model.WorkScore;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BudgetTypePagedAction extends JqGridBaseAction implements Preparable {

	private BudgetTypeManager budgetTypeManager;
	private List<BudgetType> budgetTypes;
	private BudgetType budgetType;
	private BudgetType parentBudgetType;

	private String budgetTypeCode;
	private String parentId;

	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public BudgetType getParentBudgetType() {
		return parentBudgetType;
	}

	public void setParentBudgetType(BudgetType parentBudgetType) {
		this.parentBudgetType = parentBudgetType;
	}
	public BudgetTypeManager getBudgetTypeManager() {
		return budgetTypeManager;
	}

	public void setBudgetTypeManager(BudgetTypeManager budgetTypeManager) {
		this.budgetTypeManager = budgetTypeManager;
	}

	public List<BudgetType> getBudgetTypes() {
		return budgetTypes;
	}

	public void setBudgetTypes(List<BudgetType> budgetTypes) {
		this.budgetTypes = budgetTypes;
	}

	public BudgetType getBudgetType() {
		return budgetType;
	}

	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}

	public String getBudgetTypeCode() {
		return budgetTypeCode;
	}

	public void setBudgetTypeCode(String budgetTypeCode) {
        this.budgetTypeCode = budgetTypeCode;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String budgetTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = budgetTypeManager
					.getBudgetTypeCriteria(pagedRequests,filters);
			this.budgetTypes = (List<BudgetType>) pagedRequests.getList();
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
			budgetTypeManager.save(budgetType);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "budgetType.added" : "budgetType.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (budgetTypeCode != null) {
        	budgetType = budgetTypeManager.get(budgetTypeCode);
        	if(budgetType.getParentId()!=null){
        		parentId = budgetType.getParentId().getBudgetTypeCode();
        		parentBudgetType = budgetTypeManager.get(parentId);
        		budgetType.setParentId(parentBudgetType);
        	}
        	this.setEntityIsNew(false);
        } else {
        	budgetType = new BudgetType();
        	if(parentId!=null&&!"".equals(parentId)){
        		if(parentId.equals("-1")){
        			parentBudgetType = new BudgetType();
        			parentBudgetType.setBudgetTypeCode("-1");
        			parentBudgetType.setBudgetTypeName("预算类型");
        		}else{
        			parentBudgetType = budgetTypeManager.get(parentId);
        			budgetType.setBudgetTypeCode(parentBudgetType.getBudgetTypeCode());
        		}
        		/*Integer l = parentBudgetType.getClevel();
        		if(l!=null&&!"-1".equals(parentBudgetType.getId())){
        			budgetType.setClevel(l+1);
        		}*/
        		budgetType.setParentId(parentBudgetType);
        	}
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String budgetTypeGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BudgetType budgetType = budgetTypeManager.get(removeId);
					budgetTypeManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("budgetType.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBudgetTypeGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	List<Map<String, String>> budgetTypeTreeNodes;
	
	public List<Map<String, String>> getBudgetTypeTreeNodes() {
		return budgetTypeTreeNodes;
	}

	public void setBudgetTypeTreeNodes(List<Map<String, String>> budgetTypeTreeNodes) {
		this.budgetTypeTreeNodes = budgetTypeTreeNodes;
	}

	public String getBudgetTypeTree(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","false"));
		List<BudgetType> budgetTypes = budgetTypeManager.getByFilters(filters);
		budgetTypeTreeNodes = new ArrayList<Map<String,String>>();
		Map<String, String> budgetTypeRoot = new HashMap<String, String>();
		budgetTypeRoot.put("id", "-1");
		budgetTypeRoot.put("name", "预算类型");
		budgetTypeTreeNodes.add(budgetTypeRoot);
		for(BudgetType budgetType : budgetTypes){
			Map<String, String> budgetTypeTemp = new HashMap<String, String>();
			budgetTypeTemp.put("id", budgetType.getBudgetTypeCode());
			budgetTypeTemp.put("name", budgetType.getBudgetTypeName());
			if(budgetType.getParentId()==null||"".equals(budgetType.getParentId().getBudgetTypeCode())){
				budgetTypeTemp.put("pId", "-1");
			}else{
				budgetTypeTemp.put("pId",budgetType.getParentId().getBudgetTypeCode());
			}
			budgetTypeTreeNodes.add(budgetTypeTemp);
		}
		return SUCCESS;
	}

	private String isValid() {
		if (budgetType == null) {
			return "Invalid budgetType Data";
		}

		return SUCCESS;

	}
}

