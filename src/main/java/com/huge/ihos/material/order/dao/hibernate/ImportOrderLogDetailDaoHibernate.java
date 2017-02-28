package com.huge.ihos.material.order.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.material.order.model.ImportOrderLogDetail;
import com.huge.ihos.material.order.dao.ImportOrderLogDetailDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Repository("importOrderLogDetailDao")
public class ImportOrderLogDetailDaoHibernate extends GenericDaoHibernate<ImportOrderLogDetail, String> implements ImportOrderLogDetailDao {

    public ImportOrderLogDetailDaoHibernate() {
        super(ImportOrderLogDetail.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getImportOrderLogDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("logDetailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ImportOrderLogDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getImportOrderLogDetailCriteria", e);
			return paginatedList;
		}

	}
}
