package com.huge.ihos.accounting.kjyear.dao;


import java.util.HashMap;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.accounting.kjyear.model.KjYear;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the KjYear table.
 */
public interface KjYearDao extends GenericDao<KjYear, String> {
	public JQueryPager getKjYearCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public KjYear getKjyearByDate(String orgCode,String optDate);

	public KjYear getKjYear(HashMap<String, String> environment);
}