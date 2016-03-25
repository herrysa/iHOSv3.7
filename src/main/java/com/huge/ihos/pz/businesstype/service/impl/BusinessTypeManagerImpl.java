package com.huge.ihos.pz.businesstype.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.pz.businesstype.dao.BusinessTypeDDao;
import com.huge.ihos.pz.businesstype.dao.BusinessTypeDao;
import com.huge.ihos.pz.businesstype.dao.BusinessTypeJDao;
import com.huge.ihos.pz.businesstype.model.BusinessType;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.ihos.pz.businesstype.service.BusinessTypeManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("businessTypeManager")
public class BusinessTypeManagerImpl extends GenericManagerImpl<BusinessType, String> implements BusinessTypeManager {
	private BusinessTypeDao businessTypeDao;
	
	private BusinessTypeJDao businessTypeJDao;
	
	private BusinessTypeDDao businessTypeDDao;

	@Autowired
	public BusinessTypeManagerImpl(BusinessTypeDao businessTypeDao,BusinessTypeJDao businessTypeJDao,BusinessTypeDDao businessTypeDDao) {
		super(businessTypeDao);
		this.businessTypeDao = businessTypeDao;
		this.businessTypeJDao = businessTypeJDao;
		this.businessTypeDDao = businessTypeDDao;
	}

	public JQueryPager getBusinessTypeCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {
		return businessTypeDao.getBusinessTypeCriteria(paginatedList, filters);
	}

	@Override
	public List<BusinessType> getAllAvailable() {
		return this.businessTypeDao.getAllAvailable();
	}

	@Override
	public BusinessType saveBusinessType(BusinessType businessType) {
		return this.businessTypeDao.saveBusinessType(businessType);
	}

	@Override
	public void delBusinessType(String businessId) {
		this.businessTypeDao.delBusinessType(businessId);
	}
	
	@Override
	public boolean checkBusinessTypeAdd(String businessId) {
		String tableJ = businessId + "_J";
		String tableD = businessId + "_D";
		Map<String, Object> mapJ = this.businessTypeDao.getSysTableByName(tableJ);
		Map<String, Object> mapD = this.businessTypeDao.getSysTableByName(tableD);
		
		if(mapJ != null && !mapJ.isEmpty() 
				&& mapD != null && mapD.isEmpty()) {
			return false;
		} else {
			return true;
		}
		
	}
	@Override
	public boolean checkDBTableExist(String tableName) {
		Map<String, Object> map = this.businessTypeDao.getSysTableByName(tableName);
		if(map !=null && !map.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Map<String, Object> getAccountColMap(String businessId,String type) {
		List<BusinessTypeJ> businessTypeJList = this.businessTypeJDao.getAllByBusinessId(businessId);
		List<BusinessTypeD> businessTypeDList = this.businessTypeDDao.getAllByBusinessId(businessId);
		Map<String, Object> colMap = new HashMap<String, Object>();
		if("J".equals(type)) {
			if(businessTypeJList != null && !businessTypeJList.isEmpty()) {
				for(BusinessTypeJ businessTypeJ : businessTypeJList) {
					String colName = businessTypeJ.getColName();
					String fieldName = businessTypeJ.getFieldName();
					String sourceTable = businessTypeJ.getSourceTable();
					boolean rowToCol = businessTypeJ.getRowToCol();
					boolean accCol = businessTypeJ.getAccCol();
					if(rowToCol) {
						List<Map<String, Object>> list = this.getBySqlToMap(sourceTable);
						if(list != null && !list.isEmpty()) {
		    				for(Map<String, Object> map : list) {
		    					String id = (String) map.get("id");
		    					if(id != null && !"".equals(id)) {
		    						String name = (String) map.get("name");
		    						colMap.put("J#"+fieldName+"_acct_"+name, name);
		    					}
		    				}
		    			}
					} else {
						if(accCol) {
							colMap.put("J#acctId", colName);
						} else {
							colMap.put("J#"+fieldName, colName);
						}
					}
				}
			}
		} else if("D".equals(type)) {
			if(businessTypeDList != null && !businessTypeDList.isEmpty()) {
				for(BusinessTypeD businessTypeD : businessTypeDList) {
					String colName = businessTypeD.getColName();
					String fieldName = businessTypeD.getFieldName();
					String sourceTable = businessTypeD.getSourceTable();
					boolean rowToCol = businessTypeD.getRowToCol();
					boolean accCol = businessTypeD.getAccCol();
					if(rowToCol) {
						List<Map<String, Object>> list = this.getBySqlToMap(sourceTable);
						if(list != null && !list.isEmpty()) {
		    				for(Map<String, Object> map : list) {
		    					String id = (String) map.get("id");
		    					if(id != null && !"".equals(id)) {
		    						String name = (String) map.get("name");
		    						colMap.put("D#"+fieldName+"_acct_"+name, name);
		    					}
		    				}
		    			}
					} else {
						if(accCol) {
							colMap.put("D#acctId", colName);
						} else {
							colMap.put("D#"+fieldName, colName);
						}
					}
				}
			}
		}
		return colMap;
	}
	
	@Override
	public void createCollectTempTable(String businessId, String collectTempTable) {
		String createSql = "create table "+collectTempTable + "(";
		createSql += " tempId numeric(20) PRIMARY KEY NOT NULL IDENTITY(1,1),";
		createSql += " je numeric(19,4) null,";
		createSql += " snapcode varchar(17) null,";
		createSql += " direction varchar(10)";
		createSql += ")";
		this.businessTypeDao.excuteSql(createSql);
		if(businessId != null && !"".equals(businessId)) {
			BusinessType businessType = this.businessTypeDao.get(businessId);
			if(businessType != null) {
				Set<BusinessTypeJ> businessTypeJs = businessType.getBusinessTypeJs();
				Set<BusinessTypeD> businessTypeDs = businessType.getBusinessTypeDs();
				Set<String> sqlSet = new HashSet<String>();
				if(businessTypeJs != null) {
					for(BusinessTypeJ typeJ : businessTypeJs) {
						if(!typeJ.getAccCol() || typeJ.getRowToCol()) {
							String alterSql = "ALTER TABLE " + collectTempTable +" ADD "+ typeJ.getFieldName() +" varchar(50) NULL";
							sqlSet.add(alterSql);
						}
					}
				}
				if(businessTypeDs != null) {
					for (BusinessTypeD typeD : businessTypeDs) {
						if(!typeD.getAccCol() || typeD.getRowToCol()) {
							String alterSql = "ALTER TABLE " + collectTempTable +" ADD "+ typeD.getFieldName() +" varchar(50) NULL";
							sqlSet.add(alterSql);
						}
					}
				}
				if(!sqlSet.isEmpty()) {
					List<String> sqlList = new ArrayList<String>(sqlSet);
					this.executeSqlList(sqlList);
				}
			}
		}
	}
	
	@Override
	public void checkBusinessType(String businessId) {
		BusinessType businessType = this.get(businessId);
		Set<BusinessTypeJ> businessTypeJs = businessType.getBusinessTypeJs();
		Set<BusinessTypeD> businessTypeDs = businessType.getBusinessTypeDs();
		Map<String, Object> jTableInfo = this.businessTypeDao.getSysTableByName(businessId + "_J");
		Map<String, Object> dTableInfo = this.businessTypeDao.getSysTableByName(businessId + "_D");
		if(jTableInfo == null || jTableInfo.isEmpty()) {
			this.executeSql(makeCreateSql(businessId, "J"));
		} else {
			for(BusinessTypeJ businessTypeJ : businessTypeJs) {
				String fieldName = businessTypeJ.getFieldName();
				boolean rowToCol = businessTypeJ.getRowToCol();
				if(rowToCol) {
					String sourceId = businessTypeJ.getSourceId();
					String[] ids = sourceId.split(",");
					for(String id : ids) {
						String colName = fieldName + "_" + id;
						if(!jTableInfo.containsKey(colName)) {
							this.businessTypeDao.excuteSql(makeAlterSql(businessId, "J", colName));
						}
						colName += "_name";
						if(!jTableInfo.containsKey(colName)) {
							this.businessTypeDao.excuteSql(makeAlterSql(businessId, "J", colName));
						}
					}
				} else {
					String colName = fieldName;
					if(!jTableInfo.containsKey(colName)) {
						this.businessTypeDao.excuteSql(makeAlterSql(businessId, "J", colName));
					}
					colName += "_name";
					if(!jTableInfo.containsKey(colName)) {
						this.businessTypeDao.excuteSql(makeAlterSql(businessId, "J", colName));
					}
				}
			}
		}
		if(dTableInfo == null || dTableInfo.isEmpty()) {
			this.executeSql(makeCreateSql(businessId, "D"));
		} else {
			for(BusinessTypeD businessTypeD : businessTypeDs) {
				String fieldName = businessTypeD.getFieldName();
				boolean rowToCol = businessTypeD.getRowToCol();
				if(rowToCol) {
					String sourceId = businessTypeD.getSourceId();
					String[] ids = sourceId.split(",");
					for(String id : ids) {
						String colName = fieldName + "_" + id;
						if(!jTableInfo.containsKey(colName)) {
							this.businessTypeDao.excuteSql(makeAlterSql(businessId, "D", colName));
						}
						colName += "_name";
						if(!jTableInfo.containsKey(colName)) {
							this.businessTypeDao.excuteSql(makeAlterSql(businessId, "D", colName));
						}
					}
				} else {
					String colName = fieldName;
					if(!jTableInfo.containsKey(colName)) {
						this.businessTypeDao.excuteSql(makeAlterSql(businessId, "D", colName));
					}
					colName += "_name";
					if(!jTableInfo.containsKey(colName)) {
						this.businessTypeDao.excuteSql(makeAlterSql(businessId, "D", colName));
					}
				}
			}
		}
		
	}
	
	private String makeAlterSql(String businessId,String type,String colName) {
		String tableName = businessId + "_" + type;
		String sqlStr = "ALTER TABLE " + tableName +" ADD "+ colName +" varchar(50) NULL";
		return sqlStr;
	}
	
	private String makeCreateSql(String businessId,String type) {
		String tableName = businessId + "_" + type;
		String id = "id";
		if("J".equals(type)) {
			id = "jId";
		} else if("D".equals(type)) {
			id = "dId";
		}
		String sqlStr = "create table " + tableName + "(";
		sqlStr += id + " numeric(20) PRIMARY KEY NOT NULL IDENTITY(1,1)";
		sqlStr += ")";
		return sqlStr;
	}
	
	@Override
	public List<Map<String, Object>> getAccountDataMaps(String businessId) {
		String sqlStr = "select * from "+businessId;
		List<Map<String, Object>> list = this.getBySqlToMap(sqlStr);
		return list;
	}
	
	@Override
	public List<BusinessType> getAllDescendants(String businessId) {
		return this.businessTypeDao.getAllDescendants(businessId);
	}

	@Override
	public JQueryPager getBusinessTypeQuery(JQueryPager paginatedList, Map<String, String> sqlMap) {
		return this.businessTypeDao.getBusinessTypeQuery(paginatedList, sqlMap);
	}
}