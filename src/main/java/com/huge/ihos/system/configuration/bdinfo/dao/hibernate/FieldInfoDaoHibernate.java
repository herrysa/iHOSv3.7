package com.huge.ihos.system.configuration.bdinfo.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.bdinfo.dao.FieldInfoDao;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("fieldInfoDao")
public class FieldInfoDaoHibernate extends GenericDaoHibernate<FieldInfo, String> implements FieldInfoDao {

    public FieldInfoDaoHibernate() {
        super(FieldInfo.class);
    }
    
    public JQueryPager getFieldInfoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, FieldInfo.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getFieldInfoCriteria", e);
			return paginatedList;
		}

	}
}
