package com.huge.ihos.bm.budgetUpdata.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.ihos.bm.budgetUpdata.service.BudgetModelXfManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class BudgetModelXfPagedAction extends JqGridBaseAction implements Preparable {

	private BudgetModelXfManager budgetModelXfManager;
	private List<BudgetModelXf> budgetModelXfs;
	private BudgetModelXf budgetModelXf;
	private String xfId;
	
	private BudgetModelManager budgetModelManager;

	public void setBudgetModelManager(BudgetModelManager budgetModelManager) {
		this.budgetModelManager = budgetModelManager;
	}

	public void setBudgetModelXfManager(BudgetModelXfManager budgetModelXfManager) {
		this.budgetModelXfManager = budgetModelXfManager;
	}

	public List<BudgetModelXf> getbudgetModelXfs() {
		return budgetModelXfs;
	}

	public void setBudgetModelXfs(List<BudgetModelXf> budgetModelXfs) {
		this.budgetModelXfs = budgetModelXfs;
	}

	public BudgetModelXf getBudgetModelXf() {
		return budgetModelXf;
	}

	public void setBudgetModelXf(BudgetModelXf budgetModelXf) {
		this.budgetModelXf = budgetModelXf;
	}

	public String getXfId() {
		return xfId;
	}

	public void setXfId(String xfId) {
        this.xfId = xfId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String budgetModelXfGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = budgetModelXfManager
					.getBudgetModelXfCriteria(pagedRequests,filters);
			this.budgetModelXfs = (List<BudgetModelXf>) pagedRequests.getList();
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
			budgetModelXfManager.save(budgetModelXf);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "budgetModelXf.added" : "budgetModelXf.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (xfId != null) {
        	budgetModelXf = budgetModelXfManager.get(xfId);
        	this.setEntityIsNew(false);
        } else {
        	budgetModelXf = new BudgetModelXf();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String budgetModelXfGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					BudgetModelXf budgetModelXf = budgetModelXfManager.get(removeId);
					budgetModelXfManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("budgetModelXf.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkBudgetModelXfGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (budgetModelXf == null) {
			return "Invalid budgetModelXf Data";
		}

		return SUCCESS;

	}
	
	public String budgetModelXfRefresh(){
		List<PropertyFilter> modelfilters = new ArrayList<PropertyFilter>();
		modelfilters.add(new PropertyFilter("EQB_disabled", "false"));
		String periodYear = UserContextUtil.getUserContext().getPeriodYear();
		modelfilters.add(new PropertyFilter("SQS_modelId", "modelId not in (select modelId from bm_model_xf where period_year='"+periodYear+"')"));
		List<BudgetModel> budgetModels = budgetModelManager.getByFilters(modelfilters);
		for(BudgetModel bmm :budgetModels){
			BudgetModelXf budgetModelXf = new BudgetModelXf();
			budgetModelXf.setModelId(bmm);
			budgetModelXf.setPeriodYear(periodYear);
			budgetModelXf.setState(0);
			budgetModelXf.setXfDate(Calendar.getInstance().getTime());
			budgetModelXfManager.save(budgetModelXf);
		}
		return ajaxForward("刷新成功！");
	}
	
	public String budgetModel_Xf(){
		List<PropertyFilter> xfModelfilters = new ArrayList<PropertyFilter>();
		String periodYear = UserContextUtil.getUserContext().getPeriodYear();
		xfModelfilters.add(new PropertyFilter("EQS_periodYear", periodYear));
		List<BudgetModelXf> xfBudgetModelXfs = budgetModelXfManager.getByFilters(xfModelfilters);
		for(BudgetModelXf bmmXf :xfBudgetModelXfs){
			BudgetModel bmm = bmmXf.getModelId();
			int state = bmmXf.getState();
			Set<Department> departments = bmm.getDepartments();
			
		}
		return ajaxForward("刷新成功！");
	}
}

