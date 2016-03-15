package com.huge.ihos.pz.businesstype.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.pz.businesstype.dao.BusinessTypeJDao;
import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("businessTypeJDao")
public class BusinessTypeJDaoHibernate extends GenericDaoHibernate<BusinessTypeJ, String> implements BusinessTypeJDao {

    public BusinessTypeJDaoHibernate() {
        super(BusinessTypeJ.class);
    }
    
    public JQueryPager getBusinessTypeJCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, BusinessTypeJ.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getBusinessTypeJCriteria", e);
			return paginatedList;
		}

	}
    
    private void addColumn(Session session,String tableName,String colName) {
    	String sqlStr = "ALTER TABLE " + tableName +" ADD "+ colName +" varchar(50) NULL";
    	session.createSQLQuery(sqlStr).executeUpdate();
    }
    
    @Override
    public BusinessTypeJ saveBusinessTypeJ(BusinessTypeJ businessTypeJ) throws Exception {
    	BusinessTypeJ typeJ = null;
    	Session session = null;
    	//Transaction tc = null;
    	try {
    		String tableName = businessTypeJ.getBusinessType().getBusinessId() + "_J";
    		String colName = "";
    		session = this.sessionFactory.getCurrentSession();
    		//tc = session.beginTransaction();
    		boolean rowToCol = businessTypeJ.getRowToCol();
    		if(rowToCol) {
    			String sourceTable = businessTypeJ.getSourceTable();
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
    						ids += id+",";
    						names += name + ",";
    						colName = businessTypeJ.getFieldName() + "_" + id; // + "_acctId";
    						addColumn(session, tableName, colName);
    						colName = colName + "_name";
    						addColumn(session, tableName, colName);
    					}
    				}
    				businessTypeJ.setSourceId(ids.substring(0,ids.length()-1));
    				businessTypeJ.setSourceName(names.substring(0,names.length()-1));
    			}

    		} else {
    			boolean accCol = businessTypeJ.getAccCol();
    			if(accCol) {
    				colName = "acctId";
    			} else {
    				colName = businessTypeJ.getFieldName();
    			}
    			addColumn(session, tableName, colName);
    			colName = colName + "_name";
				addColumn(session, tableName, colName);
    		}
    		typeJ = (BusinessTypeJ) session.merge(businessTypeJ);
    		//tc.commit();
    		
		} catch (Exception e) {
			throw e;
			/*if(tc != null) {
				tc.rollback();
			}*/
		}
		return typeJ;
    }
    
    private void dropColumn(String tableName,String columnName) {
    	String sqlStr = "alter table " + tableName + " drop column " + columnName;
    	this.excuteSql(sqlStr);
    }
    
    public void deleteBusinessTypeJ(BusinessTypeJ businessTypeJ) {
    	this.remove(businessTypeJ.getId());
    	String tableName = businessTypeJ.getBusinessType().getBusinessId() + "_J";
    	if(businessTypeJ.getRowToCol() != null && businessTypeJ.getRowToCol()) {
    		String fieldName = businessTypeJ.getFieldName();
    		String ids = businessTypeJ.getSourceId();
    		if(ids!= null  && !"".equals(ids)) {
    			String[] idArr =  ids.split(",");
    			for(int i = 0;i< idArr.length;i++) {
    				String columnName =  fieldName + "_" + idArr[i]; // + "_acctId";
    				dropColumn(tableName, columnName);
    				columnName = columnName + "_name";
    				dropColumn(tableName, columnName);
    			}
    		} else {
    			String sourceTable = businessTypeJ.getSourceTable();
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
    		String columnName = businessTypeJ.getFieldName();
    		dropColumn(tableName, columnName);
			columnName = columnName + "_name";
			dropColumn(tableName, columnName);
    	}
    }
    @Override
    public List<BusinessTypeJ> getAllByBusinessId(String businessId) {
    	Criteria criteria = this.getCriteria();
    	criteria.add(Restrictions.eq("businessType.businessId", businessId));
    	return criteria.list();
    }
}
