package com.huge.ihos.system.reportManager.chart.webapp.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.huge.ihos.formula.model.FormulaField;
import com.huge.ihos.system.context.ContextUtil;
import com.huge.ihos.system.context.UserContextUtil;
import com.huge.ihos.system.reportManager.chart.model.IntegrationData;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.ihos.system.reportManager.search.util.FontUtil;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.util.OptFile;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.BaseMccKeyAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;

public class MccKeyAction extends BaseMccKeyAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1954408359895333691L;
	/**
	 * @throws Exception
	 * 
	 */
	private String deptJsonStr;
	
	private String herpType;
	@Override
	public void prepare() throws Exception {
		super.prepare();
		fontType = FontUtil.GetSystemFont();// 获取系统所有字体样式
		departments = departmentManager.getAllDept();// 所有科室
		JSONObject deptJson = new JSONObject();
		if(OtherUtil.measureNotNull(departments)&&!departments.isEmpty()){
			for(Department dept:departments){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("deptId", dept.getDepartmentId());
				jsonObject.put("deptName", dept.getName());
				jsonObject.put("orgCode", dept.getOrgCode());
				deptJson.put(dept.getDepartmentId(), jsonObject);
			}
		}
		deptJsonStr = deptJson.toString();
		deptKeys = formulaFieldManager.getAllFormulaFieldByEntityId("2");// 科级指标
		orgKeys = formulaFieldManager.getAllFormulaFieldByEntityId("1");// 院级指标
		allKeys = formulaFieldManager.getAllFormulaField();// 所有指标
		this.herpType = ContextUtil.herpType;
		// this.currentPeriod =
		// this.periodManager.getCurrentPeriod().getPeriodCode();
		this.currentPeriod = this.getLoginPeriod();
		if (OtherUtil.measureNotNull(currentPeriod)
				&& OtherUtil.measureNull(currentPeriod1))
			currentPeriod1 = currentPeriod;
		this.clearSessionMessages();
	}

	public String xuanCharts() throws SQLException {
		initCondition();
		String[] types = null, ckeys = null;
		if (OtherUtil.measureNotNull(deptId)
				|| OtherUtil.measureNotNull(deptKeyName)
				|| OtherUtil.measureNotNull(conditionO)) {
			if (OtherUtil.measureNotNull(type)
					&& OtherUtil.measureNotNull(ckey)) {
				types = type.split(",");
				ckeys = ckey.split(",");
				initproperty(ckeys.length);
				try {
					for (int i = 0; i < types.length; i++) {
						Object[] fanZhi = new Object[6];
						if (types[i].trim().equalsIgnoreCase("bing")) {
							fanZhi = publicChartsConditionSTD(ckeys[i].trim());
							evalBing(fanZhi, i);

						} else if (types[i].trim().equalsIgnoreCase("zheXian")
								|| types[i].trim().equalsIgnoreCase("leiDa")) {
							chartStr[i] = (String) publicChartsConditionS(ckeys[i]
									.trim())[0];

						} else if (types[i].trim().equalsIgnoreCase("zhuXing")) {
							fanZhi = publicChartsConditionSTD(ckeys[i].trim());
							chartStr[i] = (String) fanZhi[0];
							c3dS[i] = (String) fanZhi[1];

						} else if (types[i].trim().equalsIgnoreCase("biaoPan")) {
							fanZhi = biaoPan(ckeys[i].trim());
							evalBiaoPan(fanZhi, i);
						}
					}
				} catch (Exception e) {
					log.error("xuanChartsError:"+e.getMessage());
					return ajaxForward(false,
							"您指定的图形定义不存在或者查询定义数据出现错误,请检查后再试!", false);
				}
			}
		}
		return "chart_success";
	}

	/**
	 * 初始化查询条件
	 */
	protected void initCondition() {
		if (OtherUtil.measureNotNull(deptName)
				&& OtherUtil.measureNotNull(deptId))
			saveAlDeptName(deptName, deptId);
		if (OtherUtil.measureNotNull(deptKeyName))
			saveAlDeptKey(deptKey, deptKeyName);

		if (OtherUtil.measureNotNull(conditionO))
			this.currentPeriod = conditionO;
		if (OtherUtil.measureNotNull(conditionT))
			this.currentPeriod1 = conditionT;
	}

	/**
	 * 初始化图表属性
	 */
	protected void initproperty(int length) {
		/* 通用属性 */
		chartStr = new String[length];
		c3dS = new String[length];
		/* 饼图专有 */
		bingDataList = new Object[length];
		/* 表盘专有属性 */
		maxValueS = new BigDecimal[length];
		warningS = new Boolean[length];
		currentValueS = new String[length];
		biaoZhuS = new String[length];
		biaoTiS = new String[length];

	}

	/**
	 * 表盘属性赋值
	 */
	protected void evalBiaoPan(Object[] fanZhi, int order) {
		chartStr[order] = (String) fanZhi[0];
		maxValueS[order] = (BigDecimal) fanZhi[1];
		currentValueS[order] = (String) fanZhi[2];
		warningS[order] = (Boolean) fanZhi[3];
		biaoZhuS[order] = (String) fanZhi[4];
		biaoTiS[order] = (String) fanZhi[5];
	}

	/**
	 * 饼图属性赋值
	 */
	protected void evalBing(Object[] fanZhi, int order) {
		chartStr[order] = (String) fanZhi[0];
		List<IntegrationData> list = (List<IntegrationData>) fanZhi[1];
		bingDataList[order] = list;
		c3dS[order] = (String) fanZhi[2];
	}

	protected Object[] publicChartsConditionS(String ckey) throws SQLException {
		mccKey = mccKeyManager.getCkey(ckey);
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("conditionO", conditionO);
		conditionMap.put("conditionT", conditionT);
		conditionMap.put("deptKey", deptKey);
		conditionMap.put("deptId", deptId);
		conditionMap.put("orgCode", orgCode);
		conditionMap.put("branchCode", branchCode);
		Object[] fanHui = mccKeyManager.choiceChart(mccKey, conditionMap);
		return fanHui;
	}

	protected Object[] publicChartsConditionSTD(String ckey)
			throws SQLException {
		mccKey = mccKeyManager.getCkey(ckey);
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("conditionO", conditionO);
		conditionMap.put("conditionT", conditionT);
		conditionMap.put("deptKey", deptKey);
		conditionMap.put("deptId", deptId);
		conditionMap.put("orgCode", orgCode);
		conditionMap.put("branchCode", branchCode);
		Object[] fanHui = mccKeyManager.choiceChart(mccKey, conditionMap);
		String c3d = mccKey.getC3d() ? "3" : "2";
		int fhLength = fanHui.length;
		Object[] fanZhi = new Object[fhLength + 1];
		for (int i = 0; i < fanHui.length; i++) {
			fanZhi[i] = fanHui[i];
		}
		fanZhi[fhLength] = c3d;
		return fanZhi;
	}

	public Object[] biaoPan(String ckey) throws SQLException {
		Object[] object = publicChartsConditionS(ckey);
		String biaoPanStr = (String) object[0];
		BigDecimal maxValue = (BigDecimal) object[1];

		BigDecimal cValue = (BigDecimal) object[2];
		boolean warning = OtherUtil.measureNull((Boolean) object[3]) ? false
				: (Boolean) object[3];
		String currentValue = null;
		if (OtherUtil.measureNotNull(cValue)) {
			currentValue = OtherUtil.formatString(cValue);
		}

		String biaoTi = mccKey.getCaption();
		String biaoZhu = mccKey.getSubCaption();
		Object[] fanZhi = { biaoPanStr, maxValue, currentValue, warning,
				biaoZhu, biaoTi };
		return fanZhi;
	}

	protected void saveAlDeptName(String dName, String dKey) {
		String[] deptNames = dName.split(",");
		String[] dKeys = dKey.split(",");
		if (deptNames.length == dKeys.length) {
			for (int i = 0; i < deptNames.length; i++) {
				alDeptName += "<option value='" + dKeys[i] + "'>"
						+ deptNames[i] + "</option>";
			}

			List<Department> departmentLin = new ArrayList<Department>();
			departmentLin.addAll(departments);

			for (int j = 0; j < departments.size(); j++) {
				if (dKey.contains(departments.get(j).getDepartmentId())) {
					departmentLin.remove(departments.get(j));
				}
			}
			departments = departmentLin;
		}
	}

	protected void saveAlDeptKey(String dk, String dkName) {
		String[] dks = dk.split(",");
		String[] dkNames = dkName.split(",");
		if (dks.length == dkNames.length) {
			for (int i = 0; i < dkNames.length; i++) {
				alDeptKey += "<option value='" + dks[i] + "'>" + dkNames[i]
						+ "</option>";
			}
			List<FormulaField> deptKeysLin = new ArrayList<FormulaField>();
			deptKeysLin.addAll(deptKeys);

			for (int j = 0; j < deptKeys.size(); j++) {
				if (dk.contains(deptKeys.get(j).getFieldName())) {
					deptKeysLin.remove(deptKeys.get(j));
				}
			}
			deptKeys = deptKeysLin;

		}
	}

	/**
	 * 本量利分析
	 */
	public String graphicsDisplay() throws SQLException,
			UnsupportedEncodingException {
		try {
			HttpServletRequest req = this.getRequest();
			String loginName = userManager.getCurrentLoginUser().getUsername();
			String benLiangLi = loginName + "_" + random;
			String fileName = req.getRealPath("//data//" + loginName + "//"
					+ benLiangLi + ".png"); // 获得绝对路径
			String fileName2 = req.getRealPath("//data//" + loginName); // 获得绝对路径
			OptFile.delAllFile(fileName2);
			OptFile.mkParent(fileName);
			bllPath = "/data/" + loginName + "/" + benLiangLi + ".png";
			str = mccKeyManager.graDisPlay(gdParam, biaoTi, dataMsg, fileName,
					picName, xyName, biaoZhu);
		} catch (Exception e) {
			gridOperationMessage = this.getText("出现未知错误数据无法展示，请重新操作!");
			return ajaxForward(false, gridOperationMessage, false);
		}
		return "gra_success";
	}

	public String edit() {
		if (mccKeyId != null) {
			mccKey = mccKeyManager.get(mccKeyId);
			disShowValue = mccKey.isDisShowValue();
			if (OtherUtil.measureNotNull(mccKey.getChartType())) {
				chartType = mccKey.getChartType();
			}
		} else
			mccKey = new MccKey();
		return SUCCESS;
	}

	public String save() throws Exception {
		if (cancel != null)
			return "cancel";
		boolean isNew = (mccKey.getMccKeyId() == null);
		mccKey.setDisShowValue(disShowValue);
		mccKeyManager.saveMccKey(mccKey);

		String key = (isNew) ? "mccKey.added" : "mccKey.updated";
		saveMessage(getText(key));

		return ajaxForward(getText(key));
	}

	public String mccKeyGridList() {
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = mccKeyManager.getAppManagerCriteriaWithSearch(
					pagedRequests, MccKey.class, filters);
			this.mccKeys = (List<MccKey>) pagedRequests.getList();
			records = pagedRequests.getTotalNumberOfRows();
			total = pagedRequests.getTotalNumberOfPages();
			page = pagedRequests.getPageNumber();

		} catch (Exception e) {
			log.error("mccKeyGridList Error", e);
		}
		return SUCCESS;
	}

	public String mccKeyGridEdit() {
		try {
			if (oper.equals("del")) {
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					mccKeyManager.remove(removeId);
				}
				gridOperationMessage = this.getText("mccKey.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			}

			if (oper.equals("add") || oper.equals("edit")) {
				String error = isValid();
				if (!error.equalsIgnoreCase(SUCCESS)) {
					gridOperationMessage = error;
					return ajaxForward(true, gridOperationMessage, false);
				}
				mccKeyManager.save(mccKey);
				String key = (oper.equals("add")) ? "mccKey.added"
						: "mccKey.updated";
				gridOperationMessage = this.getText(key);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkPeriodGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, gridOperationMessage, false);
		}
	}

	public String getHerpType() {
		return herpType;
	}

	public void setHerpType(String herpType) {
		this.herpType = herpType;
	}

	public String getDeptJsonStr() {
		return deptJsonStr;
	}

	public void setDeptJsonStr(String deptJsonStr) {
		this.deptJsonStr = deptJsonStr;
	}
}