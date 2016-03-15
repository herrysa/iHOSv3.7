package com.huge.ihos.pz.businesstype.dao.hibernate;



import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import sun.org.mozilla.javascript.internal.IdScriptableObject;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.dao.BusinessTypeDDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("businessTypeDDao")
public class BusinessTypeDDaoHibernate extends GenericDaoHibernate<BusinessTypeD, String> implements BusinessTypeDDao {

    public BusinessTypeDDaoHibernate() {
        super(BusinessTypeD.class);
    }
    
    public JQueryPager getBusinessTypeDCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BusinessTypeD.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessTypeDCriteria", e);
			return paginatedList;
		}

	}
    
    private void addColumn(Session session,String tableName,String colName) {
    	String sqlStr = "ALTER TABLE " + tableName +" ADD "+ colName +" varchar(50) NULL";
    	session.createSQLQuery(sqlStr).executeUpdate();
    }
    private void dropColumn(String tableName,String columnName) {
    	String sqlStr = "alter table " + tableName + " drop column " + columnName;
    	this.excuteSql(sqlStr);
    }
    @Override
    public BusinessTypeD saveBusinessTypeD(BusinessTypeD businessTypeD) throws Exception {
    	BusinessTypeD typeD = null;
    	Session session = null;
    	//Transaction tc = null;
    	try {
    		String tableName = businessTypeD.getBusinessType().getBusinessId() + "_D";
    		String colName = "";
    		session = this.sessionFactory.getCurrentSession();
    		//tc = session.beginTransaction();
    		boolean rowToCol = businessTypeD.getRowToCol();
    		if(rowToCol) {
    			String sourceTable = businessTypeD.getSourceTable();
    			Query query = session.createSQLQuery(sourceTable);
    			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    			List<Map<String, Object>> list = query.list();
    			if(list != null && !list.isEmpty()) {
    				String ids = "";
    				String names = "";
    				for(Map<String, Object> map : list) {
    					String id = map.get("id") + "";
    					String name = map.get("name") + "";
    					if(id != null && !"".equals(id)) {
    						ids += id +",";
    						names += name +",";
    						colName = businessTypeD.getFieldName() + "_" + id; // + "_acctId";
    						addColumn(session, tableName, colName);
    						colName = colName + "_name";
    						addColumn(session, tableName, colName);
    					}
    				}
    				businessTypeD.setSourceId(ids.substring(0,ids.length()-1));
    				businessTypeD.setSourceName(names.substring(0,names.length()-1));
    			}
    		} else {
    			boolean accCol = businessTypeD.getAccCol();
    			if(accCol) {
    				colName = "acctId";
    			} else {
    				colName = businessTypeD.getFieldName();
    			}
    			addColumn(session, tableName, colName);
    			colName = colName + "_name";
				addColumn(session, tableName, colName);
    		}
    		typeD = (BusinessTypeD) session.merge(businessTypeD);
    		//tc.commit();
    		
		} catch (Exception e) {
			throw e;
			/*if(tc != null) {
				tc.rollback();
			}*/
		}
		return typeD;
    }
    
    public void deleteBusinessTypeD(BusinessTypeD businessTypeD) {
    	this.remove(businessTypeD.getId());
    	String tableName = businessTypeD.getBusinessType().getBusinessId() + "_D";
    	if(businessTypeD.getRowToCol() != null && businessTypeD.getRowToCol()) {
    		String fieldName = businessTypeD.getFieldName();
    		String ids = businessTypeD.getSourceId();
    		if(ids!= null  && !"".equals(ids)) {
    			String[] idArr =  ids.split(",");
    			for(int i = 0;i< idArr.length;i++) {
    				String columnName =  fieldName + "_" + idArr[i]; // + "_acctId";
    				dropColumn(tableName, columnName);
    				columnName = columnName + "_name";
    				dropColumn(tableName, columnName);
    			}
    		} else {
    			String sourceTable = businessTypeD.getSourceTable();
    			List<Map<String, Object>> list = this.getBySqlToMap(sourceTable);
    			for(Map<String, Object> map : list) {
    				String id = (String) map.get("id");
    				String columnName = fieldName + "_" + id; // + "_acctId";
    				dropColumn(tableName, columnName);
    				columnName = columnName + "_name";
    				dropColumn(tableName, columnName);
    			}
    			
    		}
    	} else {
    		String columnName = businessTypeD.getFieldName();
    		dropColumn(tableName, columnName);
			columnName = columnName + "_name";
			dropColumn(tableName, columnName);
    	}
    	
    }
    
    @Override
    public List<BusinessTypeD> getAllByBusinessId(String businessId) {
    	Criteria criteria = this.getCriteria();
    	criteria.add(Restrictions.eq("businessType.businessId", businessId));
    	return criteria.list();
    }
}
