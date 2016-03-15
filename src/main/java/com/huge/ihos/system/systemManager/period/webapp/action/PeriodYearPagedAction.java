package com.huge.ihos.system.systemManager.period.webapp.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.ihos.system.systemManager.period.service.MoudlePeriodManager;
import com.huge.ihos.system.systemManager.period.service.PeriodPlanManager;
import com.huge.ihos.system.systemManager.period.service.PeriodYearManager;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class PeriodYearPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2310571708381333285L;
	private PeriodYearManager periodYearManager;
	private List<PeriodYear> periodYears;
	private PeriodYear periodYearEntity;
	private PeriodPlanManager periodPlanManager;
	private String periodId;
	private String periodYear;
	private Integer periodNum;
	private String beginDate;
	private String endDate;
	private String editType;
	private String planId;
	private MoudlePeriodManager moudlePeriodManager;

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	public String periodYearGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = periodYearManager
					.getPeriodYearCriteria(pagedRequests,filters);
			this.periodYears = (List<PeriodYear>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
    public String edit() {
    	String periodSubject_status = this.getRequest().getParameter("periodSubject_status");
    	if(periodSubject_status != null && !"".equals(periodSubject_status)){
			if(planId == null||"null".equals(planId)||"".equals(planId)){
				return ajaxForward(false, "请选择期间方案", false);
			} else {
				PeriodYear periodSub = periodYearManager.getLastYearByPlan(planId);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(periodSub == null){
					try {
						cal.setTime(sdf.parse(UserContextUtil.getUserContext().getBusinessDateStr()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					periodYear =cal.get(Calendar.YEAR)+"";
					cal.set(Calendar.DATE, cal.get(Calendar.DATE) );  
					beginDate = sdf.format(cal.getTime());
					cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1 ); 
					cal.set(Calendar.DATE, cal.get(Calendar.DATE)-1 );
					endDate = sdf.format(cal.getTime());
				} else {
					periodYear = Integer.parseInt(periodSub.getPeriodYearCode())+1+"";
					cal.setTime(periodSub.getEndDate());
					cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1 );  
					beginDate = sdf.format(cal.getTime());
					cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1 );
					cal.set(Calendar.DATE, cal.get(Calendar.DATE)-1 );  
					endDate = sdf.format(cal.getTime());
				}
    			periodNum = 12;
    		}
    	}
        return SUCCESS;
    }

	
	public String getPeriodYear1(){
		try{
			periodYearEntity = periodYearManager.get(periodId);
			periodId = periodYearEntity.getPeriodYearId();
			periodYear = periodYearEntity.getPeriodYearCode();
		    periodNum = periodYearEntity.getPeriodNum();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginDate = sdf.format(periodYearEntity.getBeginDate());
			endDate = sdf.format(periodYearEntity.getEndDate());
		} catch(Exception e){
			log.error("getPeriodSub", e);
		}
		return SUCCESS;
	}
	
	public String delPeriodYear(){
		try{
			String periodSubject_periodId = this.getRequest().getParameter("periodSubject_periodId");
			periodYearManager.remove(periodSubject_periodId);
			moudlePeriodManager.removeBySubId(periodSubject_periodId);
			gridOperationMessage = this.getText("periodSubject.deleted");
			return ajaxForward(true, gridOperationMessage, true);
		} catch (Exception e){
			log.error(e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	

	public String savePeriodYear(){
		try{
			String periodSubject_periodId = this.getRequest().getParameter("periodSubject_periodId");
			String periodSubject_planId = this.getRequest().getParameter("periodSubject_planId");
			String periodSubject_status = this.getRequest().getParameter("periodSubject_status");
			String periodSubject_periodYear = this.getRequest().getParameter("periodSubject_periodYear");
			String periodSubject_periodNum = this.getRequest().getParameter("periodSubject_periodNum");
			String periodSubject_beginDate = this.getRequest().getParameter("periodSubject_beginDate");
			String periodSubject_endDate = this.getRequest().getParameter("periodSubject_endDate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String monthsJson = this.getRequest().getParameter("monthsJson").toString();
			if("add".equals(periodSubject_status)){
				periodYearEntity = new PeriodYear();
				gridOperationMessage = this.getText("periodSubject.added");
			} else {
				periodYearEntity = periodYearManager.get(periodSubject_periodId);
				gridOperationMessage = this.getText("periodSubject.updated");
			}
			periodYearEntity.setBeginDate(sdf.parse(periodSubject_beginDate));
			periodYearEntity.setEndDate(sdf.parse(periodSubject_endDate));
			int year = Integer.parseInt(DateUtil.convertDateToString("yyyy", periodYearEntity.getBeginDate()));
			periodYearEntity.setYear(year);
			int num = Integer.parseInt(periodSubject_periodNum);
			periodYearEntity.setPeriodNum(num);
			periodYearEntity.setPeriodYearCode(periodSubject_periodYear);
			periodYearEntity.setPeriodYearId(periodSubject_periodYear);
			JSONObject json = JSONObject.fromObject(monthsJson);
			PeriodPlan periodPlan = new PeriodPlan();
			periodPlan.setPlanId(periodSubject_planId);
			periodYearEntity.setPlan(periodPlan);
			Set<PeriodMonth> periodMonths = new HashSet<PeriodMonth>();
			for(int i = 0;i < num; i++){
				PeriodMonth month = new PeriodMonth();
				//month.setPlan(periodPlan);
				month.setPeriodYear(periodYearEntity);
				
				JSONObject monthJson = json.getJSONObject("month"+i);
				String beginDate = monthJson.getString("beginDate");
				String endDate = monthJson.getString("endDate");
				String monthStr = monthJson.getString("periodMonthCode");
				
				month.setPeriodMonthId(monthStr);
				month.setBeginDate(sdf.parse(beginDate));
				month.setEndDate(sdf.parse(endDate));
				month.setPeriodMonthCode(monthStr);
				month.setMonth(Integer.parseInt(monthStr));
				periodMonths.add(month);
			}
			periodYearEntity.setPeriodMonthSet(periodMonths);
			periodYearEntity = periodYearManager.savePeriodYear(periodYearEntity,periodSubject_status);
			return ajaxForward(true, gridOperationMessage, true);
		}catch(Exception e){
			log.error(e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private boolean exists;
	
	public boolean getExists() {
		return exists;
	}
	
	public String checkPeriodYearCode(){
		String periodYearCode = this.getRequest().getParameter("periodYearCode");
		String periodPlanId = this.getRequest().getParameter("periodPlanId");
		PeriodYear periodYearFind = new PeriodYear();
		PeriodPlan periodPlanFind = new PeriodPlan();
		periodPlanFind.setPlanId(periodPlanId);
		periodYearFind.setPlan(periodPlanFind);
		periodYearFind.setPeriodYearCode(periodYearCode);
		List<PeriodYear> periodYears = periodYearManager.getByExample(periodYearFind);
		if (periodYears != null && periodYears.size() > 0) {
			exists = true;
		} else {
			exists = false;
		}
		//exists = periodYearManager.existByExample(periodYearFind);
		//exists = periodYearManager.existCode("t_periodYear", "periodYearCode", "planId", periodYearCode, periodPlanId);
		return SUCCESS;
	}
	
	private String isValid() {
		if (periodYearEntity == null) {
			return "Invalid periodYear Data";
		}

		return SUCCESS;
	}
	
	
	/************************get/set***********************************/

	public PeriodPlanManager getPeriodPlanManager() {
		return periodPlanManager;
	}

	public void setPeriodPlanManager(PeriodPlanManager periodPlanManager) {
		this.periodPlanManager = periodPlanManager;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}

	public Integer getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(Integer periodNum) {
		this.periodNum = periodNum;
	}


	public List<PeriodYear> getPeriodYears() {
		return periodYears;
	}

	public PeriodYearManager getPeriodYearManager() {
		return periodYearManager;
	}

	public void setPeriodYearManager(PeriodYearManager periodYearManager) {
		this.periodYearManager = periodYearManager;
	}

	public void setPeriodYears(List<PeriodYear> periodYears) {
		this.periodYears = periodYears;
	}

	public PeriodYear getPeriodYearEntity() {
		return periodYearEntity;
	}

	public void setPeriodYearEntity(PeriodYear periodYearEntity) {
		this.periodYearEntity = periodYearEntity;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }
	public MoudlePeriodManager getMoudlePeriodManager() {
		return moudlePeriodManager;
	}
	public void setMoudlePeriodManager(MoudlePeriodManager moudlePeriodManager) {
		this.moudlePeriodManager = moudlePeriodManager;
	}
	
}

