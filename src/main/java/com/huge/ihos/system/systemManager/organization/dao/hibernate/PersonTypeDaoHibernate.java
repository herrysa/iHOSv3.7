package com.huge.ihos.system.systemManager.organization.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.organization.dao.PersonTypeDao;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("personTypeDao")
public class PersonTypeDaoHibernate extends GenericDaoHibernate<PersonType, String> implements PersonTypeDao {

    public PersonTypeDaoHibernate() {
        super(PersonType.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getPersonTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, PersonType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getHrPersonTypeCriteria", e);
			return paginatedList;
		}

	}
}
