package com.huge.ihos.hr.query.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huge.ihos.hr.query.dao.QueryCommonDao;
import com.huge.ihos.hr.query.model.QueryCommon;
import com.huge.ihos.hr.query.model.QueryCommonDetail;
import com.huge.ihos.hr.query.service.QueryCommonDetailManager;
import com.huge.ihos.hr.query.service.QueryCommonManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("queryCommonManager")
public class QueryCommonManagerImpl extends GenericManagerImpl<QueryCommon, String> implements QueryCommonManager {
    private QueryCommonDao queryCommonDao;
    private QueryCommonDetailManager queryCommonDetailManager;

    @Autowired
    public QueryCommonManagerImpl(QueryCommonDao queryCommonDao) {
        super(queryCommonDao);
        this.queryCommonDao = queryCommonDao;
    }
    @Autowired
    public void setQueryCommonDetailManager(QueryCommonDetailManager queryCommonDetailManager) {
		this.queryCommonDetailManager = queryCommonDetailManager;
	}
    
    public JQueryPager getQueryCommonCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return queryCommonDao.getQueryCommonCriteria(paginatedList,filters);
	}
    @Override
    public void deleteQueryCommonAndDetail(String[] ids){
    	for(int i=0;i<ids.length;i++){
    		String id=ids[i];
    		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
    		filters.add(new PropertyFilter("EQS_queryCommon.id",id));
    		List<QueryCommonDetail> queryCommonDetails=new ArrayList<QueryCommonDetail>();
    		queryCommonDetails=queryCommonDetailManager.getByFilters(filters);
    		if(queryCommonDetails!=null&&queryCommonDetails.size()>0){
    			for(QueryCommonDetail queryCommonDetail:queryCommonDetails){
    				queryCommonDetailManager.remove(queryCommonDetail.getId());
    			}
    		}
    		this.remove(id);
    	}
    }	
}