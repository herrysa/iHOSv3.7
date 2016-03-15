package com.huge.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.dao.UtilOptDao;
import com.huge.ihos.hql.HqlUtil;
import com.huge.service.UtilOptService;
import com.huge.webapp.util.PropertyFilter;

@Service( "utilOptService" )
public class UtilOptServiceImpl implements UtilOptService{

	@Autowired
	private UtilOptDao utilOptDao;

	public UtilOptDao getUtilOptDao() {
		return utilOptDao;
	}

	public void setUtilOptDao(UtilOptDao utilOptDao) {
		this.utilOptDao = utilOptDao;
	}

	@Override
	public List<Map<String, String>> getByFilters(String entityName,
			List<PropertyFilter> filters) {
		return utilOptDao.getByFilters(entityName, filters);
	}

	@Override
	public List<Map<String, String>> getByHqlUtil(HqlUtil hqlUtil) {
		return utilOptDao.getByHqlUtil(hqlUtil);
	}
	
	@Override
	public List getByHql(String hql) {
		return utilOptDao.getByHql(hql);
	}
	@Override
	public List<Map<String, Object>> getBySqlToMap(String sql){
		return utilOptDao.getBySqlToMap(sql);
	}
}
