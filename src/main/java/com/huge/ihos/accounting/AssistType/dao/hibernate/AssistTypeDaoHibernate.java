package com.huge.ihos.accounting.AssistType.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.AssistType.dao.AssistTypeDao;
import com.huge.ihos.accounting.AssistType.model.AssistType;
import com.huge.ihos.accounting.account.model.Account;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("assistTypeDao")
public class AssistTypeDaoHibernate extends
		GenericDaoHibernate<AssistType, String> implements AssistTypeDao {

	public AssistTypeDaoHibernate() {
		super(AssistType.class);
	}

	public JQueryPager getAssistTypeCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("typeCode");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(
					paginatedList, AssistType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getAssistTypeCriteria", e);
			return paginatedList;
		}

	}

	public List<HashMap<String, String>> getAssits(String table,
			String assitMark, String id_name, String id_value,
			List<AssistType> assitTypes) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		String sql = "select ";
		for (AssistType assit : assitTypes) {
			sql += assitMark + assit.getTypeCode() + " , ";
		}
		sql = sql.substring(0, sql.length() - 2);
		sql += "from " + table + " where 1=1 and " + id_name + "=" + id_value;
		Session session = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		List<HashMap<String, String>> list = session.createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (Map<String, String> map : list) {
			HashMap<String, String> assitType = new HashMap<String, String>();
			for (AssistType assit : assitTypes) {
				String assitColName = assitMark + assit.getTypeCode();
				String assitColValue = map.get(assitColName);
				assitType.put(assitColName, assitColValue);

				String itemSql = "";
				if ("0".equals(assit.getTypeCode())) {
					itemSql = "select deptId as id, name as name from t_department where deptId = '"
							+ assitColValue + "'";
				} /*
				 * else if("1".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("2".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("3".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("4".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("5".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("6".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("7".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("8".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("9".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; } else
				 * if("A".equals(assit.getAssisttypeCode())){ itemSql =
				 * "select GL_CashFlowItem as id, deptname as name from GL_CashFlowItem where deptId = '"
				 * +assitColValue+"'"; } else { itemSql =
				 * "select deptId as id, deptname as name from dept where deptId = '"
				 * +assitColValue+"'"; }
				 */

				List<HashMap<String, String>> itemList = session
						.createSQLQuery(itemSql)
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.list();
				if (itemList == null || itemList.size() != 1) {
					try {
						throw new Exception();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Map<String, String> mapp = itemList.get(0);
					String name = mapp.get("name");
					assitType.put(assitColName + "_name", name);
				}
			}
			result.add(assitType);
		}
		return result;
	}

	
}
