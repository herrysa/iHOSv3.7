package com.huge.ihos.system.formDesigner.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.formDesigner.model.FormDesigner;
import com.huge.ihos.system.formDesigner.dao.FormDesignerDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("formDesignerDao")
public class FormDesignerDaoHibernate extends GenericDaoHibernate<FormDesigner, String> implements FormDesignerDao {

    public FormDesignerDaoHibernate() {
        super(FormDesigner.class);
    }
    
    public JQueryPager getFormDesignerCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("formId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, FormDesigner.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getFormDesignerCriteria", e);
			return paginatedList;
		}

	}
}
