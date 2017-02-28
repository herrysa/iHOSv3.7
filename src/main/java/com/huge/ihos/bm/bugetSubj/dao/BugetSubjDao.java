package com.huge.ihos.bm.bugetSubj.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BugetSubj table.
 */
public interface BugetSubjDao extends GenericDao<BugetSubj, String> {
	public JQueryPager getBugetSubjCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}