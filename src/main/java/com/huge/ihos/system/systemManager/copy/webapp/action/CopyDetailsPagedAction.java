package com.huge.ihos.system.systemManager.copy.webapp.action;

import java.util.List;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.ihos.system.systemManager.copy.service.CopyManager;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.ihos.system.systemManager.organization.service.OrgManager;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.ihos.system.systemManager.period.service.PeriodMonthManager;
import com.huge.ihos.system.systemManager.period.service.PeriodPlanManager;
import com.huge.ihos.system.systemManager.period.service.PeriodYearManager;
import com.huge.ihos.system.systemManager.security.model.SystemVariable;
import com.huge.webapp.action.JqGridBaseAction;
import com.opensymphony.xwork2.Preparable;




public class CopyDetailsPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private PeriodMonthManager periodMonthManager;
	private CopyManager copyManager;
	private Copy copy;
	private Org org;
	private OrgManager orgManager;
	private PeriodPlanManager periodPlanManager;
	private PeriodYearManager periodYearManager;
	
	private List<PeriodMonth> periodMonthList;
	private PeriodYear periodYear;
	 @Override
	 public void prepare() throws Exception {
		 this.clearSessionMessages();
	 }
	public String showMainInfoDetails() {
		try {
			String copyCode = UserContextUtil.getUserContext().getCopyCode();
			copy = copyManager.get(copyCode);
			periodMonthList =  periodMonthManager.getMonthByPlanAndYear(copy.getPeriodPlan().getPlanId(), UserContextUtil.getUserContext().getPeriodYear());
			periodYear = periodYearManager.getPeriodYearByPlanAndYear(copy.getPeriodPlan().getPlanId(), UserContextUtil.getUserContext().getPeriodYear());
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	public String showNormalInfoDetails() {
		try {
			
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String showVoucherControlDetails() {
		try {
			
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String showBudgetControlDetails() {
		try {

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	public String showAuditControlDetails() {
		try {
			
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	/***************************get/set**********************/
	public PeriodMonthManager getPeriodMonthManager() {
		return periodMonthManager;
	}
	public void setPeriodMonthManager(PeriodMonthManager periodMonthManager) {
		this.periodMonthManager = periodMonthManager;
	}
	public CopyManager getCopyManager() {
		return copyManager;
	}
	public void setCopyManager(CopyManager copyManager) {
		this.copyManager = copyManager;
	}
	public Copy getCopy() {
		return copy;
	}
	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public OrgManager getOrgManager() {
		return orgManager;
	}
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	public PeriodPlanManager getPeriodPlanManager() {
		return periodPlanManager;
	}
	public void setPeriodPlanManager(PeriodPlanManager periodPlanManager) {
		this.periodPlanManager = periodPlanManager;
	}
	public PeriodYearManager getPeriodSubjectManager() {
		return periodYearManager;
	}
	public void setPeriodSubjectManager(PeriodYearManager periodYearManager) {
		this.periodYearManager = periodYearManager;
	}
	public List<PeriodMonth> getPeriodMonthList() {
		return periodMonthList;
	}
	public void setPeriodMonthList(List<PeriodMonth> periodMonthList) {
		this.periodMonthList = periodMonthList;
	}
	public PeriodYear getPeriodYear() {
		return periodYear;
	}
	public void setPeriodYear(PeriodYear periodYear) {
		this.periodYear = periodYear;
	}
	
}

