package com.huge.ihos.kq.kqType.dao;


import java.util.List;

import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KqType table.
 */
public interface KqTypeDao extends GenericDao<KqType, String> {
	public JQueryPager getKqTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<KqType> getAllAvailable(String kqTypePriv);
}