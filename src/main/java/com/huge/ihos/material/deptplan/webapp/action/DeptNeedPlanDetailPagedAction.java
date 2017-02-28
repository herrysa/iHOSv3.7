package com.huge.ihos.material.deptplan.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.material.deptapp.service.DeptAppDetailManager;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.deptplan.service.DeptNeedPlanDetailManager;
import com.huge.ihos.material.service.InvBalanceBatchManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class DeptNeedPlanDetailPagedAction extends JqGridBaseAction implements Preparable {
	private static final long serialVersionUID = -4367728326097260487L;
	
	private DeptNeedPlanDetailManager deptNeedPlanDetailManager;
	private List<DeptNeedPlanDetail> deptNeedPlanDetails;
	private DeptNeedPlanDetail deptNeedPlanDetail;
	private String needDetailId;
	private DeptAppDetailManager deptAppDetailManager;
	private InvBalanceBatchManager invBalanceBatchManager;
	
	public InvBalanceBatchManager getInvBalanceBatchManager() {
		return invBalanceBatchManager;
	}

	public void setInvBalanceBatchManager(InvBalanceBatchManager invBalanceBatchManager) {
		this.invBalanceBatchManager = invBalanceBatchManager;
	}
	
	public void setDeptAppDetailManager(DeptAppDetailManager deptAppDetailManager) {
		this.deptAppDetailManager = deptAppDetailManager;
	}

	public void setDeptNeedPlanDetailManager(DeptNeedPlanDetailManager deptNeedPlanDetailManager) {
		this.deptNeedPlanDetailManager = deptNeedPlanDetailManager;
	}

	public List<DeptNeedPlanDetail> getDeptNeedPlanDetails() {
		return deptNeedPlanDetails;
	}

	public void setDeptNeedPlanDetails(List<DeptNeedPlanDetail> deptNeedPlanDetails) {
		this.deptNeedPlanDetails = deptNeedPlanDetails;
	}

	public DeptNeedPlanDetail getDeptNeedPlanDetail() {
		return deptNeedPlanDetail;
	}

	public void setDeptNeedPlanDetail(DeptNeedPlanDetail deptNeedPlanDetail) {
		this.deptNeedPlanDetail = deptNeedPlanDetail;
	}

	public String getNeedDetailId() {
		return needDetailId;
	}

	public void setNeedDetailId(String needDetailId) {
        this.needDetailId = needDetailId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	@SuppressWarnings("unchecked")
	public String deptNeedPlanDetailGridList() {
		log.debug("enter list method!");
		try {
			JQueryPager pagedRequests = null;
			if("deptAppNeed".equals(docPreview)){
				String deptAppDetailIds = this.getRequest().getParameter("deptAppDetailIds");
				String deptAppId = this.getRequest().getParameter("deptAppId");
				this.deptNeedPlanDetails = deptAppDetailManager.getDeptNeedPlanByDis(deptAppId,deptAppDetailIds);
			}else{
				List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
				pagedRequests = (JQueryPager) pagerFactory.getPager(
						PagerFactory.JQUERYTYPE, getRequest());
				pagedRequests = deptNeedPlanDetailManager
						.getDeptNeedPlanDetailCriteria(pagedRequests,filters);
				this.deptNeedPlanDetails = (List<DeptNeedPlanDetail>) pagedRequests.getList();
				records = pagedRequests.getTotalNumberOfRows();
				total = pagedRequests.getTotalNumberOfPages();
				page = pagedRequests.getPageNumber();
			}
			if(deptNeedPlanDetails!=null && deptNeedPlanDetails.size()>0){
				DeptNeedPlan dnp = deptNeedPlanDetails.get(0).getDeptNeedPlan();
				String orgCode = dnp.getOrgCode();
				String copyCode = dnp.getCopyCode();
				String yearMonth = dnp.getPeriodMonth();
				String storeId = dnp.getStore().getId();
				String deptId = dnp.getDept().getDepartmentId();
				for(DeptNeedPlanDetail deptNeedPlanDetail:deptNeedPlanDetails){
					String invDictId = deptNeedPlanDetail.getInvDict().getInvId();
					deptNeedPlanDetail.setLastAmount(deptNeedPlanDetailManager.getLastAmount(yearMonth, storeId,deptId,invDictId));
					deptNeedPlanDetail.setSumAmount(deptNeedPlanDetailManager.getsumAmount(yearMonth, storeId,deptId,invDictId));
					deptNeedPlanDetail.setLastCostAmount(0d);
					deptNeedPlanDetail.setStoreAmount(invBalanceBatchManager.getStoreCurAmount(orgCode, copyCode, yearMonth, storeId, invDictId));
//					deptNeedPlanDetail.setStoreAmount(invBalanceBatchManager.getStoreCurAmount(orgCode, copyCode, yearMonth, storeId, invDictId)
//					deptNeedPlanDetail.setRealBuyAmount(0d);
				}
			}
			
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	private Map<String,Double> amounts;
	
	public Map<String, Double> getAmounts() {
		return amounts;
	}

	public String getLastAndStoreAmount() {
		String invId = this.getRequest().getParameter("invId");
		log.info("invId:"+invId);
		amounts = new HashMap<String,Double>();
		amounts.put("lastAmount", deptNeedPlanDetailManager.getLastAmount(this.getRequest().getParameter("periodMonth"), this.getRequest().getParameter("store_id"), this.getRequest().getParameter("dept_id"), this.getRequest().getParameter("invId")));
		amounts.put("sumAmount", deptNeedPlanDetailManager.getsumAmount(this.getRequest().getParameter("periodMonth"), this.getRequest().getParameter("store_id"), this.getRequest().getParameter("dept_id"), this.getRequest().getParameter("invId")));
		amounts.put("storeAmount", invBalanceBatchManager.getStoreCurAmount( this.getRequest().getParameter("orgCode"), this.getRequest().getParameter("copyCode"), this.getRequest().getParameter("periodMonth"), this.getRequest().getParameter("store_id"), this.getRequest().getParameter("invId")));
		amounts.put("lastCostAmount", 0d);
		return SUCCESS;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			deptNeedPlanDetailManager.save(deptNeedPlanDetail);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "deptNeedPlanDetail.added" : "deptNeedPlanDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (needDetailId != null) {
        	deptNeedPlanDetail = deptNeedPlanDetailManager.get(needDetailId);
        	this.setEntityIsNew(false);
        } else {
        	deptNeedPlanDetail = new DeptNeedPlanDetail();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String deptNeedPlanDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				List<String> idl = new ArrayList<String>();
				StringTokenizer ids = new StringTokenizer(id,",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					//Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);
				}
				String[] ida=new String[idl.size()];
				idl.toArray(ida);
				this.deptNeedPlanDetailManager.remove(ida);
				gridOperationMessage = this.getText("deptNeedPlanDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkDeptNeedPlanDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
		
	private String isValid() {
		if (deptNeedPlanDetail == null) {
			return "Invalid deptNeedPlanDetail Data";
		}
		return SUCCESS;
	}
}

