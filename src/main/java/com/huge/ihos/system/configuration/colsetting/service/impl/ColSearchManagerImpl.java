package com.huge.ihos.system.configuration.colsetting.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.colsetting.dao.ColSearchDao;
import com.huge.ihos.system.configuration.colsetting.model.ColSearch;
import com.huge.ihos.system.configuration.colsetting.service.ColSearchManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("colSearchManager")
public class ColSearchManagerImpl extends GenericManagerImpl<ColSearch, String> implements ColSearchManager {
    private ColSearchDao colSearchDao;

    @Autowired
    public ColSearchManagerImpl(ColSearchDao colSearchDao) {
        super(colSearchDao);
        this.colSearchDao = colSearchDao;
    }
    
    public JQueryPager getColSearchCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return colSearchDao.getColSearchCriteria(paginatedList,filters);
	}
    
    public List<ColSearch> getByEntityName(String entityName){
    	return colSearchDao.getByEntityName(entityName);
    }

	@Override
	public List<ColSearch> getByTemplName(String templName,String entityName,String userId) {
		return colSearchDao.getByTemplName(templName,entityName,userId);
	}

	@Override
	public void delByTemplName(String templName,String entityName,String userId) {
		List<ColSearch> colSearchs = colSearchDao.getByTemplName(templName,entityName,userId);
		if(colSearchs!=null&&colSearchs.size()!=0){
			this.getHibernateTemplate().deleteAll(colSearchs);
		}
	}

	@Override
	public List<HashMap<String,String>> getAllTempl(String entityName,String userId) {
		return colSearchDao.getAllTempl(entityName,userId);
	}
}