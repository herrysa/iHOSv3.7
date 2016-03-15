package com.huge.ihos.kq.kqType.dao.hibernate;



import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.kq.kqType.model.KqType;
import com.huge.ihos.kq.kqType.dao.KqTypeDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;


@Repository("kqTypeDao")
public class KqTypeDaoHibernate extends GenericDaoHibernate<KqType, String> implements KqTypeDao {

    public KqTypeDaoHibernate() {
        super(KqType.class);
    }
    
    public JQueryPager getKqTypeCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {

		try {

			if (paginatedList.getSortCriterion() == null)
				paginatedList.setSortCriterion("kqTypeId");
			Map<String, Object> resultMap = getAppManagerCriteriaWithSearch(paginatedList, KqType.class, filters);
			paginatedList.setList((List) resultMap.get("list"));
			int count = 0;
			Integer icount = (Integer) resultMap.get("count");
			if (icount != null)
				count = icount.intValue();
			paginatedList.setTotalNumberOfRows(count);
			return paginatedList;
		} catch (Exception e) {
			log.error("getKqTypeCriteria", e);
			return paginatedList;
		}

	}
    @Override
    public List<KqType> getAllAvailable(String kqTypePriv) {
    	Criteria criteria = this.getCriteria();
    	criteria.add(Restrictions.eq("disabled", false));
    	criteria.add(Restrictions.ne("kqTypeCode", "XT"));
    	if(kqTypePriv != null && !"".equals(kqTypePriv)) {
    		if(!kqTypePriv.startsWith("select")&&!kqTypePriv.startsWith("SELECT")) {
    			String[] kqType = kqTypePriv.split(",");
    			if(kqType!=null){
    				criteria.add(Restrictions.in("kqTypeId", kqType));
    			}
    		}
    	}
    	return criteria.list();
    }
}
