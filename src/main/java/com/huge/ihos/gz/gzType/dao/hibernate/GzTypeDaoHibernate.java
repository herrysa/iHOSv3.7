package com.huge.ihos.gz.gzType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.gz.gzType.model.GzType;
import com.huge.ihos.gz.gzType.dao.GzTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("gzTypeDao")
public class GzTypeDaoHibernate extends GenericDaoHibernate<GzType, String> implements GzTypeDao {

    public GzTypeDaoHibernate() {
        super(GzType.class);
    }
    
    public JQueryPager getGzTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("gzTypeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, GzType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getGzTypeCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public List<GzType> getAllAvailable(String gzTypePriv) {
    	Criteria criteria = this.getCriteria();
    	criteria.add(Restrictions.eq("disabled", false));
    	criteria.add(Restrictions.ne("gzTypeId", "XT"));
    	if(gzTypePriv != null && !"".equals(gzTypePriv)) {
    		if(!gzTypePriv.startsWith("select")&&!gzTypePriv.startsWith("SELECT")) {
    			criteria.add(Restrictions.in("gzTypeId", gzTypePriv.split(",")));
    		}
    	}
    	return criteria.list();
    }
}
