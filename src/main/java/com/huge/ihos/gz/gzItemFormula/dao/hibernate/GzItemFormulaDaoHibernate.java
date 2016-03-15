package com.huge.ihos.gz.gzItemFormula.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormula;
import com.huge.ihos.gz.gzItemFormula.dao.GzItemFormulaDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzItemFormulaDao")
public class GzItemFormulaDaoHibernate extends GenericDaoHibernate<GzItemFormula, String> implements GzItemFormulaDao {

    public GzItemFormulaDaoHibernate() {
        super(GzItemFormula.class);
    }
    
    public JQueryPager getGzItemFormulaCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("formulaId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzItemFormula.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzItemFormulaCriteria", e);
			return paginatedList;
		}

	}
}
