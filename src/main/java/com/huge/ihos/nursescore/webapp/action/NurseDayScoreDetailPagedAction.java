package com.huge.ihos.nursescore.webapp.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.huge.ihos.nursescore.model.MonthNurse;
import com.huge.ihos.nursescore.model.NurseDayScore;
import com.huge.ihos.nursescore.model.NurseDayScoreDetail;
import com.huge.ihos.nursescore.model.NursePostRate;
import com.huge.ihos.nursescore.model.NurseShiftRate;
import com.huge.ihos.nursescore.service.MonthNurseManager;
import com.huge.ihos.nursescore.service.NurseDayScoreDetailManager;
import com.huge.ihos.nursescore.service.NurseDayScoreManager;
import com.huge.ihos.nursescore.service.NursePostRateManager;
import com.huge.ihos.nursescore.service.NurseShiftRateManager;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.huge.webapp.util.RequestUtil;
import com.huge.webapp.util.SpringContextHelper;




public class NurseDayScoreDetailPagedAction extends JqGridBaseAction  {

	private NurseDayScoreDetailManager nurseDayScoreDetailManager;
	private NurseDayScoreManager nurseDayScoreManager;
	private List<NurseDayScoreDetail> nurseDayScoreDetails;
	private NurseDayScoreDetail nurseDayScoreDetail;
	private Long dayScoreDetailID;
	private String postSelect = "";
	private String shiftSelect = "";
	private NursePostRateManager nursePostRateManager;
	private NurseShiftRateManager nurseShiftRateManager;
	private PeriodManager periodManager;
	private PersonManager personManager;
	private MonthNurseManager monthNurseManager;
	private String checkPeriod;
	private Long dayScoreID;
	private String deptId;
	private DataSource dataSource = SpringContextHelper.getDataSource();

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	private String selectedPerson = "";

	public String getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(String selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public List<NurseDayScoreDetail> getNurseDayScoreDetails() {
		return nurseDayScoreDetails;
	}

	public Long getDayScoreID() {
		return dayScoreID;
	}

	public void setDayScoreID(Long dayScoreID) {
		this.dayScoreID = dayScoreID;
	}

	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public NurseDayScoreDetailManager getNurseDayScoreDetailManager() {
		return nurseDayScoreDetailManager;
	}

	public void setNurseDayScoreDetailManager(NurseDayScoreDetailManager nurseDayScoreDetailManager) {
		this.nurseDayScoreDetailManager = nurseDayScoreDetailManager;
	}

	public MonthNurseManager getMonthNurseManager() {
		return monthNurseManager;
	}

	public void setMonthNurseManager(MonthNurseManager monthNurseManager) {
		this.monthNurseManager = monthNurseManager;
	}

	public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public NurseDayScoreManager getNurseDayScoreManager() {
		return nurseDayScoreManager;
	}

	public void setNurseDayScoreManager(NurseDayScoreManager nurseDayScoreManager) {
		this.nurseDayScoreManager = nurseDayScoreManager;
	}

	public String getPostSelect() {
		return postSelect;
	}

	public void setPostSelect(String postSelect) {
		this.postSelect = postSelect;
	}

	public String getShiftSelect() {
		return shiftSelect;
	}

	public void setShiftSelect(String shiftSelect) {
		this.shiftSelect = shiftSelect;
	}

	public NursePostRateManager getNursePostRateManager() {
		return nursePostRateManager;
	}

	public void setNursePostRateManager(NursePostRateManager nursePostRateManager) {
		this.nursePostRateManager = nursePostRateManager;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public NurseShiftRateManager getNurseShiftRateManager() {
		return nurseShiftRateManager;
	}

	public void setNurseShiftRateManager(NurseShiftRateManager nurseShiftRateManager) {
		this.nurseShiftRateManager = nurseShiftRateManager;
	}

	public List<NurseDayScoreDetail> getnurseDayScoreDetails() {
		return nurseDayScoreDetails;
	}

	public void setNurseDayScoreDetails(List<NurseDayScoreDetail> nurseDayScoreDetails) {
		this.nurseDayScoreDetails = nurseDayScoreDetails;
	}

	public NurseDayScoreDetail getNurseDayScoreDetail() {
		return nurseDayScoreDetail;
	}

	public void setNurseDayScoreDetail(NurseDayScoreDetail nurseDayScoreDetail) {
		this.nurseDayScoreDetail = nurseDayScoreDetail;
	}

	public Long getDayScoreDetailID() {
		return dayScoreDetailID;
	}

	public void setDayScoreDetailID(Long dayScoreDetailID) {
        this.dayScoreDetailID = dayScoreDetailID;
    }


	public String prepareNurseDayScoreDetailInfo(){
		try {
			List<NursePostRate> nursePostRates = nursePostRateManager.getAll();
			for(NursePostRate nursePostRate :nursePostRates){
				postSelect += nursePostRate.getCode()+":"+nursePostRate.getPost()+";";
			}
			if(!postSelect.equals("")){
				postSelect = postSelect.substring(0,postSelect.length()-1);
			}
			
			List<NurseShiftRate> nurseShiftRates = nurseShiftRateManager.getAll();
			for(NurseShiftRate nurseShiftRate :nurseShiftRates){
				shiftSelect += nurseShiftRate.getCode()+":"+nurseShiftRate.getShift()+";";
			}
			if(!shiftSelect.equals("")){
				shiftSelect = shiftSelect.substring(0,shiftSelect.length()-1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SUCCESS;
	}
	
	public String nurseDayScoreDetailGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			PropertyFilter propertyFilterDayScoreID = new PropertyFilter("EQL_dayScoreID.dayScoreID",""+dayScoreID);
			filters.add(propertyFilterDayScoreID);
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = nurseDayScoreDetailManager
					.getNurseDayScoreDetailCriteria(pagedRequests,filters);
			this.nurseDayScoreDetails = (List<NurseDayScoreDetail>) pagedRequests.getList();
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
			String personIds = nurseDayScoreDetail.getPersonid().getPersonId();
			String[] personIdArr = personIds.split(",");
			for(String person:personIdArr){
				NurseDayScoreDetail nurseDayScoreDetailTemp = nurseDayScoreDetail;
				NurseDayScore nurseDayScore = nurseDayScoreManager.get(nurseDayScoreDetail.getDayScoreID().getDayScoreID());
				MonthNurse monthNurse = monthNurseManager.get(new Long(person));
				nurseDayScoreDetailTemp.setCode(monthNurse.getCode());
				nurseDayScoreDetailTemp.setGroupid(nurseDayScore.getGroupid());
				nurseDayScoreDetailTemp.setName(monthNurse.getName());
				nurseDayScoreDetailTemp.setPersonid(monthNurse.getPersonid());
				nurseDayScoreDetailTemp.setState(0);
				nurseDayScoreDetailTemp.setYearcode(monthNurse.getYearcode());
				nurseDayScoreDetailTemp.setDayScoreID(nurseDayScore);
				//nurseDayScoreDetail.set
				nurseDayScoreDetailManager.save(nurseDayScoreDetailTemp);
			}
			
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "nurseDayScoreDetail.added" : "nurseDayScoreDetail.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
    	Calendar calendar = Calendar.getInstance();
    	NurseDayScore nurseDayScore = nurseDayScoreManager.get(dayScoreID);
    	checkPeriod = nurseDayScore.getCheckperiod();
    	deptId = nurseDayScore.getGroupid().getDepartmentId();
    	if (dayScoreDetailID != null) {
        	nurseDayScoreDetail = nurseDayScoreDetailManager.get(dayScoreDetailID);
        	this.setEntityIsNew(false);
        } else {
        	nurseDayScoreDetail = new NurseDayScoreDetail();
        	nurseDayScoreDetail.setCheckperiod(nurseDayScore.getCheckperiod());
        	nurseDayScoreDetail.setScoredate(nurseDayScore.getScoredate());
        	nurseDayScoreDetail.setGroupid(nurseDayScore.getGroupid());
        	nurseDayScoreDetail.setGroupname(nurseDayScoreDetail.getGroupid().getName());
        	nurseDayScoreDetail.setDayScoreID(nurseDayScore);
        	this.setEntityIsNew(true);
        }
    	List<NurseDayScoreDetail> nurseDayScoreDetails = nurseDayScoreDetailManager.selectedPerson(nurseDayScore.getScoredate(),nurseDayScore.getGroupid().getDepartmentId());
    	selectedPerson += "(";
    	for(NurseDayScoreDetail nurseDayScoreDetail : nurseDayScoreDetails){
        	selectedPerson += "\\'"+nurseDayScoreDetail.getPersonid().getPersonId()+"\\',";
        }
    	if(selectedPerson.equals("(")){
    		selectedPerson += "\\'\\'";
    	}else{
    		selectedPerson = selectedPerson.substring(0,selectedPerson.length()-1);
    	}
    	selectedPerson += ")";
    	return SUCCESS;
    }
	public String nurseDayScoreDetailGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					Long removeId = Long.parseLong(ids.nextToken());
					log.debug("Delete Customer " + removeId);
					NurseDayScoreDetail nurseDayScoreDetail = nurseDayScoreDetailManager.get(new Long(removeId));
					nurseDayScoreDetailManager.remove(new Long(removeId));
					
				}
				gridOperationMessage = this.getText("nurseDayScoreDetail.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkNurseDayScoreDetailGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (nurseDayScoreDetail == null) {
			return "Invalid nurseDayScoreDetail Data";
		}

		return SUCCESS;

	}
	
	public String saveNurseDayScoreDetailInfo(){
		try {
			Map<String, Object> filterParamMap = RequestUtil.getParametersStartingWith( getRequest(), "defData_" );
			String defData = filterParamMap.get("editData").toString();
			String[] defColumnArr = defData.split("@");
			Map<String, String[]> defDataMap = new HashMap<String, String[]>();
			for(int i=0;i<defColumnArr.length;i++){
				String defColumn = defColumnArr[i];
					//updataId = defColumn.split("=")[1].split(",");
				defDataMap.put(defColumn.split("=")[0], defColumn.split("=")[1].split(","));
			}
			String[] idArr = (String[])defDataMap.get("id");
			for(int i=0;i<idArr.length-1;i++){
				Set<String> keySet = defDataMap.keySet();
				String setKeyValue = "";
				for(String key : keySet){
					if(!key.contains("id")){
						String defColumn = "";
						if(key.split("\\.").length>1){
							defColumn = key.split("\\.")[0];
						}else{
							defColumn = key;
						}
						setKeyValue += defColumn+"='"+((String[])defDataMap.get(key))[i]+"',";
					}
				}
				if(!setKeyValue.equals("")){
					setKeyValue = setKeyValue.substring(0,setKeyValue.length()-1);
				}
				String sql = "update jj_t_NurseDayScoreDetail set "+setKeyValue+" where DayScoreDetailID='"+idArr[i]+"'";
				JdbcTemplate jtl = new JdbcTemplate( this.dataSource );
	            jtl.execute(sql);
			}
            return ajaxForward( true, "保存成功", false );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ajaxForward( false, "保存失败", false );
		}
	}
}

