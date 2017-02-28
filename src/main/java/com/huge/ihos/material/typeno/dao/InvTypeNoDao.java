package com.huge.ihos.material.typeno.dao;


import java.util.List;

import com.huge.ihos.material.typeno.model.InvTypeNo;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvTypeNo table.
 */
public interface InvTypeNoDao extends GenericDao<InvTypeNo, Long> {
	public JQueryPager getInvTypeNoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public String getTypeByNo(String no,String orgCode,String copyCode);

}