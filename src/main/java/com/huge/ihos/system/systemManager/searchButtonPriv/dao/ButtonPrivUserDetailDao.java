package com.huge.ihos.system.systemManager.searchButtonPriv.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SearchButtonPrivUserDetail table.
 */
public interface ButtonPrivUserDetailDao extends GenericDao<ButtonPrivUserDetail, String> {
	public JQueryPager getButtonPrivUserDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ButtonPrivUserDetail> findNoRightPrivDetail(String userId,String searchId);

	public List<ButtonPrivUserDetail> findByUserAndSearch(String userId,
			String searchId);
}