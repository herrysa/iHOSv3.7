package com.huge.ihos.system.excelTemplate.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.excelTemplate.dao.ExcelTemplateDao;
import com.huge.ihos.system.excelTemplate.model.ExcelTemplate;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("excelTemplateDao")
public class ExcelTemplateDaoHibernate extends GenericDaoHibernate<ExcelTemplate, String> implements ExcelTemplateDao {

    public ExcelTemplateDaoHibernate() {
        super(ExcelTemplate.class);
    }
    
    public JQueryPager getExcelTemplateCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("templateId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ExcelTemplate.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getExcelTemplateCriteria", e);
			return paginatedList;
		}

	}
}
