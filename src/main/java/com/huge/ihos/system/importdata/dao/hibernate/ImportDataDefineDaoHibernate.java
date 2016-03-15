package com.huge.ihos.system.importdata.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.importdata.dao.ImportDataDefineDao;
import com.huge.ihos.system.importdata.model.ImportDataDefine;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("importDataDefineDao")
public class ImportDataDefineDaoHibernate extends GenericDaoHibernate<ImportDataDefine, String> implements ImportDataDefineDao {

    public ImportDataDefineDaoHibernate() {
        super(ImportDataDefine.class);
    }
    
    public JQueryPager getImportDataDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("interfaceId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, ImportDataDefine.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getImportDataDefineCriteria", e);
			return paginatedList;
		}

	}
    public ImportDataDefine findById(String interfaceId) {
    	return (ImportDataDefine)this.hibernateTemplate.find("from ImportDataDefine define where define.interfaceId=? order by define.sn asc",interfaceId);
    }
}
