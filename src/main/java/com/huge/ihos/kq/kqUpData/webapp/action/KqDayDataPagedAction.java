package com.huge.ihos.kq.kqUpData.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.kq.generalHoliday.service.GeneralHolidayChangeManager;
import com.huge.ihos.kq.kqHoliday.service.KqHolidayManager;
import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.ihos.kq.kqItem.service.KqItemManager;
import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.kq.kqType.service.KqTypeManager;
import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.ihos.kq.kqUpData.model.KqDayDataDetail;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.service.KqDayDataDetailManager;
import com.huge.ihos.kq.kqUpData.service.KqDayDataManager;
import com.huge.ihos.kq.kqUpData.service.KqUpItemManager;
import com.huge.ihos.kq.util.KqUtil;
import com.huge.ihos.system.configuration.colsetting.model.ColShow;
import com.huge.ihos.system.configuration.colsetting.service.ColShowManager;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.model.MonthPerson;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class KqDayDataPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8679524564067784419L;
	private KqDayDataManager kqDayDataManager;
	private List<KqDayData> kqDayDatas;
	private KqDayData kqDayData;
	private String kqId;
	private KqTypeManager kqTypeManager;

	public KqDayDataManager getKqDayDataManager() {
		return kqDayDataManager;
	}

	public void setKqDayDataManager(KqDayDataManager kqDayDataManager) {
		this.kqDayDataManager = kqDayDataManager;
	}

	public List<KqDayData> getkqDayDatas() {
		return kqDayDatas;
	}

	public void setKqDayDatas(List<KqDayData> kqDayDatas) {
		this.kqDayDatas = kqDayDatas;
	}

	public KqDayData getKqDayData() {
		return kqDayData;
	}

	public void setKqDayData(KqDayData kqDayData) {
		this.kqDayData = kqDayData;
	}

	public String getKqId() {
		return kqId;
	}

	public void setKqId(String kqId) {
        this.kqId = kqId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	private String kqUpDataType;
	public String kqUpDataList(){
		kqUpDataType = this.getGlobalParamByKey("kqUpDataType");
		if("1".equals(kqUpDataType)){
			String kqTypePriv = UserContextUtil.findUserDataPrivilegeStr("kqtype_dp", "1");
			List<KqType> kqTypes = kqTypeManager.getAllAvailable(kqTypePriv);
			this.getRequest().setAttribute("kqTypes",kqTypes);
			if(OtherUtil.measureNotNull(kqTypes)&&!kqTypes.isEmpty()){
				kqTypeId = kqTypes.get(0).getKqTypeId();
			}else{
				kqTypeId = "";
			}
			kqUpDataType = this.getGlobalParamByKey("kqUpDataType");
//			User sessionUser = getSessionUser();
//			Department dept = sessionUser.getPerson().getDepartment();
			curPeriod = this.getLoginPeriod();
			curDeptId = KqUtil.getCurDeptIds();
//			curDeptId = dept.getDepartmentId();
			lastPeriod = KqUtil.getLastPeriod(curPeriod, globalParamManager.getGlobalParamByKey("kqPersonDelayMonth"));
			return INPUT;
		}
		return SUCCESS;
	}
	private String kqTypeId;
	private String curDeptId;
	private String curPeriod;//当前期间
	private String lastPeriod;//t_monthperson取值期间
	private String kqCustomLayout;
	private String curPeriodWeek;
	private String curCheckStatus;//审核状态
	private String periodDayCount;
	private String curPeriodStatus;//期间状态
	private String mpPeriodChange;//月度表人员变化
	private List<Branch> branchs;
	private BranchManager branchManager;
	public void setBranchManager(BranchManager branchManager) {
		this.branchManager = branchManager;
	}

	public List<Branch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
	}
	public String kqDayDataList(){
		try {
			kqUpDataType = this.getGlobalParamByKey("kqUpDataType");
			String kqTypePriv = UserContextUtil.findUserDataPrivilegeStr("kqtype_dp", "1");
			List<KqType> kqTypes = kqTypeManager.getAllAvailable(kqTypePriv);
			this.getRequest().setAttribute("kqTypes",kqTypes);
			if(OtherUtil.measureNotNull(kqTypes)&&!kqTypes.isEmpty()){
				kqTypeId = kqTypes.get(0).getKqTypeId();
			}else{
				kqTypeId = "";
			}
			curPeriod = this.getLoginPeriod();
			curDeptId = KqUtil.getCurDeptIds();
			if("0".equals(kqUpDataType)){
				curPeriodWeek = calWeekDetailOfPeriod(curPeriod);
			}
			String yearStr = curPeriod.substring(0, 4);
			String monthStr = curPeriod.substring(4);
			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);
			periodDayCount = DateUtil.dayNumOfMonth(year, month) + "";
			lastPeriod = KqUtil.getLastPeriod(curPeriod, globalParamManager.getGlobalParamByKey("kqPersonDelayMonth"));
			ColShow colShow = colShowManager.getLastByTemplName("com.huge.ihos.kq.kqUpData.model.KqDayData", this.getSessionUser().getId()+"",kqUpDataType);
			if(OtherUtil.measureNotNull(colShow)){
				kqCustomLayout = colShow.getCustomLayout();
			}
			List<MenuButton> menuButtons = this.getMenuButtons();
			curPeriodStatus = "";
//			if(OtherUtil.measureNotNull(kqTypeId)){
//				KqType kqTypeTemp = kqTypeManager.get(kqTypeId);
//				ModelStatus msTemp = modelStatusManager.getUsableModelStatus(kqTypeTemp.getKqTypeCode(), curPeriod,"KQ");
//				String operStrTemp = "期间："+curPeriod+" 考勤类别："+kqTypeTemp.getKqTypeName()+" ";
//				if(OtherUtil.measureNull(msTemp)){
//					curPeriodStatus = operStrTemp + "尚未启用。";
//				}else if("2".equals(msTemp.getStatus())){
//					curPeriodStatus = operStrTemp + "已结账。";
//				}
//			}
//			Iterator<MenuButton> ite = menuButtons.iterator();
//			while (ite.hasNext()) {
//				MenuButton button = ite.next();
//				if(OtherUtil.measureNotNull(curPeriodStatus)){
//					button.setEnable(false);
//				}
//			}
			setMenuButtonsToJson(menuButtons);
			//月度表人员变化
			JSONArray jsonArray = monthPersonPeriodChange(lastPeriod,KqUtil.getLastPeriod(lastPeriod, "1"),curDeptId);
			mpPeriodChange = jsonArray.toString();
			
			String branchDp = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
			branchs = branchManager.getAllAvailable(branchDp);
		} catch (Exception e) {
			log.error("kqDayDataListError:"+e.getMessage());
		}
		return SUCCESS;
	}
	//月度表人员变动
	private JSONArray monthPersonPeriodChange(String curPeriod,String lastPeriod,String deptIds) throws Exception{
		try {
			JSONArray jsonArray = new JSONArray();
			if(OtherUtil.measureNull(deptIds)){
				return jsonArray;
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			if(!deptIds.equals("all")){
				filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
			}
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			filters.add(new PropertyFilter("OAS_personCode",""));
			List<MonthPerson> monthPersons = kqDayDataManager.getByFilters(filters, MonthPerson.class);
			Map<String,List<String>> lastPeriodMap = new HashMap<String, List<String>>();
			Map<String, MonthPerson> lastPeriodMPMap = new HashMap<String, MonthPerson>();
			if(OtherUtil.measureNotNull(monthPersons)&&!monthPersons.isEmpty()){
				for(MonthPerson mp:monthPersons){
					String personId = mp.getPersonId();
					String deptName = mp.getDepartment().getName();
					lastPeriodMPMap.put(personId, mp);
					List<String> personIdList = null;
					if(lastPeriodMap.containsKey(deptName)){
						personIdList = lastPeriodMap.get(deptName);
					}else{
						personIdList = new ArrayList<String>();
					}
					personIdList.add(personId);
					lastPeriodMap.put(deptName, personIdList);
				}
			}
			filters.clear();
			if(!deptIds.equals("all")){
				filters.add(new PropertyFilter("INS_department.departmentId",deptIds));
			}
			filters.add(new PropertyFilter("EQS_checkperiod",curPeriod));
			filters.add(new PropertyFilter("OAS_personCode",""));
			monthPersons = kqDayDataManager.getByFilters(filters, MonthPerson.class);
			Map<String,List<String>> curPeriodMap = new HashMap<String, List<String>>();
			Map<String, MonthPerson> curPeriodMPMap = new HashMap<String, MonthPerson>();
			if(OtherUtil.measureNotNull(monthPersons)&&!monthPersons.isEmpty()){
				for(MonthPerson mp:monthPersons){
					String personId = mp.getPersonId();
					String deptName = mp.getDepartment().getName();
					curPeriodMPMap.put(personId, mp);
					List<String> personIdList = null;
					if(curPeriodMap.containsKey(deptName)){
						personIdList = curPeriodMap.get(deptName);
					}else{
						personIdList = new ArrayList<String>();
					}
					personIdList.add(personId);
					curPeriodMap.put(deptName, personIdList);
				}
			}
			filters.clear();
			if(!deptIds.equals("all")){
				filters.add(new PropertyFilter("INS_departmentId",deptIds));
			}
			filters.add(new PropertyFilter("EQB_leaf","1"));
			filters.add(new PropertyFilter("OAS_deptCode",""));
			List<Department> departments = departmentManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(departments)&&!departments.isEmpty()){
				for(Department dept:departments){
					JSONObject jsonObject = new JSONObject();
					String deptName = dept.getName();
					List<String> curPersonIdList = null;
					List<String> lastPersonIdList = null;
					if(curPeriodMap.containsKey(deptName)){
						curPersonIdList = curPeriodMap.get(deptName);
					}else{
						curPersonIdList = new ArrayList<String>();
					}
					if(lastPeriodMap.containsKey(deptName)){
						lastPersonIdList = lastPeriodMap.get(deptName);
					}else{
						lastPersonIdList = new ArrayList<String>();
					}
					List<String> pInList = new ArrayList<String>();
					for(String pId:curPersonIdList){
						if(lastPersonIdList.contains(pId)){
							lastPersonIdList.remove(pId);
						}else{
							pInList.add(pId);
						}
					}
					jsonObject.put("deptId", dept.getDepartmentId());
					jsonObject.put("deptName", deptName);
					jsonObject.put("pInCount", pInList.size());
					if(pInList.size() == 0){
						jsonObject.put("pIn", "无");
					}else{
						String mpName = "";
						for(String pId:pInList){
							MonthPerson mp = curPeriodMPMap.get(pId);
							mpName += mp.getName() + ",";
						}
						mpName = OtherUtil.subStrEnd(mpName, ",");
						jsonObject.put("pIn", mpName);
					}
					jsonObject.put("pOutCount", lastPersonIdList.size());
					if(lastPersonIdList.size() == 0){
						jsonObject.put("pOut", "无");
					}else{
						String mpName = "";
						for(String pId:lastPersonIdList){
							MonthPerson mp = lastPeriodMPMap.get(pId);
							mpName += mp.getName() + ",";
						}
						mpName = OtherUtil.subStrEnd(mpName, ",");
						jsonObject.put("pOut", mpName);
					}
					jsonArray.add(jsonObject);
				}
			}
			return jsonArray;
		} catch (Exception e) {
			throw e;
		}
	}
	private DepartmentManager departmentManager;
	private ColShowManager colShowManager;
	private KqHolidayManager kqHolidayManager;
	private GeneralHolidayChangeManager generalHolidayChangeManager;
	//当月星期与日期
	private String calWeekDetailOfPeriod(String period){
		JSONArray jsonArray = new JSONArray();
		if(OtherUtil.measureNull(period)||period.length()!=6){
			return "";
		}
		String yearStr = period.substring(0, 4);
		String monthStr = period.substring(4);
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		int days = DateUtil.dayNumOfMonth(year, month);
		Map<String, Boolean> holidayMap = kqHolidayManager.getMonthHoliday(year, month, days);
		Map<String, Boolean> holidayChangeMap = generalHolidayChangeManager.getMonthHoliday(year, month, days);
		int week = DateUtil.peiodFirstDayWeek(period);
		String[] weekDetail = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		for(int i =0;i<days;i++){
			JSONObject jsonObject = new JSONObject();
			String weekStr = weekDetail[week-1];
			String keyTemp = "day"+(i+1);
			jsonObject.put("code", keyTemp);
			jsonObject.put("name",weekStr);
			jsonObject.put("text",i+1);
			Boolean isHoliday = false;
			if(week==1 || week==7){//周六日
				isHoliday = true;
			}else{
				isHoliday = false;
			}
			if(holidayMap.containsKey(keyTemp)){//节假日
				isHoliday = holidayMap.get(keyTemp);
			}
			if(holidayChangeMap.containsKey(keyTemp)){//调休
				isHoliday = holidayChangeMap.get(keyTemp);
			}
			jsonObject.put("isHoliday",isHoliday + "");
			jsonArray.add(jsonObject);
			week++;
			if(week > 7){
				week = 1;
			}
		}
		return jsonArray.toString();
	}
	private KqDayDataDetailManager kqDayDataDetailManager;
	private String kqDayDataDetailStr;
	private List<Map<String, Object>> kqDayDataSets;
	private String columns;
	public String kqDayDataGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			List<String> sqlFilterList = new ArrayList<String>();
			List<String> sqlOrderList = new ArrayList<String>();
			String sqlTemp = "";
			String branchPriv = UserContextUtil.findUserDataPrivilegeSql("branch_dp", "1");
			if("S2".equals(ContextUtil.herpType)){
				if(branchPriv != null && !"".equals(branchPriv)) {
					sqlTemp = "kq.branchCode in " + branchPriv;
					sqlFilterList.add(sqlTemp);
				} else {
					sqlTemp = "1=2";
					sqlFilterList.add(sqlTemp);
				}
			}
			if(OtherUtil.measureNotNull(curPeriod)){
				sqlTemp = "kq.period = '" + curPeriod +"'";
				sqlFilterList.add(sqlTemp);
				filters.add(new PropertyFilter("EQS_kqDayData.period",curPeriod));
			}
			if(OtherUtil.measureNotNull(kqTypeId)){
				sqlTemp = "kq.kqTypeId = '" + kqTypeId +"'";
				sqlFilterList.add(sqlTemp);
				filters.add(new PropertyFilter("EQS_kqDayData.kqType",kqTypeId));
			}
			if(OtherUtil.measureNotNull(curDeptId)&&!curDeptId.equals("all")){
				filters.add(new PropertyFilter("INS_kqDayData.deptId",curDeptId));
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				sqlTemp = "kq.deptId in (" + curDeptId +")";
				sqlFilterList.add(sqlTemp);
			}else if("all".equals(curDeptId)){
				
			}else{
				sqlTemp = "kq.deptId in (" + "''" +")";
				sqlFilterList.add(sqlTemp);
			}
//			if(OtherUtil.measureNotNull(curPeriodStatus)){//期间状态
//				if(curPeriodStatus.indexOf("已结账") == -1){
//					sqlTemp = "1=2";
//					sqlFilterList.add(sqlTemp);
//				}
//			}
			filters.add(new PropertyFilter("OAS_kqDayData.kqId",""));
			List<KqDayDataDetail> kqDayDataDetails = kqDayDataDetailManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(kqDayDataDetails)&&!kqDayDataDetails.isEmpty()){
				Map<String, List<KqDayDataDetail>> mapTemp = new HashMap<String, List<KqDayDataDetail>>();
				for(KqDayDataDetail kqDayDataDetail:kqDayDataDetails){
					String kqIdTemp = kqDayDataDetail.getKqDayData().getKqId();
					String columnTemp = kqDayDataDetail.getKqColumn();
					kqIdTemp = kqIdTemp + "_" + columnTemp;
					List<KqDayDataDetail> listTemp = null;
					if(mapTemp.containsKey(kqIdTemp)){
						listTemp = mapTemp.get(kqIdTemp);
					}else{
						listTemp = new ArrayList<KqDayDataDetail>();
					}
					listTemp.add(kqDayDataDetail);
					mapTemp.put(kqIdTemp, listTemp);
				}
				JSONObject jsonObject = new JSONObject();
				for(String keyTemp:mapTemp.keySet()){
					List<KqDayDataDetail> listTemp = mapTemp.get(keyTemp);
					JSONArray jsonArray = new JSONArray();
					for(KqDayDataDetail kqDayDataDetail:listTemp){
						JSONObject joTemp = new JSONObject();
						joTemp.put("detailId", kqDayDataDetail.getDetailId());
						joTemp.put("kqId", kqDayDataDetail.getKqDayData().getKqId());
						joTemp.put("column", kqDayDataDetail.getKqColumn());
						joTemp.put("item", kqDayDataDetail.getKqItem());
						joTemp.put("value", kqDayDataDetail.getKqValue());
						jsonArray.add(joTemp);
					}
					jsonObject.put(keyTemp, jsonArray);
				}
				kqDayDataDetailStr = jsonObject.toString();
			}else{
				kqDayDataDetailStr = "";
			}
			kqDayDataSets = kqDayDataManager.getKqDayDataGridData(columns, lastPeriod, sqlFilterList, sqlOrderList);
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	private KqItemManager kqItemManager;
	/*初始化*/
	public String refreshKqDayData(){
		try {
			Date operDate = new Date();
    		Person operPerson = this.getSessionUser().getPerson();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			String[] personIdArr = this.getRequest().getParameterValues("personId");
			String personIdStr = OtherUtil.transferArrToString(personIdArr);
			if(OtherUtil.measureNotNull(personIdStr)){
				filters.add(new PropertyFilter("INS_personId",personIdStr));
			}else{
				filters.add(new PropertyFilter("NES_personId","XT"));
				filters.add(new PropertyFilter("EQB_stopKq","0"));
				//filters.add(new PropertyFilter("EQB_disable","0"));
			}
			if(curDeptId!=null&&!curDeptId.equals("all")){
				filters.add(new PropertyFilter("INS_department.departmentId",curDeptId));
			}
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			if("S2".equals(ContextUtil.herpType)){
				String branchDp = UserContextUtil.findUserDataPrivilegeStr("branch_dp", "2");
				if(!branchDp.startsWith("select")&&!branchDp.startsWith("SELECT")) {
					
					filters.add(new PropertyFilter("INS_department.branch.branchCode",branchDp));
				}
			}
			List<MonthPerson> monthPersons = kqDayDataManager.getByFilters(filters,MonthPerson.class);
			if(OtherUtil.measureNull(monthPersons)||monthPersons.isEmpty()){
				return ajaxForwardError("您所在部门当前考勤类别与"+lastPeriod+"期间无人员数据！");
			}
			filters.clear();
			if(curDeptId!=null&&!curDeptId.equals("all")){
				filters.add(new PropertyFilter("INS_deptId",curDeptId));
			}
			filters.add(new PropertyFilter("EQS_period",curPeriod));
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			kqDayDatas = kqDayDataManager.getByFilters(filters);
			List<String> kqTypePersonIdList = new ArrayList<String>();
			if(OtherUtil.measureNotNull(kqDayDatas)&&!kqDayDatas.isEmpty()){
				for(KqDayData kqDayData:kqDayDatas){
					kqTypePersonIdList.add(kqDayData.getPersonId());
				}
			}
			kqDayDatas = new ArrayList<KqDayData>();
//			String upDateSqlTemp = "";
			List<String> sqlList = new ArrayList<String>();
			List<String> upDateDayList = new ArrayList<String>();
			Map<String,Map<String, Object>> upDateDayMap = new HashMap<String, Map<String,Object>>();
			String defaultKqValue = null;
			KqItem kqItem = kqItemManager.getDefaultKqItem();
			String sumItemCode = null;
			if(OtherUtil.measureNotNull(kqItem)){
				defaultKqValue = kqItem.getShortName();
				sumItemCode = kqUpItemManager.getDefaultKqCode(kqItem.getKqItemName(), kqTypeId);
			}
			if("0".equals(kqUpDataType)){
				if(OtherUtil.measureNotNull(defaultKqValue)){
					String checkSqlTemp = "SELECT personId";
					String yearStr = curPeriod.substring(0, 4);
					String monthStr = curPeriod.substring(4);
					int year = Integer.parseInt(yearStr);
					int month = Integer.parseInt(monthStr);
					int days = DateUtil.dayNumOfMonth(year, month);
					for(int i = 1;i <= days;i++){
						upDateDayList.add("day"+i);
						checkSqlTemp += ",SUM(CASE ISNULL(day"+i+", '') WHEN '' THEN 0 ELSE 1 END) AS day"+i;
					}
					if(OtherUtil.measureNotNull(personIdStr)){
						checkSqlTemp += " FROM kq_dayData WHERE kqTypeId = '"+kqTypeId+"' ";
						checkSqlTemp +=	" AND period = '"+curPeriod+"' AND personId IN ("+OtherUtil.splitStrAddQuotes(personIdStr, ",")+") ";
						checkSqlTemp += " GROUP BY personId";
						List<Map<String,Object>> resultTempList = kqDayDataManager.getBySqlToMap(checkSqlTemp);
						if(OtherUtil.measureNotNull(resultTempList)&&!resultTempList.isEmpty()){
							for(Map<String, Object> tempMap:resultTempList){
								String personIdTemp = tempMap.get("personId").toString();
								upDateDayMap.put(personIdTemp, tempMap);
							}
						}
					}
				}
			}
			//取上报表中已经存在的数据
			Map<String, String> personIdKqIdMap = new HashMap<String, String>();
			if(OtherUtil.measureNotNull(personIdStr)){
				filters.clear();
				filters.add(new PropertyFilter("INS_personId",personIdStr));
				filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
				filters.add(new PropertyFilter("EQS_period",curPeriod));
				List<KqDayData> kqDayDatasTemp = kqDayDataManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(kqDayDatasTemp)&&!kqDayDatasTemp.isEmpty()){
					for(KqDayData kqDayDataTemp:kqDayDatasTemp){
						String personIdTemp = kqDayDataTemp.getPersonId();
						String kqIdTemp = kqDayDataTemp.getKqId();
						if(personIdKqIdMap.containsKey(personIdTemp)){
							kqIdTemp = personIdKqIdMap.get(personIdTemp) + "," + kqIdTemp;
						}
						personIdKqIdMap.put(personIdTemp, kqIdTemp);
					}
				}
			}
			//更新数据
			for(MonthPerson person : monthPersons){
				KqDayData kqDayDataTemp = new KqDayData();
				String personId = person.getPersonId();
				String mapKey = personId;
    			if(kqTypePersonIdList.contains(mapKey)&&OtherUtil.measureNull(personIdStr)){
    				continue;
    			}
    			kqDayDataTemp.setKqType(kqTypeId);
    			kqDayDataTemp.setPeriod(curPeriod);
    			kqDayDataTemp.setPersonId(personId);
    			if("0".equals(kqUpDataType)&&!upDateDayList.isEmpty()
    					&&OtherUtil.measureNotNull(defaultKqValue)){
    				String upDateSqlTemp = "";
    				Map<String, Object> checkMapTemp = new HashMap<String, Object>();
    				if(upDateDayMap.containsKey(personId)){
    					checkMapTemp = upDateDayMap.get(personId);
    				}
    				int sumTemp = 0;
    				for(String dayTemp:upDateDayList){
    					if(checkMapTemp.containsKey(dayTemp)&&"1".equals(checkMapTemp.get(dayTemp).toString())){
    						continue;
    					}else{
    						upDateSqlTemp += dayTemp + " = '" + defaultKqValue + "',";
    						sumTemp ++;
    					}
    				}
    				if(OtherUtil.measureNotNull(upDateSqlTemp)){
    					upDateSqlTemp = OtherUtil.subStrEnd(upDateSqlTemp, ",");
    					String sqlTemp = "UPDATE kq_dayData SET "+upDateSqlTemp;
    					if(OtherUtil.measureNotNull(sumItemCode)){
    						sqlTemp += ","+sumItemCode+"=" + sumTemp;
    					}
    					sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND period = '"+curPeriod+"' AND personId='"+personId+"'";
    					if(personIdKqIdMap.containsKey(personId)){
        					String kqIdTemp = personIdKqIdMap.get(personId);
        					kqIdTemp = OtherUtil.splitStrAddQuotes(kqIdTemp, ",");
        					sqlTemp += " AND kqId NOT IN (" + kqIdTemp + ")";
        				}
    					sqlList.add(sqlTemp);
    				}
    			}
    			kqDayDataTemp.setPersonCode(person.getPersonCode());
    			kqDayDataTemp.setBranchCode(person.getBranchCode());
    			if(OtherUtil.measureNotNull(person.getDepartment().getBranch()) && OtherUtil.measureNotNull(person.getDepartment().getBranch().getBranchCode())) {
    				kqDayDataTemp.setBranchName(person.getDepartment().getBranch().getBranchName());
    			} else {
    				kqDayDataTemp.setBranchName("");
    			}
    			kqDayDataTemp.setPersonName(person.getName());
    			kqDayDataTemp.setOrgCode(person.getOrgCode());
				if(OtherUtil.measureNotNull(person.getDepartment().getOrg())&&OtherUtil.measureNotNull(person.getDepartment().getOrg().getOrgCode())){
					kqDayDataTemp.setOrgName(person.getDepartment().getOrg().getOrgname());
//					kqDayDataTemp.setOrgName(person.getDepartment().getOrg().getOrgCode());
    			}else{
    				kqDayDataTemp.setOrgName("");
    			}
				kqDayDataTemp.setDeptId(person.getDepartment().getDepartmentId());
				kqDayDataTemp.setDeptCode(person.getDepartment().getDeptCode());
				kqDayDataTemp.setDeptName(person.getDepartment().getName());
				kqDayDataTemp.setKqDeptName(kqDayDataTemp.getDeptName());
				kqDayDataTemp.setStatus("0");
//				gzContentNeedCheck = this.getGlobalParamByKey("gzContentNeedCheck");
//				if("1".equals(gzContentNeedCheck)){
//					gzContentTemp.setStatus("0");
//				}else{
//					gzContentTemp.setStatus("1");
//				}
				kqDayDataTemp.setMakeDate(operDate);
				kqDayDataTemp.setMaker(operPerson.getName());
				kqDayDataTemp.setEmpType(person.getStatus());
				kqDayDatas.add(kqDayDataTemp);
			}
			kqDayDataManager.saveAll(kqDayDatas);
			kqDayDataManager.executeSqlList(sqlList);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
		return  ajaxForward("刷新成功。");
	}
	public String save(){
		try {
			String kqDayDataStr = this.getRequest().getParameter("kqDayDataStr");
			String needSaveColumn = this.getRequest().getParameter("needSaveColumn"); 
			List<String> sqlList = new ArrayList<String>();
			String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
			Person operPerson = this.getSessionUser().getPerson();
			String sqlTemp = "UPDATE kq_dayData SET makeDate = '"+curTime+"',maker ='"+operPerson.getName()+"' ";
			curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
			if(curDeptId!=null&&!curDeptId.equals("all")){
				curDeptId = "deptId in ("+curDeptId+") AND ";
			}else{
				curDeptId = "";
			}
			sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND "+curDeptId+"period = '"+curPeriod+"'";
			log.debug("更新："+sqlTemp);
			sqlList.add(sqlTemp);
			if(OtherUtil.measureNotNull(kqDayDataStr)){
				String[] needSaveColumns = needSaveColumn.split(",");
				JSONArray jsonArray = JSONArray.fromObject(kqDayDataStr );
				if(jsonArray.size()>0){
					  for(int i=0;i<jsonArray.size();i++){
						sqlTemp = "";
						String gzIdString = "";
					    JSONObject jsonObject = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					    Iterator jsonObjIterator = jsonObject.keys();
					    while (jsonObjIterator.hasNext()) {
					    	String keyString = jsonObjIterator.next().toString();
							int keyIndex = Integer.parseInt(keyString);
							String column = needSaveColumns[keyIndex];
							String columnValue = jsonObject.getString(keyString);
							if("kqId".equals(column)){
								gzIdString = columnValue;
							}else{
								if("".equals(columnValue)){
									sqlTemp += column + "=null,";
								}else{
									sqlTemp += column + "='" + columnValue+"',";
								}
								
							}
						}
					    if(OtherUtil.measureNotNull(sqlTemp)){
					    	sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
					    	sqlTemp = " UPDATE kq_dayData SET "+sqlTemp+" WHERE kqId = '" + gzIdString + "'";
					    	log.debug("更新："+sqlTemp);
					    	sqlList.add(sqlTemp);
					    }
					  }
					}
			}
			sqlTemp = " DELETE kq_dayDataDetail";
	    	log.debug("更新："+sqlTemp);
	    	sqlList.add(sqlTemp);
			List<KqDayDataDetail> kqDayDataDetails = new ArrayList<KqDayDataDetail>();
			if(OtherUtil.measureNotNull(kqDayDataDetailStr)){
				JSONArray jsonArray = JSONArray.fromObject(kqDayDataDetailStr);
				if(jsonArray !=null && jsonArray.size()>0){
					for(int i = 0;i<jsonArray.size();i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String detailId = jsonObject.getString("detailId");
						String kqId = jsonObject.getString("kqId");
						String column = jsonObject.getString("column");
						String item = jsonObject.getString("item");
						String value = jsonObject.getString("value");
						KqDayDataDetail kqDayDataDetail = new KqDayDataDetail();
						if(detailId.indexOf("detailId") == -1){
							kqDayDataDetail.setDetailId(detailId);
						}
						kqDayDataDetail.setKqColumn(column);
						kqDayDataDetail.setKqDayData(kqDayDataManager.get(kqId));
						kqDayDataDetail.setKqItem(item);
						if(OtherUtil.measureNotNull(value)){
							kqDayDataDetail.setKqValue(Double.parseDouble(value));
						}
						kqDayDataDetails.add(kqDayDataDetail);
					}
				}
			}
			kqDayDataManager.executeSqlList(sqlList);
			kqDayDataDetailManager.saveAll(kqDayDataDetails);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			log.error("saveKqDayData:"+dre.getMessage());
			return ajaxForwardError(gridOperationMessage);
		}
		//String key = ((this.isEntityIsNew())) ? "kqDayData.added" : "kqDayData.updated";
		return ajaxForward("保存成功。");
	}
    public String edit() {
        if (kqId != null) {
        	kqDayData = kqDayDataManager.get(kqId);
        	this.setEntityIsNew(false);
        } else {
        	kqDayData = new KqDayData();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String kqDayDataGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,",");
				List<String> sqlList = new ArrayList<String>();
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					kqDayDataManager.remove(removeId);
					String sqlTemp = " DELETE kq_dayDataDetail where kqId = '"+removeId+"'";
			    	sqlList.add(sqlTemp);
				}
				kqDayDataManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("kqDayData.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("check".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
				Person operPerson = this.getSessionUser().getPerson();
				String sqlTemp = "UPDATE kq_dayData SET status = '1',checker='"+operPerson.getName()+"',checkDate='"+curTime+"' ";
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				if(curDeptId!=null&&!curDeptId.equals("'all'")){
					curDeptId = "deptId in ("+curDeptId+") AND ";
				}else{
					curDeptId = "";
				}
				sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND "+curDeptId+"period = '"+curPeriod+"'";
				sqlList.add(sqlTemp);
				kqDayDataManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("kqDayData.checked");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("cancelCheck".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String sqlTemp = "UPDATE kq_dayData SET status = '0',checker=NULL,checkDate=NULL ";
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				if(curDeptId!=null&&!curDeptId.equals("'all'")){
					curDeptId = "deptId in ("+curDeptId+") AND ";
				}else{
					curDeptId = "";
				}
				sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND "+curDeptId+"period = '"+curPeriod+"'";
				sqlList.add(sqlTemp);
				kqDayDataManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("kqDayData.cancelCheck");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("submit".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
				Person operPerson = this.getSessionUser().getPerson();
				String sqlTemp = "UPDATE kq_dayData SET status = '2',submiter='"+operPerson.getName()+"',submitDate='"+curTime+"' ";
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				if(curDeptId!=null&&!curDeptId.equals("'all'")){
					curDeptId = "deptId in ("+curDeptId+") AND ";
				}else{
					curDeptId = "";
				}
				sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND "+curDeptId+"period = '"+curPeriod+"'";
				sqlList.add(sqlTemp);
				kqDayDataManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("kqDayData.submit");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqDayDataGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	private List<MonthPerson> monthPersons;
	public String kqDayDataPersonGridList(){
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(request);
			
//			String sqlTemp = "personId NOT in ( select personId from kq_dayData where period = '"+curPeriod+"' ";
//			sqlTemp += " AND deptId = '" + curDeptId +"')";
//			filters.add(new PropertyFilter("SQS_personId",sqlTemp));
			filters.add(new PropertyFilter("NES_personId","XT"));
			filters.add(new PropertyFilter("EQB_stopKq","0"));
			if(curDeptId!=null&&!curDeptId.equals("all")){
				filters.add(new PropertyFilter("INS_department.departmentId",curDeptId));
			}
//			String disableStr = this.getRequest().getParameter("filter_EQB_disable");
//			if(OtherUtil.measureNull(disableStr)){
//				filters.add(new PropertyFilter("EQB_disable","0"));
//			}
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			String sortFilter = OtherUtil.getSortFilterFromRequest(request);
			if(OtherUtil.measureNotNull(sortFilter)){
				filters.add(new PropertyFilter(sortFilter,""));
			}else{
				filters.add(new PropertyFilter("OAS_personCode",""));
			}
			monthPersons = kqDayDataManager.getByFilters(filters,MonthPerson.class);
		} catch (Exception e) {
			log.error("kqDayDataPersonGridList Error", e);
		}
		return SUCCESS;
	}
	private List<KqUpItem> kqUpItems;
	private KqUpItemManager kqUpItemManager;
	public String kqDayDataColumnInfo(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if(!"0".equals(kqUpDataType)){
			filters.add(new PropertyFilter("NES_showType","day"));
		}
		filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
		filters.add(new PropertyFilter("OAI_sn",""));
		this.kqUpItems = kqUpItemManager.getByFilters(filters);
		List<KqItem> kqItems = kqItemManager.getLeafKqItems();
		if(OtherUtil.measureNotNull(kqItems)&&!kqItems.isEmpty()){
			Map<String,String> kqItemMap = new HashMap<String, String>();
			Map<String, String> shortNameMap = new HashMap<String, String>();
			for(KqItem itemTemp:kqItems){
				String keyTemp = itemTemp.getKqItemName();
				int frequencyTemp = itemTemp.getFrequency();
				String valueTemp = "";
				switch (frequencyTemp) {
				case 0:
					valueTemp = "天";
					break;
				case 1:
					valueTemp = "次";
					break;
				case 2:
					valueTemp = "小时";
					break;
				default:
					break;
				}
				kqItemMap.put(keyTemp, valueTemp);
				shortNameMap.put(keyTemp, itemTemp.getShortName());
			}
			if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
				for(KqUpItem itemTemp:kqUpItems){
					String keyTemp = itemTemp.getItemName();
					if(kqItemMap.containsKey(keyTemp)){
						itemTemp.setFrequency(kqItemMap.get(keyTemp));
						itemTemp.setShortName(shortNameMap.get(keyTemp));
					}
				}
			}
		}
		filters.clear();
		filters.add(new PropertyFilter("EQS_period",curPeriod));
		if(curDeptId!=null&&!curDeptId.equals("all")){
			filters.add(new PropertyFilter("INS_deptId",curDeptId));
		}
		filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
		kqDayDatas = kqDayDataManager.getByFilters(filters);
		curCheckStatus = "";
		if(OtherUtil.measureNotNull(kqDayDatas)&&!kqDayDatas.isEmpty()){
			String status = kqDayDatas.get(0).getStatus();
			curCheckStatus = status;
		}
		curPeriodStatus = "";
		if(OtherUtil.measureNotNull(kqTypeId)){
			KqType kqTypeTemp = kqTypeManager.get(kqTypeId);
			ModelStatus msTemp = modelStatusManager.getUsableModelStatus(kqTypeTemp.getKqTypeCode(), curPeriod,"KQ");
			String operStrTemp = "期间："+curPeriod+" 考勤类别："+kqTypeTemp.getKqTypeName()+" ";
			if(OtherUtil.measureNull(msTemp)){
				curPeriodStatus = operStrTemp + "尚未启用。";
			}else if("2".equals(msTemp.getStatus())){
				curPeriodStatus = operStrTemp + "已结账。";
			}
		}
		return SUCCESS;
	}
	private String isValid() {
		if (kqDayData == null) {
			return "Invalid kqDayData Data";
		}

		return SUCCESS;

	}

	public String getCurDeptId() {
		return curDeptId;
	}

	public void setCurDeptId(String curDeptId) {
		this.curDeptId = curDeptId;
	}

	public String getCurPeriod() {
		return curPeriod;
	}

	public void setCurPeriod(String curPeriod) {
		this.curPeriod = curPeriod;
	}

	public String getKqCustomLayout() {
		return kqCustomLayout;
	}

	public void setKqCustomLayout(String kqCustomLayout) {
		this.kqCustomLayout = kqCustomLayout;
	}

	public String getLastPeriod() {
		return lastPeriod;
	}

	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod = lastPeriod;
	}

	public String getCurPeriodWeek() {
		return curPeriodWeek;
	}

	public void setCurPeriodWeek(String curPeriodWeek) {
		this.curPeriodWeek = curPeriodWeek;
	}

	public String getKqTypeId() {
		return kqTypeId;
	}

	public void setKqTypeId(String kqTypeId) {
		this.kqTypeId = kqTypeId;
	}

	public List<MonthPerson> getMonthPersons() {
		return monthPersons;
	}

	public void setMonthPersons(List<MonthPerson> monthPersons) {
		this.monthPersons = monthPersons;
	}

	public List<Map<String, Object>> getKqDayDataSets() {
		return kqDayDataSets;
	}

	public void setKqDayDataSets(List<Map<String, Object>> kqDayDataSets) {
		this.kqDayDataSets = kqDayDataSets;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public void setKqDayDataDetailManager(KqDayDataDetailManager kqDayDataDetailManager) {
		this.kqDayDataDetailManager = kqDayDataDetailManager;
	}

	public String getKqDayDataDetailStr() {
		return kqDayDataDetailStr;
	}

	public void setKqDayDataDetailStr(String kqDayDataDetailStr) {
		this.kqDayDataDetailStr = kqDayDataDetailStr;
	}

	public String getCurCheckStatus() {
		return curCheckStatus;
	}

	public void setCurCheckStatus(String curCheckStatus) {
		this.curCheckStatus = curCheckStatus;
	}

	public String getKqUpDataType() {
		return kqUpDataType;
	}

	public void setKqUpDataType(String kqUpDataType) {
		this.kqUpDataType = kqUpDataType;
	}

	public void setKqTypeManager(KqTypeManager kqTypeManager) {
		this.kqTypeManager = kqTypeManager;
	}

	public KqItemManager getKqItemManager() {
		return kqItemManager;
	}

	public void setKqItemManager(KqItemManager kqItemManager) {
		this.kqItemManager = kqItemManager;
	}

	public List<KqUpItem> getKqUpItems() {
		return kqUpItems;
	}

	public void setKqUpItems(List<KqUpItem> kqUpItems) {
		this.kqUpItems = kqUpItems;
	}

	public KqUpItemManager getKqUpItemManager() {
		return kqUpItemManager;
	}

	public void setKqUpItemManager(KqUpItemManager kqUpItemManager) {
		this.kqUpItemManager = kqUpItemManager;
	}

	public KqHolidayManager getKqHolidayManager() {
		return kqHolidayManager;
	}

	public void setKqHolidayManager(KqHolidayManager kqHolidayManager) {
		this.kqHolidayManager = kqHolidayManager;
	}

	public GeneralHolidayChangeManager getGeneralHolidayChangeManager() {
		return generalHolidayChangeManager;
	}

	public void setGeneralHolidayChangeManager(
			GeneralHolidayChangeManager generalHolidayChangeManager) {
		this.generalHolidayChangeManager = generalHolidayChangeManager;
	}
	public String editKqDeptCheck() {
		HttpServletRequest request = this.getRequest();
		String id = request.getParameter("id");
		//String orgCode = null;
		if(OtherUtil.measureNotNull(id)) {
			String[] ids = id.split("__");
			//orgCode = ids[0];
			curDeptId = ids[1];
			kqTypeId = ids[2];
		}
		kqUpDataType = this.getGlobalParamByKey("kqUpDataType");
		curPeriod = this.getLoginPeriod();
		lastPeriod = KqUtil.getLastPeriod(curPeriod, globalParamManager.getGlobalParamByKey("kqPersonDelayMonth"));
		if("0".equals(kqUpDataType)){
			curPeriodWeek = calWeekDetailOfPeriod(curPeriod);
		}
		return SUCCESS;
	}

	public String getPeriodDayCount() {
		return periodDayCount;
	}

	public void setPeriodDayCount(String periodDayCount) {
		this.periodDayCount = periodDayCount;
	}
	public void setColShowManager(ColShowManager colShowManager) {
		this.colShowManager = colShowManager;
	}

	public String getCurPeriodStatus() {
		return curPeriodStatus;
	}

	public void setCurPeriodStatus(String curPeriodStatus) {
		this.curPeriodStatus = curPeriodStatus;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public String getMpPeriodChange() {
		return mpPeriodChange;
	}

	public void setMpPeriodChange(String mpPeriodChange) {
		this.mpPeriodChange = mpPeriodChange;
	}
}

