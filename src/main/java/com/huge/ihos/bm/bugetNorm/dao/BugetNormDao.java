package com.huge.ihos.bm.bugetNorm.dao;


import java.util.List;

import com.huge.ihos.bm.bugetNorm.model.BugetNorm;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BugetNorm table.
 */
public interface BugetNormDao extends GenericDao<BugetNorm, String> {
	public JQueryPager getBugetNormCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}