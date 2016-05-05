package com.huge.ihos.kq.kqType.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import sun.rmi.transport.ObjectTable;

import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.kq.kqType.service.KqTypeManager;
import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.ihos.kq.kqUpData.model.KqMonthData;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.service.KqDayDataManager;
import com.huge.ihos.kq.kqUpData.service.KqMonthDataManager;
import com.huge.ihos.kq.kqUpData.service.KqUpItemManager;
import com.huge.ihos.kq.util.KqUtil;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.ihos.system.context.UserContext;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.MonthPerson;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.ihos.system.systemManager.organization.service.PersonManager;
import com.huge.ihos.system.systemManager.organization.service.PersonTypeManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class KqTypePagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4973906765683456335L;
	private KqTypeManager kqTypeManager;
	private List<KqType> kqTypes;
	private KqType kqType;
	private String kqTypeId;

	public KqTypeManager getKqTypeManager() {
		return kqTypeManager;
	}

	public void setKqTypeManager(KqTypeManager kqTypeManager) {
		this.kqTypeManager = kqTypeManager;
	}

	public List<KqType> getkqTypes() {
		return kqTypes;
	}

	public void setKqTypes(List<KqType> kqTypes) {
		this.kqTypes = kqTypes;
	}

	public KqType getKqType() {
		return kqType;
	}

	public void setKqType(KqType kqType) {
		this.kqType = kqType;
	}

	public String getKqTypeId() {
		return kqTypeId;
	}

	public void setKqTypeId(String kqTypeId) {
		this.kqTypeId = kqTypeId;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	/**
	 * 添加菜单
	 * @return
	 */
	public String kqTypeList() {
		try {
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("kqTypeList Error", e);
		}
		return SUCCESS;
	}

	public String kqTypeGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			filters.add(new PropertyFilter("NES_kqTypeId", "XT"));
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = kqTypeManager.getKqTypeCriteria(pagedRequests, filters);
			this.kqTypes = (List<KqType>) pagedRequests.getList();
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
			kqTypeManager.saveKqType(kqType, oper);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "kqType.added" : "kqType.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (kqTypeId != null) {
			kqType = kqTypeManager.get(kqTypeId);
			this.setEntityIsNew(false);
		} else {
			kqType = new KqType();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String kqTypeGridEdit() {
		try {
			if ("del".equals(oper)) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete Customer " + removeId);
					//KqType kqType = kqTypeManager.get(removeId);
					kqTypeManager.remove(removeId);

				}
				gridOperationMessage = this.getText("kqType.deleted");
			} else if ("enable".equals(oper)) {
				if (OtherUtil.measureNotNull(id)) {
					StringTokenizer ids = new StringTokenizer(id, ",");
					kqTypes = new ArrayList<KqType>();
					while (ids.hasMoreElements()) {
						String editId = ids.nextToken();
						kqType = kqTypeManager.get(editId);
						kqType.setDisabled(false);
						kqTypes.add(kqType);
					}
					kqTypeManager.saveAll(kqTypes);
				}
				gridOperationMessage = this.getText("kqType.enable");
			} else if ("disable".equals(oper)) {
				if (OtherUtil.measureNotNull(id)) {
					StringTokenizer ids = new StringTokenizer(id, ",");
					kqTypes = new ArrayList<KqType>();
					while (ids.hasMoreElements()) {
						String editId = ids.nextToken();
						kqType = kqTypeManager.get(editId);
						kqType.setDisabled(true);
						kqTypes.add(kqType);
					}
					kqTypeManager.saveAll(kqTypes);
				}
				gridOperationMessage = this.getText("kqType.disable");
			}
			return ajaxForward(true, gridOperationMessage, false);
		} catch (Exception e) {
			log.error("checkKqTypeGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage() + e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private KqUpItemManager kqUpItemManager;
	private KqDayDataManager kqDayDataManager;
	private KqMonthDataManager kqMonthDataManager;

	public String checkKqTypeDel() {
		if (OtherUtil.measureNull(id)) {
			return ajaxForward("");
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("INS_kqType.kqTypeId", id));
		filters.add(new PropertyFilter("EQB_sysField", "1"));
		List<KqUpItem> kqUpItems = kqUpItemManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(kqUpItems) && !kqUpItems.isEmpty()) {
			return ajaxForward("该考勤类别下包含系统考勤上报项目，不能删除。");
		}
		filters.clear();
		filters.add(new PropertyFilter("INS_kqType", id));
		List<KqDayData> kqDayDatas = kqDayDataManager.getByFilters(filters);
		List<KqMonthData> kqMonthDatas = kqMonthDataManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(kqDayDatas) && !kqDayDatas.isEmpty()) {
			return ajaxForward("该考勤类别已进行日考勤上报，不能删除。");
		}
		if (OtherUtil.measureNotNull(kqMonthDatas) && !kqMonthDatas.isEmpty()) {
			return ajaxForward("该考勤类别已进行月考勤上报，不能删除。");
		}
		filters.clear();
		filters.add(new PropertyFilter("INS_kqType",id));
		List<Person> persons = kqTypeManager.getByFilters(filters, Person.class);
		if(OtherUtil.measureNotNull(persons) && !persons.isEmpty()) {
			return ajaxForward("该考勤类别已被职工表使用，不能删除。");
		}
		filters.clear();
		filters.add(new PropertyFilter("INS_kqType",id));
		List<MonthPerson> monthPersons = kqTypeManager.getByFilters(filters, MonthPerson.class);
		if(OtherUtil.measureNotNull(monthPersons) && !monthPersons.isEmpty()) {
			return ajaxForward("该考勤类别已被月度职工表使用，不能删除。");
		}
		return ajaxForward("");
	}

	private String isValid() {
		if (kqType == null) {
			return "Invalid kqType Data";
		}

		return SUCCESS;

	}

	/*考勤职员信息*/
	private String kqTypeJsonStr;
	private PersonTypeManager personTypeManager;
	private List postTypeList;
	private List statusList;
	private List dutyList;
	private List educationalLevelList;
	private List jobTitleList;
	private List sexList;
	private List<Branch> branches;
	private BranchManager branchManager;

	public String kqPersonList() {
		try {
			currentPeriod = this.getLoginPeriod();
			this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode("postType");
			this.statusList = personTypeManager.personTypeDictionaryList();
			this.branches = this.branchManager.getAllAvailable();
			kqTypes = kqTypeManager.allKqTypes(true);
			String jsonStr = "[";
			if (OtherUtil.measureNotNull(kqTypes) && !kqTypes.isEmpty()) {
				for (KqType kqTypeTemp : kqTypes) {
					jsonStr += "{\"kqTypeId\":\"" + kqTypeTemp.getKqTypeId() + "\",\"kqTypeName\":\"" + kqTypeTemp.getKqTypeName() + "\"},";
				}
				jsonStr = OtherUtil.subStrEnd(jsonStr, ",");
			}
			jsonStr += "]";
			kqTypeJsonStr = jsonStr;
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			log.error("kqPersonList Error", e);
		}
		return SUCCESS;
	}

	private Person person;
	private PersonManager personManager;
	private GzTypeManager gzTypeManager;
	private DepartmentManager departmentManager;

	public String editKqPerson() {
		this.postTypeList = this.getDictionaryItemManager().getAllItemsByCode("postType");
		this.statusList = personTypeManager.personTypeDictionaryList();
		this.dutyList = this.getDictionaryItemManager().getAllItemsByCode("postDuty");
		this.educationalLevelList = this.getDictionaryItemManager().getAllItemsByCode("education");
		this.jobTitleList = this.getDictionaryItemManager().getAllItemsByCode("jobTitle");
		this.sexList = this.getDictionaryItemManager().getAllItemsByCode("sex");
		HttpServletRequest req = this.getRequest();
		List<KqType> kqtypes = kqTypeManager.allKqTypes(true);
		List<GzType> gztypes = gzTypeManager.allGzTypes(true);
		req.setAttribute("gztypes", gztypes);
		req.setAttribute("kqtypes", kqtypes);
		String deptId = req.getParameter("deptId");
		String personId = req.getParameter("personId");
		if (OtherUtil.measureNotNull(personId)) {
			person = personManager.get(personId);
			this.setEntityIsNew(false);
		} else {
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

	/*======================考勤结账/反结账===========================*/
	private List<ModelStatus> modelStatuss;
	private ModelStatus modelStatus;
	private PeriodManager periodManager;
	private String nextPeriod;

	public String kqCloseCount() {
		HttpServletRequest request = this.getRequest();
		//String NowkqType = kqTypeManager.getNowKqType();
		List<KqType> kqtypes = kqTypeManager.allKqTypes(false);
		request.setAttribute("kqtypes", kqtypes);
		//request.setAttribute("now", NowkqType);
		return SUCCESS;
	}

	public String kqAntiCount() {
		HttpServletRequest request = this.getRequest();
		//String NowkqType = kqTypeManager.getNowKqType();
		List<KqType> kqtypes = kqTypeManager.allKqTypes(false);
		request.setAttribute("kqtypes", kqtypes);
		//request.setAttribute("now", NowkqType);
		return SUCCESS;
	}

	/*待结账列表*/
	public String kqCloseCountGridList() {
		log.debug("enter list method!");
		try {
			/*取出所有考勤类别最新的结账期间*/
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("LIKES_modelId", "KQ*"));
			filters.add(new PropertyFilter("EQS_status", "1"));
			this.modelStatuss = modelStatusManager.getByFilters(filters);
			Map<String, ModelStatus> modelStatusMap = new HashMap<String, ModelStatus>();
			if (OtherUtil.measureNotNull(modelStatuss) && !modelStatuss.isEmpty()) {
				for (ModelStatus msTemp : modelStatuss) {
					String modelId = msTemp.getModelId();
					if (modelStatusMap.containsKey(modelId)) {
						ModelStatus msMapTemp = modelStatusMap.get(modelId);
						String checkPeriod = msTemp.getCheckperiod();
						int checkNumber = msTemp.getCheckNumber();
						String oldCheckPeriod = msMapTemp.getCheckperiod();
						int oldCheckNumber = msMapTemp.getCheckNumber();
						if (checkPeriod.substring(0, 4).equals(oldCheckPeriod.substring(0, 4))) {
							if (checkNumber < oldCheckNumber) {
								modelStatusMap.put(modelId, msTemp);
							}
						} else if (Integer.parseInt(checkPeriod.substring(0, 4)) < Integer.parseInt(oldCheckPeriod.substring(0, 4))) {
							modelStatusMap.put(modelId, msTemp);
						}
					} else {
						modelStatusMap.put(modelId, msTemp);
					}
				}
			}
			/*取出考勤类别*/
			List<PropertyFilter> kqTypeFilters = new ArrayList<PropertyFilter>();
			kqTypeFilters.add(new PropertyFilter("EQB_disabled", "0"));
			List<KqType> kqTypes = kqTypeManager.getByFilters(kqTypeFilters);
			Map<String, KqType> kqTypeMap = new HashMap<String, KqType>();
			if (OtherUtil.measureNotNull(kqTypes) && !kqTypes.isEmpty()) {
				for (KqType gtTemp : kqTypes) {
					kqTypeMap.put(gtTemp.getKqTypeCode(), gtTemp);
				}
			}
			HttpServletRequest request = this.getRequest();
			String kqTypeIdStr = request.getParameter("kqTypeId");
			String checkNumbersStr = request.getParameter("checkNumber");
			String checkperiodStr = request.getParameter("checkperiod");
			kqModelStatusList = new ArrayList<Map<String, Object>>();
			if (!modelStatusMap.isEmpty()) {
				for (ModelStatus msTemp : modelStatusMap.values()) {
					Map<String, Object> msMap = new HashMap<String, Object>();
					msMap.put("id", msTemp.getId());
					msMap.put("periodType", msTemp.getPeriodType());
					msMap.put("checkperiod", msTemp.getCheckperiod());
					msMap.put("checkNumber", msTemp.getCheckNumber());
					String checkNumber = msTemp.getCheckNumber() + "";
					String checkperiod = msTemp.getCheckperiod();
					String modelId = msTemp.getModelId();
					msMap.put("modelId", modelId);
					msMap.put("kqTypeName", "");
					if (OtherUtil.measureNotNull(modelId) && modelId.length() > 2) {
						String kqTypeCode = modelId.substring(3);
						if (kqTypeMap.containsKey(kqTypeCode)) {
							KqType gtTemp = kqTypeMap.get(kqTypeCode);
							msMap.put("kqTypeName", gtTemp.getKqTypeName());
							if (OtherUtil.measureNotNull(kqTypeIdStr) && kqTypeIdStr.equals(kqTypeCode) || OtherUtil.measureNotNull(checkNumbersStr) && checkNumbersStr.equals(checkNumber) || OtherUtil.measureNotNull(checkperiodStr) && checkperiodStr.equals(checkperiod) || OtherUtil.measureNull(kqTypeIdStr) && OtherUtil.measureNull(checkNumbersStr) && OtherUtil.measureNull(checkperiodStr)) {
								kqModelStatusList.add(msMap);
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
	public String kqAntiCountGridList() {
		log.debug("enter list method!");
		try {
			/*取出所有考勤类别最新的结账期间*/
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("LIKES_modelId", "KQ*"));
			filters.add(new PropertyFilter("EQS_status", "2"));
			this.modelStatuss = modelStatusManager.getByFilters(filters);
			Map<String, ModelStatus> modelStatusMap = new HashMap<String, ModelStatus>();
			if (OtherUtil.measureNotNull(modelStatuss) && !modelStatuss.isEmpty()) {
				for (ModelStatus msTemp : modelStatuss) {
					String modelId = msTemp.getModelId();
					if (modelStatusMap.containsKey(modelId)) {
						ModelStatus msMapTemp = modelStatusMap.get(modelId);
						String checkPeriod = msTemp.getCheckperiod();
						int checkNumber = msTemp.getCheckNumber();
						String oldCheckPeriod = msMapTemp.getCheckperiod();
						int oldCheckNumber = msMapTemp.getCheckNumber();
						if (checkPeriod.substring(0, 4).equals(oldCheckPeriod.substring(0, 4))) {
							if (checkNumber > oldCheckNumber) {
								modelStatusMap.put(modelId, msTemp);
							}
						} else if (Integer.parseInt(checkPeriod.substring(0, 4)) < Integer.parseInt(oldCheckPeriod.substring(0, 4))) {
							modelStatusMap.put(modelId, msTemp);
						}
					} else {
						modelStatusMap.put(modelId, msTemp);
					}
				}
			}
			/*取出考勤类别*/
			List<PropertyFilter> kqTypeFilters = new ArrayList<PropertyFilter>();
			kqTypeFilters.add(new PropertyFilter("EQB_disabled", "0"));
			List<KqType> kqTypes = kqTypeManager.getByFilters(kqTypeFilters);
			Map<String, KqType> kqTypeMap = new HashMap<String, KqType>();
			if (OtherUtil.measureNotNull(kqTypes) && !kqTypes.isEmpty()) {
				for (KqType gtTemp : kqTypes) {
					kqTypeMap.put(gtTemp.getKqTypeCode(), gtTemp);
				}
			}
			HttpServletRequest request = this.getRequest();
			String kqTypeIdStr = request.getParameter("kqTypeId");
			String checkNumbersStr = request.getParameter("checkNumber");
			String checkperiodStr = request.getParameter("checkperiod");
			kqModelStatusList = new ArrayList<Map<String, Object>>();
			if (!modelStatusMap.isEmpty()) {
				for (ModelStatus msTemp : modelStatusMap.values()) {
					Map<String, Object> msMap = new HashMap<String, Object>();
					msMap.put("id", msTemp.getId());
					msMap.put("periodType", msTemp.getPeriodType());
					msMap.put("checkperiod", msTemp.getCheckperiod());
					msMap.put("status", msTemp.getStatus());
					msMap.put("checkNumber", msTemp.getCheckNumber());
					String checkNumber = msTemp.getCheckNumber() + "";
					String checkperiod = msTemp.getCheckperiod();
					String modelId = msTemp.getModelId();
					msMap.put("modelId", modelId);
					msMap.put("kqTypeName", "");
					if (OtherUtil.measureNotNull(modelId) && modelId.length() > 2) {
						String kqTypeCode = modelId.substring(3);
						if (kqTypeMap.containsKey(kqTypeCode)) {
							KqType gtTemp = kqTypeMap.get(kqTypeCode);
							msMap.put("kqTypeName", gtTemp.getKqTypeName());
							if (OtherUtil.measureNotNull(kqTypeIdStr) && kqTypeIdStr.equals(kqTypeCode) || OtherUtil.measureNotNull(checkNumbersStr) && checkNumbersStr.equals(checkNumber) || OtherUtil.measureNotNull(checkperiodStr) && checkperiodStr.equals(checkperiod) || OtherUtil.measureNull(kqTypeIdStr) && OtherUtil.measureNull(checkNumbersStr) && OtherUtil.measureNull(checkperiodStr)) {
								kqModelStatusList.add(msMap);
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

	/*结账页面刷新*/
	public String kqRefreshCount() {
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQB_disabled", "0"));
			filters.add(new PropertyFilter("NES_kqTypeId", "XT"));
			List<KqType> kqTypes = kqTypeManager.getByFilters(filters);
			filters.clear();
			filters.add(new PropertyFilter("LIKES_modelId", "KQ*"));
			filters.add(new PropertyFilter("ODS_checkperiod", ""));
			filters.add(new PropertyFilter("ODI_checkNumber", ""));
			modelStatuss = modelStatusManager.getByFilters(filters);
			List<ModelStatus> needSaveMsList = new ArrayList<ModelStatus>();
			if (OtherUtil.measureNotNull(kqTypes) && !kqTypes.isEmpty()) {
				for (KqType kqType : kqTypes) {
					String kqTypeCode = kqType.getKqTypeCode();
					String modelIdString = "KQ_" + kqTypeCode;
					String insertFlag = "";
					String checkPeriodString = "";
					int checkNumber = 1;
					if (OtherUtil.measureNotNull(modelStatuss) && !modelStatuss.isEmpty()) {
						List<ModelStatus> status1List = new ArrayList<ModelStatus>();
						List<ModelStatus> status2List = new ArrayList<ModelStatus>();
						for (ModelStatus msTemp : modelStatuss) {
							String modelIdTemp = msTemp.getModelId();
							String statusTemp = msTemp.getStatus();
							if (modelIdString.equals(modelIdTemp)) {
								if ("1".equals(statusTemp)) {
									status1List.add(msTemp);
								} else if ("2".equals(statusTemp)) {
									status2List.add(msTemp);
								}
							}
						}
						if (status1List.isEmpty() && status2List.isEmpty()) {
							insertFlag = "newType";
						} else if (!status1List.isEmpty()) {
							continue;
						} else if (!status2List.isEmpty()) {
							insertFlag = "newPeriod";
							checkPeriodString = status2List.get(0).getCheckperiod();
							checkNumber = status2List.get(0).getCheckNumber();
						}
					} else {
						insertFlag = "newType";
					}
					if (OtherUtil.measureNull(insertFlag)) {
						continue;
					} else if ("newType".equals(insertFlag)) {
						//						checkPeriodString = kqType.getIssueDate();
						checkPeriodString = UserContextUtil.getUserContext().getPeriodMonth();

						filters.clear();
						filters.add(new PropertyFilter("EQS_periodCode", checkPeriodString));
						List<Period> periods = periodManager.getByFilters(filters);
						if (OtherUtil.measureNotNull(periods) && !periods.isEmpty()) {
							ModelStatus msTemp = new ModelStatus();
							msTemp.setModelId(modelIdString);
							msTemp.setPeriodType("月");
							msTemp.setCheckNumber(checkNumber);
							msTemp.setCheckperiod(checkPeriodString);
							msTemp.setStatus("1");
							needSaveMsList.add(msTemp);
						}
					} else if ("newPeriod".equals(insertFlag)) {
						String nextPeriodString = periodManager.getNextPeriod(checkPeriodString);
						if (OtherUtil.measureNotNull(nextPeriodString)) {
							ModelStatus msTemp = new ModelStatus();
							msTemp.setModelId(modelIdString);
							msTemp.setPeriodType("月");
							msTemp.setCheckNumber(checkNumber + 1);
							msTemp.setCheckperiod(nextPeriodString);
							msTemp.setStatus("1");
							needSaveMsList.add(msTemp);
						}
					}
				}
			}
			if (!needSaveMsList.isEmpty()) {
				modelStatusManager.saveAll(needSaveMsList);
			}
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "刷新成功。";
		return ajaxForward(this.getText(key));
	}
	/*结账*/
	public String kqModelStatusClose() {
		try {
			ModelStatus mStatus = modelStatusManager.get(id);
			String period = mStatus.getCheckperiod();
			String lastPeriod = KqUtil.getLastPeriod(period, this.getGlobalParamByKey("kqPersonDelayMonth"));
			String kqTypeCode = mStatus.getModelId().substring(3);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_kqTypeCode",kqTypeCode));
			kqTypes = kqTypeManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(kqTypes)&&!kqTypes.isEmpty()){
				kqTypeId = kqTypes.get(0).getKqTypeId();
			}
			filters.clear();
			
			filters.add(new PropertyFilter("NES_personId","XT"));
			filters.add(new PropertyFilter("EQB_stopKq","0"));
			//filters.add(new PropertyFilter("EQB_disable","0"));
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			List<MonthPerson> monthPersons = kqDayDataManager.getByFilters(filters,MonthPerson.class);
			Set<Branch> branches = new HashSet<Branch>();
			for(MonthPerson monthPerson: monthPersons) {
				if(monthPerson.getBranch()!=null){
					branches.add(monthPerson.getBranch());
				}
			}
			if(!branches.isEmpty()) {
				String message = "";
				for(Branch branch:branches) {
					Boolean kqContentInheritCheck = kqDayDataManager.getKqDayDataChecked(period,kqTypeId, branch.getBranchCode(), null);
					if(kqContentInheritCheck) {
						message += "期间" + period + "," + " 院区" + branch.getBranchName() + "考勤数据尚未初始化；";
					} else {
						Boolean kqContentChecked = kqDayDataManager.getKqDayDataChecked(period,kqTypeId, branch.getBranchCode(), "0,1,2,4");
						if(!kqContentChecked){
							message += "期间" + period + "," + " 院区" + branch.getBranchName() + "考勤数据尚未完全通过；";
						}
					}
					
				}
				if(!"".equals(message)) {
					return ajaxForwardError(message);
				}
				
			}
			
			/*filters.add(new PropertyFilter("EQS_period",period));
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			filters.add(new PropertyFilter("INS_status","0,1,2,4"));
			List<KqDayData> kqDayDatas = kqDayDataManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(kqDayDatas)&&!kqDayDatas.isEmpty()){
				return ajaxForwardError("期间"+period+"存在未通过的考勤数据！");
			}*/
			kqTypeManager.kqModelStatusClose(id, nextPeriod, oper);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "结账成功。";
		return ajaxForward(this.getText(key));
	}

	/*判断下次期间是否定义*/
	public String kqModelStatusNextPeriodCheck() {
		if ("anti".equals(oper)) {
			ModelStatus mStatus = modelStatusManager.get(id);
			String modelId = mStatus.getModelId();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_modelId", modelId));
			filters.add(new PropertyFilter("EQS_status", "1"));
			filters.add(new PropertyFilter("NES_id", id));
			modelStatuss = modelStatusManager.getByFilters(filters);
			if (OtherUtil.measureNotNull(modelStatuss) && !modelStatuss.isEmpty()) {
				oper = "anti";
			} else {
				oper = "closed";
			}
		}
		if (OtherUtil.measureNotNull(id)) {
			ModelStatus mStatus = modelStatusManager.get(id);
			String period = mStatus.getCheckperiod();
			nextPeriod = periodManager.getNextPeriod(period);
			if (OtherUtil.measureNull(nextPeriod)) {
				return ajaxForward("期间" + period + "的下一个期间尚未定义！");
			}
		} else {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_periodCode", nextPeriod));
			List<Period> periods = periodManager.getByFilters(filters);
			if (OtherUtil.measureNull(periods) || periods.isEmpty()) {
				return ajaxForward("期间" + nextPeriod + "尚未定义！");
			}
		}
		return ajaxForward("");
	}

	/*反结账*/
	public String kqModelStatusAnti() {
		try {
			modelStatus = modelStatusManager.get(id);
			modelStatus.setStatus("1");
			modelStatusManager.save(modelStatus);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "反结账成功。";
		return ajaxForward(this.getText(key));
	}

	private List<Map<String, Object>> kqModelStatusList;

	public List<ModelStatus> getModelStatuss() {
		return modelStatuss;
	}

	public void setModelStatuss(List<ModelStatus> modelStatuss) {
		this.modelStatuss = modelStatuss;
	}

	public PeriodManager getPeriodManager() {
		return periodManager;
	}

	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public ModelStatus getModelStatus() {
		return modelStatus;
	}

	public void setModelStatus(ModelStatus modelStatus) {
		this.modelStatus = modelStatus;
	}

	public String getNextPeriod() {
		return nextPeriod;
	}

	public void setNextPeriod(String nextPeriod) {
		this.nextPeriod = nextPeriod;
	}

	public List<Map<String, Object>> getKqModelStatusList() {
		return kqModelStatusList;
	}

	public void setKqModelStatusList(List<Map<String, Object>> kqModelStatusList) {
		this.kqModelStatusList = kqModelStatusList;
	}

	public String getKqTypeJsonStr() {
		return kqTypeJsonStr;
	}

	public void setKqTypeJsonStr(String kqTypeJsonStr) {
		this.kqTypeJsonStr = kqTypeJsonStr;
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
	
	public List<Branch> getBranches() {
		return branches;
	}
	
	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}
	

	public GzTypeManager getGzTypeManager() {
		return gzTypeManager;
	}

	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}

	public void setKqUpItemManager(KqUpItemManager kqUpItemManager) {
		this.kqUpItemManager = kqUpItemManager;
	}

	public void setKqDayDataManager(KqDayDataManager kqDayDataManager) {
		this.kqDayDataManager = kqDayDataManager;
	}

	public void setKqMonthDataManager(KqMonthDataManager kqMonthDataManager) {
		this.kqMonthDataManager = kqMonthDataManager;
	}
	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
}
