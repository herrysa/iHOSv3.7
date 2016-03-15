package com.huge.ihos.system.reportManager.report.webapp.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.exinterface.ProxyGetGZResource;
import com.huge.ihos.system.exinterface.ProxyGetKQResource;
import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportPlan;
import com.huge.ihos.system.reportManager.report.model.ReportPlanFilter;
import com.huge.ihos.system.reportManager.report.model.ReportPlanItem;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.ihos.system.reportManager.report.service.ReportDefineManager;
import com.huge.ihos.system.reportManager.report.service.ReportPlanFilterManager;
import com.huge.ihos.system.reportManager.report.service.ReportPlanItemManager;
import com.huge.ihos.system.reportManager.report.service.ReportPlanManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;
public class ReportPlanPagedAction extends JqGridBaseAction implements Preparable {
	
	private static final long serialVersionUID = 5836800718638516691L;
	private ReportPlanManager reportPlanManager;
	private List<ReportPlan> reportPlans;
	private ReportPlan reportPlan;
	private String planId;
	private String reportTitle;//报表标题

	public ReportPlanManager getReportPlanManager() {
		return reportPlanManager;
	}

	public void setReportPlanManager(ReportPlanManager reportPlanManager) {
		this.reportPlanManager = reportPlanManager;
	}

	public List<ReportPlan> getReportPlans() {
		return reportPlans;
	}

	public void setReportPlans(List<ReportPlan> reportPlans) {
		this.reportPlans = reportPlans;
	}

	public ReportPlan getReportPlan() {
		return reportPlan;
	}

	public void setReportPlan(ReportPlan reportPlan) {
		this.reportPlan = reportPlan;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
        this.planId = planId;
    }

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}
	private List<Map<String, Object>>  reportPlanContents;
	private String columns;
	//报表数据查询
	public String reportPlanGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			reportDefine = reportDefineManager.get(defineId);
			reportDefine = reportDefine.clone();
			String groupIds = request.getParameter("groupIds");
			if(OtherUtil.measureNotNull(groupIds)){
				reportDefine.setGroupIds(groupIds);
			}
			reportPlanContents = reportPlanManager.getReportPlanGridData(
					columns, converFilterToSql(request,reportDefine), reportDefine,
					convertGroupFilterToSql(request),initReportTypeList(reportDefine.getSubSystemCode()));
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}
	//反转数据
	public String reportReverseGridList(){
		try{
			HttpServletRequest request = this.getRequest();
			reportDefine = reportDefineManager.get(defineId);
			reportPlanContents = reportPlanManager.getReportPlanReverseGridData(
					rpReverseColumnInfo(typeId,reportPlanItemsStr), converFilterToSql(request,reportDefine), reportDefine);
		}catch(Exception e){
			log.error("reportReverseGridListError:"+e.getMessage());
		}
		return SUCCESS;
	}
	/* 需要反转的列表 */
	private List<ReportItem> rpReverseColumnInfo(String typeId,
			String reportPlanItemsStr) {
		String itemcodes = "";
		Map<String, ReportPlanItem> planItemMap = new HashMap<String, ReportPlanItem>();
		if (OtherUtil.measureNotNull(reportPlanItemsStr)) {
			JSONObject jsonObject = JSONObject.fromObject(reportPlanItemsStr);
			if(OtherUtil.measureNotNull(jsonObject)&&!jsonObject.isEmpty()){
				Iterator iter = jsonObject.keys();
				while (iter.hasNext()) {
					String itemCode = (String) iter.next();
					String valueStr = jsonObject.get(itemCode).toString();
					JSONObject valueObject = JSONObject.fromObject(valueStr);
					String name = valueObject.get("name").toString();
					String isThousandSeparat = valueObject.get("isThousandSeparat").toString();
					String fontIndex = valueObject.get("fontIndex").toString();
					String headerFontIndex = valueObject.get("headerFontIndex").toString();
					String headerTextColor = valueObject.get("headerTextColor").toString();
					Integer sn = valueObject.get("sn") == null ? 0:Integer.parseInt(valueObject.get("sn").toString());
					itemcodes += itemCode + ",";
					ReportPlanItem item = new ReportPlanItem();
					item.setPlanId(planId);
					item.setItemCode(itemCode);
					item.setColName(name);
					item.setIsThousandSeparat(isThousandSeparat);
					item.setFontIndex(fontIndex);
					item.setHeaderFontIndex(headerFontIndex);
					item.setHeaderTextColor(headerTextColor);
					item.setColSn(sn);
					planItemMap.put(itemCode, item);
				}
				if(OtherUtil.measureNotNull(itemcodes)){
					itemcodes = itemcodes.substring(0, itemcodes.length() - 1);
				}
			}
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		reportItems = new ArrayList<ReportItem>();
		String subSystemCode = reportDefine.getSubSystemCode();
		if("KQ".equals(subSystemCode)){
			ProxyGetKQResource proxyGetKQResource = new ProxyGetKQResource();
			filters.add(new PropertyFilter("OAI_sn", ""));
			filters.add(new PropertyFilter("EQS_kqType.kqTypeId", typeId));
			filters.add(new PropertyFilter("INS_itemCode", itemcodes));
			reportItems = proxyGetKQResource.getReportItems(filters);
		}else if("GZ".equals(subSystemCode)){
			ProxyGetGZResource proxyGetGZResource = new ProxyGetGZResource();
			filters.add(new PropertyFilter("OAI_sn", ""));
			filters.add(new PropertyFilter("EQS_gzType.gzTypeId", typeId));
			filters.add(new PropertyFilter("INS_itemCode", itemcodes));
			reportItems = proxyGetGZResource.getReportItems(filters);
		}
		if (OtherUtil.measureNotNull(reportItems) && !reportItems.isEmpty()) {
			for (ReportItem reportItem : reportItems) {
				String itemCode = reportItem.getItemCode();
				if (planItemMap.containsKey(itemCode)) {
					ReportPlanItem planItemTemp = planItemMap.get(itemCode);
					reportItem.setColName(planItemTemp.getColName());
					reportItem.setIsThousandSeparat(planItemTemp.getIsThousandSeparat());
					reportItem.setFontIndex(planItemTemp.getFontIndex());
					reportItem.setHeaderFontIndex(planItemTemp.getHeaderFontIndex());
					reportItem.setHeaderTextColor(planItemTemp.getHeaderTextColor());
					reportItem.setSn(planItemTemp.getColSn());
				} else {
					reportItem.setColName(reportItem.getItemName());
				}
			}
		}
		if (OtherUtil.measureNotNull(reportItems) && !reportItems.isEmpty()) {
			List<ReportItem> reportItemsTemp = new ArrayList<ReportItem>();
			for (ReportItem reportItemTemp : reportItems) {
				String itemType = reportItemTemp.getItemType();
				if ("1".equals(itemType) || "2".equals(itemType)) {
				} else {
					reportItemsTemp.add(reportItemTemp);
				}
			}
			this.reportItems = reportItemsTemp;
		}
		// 根据Collections.sort重载方法来实现
		Collections.sort(reportItems, new Comparator<ReportItem>() {
			@Override
			public int compare(ReportItem b1, ReportItem b2) {
				int bsn1 = b1.getSn();
				int bsn2 = b2.getSn();
				return bsn1 - bsn2;
			}
		});
		return reportItems;
	}
	/* 过滤条件 */
	private List<String> converFilterToSql(HttpServletRequest request,ReportDefine reportDefine) {
		List<String> sqlFilters = new ArrayList<String>();
		String periodTime = request.getParameter("periodTime");
		String checkYear = request.getParameter("checkYear");
		String checkNumber = request.getParameter("checkNumber");
		String fromPeriodTime = request.getParameter("fromPeriodTime");
		String toPeriodTime = request.getParameter("toPeriodTime");
		String fromCheckYear = request.getParameter("fromCheckYear");
		String fromCheckNumber = request.getParameter("fromCheckNumber");
		String toCheckYear = request.getParameter("toCheckYear");
		String toCheckNumber = request.getParameter("toCheckNumber");
		// String orgCode = request.getParameter("orgCode");
		String branchCode = request.getParameter("branchCode");
		String deptIds = request.getParameter("deptIds");
		String deptTypes = request.getParameter("deptTypes");
		String empTypes = request.getParameter("empTypes");
//		String empTypesa12 = request.getParameter("empTypesa12");
		String personName = request.getParameter("personName");
		String personCode = request.getParameter("personCode");
		String typeId = request.getParameter("typeId");
		String typeIds = request.getParameter("typeIds");
		String personId = request.getParameter("personId");
		String includeUnCheck = request.getParameter("includeUnCheck");
		String sqlString = "";
		if (OtherUtil.measureNotNull(periodTime)) {
			sqlString = "rp.period = '" + periodTime + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(fromPeriodTime)) {
			sqlString = "rp.period >= '" + fromPeriodTime + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(toPeriodTime)) {
			sqlString = "rp.period <= '" + toPeriodTime + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(deptIds)) {
			sqlString = "rp.deptId in ("
					+ OtherUtil.splitStrAddQuotes(deptIds, ",") + ")";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(empTypes)) {
			sqlString = "rp.empType in ("
					+ OtherUtil.splitStrAddQuotes(empTypes, ",") + ")";
			sqlFilters.add(sqlString);
		}
//		if (OtherUtil.measureNotNull(empTypesa12)) {
//			sqlString = "rp.empType in ("
//					+ OtherUtil.splitStrAddQuotes(empTypesa12, ",") + ")";
//			sqlFilters.add(sqlString);
//		}
		if(OtherUtil.measureNotNull(branchCode)) {
			sqlString = "rp.branchCode in ("
				+ OtherUtil.splitStrAddQuotes(branchCode, ",") + ")";
			sqlFilters.add(sqlString);
		}
		if(OtherUtil.measureNotNull(deptTypes)) {
			sqlString = "rp.deptType in ("
					+ OtherUtil.splitStrAddQuotes(deptTypes, ",") + ")";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(personName)) {
			personName = personName.replace("*", "%");
			sqlString = "rp.personName" + " like '" + personName + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(personCode)) {
			personCode = personCode.replace("*", "%");
			sqlString = "rp.personCode like '" + personCode + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(checkYear)) {
			sqlString = "rp.period like '" + checkYear + "%'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(checkNumber)) {
			sqlString = "rp.issueNumber = '" + checkNumber + "'";
			sqlFilters.add(sqlString);
		}
		if(OtherUtil.measureNotNull(fromCheckYear)&&OtherUtil.measureNotNull(fromCheckNumber)){
			sqlString = "(rp.period >= '" + fromCheckYear + "01' and rp.issueNumber >= '"+fromCheckNumber+"')";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(fromCheckYear)){
			sqlString = "rp.period >= '" + fromCheckYear + "01'";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(fromCheckNumber)){
			sqlString = "rp.issueNumber >= '" + fromCheckNumber + "'";
			sqlFilters.add(sqlString);
		}
		
		if(OtherUtil.measureNotNull(toCheckYear)&&OtherUtil.measureNotNull(toCheckNumber)){
			sqlString = "(rp.period <= '" + toCheckYear + "12' and rp.issueNumber <= '"+toCheckNumber+"')";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(toCheckYear)){
			sqlString = "rp.period <= '" + toCheckYear + "12'";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(toCheckNumber)){
			sqlString = "rp.issueNumber <= '" + toCheckNumber + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(personId)) {
			sqlString = "rp.personId = '" + personId + "'";
			sqlFilters.add(sqlString);
		}
		if(OtherUtil.measureNull(includeUnCheck)){
			sqlString = "rp.status <> '0'";
			String unCheckStatus = reportDefine.getUnCheckStatus();
			if(OtherUtil.measureNotNull(unCheckStatus)){
				unCheckStatus = OtherUtil.splitStrAddQuotes(unCheckStatus, ",");
				sqlString =  "rp.status NOT IN ("+unCheckStatus+")";
			}
			sqlFilters.add(sqlString);
		}
		if(OtherUtil.measureNotNull(reportDefine.getCurType())){
			if (OtherUtil.measureNotNull(typeIds)) {
				sqlString = "rp."+ reportDefine.getTableTypeField() +" in ("+ OtherUtil.splitStrAddQuotes(typeIds, ",") + ")";
				sqlFilters.add(sqlString);
			}
		}else{
			sqlString = "rp."+ reportDefine.getTableTypeField() +" = '" + typeId + "'";
			sqlFilters.add(sqlString);
		}
		return sqlFilters;
	}
	/* 分组过滤条件 */
	private Map<String, String> convertGroupFilterToSql(
			HttpServletRequest request) {
		String deptType = request.getParameter("deptType");
		String deptLevelFrom = request.getParameter("deptLevelFrom");
		String deptLevelTo = request.getParameter("deptLevelTo");
		String deptDetailIds = request.getParameter("deptDetailIds");
		String empTypesa12 = request.getParameter("empTypesa12");
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("deptType", deptType);
		filterMap.put("deptLevelFrom", deptLevelFrom);
		filterMap.put("deptLevelTo", deptLevelTo);
		filterMap.put("deptDetailIds", deptDetailIds);
		filterMap.put("empTypes", empTypesa12);
		return filterMap;
	}
	public String save(){
		String error = isValid();
		if (!error.equalsIgnoreCase(SUCCESS)) {
			gridOperationMessage = error;
			return ajaxForwardError(gridOperationMessage);
		}
		try {
			reportPlanManager.save(reportPlan);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "reportPlan.added" : "reportPlan.updated";
		return ajaxForward(this.getText(key));
	}
    public String edit() {
        if (planId != null) {
        	reportPlan = reportPlanManager.get(planId);
        	this.setEntityIsNew(false);
        } else {
        	reportPlan = new ReportPlan();
        	this.setEntityIsNew(true);
        }
        return SUCCESS;
    }
	public String reportPlanGridEdit() {
		try {
			if (oper.equals("del")) {
				reportPlan = reportPlanManager.get(planId);
				Boolean sysField = reportPlan.getSysField();
				if (sysField) {
					return ajaxForward(false, "系统方案不能删除！", false);
				}
				User sessionUser = getSessionUser();
				String userId = sessionUser.getId().toString();
				if (!userId.equals(reportPlan.getUserId())) {
					return ajaxForward(false, "只能删除自己创建的方案！", false);
				}
				reportPlanManager.removeReportPlan(planId);
				gridOperationMessage = "方案删除成功。";
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkReportPlanGridEdit Error", e);
			if(log.isDebugEnabled())
				gridOperationMessage = e.getMessage()+e.getLocalizedMessage()+e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (reportPlan == null) {
			return "Invalid reportPlan Data";
		}

		return SUCCESS;
	}
	private String defineId;
	private ReportDefine reportDefine;
	private ReportDefineManager reportDefineManager;
	private String subSystemPrefix;//子系统前缀
	private String curType;//当前类别
	private String operPersonName;//操作人
	private String curPeriod;//当前期间
	private String gzStubsDeptNumber;//工资条选择部门个数
	private String herpType;//HERP版本
	//点击报表页面
	public String reportPlanList(){
		if(OtherUtil.measureNotNull(defineId)){
			try{
				reportDefine = reportDefineManager.get(defineId);
				reportSubSystemInit(reportDefine);//报表子系统初始化
				Person operPerson = this.getSessionUser().getPerson();
				operPersonName = operPerson.getName();
				curPeriod = this.getLoginPeriod();
				getALLInitData(curType,reportDefine);
				herpType = ContextUtil.herpType;
				gzStubsDeptNumber = this.getGlobalParamByKey("gzStubsDeptNumber");
				if(OtherUtil.measureNull(gzStubsDeptNumber)){
					gzStubsDeptNumber = "10";
				}
			}catch(Exception e){
				log.error("reportPlanListError:"+e.getMessage());
			}
		}else{
			log.error("defineId未定义");
		}
		return SUCCESS;
	}
	private List<ReportType> reportTypes;
	private String reportTypeTreeNodeStr;
	private ReportType reportType;
	//报表子系统初始化
	private void reportSubSystemInit(ReportDefine reportDefine){
		curType = "";
		String subSystemCode = reportDefine.getSubSystemCode();
		String curTypeTemp = reportDefine.getCurType();
		List<String> typeIdList = new ArrayList<String>();
		reportTypes = initReportTypeList(subSystemCode);
		JSONArray jsonArray = new JSONArray();
		if(OtherUtil.measureNull(reportTypes)){
			reportTypes = new ArrayList<ReportType>();
		}else{
			for(ReportType rType:reportTypes){
				typeIdList.add(rType.getTypeId());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", rType.getTypeId());
				jsonObject.put("name", rType.getTypeName());
				jsonArray.add(jsonObject);
			}
		}
		reportTypeTreeNodeStr = jsonArray.toString();
		if(OtherUtil.measureNotNull(curTypeTemp)){
			curType = curTypeTemp;
			if(!typeIdList.contains(curType)){
				reportType = new ReportType();
				reportType.setTypeId(curType);
				reportType.setTypeName("系统");
				reportType.setIssueType("月");
				reportTypes.add(reportType);
			}
		}else{
			if(typeIdList.size() > 0){
				curType = typeIdList.get(0);
				reportType = reportTypes.get(0);
				if(OtherUtil.measureNotNull(typeId)){
					for(ReportType rType:reportTypes){
						if(rType.getTypeId().equals(typeId)){
							reportType = rType;
							curType = typeId;
						}
					}
				}
			}
		}
		
	}
	//获取ReportType
	private List<ReportType> initReportTypeList(String subSystemCode){
		List<ReportType> reportTypeList = null;
		if("KQ".equals(subSystemCode)){
			setSubSystemPrefix("考勤");
			ProxyGetKQResource proxyGetKQResource = new ProxyGetKQResource();
			reportTypeList = proxyGetKQResource.allKqTypes(true);
		}else if("GZ".equals(subSystemCode)){
			setSubSystemPrefix("工资");
			ProxyGetGZResource proxyGetGZResource = new ProxyGetGZResource();
			reportTypeList = proxyGetGZResource.allGzTypes(true);
		}
		return reportTypeList;
	}
	// 对页面的各种查询框赋值
	private String planItemsJsonStr;
	private String reportPlanSysStr;// 系统方案

	private void getALLInitData(String typeId,ReportDefine reportDefine) {
		String subSystemCode = reportDefine.getSubSystemCode();
		// 首先获取公共的方案列表1.获取当前用户
		User sessionUser = getSessionUser();
		String userId = sessionUser.getId().toString();
		List<User> sameRoleUsers = userManager.getUsersByRoles(sessionUser.getRoles());
		List<User> sameDeptUsers = userManager.getUsersByDept(sessionUser.getPerson().getDepartment());
		List<String> sameRoleUserIds = new ArrayList<String>();
		List<String> sameDeptUserIds = new ArrayList<String>();
		if (OtherUtil.measureNotNull(sameRoleUsers) && !sameRoleUsers.isEmpty()) {
			for (User userTemp : sameRoleUsers) {
				sameRoleUserIds.add(userTemp.getId().toString());
			}
		}
		if (OtherUtil.measureNotNull(sameDeptUsers) && !sameDeptUsers.isEmpty()) {
			for (User userTemp : sameDeptUsers) {
				sameDeptUserIds.add(userTemp.getId().toString());
			}
		}
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_defineId", defineId));
		filters.add(new PropertyFilter("EQS_typeId", typeId));
		filters.add(new PropertyFilter("EQB_sysField", "0"));
		List<ReportPlan> reportPlanList = reportPlanManager.getByFilters(filters);
		reportPlans = new ArrayList<ReportPlan>();
		if (OtherUtil.measureNotNull(reportPlanList)&& !reportPlanList.isEmpty()) {
			for (ReportPlan rpTemp : reportPlanList) {
				String userIdTemp = rpTemp.getUserId();
				/* 同一Id 或者公有或者统一角色或者统一部门 */
				if (userIdTemp.equals(userId)
						|| "1".equals(rpTemp.getToPublic())
						|| "1".equals(rpTemp.getToDepartment())
						&& sameDeptUserIds.contains(userIdTemp)
						|| "1".equals(rpTemp.getToRole())
						&& sameRoleUserIds.contains(userIdTemp)) {
					reportPlans.add(rpTemp);
				}
			}
		}
		filters.clear();
		filters.add(new PropertyFilter("EQS_defineId", defineId));
		filters.add(new PropertyFilter("EQB_sysField", "1"));
		List<ReportPlan> reportSysPlans = reportPlanManager.getByFilters(filters);
		if (OtherUtil.measureNotNull(reportSysPlans)&& !reportSysPlans.isEmpty()) {
			JSONObject jsonObject = new JSONObject();
			for (ReportPlan rpTemp : reportSysPlans) {
				jsonObject.put(rpTemp.getPlanName(), rpTemp.getPlanId());
				reportPlans.add(rpTemp);
			}
			reportPlanSysStr = jsonObject.toString();
		}
		filters.clear();
		reportItems = new ArrayList<ReportItem>();
		if("KQ".equals(subSystemCode)){
			ProxyGetKQResource proxyGetKQResource = new ProxyGetKQResource();
			filters.add(new PropertyFilter("OAI_sn", ""));
			filters.add(new PropertyFilter("EQS_kqType.kqTypeId", typeId));
			filters.add(new PropertyFilter("EQB_disabled", "0"));
			if("XT".equals(typeId)){
				filters.add(new PropertyFilter("INS_itemType", "0,3"));
			}
			reportItems = proxyGetKQResource.getReportItems(filters);
		}else if("GZ".equals(subSystemCode)){
			ProxyGetGZResource proxyGetGZResource = new ProxyGetGZResource();
			filters.add(new PropertyFilter("OAI_sn", ""));
			filters.add(new PropertyFilter("EQS_gzType.gzTypeId", typeId));
			filters.add(new PropertyFilter("EQB_disabled", "0"));
			if("XT".equals(typeId)){
				filters.add(new PropertyFilter("INS_itemType", "0,3"));
				filters.add(new PropertyFilter("EQB_statistics", "1"));
			}
			reportItems = proxyGetGZResource.getReportItems(filters);
		}
		planItemsJsonStr = JSONArray.fromObject(reportItems).toString();
		//反转可用期间
		//通过工资数据表取期间和发放次数
		String sqlString = "SELECT ";
		String reverseColumn = reportDefine.getReverseColumn();
		if(OtherUtil.measureNotNull(reverseColumn)){
			String[] reverseArr = reverseColumn.split(",");
			String sqlTemp = "";
			for(String columnTemp:reverseArr){
				sqlTemp += columnTemp + ",";
			}
			sqlTemp = OtherUtil.subStrEnd(sqlTemp, ",");
			sqlString += sqlTemp;
			sqlString += " FROM " + reportDefine.getTableName();
			sqlString += " WHERE " + reportDefine.getTableTypeField() + "='"+typeId+"' ";
			sqlString += " GROUP BY " + sqlTemp +" ORDER BY " +sqlTemp;
			List<Map<String, Object>> mapList = reportPlanManager.getBySqlToMap(sqlString);
	    	if(OtherUtil.measureNotNull(mapList)&&!mapList.isEmpty()){
	    		JSONArray jsonArray = new JSONArray();
	    		for(Map<String, Object> mapTemp:mapList){
	    			JSONObject jsonObject = new JSONObject();
	    			jsonObject.put("checkperiod", mapTemp.get("period"));
	    			jsonObject.put("checkNumber", mapTemp.get("issueNumber"));
	    			jsonArray.add(jsonObject);
	    		}
	    		modelStatusStr = jsonArray.toString();
	    	}
		}
	}
	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}

	public ReportDefine getReportDefine() {
		return reportDefine;
	}

	public void setReportDefine(ReportDefine reportDefine) {
		this.reportDefine = reportDefine;
	}

	public void setReportDefineManager(ReportDefineManager reportDefineManager) {
		this.reportDefineManager = reportDefineManager;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getSubSystemPrefix() {
		return subSystemPrefix;
	}

	public void setSubSystemPrefix(String subSystemPrefix) {
		this.subSystemPrefix = subSystemPrefix;
	}

	public String getOperPersonName() {
		return operPersonName;
	}

	public void setOperPersonName(String operPersonName) {
		this.operPersonName = operPersonName;
	}

	public String getCurPeriod() {
		return curPeriod;
	}

	public void setCurPeriod(String curPeriod) {
		this.curPeriod = curPeriod;
	}

	public String getPlanItemsJsonStr() {
		return planItemsJsonStr;
	}

	public void setPlanItemsJsonStr(String planItemsJsonStr) {
		this.planItemsJsonStr = planItemsJsonStr;
	}

	public String getReportPlanSysStr() {
		return reportPlanSysStr;
	}

	public void setReportPlanSysStr(String reportPlanSysStr) {
		this.reportPlanSysStr = reportPlanSysStr;
	}
	private String reportPlanItemsStr;//报表项目列
	private String typeId;//类Id
	private List<ReportItem> reportItems;
	//取报表项目列
	public String reportPlanColumnInfo(){
		try{
			reportDefine = reportDefineManager.get(defineId);
			String groups = reportDefine.getGroups();
			String itemcodes = "";
			Map<String, ReportPlanItem> planItemMap = new HashMap<String, ReportPlanItem>();
			if (OtherUtil.measureNotNull(reportPlanItemsStr)) {
				JSONObject jsonObject = JSONObject.fromObject(reportPlanItemsStr);
				Iterator iter = jsonObject.keys();
				while (iter.hasNext()) {
					String itemCode = (String) iter.next();
					String valueStr = jsonObject.get(itemCode).toString();
					JSONObject valueObject = JSONObject.fromObject(valueStr);
					String name = valueObject.get("name") == null ? "": valueObject.get("name").toString();
					String isThousandSeparat = valueObject.get("isThousandSeparat") == null ? "": valueObject.get("isThousandSeparat").toString();
					String fontIndex = valueObject.get("fontIndex") == null ? "": valueObject.get("fontIndex").toString();
					String headerFontIndex = valueObject.get("headerFontIndex") == null ? "": valueObject.get("headerFontIndex").toString();
					String headerTextColor = valueObject.get("headerTextColor") == null ? "": valueObject.get("headerTextColor").toString();
					Integer sn = valueObject.get("sn") == null ? 0:Integer.parseInt(valueObject.get("sn").toString());
					itemcodes += itemCode + ",";
					ReportPlanItem item = new ReportPlanItem();
					item.setPlanId(planId);
					item.setItemCode(itemCode);
					item.setColName(name);
					item.setIsThousandSeparat(isThousandSeparat);
					item.setFontIndex(fontIndex);
					item.setHeaderFontIndex(headerFontIndex);
					item.setHeaderTextColor(headerTextColor);
					item.setColSn(sn);
					planItemMap.put(itemCode, item);
				}
				if(OtherUtil.measureNotNull(itemcodes)){
					itemcodes = OtherUtil.subStrEnd(itemcodes, ",");
				}
			}
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			reportItems = new ArrayList<ReportItem>();
			String subSystemCode = reportDefine.getSubSystemCode();
			if("KQ".equals(subSystemCode)){
				ProxyGetKQResource proxyGetKQResource = new ProxyGetKQResource();
				filters.add(new PropertyFilter("OAI_sn", ""));
				filters.add(new PropertyFilter("EQS_kqType.kqTypeId", typeId));
				filters.add(new PropertyFilter("INS_itemCode", itemcodes));
				reportItems = proxyGetKQResource.getReportItems(filters);
			}else if("GZ".equals(subSystemCode)){
				ProxyGetGZResource proxyGetGZResource = new ProxyGetGZResource();
				filters.add(new PropertyFilter("OAI_sn", ""));
				filters.add(new PropertyFilter("EQS_gzType.gzTypeId", typeId));
				filters.add(new PropertyFilter("INS_itemCode", itemcodes));
				reportItems = proxyGetGZResource.getReportItems(filters);
			}
			if (OtherUtil.measureNotNull(reportItems) && !reportItems.isEmpty()) {
				for (ReportItem reportItem : reportItems) {
					String itemCode = reportItem.getItemCode();
					if (planItemMap.containsKey(itemCode)) {
						ReportPlanItem planItemTemp = planItemMap.get(itemCode);
						reportItem.setColName(planItemTemp.getColName());
						reportItem.setIsThousandSeparat(planItemTemp.getIsThousandSeparat());
						reportItem.setFontIndex(planItemTemp.getFontIndex());
						reportItem.setHeaderFontIndex(planItemTemp.getHeaderFontIndex());
						reportItem.setHeaderTextColor(planItemTemp.getHeaderTextColor());
						reportItem.setSn(planItemTemp.getColSn());
					} else {
						reportItem.setColName(reportItem.getItemName());
					}
				}
			}
			if(OtherUtil.measureNotNull(reportDefine.getCurType())){
				if (OtherUtil.measureNotNull(reportItems) && !reportItems.isEmpty()) {
					List<ReportItem> reportItemsTemp = new ArrayList<ReportItem>();
					for (ReportItem reportItemTemp : reportItems) {
						String itemType = reportItemTemp.getItemType();
						if ("0".equals(itemType) || "3".equals(itemType)) {
							reportItemsTemp.add(reportItemTemp);
						}
					}
					reportItems = reportItemsTemp;
				}
			}
			String groupSelectItems = reportDefine.getGroupSelectItems();
			if (OtherUtil.measureNotNull(groups)&&OtherUtil.measureNull(groupSelectItems)) {
				String[] groupColumns = groups.split(",");
				List<String> groupColumnList = Arrays.asList(groupColumns);
				if (OtherUtil.measureNotNull(reportItems) && !reportItems.isEmpty()) {
					List<ReportItem> reportItemsTemp = new ArrayList<ReportItem>();
					boolean countFlag = true;
					for (ReportItem reportItemTemp : reportItems) {
						String itemCode = reportItemTemp.getItemCode();
						String itemType = reportItemTemp.getItemType();
						if ("1".equals(itemType) || "2".equals(itemType)) {
							if (groupColumnList.contains(itemCode)) {
								reportItemsTemp.add(reportItemTemp);
							}
						} else {
							reportItemsTemp.add(reportItemTemp);
						}
					}
					ReportItem gzItemTemp = new ReportItem();
					gzItemTemp.setItemCode("count");
					gzItemTemp.setItemName("人数");
					gzItemTemp.setItemType("3");
					gzItemTemp.setColName("人数");
					gzItemTemp.setSn(0);
					reportItemsTemp.add(gzItemTemp);
					reportItems = reportItemsTemp;
				} else {
					reportItems = new ArrayList<ReportItem>();
					ReportItem reportItemTemp = new ReportItem();
					reportItemTemp.setItemCode("count");
					reportItemTemp.setItemName("人数");
					reportItemTemp.setItemType("3");
					reportItemTemp.setColName("人数");
					reportItemTemp.setSn(0);
					reportItems.add(reportItemTemp);
				}
			}
			if(OtherUtil.measureNotNull(groupSelectItems)){
				String[] groupSelectItemArr = groupSelectItems.split(";");
				int addLength = groupSelectItemArr.length + 1;
				for(ReportItem rpTempItem:reportItems){
					rpTempItem.setSn(rpTempItem.getSn() + addLength);
				}
				int index = 0;
		    	for(String groupSelectItemTemp:groupSelectItemArr){
		    		String[] groupItemTemp = groupSelectItemTemp.split(":");
		    		if(groupItemTemp.length == 2){
		    			ReportItem reportItemTemp = new ReportItem();
		    			if("period".equals(groupItemTemp[0])){
		    				reportItemTemp.setSumColumn(true);
		    			}
						reportItemTemp.setItemCode(groupItemTemp[0]);
						reportItemTemp.setItemName(groupItemTemp[1]);
						reportItemTemp.setItemType("1");
						reportItemTemp.setColName(groupItemTemp[1]);
						reportItemTemp.setSn(index++);
						reportItems.add(reportItemTemp);
		    		}
		    	}
		    	ReportItem reportItemTemp = new ReportItem();
				reportItemTemp.setItemCode("count");
				reportItemTemp.setItemName("人数");
				reportItemTemp.setItemType("3");
				reportItemTemp.setColName("人数");
				reportItemTemp.setSn(index++);
				reportItems.add(reportItemTemp);
			}
	    	
			// 根据Collections.sort重载方法来实现
			Collections.sort(reportItems, new Comparator<ReportItem>() {
				@Override
				public int compare(ReportItem b1, ReportItem b2) {
					int bsn1 = b1.getSn();
					int bsn2 = b2.getSn();
					return bsn1 - bsn2;
				}
			});
		}catch(Exception e){
			log.error("reportPlanColumnInfoError:"+e.getMessage());
		}
		return SUCCESS;
	}

	public String getReportPlanItemsStr() {
		return reportPlanItemsStr;
	}

	public void setReportPlanItemsStr(String reportPlanItemsStr) {
		this.reportPlanItemsStr = reportPlanItemsStr;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List<ReportItem> getReportItems() {
		return reportItems;
	}

	public void setReportItems(List<ReportItem> reportItems) {
		this.reportItems = reportItems;
	}

	public List<Map<String, Object>> getReportPlanContents() {
		return reportPlanContents;
	}

	public void setReportPlanContents(List<Map<String, Object>> reportPlanContents) {
		this.reportPlanContents = reportPlanContents;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
	private ReportPlanFilterManager reportPlanFilterManager;
	private ReportPlanItemManager reportPlanItemManager;
	private List<ReportPlanFilter> reportPlanFilters;
	private List<ReportPlanItem> reportPlanItems;
	//获取选中的方案的详细信息
	public String getSelectedReportPlan(){
		// 获取planid
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_planId", planId));
		reportPlans = reportPlanManager.getByFilters(filters);
		reportPlanFilters = reportPlanFilterManager.getByFilters(filters);
		filters.add(new PropertyFilter("OAI_colSn", ""));
		reportPlanItems = reportPlanItemManager.getByFilters(filters);
		return SUCCESS;
	}

	public void setReportPlanFilterManager(ReportPlanFilterManager reportPlanFilterManager) {
		this.reportPlanFilterManager = reportPlanFilterManager;
	}

	public List<ReportPlanFilter> getReportPlanFilters() {
		return reportPlanFilters;
	}

	public void setReportPlanFilters(List<ReportPlanFilter> reportPlanFilters) {
		this.reportPlanFilters = reportPlanFilters;
	}

	public void setReportPlanItemManager(ReportPlanItemManager reportPlanItemManager) {
		this.reportPlanItemManager = reportPlanItemManager;
	}

	public List<ReportPlanItem> getReportPlanItems() {
		return reportPlanItems;
	}

	public void setReportPlanItems(List<ReportPlanItem> reportPlanItems) {
		this.reportPlanItems = reportPlanItems;
	}
	//类别切换
	public String reportPlanTypeChange(){
		reportDefine = reportDefineManager.get(defineId);
		getALLInitData(typeId,reportDefine);
		return SUCCESS;
	}
	private String typeJson;
	private String reportPlanFilterStr;//过滤条件
	private String reportCustomLayout;//方案格式
	private String firstPeriod;//年度第一个期间
	private String modelStatusStr;//可用期间
	//帐表显示
	public String reportPlanShow(){
		try {
			if(OtherUtil.measureNull(planId)){
				planId = "";
			}
			reportDefine = reportDefineManager.get(defineId);
			Person operPerson = this.getSessionUser().getPerson();
			operPersonName = operPerson.getName();
			curPeriod = this.getLoginPeriod();
			this.herpType = ContextUtil.herpType;
			firstPeriod = DateUtil.getFirstPeriod(curPeriod);
			HttpServletRequest request = this.getRequest();
			String tabOpenType = request.getParameter("tabOpenType");
			String savePlan = request.getParameter("savePlan");
			if (OtherUtil.measureNotNull(tabOpenType)) {
				if ("1".equals(savePlan)) {
					String planName = request.getParameter("planName");
					String toPublic = request.getParameter("toPublic");
					String toDepartment = request.getParameter("toRole");
					String toRole = request.getParameter("toRole");
					reportPlan = new ReportPlan();
					reportPlan.setPlanName(planName);
					reportPlan.setToPublic(toPublic);
					reportPlan.setToDepartment(toDepartment);
					reportPlan.setToRole(toRole);
					reportPlan.setReportTitle(reportTitle);
					reportPlan.setDefineId(defineId);
					reportPlan.setTypeId(typeId);
					reportPlan.setUserId(getSessionUser().getId().toString());
					reportPlan.setCustomLayout(reportCustomLayout);
					reportPlan = reportPlanManager.saveReportPlan(reportPlan,reportPlanItemsStr,reportPlanFilterStr);
					planId = reportPlan.getPlanId();
				}
				this.getSession().setAttribute(
						defineId + "reportPlanItemsStr", reportPlanItemsStr);
				this.getSession().setAttribute(
						defineId + "reportPlanFilterStr", reportPlanFilterStr);
				
				this.getSession().setAttribute(
						defineId + "reportTitle", reportTitle);
				this.getSession().setAttribute(
						defineId + "planId", planId);
				this.getSession().setAttribute(
						defineId + "reportCustomLayout", reportCustomLayout);
				this.getSession().setAttribute(
						defineId + "subSystemPrefix", subSystemPrefix);
				this.getSession().setAttribute(
						defineId + "reportTypeTreeNodeStr", reportTypeTreeNodeStr);
				this.getSession().setAttribute(
						defineId + "gzStubsDeptNumber", gzStubsDeptNumber);
				this.getSession().setAttribute(
						defineId + "modelStatusStr", modelStatusStr);
//				this.getSession().setAttribute(
//						defineId + "menuId", menuId);
				this.getSession().setAttribute(
						defineId + "width", width);
				this.getSession().setAttribute(
						defineId + "height", height);
				this.getSession().setAttribute(
						defineId + "typeId", typeId);
				this.getSession().setAttribute(
						defineId + "typeJson", typeJson);
			} else {
				Object reportPlanItemsStrTemp = this.getSession().getAttribute(
						defineId + "reportPlanItemsStr");
				Object reportPlanFilterStrTemp = this.getSession().getAttribute(
						defineId + "reportPlanFilterStr");
				
				Object reportTitleTemp = this.getSession().getAttribute(
						defineId + "reportTitle");
				Object planIdTemp = this.getSession().getAttribute(
						defineId + "planId");
				Object reportCustomLayoutTemp = this.getSession().getAttribute(
						defineId + "reportCustomLayout");
				Object subSystemPrefixTemp = this.getSession().getAttribute(
						defineId + "subSystemPrefix");
				Object reportTypeTreeNodeStrTemp = this.getSession().getAttribute(
						defineId + "reportTypeTreeNodeStr");
				Object gzStubsDeptNumberTemp = this.getSession().getAttribute(
						defineId + "gzStubsDeptNumber");
				Object modelStatusStrTemp = this.getSession().getAttribute(
						defineId + "modelStatusStr");
				Object widthTemp = this.getSession().getAttribute(
						defineId + "width");
				Object heightTemp = this.getSession().getAttribute(
						defineId + "height");
				Object typeIdTemp = this.getSession().getAttribute(
						defineId + "typeId");
				Object typeJsonTemp = this.getSession().getAttribute(
						defineId + "typeJson");
				if (OtherUtil.measureNotNull(reportPlanItemsStrTemp)) {
					reportPlanItemsStr = reportPlanItemsStrTemp.toString();
				}
				if (OtherUtil.measureNotNull(reportPlanFilterStrTemp)) {
					reportPlanFilterStr = reportPlanFilterStrTemp.toString();
				}
				
				if (OtherUtil.measureNotNull(reportTitleTemp)) {
					reportTitle = reportTitleTemp.toString();
				}
				if (OtherUtil.measureNotNull(planIdTemp)) {
					planId = planIdTemp.toString();
				}
				if (OtherUtil.measureNotNull(reportCustomLayoutTemp)) {
					reportCustomLayout = reportCustomLayoutTemp.toString();
				}
				if (OtherUtil.measureNotNull(subSystemPrefixTemp)) {
					subSystemPrefix = subSystemPrefixTemp.toString();
				}
				if (OtherUtil.measureNotNull(reportTypeTreeNodeStrTemp)) {
					reportTypeTreeNodeStr = reportTypeTreeNodeStrTemp.toString();
				}
				if (OtherUtil.measureNotNull(gzStubsDeptNumberTemp)) {
					gzStubsDeptNumber = gzStubsDeptNumberTemp.toString();
				}
				if (OtherUtil.measureNotNull(modelStatusStrTemp)) {
					modelStatusStr = modelStatusStrTemp.toString();
				}
				if (OtherUtil.measureNotNull(widthTemp)) {
					width = widthTemp.toString();
				}
				if (OtherUtil.measureNotNull(heightTemp)) {
					height = heightTemp.toString();
				}
				if (OtherUtil.measureNotNull(typeIdTemp)) {
					typeId = typeIdTemp.toString();
				}
				if (OtherUtil.measureNotNull(typeJsonTemp)) {
					typeJson = typeJsonTemp.toString();
				}
			}
		} catch (Exception e) {
			log.error("reportPlanShowError:" + e.getMessage());
		}
		return SUCCESS;
	}

	public String getTypeJson() {
		return typeJson;
	}

	public void setTypeJson(String typeJson) {
		this.typeJson = typeJson;
	}

	public String getReportPlanFilterStr() {
		return reportPlanFilterStr;
	}

	public void setReportPlanFilterStr(String reportPlanFilterStr) {
		this.reportPlanFilterStr = reportPlanFilterStr;
	}

	public String getReportCustomLayout() {
		return reportCustomLayout;
	}

	public void setReportCustomLayout(String reportCustomLayout) {
		this.reportCustomLayout = reportCustomLayout;
	}

	public String getFirstPeriod() {
		return firstPeriod;
	}

	public void setFirstPeriod(String firstPeriod) {
		this.firstPeriod = firstPeriod;
	}
	private String width;
	private String height;
	//保存格式
	public String saveReportPlanCustomLayout(){
		try {
			reportPlan = reportPlanManager.get(planId);
			reportPlan.setCustomLayout(reportCustomLayout);
			reportPlanManager.save(reportPlan);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward(true, "保存成功。", false);
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	//高级设置
	public String reportPlanSet(){
		reportDefine = reportDefineManager.get(defineId);
		return SUCCESS;
	}

	public List<ReportType> getReportTypes() {
		return reportTypes;
	}

	public void setReportTypes(List<ReportType> reportTypes) {
		this.reportTypes = reportTypes;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public String getReportTypeTreeNodeStr() {
		return reportTypeTreeNodeStr;
	}

	public void setReportTypeTreeNodeStr(String reportTypeTreeNodeStr) {
		this.reportTypeTreeNodeStr = reportTypeTreeNodeStr;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getModelStatusStr() {
		return modelStatusStr;
	}

	public void setModelStatusStr(String modelStatusStr) {
		this.modelStatusStr = modelStatusStr;
	}

	public String getGzStubsDeptNumber() {
		return gzStubsDeptNumber;
	}

	public void setGzStubsDeptNumber(String gzStubsDeptNumber) {
		this.gzStubsDeptNumber = gzStubsDeptNumber;
	}

	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}
	
}

