package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvCheck;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvCheck table.
 */
public interface InvCheckDao extends GenericDao<InvCheck, String> {
	public JQueryPager getInvCheckCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public InvCheck getInvCheckByCheckNo(String checkNo,String orgCode,String copyCode);
}