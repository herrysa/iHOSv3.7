package com.huge.ihos.system.systemManager.period.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.service.PeriodMonthManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;
//import com.huge.ihos.copy.model.Copy;
//import com.huge.ihos.copy.service.CopyManager;




public class PeriodMonthPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6250252296219351302L;
	private PeriodMonthManager periodMonthManager;
	private List<PeriodMonth> periodMonths;
	private PeriodMonth periodMonth;
	private String monthId;
	private String optDate;
	private String planId;
	private String copyCode;
/*	private CopyManager copyManager;
	

	public CopyManager getCopyManager() {
		return copyManager;
	}

	public void setCopyManager(CopyManager copyManager) {
		this.copyManager = copyManager;
	}*/

	public String getCopyCode() {
		return copyCode;
	}

	public void setCopyCode(String copyCode) {
		this.copyCode = copyCode;
	}

	public String getOptDate() {
		return optDate;
	}

	public void setOptDate(String optDate) {
		this.optDate = optDate;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public PeriodMonthManager getPeriodMonthManager() {
		return periodMonthManager;
	}

	public void setPeriodMonthManager(PeriodMonthManager periodMonthManager) {
		this.periodMonthManager = periodMonthManager;
	}

	public List<PeriodMonth> getperiodMonths() {
		return periodMonths;
	}

	public void setPeriodMonths(List<PeriodMonth> periodMonths) {
		this.periodMonths = periodMonths;
	}

	public PeriodMonth getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(PeriodMonth periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
        this.monthId = monthId;
    }
	

	public List<PeriodMonth> getPeriodMonths() {
		return periodMonths;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String periodMonthGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = periodMonthManager
					.getPeriodMonthCriteria(pagedRequests,filters);
			this.periodMonths = (List<PeriodMonth>) pagedRequests.getList();
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
			periodMonthManager.save(periodMonth);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "periodMonth.added" : "periodMonth.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (monthId != null) {
        	periodMonth = periodMonthManager.get(monthId);
        	this.setEntityIsNew(false);
        } else {
        	periodMonth = new PeriodMonth();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String periodMonthGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					PeriodMonth periodMonth = periodMonthManager.get(removeId);
					periodMonthManager.remove(removeId);
					
				}
				gridOperationMessage = this.getText("periodMonth.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodMonthGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String calculatePeriodMonth(){
		try{
			periodMonths = new ArrayList<PeriodMonth>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String beginDateStr = this.getRequest().getParameter("beginDate");
			String endDateStr = this.getRequest().getParameter("endDate");
			String numStr = this.getRequest().getParameter("num");
			String periodYear = this.getRequest().getParameter("periodYear");
		    int num = Integer.parseInt(numStr)-1;
		    Calendar beginCal = Calendar.getInstance();
		    beginCal.setTime( sdf.parse(beginDateStr));
			while(num > 0){
				PeriodMonth month = new PeriodMonth();
				month.setBeginDate(beginCal.getTime());
				beginCal.set(Calendar.MONTH, beginCal.get(Calendar.MONTH)+1);
				beginCal.set(Calendar.DATE, beginCal.get(Calendar.DATE)-1);
				month.setEndDate(beginCal.getTime());
				beginCal.set(Calendar.DATE, beginCal.get(Calendar.DATE)+1);
				month.setPeriodMonthCode(periodYear+(Integer.parseInt(numStr)-num > 9?Integer.parseInt(numStr)-num :"0"+(Integer.parseInt(numStr)-num )));
				periodMonths.add(month);
				num--;
			}
			PeriodMonth  month = new PeriodMonth();
			month.setBeginDate(beginCal.getTime());
			month.setEndDate(sdf.parse(endDateStr));
			month.setPeriodMonthCode(periodYear+(Integer.parseInt(numStr)>9?Integer.parseInt(numStr):"0"+Integer.parseInt(numStr)));
			periodMonths.add(month);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getPeriodMonthByBusinessDate(){
		try {
			//Copy copy = copyManager.get(copyCode);
			//periodMonth = periodMonthManager.getPeriodMonth(copy.getPeriodPlan().getPlanId(), DateUtil.convertStringToDate(optDate));
			if(periodMonth!=null){
				return ajaxForward("1");
			}else {
				return ajaxForward("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward("0");
		}
	}

	private String isValid() {
		if (periodMonth == null) {
			return "Invalid periodMonth Data";
		}

		return SUCCESS;

	}
}

