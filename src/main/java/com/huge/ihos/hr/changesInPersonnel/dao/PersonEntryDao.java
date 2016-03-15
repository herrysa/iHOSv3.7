package com.huge.ihos.hr.changesInPersonnel.dao;


import java.util.List;

import com.huge.ihos.hr.changesInPersonnel.model.PersonEntry;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PersonEntry table.
 */
public interface PersonEntryDao extends GenericDao<PersonEntry, String> {
	public JQueryPager getPersonEntryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}