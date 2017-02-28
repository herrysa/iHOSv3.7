package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvDetail table.
 */
public interface InvDetailDao extends GenericDao<InvDetail, String> {
	public JQueryPager getInvDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<InvDetail> getInvDetailsInSameInvMain(InvMain invMain);
}