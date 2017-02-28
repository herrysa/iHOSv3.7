package com.huge.ihos.gz.gzType.webapp.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.huge.ihos.gz.gzContent.model.GzContent;
import com.huge.ihos.gz.gzContent.service.GzContentManager;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.gz.util.GzUtil;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.MonthPerson;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class GzTypePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2126171746268954458L;
	private GzTypeManager gzTypeManager;
	private List<GzType> gzTypes;
	private GzType gzType;
	private String gzTypeId;
	private String id;
	private String tablecontainer;
	private DepartmentManager departmentManager;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public GzTypeManager getGzTypeManager() {
		return gzTypeManager;
	}

	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}

	public List<GzType> getgzTypes() {
		return gzTypes;
	}

	public void setGzTypes(List<GzType> gzTypes) {
		this.gzTypes = gzTypes;
	}

	public GzType getGzType() {
		return gzType;
	}

	public void setGzType(GzType gzType) {
		this.gzType = gzType;
	}

	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String gzTypeList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("gzTypeList Error", e);
		}
		return SUCCESS;
	}

	public String gzTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("NES_gzTypeId","XT"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = gzTypeManager.getGzTypeCriteria(pagedRequests,
					filters);
			this.gzTypes = (List<GzType>) pagedRequests.getList();
			User sessionUser = getSessionUser();
			String userId = sessionUser.getId().toString();
			GzType curGzType = gzTypeManager.getCurrentGzType(userId);
			if(OtherUtil.measureNotNull(curGzType)&&OtherUtil.measureNotNull(gzTypes)&&!gzTypes.isEmpty()){
				String curGzTypeId = curGzType.getGzTypeId();
				for(GzType gzTypeTemp:gzTypes){
					if(curGzTypeId.equals(gzTypeTemp.getGzTypeId())){
						gzTypeTemp.setStatus(true);
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

	public String save() {
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			if (null == gzType.getStatus()) {
				gzType.setStatus(false);
			}
			gzTypeManager.savaGzType(gzType,oper,isEntityIsNew());
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "gzType.added"
				: "gzType.updated";
		return ajaxForward(this.getText(key));
	}

	private String gzTypeStatus;

	public String edit() {
		if (gzTypeId != null) {
			gzType = gzTypeManager.get(gzTypeId);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			String modelId = "GZ_" + gzTypeId;
			filters.add(new PropertyFilter("EQS_modelId",modelId));
			modelStatuss = modelStatusManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
				gzTypeStatus = "closed";
			}
			this.setEntityIsNew(false);
		} else {
			gzType = new GzType();
			gzType.setIssueNumber(1);
			gzType.setIssueDate(this.getLoginPeriod());
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String gzTypeGridEdit() {
		try {
			if ("del".equals(oper)) {
				String[] removeIdArr = id.split(",");
				if (OtherUtil.measureNotNull(removeIdArr)) {
					List<String> removeIds = Arrays.asList(removeIdArr);
					gzTypeManager.removeGzType(removeIds);
				}
				gridOperationMessage = this.getText("gzType.deleted");
			}else if("disable".equals(oper)){
				if (OtherUtil.measureNotNull(id)) {
					String[] disableIdArr = id.split(",");
					gzTypes = new ArrayList<GzType>();
					for(String disableId:disableIdArr){
						gzType = gzTypeManager.get(disableId);
						gzType.setDisabled(true);
						gzTypes.add(gzType);
					}
					gzTypeManager.saveAll(gzTypes);
				}
				gridOperationMessage = this.getText("gzType.disable");
			}else if("enable".equals(oper)){
				String[] disableIdArr = id.split(",");
				if (OtherUtil.measureNotNull(disableIdArr)) {
					gzTypes = new ArrayList<GzType>();
					for(String disableId:disableIdArr){
						gzType = gzTypeManager.get(disableId);
						gzType.setDisabled(false);
						gzTypes.add(gzType);
					}
					gzTypeManager.saveAll(gzTypes);
				}
				gridOperationMessage = this.getText("gzType.enable");
			}else if("changeCur".equals(oper)){
				String[] changeIdArr = this.getRequest().getParameterValues("gzTypeId");
				User sessionUser = getSessionUser();
				String userId = sessionUser.getId().toString();
				gzTypeManager.changeCurrentGzType(userId, changeIdArr);
				gridOperationMessage = this.getText("gzType.changeCur");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkGzTypeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (gzType == null) {
			return "Invalid gzType Data";
		}

		return SUCCESS;

	}

	/**
	 * 检查用户填写的code是否已经存在
	 * **/
	public String checkCodeExist() {
		String code = this.getRequest().getParameter("code");
		// 根据code去查询是否存在
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_gzTypeId", code));
		List<GzType> lst = gzTypeManager.getByFilters(filters);
		if (lst.size() == 0) {
			return ajaxForward("0");
		}
		return ajaxForward("1");
	}

	/* 检查工资类别是否可以删除 */
	private GzItemManager gzItemManager;
	private GzContentManager gzContentManager;

	public String checkGzTypeDel() {
		if (OtherUtil.measureNull(id)) {
			return ajaxForward("");
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_gzType.gzTypeId", id));
		filters.add(new PropertyFilter("EQB_sysField","1"));
		List<GzItem> gzItems = gzItemManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(gzItems) && !gzItems.isEmpty()) {
			return ajaxForward("该工资类别下包含系统工资项目，不能删除。");
		}
		filters.clear();
		filters.add(new PropertyFilter("INS_gzType", id));
		List<GzContent> gzContents = gzContentManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(gzContents) && !gzContents.isEmpty()) {
			return ajaxForward("该工资类别已进行工资数据编辑，不能删除。");
		}
		filters.clear();
		//filters.add(new PropertyFilter("INS_gzType",id));
		PropertyFilter gzTypeFilter = new PropertyFilter("INS_gzType",id);
		filters.add(gzTypeFilter);
		List<Person> persons = personManager.getByFilters(filters);
		filters.remove(gzTypeFilter);
		filters.add(new PropertyFilter("INS_gzType2",id));
		persons.addAll(personManager.getByFilters(filters));
		if (OtherUtil.measureNotNull(persons) && !persons.isEmpty()) {
			return ajaxForward("该工资类别已被职工表使用，不能删除。");
		}
		filters.clear();
		//filters.add(new PropertyFilter("INS_gzType",id));
		gzTypeFilter = new PropertyFilter("INS_gzType",id);
		filters.add(gzTypeFilter);
		List<MonthPerson> monthPersons = personManager.getByFilters(filters,MonthPerson.class);
		filters.remove(gzTypeFilter);
		filters.add(new PropertyFilter("INS_gzType2",id));
		monthPersons.addAll(personManager.getByFilters(filters,MonthPerson.class));
		if (OtherUtil.measureNotNull(monthPersons) && !monthPersons.isEmpty()) {
			return ajaxForward("该工资类别已被月度职工表使用，不能删除。");
		}
		return ajaxForward("");
	}

	public GzItemManager getGzItemManager() {
		return gzItemManager;
	}

	public void setGzItemManager(GzItemManager gzItemManager) {
		this.gzItemManager = gzItemManager;
	}

	public GzContentManager getGzContentManager() {
		return gzContentManager;
	}

	public void setGzContentManager(GzContentManager gzContentManager) {
		this.gzContentManager = gzContentManager;
	}

	public String getGzTypeStatus() {
		return gzTypeStatus;
	}

	public void setGzTypeStatus(String gzTypeStatus) {
		this.gzTypeStatus = gzTypeStatus;
	}

	public String getTablecontainer() {
		return tablecontainer;
	}

	public void setTablecontainer(String tablecontainer) {
		this.tablecontainer = tablecontainer;
	}
	/*工资职员信息*/
	private String gzTypeJsonStr;
	private PersonTypeManager personTypeManager;
	private List postTypeList;
	private List statusList;
	private List dutyList;
	private List educationalLevelList;
	private List jobTitleList;
	private List sexList;
	private List<Branch> branches;
	private BranchManager branchManager;
	public String gzPersonList(){
		try {
			currentPeriod = this.getLoginPeriod();
			this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode( "postType" );
			this.statusList = personTypeManager.personTypeDictionaryList();
			this.branches = this.branchManager.getAllAvailable();
			gzTypes = gzTypeManager.allGzTypes(true);
			String jsonStr = "[";
			if(OtherUtil.measureNotNull(gzTypes)&&!gzTypes.isEmpty()){
				for(GzType gzTypeTemp:gzTypes){
					jsonStr += "{\"gzTypeId\":\""+gzTypeTemp.getGzTypeId()+"\",\"gzTypeName\":\""+gzTypeTemp.getGzTypeName()+"\"},";
				}
				jsonStr = OtherUtil.subStrEnd(jsonStr, ",");
			}
			jsonStr += "]";
			gzTypeJsonStr = jsonStr;
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("gzPersonList Error", e);
		}
		return SUCCESS;
	}
	private Person person;
	private PersonManager personManager;
	public String editGzPerson(){
		this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode( "postType" );
		this.statusList = personTypeManager.personTypeDictionaryList();
		this.dutyList = this.getDictionaryItemManager().getAllItemsByCode( "postDuty" );
	    this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode( "education" );
	    this.jobTitleList = this.getDictionaryItemManager().getAllItemsByCode( "jobTitle" );
	    this.sexList = this.getDictionaryItemManager().getAllItemsByCode( "sex" );
		HttpServletRequest req = this.getRequest();
		List<GzType> gztypes = gzTypeManager.allGzTypes(true);
    	req.setAttribute("gztypes", gztypes);
    	String deptId = req.getParameter("deptId");
    	String personId = req.getParameter("personId");
    	if (OtherUtil.measureNotNull(personId)) {
             person = personManager.get( personId );
             this.setEntityIsNew(false);
    	}else{
    		person = new Person();
    		
    		if(OtherUtil.measureNotNull(deptId)){
        		Department department = departmentManager.get(deptId);
        		person.setDepartment(department);
        		person.setOrgCode(department.getOrgCode());
        		person.setBranchCode(department.getBranchCode());
        		person.setBranch(department.getBranch());
        	}
    		 this.setEntityIsNew(true);
    	}
    	return SUCCESS;
	}
	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}
	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

	public String getGzTypeJsonStr() {
		return gzTypeJsonStr;
	}

	public void setGzTypeJsonStr(String gzTypeJsonStr) {
		this.gzTypeJsonStr = gzTypeJsonStr;
	}
	public List getPostTypeList() {
	    return postTypeList;
	}

	public List getStatusList() {
		return statusList;
	}
	
	public void setPersonTypeManager(PersonTypeManager personTypeManager) {
		this.personTypeManager = personTypeManager;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public List getDutyList() {
		return dutyList;
	}

	public List getEducationalLevelList() {
		return educationalLevelList;
	}

	public List getJobTitleList() {
		return jobTitleList;
	}

	public List getSexList() {
		return sexList;
	}
	/*======================工资结账/反结账===========================*/
	public String gzCloseCount(){
		HttpServletRequest request = this.getRequest();
		String NowgzType = "";
		GzType curGzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
		if(OtherUtil.measureNotNull(curGzType)){
			NowgzType = curGzType.getGzTypeId();
		}
		List<GzType> gztypes = gzTypeManager.allGzTypes(false);
		request.setAttribute("gztypes", gztypes);
		request.setAttribute("now", NowgzType);
		return SUCCESS;
	}
	public String gzAntiCount(){
		HttpServletRequest request = this.getRequest();
		String NowgzType = "";
		GzType curGzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
		if(OtherUtil.measureNotNull(curGzType)){
			NowgzType = curGzType.getGzTypeId();
		}
		List<GzType> gztypes = gzTypeManager.allGzTypes(false);
		request.setAttribute("gztypes", gztypes);
		request.setAttribute("now", NowgzType);
		return SUCCESS;
	}
	private List<Map<String, Object>> gzModelStatusList;
	private List<ModelStatus> modelStatuss;
	private PeriodManager periodManager;
	public List<ModelStatus> getModelStatuss(){
		return modelStatuss;
	}
	public void setModelStatuss(List<ModelStatus> modelStatuss){
		this.modelStatuss = modelStatuss;
	}
	public PeriodManager getPeriodManager(){
		return periodManager;
	}
	public void setPeriodManager(PeriodManager periodManager){
		this.periodManager = periodManager;
	}
	/*待结账列表*/
	public String gzCloseCountGridList(){
		log.debug("enter list method!");
		try {
			/*取出所有工资类别最新的结账期间*/
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("LIKES_modelId","GZ*"));
			filters.add(new PropertyFilter("EQS_status","1"));
			this.modelStatuss = modelStatusManager.getByFilters(filters);
			Map<String, ModelStatus> modelStatusMap = new HashMap<String, ModelStatus>();
			if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
				for(ModelStatus msTemp:modelStatuss){
					String modelId = msTemp.getModelId();
					if(modelStatusMap.containsKey(modelId)){
						ModelStatus msMapTemp = modelStatusMap.get(modelId);
						String checkPeriod = msTemp.getCheckperiod();
						int checkNumber = msTemp.getCheckNumber();
						String oldCheckPeriod = msMapTemp.getCheckperiod();
						int oldCheckNumber = msMapTemp.getCheckNumber();
						if(checkPeriod.substring(0, 4).equals(oldCheckPeriod.substring(0, 4))){
							if(checkNumber < oldCheckNumber){
								modelStatusMap.put(modelId, msTemp);
							}
						}else if(Integer.parseInt(checkPeriod.substring(0, 4))<Integer.parseInt(oldCheckPeriod.substring(0, 4))){
							modelStatusMap.put(modelId, msTemp);
						}
					}else{
						modelStatusMap.put(modelId, msTemp);
					}
				}
			}
			/*取出工资类别*/
			List<PropertyFilter> gzTypeFilters = new ArrayList<PropertyFilter>();
			gzTypeFilters.add(new PropertyFilter("EQB_disabled","0"));
			List<GzType> gzTypes = gzTypeManager.getByFilters(gzTypeFilters);
			Map<String, GzType> gzTypeMap = new HashMap<String, GzType>();
			if(OtherUtil.measureNotNull(gzTypes)&&!gzTypes.isEmpty()){
				for(GzType gtTemp:gzTypes){
					gzTypeMap.put(gtTemp.getGzTypeId(), gtTemp);
				}
			}
			HttpServletRequest request = this.getRequest();
			String gzTypeIdStr = request.getParameter("gzTypeId");
			String checkNumbersStr = request.getParameter("checkNumber");
			String checkperiodStr = request.getParameter("checkperiod");
			String issueTypeStr = request.getParameter("issueType");
			gzModelStatusList = new ArrayList<Map<String,Object>>();
			if(!modelStatusMap.isEmpty()){
				for (ModelStatus msTemp : modelStatusMap.values()) {
					Map<String,Object> msMap = new HashMap<String, Object>();
					msMap.put("id", msTemp.getId());
					msMap.put("periodType", msTemp.getPeriodType());
					msMap.put("checkperiod", msTemp.getCheckperiod());
					msMap.put("status", msTemp.getStatus());
					msMap.put("checkNumber", msTemp.getCheckNumber());
					String checkNumber = msTemp.getCheckNumber()+"";
					String checkperiod = msTemp.getCheckperiod();
					String modelId =  msTemp.getModelId();
					msMap.put("modelId",modelId);
					msMap.put("gzTypeName","");
					msMap.put("issueType","");
					if(OtherUtil.measureNotNull(modelId)&&modelId.length()>2){
						String gzTypeId = modelId.substring(3);
						if(gzTypeMap.containsKey(gzTypeId)){
							GzType gtTemp = gzTypeMap.get(gzTypeId);
							String issueType = gtTemp.getIssueType();
							msMap.put("gzTypeName",gtTemp.getGzTypeName());
							msMap.put("issueType",gtTemp.getIssueType());
							if(OtherUtil.measureNotNull(gzTypeIdStr)&&gzTypeIdStr.equals(gzTypeId)||
									OtherUtil.measureNotNull(checkNumbersStr)&&checkNumbersStr.equals(checkNumber)||
									OtherUtil.measureNotNull(checkperiodStr)&&checkperiodStr.equals(checkperiod)||
									OtherUtil.measureNotNull(issueTypeStr)&&issueTypeStr.equals(issueType)||
									OtherUtil.measureNull(gzTypeIdStr)&&OtherUtil.measureNull(checkNumbersStr)&&
									OtherUtil.measureNull(checkperiodStr)&&OtherUtil.measureNull(issueTypeStr)){
								gzModelStatusList.add(msMap);								
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	/*已结账列表*/
	public String gzAntiCountGridList(){
		log.debug("enter list method!");
		try {
			/*取出所有工资类别最新的结账期间*/
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("LIKES_modelId","GZ*"));
			filters.add(new PropertyFilter("EQS_status","2"));
			this.modelStatuss = modelStatusManager.getByFilters(filters);
			Map<String, ModelStatus> modelStatusMap = new HashMap<String, ModelStatus>();
			if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
				for(ModelStatus msTemp:modelStatuss){ 
					String modelId = msTemp.getModelId();
					if(modelStatusMap.containsKey(modelId)){
						ModelStatus msMapTemp = modelStatusMap.get(modelId);
						String checkPeriod = msTemp.getCheckperiod();
						int checkNumber = msTemp.getCheckNumber();
						String oldCheckPeriod = msMapTemp.getCheckperiod();
						int oldCheckNumber = msMapTemp.getCheckNumber();
						if(checkPeriod.substring(0, 4).equals(oldCheckPeriod.substring(0, 4))){
							if(checkNumber > oldCheckNumber){
								modelStatusMap.put(modelId, msTemp);
							}
						}else if(Integer.parseInt(checkPeriod.substring(0, 4))>Integer.parseInt(oldCheckPeriod.substring(0, 4))){
							modelStatusMap.put(modelId, msTemp);
						}
					}else{
						modelStatusMap.put(modelId, msTemp);
					}
				}
			}
			/*取出工资类别*/
			List<PropertyFilter> gzTypeFilters = new ArrayList<PropertyFilter>();
			gzTypeFilters.add(new PropertyFilter("EQB_disabled","0"));
			List<GzType> gzTypes = gzTypeManager.getByFilters(gzTypeFilters);
			Map<String, GzType> gzTypeMap = new HashMap<String, GzType>();
			if(OtherUtil.measureNotNull(gzTypes)&&!gzTypes.isEmpty()){
				for(GzType gtTemp:gzTypes){
					gzTypeMap.put(gtTemp.getGzTypeId(), gtTemp);
				}
			}
			HttpServletRequest request = this.getRequest();
			String gzTypeIdStr = request.getParameter("gzTypeId");
			String checkNumbersStr = request.getParameter("checkNumber");
			String checkperiodStr = request.getParameter("checkperiod");
			String issueTypeStr = request.getParameter("issueType");
			gzModelStatusList = new ArrayList<Map<String,Object>>();
			if(!modelStatusMap.isEmpty()){
				for (ModelStatus msTemp : modelStatusMap.values()) {
					Map<String,Object> msMap = new HashMap<String, Object>();
					msMap.put("id", msTemp.getId());
					msMap.put("periodType", msTemp.getPeriodType());
					msMap.put("checkperiod", msTemp.getCheckperiod());
					msMap.put("status", msTemp.getStatus());
					msMap.put("checkNumber", msTemp.getCheckNumber());
					String checkNumber = msTemp.getCheckNumber()+"";
					String checkperiod = msTemp.getCheckperiod();
					String modelId =  msTemp.getModelId();
					msMap.put("modelId",modelId);
					msMap.put("gzTypeName","");
					msMap.put("issueType","");
					if(OtherUtil.measureNotNull(modelId)&&modelId.length()>2){
						String gzTypeId = modelId.substring(3);
						if(gzTypeMap.containsKey(gzTypeId)){
							GzType gtTemp = gzTypeMap.get(gzTypeId);
							String issueType = gtTemp.getIssueType();
							msMap.put("gzTypeName",gtTemp.getGzTypeName());
							msMap.put("issueType",gtTemp.getIssueType());
							if(OtherUtil.measureNotNull(gzTypeIdStr)&&gzTypeIdStr.equals(gzTypeId)||
									OtherUtil.measureNotNull(checkNumbersStr)&&checkNumbersStr.equals(checkNumber)||
									OtherUtil.measureNotNull(checkperiodStr)&&checkperiodStr.equals(checkperiod)||
									OtherUtil.measureNotNull(issueTypeStr)&&issueTypeStr.equals(issueType)||
									OtherUtil.measureNull(gzTypeIdStr)&&OtherUtil.measureNull(checkNumbersStr)&&
									OtherUtil.measureNull(checkperiodStr)&&OtherUtil.measureNull(issueTypeStr)){
								gzModelStatusList.add(msMap);								
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	private String nextPeriod;
	/*结账*/
	public String gzModelStatusClose(){
		try {
			ModelStatus mStatus = modelStatusManager.get(id);
			String period = mStatus.getCheckperiod();
			String lastPeriod = GzUtil.getLastPeriod(period, globalParamManager.getGlobalParamByKey("personDelayMonth"));
			String gzType = mStatus.getModelId().substring(3);
			String gzNoCheckedClose = this.getGlobalParamByKey("gzNoCheckedClose");//未审核工资不允许结账:0--不需要;1--需要
			//Boolean gzContentChecked = gzContentManager.getGzContentChecked(period, mStatus.getCheckNumber()+"");
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("NES_personId","XT"));
			filters.add(new PropertyFilter("EQB_stopSalary","0"));
			//filters.add(new PropertyFilter("EQB_disable","0"));
			PropertyFilter gzTypeFilter = new PropertyFilter("EQS_gzType",gzType);
			filters.add(gzTypeFilter);
			//filters.add(new PropertyFilter("EQS_gzType",gzType));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			List<MonthPerson> monthPersons = gzContentManager.getByFilters(filters,MonthPerson.class);
			filters.remove(gzTypeFilter);
			filters.add(new PropertyFilter("EQS_gzType2",gzType));
			monthPersons.addAll(gzContentManager.getByFilters(filters,MonthPerson.class));
			Set<Branch> branches = new HashSet<Branch>();
			for(MonthPerson monthPerson: monthPersons) {
				Branch branch = monthPerson.getBranch();
				if(branch!=null){
					branches.add(branch);
				}
			}
			if(!branches.isEmpty()) {
				String message = "";
				for(Branch branch:branches) {
					Boolean gzContentInheritCheck = gzContentManager.getGzContentChecked(period,mStatus.getCheckNumber()+"",gzType, branch.getBranchCode(), null);
					if(gzContentInheritCheck) {
						message += period + branch.getBranchName() + "工资数据尚未初始化!";
					} else {
						Boolean gzContentChecked = gzContentManager.getGzContentChecked(period,mStatus.getCheckNumber()+"",gzType, branch.getBranchCode(), "0");
						if(!gzContentChecked&&"1".equals(gzNoCheckedClose)){
							message += period + branch.getBranchName() + "工资数据尚未审核!";
						}
					}
					
				}
				if(!"".equals(message)) {
					return ajaxForwardError(message);
				}
				
			}
			modelStatusManager.gzModelStatusClose(id,nextPeriod,oper);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "结账成功。";
		return ajaxForward(this.getText(key));
	}
	/*判断下一期间是否定义*/
	public String gzModelStatusNextPeriodCheck(){
		if("anti".equals(oper)){
			ModelStatus mStatus = modelStatusManager.get(id);
			String modelId = mStatus.getModelId();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_modelId",modelId));
			filters.add(new PropertyFilter("EQS_status","1"));
			filters.add(new PropertyFilter("NES_id",id));
			modelStatuss = modelStatusManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
				oper = "anti";
			}else{
				oper = "closed";
			}
		}
		if(OtherUtil.measureNotNull(id)){
			ModelStatus mStatus = modelStatusManager.get(id);
			String period = mStatus.getCheckperiod();
			nextPeriod = periodManager.getNextPeriod(period);
			if(OtherUtil.measureNull(nextPeriod)){
				return ajaxForward("期间"+period+"的下一个期间尚未定义！");
			}
		}else{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_periodCode",nextPeriod));
			List<Period> periods = periodManager.getByFilters(filters);
			if(OtherUtil.measureNull(periods)||periods.isEmpty()){
				return ajaxForward("期间"+nextPeriod+"尚未定义！");
			}
		}
		return ajaxForward("");
	}
	/*次结获取下一期间*/
	public String gzModelStatusNextPeriod(){
//		if(OtherUtil.measureNotNull(id)){
//			ModelStatus mStatus = modelStatusManager.get(id);
//			String period = mStatus.getCheckperiod();
//			nextPeriod = periodManager.getNextPeriod(period);
//		}
		return SUCCESS;
	}
	/*反结账*/
	public String gzModelStatusAnti(){
		try {
			ModelStatus modelStatus = modelStatusManager.get(id);
			modelStatus.setStatus("1");
			modelStatusManager.save(modelStatus);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "反结账成功。";
		return ajaxForward(this.getText(key));
	}
	/*结账页面刷新*/
	public String gzRefreshCount(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQB_disabled","0"));
			filters.add(new PropertyFilter("NES_gzTypeId","XT"));
			List<GzType> gzTypes = gzTypeManager.getByFilters(filters);
			filters.clear();
			filters.add(new PropertyFilter("LIKES_modelId","GZ*"));
			filters.add(new PropertyFilter("ODS_checkperiod",""));
			filters.add(new PropertyFilter("ODI_checkNumber",""));
			modelStatuss = modelStatusManager.getByFilters(filters);
			List<ModelStatus> needSaveMsList = new ArrayList<ModelStatus>();
			if(OtherUtil.measureNotNull(gzTypes)&&!gzTypes.isEmpty()){
				for(GzType gzType:gzTypes){
					String gzTypeId = gzType.getGzTypeId();
					String modelIdString = "GZ_"+gzTypeId;
					String insertFlag = "";
					String checkPeriodString = "";
					int checkNumber = 1;
					if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
						List<ModelStatus> status1List = new ArrayList<ModelStatus>();
						List<ModelStatus> status2List = new ArrayList<ModelStatus>();
						for(ModelStatus msTemp:modelStatuss){
							String modelIdTemp = msTemp.getModelId();
							String statusTemp = msTemp.getStatus();
							if(modelIdString.equals(modelIdTemp)){
								if("1".equals(statusTemp)){
									status1List.add(msTemp);
								}else if("2".equals(statusTemp)){
									status2List.add(msTemp);
								}
							}
						}
						if(status1List.isEmpty()&&status2List.isEmpty()){
							insertFlag = "newType";
						}else if(!status1List.isEmpty()){
							continue;
						}else if(!status2List.isEmpty()){
							insertFlag = "newPeriod";
							checkPeriodString = status2List.get(0).getCheckperiod();
							checkNumber	= status2List.get(0).getCheckNumber();
						}
					}else{
						insertFlag = "newType";
					}
					if(OtherUtil.measureNull(insertFlag)){
						continue;
					}else if("newType".equals(insertFlag)){
						checkPeriodString = gzType.getIssueDate();
						checkNumber	= gzType.getIssueNumber();
						filters.clear();
						filters.add(new PropertyFilter("EQS_periodCode",checkPeriodString));
						List<Period> periods = periodManager.getByFilters(filters);
						if(OtherUtil.measureNotNull(periods)&&!periods.isEmpty()){
							ModelStatus msTemp = new ModelStatus();
							msTemp.setModelId(modelIdString);
							msTemp.setPeriodType("月");
							msTemp.setCheckNumber(checkNumber);
							msTemp.setCheckperiod(checkPeriodString);
							msTemp.setStatus("1");
							needSaveMsList.add(msTemp);
						}
					}else if("newPeriod".equals(insertFlag)){
						String nextPeriodString = periodManager.getNextPeriod(checkPeriodString);
						if(OtherUtil.measureNotNull(nextPeriodString)){
							ModelStatus msTemp = new ModelStatus();
							msTemp.setModelId(modelIdString);
							msTemp.setPeriodType("月");
							msTemp.setCheckNumber(checkNumber+1);
							msTemp.setCheckperiod(nextPeriodString);
							msTemp.setStatus("1");
							needSaveMsList.add(msTemp);
						}
					}
				}
			}
			if(!needSaveMsList.isEmpty()){
				modelStatusManager.saveAll(needSaveMsList);
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "刷新成功。";
		return ajaxForward(this.getText(key));
	}
	public List<Map<String, Object>> getGzModelStatusList() {
		return gzModelStatusList;
	}

	public void setGzModelStatusList(List<Map<String, Object>> gzModelStatusList) {
		this.gzModelStatusList = gzModelStatusList;
	}


	public String getNextPeriod() {
		return nextPeriod;
	}

	public void setNextPeriod(String nextPeriod) {
		this.nextPeriod = nextPeriod;
	}
	//检查结账表删除
	public String checkGzDelCount() {
		if (OtherUtil.measureNull(id)) {
			return ajaxForward("");
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_id",id));
		modelStatuss = modelStatusManager.getByFilters(filters);
		String gzTypeId = "";
		if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
			for(ModelStatus mStatus:modelStatuss){
				String modelId = mStatus.getModelId();
				gzTypeId += modelId.substring(3) + ",";
			}
		}
		if(OtherUtil.measureNotNull(gzTypeId)){
			OtherUtil.subStrEnd(gzTypeId, ",");
		}
		filters.clear();
		filters.add(new PropertyFilter("INS_gzType", gzTypeId));
		List<GzContent> gzContents = gzContentManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(gzContents) && !gzContents.isEmpty()) {
			return ajaxForward("该工资类别已进行工资数据编辑，不能删除。");
		}
		return ajaxForward("");
	}
}
