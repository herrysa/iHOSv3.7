package com.huge.ihos.bm.budgetUpdata.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.bm.budgetModel.model.BmModelProcess;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.ihos.bm.budgetModel.service.BudgetModelManager;
import com.huge.ihos.bm.budgetUpdata.model.BmProcessColumn;
import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.ihos.bm.budgetUpdata.service.BudgetModelXfManager;
import com.huge.ihos.bm.budgetUpdata.service.BudgetUpdataManager;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessStepManager;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.user.model.User;
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
	private Integer state;
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	private BudgetModelManager budgetModelManager;
	private BudgetUpdataManager budgetUpdataManager;
	
	private BusinessProcessStepManager businessProcessStepManager;
	
	private DepartmentManager departmentManager;
	
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public void setBusinessProcessStepManager(
			BusinessProcessStepManager businessProcessStepManager) {
		this.businessProcessStepManager = businessProcessStepManager;
	}

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
	List<BusinessProcessStep> bsStepList;
	public List<BusinessProcessStep> getBsStepList() {
		return bsStepList;
	}

	public void setBsStepList(List<BusinessProcessStep> bsStepList) {
		this.bsStepList = bsStepList;
	}
	
	List<Map<String,Object>> yearList;

	public List<Map<String,Object>> getYearList() {
		return yearList;
	}

	public void setYearList(List<Map<String,Object>> yearList) {
		this.yearList = yearList;
	}

	public String budgetModelXfList(){
		try {
			String bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmCheckProcess");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			filters.add(new PropertyFilter("OAS_state",""));
			bsStepList = businessProcessStepManager.getByFilters(filters);
			for(BusinessProcessStep businessProcessStep : bsStepList){
				if(businessProcessStep.getIsEnd()==null||!businessProcessStep.getIsEnd()){
					businessProcessStep.setStepName(businessProcessStep.getStepName()+"中");
				}
			}
			String yearSql = "select period_year periodYear from bm_model_xf group by period_year ORDER BY period_year DESC";
			yearList = businessProcessStepManager.getBySqlToMap(yearSql);
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
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
			for(BudgetModelXf budgetModelXf : budgetModelXfs){
				String bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmCheckProcess");
				List<PropertyFilter> bsStepfilters = new ArrayList<PropertyFilter>();
				bsStepfilters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
				bsStepfilters.add(new PropertyFilter("OAS_state",""));
				bsStepList = businessProcessStepManager.getByFilters(bsStepfilters);
				Map<String, Object> stepMap = new HashMap<String, Object>();
				for(BusinessProcessStep businessProcessStep : bsStepList){
					String stepSql = "select count(*) count from bm_updata up where up.modelXfId='"+budgetModelXf.getXfId()+"' and up.state="+businessProcessStep.getState();
					List<Map<String,Object>> rsList = budgetModelXfManager.getBySqlToMap(stepSql);
					if(rsList!=null&rsList.size()>0){
						stepMap.put(businessProcessStep.getStepCode(),rsList.get(0).get("count"));
					}
				}
				budgetModelXf.setStepMap(stepMap);
				if(budgetModelXf.getState()!=3&&"2".equals(budgetModelXf.getModelId().getModelType())){
					BudgetModel hzModel = budgetModelXf.getModelId().getHzModelId();
					if(hzModel!=null){
						List<PropertyFilter> hzfilters = new ArrayList<PropertyFilter>();
						hzfilters.add(new PropertyFilter("EQS_modelId.modelId",hzModel.getModelId()));
						hzfilters.add(new PropertyFilter("NEI_state","3"));
						List<BudgetModelXf> budgetModelXfs = budgetModelXfManager.getByFilters(hzfilters);
						if(budgetModelXfs!=null&&budgetModelXfs.size()>0){
							budgetModelXf.setBmXf(budgetModelXfs.get(0));
						}
					}
				}
			}
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
    
    public String budgetModelXfDel(){
    	String msg = "下发";
    	try {
    		if("2".equals(xfType)){
    			msg = "汇总";
    		}
    		id = id.replaceAll(" ", "");
			StringTokenizer ids = new StringTokenizer(id,
					",");
			while (ids.hasMoreTokens()) {
				String removeId = ids.nextToken();
				List<PropertyFilter> updatafilters = new ArrayList<PropertyFilter>();
				updatafilters.add(new PropertyFilter("EQS_modelXfId.xfId",removeId));
				List<BudgetUpdata> budgetUpdataList = budgetUpdataManager.getByFilters(updatafilters);
				for(BudgetUpdata bup : budgetUpdataList){
					budgetUpdataManager.executeSql("delete bm_updatadetail where updataId='"+bup.getUpdataId()+"'");
					budgetUpdataManager.executeSql("delete bm_process_log where updataId='"+bup.getUpdataId()+"'");
					budgetUpdataManager.remove(bup.getUpdataId());
				}
				budgetModelXfManager.remove(removeId);
			}
			return ajaxForward(true, "预算"+msg+"删除成功！", false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(true, "预算"+msg+"删除失败！", false);
		}
    }
    
	public String budgetModelXfGridEdit() {
		try {
			if (oper.equals("del")) {
				id = id.replaceAll(" ", "");
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
	
	private String modelType;
	
	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String budgetModelXfRefresh(){
		List<PropertyFilter> modelfilters = new ArrayList<PropertyFilter>();
		modelfilters.add(new PropertyFilter("EQB_disabled", "false"));
		String periodYear = UserContextUtil.getUserContext().getPeriodYear();
		String periodYearNum = ContextUtil.getGlobalParamByKey("periodYearNum");
		if(periodYearNum!=null){
			int y = Integer.parseInt(periodYearNum);
			int year = Integer.parseInt(periodYear);
			periodYear = ""+(year+y);
		}
		//modelfilters.add(new PropertyFilter("SQS_modelId.modelId", "modelId not in (select modelId from bm_model_xf where period_year='"+periodYear+"')"));
		modelfilters.add(new PropertyFilter("EQS_modelType", modelType));
		modelfilters.add(new PropertyFilter("EQB_disabled", "false"));
		modelfilters.add(new PropertyFilter("SQS_modelId", "this_.modelId not in (select xf.modelId from bm_model_xf xf where xf.period_year='"+periodYear+"' and state!=3)"));
		List<BudgetModel> budgetModels = budgetModelManager.getByFilters(modelfilters);
		for(BudgetModel bmm :budgetModels){
			if("2".equals(bmm.getModelType())||"3".equals(bmm.getModelType())){
				User user = UserContextUtil.getContextUser();
				String deptId = user.getPerson().getDepartment().getDepartmentId();
				BmModelProcess bmp = bmm.getUpdataProcess();
				String checkDeptId = bmp.getCheckDeptId();
				if(deptId.equals(checkDeptId)){
					BudgetModelXf budgetModelXf = new BudgetModelXf();
					budgetModelXf.setModelId(bmm);
					budgetModelXf.setPeriodYear(periodYear);
					budgetModelXf.setState(0);
					budgetModelXf.setXfDate(Calendar.getInstance().getTime());
					budgetModelXf = budgetModelXfManager.save(budgetModelXf);
					Set<Department> departments = bmm.getDepartments();
					List<BudgetUpdata> budgetUpdatas = new ArrayList<BudgetUpdata>();
					for(Department dept : departments){
						BudgetUpdata budgetUpdata = new BudgetUpdata();
						budgetUpdata.setModelXfId(budgetModelXf);
						budgetUpdata.setDepartment(dept);
						budgetUpdata.setPeriodYear(periodYear);
						budgetUpdata.setState(0);
						budgetUpdata.setDeptType(0);
						//Person operator = UserContextUtil.getSessionUser().getPerson();
						//budgetUpdata.setOperator(operator);
						//budgetUpdata.setOptDate(Calendar.getInstance().getTime());
						budgetUpdatas.add(budgetUpdata);
					}
					if(checkDeptId!=null){
						Department department = departmentManager.get(checkDeptId);
						BudgetUpdata budgetUpdata = new BudgetUpdata();
						budgetUpdata.setModelXfId(budgetModelXf);
						budgetUpdata.setDepartment(department);
						budgetUpdata.setPeriodYear(periodYear);
						budgetUpdata.setState(0);
						budgetUpdata.setDeptType(1);
						budgetUpdatas.add(budgetUpdata);
					}
					budgetUpdataManager.saveAll(budgetUpdatas);
				}else{
					continue;
				}
			}else{
				BudgetModelXf budgetModelXf = new BudgetModelXf();
				budgetModelXf.setModelId(bmm);
				budgetModelXf.setPeriodYear(periodYear);
				budgetModelXf.setState(0);
				budgetModelXf.setXfDate(Calendar.getInstance().getTime());
				budgetModelXfManager.save(budgetModelXf);
			}
			
		}
		return ajaxForward("刷新成功！");
	}
	
	String xfType ;
	String msg;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getXfType() {
		return xfType;
	}

	public void setXfType(String xfType) {
		this.xfType = xfType;
	}

	public String budgetModel_Xf(){
		String msg = "下发";
		/*if("3".equals(xfType)){
			msg = "汇总";
		}else if("4".equals(xfType)){
			msg = "重新汇总";
		}*/
		if(xfId!=null&&!"".equals(xfId)){
			List<PropertyFilter> xfModelfilters = new ArrayList<PropertyFilter>();
			String periodYear = UserContextUtil.getUserContext().getPeriodYear();
			String periodYearNum = ContextUtil.getGlobalParamByKey("periodYearNum");
			if(periodYearNum!=null){
				int y = Integer.parseInt(periodYearNum);
				int year = Integer.parseInt(periodYear);
				periodYear = ""+(year+y);
			}
			//xfModelfilters.add(new PropertyFilter("EQS_periodYear", periodYear));
			xfModelfilters.add(new PropertyFilter("INS_xfId", xfId));
			List<BudgetModelXf> xfBudgetModelXfs = budgetModelXfManager.getByFilters(xfModelfilters);
			for(BudgetModelXf bmmXf :xfBudgetModelXfs){
				BudgetModel bmm = bmmXf.getModelId();
				modelType = bmm.getModelType();
				if("1".equals(modelType)){
					msg = "下发";
				}else if("2".equals(modelType)){
					msg = "汇总";
				}else if("3".equals(modelType)){
					msg = "上报";
				}
				if(this.msg!=null){
					msg = this.msg;
				}
				int state = bmmXf.getState();
				Set<Department> departments = bmm.getDepartments();
				if("1".equals(modelType)){
					if(departments==null||departments.size()==0){
						return ajaxForward(false,bmm.getModelName()+" 没有预算部门，无法"+msg+"！",false);
					}
				}
				if(state>0){
					//xfType:1:下发2：重新下发
					if("2".equals(xfType)){
						List<PropertyFilter> updatafilters = new ArrayList<PropertyFilter>();
						updatafilters.add(new PropertyFilter("EQS_modelXfId.xfId",bmmXf.getXfId()));
						List<BudgetUpdata> budgetUpdataList = budgetUpdataManager.getByFilters(updatafilters);
						for(BudgetUpdata bup : budgetUpdataList){
							bup.setState(9);
							budgetUpdataManager.executeSql("update bm_updatadetail set state=9 where updataId='"+bup.getUpdataId()+"'");
							budgetUpdataManager.save(bup);
						}
						bmmXf.setState(3);
						budgetModelXfManager.save(bmmXf);
						bmmXf = new BudgetModelXf();
						bmmXf.setModelId(bmm);
						bmmXf.setPeriodYear(periodYear);
						bmmXf.setState(1);
						bmmXf.setXfDate(Calendar.getInstance().getTime());
						bmmXf = budgetModelXfManager.save(bmmXf);
					}else{
						return ajaxForward(false,bmm.getModelName()+" 已"+msg+"!",false);
					}
				}else{
					bmmXf.setState(1);
					bmmXf.setXfDate(Calendar.getInstance().getTime());
				}
				if("1".equals(modelType)||(("2".equals(modelType)||"3".equals(modelType))&&"2".equals(xfType))){
					List<BudgetUpdata> budgetUpdatas = new ArrayList<BudgetUpdata>();
					for(Department dept : departments){
						BudgetUpdata budgetUpdata = new BudgetUpdata();
						budgetUpdata.setModelXfId(bmmXf);
						budgetUpdata.setDepartment(dept);
						budgetUpdata.setPeriodYear(periodYear);
						budgetUpdata.setState(0);
						budgetUpdata.setDeptType(0);
						//Person operator = UserContextUtil.getSessionUser().getPerson();
						//budgetUpdata.setOperator(operator);
						//budgetUpdata.setOptDate(Calendar.getInstance().getTime());
						budgetUpdatas.add(budgetUpdata);
					}
					
					if("2".equals(modelType)||"3".equals(modelType)){
						BmModelProcess bmp = bmm.getUpdataProcess();
						String checkDeptId = bmp.getCheckDeptId();
						String[] checkDeptIdArr = checkDeptId.split(",");
						for(String deptId : checkDeptIdArr){
							Department department = departmentManager.get(deptId);
							BudgetUpdata budgetUpdata = new BudgetUpdata();
							budgetUpdata.setModelXfId(bmmXf);
							budgetUpdata.setDepartment(department);
							budgetUpdata.setPeriodYear(periodYear);
							budgetUpdata.setState(0);
							budgetUpdata.setDeptType(1);
							budgetUpdatas.add(budgetUpdata);
						}
					}
					budgetUpdataManager.saveAll(budgetUpdatas);
				}
				
				/*if(checkDeptId!=null){
					String[] checkDeptIdArr = checkDeptId.split(",");
					for(String deptId : checkDeptIdArr){
						Department department = departmentManager.get(deptId);
						BudgetUpdata budgetUpdata = new BudgetUpdata();
						budgetUpdata.setModelXfId(bmmXf);
						budgetUpdata.setDepartment(department);
						budgetUpdata.setPeriodYear(periodYear);
						budgetUpdata.setState(0);
						budgetUpdata.setDeptType(1);
						budgetUpdatas.add(budgetUpdata);
					}
				}*/
				
			}
			budgetModelXfManager.saveAll(xfBudgetModelXfs);
		}
		return ajaxForward(msg+"成功！");
	}
	
	
	List<BmProcessColumn> processColumns;
	public List<BmProcessColumn> getProcessColumns() {
		return processColumns;
	}

	public void setProcessColumns(List<BmProcessColumn> processColumns) {
		this.processColumns = processColumns;
	}
	public String needBmHzCheckProcess;
	public String getNeedBmHzCheckProcess() {
		return needBmHzCheckProcess;
	}

	public void setNeedBmHzCheckProcess(String needBmHzCheckProcess) {
		this.needBmHzCheckProcess = needBmHzCheckProcess;
	}

	public String bmHzList(){
		try {
			needBmHzCheckProcess = ContextUtil.getGlobalParamByKey("needBmHzCheckProcess");
			/*String bmCheckProcessCode = ContextUtil.getGlobalParamByKey("bmHzCheckProcess");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_businessProcess.processCode",bmCheckProcessCode));
			filters.add(new PropertyFilter("OAS_state",""));
        	List<BusinessProcessStep> beforeStepList = businessProcessStepManager.getByFilters(filters);
        	processColumns = new ArrayList<BmProcessColumn>();
        	int i=0;
        	for(BusinessProcessStep bps : beforeStepList){
        		if(i==beforeStepList.size()-1){
        			break;
        		}
        		BmProcessColumn bpc_pserson = new BmProcessColumn();
        		bpc_pserson.setCode("person_"+bps.getStepCode());
        		bpc_pserson.setName(bps.getStepName());
        		bpc_pserson.setDataType("string");
        		processColumns.add(bpc_pserson);
        		BmProcessColumn bpc_time = new BmProcessColumn();
        		bpc_time.setCode("date_"+bps.getStepCode());
        		bpc_time.setName(bps.getStepName()+"时间");
        		bpc_time.setDataType("date");
        		processColumns.add(bpc_time);
        		if(bps.getState()!=0){
        			BmProcessColumn bpc_info = new BmProcessColumn();
        			bpc_info.setCode("info_"+bps.getStepCode());
        			bpc_info.setName("审核信息");
        			bpc_info.setDataType("string");
        			processColumns.add(bpc_info);
        		}
        		i++;
        	}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String budgetModel_gq(){
		try {
			List<PropertyFilter> xfModelfilters = new ArrayList<PropertyFilter>();
			xfModelfilters.add(new PropertyFilter("INS_xfId", xfId));
			List<BudgetModelXf> xfBudgetModelXfs = budgetModelXfManager.getByFilters(xfModelfilters);
			for(BudgetModelXf bmmXf :xfBudgetModelXfs){
				List<PropertyFilter> updatafilters = new ArrayList<PropertyFilter>();
				updatafilters.add(new PropertyFilter("EQS_modelXfId.xfId",bmmXf.getXfId()));
				List<BudgetUpdata> budgetUpdataList = budgetUpdataManager.getByFilters(updatafilters);
				for(BudgetUpdata bup : budgetUpdataList){
					bup.setState(9);
					budgetUpdataManager.executeSql("update bm_updatadetail set state=9 where updataId='"+bup.getUpdataId()+"'");
					budgetUpdataManager.save(bup);
				}
				bmmXf.setState(3);
				budgetModelXfManager.save(bmmXf);
			}
			return ajaxForward(true,"作废成功！",false);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false,"作废失败！",false);
		}
		
	}
}

