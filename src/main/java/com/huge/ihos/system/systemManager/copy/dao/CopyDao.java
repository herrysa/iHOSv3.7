package com.huge.ihos.system.systemManager.copy.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.copy.model.Copy;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Copy table.
 */
public interface CopyDao extends GenericDao<Copy, String> {
	public JQueryPager getCopyCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<Copy> getRightCopy(List dataprivi);

	public Copy getCopyByCode(String copyCode);
}