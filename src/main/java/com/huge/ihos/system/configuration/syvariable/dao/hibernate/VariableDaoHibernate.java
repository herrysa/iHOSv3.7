package com.huge.ihos.system.configuration.syvariable.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.syvariable.dao.VariableDao;
import com.huge.ihos.system.configuration.syvariable.model.Variable;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("variableDao")
public class VariableDaoHibernate extends GenericDaoHibernate<Variable, String> implements VariableDao {

    public VariableDaoHibernate() {
        super(Variable.class);
    }
    
    public JQueryPager getSyVariableCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("variableName");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, Variable.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getSyVariableCriteria", e);
			return paginatedList;
		}

	}

    @SuppressWarnings("unchecked")
    public List<Variable> getVariableByStatus(Boolean isSys) {
    	Criteria criteria = getCriteria();
    	List<Variable> list = criteria.add(Restrictions.eq("disabled", false))
    			.add(Restrictions.eq("sys", isSys))
    			.list();
    	return list;
    }
    @SuppressWarnings("unchecked")
    public List<Variable> getEnableVariables() {
    	Criteria criteria = getCriteria();
    	criteria.add(Restrictions.eq("disabled", false));
    	criteria.addOrder(Order.asc("sn"));
    	List<Variable> list = criteria.list();
    	return list;
    }
}
