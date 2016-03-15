package com.huge.ihos.hr.statistics.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap;
import com.huge.ihos.hr.hrDeptment.service.HrDepartmentSnapManager;
import com.huge.ihos.hr.hrOrg.model.HrOrgSnap;
import com.huge.ihos.hr.hrOrg.service.HrOrgSnapManager;
import com.huge.ihos.hr.statistics.dao.StatisticsConditionDao;
import com.huge.ihos.hr.statistics.dao.StatisticsItemDao;
import com.huge.ihos.hr.statistics.dao.StatisticsTargetDao;
import com.huge.ihos.hr.statistics.model.StatisticsCondition;
import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.ihos.hr.statistics.model.StatisticsJsonData;
import com.huge.ihos.hr.statistics.model.StatisticsResultData;
import com.huge.ihos.hr.statistics.model.StatisticsTarget;
import com.huge.ihos.system.configuration.bdinfo.dao.BdInfoDao;
import com.huge.ihos.system.configuration.bdinfo.dao.FieldInfoDao;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.ihos.system.configuration.bdinfo.util.BdInfoUtil;
import com.huge.ihos.system.reportManager.chart.dao.MccKeyDao;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
import com.jfusionchart.chart.ChartFactory;
import com.jfusionchart.chart.JFusionChart;
import com.jfusionchart.chart.plot.Plot;
import com.jfusionchart.data.CategoryDataset;
import com.jfusionchart.data.Dataset;
import com.jfusionchart.data.Dataset2D;
import com.jfusionchart.data.DefaultDataset;

@Repository("statisticsTargetDao")
public class StatisticsTargetDaoHibernate extends
		GenericDaoHibernate<StatisticsTarget, String> implements
		StatisticsTargetDao {

	private StatisticsConditionDao statisticsConditionDao;
	private FieldInfoDao fieldInfoDao;
	private BdInfoDao bdInfoDao;
	private StatisticsItemDao statisticsItemDao;
	private MccKeyDao mccKeyDao;
	public ScriptEngineManager mgr = new ScriptEngineManager();
	public ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");

	private HrOrgSnapManager hrOrgSnapManager;
	private HrDepartmentSnapManager hrDepartmentSnapManager;

	public StatisticsTargetDaoHibernate() {
		super(StatisticsTarget.class);
	}

	@Autowired
	public void setStatisticsConditionDao(
			StatisticsConditionDao statisticsConditionDao) {
		this.statisticsConditionDao = statisticsConditionDao;
	}

	@Autowired
	public void setHrOrgSnapManager(HrOrgSnapManager hrOrgSnapManager) {
		this.hrOrgSnapManager = hrOrgSnapManager;
	}

	@Autowired
	public void setHrDepartmentSnapManager(
			HrDepartmentSnapManager hrDepartmentSnapManager) {
		this.hrDepartmentSnapManager = hrDepartmentSnapManager;
	}

	@Autowired
	public void setFieldInfoDao(FieldInfoDao fieldInfoDao) {
		this.fieldInfoDao = fieldInfoDao;
	}

	@Autowired
	public void setStatisticsItemDao(StatisticsItemDao statisticsItemDao) {
		this.statisticsItemDao = statisticsItemDao;
	}

	@Autowired
	public void setMccKeyDao(MccKeyDao mccKeyDao) {
		this.mccKeyDao = mccKeyDao;
	}

	@Autowired
	public void setBdInfoDao(BdInfoDao bdInfoDao) {
		this.bdInfoDao = bdInfoDao;
	}

	public JQueryPager getStatisticsTargetCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, StatisticsTarget.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getStatisticsTargetCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveStatisticsTargetGridData(String gridAllDatas,
			String conditionId, Person person) {
		JSONObject json = JSONObject.fromObject(gridAllDatas);
		JSONArray allDatas = json.getJSONArray("edit");
		String hql = "select id from "
				+ this.getPersistentClass().getSimpleName()
				+ " where  parentCondition.id=?";
		hql += " ORDER BY id ASC";
		List<Object> ids = new ArrayList<Object>();
		ids = this.getHibernateTemplate().find(hql, conditionId);
		String id = "";
		for (int i = 0; i < allDatas.size(); i++) {
			// 获取每一个JsonObject对象
			JSONObject myjObject = allDatas.getJSONObject(i);
			if (myjObject.size() > 0) {
				StatisticsTarget statisticsTarget = new StatisticsTarget();
				id = myjObject.getString("id");
				if (id != null && !id.equals("")) {
					statisticsTarget = this.get(id);
					ids.remove(id);
				}
				statisticsTarget.setName(myjObject.getString("name"));
				statisticsTarget.setOperation(myjObject.getString("operation"));
				statisticsTarget.setTargetValue(myjObject
						.getString("targetValue"));
				statisticsTarget.setFieldInfo(fieldInfoDao.get(myjObject
						.getString("fieldInfoId")));
				statisticsTarget.setTableName(myjObject.getString("tableName"));
				statisticsTarget.setParentCondition(statisticsConditionDao
						.get(conditionId));
				statisticsTarget.setChangeUser(person);
				statisticsTarget.setChangeDate(new Date());
				this.save(statisticsTarget);
			}
		}
		String[] ida = new String[ids.size()];
		ids.toArray(ida);
		this.remove(ida);
	}

	@Override
	public Object[] statisticsFullData(String itemId) {
		List<StatisticsResultData> statisticsResultDataList = new ArrayList<StatisticsResultData>();// 返回结果List;StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
		Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
		Map<String, String> fieldAlias = new HashMap<String, String>();// sql中出现的字段别名(Key为表别名.字段，value为字段别名)
		Map<String, String> conditionJsStrMap = new LinkedHashMap<String, String>();// JS字符串(Key为统计条件名称，value为该统计条件下JS串)
		List<String> tempList = new ArrayList<String>();
		Map<String, List<String>> likeMap = new HashMap<String, List<String>>();// Like操作符(Key为like的自定义名称，value为字段别名和统计指标查询值)
		int likeNum = 1;
		List<String> joinTables = new ArrayList<String>();// 需要使用left join的表
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		StatisticsItem statisticsItem = new StatisticsItem();
		statisticsItem = statisticsItemDao.get(itemId);
		String sql = "SELECT ";
		int fieldAliasNum = 1;
		int tableAliasNum = 1;
		String mainTable = statisticsItem.getStatisticsBdInfo().getBdInfo()
				.getTableName();// 统计项表
		String mainTablePk = "";
		BdInfoUtil bdInfoUtil = new BdInfoUtil(statisticsItem
				.getStatisticsBdInfo().getBdInfo());
		if (OtherUtil.measureNotNull(bdInfoUtil.getPkCol())) {
			mainTablePk = bdInfoUtil.getPkCol().getFieldCode();
		}

		// statisticsItem.getStatisticsBdInfo().getBdInfo().getTablePkName();//统计项表主键
		tableAlias.put(mainTable, "mainTable");
		fieldAlias.put(tableAlias.get(mainTable) + "." + mainTablePk,
				"mainField");
		if (statisticsItem.getStatisticsFieldInfo() != null
				&& !statisticsItem.getStatisticsFieldInfo().equals("")) {// 有统计字段的情况
			if (!statisticsItem.getStatisticsFieldInfo().equals(mainTablePk)) {// 统计字段与统计项表主键不同时
				fieldAlias.put(
						tableAlias.get(mainTable) + "."
								+ statisticsItem.getStatisticsFieldInfo(),
						"statisticsField");
			}
		}
		if (statisticsItem.getDynamicStatistics()) {// 动态统计
			tableAlias.put(statisticsItem.getDynamicTable(), "dynamicTable");
			fieldAlias.put(tableAlias.get(statisticsItem.getDynamicTable())
					+ "." + statisticsItem.getDynamicCode(), "dynamicField");
			Set<String> fieldKey = fieldAlias.keySet();
			for (Iterator it = fieldKey.iterator(); it.hasNext();) {
				String keyTemp = (String) it.next();
				sql += keyTemp + " AS " + fieldAlias.get(keyTemp) + ",";
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable)
					+ " ";
			sql += " JOIN " + statisticsItem.getDynamicTable() + " AS "
					+ tableAlias.get(statisticsItem.getDynamicTable()) + " ON ";
			sql += tableAlias.get(statisticsItem.getDynamicTable()) + "."
					+ statisticsItem.getDynamicTablePk() + "="
					+ tableAlias.get(mainTable) + "."
					+ statisticsItem.getDynamicTableForeignKey() + " ";
			if (statisticsItem.getDynamicFilterSql() != null
					&& !statisticsItem.getDynamicFilterSql().equals("")) {// 动态统计过滤条件
				sql += " WHERE "
						+ statisticsItem
								.getDynamicFilterSql()
								.replaceAll(
										statisticsItem.getDynamicTable(),
										tableAlias.get(statisticsItem
												.getDynamicTable()))
								.replaceAll(mainTable,
										tableAlias.get(mainTable));
			}
		} else {
			List<StatisticsCondition> statisticsConditions = new ArrayList<StatisticsCondition>();
			filters.add(new PropertyFilter("EQS_parentItem.id", itemId));
			filters.add(new PropertyFilter("OAS_sn", "sn"));
			statisticsConditions = statisticsConditionDao.getByFilters(filters);
			String filterSql = "";
			String sqlEnd = "";
			String jsStr = "";
			if (statisticsConditions != null && statisticsConditions.size() > 0) {
				for (StatisticsCondition statisticsCondition : statisticsConditions) {
					List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
					expressionList = getExpressionListEffectDigital(statisticsCondition
							.getExpression());
					filters.clear();
					List<StatisticsTarget> statisticsTargets = new ArrayList<StatisticsTarget>();
					filters.add(new PropertyFilter("EQS_parentCondition.id",
							statisticsCondition.getId()));
					filters.add(new PropertyFilter("OAS_id", "id"));
					statisticsTargets = this.getByFilters(filters);
					Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
					String itrNext = "";
					String tableName = "";
					String field = "";
					while (itr.hasNext()) {
						itrNext = itr.next();
						if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
							try {
								int num = Integer.valueOf(itrNext);
								if (num > 0 && num <= statisticsTargets.size()) {
									StatisticsTarget statisticsTarget = new StatisticsTarget();
									statisticsTarget = statisticsTargets
											.get(num - 1);
									tableName = statisticsTarget.getFieldInfo()
											.getBdInfo().getTableName();
									field = statisticsTarget.getFieldInfo()
											.getFieldCode();
									if (tableName.equals(mainTable)) {// 统计指标
																		// 表与统计项表相同
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									} else {// 统计指标 表与统计项表不同
										if (!tableAlias.containsKey(tableName)) {
											joinTables.add(tableName);
											tableAlias.put(tableName, "table"
													+ tableAliasNum);
											tableAliasNum++;
										}
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									}
									if (statisticsTarget.getOperation().equals(
											"like")) {// 统计指标操作符为Like时
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ " "
												+ statisticsTarget
														.getOperation()
												+ " '%"
												+ statisticsTarget
														.getTargetValue()
												+ "%' ";
										jsStr += "LikeReplace" + likeNum;
										tempList.clear();
										tempList.add(fieldAlias.get(tableAlias
												.get(tableName) + "." + field));
										tempList.add(statisticsTarget
												.getTargetValue());
										likeMap.put("LikeReplace" + likeNum,
												tempList);
										likeNum++;
									} else {// 统计指标操作符为其他字符时
										jsStr += "'"
												+ fieldAlias.get(tableAlias
														.get(tableName)
														+ "."
														+ field) + "'";
										if (statisticsTarget.getOperation()
												.equals("=")) {
											jsStr += "==";
										} else if (statisticsTarget
												.getOperation().equals("<>")) {
											jsStr += "!=";
										} else {
											jsStr += statisticsTarget
													.getOperation();
										}
										jsStr += "'"
												+ statisticsTarget
														.getTargetValue() + "'";
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ statisticsTarget
														.getOperation()
												+ "'"
												+ statisticsTarget
														.getTargetValue()
												+ "' ";
									}
								}
							} catch (Exception e) {
							}

						} else if (itrNext.equals("(") || itrNext.equals(")")) {
							filterSql += itrNext + " ";
							jsStr += itrNext;
						} else if (itrNext.equals("+")) {
							filterSql += "or ";
							jsStr += "||";
						} else if (itrNext.equals("*")) {
							filterSql += "and ";
							jsStr += "&&";
						}
					}
					conditionJsStrMap.put(statisticsCondition.getName(), jsStr);
					if (!filterSql.equals("")) {
						sqlEnd += " " + filterSql + " or";
					}
					filterSql = "";
					jsStr = "";
				}
			}
			Set<String> fieldKey = fieldAlias.keySet(); // 需要查询的字段和别名依次加入sql中
			for (Iterator it = fieldKey.iterator(); it.hasNext();) {
				String keyTemp = (String) it.next();
				sql += keyTemp + " AS " + fieldAlias.get(keyTemp) + ",";
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable)
					+ " ";
			for (String joinTableTemp : joinTables) {// 需要join的表
				sql += " LEFT JOIN " + joinTableTemp + " AS "
						+ tableAlias.get(joinTableTemp);
				sql += " ON " + tableAlias.get(joinTableTemp) + "."
						+ mainTablePk + "=" + tableAlias.get(mainTable) + "."
						+ mainTablePk + " ";
			}
			if (sqlEnd.length() > 2) {// where条件
				if (sqlEnd.substring(sqlEnd.length() - 2, sqlEnd.length())
						.equals("or")) {
					sqlEnd = sqlEnd.substring(0, sqlEnd.length() - 2);
				}
				sql += " WHERE " + sqlEnd;
			}
		}
		List<String> resultKeyList = new ArrayList<String>();
		List<Map<String, Object>> queryDataList = new ArrayList<Map<String, Object>>();
		queryDataList = getBySqlToMap(sql);
		if (statisticsItem.getDynamicStatistics()) {// 动态统计；每一条记录都加入结果集
			String dynamicField = fieldAlias
					.get(tableAlias.get(statisticsItem.getDynamicTable()) + "."
							+ statisticsItem.getDynamicCode());
			if (queryDataList != null && queryDataList.size() > 0) {
				for (Map<String, Object> dataMapTemp : queryDataList) {
					String dynamicFieldValue = dataMapTemp.get(dynamicField)
							.toString();
					StatisticsResultData statisticsResultData = new StatisticsResultData();
					statisticsResultData.setResultType(dynamicFieldValue);
					statisticsResultData.setMainField(dataMapTemp.get(
							fieldAlias.get(tableAlias.get(mainTable) + "."
									+ mainTablePk)).toString());
					if (statisticsItem.getStatisticsFieldInfo() != null
							&& !statisticsItem.getStatisticsFieldInfo().equals(
									"")) {
						if (!statisticsItem.getStatisticsFieldInfo().equals(
								mainTablePk)) {
							statisticsResultData.setStatisticsField(dataMapTemp
									.get(fieldAlias.get(tableAlias
											.get(mainTable)
											+ "."
											+ statisticsItem
													.getStatisticsFieldInfo()))
									.toString());
						} else {
							statisticsResultData
									.setStatisticsField(dataMapTemp.get(
											fieldAlias.get(tableAlias
													.get(mainTable)
													+ "."
													+ mainTablePk)).toString());
						}
					}
					if (!resultKeyList.contains(dynamicFieldValue)) {
						resultKeyList.add(dynamicFieldValue);
					}
					statisticsResultDataList.add(statisticsResultData);
				}
			}
		} else {
			try {
				Set<String> conditionKey = conditionJsStrMap.keySet();
				for (Iterator it = conditionKey.iterator(); it.hasNext();) {
					String conditionKeyTemp = (String) it.next();
					if (!resultKeyList.contains(conditionKeyTemp)) {
						resultKeyList.add(conditionKeyTemp);
					}
					if (queryDataList != null && queryDataList.size() > 0) {// 遍历SQL查询的结果
						for (Map<String, Object> dataMapTemp : queryDataList) {
							String jsEvalStr = conditionJsStrMap
									.get(conditionKeyTemp);
							if (jsEvalStr.contains("LikeReplace")) {// JS串中like处理
								Set<String> likeMapKey = likeMap.keySet();
								for (Iterator likeMapIt = likeMapKey.iterator(); likeMapIt
										.hasNext();) {
									String likeMapKeyTemp = (String) likeMapIt
											.next();
									List<String> likeTempList = new ArrayList<String>();
									likeTempList = likeMap.get(likeMapKeyTemp);
									jsEvalStr = jsEvalStr
											.replaceAll(
													likeMapKeyTemp,
													(dataMapTemp
															.get(likeTempList
																	.get(0)) == null ? ""
															: dataMapTemp
																	.get(likeTempList
																			.get(0)))
															.toString()
															.contains(
																	likeTempList
																			.get(1))
															+ "");
								}
							}
							Set<String> dataMapKey = dataMapTemp.keySet();
							for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
									.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
								String dataMapKeyTemp = (String) dataMapIt
										.next();
								jsEvalStr = jsEvalStr
										.replaceAll(
												dataMapKeyTemp,
												dataMapTemp.get(dataMapKeyTemp) == null ? ""
														: dataMapTemp.get(
																dataMapKeyTemp)
																.toString());
							}
							Object rs = jsEngine.eval(jsEvalStr);
							Boolean rsBoolean = Boolean.parseBoolean(rs
									.toString());
							if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
								StatisticsResultData statisticsResultData = new StatisticsResultData();
								statisticsResultData
										.setResultType(conditionKeyTemp);
								statisticsResultData.setMainField(dataMapTemp
										.get(fieldAlias.get(tableAlias
												.get(mainTable)
												+ "."
												+ mainTablePk)).toString());
								if (statisticsItem.getStatisticsFieldInfo() != null
										&& !statisticsItem
												.getStatisticsFieldInfo()
												.equals("")) {// statisticsField统计项字段非空时的统计项字段查询值
									if (!statisticsItem
											.getStatisticsFieldInfo().equals(
													mainTablePk)) {
										statisticsResultData
												.setStatisticsField(dataMapTemp
														.get(fieldAlias.get(tableAlias
																.get(mainTable)
																+ "."
																+ statisticsItem
																		.getStatisticsFieldInfo()))
														.toString());
									} else {
										statisticsResultData
												.setStatisticsField(dataMapTemp
														.get(fieldAlias.get(tableAlias
																.get(mainTable)
																+ "."
																+ mainTablePk))
														.toString());
									}
								}
								statisticsResultDataList
										.add(statisticsResultData);
							}
						}
					}

				}
			} catch (ScriptException e) {
			}
		}
		Object[] resultObj = new Object[2];
		resultObj[0] = statisticsResultDataList;
		resultObj[1] = resultKeyList;
		return resultObj;
	}

	@Override
	public Object[] statisticsFullChartDataset(
			List<StatisticsResultData> statisticsResultDataList,
			List<String> keyList) {
		Dataset dataset = new DefaultDataset();
		Map<String, List<String>> resultDataTypeMap = new HashMap<String, List<String>>();
		if (statisticsResultDataList != null
				&& statisticsResultDataList.size() > 0) {
			for (StatisticsResultData statisticsResultDataTemp : statisticsResultDataList) {
				if (resultDataTypeMap.containsKey(statisticsResultDataTemp
						.getResultType())) {
					List<String> tempList = new ArrayList<String>();
					tempList = resultDataTypeMap.get(statisticsResultDataTemp
							.getResultType());
					if (!tempList.contains(statisticsResultDataTemp
							.getMainField())) {
						tempList.add(statisticsResultDataTemp.getMainField());
					}
					resultDataTypeMap.put(
							statisticsResultDataTemp.getResultType(), tempList);
				} else {
					List<String> tempList = new ArrayList<String>();
					tempList.add(statisticsResultDataTemp.getMainField());
					resultDataTypeMap.put(
							statisticsResultDataTemp.getResultType(), tempList);
				}
			}
		}
		List<StatisticsJsonData> jsonDatas = new ArrayList<StatisticsJsonData>();
		int sum = 0;
		if (keyList != null && keyList.size() > 0) {
			for (String keyTemp : keyList) {
				if (resultDataTypeMap.keySet().contains(keyTemp)) {
					dataset.addValue(keyTemp, resultDataTypeMap.get(keyTemp)
							.size(), null);
					StatisticsJsonData statisticsJsonData = new StatisticsJsonData();
					statisticsJsonData.setColumnName(keyTemp);
					statisticsJsonData.setRowName("统计值");
					statisticsJsonData.setValue(resultDataTypeMap.get(keyTemp)
							.size() + "");
					jsonDatas.add(statisticsJsonData);
					sum = sum + resultDataTypeMap.get(keyTemp).size();
				} else {
					dataset.addValue(keyTemp, 0, null);
					StatisticsJsonData statisticsJsonData = new StatisticsJsonData();
					statisticsJsonData.setColumnName(keyTemp);
					statisticsJsonData.setRowName("统计值");
					statisticsJsonData.setValue("0");
					jsonDatas.add(statisticsJsonData);
				}
			}
		}
		StatisticsJsonData statisticsJsonData = new StatisticsJsonData();
		statisticsJsonData.setColumnName("合计");
		statisticsJsonData.setRowName("统计值");
		statisticsJsonData.setValue(sum + "");
		jsonDatas.add(statisticsJsonData);
		JSONArray jsonarray = JSONArray.fromObject(jsonDatas);
		Object[] retData = new Object[2];
		retData[0] = dataset;
		retData[1] = jsonarray.toString();
		return retData;
	}

	@Override
	public Map<String, String> statisticsFullChartXMLMap(Dataset dataSet,
			String mccKeyId, String jsonStr, String subTitle) throws Exception {
		MccKey mccKey = new MccKey();
		mccKey = mccKeyDao.get(mccKeyId);
		String title = mccKey.getCaption();
		Boolean showValues = mccKey.getShowValues();
		char numberPrefix = ' ';
		char numberSuffix = ' ';
		if (mccKey.getNumberPrefix() != null
				&& mccKey.getNumberPrefix().length() > 0) {
			numberPrefix = mccKey.getNumberPrefix().trim().charAt(0);
		}
		if (mccKey.getNumberSuffix() != null
				&& mccKey.getNumberSuffix().length() > 0) {
			numberSuffix = mccKey.getNumberSuffix().trim().charAt(0);
		}
		Map<String, String> fullChartXMLMap = new HashMap<String, String>();
		if (dataSet.getLabels() == null || dataSet.getLabels().size() == 0) {
			fullChartXMLMap.put("pieChart", null);
			fullChartXMLMap.put("column2DSingleChart", null);
			fullChartXMLMap.put("line2DSingleChart", null);
			fullChartXMLMap.put("jsonStr", jsonStr);
			fullChartXMLMap.put("title", title);
			fullChartXMLMap.put("subTitle", subTitle);
			fullChartXMLMap.put("baseFontSize", mccKey.getBaseFontSize());
		} else {
			try {
				String baseFontSizeStr = mccKey.getBaseFontSize();
				Integer baseFontSize = 0;
				if (OtherUtil.measureNotNull(baseFontSizeStr)) {
					baseFontSize = Integer.parseInt(mccKey.getBaseFontSize());
				}
				JFusionChart pieChart = ChartFactory.createPieChart(dataSet,
						title, subTitle, showValues, numberPrefix == ' ' ? null
								: numberPrefix);
				Plot plot = (Plot) pieChart.getPlot();
				plot.setNumberSuffix(numberSuffix == ' ' ? null : numberSuffix);
				plot.setAnimation(true);
				if (baseFontSize != 0) {
					plot.setBaseFontSize(baseFontSize);
				}
				JFusionChart column2DSingleChart = ChartFactory
						.createColumn2DSingleChart(dataSet, title, subTitle,
								mccKey.getxAxisName(), mccKey.getYAxisName(),
								showValues, numberPrefix == ' ' ? null
										: numberPrefix);
				plot = (Plot) column2DSingleChart.getPlot();
				plot.setNumberSuffix(numberSuffix == ' ' ? null : numberSuffix);
				plot.setAnimation(true);
				if (baseFontSize != 0) {
					plot.setBaseFontSize(baseFontSize);
				}
				JFusionChart line2DSingleChart = ChartFactory
						.createLine2DSingleChart(dataSet, title, subTitle,
								mccKey.getxAxisName(), mccKey.getYAxisName(),
								showValues, numberPrefix == ' ' ? null
										: numberPrefix);
				plot = (Plot) line2DSingleChart.getPlot();
				plot.setNumberSuffix(numberSuffix == ' ' ? null : numberSuffix);
				plot.setBorderThickness(1);
				plot.setAnimation(true);
				if (baseFontSize != 0) {
					plot.setBaseFontSize(baseFontSize);
				}
				fullChartXMLMap.put("pieChart", pieChart.toFormatXML());
				fullChartXMLMap.put("column2DSingleChart",
						column2DSingleChart.toFormatXML());
				fullChartXMLMap.put("line2DSingleChart",
						line2DSingleChart.toFormatXML());
				fullChartXMLMap.put("jsonStr", jsonStr);
				fullChartXMLMap.put("title", title);
				fullChartXMLMap.put("subTitle", subTitle);
				fullChartXMLMap.put("baseFontSize", mccKey.getBaseFontSize());
			} catch (Exception e) {
				throw e;
			}
		}
		return fullChartXMLMap;
	}

	@Override
	public Object[] statisticsFullData2D(String itemId, String itemIdSecond)
			throws Exception {
		List<StatisticsResultData> statisticsResultDataList = new ArrayList<StatisticsResultData>();// 返回结果List;StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
		Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
		Map<String, String> fieldAlias = new HashMap<String, String>();// sql中出现的字段别名(Key为表别名.字段，value为字段别名)
		Map<String, String> conditionJsStrMap = new LinkedHashMap<String, String>();// JS字符串(Key为统计条件名称，value为该统计条件下JS串)
		Map<String, String> conditionJsStrMapSecond = new LinkedHashMap<String, String>();
		List<String> tempList = new ArrayList<String>();
		Map<String, List<String>> likeMap = new HashMap<String, List<String>>();// Like操作符(Key为like的自定义名称，value为字段别名和统计指标查询值)
		int likeNum = 1;
		List<String> joinTables = new ArrayList<String>();// 需要使用left join的表
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		StatisticsItem statisticsItem = new StatisticsItem();
		StatisticsItem statisticsItemSecond = new StatisticsItem();
		statisticsItem = statisticsItemDao.get(itemId);
		statisticsItemSecond = statisticsItemDao.get(itemIdSecond);
		String sql = "SELECT ";
		String dynamicFilterSql = "";
		String sqlEnd = "";
		String sqlEndSecond = "";
		String dynamicFieldAlia = "";
		String dynamicFieldAliaSecond = "";
		String dynamicJoinSql = "";
		int fieldAliasNum = 1;
		int tableAliasNum = 1;
		String mainTable = statisticsItem.getStatisticsBdInfo().getBdInfo()
				.getTableName();// 统计项表
		String mainTablePk = "";
		BdInfoUtil bdInfoUtil = new BdInfoUtil(statisticsItem
				.getStatisticsBdInfo().getBdInfo());
		if (OtherUtil.measureNotNull(bdInfoUtil.getPkCol())) {
			mainTablePk = bdInfoUtil.getPkCol().getFieldCode();
		}
		// String
		// mainTablePk=statisticsItem.getStatisticsBdInfo().getBdInfo().getTablePkName();//统计项表主键
		tableAlias.put(mainTable, "mainTable");
		fieldAlias.put(tableAlias.get(mainTable) + "." + mainTablePk,
				"mainField");
		if (statisticsItem.getDynamicStatistics()) {// 动态统计
			String dynamicTable = statisticsItem.getDynamicTable();
			if (!tableAlias.containsKey(dynamicTable)) {
				tableAlias.put(dynamicTable, "dynamicTable");
				joinTables.add(dynamicTable);
			}
			String dynamicField = tableAlias.get(dynamicTable) + "."
					+ statisticsItem.getDynamicCode();
			if (!fieldAlias.containsKey(dynamicField)) {
				fieldAlias.put(dynamicField, "dynamicField");
			}
			dynamicJoinSql = dynamicJoinSql + " LEFT JOIN " + dynamicTable
					+ " AS " + tableAlias.get(dynamicTable);
			dynamicJoinSql = dynamicJoinSql + " ON "
					+ tableAlias.get(dynamicTable) + "."
					+ statisticsItem.getDynamicTablePk() + "="
					+ tableAlias.get(mainTable) + "."
					+ statisticsItem.getDynamicTableForeignKey();
			dynamicFieldAlia = fieldAlias.get(dynamicField);
			if (statisticsItem.getDynamicFilterSql() != null
					&& !statisticsItem.getDynamicFilterSql().equals("")) {// 动态统计过滤条件
				dynamicFilterSql += statisticsItem.getDynamicFilterSql()
						.replaceAll(dynamicTable, tableAlias.get(dynamicTable))
						.replaceAll(mainTable, tableAlias.get(mainTable));
			}
		} else {
			filters.clear();
			List<StatisticsCondition> statisticsConditions = new ArrayList<StatisticsCondition>();
			filters.add(new PropertyFilter("EQS_parentItem.id", itemId));
			filters.add(new PropertyFilter("OAS_sn", "sn"));
			statisticsConditions = statisticsConditionDao.getByFilters(filters);
			String filterSql = "";
			String jsStr = "";
			if (statisticsConditions != null && statisticsConditions.size() > 0) {
				for (StatisticsCondition statisticsCondition : statisticsConditions) {
					List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
					expressionList = getExpressionListEffectDigital(statisticsCondition
							.getExpression());
					filters.clear();
					List<StatisticsTarget> statisticsTargets = new ArrayList<StatisticsTarget>();
					filters.add(new PropertyFilter("EQS_parentCondition.id",
							statisticsCondition.getId()));
					filters.add(new PropertyFilter("OAS_id", "id"));
					statisticsTargets = this.getByFilters(filters);
					Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
					String itrNext = "";
					String tableName = "";
					String field = "";
					while (itr.hasNext()) {
						itrNext = itr.next();
						if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
							try {
								int num = Integer.valueOf(itrNext);
								if (num > 0 && num <= statisticsTargets.size()) {
									StatisticsTarget statisticsTarget = new StatisticsTarget();
									statisticsTarget = statisticsTargets
											.get(num - 1);
									tableName = statisticsTarget.getFieldInfo()
											.getBdInfo().getTableName();
									field = statisticsTarget.getFieldInfo()
											.getFieldCode();
									if (tableName.equals(mainTable)) {// 统计指标
																		// 表与统计项表相同
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									} else {// 统计指标 表与统计项表不同
										if (!tableAlias.containsKey(tableName)) {
											joinTables.add(tableName);
											tableAlias.put(tableName, "table"
													+ tableAliasNum);
											tableAliasNum++;
										}
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									}
									if (statisticsTarget.getOperation().equals(
											"like")) {// 统计指标操作符为Like时
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ " "
												+ statisticsTarget
														.getOperation()
												+ " '%"
												+ statisticsTarget
														.getTargetValue()
												+ "%' ";
										jsStr += "LikeReplace" + likeNum;
										tempList.clear();
										tempList.add(fieldAlias.get(tableAlias
												.get(tableName) + "." + field));
										tempList.add(statisticsTarget
												.getTargetValue());
										likeMap.put("LikeReplace" + likeNum,
												tempList);
										likeNum++;
									} else {// 统计指标操作符为其他字符时
										jsStr += "'"
												+ fieldAlias.get(tableAlias
														.get(tableName)
														+ "."
														+ field) + "'";
										if (statisticsTarget.getOperation()
												.equals("=")) {
											jsStr += "==";
										} else if (statisticsTarget
												.getOperation().equals("<>")) {
											jsStr += "!=";
										} else {
											jsStr += statisticsTarget
													.getOperation();
										}
										jsStr += "'"
												+ statisticsTarget
														.getTargetValue() + "'";
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ statisticsTarget
														.getOperation()
												+ "'"
												+ statisticsTarget
														.getTargetValue()
												+ "' ";
									}
								}
							} catch (NumberFormatException e) {
								throw e;
							}

						} else if (itrNext.equals("(") || itrNext.equals(")")) {
							filterSql += itrNext + " ";
							jsStr += itrNext;
						} else if (itrNext.equals("+")) {
							filterSql += "or ";
							jsStr += "||";
						} else if (itrNext.equals("*")) {
							filterSql += "and ";
							jsStr += "&&";
						}
					}
					conditionJsStrMap.put(statisticsCondition.getName(), jsStr);
					if (!filterSql.equals("")) {
						sqlEnd += " " + filterSql + " or";
					}
					filterSql = "";
					jsStr = "";
				}
			}
		}
		/** ItemSecond **/
		if (statisticsItemSecond.getDynamicStatistics()) {// 动态统计
			String dynamicTable = statisticsItemSecond.getDynamicTable();
			if (!tableAlias.containsKey(dynamicTable)) {
				tableAlias.put(dynamicTable, "dynamicTableSecond");
				joinTables.add(dynamicTable);
			}
			String dynamicField = tableAlias.get(dynamicTable) + "."
					+ statisticsItemSecond.getDynamicCode();
			if (!fieldAlias.containsKey(dynamicField)) {
				fieldAlias.put(dynamicField, "dynamicFieldSecond");
			}
			dynamicFieldAliaSecond = fieldAlias.get(dynamicField);
			dynamicJoinSql = dynamicJoinSql + " LEFT JOIN " + dynamicTable
					+ " AS " + tableAlias.get(dynamicTable);
			dynamicJoinSql = dynamicJoinSql + " ON "
					+ tableAlias.get(dynamicTable) + "."
					+ statisticsItemSecond.getDynamicTablePk() + "="
					+ tableAlias.get(mainTable) + "."
					+ statisticsItemSecond.getDynamicTableForeignKey();
			if (statisticsItemSecond.getDynamicFilterSql() != null
					&& !statisticsItemSecond.getDynamicFilterSql().equals("")) {// 动态统计过滤条件
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += statisticsItemSecond
							.getDynamicFilterSql()
							.replaceAll(dynamicTable,
									tableAlias.get(dynamicTable))
							.replaceAll(mainTable, tableAlias.get(mainTable));
				} else {
					dynamicFilterSql += " AND "
							+ statisticsItemSecond
									.getDynamicFilterSql()
									.replaceAll(dynamicTable,
											tableAlias.get(dynamicTable))
									.replaceAll(mainTable,
											tableAlias.get(mainTable));
				}

			}
		} else {
			filters.clear();
			List<StatisticsCondition> statisticsConditions = new ArrayList<StatisticsCondition>();
			filters.add(new PropertyFilter("EQS_parentItem.id", itemIdSecond));
			filters.add(new PropertyFilter("OAS_sn", "sn"));
			statisticsConditions = statisticsConditionDao.getByFilters(filters);
			String filterSql = "";
			String jsStr = "";
			if (statisticsConditions != null && statisticsConditions.size() > 0) {
				for (StatisticsCondition statisticsCondition : statisticsConditions) {
					List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
					expressionList = getExpressionListEffectDigital(statisticsCondition
							.getExpression());
					filters.clear();
					List<StatisticsTarget> statisticsTargets = new ArrayList<StatisticsTarget>();
					filters.add(new PropertyFilter("EQS_parentCondition.id",
							statisticsCondition.getId()));
					filters.add(new PropertyFilter("OAS_id", "id"));
					statisticsTargets = this.getByFilters(filters);
					Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
					String itrNext = "";
					String tableName = "";
					String field = "";
					while (itr.hasNext()) {
						itrNext = itr.next();
						if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
							try {
								int num = Integer.valueOf(itrNext);
								if (num > 0 && num <= statisticsTargets.size()) {
									StatisticsTarget statisticsTarget = new StatisticsTarget();
									statisticsTarget = statisticsTargets
											.get(num - 1);
									tableName = statisticsTarget.getFieldInfo()
											.getBdInfo().getTableName();
									field = statisticsTarget.getFieldInfo()
											.getFieldCode();
									if (tableName.equals(mainTable)) {// 统计指标
																		// 表与统计项表相同
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									} else {// 统计指标 表与统计项表不同
										if (!tableAlias.containsKey(tableName)) {
											joinTables.add(tableName);
											tableAlias.put(tableName, "table"
													+ tableAliasNum);
											tableAliasNum++;
										}
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									}
									if (statisticsTarget.getOperation().equals(
											"like")) {// 统计指标操作符为Like时
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ " "
												+ statisticsTarget
														.getOperation()
												+ " '%"
												+ statisticsTarget
														.getTargetValue()
												+ "%' ";
										jsStr += "LikeReplace" + likeNum;
										tempList.clear();
										tempList.add(fieldAlias.get(tableAlias
												.get(tableName) + "." + field));
										tempList.add(statisticsTarget
												.getTargetValue());
										likeMap.put("LikeReplace" + likeNum,
												tempList);
										likeNum++;
									} else {// 统计指标操作符为其他字符时
										jsStr += "'"
												+ fieldAlias.get(tableAlias
														.get(tableName)
														+ "."
														+ field) + "'";
										if (statisticsTarget.getOperation()
												.equals("=")) {
											jsStr += "==";
										} else if (statisticsTarget
												.getOperation().equals("<>")) {
											jsStr += "!=";
										} else {
											jsStr += statisticsTarget
													.getOperation();
										}
										jsStr += "'"
												+ statisticsTarget
														.getTargetValue() + "'";
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ statisticsTarget
														.getOperation()
												+ "'"
												+ statisticsTarget
														.getTargetValue()
												+ "' ";
									}
								}
							} catch (NumberFormatException e) {
								throw e;
							}

						} else if (itrNext.equals("(") || itrNext.equals(")")) {
							filterSql += itrNext + " ";
							jsStr += itrNext;
						} else if (itrNext.equals("+")) {
							filterSql += "or ";
							jsStr += "||";
						} else if (itrNext.equals("*")) {
							filterSql += "and ";
							jsStr += "&&";
						}
					}
					conditionJsStrMapSecond.put(statisticsCondition.getName(),
							jsStr);
					if (!filterSql.equals("")) {
						sqlEndSecond += " " + filterSql + " or";
					}
					filterSql = "";
					jsStr = "";
				}
			}
		}
		/** ItemSecond End **/
		/** Create SQL **/
		Set<String> fieldKey = fieldAlias.keySet(); // 需要查询的字段和别名依次加入sql中
		for (Iterator it = fieldKey.iterator(); it.hasNext();) {
			String keyTemp = (String) it.next();
			sql += keyTemp + " AS " + fieldAlias.get(keyTemp) + ",";
		}
		if (sql.contains(",")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable) + " ";
		for (String joinTableTemp : joinTables) {// 需要join的表
			if (!tableAlias.get(joinTableTemp).contains("dynamicTable")) {
				sql += " LEFT JOIN " + joinTableTemp + " AS "
						+ tableAlias.get(joinTableTemp);
				sql += " ON " + tableAlias.get(joinTableTemp) + "."
						+ mainTablePk + "=" + tableAlias.get(mainTable) + "."
						+ mainTablePk + " ";
			}
		}
		sql += dynamicJoinSql + " ";
		String sqlEndStr = "";
		if (sqlEnd.length() > 2) {// where条件
			if (sqlEnd.substring(sqlEnd.length() - 2, sqlEnd.length()).equals(
					"or")) {
				sqlEnd = sqlEnd.substring(0, sqlEnd.length() - 2);
			}
			sqlEndStr += " WHERE " + sqlEnd;
		}
		if (sqlEndSecond.length() > 2) {// where条件
			if (sqlEndSecond.substring(sqlEndSecond.length() - 2,
					sqlEndSecond.length()).equals("or")) {
				sqlEndSecond = sqlEndSecond.substring(0,
						sqlEndSecond.length() - 2);
			}
			if (!sqlEndStr.equals("")) {
				sqlEndStr += " AND " + sqlEndSecond;
			} else {
				sqlEndStr += " WHERE " + sqlEndSecond;
			}

		}
		if (!dynamicFilterSql.equals("")) {
			if (!sqlEndStr.equals("")) {
				sqlEndStr += " AND " + dynamicFilterSql;
			} else {
				sqlEndStr += " WHERE " + dynamicFilterSql;
			}
		}
		sql += sqlEndStr;
		/** SQL End **/
		List<String> keyList = new ArrayList<String>();
		List<String> keySecondList = new ArrayList<String>();
		List<Map<String, Object>> queryDataList = new ArrayList<Map<String, Object>>();
		queryDataList = getBySqlToMap(sql);
		try {
			if (!dynamicFieldAlia.equals("")
					&& !dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						String dynamicFieldValue = dataMapTemp.get(
								dynamicFieldAlia).toString();
						String dynamicFieldValueSecond = dataMapTemp.get(
								dynamicFieldAliaSecond).toString();
						StatisticsResultData statisticsResultData = new StatisticsResultData();
						statisticsResultData.setResultType(dynamicFieldValue);
						statisticsResultData
								.setResultTypeSecond(dynamicFieldValueSecond);
						statisticsResultData.setMainField(dataMapTemp.get(
								fieldAlias.get(tableAlias.get(mainTable) + "."
										+ mainTablePk)).toString());
						statisticsResultDataList.add(statisticsResultData);
						if (!keyList.contains(dynamicFieldValue)) {
							keyList.add(dynamicFieldValue);
						}
						if (!keySecondList.contains(dynamicFieldValueSecond)) {
							keySecondList.add(dynamicFieldValueSecond);
						}
					}
				}
			} else if (!dynamicFieldAlia.equals("")
					&& dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						String dynamicFieldValue = dataMapTemp.get(
								dynamicFieldAlia).toString();
						Set<String> conditionKey = conditionJsStrMapSecond
								.keySet();
						for (Iterator it = conditionKey.iterator(); it
								.hasNext();) {
							String conditionKeyTemp = (String) it.next();
							if (!keyList.contains(dynamicFieldValue)) {
								keyList.add(dynamicFieldValue);
							}
							if (!keySecondList.contains(conditionKeyTemp)) {
								keySecondList.add(conditionKeyTemp);
							}
							String jsEvalStr = conditionJsStrMapSecond
									.get(conditionKeyTemp);
							if (jsEvalStr.contains("LikeReplace")) {// JS串中like处理
								Set<String> likeMapKey = likeMap.keySet();
								for (Iterator likeMapIt = likeMapKey.iterator(); likeMapIt
										.hasNext();) {
									String likeMapKeyTemp = (String) likeMapIt
											.next();
									List<String> likeTempList = new ArrayList<String>();
									likeTempList = likeMap.get(likeMapKeyTemp);
									jsEvalStr = jsEvalStr
											.replaceAll(
													likeMapKeyTemp,
													(dataMapTemp
															.get(likeTempList
																	.get(0)) == null ? ""
															: dataMapTemp
																	.get(likeTempList
																			.get(0)))
															.toString()
															.contains(
																	likeTempList
																			.get(1))
															+ "");
								}
							}
							Set<String> dataMapKey = dataMapTemp.keySet();
							for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
									.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
								String dataMapKeyTemp = (String) dataMapIt
										.next();
								jsEvalStr = jsEvalStr
										.replaceAll(
												dataMapKeyTemp,
												dataMapTemp.get(dataMapKeyTemp) == null ? ""
														: dataMapTemp.get(
																dataMapKeyTemp)
																.toString());
							}
							Object rs = jsEngine.eval(jsEvalStr);
							Boolean rsBoolean = Boolean.parseBoolean(rs
									.toString());
							if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
								StatisticsResultData statisticsResultData = new StatisticsResultData();
								statisticsResultData
										.setResultType(dynamicFieldValue);
								statisticsResultData
										.setResultTypeSecond(conditionKeyTemp);
								statisticsResultData.setMainField(dataMapTemp
										.get(fieldAlias.get(tableAlias
												.get(mainTable)
												+ "."
												+ mainTablePk)).toString());
								statisticsResultDataList
										.add(statisticsResultData);
							}
						}
					}
				}
			} else if (dynamicFieldAlia.equals("")
					&& !dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						String dynamicFieldValueSecond = dataMapTemp.get(
								dynamicFieldAliaSecond).toString();
						Set<String> conditionKey = conditionJsStrMap.keySet();
						for (Iterator it = conditionKey.iterator(); it
								.hasNext();) {
							String conditionKeyTemp = (String) it.next();
							if (!keyList.contains(conditionKeyTemp)) {
								keyList.add(conditionKeyTemp);
							}
							if (!keySecondList
									.contains(dynamicFieldValueSecond)) {
								keySecondList.add(dynamicFieldValueSecond);
							}
							String jsEvalStr = conditionJsStrMap
									.get(conditionKeyTemp);
							if (jsEvalStr.contains("LikeReplace")) {// JS串中like处理
								Set<String> likeMapKey = likeMap.keySet();
								for (Iterator likeMapIt = likeMapKey.iterator(); likeMapIt
										.hasNext();) {
									String likeMapKeyTemp = (String) likeMapIt
											.next();
									List<String> likeTempList = new ArrayList<String>();
									likeTempList = likeMap.get(likeMapKeyTemp);
									jsEvalStr = jsEvalStr
											.replaceAll(
													likeMapKeyTemp,
													(dataMapTemp
															.get(likeTempList
																	.get(0)) == null ? ""
															: dataMapTemp
																	.get(likeTempList
																			.get(0)))
															.toString()
															.contains(
																	likeTempList
																			.get(1))
															+ "");
								}
							}
							Set<String> dataMapKey = dataMapTemp.keySet();
							for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
									.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
								String dataMapKeyTemp = (String) dataMapIt
										.next();
								jsEvalStr = jsEvalStr
										.replaceAll(
												dataMapKeyTemp,
												dataMapTemp.get(dataMapKeyTemp) == null ? ""
														: dataMapTemp.get(
																dataMapKeyTemp)
																.toString());
							}
							Object rs = jsEngine.eval(jsEvalStr);
							Boolean rsBoolean = Boolean.parseBoolean(rs
									.toString());
							if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
								StatisticsResultData statisticsResultData = new StatisticsResultData();
								statisticsResultData
										.setResultType(conditionKeyTemp);
								statisticsResultData
										.setResultTypeSecond(dynamicFieldValueSecond);
								statisticsResultData.setMainField(dataMapTemp
										.get(fieldAlias.get(tableAlias
												.get(mainTable)
												+ "."
												+ mainTablePk)).toString());
								statisticsResultDataList
										.add(statisticsResultData);
							}
						}
					}
				}
			} else if (dynamicFieldAlia.equals("")
					&& dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						Set<String> conditionKey = conditionJsStrMap.keySet();
						for (Iterator it = conditionKey.iterator(); it
								.hasNext();) {
							String conditionKeyTemp = (String) it.next();
							String jsEvalStr = conditionJsStrMap
									.get(conditionKeyTemp);
							Set<String> conditionKeySecond = conditionJsStrMapSecond
									.keySet();
							for (Iterator itSecond = conditionKeySecond
									.iterator(); itSecond.hasNext();) {
								String conditionKeyTempSecond = (String) itSecond
										.next();
								if (!keyList.contains(conditionKeyTemp)) {
									keyList.add(conditionKeyTemp);
								}
								if (!keySecondList
										.contains(conditionKeyTempSecond)) {
									keySecondList.add(conditionKeyTempSecond);
								}
								String jsEvalStrSecond = jsEvalStr
										+ "&&"
										+ conditionJsStrMapSecond
												.get(conditionKeyTempSecond);

								if (jsEvalStrSecond.contains("LikeReplace")) {// JS串中like处理
									Set<String> likeMapKey = likeMap.keySet();
									for (Iterator likeMapIt = likeMapKey
											.iterator(); likeMapIt.hasNext();) {
										String likeMapKeyTemp = (String) likeMapIt
												.next();
										List<String> likeTempList = new ArrayList<String>();
										likeTempList = likeMap
												.get(likeMapKeyTemp);
										jsEvalStrSecond = jsEvalStrSecond
												.replaceAll(
														likeMapKeyTemp,
														(dataMapTemp
																.get(likeTempList
																		.get(0)) == null ? ""
																: dataMapTemp
																		.get(likeTempList
																				.get(0)))
																.toString()
																.contains(
																		likeTempList
																				.get(1))
																+ "");
									}
								}
								Set<String> dataMapKey = dataMapTemp.keySet();
								for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
										.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
									String dataMapKeyTemp = (String) dataMapIt
											.next();
									jsEvalStrSecond = jsEvalStrSecond
											.replaceAll(
													dataMapKeyTemp,
													dataMapTemp
															.get(dataMapKeyTemp) == null ? ""
															: dataMapTemp
																	.get(dataMapKeyTemp)
																	.toString());
								}
								Object rs = jsEngine.eval(jsEvalStrSecond);
								Boolean rsBoolean = Boolean.parseBoolean(rs
										.toString());
								if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
									StatisticsResultData statisticsResultData = new StatisticsResultData();
									statisticsResultData
											.setResultType(conditionKeyTemp);
									statisticsResultData
											.setResultTypeSecond(conditionKeyTempSecond);
									statisticsResultData
											.setMainField(dataMapTemp
													.get(fieldAlias.get(tableAlias
															.get(mainTable)
															+ "." + mainTablePk))
													.toString());
									statisticsResultDataList
											.add(statisticsResultData);
								}

							}
						}
					}
				}
			}
		} catch (ScriptException e) {
			throw e;
		}
		Object[] resultObj = new Object[3];
		resultObj[0] = statisticsResultDataList;
		resultObj[1] = keyList;
		resultObj[2] = keySecondList;
		return resultObj;
	}

	@Override
	public Object[] statisticsFullChartDataset2D(
			List<StatisticsResultData> statisticsResultDataList,
			List<String> keyList, List<String> keySecondList) {
		Dataset2D<String> dataset = new CategoryDataset<String>();
		Map<List<String>, List<String>> resultDataTypeMap = new LinkedHashMap<List<String>, List<String>>();
		if (statisticsResultDataList != null
				&& statisticsResultDataList.size() > 0) {
			for (StatisticsResultData statisticsResultDataTemp : statisticsResultDataList) {
				List<String> keyTemp = new ArrayList<String>();
				List<String> valueTemp = new ArrayList<String>();
				keyTemp.add(statisticsResultDataTemp.getResultType());
				keyTemp.add(statisticsResultDataTemp.getResultTypeSecond());
				if (resultDataTypeMap.containsKey(keyTemp)) {
					valueTemp = resultDataTypeMap.get(keyTemp);
					if (!valueTemp.contains(statisticsResultDataTemp
							.getMainField())) {
						valueTemp.add(statisticsResultDataTemp.getMainField());
					}
					resultDataTypeMap.put(keyTemp, valueTemp);
				} else {
					valueTemp.add(statisticsResultDataTemp.getMainField());
					resultDataTypeMap.put(keyTemp, valueTemp);
				}
			}
		}
		List<StatisticsJsonData> jsonDatas = new ArrayList<StatisticsJsonData>();
		for (String keyFirstTemp : keyList) {
			for (String keySecondTemp : keySecondList) {
				List<String> keyTemp = new ArrayList<String>();
				keyTemp.add(keyFirstTemp);
				keyTemp.add(keySecondTemp);
				if (resultDataTypeMap.containsKey(keyTemp)) {
					dataset.addValue(keyFirstTemp, keySecondTemp,
							resultDataTypeMap.get(keyTemp).size(), null);
					StatisticsJsonData statisticsJsonData = new StatisticsJsonData();
					statisticsJsonData.setColumnName(keyFirstTemp);
					statisticsJsonData.setRowName(keySecondTemp);
					statisticsJsonData.setValue(resultDataTypeMap.get(keyTemp)
							.size() + "");
					jsonDatas.add(statisticsJsonData);
				} else {
					dataset.addValue(keyFirstTemp, keySecondTemp, 0, null);
					StatisticsJsonData statisticsJsonData = new StatisticsJsonData();
					statisticsJsonData.setColumnName(keyFirstTemp);
					statisticsJsonData.setRowName(keySecondTemp);
					statisticsJsonData.setValue("0");
					jsonDatas.add(statisticsJsonData);
				}
			}
		}
		JSONArray jsonarray = JSONArray.fromObject(jsonDatas);
		Object[] retDataobj = new Object[2];
		retDataobj[0] = dataset;
		retDataobj[1] = jsonarray.toString();
		return retDataobj;
	}

	@Override
	public Map<String, String> statisticsFullChartXMLMap2D(
			Dataset2D<String> dataSet, String mccKeyId, String jsonStr,
			String subTitle) throws Exception {
		MccKey mccKey = new MccKey();
		mccKey = mccKeyDao.get(mccKeyId);
		String title = mccKey.getCaption();
		char numberPrefix = ' ';
		char numberSuffix = ' ';
		if (mccKey.getNumberPrefix() != null
				&& mccKey.getNumberPrefix().length() > 0) {
			numberPrefix = mccKey.getNumberPrefix().trim().charAt(0);
		}
		if (mccKey.getNumberSuffix() != null
				&& mccKey.getNumberSuffix().length() > 0) {
			numberSuffix = mccKey.getNumberSuffix().trim().charAt(0);
		}
		Map<String, String> fullChartXMLMap = new HashMap<String, String>();
		if (dataSet.getCategories() == null
				|| dataSet.getCategories().size() == 0) {
			fullChartXMLMap.put("column2DMultiSeriesChart", null);
			fullChartXMLMap.put("line2DMultiSeriesChart", null);
			fullChartXMLMap.put("jsonStr", jsonStr);
			fullChartXMLMap.put("title", title);
			fullChartXMLMap.put("subTitle", subTitle);
			fullChartXMLMap.put("baseFontSize", mccKey.getBaseFontSize());
		} else {
			try {
				String baseFontSizeStr = mccKey.getBaseFontSize();
				Integer baseFontSize = 0;
				if (OtherUtil.measureNotNull(baseFontSizeStr)) {
					baseFontSize = Integer.parseInt(baseFontSizeStr);
				}
				JFusionChart column2DMultiSeriesChart = ChartFactory
						.createColumn2DMultiSeriesChart(dataSet, title,
								subTitle, mccKey.getxAxisName(),
								mccKey.getYAxisName(), mccKey.getShowValues(),
								numberPrefix == ' ' ? null : numberPrefix);
				Plot plot = (Plot) column2DMultiSeriesChart.getPlot();
				plot.setPalette(2);
				plot.setNumberSuffix(numberSuffix == ' ' ? null : numberSuffix);
				plot.setAnimation(true);
				if (baseFontSize != 0) {
					plot.setBaseFontSize(baseFontSize);
				}
				JFusionChart line2DMultiSeriesChart = ChartFactory
						.createLine2DMultiSeriesChart(dataSet, title, subTitle,
								mccKey.getxAxisName(), mccKey.getYAxisName(),
								mccKey.getShowValues(),
								numberPrefix == ' ' ? null : numberPrefix);
				plot = (Plot) line2DMultiSeriesChart.getPlot();
				plot.setNumberSuffix(numberSuffix == ' ' ? null : numberSuffix);
				plot.setAnimation(true);
				if (baseFontSize != 0) {
					plot.setBaseFontSize(baseFontSize);
				}
				fullChartXMLMap.put("column2DMultiSeriesChart",
						column2DMultiSeriesChart.toFormatXML());
				fullChartXMLMap.put("line2DMultiSeriesChart",
						line2DMultiSeriesChart.toFormatXML());
				fullChartXMLMap.put("jsonStr", jsonStr);
				fullChartXMLMap.put("title", title);
				fullChartXMLMap.put("subTitle", subTitle);
				fullChartXMLMap.put("baseFontSize", mccKey.getBaseFontSize());
			} catch (Exception e) {
				// log.error("toFormatXML ", e);
				throw e;
			}
		}
		return fullChartXMLMap;
	}

	@Override
	public Object[] statisticsFullDataByFilter(String itemId, String deptIds,
			String gridAllDatas, String filterExpression,
			String searchDateFrom, String searchDateTo, String snapCode)
			throws Exception {
		List<StatisticsResultData> statisticsResultDataList = new ArrayList<StatisticsResultData>();// 返回结果List;StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
		Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
		Map<String, String> fieldAlias = new HashMap<String, String>();// sql中出现的字段别名(Key为表别名.字段，value为字段别名)
		Map<String, String> conditionJsStrMap = new LinkedHashMap<String, String>();// JS字符串(Key为统计条件名称，value为该统计条件下JS串)
		List<String> tempList = new ArrayList<String>();
		Map<String, List<String>> likeMap = new HashMap<String, List<String>>();// Like操作符(Key为like的自定义名称，value为字段别名和统计指标查询值)
		int likeNum = 1;
		List<String> joinTables = new ArrayList<String>();// 需要使用left join的表
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		StatisticsItem statisticsItem = new StatisticsItem();
		statisticsItem = statisticsItemDao.get(itemId);
		String sql = "SELECT ";
		String sqlEnd = "";
		String dynamicFilterSql = "";
		String dynamicJoinSql = "";
		String dynamicKeySql = "";
		int fieldAliasNum = 1;
		int tableAliasNum = 1;
		String mainTable = statisticsItem.getStatisticsBdInfo().getBdInfo()
				.getTableName();// 统计项表
		String mainTablePk = "";
		BdInfoUtil bdInfoUtil = new BdInfoUtil(statisticsItem
				.getStatisticsBdInfo().getBdInfo());
		if (OtherUtil.measureNotNull(bdInfoUtil.getPkCol())) {
			mainTablePk = bdInfoUtil.getPkCol().getFieldCode();
		}
		// String mainTablePk =
		// statisticsItem.getStatisticsBdInfo().getBdInfo().getTablePkName();//统计项表主键
		tableAlias.put(mainTable, "mainTable");
		fieldAlias.put(tableAlias.get(mainTable) + "." + mainTablePk,
				"mainField");
		if (statisticsItem.getStatisticsFieldInfo() != null
				&& !statisticsItem.getStatisticsFieldInfo().equals("")) {// 有统计字段的情况
			if (!statisticsItem.getStatisticsFieldInfo().equals(mainTablePk)) {// 统计字段与统计项表主键不同时
				fieldAlias.put(
						tableAlias.get(mainTable) + "."
								+ statisticsItem.getStatisticsFieldInfo(),
						"statisticsField");
			}
		}
		String dynamicTable = "";
		if (statisticsItem.getDynamicStatistics()) {// 动态统计
			dynamicTable = statisticsItem.getDynamicTable();
			String dynamicTableForeignKey = statisticsItem
					.getDynamicTableForeignKey();
			String deptSnapSql = "";
			dynamicKeySql = "SELECT DISTINCT "
					+ statisticsItem.getDynamicCode() + ",'1' FROM "
					+ dynamicTable;
			if (dynamicTable.equals("v_hr_department_current")
					|| dynamicTable.equals("v_hr_department_his")) {
				dynamicTableForeignKey = "deptId";
				dynamicTable = "v_hr_department_his";
				deptSnapSql = " dynamicTable.snapId IN (SELECT MAX(snapId) FROM v_hr_department_his WHERE snapCode <='"
						+ snapCode + "' GROUP BY deptId)";
				dynamicKeySql = "SELECT name,'1' FROM v_hr_department_his WHERE snapId IN (SELECT MAX(snapId) FROM v_hr_department_his WHERE snapCode <='"
						+ snapCode + "' GROUP BY deptId)";
			}
			if (!tableAlias.containsKey(dynamicTable)) {
				tableAlias.put(dynamicTable, "dynamicTable");
				joinTables.add(dynamicTable);
			}
			String dynamicField = tableAlias.get(dynamicTable) + "."
					+ statisticsItem.getDynamicCode();
			if (!fieldAlias.containsKey(dynamicField)) {
				fieldAlias.put(dynamicField, "dynamicField");
			}
			dynamicJoinSql = dynamicJoinSql + " LEFT JOIN " + dynamicTable
					+ " AS " + tableAlias.get(dynamicTable);
			dynamicJoinSql = dynamicJoinSql + " ON "
					+ tableAlias.get(dynamicTable) + "."
					+ statisticsItem.getDynamicTablePk() + "="
					+ tableAlias.get(mainTable) + "." + dynamicTableForeignKey;
			if (statisticsItem.getDynamicFilterSql() != null
					&& !statisticsItem.getDynamicFilterSql().equals("")) {// 动态统计过滤条件
				dynamicFilterSql += statisticsItem
						.getDynamicFilterSql()
						.replaceAll(statisticsItem.getDynamicTable(),
								tableAlias.get(dynamicTable))
						.replaceAll(mainTable, tableAlias.get(mainTable));
			}
			if (!deptSnapSql.equals("")) {
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += deptSnapSql;
				} else {
					dynamicFilterSql += " AND " + deptSnapSql;
				}
			}
		} else {
			List<StatisticsCondition> statisticsConditions = new ArrayList<StatisticsCondition>();
			filters.add(new PropertyFilter("EQS_parentItem.id", itemId));
			filters.add(new PropertyFilter("OAS_sn", "sn"));
			statisticsConditions = statisticsConditionDao.getByFilters(filters);
			String filterSql = "";
			String jsStr = "";
			if (statisticsConditions != null && statisticsConditions.size() > 0) {
				for (StatisticsCondition statisticsCondition : statisticsConditions) {
					List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
					expressionList = getExpressionListEffectDigital(statisticsCondition
							.getExpression());
					filters.clear();
					List<StatisticsTarget> statisticsTargets = new ArrayList<StatisticsTarget>();
					filters.add(new PropertyFilter("EQS_parentCondition.id",
							statisticsCondition.getId()));
					filters.add(new PropertyFilter("OAS_id", "id"));
					statisticsTargets = this.getByFilters(filters);
					Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
					String itrNext = "";
					String tableName = "";
					String field = "";
					while (itr.hasNext()) {
						itrNext = itr.next();
						if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
							try {
								int num = Integer.valueOf(itrNext);
								if (num > 0 && num <= statisticsTargets.size()) {
									StatisticsTarget statisticsTarget = new StatisticsTarget();
									statisticsTarget = statisticsTargets
											.get(num - 1);
									tableName = statisticsTarget.getFieldInfo()
											.getBdInfo().getTableName();
									field = statisticsTarget.getFieldInfo()
											.getFieldCode();
									if (tableName.equals(mainTable)) {// 统计指标
																		// 表与统计项表相同
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									} else {// 统计指标 表与统计项表不同
										if (!tableAlias.containsKey(tableName)) {
											joinTables.add(tableName);
											tableAlias.put(tableName, "table"
													+ tableAliasNum);
											tableAliasNum++;
										}
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									}
									if (statisticsTarget.getOperation().equals(
											"like")) {// 统计指标操作符为Like时
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ " "
												+ statisticsTarget
														.getOperation()
												+ " '%"
												+ statisticsTarget
														.getTargetValue()
												+ "%' ";
										jsStr += "LikeReplace" + likeNum;
										tempList.clear();
										tempList.add(fieldAlias.get(tableAlias
												.get(tableName) + "." + field));
										tempList.add(statisticsTarget
												.getTargetValue());
										likeMap.put("LikeReplace" + likeNum,
												tempList);
										likeNum++;
									} else {// 统计指标操作符为其他字符时
										jsStr += "'"
												+ fieldAlias.get(tableAlias
														.get(tableName)
														+ "."
														+ field) + "'";
										if (statisticsTarget.getOperation()
												.equals("=")) {
											jsStr += "==";
										} else if (statisticsTarget
												.getOperation().equals("<>")) {
											jsStr += "!=";
										} else {
											jsStr += statisticsTarget
													.getOperation();
										}
										jsStr += "'"
												+ statisticsTarget
														.getTargetValue() + "'";
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ statisticsTarget
														.getOperation()
												+ "'"
												+ statisticsTarget
														.getTargetValue()
												+ "' ";
									}
								}
							} catch (NumberFormatException e) {
								throw e;
							}

						} else if (itrNext.equals("(") || itrNext.equals(")")) {
							filterSql += itrNext + " ";
							jsStr += itrNext;
						} else if (itrNext.equals("+")) {
							filterSql += "or ";
							jsStr += "||";
						} else if (itrNext.equals("*")) {
							filterSql += "and ";
							jsStr += "&&";
						}
					}
					conditionJsStrMap.put(statisticsCondition.getName(), jsStr);
					if (!filterSql.equals("")) {
						sqlEnd += " " + filterSql + " or";
					}
					filterSql = "";
					jsStr = "";
				}
			}
		}
		String sqlEndgrid = "";
		if (gridAllDatas != null && !gridAllDatas.equals("")) {
			JSONObject json = JSONObject.fromObject(gridAllDatas);
			JSONArray allDatas = json.getJSONArray("edit");
			Map<String, List<String>> targetList = new HashMap<String, List<String>>();
			int id = 1;
			for (int i = 0; i < allDatas.size(); i++) {
				// 获取每一个JsonObject对象
				JSONObject myjObject = allDatas.getJSONObject(i);
				if (myjObject.size() > 0) {
					List<String> targetValueTemp = new ArrayList<String>();
					targetValueTemp.add(myjObject.getString("fieldInfoId"));
					targetValueTemp.add(myjObject.getString("operation"));
					targetValueTemp.add(myjObject.getString("targetValue"));
					targetList.put(id + "", targetValueTemp);
					id++;
				}
			}
			String expression = filterExpression;
			expression = expression.trim();
			String strDigital = "";
			List<String> expressionList = new ArrayList<String>();// 公式第一遍解析(提取数字)
			if (expression != null && !"".equals(expression)) {
				for (int i = 0; i < expression.length(); i++) {
					if (expression.charAt(i) >= 48
							&& expression.charAt(i) <= 57) {
						strDigital += expression.charAt(i);
					} else {
						if (!strDigital.equals("")) {
							expressionList.add(strDigital);
						}
						expressionList.add(expression.charAt(i) + "");
						strDigital = "";
					}
				}
				if (!strDigital.equals("")) {
					expressionList.add(strDigital);
					strDigital = "";
				}
			}
			Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
			String itrNext = "";
			String tableName = "";
			String field = "";
			String filterSql = "";
			while (itr.hasNext()) {
				try {
					itrNext = itr.next();
					if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的

						int num = Integer.valueOf(itrNext);
						if (num > 0 && num <= targetList.size()) {
							List<String> valueTempList = new ArrayList<String>();
							valueTempList = targetList.get(num + "");
							FieldInfo fieldInfo = fieldInfoDao
									.get(valueTempList.get(0));
							tableName = fieldInfo.getBdInfo().getTableName();
							field = fieldInfo.getFieldCode();

							if (tableName.equals(mainTable)) {// 统计指标 表与统计项表相同
								if (!fieldAlias.containsKey(tableAlias
										.get(tableName) + "." + field)) {
									fieldAlias.put(tableAlias.get(tableName)
											+ "." + field, "field"
											+ fieldAliasNum);
									fieldAliasNum++;
								}
							} else {// 统计指标 表与统计项表不同
								if (!tableAlias.containsKey(tableName)) {
									joinTables.add(tableName);
									tableAlias.put(tableName, "table"
											+ tableAliasNum);
									tableAliasNum++;
								}
								if (!fieldAlias.containsKey(tableAlias
										.get(tableName) + "." + field)) {
									fieldAlias.put(tableAlias.get(tableName)
											+ "." + field, "field"
											+ fieldAliasNum);
									fieldAliasNum++;
								}
							}
							if (valueTempList.get(1).equals("like")) {// 统计指标操作符为Like时
								filterSql += tableAlias.get(tableName) + "."
										+ field + " " + valueTempList.get(1)
										+ " '%" + valueTempList.get(2) + "%' ";
							} else {// 统计指标操作符为其他字符时
								filterSql += tableAlias.get(tableName) + "."
										+ field + valueTempList.get(1) + "'"
										+ valueTempList.get(2) + "' ";
							}
						}
					} else if (itrNext.equals("(") || itrNext.equals(")")) {
						filterSql += itrNext + " ";
					} else if (itrNext.equals("+")) {
						filterSql += "or ";
					} else if (itrNext.equals("*")) {
						filterSql += "and ";
					}
				} catch (NumberFormatException e) {
					throw e;
				}
			}
			if (!filterSql.equals("")) {
				sqlEndgrid += " " + filterSql + " or";
			}
			filterSql = "";
		}
		String sqlEndDept = "";
		String deptJoinSql = "";
		if (deptIds == null || deptIds.equals("")) {
			deptIds = getDeptIds(snapCode);
		}

		String deptTableName = "v_hr_department_his";
		String deptTablePK = "deptId";
		if (!tableAlias.containsKey(deptTableName)) {
			tableAlias.put(deptTableName, "table" + tableAliasNum);
			tableAliasNum++;
			deptJoinSql = " LEFT JOIN " + deptTableName + " AS "
					+ tableAlias.get(deptTableName);
			deptJoinSql = deptJoinSql + " ON " + tableAlias.get(deptTableName)
					+ "." + deptTablePK + "=" + tableAlias.get(mainTable) + "."
					+ "deptId";
		}
		String[] deptId = deptIds.split(",");
		String deptIdstr = "";
		for (int i = 0; i < deptId.length; i++) {
			if (i == 0) {
				deptIdstr += "'" + deptId[i] + "'";
			} else {
				deptIdstr += "," + "'" + deptId[i] + "'";
			}
		}
		if (OtherUtil.measureNotNull(dynamicKeySql)
				&& dynamicKeySql.indexOf("v_hr_department_his") >= 0) {
			dynamicKeySql += " AND deptId IN (" + deptIdstr
					+ ") ORDER BY deptCode";
		}
		sqlEndDept = tableAlias.get(deptTableName) + "." + deptTablePK
				+ " IN (" + deptIdstr + ")";

		/** Create SQL **/
		Set<String> fieldKey = fieldAlias.keySet(); // 需要查询的字段和别名依次加入sql中
		for (Iterator it = fieldKey.iterator(); it.hasNext();) {
			String keyTemp = (String) it.next();
			sql += keyTemp + " AS " + fieldAlias.get(keyTemp) + ",";
		}
		if (sql.contains(",")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable) + " ";
		for (String joinTableTemp : joinTables) {// 需要join的表
			if (!tableAlias.get(joinTableTemp).contains("dynamicTable")) {
				sql += " LEFT JOIN " + joinTableTemp + " AS "
						+ tableAlias.get(joinTableTemp);
				sql += " ON " + tableAlias.get(joinTableTemp) + "."
						+ mainTablePk + "=" + tableAlias.get(mainTable) + "."
						+ mainTablePk + " ";
			}
		}
		sql += dynamicJoinSql + " ";
		sql += deptJoinSql + " ";
		String sqlEndStr = "";
		sqlEndStr = "WHERE " + tableAlias.get(mainTable) + ".disabled = '0'";

		if (sqlEnd.length() > 2) {// where条件
			if (sqlEnd.substring(sqlEnd.length() - 2, sqlEnd.length()).equals(
					"or")) {
				sqlEnd = sqlEnd.substring(0, sqlEnd.length() - 2);
			}
			sqlEndStr += " AND " + "(" + sqlEnd + ")";
		}
		if (!dynamicFilterSql.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + dynamicFilterSql;
			// }else{
			// sqlEndStr+=" WHERE "+dynamicFilterSql;
			// }
		}
		if (!sqlEndDept.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + sqlEndDept;
			// }else{
			// sqlEndStr+=" WHERE "+sqlEndDept;
			// }
		}
		if (!sqlEndgrid.equals("")) {
			if (sqlEndgrid.substring(sqlEndgrid.length() - 2,
					sqlEndgrid.length()).equals("or")) {
				sqlEndgrid = sqlEndgrid.substring(0, sqlEndgrid.length() - 2);
			}
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + sqlEndgrid;
			// }else{
			// sqlEndStr+=" WHERE "+sqlEndgrid;
			// }
		}
		/** 时间 **/
		String dateFK = statisticsItem.getStatisticsBdInfo().getShijianFK();
		if (searchDateFrom != null && !searchDateFrom.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + tableAlias.get(mainTable) + "." + dateFK
					+ ">=" + "'" + searchDateFrom + "'";
			// }else{
			// sqlEndStr+=" WHERE "+ tableAlias.get(mainTable)+"."+dateFK +">="+
			// "'"+searchDateFrom+"'";
			// }
		}
		if (searchDateTo != null && !searchDateTo.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + tableAlias.get(mainTable) + "." + dateFK
					+ "<=" + "'" + searchDateTo + "'";
			// }else{
			// sqlEndStr+=" WHERE "+ tableAlias.get(mainTable)+"."+dateFK +"<="+
			// "'"+searchDateTo+"'";
			// }
		}

		/** snapCode **/
		if (snapCode != null) {
			sql = sql.replace(mainTable, "v_hr_person_his");
			String snapCodeSql = "";
			snapCodeSql += tableAlias.get(mainTable) + ".snapId IN (";
			snapCodeSql += "SELECT MAX(snapId) FROM v_hr_person_his WHERE snapCode <='"
					+ snapCode + "' GROUP BY personId)";
			snapCodeSql += " AND " + tableAlias.get(mainTable) + ".deleted=0 ";
			for (String joinTableTemp : joinTables) {// 需要join的表
				if (!tableAlias.get(joinTableTemp).contains("dynamicTable")) {
					snapCodeSql += " AND " + tableAlias.get(joinTableTemp)
							+ ".snapCode <='" + snapCode + "'";
				}
			}
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + snapCodeSql;
			// }else{
			// sqlEndStr+=" WHERE "+ snapCodeSql;
			// }
		}
		sql += sqlEndStr;
		/** SQL End **/

		List<String> keyList = new ArrayList<String>();
		if (OtherUtil.measureNotNull(dynamicKeySql)) {
			List<Object[]> dynamicKeyList = (List<Object[]>) this
					.getBySql(dynamicKeySql);
			if (OtherUtil.measureNotNull(dynamicKeyList)
					&& !dynamicKeyList.isEmpty()) {
				for (Object[] dynamicKeyTemp : dynamicKeyList) {
					String keyTemp = dynamicKeyTemp[0].toString();
					if (!keyList.contains(keyTemp)) {
						keyList.add(keyTemp);
					}
				}
			}
		}

		List<Map<String, Object>> queryDataList = getBySqlToMap(sql);
		try {
			if (statisticsItem.getDynamicStatistics()) {// 动态统计；每一条记录都加入结果集
				String dynamicField = fieldAlias.get(tableAlias
						.get(dynamicTable)
						+ "."
						+ statisticsItem.getDynamicCode());
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						String dynamicFieldValue = dataMapTemp
								.get(dynamicField).toString();
						StatisticsResultData statisticsResultData = new StatisticsResultData();
						statisticsResultData.setResultType(dynamicFieldValue);
						statisticsResultData.setMainField(dataMapTemp.get(
								fieldAlias.get(tableAlias.get(mainTable) + "."
										+ mainTablePk)).toString());
						if (statisticsItem.getStatisticsFieldInfo() != null
								&& !statisticsItem.getStatisticsFieldInfo()
										.equals("")) {
							if (!statisticsItem.getStatisticsFieldInfo()
									.equals(mainTablePk)) {
								statisticsResultData
										.setStatisticsField(dataMapTemp
												.get(fieldAlias.get(tableAlias
														.get(mainTable)
														+ "."
														+ statisticsItem
																.getStatisticsFieldInfo()))
												.toString());
							} else {
								statisticsResultData
										.setStatisticsField(dataMapTemp.get(
												fieldAlias.get(tableAlias
														.get(mainTable)
														+ "."
														+ mainTablePk))
												.toString());
							}
						}
						if (!keyList.contains(dynamicFieldValue)) {
							keyList.add(dynamicFieldValue);
						}
						statisticsResultDataList.add(statisticsResultData);
					}
				}
			} else {
				Set<String> conditionKey = conditionJsStrMap.keySet();
				for (Iterator it = conditionKey.iterator(); it.hasNext();) {
					String conditionKeyTemp = (String) it.next();
					if (!keyList.contains(conditionKeyTemp)) {
						keyList.add(conditionKeyTemp);
					}
					if (queryDataList != null && queryDataList.size() > 0) {// 遍历SQL查询的结果
						for (Map<String, Object> dataMapTemp : queryDataList) {
							String jsEvalStr = conditionJsStrMap
									.get(conditionKeyTemp);
							if (jsEvalStr.contains("LikeReplace")) {// JS串中like处理
								Set<String> likeMapKey = likeMap.keySet();
								for (Iterator likeMapIt = likeMapKey.iterator(); likeMapIt
										.hasNext();) {
									String likeMapKeyTemp = (String) likeMapIt
											.next();
									List<String> likeTempList = new ArrayList<String>();
									likeTempList = likeMap.get(likeMapKeyTemp);
									jsEvalStr = jsEvalStr
											.replaceAll(
													likeMapKeyTemp,
													(dataMapTemp
															.get(likeTempList
																	.get(0)) == null ? ""
															: dataMapTemp
																	.get(likeTempList
																			.get(0)))
															.toString()
															.contains(
																	likeTempList
																			.get(1))
															+ "");
								}
							}
							Set<String> dataMapKey = dataMapTemp.keySet();
							for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
									.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
								String dataMapKeyTemp = (String) dataMapIt
										.next();
								jsEvalStr = jsEvalStr
										.replaceAll(
												dataMapKeyTemp,
												dataMapTemp.get(dataMapKeyTemp) == null ? ""
														: dataMapTemp.get(
																dataMapKeyTemp)
																.toString());
							}
							Object rs = jsEngine.eval(jsEvalStr);
							Boolean rsBoolean = Boolean.parseBoolean(rs
									.toString());
							if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
								StatisticsResultData statisticsResultData = new StatisticsResultData();
								statisticsResultData
										.setResultType(conditionKeyTemp);
								statisticsResultData.setMainField(dataMapTemp
										.get(fieldAlias.get(tableAlias
												.get(mainTable)
												+ "."
												+ mainTablePk)).toString());
								if (statisticsItem.getStatisticsFieldInfo() != null
										&& !statisticsItem
												.getStatisticsFieldInfo()
												.equals("")) {// statisticsField统计项字段非空时的统计项字段查询值
									if (!statisticsItem
											.getStatisticsFieldInfo().equals(
													mainTablePk)) {
										statisticsResultData
												.setStatisticsField(dataMapTemp
														.get(fieldAlias.get(tableAlias
																.get(mainTable)
																+ "."
																+ statisticsItem
																		.getStatisticsFieldInfo()))
														.toString());
									} else {
										statisticsResultData
												.setStatisticsField(dataMapTemp
														.get(fieldAlias.get(tableAlias
																.get(mainTable)
																+ "."
																+ mainTablePk))
														.toString());
									}
								}
								statisticsResultDataList
										.add(statisticsResultData);
							}
						}
					}

				}
			}
		} catch (ScriptException e) {
			throw e;
		}
		Object[] resultObj = new Object[3];
		resultObj[0] = statisticsResultDataList;
		resultObj[1] = keyList;
		resultObj[2] = statisticsItem.getName();
		return resultObj;
	}

	@Override
	public Object[] statisticsFullData2DByFilter(String itemId,
			String itemIdSecond, String deptIds, String gridAllDatas,
			String filterExpression, String conditionIds,
			String conditionSecondIds, String searchDateFrom,
			String searchDateTo, String snapCode) throws Exception {
		List<StatisticsResultData> statisticsResultDataList = new ArrayList<StatisticsResultData>();// 返回结果List;StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
		Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
		Map<String, String> fieldAlias = new HashMap<String, String>();// sql中出现的字段别名(Key为表别名.字段，value为字段别名)
		Map<String, String> conditionJsStrMap = new LinkedHashMap<String, String>();// JS字符串(Key为统计条件名称，value为该统计条件下JS串)
		Map<String, String> conditionJsStrMapSecond = new LinkedHashMap<String, String>();
		List<String> tempList = new ArrayList<String>();
		Map<String, List<String>> likeMap = new HashMap<String, List<String>>();// Like操作符(Key为like的自定义名称，value为字段别名和统计指标查询值)
		int likeNum = 1;
		List<String> joinTables = new ArrayList<String>();// 需要使用left join的表
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		StatisticsItem statisticsItem = new StatisticsItem();
		StatisticsItem statisticsItemSecond = new StatisticsItem();
		statisticsItem = statisticsItemDao.get(itemId);
		statisticsItemSecond = statisticsItemDao.get(itemIdSecond);
		String subTitle = statisticsItem.getName() + "-"
				+ statisticsItemSecond.getName();
		String sql = "SELECT ";
		String dynamicFilterSql = "";
		String sqlEnd = "";
		String sqlEndSecond = "";
		String dynamicFieldAlia = "";
		String dynamicFieldAliaSecond = "";
		String dynamicJoinSql = "";
		String dynamicKeySql = "";
		String dynamicKeySqlSecond = "";
		int fieldAliasNum = 1;
		int tableAliasNum = 1;
		String mainTable = statisticsItem.getStatisticsBdInfo().getBdInfo()
				.getTableName();// 统计项表
		String mainTablePk = "";
		BdInfoUtil bdInfoUtil = new BdInfoUtil(statisticsItem
				.getStatisticsBdInfo().getBdInfo());
		if (OtherUtil.measureNotNull(bdInfoUtil.getPkCol())) {
			mainTablePk = bdInfoUtil.getPkCol().getFieldCode();
		}
		// String
		// mainTablePk=statisticsItem.getStatisticsBdInfo().getBdInfo().getTablePkName();//统计项表主键
		tableAlias.put(mainTable, "mainTable");
		fieldAlias.put(tableAlias.get(mainTable) + "." + mainTablePk,
				"mainField");
		if (statisticsItem.getDynamicStatistics()) {// 动态统计
			String dynamicTable = statisticsItem.getDynamicTable();
			String dynamicTableForeignKey = statisticsItem
					.getDynamicTableForeignKey();
			String deptSnapSql = "";
			dynamicKeySql = "SELECT DISTINCT "
					+ statisticsItem.getDynamicCode() + ",'1' FROM "
					+ dynamicTable;
			if (dynamicTable.equals("v_hr_department_current")
					|| dynamicTable.equals("v_hr_department_his")) {
				deptIds = conditionIds;
				dynamicTableForeignKey = "deptId";
				dynamicTable = "v_hr_department_his";
				deptSnapSql = " dynamicTable.snapId IN (SELECT MAX(snapId) FROM v_hr_department_his WHERE snapCode <='"
						+ snapCode + "' GROUP BY deptId)";
				dynamicKeySql = "SELECT name,'1' FROM v_hr_department_his WHERE snapId IN (SELECT MAX(snapId) FROM v_hr_department_his WHERE snapCode <='"
						+ snapCode + "' GROUP BY deptId)";
			}
			if (!tableAlias.containsKey(dynamicTable)) {
				tableAlias.put(dynamicTable, "dynamicTable");
				joinTables.add(dynamicTable);
			}
			String dynamicField = tableAlias.get(dynamicTable) + "."
					+ statisticsItem.getDynamicCode();
			if (!fieldAlias.containsKey(dynamicField)) {
				fieldAlias.put(dynamicField, "dynamicField");
			}
			dynamicJoinSql = dynamicJoinSql + " LEFT JOIN " + dynamicTable
					+ " AS " + tableAlias.get(dynamicTable);
			dynamicJoinSql = dynamicJoinSql + " ON "
					+ tableAlias.get(dynamicTable) + "."
					+ statisticsItem.getDynamicTablePk() + "="
					+ tableAlias.get(mainTable) + "." + dynamicTableForeignKey;
			dynamicFieldAlia = fieldAlias.get(dynamicField);
			if (conditionIds != null && !conditionIds.equals("")) {
				String[] dynamicId = conditionIds.split(",");
				String dynamicstr = "";
				for (int i = 0; i < dynamicId.length; i++) {
					if (i == 0) {
						dynamicstr += "'" + dynamicId[i] + "'";
					} else {
						dynamicstr += "," + "'" + dynamicId[i] + "'";
					}
				}
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += tableAlias.get(dynamicTable) + "."
							+ statisticsItem.getDynamicTablePk() + " IN ("
							+ dynamicstr + ") ";
				} else {
					dynamicFilterSql += " AND " + tableAlias.get(dynamicTable)
							+ "." + statisticsItem.getDynamicTablePk()
							+ " IN (" + dynamicstr + ") ";
				}
			}
			if (statisticsItem.getDynamicFilterSql() != null
					&& !statisticsItem.getDynamicFilterSql().equals("")) {// 动态统计过滤条件
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += statisticsItem
							.getDynamicFilterSql()
							.replaceAll(statisticsItem.getDynamicTable(),
									tableAlias.get(dynamicTable))
							.replaceAll(mainTable, tableAlias.get(mainTable));
				} else {
					dynamicFilterSql += " AND "
							+ statisticsItem
									.getDynamicFilterSql()
									.replaceAll(
											statisticsItem.getDynamicTable(),
											tableAlias.get(dynamicTable))
									.replaceAll(mainTable,
											tableAlias.get(mainTable));
				}

			}
			if (!deptSnapSql.equals("")) {
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += deptSnapSql;
				} else {
					dynamicFilterSql += " AND " + deptSnapSql;
				}
			}
		} else {
			filters.clear();
			List<StatisticsCondition> statisticsConditions = new ArrayList<StatisticsCondition>();
			filters.add(new PropertyFilter("EQS_parentItem.id", itemId));
			filters.add(new PropertyFilter("OAS_sn", "sn"));
			if (conditionIds != null && !conditionIds.equals("")) {
				filters.add(new PropertyFilter("INS_id", conditionIds));
			}
			statisticsConditions = statisticsConditionDao.getByFilters(filters);
			String filterSql = "";
			String jsStr = "";
			if (statisticsConditions != null && statisticsConditions.size() > 0) {
				for (StatisticsCondition statisticsCondition : statisticsConditions) {
					List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
					expressionList = getExpressionListEffectDigital(statisticsCondition
							.getExpression());
					filters.clear();
					List<StatisticsTarget> statisticsTargets = new ArrayList<StatisticsTarget>();
					filters.add(new PropertyFilter("EQS_parentCondition.id",
							statisticsCondition.getId()));
					filters.add(new PropertyFilter("OAS_id", "id"));
					statisticsTargets = this.getByFilters(filters);
					Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
					String itrNext = "";
					String tableName = "";
					String field = "";
					while (itr.hasNext()) {
						itrNext = itr.next();
						if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
							try {
								int num = Integer.valueOf(itrNext);
								if (num > 0 && num <= statisticsTargets.size()) {
									StatisticsTarget statisticsTarget = new StatisticsTarget();
									statisticsTarget = statisticsTargets
											.get(num - 1);
									tableName = statisticsTarget.getFieldInfo()
											.getBdInfo().getTableName();
									field = statisticsTarget.getFieldInfo()
											.getFieldCode();
									if (tableName.equals(mainTable)) {// 统计指标
																		// 表与统计项表相同
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									} else {// 统计指标 表与统计项表不同
										if (!tableAlias.containsKey(tableName)) {
											joinTables.add(tableName);
											tableAlias.put(tableName, "table"
													+ tableAliasNum);
											tableAliasNum++;
										}
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									}
									if (statisticsTarget.getOperation().equals(
											"like")) {// 统计指标操作符为Like时
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ " "
												+ statisticsTarget
														.getOperation()
												+ " '%"
												+ statisticsTarget
														.getTargetValue()
												+ "%' ";
										jsStr += "LikeReplace" + likeNum;
										tempList.clear();
										tempList.add(fieldAlias.get(tableAlias
												.get(tableName) + "." + field));
										tempList.add(statisticsTarget
												.getTargetValue());
										likeMap.put("LikeReplace" + likeNum,
												tempList);
										likeNum++;
									} else {// 统计指标操作符为其他字符时
										jsStr += "'"
												+ fieldAlias.get(tableAlias
														.get(tableName)
														+ "."
														+ field) + "'";
										if (statisticsTarget.getOperation()
												.equals("=")) {
											jsStr += "==";
										} else if (statisticsTarget
												.getOperation().equals("<>")) {
											jsStr += "!=";
										} else {
											jsStr += statisticsTarget
													.getOperation();
										}
										jsStr += "'"
												+ statisticsTarget
														.getTargetValue() + "'";
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ statisticsTarget
														.getOperation()
												+ "'"
												+ statisticsTarget
														.getTargetValue()
												+ "' ";
									}
								}
							} catch (NumberFormatException e) {
								throw e;
							}

						} else if (itrNext.equals("(") || itrNext.equals(")")) {
							filterSql += itrNext + " ";
							jsStr += itrNext;
						} else if (itrNext.equals("+")) {
							filterSql += "or ";
							jsStr += "||";
						} else if (itrNext.equals("*")) {
							filterSql += "and ";
							jsStr += "&&";
						}
					}
					conditionJsStrMap.put(statisticsCondition.getName(), jsStr);
					if (!filterSql.equals("")) {
						sqlEnd += " " + filterSql + " or";
					}
					filterSql = "";
					jsStr = "";
				}
			}
		}
		/** ItemSecond **/

		if (statisticsItemSecond.getDynamicStatistics()) {// 动态统计
			String dynamicTable = statisticsItemSecond.getDynamicTable();
			String dynamicTableForeignKey = statisticsItemSecond
					.getDynamicTableForeignKey();
			String deptSnapSql = "";
			dynamicKeySqlSecond = "SELECT DISTINCT "
					+ statisticsItemSecond.getDynamicCode() + ",'1' FROM "
					+ dynamicTable;
			if (dynamicTable.equals("v_hr_department_current")
					|| dynamicTable.equals("v_hr_department_his")) {
				deptIds = conditionSecondIds;
				dynamicTableForeignKey = "deptId";
				dynamicTable = "v_hr_department_his";
				deptSnapSql = " dynamicTableSecond.snapId IN (SELECT MAX(snapId) FROM v_hr_department_his WHERE snapCode <='"
						+ snapCode + "' GROUP BY deptId)";
				dynamicKeySqlSecond = "SELECT name,'1' FROM v_hr_department_his WHERE snapId IN (SELECT MAX(snapId) FROM v_hr_department_his WHERE snapCode <='"
						+ snapCode + "' GROUP BY deptId)";
			}
			if (!tableAlias.containsKey(dynamicTable)) {
				tableAlias.put(dynamicTable, "dynamicTableSecond");
				joinTables.add(dynamicTable);
			}
			String dynamicField = tableAlias.get(dynamicTable) + "."
					+ statisticsItemSecond.getDynamicCode();
			if (!fieldAlias.containsKey(dynamicField)) {
				fieldAlias.put(dynamicField, "dynamicFieldSecond");
			}
			dynamicFieldAliaSecond = fieldAlias.get(dynamicField);
			dynamicJoinSql = dynamicJoinSql + " LEFT JOIN " + dynamicTable
					+ " AS " + tableAlias.get(dynamicTable);
			dynamicJoinSql = dynamicJoinSql + " ON "
					+ tableAlias.get(dynamicTable) + "."
					+ statisticsItemSecond.getDynamicTablePk() + "="
					+ tableAlias.get(mainTable) + "." + dynamicTableForeignKey;
			if (conditionSecondIds != null && !conditionSecondIds.equals("")) {
				String[] dynamicId = conditionSecondIds.split(",");
				String dynamicstr = "";
				for (int i = 0; i < dynamicId.length; i++) {
					if (i == 0) {
						dynamicstr += "'" + dynamicId[i] + "'";
					} else {
						dynamicstr += "," + "'" + dynamicId[i] + "'";
					}

				}
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += tableAlias.get(dynamicTable) + "."
							+ statisticsItemSecond.getDynamicTablePk()
							+ " IN (" + dynamicstr + ") ";
				} else {
					dynamicFilterSql += " AND " + tableAlias.get(dynamicTable)
							+ "." + statisticsItemSecond.getDynamicTablePk()
							+ " IN (" + dynamicstr + ") ";
				}
			}
			if (statisticsItemSecond.getDynamicFilterSql() != null
					&& !statisticsItemSecond.getDynamicFilterSql().equals("")) {// 动态统计过滤条件
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += statisticsItemSecond
							.getDynamicFilterSql()
							.replaceAll(statisticsItemSecond.getDynamicTable(),
									tableAlias.get(dynamicTable))
							.replaceAll(mainTable, tableAlias.get(mainTable));
				} else {
					dynamicFilterSql += " AND "
							+ statisticsItemSecond
									.getDynamicFilterSql()
									.replaceAll(
											statisticsItemSecond
													.getDynamicTable(),
											tableAlias.get(dynamicTable))
									.replaceAll(mainTable,
											tableAlias.get(mainTable));
				}
			}
			if (!deptSnapSql.equals("")) {
				if (dynamicFilterSql.equals("")) {
					dynamicFilterSql += deptSnapSql;
				} else {
					dynamicFilterSql += " AND " + deptSnapSql;
				}
			}
		} else {
			filters.clear();
			List<StatisticsCondition> statisticsConditions = new ArrayList<StatisticsCondition>();
			filters.add(new PropertyFilter("EQS_parentItem.id", itemIdSecond));
			filters.add(new PropertyFilter("OAS_sn", "sn"));
			if (conditionSecondIds != null && !conditionSecondIds.equals("")) {
				filters.add(new PropertyFilter("INS_id", conditionSecondIds));
			}
			statisticsConditions = statisticsConditionDao.getByFilters(filters);
			String filterSql = "";
			String jsStr = "";
			if (statisticsConditions != null && statisticsConditions.size() > 0) {
				for (StatisticsCondition statisticsCondition : statisticsConditions) {
					List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
					expressionList = getExpressionListEffectDigital(statisticsCondition
							.getExpression());
					filters.clear();
					List<StatisticsTarget> statisticsTargets = new ArrayList<StatisticsTarget>();
					filters.add(new PropertyFilter("EQS_parentCondition.id",
							statisticsCondition.getId()));
					filters.add(new PropertyFilter("OAS_id", "id"));
					statisticsTargets = this.getByFilters(filters);
					Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
					String itrNext = "";
					String tableName = "";
					String field = "";
					while (itr.hasNext()) {
						itrNext = itr.next();
						if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
							try {
								int num = Integer.valueOf(itrNext);
								if (num > 0 && num <= statisticsTargets.size()) {
									StatisticsTarget statisticsTarget = new StatisticsTarget();
									statisticsTarget = statisticsTargets
											.get(num - 1);
									tableName = statisticsTarget.getFieldInfo()
											.getBdInfo().getTableName();
									field = statisticsTarget.getFieldInfo()
											.getFieldCode();
									if (tableName.equals(mainTable)) {// 统计指标
																		// 表与统计项表相同
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									} else {// 统计指标 表与统计项表不同
										if (!tableAlias.containsKey(tableName)) {
											joinTables.add(tableName);
											tableAlias.put(tableName, "table"
													+ tableAliasNum);
											tableAliasNum++;
										}
										if (!fieldAlias.containsKey(tableAlias
												.get(tableName) + "." + field)) {
											fieldAlias.put(
													tableAlias.get(tableName)
															+ "." + field,
													"field" + fieldAliasNum);
											fieldAliasNum++;
										}
									}
									if (statisticsTarget.getOperation().equals(
											"like")) {// 统计指标操作符为Like时
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ " "
												+ statisticsTarget
														.getOperation()
												+ " '%"
												+ statisticsTarget
														.getTargetValue()
												+ "%' ";
										jsStr += "LikeReplace" + likeNum;
										tempList.clear();
										tempList.add(fieldAlias.get(tableAlias
												.get(tableName) + "." + field));
										tempList.add(statisticsTarget
												.getTargetValue());
										likeMap.put("LikeReplace" + likeNum,
												tempList);
										likeNum++;
									} else {// 统计指标操作符为其他字符时
										jsStr += "'"
												+ fieldAlias.get(tableAlias
														.get(tableName)
														+ "."
														+ field) + "'";
										if (statisticsTarget.getOperation()
												.equals("=")) {
											jsStr += "==";
										} else if (statisticsTarget
												.getOperation().equals("<>")) {
											jsStr += "!=";
										} else {
											jsStr += statisticsTarget
													.getOperation();
										}
										jsStr += "'"
												+ statisticsTarget
														.getTargetValue() + "'";
										filterSql += tableAlias.get(tableName)
												+ "."
												+ statisticsTarget
														.getFieldInfo()
														.getFieldCode()
												+ statisticsTarget
														.getOperation()
												+ "'"
												+ statisticsTarget
														.getTargetValue()
												+ "' ";
									}
								}
							} catch (NumberFormatException e) {
								throw e;
							}

						} else if (itrNext.equals("(") || itrNext.equals(")")) {
							filterSql += itrNext + " ";
							jsStr += itrNext;
						} else if (itrNext.equals("+")) {
							filterSql += "or ";
							jsStr += "||";
						} else if (itrNext.equals("*")) {
							filterSql += "and ";
							jsStr += "&&";
						}
					}
					conditionJsStrMapSecond.put(statisticsCondition.getName(),
							jsStr);
					if (!filterSql.equals("")) {
						sqlEndSecond += " " + filterSql + " or";
					}
					filterSql = "";
					jsStr = "";
				}
			}
		}
		/** ItemSecond End **/
		String sqlEndgrid = "";
		if (gridAllDatas != null && !gridAllDatas.equals("")) {
			JSONObject json = JSONObject.fromObject(gridAllDatas);
			JSONArray allDatas = json.getJSONArray("edit");
			Map<String, List<String>> targetList = new HashMap<String, List<String>>();
			int id = 1;
			for (int i = 0; i < allDatas.size(); i++) {
				// 获取每一个JsonObject对象
				JSONObject myjObject = allDatas.getJSONObject(i);
				if (myjObject.size() > 0) {
					List<String> targetValueTemp = new ArrayList<String>();
					targetValueTemp.add(myjObject.getString("fieldInfoId"));
					targetValueTemp.add(myjObject.getString("operation"));
					targetValueTemp.add(myjObject.getString("targetValue"));
					targetList.put(id + "", targetValueTemp);
					id++;
				}
			}
			String expression = filterExpression;
			expression = expression.trim();
			String strDigital = "";
			List<String> expressionList = new ArrayList<String>();// 公式第一遍解析(提取数字)
			if (expression != null && !"".equals(expression)) {
				for (int i = 0; i < expression.length(); i++) {
					if (expression.charAt(i) >= 48
							&& expression.charAt(i) <= 57) {
						strDigital += expression.charAt(i);
					} else {
						if (!strDigital.equals("")) {
							expressionList.add(strDigital);
						}
						expressionList.add(expression.charAt(i) + "");
						strDigital = "";
					}
				}
				if (!strDigital.equals("")) {
					expressionList.add(strDigital);
					strDigital = "";
				}
			}
			Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
			String itrNext = "";
			String tableName = "";
			String field = "";
			String filterSql = "";
			while (itr.hasNext()) {
				try {
					itrNext = itr.next();
					if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的

						int num = Integer.valueOf(itrNext);
						if (num > 0 && num <= targetList.size()) {
							List<String> valueTempList = new ArrayList<String>();
							valueTempList = targetList.get(num + "");
							FieldInfo fieldInfo = fieldInfoDao
									.get(valueTempList.get(0));
							tableName = fieldInfo.getBdInfo().getTableName();
							field = fieldInfo.getFieldCode();

							if (tableName.equals(mainTable)) {// 统计指标 表与统计项表相同
								if (!fieldAlias.containsKey(tableAlias
										.get(tableName) + "." + field)) {
									fieldAlias.put(tableAlias.get(tableName)
											+ "." + field, "field"
											+ fieldAliasNum);
									fieldAliasNum++;
								}
							} else {// 统计指标 表与统计项表不同
								if (!tableAlias.containsKey(tableName)) {
									joinTables.add(tableName);
									tableAlias.put(tableName, "table"
											+ tableAliasNum);
									tableAliasNum++;
								}
								if (!fieldAlias.containsKey(tableAlias
										.get(tableName) + "." + field)) {
									fieldAlias.put(tableAlias.get(tableName)
											+ "." + field, "field"
											+ fieldAliasNum);
									fieldAliasNum++;
								}
							}
							if (valueTempList.get(1).equals("like")) {// 统计指标操作符为Like时
								filterSql += tableAlias.get(tableName) + "."
										+ field + " " + valueTempList.get(1)
										+ " '%" + valueTempList.get(2) + "%' ";
							} else {// 统计指标操作符为其他字符时
								filterSql += tableAlias.get(tableName) + "."
										+ field + valueTempList.get(1) + "'"
										+ valueTempList.get(2) + "' ";
							}
						}
					} else if (itrNext.equals("(") || itrNext.equals(")")) {
						filterSql += itrNext + " ";
					} else if (itrNext.equals("+")) {
						filterSql += "or ";
					} else if (itrNext.equals("*")) {
						filterSql += "and ";
					}
				} catch (NumberFormatException e) {
					throw e;
				}
			}
			if (!filterSql.equals("")) {
				sqlEndgrid += " " + filterSql + " or";
			}
			filterSql = "";
		}
		String sqlEndDept = "";
		String deptJoinSql = "";
		if (OtherUtil.measureNull(deptIds)) {
			deptIds = getDeptIds(snapCode);
		}
		String deptTableName = "v_hr_department_his";
		String deptTablePK = "deptId";
		if (!tableAlias.containsKey(deptTableName)) {
			tableAlias.put(deptTableName, "table" + tableAliasNum);
			tableAliasNum++;
			deptJoinSql = " LEFT JOIN " + deptTableName + " AS "
					+ tableAlias.get(deptTableName);
			deptJoinSql = deptJoinSql + " ON " + tableAlias.get(deptTableName)
					+ "." + deptTablePK + "=" + tableAlias.get(mainTable) + "."
					+ "deptId";
		}
		String[] deptId = deptIds.split(",");
		String deptIdstr = "";
		for (int i = 0; i < deptId.length; i++) {
			if (i == 0) {
				deptIdstr += "'" + deptId[i] + "'";
			} else {
				deptIdstr += "," + "'" + deptId[i] + "'";
			}
		}
		if (OtherUtil.measureNotNull(dynamicKeySql)
				&& dynamicKeySql.indexOf("v_hr_department_his") >= 0) {
			dynamicKeySql += " AND deptId IN (" + deptIdstr
					+ ") ORDER BY deptCode";
		}
		if (OtherUtil.measureNotNull(dynamicKeySqlSecond)
				&& dynamicKeySqlSecond.indexOf("v_hr_department_his") >= 0) {
			dynamicKeySqlSecond += " AND deptId IN (" + deptIdstr
					+ ") ORDER BY deptCode";
		}
		sqlEndDept = tableAlias.get(deptTableName) + "." + deptTablePK
				+ " IN (" + deptIdstr + ")";
		/** Create SQL **/
		Set<String> fieldKey = fieldAlias.keySet(); // 需要查询的字段和别名依次加入sql中
		for (Iterator it = fieldKey.iterator(); it.hasNext();) {
			String keyTemp = (String) it.next();
			sql += keyTemp + " AS " + fieldAlias.get(keyTemp) + ",";
		}
		if (sql.contains(",")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable) + " ";
		for (String joinTableTemp : joinTables) {// 需要join的表
			if (!tableAlias.get(joinTableTemp).contains("dynamicTable")) {
				sql += " LEFT JOIN " + joinTableTemp + " AS "
						+ tableAlias.get(joinTableTemp);
				sql += " ON " + tableAlias.get(joinTableTemp) + "."
						+ mainTablePk + "=" + tableAlias.get(mainTable) + "."
						+ mainTablePk + " ";
			}
		}
		sql += dynamicJoinSql + " ";
		sql += deptJoinSql + " ";
		String sqlEndStr = "";
		sqlEndStr = " WHERE " + tableAlias.get(mainTable) + ".disabled = '0'";
		if (sqlEnd.length() > 2) {// where条件
			if (sqlEnd.substring(sqlEnd.length() - 2, sqlEnd.length()).equals(
					"or")) {
				sqlEnd = sqlEnd.substring(0, sqlEnd.length() - 2);
			}
			sqlEndStr += " AND " + "(" + sqlEnd + ")";
		}
		if (sqlEndSecond.length() > 2) {// where条件
			if (sqlEndSecond.substring(sqlEndSecond.length() - 2,
					sqlEndSecond.length()).equals("or")) {
				sqlEndSecond = sqlEndSecond.substring(0,
						sqlEndSecond.length() - 2);
			}
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + "(" + sqlEndSecond + ")";
			// }else{
			// sqlEndStr+=" WHERE "+"("+sqlEndSecond+")";
			// }

		}
		if (!dynamicFilterSql.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + dynamicFilterSql;
			// }else{
			// sqlEndStr+=" WHERE "+dynamicFilterSql;
			// }
		}
		if (!sqlEndDept.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + sqlEndDept;
			// }else{
			// sqlEndStr+=" WHERE "+sqlEndDept;
			// }
		}
		if (!sqlEndgrid.equals("")) {
			if (sqlEndgrid.substring(sqlEndgrid.length() - 2,
					sqlEndgrid.length()).equals("or")) {
				sqlEndgrid = sqlEndgrid.substring(0, sqlEndgrid.length() - 2);
			}
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + sqlEndgrid;
			// }else{
			// sqlEndStr+=" WHERE "+sqlEndgrid;
			// }
		}
		/** 时间 **/
		String dateFK = statisticsItem.getStatisticsBdInfo().getShijianFK();
		if (searchDateFrom != null && !searchDateFrom.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + tableAlias.get(mainTable) + "." + dateFK
					+ ">=" + "'" + searchDateFrom + "'";
			// }else{
			// sqlEndStr+=" WHERE "+ tableAlias.get(mainTable)+"."+dateFK +">="+
			// "'"+searchDateFrom+"'";
			// }
		}
		if (searchDateTo != null && !searchDateTo.equals("")) {
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + tableAlias.get(mainTable) + "." + dateFK
					+ "<=" + "'" + searchDateTo + "'";
			// }else{
			// sqlEndStr+=" WHERE "+ tableAlias.get(mainTable)+"."+dateFK +"<="+
			// "'"+searchDateTo+"'";
			// }
		}
		/** snapCode **/
		if (snapCode != null) {
			sql = sql.replace(mainTable, "v_hr_person_his");
			String snapCodeSql = "";
			snapCodeSql += tableAlias.get(mainTable) + ".snapId IN (";
			snapCodeSql += "SELECT MAX(snapId) FROM v_hr_person_his WHERE snapCode <='"
					+ snapCode + "' GROUP BY personId)";
			snapCodeSql += " AND " + tableAlias.get(mainTable) + ".deleted=0 ";
			for (String joinTableTemp : joinTables) {// 需要join的表
				if (!tableAlias.get(joinTableTemp).contains("dynamicTable")) {
					snapCodeSql += " AND " + tableAlias.get(joinTableTemp)
							+ ".snapCode <='" + snapCode + "'";
				}
			}
			// if(!sqlEndStr.equals("")){
			sqlEndStr += " AND " + snapCodeSql;
			// }else{
			// sqlEndStr+=" WHERE "+ snapCodeSql;
			// }
		}
		sql += sqlEndStr;
		/** SQL End **/
		List<String> keyList = new ArrayList<String>();
		List<String> keySecondList = new ArrayList<String>();
		if (OtherUtil.measureNotNull(dynamicKeySql)) {
			List<Object[]> dynamicKeyList = (List<Object[]>) this
					.getBySql(dynamicKeySql);
			if (OtherUtil.measureNotNull(dynamicKeyList)
					&& !dynamicKeyList.isEmpty()) {
				for (Object[] dynamicKeyTemp : dynamicKeyList) {
					String keyTemp = dynamicKeyTemp[0].toString();
					if (!keyList.contains(keyTemp)) {
						keyList.add(keyTemp);
					}
				}
			}
		}
		if (OtherUtil.measureNotNull(dynamicKeySqlSecond)) {
			List<Object[]> dynamicKeyListSecond = (List<Object[]>) this
					.getBySql(dynamicKeySqlSecond);
			if (OtherUtil.measureNotNull(dynamicKeyListSecond)
					&& !dynamicKeyListSecond.isEmpty()) {
				for (Object[] dynamicKeyTemp : dynamicKeyListSecond) {
					String keyTemp = dynamicKeyTemp[0].toString();
					if (!keySecondList.contains(keyTemp)) {
						keySecondList.add(keyTemp);
					}
				}
			}
		}
		List<Map<String, Object>> queryDataList = new ArrayList<Map<String, Object>>();
		queryDataList = getBySqlToMap(sql);
		try {
			if (!dynamicFieldAlia.equals("")
					&& !dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						String dynamicFieldValue = dataMapTemp.get(
								dynamicFieldAlia).toString();
						String dynamicFieldValueSecond = dataMapTemp.get(
								dynamicFieldAliaSecond).toString();
						StatisticsResultData statisticsResultData = new StatisticsResultData();
						statisticsResultData.setResultType(dynamicFieldValue);
						statisticsResultData
								.setResultTypeSecond(dynamicFieldValueSecond);
						statisticsResultData.setMainField(dataMapTemp.get(
								fieldAlias.get(tableAlias.get(mainTable) + "."
										+ mainTablePk)).toString());
						statisticsResultDataList.add(statisticsResultData);
						if (!keyList.contains(dynamicFieldValue)) {
							keyList.add(dynamicFieldValue);
						}
						if (!keySecondList.contains(dynamicFieldValueSecond)) {
							keySecondList.add(dynamicFieldValueSecond);
						}
					}
				}
			} else if (!dynamicFieldAlia.equals("")
					&& dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						String dynamicFieldValue = dataMapTemp.get(
								dynamicFieldAlia).toString();
						Set<String> conditionKey = conditionJsStrMapSecond
								.keySet();
						for (Iterator it = conditionKey.iterator(); it
								.hasNext();) {
							String conditionKeyTemp = (String) it.next();
							if (!keyList.contains(dynamicFieldValue)) {
								keyList.add(dynamicFieldValue);
							}
							if (!keySecondList.contains(conditionKeyTemp)) {
								keySecondList.add(conditionKeyTemp);
							}
							String jsEvalStr = conditionJsStrMapSecond
									.get(conditionKeyTemp);
							if (jsEvalStr.contains("LikeReplace")) {// JS串中like处理
								Set<String> likeMapKey = likeMap.keySet();
								for (Iterator likeMapIt = likeMapKey.iterator(); likeMapIt
										.hasNext();) {
									String likeMapKeyTemp = (String) likeMapIt
											.next();
									List<String> likeTempList = new ArrayList<String>();
									likeTempList = likeMap.get(likeMapKeyTemp);
									jsEvalStr = jsEvalStr
											.replaceAll(
													likeMapKeyTemp,
													(dataMapTemp
															.get(likeTempList
																	.get(0)) == null ? ""
															: dataMapTemp
																	.get(likeTempList
																			.get(0)))
															.toString()
															.contains(
																	likeTempList
																			.get(1))
															+ "");
								}
							}
							Set<String> dataMapKey = dataMapTemp.keySet();
							for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
									.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
								String dataMapKeyTemp = (String) dataMapIt
										.next();
								jsEvalStr = jsEvalStr
										.replaceAll(
												dataMapKeyTemp,
												dataMapTemp.get(dataMapKeyTemp) == null ? ""
														: dataMapTemp.get(
																dataMapKeyTemp)
																.toString());
							}
							Object rs = jsEngine.eval(jsEvalStr);
							Boolean rsBoolean = Boolean.parseBoolean(rs
									.toString());
							if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
								StatisticsResultData statisticsResultData = new StatisticsResultData();
								statisticsResultData
										.setResultType(dynamicFieldValue);
								statisticsResultData
										.setResultTypeSecond(conditionKeyTemp);
								statisticsResultData.setMainField(dataMapTemp
										.get(fieldAlias.get(tableAlias
												.get(mainTable)
												+ "."
												+ mainTablePk)).toString());
								statisticsResultDataList
										.add(statisticsResultData);
							}
						}
					}
				}
			} else if (dynamicFieldAlia.equals("")
					&& !dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						String dynamicFieldValueSecond = dataMapTemp.get(
								dynamicFieldAliaSecond).toString();
						Set<String> conditionKey = conditionJsStrMap.keySet();
						for (Iterator it = conditionKey.iterator(); it
								.hasNext();) {
							String conditionKeyTemp = (String) it.next();
							if (!keyList.contains(conditionKeyTemp)) {
								keyList.add(conditionKeyTemp);
							}
							if (!keySecondList
									.contains(dynamicFieldValueSecond)) {
								keySecondList.add(dynamicFieldValueSecond);
							}
							String jsEvalStr = conditionJsStrMap
									.get(conditionKeyTemp);
							if (jsEvalStr.contains("LikeReplace")) {// JS串中like处理
								Set<String> likeMapKey = likeMap.keySet();
								for (Iterator likeMapIt = likeMapKey.iterator(); likeMapIt
										.hasNext();) {
									String likeMapKeyTemp = (String) likeMapIt
											.next();
									List<String> likeTempList = new ArrayList<String>();
									likeTempList = likeMap.get(likeMapKeyTemp);
									jsEvalStr = jsEvalStr
											.replaceAll(
													likeMapKeyTemp,
													(dataMapTemp
															.get(likeTempList
																	.get(0)) == null ? ""
															: dataMapTemp
																	.get(likeTempList
																			.get(0)))
															.toString()
															.contains(
																	likeTempList
																			.get(1))
															+ "");
								}
							}
							Set<String> dataMapKey = dataMapTemp.keySet();
							for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
									.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
								String dataMapKeyTemp = (String) dataMapIt
										.next();
								jsEvalStr = jsEvalStr
										.replaceAll(
												dataMapKeyTemp,
												dataMapTemp.get(dataMapKeyTemp) == null ? ""
														: dataMapTemp.get(
																dataMapKeyTemp)
																.toString());
							}
							Object rs = jsEngine.eval(jsEvalStr);
							Boolean rsBoolean = Boolean.parseBoolean(rs
									.toString());
							if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
								StatisticsResultData statisticsResultData = new StatisticsResultData();
								statisticsResultData
										.setResultType(conditionKeyTemp);
								statisticsResultData
										.setResultTypeSecond(dynamicFieldValueSecond);
								statisticsResultData.setMainField(dataMapTemp
										.get(fieldAlias.get(tableAlias
												.get(mainTable)
												+ "."
												+ mainTablePk)).toString());
								statisticsResultDataList
										.add(statisticsResultData);
							}
						}
					}
				}
			} else if (dynamicFieldAlia.equals("")
					&& dynamicFieldAliaSecond.equals("")) {
				if (queryDataList != null && queryDataList.size() > 0) {
					for (Map<String, Object> dataMapTemp : queryDataList) {
						Set<String> conditionKey = conditionJsStrMap.keySet();
						for (Iterator it = conditionKey.iterator(); it
								.hasNext();) {
							String conditionKeyTemp = (String) it.next();
							String jsEvalStr = conditionJsStrMap
									.get(conditionKeyTemp);
							Set<String> conditionKeySecond = conditionJsStrMapSecond
									.keySet();
							for (Iterator itSecond = conditionKeySecond
									.iterator(); itSecond.hasNext();) {
								String conditionKeyTempSecond = (String) itSecond
										.next();
								String jsEvalStrSecond = jsEvalStr
										+ "&&"
										+ conditionJsStrMapSecond
												.get(conditionKeyTempSecond);
								if (!keyList.contains(conditionKeyTemp)) {
									keyList.add(conditionKeyTemp);
								}
								if (!keySecondList
										.contains(conditionKeyTempSecond)) {
									keySecondList.add(conditionKeyTempSecond);
								}
								if (jsEvalStrSecond.contains("LikeReplace")) {// JS串中like处理
									Set<String> likeMapKey = likeMap.keySet();
									for (Iterator likeMapIt = likeMapKey
											.iterator(); likeMapIt.hasNext();) {
										String likeMapKeyTemp = (String) likeMapIt
												.next();
										List<String> likeTempList = new ArrayList<String>();
										likeTempList = likeMap
												.get(likeMapKeyTemp);
										jsEvalStrSecond = jsEvalStrSecond
												.replaceAll(
														likeMapKeyTemp,
														(dataMapTemp
																.get(likeTempList
																		.get(0)) == null ? ""
																: dataMapTemp
																		.get(likeTempList
																				.get(0)))
																.toString()
																.contains(
																		likeTempList
																				.get(1))
																+ "");
									}
								}
								Set<String> dataMapKey = dataMapTemp.keySet();
								for (Iterator dataMapIt = dataMapKey.iterator(); dataMapIt
										.hasNext();) {// JS串中的其他别名替换为该别名对应的查询值
									String dataMapKeyTemp = (String) dataMapIt
											.next();
									jsEvalStrSecond = jsEvalStrSecond
											.replaceAll(
													dataMapKeyTemp,
													dataMapTemp
															.get(dataMapKeyTemp) == null ? ""
															: dataMapTemp
																	.get(dataMapKeyTemp)
																	.toString());
								}
								Object rs = jsEngine.eval(jsEvalStrSecond);
								Boolean rsBoolean = Boolean.parseBoolean(rs
										.toString());
								if (rsBoolean) {// StatisticsResultData中resultType为统计条件名称；mainField为统计项表主键；statisticsField统计项字段非空时的统计项字段查询值
									StatisticsResultData statisticsResultData = new StatisticsResultData();
									statisticsResultData
											.setResultType(conditionKeyTemp);
									statisticsResultData
											.setResultTypeSecond(conditionKeyTempSecond);
									statisticsResultData
											.setMainField(dataMapTemp
													.get(fieldAlias.get(tableAlias
															.get(mainTable)
															+ "." + mainTablePk))
													.toString());
									statisticsResultDataList
											.add(statisticsResultData);
								}

							}
						}
					}
				}
			}
		} catch (ScriptException e) {
			throw e;
		}
		Object[] resultObj = new Object[4];
		resultObj[0] = statisticsResultDataList;
		resultObj[1] = keyList;
		resultObj[2] = keySecondList;
		resultObj[3] = subTitle;
		return resultObj;
	}

	@Override
	public Map<String, String> statisticsFullSingleFieldData(String fieldId,
			String mainTableId, String deptFK, String deptIds,
			String gridAllDatas, String filterExpression,
			String searchDateFrom, String searchDateTo, String shijianFK,
			String snapCode) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("count", "0");
		resultMap.put("sum", "0");
		resultMap.put("avg", "0");
		resultMap.put("max", "0");
		resultMap.put("min", "0");
		FieldInfo fieldInfo = fieldInfoDao.get(fieldId);
		String fieldCode = fieldInfo.getFieldCode();
		String tableCode = fieldInfo.getBdInfo().getTableName();
		String dataType = fieldInfo.getDataType();
		String sql = "";
		if (dataType.equals("4")) {
			sql = "select count(*) as count,'' as sum,'' as avg,max("
					+ fieldCode + ") as max,min(" + fieldCode + ") as min ";
		} else if (dataType.equals("2")) {
			sql = "select count(*) as count,ROUND(sum(" + fieldCode
					+ "),4) as sum,ROUND(avg(" + fieldCode
					+ "),4) as avg,ROUND(max(" + fieldCode
					+ "),4) as max,ROUND(min(" + fieldCode + "),4) as min ";
		} else {
			sql = "select count(*) as count,sum(" + fieldCode + ") as sum,avg("
					+ fieldCode + ") as avg,max(" + fieldCode + ") as max,min("
					+ fieldCode + ") as min ";
		}
		sql = sql + " from " + tableCode;
		if (tableCode.equals("v_hr_person_current")
				|| tableCode.equals("v_hr_person_his")) {
			sql = sql.replaceAll(tableCode, "v_hr_person_his");
			sql = sql
					+ " where v_hr_person_his.snapId IN (SELECT MAX(snapId) FROM v_hr_person_his WHERE snapCode <='"
					+ snapCode + "' GROUP BY personId)";
		} else {
			sql = sql + " where snapCode <='" + snapCode + "'";
		}
		List<Map<String, Object>> queryDataList = new ArrayList<Map<String, Object>>();
		queryDataList = getBySqlToMap(sql);
		if (queryDataList != null && queryDataList.size() > 0) {
			for (Map<String, Object> dataMapTemp : queryDataList) {
				resultMap.put("count", dataMapTemp.get("count") == null ? ""
						: dataMapTemp.get("count").toString());
				resultMap.put("sum", dataMapTemp.get("sum") == null ? ""
						: dataMapTemp.get("sum").toString());
				resultMap.put("avg", dataMapTemp.get("avg") == null ? ""
						: dataMapTemp.get("avg").toString());
				resultMap.put("max", dataMapTemp.get("max") == null ? ""
						: dataMapTemp.get("max").toString());
				resultMap.put("min", dataMapTemp.get("min") == null ? ""
						: dataMapTemp.get("min").toString());
			}
		}
		/** filter start **/
		BdInfo bdInfo = bdInfoDao.get(mainTableId);
		String mainTable = bdInfo.getTableName();
		String mainTablePk = "";
		BdInfoUtil bdInfoUtil = new BdInfoUtil(bdInfo);
		if (OtherUtil.measureNotNull(bdInfoUtil.getPkCol())) {
			mainTablePk = bdInfoUtil.getPkCol().getFieldCode();
		}
		// String mainTablePk = bdInfo.getTablePkName();
		Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
		List<String> joinTables = new ArrayList<String>();// 需要使用left join的表
		if (mainTable.equals("v_hr_person_current")
				|| mainTable.equals("v_hr_person_his")) {
			mainTable = "v_hr_person_his";
		}
		tableAlias.put(mainTable, "mainTable");
		int tableAliasNum = 1;
		if (!tableCode.equals(mainTable)) {
			joinTables.add(tableCode);
			tableAlias.put(tableCode, "table" + tableAliasNum);
			tableAliasNum++;
		}
		String sqlEndgrid = "";
		if (gridAllDatas != null && !gridAllDatas.equals("")) {
			JSONObject json = JSONObject.fromObject(gridAllDatas);
			JSONArray allDatas = json.getJSONArray("edit");
			Map<String, List<String>> targetList = new HashMap<String, List<String>>();
			int id = 1;
			for (int i = 0; i < allDatas.size(); i++) {
				// 获取每一个JsonObject对象
				JSONObject myjObject = allDatas.getJSONObject(i);
				if (myjObject.size() > 0) {
					List<String> targetValueTemp = new ArrayList<String>();
					targetValueTemp.add(myjObject.getString("fieldInfoId"));
					targetValueTemp.add(myjObject.getString("operation"));
					targetValueTemp.add(myjObject.getString("targetValue"));
					targetList.put(id + "", targetValueTemp);
					id++;
				}
			}
			List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
			expressionList = getExpressionListEffectDigital(filterExpression);
			Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
			String itrNext = "";
			String tableName = "";
			String field = "";
			String filterSql = "";
			while (itr.hasNext()) {
				try {
					itrNext = itr.next();
					if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的

						int num = Integer.valueOf(itrNext);
						if (num > 0 && num <= targetList.size()) {
							List<String> valueTempList = new ArrayList<String>();
							valueTempList = targetList.get(num + "");
							fieldInfo = new FieldInfo();
							fieldInfo = fieldInfoDao.get(valueTempList.get(0));
							tableName = fieldInfo.getBdInfo().getTableName();
							field = fieldInfo.getFieldCode();

							if (!tableName.equals(mainTable)
									&& !tableAlias.containsKey(tableName)) {// 统计指标
																			// 表与统计项表相同
								joinTables.add(tableName);
								tableAlias.put(tableName, "table"
										+ tableAliasNum);
								tableAliasNum++;
							}
							if (valueTempList.get(1).equals("like")) {// 统计指标操作符为Like时
								filterSql += tableAlias.get(tableName) + "."
										+ field + " " + valueTempList.get(1)
										+ " '%" + valueTempList.get(2) + "%' ";
							} else {// 统计指标操作符为其他字符时
								filterSql += tableAlias.get(tableName) + "."
										+ field + valueTempList.get(1) + "'"
										+ valueTempList.get(2) + "' ";
							}
						}
					} else if (itrNext.equals("(") || itrNext.equals(")")) {
						filterSql += itrNext + " ";
					} else if (itrNext.equals("+")) {
						filterSql += "or ";
					} else if (itrNext.equals("*")) {
						filterSql += "and ";
					}
				} catch (NumberFormatException e) {
					throw e;
				}
			}
			if (!filterSql.equals("")) {
				sqlEndgrid += " " + filterSql + " or";
			}
			filterSql = "";
		}
		String sqlEndDept = "";
		String deptJoinSql = "";
		if (deptIds == null || deptIds.equals("")) {
			deptIds = getDeptIds(snapCode);
		}
		String deptTableName = "v_hr_department_his";
		String deptTablePK = "deptId";
		if (!tableAlias.containsKey(deptTableName)) {
			tableAlias.put(deptTableName, "table" + tableAliasNum);
			tableAliasNum++;
			deptJoinSql = " LEFT JOIN " + deptTableName + " AS "
					+ tableAlias.get(deptTableName);
			deptJoinSql = deptJoinSql + " ON " + tableAlias.get(deptTableName)
					+ "." + deptTablePK + "=" + tableAlias.get(mainTable) + "."
					+ "deptId";
		}
		String[] deptId = deptIds.split(",");
		String deptIdstr = "";
		for (int i = 0; i < deptId.length; i++) {
			if (i == 0) {
				deptIdstr += "'" + deptId[i] + "'";
			} else {
				deptIdstr += "," + "'" + deptId[i] + "'";
			}
		}
		sqlEndDept = tableAlias.get(deptTableName) + "." + deptTablePK
				+ " IN (" + deptIdstr + ")";
		sqlEndDept = sqlEndDept
				+ " AND "
				+ tableAlias.get(deptTableName)
				+ ".snapId IN (SELECT MAX(snapId) FROM v_hr_department_his WHERE snapCode <='"
				+ snapCode + "' GROUP BY deptId)";
		/** filter end **/
		/** Create SQL **/
		String tableCodeAlia = tableAlias.get(tableCode);
		String fieldAlia = tableCodeAlia + "." + fieldCode;
		if (dataType.equals("4")) {
			sql = "select '' as sum,'' as avg,max(" + fieldAlia
					+ ") as max,min(" + fieldAlia + ") as min ";
		} else if (dataType.equals("2")) {
			sql = "select ROUND(sum(" + fieldAlia + "),4) as sum,ROUND(avg("
					+ fieldAlia + "),4) as avg,ROUND(max(" + fieldAlia
					+ "),4) as max,ROUND(min(" + fieldAlia + "),4) as min ";
		} else {
			sql = "select sum(" + fieldAlia + ") as sum,avg(" + fieldAlia
					+ ") as avg,max(" + fieldAlia + ") as max,min(" + fieldAlia
					+ ") as min ";
		}
		sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable) + " ";
		String joinTableStr = "";
		for (String joinTableTemp : joinTables) {// 需要join的表
			if (!tableAlias.get(joinTableTemp).contains("dynamicTable")) {
				sql += " LEFT JOIN " + joinTableTemp + " AS "
						+ tableAlias.get(joinTableTemp);
				sql += " ON " + tableAlias.get(joinTableTemp) + "."
						+ mainTablePk + "=" + tableAlias.get(mainTable) + "."
						+ mainTablePk + " ";
				if (joinTableStr.equals("")) {
					joinTableStr += tableAlias.get(joinTableTemp)
							+ ".snapCode <='" + snapCode + "' ";
				} else {
					joinTableStr += " AND " + tableAlias.get(joinTableTemp)
							+ ".snapCode <='" + snapCode + "' ";
				}
			}
		}
		sql += deptJoinSql + " ";
		sql += " WHERE "
				+ tableAlias.get(mainTable)
				+ ".snapId IN (SELECT MAX(snapId) FROM v_hr_person_his WHERE snapCode <='"
				+ snapCode + "' GROUP BY personId) ";
		sql += " AND " + tableAlias.get(mainTable) + ".deleted=0 ";
		String sqlEndStr = "";
		if (!sqlEndDept.equals("")) {
			sqlEndStr += " AND " + sqlEndDept;
		}
		if (!joinTableStr.equals("")) {
			sqlEndStr += " AND " + joinTableStr;
		}
		if (!sqlEndgrid.equals("")) {
			if (sqlEndgrid.substring(sqlEndgrid.length() - 2,
					sqlEndgrid.length()).equals("or")) {
				sqlEndgrid = sqlEndgrid.substring(0, sqlEndgrid.length() - 2);
			}
			sqlEndStr += " AND " + sqlEndgrid;
		}
		/** 时间 **/
		if (searchDateFrom != null && !searchDateFrom.equals("")) {
			sqlEndStr += " AND " + tableAlias.get(mainTable) + "." + shijianFK
					+ ">=" + "'" + searchDateFrom + "'";
		}
		if (searchDateTo != null && !searchDateTo.equals("")) {
			sqlEndStr += " AND " + tableAlias.get(mainTable) + "." + shijianFK
					+ "<=" + "'" + searchDateTo + "'";
		}
		sql += sqlEndStr;
		/** SQL End **/
		if (deptIds != null && !deptIds.equals("") || gridAllDatas != null
				&& !gridAllDatas.equals("") || searchDateFrom != null
				&& !searchDateFrom.equals("") || searchDateTo != null
				&& !searchDateTo.equals("")) {
			queryDataList = getBySqlToMap(sql);
			if (queryDataList != null && queryDataList.size() > 0) {
				for (Map<String, Object> dataMapTemp : queryDataList) {
					resultMap.put("sum", dataMapTemp.get("sum") == null ? ""
							: dataMapTemp.get("sum").toString());
					resultMap.put("avg", dataMapTemp.get("avg") == null ? ""
							: dataMapTemp.get("avg").toString());
					resultMap.put("max", dataMapTemp.get("max") == null ? ""
							: dataMapTemp.get("max").toString());
					resultMap.put("min", dataMapTemp.get("min") == null ? ""
							: dataMapTemp.get("min").toString());
				}
			}
		}
		return resultMap;
	}

	/** 公式数字提取 **/
	private List<String> getExpressionListEffectDigital(String expression) {
		expression = expression.trim();
		String strDigital = "";
		List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
		if (expression != null && !"".equals(expression)) {
			for (int i = 0; i < expression.length(); i++) {
				if (expression.charAt(i) >= 48 && expression.charAt(i) <= 57) {
					strDigital += expression.charAt(i);
				} else {
					if (!strDigital.equals("")) {
						expressionList.add(strDigital);
					}
					expressionList.add(expression.charAt(i) + "");
					strDigital = "";
				}
			}
			if (!strDigital.equals("")) {
				expressionList.add(strDigital);
				strDigital = "";
			}
		}
		return expressionList;
	}

	// /**取当前可用的orgCodes**/
	// private String getOrgCodes(String snapCode){
	// List<HrOrgSnap> orgList = hrOrgSnapManager.getAllHisAvailable(snapCode);
	// String orgCodes = "";
	// if(orgList!=null && !orgList.isEmpty()){
	// for(HrOrgSnap org:orgList){
	// orgCodes += "'"+org.getOrgCode()+"'"+",";
	// }
	// orgCodes = OtherUtil.subStrEnd(orgCodes, ",");
	// }
	// if(orgCodes.equals("")){
	// orgCodes="'-1'";
	// }
	// orgCodes="("+orgCodes+")";
	// return orgCodes;
	// }
	/** 取当前可用的deptIds **/
	private String getDeptIds(String snapCode) {
		List<HrOrgSnap> orgList = hrOrgSnapManager.getAllHisAvailable(snapCode);
		String orgCodes = "";
		if (orgList != null && !orgList.isEmpty()) {
			for (HrOrgSnap org : orgList) {
				orgCodes += org.getOrgCode() + ",";
			}
			orgCodes = OtherUtil.subStrEnd(orgCodes, ",");
		}
		List<PropertyFilter> deptFilters = new ArrayList<PropertyFilter>();
		deptFilters.add(new PropertyFilter("INS_orgCode", orgCodes));
		List<String> deptSnapIdList = hrDepartmentSnapManager
				.getHisSnapIdList(snapCode);
		String snapCodes = OtherUtil.transferListToString(deptSnapIdList);
		deptFilters.add(new PropertyFilter("INS_snapId", snapCodes));
		deptFilters.add(new PropertyFilter("EQB_disabled", "0"));
		deptFilters.add(new PropertyFilter("EQB_deleted", "0"));
		deptFilters.add(new PropertyFilter("EQB_leaf", "1"));
		List<HrDepartmentSnap> hrDeptSnaps = hrDepartmentSnapManager
				.getByFilters(deptFilters);
		String deptIds = "";
		if (hrDeptSnaps != null && !hrDeptSnaps.isEmpty()) {
			for (HrDepartmentSnap deptSnap : hrDeptSnaps) {
				deptIds += deptSnap.getDeptId() + ",";
			}
			deptIds = OtherUtil.subStrEnd(deptIds, ",");
		} else {
			deptIds += "-1";
		}
		return deptIds;
	}
}
