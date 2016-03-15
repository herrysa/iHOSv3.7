package com.huge.ihos.kq.kqUpData.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqUpData.dao.KqUpItemFormulaDao;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormula;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqUpItemFormulaDao")
public class KqUpItemFormulaDaoHibernate extends GenericDaoHibernate<KqUpItemFormula, String> implements KqUpItemFormulaDao {

    public KqUpItemFormulaDaoHibernate() {
        super(KqUpItemFormula.class);
    }
    
    public JQueryPager getKqUpItemFormulaCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("formulaId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqUpItemFormula.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqUpItemFormulaCriteria", e);
			return paginatedList;
		}

	}
}
