package com.huge.ihos.bm.index.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetType.model.BudgetType;
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
	private BudgetIndex parentBudgetIndex;
	public BudgetIndex getParentBudgetIndex() {
		return parentBudgetIndex;
	}

	public void setParentBudgetIndex(BudgetIndex parentBudgetIndex) {
		this.parentBudgetIndex = parentBudgetIndex;
	}

	private String indexCode;
	private String parentId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

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
			pagedRequests = budgetIndexManager.getAppManagerCriteriaWithSearch(pagedRequests, BudgetIndex.class, filters, group_on);
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
			parentBudgetIndex = budgetIndex.getParentId();
			if("-1".equals(parentBudgetIndex.getIndexCode())){
				budgetIndex.setParentId(null);
			}
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
        	if(budgetIndex.getParentId()!=null){
        		parentId = budgetIndex.getParentId().getIndexCode();
        		parentBudgetIndex = budgetIndexManager.get(parentId);
        		budgetIndex.setParentId(parentBudgetIndex);
        	}
        	this.setEntityIsNew(false);
        } else {
        	budgetIndex = new BudgetIndex();
        	if(parentId!=null&&!"".equals(parentId)){
        		if(parentId.equals("-1")){
        			parentBudgetIndex = new BudgetIndex();
        			parentBudgetIndex.setIndexCode("-1");
        			parentBudgetIndex.setIndexName("预算指标");
        		}else{
        			parentBudgetIndex = budgetIndexManager.get(parentId);
        			budgetIndex.setIndexCode(parentBudgetIndex.getIndexCode());
        		}
        		/*Integer l = parentBudgetType.getClevel();
        		if(l!=null&&!"-1".equals(parentBudgetType.getId())){
        			budgetType.setClevel(l+1);
        		}*/
        		budgetIndex.setParentId(parentBudgetIndex);
        	}
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
	
	private List<Map<String,String>> budgetIndexTreeNodes;
	
	public List<Map<String,String>> getBudgetIndexTreeNodes() {
		return budgetIndexTreeNodes;
	}

	public void setBudgetIndexTreeNodes(List<Map<String,String>> budgetIndexTreeNodes) {
		this.budgetIndexTreeNodes = budgetIndexTreeNodes;
	}

	public String getBudgetIndexTree(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled","false"));
		List<BudgetIndex> budgetIndexs = budgetIndexManager.getByFilters(filters);
		budgetIndexTreeNodes = new ArrayList<Map<String,String>>();
		Map<String, String> budgetIndexRoot = new HashMap<String, String>();
		budgetIndexRoot.put("id", "-1");
		budgetIndexRoot.put("name", "预算指标");
		budgetIndexTreeNodes.add(budgetIndexRoot);
		for(BudgetIndex budgetIndex : budgetIndexs){
			Map<String, String> budgetIndexTemp = new HashMap<String, String>();
			budgetIndexTemp.put("id", budgetIndex.getIndexCode());
			budgetIndexTemp.put("name", budgetIndex.getIndexName());
			if(budgetIndex.getParentId()==null||"".equals(budgetIndex.getParentId().getIndexCode())){
				budgetIndexTemp.put("pId", "-1");
			}else{
				budgetIndexTemp.put("pId",budgetIndex.getParentId().getIndexCode());
			}
			budgetIndexTreeNodes.add(budgetIndexTemp);
		}
		return SUCCESS;
	}
}

