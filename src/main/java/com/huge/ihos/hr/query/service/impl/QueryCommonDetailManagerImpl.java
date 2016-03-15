package com.huge.ihos.hr.query.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.query.dao.QueryCommonDetailDao;
import com.huge.ihos.hr.query.model.QueryCommonDetail;
import com.huge.ihos.hr.query.service.QueryCommonDetailManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("queryCommonDetailManager")
public class QueryCommonDetailManagerImpl extends GenericManagerImpl<QueryCommonDetail, String> implements QueryCommonDetailManager {
    private QueryCommonDetailDao queryCommonDetailDao;

    @Autowired
    public QueryCommonDetailManagerImpl(QueryCommonDetailDao queryCommonDetailDao) {
        super(queryCommonDetailDao);
        this.queryCommonDetailDao = queryCommonDetailDao;
    }
    
    public JQueryPager getQueryCommonDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return queryCommonDetailDao.getQueryCommonDetailCriteria(paginatedList,filters);
	}
    @Override
    public void saveQueryCommonDetailGridData(String gridAllDatas,String queryCommonId,Person person){
    	queryCommonDetailDao.saveQueryCommonDetailGridData(gridAllDatas, queryCommonId, person);
    }
    @Override
    public String queryHrPersonIds(String queryCommonId,String snapCode){
    	return queryCommonDetailDao.queryHrPersonIds(queryCommonId,snapCode);
    }
    @Override
    public String getQueryCommonSql(){
    	return queryCommonDetailDao.getQueryCommonSql();
    }
    @Override
    public String getQueryCommonResult(String sql,String personId){
    	return queryCommonDetailDao.getQueryCommonResult(sql, personId);
    }
    @Override
    public String getQueryCommonSql(String queryCommonId,String snapCode){
    	return queryCommonDetailDao.getQueryCommonSql(queryCommonId,snapCode);
    }
}