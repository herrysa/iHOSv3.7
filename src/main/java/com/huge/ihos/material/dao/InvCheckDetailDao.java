package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.InvCheck;
import com.huge.ihos.material.model.InvCheckDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvCheckDetail table.
 */
public interface InvCheckDetailDao extends GenericDao<InvCheckDetail, String> {
	public JQueryPager getInvCheckDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<InvCheckDetail> getInvCheckDetailsInSameCheck(InvCheck invCheck);
}