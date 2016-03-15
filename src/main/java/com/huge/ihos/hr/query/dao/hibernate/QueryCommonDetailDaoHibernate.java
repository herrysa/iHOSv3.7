package com.huge.ihos.hr.query.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.query.dao.QueryCommonDao;
import com.huge.ihos.hr.query.dao.QueryCommonDetailDao;
import com.huge.ihos.hr.query.model.QueryCommon;
import com.huge.ihos.hr.query.model.QueryCommonDetail;
import com.huge.ihos.system.configuration.bdinfo.dao.FieldInfoDao;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.util.OtherUtil;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("queryCommonDetailDao")
public class QueryCommonDetailDaoHibernate extends
		GenericDaoHibernate<QueryCommonDetail, String> implements
		QueryCommonDetailDao {

	private QueryCommonDao queryCommonDao;
	private FieldInfoDao fieldInfoDao;

	public QueryCommonDetailDaoHibernate() {
		super(QueryCommonDetail.class);
	}

	@Autowired
	public void setQueryCommonDao(QueryCommonDao queryCommonDao) {
		this.queryCommonDao = queryCommonDao;
	}

	@Autowired
	public void setFieldInfoDao(FieldInfoDao fieldInfoDao) {
		this.fieldInfoDao = fieldInfoDao;
	}

	public JQueryPager getQueryCommonDetailCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, QueryCommonDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getQueryCommonDetailCriteria", e);
			return paginatedList;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveQueryCommonDetailGridData(String gridAllDatas,
			String queryCommonId, Person person) {
		JSONObject json = JSONObject.fromObject(gridAllDatas);
		JSONArray allDatas = json.getJSONArray("edit");
		String hql = "select id from "
				+ this.getPersistentClass().getSimpleName()
				+ " where  queryCommon.id=?";
		hql += " ORDER BY id ASC";
		List<Object> ids = new ArrayList<Object>();
		ids = this.getHibernateTemplate().find(hql, queryCommonId);
		String id = "";
		for (int i = 0; i < allDatas.size(); i++) {
			// 获取每一个JsonObject对象
			JSONObject myjObject = allDatas.getJSONObject(i);
			if (myjObject.size() > 0) {
				QueryCommonDetail queryCommonDetail = new QueryCommonDetail();
				id = myjObject.getString("id");
				if (id != null && !id.equals("")) {
					queryCommonDetail = this.get(id);
					ids.remove(id);
				}
				queryCommonDetail.setName(myjObject.getString("name"));
				queryCommonDetail
						.setOperation(myjObject.getString("operation"));
				queryCommonDetail.setTargetValue(myjObject
						.getString("targetValue"));
				queryCommonDetail.setFieldInfo(fieldInfoDao.get(myjObject
						.getString("fieldInfoId")));
				queryCommonDetail
						.setTableName(myjObject.getString("tableName"));
				queryCommonDetail.setQueryCommon(queryCommonDao
						.get(queryCommonId));
				queryCommonDetail.setChangeUser(person);
				queryCommonDetail.setChangeDate(new Date());
				this.save(queryCommonDetail);
			}
		}
		String[] ida = new String[ids.size()];
		ids.toArray(ida);
		this.remove(ida);
	}

	@Override
	public String queryHrPersonIds(String queryCommonId, String snapCode) {
		Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
		List<String> joinTables = new ArrayList<String>();// 需要使用left join的表
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		QueryCommon queryCommon = queryCommonDao.get(queryCommonId);
		int tableAliasNum = 1;
		String mainTable = "v_hr_person_current";// 统计项表
		String mainTablePk = "personId";// 统计项表主键
		tableAlias.put(mainTable, "mainTable");
		String filterSql = "";
		String sqlEnd = "";
		String expression = queryCommon.getExpression();
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
		filters.clear();
		List<QueryCommonDetail> queryCommonDetails = new ArrayList<QueryCommonDetail>();
		filters.add(new PropertyFilter("EQS_queryCommon.id", queryCommon
				.getId()));
		filters.add(new PropertyFilter("OAS_id", "id"));
		queryCommonDetails = this.getByFilters(filters);
		Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
		String itrNext = "";
		String tableName = "";
		while (itr.hasNext()) {
			itrNext = itr.next();
			if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
				int num = Integer.valueOf(itrNext);
				if (num > 0 && num <= queryCommonDetails.size()) {
					QueryCommonDetail queryCommonDetail = queryCommonDetails
							.get(num - 1);
					tableName = queryCommonDetail.getFieldInfo().getBdInfo()
							.getTableName();
					if (!tableName.equals(mainTable)
							&& !tableAlias.containsKey(tableName)) {
						joinTables.add(tableName);
						tableAlias.put(tableName, "table" + tableAliasNum);
						tableAliasNum++;
					}
					if (queryCommonDetail.getOperation().equals("like")) {// 统计指标操作符为Like时
						filterSql += tableAlias.get(tableName)
								+ "."
								+ queryCommonDetail.getFieldInfo()
										.getFieldCode() + " "
								+ queryCommonDetail.getOperation() + " '%"
								+ queryCommonDetail.getTargetValue() + "%' ";
					} else {// 统计指标操作符为其他字符时
						filterSql += tableAlias.get(tableName)
								+ "."
								+ queryCommonDetail.getFieldInfo()
										.getFieldCode()
								+ queryCommonDetail.getOperation() + "'"
								+ queryCommonDetail.getTargetValue() + "' ";
					}
				}

			} else if (itrNext.equals("(") || itrNext.equals(")")) {
				filterSql += itrNext + " ";
			} else if (itrNext.equals("+")) {
				filterSql += "or ";
			} else if (itrNext.equals("*")) {
				filterSql += "and ";
			}
		}
		if (!filterSql.equals("")) {
			sqlEnd += " " + filterSql + " or";
		}
		filterSql = "";
		/** sql start **/
		String sql = "SELECT " + tableAlias.get(mainTable) + "." + mainTablePk
				+ " AS id";
		sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable) + " ";
		for (String joinTableTemp : joinTables) {// 需要join的表
			sql += " LEFT JOIN " + joinTableTemp + " AS "
					+ tableAlias.get(joinTableTemp);
			sql += " ON " + tableAlias.get(joinTableTemp) + "." + mainTablePk
					+ "=" + tableAlias.get(mainTable) + "." + mainTablePk + " ";
		}
		String sqlEndStr = "";
		if (sqlEnd.length() > 2) {// where条件
			if (sqlEnd.substring(sqlEnd.length() - 2, sqlEnd.length()).equals(
					"or")) {
				sqlEnd = sqlEnd.substring(0, sqlEnd.length() - 2);
			}
			sqlEndStr += " WHERE " + sqlEnd;
		}
		if (snapCode != null) {
			sql = sql.replace(mainTable, "v_hr_person_his");
			String snapCodeSql = "";
			snapCodeSql += tableAlias.get(mainTable) + ".snapId IN (";
			snapCodeSql += "SELECT MAX(snapId) FROM v_hr_person_his WHERE snapCode <='"
					+ snapCode + "' GROUP BY personId)";
			for (String joinTableTemp : joinTables) {// 需要join的表
				snapCodeSql += " AND " + tableAlias.get(joinTableTemp)
						+ ".snapCode <='" + snapCode + "'";
			}
			if (!sqlEndStr.equals("")) {
				sqlEndStr += " AND " + snapCodeSql;
			} else {
				sqlEndStr += " WHERE " + snapCodeSql;
			}
		}
		sql = sql + sqlEndStr;
		List<Map<String, Object>> queryDataList = getBySqlToMap(sql);
		String ids = "";
		if (queryDataList != null && queryDataList.size() > 0) {// 遍历SQL查询的结果
			for (Map<String, Object> dataMapTemp : queryDataList) {
				if (ids.equals("")) {
					ids = ids + dataMapTemp.get("id").toString();
				} else {
					ids = ids + "," + dataMapTemp.get("id").toString();
				}
			}
		}
		return ids;
	}

	@Override
	public String getQueryCommonSql(String queryCommonId, String snapCode) {
		Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
		List<String> joinTables = new ArrayList<String>();// 需要使用left join的表
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		QueryCommon queryCommon = queryCommonDao.get(queryCommonId);
		int tableAliasNum = 1;
		String mainTable = "v_hr_person_current";// 统计项表
		String mainTablePk = "personId";// 统计项表主键
		tableAlias.put(mainTable, "mainTable");
		String filterSql = "";
		String sqlEnd = "";
		String expression = queryCommon.getExpression();
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
		filters.clear();
		List<QueryCommonDetail> queryCommonDetails = new ArrayList<QueryCommonDetail>();
		filters.add(new PropertyFilter("EQS_queryCommon.id", queryCommon
				.getId()));
		filters.add(new PropertyFilter("OAS_id", "id"));
		queryCommonDetails = this.getByFilters(filters);
		Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
		String itrNext = "";
		String tableName = "";
		while (itr.hasNext()) {
			itrNext = itr.next();
			if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
				int num = Integer.valueOf(itrNext);
				if (num > 0 && num <= queryCommonDetails.size()) {
					QueryCommonDetail queryCommonDetail = queryCommonDetails
							.get(num - 1);
					tableName = queryCommonDetail.getFieldInfo().getBdInfo()
							.getTableName();
					if (!tableName.equals(mainTable)
							&& !tableAlias.containsKey(tableName)) {
						joinTables.add(tableName);
						tableAlias.put(tableName, "table" + tableAliasNum);
						tableAliasNum++;
					}
					if (queryCommonDetail.getOperation().equals("like")) {// 统计指标操作符为Like时
						filterSql += tableAlias.get(tableName)
								+ "."
								+ queryCommonDetail.getFieldInfo()
										.getFieldCode() + " "
								+ queryCommonDetail.getOperation() + " '%"
								+ queryCommonDetail.getTargetValue() + "%' ";
					} else {// 统计指标操作符为其他字符时
						filterSql += tableAlias.get(tableName)
								+ "."
								+ queryCommonDetail.getFieldInfo()
										.getFieldCode()
								+ queryCommonDetail.getOperation() + "'"
								+ queryCommonDetail.getTargetValue() + "' ";
					}
				}

			} else if (itrNext.equals("(") || itrNext.equals(")")) {
				filterSql += itrNext + " ";
			} else if (itrNext.equals("+")) {
				filterSql += "or ";
			} else if (itrNext.equals("*")) {
				filterSql += "and ";
			}
		}
		if (!filterSql.equals("")) {
			sqlEnd += " " + filterSql + " or";
		}
		filterSql = "";
		/** sql start **/
		String sql = "SELECT " + tableAlias.get(mainTable) + "." + mainTablePk
				+ " AS id";
		sql += " FROM " + mainTable + " AS " + tableAlias.get(mainTable) + " ";
		for (String joinTableTemp : joinTables) {// 需要join的表
			sql += " LEFT JOIN " + joinTableTemp + " AS "
					+ tableAlias.get(joinTableTemp);
			sql += " ON " + tableAlias.get(joinTableTemp) + "." + mainTablePk
					+ "=" + tableAlias.get(mainTable) + "." + mainTablePk + " ";
		}
		String sqlEndStr = "";
		if (sqlEnd.length() > 2) {// where条件
			if (sqlEnd.substring(sqlEnd.length() - 2, sqlEnd.length()).equals(
					"or")) {
				sqlEnd = sqlEnd.substring(0, sqlEnd.length() - 2);
			}
			sqlEndStr += " WHERE " + sqlEnd;
		}
		if (snapCode != null) {
			sql = sql.replace(mainTable, "v_hr_person_his");
			String snapCodeSql = "";
			snapCodeSql += tableAlias.get(mainTable) + ".snapId IN (";
			snapCodeSql += "SELECT MAX(snapId) FROM v_hr_person_his WHERE snapCode <='"
					+ snapCode + "' GROUP BY personId)";
			for (String joinTableTemp : joinTables) {// 需要join的表
				snapCodeSql += " AND " + tableAlias.get(joinTableTemp)
						+ ".snapCode <='" + snapCode + "'";
			}
			if (!sqlEndStr.equals("")) {
				sqlEndStr += " AND " + snapCodeSql;
			} else {
				sqlEndStr += " WHERE " + snapCodeSql;
			}
		}
		sql = sql + sqlEndStr;
		return sql;
	}

	@Override
	public String getQueryCommonSql() {
		String sqlStr = "";
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQB_disabled", "0"));
		List<QueryCommon> qCommons = queryCommonDao.getByFilters(filters);
		if (OtherUtil.measureNotNull(qCommons) && !qCommons.isEmpty()) {
			String mainTable = "v_hr_person_current";// 统计项表
			String mainTablePk = "personId";// 统计项表主键
			for (QueryCommon queryCommon : qCommons) {
				String qcId = queryCommon.getId();
				Map<String, String> tableAlias = new HashMap<String, String>();// sql中出现的表别名(Key为表，value为表别名)
				List<String> joinTables = new ArrayList<String>();// 需要使用left
																	// join的表
				filters.clear();
				int tableAliasNum = 1;
				tableAlias.put(mainTable, "mainTable");
				String filterSql = "";
				String sqlEnd = "";
				String expression = queryCommon.getExpression();
				expression = expression.trim();
				String strDigital = "";
				List<String> expressionList = new ArrayList<String>();// 统计条件公式第一遍解析(提取数字)
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
				filters.clear();
				List<QueryCommonDetail> queryCommonDetails = new ArrayList<QueryCommonDetail>();
				filters.add(new PropertyFilter("EQS_queryCommon.id",
						queryCommon.getId()));
				filters.add(new PropertyFilter("OAS_id", "id"));
				queryCommonDetails = this.getByFilters(filters);
				Iterator<String> itr = expressionList.iterator(); // 统计条件公式第二遍解析；数字，符号的具体意义
				String itrNext = "";
				String tableName = "";
				while (itr.hasNext()) {
					itrNext = itr.next();
					if (itrNext.matches("[0-9]+")) {// 统计条件 公式是数字的
						int num = Integer.valueOf(itrNext);
						if (num > 0 && num <= queryCommonDetails.size()) {
							QueryCommonDetail queryCommonDetail = queryCommonDetails
									.get(num - 1);
							tableName = queryCommonDetail.getFieldInfo()
									.getBdInfo().getTableName();
							if (!tableName.equals(mainTable)
									&& !tableAlias.containsKey(tableName)) {
								joinTables.add(tableName);
								tableAlias.put(tableName, "table"
										+ tableAliasNum);
								tableAliasNum++;
							}
							if (queryCommonDetail.getOperation().equals("like")) {// 统计指标操作符为Like时
								filterSql += tableAlias.get(tableName)
										+ "."
										+ queryCommonDetail.getFieldInfo()
												.getFieldCode() + " "
										+ queryCommonDetail.getOperation()
										+ " '%"
										+ queryCommonDetail.getTargetValue()
										+ "%' ";
							} else {// 统计指标操作符为其他字符时
								filterSql += tableAlias.get(tableName)
										+ "."
										+ queryCommonDetail.getFieldInfo()
												.getFieldCode()
										+ queryCommonDetail.getOperation()
										+ "'"
										+ queryCommonDetail.getTargetValue()
										+ "' ";
							}
						}

					} else if (itrNext.equals("(") || itrNext.equals(")")) {
						filterSql += itrNext + " ";
					} else if (itrNext.equals("+")) {
						filterSql += "or ";
					} else if (itrNext.equals("*")) {
						filterSql += "and ";
					}
				}
				if (!filterSql.equals("")) {
					sqlEnd += " " + filterSql + " or";
				}
				filterSql = "";
				/** sql start **/
				// String sql="SELECT "+
				// tableAlias.get(mainTable)+"."+mainTablePk +" AS id";
				String sql = "SELECT '" + qcId + "' AS id";
				sql += " FROM " + mainTable + " AS "
						+ tableAlias.get(mainTable) + " ";
				for (String joinTableTemp : joinTables) {// 需要join的表
					sql += " LEFT JOIN " + joinTableTemp + " AS "
							+ tableAlias.get(joinTableTemp);
					sql += " ON " + tableAlias.get(joinTableTemp) + "."
							+ mainTablePk + "=" + tableAlias.get(mainTable)
							+ "." + mainTablePk + " ";
				}
				String sqlEndStr = "";
				if (sqlEnd.length() > 2) {// where条件
					if (sqlEnd.substring(sqlEnd.length() - 2, sqlEnd.length())
							.equals("or")) {
						sqlEnd = sqlEnd.substring(0, sqlEnd.length() - 2);
					}
					sqlEndStr += " WHERE (" + sqlEnd + ") AND "
							+ tableAlias.get(mainTable) + "." + mainTablePk
							+ " = ? ";
				} else {
					sqlEndStr += " WHERE " + tableAlias.get(mainTable) + "."
							+ mainTablePk + " = ? ";
				}
				sql = sql + sqlEndStr;
				if ("".equals(sqlStr)) {
					sqlStr = sql;
				} else {
					sqlStr = sqlStr + " UNION " + sql;
				}
			}
		}
		return sqlStr;
	}

	@Override
	public String getQueryCommonResult(String sql, String personId) {
		if (!sql.contains("?")) {
			return null;
		}
		sql = sql.replaceAll("[?]", "'" + personId + "'");
		List<Map<String, Object>> queryDataList = getBySqlToMap(sql);
		String ids = null;
		if (queryDataList != null && queryDataList.size() > 0) {// 遍历SQL查询的结果
			for (Map<String, Object> dataMapTemp : queryDataList) {
				if (ids == null) {
					ids = dataMapTemp.get("id").toString();
				} else {
					ids = ids + "," + dataMapTemp.get("id").toString();
				}
			}
		}
		return ids;
	}
}
