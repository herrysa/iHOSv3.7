package com.huge.ihos.material.deptplan.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.material.deptplan.model.DeptMRSummary;
import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.deptplan.service.DeptMRSummaryDetailManager;
import com.huge.ihos.material.deptplan.service.DeptNeedPlanManager;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class DeptMRSummaryDetailPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6861161315556943449L;
	private DeptMRSummaryDetailManager deptMRSummaryDetailManager;
	private List<DeptMRSummaryDetail> deptMRSummaryDetails;
	private DeptMRSummaryDetail deptMRSummaryDetail;
	private String mrDetailId;
	private String store;
	private String planType;
	private List<DeptNeedPlan> deptNeedPlans;
	private DeptNeedPlanManager deptNeedPlanManager;
	private DeptMRSummary deptMRSummary;

	
	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public List<DeptNeedPlan> getDeptNeedPlans() {
		return deptNeedPlans;
	}

	public void setDeptNeedPlans(List<DeptNeedPlan> deptNeedPlans) {
		this.deptNeedPlans = deptNeedPlans;
	}
	public DeptMRSummary getDeptMRSummary(){
		return deptMRSummary;
	}
	public void setDeptMRSummary(DeptMRSummary deptMRSummary){
		this.deptMRSummary=deptMRSummary;
	}
	public DeptMRSummaryDetailManager getDeptMRSummaryDetailManager() {
		return deptMRSummaryDetailManager;
	}

	public void setDeptMRSummaryDetailManager(DeptMRSummaryDetailManager deptMRSummaryDetailManager) {
		this.deptMRSummaryDetailManager = deptMRSummaryDetailManager;
	}

	public List<DeptMRSummaryDetail> getdeptMRSummaryDetails() {
		return deptMRSummaryDetails;
	}
	
	public void setDeptNeedPlanManager(DeptNeedPlanManager deptNeedPlanManager) {
		this.deptNeedPlanManager = deptNeedPlanManager;
	}

	public void setDeptMRSummaryDetails(List<DeptMRSummaryDetail> deptMRSummaryDetails) {
		this.deptMRSummaryDetails = deptMRSummaryDetails;
	}
	
	public DeptMRSummaryDetail getDeptMRSummaryDetail() {
		return deptMRSummaryDetail;
	}

	public void setDeptMRSummaryDetail(DeptMRSummaryDetail deptMRSummaryDetail) {
		this.deptMRSummaryDetail = deptMRSummaryDetail;
	}

	public String getMrDetailId() {
		return mrDetailId;
	}

	public void setMrDetailId(String mrDetailId) {
        this.mrDetailId = mrDetailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String deptMRSummaryDetailGridList() {
		log.debug("enter list method!");
		try {		
			if(store!=null){
				UserContext userContext = UserContextUtil.getUserContext();
			PropertyFilter storefilter = new PropertyFilter("EQS_store.id",store);
			PropertyFilter planTypefilter = new PropertyFilter("EQS_planType", planType);
			PropertyFilter periodMonthfilter = new PropertyFilter("EQS_periodMonth",userContext.getPeriodMonth());
			PropertyFilter statefilter = new PropertyFilter("EQS_state", "1");
			List<PropertyFilter> deptNeedPlanfilters = new ArrayList<PropertyFilter>();
			deptNeedPlanfilters.add(storefilter);
			deptNeedPlanfilters.add(planTypefilter);
			deptNeedPlanfilters.add(periodMonthfilter);
			deptNeedPlanfilters.add(statefilter);
			deptNeedPlanfilters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
			deptNeedPlanfilters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));			
			this.deptNeedPlans = deptNeedPlanManager.getByFilters(deptNeedPlanfilters);
			Map<String,Date> mapMakeDate = new LinkedHashMap<String, Date>();
			Map<String, Double> map = new LinkedHashMap<String, Double>();
			Map<String, DeptNeedPlanDetail> mapDeptNeedPlanDetail = new LinkedHashMap<String, DeptNeedPlanDetail>();
			List<DeptMRSummaryDetail> deptMRSummaryDetailstemp = new ArrayList<DeptMRSummaryDetail>();
			if(this.deptNeedPlans !=null &&!this.deptNeedPlans.isEmpty())
			{
				for(DeptNeedPlan deptNeedPlantemp:this.deptNeedPlans)
				{
					Set<DeptNeedPlanDetail> deptNeedPlanDetailSet = deptNeedPlantemp.getDeptNeedPlanDetails();
					for(DeptNeedPlanDetail deptNeedPlanDetailTemp:deptNeedPlanDetailSet){
						String invid = deptNeedPlanDetailTemp.getInvDict().getInvId();
						Double amount = deptNeedPlanDetailTemp.getAmount();					
						if(map.containsKey(invid)){
							map.put(invid, (map.get(invid)+amount));
							if(deptNeedPlanDetailTemp.getNeedDate().before( mapMakeDate.get(invid))){
								mapMakeDate.put(invid, deptNeedPlanDetailTemp.getNeedDate());
							}
						}else{
							map.put(invid, amount);
							mapDeptNeedPlanDetail.put(invid, deptNeedPlanDetailTemp);
							mapMakeDate.put(invid, deptNeedPlanDetailTemp.getNeedDate());
							}
						}
				}
				if(!map.isEmpty()){
					for (String key : map.keySet()) {
						DeptMRSummaryDetail deptMRSummaryDetail = new DeptMRSummaryDetail();
						deptMRSummaryDetail.setInvDict(mapDeptNeedPlanDetail.get(key).getInvDict());;
						deptMRSummaryDetail.setAmount(map.get(key));
						deptMRSummaryDetail.setPrice(mapDeptNeedPlanDetail.get(key).getPrice());
						deptMRSummaryDetail.setMakeDate(mapMakeDate.get(key));
						deptMRSummaryDetailstemp.add(deptMRSummaryDetail);
					}
				}
				this.deptMRSummaryDetails = deptMRSummaryDetailstemp;	
				records = deptMRSummaryDetailstemp.size();
				total = records;
				page = 1;
			}
			else{
				this.deptMRSummaryDetails = deptMRSummaryDetailstemp;	
				records = 0;
				total = records;
				page = 1;
			}				
			}
			else{
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = deptMRSummaryDetailManager
					.getDeptMRSummaryDetailCriteria(pagedRequests,filters);
			this.deptMRSummaryDetails = (List<DeptMRSummaryDetail>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();	
			}

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
			deptMRSummaryDetailManager.save(deptMRSummaryDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "deptMRSummaryDetail.added" : "deptMRSummaryDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (mrDetailId != null) {
        	deptMRSummaryDetail = deptMRSummaryDetailManager.get(mrDetailId);
        	this.setEntityIsNew(false);
        } else {
        	deptMRSummaryDetail = new DeptMRSummaryDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String deptMRSummaryDetailGridEdit() {
		try {
			//修改被汇总的表单状态
			UserContext userContext = UserContextUtil.getUserContext();
			if(oper.equals("update")){
			if(store!=null){
				PropertyFilter storefilter = new PropertyFilter("EQS_store.id",store);
				PropertyFilter planTypefilter = new PropertyFilter("EQS_planType", planType);
				PropertyFilter periodMonthfilter = new PropertyFilter("EQS_periodMonth",userContext.getPeriodMonth());
				PropertyFilter statefilter = new PropertyFilter("EQS_state", "1");
				List<PropertyFilter> deptNeedPlanfilters = new ArrayList<PropertyFilter>();
				deptNeedPlanfilters.add(storefilter);
				deptNeedPlanfilters.add(planTypefilter);
				deptNeedPlanfilters.add(periodMonthfilter);
				deptNeedPlanfilters.add(statefilter);
				deptNeedPlanfilters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
				deptNeedPlanfilters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));			
				this.deptNeedPlans = deptNeedPlanManager.getByFilters(deptNeedPlanfilters);
				if(this.deptNeedPlans !=null &&!this.deptNeedPlans.isEmpty()){
					for(DeptNeedPlan deptNeedPlantemp:this.deptNeedPlans){
						deptNeedPlantemp.setState("2");
						deptNeedPlanManager.save(deptNeedPlantemp);
					}
				}
				}
			}
			if(oper.equals("check")){
				if(store!=null){
					PropertyFilter storefilter = new PropertyFilter("EQS_store.id",store);
					PropertyFilter planTypefilter = new PropertyFilter("EQS_planType", planType);
					PropertyFilter periodMonthfilter = new PropertyFilter("EQS_periodMonth",userContext.getPeriodMonth());
					PropertyFilter statefilter = new PropertyFilter("EQS_state", "0");
					List<PropertyFilter> deptNeedPlanfilters = new ArrayList<PropertyFilter>();
					deptNeedPlanfilters.add(storefilter);
					deptNeedPlanfilters.add(planTypefilter);
					deptNeedPlanfilters.add(periodMonthfilter);
					deptNeedPlanfilters.add(statefilter);
					deptNeedPlanfilters.add(new PropertyFilter("EQS_orgCode", userContext.getOrgCode()));
					deptNeedPlanfilters.add(new PropertyFilter("EQS_copyCode", userContext.getCopyCode()));			
					this.deptNeedPlans = deptNeedPlanManager.getByFilters(deptNeedPlanfilters);
					if(this.deptNeedPlans !=null &&!this.deptNeedPlans.isEmpty()){
						if(this.getGlobalParamByKey("whetherToIgnore").equals("1")){
							this.setCallbackType("exist");
						}else if(this.getGlobalParamByKey("whetherToIgnore").equals("0")){
							this.setCallbackType("error");
						}else{
							this.setCallbackType("paramerror");
						}
					}else{
						this.setCallbackType("notexist");
					}
				}
			}
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
					
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.deptMRSummaryDetailManager.remove(ida);
				gridOperationMessage = this.getText("deptMRSummaryDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDeptMRSummaryDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (deptMRSummaryDetail == null) {
			return "Invalid deptMRSummaryDetail Data";
		}

		return SUCCESS;

	}
}

