package com.huge.ihos.material.deptplan.webapp.action;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;

import com.huge.exceptions.BillNumCreateException;
import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.ihos.material.deptapp.service.DeptAppManager;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.deptplan.service.DeptNeedPlanManager;
import com.huge.ihos.material.documenttemplate.model.DocumentTemplate;
import com.huge.ihos.material.documenttemplate.service.DocumentTemplateManager;
import com.huge.ihos.material.model.InvBillNumberSetting;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class DeptNeedPlanPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -3934019950382708083L;
	private DeptNeedPlanManager deptNeedPlanManager;
	private List<DeptNeedPlan> deptNeedPlans;
	private DeptNeedPlan deptNeedPlan;
	private String needId;
	private String deptNeedPlanDetailJson;
	private String storeId;
	private BillNumberManager billNumberManager;
	private String needDeptId;

	public String getNeedDeptId() {
		return needDeptId;
	}

	public void setNeedDeptId(String needDeptId) {
		this.needDeptId = needDeptId;
	}

	public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public void setDeptNeedPlanDetailJson(String deptNeedPlanDetailJson) {
		this.deptNeedPlanDetailJson = deptNeedPlanDetailJson;
	}

	public void setDeptNeedPlanManager(DeptNeedPlanManager deptNeedPlanManager) {
		this.deptNeedPlanManager = deptNeedPlanManager;
	}

	public List<DeptNeedPlan> getdeptNeedPlans() {
		return deptNeedPlans;
	}

	public void setDeptNeedPlans(List<DeptNeedPlan> deptNeedPlans) {
		this.deptNeedPlans = deptNeedPlans;
	}

	public DeptNeedPlan getDeptNeedPlan() {
		return deptNeedPlan;
	}

	public void setDeptNeedPlan(DeptNeedPlan deptNeedPlan) {
		this.deptNeedPlan = deptNeedPlan;
	}

	public String getNeedId() {
		return needId;
	}

	public void setNeedId(String needId) {
        this.needId = needId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String selectDeptNeedPlanList(){
		this.setRandom(this.getRequest().getParameter("random"));
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String deptNeedPlanGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = deptNeedPlanManager
					.getDeptNeedPlanCriteria(pagedRequests,filters);
			this.deptNeedPlans = (List<DeptNeedPlan>) pagedRequests.getList();
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
			JSONUtils.getMorpherRegistry().registerMorpher( new DateMorpher(new String[] { "yyyy-MM-dd" }));
			JSONArray jsa = JSONArray.fromObject(this.deptNeedPlanDetailJson);
			DeptNeedPlanDetail[] deptNeedPlanDetails = (DeptNeedPlanDetail[]) JSONArray.toArray(jsa, DeptNeedPlanDetail.class);
			
			//判断月计划与追加计划
			if(this.isEntityIsNew()){
			String deptId = deptNeedPlan.getDept().getDepartmentId();
			String planType = deptNeedPlan.getPlanType();
			String  periodMonth = deptNeedPlan.getPeriodMonth();
			
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter periodMonthFilter = new PropertyFilter("EQS_periodMonth",periodMonth);
			PropertyFilter planTypeFilter = new PropertyFilter("EQS_planType","1");
			PropertyFilter deptIdFilter = new PropertyFilter("EQS_dept.departmentId",deptId);
			filters.add(periodMonthFilter);
			filters.add(planTypeFilter);
			filters.add(deptIdFilter);
			UserContext userContext = UserContextUtil.getUserContext();
			filters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			filters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));			
			this.deptNeedPlans = deptNeedPlanManager.getByFilters(filters);
			if(this.deptNeedPlans.isEmpty()){
				if(planType.equals("2")){
					return ajaxForwardError(this.getText("deptNeedPlan.monthlyPlanNotExists"));
				}
			}else{
				if(planType.equals("1")){
					return ajaxForwardError(this.getText("deptNeedPlan.monthlyPlanAlreadyExists"));
				}
			}
			}
			String saveFrom = this.getRequest().getParameter("saveFrom");
			if("deptAppNeed".equals(saveFrom)){
				deptNeedPlan = deptNeedPlanManager.saveDeptDeedPlanFromDeptApp(deptNeedPlan,deptNeedPlanDetails,deptAppDetailIds);
			}else{
				deptNeedPlan = deptNeedPlanManager.saveDeptDeedPlan(deptNeedPlan,deptNeedPlanDetails);
			}
		} catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}  catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "deptNeedPlan.added" : "deptNeedPlan.updated";
		String saveType = this.getRequest().getParameter("saveType");
		if (saveType != null && saveType.equalsIgnoreCase("saveStay")) {
			this.setCallbackType(saveType);
			this.setForwardUrl(deptNeedPlan.getNeedId());
			return ajaxForward(true, this.getText(key), false);
		} else {
			return ajaxForward(true, this.getText(key), true);
		}
	}
	
	private DocumentTemplateManager documentTemplateManager;
	
	public void setDocumentTemplateManager(DocumentTemplateManager documentTemplateManager) {
		this.documentTemplateManager = documentTemplateManager;
	}
	
	private DocumentTemplate docTemp;
	
	public DocumentTemplate getDocTemp() {
		return docTemp;
	}
	
    public String edit(){
    	UserContext userContext = UserContextUtil.getUserContext();
    	String orgCode = userContext.getOrgCode();
    	String cpoyCode = userContext.getCopyCode();
    	String period = userContext.getPeriodMonth();
    	String docTemId = this.getRequest().getParameter("docTemId");
        if (needId != null) {
        	deptNeedPlan = deptNeedPlanManager.get(needId);
        	docTemId = deptNeedPlan.getDocTemId();
        	if(docTemId!=null && !(docTemId.trim().equals(""))){//useDocTemp
				docTemp = documentTemplateManager.get(docTemId);
			}else{//not useDocTemp
				docTemp = documentTemplateManager.initDocumentTemplate("科室需求计划单",orgCode,cpoyCode);
			}
        	this.setEntityIsNew(false);
        } else {
        	if(docTemId!=null){//preview
        		docTemp = documentTemplateManager.get(docTemId);
        	}else{//new
        		docTemp = documentTemplateManager.getDocumentTemplateInUse("科室需求计划单",orgCode,cpoyCode);
        		docTemId = docTemp.getId();
        	}
        	deptNeedPlan = new DeptNeedPlan();
        	deptNeedPlan.setOrgCode(orgCode);
        	deptNeedPlan.setCopyCode(cpoyCode);
        	deptNeedPlan.setPeriodMonth(period);
        	deptNeedPlan.setMakePerson(this.getSessionUser().getPerson());
        	deptNeedPlan.setMakeDate(userContext.getBusinessDate());
        	deptNeedPlan.setState("0");
        	deptNeedPlan.setDocTemId(docTemId);
        	deptNeedPlan.setDept(this.getSessionUser().getPerson().getDepartment());
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
    private DeptAppManager deptAppManager;
	
	public void setDeptAppManager(DeptAppManager deptAppManager) {
		this.deptAppManager = deptAppManager;
	}
	private String deptAppDetailIds;
	private String deptAppId;
	
	
	public String getDeptAppId() {
		return deptAppId;
	}

	public void setDeptAppId(String deptAppId) {
		this.deptAppId = deptAppId;
	}

	public String getDeptAppDetailIds() {
		return deptAppDetailIds;
	}
	
	public void setDeptAppDetailIds(String deptAppDetailIds) {
		this.deptAppDetailIds = deptAppDetailIds;
	}
    public String createDeptNeedPlanByDistribute(){
    	DeptApp deptApp = deptAppManager.get(deptAppId);
    	UserContext userContext = UserContextUtil.getUserContext();
    	String orgCode = userContext.getOrgCode();
    	String cpoyCode = userContext.getCopyCode();
    	//String period = userContext.getPeriodMonth();
    	deptNeedPlan = new DeptNeedPlan();
    	deptNeedPlan.setOrgCode(deptApp.getOrgCode());
    	deptNeedPlan.setCopyCode(deptApp.getCopyCode());
    	deptNeedPlan.setPeriodMonth(deptApp.getYearMonth());
    	deptNeedPlan.setMakePerson(this.getSessionUser().getPerson());
    	deptNeedPlan.setMakeDate(userContext.getBusinessDate());
    	deptNeedPlan.setState("0");
    	docTemp = documentTemplateManager.getDocumentTemplateInUse("科室需求计划单",orgCode,cpoyCode);
    	deptNeedPlan.setDocTemId(docTemp.getId());
    	deptNeedPlan.setDept(deptApp.getAppDept());
    	deptNeedPlan.setStore(deptApp.getStore());
    	deptNeedPlan.setPlanType("2");
    	deptNeedPlan.setRemark("申领单号-" +deptApp.getAppNo());
    	return SUCCESS;
    }
	public String deptNeedPlanGridEdit() {
		try {
			UserContext userContext = UserContextUtil.getUserContext();
			String orgCode = userContext.getOrgCode();
	    	String copyCode = userContext.getCopyCode();
	    	String period = userContext.getPeriodMonth();
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				boolean isLastNumber = true;
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					deptNeedPlan = this.deptNeedPlanManager.get(removeId);
					//isLastNumber = invBillNumberManager.isLastNumber(deptNeedPlan.getNeedNo(), InvBillNumberSetting.DEPT_NEED_PLAN, orgCode, copyCode, period);
					isLastNumber = true;
					if(!isLastNumber){
						return ajaxForward(false, "只能删除最后一条新建记录!", false);
					}else{
						this.deptNeedPlanManager.remove(removeId);
					}
				}
				gridOperationMessage = this.getText("deptNeedPlan.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("check")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> checkIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String checkId = ids.nextToken();
					checkIds.add(checkId);
				}
				deptNeedPlanManager.auditDeptDeedPlan(checkIds,this.getSessionUser().getPerson(),userContext.getBusinessDate());
				gridOperationMessage = this.getText("审核成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("cancelCheck")){
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> cancelCheckIds = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String cancelCheckId = ids.nextToken();
					cancelCheckIds.add(cancelCheckId);
				}
				deptNeedPlanManager.antiAuditDeptNeedPlan(cancelCheckIds);
				gridOperationMessage = this.getText("销审成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("stop")){
				StringTokenizer ids = new StringTokenizer(id,",");
				deptNeedPlans = new ArrayList<DeptNeedPlan>();
				while (ids.hasMoreTokens()) {
					String stopId = ids.nextToken();
					deptNeedPlan = this.deptNeedPlanManager.get(stopId);
					deptNeedPlan.setState("3");
					deptNeedPlans.add(deptNeedPlan);
				}
				deptNeedPlanManager.saveAll(deptNeedPlans);
				gridOperationMessage = this.getText("中止计划成功");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("abandon")){
				StringTokenizer ids = new StringTokenizer(id,",");
				deptNeedPlans = new ArrayList<DeptNeedPlan>();
				while (ids.hasMoreTokens()) {
					String abandonId = ids.nextToken();
					deptNeedPlan = this.deptNeedPlanManager.get(abandonId);
					deptNeedPlan.setState("4");
					deptNeedPlans.add(deptNeedPlan);
				}
				deptNeedPlanManager.saveAll(deptNeedPlans);
				gridOperationMessage = this.getText("作废计划成功");
				return ajaxForward(true, gridOperationMessage, false);
			}
			else if(oper.equals("copy")){//复制功能
				StringTokenizer ids = new StringTokenizer(id,",");
				deptNeedPlans = new ArrayList<DeptNeedPlan>();
				while (ids.hasMoreTokens()) {				
					String copyId = ids.nextToken();
					deptNeedPlan = this.deptNeedPlanManager.get(copyId);
					//String tempNeedNo = invBillNumberManager.createNextBillNumber(InvBillNumberSetting.DEPT_NEED_PLAN, deptNeedPlan.getOrgCode(), deptNeedPlan.getCopyCode(), deptNeedPlan.getPeriodMonth(),true);//生成单号
					String tempNeedNo = billNumberManager.createNextBillNumber("MM",InvBillNumberSetting.DEPT_NEED_PLAN, true, deptNeedPlan.getOrgCode(), deptNeedPlan.getCopyCode(),deptNeedPlan.getPeriodMonth());//生成单号
					docTemp = documentTemplateManager.getDocumentTemplateInUse("科室需求计划单",orgCode,copyCode);
					DeptNeedPlan deptNeedPlanTemp = new DeptNeedPlan();
					deptNeedPlanTemp = deptNeedPlan.clone();
					deptNeedPlanTemp.setState("0");
					deptNeedPlanTemp.setPlanType("2");
					deptNeedPlanTemp.setNeedId(null);
					deptNeedPlanTemp.setNeedNo(tempNeedNo);					
					deptNeedPlanTemp.setDocTemId(docTemp.getId());
					deptNeedPlanTemp.setCheckPerson(null);//审核人清空
					deptNeedPlanTemp.setCheckDate(null);//审核日期清空
					deptNeedPlanTemp.setMakePerson(this.getSessionUser().getPerson());
					deptNeedPlanTemp.setMakeDate(userContext.getBusinessDate());
					Set<DeptNeedPlanDetail> deptNeedPlanDetailSetOld = deptNeedPlan.getDeptNeedPlanDetails();
					Set<DeptNeedPlanDetail> deptNeedPlanDetailSetNew = new HashSet<DeptNeedPlanDetail>();
					for(DeptNeedPlanDetail deptNeedPlanDetailTemp:deptNeedPlanDetailSetOld){
						DeptNeedPlanDetail deptNeedPlanDetailTempClone = new DeptNeedPlanDetail();
						deptNeedPlanDetailTempClone = deptNeedPlanDetailTemp.clone();
						deptNeedPlanDetailTempClone.setNeedDetailId(null);
						deptNeedPlanDetailTempClone.setDeptNeedPlan(deptNeedPlanTemp);
						deptNeedPlanDetailSetNew.add(deptNeedPlanDetailTempClone);
					}
					deptNeedPlanTemp.setDeptNeedPlanDetails(deptNeedPlanDetailSetNew);
					deptNeedPlans.add(deptNeedPlanTemp);
				}
				deptNeedPlanManager.saveAll(deptNeedPlans);
				gridOperationMessage = this.getText("复制成功");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		}catch (BillNumCreateException bnce){
			return ajaxForwardError(bnce.getMessage());
		}catch (Exception e) {
			log.error("checkDeptNeedPlanGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (deptNeedPlan == null) {
			return "Invalid deptNeedPlan Data";
		}

		return SUCCESS;

	}
}

