package com.huge.ihos.gz.gzAccount.webapp.action;

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

import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.gz.gzAccount.model.GzAccountBean;
import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlan;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanFilter;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanItem;
import com.huge.ihos.gz.gzAccount.service.GzAccountDefineManager;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanFilterManager;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanItemManager;
import com.huge.ihos.gz.gzAccount.service.GzAccountPlanManager;
import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.ihos.gz.gzItem.service.GzItemManager;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.service.GzTypeManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class GzAccountPlanPagedAction extends JqGridBaseAction implements
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8589406102786049335L;
	private GzAccountDefineManager gzAccountDefineManager;
	private GzAccountPlanManager gzAccountPlanManager;
	private List<GzAccountDefine> gzAccountDefines;
	private List<GzAccountPlan> gzAccountPlans;
	private GzAccountPlan gzAccountPlan;
	private GzTypeManager gzTypeManager;
	private GzAccountPlanFilterManager gzAccountPlanFilterManager;
	private GzAccountPlanItemManager gzAccountPlanItemManager;
	private GzItemManager gzItemManager;
	private String planId;
	private String columns;
	private GzAccountBean accountBean;
	private String gzAccountCustomLayout;
	// 页面data
	private List<Map<String, Object>> gzAccountContents;
	// 向页面传输gzItems
	private List<GzAccountPlanItem> items;

	private List<GzItem> gzItems;

	private List<GzItem> gzItems3;

	public List<GzItem> getGzItems3() {
		return gzItems3;
	}

	public void setGzAccountDefineManager(
			GzAccountDefineManager gzAccountDefineManager) {
		this.gzAccountDefineManager = gzAccountDefineManager;
	}

	public void setGzItems3(List<GzItem> gzItems3) {
		this.gzItems3 = gzItems3;
	}

	public List<GzAccountDefine> getGzAccountDefines() {
		return gzAccountDefines;
	}

	public void setGzAccountDefines(List<GzAccountDefine> gzAccountDefines) {
		this.gzAccountDefines = gzAccountDefines;
	}

	public List<GzAccountPlanItem> getItems() {
		return items;
	}

	public void setItems(List<GzAccountPlanItem> items) {
		this.items = items;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public List<Map<String, Object>> getGzAccountContents() {
		return gzAccountContents;
	}

	public void setGzAccountContents(List<Map<String, Object>> gzAccountContents) {
		this.gzAccountContents = gzAccountContents;
	}

	public List<GzItem> getGzItems() {
		return gzItems;
	}

	public void setGzItems(List<GzItem> gzItems) {
		this.gzItems = gzItems;
	}

	@Autowired
	public void setGzAccountPlanFilterManager(
			GzAccountPlanFilterManager gzAccountPlanFilterManager) {
		this.gzAccountPlanFilterManager = gzAccountPlanFilterManager;
	}

	public void setGzAccountPlanItemManager(
			GzAccountPlanItemManager gzAccountPlanItemManager) {
		this.gzAccountPlanItemManager = gzAccountPlanItemManager;
	}

	public GzAccountBean getAccountBean() {
		return accountBean;
	}

	public void setAccountBean(GzAccountBean accountBean) {
		this.accountBean = accountBean;
	}

	public void setGzItemManager(GzItemManager gzItemManager) {
		this.gzItemManager = gzItemManager;
	}

	public void setGzTypeManager(GzTypeManager gzTypeManager) {
		this.gzTypeManager = gzTypeManager;
	}

	public void setGzAccountPlanManager(
			GzAccountPlanManager gzAccountPlanManager) {
		this.gzAccountPlanManager = gzAccountPlanManager;
	}

	public List<GzAccountPlan> getGzAccountPlans() {
		return gzAccountPlans;
	}

	public void setGzAccountPlans(List<GzAccountPlan> gzAccountPlans) {
		this.gzAccountPlans = gzAccountPlans;
	}

	public GzAccountPlan getGzAccountPlan() {
		return gzAccountPlan;
	}

	public void setGzAccountPlan(GzAccountPlan gzAccountPlan) {
		this.gzAccountPlan = gzAccountPlan;
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

	public String gzAccountPlanGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			gzAccountDefine = gzAccountDefineManager.get(accountType);
			this.gzAccountContents = gzAccountPlanManager.getGzAccountGridData(
					columns, converFilterToSql(request,gzAccountDefine), gzAccountDefine,
					convertGroupFilterToSql(request));
		} catch (Exception e) {
			log.error("gzAccountPlanGridList Error", e);
		}
		return SUCCESS;
	}

	/* 反转查询 */
	public String gzAccountReverseGridList() {
		log.debug("enter list method!");
		try {
			HttpServletRequest request = this.getRequest();
			gzAccountDefine = gzAccountDefineManager.get(accountType);
			this.gzAccountContents = gzAccountPlanManager
					.getGzAccountReverseGridData(
							gzAccountReverseColumnInfo(gzTypeId,
									gzAccountItemsStr),
							converFilterToSql(request,gzAccountDefine), gzAccountDefine);
		} catch (Exception e) {
			log.error("List Error", e);
		}
		return SUCCESS;
	}

	/* 需要反转的列表 */
	private List<GzItem> gzAccountReverseColumnInfo(String gzTypeId,
			String gzAccountItemsStr) {
		String itemcodes = "";
		Map<String, GzAccountPlanItem> planItemMap = new HashMap<String, GzAccountPlanItem>();
		if (OtherUtil.measureNotNull(gzAccountItemsStr)) {
			JSONObject jsonObject = JSONObject.fromObject(gzAccountItemsStr);
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
					GzAccountPlanItem item = new GzAccountPlanItem();
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
				itemcodes = itemcodes.substring(0, itemcodes.length() - 1);
			}
		}
		List<PropertyFilter> filters2 = new ArrayList<PropertyFilter>();
		filters2.add(new PropertyFilter("INS_itemCode", itemcodes));
		filters2.add(new PropertyFilter("EQS_gzType.gzTypeId", gzTypeId));
		filters2.add(new PropertyFilter("OAI_sn", ""));
		this.gzItems = gzItemManager.getByFilters(filters2);
		if (OtherUtil.measureNotNull(gzItems) && !gzItems.isEmpty()) {
			for (GzItem gzItem : gzItems) {
				String itemCode = gzItem.getItemCode();
				if (planItemMap.containsKey(itemCode)) {
					GzAccountPlanItem planItemTemp = planItemMap.get(itemCode);
					gzItem.setColName(planItemTemp.getColName());
					gzItem.setIsThousandSeparat(planItemTemp.getIsThousandSeparat());
					gzItem.setFontIndex(planItemTemp.getFontIndex());
					gzItem.setHeaderFontIndex(planItemTemp.getHeaderFontIndex());
					gzItem.setHeaderTextColor(planItemTemp.getHeaderTextColor());
					gzItem.setSn(planItemTemp.getColSn());
				} else {
					gzItem.setColName(gzItem.getItemName());
				}
			}
		}
		if (OtherUtil.measureNotNull(gzItems) && !gzItems.isEmpty()) {
			List<GzItem> gzItemsTemp = new ArrayList<GzItem>();
			for (GzItem gzItemTemp : gzItems) {
				String itemType = gzItemTemp.getItemType();
				if ("1".equals(itemType) || "2".equals(itemType)) {
				} else {
					gzItemsTemp.add(gzItemTemp);
				}
			}
			this.gzItems = gzItemsTemp;
		}
		// 根据Collections.sort重载方法来实现
		Collections.sort(gzItems, new Comparator<GzItem>() {
			@Override
			public int compare(GzItem b1, GzItem b2) {
				int bsn1 = b1.getSn();
				int bsn2 = b2.getSn();
				return bsn1 - bsn2;
			}
		});
		return gzItems;
	}

	/* 分组过滤条件 */
	private Map<String, String> convertGroupFilterToSql(
			HttpServletRequest request) {
		String deptType = request.getParameter("deptType");
		String deptLevelFrom = request.getParameter("deptLevelFrom");
		String deptLevelTo = request.getParameter("deptLevelTo");
		String deptDetailIds = request.getParameter("deptDetailIds");
		Map<String, String> filterMap = new HashMap<String, String>();
		filterMap.put("deptType", deptType);
		filterMap.put("deptLevelFrom", deptLevelFrom);
		filterMap.put("deptLevelTo", deptLevelTo);
		filterMap.put("deptDetailIds", deptDetailIds);
		return filterMap;
	}

	/* 过滤条件 */
	private List<String> converFilterToSql(HttpServletRequest request,GzAccountDefine gzAccountDefine) {
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
		String deptIds = request.getParameter("deptIds");
		String empTypes = request.getParameter("empTypes");
		String personName = request.getParameter("personName");
		String personCode = request.getParameter("personCode");
		String gzTypeId = request.getParameter("gzTypeId");
		String gzTypeIds = request.getParameter("gzTypeIds");
		String personId = request.getParameter("personId");
		String includeUnCheck = request.getParameter("includeUnCheck");
		String sqlString = "";
		if (OtherUtil.measureNotNull(periodTime)) {
			sqlString = "gz.period = '" + periodTime + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(fromPeriodTime)) {
			sqlString = "gz.period >= '" + fromPeriodTime + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(toPeriodTime)) {
			sqlString = "gz.period <= '" + toPeriodTime + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(deptIds)) {
			sqlString = "gz.deptId in ("
					+ OtherUtil.splitStrAddQuotes(deptIds, ",") + ")";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(empTypes)) {
			sqlString = "gz.empType in ("
					+ OtherUtil.splitStrAddQuotes(empTypes, ",") + ")";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(personName)) {
			personName = personName.replace("*", "%");
			sqlString = "gz.personName" + " like '" + personName + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(personCode)) {
			personCode = personCode.replace("*", "%");
			sqlString = "gz.personCode like '" + personCode + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(checkYear)) {
			sqlString = "gz.period like '" + checkYear + "%'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(checkNumber)) {
			sqlString = "gz.issueNumber = '" + checkNumber + "'";
			sqlFilters.add(sqlString);
		}
		if(OtherUtil.measureNotNull(fromCheckYear)&&OtherUtil.measureNotNull(fromCheckNumber)){
			sqlString = "(gz.period >= '" + fromCheckYear + "01' and gz.issueNumber >= '"+fromCheckNumber+"')";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(fromCheckYear)){
			sqlString = "gz.period >= '" + fromCheckYear + "01'";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(fromCheckNumber)){
			sqlString = "gz.issueNumber >= '" + fromCheckNumber + "'";
			sqlFilters.add(sqlString);
		}
		
		if(OtherUtil.measureNotNull(toCheckYear)&&OtherUtil.measureNotNull(toCheckNumber)){
			sqlString = "(gz.period <= '" + toCheckYear + "12' and gz.issueNumber <= '"+toCheckNumber+"')";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(toCheckYear)){
			sqlString = "gz.period <= '" + toCheckYear + "12'";
			sqlFilters.add(sqlString);
		}else if(OtherUtil.measureNotNull(toCheckNumber)){
			sqlString = "gz.issueNumber <= '" + toCheckNumber + "'";
			sqlFilters.add(sqlString);
		}
		if (OtherUtil.measureNotNull(personId)) {
			sqlString = "gz.personId = '" + personId + "'";
			sqlFilters.add(sqlString);
		}
		if(OtherUtil.measureNull(includeUnCheck)){
			sqlString = "gz.status <> '0'";
			sqlFilters.add(sqlString);
		}
		if("09".equals(gzAccountDefine.getDefineId())){
			if (OtherUtil.measureNotNull(gzTypeIds)) {
				sqlString = "gz.gzTypeId in ("
						+ OtherUtil.splitStrAddQuotes(gzTypeIds, ",") + ")";
				sqlFilters.add(sqlString);
			}
		}else{
			sqlString = "gz.gzTypeId = '" + gzTypeId + "'";
		}
		sqlFilters.add(sqlString);
		return sqlFilters;
	}

	private String planName;

	public String save() {
		try {
			if (OtherUtil.measureNull(accountBean.getPlanName())) {
				return ajaxForward(false, "方案名称不能为空！", false);
			}
			if (OtherUtil.measureNull(accountBean.getGzType())) {
				return ajaxForward(false, "工资类别不能为空！", false);
			}
			HttpServletRequest request = this.getRequest();
			String toPublic = request.getParameter("toPublic");
			String toDepartment = request.getParameter("toDepartment");
			String toRole = request.getParameter("toRole");
			accountBean.setToPublic(toPublic);
			accountBean.setToDepartment(toDepartment);
			accountBean.setToRole(toRole);
			// gzAccountPlan =
			// gzAccountPlanManager.saveGzAccountPlan(accountBean,
			// gzAccountDefine.getDefineId(), getSessionUser().getId()
			// .toString());
			planId = gzAccountPlan.getPlanId();
			planName = gzAccountPlan.getPlanName();
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = "方案保存成功。";
		return ajaxForward(true, this.getText(key), false);
	}

	/* 保存方案格式 */
	public String saveGzAccountPlanCustomLayout() {
		try {
			GzAccountPlan planTemp = gzAccountPlanManager.get(planId);
			planTemp.setCustomLayout(gzAccountCustomLayout);
			gzAccountPlanManager.save(planTemp);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		return ajaxForward(true, "保存成功。", false);
	}

	private String width;
	private String height;
	private List<GzType> gzTypeList;
	private GzType curGzType;
	public String edit() {
		try {
			gzTypeList = gzTypeManager.allGzTypes(true);
			if(OtherUtil.measureNotNull(gzTypeId)){
				curGzType = gzTypeManager.get(gzTypeId);
			}else{
				curGzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
			}
			if(OtherUtil.measureNull(curGzType)&&OtherUtil.measureNotNull(gzTypeList)&&!gzTypeList.isEmpty()){
				curGzType = gzTypeList.get(0);
			}
			if(OtherUtil.measureNotNull(curGzType)){
				gzTypeId = curGzType.getGzTypeId();
			}else{
				gzTypeId = "";
			}
			if (OtherUtil.measureNotNull(accountType)) {
				gzAccountDefine = gzAccountDefineManager.get(accountType);
				if("09".equals(gzAccountDefine.getDefineId())){
					curGzType = gzTypeManager.get("XT");
					gzTypeId = "XT";
					if(OtherUtil.measureNotNull(gzTypeList)){
						gzTypeList.add(curGzType);
					}else{
						gzTypeList = new ArrayList<GzType>();
						gzTypeList.add(curGzType);
					}
				}
				getALLInitData(gzTypeId);
			}
			gzStubsDeptNumber = this.getGlobalParamByKey("gzStubsDeptNumber");
			if(OtherUtil.measureNull(gzStubsDeptNumber)){
				gzStubsDeptNumber = "10";
			}
			Person operPerson = this.getSessionUser().getPerson();
			operPersonName = operPerson.getName();
			curPeriod = this.getLoginPeriod();
		} catch (Exception e) {
			log.error("gzAccountPlanError:" + e.getMessage());
		}
		return SUCCESS;
	}

	/* 不可配置初始化 */
	private void initDataNotConfigurable(String accountType) {
		Person operPerson = this.getSessionUser().getPerson();
		operPersonName = operPerson.getName();
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_defineId", accountType));
		filters.add(new PropertyFilter("EQB_sysField", "1"));
		List<GzAccountPlan> accountPlanList = gzAccountPlanManager
				.getByFilters(filters);
		if (OtherUtil.measureNotNull(accountPlanList)
				&& !accountPlanList.isEmpty()) {
			GzAccountPlan planItem = accountPlanList.get(0);
			planId = planItem.getPlanId();
			JSONObject jsonArray = new JSONObject();
			filters.clear();
			filters.add(new PropertyFilter("EQS_planId", planId));
			filters.add(new PropertyFilter("OAI_colSn", ""));
			List<GzAccountPlanItem> planItems = gzAccountPlanItemManager
					.getByFilters(filters);
			if (OtherUtil.measureNotNull(planItems) && !planItems.isEmpty()) {
				for (GzAccountPlanItem itemTemp : planItems) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("name", itemTemp.getColName());
					jsonObject.put("oldName", itemTemp.getColName());
					jsonObject.put("isThousandSeparat",
							itemTemp.getIsThousandSeparat());
					jsonObject.put("headerFontIndex",
							itemTemp.getHeaderFontIndex());
					jsonObject.put("fontIndex", itemTemp.getFontIndex());
					jsonObject.put("headerTextColor",
							itemTemp.getHeaderTextColor());
					jsonArray.put(itemTemp.getItemCode(), jsonObject);
				}
			}
			gzAccountItemsStr = jsonArray.toString();
			filters.clear();
			filters.add(new PropertyFilter("EQS_planId", planId));
			List<GzAccountPlanFilter> planFilters = gzAccountPlanFilterManager
					.getByFilters(filters);
			if (OtherUtil.measureNotNull(planFilters) && !planFilters.isEmpty()) {
				JSONObject jsonObject = new JSONObject();
				for (GzAccountPlanFilter filterTemp : planFilters) {
					jsonObject.put(filterTemp.getFilterCode(),
							filterTemp.getFilterValue());
				}
				gzAccountFilterStr = jsonObject.toString();
			}
		}
		this.getSession().setAttribute(accountType + "gzAccountItemsStr",
				gzAccountItemsStr);
		this.getSession().setAttribute(accountType + "gzAccountFilterStr",
				gzAccountFilterStr);
	}

	public String gzAccountPlanGridEdit() {
		try {
			if (oper.equals("del")) {
				gzAccountPlan = gzAccountPlanManager.get(planId);
				Boolean sysField = gzAccountPlan.getSysField();
				if (sysField) {
					return ajaxForward(false, "系统方案不能删除！", false);
				}
				User sessionUser = getSessionUser();
				String userId = sessionUser.getId().toString();
				if (!userId.equals(gzAccountPlan.getUserId())) {
					return ajaxForward(false, "只能删除自己创建的方案！", false);
				}
				gzAccountPlanManager.removeGzAccountPlan(planId);
				gridOperationMessage = this.getText("gzAccountPlan.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkGzAccountPlanGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	//
	// private String isValid() {
	// if (gzAccountPlan == null) {
	// return "Invalid gzAccountPlan Data";
	// }
	//
	// return SUCCESS;
	//
	// }

	public void popGzAccountPlan() {
		// getALLInitData();

	}

	private String issueType;

	/* 工资项改变 */
	public String gzAccountPlanGztypeChange() {
		HttpServletRequest request = this.getRequest();
		String gzTypeId = request.getParameter("gzTypeId");
		gzType = gzTypeManager.get(gzTypeId);
		issueType = gzType.getIssueType();
		getALLInitData(gzTypeId);
		return SUCCESS;
	}

	// 对页面的各种查询框赋值
	private String gzItemsJsonStr;
	private String gzAccountPlanSysStr;// 系统方案

	private void getALLInitData(String gzTypeId) {
		// 首先获取公共的方案列表1.获取当前用户
		User sessionUser = getSessionUser();
		String userId = sessionUser.getId().toString();
		List<User> sameRoleUsers = userManager.getUsersByRoles(sessionUser
				.getRoles());
		List<User> sameDeptUsers = userManager.getUsersByDept(sessionUser
				.getPerson().getDepartment());
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
		List<PropertyFilter> filters1 = new ArrayList<PropertyFilter>();
		filters1.add(new PropertyFilter("EQS_defineId", accountType));
		filters1.add(new PropertyFilter("EQS_gzTypeId", gzTypeId));
		filters1.add(new PropertyFilter("EQB_sysField", "0"));
		List<GzAccountPlan> accountPlanList = gzAccountPlanManager
				.getByFilters(filters1);
		gzAccountPlans = new ArrayList<GzAccountPlan>();
		if (OtherUtil.measureNotNull(accountPlanList)
				&& !accountPlanList.isEmpty()) {
			for (GzAccountPlan gzApTemp : accountPlanList) {
				String userIdTemp = gzApTemp.getUserId();
				/* 同一Id 或者公有或者统一角色或者统一部门 */
				if (userIdTemp.equals(userId)
						|| "1".equals(gzApTemp.getToPublic())
						|| "1".equals(gzApTemp.getToDepartment())
						&& sameDeptUserIds.contains(userIdTemp)
						|| "1".equals(gzApTemp.getToRole())
						&& sameRoleUserIds.contains(userIdTemp)) {
					gzAccountPlans.add(gzApTemp);
				}
			}
		}
		filters1.clear();
		filters1.add(new PropertyFilter("EQS_defineId", accountType));
		filters1.add(new PropertyFilter("EQB_sysField", "1"));
		List<GzAccountPlan> gzAccountSysPlans = gzAccountPlanManager
				.getByFilters(filters1);
		if (OtherUtil.measureNotNull(gzAccountSysPlans)
				&& !gzAccountSysPlans.isEmpty()) {
			JSONObject jsonObject = new JSONObject();
			for (GzAccountPlan planTemp : gzAccountSysPlans) {
				jsonObject.put(planTemp.getPlanName(), planTemp.getPlanId());
				gzAccountPlans.add(planTemp);
			}
			gzAccountPlanSysStr = jsonObject.toString();
		}
		List<PropertyFilter> itemFilters = new ArrayList<PropertyFilter>();
		itemFilters.add(new PropertyFilter("OAI_sn", ""));
		itemFilters.add(new PropertyFilter("EQS_gzType.gzTypeId", gzTypeId));
		itemFilters.add(new PropertyFilter("EQB_disabled", "0"));
		if("XT".equals(gzTypeId)){
			itemFilters.add(new PropertyFilter("INS_itemType", "0,3"));
			itemFilters.add(new PropertyFilter("EQB_statistics", "1"));
		}
		gzItems = gzItemManager.getByFilters(itemFilters);
		gzItemsJsonStr = JSONArray.fromObject(gzItems).toString();
		//通过工资数据表取期间和发放次数
    	String sqlString = "SELECT period,issueNumber FROM gz_gzContent WHERE gzTypeId = '"+gzTypeId+"' "
    			+ "GROUP BY period,issueNumber ORDER BY period,issueNumber";
    	List<Map<String, Object>> mapList = gzAccountPlanManager.getBySqlToMap(sqlString);
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

	public String gzAccountColumnInfo() {
		try {
			gzAccountDefine = gzAccountDefineManager.get(accountType);
			String groups = gzAccountDefine.getGroups();
			String itemcodes = "";
			Map<String, GzAccountPlanItem> planItemMap = new HashMap<String, GzAccountPlanItem>();
			if (OtherUtil.measureNotNull(gzAccountItemsStr)) {
				JSONObject jsonObject = JSONObject
						.fromObject(gzAccountItemsStr);
				Iterator iter = jsonObject.keys();
				while (iter.hasNext()) {
					String itemCode = (String) iter.next();
					String valueStr = jsonObject.get(itemCode).toString();
					JSONObject valueObject = JSONObject.fromObject(valueStr);
					String name = valueObject.get("name") == null ? ""
							: valueObject.get("name").toString();
					String isThousandSeparat = valueObject
							.get("isThousandSeparat") == null ? ""
							: valueObject.get("isThousandSeparat").toString();
					String fontIndex = valueObject.get("fontIndex") == null ? ""
							: valueObject.get("fontIndex").toString();
					String headerFontIndex = valueObject.get("headerFontIndex") == null ? ""
							: valueObject.get("headerFontIndex").toString();
					String headerTextColor = valueObject.get("headerTextColor") == null ? ""
							: valueObject.get("headerTextColor").toString();
					Integer sn = valueObject.get("sn") == null ? 0:Integer.parseInt(valueObject.get("sn").toString());
					itemcodes += itemCode + ",";
					GzAccountPlanItem item = new GzAccountPlanItem();
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
			List<PropertyFilter> filters2 = new ArrayList<PropertyFilter>();
			filters2.add(new PropertyFilter("EQS_gzType.gzTypeId", gzTypeId));
			filters2.add(new PropertyFilter("INS_itemCode", itemcodes));
			this.gzItems = gzItemManager.getByFilters(filters2);
			if (OtherUtil.measureNotNull(gzItems) && !gzItems.isEmpty()) {
				for (GzItem gzItem : gzItems) {
					String itemCode = gzItem.getItemCode();
					if (planItemMap.containsKey(itemCode)) {
						GzAccountPlanItem planItemTemp = planItemMap
								.get(itemCode);
						gzItem.setColName(planItemTemp.getColName());
						gzItem.setIsThousandSeparat(planItemTemp
								.getIsThousandSeparat());
						gzItem.setFontIndex(planItemTemp.getFontIndex());
						gzItem.setHeaderFontIndex(planItemTemp
								.getHeaderFontIndex());
						gzItem.setHeaderTextColor(planItemTemp
								.getHeaderTextColor());
						gzItem.setSn(planItemTemp.getColSn());
					} else {
						gzItem.setColName(gzItem.getItemName());
					}
				}
			}
			if("09".equals(gzAccountDefine.getDefineId())){
				if (OtherUtil.measureNotNull(gzItems) && !gzItems.isEmpty()) {
					List<GzItem> gzItemsTemp = new ArrayList<GzItem>();
					for (GzItem gzItemTemp : gzItems) {
						String itemType = gzItemTemp.getItemType();
						if ("0".equals(itemType) || "3".equals(itemType)) {
							gzItemsTemp.add(gzItemTemp);
						}
						gzItemTemp.setSn(gzItemTemp.getSn()+1);
					}
					this.gzItems = gzItemsTemp;
				}else{
					gzItems = new ArrayList<GzItem>();
				}
				GzItem gzItemTemp = new GzItem();
				gzItemTemp.setItemCode("count");
				gzItemTemp.setItemName("人数");
				gzItemTemp.setItemType("3");
				gzItemTemp.setColName("人数");
				gzItemTemp.setSn(0);
				gzItems.add(gzItemTemp);
			}
			if (OtherUtil.measureNotNull(groups)) {
				String[] groupColumns = groups.split(",");
				List<String> groupColumnList = Arrays.asList(groupColumns);
				if (OtherUtil.measureNotNull(gzItems) && !gzItems.isEmpty()) {
					List<GzItem> gzItemsTemp = new ArrayList<GzItem>();
					boolean countFlag = true;
					for (GzItem gzItemTemp : gzItems) {
						String itemCode = gzItemTemp.getItemCode();
						String itemType = gzItemTemp.getItemType();
						if ("1".equals(itemType) || "2".equals(itemType)) {
							if (groupColumnList.contains(itemCode)) {
								gzItemsTemp.add(gzItemTemp);
								if (("deptName".equals(itemCode) || "empType"
										.equals(itemCode)) && countFlag) {
									Integer snTemp = gzItemTemp.getSn();
									gzItemTemp = new GzItem();
									gzItemTemp.setItemCode("count");
									gzItemTemp.setItemName("人数");
									gzItemTemp.setItemType("3");
									gzItemTemp.setColName("人数");
									gzItemTemp.setSn(snTemp+1);
									gzItemsTemp.add(gzItemTemp);
									countFlag = false;
								}
							}
						} else {
							gzItemsTemp.add(gzItemTemp);
						}
					}
					this.gzItems = gzItemsTemp;
				} else {
					gzItems = new ArrayList<GzItem>();
					GzItem gzItemTemp = new GzItem();
					gzItemTemp.setItemCode("count");
					gzItemTemp.setItemName("人数");
					gzItemTemp.setItemType("3");
					gzItemTemp.setColName("人数");
					gzItemTemp.setSn(0);
					gzItems.add(gzItemTemp);
				}
			}
			// 根据Collections.sort重载方法来实现
			Collections.sort(gzItems, new Comparator<GzItem>() {
				@Override
				public int compare(GzItem b1, GzItem b2) {
					int bsn1 = b1.getSn();
					int bsn2 = b2.getSn();
					return bsn1 - bsn2;
				}
			});
		} catch (Exception e) {
			log.error("gzAccountColumnInfo:" + e.getMessage());
		}

		return SUCCESS;
	}

	public String getSetPlanId() {
		String planId = (String) this.getRequest().getAttribute("planId");
		return planId;
	}

	private String accountType;
	private GzAccountDefine gzAccountDefine;

	public GzAccountDefine getGzAccountDefine() {
		return gzAccountDefine;
	}

	public void setGzAccountDefine(GzAccountDefine gzAccountDefine) {
		this.gzAccountDefine = gzAccountDefine;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String gzAccountPre() {
		if (OtherUtil.measureNotNull(accountType)) {
			gzAccountDefine = gzAccountDefineManager.get(accountType);
		}
		return SUCCESS;
	}

	private GzType gzType;
	private String operPersonName;
	private String gzTypeId;
	private String gzAccountItemsStr;
	private String gzAccountFilterStr;
	private String modelStatusStr;
	private String curPeriod;//期间
	private String firstPeriod;
	private String gzStubsDeptNumber;//工资条选择部门个数

	public String gzAccountByPlan() {
		try {
			if (OtherUtil.measureNotNull(accountType)) {
				gzAccountDefine = gzAccountDefineManager.get(accountType);
			}
			gzStubsDeptNumber = this.getGlobalParamByKey("gzStubsDeptNumber");
			if(OtherUtil.measureNull(gzStubsDeptNumber)){
				gzStubsDeptNumber = "10";
			}
			Person operPerson = this.getSessionUser().getPerson();
			operPersonName = operPerson.getName();
			curPeriod = this.getLoginPeriod();
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
					gzAccountPlan = gzAccountPlanManager.saveGzAccountPlan(accountType,
							gzTypeId, planName, toPublic, toDepartment, toRole,
							gzAccountItemsStr, gzAccountFilterStr,
							getSessionUser().getId().toString());
					planId = gzAccountPlan.getPlanId();
				}
				this.getSession().setAttribute(
						accountType + "gzAccountItemsStr", gzAccountItemsStr);
				this.getSession().setAttribute(
						accountType + "gzAccountFilterStr", gzAccountFilterStr);
			} else {
				Object gzAccountItemsTemp = this.getSession().getAttribute(
						accountType + "gzAccountItemsStr");
				Object gzAccountFilterTemp = this.getSession().getAttribute(
						accountType + "gzAccountFilterStr");
				if (OtherUtil.measureNotNull(gzAccountItemsTemp)) {
					gzAccountItemsStr = gzAccountItemsTemp.toString();
				}
				if (OtherUtil.measureNotNull(gzAccountFilterTemp)) {
					gzAccountFilterStr = gzAccountFilterTemp.toString();
				}
			}
			gzType = gzTypeManager.get(gzTypeId);
			//modelStatusManager
//			String modelId = "GZ_" + gzTypeId;
//			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
//	    	filters.add(new PropertyFilter("EQS_modelId",modelId));
//	    	filters.add(new PropertyFilter("OAS_checkperiod",""));
//	    	filters.add(new PropertyFilter("OAI_checkNumber",""));
//	    	List<ModelStatus> modelStatusList = modelStatusManager.getByFilters(filters);
//	    	JSONArray jsonArray = new JSONArray();
//	    	if(OtherUtil.measureNotNull(modelStatusList)&&!modelStatusList.isEmpty()){
//	    		for(ModelStatus msTemp:modelStatusList){
//	    			JSONObject jsonObject = new JSONObject();
//	    			jsonObject.put("modelId", msTemp.getModelId());
//	    			jsonObject.put("checkperiod", msTemp.getCheckperiod());
//	    			jsonObject.put("checkNumber", msTemp.getCheckNumber());
//	    			jsonArray.add(jsonObject);
//	    		}
//	    	}
		} catch (Exception e) {
			log.error("gzAccountByPlanError:" + e.getMessage());
		}
		return SUCCESS;
	}

	public String gzAccountByPlanItem() {
		return SUCCESS;
	}

	private List<GzAccountPlanFilter> gzAccountPlanFilters;

	public String getSelectedGzPlan() {
		// 获取planid
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_planId", planId));
		this.gzAccountPlans = gzAccountPlanManager.getByFilters(filters);
		this.gzAccountPlanFilters = gzAccountPlanFilterManager
				.getByFilters(filters);
		filters.add(new PropertyFilter("OAI_colSn", ""));
		this.items = gzAccountPlanItemManager.getByFilters(filters);
		return SUCCESS;
	}

	public GzType getGzType() {
		return gzType;
	}

	public void setGzType(GzType gzType) {
		this.gzType = gzType;
	}

	public List<GzAccountPlanFilter> getGzAccountPlanFilters() {
		return gzAccountPlanFilters;
	}

	public void setGzAccountPlanFilters(
			List<GzAccountPlanFilter> gzAccountPlanFilters) {
		this.gzAccountPlanFilters = gzAccountPlanFilters;
	}

	public String getGzitems() {
		String curGzTypeId = "";
		GzType curGzType = gzTypeManager.getCurrentGzType(getSessionUser().getId().toString());
		if(OtherUtil.measureNotNull(curGzType)){
			curGzTypeId = curGzType.getGzTypeId();
		}
		List<PropertyFilter> itemFilters = new ArrayList<PropertyFilter>();
		itemFilters.add(new PropertyFilter("OAI_sn", ""));
		itemFilters.add(new PropertyFilter("EQS_gzType.gzTypeId", curGzTypeId));
		itemFilters.add(new PropertyFilter("EQB_disabled", "0"));
		this.gzItems3 = gzItemManager.getByFilters(itemFilters);

		return SUCCESS;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String gzAccountReportData() {
		return SUCCESS;
	}

	public String getOperPersonName() {
		return operPersonName;
	}

	public void setOperPersonName(String operPersonName) {
		this.operPersonName = operPersonName;
	}

	public String getGzTypeId() {
		return gzTypeId;
	}

	public void setGzTypeId(String gzTypeId) {
		this.gzTypeId = gzTypeId;
	}

	public String getGzAccountFilterStr() {
		return gzAccountFilterStr;
	}

	public void setGzAccountFilterStr(String gzAccountFilterStr) {
		this.gzAccountFilterStr = gzAccountFilterStr;
	}

	public String getGzAccountItemsStr() {
		return gzAccountItemsStr;
	}

	public void setGzAccountItemsStr(String gzAccountItemsStr) {
		this.gzAccountItemsStr = gzAccountItemsStr;
	}

	public List<GzType> getGzTypeList() {
		return gzTypeList;
	}

	public void setGzTypeList(List<GzType> gzTypeList) {
		this.gzTypeList = gzTypeList;
	}

	public GzType getCurGzType() {
		return curGzType;
	}

	public void setCurGzType(GzType curGzType) {
		this.curGzType = curGzType;
	}

	public String getGzItemsJsonStr() {
		return gzItemsJsonStr;
	}

	public void setGzItemsJsonStr(String gzItemsJsonStr) {
		this.gzItemsJsonStr = gzItemsJsonStr;
	}

	public String getGzAccountCustomLayout() {
		return gzAccountCustomLayout;
	}

	public void setGzAccountCustomLayout(String gzAccountCustomLayout) {
		this.gzAccountCustomLayout = gzAccountCustomLayout;
	}

	public String getGzAccountPlanSysStr() {
		return gzAccountPlanSysStr;
	}

	public void setGzAccountPlanSysStr(String gzAccountPlanSysStr) {
		this.gzAccountPlanSysStr = gzAccountPlanSysStr;
	}

	public String getModelStatusStr() {
		return modelStatusStr;
	}

	public void setModelStatusStr(String modelStatusStr) {
		this.modelStatusStr = modelStatusStr;
	}

	public String getCurPeriod() {
		return curPeriod;
	}

	public void setCurPeriod(String curPeriod) {
		this.curPeriod = curPeriod;
	}

	public String getGzStubsDeptNumber() {
		return gzStubsDeptNumber;
	}

	public void setGzStubsDeptNumber(String gzStubsDeptNumber) {
		this.gzStubsDeptNumber = gzStubsDeptNumber;
	}

	public String getFirstPeriod() {
		return firstPeriod;
	}

	public void setFirstPeriod(String firstPeriod) {
		this.firstPeriod = firstPeriod;
	}
}
