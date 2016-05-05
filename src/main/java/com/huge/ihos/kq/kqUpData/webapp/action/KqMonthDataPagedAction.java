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

import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.ihos.kq.kqItem.service.KqItemManager;
import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.kq.kqType.service.KqTypeManager;
import com.huge.ihos.kq.kqUpData.model.KqDayData;
import com.huge.ihos.kq.kqUpData.model.KqDayDataDetail;
import com.huge.ihos.kq.kqUpData.model.KqMonthData;
import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.ihos.kq.kqUpData.service.KqDayDataDetailManager;
import com.huge.ihos.kq.kqUpData.service.KqDayDataManager;
import com.huge.ihos.kq.kqUpData.service.KqMonthDataManager;
import com.huge.ihos.kq.kqUpData.service.KqUpItemManager;
import com.huge.ihos.kq.util.KqUtil;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.organization.model.MonthPerson;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;




public class KqMonthDataPagedAction extends JqGridBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5400735341634281305L;
	private KqMonthDataManager kqMonthDataManager;
	private List<KqMonthData> kqMonthDatas;
	private KqMonthData kqMonthData;
	private KqItemManager kqItemManager;
	private String kqId;

	public KqMonthDataManager getKqMonthDataManager() {
		return kqMonthDataManager;
	}

	public void setKqMonthDataManager(KqMonthDataManager kqMonthDataManager) {
		this.kqMonthDataManager = kqMonthDataManager;
	}

	public List<KqMonthData> getkqMonthDatas() {
		return kqMonthDatas;
	}

	public void setKqMonthDatas(List<KqMonthData> kqMonthDatas) {
		this.kqMonthDatas = kqMonthDatas;
	}

	public KqMonthData getKqMonthData() {
		return kqMonthData;
	}

	public void setKqMonthData(KqMonthData kqMonthData) {
		this.kqMonthData = kqMonthData;
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
	private String kqTypeId;
	private String isDayUp;
	private String curDeptId;
	private String curPeriod;//当前期间
	private String lastPeriod;//t_monthperson取值期间
	private String kqCustomLayout;
	private String kqUpDataType;
	private String curDayDataCheckStatus;//审核状态
	private List<KqItem> kqItems;
	private KqDayDataManager kqDayDataManager;
	private List<KqDayData> kqDayDatas;
	private String columns;
	private KqTypeManager kqTypeManager;
	private String periodDayCount;
	public String kqMonthDataList(){
		List<KqType> kqTypes = kqTypeManager.allKqTypes(false);
		this.getRequest().setAttribute("kqTypes",kqTypes);
		if(OtherUtil.measureNotNull(kqTypes)&&!kqTypes.isEmpty()){
			kqTypeId = kqTypes.get(0).getKqTypeId();
		}else{
			kqTypeId = "";
		}
		kqUpDataType = this.getGlobalParamByKey("kqUpDataType");
//		User sessionUser = getSessionUser();
//		Department dept = sessionUser.getPerson().getDepartment();
		curPeriod = this.getLoginPeriod();
		String yearStr = curPeriod.substring(0, 4);
		String monthStr = curPeriod.substring(4);
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		periodDayCount = DateUtil.dayNumOfMonth(year, month) + "";
		curDeptId = KqUtil.getCurDeptIds();
		lastPeriod = KqUtil.getLastPeriod(curPeriod, globalParamManager.getGlobalParamByKey("kqPersonDelayMonth"));
		return SUCCESS;
	}
	
	public String getMDKqItems(){
		kqItems = kqItemManager.getLeafKqItems();
		return SUCCESS;
	}
	private List<Map<String, Object>> kqMonthDataSets;
	public String kqMonthDataGridList() {
		log.debug("enter list method!");
		try {
			List<String> sqlFilterList = new ArrayList<String>();
			List<String> sqlOrderList = new ArrayList<String>();
			String sqlTemp = "";
			if(OtherUtil.measureNotNull(curPeriod)){
				sqlTemp = "kq.period = '" + curPeriod +"'";
				sqlFilterList.add(sqlTemp);
			}
			if(OtherUtil.measureNotNull(kqTypeId)){
				sqlTemp = "kq.kqTypeId = '" + kqTypeId +"'";
				sqlFilterList.add(sqlTemp);
			}
			if(OtherUtil.measureNotNull(curDeptId)){
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				sqlTemp = "kq.deptId in (" + curDeptId +")";
				sqlFilterList.add(sqlTemp);
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
			kqMonthDataSets = kqMonthDataManager.getKqMonthDataGridData(columns, lastPeriod, sqlFilterList, sqlOrderList);
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	
	/*初始化*/
	public String refreshKqMonthData(){
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
			filters.add(new PropertyFilter("INS_department.departmentId",curDeptId));
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			List<MonthPerson> monthPersons = kqMonthDataManager.getByFilters(filters,MonthPerson.class);
			if(OtherUtil.measureNull(monthPersons)||monthPersons.isEmpty()){
				return ajaxForwardError("您所在部门当前考勤类别与"+lastPeriod+"期间无人员数据！");
			}
			filters.clear();
			filters.add(new PropertyFilter("INS_deptId",curDeptId));
			filters.add(new PropertyFilter("EQS_period",curPeriod));
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			kqMonthDatas = kqMonthDataManager.getByFilters(filters);
			List<String> kqTypePersonIdList = new ArrayList<String>();
			if(OtherUtil.measureNotNull(kqMonthDatas)&&!kqMonthDatas.isEmpty()){
				for(KqMonthData kqMonthData:kqMonthDatas){
					kqTypePersonIdList.add(kqMonthData.getPersonId());
				}
			}
			kqMonthDatas = new ArrayList<KqMonthData>();
			for(MonthPerson person : monthPersons){
				KqMonthData kqMonthDataTemp = new KqMonthData();
				String personId = person.getPersonId();
				String mapKey = personId;
    			if(kqTypePersonIdList.contains(mapKey)&&OtherUtil.measureNull(personIdStr)){
    				continue;
    			}
    			kqMonthDataTemp.setKqType(kqTypeId);
    			kqMonthDataTemp.setPeriod(curPeriod);
    			kqMonthDataTemp.setPersonId(personId);
    			kqMonthDataTemp.setPersonCode(person.getPersonCode());
    			kqMonthDataTemp.setPersonName(person.getName());
    			//kqMonthDataTemp.setOrgCode(person.getOrgCode());
				if(OtherUtil.measureNotNull(person.getDepartment().getOrg())&&OtherUtil.measureNotNull(person.getDepartment().getOrg().getOrgCode())){
					kqMonthDataTemp.setOrgName(person.getDepartment().getOrg().getOrgname());
					kqMonthDataTemp.setOrgCode(person.getDepartment().getOrg().getOrgCode());
    			}else{
    				kqMonthDataTemp.setOrgName("");
    			}
				kqMonthDataTemp.setDeptId(person.getDepartment().getDepartmentId());
				kqMonthDataTemp.setDeptCode(person.getDepartment().getDeptCode());
				kqMonthDataTemp.setDeptName(person.getDepartment().getName());
				kqMonthDataTemp.setKqDeptName(kqMonthDataTemp.getDeptName());
				kqMonthDataTemp.setStatus("0");
//				gzContentNeedCheck = this.getGlobalParamByKey("gzContentNeedCheck");
//				if("1".equals(gzContentNeedCheck)){
//					gzContentTemp.setStatus("0");
//				}else{
//					gzContentTemp.setStatus("1");
//				}
				kqMonthDataTemp.setMakeDate(operDate);
				kqMonthDataTemp.setMaker(operPerson.getName());
				kqMonthDataTemp.setEmpType(person.getStatus());
				kqMonthDatas.add(kqMonthDataTemp);
			}
			kqMonthDataManager.saveAll(kqMonthDatas);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
		return  ajaxForward("刷新成功。");
	}
	private KqDayDataDetailManager kqDayDataDetailManager;
	//汇总
	public String summaryKqDayData() {
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
			filters.add(new PropertyFilter("EQB_isDayUp","0"));
			filters.add(new PropertyFilter("OAI_sn",""));
			this.kqUpItems = kqUpItemManager.getByFilters(filters);
			Map<String, String> kqUpItemMap = new HashMap<String, String>();
			if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
				for(KqUpItem kqUpItem:kqUpItems){
					kqUpItemMap.put(kqUpItem.getItemName(), kqUpItem.getItemCode());
				}
			}
			kqItems = kqItemManager.getLeafKqItems();
			Map<String, String> itemMap = new HashMap<String, String>();
			for(KqItem kqItemTemp:kqItems){
				String nameTemp = kqItemTemp.getKqItemName();
				if(kqUpItemMap.containsKey(nameTemp)){
					itemMap.put(kqItemTemp.getShortName(), kqUpItemMap.get(nameTemp));
				}
			}
			List<Map<String, Object>> kqDayDataMap = kqDayDataManager.getKqSumaryDayData(curPeriod, kqTypeId, curDeptId);
			filters.clear();
			filters.add(new PropertyFilter("EQS_kqDayData.period",curPeriod));
			filters.add(new PropertyFilter("EQS_kqDayData.kqType",kqTypeId));
			filters.add(new PropertyFilter("INS_kqDayData.deptId",curDeptId));
			List<KqDayDataDetail> kqDayDataDetails = kqDayDataDetailManager.getByFilters(filters);
			Map<String,List<KqDayDataDetail>> detailMap = new HashMap<String, List<KqDayDataDetail>>();
			if(OtherUtil.measureNotNull(kqDayDataDetails)&&!kqDayDataDetails.isEmpty()){
				for(KqDayDataDetail kqDayDataDetail:kqDayDataDetails){
					String keyTemp = kqDayDataDetail.getKqDayData().getKqId()+"_"+kqDayDataDetail.getKqColumn();
					if(detailMap.containsKey(keyTemp)){
						List<KqDayDataDetail> deatailTempList = detailMap.get(keyTemp);
						deatailTempList.add(kqDayDataDetail);
						detailMap.put(keyTemp, deatailTempList);
					}else{
						List<KqDayDataDetail> deatailTempList = new ArrayList<KqDayDataDetail>();
						deatailTempList.add(kqDayDataDetail);
						detailMap.put(keyTemp, deatailTempList);
					}
				}
			}
			String yearStr = curPeriod.substring(0, 4);
			String monthStr = curPeriod.substring(4);
			int year = Integer.parseInt(yearStr);
			int month = Integer.parseInt(monthStr);
			int days = DateUtil.dayNumOfMonth(year, month);
			String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
			Person operPerson = this.getSessionUser().getPerson();
			List<String> sqlList = new ArrayList<String>();
			if(OtherUtil.measureNotNull(kqDayDataMap)&&!kqDayDataMap.isEmpty()){
				for(Map<String, Object> kqDayMapTemp:kqDayDataMap){
					String kqId = kqDayMapTemp.get("kqId").toString();
					Map<String, Double> mapTempMap = new HashMap<String, Double>();
					for(int i =1;i<= days;i++){
						String keyTemp = "day"+i;
						String checkKeyTemp = kqId +"_"+keyTemp;
						if(detailMap.containsKey(checkKeyTemp)){
							List<KqDayDataDetail> deatailTempList = detailMap.get(checkKeyTemp);
							for(KqDayDataDetail kqDayDataDetail:deatailTempList){
								String valueTempStr = kqDayDataDetail.getKqItem();
								double valueTemp = kqDayDataDetail.getKqValue();
								if(mapTempMap.containsKey(valueTempStr)){
									valueTemp += mapTempMap.get(valueTempStr);
								}
								mapTempMap.put(valueTempStr, valueTemp);
							}
						}else{
							String valueTempStr = kqDayMapTemp.get(keyTemp).toString();
							double valueTemp = 1;
							if(mapTempMap.containsKey(valueTempStr)){
								valueTemp += mapTempMap.get(valueTempStr);
							}
							mapTempMap.put(valueTempStr, valueTemp);
						}
					}
					kqId = OtherUtil.getRandomUUID();
					String sqlTemp = "INSERT INTO kq_monthData (kqId,kqTypeId,orgCode,orgName,";
					sqlTemp += "period,personId,personCode,personName,deptId,deptName,deptCode,";
					sqlTemp += "empType,maker,makeDate,kqDeptName,";
					String sqlValueTemp = "'"+kqId+"','"+kqTypeId+"','"+kqDayMapTemp.get("orgCode").toString()+"',";
					sqlValueTemp += "'"+kqDayMapTemp.get("orgName").toString()+"','"+curPeriod+"','"+kqDayMapTemp.get("personId").toString()+"',";
					sqlValueTemp += "'"+kqDayMapTemp.get("personCode").toString()+"','"+kqDayMapTemp.get("personName").toString()+"','"+kqDayMapTemp.get("deptId").toString()+"',";
					sqlValueTemp += "'"+kqDayMapTemp.get("deptName").toString()+"','"+kqDayMapTemp.get("deptCode").toString()+"','"+kqDayMapTemp.get("empType").toString()+"',";
					sqlValueTemp += "'"+operPerson.getName()+"','"+curTime+"','"+kqDayMapTemp.get("kqDeptName").toString()+"',";
					for(String keyTemp:mapTempMap.keySet()){
						if(itemMap.containsKey(keyTemp)){
							String itemCode = itemMap.get(keyTemp);
							sqlTemp += itemCode+",";
							sqlValueTemp += "'"+mapTempMap.get(keyTemp)+"',";
						}
					}
					sqlTemp = sqlTemp + "status)";
					sqlValueTemp = "("+sqlValueTemp + "'0')";
					sqlTemp = sqlTemp + " VALUES "+sqlValueTemp;
					sqlList.add(sqlTemp);
				}
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				String sqlTemp = "UPDATE kq_dayData SET status = '2',summaryer='"+operPerson.getName()+"',summaryDate='"+curTime+"' WHERE kqTypeId = '"+kqTypeId+"' and deptId IN ("+curDeptId+") and period='"+curPeriod+"'";
				sqlList.add(sqlTemp);
			}
			kqDayDataManager.executeSqlList(sqlList);
			return ajaxForward("汇总成功。");
		} catch (Exception e) {
			return ajaxForwardError("系统错误!");
		}
	} 
	
	public String save(){
		try {
			String kqMonthDataStr = this.getRequest().getParameter("kqMonthDataStr");
			String needSaveColumn = this.getRequest().getParameter("needSaveColumn"); 
			List<String> sqlList = new ArrayList<String>();
			String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
			Person operPerson = this.getSessionUser().getPerson();
			String sqlTemp = "UPDATE kq_monthData SET makeDate = '"+curTime+"',maker ='"+operPerson.getName()+"' ";
			curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
			sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND deptId IN ("+curDeptId+") AND period = '"+curPeriod+"'";
			log.debug("更新："+sqlTemp);
			sqlList.add(sqlTemp);
			if(OtherUtil.measureNotNull(kqMonthDataStr)){
				String[] needSaveColumns = needSaveColumn.split(",");
				JSONArray jsonArray = JSONArray.fromObject(kqMonthDataStr );
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
								sqlTemp += column + "='" + columnValue+"',";
							}
						}
					    if(OtherUtil.measureNotNull(sqlTemp)){
					    	sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
					    	sqlTemp = " UPDATE kq_monthData SET "+sqlTemp+" WHERE kqId = '" + gzIdString + "'";
					    	log.debug("更新："+sqlTemp);
					    	sqlList.add(sqlTemp);
					    }
					  }
					}
			}
			kqDayDataManager.executeSqlList(sqlList);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
//		String key = ((this.isEntityIsNew())) ? "kqMonthData.added" : "kqMonthData.updated";
		return ajaxForward("保存成功。");
	}
    public String edit() {
        if (kqId != null) {
        	kqMonthData = kqMonthDataManager.get(kqId);
        	this.setEntityIsNew(false);
        } else {
        	kqMonthData = new KqMonthData();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String kqMonthDataGridEdit() {
		try {
			String curTime = DateUtil.convertDateToString(DateUtil.ALL_PATTERN, new Date());
			Person operPerson = this.getSessionUser().getPerson();
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					kqMonthDataManager.remove(removeId);
				}
				gridOperationMessage = this.getText("kqMonthData.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("check".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String sqlTemp = "UPDATE kq_monthData SET status = '1',checker='"+operPerson.getName()+"',checkDate='"+curTime+"' ";
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND deptId IN ("+curDeptId+") AND period = '"+curPeriod+"'";
				sqlList.add(sqlTemp);
				kqDayDataManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("kqDayData.checked");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("cancelCheck".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String sqlTemp = "UPDATE kq_monthData SET status = '0',checker=NULL,checkDate=NULL ";
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND deptId IN ("+curDeptId+") AND period = '"+curPeriod+"'";
				sqlList.add(sqlTemp);
				kqDayDataManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("kqDayData.cancelCheck");
				return ajaxForward(true, gridOperationMessage, false);
			}else if("submit".equals(oper)){
				List<String> sqlList = new ArrayList<String>();
				String sqlTemp = "UPDATE kq_monthData SET status = '2',submiter='"+operPerson.getName()+"',submitDate='"+curTime+"' ";
				curDeptId = OtherUtil.splitStrAddQuotes(curDeptId, ",");
				sqlTemp += " WHERE kqTypeId = '"+kqTypeId+"' AND deptId IN ("+curDeptId+") AND period = '"+curPeriod+"'";
				sqlList.add(sqlTemp);
				kqDayDataManager.executeSqlList(sqlList);
				gridOperationMessage = this.getText("kqDayData.submit");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkKqMonthDataGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}
	
	private List<KqUpItem> kqUpItems;
	private KqUpItemManager kqUpItemManager;
	private String curCheckStatus;
	public String kqMonthDataColumnInfo(){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_kqType.kqTypeId",kqTypeId));
		filters.add(new PropertyFilter("EQB_isDayUp",isDayUp));
		filters.add(new PropertyFilter("OAI_sn",""));
		this.kqUpItems = kqUpItemManager.getByFilters(filters);
		kqItems = kqItemManager.getLeafKqItems();
		if(OtherUtil.measureNotNull(kqItems)&&!kqItems.isEmpty()){
			Map<String,String> kqItemMap = new HashMap<String, String>();
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
			}
			if(OtherUtil.measureNotNull(kqUpItems)&&!kqUpItems.isEmpty()){
				for(KqUpItem itemTemp:kqUpItems){
					String keyTemp = itemTemp.getItemName();
					if(kqItemMap.containsKey(keyTemp)){
						itemTemp.setFrequency(kqItemMap.get(keyTemp));
					}
				}
			}
		}
		
		
		filters.clear();
		filters.add(new PropertyFilter("EQS_period",curPeriod));
		filters.add(new PropertyFilter("INS_deptId",curDeptId));
		filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
		kqDayDatas = kqDayDataManager.getByFilters(filters);
		curDayDataCheckStatus = "";
		if(OtherUtil.measureNotNull(kqDayDatas)&&!kqDayDatas.isEmpty()){
			String status = kqDayDatas.get(0).getStatus();
			curDayDataCheckStatus = status;
		}
		filters.clear();
		filters.add(new PropertyFilter("EQS_period",curPeriod));
		filters.add(new PropertyFilter("INS_deptId",curDeptId));
		filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
		kqMonthDatas = kqMonthDataManager.getByFilters(filters);
		curCheckStatus = "";
		if(OtherUtil.measureNotNull(kqMonthDatas)&&!kqMonthDatas.isEmpty()){
			String status = kqMonthDatas.get(0).getStatus();
			curCheckStatus = status;
		}
		return SUCCESS;
	}
	
	private List<MonthPerson> monthPersons;
	public String kqMonthDataPersonGridList(){
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
//			String sqlTemp = "personId NOT in ( select personId from kq_monthData where period = '"+curPeriod+"' ";
//			sqlTemp += " AND deptId IN (" + OtherUtil.splitStrAddQuotes(curDeptId, ",") +"')";
//			filters.add(new PropertyFilter("SQS_personId",sqlTemp));
			filters.add(new PropertyFilter("NES_personId","XT"));
			filters.add(new PropertyFilter("EQB_stopKq","0"));
			filters.add(new PropertyFilter("INS_department.departmentId",curDeptId));
//			String disableStr = this.getRequest().getParameter("filter_EQB_disable");
//			if(OtherUtil.measureNull(disableStr)){
//				filters.add(new PropertyFilter("EQB_disable","0"));
//			}
			filters.add(new PropertyFilter("EQS_kqType",kqTypeId));
			filters.add(new PropertyFilter("EQS_checkperiod",lastPeriod));
			monthPersons = kqMonthDataManager.getByFilters(filters,MonthPerson.class);
		} catch (Exception e) {
			log.error("kqMonthDataPersonGridList Error", e);
		}
		return SUCCESS;
	}
	private String isValid() {
		if (kqMonthData == null) {
			return "Invalid kqMonthData Data";
		}

		return SUCCESS;

	}

	public String getKqTypeId() {
		return kqTypeId;
	}

	public void setKqTypeId(String kqTypeId) {
		this.kqTypeId = kqTypeId;
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

	public String getLastPeriod() {
		return lastPeriod;
	}

	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod = lastPeriod;
	}

	public String getKqCustomLayout() {
		return kqCustomLayout;
	}

	public void setKqCustomLayout(String kqCustomLayout) {
		this.kqCustomLayout = kqCustomLayout;
	}

	public List<MonthPerson> getMonthPersons() {
		return monthPersons;
	}

	public void setMonthPersons(List<MonthPerson> monthPersons) {
		this.monthPersons = monthPersons;
	}

	public String getKqUpDataType() {
		return kqUpDataType;
	}

	public void setKqUpDataType(String kqUpDataType) {
		this.kqUpDataType = kqUpDataType;
	}

	public List<KqItem> getKqItems() {
		return kqItems;
	}

	public void setKqItems(List<KqItem> kqItems) {
		this.kqItems = kqItems;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public List<Map<String, Object>> getKqMonthDataSets() {
		return kqMonthDataSets;
	}

	public void setKqMonthDataSets(List<Map<String, Object>> kqMonthDataSets) {
		this.kqMonthDataSets = kqMonthDataSets;
	}

	public String getCurDayDataCheckStatus() {
		return curDayDataCheckStatus;
	}

	public void setCurDayDataCheckStatus(String curDayDataCheckStatus) {
		this.curDayDataCheckStatus = curDayDataCheckStatus;
	}

	public KqDayDataManager getKqDayDataManager() {
		return kqDayDataManager;
	}

	public void setKqDayDataManager(KqDayDataManager kqDayDataManager) {
		this.kqDayDataManager = kqDayDataManager;
	}

	public List<KqDayData> getKqDayDatas() {
		return kqDayDatas;
	}

	public void setKqDayDatas(List<KqDayData> kqDayDatas) {
		this.kqDayDatas = kqDayDatas;
	}

	public KqItemManager getKqItemManager() {
		return kqItemManager;
	}

	public void setKqItemManager(KqItemManager kqItemManager) {
		this.kqItemManager = kqItemManager;
	}

	public KqTypeManager getKqTypeManager() {
		return kqTypeManager;
	}

	public void setKqTypeManager(KqTypeManager kqTypeManager) {
		this.kqTypeManager = kqTypeManager;
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

	public String getCurCheckStatus() {
		return curCheckStatus;
	}

	public void setCurCheckStatus(String curCheckStatus) {
		this.curCheckStatus = curCheckStatus;
	}

	public String getIsDayUp() {
		return isDayUp;
	}

	public void setIsDayUp(String isDayUp) {
		this.isDayUp = isDayUp;
	}
	
	/*===========================人事部门审核=============================*/
	private List<Map<String, Object>> kqDeptChecks;
	
	public String kqDeptCheckList() {
		try {
			List<KqType> kqTypes = kqTypeManager.allKqTypes(false);
			this.getRequest().setAttribute("kqTypes",kqTypes);
			List<MenuButton> menuButtons = this.getMenuButtons();
			setMenuButtonsToJson(menuButtons);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("kqItemList Error", e);
		}
		return SUCCESS;
	}
	public String kqDeptCheckGridList() {
		log.debug("enter list method!");
		try {
			//List list = kqMonthDataManager.getkqDeptCheckDatas();
			kqDeptChecks = new ArrayList<Map<String,Object>>();
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_period",getLoginPeriod()));
			filters.add(new PropertyFilter("GPS_deptId","orgCode,deptId,deptName,kqType,status"));
			List list = kqDayDataManager.getByFilters(filters);
			if(OtherUtil.measureNotNull(list)) {
				for (int i = 0; i < list.size(); i++) {
					Object[] objs = (Object[]) list.get(i);
					String orgCodeStr = (String) objs[0];
					String deptIdStr = (String) objs[1];
					String deptNameStr = (String) objs[2];
					String kqTypeStr = (String) objs[3];
					String statusStr = (String) objs[4];
					String idStr = orgCodeStr +"__" + deptIdStr + "__" + kqTypeStr;
					KqType kqType = kqTypeManager.get(kqTypeStr);
					Map<String, Object> kcMap = new HashMap<String, Object>();
					kcMap.put("id", idStr);
					kcMap.put("orgCode", orgCodeStr);
					kcMap.put("deptId", deptIdStr);
					kcMap.put("deptName", deptNameStr);
					kcMap.put("kqType", kqTypeStr);
					kcMap.put("kqTypeName", kqType.getKqTypeName());
					kcMap.put("period",this.getLoginPeriod());
					kcMap.put("status", statusStr);
					kqDeptChecks.add(kcMap);
				}
			}

		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	/**
	 * 审核/否决
	 * @return
	 */
	public String kqDeptChecked() {
		try {
			HttpServletRequest request = this.getRequest();
			String id = request.getParameter("id");
			String orgCode = null;
			String deptId = null;
			String kqType = null;
			if(OtherUtil.measureNotNull(id)) {
				String[] ids = id.split("__");
				orgCode = ids[0];
				deptId = ids[1];
				kqType = ids[2];
			}
			List<KqDayData> kqDayDatas = null;
			if(OtherUtil.measureNotNull(deptId) && OtherUtil.measureNotNull(orgCode) && OtherUtil.measureNotNull(kqType)) {
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_deptId",deptId));
				filters.add(new PropertyFilter("EQS_orgCode",orgCode));
				filters.add(new PropertyFilter("EQS_kqType",kqType));
				kqDayDatas = kqDayDataManager.getByFilters(filters);
			}
			if(kqDayDatas != null) {
				if("check".equals(oper)) {
					for(KqDayData data : kqDayDatas) {
						data.setStatus("3");
					}
				} else if("veto".equals(oper)) {
					for(KqDayData data : kqDayDatas) {
						data.setStatus("4");
						data.setCheckDate(null);
						data.setChecker(null);
						data.setSubmiter(null);
						data.setSubmitDate(null);
					}
				}else if("reCheck".equals(oper)) {
					for(KqDayData data : kqDayDatas) {
						data.setStatus("2");
						data.setCheckDate(null);
						data.setChecker(null);
					}
				}
				kqDayDataManager.saveAll(kqDayDatas);
			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error("kqDeptChecked error!",e);
			return ajaxForwardError("系统错误！");
		}

		return SUCCESS;
	}
	
	public List<Map<String, Object>> getKqDeptChecks() {
		return kqDeptChecks;
	}

	public void setKqDeptChecks(List<Map<String, Object>> kqDeptChecks) {
		this.kqDeptChecks = kqDeptChecks;
	}

	public KqDayDataDetailManager getKqDayDataDetailManager() {
		return kqDayDataDetailManager;
	}

	public void setKqDayDataDetailManager(KqDayDataDetailManager kqDayDataDetailManager) {
		this.kqDayDataDetailManager = kqDayDataDetailManager;
	}

	public String getPeriodDayCount() {
		return periodDayCount;
	}

	public void setPeriodDayCount(String periodDayCount) {
		this.periodDayCount = periodDayCount;
	}

}

