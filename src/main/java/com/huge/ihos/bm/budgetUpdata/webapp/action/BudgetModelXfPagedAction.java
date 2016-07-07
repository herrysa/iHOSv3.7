package com.huge.ihos.bm.budgetUpdata.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.ihos.bm.budgetUpdata.service.BudgetModelXfManager;
import com.huge.ihos.bm.budgetUpdata.service.BudgetUpdataManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
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
	private BudgetUpdataManager budgetUpdataManager;

	public void setBudgetUpdataManager(BudgetUpdataManager budgetUpdataManager) {
		this.budgetUpdataManager = budgetUpdataManager;
	}

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
	
	String xfType ;
	
	public String getXfType() {
		return xfType;
	}

	public void setXfType(String xfType) {
		this.xfType = xfType;
	}

	public String budgetModel_Xf(){
		if(xfId!=null&&!"".equals(xfId)){
			List<PropertyFilter> xfModelfilters = new ArrayList<PropertyFilter>();
			String periodYear = UserContextUtil.getUserContext().getPeriodYear();
			xfModelfilters.add(new PropertyFilter("EQS_periodYear", periodYear));
			xfModelfilters.add(new PropertyFilter("INS_xfId", xfId));
			List<BudgetModelXf> xfBudgetModelXfs = budgetModelXfManager.getByFilters(xfModelfilters);
			for(BudgetModelXf bmmXf :xfBudgetModelXfs){
				BudgetModel bmm = bmmXf.getModelId();
				int state = bmmXf.getState();
				if(state>0){
					if("1".equals(xfType)){
						List<PropertyFilter> updatafilters = new ArrayList<PropertyFilter>();
						updatafilters.add(new PropertyFilter("EQS_modelXfId.xfId",bmmXf.getXfId()));
						List<BudgetUpdata> budgetUpdataList = budgetUpdataManager.getByFilters(updatafilters);
						for(BudgetUpdata bup : budgetUpdataList){
							bup.setState(4);
							budgetUpdataManager.executeSql("update bm_updatadetail set state=4 where updataId='"+bup.getUpdataId()+"'");
							budgetUpdataManager.save(bup);
						}
						bmmXf.setState(3);
						budgetModelXfManager.save(bmmXf);
						bmmXf = new BudgetModelXf();
						bmmXf.setModelId(bmm);
						bmmXf.setPeriodYear(periodYear);
						bmmXf.setState(0);
						bmmXf.setXfDate(Calendar.getInstance().getTime());
						bmmXf = budgetModelXfManager.save(bmmXf);
					}else{
						return ajaxForward(false,bmm.getModelName()+" 已下发！",false);
					}
				}else{
					bmmXf.setState(1);
				}
				Set<Department> departments = bmm.getDepartments();
				List<BudgetUpdata> budgetUpdatas = new ArrayList<BudgetUpdata>();
				for(Department dept : departments){
					BudgetUpdata budgetUpdata = new BudgetUpdata();
					budgetUpdata.setModelXfId(bmmXf);
					budgetUpdata.setDepartment(dept);
					budgetUpdata.setPeriodYear(periodYear);
					budgetUpdata.setState(0);
					//Person operator = UserContextUtil.getSessionUser().getPerson();
					//budgetUpdata.setOperator(operator);
					//budgetUpdata.setOptDate(Calendar.getInstance().getTime());
					budgetUpdatas.add(budgetUpdata);
				}
				budgetUpdataManager.saveAll(budgetUpdatas);
			}
			budgetModelXfManager.saveAll(xfBudgetModelXfs);
		}
		
		return ajaxForward("下发成功！");
	}
}

