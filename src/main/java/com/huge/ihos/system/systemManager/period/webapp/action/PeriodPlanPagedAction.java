package com.huge.ihos.system.systemManager.period.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.ihos.system.systemManager.period.service.PeriodPlanManager;
import com.huge.ihos.system.systemManager.period.service.PeriodYearManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.ztree.ZTreeSimpleNode;
import com.opensymphony.xwork2.Preparable;




public class PeriodPlanPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341724135545408614L;
	private PeriodPlanManager periodPlanManager;
	private List<PeriodPlan> periodPlans;
	private PeriodPlan periodPlan;
	private String planId;
	private String remark;
	private String planName;
	private List<ZTreeSimpleNode> ztreeList = new ArrayList<ZTreeSimpleNode>();
	
	private PeriodYearManager periodYearManager;

	public PeriodPlanManager getPeriodPlanManager() {
		return periodPlanManager;
	}

	public void setPeriodPlanManager(PeriodPlanManager periodPlanManager) {
		this.periodPlanManager = periodPlanManager;
	}

	public List<PeriodPlan> getperiodPlans() {
		return periodPlans;
	}

	public void setPeriodPlans(List<PeriodPlan> periodPlans) {
		this.periodPlans = periodPlans;
	}

	public PeriodPlan getPeriodPlan() {
		return periodPlan;
	}

	public void setPeriodPlan(PeriodPlan periodPlan) {
		this.periodPlan = periodPlan;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
        this.planId = planId;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public List<PeriodPlan> getPeriodPlans() {
		return periodPlans;
	}
	

	public List<ZTreeSimpleNode> getZtreeList() {
		return ztreeList;
	}

	public void setZtreeList(List<ZTreeSimpleNode> ztreeList) {
		this.ztreeList = ztreeList;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String periodPlanGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = periodPlanManager
					.getPeriodPlanCriteria(pagedRequests,filters);
			this.periodPlans = (List<PeriodPlan>) pagedRequests.getList();
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
			periodPlanManager.save(periodPlan);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "periodPlan.added" : "periodPlan.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (planId != null) {
        	periodPlan = periodPlanManager.get(planId);
        	this.setEntityIsNew(false);
        } else {
        	periodPlan = new PeriodPlan();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String periodPlanGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					periodPlanManager.remove(removeId);
				}
				gridOperationMessage = this.getText("periodPlan.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if(oper.equals("edit")|| oper.equals("add")){
				if(planId == null ||"".equals(planId)){
					return ajaxForward(false,"方案编码不能为空！",false);
				} else {
					periodPlan = periodPlanManager.get(planId);
					if(periodPlan == null) {
						periodPlan = new PeriodPlan();
						periodPlan.setPlanId(planId);
					}
				}
				periodPlan.setRemark(remark);
				periodPlan.setPlanName(planName);
				String isDefaultStr = getRequest().getParameter("isDefault");
				Boolean isDefault = false;
				if("Yes".equals(isDefaultStr)) {
					isDefault = true;
				}
				periodPlan.setIsDefault(isDefault);
				periodPlanManager.save(periodPlan);
				return ajaxForward(true, "保存成功！", false);
			} else if("changeDef".equals(oper)) {
				if(OtherUtil.measureNotNull(planId)) {
					List<PeriodPlan> list = periodPlanManager.getAll();
					for(PeriodPlan plan : list) {
						plan.setIsDefault(false);
					}
					periodPlanManager.saveAll(list);
					PeriodPlan periodPlan = periodPlanManager.get(planId);
					periodPlan.setIsDefault(true);
					periodPlanManager.save(periodPlan);
					return ajaxForward(true, "切换成功！", false);
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodPlanGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String makePeriodPlanTree(){
		try{
			ZTreeSimpleNode ztree = new ZTreeSimpleNode();
			ztree.setId("-1");
			ztree.setOpen(true);
			ztree.setName("期间方案");
			ztreeList.add(ztree);
			List<PeriodPlan> planList = periodPlanManager.getAll();
			for(PeriodPlan plan: planList){
				ZTreeSimpleNode znode = new ZTreeSimpleNode();
				znode.setId(plan.getPlanId());
				znode.setSubSysTem("plan");
				znode.setName(plan.getPlanName());
				znode.setpId("-1");
				ztreeList.add(znode);
				Set<PeriodYear> periodYearSet = plan.getPeriodYearSet();
				Iterator<PeriodYear> it = periodYearSet.iterator();
				while(it.hasNext()){
					PeriodYear periodYear = it.next();
					ZTreeSimpleNode node = new ZTreeSimpleNode();
					node.setId(periodYear.getPeriodYearId());
					node.setSubSysTem("period");
					node.setName(periodYear.getPeriodYearCode());
					node.setpId(plan.getPlanId());
					ztreeList.add(node);
				}
			}
		}catch(Exception e){
			log.error(e);
		}
		return SUCCESS;
	}

	private String isValid() {
		if (periodPlan == null) {
			return "Invalid periodPlan Data";
		}

		return SUCCESS;

	}
}

