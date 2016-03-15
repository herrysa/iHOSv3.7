package com.huge.ihos.nursescore.webapp.action;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import com.huge.ihos.nursescore.model.MonthNurse;
import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.ihos.nursescore.model.NurseDayScoreDetail;
import com.huge.ihos.nursescore.service.MonthNurseManager;
import com.huge.ihos.nursescore.service.NurseDayScoreDetailManager;
import com.huge.ihos.nursescore.service.NurseDayScoreManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.util.DateUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;




public class NurseDayScorePagedAction extends JqGridBaseAction  {

	private NurseDayScoreManager nurseDayScoreManager;
	private NurseDayScoreDetailManager nurseDayScoreDetailManager;
	private List<NurseDayScore> nurseDayScores;
	private NurseDayScore nurseDayScore;
	private Long dayScoreID;
	private String checkPeriod ;
	private PeriodManager periodManager;
	private PersonManager personManager;
	private DepartmentManager departmentManager;
	private MonthNurseManager monthNurseManager;
	private String selectDate;
	private String isHaveRight = "0";

	public MonthNurseManager getMonthNurseManager() {
		return monthNurseManager;
	}

	public void setMonthNurseManager(MonthNurseManager monthNurseManager) {
		this.monthNurseManager = monthNurseManager;
	}

	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public NurseDayScoreDetailManager getNurseDayScoreDetailManager() {
		return nurseDayScoreDetailManager;
	}

	public void setNurseDayScoreDetailManager(
			NurseDayScoreDetailManager nurseDayScoreDetailManager) {
		this.nurseDayScoreDetailManager = nurseDayScoreDetailManager;
	}

	private List<Department> updataDepts;

	public NurseDayScoreManager getNurseDayScoreManager() {
		return nurseDayScoreManager;
	}

	public void setNurseDayScoreManager(NurseDayScoreManager nurseDayScoreManager) {
		this.nurseDayScoreManager = nurseDayScoreManager;
	}

	public List<NurseDayScore> getnurseDayScores() {
		return nurseDayScores;
	}

	public void setNurseDayScores(List<NurseDayScore> nurseDayScores) {
		this.nurseDayScores = nurseDayScores;
	}

	public NurseDayScore getNurseDayScore() {
		return nurseDayScore;
	}

	public void setNurseDayScore(NurseDayScore nurseDayScore) {
		this.nurseDayScore = nurseDayScore;
	}

	public Long getDayScoreID() {
		return dayScoreID;
	}

	public void setDayScoreID(Long dayScoreID) {
        this.dayScoreID = dayScoreID;
    }

	public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public List<Department> getUpdataDepts() {
		return updataDepts;
	}

	public void setUpdataDepts(List<Department> updataDepts) {
		this.updataDepts = updataDepts;
	}
	
	public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public String getIsHaveRight() {
		return isHaveRight;
	}

	public void setIsHaveRight(String isHaveRight) {
		this.isHaveRight = isHaveRight;
	}

	public String nurseDayScorePre(){
		try {
			String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
			updataDepts = departmentManager.getAllDeptByDeptIds(deptIds);
			//updataDepts = jjDeptMapManager.getByOperatorId(this.getUserManager().getCurrentLoginUser().getPerson().getPersonId());
			if(updataDepts==null||updataDepts.size()==0){
				isHaveRight = "0";
			}else{
				isHaveRight = "1";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String nurseDayScoreGridList() {
		log.debug("enter list method!");
		try {
			Person operator = this.getUserManager().getCurrentLoginUser().getPerson();
			
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			PropertyFilter propertyFilterDept = new PropertyFilter("EQS_operatorid.department.departmentId",""+operator.getDepartment().getDepartmentId());
			filters.add(propertyFilterDept);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = nurseDayScoreManager
					.getNurseDayScoreCriteria(pagedRequests,filters);
			this.nurseDayScores = (List<NurseDayScore>) pagedRequests.getList();
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
//			checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
			checkPeriod =  this.getLoginPeriod();
			Person operator = this.getUserManager().getCurrentLoginUser().getPerson();
			if(Integer.parseInt(nurseDayScore.getCheckperiod())<Integer.parseInt(checkPeriod)){
				return ajaxForward(false,"所选日期不能小于当前核算期间！",false);
			}
			List<NurseDayScore> nurseDayScores = nurseDayScoreManager.findByScoreDate(nurseDayScore.getGroupid().getDepartmentId(),nurseDayScore.getScoredate());
			if(nurseDayScores!=null&&nurseDayScores.size()!=0){
				return ajaxForward(false,"所选日期考勤已上报，请不要重复！",false);
			}
			nurseDayScore.setOperatorid(operator);
			nurseDayScore.setGroupid(departmentManager.get(nurseDayScore.getGroupid().getDepartmentId()));
			nurseDayScore.setGroupname(nurseDayScore.getGroupid().getName());
			nurseDayScore.setState(0);
			nurseDayScore = nurseDayScoreManager.save(nurseDayScore);
			List<MonthNurse> nurseList = monthNurseManager.getByCheckPeriodAndDept(nurseDayScore.getCheckperiod(),nurseDayScore.getGroupid().getDepartmentId());
			for(MonthNurse monthNurse : nurseList){
				NurseDayScoreDetail nurseDayScoreDetail = new NurseDayScoreDetail();
				nurseDayScoreDetail.setCheckperiod(nurseDayScore.getCheckperiod());
				nurseDayScoreDetail.setDayScoreID(nurseDayScore);
				nurseDayScoreDetail.setGroupid(nurseDayScore.getGroupid());
				nurseDayScoreDetail.setGroupname(nurseDayScore.getGroupid().getName());
				nurseDayScoreDetail.setPersonid(monthNurse.getPersonid());
				nurseDayScoreDetail.setName(nurseDayScoreDetail.getPersonid().getName());
				nurseDayScoreDetail.setState(0);
				nurseDayScoreDetail.setYearcode(monthNurse.getYearcode());
				nurseDayScoreDetail.setScoredate(nurseDayScore.getScoredate());
				nurseDayScoreDetail.setCode(monthNurse.getCode());
				nurseDayScoreDetailManager.save(nurseDayScoreDetail);
			}
		} catch (Exception dre) {
			dre.printStackTrace();
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "nurseDayScore.added" : "nurseDayScore.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
    	Person operator = this.getUserManager().getCurrentLoginUser().getPerson();
//    	checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
    	checkPeriod =  this.getLoginPeriod();
    	Calendar calendar = Calendar.getInstance();
    	//updataDepts = jjDeptMapManager.getByOperatorId(this.getUserManager().getCurrentLoginUser().getPerson().getPersonId());
    	String deptIds = UserContextUtil.findUserDataPrivilegeStr("jjdept_dp", "2");
		updataDepts = departmentManager.getAllDeptByDeptIds(deptIds);
    	if (dayScoreID != null) {
        	nurseDayScore = nurseDayScoreManager.get(dayScoreID);
        	this.setEntityIsNew(false);
        } else {
        	nurseDayScore = new NurseDayScore();
        	nurseDayScore.setCheckperiod(checkPeriod);
        	nurseDayScore.setOperatorid(operator);
        	nurseDayScore.setOperatorname(operator.getName());
        	nurseDayScore.setOperatedate(calendar.getTime());
        	nurseDayScore.setScoredate(calendar.getTime()); 
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String nurseDayScoreGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					NurseDayScore nurseDayScore = nurseDayScoreManager.get(new Long(removeId));
					nurseDayScoreManager.delNurseDayscore(new Long(removeId));
					
				}
				gridOperationMessage = this.getText("nurseDayScore.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkNurseDayScoreGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (nurseDayScore == null) {
			return "Invalid nurseDayScore Data";
		}

		return SUCCESS;

	}
	
	public String checkPeriod(){
		try {
			String periodCode = periodManager.getPeriodCodeByDate(DateUtil.convertStringToDate(selectDate));
			if(periodCode!=null){
				return ajaxForward(true, periodCode, false);
			}else{
				return ajaxForward(false, "所选日期没有相应的核算期间！", false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	public String auditNurseDayScore(){
		try {
			Person operator = this.getUserManager().getCurrentLoginUser().getPerson();
//	    	checkPeriod = periodManager.getCurrentPeriod().getPeriodCode();
			checkPeriod =  this.getLoginPeriod();
	    	Calendar calendar = Calendar.getInstance();
			String[] idArr = id.split(",");
			for(String idTemp : idArr){
				nurseDayScore = nurseDayScoreManager.get(new Long(idTemp));
				/*if(nurseDayScore.getState()==1){
					return ajaxForward(false,"")
				}*/
				nurseDayScore.setAuditid(operator);
				nurseDayScore.setAuditdate(calendar.getTime());
				nurseDayScore.setState(1);
				
				List<NurseDayScoreDetail> nurseDayScoreDetails = nurseDayScoreDetailManager.findByNurseDayScore(nurseDayScore);
				for(NurseDayScoreDetail nurseDayScoreDetail : nurseDayScoreDetails){
					nurseDayScoreDetail.setState(1);
					nurseDayScoreDetailManager.save(nurseDayScoreDetail);
				}
				nurseDayScoreManager.save(nurseDayScore);
			}
			return ajaxForward(true,"审核成功！",false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ajaxForward(false,"审核失败！",false);
		}
	}
}

