package com.huge.ihos.system.configuration.modelstatus.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.PeriodManager;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.ihos.system.exinterface.ProxyGetGZResource;
import com.huge.ihos.system.exinterface.ProxyGetKQResource;
import com.huge.ihos.system.exinterface.ProxySynchronizeToHr;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.ihos.system.systemManager.menu.service.MenuManager;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class ModelStatusPagedAction extends JqGridBaseAction implements Preparable {

	private List<ModelStatus> modelStatuss;
	private ModelStatus modelStatus;
	private String id;
	private String subSystemCode;
	
	private PeriodManager periodManager;
	private MenuManager menuManager;
	private String jzType;
	
	public void setPeriodManager(PeriodManager periodManager) {
		this.periodManager = periodManager;
	}

	public List<ModelStatus> getModelStatuss() {
		return modelStatuss;
	}

	public void setModelStatuss(List<ModelStatus> modelStatuss) {
		this.modelStatuss = modelStatuss;
	}

	public ModelStatus getModelStatus() {
		return modelStatus;
	}

	public void setModelStatus(ModelStatus modelStatus) {
		this.modelStatus = modelStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

	public void prepare() throws Exception {
		
		this.clearSessionMessages();
	}
	
	public String modelStatusLoad(){
		if(OtherUtil.measureNotNull(subSystemCode)){
			jzType = menuManager.getJzTypeBySubSystemCode(subSystemCode);
		}else{
			jzType = "月";
		}
		getResponse().setCharacterEncoding("UTF-8");
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String modelStatusGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = modelStatusManager
					.getModelStatusCriteria(pagedRequests,filters);
			this.modelStatuss = (List<ModelStatus>) pagedRequests.getList();
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
			modelStatusManager.save(modelStatus);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "modelStatus.added" : "modelStatus.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (id != null) {
        	modelStatus = modelStatusManager.get(id);
        	this.setEntityIsNew(false);
        } else {
        	modelStatus = new ModelStatus();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String modelStatusGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id,
						",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					log.debug("Delete ModelStatus " + removeId);
//					ModelStatus modelStatus = modelStatusManager.get(removeId);
					modelStatusManager.remove(removeId);
				}
				gridOperationMessage = this.getText("modelStatus.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}else if(oper.equals("delModelId")){
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("INS_modelId",id));
				modelStatuss = modelStatusManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
					for(ModelStatus mStatus:modelStatuss){
						String removeId = mStatus.getId();
						modelStatusManager.remove(removeId);
					}
				}
				gridOperationMessage = this.getText("modelStatus.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkModelStatusGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (modelStatus == null) {
			return "Invalid modelStatus Data";
		}

		return SUCCESS;

	}
	/**
	 * ==============================================================概况
	 */
	private Map<String,String> generalDatas;
	
	public Map<String, String> getGeneralDatas() {
		return generalDatas;
	}

	/**
	 * 获得当前活动年、月、半年、季期间以及已结账的个数，当前年期间个数
	 * @return
	 */
	public String getModelStatusGeneralData(){
		currentYear = modelStatusManager.getUsingPeriod(subSystemCode, "年");
		generalDatas = new HashMap<String,String>();
		int periodCount = 0;
		if(OtherUtil.measureNull(currentYear)){
			currentYear = "-1";
			
		}else{
			List<Period> periods = periodManager.getPeriodsByYear(currentYear);
			if(periods!=null && periods.size()>0){
				periodCount = periods.size();
			}
		}
		generalDatas.put("periodCount", ""+periodCount);
		String period = null;
		int count = 0;
		for(int i=0;i<periodTypes.length;i++){
			String periodType = periodTypes[i];
			period = modelStatusManager.getUsingPeriod(subSystemCode, periodType);
			if(period==null){
				period = "无";
			}
			generalDatas.put(periodType, OtherUtil.parsePeriod(period));
			if(i==0){
				continue;
			}else{
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
				filters.add(new PropertyFilter("EQS_periodType",periodType));
				filters.add(new PropertyFilter("EQS_status","2"));
				filters.add(new PropertyFilter("LIKES_checkperiod",currentYear+"*"));
				modelStatuss = modelStatusManager.getByFilters(filters);
				count = 0;
				if(modelStatuss!=null && modelStatuss.size()>0){
					count = modelStatuss.size();
				}
				generalDatas.put(periodType+"count", ""+count);
			}
		}
		return SUCCESS;
	}
	/**
	 *================================================================初始
	 */
	
	private List<String> yearList;//年份集合
	private List<String> monthList;//月份集合--对应年
	private String currentYear;//当前年--选择的考核年
	private List<String> hasInitialized;//已经启用的季度集合
	
	
	public List<String> getYearList() {
		return yearList;
	}

	public List<String> getMonthList() {
		return monthList;
	}
	
	public String getCurrentYear() {
		return currentYear;
	}
	
	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public List<String> getHasInitialized() {
		return hasInitialized;
	}

	/**
	 * 跳转至初始页面时方法，需要初始化年列表、当前年期间列表、当前年度考核是否启用
	 * @return
	 */
	private String currentHalfYear;//当前半年
	private String currentSeason;//当前季度
	private String currentMonth;//当前月
	public String modelStatusInit(){
		try {
			yearList = periodManager.getYearList();
			if(yearList!=null && yearList.size()>0){
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
				filters.add(new PropertyFilter("EQS_periodType","年"));
				filters.add(new PropertyFilter("EQS_status","2"));
				modelStatuss = modelStatusManager.getByFilters(filters);
				if(modelStatuss!=null && modelStatuss.size()>0){
					List<String> closedYears = new ArrayList<String>();
					for(ModelStatus modelStatus:modelStatuss){
						closedYears.add(modelStatus.getCheckperiod());
					}
					yearList.removeAll(closedYears);
				}
			}
			
			if(yearList.size()>0){
				currentYear = yearList.get(0);				
				monthList = periodManager.getMonthList(currentYear);
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
			filters.add(new PropertyFilter("EQS_periodType","年"));
			filters.add(new PropertyFilter("EQS_status","1"));
			modelStatuss = modelStatusManager.getByFilters(filters);
			if(modelStatuss!=null && modelStatuss.size()>0){				
				currentYear = modelStatuss.get(0).getCheckperiod();
				monthList = periodManager.getMonthList(currentYear);
				filters.clear();
				filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
				filters.add(new PropertyFilter("LIKES_checkperiod",currentYear+"*"));
				filters.add(new PropertyFilter("EQS_status","1"));
				modelStatuss = modelStatusManager.getByFilters(filters);
				if(OtherUtil.measureNotNull(modelStatuss)&&!modelStatuss.isEmpty()){
					for(ModelStatus msStatus:modelStatuss){
						if("半年".equals(msStatus.getPeriodType())){
							currentHalfYear = msStatus.getCheckperiod();
						}
						if("季".equals(msStatus.getPeriodType())){
							currentSeason = msStatus.getCheckperiod();
						}
						if("月".equals(msStatus.getPeriodType())){
							currentMonth = msStatus.getCheckperiod();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 切换考核年份时，重新获取monthList
	 * @return
	 */
	public String initPeriodTypeData(){
		currentYear = this.getRequest().getParameter("year");
		monthList = periodManager.getMonthList(currentYear);
		return SUCCESS;
	}
	private String[] periodTypes = {"年","半年","季","月"};
	/**
	 * 检查期间类型是否启用
	 * @return
	 */
	public String checkPeriodTypeIsStarted(){
		List<PropertyFilter> filters = null;
		List<ModelStatus> list = null;
		hasInitialized = new ArrayList<String>();
		for(int i=0;i<periodTypes.length;i++){
			filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
			filters.add(new PropertyFilter("EQS_periodType",periodTypes[i]));
			filters.add(new PropertyFilter("LIKES_checkperiod",currentYear+"*"));
			list = modelStatusManager.getByFilters(filters);
			if(list!=null && list.size()>0){
				hasInitialized.add(periodTypes[i]);
			}
		}
		return SUCCESS;
	}
	/**
	 * 启用期间
	 * @return
	 */
	public String startPeriod(){
		try {
			/*if(!"年".equals(periodType)){
				String period = modelStatusManager.getUsingPeriod("kh", "年");
				if(period==null){
					period = modelStatusManager.getClosedPeriod("kh", "年");
					if(period==null){
						return ajaxForward(false, "当前年度考核尚未启用！", false);
					}else{
						return ajaxForward(false, "当前年度考核已结账，不能再启用其他考核期间！", false);
					}
				}
			}*/
			modelStatus = new ModelStatus();
			modelStatus.setModelId(subSystemCode);
			modelStatus.setPeriodType(periodType);
			modelStatus.setCheckperiod(checkperiod);
			modelStatus.setStatus("1");
			modelStatusManager.save(modelStatus);
			return ajaxForward(true, "启用成功！", false);
		} catch (Exception e) {
			log.error("initPeriod Error", e);
			return ajaxForward(false, "启用失败", false);
		}
	}
	/**
	 * ===================================================================结账
	 */
	private String checkperiodShow;
	
	public String getCheckperiodShow() {
		return checkperiodShow;
	}
	
	private Map<String,String> closingPeriods;//等待结账的集合
	
	public Map<String, String> getClosingPeriods() {
		return closingPeriods;
	}

	/**
	 * 获取当前所有考核类型待结账的期间
	 * @return
	 */
	public String getAllClosingPeriods(){
		String period = null;
		closingPeriods = new HashMap<String,String>();
		currentYear = modelStatusManager.getUsingPeriod(subSystemCode, "年");
		if(currentYear==null){
			currentYear = modelStatusManager.getClosedPeriod(subSystemCode, "年");
			if(currentYear==null){
				currentYear = "";
			}
		}
		for(int i=0;i<periodTypes.length;i++){
			period = modelStatusManager.getUsingPeriod(subSystemCode, periodTypes[i]);
			
			if(period==null){
				period = modelStatusManager.getClosedPeriod(subSystemCode, periodTypes[i]);
				if(period==null){//未启用
					period = "notInit";
				}else{//已结账
					if(!period.startsWith(currentYear)){
						period = "notInit";
					}else{
						period = OtherUtil.parsePeriod(period);
						closingPeriods.put(periodTypes[i]+"hasClosed", period);
						period = "hasClosed";
					}
				}
			}
			closingPeriods.put(periodTypes[i], period);
			if(period.length()==4){
				closingPeriods.put("showYear", period+"年");
			}else if(period.indexOf('-')>0){
				closingPeriods.put("showSeason", period.substring(0, 4)+"年"+period.substring(5, 6)+"季度");
			}
		}
		return SUCCESS;
	}
	
	
	
	public String periodType;
	public String checkperiod;
	
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}
	
	public String getCheckperiod() {
		return checkperiod;
	}
	
	private String showPeriod = "";
	
	public String getShowPeriod() {
		return showPeriod;
	}
	
	private boolean allClosed;

	public boolean getAllClosed() {
		return allClosed;
	}

	/**
	 * 结账
	 * @return
	 */
	public String closePeriod(){
		try {
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_periodType",periodType));
			filters.add(new PropertyFilter("EQS_checkperiod",checkperiod));
			filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
			filters.add(new PropertyFilter("EQS_status","1"));
			modelStatuss = modelStatusManager.getByFilters(filters);
			if(modelStatuss!=null && modelStatuss.size()==1){
				modelStatus = modelStatuss.get(0);
				/*
				 * 判断是否为最后期间[最后一个月、第四季、下半年、年]
				 * 如果是，返回一个特定的值表示
				 */
				boolean syncFlag = true;
				if(modelStatus.getPeriodType().equals("年")) {
					syncFlag = false;
				}
				//这里新建一个同步日志对象用来保存日志
				//TODO 用外部接口替换直接调用
				if(syncFlag) {
					ProxySynchronizeToHr proxySynchronizeToHr = new ProxySynchronizeToHr();
					proxySynchronizeToHr.jzSynchronizeOrgToHr(modelStatus.getCheckperiod());
				}
				//syncHrData syncHrData = new syncHrData();
				//syncHrData.setSyncOperator(this.getSessionUser().getPerson().getName());
				//String ansyOrgDeptPerson = this.globalParamManager.getGlobalParamByKey("ansyOrgDeptPerson");
				modelStatus = modelStatusManager.closeCount(modelStatus,subSystemCode);

				if(periodType.equals("月")){
					if(periodManager.getNextPeriod(checkperiod)==null){
						checkperiod = "hasClosed";
					}else{
						checkperiod = modelStatus.getCheckperiod();
					}
				}else if(checkperiod.endsWith("-4") || checkperiod.endsWith("下半年") || periodType.equals("年")){
					checkperiod = "hasClosed";
				}else{
					checkperiod = modelStatus.getCheckperiod();
					showPeriod = OtherUtil.parsePeriod(checkperiod);
				}
			}else {
				
			}
			if(!"年".equals(periodType)){
				filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("NIS_periodType","年"));
				filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
				filters.add(new PropertyFilter("EQS_status","1"));
				modelStatuss = modelStatusManager.getByFilters(filters);
				if(modelStatuss!=null &&  modelStatuss.size()>0){
					allClosed = false;
				}else{//年度其他期间类型全部结账
					allClosed = true;
				}
			}
			return ajaxForward(true, "结账成功！", false);
		} catch (Exception e) {
			log.error("closePeriod Error", e);
			return ajaxForward(false, "结账失败", false);
		}
	}
	/**
	 * ===============================================反结账
	 */
	
	private Map<String,String> antiPeriods;//当前可以反结账的期间集合
	
	public Map<String, String> getAntiPeriods() {
		return antiPeriods;
	}
	/**
	 * 获取可以反结账的所有期间
	 * @return
	 */
	public String getAllAntiPeriods(){
		String period = null;
		/*currentYear = modelStatusManager.getClosedPeriod("kh", "年");//
		if(currentYear==null){//表示没有年结账
			currentYear = "";
		}else{
			String usingYear = modelStatusManager.getUsingPeriod("kh", "年");//
			if(usingYear!=null){
				currentYear = usingYear;
			}
		}*/
		currentYear = modelStatusManager.getUsingPeriod(subSystemCode, "年");
		if(currentYear==null){
			currentYear = modelStatusManager.getClosedPeriod(subSystemCode, "年");
			if(currentYear==null){
				currentYear = "";
			}
		}
		antiPeriods = new HashMap<String,String>();
		for(int i=0;i<periodTypes.length;i++){
			period = modelStatusManager.getClosedPeriod(subSystemCode, periodTypes[i]);
			if(period==null){
				period = "noAnti";
			}else{
				if(i!=0 && !period.startsWith(currentYear)){
					period = "noAnti";
				}
			}
			antiPeriods.put(periodTypes[i], period);
			if(period.length()==4){
				antiPeriods.put("showYear", period+"年");
			}else if(period.indexOf('-')>0){
				antiPeriods.put("showSeason", period.substring(0, 4)+"年"+period.substring(5, 6)+"季度");
			}
		}
		if(!"".equals(currentYear) && !currentYear.equals(modelStatusManager.getClosedPeriod(subSystemCode, "年"))){
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
			filters.add(new PropertyFilter("EQS_status","2"));
			filters.add(new PropertyFilter("LIKES_checkperiod",currentYear+"*"));
//			filters.add(new PropertyFilter("NES_periodType","年"));
			modelStatuss = modelStatusManager.getByFilters(filters);
			if(modelStatuss!=null &&  modelStatuss.size()>0){//上年度不能反结账
				antiPeriods.put("showNot", "showNot");
				antiPeriods.put("currentYear", currentYear);
			}
		}
		return SUCCESS;
	}
	/**
	 * 反结账
	 * @return
	 */
	public String antiPeriod(){
		try {
			String antiPeriod = checkperiod;
			checkperiod = modelStatusManager.antiCount(periodType, checkperiod,subSystemCode);
			if(checkperiod==null){
				checkperiod = "noAnti";
			}
			if(checkperiod.length()==4){
				showPeriod = checkperiod+"年";
			}else if(checkperiod.indexOf('-')>0){
				showPeriod = checkperiod.substring(0, 4)+"年"+checkperiod.substring(5, 6)+"季度";
			}
			antiPeriods = new HashMap<String,String>();
			if("年".equals(periodType)){
				String period = null;
				for(int i=1;i<periodTypes.length;i++){
					period = modelStatusManager.getClosedPeriod(subSystemCode, periodTypes[i]);
					if(period==null){
						period = "noAnti";
					}
					antiPeriods.put(periodTypes[i], period);
					if(period.indexOf('-')>0){
						antiPeriods.put("showSeason", period.substring(0, 4)+"年"+period.substring(5, 6)+"季度");
					}
				}
			}else{//本年度非"年"反结账后，判断上一年度是否可以反结账
				currentYear = antiPeriod.substring(0, 4);
				String closedYear = modelStatusManager.getClosedPeriod(subSystemCode, "年");
				if(closedYear==null){
					currentYear = null;
				}
				if(currentYear!=null){
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQS_modelId",subSystemCode));
					filters.add(new PropertyFilter("EQS_status","2"));
					filters.add(new PropertyFilter("LIKES_checkperiod",currentYear+"*"));
					modelStatuss = modelStatusManager.getByFilters(filters);
					if(modelStatuss==null || modelStatuss.size()==0){//上年度可以反结账
						antiPeriods.put("showDo", "showDo");
					}
				}
			}
			return ajaxForward(true, "反结账成功！", false);
		} catch (Exception e) {
			log.error("antiPeriod Error", e);
			return ajaxForward(false, "反结账失败", false);
		}
	}

	public String getSubSystemCode() {
		return subSystemCode;
	}

	public void setSubSystemCode(String subSystemCode) {
		this.subSystemCode = subSystemCode;
	}

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public String getJzType() {
		return jzType;
	}

	public void setJzType(String jzType) {
		this.jzType = jzType;
	}

	public String getCurrentHalfYear() {
		return currentHalfYear;
	}

	public void setCurrentHalfYear(String currentHalfYear) {
		this.currentHalfYear = currentHalfYear;
	}

	public String getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(String currentSeason) {
		this.currentSeason = currentSeason;
	}

	public String getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}
	private String gridSql;
	private String gridColModelStr;
	private String groupHeaderStr;
	//所有子系统期间状态
	public String allSystemPeriodStatus(){
		gridSql = "";
		JSONArray colModelArray = new JSONArray();
		JSONArray groupHeaders = new JSONArray();
		JSONArray gridSqlArray = new JSONArray();
		JSONObject periodObject = new JSONObject();
		periodObject.put("name", "period");
		periodObject.put("index", "period");
		periodObject.put("align", "center");
		periodObject.put("width", "80px");
		periodObject.put("label", "期间");
		periodObject.put("hidden", false);
		periodObject.put("key", true);
		colModelArray.add(periodObject);
		String sql = "SELECT a.code code,b.menuName name FROM sy_model a,t_menu b WHERE a.menuId = b.menuId ORDER BY jzSn";
		List<Map<String, Object>> modelList = modelStatusManager.getBySqlToMap(sql);
		if(OtherUtil.measureNotNull(modelList)&&!modelList.isEmpty()){
			for(Map<String, Object> modelMap:modelList){
				String code = modelMap.get("code").toString();
				String name = modelMap.get("name").toString();
				if("KQ".equals(code)){
					ProxyGetKQResource proxyGetKQResource = new ProxyGetKQResource();
					List<ReportType> reportTypes = proxyGetKQResource.allKqTypes(false);
					if(OtherUtil.measureNotNull(reportTypes)&&!reportTypes.isEmpty()){
						for(ReportType reportType:reportTypes){
							String typeId = reportType.getTypeCode();
							String typeName = reportType.getTypeName();
							String codeTemp = code + "_" + typeId;
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("name", codeTemp);
							jsonObject.put("index", codeTemp);
							jsonObject.put("align", "center");
							jsonObject.put("width", "120px");
							jsonObject.put("label", typeName);
							jsonObject.put("hidden", false);
							jsonObject.put("key", false);
							colModelArray.add(jsonObject);
							JSONObject gridSqlObject = new JSONObject();
							gridSqlObject.put("code", codeTemp);
							gridSqlObject.put("type", "月");
							gridSqlArray.add(gridSqlObject);
						}
						JSONObject groupObject = new JSONObject();
						String codeTemp = code + "_" + reportTypes.get(0).getTypeCode();
						groupObject.put("startColumnName", codeTemp);
						groupObject.put("numberOfColumns", reportTypes.size());
						groupObject.put("titleText", name);
						groupHeaders.add(groupObject);
					}
				}else if("GZ".equals(code)){
					ProxyGetGZResource proxyGetGZResource = new ProxyGetGZResource();
					List<ReportType> reportTypes = proxyGetGZResource.allGzTypes(false);
					if(OtherUtil.measureNotNull(reportTypes)&&!reportTypes.isEmpty()){
						for(ReportType reportType:reportTypes){
							String typeId = reportType.getTypeCode();
							String typeName = reportType.getTypeName();
							String codeTemp = code + "_" + typeId;
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("name", codeTemp);
							jsonObject.put("index", codeTemp);
							jsonObject.put("align", "center");
							jsonObject.put("width", "120px");
							jsonObject.put("label", typeName);
							jsonObject.put("hidden", false);
							jsonObject.put("key", false);
							colModelArray.add(jsonObject);
							JSONObject gridSqlObject = new JSONObject();
							gridSqlObject.put("code", codeTemp);
							gridSqlObject.put("type", reportType.getIssueType());
							gridSqlArray.add(gridSqlObject);
						}
						JSONObject groupObject = new JSONObject();
						String codeTemp = code + "_" + reportTypes.get(0).getTypeCode();
						groupObject.put("startColumnName", codeTemp);
						groupObject.put("numberOfColumns", reportTypes.size());
						groupObject.put("titleText", name);
						groupHeaders.add(groupObject);
					}
				}else{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("name", code);
					jsonObject.put("index", code);
					jsonObject.put("align", "center");
					jsonObject.put("width", "120px");
					jsonObject.put("label", name);
					jsonObject.put("hidden", false);
					jsonObject.put("key", false);
					colModelArray.add(jsonObject);
					JSONObject gridSqlObject = new JSONObject();
					gridSqlObject.put("code", code);
					gridSqlObject.put("type", "月");
					gridSqlArray.add(gridSqlObject);
				}
			}
		}
		gridColModelStr = colModelArray.toString();
		groupHeaderStr = groupHeaders.toString();
		gridSql = gridSqlArray.toString();
		return SUCCESS;
	}
	private List<Map<String, Object>> allSystemPeriodStatuss;
	public String allSystemPeriodStatusGridList(){
		try {
			String sql = "SELECT b.periodCode period";
			String addFilter = "";
			List<String> addColList = new ArrayList<String>();
			if(OtherUtil.measureNotNull(gridSql)){
				JSONArray gridSqlArray = JSONArray.fromObject(gridSql);
				for(int i = 0;i < gridSqlArray.size();i++ ){
					JSONObject jsonObject = gridSqlArray.getJSONObject(i);
					String code = jsonObject.get("code").toString();
					String type = jsonObject.get("type").toString();
					if("次".equals(type)){
						addColList.add(code);
						addFilter += "'" + code + "',";
					}else{
						sql += ",CASE (SELECT a.status FROM sy_modelstatus a WHERE a.modelId = '"+code+"' AND a.checkperiod = b.periodCode) ";
						sql += " WHEN '1' THEN '进行中' WHEN '2' THEN '已结账' ELSE '' END ";
						sql += " AS " + code;
					}
				}
			}
			sql += " FROM t_period b ORDER BY b.periodCode DESC ";
			allSystemPeriodStatuss = modelStatusManager.getBySqlToMap(sql);
			if(addColList.size() > 0){
				String addSql = "SELECT b.periodCode period";
				addFilter = OtherUtil.subStrEnd(addFilter, ",");
				addSql += " ,CASE a.status ";
				addSql += " WHEN '1' THEN '进行中' WHEN '2' THEN '已结账' ELSE '' END AS status ";
				addSql += " ,a.modelId AS modelId,a.checkNumber AS checkNumber ";
				addSql += " FROM t_period b,sy_modelstatus a WHERE a.modelId IN (" + addFilter + ") ";
				addSql += " AND a.checkperiod = b.periodCode ORDER BY b.periodCode DESC,a.checkNumber";
				List<Map<String, Object>> addResultList = modelStatusManager.getBySqlToMap(addSql);
				if(OtherUtil.measureNotNull(addResultList)&&!addResultList.isEmpty()){
					Map<String, Map<String, String>> addMap = new HashMap<String, Map<String,String>>();
					for(Map<String, Object> map:addResultList){
						String period = map.get("period").toString();
						String status = map.get("status").toString();
						String checkNumber = map.get("checkNumber").toString();
						String modelId = map.get("modelId").toString();
						if(addMap.containsKey(period)){
							Map<String, String> tempMap = addMap.get(period);
							if(tempMap.containsKey(modelId)){
								String valueTemp = tempMap.get(modelId);
								valueTemp += ";"+checkNumber+":"+status;
								tempMap.put(modelId, valueTemp);
							}else{
								tempMap.put(modelId, checkNumber+":"+status);
							}
							addMap.put(period, tempMap);
						}else{
							Map<String, String> tempMap = new HashMap<String, String>();
							tempMap.put(modelId, checkNumber+":"+status);
							addMap.put(period, tempMap);
						}
					}
					if(OtherUtil.measureNull(allSystemPeriodStatuss)){
						allSystemPeriodStatuss = new ArrayList<Map<String,Object>>();
					}
					for(Map<String,Object> mapTemp:allSystemPeriodStatuss){
						String period = mapTemp.get("period").toString();
						if(addMap.containsKey(period)){
							Map<String, String> addTempMap = addMap.get(period);
							for(String keyTemp:addTempMap.keySet()){
								mapTemp.put(keyTemp, addTempMap.get(keyTemp).toString());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("allSystemPeriodStatusGridListError:"+e.getMessage());
		}
		return SUCCESS;
	}

	public String getGridSql() {
		return gridSql;
	}

	public void setGridSql(String gridSql) {
		this.gridSql = gridSql;
	}

	public String getGridColModelStr() {
		return gridColModelStr;
	}

	public void setGridColModelStr(String gridColModelStr) {
		this.gridColModelStr = gridColModelStr;
	}

	public List<Map<String, Object>> getAllSystemPeriodStatuss() {
		return allSystemPeriodStatuss;
	}

	public void setAllSystemPeriodStatuss(List<Map<String, Object>> allSystemPeriodStatuss) {
		this.allSystemPeriodStatuss = allSystemPeriodStatuss;
	}

	public String getGroupHeaderStr() {
		return groupHeaderStr;
	}

	public void setGroupHeaderStr(String groupHeaderStr) {
		this.groupHeaderStr = groupHeaderStr;
	}
}

