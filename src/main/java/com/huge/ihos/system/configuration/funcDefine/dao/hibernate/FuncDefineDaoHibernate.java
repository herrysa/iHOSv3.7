package com.huge.ihos.system.configuration.funcDefine.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.configuration.funcDefine.dao.FuncDefineDao;
import com.huge.ihos.system.configuration.funcDefine.model.FuncDefine;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("funcDefineDao")
public class FuncDefineDaoHibernate extends GenericDaoHibernate<FuncDefine, String> implements FuncDefineDao {

    public FuncDefineDaoHibernate() {
        super(FuncDefine.class);
    }
    
    public JQueryPager getFuncDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("funcId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, FuncDefine.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getFuncDefineCriteria", e);
			return paginatedList;
		}

	}
}
