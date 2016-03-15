package com.huge.ihos.gz.gzType.dao;


import java.util.List;

import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzType table.
 */
public interface GzTypeDao extends GenericDao<GzType, String> {
	public JQueryPager getGzTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	
	public List<GzType> getAllAvailable(String gzTypePriv);
	
}