package com.huge.ihos.system.importdata.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.importdata.dao.ImportDataDefineDetailDao;
import com.huge.ihos.system.importdata.model.ImportDataDefineDetail;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("importDataDefineDetailDao")
public class ImportDataDefineDetailDaoHibernate extends GenericDaoHibernate<ImportDataDefineDetail, String> implements ImportDataDefineDetailDao {

    public ImportDataDefineDetailDaoHibernate() {
        super(ImportDataDefineDetail.class);
    }
    
    public JQueryPager getImportDataDefineDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("detailId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ImportDataDefineDetail.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getImportDataDefineDetailCriteria", e);
			return paginatedList;
		}

	}
}
