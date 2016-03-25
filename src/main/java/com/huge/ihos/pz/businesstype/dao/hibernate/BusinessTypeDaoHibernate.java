package com.huge.ihos.pz.businesstype.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.pz.businesstype.dao.BusinessTypeDao;
import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("businessTypeDao")
public class BusinessTypeDaoHibernate extends GenericDaoHibernate<BusinessType, String> implements BusinessTypeDao {

	public BusinessTypeDaoHibernate() {
		super(BusinessType.class);
	}

	public JQueryPager getBusinessTypeCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("businessId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BusinessType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessTypeCriteria", e);
			return paginatedList;
		}

	}
	
	@Override
	public List<BusinessType> getAllAvailable() {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.eq("disabled", false))
				.addOrder(Order.asc("sn"));
		return criteria.list();
	}
	
	private void createTable(Session session,String tableName,String type) {
		String id = "id";
		if("J".equals(type)) {
			id = "jId";
		} else if("D".equals(type)) {
			id = "dId";
		}
		String sqlStr = "create table " + tableName + "(";
		sqlStr += id + " numeric(20) PRIMARY KEY NOT NULL IDENTITY(1,1)";
		sqlStr += ")";
		session.createSQLQuery(sqlStr).executeUpdate();
	}
	
	@Override
	public BusinessType saveBusinessType(BusinessType businessType) {
		Session session = null;
		//Transaction tc = null;
		BusinessType type = null;
		try {
			session = this.sessionFactory.getCurrentSession();
			//tc = session.beginTransaction();
			String tableName = businessType.getBusinessId();
			String tableNameJ = tableName + "_J";
			createTable(session, tableNameJ,"J");
			String tableNameD = tableName + "_D";
			createTable(session, tableNameD,"D");
			type = (BusinessType) session.merge(businessType);
			
			//tc.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			/*if(tc != null) {
				tc.rollback();
			}*/
		}
		
		return type;
	}
	
	@Override
	public List<BusinessType> getBusinessTypeByIds(String ids) {
		Criteria criteria = this.getCriteria();
		criteria.add(Restrictions.in("businessId", ids.split(",")));
		List<BusinessType> list = criteria.list();
		return list;
	}
	
	@Override
	public Map<String, Object> getSysTableByName(String tableName) {
		Session session = this.sessionFactory.getCurrentSession();
		String sqlStr = "select name from sysobjects where xtype = 'u' and name = '"+tableName + "'";
		Query query = session.createSQLQuery(sqlStr);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public void delBusinessType(String businessId) {
		String tableJ = businessId + "_J";
		Map<String, Object> mapJ = getSysTableByName(tableJ);
		if(mapJ != null) {
			String sqlStrJ = "drop table " + businessId + "_J";
			this.excuteSql(sqlStrJ);
		}
		String tableD = businessId + "_D";
		Map<String, Object> mapD = getSysTableByName(tableD);
		if(mapD != null) {
			String sqlStrD = "drop table " + businessId + "_D";
			this.excuteSql(sqlStrD);
		}
		this.remove(businessId);
	}
	
	@Override
	public List<BusinessType> getAllDescendants(String businessId) {
		Criteria criteria = this.getCriteria();
		List<BusinessType> businessTypes = new ArrayList<BusinessType>();
		List<BusinessType> businessTypeTemp = criteria.add(Restrictions.eq("parentType.businessId", businessId)).list();
		if(businessTypeTemp != null && !businessTypeTemp.isEmpty()) {
			businessTypes.addAll(businessTypeTemp);
			for(BusinessType businessType : businessTypeTemp) {
				List<BusinessType> businessTypeTemp2 = this.getAllDescendants(businessType.getBusinessId());
				businessTypes.addAll(businessTypeTemp2);
			}
		}
		return businessTypes;
	}

	@Override
	public JQueryPager getBusinessTypeQuery(JQueryPager paginatedList, Map<String, String> sqlMap) {
		String rowSql = sqlMap.get("rowSql");
		String countSql = sqlMap.get("countSql");
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(countSql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> countList = query.list();
		Integer count = 0;
		if(countList != null) {
			Map<String, Object> countMap = countList.get(0);
			count =  (Integer) countMap.get("c");
		}
		int index = paginatedList.getIndex();
		int pageSize = paginatedList.getPageSize();
		Query resultQuery = session.createSQLQuery(rowSql);
		resultQuery.setFirstResult(index*pageSize);
		resultQuery.setMaxResults(count);
		resultQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = resultQuery.list();
		paginatedList.setList(list);
		paginatedList.setTotalNumberOfRows(count);
		return paginatedList;
	}
}
