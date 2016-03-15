package com.huge.ihos.accounting.glabstract.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.accounting.glabstract.model.GLAbstract;
import com.huge.ihos.accounting.glabstract.dao.GLAbstractDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gLAbstractDao")
public class GLAbstractDaoHibernate extends GenericDaoHibernate<GLAbstract, String> implements GLAbstractDao {

    public GLAbstractDaoHibernate() {
        super(GLAbstract.class);
    }
    
    public JQueryPager getGLAbstractCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("abstractId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GLAbstract.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGLAbstractCriteria", e);
			return paginatedList;
		}

	}
}
