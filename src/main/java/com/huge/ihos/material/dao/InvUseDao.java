package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.ihos.material.model.InvUse;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvUse table.
 */
public interface InvUseDao extends GenericDao<InvUse, String> {
	public JQueryPager getInvUseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<InvUse> getAllByCO(String copycode,String orgcode);
}