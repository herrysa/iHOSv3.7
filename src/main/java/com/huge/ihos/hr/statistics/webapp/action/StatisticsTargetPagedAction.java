package com.huge.ihos.hr.statistics.webapp.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.ihos.hr.statistics.model.StatisticsTarget;
import com.huge.ihos.hr.statistics.service.StatisticsItemManager;
import com.huge.ihos.hr.statistics.service.StatisticsTargetManager;
import com.huge.ihos.hr.sysTables.model.SysTableContent;
import com.huge.ihos.hr.sysTables.model.SysTableStructure;
import com.huge.ihos.hr.sysTables.service.SysTableContentManager;
import com.huge.ihos.hr.sysTables.service.SysTableStructureManager;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.service.BdInfoManager;
import com.huge.ihos.system.configuration.bdinfo.service.FieldInfoManager;
import com.huge.util.DateUtil;
import com.huge.util.OtherUtil;
import com.huge.webapp.action.JqGridBaseAction;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.pagers.PagerFactory;
import com.huge.webapp.util.PropertyFilter;
import com.opensymphony.xwork2.Preparable;

public class StatisticsTargetPagedAction extends JqGridBaseAction implements
		Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4415994479599980772L;
	private StatisticsTargetManager statisticsTargetManager;
	private StatisticsItemManager statisticsItemManager;
	private List<SysTableContent> sysTableContents;
	private SysTableContentManager sysTableContentManager;
	private BdInfoManager bdInfoManager;
	private List<BdInfo> bdInfoes;
	private List<StatisticsTarget> statisticsTargets;
	private StatisticsTarget statisticsTarget;
	private FieldInfoManager fieldInfoManager;
	private List<FieldInfo> fieldInfoes;
	private List<SysTableStructure> sysTableStructures;
	private SysTableStructureManager sysTableStructureManager;
	private String id;
	private String menuCode;
	private String itemId;
	private String itemIdSecond;
	private String subSetType;
	private String bdInfoId;
	private String statisticsSql;
	private Map<String, String> chartXMLMap;
	private String deptIds;
	private String gridAllDatas;
	private String expression;
	private String mccKeyId;
	private String conditionIds;
	private String conditionSecondIds;
	private String deptFK;
	private String searchDateFrom;
	private String searchDateTo;
	private String shijianFK;
	private String hisTime;

	public StatisticsTargetManager getStatisticsTargetManager() {
		return statisticsTargetManager;
	}

	public void setStatisticsTargetManager(
			StatisticsTargetManager statisticsTargetManager) {
		this.statisticsTargetManager = statisticsTargetManager;
	}

	public List<StatisticsTarget> getstatisticsTargets() {
		return statisticsTargets;
	}

	public void setStatisticsTargets(List<StatisticsTarget> statisticsTargets) {
		this.statisticsTargets = statisticsTargets;
	}

	public StatisticsTarget getStatisticsTarget() {
		return statisticsTarget;
	}

	public void setStatisticsTarget(StatisticsTarget statisticsTarget) {
		this.statisticsTarget = statisticsTarget;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptFK() {
		return deptFK;
	}

	public void setDeptFK(String deptFK) {
		this.deptFK = deptFK;
	}

	public void prepare() throws Exception {
		this.clearSessionMessages();
	}

	public String statisticsTargetGridList() {
		log.debug("enter list method!");
		try {
			List<PropertyFilter> filters = PropertyFilter
					.buildFromHttpRequest(getRequest());
			JQueryPager pagedRequests = null;
			pagedRequests = (JQueryPager) pagerFactory.getPager(
					PagerFactory.JQUERYTYPE, getRequest());
			pagedRequests = statisticsTargetManager
					.getStatisticsTargetCriteria(pagedRequests, filters);
			this.statisticsTargets = (List<StatisticsTarget>) pagedRequests
					.getList();
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
			statisticsTargetManager.save(statisticsTarget);
		} catch (Exception dre) {
			gridOperationMessage = dre.getMessage();
			return ajaxForwardError(gridOperationMessage);
		}
		String key = ((this.isEntityIsNew())) ? "statisticsTarget.added"
				: "statisticsTarget.updated";
		return ajaxForward(this.getText(key));
	}

	public String edit() {
		if (id != null) {
			// statisticsTarget = statisticsTargetManager.get(id);
			this.setEntityIsNew(false);
		} else {
			// statisticsTarget = new StatisticsTarget();
			this.setEntityIsNew(true);
		}
		return SUCCESS;
	}

	public String statisticsTargetGridEdit() {
		try {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			if (OtherUtil.measureNotNull(hisTime)) {
				timestamp = Timestamp.valueOf(hisTime);
			}
			String snapCode = DateUtil.convertDateToString(
					DateUtil.TIMESTAMP_PATTERN, timestamp);
			if (oper.equals("del")) {
				List idl = new ArrayList();
				StringTokenizer ids = new StringTokenizer(id, ",");
				while (ids.hasMoreTokens()) {
					String removeId = ids.nextToken();
					// Long removeId = Long.parseLong(ids.nextToken());
					idl.add(removeId);

				}
				String[] ida = new String[idl.size()];
				idl.toArray(ida);
				this.statisticsTargetManager.remove(ida);
				gridOperationMessage = this.getText("statisticsTarget.deleted");
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("chart")) {
				if (itemId != null && itemIdSecond != null) {
					StatisticsItem statisticsItem = new StatisticsItem();
					StatisticsItem statisticsItemSecond = new StatisticsItem();
					statisticsItem = statisticsItemManager.get(itemId);
					statisticsItemSecond = statisticsItemManager
							.get(itemIdSecond);
					if (!statisticsItem
							.getStatisticsBdInfo()
							.getBdInfo()
							.getBdInfoId()
							.equals(statisticsItemSecond.getStatisticsBdInfo()
									.getBdInfo().getBdInfoId())) {
						gridOperationMessage = "二维统计只能统计统计表相同的统计项";
						return ajaxForward(false, gridOperationMessage, false);
					}
					if (statisticsItem.getStatisticsFieldInfo() != null
							&& !statisticsItem.getStatisticsFieldInfo().equals(
									"")
							|| statisticsItemSecond.getStatisticsFieldInfo() != null
							&& !statisticsItemSecond.getStatisticsFieldInfo()
									.equals("")) {
						gridOperationMessage = "二维统计不能统计带有统计字段的统计项";
						return ajaxForward(false, gridOperationMessage, false);
					}
					chartXMLMap = this.statisticsTargetManager
							.statisticsFullData2DByFilter(itemId, itemIdSecond,
									statisticsItemManager.get(itemId)
											.getMccKeyId(), deptIds,
									gridAllDatas, expression, conditionIds,
									conditionSecondIds, searchDateFrom,
									searchDateTo, snapCode);
				} else {
					chartXMLMap = this.statisticsTargetManager
							.statisticsFullDataByFilter(itemId,
									statisticsItemManager.get(itemId)
											.getMccKeyId(), deptIds,
									gridAllDatas, expression, searchDateFrom,
									searchDateTo, snapCode);
					// chartXMLMap=this.statisticsTargetManager.statisticsFullData(itemId,
					// null,statisticsItemManager.get(itemId).getMccKeyId());
				}
				gridOperationMessage = "success";
				return ajaxForward(true, gridOperationMessage, false);
			} else if (oper.equals("filterchart")) {
				chartXMLMap = this.statisticsTargetManager
						.statisticsFullDataByFilter(
								itemId,
								statisticsItemManager.get(itemId).getMccKeyId(),
								deptIds, gridAllDatas, expression,
								searchDateFrom, searchDateTo, snapCode);
				gridOperationMessage = "查询成功";
				return ajaxForward(gridOperationMessage);
			} else if (oper.equals("filtermultichart")) {
				StatisticsItem statisticsItem = new StatisticsItem();
				StatisticsItem statisticsItemSecond = new StatisticsItem();
				statisticsItem = statisticsItemManager.get(itemId);
				statisticsItemSecond = statisticsItemManager.get(itemIdSecond);
				if (!statisticsItem
						.getStatisticsBdInfo()
						.getBdInfo()
						.getBdInfoId()
						.equals(statisticsItemSecond.getStatisticsBdInfo()
								.getBdInfo().getBdInfoId())) {
					gridOperationMessage = "二维统计只能统计统计表相同的统计项";
					return ajaxForward(false, gridOperationMessage, false);
				}
				if (statisticsItem.getStatisticsFieldInfo() != null
						&& !statisticsItem.getStatisticsFieldInfo().equals("")
						|| statisticsItemSecond.getStatisticsFieldInfo() != null
						&& !statisticsItemSecond.getStatisticsFieldInfo()
								.equals("")) {
					gridOperationMessage = "二维统计不能统计带有统计字段的统计项";
					return ajaxForward(false, gridOperationMessage, false);
				}
				chartXMLMap = this.statisticsTargetManager
						.statisticsFullData2DByFilter(
								itemId,
								itemIdSecond,
								statisticsItemManager.get(itemId).getMccKeyId(),
								deptIds, gridAllDatas, expression,
								conditionIds, conditionSecondIds,
								searchDateFrom, searchDateTo, snapCode);
				gridOperationMessage = "查询成功";
				return ajaxForward(gridOperationMessage);
			} else if (oper.equals("singleField")) {
				chartXMLMap = this.statisticsTargetManager
						.statisticsFullSingleFieldData(id, bdInfoId, deptFK,
								deptIds, gridAllDatas, expression,
								searchDateFrom, searchDateTo, shijianFK,
								snapCode);
				gridOperationMessage = "查询成功";
				return ajaxForward(gridOperationMessage);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("checkStatisticsTargetGridEdit Error", e);
			if (log.isDebugEnabled())
				gridOperationMessage = e.getMessage() + e.getLocalizedMessage()
						+ e.getStackTrace();
			return ajaxForward(false, e.getMessage(), false);
		}
	}

	private String isValid() {
		if (statisticsTarget == null) {
			return "Invalid statisticsTarget Data";
		}

		return SUCCESS;

	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/** 表名下拉列表 **/
	public String getStatisticsTargetBdInfoList() {
		bdInfoes = new ArrayList<BdInfo>();
		if (oper != null && oper.equals("tableId")) {
			if (subSetType != null && subSetType.equals("subSet")) {
				subSetType = "subSet";
				sysTableContents = new ArrayList<SysTableContent>();
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQB_disabled", "0"));
				filters.add(new PropertyFilter("EQB_tableKind.disabled", "0"));
				filters.add(new PropertyFilter("EQS_tableKind.id", itemId));
				sysTableContents = this.sysTableContentManager
						.getByFilters(filters);
				if (sysTableContents != null && sysTableContents.size() > 0) {
					for (SysTableContent sysTableContent : sysTableContents) {
						BdInfo bdInfo = sysTableContent.getBdinfo();
						if (bdInfo.getStatistics()) {
							bdInfo.setBdInfoId(sysTableContent.getId());
							bdInfo.setBdInfoName(sysTableContent.getName());
							bdInfoes.add(bdInfo);
						}
					}
				}
			} else {
				subSetType = "";
				BdInfo bdInfo = new BdInfo();
				bdInfo = bdInfoManager.get(itemId);
				bdInfoes.add(bdInfo);
			}
		} else if (oper != null && oper.equals("mainTable")) {
			if (subSetType != null && subSetType.equals("subSet")) {
				subSetType = "subSet";
				sysTableContents = new ArrayList<SysTableContent>();
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQB_disabled", "0"));
				filters.add(new PropertyFilter("EQB_tableKind.disabled", "0"));
				filters.add(new PropertyFilter("EQS_tableKind.mainTable",
						itemId));
				sysTableContents = this.sysTableContentManager
						.getByFilters(filters);
				if (sysTableContents != null && sysTableContents.size() > 0) {
					for (SysTableContent sysTableContent : sysTableContents) {
						BdInfo bdInfo = sysTableContent.getBdinfo();
						if (bdInfo.getStatistics()) {
							bdInfo.setBdInfoId(sysTableContent.getId());
							bdInfo.setBdInfoName(sysTableContent.getName());
							bdInfoes.add(bdInfo);
						}
					}
				}
			} else {
				subSetType = "";
				BdInfo bdInfo = new BdInfo();
				bdInfo = bdInfoManager.get(itemId);
				bdInfoes.add(bdInfo);
			}
		} else {
			StatisticsItem statisticsItem = new StatisticsItem();
			statisticsItem = this.statisticsItemManager.get(itemId);
			Boolean subSet = statisticsItem.getStatisticsBdInfo().getsubset();
			if (subSet) {
				subSetType = "subSet";
				sysTableContents = new ArrayList<SysTableContent>();
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQB_disabled", "0"));
				filters.add(new PropertyFilter("EQB_tableKind.disabled", "0"));
				filters.add(new PropertyFilter("EQS_tableKind.id",
						statisticsItem.getStatisticsBdInfo().getSubsetKind()
								.getId()));
				sysTableContents = this.sysTableContentManager
						.getByFilters(filters);
				if (sysTableContents != null && sysTableContents.size() > 0) {
					for (SysTableContent sysTableContent : sysTableContents) {
						BdInfo bdInfo = sysTableContent.getBdinfo();
						if (bdInfo.getStatistics()) {
							bdInfo.setBdInfoId(sysTableContent.getId());
							bdInfo.setBdInfoName(sysTableContent.getName());
							bdInfoes.add(bdInfo);
						}
					}
				}
			} else {
				subSetType = "";
				if (statisticsItem.getStatisticsBdInfo().getBdInfo()
						.getStatistics()
						&& !statisticsItem.getStatisticsBdInfo().getBdInfo()
								.getDisabled()) {
					bdInfoes.add(statisticsItem.getStatisticsBdInfo()
							.getBdInfo());
				}
			}
		}
		return SUCCESS;
	}

	/** 字段下拉列表 **/
	public String getStatisticsTargetFieldInfoList() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		fieldInfoes = new ArrayList<FieldInfo>();
		if (oper != null && oper.equals("singleFiled")) {
			if (subSetType.equals("subSet")) {
				filters.add(new PropertyFilter("EQB_disabled", "0"));
				filters.add(new PropertyFilter("EQS_tableContent.id", bdInfoId));
				sysTableStructures = new ArrayList<SysTableStructure>();
				sysTableStructures = this.sysTableStructureManager
						.getByFilters(filters);
				List<String> dataTypes = new ArrayList<String>();// 数字和日期型
				dataTypes.add("2");
				dataTypes.add("4");
				dataTypes.add("5");
				dataTypes.add("6");
				if (sysTableStructures != null && sysTableStructures.size() > 0) {
					for (SysTableStructure sysTableStructure : sysTableStructures) {
						FieldInfo fieldInfo = sysTableStructure.getFieldInfo();
						if (fieldInfo.getStatisticsSingle()
								&& dataTypes.contains(fieldInfo.getDataType())) {
							fieldInfo.setFieldName(sysTableStructure.getName());
							fieldInfoes.add(fieldInfo);
						}
					}
				}

			} else {
				filters.add(new PropertyFilter("EQB_isPkCol", "0"));
				filters.add(new PropertyFilter("EQB_disabled", "0"));
				filters.add(new PropertyFilter("EQS_bdInfo.bdInfoId", bdInfoId));
				filters.add(new PropertyFilter("EQB_statisticsSingle", "1"));
				filters.add(new PropertyFilter("INS_dataType", "2,4,5,6"));
				fieldInfoes = this.fieldInfoManager.getByFilters(filters);
			}
		} else {
			if (subSetType.equals("subSet")) {
				filters.add(new PropertyFilter("EQB_disabled", "0"));
				filters.add(new PropertyFilter("EQS_tableContent.id", bdInfoId));
				sysTableStructures = new ArrayList<SysTableStructure>();
				sysTableStructures = this.sysTableStructureManager
						.getByFilters(filters);
				if (sysTableStructures != null && sysTableStructures.size() > 0) {
					for (SysTableStructure sysTableStructure : sysTableStructures) {
						FieldInfo fieldInfo = new FieldInfo();
						fieldInfo = sysTableStructure.getFieldInfo();
						if (fieldInfo.getStatistics()) {
							fieldInfo.setFieldName(sysTableStructure.getName());
							fieldInfoes.add(fieldInfo);
						}
					}
				}

			} else {
				filters.add(new PropertyFilter("EQB_isPkCol", "0"));
				filters.add(new PropertyFilter("EQB_disabled", "0"));
				filters.add(new PropertyFilter("EQS_bdInfo.bdInfoId", bdInfoId));
				filters.add(new PropertyFilter("EQB_statistics", "1"));
				fieldInfoes = this.fieldInfoManager.getByFilters(filters);
			}
		}
		return SUCCESS;
	}

	public StatisticsItemManager getStatisticsItemManager() {
		return statisticsItemManager;
	}

	public void setStatisticsItemManager(
			StatisticsItemManager statisticsItemManager) {
		this.statisticsItemManager = statisticsItemManager;
	}

	public BdInfoManager getBdInfoManager() {
		return bdInfoManager;
	}

	public void setBdInfoManager(BdInfoManager bdInfoManager) {
		this.bdInfoManager = bdInfoManager;
	}

	public List<BdInfo> getBdInfoes() {
		return bdInfoes;
	}

	public void setBdInfoes(List<BdInfo> bdInfoes) {
		this.bdInfoes = bdInfoes;
	}

	public List<SysTableContent> getSysTableContents() {
		return sysTableContents;
	}

	public void setSysTableContents(List<SysTableContent> sysTableContents) {
		this.sysTableContents = sysTableContents;
	}

	public SysTableContentManager getSysTableContentManager() {
		return sysTableContentManager;
	}

	public void setSysTableContentManager(
			SysTableContentManager sysTableContentManager) {
		this.sysTableContentManager = sysTableContentManager;
	}

	public String getSubSetType() {
		return subSetType;
	}

	public void setSubSetType(String subSetType) {
		this.subSetType = subSetType;
	}

	public String getBdInfoId() {
		return bdInfoId;
	}

	public void setBdInfoId(String bdInfoId) {
		this.bdInfoId = bdInfoId;
	}

	public FieldInfoManager getFieldInfoManager() {
		return fieldInfoManager;
	}

	public void setFieldInfoManager(FieldInfoManager fieldInfoManager) {
		this.fieldInfoManager = fieldInfoManager;
	}

	public List<FieldInfo> getFieldInfoes() {
		return fieldInfoes;
	}

	public void setFieldInfoes(List<FieldInfo> fieldInfoes) {
		this.fieldInfoes = fieldInfoes;
	}

	public List<SysTableStructure> getSysTableStructures() {
		return sysTableStructures;
	}

	public void setSysTableStructures(List<SysTableStructure> sysTableStructures) {
		this.sysTableStructures = sysTableStructures;
	}

	public SysTableStructureManager getSysTableStructureManager() {
		return sysTableStructureManager;
	}

	public void setSysTableStructureManager(
			SysTableStructureManager sysTableStructureManager) {
		this.sysTableStructureManager = sysTableStructureManager;
	}

	public String getStatisticsSql() {
		return statisticsSql;
	}

	public void setStatisticsSql(String statisticsSql) {
		this.statisticsSql = statisticsSql;
	}

	public Map<String, String> getChartXMLMap() {
		return chartXMLMap;
	}

	public void setChartXMLMap(Map<String, String> chartXMLMap) {
		this.chartXMLMap = chartXMLMap;
	}

	public String getItemIdSecond() {
		return itemIdSecond;
	}

	public void setItemIdSecond(String itemIdSecond) {
		this.itemIdSecond = itemIdSecond;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getGridAllDatas() {
		return gridAllDatas;
	}

	public void setGridAllDatas(String gridAllDatas) {
		this.gridAllDatas = gridAllDatas;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getMccKeyId() {
		return mccKeyId;
	}

	public void setMccKeyId(String mccKeyId) {
		this.mccKeyId = mccKeyId;
	}

	public String getConditionIds() {
		return conditionIds;
	}

	public void setConditionIds(String conditionIds) {
		this.conditionIds = conditionIds;
	}

	public String getConditionSecondIds() {
		return conditionSecondIds;
	}

	public void setConditionSecondIds(String conditionSecondIds) {
		this.conditionSecondIds = conditionSecondIds;
	}

	public String getSearchDateFrom() {
		return searchDateFrom;
	}

	public void setSearchDateFrom(String searchDateFrom) {
		this.searchDateFrom = searchDateFrom;
	}

	public String getSearchDateTo() {
		return searchDateTo;
	}

	public void setSearchDateTo(String searchDateTo) {
		this.searchDateTo = searchDateTo;
	}

	public String getShijianFK() {
		return shijianFK;
	}

	public void setShijianFK(String shijianFK) {
		this.shijianFK = shijianFK;
	}

	public String getHisTime() {
		return hisTime;
	}

	public void setHisTime(String hisTime) {
		this.hisTime = hisTime;
	}
}
