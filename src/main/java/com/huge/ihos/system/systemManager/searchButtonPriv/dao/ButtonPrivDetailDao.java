package com.huge.ihos.system.systemManager.searchButtonPriv.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SearchButtoPrivDetail table.
 */
public interface ButtonPrivDetailDao extends GenericDao<ButtonPrivDetail, String> {
	public JQueryPager getButtonPrivDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ButtonPrivDetail> findNoRightPrivDetail(String roleId,String searchId);
}