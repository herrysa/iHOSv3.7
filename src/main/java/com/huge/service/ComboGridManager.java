package com.huge.service;

import com.huge.webapp.pagers.JQueryPager;

public interface ComboGridManager {
	public JQueryPager getComboGridCriteria(final JQueryPager paginatedList,String sql, Object[] args);
}