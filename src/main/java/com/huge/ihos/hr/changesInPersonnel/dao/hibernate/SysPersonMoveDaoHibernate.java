package com.huge.ihos.hr.changesInPersonnel.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove;
import com.huge.ihos.hr.changesInPersonnel.dao.SysPersonMoveDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("sysPersonMoveDao")
public class SysPersonMoveDaoHibernate extends GenericDaoHibernate<SysPersonMove, String> implements SysPersonMoveDao {

    public SysPersonMoveDaoHibernate() {
        super(SysPersonMove.class);
    }
    
    @SuppressWarnings("rawtypes")
	public JQueryPager getSysPersonMoveCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("id");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, SysPersonMove.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getsysPersonMoveCriteria", e);
			return paginatedList;
		}

	}
}
